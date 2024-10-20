/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.Random;
import net.minecraft.src.EntityWolf;
import net.minecraft.src.MobSpawnerBase;
import net.minecraft.src.SpawnListEntry;
import net.minecraft.src.WorldGenTaiga1;
import net.minecraft.src.WorldGenTaiga2;
import net.minecraft.src.WorldGenerator;

public class MobSpawnerTaiga
extends MobSpawnerBase {
    public MobSpawnerTaiga() {
        this.spawnableCreatureList.add(new SpawnListEntry(EntityWolf.class, 2));
    }

    @Override
    public WorldGenerator getRandomWorldGenForTrees(Random var1) {
        return var1.nextInt(3) == 0 ? new WorldGenTaiga1() : new WorldGenTaiga2();
    }
}

