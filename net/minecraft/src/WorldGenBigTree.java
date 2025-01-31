/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.Random;
import net.minecraft.src.MathHelper;
import net.minecraft.src.World;
import net.minecraft.src.WorldGenerator;

public class WorldGenBigTree
extends WorldGenerator {
    static final byte[] field_882_a;
    Random field_881_b = new Random();
    World worldObj;
    int[] basePos = new int[3];
    int field_878_e = 0;
    int height;
    double field_876_g = 0.618;
    double field_875_h = 1.0;
    double field_874_i = 0.381;
    double field_873_j = 1.0;
    double field_872_k = 1.0;
    int field_871_l = 1;
    int field_870_m = 12;
    int field_869_n = 4;
    int[][] field_868_o;

    static {
        byte[] byArray = new byte[6];
        byArray[0] = 2;
        byArray[3] = 1;
        byArray[4] = 2;
        byArray[5] = 1;
        field_882_a = byArray;
    }

    void func_521_a() {
        int var1;
        this.height = (int)((double)this.field_878_e * this.field_876_g);
        if (this.height >= this.field_878_e) {
            this.height = this.field_878_e - 1;
        }
        if ((var1 = (int)(1.382 + Math.pow(this.field_872_k * (double)this.field_878_e / 13.0, 2.0))) < 1) {
            var1 = 1;
        }
        int[][] var2 = new int[var1 * this.field_878_e][4];
        int var3 = this.basePos[1] + this.field_878_e - this.field_869_n;
        int var4 = 1;
        int var5 = this.basePos[1] + this.height;
        int var6 = var3 - this.basePos[1];
        var2[0][0] = this.basePos[0];
        var2[0][1] = var3--;
        var2[0][2] = this.basePos[2];
        var2[0][3] = var5;
        while (var6 >= 0) {
            int var7 = 0;
            float var8 = this.func_528_a(var6);
            if (var8 < 0.0f) {
                --var3;
                --var6;
                continue;
            }
            double var9 = 0.5;
            while (var7 < var1) {
                int[] var18;
                int var16;
                double var13;
                double var11 = this.field_873_j * (double)var8 * ((double)this.field_881_b.nextFloat() + 0.328);
                int var15 = (int)(var11 * Math.sin(var13 = (double)this.field_881_b.nextFloat() * 2.0 * 3.14159) + (double)this.basePos[0] + var9);
                int[] var17 = new int[]{var15, var3, var16 = (int)(var11 * Math.cos(var13) + (double)this.basePos[2] + var9)};
                if (this.func_524_a(var17, var18 = new int[]{var15, var3 + this.field_869_n, var16}) == -1) {
                    int[] var19 = new int[]{this.basePos[0], this.basePos[1], this.basePos[2]};
                    double var20 = Math.sqrt(Math.pow(Math.abs(this.basePos[0] - var17[0]), 2.0) + Math.pow(Math.abs(this.basePos[2] - var17[2]), 2.0));
                    double var22 = var20 * this.field_874_i;
                    var19[1] = (double)var17[1] - var22 > (double)var5 ? var5 : (int)((double)var17[1] - var22);
                    if (this.func_524_a(var19, var17) == -1) {
                        var2[var4][0] = var15;
                        var2[var4][1] = var3;
                        var2[var4][2] = var16;
                        var2[var4][3] = var19[1];
                        ++var4;
                    }
                }
                ++var7;
            }
            --var3;
            --var6;
        }
        this.field_868_o = new int[var4][4];
        System.arraycopy(var2, 0, this.field_868_o, 0, var4);
    }

    void func_523_a(int var1, int var2, int var3, float var4, byte var5, int var6) {
        int var7 = (int)((double)var4 + 0.618);
        byte var8 = field_882_a[var5];
        byte var9 = field_882_a[var5 + 3];
        int[] var10 = new int[]{var1, var2, var3};
        int[] var11 = new int[3];
        int var12 = -var7;
        int var13 = -var7;
        var11[var5] = var10[var5];
        while (var12 <= var7) {
            var11[var8] = var10[var8] + var12;
            var13 = -var7;
            while (var13 <= var7) {
                double var15 = Math.sqrt(Math.pow((double)Math.abs(var12) + 0.5, 2.0) + Math.pow((double)Math.abs(var13) + 0.5, 2.0));
                if (var15 > (double)var4) {
                    ++var13;
                    continue;
                }
                var11[var9] = var10[var9] + var13;
                int var14 = this.worldObj.getBlockId(var11[0], var11[1], var11[2]);
                if (var14 != 0 && var14 != 18) {
                    ++var13;
                    continue;
                }
                this.worldObj.setBlock(var11[0], var11[1], var11[2], var6);
                ++var13;
            }
            ++var12;
        }
    }

    float func_528_a(int var1) {
        if ((double)var1 < (double)this.field_878_e * 0.3) {
            return -1.618f;
        }
        float var2 = (float)this.field_878_e / 2.0f;
        float var3 = (float)this.field_878_e / 2.0f - (float)var1;
        float var4 = var3 == 0.0f ? var2 : (Math.abs(var3) >= var2 ? 0.0f : (float)Math.sqrt(Math.pow(Math.abs(var2), 2.0) - Math.pow(Math.abs(var3), 2.0)));
        return var4 *= 0.5f;
    }

    float func_526_b(int var1) {
        if (var1 >= 0 && var1 < this.field_869_n) {
            return var1 != 0 && var1 != this.field_869_n - 1 ? 3.0f : 2.0f;
        }
        return -1.0f;
    }

    void func_520_a(int var1, int var2, int var3) {
        int var4 = var2;
        int var5 = var2 + this.field_869_n;
        while (var4 < var5) {
            float var6 = this.func_526_b(var4 - var2);
            this.func_523_a(var1, var4, var3, var6, (byte)1, 18);
            ++var4;
        }
    }

    void func_522_a(int[] var1, int[] var2, int var3) {
        int[] var4 = new int[3];
        int var5 = 0;
        int var6 = 0;
        while (var5 < 3) {
            var4[var5] = var2[var5] - var1[var5];
            if (Math.abs(var4[var5]) > Math.abs(var4[var6])) {
                var6 = var5;
            }
            var5 = (byte)(var5 + 1);
        }
        if (var4[var6] != 0) {
            byte var7 = field_882_a[var6];
            byte var8 = field_882_a[var6 + 3];
            int var9 = var4[var6] > 0 ? 1 : -1;
            double var10 = (double)var4[var7] / (double)var4[var6];
            double var12 = (double)var4[var8] / (double)var4[var6];
            int[] var14 = new int[3];
            int var15 = 0;
            int var16 = var4[var6] + var9;
            while (var15 != var16) {
                var14[var6] = MathHelper.floor_double((double)(var1[var6] + var15) + 0.5);
                var14[var7] = MathHelper.floor_double((double)var1[var7] + (double)var15 * var10 + 0.5);
                var14[var8] = MathHelper.floor_double((double)var1[var8] + (double)var15 * var12 + 0.5);
                this.worldObj.setBlock(var14[0], var14[1], var14[2], var3);
                var15 += var9;
            }
        }
    }

    void func_518_b() {
        int var1 = 0;
        int var2 = this.field_868_o.length;
        while (var1 < var2) {
            int var3 = this.field_868_o[var1][0];
            int var4 = this.field_868_o[var1][1];
            int var5 = this.field_868_o[var1][2];
            this.func_520_a(var3, var4, var5);
            ++var1;
        }
    }

    boolean func_527_c(int var1) {
        return (double)var1 >= (double)this.field_878_e * 0.2;
    }

    void func_529_c() {
        int var1 = this.basePos[0];
        int var2 = this.basePos[1];
        int var3 = this.basePos[1] + this.height;
        int var4 = this.basePos[2];
        int[] var5 = new int[]{var1, var2, var4};
        int[] var6 = new int[]{var1, var3, var4};
        this.func_522_a(var5, var6, 17);
        if (this.field_871_l == 2) {
            int n = var5[0];
            var5[0] = n + 1;
            int var10002 = n;
            int n2 = var6[0];
            var6[0] = n2 + 1;
            var10002 = n2;
            this.func_522_a(var5, var6, 17);
            int n3 = var5[2];
            var5[2] = n3 + 1;
            var10002 = n3;
            int n4 = var6[2];
            var6[2] = n4 + 1;
            var10002 = n4;
            this.func_522_a(var5, var6, 17);
            var5[0] = var5[0] + -1;
            var6[0] = var6[0] + -1;
            this.func_522_a(var5, var6, 17);
        }
    }

    void func_525_d() {
        int var1 = 0;
        int var2 = this.field_868_o.length;
        int[] var3 = new int[]{this.basePos[0], this.basePos[1], this.basePos[2]};
        while (var1 < var2) {
            int[] var4 = this.field_868_o[var1];
            int[] var5 = new int[]{var4[0], var4[1], var4[2]};
            var3[1] = var4[3];
            int var6 = var3[1] - this.basePos[1];
            if (this.func_527_c(var6)) {
                this.func_522_a(var3, var5, 17);
            }
            ++var1;
        }
    }

    int func_524_a(int[] var1, int[] var2) {
        int[] var3 = new int[3];
        int var4 = 0;
        int var5 = 0;
        while (var4 < 3) {
            var3[var4] = var2[var4] - var1[var4];
            if (Math.abs(var3[var4]) > Math.abs(var3[var5])) {
                var5 = var4;
            }
            var4 = (byte)(var4 + 1);
        }
        if (var3[var5] == 0) {
            return -1;
        }
        byte var6 = field_882_a[var5];
        byte var7 = field_882_a[var5 + 3];
        int var8 = var3[var5] > 0 ? 1 : -1;
        double var9 = (double)var3[var6] / (double)var3[var5];
        double var11 = (double)var3[var7] / (double)var3[var5];
        int[] var13 = new int[3];
        int var14 = 0;
        int var15 = var3[var5] + var8;
        while (var14 != var15) {
            var13[var5] = var1[var5] + var14;
            var13[var6] = (int)((double)var1[var6] + (double)var14 * var9);
            var13[var7] = (int)((double)var1[var7] + (double)var14 * var11);
            int var16 = this.worldObj.getBlockId(var13[0], var13[1], var13[2]);
            if (var16 != 0 && var16 != 18) break;
            var14 += var8;
        }
        return var14 == var15 ? -1 : Math.abs(var14);
    }

    boolean func_519_e() {
        int[] var1 = new int[]{this.basePos[0], this.basePos[1], this.basePos[2]};
        int[] var2 = new int[]{this.basePos[0], this.basePos[1] + this.field_878_e - 1, this.basePos[2]};
        int var3 = this.worldObj.getBlockId(this.basePos[0], this.basePos[1] - 1, this.basePos[2]);
        if (var3 != 2 && var3 != 3) {
            return false;
        }
        int var4 = this.func_524_a(var1, var2);
        if (var4 == -1) {
            return true;
        }
        if (var4 < 6) {
            return false;
        }
        this.field_878_e = var4;
        return true;
    }

    @Override
    public void func_517_a(double var1, double var3, double var5) {
        this.field_870_m = (int)(var1 * 12.0);
        if (var1 > 0.5) {
            this.field_869_n = 5;
        }
        this.field_873_j = var3;
        this.field_872_k = var5;
    }

    @Override
    public boolean generate(World var1, Random var2, int var3, int var4, int var5) {
        this.worldObj = var1;
        long var6 = var2.nextLong();
        this.field_881_b.setSeed(var6);
        this.basePos[0] = var3;
        this.basePos[1] = var4;
        this.basePos[2] = var5;
        if (this.field_878_e == 0) {
            this.field_878_e = 5 + this.field_881_b.nextInt(this.field_870_m);
        }
        if (!this.func_519_e()) {
            return false;
        }
        this.func_521_a();
        this.func_518_b();
        this.func_529_c();
        this.func_525_d();
        return true;
    }
}

