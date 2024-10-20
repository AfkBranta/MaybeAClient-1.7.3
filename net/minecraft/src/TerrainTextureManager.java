package net.minecraft.src;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import javax.imageio.ImageIO;
import net.minecraft.src.Block;
import net.minecraft.src.Chunk;
import net.minecraft.src.IsoImageBuffer;
import net.minecraft.src.Material;
import net.minecraft.src.World;

public class TerrainTextureManager {
    private float[] field_1181_a = new float[768];
    private int[] field_1180_b = new int[5120];
    private int[] field_1186_c = new int[5120];
    private int[] field_1185_d = new int[5120];
    private int[] field_1184_e = new int[5120];
    private int[] field_1183_f = new int[34];
    private int[] field_1182_g = new int[768];

    public TerrainTextureManager() {
        try {
            BufferedImage var1 = ImageIO.read(TerrainTextureManager.class.getResource("/terrain.png"));
            int[] var2 = new int[65536];
            var1.getRGB(0, 0, 256, 256, var2, 0, 256);
            int var3 = 0;
            while (var3 < 256) {
                int var4 = 0;
                int var5 = 0;
                int var6 = 0;
                int var7 = var3 % 16 * 16;
                int var8 = var3 / 16 * 16;
                int var9 = 0;
                int var10 = 0;
                while (var10 < 16) {
                    int var11 = 0;
                    while (var11 < 16) {
                        int var12 = var2[var11 + var7 + (var10 + var8) * 256];
                        int var13 = var12 >> 24 & 0xFF;
                        if (var13 > 128) {
                            var4 += var12 >> 16 & 0xFF;
                            var5 += var12 >> 8 & 0xFF;
                            var6 += var12 & 0xFF;
                            ++var9;
                        }
                        ++var11;
                    }
                    if (var9 == 0) {
                        ++var9;
                    }
                    this.field_1181_a[var3 * 3 + 0] = var4 / var9;
                    this.field_1181_a[var3 * 3 + 1] = var5 / var9;
                    this.field_1181_a[var3 * 3 + 2] = var6 / var9;
                    ++var10;
                }
                ++var3;
            }
        }
        catch (IOException var14) {
            var14.printStackTrace();
        }
        int var15 = 0;
        while (var15 < 256) {
            if (Block.blocksList[var15] != null) {
                this.field_1182_g[var15 * 3 + 0] = Block.blocksList[var15].getBlockTextureFromSide(1);
                this.field_1182_g[var15 * 3 + 1] = Block.blocksList[var15].getBlockTextureFromSide(2);
                this.field_1182_g[var15 * 3 + 2] = Block.blocksList[var15].getBlockTextureFromSide(3);
            }
            ++var15;
        }
    }

