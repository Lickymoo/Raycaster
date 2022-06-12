package com.mitchellg.raycaster.engine.model.render.math;

public class Mathf {
    static final float PI_f = (float) java.lang.Math.PI;
    public static final double PI = java.lang.Math.PI;
    static final float PIHalf_f = (float) (PI * 0.5);
    static final float PI2_f = PI_f * 2.0f;

    public static float cosFromSinInternal(float sin, float angle) {
        // sin(x)^2 + cos(x)^2 = 1
        float cos = (float)Math.sqrt(1.0f - sin * sin);
        float a = angle + PIHalf_f;
        float b = a - (int)(a / PI2_f) * PI2_f;
        if (b < 0.0)
            b = PI2_f + b;
        if (b >= PI_f)
            return -cos;
        return cos;
    }
}
