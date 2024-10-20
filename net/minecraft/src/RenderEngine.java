/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.src;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import net.minecraft.src.GLAllocation;
import net.minecraft.src.GameSettings;
import net.minecraft.src.ImageBuffer;
import net.minecraft.src.TextureFX;
import net.minecraft.src.TexturePackBase;
import net.minecraft.src.TexturePackList;
import net.minecraft.src.ThreadDownloadImageData;
import org.lwjgl.opengl.GL11;

public class RenderEngine {
    public static boolean useMipmaps = false;
    public HashMap textureMap = new HashMap();
    private HashMap textureNameToImageMap = new HashMap();
    public IntBuffer singleIntBuffer = GLAllocation.createDirectIntBuffer(1);
    private ByteBuffer imageData = GLAllocation.createDirectByteBuffer(0x100000);
    private List textureList = new ArrayList();
    private Map urlToImageDataMap = new HashMap();
    private GameSettings options;
    private boolean clampTexture = false;
    private boolean blurTexture = false;
    private TexturePackList field_6527_k;
    private BufferedImage field_25189_l = new BufferedImage(64, 64, 2);

    public RenderEngine(TexturePackList var1, GameSettings var2) {
        this.field_6527_k = var1;
        this.options = var2;
        Graphics var3 = this.field_25189_l.getGraphics();
        var3.setColor(Color.WHITE);
        var3.fillRect(0, 0, 64, 64);
        var3.setColor(Color.BLACK);
        var3.drawString("missingtex", 1, 10);
        var3.dispose();
    }

    public int putTexture(String key, BufferedImage value) {
        Integer var3 = (Integer)this.textureMap.get(key);
        if (var3 != null) {
            return var3;
        }
        this.singleIntBuffer.clear();
        GLAllocation.generateTextureNames(this.singleIntBuffer);
        int var6 = this.singleIntBuffer.get(0);
        if (key.startsWith("##")) {
            this.setupTexture(value, var6);
        } else if (key.startsWith("%clamp%")) {
            this.clampTexture = true;
            this.setupTexture(value, var6);
            this.clampTexture = false;
        } else if (key.startsWith("%blur%")) {
            this.blurTexture = true;
            this.setupTexture(value, var6);
            this.blurTexture = false;
        } else {
            this.setupTexture(value, var6);
        }
        this.textureMap.put(key, var6);
        return var6;
    }

    public int getTexture(String var1) {
        TexturePackBase var2 = this.field_6527_k.selectedTexturePack;
        Integer var3 = (Integer)this.textureMap.get(var1);
        if (var3 != null) {
            return var3;
        }
        try {
            this.singleIntBuffer.clear();
            GLAllocation.generateTextureNames(this.singleIntBuffer);
            int var6 = this.singleIntBuffer.get(0);
            if (var1.startsWith("##")) {
                this.setupTexture(this.unwrapImageByColumns(this.readTextureImage(var2.func_6481_a(var1.substring(2)))), var6);
            } else if (var1.startsWith("%clamp%")) {
                this.clampTexture = true;
                this.setupTexture(this.readTextureImage(var2.func_6481_a(var1.substring(7))), var6);
                this.clampTexture = false;
            } else if (var1.startsWith("%blur%")) {
                this.blurTexture = true;
                this.setupTexture(this.readTextureImage(var2.func_6481_a(var1.substring(6))), var6);
                this.blurTexture = false;
            } else {
                InputStream var4 = var2.func_6481_a(var1);
                if (var4 == null) {
                    this.setupTexture(this.field_25189_l, var6);
                } else {
                    this.setupTexture(this.readTextureImage(var4), var6);
                }
            }
            this.textureMap.put(var1, var6);
            return var6;
        }
        catch (IOException var5) {
            throw new RuntimeException("!!");
        }
    }

    private BufferedImage unwrapImageByColumns(BufferedImage var1) {
        int var2 = var1.getWidth() / 16;
        BufferedImage var3 = new BufferedImage(16, var1.getHeight() * var2, 2);
        Graphics var4 = var3.getGraphics();
        int var5 = 0;
        while (var5 < var2) {
            var4.drawImage(var1, -var5 * 16, var5 * var1.getHeight(), null);
            ++var5;
        }
        var4.dispose();
        return var3;
    }

    public int allocateAndSetupTexture(BufferedImage var1) {
        this.singleIntBuffer.clear();
        GLAllocation.generateTextureNames(this.singleIntBuffer);
        int var2 = this.singleIntBuffer.get(0);
        this.setupTexture(var1, var2);
        this.textureNameToImageMap.put(var2, var1);
        return var2;
    }

