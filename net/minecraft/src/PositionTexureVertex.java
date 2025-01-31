/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.Vec3D;

public class PositionTexureVertex {
    public Vec3D vector3D;
    public float texturePositionX;
    public float texturePositionY;

    public PositionTexureVertex(float var1, float var2, float var3, float var4, float var5) {
        this(Vec3D.createVectorHelper(var1, var2, var3), var4, var5);
    }

    public PositionTexureVertex setTexturePosition(float var1, float var2) {
        return new PositionTexureVertex(this, var1, var2);
    }

    public PositionTexureVertex(PositionTexureVertex var1, float var2, float var3) {
        this.vector3D = var1.vector3D;
        this.texturePositionX = var2;
        this.texturePositionY = var3;
    }

    public PositionTexureVertex(Vec3D var1, float var2, float var3) {
        this.vector3D = var1;
        this.texturePositionX = var2;
        this.texturePositionY = var3;
    }
}

