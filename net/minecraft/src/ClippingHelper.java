/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

public class ClippingHelper {
    public float[][] frustum = new float[16][16];
    public float[] projectionMatrix = new float[16];
    public float[] modelviewMatrix = new float[16];
    public float[] clippingMatrix = new float[16];

    public boolean isBoxInFrustum(double var1, double var3, double var5, double var7, double var9, double var11) {
        int var13 = 0;
        while (var13 < 6) {
            if ((double)this.frustum[var13][0] * var1 + (double)this.frustum[var13][1] * var3 + (double)this.frustum[var13][2] * var5 + (double)this.frustum[var13][3] <= 0.0 && (double)this.frustum[var13][0] * var7 + (double)this.frustum[var13][1] * var3 + (double)this.frustum[var13][2] * var5 + (double)this.frustum[var13][3] <= 0.0 && (double)this.frustum[var13][0] * var1 + (double)this.frustum[var13][1] * var9 + (double)this.frustum[var13][2] * var5 + (double)this.frustum[var13][3] <= 0.0 && (double)this.frustum[var13][0] * var7 + (double)this.frustum[var13][1] * var9 + (double)this.frustum[var13][2] * var5 + (double)this.frustum[var13][3] <= 0.0 && (double)this.frustum[var13][0] * var1 + (double)this.frustum[var13][1] * var3 + (double)this.frustum[var13][2] * var11 + (double)this.frustum[var13][3] <= 0.0 && (double)this.frustum[var13][0] * var7 + (double)this.frustum[var13][1] * var3 + (double)this.frustum[var13][2] * var11 + (double)this.frustum[var13][3] <= 0.0 && (double)this.frustum[var13][0] * var1 + (double)this.frustum[var13][1] * var9 + (double)this.frustum[var13][2] * var11 + (double)this.frustum[var13][3] <= 0.0 && (double)this.frustum[var13][0] * var7 + (double)this.frustum[var13][1] * var9 + (double)this.frustum[var13][2] * var11 + (double)this.frustum[var13][3] <= 0.0) {
                return false;
            }
            ++var13;
        }
        return true;
    }
}

