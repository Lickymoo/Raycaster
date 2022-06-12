package com.mitchellg.raycaster.engine.model.render;

import com.aparapi.Kernel;
import com.mitchellg.raycaster.engine.model.game.*;
import com.mitchellg.raycaster.engine.model.location.Vector3f;
import com.mitchellg.raycaster.engine.model.render.math.Ray;
import com.mitchellg.raycaster.engine.model.render.math.RayHit;
import lombok.Getter;

import java.awt.*;
import java.awt.image.BufferedImage;

@Getter
public class Renderer {
    private final Game game;
    private final Camera camera;

    private static final float GLOBAL_ILLUMINATION = 0.3F;
    private static final float SKY_EMISSION = 0.5F;
    private static final int MAX_REFLECTION_BOUNCES = 5;
    private static final boolean SHOW_SKYBOX = true;

    public Renderer(Game game){
        this.game = game;
        this.camera = new Camera(80);
    }

    BufferedImage img;
    public void render(Graphics graphics){
        int height = game.getHeight();
        int width = game.getWidth();

        if(img == null || img.getHeight() != height || img.getWidth() != width){
            img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        }
        if(game.getWindow() == null) return;
        if(game.getCurrentScene() == null) return;

        /*
        Kernel kernel = new Kernel() {
            @Override
            public void run() {
                int i = getGlobalId();

                int x = i % width;
                int y = i / width;


                float[] uv = getNormalizedScreenCoordinates(x, y, width, height);
                PixelData pixelData = computePixelInfo(game.getCurrentScene(), uv[0], uv[1]);
                img.setRGB(x, y, pixelData.getColor().getRGB());
            }
        };*/

        for(int x = 0; x < width; x++){
            for(int y = 0; y < height; y++){
                float[] uv = getNormalizedScreenCoordinates(x, y, width, height);
                PixelData pixelData = computePixelInfo(game.getCurrentScene(), uv[0], uv[1]);
                img.setRGB(x, y, pixelData.getColor().getRGB());
            }
        }
        try {
            //kernel.setAutoCleanUpArrays(true);
            //kernel.execute(width * height);
        }catch (Exception ignore){
            ignore.printStackTrace();
        }
       // kernel.dispose();

        graphics.drawImage(img, 0, 0, null);
        graphics.dispose();

        img.flush();
    }

    public float[] getNormalizedScreenCoordinates(int x, int y, int width, int height) {
        float u = 0, v = 0;
        if (width > height) {
            u = (float)(x - width/2+height/2) / height * 2 - 1;
            v =  -((float) y / height * 2 - 1);
        } else {
            u = (float)x / width * 2 - 1;
            v =  -((float) (y - height/2+width/2) / width * 2 - 1);
        }

        return new float[]{u, v};
    }

    public PixelData computePixelInfo(GameScene scene, float u, float v){
        Vector3f eyePos = new Vector3f(0, 0, (float) (-1 / Math.tan(Math.toRadians(camera.getFOV() / 2))));
        Vector3f rayDir = new Vector3f(u, v, 0).subtract(eyePos).normalize().rotateYP(camera.getYaw(), camera.getPitch());
        Ray ray = new Ray(eyePos.add(camera.getPosition()), rayDir);

        RayHit hit = scene.raycast(ray);
        if(hit != null){
            return computePixelInfoAtHit(scene, hit, MAX_REFLECTION_BOUNCES);
        }else if(scene.getSkybox() != null){
            Color sbColor = scene.getSkybox().getColor(rayDir);
            return new PixelData(sbColor, Float.POSITIVE_INFINITY, sbColor.getLuminance()*SKY_EMISSION);
        }else{
            return new PixelData(Color.BLACK, Float.POSITIVE_INFINITY, 0);
        }
    }

    private PixelData computePixelInfoAtHit(GameScene scene, RayHit hit, int recursionLimit) {
        Vector3f hitPos = hit.getPosition();
        Vector3f rayDir = hit.getRay().getDirection();
        Geometry hitGeometry = hit.getGeometry();
        GameObject object = hitGeometry.getParent();
        Material mat = object.getMaterial();

        Color hitColor = mat.getTextureColor(hitPos.subtract(object.getTransform().getPosition()));
        float brightness = getDiffuseBrightness(scene, hit);
        float specularBrightness = getSpecularBrightness(scene, hit);
        float reflectivity = mat.getReflectivity();
        float emission = mat.getEmission();

        PixelData reflection;
        Vector3f reflectionVector = rayDir.subtract(hit.getNormal().multiply(2*Vector3f.dot(rayDir, hit.getNormal())));
        Vector3f reflectionRayOrigin = hitPos.add(reflectionVector.multiply(0.001F)); // Add a little to avoid hitting the same solid again
        RayHit reflectionHit = recursionLimit > 0 ? scene.raycast(new Ray(reflectionRayOrigin, reflectionVector)) : null;
        if (reflectionHit != null) {
            reflection = computePixelInfoAtHit(scene, reflectionHit, recursionLimit-1);
        } else {
            Color sbColor = scene.getSkybox().getColor(reflectionVector);
            reflection = new PixelData(sbColor, Float.POSITIVE_INFINITY, sbColor.getLuminance()*SKY_EMISSION);
        }

        Color pixelColor = Color.lerp(hitColor, reflection.getColor(), reflectivity) // Reflected color
                .multiply(brightness) // Diffuse lighting
                .add(specularBrightness) // Specular lighting
                .add(hitColor.multiply(emission)) // Object emission
                .add(reflection.getColor().multiply(reflection.getEmission()*reflectivity)); // Indirect illumination

        return new PixelData(pixelColor, Vector3f.distance(camera.getPosition(), hitPos), Math.min(1, emission+reflection.getEmission()*reflectivity+specularBrightness));

    }

    private float getDiffuseBrightness(GameScene scene, RayHit hit) {
        Light sceneLight = scene.getLight();

        // Raytrace to light to check if something blocks the light
        RayHit lightBlocker = scene.raycast(new Ray(sceneLight.getPosition(), hit.getPosition().subtract(sceneLight.getPosition()).normalize()));
        float r = 0;
        if (lightBlocker != null && lightBlocker.getGeometry() != hit.getGeometry()) {
            r = GLOBAL_ILLUMINATION; // GOBAL_ILLUMINATION = Minimum brightness
        } else {
            r = Math.max(GLOBAL_ILLUMINATION, Math.min(1, Vector3f.dot(hit.getNormal(), sceneLight.getPosition().subtract(hit.getPosition()))));
        }

        if(Float.isNaN(r))
            r = 0;
        return r;
    }

    private float getSpecularBrightness(GameScene scene, RayHit hit) {
        Vector3f hitPos = hit.getPosition();
        Vector3f cameraDirection = camera.getPosition().subtract(hitPos).normalize();
        Vector3f lightDirection = hitPos.subtract(scene.getLight().getPosition()).normalize();
        Vector3f lightReflectionVector = lightDirection.subtract(hit.getNormal().multiply(2*Vector3f.dot(lightDirection, hit.getNormal())));

        GameObject object = hit.getGeometry().getParent();
        Material mat = object.getMaterial();

        float specularFactor = Math.max(0, Math.min(1, Vector3f.dot(lightReflectionVector, cameraDirection)));
        float r = (float) Math.pow(specularFactor, 2)*mat.getReflectivity();

        if(Float.isNaN(r))
            r = 0;
        return r;
    }
}
