/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.Random;
import net.minecraft.src.Block;
import net.minecraft.src.MapGenBase;
import net.minecraft.src.MathHelper;
import net.minecraft.src.World;

public class MapGenCaves
extends MapGenBase {
    protected void func_870_a(int var1, int var2, byte[] var3, double var4, double var6, double var8) {
        this.releaseEntitySkin(var1, var2, var3, var4, var6, var8, 1.0f + this.rand.nextFloat() * 6.0f, 0.0f, 0.0f, -1, -1, 0.5);
    }

    protected void releaseEntitySkin(int var1, int var2, byte[] var3, double var4, double var6, double var8, float var10, float var11, float var12, int var13, int var14, double var15) {
        double var17 = var1 * 16 + 8;
        double var19 = var2 * 16 + 8;
        float var21 = 0.0f;
        float var22 = 0.0f;
        Random var23 = new Random(this.rand.nextLong());
        if (var14 <= 0) {
            int var24 = this.field_1306_a * 16 - 16;
            var14 = var24 - var23.nextInt(var24 / 4);
        }
        boolean var52 = false;
        if (var13 == -1) {
            var13 = var14 / 2;
            var52 = true;
        }
        int var25 = var23.nextInt(var14 / 2) + var14 / 4;
        boolean var26 = var23.nextInt(6) == 0;
        while (var13 < var14) {
            double var27 = 1.5 + (double)(MathHelper.sin((float)var13 * (float)Math.PI / (float)var14) * var10 * 1.0f);
            double var29 = var27 * var15;
            float var31 = MathHelper.cos(var12);
            float var32 = MathHelper.sin(var12);
            var4 += (double)(MathHelper.cos(var11) * var31);
            var6 += (double)var32;
            var8 += (double)(MathHelper.sin(var11) * var31);
            var12 = var26 ? (var12 *= 0.92f) : (var12 *= 0.7f);
            var12 += var22 * 0.1f;
            var11 += var21 * 0.1f;
            var22 *= 0.9f;
            var21 *= 0.75f;
            var22 += (var23.nextFloat() - var23.nextFloat()) * var23.nextFloat() * 2.0f;
            var21 += (var23.nextFloat() - var23.nextFloat()) * var23.nextFloat() * 4.0f;
            if (!var52 && var13 == var25 && var10 > 1.0f) {
                this.releaseEntitySkin(var1, var2, var3, var4, var6, var8, var23.nextFloat() * 0.5f + 0.5f, var11 - 1.5707964f, var12 / 3.0f, var13, var14, 1.0);
                this.releaseEntitySkin(var1, var2, var3, var4, var6, var8, var23.nextFloat() * 0.5f + 0.5f, var11 + 1.5707964f, var12 / 3.0f, var13, var14, 1.0);
                return;
            }
            if (var52 || var23.nextInt(4) != 0) {
                double var33 = var4 - var17;
                double var35 = var8 - var19;
                double var37 = var14 - var13;
                double var39 = var10 + 2.0f + 16.0f;
                if (var33 * var33 + var35 * var35 - var37 * var37 > var39 * var39) {
                    return;
                }
                if (var4 >= var17 - 16.0 - var27 * 2.0 && var8 >= var19 - 16.0 - var27 * 2.0 && var4 <= var17 + 16.0 + var27 * 2.0 && var8 <= var19 + 16.0 + var27 * 2.0) {
                    int var43;
                    int var53 = MathHelper.floor_double(var4 - var27) - var1 * 16 - 1;
                    int var34 = MathHelper.floor_double(var4 + var27) - var1 * 16 + 1;
                    int var54 = MathHelper.floor_double(var6 - var29) - 1;
                    int var36 = MathHelper.floor_double(var6 + var29) + 1;
                    int var55 = MathHelper.floor_double(var8 - var27) - var2 * 16 - 1;
                    int var38 = MathHelper.floor_double(var8 + var27) - var2 * 16 + 1;
                    if (var53 < 0) {
                        var53 = 0;
                    }
                    if (var34 > 16) {
                        var34 = 16;
                    }
                    if (var54 < 1) {
                        var54 = 1;
                    }
                    if (var36 > 120) {
                        var36 = 120;
                    }
                    if (var55 < 0) {
                        var55 = 0;
                    }
                    if (var38 > 16) {
                        var38 = 16;
                    }
                    boolean var56 = false;
                    int var40 = var53;
                    while (!var56 && var40 < var34) {
                        int var41 = var55;
                        while (!var56 && var41 < var38) {
                            int var42 = var36 + 1;
                            while (!var56 && var42 >= var54 - 1) {
                                var43 = (var40 * 16 + var41) * 128 + var42;
                                if (var42 >= 0 && var42 < 128) {
                                    if (var3[var43] == Block.waterMoving.blockID || var3[var43] == Block.waterStill.blockID) {
                                        var56 = true;
                                    }
                                    if (var42 != var54 - 1 && var40 != var53 && var40 != var34 - 1 && var41 != var55 && var41 != var38 - 1) {
                                        var42 = var54;
                                    }
                                }
                                --var42;
                            }
                            ++var41;
                        }
                        ++var40;
                    }
                    if (!var56) {
                        var40 = var53;
                        while (var40 < var34) {
                            double var57 = ((double)(var40 + var1 * 16) + 0.5 - var4) / var27;
                            var43 = var55;
                            while (var43 < var38) {
                                double var44 = ((double)(var43 + var2 * 16) + 0.5 - var8) / var27;
                                int var46 = (var40 * 16 + var43) * 128 + var36;
                                boolean var47 = false;
                                if (var57 * var57 + var44 * var44 < 1.0) {
                                    int var48 = var36 - 1;
                                    while (var48 >= var54) {
                                        double var49 = ((double)var48 + 0.5 - var6) / var29;
                                        if (var49 > -0.7 && var57 * var57 + var49 * var49 + var44 * var44 < 1.0) {
                                            byte var51 = var3[var46];
                                            if (var51 == Block.grass.blockID) {
                                                var47 = true;
                                            }
                                            if (var51 == Block.stone.blockID || var51 == Block.dirt.blockID || var51 == Block.grass.blockID) {
                                                if (var48 < 10) {
                                                    var3[var46] = (byte)Block.lavaMoving.blockID;
                                                } else {
                                                    var3[var46] = 0;
                                                    if (var47 && var3[var46 - 1] == Block.dirt.blockID) {
                                                        var3[var46 - 1] = (byte)Block.grass.blockID;
                                                    }
                                                }
                                            }
                                        }
                                        --var46;
                                        --var48;
                                    }
                                }
                                ++var43;
                            }
                            ++var40;
                        }
                        if (var52) break;
                    }
                }
            }
            ++var13;
        }
    }

    @Override
    protected void func_868_a(World var1, int var2, int var3, int var4, int var5, byte[] var6) {
        int var7 = this.rand.nextInt(this.rand.nextInt(this.rand.nextInt(40) + 1) + 1);
        if (this.rand.nextInt(15) != 0) {
            var7 = 0;
        }
        int var8 = 0;
        while (var8 < var7) {
            double var9 = var2 * 16 + this.rand.nextInt(16);
            double var11 = this.rand.nextInt(this.rand.nextInt(120) + 8);
            double var13 = var3 * 16 + this.rand.nextInt(16);
            int var15 = 1;
            if (this.rand.nextInt(4) == 0) {
                this.func_870_a(var4, var5, var6, var9, var11, var13);
                var15 += this.rand.nextInt(4);
            }
            int var16 = 0;
            while (var16 < var15) {
                float var17 = this.rand.nextFloat() * (float)Math.PI * 2.0f;
                float var18 = (this.rand.nextFloat() - 0.5f) * 2.0f / 8.0f;
                float var19 = this.rand.nextFloat() * 2.0f + this.rand.nextFloat();
                this.releaseEntitySkin(var4, var5, var6, var9, var11, var13, var19, var17, var18, 0, 0, 1.0);
                ++var16;
            }
            ++var8;
        }
    }
}

