package com.mitchellg.raycaster.engine.model.render;


import com.mitchellg.raycaster.engine.model.location.Vector3f;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.*;

public class Skybox {
    private BufferedImage sphereImage;
    private boolean loaded;

    public Skybox(String resourceName) {
        sphereImage = new BufferedImage(2,2, BufferedImage.TYPE_INT_RGB);
        loaded = false;

        try(InputStream stream = getClass().getClassLoader().getResourceAsStream(resourceName)){
            if(stream == null) return;
            ImageIO.setUseCache(false);
            sphereImage = ImageIO.read(stream);

            loaded = true;
        } catch (IOException | IllegalArgumentException ex) {
            ex.printStackTrace();
        }
    }

    public Color getColor(Vector3f d) {
        // Convert Unit vector to texture coordinates (Wikipedia UV Unwrapping page)
        float u = (float) (0.5+Math.atan2(d.getZ(), d.getX())/(2*Math.PI));
        float v = (float) (0.5 - Math.asin(d.getY())/Math.PI);

        try {
            return Color.fromInt(sphereImage.getRGB((int)(u*(sphereImage.getWidth()-1)), (int)(v*(sphereImage.getHeight()-1))));
        } catch (Exception e) {
            System.out.println("U: "+u+" V: "+v);
            e.printStackTrace();

            return Color.MAGENTA;
        }
    }

    public boolean isLoaded() {
        return loaded;
    }

}