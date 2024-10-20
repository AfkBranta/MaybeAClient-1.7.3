package net.minecraft.src;

import net.minecraft.src.Block;
import net.minecraft.src.TextureFX;

public class TextureFlamesFX
extends TextureFX {
    protected float[] field_1133_g = new float[320];
    protected float[] field_1132_h = new float[320];

    public TextureFlamesFX(int var1) {
        super(Block.fire.blockIndexInTexture + var1 * 16);
    }

    @Override
    public void onTick() {
        int var6;
        int var5;
        float var4;
        int var2;
        int var1 = 0;
        while (var1 < 16) {
            var2 = 0;
            while (var2 < 20) {
                int var3 = 18;
                var4 = this.field_1133_g[var1 + (var2 + 1) % 20 * 16] * (float)var3;
                var5 = var1 - 1;
                while (var5 <= var1 + 1) {
                    var6 = var2;
                    while (var6 <= var2 + 1) {
                        if (var5 >= 0 && var6 >= 0 && var5 < 16 && var6 < 20) {
                            var4 += this.field_1133_g[var5 + var6 * 16];
                        }
                        ++var3;
                        ++var6;
                    }
                    ++var5;
                }
                this.field_1132_h[var1 + var2 * 16] = var4 / ((float)var3 * 1.06f);
                if (var2 >= 19) {
                    this.field_1132_h[var1 + var2 * 16] = (float)(Math.random() * Math.random() * Math.random() * 4.0 + Math.random() * (double)0.1f + (double)0.2f);
                }
                ++var2;
            }
            ++var1;
        }
        float[] var12 = this.field_1132_h;
        this.field_1132_h = this.field_1133_g;
        this.field_1133_g = var12;
        var2 = 0;
        while (var2 < 256) {
            float var13 = this.field_1133_g[var2] * 1.8f;
            if (var13 > 1.0f) {
                var13 = 1.0f;
            }
            if (var13 < 0.0f) {
                var13 = 0.0f;
            }
            var5 = (int)(var13 * 155.0f + 100.0f);
            var6 = (int)(var13 * var13 * 255.0f);
            int var7 = (int)(var13 * var13 * var13 * var13 * var13 * var13 * var13 * var13 * var13 * var13 * 255.0f);
            int var8 = 255;
            if (var13 < 0.5f) {
                var8 = 0;
            }
            var4 = (var13 - 0.5f) * 2.0f;
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
    }
}

