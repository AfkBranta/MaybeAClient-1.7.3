/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.Random;
import net.minecraft.src.ChunkCoordIntPair;
import net.minecraft.src.MobSpawnerBase;
import net.minecraft.src.NoiseGeneratorOctaves2;
import net.minecraft.src.World;

public class WorldChunkManager {
    private NoiseGeneratorOctaves2 field_4194_e;
    private NoiseGeneratorOctaves2 field_4193_f;
    private NoiseGeneratorOctaves2 field_4192_g;
    public double[] temperature;
    public double[] humidity;
    public double[] field_4196_c;
    public MobSpawnerBase[] field_4195_d;

    protected WorldChunkManager() {
    }

    public WorldChunkManager(World var1) {
        this.field_4194_e = new NoiseGeneratorOctaves2(new Random(var1.getRandomSeed() * 9871L), 4);
        this.field_4193_f = new NoiseGeneratorOctaves2(new Random(var1.getRandomSeed() * 39811L), 4);
        this.field_4192_g = new NoiseGeneratorOctaves2(new Random(var1.getRandomSeed() * 543321L), 2);
    }

    public MobSpawnerBase func_4074_a(ChunkCoordIntPair var1) {
        return this.func_4073_a(var1.chunkXPos << 4, var1.chunkZPos << 4);
    }

    public MobSpawnerBase func_4073_a(int var1, int var2) {
        return this.func_4069_a(var1, var2, 1, 1)[0];
    }

    public double func_4072_b(int var1, int var2) {
        this.temperature = this.field_4194_e.func_4112_a(this.temperature, var1, var2, 1, 1, 0.025f, 0.025f, 0.5);
        return this.temperature[0];
    }

    public MobSpawnerBase[] func_4069_a(int var1, int var2, int var3, int var4) {
        this.field_4195_d = this.loadBlockGeneratorData(this.field_4195_d, var1, var2, var3, var4);
        return this.field_4195_d;
    }

    public double[] getTemperatures(double[] var1, int var2, int var3, int var4, int var5) {
        if (var1 == null || var1.length < var4 * var5) {
            var1 = new double[var4 * var5];
        }
        var1 = this.field_4194_e.func_4112_a(var1, var2, var3, var4, var5, 0.025f, 0.025f, 0.25);
        this.field_4196_c = this.field_4192_g.func_4112_a(this.field_4196_c, var2, var3, var4, var5, 0.25, 0.25, 0.5882352941176471);
        int var6 = 0;
        int var7 = 0;
        while (var7 < var4) {
            int var8 = 0;
            while (var8 < var5) {
                double var9 = this.field_4196_c[var6] * 1.1 + 0.5;
                double var11 = 0.01;
                double var13 = 1.0 - var11;
                double var15 = (var1[var6] * 0.15 + 0.7) * var13 + var9 * var11;
                if ((var15 = 1.0 - (1.0 - var15) * (1.0 - var15)) < 0.0) {
                    var15 = 0.0;
                }
                if (var15 > 1.0) {
                    var15 = 1.0;
                }
                var1[var6] = var15;
                ++var6;
                ++var8;
            }
            ++var7;
        }
        return var1;
    }

    public MobSpawnerBase[] loadBlockGeneratorData(MobSpawnerBase[] var1, int var2, int var3, int var4, int var5) {
        if (var1 == null || var1.length < var4 * var5) {
            var1 = new MobSpawnerBase[var4 * var5];
        }
        this.temperature = this.field_4194_e.func_4112_a(this.temperature, var2, var3, var4, var4, 0.025f, 0.025f, 0.25);
        this.humidity = this.field_4193_f.func_4112_a(this.humidity, var2, var3, var4, var4, 0.05f, 0.05f, 0.3333333333333333);
        this.field_4196_c = this.field_4192_g.func_4112_a(this.field_4196_c, var2, var3, var4, var4, 0.25, 0.25, 0.5882352941176471);
        int var6 = 0;
        int var7 = 0;
        while (var7 < var4) {
            int var8 = 0;
            while (var8 < var5) {
                double var9 = this.field_4196_c[var6] * 1.1 + 0.5;
                double var11 = 0.01;
                double var13 = 1.0 - var11;
                double var15 = (this.temperature[var6] * 0.15 + 0.7) * var13 + var9 * var11;
                var11 = 0.002;
                var13 = 1.0 - var11;
                double var17 = (this.humidity[var6] * 0.15 + 0.5) * var13 + var9 * var11;
                if ((var15 = 1.0 - (1.0 - var15) * (1.0 - var15)) < 0.0) {
                    var15 = 0.0;
                }
                if (var17 < 0.0) {
                    var17 = 0.0;
                }
                if (var15 > 1.0) {
                    var15 = 1.0;
                }
                if (var17 > 1.0) {
                    var17 = 1.0;
                }
                this.temperature[var6] = var15;
                this.humidity[var6] = var17;
                var1[var6++] = MobSpawnerBase.getBiomeFromLookup(var15, var17);
                ++var8;
            }
            ++var7;
        }
        return var1;
    }
}

