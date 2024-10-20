/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.Block;
import net.minecraft.src.ChunkProviderHell;
import net.minecraft.src.IChunkProvider;
import net.minecraft.src.MobSpawnerBase;
import net.minecraft.src.Vec3D;
import net.minecraft.src.WorldChunkManagerHell;
import net.minecraft.src.WorldProvider;

public class WorldProviderHell
extends WorldProvider {
    @Override
    public void registerWorldChunkManager() {
        this.worldChunkMgr = new WorldChunkManagerHell(MobSpawnerBase.hell, 1.0, 0.0);
        this.field_4220_c = true;
        this.isHellWorld = true;
        this.field_6478_e = true;
        this.worldType = -1;
    }

    @Override
    public Vec3D func_4096_a(float var1, float var2) {
        return Vec3D.createVector(0.2f, 0.03f, 0.03f);
    }

    @Override
    protected void generateLightBrightnessTable() {
        float var1 = 0.1f;
        int var2 = 0;
        while (var2 <= 15) {
            float var3 = 1.0f - (float)var2 / 15.0f;
            this.lightBrightnessTable[var2] = (1.0f - var3) / (var3 * 3.0f + 1.0f) * (1.0f - var1) + var1;
            ++var2;
        }
    }

    @Override
    public IChunkProvider getChunkProvider() {
        return new ChunkProviderHell(this.worldObj, this.worldObj.getRandomSeed());
    }

    @Override
    public boolean canCoordinateBeSpawn(int var1, int var2) {
        int var3 = this.worldObj.getFirstUncoveredBlock(var1, var2);
        if (var3 == Block.bedrock.blockID) {
            return false;
        }
        if (var3 == 0) {
            return false;
        }
        return Block.opaqueCubeLookup[var3];
    }

    @Override
    public float calculateCelestialAngle(long var1, float var3) {
        return 0.5f;
    }

    @Override
    public boolean canRespawnHere() {
        return false;
    }
}

