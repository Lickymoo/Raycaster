package com.mitchellg.raycaster.engine.model.location;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Vector3f {
    private float x, y, z;

    public Vector3f(){
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }

    public Vector3f(float x, float y, float z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3f(float i){
        this.x = i;
        this.y = i;
        this.z = i;
    }

    public Vector3f(float[] val){
        x = val[0];
        y = val[1];
        z = val[2];
    }

    public Vector3f add(Vector3f value){
        return new Vector3f(this.x + value.getX(), this.y + value.getY(), this.z + value.getZ());
    }

    public Vector3f add(float value){
        return new Vector3f(this.x + value, this.y + value, this.z + value);
    }

    public Vector3f subtract(Vector3f value){
        return new Vector3f(this.x - value.getX(), this.y - value.getY(), this.z - value.getZ());
    }

    public Vector3f multiply(float scalar){
        return new Vector3f(this.x * scalar, this.y * scalar, this.z * scalar);
    }

    public Vector3f multiply(Vector3f value){
        return new Vector3f(this.x * value.getX(), this.y * value.getY(), this.z * value.getZ());
    }

    public Vector3f normalize(){
        float m = (float)Math.sqrt(x*x + y*y + z*z);
        return new Vector3f(this.x / m , this.y / m, this.z /m);
    }

    public float length() {
        return (float) Math.sqrt(x*x+y*y+z*z);
    }

    public static float dot(Vector3f a, Vector3f b) {
        return a.x * b.x + a.y * b.y + a.z * b.z;
    }

    public static float distance(Vector3f a, Vector3f b) {
        return (float) Math.sqrt(Math.pow(a.x - b.x, 2)+Math.pow(a.y - b.y, 2)+Math.pow(a.z - b.z, 2));
    }

    public static Vector3f cross(Vector3f a, Vector3f b){
        float[] cp = new float[3];

        cp[0] = a.getY() * b.getZ() - a.getZ() * b.getY();
        cp[1] = a.getZ() * b.getX() - a.getX() * b.getZ();
        cp[2] = a.getX() * b.getY() - a.getY() * b.getX();

        return new Vector3f(cp[0], cp[1], cp[2]);
    }

    public Vector3f rotateYP(float yaw, float pitch) {
        // Convert to radians
        double yawRads = Math.toRadians(yaw);
        double pitchRads = Math.toRadians(pitch);

        // Step one: Rotate around X axis (pitch)
        float _y = (float) (y*Math.cos(pitchRads) - z*Math.sin(pitchRads));
        float _z = (float) (y*Math.sin(pitchRads) + z*Math.cos(pitchRads));

        // Step two: Rotate around the Y axis (yaw)
        float _x = (float) (x*Math.cos(yawRads) + _z*Math.sin(yawRads));
        _z = (float) (-x*Math.sin(yawRads) + _z*Math.cos(yawRads));

        return new Vector3f(_x, _y, _z);
    }

    public Vector3f rotateRYP(float roll, float yaw, float pitch){

        double yawRads = Math.toRadians(yaw);
        double pitchRads = Math.toRadians(pitch);
        double rollRads = Math.toRadians(roll);

        float _x = x;
        float _y = y;
        float _z = z;
        //Rotate around X axis
        _y = (float) (_y*Math.cos(pitchRads) - _z*Math.sin(pitchRads));
        _z = (float) (_y*Math.sin(pitchRads) + _z*Math.cos(pitchRads));

        //Rotate around Y axis
        _x = (float) (_x*Math.cos(yawRads) + _z*Math.sin(yawRads));
        _z = (float) (_z*Math.cos(yawRads) - _x*Math.sin(yawRads));

        //Rotate aroudn Z axis
        _x = (float) (_x*Math.cos(rollRads) - _y*Math.sin(rollRads));
        _y = (float) (_x*Math.sin(rollRads) + _y*Math.cos(rollRads));

        return new Vector3f(_x, _y, _z);
    }

    public float getMaxAttrib(){
        return Math.max(x, Math.max(y, z));
    }

    public float getMinAttrib(){
        return Math.min(x, Math.min(y, z));
    }

    public float[] asArray3f(){
        return new float[]{x, y, z};
    }
}

































