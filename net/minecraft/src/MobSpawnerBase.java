/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.minecraft.src.Block;
import net.minecraft.src.EntityChicken;
import net.minecraft.src.EntityCow;
import net.minecraft.src.EntityCreeper;
import net.minecraft.src.EntityPig;
import net.minecraft.src.EntitySheep;
import net.minecraft.src.EntitySkeleton;
import net.minecraft.src.EntitySlime;
import net.minecraft.src.EntitySpider;
import net.minecraft.src.EntitySquid;
import net.minecraft.src.EntityZombie;
import net.minecraft.src.EnumCreatureType;
import net.minecraft.src.MobSpawnerDesert;
import net.minecraft.src.MobSpawnerForest;
import net.minecraft.src.MobSpawnerHell;
import net.minecraft.src.MobSpawnerRainforest;
import net.minecraft.src.MobSpawnerSwamp;
import net.minecraft.src.MobSpawnerTaiga;
import net.minecraft.src.SpawnListEntry;
import net.minecraft.src.WorldGenBigTree;
import net.minecraft.src.WorldGenTrees;
import net.minecraft.src.WorldGenerator;

public class MobSpawnerBase {
    public static final MobSpawnerBase rainforest = new MobSpawnerRainforest().setColor(588342).setBiomeName("Rainforest").func_4124_a(2094168);
    public static final MobSpawnerBase swampland = new MobSpawnerSwamp().setColor(522674).setBiomeName("Swampland").func_4124_a(9154376);
    public static final MobSpawnerBase seasonalForest = new MobSpawnerBase().setColor(10215459).setBiomeName("Seasonal Forest");
    public static final MobSpawnerBase forest = new MobSpawnerForest().setColor(353825).setBiomeName("Forest").func_4124_a(5159473);
    public static final MobSpawnerBase savanna = new MobSpawnerDesert().setColor(14278691).setBiomeName("Savanna");
    public static final MobSpawnerBase shrubland = new MobSpawnerBase().setColor(10595616).setBiomeName("Shrubland");
    public static final MobSpawnerBase taiga = new MobSpawnerTaiga().setColor(3060051).setBiomeName("Taiga").doesNothingForMobSpawnerBase().func_4124_a(8107825);
    public static final MobSpawnerBase desert = new MobSpawnerDesert().setColor(16421912).setBiomeName("Desert");
    public static final MobSpawnerBase plains = new MobSpawnerDesert().setColor(16767248).setBiomeName("Plains");
    public static final MobSpawnerBase iceDesert = new MobSpawnerDesert().setColor(16772499).setBiomeName("Ice Desert").doesNothingForMobSpawnerBase().func_4124_a(12899129);
    public static final MobSpawnerBase tundra = new MobSpawnerBase().setColor(5762041).setBiomeName("Tundra").doesNothingForMobSpawnerBase().func_4124_a(12899129);
    public static final MobSpawnerBase hell = new MobSpawnerHell().setColor(0xFF0000).setBiomeName("Hell");
    public String biomeName;
    public int color;
    public byte topBlock;
    public byte fillerBlock;
    public int field_6502_q;
    protected List spawnableMonsterList;
    protected List spawnableCreatureList;
    protected List spawnableWaterCreatureList;
    private static MobSpawnerBase[] biomeLookupTable = new MobSpawnerBase[4096];

    static {
        MobSpawnerBase.generateBiomeLookup();
    }

    protected MobSpawnerBase() {
        this.topBlock = (byte)Block.grass.blockID;
        this.fillerBlock = (byte)Block.dirt.blockID;
        this.field_6502_q = 5169201;
        this.spawnableMonsterList = new ArrayList();
        this.spawnableCreatureList = new ArrayList();
        this.spawnableWaterCreatureList = new ArrayList();
        this.spawnableMonsterList.add(new SpawnListEntry(EntitySpider.class, 10));
        this.spawnableMonsterList.add(new SpawnListEntry(EntityZombie.class, 10));
        this.spawnableMonsterList.add(new SpawnListEntry(EntitySkeleton.class, 10));
        this.spawnableMonsterList.add(new SpawnListEntry(EntityCreeper.class, 10));
        this.spawnableMonsterList.add(new SpawnListEntry(EntitySlime.class, 10));
        this.spawnableCreatureList.add(new SpawnListEntry(EntitySheep.class, 12));
        this.spawnableCreatureList.add(new SpawnListEntry(EntityPig.class, 10));
        this.spawnableCreatureList.add(new SpawnListEntry(EntityChicken.class, 10));
        this.spawnableCreatureList.add(new SpawnListEntry(EntityCow.class, 8));
        this.spawnableWaterCreatureList.add(new SpawnListEntry(EntitySquid.class, 10));
    }

    public static void generateBiomeLookup() {
        int var0 = 0;
        while (var0 < 64) {
            int var1 = 0;
            while (var1 < 64) {
                MobSpawnerBase.biomeLookupTable[var0 + var1 * 64] = MobSpawnerBase.getBiome((float)var0 / 63.0f, (float)var1 / 63.0f);
                ++var1;
            }
            ++var0;
        }
        MobSpawnerBase.desert.topBlock = MobSpawnerBase.desert.fillerBlock = (byte)Block.sand.blockID;
        MobSpawnerBase.iceDesert.topBlock = MobSpawnerBase.iceDesert.fillerBlock = (byte)Block.sand.blockID;
    }

    public WorldGenerator getRandomWorldGenForTrees(Random var1) {
        return var1.nextInt(10) == 0 ? new WorldGenBigTree() : new WorldGenTrees();
    }

    protected MobSpawnerBase doesNothingForMobSpawnerBase() {
        return this;
    }

    protected MobSpawnerBase setBiomeName(String var1) {
        this.biomeName = var1;
        return this;
    }

    protected MobSpawnerBase func_4124_a(int var1) {
        this.field_6502_q = var1;
        return this;
    }

    protected MobSpawnerBase setColor(int var1) {
        this.color = var1;
        return this;
    }

    public static MobSpawnerBase getBiomeFromLookup(double var0, double var2) {
        int var4 = (int)(var0 * 63.0);
        int var5 = (int)(var2 * 63.0);
        return biomeLookupTable[var4 + var5 * 64];
    }

    public static MobSpawnerBase getBiome(float var0, float var1) {
        var1 *= var0;
        if (var0 < 0.1f) {
            return tundra;
        }
        if (var1 < 0.2f) {
            if (var0 < 0.5f) {
                return tundra;
            }
            return var0 < 0.95f ? savanna : desert;
        }
        if (var1 > 0.5f && var0 < 0.7f) {
            return swampland;
        }
        if (var0 < 0.5f) {
            return taiga;
        }
        if (var0 < 0.97f) {
            return var1 < 0.35f ? shrubland : forest;
        }
        if (var1 < 0.45f) {
            return plains;
        }
        return var1 < 0.9f ? seasonalForest : rainforest;
    }

    public int getSkyColorByTemp(float var1) {
        if ((var1 /= 3.0f) < -1.0f) {
            var1 = -1.0f;
        }
        if (var1 > 1.0f) {
            var1 = 1.0f;
        }
        return Color.getHSBColor(0.62222224f - var1 * 0.05f, 0.5f + var1 * 0.1f, 1.0f).getRGB();
    }

    public List getSpawnableList(EnumCreatureType var1) {
        if (var1 == EnumCreatureType.monster) {
            return this.spawnableMonsterList;
        }
        if (var1 == EnumCreatureType.creature) {
            return this.spawnableCreatureList;
        }
        return var1 == EnumCreatureType.waterCreature ? this.spawnableWaterCreatureList : null;
    }
}