    public void setupTexture(BufferedImage var1, int var2) {
        int var14;
        int var13;
        int var12;
        int var11;
        int var10;
        int var9;
        int var8;
        GL11.glBindTexture((int)3553, (int)var2);
        if (useMipmaps) {
            GL11.glTexParameteri((int)3553, (int)10241, (int)9987);
            GL11.glTexParameteri((int)3553, (int)10240, (int)9729);
        } else {
            GL11.glTexParameteri((int)3553, (int)10241, (int)9728);
            GL11.glTexParameteri((int)3553, (int)10240, (int)9728);
        }
        if (this.blurTexture) {
            GL11.glTexParameteri((int)3553, (int)10241, (int)9729);
            GL11.glTexParameteri((int)3553, (int)10240, (int)9729);
        }
        if (this.clampTexture) {
            GL11.glTexParameteri((int)3553, (int)10242, (int)10496);
            GL11.glTexParameteri((int)3553, (int)10243, (int)10496);
        } else {
            GL11.glTexParameteri((int)3553, (int)10242, (int)10497);
            GL11.glTexParameteri((int)3553, (int)10243, (int)10497);
        }
        int var3 = var1.getWidth();
        int var4 = var1.getHeight();
        int[] var5 = new int[var3 * var4];
        byte[] var6 = new byte[var3 * var4 * 4];
        var1.getRGB(0, 0, var3, var4, var5, 0, var3);
        int var7 = 0;
        while (var7 < var5.length) {
            var8 = var5[var7] >> 24 & 0xFF;
            var9 = var5[var7] >> 16 & 0xFF;
            var10 = var5[var7] >> 8 & 0xFF;
            var11 = var5[var7] & 0xFF;
            if (this.options != null && this.options.anaglyph) {
                var12 = (var9 * 30 + var10 * 59 + var11 * 11) / 100;
                var13 = (var9 * 30 + var10 * 70) / 100;
                var14 = (var9 * 30 + var11 * 70) / 100;
                var9 = var12;
                var10 = var13;
                var11 = var14;
            }
            var6[var7 * 4 + 0] = (byte)var9;
            var6[var7 * 4 + 1] = (byte)var10;
            var6[var7 * 4 + 2] = (byte)var11;
            var6[var7 * 4 + 3] = (byte)var8;
            ++var7;
        }
        this.imageData.clear();
        this.imageData.put(var6);
        this.imageData.position(0).limit(var6.length);
        GL11.glTexImage2D((int)3553, (int)0, (int)6408, (int)var3, (int)var4, (int)0, (int)6408, (int)5121, (ByteBuffer)this.imageData);
        if (useMipmaps) {
            var7 = 1;
            while (var7 <= 4) {
                var8 = var3 >> var7 - 1;
                var9 = var3 >> var7;
                var10 = var4 >> var7;
                var11 = 0;
                while (var11 < var9) {
                    var12 = 0;
                    while (var12 < var10) {
                        var13 = this.imageData.getInt((var11 * 2 + 0 + (var12 * 2 + 0) * var8) * 4);
                        var14 = this.imageData.getInt((var11 * 2 + 1 + (var12 * 2 + 0) * var8) * 4);
                        int var15 = this.imageData.getInt((var11 * 2 + 1 + (var12 * 2 + 1) * var8) * 4);
                        int var16 = this.imageData.getInt((var11 * 2 + 0 + (var12 * 2 + 1) * var8) * 4);
                        int var17 = this.weightedAverageColor(this.weightedAverageColor(var13, var14), this.weightedAverageColor(var15, var16));
                        this.imageData.putInt((var11 + var12 * var9) * 4, var17);
                        ++var12;
                    }
                    ++var11;
                }
                GL11.glTexImage2D((int)3553, (int)var7, (int)6408, (int)var9, (int)var10, (int)0, (int)6408, (int)5121, (ByteBuffer)this.imageData);
                ++var7;
            }
        }
    }

    public void deleteTexture(int var1) {
        this.textureNameToImageMap.remove(var1);
        this.singleIntBuffer.clear();
        this.singleIntBuffer.put(var1);
        this.singleIntBuffer.flip();
        GL11.glDeleteTextures((IntBuffer)this.singleIntBuffer);
    }

