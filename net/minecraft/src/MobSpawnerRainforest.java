package net.minecraft.src;

import java.util.Random;
import net.minecraft.src.MobSpawnerBase;
import net.minecraft.src.WorldGenBigTree;
import net.minecraft.src.WorldGenTrees;
import net.minecraft.src.WorldGenerator;

public class MobSpawnerRainforest
extends MobSpawnerBase {
    @Override
    public WorldGenerator getRandomWorldGenForTrees(Random var1) {
        return var1.nextInt(3) == 0 ? new WorldGenBigTree() : new WorldGenTrees();
    }
}

