/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import net.minecraft.client.Minecraft;
import net.minecraft.src.Item;
import net.minecraft.src.TextureFX;

public class TextureWatchFX
extends TextureFX {
    private Minecraft field_4225_g;
    private int[] field_4224_h = new int[256];
    private int[] field_4223_i = new int[256];
    private double field_4222_j;
    private double field_4221_k;

    public TextureWatchFX(Minecraft var1) {
        super(Item.pocketSundial.getIconIndex(null));
        this.field_4225_g = var1;
        this.tileImage = 1;
        try {
            BufferedImage var2 = ImageIO.read(Minecraft.class.getResource("/gui/items.png"));
            int var3 = this.iconIndex % 16 * 16;
            int var4 = this.iconIndex / 16 * 16;
            var2.getRGB(var3, var4, 16, 16, this.field_4224_h, 0, 16);
            var2 = ImageIO.read(Minecraft.class.getResource("/misc/dial.png"));
            var2.getRGB(0, 0, 16, 16, this.field_4223_i, 0, 16);
        }
        catch (IOException var5) {
            var5.printStackTrace();
        }
    }

    @Override
    public void onTick() {
        double var1 = 0.0;
        if (this.field_4225_g.theWorld != null && this.field_4225_g.thePlayer != null) {
            float var3 = this.field_4225_g.theWorld.getCelestialAngle(1.0f);
            var1 = -var3 * (float)Math.PI * 2.0f;
            if (this.field_4225_g.theWorld.worldProvider.field_4220_c) {
                var1 = Math.random() * 3.1415927410125732 * 2.0;
            }
        }
        double var22 = var1 - this.field_4222_j;
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
        this.field_4221_k += var22 * 0.1;
        this.field_4221_k *= 0.8;
        this.field_4222_j += this.field_4221_k;
        double var5 = Math.sin(this.field_4222_j);
        double var7 = Math.cos(this.field_4222_j);
        int var9 = 0;
        while (var9 < 256) {
            int var10 = this.field_4224_h[var9] >> 24 & 0xFF;
            int var11 = this.field_4224_h[var9] >> 16 & 0xFF;
            int var12 = this.field_4224_h[var9] >> 8 & 0xFF;
            int var13 = this.field_4224_h[var9] >> 0 & 0xFF;
            if (var11 == var13 && var12 == 0 && var13 > 0) {
                double var14 = -((double)(var9 % 16) / 15.0 - 0.5);
                double var16 = (double)(var9 / 16) / 15.0 - 0.5;
                int var18 = var11;
                int var19 = (int)((var14 * var7 + var16 * var5 + 0.5) * 16.0);
                int var20 = (int)((var16 * var7 - var14 * var5 + 0.5) * 16.0);
                int var21 = (var19 & 0xF) + (var20 & 0xF) * 16;
                var10 = this.field_4223_i[var21] >> 24 & 0xFF;
                var11 = (this.field_4223_i[var21] >> 16 & 0xFF) * var11 / 255;
                var12 = (this.field_4223_i[var21] >> 8 & 0xFF) * var18 / 255;
                var13 = (this.field_4223_i[var21] >> 0 & 0xFF) * var18 / 255;
            }
            if (this.anaglyphEnabled) {
                int var23 = (var11 * 30 + var12 * 59 + var13 * 11) / 100;
                int var15 = (var11 * 30 + var12 * 70) / 100;
                int var24 = (var11 * 30 + var13 * 70) / 100;
                var11 = var23;
                var12 = var15;
                var13 = var24;
            }
            this.imageData[var9 * 4 + 0] = (byte)var11;
            this.imageData[var9 * 4 + 1] = (byte)var12;
            this.imageData[var9 * 4 + 2] = (byte)var13;
            this.imageData[var9 * 4 + 3] = (byte)var10;
            ++var9;
        }
    }
}

