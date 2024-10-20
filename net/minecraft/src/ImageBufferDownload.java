/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import net.minecraft.src.ImageBuffer;

public class ImageBufferDownload
implements ImageBuffer {
    private int[] imageData;
    private int imageWidth;
    private int imageHeight;

    @Override
    public BufferedImage parseUserSkin(BufferedImage var1) {
        int var7;
        int var6;
        if (var1 == null) {
            return null;
        }
        this.imageWidth = 64;
        this.imageHeight = 32;
        BufferedImage var2 = new BufferedImage(this.imageWidth, this.imageHeight, 2);
        Graphics var3 = var2.getGraphics();
        var3.drawImage(var1, 0, 0, null);
        var3.dispose();
        this.imageData = ((DataBufferInt)var2.getRaster().getDataBuffer()).getData();
        this.func_884_b(0, 0, 32, 16);
        this.func_885_a(32, 0, 64, 32);
        this.func_884_b(0, 16, 64, 32);
        boolean var4 = false;
        int var5 = 32;
        while (var5 < 64) {
            var6 = 0;
            while (var6 < 16) {
                var7 = this.imageData[var5 + var6 * 64];
                if ((var7 >> 24 & 0xFF) < 128) {
                    var4 = true;
                }
                ++var6;
            }
            ++var5;
        }
        if (!var4) {
            var5 = 32;
            while (var5 < 64) {
                var6 = 0;
                while (var6 < 16) {
                    var7 = this.imageData[var5 + var6 * 64];
                    if ((var7 >> 24 & 0xFF) < 128) {
                        var4 = true;
                    }
                    ++var6;
                }
                ++var5;
            }
        }
        return var2;
    }

    private void func_885_a(int var1, int var2, int var3, int var4) {
        if (!this.func_886_c(var1, var2, var3, var4)) {
            int var5 = var1;
            while (var5 < var3) {
                int var6 = var2;
                while (var6 < var4) {
                    int var10001;
                    int[] var10000 = this.imageData;
                    int n = var10001 = var5 + var6 * this.imageWidth;
                    var10000[n] = var10000[n] & 0xFFFFFF;
                    ++var6;
                }
                ++var5;
            }
        }
    }

    private void func_884_b(int var1, int var2, int var3, int var4) {
        int var5 = var1;
        while (var5 < var3) {
            int var6 = var2;
            while (var6 < var4) {
                int var10001;
                int[] var10000 = this.imageData;
                int n = var10001 = var5 + var6 * this.imageWidth;
                var10000[n] = var10000[n] | 0xFF000000;
                ++var6;
            }
            ++var5;
        }
    }

    private boolean func_886_c(int var1, int var2, int var3, int var4) {
        int var5 = var1;
        while (var5 < var3) {
            int var6 = var2;
            while (var6 < var4) {
                int var7 = this.imageData[var5 + var6 * this.imageWidth];
                if ((var7 >> 24 & 0xFF) < 128) {
                    return true;
                }
                ++var6;
            }
            ++var5;
        }
        return false;
    }
}

