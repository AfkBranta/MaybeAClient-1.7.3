package net.minecraft.src;

import net.minecraft.src.Block;
import net.minecraft.src.ChunkProviderGenerate;
import net.minecraft.src.IChunkProvider;
import net.minecraft.src.MathHelper;
import net.minecraft.src.Vec3D;
import net.minecraft.src.World;
import net.minecraft.src.WorldChunkManager;
import net.minecraft.src.WorldProviderHell;

public class WorldProvider {
    public World worldObj;
    public WorldChunkManager worldChunkMgr;
    public boolean field_4220_c = false;
    public boolean isHellWorld = false;
    public boolean field_6478_e = false;
    public float[] lightBrightnessTable = new float[16];
    public int worldType = 0;
    private float[] colorsSunriseSunset = new float[4];

    public final void registerWorld(World var1) {
        this.worldObj = var1;
        this.registerWorldChunkManager();
        this.generateLightBrightnessTable();
    }

    protected void generateLightBrightnessTable() {
        float var1 = 0.05f;
        int var2 = 0;
        while (var2 <= 15) {
            float var3 = 1.0f - (float)var2 / 15.0f;
            this.lightBrightnessTable[var2] = (1.0f - var3) / (var3 * 3.0f + 1.0f) * (1.0f - var1) + var1;
            ++var2;
        }
    }

    protected void registerWorldChunkManager() {
        this.worldChunkMgr = new WorldChunkManager(this.worldObj);
    }

    public IChunkProvider getChunkProvider() {
        return new ChunkProviderGenerate(this.worldObj, this.worldObj.getRandomSeed());
    }

    public boolean canCoordinateBeSpawn(int var1, int var2) {
        int var3 = this.worldObj.getFirstUncoveredBlock(var1, var2);
        return var3 == Block.sand.blockID;
    }

    public float calculateCelestialAngle(long var1, float var3) {
        int var4 = (int)(var1 % 24000L);
        float var5 = ((float)var4 + var3) / 24000.0f - 0.25f;
        if (var5 < 0.0f) {
            var5 += 1.0f;
        }
        if (var5 > 1.0f) {
            var5 -= 1.0f;
        }
        float var6 = var5;
        var5 = 1.0f - (float)((Math.cos((double)var5 * Math.PI) + 1.0) / 2.0);
        var5 = var6 + (var5 - var6) / 3.0f;
        return var5;
    }

    public float[] calcSunriseSunsetColors(float var1, float var2) {
        float var5;
        float var3 = 0.4f;
        float var4 = MathHelper.cos(var1 * (float)Math.PI * 2.0f) - 0.0f;
        if (var4 >= (var5 = -0.0f) - var3 && var4 <= var5 + var3) {
            float var6 = (var4 - var5) / var3 * 0.5f + 0.5f;
            float var7 = 1.0f - (1.0f - MathHelper.sin(var6 * (float)Math.PI)) * 0.99f;
            var7 *= var7;
            this.colorsSunriseSunset[0] = var6 * 0.3f + 0.7f;
            this.colorsSunriseSunset[1] = var6 * var6 * 0.7f + 0.2f;
            this.colorsSunriseSunset[2] = var6 * var6 * 0.0f + 0.2f;
            this.colorsSunriseSunset[3] = var7;
            return this.colorsSunriseSunset;
        }
        return null;
    }

    public Vec3D func_4096_a(float var1, float var2) {
        float var3 = MathHelper.cos(var1 * (float)Math.PI * 2.0f) * 2.0f + 0.5f;
        if (var3 < 0.0f) {
            var3 = 0.0f;
        }
        if (var3 > 1.0f) {
            var3 = 1.0f;
        }
        float var4 = 0.7529412f;
        float var5 = 0.84705883f;
        float var6 = 1.0f;
        return Vec3D.createVector(var4 *= var3 * 0.94f + 0.06f, var5 *= var3 * 0.94f + 0.06f, var6 *= var3 * 0.91f + 0.09f);
    }

    public boolean canRespawnHere() {
        return true;
    }

    public static WorldProvider func_4101_a(int var0) {
        if (var0 == 0) {
            return new WorldProvider();
        }
        return var0 == -1 ? new WorldProviderHell() : null;
    }
}