    public int getTextureForDownloadableImage(String var1, String var2) {
        ThreadDownloadImageData var3 = (ThreadDownloadImageData)this.urlToImageDataMap.get(var1);
        if (var3 != null && var3.image != null && !var3.textureSetupComplete) {
            if (var3.textureName < 0) {
                var3.textureName = this.allocateAndSetupTexture(var3.image);
            } else {
                this.setupTexture(var3.image, var3.textureName);
            }
            var3.textureSetupComplete = true;
        }
        if (var3 != null && var3.textureName >= 0) {
            return var3.textureName;
        }
        return var2 == null ? -1 : this.getTexture(var2);
    }

    public ThreadDownloadImageData obtainImageData(String var1, ImageBuffer var2) {
        ThreadDownloadImageData var3 = (ThreadDownloadImageData)this.urlToImageDataMap.get(var1);
        if (var3 == null) {
            this.urlToImageDataMap.put(var1, new ThreadDownloadImageData(var1, var2));
        } else {
            ++var3.referenceCount;
        }
        return var3;
    }

    public void releaseImageData(String var1) {
        ThreadDownloadImageData var2 = (ThreadDownloadImageData)this.urlToImageDataMap.get(var1);
        if (var2 != null) {
            --var2.referenceCount;
            if (var2.referenceCount == 0) {
                if (var2.textureName >= 0) {
                    this.deleteTexture(var2.textureName);
                }
                this.urlToImageDataMap.remove(var1);
            }
        }
    }

    public void registerTextureFX(TextureFX var1) {
        this.textureList.add(var1);
        var1.onTick();
    }

    public void func_1067_a() {
        int var12;
        int var11;
        int var10;
        int var9;
        int var8;
        int var7;
        int var6;
        int var5;
        int var4;
        int var3;
        TextureFX var2;
        int var1 = 0;
        while (var1 < this.textureList.size()) {
            var2 = (TextureFX)this.textureList.get(var1);
            var2.anaglyphEnabled = this.options.anaglyph;
            var2.onTick();
            this.imageData.clear();
            this.imageData.put(var2.imageData);
            this.imageData.position(0).limit(var2.imageData.length);
            var2.bindImage(this);
            var3 = 0;
            while (var3 < var2.tileSize) {
                var4 = 0;
                while (var4 < var2.tileSize) {
                    GL11.glTexSubImage2D((int)3553, (int)0, (int)(var2.iconIndex % 16 * 16 + var3 * 16), (int)(var2.iconIndex / 16 * 16 + var4 * 16), (int)16, (int)16, (int)6408, (int)5121, (ByteBuffer)this.imageData);
                    if (useMipmaps) {
                        var5 = 1;
                        while (var5 <= 4) {
                            var6 = 16 >> var5 - 1;
                            var7 = 16 >> var5;
                            var8 = 0;
                            while (var8 < var7) {
                                var9 = 0;
                                while (var9 < var7) {
                                    var10 = this.imageData.getInt((var8 * 2 + 0 + (var9 * 2 + 0) * var6) * 4);
                                    var11 = this.imageData.getInt((var8 * 2 + 1 + (var9 * 2 + 0) * var6) * 4);
                                    var12 = this.imageData.getInt((var8 * 2 + 1 + (var9 * 2 + 1) * var6) * 4);
                                    int var13 = this.imageData.getInt((var8 * 2 + 0 + (var9 * 2 + 1) * var6) * 4);
                                    int var14 = this.averageColor(this.averageColor(var10, var11), this.averageColor(var12, var13));
                                    this.imageData.putInt((var8 + var9 * var7) * 4, var14);
                                    ++var9;
                                }
                                ++var8;
                            }
                            GL11.glTexSubImage2D((int)3553, (int)var5, (int)(var2.iconIndex % 16 * var7), (int)(var2.iconIndex / 16 * var7), (int)var7, (int)var7, (int)6408, (int)5121, (ByteBuffer)this.imageData);
                            ++var5;
                        }
                    }
                    ++var4;
                }
                ++var3;
            }
            ++var1;
        }
        var1 = 0;
        while (var1 < this.textureList.size()) {
            var2 = (TextureFX)this.textureList.get(var1);
            if (var2.field_1130_d > 0) {
                this.imageData.clear();
                this.imageData.put(var2.imageData);
                this.imageData.position(0).limit(var2.imageData.length);
                GL11.glBindTexture((int)3553, (int)var2.field_1130_d);
                GL11.glTexSubImage2D((int)3553, (int)0, (int)0, (int)0, (int)16, (int)16, (int)6408, (int)5121, (ByteBuffer)this.imageData);
                if (useMipmaps) {
                    var3 = 1;
                    while (var3 <= 4) {
                        var4 = 16 >> var3 - 1;
                        var5 = 16 >> var3;
                        var6 = 0;
                        while (var6 < var5) {
                            var7 = 0;
                            while (var7 < var5) {
                                var8 = this.imageData.getInt((var6 * 2 + 0 + (var7 * 2 + 0) * var4) * 4);
                                var9 = this.imageData.getInt((var6 * 2 + 1 + (var7 * 2 + 0) * var4) * 4);
                                var10 = this.imageData.getInt((var6 * 2 + 1 + (var7 * 2 + 1) * var4) * 4);
                                var11 = this.imageData.getInt((var6 * 2 + 0 + (var7 * 2 + 1) * var4) * 4);
                                var12 = this.averageColor(this.averageColor(var8, var9), this.averageColor(var10, var11));
                                this.imageData.putInt((var6 + var7 * var5) * 4, var12);
                                ++var7;
                            }
                            ++var6;
                        }
                        GL11.glTexSubImage2D((int)3553, (int)var3, (int)0, (int)0, (int)var5, (int)var5, (int)6408, (int)5121, (ByteBuffer)this.imageData);
                        ++var3;
                    }
                }
            }
            ++var1;
        }
    }

