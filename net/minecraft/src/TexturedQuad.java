/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.PositionTexureVertex;
import net.minecraft.src.Tessellator;
import net.minecraft.src.Vec3D;

public class TexturedQuad {
    public PositionTexureVertex[] vertexPositions;
    public int nVertices = 0;
    private boolean invertNormal = false;

    public TexturedQuad(PositionTexureVertex[] var1) {
        this.vertexPositions = var1;
        this.nVertices = var1.length;
    }

    public TexturedQuad(PositionTexureVertex[] var1, int var2, int var3, int var4, int var5) {
        this(var1);
        float var6 = 0.0015625f;
        float var7 = 0.003125f;
        var1[0] = var1[0].setTexturePosition((float)var4 / 64.0f - var6, (float)var3 / 32.0f + var7);
        var1[1] = var1[1].setTexturePosition((float)var2 / 64.0f + var6, (float)var3 / 32.0f + var7);
        var1[2] = var1[2].setTexturePosition((float)var2 / 64.0f + var6, (float)var5 / 32.0f - var7);
        var1[3] = var1[3].setTexturePosition((float)var4 / 64.0f - var6, (float)var5 / 32.0f - var7);
    }

    public void flipFace() {
        PositionTexureVertex[] var1 = new PositionTexureVertex[this.vertexPositions.length];
        int var2 = 0;
        while (var2 < this.vertexPositions.length) {
            var1[var2] = this.vertexPositions[this.vertexPositions.length - var2 - 1];
            ++var2;
        }
        this.vertexPositions = var1;
    }

    public void draw(Tessellator var1, float var2) {
        Vec3D var3 = this.vertexPositions[1].vector3D.subtract(this.vertexPositions[0].vector3D);
        Vec3D var4 = this.vertexPositions[1].vector3D.subtract(this.vertexPositions[2].vector3D);
        Vec3D var5 = var4.crossProduct(var3).normalize();
        var1.startDrawingQuads();
        if (this.invertNormal) {
            var1.setNormal(-((float)var5.xCoord), -((float)var5.yCoord), -((float)var5.zCoord));
        } else {
            var1.setNormal((float)var5.xCoord, (float)var5.yCoord, (float)var5.zCoord);
        }
        int var6 = 0;
        while (var6 < 4) {
            PositionTexureVertex var7 = this.vertexPositions[var6];
            var1.addVertexWithUV((float)var7.vector3D.xCoord * var2, (float)var7.vector3D.yCoord * var2, (float)var7.vector3D.zCoord * var2, var7.texturePositionX, var7.texturePositionY);
            ++var6;
        }
        var1.draw();
    }
}

