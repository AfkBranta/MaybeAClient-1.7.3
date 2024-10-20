/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.Random;
import net.minecraft.src.Block;
import net.minecraft.src.Entity;
import net.minecraft.src.MathHelper;
import net.minecraft.src.World;

public class Teleporter {
    private Random field_4232_a = new Random();

    public void func_4107_a(World var1, Entity var2) {
        if (!this.func_4106_b(var1, var2)) {
            this.func_4108_c(var1, var2);
            this.func_4106_b(var1, var2);
        }
    }

    public boolean func_4106_b(World var1, Entity var2) {
        double var18;
        int var3 = 128;
        double var4 = -1.0;
        int var6 = 0;
        int var7 = 0;
        int var8 = 0;
        int var9 = MathHelper.floor_double(var2.posX);
        int var10 = MathHelper.floor_double(var2.posZ);
        int var11 = var9 - var3;
        while (var11 <= var9 + var3) {
            double var12 = (double)var11 + 0.5 - var2.posX;
            int var14 = var10 - var3;
            while (var14 <= var10 + var3) {
                double var15 = (double)var14 + 0.5 - var2.posZ;
                int var17 = 127;
                while (var17 >= 0) {
                    if (var1.getBlockId(var11, var17, var14) == Block.portal.blockID) {
                        while (var1.getBlockId(var11, var17 - 1, var14) == Block.portal.blockID) {
                            --var17;
                        }
                        var18 = (double)var17 + 0.5 - var2.posY;
                        double var20 = var12 * var12 + var18 * var18 + var15 * var15;
                        if (var4 < 0.0 || var20 < var4) {
                            var4 = var20;
                            var6 = var11;
                            var7 = var17;
                            var8 = var14;
                        }
                    }
                    --var17;
                }
                ++var14;
            }
            ++var11;
        }
        if (var4 >= 0.0) {
            double var22 = (double)var6 + 0.5;
            double var16 = (double)var7 + 0.5;
            var18 = (double)var8 + 0.5;
            if (var1.getBlockId(var6 - 1, var7, var8) == Block.portal.blockID) {
                var22 -= 0.5;
            }
            if (var1.getBlockId(var6 + 1, var7, var8) == Block.portal.blockID) {
                var22 += 0.5;
            }
            if (var1.getBlockId(var6, var7, var8 - 1) == Block.portal.blockID) {
                var18 -= 0.5;
            }
            if (var1.getBlockId(var6, var7, var8 + 1) == Block.portal.blockID) {
                var18 += 0.5;
            }
            System.out.println("Teleporting to " + var22 + ", " + var16 + ", " + var18);
            var2.setLocationAndAngles(var22, var16, var18, var2.rotationYaw, 0.0f);
            var2.motionZ = 0.0;
            var2.motionY = 0.0;
            var2.motionX = 0.0;
            return true;
        }
        return false;
    }

