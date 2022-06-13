package com.mitchellg.raycaster.engine.model.game;

import com.mitchellg.raycaster.engine.model.location.Vector3f;
import com.mitchellg.raycaster.engine.model.render.geometry.Geometry;
import com.mitchellg.raycaster.engine.model.render.geometry.impl.Vert;
import org.lwjgl.PointerBuffer;
import org.lwjgl.assimp.*;

import java.util.ArrayList;
import java.util.List;

public class ModelLoader {
    public static Geometry[] getGeometry(float[] vertices, int[] indices, float[] normals){
        ArrayList<Geometry> geometryList = new ArrayList<>();

        Vector3f[] vertBuffer = new Vector3f[indices.length];
        Vector3f[] normBuffer = new Vector3f[indices.length];

        for(int i = 0 ; i < indices.length; i++){
            int pointer = indices[i];
            vertBuffer[i] = new Vector3f(vertices[pointer * 3], vertices[pointer * 3 + 1], vertices[pointer * 3 + 2]);
            normBuffer[i] = new Vector3f(normals[pointer], normals[pointer + 1], normals[pointer + 2]);
        }

        for (int i = 0; i < indices.length; i++) {
            normBuffer[i] = new Vector3f();

            normBuffer[i].setX(normals[indices[i / 6]]);
            normBuffer[i].setY(normals[indices[i / 6] + 1]);
            normBuffer[i].setZ(normals[indices[i / 6] + 2]);
        }

        for(int i = 0; i < vertBuffer.length; i+=3){
            Vector3f v0 = vertBuffer[i    ];
            Vector3f v1 = vertBuffer[i + 1];
            Vector3f v2 = vertBuffer[i + 2];

            Vector3f n0 = normBuffer[i    ];
            Vector3f n1 = normBuffer[i + 1];
            Vector3f n2 = normBuffer[i + 2];

            Vert vert = new Vert(
                    null,
                    v0,
                    v1,
                    v2,
                    n0,
                    n1,
                    n2
            );
            geometryList.add(vert);
        }

        return geometryList.toArray(new Geometry[0]);
    }

    public static Geometry[] loadModel(String filepath, boolean asResource){
        if(asResource){
            filepath = ModelLoader.class.getClassLoader().getResource(filepath).getPath();
            filepath = filepath.replaceFirst("/", "");
            System.out.println(filepath);
        }
        AIScene scene = Assimp.aiImportFile(filepath, Assimp.aiProcess_Triangulate);
        if(scene == null) return null;
        PointerBuffer buffer = scene.mMeshes();

        List<Geometry[]> geometries = new ArrayList<>();
        for(int i = 0; i < buffer.limit(); i++){
            AIMesh mesh = AIMesh.create(buffer.get(i));
            geometries.add(processMesh(mesh));
        }

        Assimp.aiReleaseImport(scene);



        return geometries.get(0);
    }

    public static Geometry[] processMesh(AIMesh mesh){

        float[] vertices = null;
        float[] normals = null;
        int[] indices = null;

        try {
            AIVector3D.Buffer vertBuffer = mesh.mVertices();
            vertices = new float[vertBuffer.limit() * 3];
            for (int i = 0; i < vertBuffer.limit(); i++) {
                AIVector3D vector = vertBuffer.get(i);

                vertices[i * 3] = vector.x();
                vertices[i * 3 + 1] = vector.y();
                vertices[i * 3 + 2] = vector.z();
            }

        }catch (NullPointerException ignored){
        }


        try {
            AIVector3D.Buffer normBuffer = mesh.mNormals();
            normals = new float[normBuffer.limit() * 3];
            for (int i = 0; i < normBuffer.limit(); i++) {
                AIVector3D norm = normBuffer.get(i);

                normals[i * 3 ] = (norm.x());
                normals[i * 3 + 1] = (norm.y());
                normals[i * 3 + 2] = (norm.z());
            }
        }catch (NullPointerException ignored){
        }

        try{

            AIFace.Buffer indicesBuffer = mesh.mFaces();
            indices = new int[mesh.mNumFaces() * 3];
            for(int i = 0; i < indicesBuffer.limit(); i++){
                AIFace face = indicesBuffer.get(i);
                indices[i * 3] = face.mIndices().get(0);
                indices[i * 3 + 1] = face.mIndices().get(1);
                indices[i * 3 + 2] = face.mIndices().get(2);
            }
        }catch (NullPointerException e){
        }

        return getGeometry(vertices, indices, normals);
    }
}