    private int averageColor(int var1, int var2) {
        int var3 = (var1 & 0xFF000000) >> 24 & 0xFF;
        int var4 = (var2 & 0xFF000000) >> 24 & 0xFF;
        return (var3 + var4 >> 1 << 24) + ((var1 & 0xFEFEFE) + (var2 & 0xFEFEFE) >> 1);
    }

    private int weightedAverageColor(int var1, int var2) {
        int var3 = (var1 & 0xFF000000) >> 24 & 0xFF;
        int var4 = (var2 & 0xFF000000) >> 24 & 0xFF;
        int var5 = 255;
        if (var3 + var4 == 0) {
            var3 = 1;
            var4 = 1;
            var5 = 0;
        }
        int var6 = (var1 >> 16 & 0xFF) * var3;
        int var7 = (var1 >> 8 & 0xFF) * var3;
        int var8 = (var1 & 0xFF) * var3;
        int var9 = (var2 >> 16 & 0xFF) * var4;
        int var10 = (var2 >> 8 & 0xFF) * var4;
        int var11 = (var2 & 0xFF) * var4;
        int var12 = (var6 + var9) / (var3 + var4);
        int var13 = (var7 + var10) / (var3 + var4);
        int var14 = (var8 + var11) / (var3 + var4);
        return var5 << 24 | var12 << 16 | var13 << 8 | var14;
    }

    public void refreshTextures() {
        BufferedImage var4;
        TexturePackBase var1 = this.field_6527_k.selectedTexturePack;
        Iterator<Object> var2 = this.textureNameToImageMap.keySet().iterator();
        while (var2.hasNext()) {
            int var3 = (Integer)var2.next();
            var4 = (BufferedImage)this.textureNameToImageMap.get(var3);
            this.setupTexture(var4, var3);
        }
        for (ThreadDownloadImageData var7 : this.urlToImageDataMap.values()) {
            var7.textureSetupComplete = false;
        }
        for (String var8 : this.textureMap.keySet()) {
            try {
                if (var8.startsWith("##")) {
                    var4 = this.unwrapImageByColumns(this.readTextureImage(var1.func_6481_a(var8.substring(2))));
                } else if (var8.startsWith("%clamp%")) {
                    this.clampTexture = true;
                    var4 = this.readTextureImage(var1.func_6481_a(var8.substring(7)));
                } else if (var8.startsWith("%blur%")) {
                    this.blurTexture = true;
                    var4 = this.readTextureImage(var1.func_6481_a(var8.substring(6)));
                } else {
                    var4 = this.readTextureImage(var1.func_6481_a(var8));
                }
                int var5 = (Integer)this.textureMap.get(var8);
                this.setupTexture(var4, var5);
                this.blurTexture = false;
                this.clampTexture = false;
            }
            catch (IOException var6) {
                var6.printStackTrace();
            }
        }
    }

    private BufferedImage readTextureImage(InputStream var1) throws IOException {
        BufferedImage var2 = ImageIO.read(var1);
        var1.close();
        return var2;
    }

    public void bindTexture(int var1) {
        if (var1 >= 0) {
            GL11.glBindTexture((int)3553, (int)var1);
        }
    }
}