    public boolean func_4108_c(World var1, Entity var2) {
        boolean var34;
        double var33;
        double var32;
        int var28;
        int var27;
        int var26;
        int var25;
        int var24;
        int var23;
        int var22;
        int var21;
        int var20;
        double var18;
        int var17;
        double var15;
        int var3 = 16;
        double var4 = -1.0;
        int var6 = MathHelper.floor_double(var2.posX);
        int var7 = MathHelper.floor_double(var2.posY);
        int var8 = MathHelper.floor_double(var2.posZ);
        int var9 = var6;
        int var10 = var7;
        int var11 = var8;
        int var12 = 0;
        int var13 = this.field_4232_a.nextInt(4);
        int var14 = var6 - var3;
        while (var14 <= var6 + var3) {
            var15 = (double)var14 + 0.5 - var2.posX;
            var17 = var8 - var3;
            while (var17 <= var8 + var3) {
                var18 = (double)var17 + 0.5 - var2.posZ;
                var20 = 127;
                while (var20 >= 0) {
                    if (var1.isAirBlock(var14, var20, var17)) {
                        while (var20 > 0 && var1.isAirBlock(var14, var20 - 1, var17)) {
                            --var20;
                        }
                        var21 = var13;
                        block4: while (var21 < var13 + 4) {
                            var22 = var21 % 2;
                            var23 = 1 - var22;
                            if (var21 % 4 >= 2) {
                                var22 = -var22;
                                var23 = -var23;
                            }
                            var24 = 0;
                            while (var24 < 3) {
                                var25 = 0;
                                while (var25 < 4) {
                                    var26 = -1;
                                    while (var26 < 4) {
                                        var27 = var14 + (var25 - 1) * var22 + var24 * var23;
                                        var28 = var20 + var26;
                                        int var29 = var17 + (var25 - 1) * var23 - var24 * var22;
                                        if (var26 < 0 && !var1.getBlockMaterial(var27, var28, var29).isSolid() || var26 >= 0 && !var1.isAirBlock(var27, var28, var29)) break block4;
                                        ++var26;
                                    }
                                    ++var25;
                                }
                                ++var24;
                            }
                            var32 = (double)var20 + 0.5 - var2.posY;
                            var33 = var15 * var15 + var32 * var32 + var18 * var18;
                            if (var4 < 0.0 || var33 < var4) {
                                var4 = var33;
                                var9 = var14;
                                var10 = var20;
                                var11 = var17;
                                var12 = var21 % 4;
                            }
                            ++var21;
                        }
                    }
                    --var20;
                }
                ++var17;
            }
            ++var14;
        }
        if (var4 < 0.0) {
            var14 = var6 - var3;
            while (var14 <= var6 + var3) {
                var15 = (double)var14 + 0.5 - var2.posX;
                var17 = var8 - var3;
                while (var17 <= var8 + var3) {
                    var18 = (double)var17 + 0.5 - var2.posZ;
                    var20 = 127;
                    while (var20 >= 0) {
                        if (var1.isAirBlock(var14, var20, var17)) {
                            while (var1.isAirBlock(var14, var20 - 1, var17)) {
                                --var20;
                            }
                            var21 = var13;
                            block12: while (var21 < var13 + 2) {
                                var22 = var21 % 2;
                                var23 = 1 - var22;
                                var24 = 0;
                                while (var24 < 4) {
                                    var25 = -1;
                                    while (var25 < 4) {
                                        var26 = var14 + (var24 - 1) * var22;
                                        var27 = var20 + var25;
                                        var28 = var17 + (var24 - 1) * var23;
                                        if (var25 < 0 && !var1.getBlockMaterial(var26, var27, var28).isSolid() || var25 >= 0 && !var1.isAirBlock(var26, var27, var28)) break block12;
                                        ++var25;
                                    }
                                    ++var24;
                                }
                                var32 = (double)var20 + 0.5 - var2.posY;
                                var33 = var15 * var15 + var32 * var32 + var18 * var18;
                                if (var4 < 0.0 || var33 < var4) {
                                    var4 = var33;
                                    var9 = var14;
                                    var10 = var20;
                                    var11 = var17;
                                    var12 = var21 % 2;
                                }
                                ++var21;
                            }
                        }
                        --var20;
                    }
                    ++var17;
                }
                ++var14;
            }
        }
        int var30 = var9;
        int var16 = var10;
        var17 = var11;
        int var31 = var12 % 2;
        int var19 = 1 - var31;
        if (var12 % 4 >= 2) {
            var31 = -var31;
            var19 = -var19;
        }
        if (var4 < 0.0) {
            if (var10 < 70) {
                var10 = 70;
            }
            if (var10 > 118) {
                var10 = 118;
            }
            var16 = var10;
            var20 = -1;
            while (var20 <= 1) {
                var21 = 1;
                while (var21 < 3) {
                    var22 = -1;
                    while (var22 < 3) {
                        var23 = var30 + (var21 - 1) * var31 + var20 * var19;
                        var24 = var16 + var22;
                        var25 = var17 + (var21 - 1) * var19 - var20 * var31;
                        var34 = var22 < 0;
                        var1.setBlockWithNotify(var23, var24, var25, var34 ? Block.obsidian.blockID : 0);
                        ++var22;
                    }
                    ++var21;
                }
                ++var20;
            }
        }
        var20 = 0;
        while (var20 < 4) {
            var1.editingBlocks = true;
            var21 = 0;
            while (var21 < 4) {
                var22 = -1;
                while (var22 < 4) {
                    var23 = var30 + (var21 - 1) * var31;
                    var24 = var16 + var22;
                    var25 = var17 + (var21 - 1) * var19;
                    var34 = var21 == 0 || var21 == 3 || var22 == -1 || var22 == 3;
                    var1.setBlockWithNotify(var23, var24, var25, var34 ? Block.obsidian.blockID : Block.portal.blockID);
                    ++var22;
                }
                ++var21;
            }
            var1.editingBlocks = false;
            var21 = 0;
            while (var21 < 4) {
                var22 = -1;
                while (var22 < 4) {
                    var23 = var30 + (var21 - 1) * var31;
                    var24 = var16 + var22;
                    var25 = var17 + (var21 - 1) * var19;
                    var1.notifyBlocksOfNeighborChange(var23, var24, var25, var1.getBlockId(var23, var24, var25));
                    ++var22;
                }
                ++var21;
            }
            ++var20;
        }
        return true;
    }
}

