/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.Random;

public class NoiseGenerator2 {
    private static int[][] field_4296_d;
    private int[] field_4295_e = new int[512];
    public double field_4292_a;
    public double field_4291_b;
    public double field_4297_c;
    private static final double field_4294_f;
    private static final double field_4293_g;

    static {
        int[][] nArrayArray = new int[12][];
        int[] nArray = new int[3];
        nArray[0] = 1;
        nArray[1] = 1;
        nArrayArray[0] = nArray;
        int[] nArray2 = new int[3];
        nArray2[0] = -1;
        nArray2[1] = 1;
        nArrayArray[1] = nArray2;
        int[] nArray3 = new int[3];
        nArray3[0] = 1;
        nArray3[1] = -1;
        nArrayArray[2] = nArray3;
        int[] nArray4 = new int[3];
        nArray4[0] = -1;
        nArray4[1] = -1;
        nArrayArray[3] = nArray4;
        int[] nArray5 = new int[3];
        nArray5[0] = 1;
        nArray5[2] = 1;
        nArrayArray[4] = nArray5;
        int[] nArray6 = new int[3];
        nArray6[0] = -1;
        nArray6[2] = 1;
        nArrayArray[5] = nArray6;
        int[] nArray7 = new int[3];
        nArray7[0] = 1;
        nArray7[2] = -1;
        nArrayArray[6] = nArray7;
        int[] nArray8 = new int[3];
        nArray8[0] = -1;
        nArray8[2] = -1;
        nArrayArray[7] = nArray8;
        int[] nArray9 = new int[3];
        nArray9[1] = 1;
        nArray9[2] = 1;
        nArrayArray[8] = nArray9;
        int[] nArray10 = new int[3];
        nArray10[1] = -1;
        nArray10[2] = 1;
        nArrayArray[9] = nArray10;
        int[] nArray11 = new int[3];
        nArray11[1] = 1;
        nArray11[2] = -1;
        nArrayArray[10] = nArray11;
        int[] nArray12 = new int[3];
        nArray12[1] = -1;
        nArray12[2] = -1;
        nArrayArray[11] = nArray12;
        field_4296_d = nArrayArray;
        field_4294_f = 0.5 * (Math.sqrt(3.0) - 1.0);
        field_4293_g = (3.0 - Math.sqrt(3.0)) / 6.0;
    }

    public NoiseGenerator2() {
        this(new Random());
    }

    public NoiseGenerator2(Random var1) {
        this.field_4292_a = var1.nextDouble() * 256.0;
        this.field_4291_b = var1.nextDouble() * 256.0;
        this.field_4297_c = var1.nextDouble() * 256.0;
        int var2 = 0;
        while (var2 < 256) {
            this.field_4295_e[var2] = var2++;
        }
        var2 = 0;
        while (var2 < 256) {
            int var3 = var1.nextInt(256 - var2) + var2;
            int var4 = this.field_4295_e[var2];
            this.field_4295_e[var2] = this.field_4295_e[var3];
            this.field_4295_e[var3] = var4;
            this.field_4295_e[var2 + 256] = this.field_4295_e[var2];
            ++var2;
        }
    }

    private static int wrap(double var0) {
        return var0 > 0.0 ? (int)var0 : (int)var0 - 1;
    }

    private static double func_4156_a(int[] var0, double var1, double var3) {
        return (double)var0[0] * var1 + (double)var0[1] * var3;
    }

    public void func_4157_a(double[] var1, double var2, double var4, int var6, int var7, double var8, double var10, double var12) {
        int var14 = 0;
        int var15 = 0;
        while (var15 < var6) {
            double var16 = (var2 + (double)var15) * var8 + this.field_4292_a;
            int var18 = 0;
            while (var18 < var7) {
                int var10001;
                double var25;
                double var23;
                double var21;
                int var42;
                int var41;
                double var35;
                double var39;
                int var30;
                double var31;
                double var19 = (var4 + (double)var18) * var10 + this.field_4291_b;
                double var27 = (var16 + var19) * field_4294_f;
                int var29 = NoiseGenerator2.wrap(var16 + var27);
                double var33 = (double)var29 - (var31 = (double)(var29 + (var30 = NoiseGenerator2.wrap(var19 + var27))) * field_4293_g);
                double var37 = var16 - var33;
                if (var37 > (var39 = var19 - (var35 = (double)var30 - var31))) {
                    var41 = 1;
                    var42 = 0;
                } else {
                    var41 = 0;
                    var42 = 1;
                }
                double var43 = var37 - (double)var41 + field_4293_g;
                double var45 = var39 - (double)var42 + field_4293_g;
                double var47 = var37 - 1.0 + 2.0 * field_4293_g;
                double var49 = var39 - 1.0 + 2.0 * field_4293_g;
                int var51 = var29 & 0xFF;
                int var52 = var30 & 0xFF;
                int var53 = this.field_4295_e[var51 + this.field_4295_e[var52]] % 12;
                int var54 = this.field_4295_e[var51 + var41 + this.field_4295_e[var52 + var42]] % 12;
                int var55 = this.field_4295_e[var51 + 1 + this.field_4295_e[var52 + 1]] % 12;
                double var56 = 0.5 - var37 * var37 - var39 * var39;
                if (var56 < 0.0) {
                    var21 = 0.0;
                } else {
                    var56 *= var56;
                    var21 = var56 * var56 * NoiseGenerator2.func_4156_a(field_4296_d[var53], var37, var39);
                }
                double var58 = 0.5 - var43 * var43 - var45 * var45;
                if (var58 < 0.0) {
                    var23 = 0.0;
                } else {
                    var58 *= var58;
                    var23 = var58 * var58 * NoiseGenerator2.func_4156_a(field_4296_d[var54], var43, var45);
                }
                double var60 = 0.5 - var47 * var47 - var49 * var49;
                if (var60 < 0.0) {
                    var25 = 0.0;
                } else {
                    var60 *= var60;
                    var25 = var60 * var60 * NoiseGenerator2.func_4156_a(field_4296_d[var55], var47, var49);
                }
                int n = var10001 = var14++;
                var1[n] = var1[n] + 70.0 * (var21 + var23 + var25) * var12;
                ++var18;
            }
            ++var15;
        }
    }
}

