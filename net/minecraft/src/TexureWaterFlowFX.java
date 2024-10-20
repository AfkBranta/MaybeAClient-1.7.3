/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.Block;
import net.minecraft.src.TextureFX;
import net.skidcode.gh.maybeaclient.hacks.NoRenderHack;

public class TexureWaterFlowFX
extends TextureFX {
    protected float[] field_1138_g = new float[256];
    protected float[] field_1137_h = new float[256];
    protected float[] field_1136_i = new float[256];
    protected float[] field_1135_j = new float[256];
    private int field_1134_k = 0;
    public boolean hasTexture = false;

    public TexureWaterFlowFX() {
        super(Block.waterMoving.blockIndexInTexture + 1);
        this.tileSize = 2;
    }

    @Override
    public void onTick() {
        int var6;
        int var5;
        float var3;
        int var2;
        ++this.field_1134_k;
        if (this.hasTexture && NoRenderHack.instance.status && NoRenderHack.instance.waterAnim.value) {
            return;
        }
        int var1 = 0;
        while (var1 < 16) {
            var2 = 0;
            while (var2 < 16) {
                var3 = 0.0f;
                int var4 = var2 - 2;
                while (var4 <= var2) {
                    var5 = var1 & 0xF;
                    var6 = var4 & 0xF;
                    var3 += this.field_1138_g[var5 + var6 * 16];
                    ++var4;
                }
                this.field_1137_h[var1 + var2 * 16] = var3 / 3.2f + this.field_1136_i[var1 + var2 * 16] * 0.8f;
                ++var2;
            }
            ++var1;
        }
        var1 = 0;
        while (var1 < 16) {
            var2 = 0;
            while (var2 < 16) {
                float[] var10000 = this.field_1136_i;
                int n = var1 + var2 * 16;
                var10000[n] = var10000[n] + this.field_1135_j[var1 + var2 * 16] * 0.05f;
                if (this.field_1136_i[var1 + var2 * 16] < 0.0f) {
                    this.field_1136_i[var1 + var2 * 16] = 0.0f;
                }
                var10000 = this.field_1135_j;
                int n2 = var1 + var2 * 16;
                var10000[n2] = var10000[n2] - 0.3f;
                if (Math.random() < 0.2) {
                    this.field_1135_j[var1 + var2 * 16] = 0.5f;
                }
                ++var2;
            }
            ++var1;
        }
        float[] var12 = this.field_1137_h;
        this.field_1137_h = this.field_1138_g;
        this.field_1138_g = var12;
        var2 = 0;
        while (var2 < 256) {
            var3 = this.field_1138_g[var2 - this.field_1134_k * 16 & 0xFF];
            if (var3 > 1.0f) {
                var3 = 1.0f;
            }
            if (var3 < 0.0f) {
                var3 = 0.0f;
            }
            float var13 = var3 * var3;
            var5 = (int)(32.0f + var13 * 32.0f);
            var6 = (int)(50.0f + var13 * 64.0f);
            int var7 = 255;
            int var8 = (int)(146.0f + var13 * 50.0f);
            if (this.anaglyphEnabled) {
                int var9 = (var5 * 30 + var6 * 59 + var7 * 11) / 100;
                int var10 = (var5 * 30 + var6 * 70) / 100;
                int var11 = (var5 * 30 + var7 * 70) / 100;
                var5 = var9;
                var6 = var10;
                var7 = var11;
            }
            this.imageData[var2 * 4 + 0] = (byte)var5;
            this.imageData[var2 * 4 + 1] = (byte)var6;
            this.imageData[var2 * 4 + 2] = (byte)var7;
            this.imageData[var2 * 4 + 3] = (byte)var8;
            ++var2;
        }
        this.hasTexture = true;
    }
}

