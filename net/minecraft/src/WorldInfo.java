/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.List;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.NBTTagCompound;

public class WorldInfo {
    private long randomSeed;
    private int spawnX;
    private int spawnY;
    private int spawnZ;
    private long worldTime;
    private long lastTimePlayed;
    private long sizeOnDisk;
    private NBTTagCompound playerTag;
    private int dimension;
    private String levelName;
    private int saveVersion;

    public WorldInfo(NBTTagCompound var1) {
        this.randomSeed = var1.getLong("RandomSeed");
        this.spawnX = var1.getInteger("SpawnX");
        this.spawnY = var1.getInteger("SpawnY");
        this.spawnZ = var1.getInteger("SpawnZ");
        this.worldTime = var1.getLong("Time");
        this.lastTimePlayed = var1.getLong("LastPlayed");
        this.sizeOnDisk = var1.getLong("SizeOnDisk");
        this.levelName = var1.getString("LevelName");
        this.saveVersion = var1.getInteger("version");
        if (var1.hasKey("Player")) {
            this.playerTag = var1.getCompoundTag("Player");
            this.dimension = this.playerTag.getInteger("Dimension");
        }
    }

    public WorldInfo(long var1, String var3) {
        this.randomSeed = var1;
        this.levelName = var3;
    }

    public WorldInfo(WorldInfo var1) {
        this.randomSeed = var1.randomSeed;
        this.spawnX = var1.spawnX;
        this.spawnY = var1.spawnY;
        this.spawnZ = var1.spawnZ;
        this.worldTime = var1.worldTime;
        this.lastTimePlayed = var1.lastTimePlayed;
        this.sizeOnDisk = var1.sizeOnDisk;
        this.playerTag = var1.playerTag;
        this.dimension = var1.dimension;
        this.levelName = var1.levelName;
        this.saveVersion = var1.saveVersion;
    }

    public NBTTagCompound getNBTTagCompound() {
        NBTTagCompound var1 = new NBTTagCompound();
        this.updateTagCompound(var1, this.playerTag);
        return var1;
    }

    public NBTTagCompound getNBTTagCompoundWithPlayer(List var1) {
        NBTTagCompound var2 = new NBTTagCompound();
        EntityPlayer var3 = null;
        NBTTagCompound var4 = null;
        if (var1.size() > 0) {
            var3 = (EntityPlayer)var1.get(0);
        }
        if (var3 != null) {
            var4 = new NBTTagCompound();
            var3.writeToNBT(var4);
        }
        this.updateTagCompound(var2, var4);
        return var2;
    }

    private void updateTagCompound(NBTTagCompound var1, NBTTagCompound var2) {
        var1.setLong("RandomSeed", this.randomSeed);
        var1.setInteger("SpawnX", this.spawnX);
        var1.setInteger("SpawnY", this.spawnY);
        var1.setInteger("SpawnZ", this.spawnZ);
        var1.setLong("Time", this.worldTime);
        var1.setLong("SizeOnDisk", this.sizeOnDisk);
        var1.setLong("LastPlayed", System.currentTimeMillis());
        var1.setString("LevelName", this.levelName);
        var1.setInteger("version", this.saveVersion);
        if (var2 != null) {
            var1.setCompoundTag("Player", var2);
        }
    }

    public long getRandomSeed() {
        return this.randomSeed;
    }

    public int getSpawnX() {
        return this.spawnX;
    }

    public int getSpawnY() {
        return this.spawnY;
    }

    public int getSpawnZ() {
        return this.spawnZ;
    }

    public long getWorldTime() {
        return this.worldTime;
    }

    public long getSizeOnDisk() {
        return this.sizeOnDisk;
    }

    public NBTTagCompound getPlayerNBTTagCompound() {
        return this.playerTag;
    }

    public int getDimension() {
        return this.dimension;
    }

    public void setSpawnX(int var1) {
        this.spawnX = var1;
    }

    public void setSpawnY(int var1) {
        this.spawnY = var1;
    }

    public void setSpawnZ(int var1) {
        this.spawnZ = var1;
    }

    public void setWorldTime(long var1) {
        this.worldTime = var1;
    }

    public void setSizeOnDisk(long var1) {
        this.sizeOnDisk = var1;
    }

    public void setPlayerNBTTagCompound(NBTTagCompound var1) {
        this.playerTag = var1;
    }

    public void setSpawn(int var1, int var2, int var3) {
        this.spawnX = var1;
        this.spawnY = var2;
        this.spawnZ = var3;
    }

    public String getWorldName() {
        return this.levelName;
    }

    public void setWorldName(String var1) {
        this.levelName = var1;
    }

    public int getSaveVersion() {
        return this.saveVersion;
    }

    public void setSaveVersion(int var1) {
        this.saveVersion = var1;
    }

    public long getLastTimePlayed() {
        return this.lastTimePlayed;
    }
}

