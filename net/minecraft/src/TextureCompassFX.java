/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import net.minecraft.client.Minecraft;
import net.minecraft.src.ChunkCoordinates;
import net.minecraft.src.Item;
import net.minecraft.src.TextureFX;

public class TextureCompassFX
extends TextureFX {
    private Minecraft mc;
    private int[] field_4230_h = new int[256];
    private double field_4229_i;
    private double field_4228_j;

    public TextureCompassFX(Minecraft var1) {
        super(Item.compass.getIconIndex(null));
        this.mc = var1;
        this.tileImage = 1;
        try {
            BufferedImage var2 = ImageIO.read(Minecraft.class.getResource("/gui/items.png"));
            int var3 = this.iconIndex % 16 * 16;
            int var4 = this.iconIndex / 16 * 16;
            var2.getRGB(var3, var4, 16, 16, this.field_4230_h, 0, 16);
        }
        catch (IOException var5) {
            var5.printStackTrace();
        }
    }

    @Override
    public void onTick() {
        int var19;
        int var18;
        int var17;
        int var16;
        int var15;
        int var14;
        int var13;
        int var12;
        int var11;
        int var10;
        int var1 = 0;
        while (var1 < 256) {
            int var2 = this.field_4230_h[var1] >> 24 & 0xFF;
            int var3 = this.field_4230_h[var1] >> 16 & 0xFF;
            int var4 = this.field_4230_h[var1] >> 8 & 0xFF;
            int var5 = this.field_4230_h[var1] >> 0 & 0xFF;
            if (this.anaglyphEnabled) {
                int var6 = (var3 * 30 + var4 * 59 + var5 * 11) / 100;
                int var7 = (var3 * 30 + var4 * 70) / 100;
                int var8 = (var3 * 30 + var5 * 70) / 100;
                var3 = var6;
                var4 = var7;
                var5 = var8;
            }
            this.imageData[var1 * 4 + 0] = (byte)var3;
            this.imageData[var1 * 4 + 1] = (byte)var4;
            this.imageData[var1 * 4 + 2] = (byte)var5;
            this.imageData[var1 * 4 + 3] = (byte)var2;
            ++var1;
        }
        double var20 = 0.0;
        if (this.mc.theWorld != null && this.mc.thePlayer != null) {
            ChunkCoordinates var21 = this.mc.theWorld.getSpawnPoint();
            double var23 = (double)var21.x - this.mc.thePlayer.posX;
            double var25 = (double)var21.z - this.mc.thePlayer.posZ;
            var20 = (double)(this.mc.thePlayer.rotationYaw - 90.0f) * Math.PI / 180.0 - Math.atan2(var25, var23);
            if (this.mc.theWorld.worldProvider.field_4220_c) {
                var20 = Math.random() * 3.1415927410125732 * 2.0;
            }
        }
        double var22 = var20 - this.field_4229_i;
        while (var22 < -Math.PI) {
            var22 += Math.PI * 2;
        }
        while (var22 >= Math.PI) {
            var22 -= Math.PI * 2;
        }
        if (var22 < -1.0) {
            var22 = -1.0;
        }
        if (var22 > 1.0) {
            var22 = 1.0;
        }
        this.field_4228_j += var22 * 0.1;
        this.field_4228_j *= 0.8;
        this.field_4229_i += this.field_4228_j;
        double var24 = Math.sin(this.field_4229_i);
        double var26 = Math.cos(this.field_4229_i);
        int var9 = -4;
        while (var9 <= 4) {
            var10 = (int)(8.5 + var26 * (double)var9 * 0.3);
            var11 = (int)(7.5 - var24 * (double)var9 * 0.3 * 0.5);
            var12 = var11 * 16 + var10;
            var13 = 100;
            var14 = 100;
            var15 = 100;
            var16 = 255;
            if (this.anaglyphEnabled) {
                var17 = (var13 * 30 + var14 * 59 + var15 * 11) / 100;
                var18 = (var13 * 30 + var14 * 70) / 100;
                var19 = (var13 * 30 + var15 * 70) / 100;
                var13 = var17;
                var14 = var18;
                var15 = var19;
            }
            this.imageData[var12 * 4 + 0] = (byte)var13;
            this.imageData[var12 * 4 + 1] = (byte)var14;
            this.imageData[var12 * 4 + 2] = (byte)var15;
            this.imageData[var12 * 4 + 3] = (byte)var16;
            ++var9;
        }
        var9 = -8;
        while (var9 <= 16) {
            var10 = (int)(8.5 + var24 * (double)var9 * 0.3);
            var11 = (int)(7.5 + var26 * (double)var9 * 0.3 * 0.5);
            var12 = var11 * 16 + var10;
            var13 = var9 >= 0 ? 255 : 100;
            var14 = var9 >= 0 ? 20 : 100;
            var15 = var9 >= 0 ? 20 : 100;
            var16 = 255;
            if (this.anaglyphEnabled) {
                var17 = (var13 * 30 + var14 * 59 + var15 * 11) / 100;
                var18 = (var13 * 30 + var14 * 70) / 100;
                var19 = (var13 * 30 + var15 * 70) / 100;
                var13 = var17;
                var14 = var18;
                var15 = var19;
            }
            this.imageData[var12 * 4 + 0] = (byte)var13;
            this.imageData[var12 * 4 + 1] = (byte)var14;
            this.imageData[var12 * 4 + 2] = (byte)var15;
            this.imageData[var12 * 4 + 3] = (byte)var16;
            ++var9;
        }
    }
}

