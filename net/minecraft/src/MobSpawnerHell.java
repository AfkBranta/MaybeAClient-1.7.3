/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.EntityGhast;
import net.minecraft.src.EntityPigZombie;
import net.minecraft.src.MobSpawnerBase;
import net.minecraft.src.SpawnListEntry;

public class MobSpawnerHell
extends MobSpawnerBase {
    public MobSpawnerHell() {
        this.spawnableMonsterList.clear();
        this.spawnableCreatureList.clear();
        this.spawnableWaterCreatureList.clear();
        this.spawnableMonsterList.add(new SpawnListEntry(EntityGhast.class, 10));
        this.spawnableMonsterList.add(new SpawnListEntry(EntityPigZombie.class, 10));
    }
}

