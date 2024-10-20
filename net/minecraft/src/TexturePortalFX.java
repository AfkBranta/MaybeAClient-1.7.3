/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.Random;
import net.minecraft.src.Block;
import net.minecraft.src.MathHelper;
import net.minecraft.src.TextureFX;

public class TexturePortalFX
extends TextureFX {
    private int field_4227_g = 0;
    private byte[][] field_4226_h = new byte[32][1024];

    public TexturePortalFX() {
        super(Block.portal.blockIndexInTexture);
        Random var1 = new Random(100L);
        int var2 = 0;
        while (var2 < 32) {
            int var3 = 0;
            while (var3 < 16) {
                int var4 = 0;
                while (var4 < 16) {
                    float var5 = 0.0f;
                    int var6 = 0;
                    while (var6 < 2) {
                        float var7 = var6 * 8;
                        float var8 = var6 * 8;
                        float var9 = ((float)var3 - var7) / 16.0f * 2.0f;
                        float var10 = ((float)var4 - var8) / 16.0f * 2.0f;
                        if (var9 < -1.0f) {
                            var9 += 2.0f;
                        }
                        if (var9 >= 1.0f) {
                            var9 -= 2.0f;
                        }
                        if (var10 < -1.0f) {
                            var10 += 2.0f;
                        }
                        if (var10 >= 1.0f) {
                            var10 -= 2.0f;
                        }
                        float var11 = var9 * var9 + var10 * var10;
                        float var12 = (float)Math.atan2(var10, var9) + ((float)var2 / 32.0f * (float)Math.PI * 2.0f - var11 * 10.0f + (float)(var6 * 2)) * (float)(var6 * 2 - 1);
                        var12 = (MathHelper.sin(var12) + 1.0f) / 2.0f;
                        var5 += (var12 /= var11 + 1.0f) * 0.5f;
                        ++var6;
                    }
                    var6 = (int)((var5 += var1.nextFloat() * 0.1f) * 100.0f + 155.0f);
                    int var13 = (int)(var5 * var5 * 200.0f + 55.0f);
                    int var14 = (int)(var5 * var5 * var5 * var5 * 255.0f);
                    int var15 = (int)(var5 * 100.0f + 155.0f);
                    int var16 = var4 * 16 + var3;
                    this.field_4226_h[var2][var16 * 4 + 0] = (byte)var13;
                    this.field_4226_h[var2][var16 * 4 + 1] = (byte)var14;
                    this.field_4226_h[var2][var16 * 4 + 2] = (byte)var6;
                    this.field_4226_h[var2][var16 * 4 + 3] = (byte)var15;
                    ++var4;
                }
                ++var3;
            }
            ++var2;
        }
    }

    @Override
    public void onTick() {
        ++this.field_4227_g;
        byte[] var1 = this.field_4226_h[this.field_4227_g & 0x1F];
        int var2 = 0;
        while (var2 < 256) {
            int var3 = var1[var2 * 4 + 0] & 0xFF;
            int var4 = var1[var2 * 4 + 1] & 0xFF;
            int var5 = var1[var2 * 4 + 2] & 0xFF;
            int var6 = var1[var2 * 4 + 3] & 0xFF;
            if (this.anaglyphEnabled) {
                int var7 = (var3 * 30 + var4 * 59 + var5 * 11) / 100;
                int var8 = (var3 * 30 + var4 * 70) / 100;
                int var9 = (var3 * 30 + var5 * 70) / 100;
                var3 = var7;
                var4 = var8;
                var5 = var9;
            }
            this.imageData[var2 * 4 + 0] = (byte)var3;
            this.imageData[var2 * 4 + 1] = (byte)var4;
            this.imageData[var2 * 4 + 2] = (byte)var5;
            this.imageData[var2 * 4 + 3] = (byte)var6;
            ++var2;
        }
    }
}

