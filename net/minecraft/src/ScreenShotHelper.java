/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.BufferUtils
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.src;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.imageio.ImageIO;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

public class ScreenShotHelper {
    private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss");
    private static ByteBuffer buffer;
    private static byte[] pixelData;
    private static int[] imageData;
    private int field_21197_e;
    private DataOutputStream field_21196_f;
    private byte[] field_21195_g;
    private int field_21194_h;
    private int field_21193_i;
    private File field_21192_j;

    public static String saveScreenshot(File var0, int var1, int var2) {
        try {
            File var5;
            File var3 = new File(var0, "screenshots");
            var3.mkdir();
            if (buffer == null || buffer.capacity() < var1 * var2) {
                buffer = BufferUtils.createByteBuffer((int)(var1 * var2 * 3));
            }
            if (imageData == null || imageData.length < var1 * var2 * 3) {
                pixelData = new byte[var1 * var2 * 3];
                imageData = new int[var1 * var2];
            }
            GL11.glPixelStorei((int)3333, (int)1);
            GL11.glPixelStorei((int)3317, (int)1);
            buffer.clear();
            GL11.glReadPixels((int)0, (int)0, (int)var1, (int)var2, (int)6407, (int)5121, (ByteBuffer)buffer);
            buffer.clear();
            String var4 = dateFormat.format(new Date());
            int var6 = 1;
            while ((var5 = new File(var3, String.valueOf(var4) + (var6 == 1 ? "" : "_" + var6) + ".png")).exists()) {
                ++var6;
            }
            buffer.get(pixelData);
            int var7 = 0;
            while (var7 < var1) {
                int var8 = 0;
                while (var8 < var2) {
                    int var13;
                    int var9 = var7 + (var2 - var8 - 1) * var1;
                    int var10 = pixelData[var9 * 3 + 0] & 0xFF;
                    int var11 = pixelData[var9 * 3 + 1] & 0xFF;
                    int var12 = pixelData[var9 * 3 + 2] & 0xFF;
                    ScreenShotHelper.imageData[var7 + var8 * var1] = var13 = 0xFF000000 | var10 << 16 | var11 << 8 | var12;
                    ++var8;
                }
                ++var7;
            }
            BufferedImage var15 = new BufferedImage(var1, var2, 1);
            var15.setRGB(0, 0, var1, var2, imageData, 0, var1);
            ImageIO.write((RenderedImage)var15, "png", var5);
            return "Saved screenshot as " + var5.getName();
        }
        catch (Exception var14) {
            var14.printStackTrace();
            return "Failed to save: " + var14;
        }
    }

    public ScreenShotHelper(File var1, int var2, int var3, int var4) throws IOException {
        this.field_21194_h = var2;
        this.field_21193_i = var3;
        this.field_21197_e = var4;
        File var5 = new File(var1, "screenshots");
        var5.mkdir();
        String var6 = "huge_" + dateFormat.format(new Date());
        int var7 = 1;
        while ((this.field_21192_j = new File(var5, String.valueOf(var6) + (var7 == 1 ? "" : "_" + var7) + ".tga")).exists()) {
            ++var7;
        }
        byte[] var8 = new byte[18];
        var8[2] = 2;
        var8[12] = (byte)(var2 % 256);
        var8[13] = (byte)(var2 / 256);
        var8[14] = (byte)(var3 % 256);
        var8[15] = (byte)(var3 / 256);
        var8[16] = 24;
        this.field_21195_g = new byte[var2 * var4 * 3];
        this.field_21196_f = new DataOutputStream(new FileOutputStream(this.field_21192_j));
        this.field_21196_f.write(var8);
    }

    public void func_21189_a(ByteBuffer var1, int var2, int var3, int var4, int var5) throws IOException {
        int var6 = var4;
        int var7 = var5;
        if (var4 > this.field_21194_h - var2) {
            var6 = this.field_21194_h - var2;
        }
        if (var5 > this.field_21193_i - var3) {
            var7 = this.field_21193_i - var3;
        }
        this.field_21197_e = var7;
        int var8 = 0;
        while (var8 < var7) {
            var1.position((var5 - var7) * var4 * 3 + var8 * var4 * 3);
            int var9 = (var2 + var8 * this.field_21194_h) * 3;
            var1.get(this.field_21195_g, var9, var6 * 3);
            ++var8;
        }
    }

    public void func_21191_a() throws IOException {
        this.field_21196_f.write(this.field_21195_g, 0, this.field_21194_h * 3 * this.field_21197_e);
    }

    public String func_21190_b() throws IOException {
        this.field_21196_f.close();
        return "Saved screenshot as " + this.field_21192_j.getName();
    }
}