    public void func_799_a(IsoImageBuffer var1) {
        World var2 = var1.worldObj;
        if (var2 == null) {
            var1.field_1351_f = true;
            var1.field_1352_e = true;
        } else {
            int var3 = var1.field_1354_c * 16;
            int var4 = var1.field_1353_d * 16;
            int var5 = var3 + 16;
            int var6 = var4 + 16;
            Chunk var7 = var2.getChunkFromChunkCoords(var1.field_1354_c, var1.field_1353_d);
            if (var7.func_21167_h()) {
                var1.field_1351_f = true;
                var1.field_1352_e = true;
            } else {
                var1.field_1351_f = false;
                Arrays.fill(this.field_1186_c, 0);
                Arrays.fill(this.field_1185_d, 0);
                Arrays.fill(this.field_1183_f, 160);
                int var8 = var6 - 1;
                while (var8 >= var4) {
                    int var9 = var5 - 1;
                    while (var9 >= var3) {
                        int var10 = var9 - var3;
                        int var11 = var8 - var4;
                        int var12 = var10 + var11;
                        boolean var13 = true;
                        int var14 = 0;
                        while (var14 < 128) {
                            int var15 = var11 - var10 - var14 + 160 - 16;
                            if (var15 < this.field_1183_f[var12] || var15 < this.field_1183_f[var12 + 1]) {
                                Block var16 = Block.blocksList[var2.getBlockId(var9, var14, var8)];
                                if (var16 == null) {
                                    var13 = false;
                                } else if (var16.blockMaterial == Material.water) {
                                    int var24 = var2.getBlockId(var9, var14 + 1, var8);
                                    if (var24 == 0 || Block.blocksList[var24].blockMaterial != Material.water) {
                                        float var25 = (float)var14 / 127.0f * 0.6f + 0.4f;
                                        float var26 = var2.getLightBrightness(var9, var14 + 1, var8) * var25;
                                        if (var15 >= 0 && var15 < 160) {
                                            int var27 = var12 + var15 * 32;
                                            if (var12 >= 0 && var12 <= 32 && this.field_1185_d[var27] <= var14) {
                                                this.field_1185_d[var27] = var14;
                                                this.field_1184_e[var27] = (int)(var26 * 127.0f);
                                            }
                                            if (var12 >= -1 && var12 <= 31 && this.field_1185_d[var27 + 1] <= var14) {
                                                this.field_1185_d[var27 + 1] = var14;
                                                this.field_1184_e[var27 + 1] = (int)(var26 * 127.0f);
                                            }
                                            var13 = false;
                                        }
                                    }
                                } else {
                                    float var22;
                                    if (var13) {
                                        if (var15 < this.field_1183_f[var12]) {
                                            this.field_1183_f[var12] = var15;
                                        }
                                        if (var15 < this.field_1183_f[var12 + 1]) {
                                            this.field_1183_f[var12 + 1] = var15;
                                        }
                                    }
                                    float var17 = (float)var14 / 127.0f * 0.6f + 0.4f;
                                    if (var15 >= 0 && var15 < 160) {
                                        int var18 = var12 + var15 * 32;
                                        int var19 = this.field_1182_g[var16.blockID * 3 + 0];
                                        float var20 = (var2.getLightBrightness(var9, var14 + 1, var8) * 0.8f + 0.2f) * var17;
                                        if (var12 >= 0 && this.field_1186_c[var18] <= var14) {
                                            this.field_1186_c[var18] = var14;
                                            this.field_1180_b[var18] = 0xFF000000 | (int)(this.field_1181_a[var19 * 3 + 0] * var20) << 16 | (int)(this.field_1181_a[var19 * 3 + 1] * var20) << 8 | (int)(this.field_1181_a[var19 * 3 + 2] * var20);
                                        }
                                        if (var12 < 31) {
                                            var22 = var20 * 0.9f;
                                            if (this.field_1186_c[var18 + 1] <= var14) {
                                                this.field_1186_c[var18 + 1] = var14;
                                                this.field_1180_b[var18 + 1] = 0xFF000000 | (int)(this.field_1181_a[var19 * 3 + 0] * var22) << 16 | (int)(this.field_1181_a[var19 * 3 + 1] * var22) << 8 | (int)(this.field_1181_a[var19 * 3 + 2] * var22);
                                            }
                                        }
                                    }
                                    if (var15 >= -1 && var15 < 159) {
                                        float var23;
                                        int var18 = var12 + (var15 + 1) * 32;
                                        int var19 = this.field_1182_g[var16.blockID * 3 + 1];
                                        float var20 = var2.getLightBrightness(var9 - 1, var14, var8) * 0.8f + 0.2f;
                                        int var21 = this.field_1182_g[var16.blockID * 3 + 2];
                                        var22 = var2.getLightBrightness(var9, var14, var8 + 1) * 0.8f + 0.2f;
                                        if (var12 >= 0) {
                                            var23 = var20 * var17 * 0.6f;
                                            if (this.field_1186_c[var18] <= var14 - 1) {
                                                this.field_1186_c[var18] = var14 - 1;
                                                this.field_1180_b[var18] = 0xFF000000 | (int)(this.field_1181_a[var19 * 3 + 0] * var23) << 16 | (int)(this.field_1181_a[var19 * 3 + 1] * var23) << 8 | (int)(this.field_1181_a[var19 * 3 + 2] * var23);
                                            }
                                        }
                                        if (var12 < 31) {
                                            var23 = var22 * 0.9f * var17 * 0.4f;
                                            if (this.field_1186_c[var18 + 1] <= var14 - 1) {
                                                this.field_1186_c[var18 + 1] = var14 - 1;
                                                this.field_1180_b[var18 + 1] = 0xFF000000 | (int)(this.field_1181_a[var21 * 3 + 0] * var23) << 16 | (int)(this.field_1181_a[var21 * 3 + 1] * var23) << 8 | (int)(this.field_1181_a[var21 * 3 + 2] * var23);
                                            }
                                        }
                                    }
                                }
                            }
                            ++var14;
                        }
                        --var9;
                    }
                    --var8;
                }
                this.func_800_a();
                if (var1.field_1348_a == null) {
                    var1.field_1348_a = new BufferedImage(32, 160, 2);
                }
                var1.field_1348_a.setRGB(0, 0, 32, 160, this.field_1180_b, 0, 32);
                var1.field_1352_e = true;
            }
        }
    }

    private void func_800_a() {
        int var1 = 0;
        while (var1 < 32) {
            int var2 = 0;
            while (var2 < 160) {
                int var3 = var1 + var2 * 32;
                if (this.field_1186_c[var3] == 0) {
                    this.field_1180_b[var3] = 0;
                }
                if (this.field_1185_d[var3] > this.field_1186_c[var3]) {
                    int var4 = this.field_1180_b[var3] >> 24 & 0xFF;
                    this.field_1180_b[var3] = ((this.field_1180_b[var3] & 0xFEFEFE) >> 1) + this.field_1184_e[var3];
                    if (var4 < 128) {
                        this.field_1180_b[var3] = Integer.MIN_VALUE + this.field_1184_e[var3] * 2;
                    } else {
                        int[] var10000 = this.field_1180_b;
                        int n = var3;
                        var10000[n] = var10000[n] | 0xFF000000;
                    }
                }
                ++var2;
            }
            ++var1;
        }
    }
}

