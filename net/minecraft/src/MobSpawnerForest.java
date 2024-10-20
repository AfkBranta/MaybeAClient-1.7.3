/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.Random;
import net.minecraft.src.EntityWolf;
import net.minecraft.src.MobSpawnerBase;
import net.minecraft.src.SpawnListEntry;
import net.minecraft.src.WorldGenBigTree;
import net.minecraft.src.WorldGenForest;
import net.minecraft.src.WorldGenTrees;
import net.minecraft.src.WorldGenerator;

public class MobSpawnerForest
extends MobSpawnerBase {
    public MobSpawnerForest() {
        this.spawnableCreatureList.add(new SpawnListEntry(EntityWolf.class, 2));
    }

    @Override
    public WorldGenerator getRandomWorldGenForTrees(Random var1) {
        if (var1.nextInt(5) == 0) {
            return new WorldGenForest();
        }
        return var1.nextInt(3) == 0 ? new WorldGenBigTree() : new WorldGenTrees();
    }
}

