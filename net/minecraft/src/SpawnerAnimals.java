package net.minecraft.src;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import net.minecraft.src.BlockBed;
import net.minecraft.src.ChunkCoordIntPair;
import net.minecraft.src.ChunkCoordinates;
import net.minecraft.src.ChunkPosition;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntitySheep;
import net.minecraft.src.EntitySkeleton;
import net.minecraft.src.EntitySpider;
import net.minecraft.src.EntityZombie;
import net.minecraft.src.EnumCreatureType;
import net.minecraft.src.Material;
import net.minecraft.src.MathHelper;
import net.minecraft.src.MobSpawnerBase;
import net.minecraft.src.Pathfinder;
import net.minecraft.src.SpawnListEntry;
import net.minecraft.src.World;

public final class SpawnerAnimals {
    private static Set eligibleChunksForSpawning = new HashSet();
    protected static final Class[] nightSpawnEntities = new Class[]{EntitySpider.class, EntityZombie.class, EntitySkeleton.class};

    protected static ChunkPosition getRandomSpawningPointInChunk(World var0, int var1, int var2) {
        int var3 = var1 + var0.rand.nextInt(16);
        int var4 = var0.rand.nextInt(128);
        int var5 = var2 + var0.rand.nextInt(16);
        return new ChunkPosition(var3, var4, var5);
    }

    public static final int performSpawning(World var0, boolean var1, boolean var2) {
        int var6;
        if (!var1 && !var2) {
            return 0;
        }
        eligibleChunksForSpawning.clear();
        int var3 = 0;
        while (var3 < var0.playerEntities.size()) {
            EntityPlayer var4 = (EntityPlayer)var0.playerEntities.get(var3);
            int var5 = MathHelper.floor_double(var4.posX / 16.0);
            var6 = MathHelper.floor_double(var4.posZ / 16.0);
            int var7 = 8;
            int var8 = -var7;
            while (var8 <= var7) {
                int var9 = -var7;
                while (var9 <= var7) {
                    eligibleChunksForSpawning.add(new ChunkCoordIntPair(var8 + var5, var9 + var6));
                    ++var9;
                }
                ++var8;
            }
            ++var3;
        }
        var3 = 0;
        ChunkCoordinates var35 = var0.getSpawnPoint();
        EnumCreatureType[] var36 = EnumCreatureType.values();
        var6 = var36.length;
        int var37 = 0;
        while (var37 < var6) {
            EnumCreatureType var38 = var36[var37];
            if ((!var38.getPeacefulCreature() || var2) && (var38.getPeacefulCreature() || var1) && var0.countEntities(var38.getCreatureClass()) <= var38.getMaxNumberOfCreature() * eligibleChunksForSpawning.size() / 256) {
                block6: for (ChunkCoordIntPair var10 : eligibleChunksForSpawning) {
                    SpawnListEntry var152;
                    MobSpawnerBase var11 = var0.getWorldChunkManager().func_4074_a(var10);
                    List var12 = var11.getSpawnableList(var38);
                    if (var12 == null || var12.isEmpty()) continue;
                    int var13 = 0;
                    for (SpawnListEntry var152 : var12) {
                        var13 += var152.field_25211_b;
                    }
                    int var40 = var0.rand.nextInt(var13);
                    var152 = (SpawnListEntry)var12.get(0);
                    for (SpawnListEntry var17 : var12) {
                        if ((var40 -= var17.field_25211_b) >= 0) continue;
                        var152 = var17;
                        break;
                    }
                    ChunkPosition var41 = SpawnerAnimals.getRandomSpawningPointInChunk(var0, var10.chunkXPos * 16, var10.chunkZPos * 16);
                    int var42 = var41.x;
                    int var18 = var41.y;
                    int var19 = var41.z;
                    if (var0.isBlockOpaqueCube(var42, var18, var19) || var0.getBlockMaterial(var42, var18, var19) != var38.getCreatureMaterial()) continue;
                    int var20 = 0;
                    int var21 = 0;
                    while (var21 < 3) {
                        int var22 = var42;
                        int var23 = var18;
                        int var24 = var19;
                        int var25 = 6;
                        int var26 = 0;
                        while (var26 < 4) {
                            float var32;
                            float var31;
                            float var30;
                            float var33;
                            float var29;
                            float var28;
                            float var27;
                            if (SpawnerAnimals.canCreatureTypeSpawnAtLocation(var38, var0, var22 += var0.rand.nextInt(var25) - var0.rand.nextInt(var25), var23 += var0.rand.nextInt(1) - var0.rand.nextInt(1), var24 += var0.rand.nextInt(var25) - var0.rand.nextInt(var25)) && var0.getClosestPlayer(var27 = (float)var22 + 0.5f, var28 = (float)var23, var29 = (float)var24 + 0.5f, 24.0) == null && (var33 = (var30 = var27 - (float)var35.x) * var30 + (var31 = var28 - (float)var35.y) * var31 + (var32 = var29 - (float)var35.z) * var32) >= 576.0f) {
                                EntityLiving var43;
                                try {
                                    var43 = (EntityLiving)var152.field_25212_a.getConstructor(World.class).newInstance(var0);
                                }
                                catch (Exception var34) {
                                    var34.printStackTrace();
                                    return var3;
                                }
                                var43.setLocationAndAngles(var27, var28, var29, var0.rand.nextFloat() * 360.0f, 0.0f);
                                if (var43.getCanSpawnHere()) {
                                    var0.entityJoinedWorld(var43);
                                    SpawnerAnimals.creatureSpecificInit(var43, var0, var27, var28, var29);
                                    if (++var20 >= var43.getMaxSpawnedInChunk()) continue block6;
                                }
                                var3 += var20;
                            }
                            ++var26;
                        }
                        ++var21;
                    }
                }
            }
            ++var37;
        }
        return var3;
    }

    private static boolean canCreatureTypeSpawnAtLocation(EnumCreatureType var0, World var1, int var2, int var3, int var4) {
        if (var0.getCreatureMaterial() == Material.water) {
            return var1.getBlockMaterial(var2, var3, var4).getIsLiquid() && !var1.isBlockOpaqueCube(var2, var3 + 1, var4);
        }
        return var1.isBlockOpaqueCube(var2, var3 - 1, var4) && !var1.isBlockOpaqueCube(var2, var3, var4) && !var1.getBlockMaterial(var2, var3, var4).getIsLiquid() && !var1.isBlockOpaqueCube(var2, var3 + 1, var4);
    }

    private static void creatureSpecificInit(EntityLiving var0, World var1, float var2, float var3, float var4) {
        if (var0 instanceof EntitySpider && var1.rand.nextInt(100) == 0) {
            EntitySkeleton var5 = new EntitySkeleton(var1);
            var5.setLocationAndAngles(var2, var3, var4, var0.rotationYaw, 0.0f);
            var1.entityJoinedWorld(var5);
            var5.mountEntity(var0);
        } else if (var0 instanceof EntitySheep) {
            ((EntitySheep)var0).setFleeceColor(EntitySheep.getRandomFleeceColor(var1.rand));
        }
    }

    /*
     * Unable to fully structure code
     */
    public static boolean performSleepSpawning(World var0, List var1) {
        var2 = false;
        var3 = new Pathfinder(var0);
        var4 = var1.iterator();
        block2: while (true) {
            if (!var4.hasNext()) {
                return var2;
            }
            var5 = (EntityPlayer)var4.next();
            var6 = SpawnerAnimals.nightSpawnEntities;
            if (var6 == null || var6.length == 0) continue;
            var7 = false;
            var8 = 0;
            while (true) {
                if (var8 < 20 && !var7) ** break;
                continue block2;
                var9 = MathHelper.floor_double(var5.posX) + var0.rand.nextInt(32) - var0.rand.nextInt(32);
                var10 = MathHelper.floor_double(var5.posZ) + var0.rand.nextInt(32) - var0.rand.nextInt(32);
                var11 = MathHelper.floor_double(var5.posY) + var0.rand.nextInt(16) - var0.rand.nextInt(16);
                if (var11 < 1) {
                    var11 = 1;
                } else if (var11 > 128) {
                    var11 = 128;
                }
                var12 = var0.rand.nextInt(var6.length);
                var13 = var11;
                while (var13 > 2 && !var0.isBlockOpaqueCube(var9, var13 - 1, var10)) {
                    --var13;
                }
                while (!SpawnerAnimals.canCreatureTypeSpawnAtLocation(EnumCreatureType.monster, var0, var9, var13, var10) && var13 < var11 + 16 && var13 < 128) {
                    ++var13;
                }
                if (var13 < var11 + 16 && var13 < 128) {
                    var14 = (float)var9 + 0.5f;
                    var15 = var13;
                    var16 = (float)var10 + 0.5f;
                    try {
                        var17 = (EntityLiving)var6[var12].getConstructor(new Class[]{World.class}).newInstance(new Object[]{var0});
                    }
                    catch (Exception var21) {
                        var21.printStackTrace();
                        return var2;
                    }
                    var17.setLocationAndAngles(var14, var15, var16, var0.rand.nextFloat() * 360.0f, 0.0f);
                    if (var17.getCanSpawnHere() && (var18 = var3.createEntityPathTo(var17, var5, 32.0f)) != null && var18.pathLength > 1) {
                        var19 = var18.func_22328_c();
                        if (Math.abs((double)var19.xCoord - var5.posX) < 1.5 && Math.abs((double)var19.zCoord - var5.posZ) < 1.5 && Math.abs((double)var19.yCoord - var5.posY) < 1.5) {
                            var20 = BlockBed.getNearestEmptyChunkCoordinates(var0, MathHelper.floor_double(var5.posX), MathHelper.floor_double(var5.posY), MathHelper.floor_double(var5.posZ), 1);
                            if (var20 == null) {
                                var20 = new ChunkCoordinates(var9, var13 + 1, var10);
                            }
                            var17.setLocationAndAngles((float)var20.x + 0.5f, var20.y, (float)var20.z + 0.5f, 0.0f, 0.0f);
                            var0.entityJoinedWorld(var17);
                            SpawnerAnimals.creatureSpecificInit(var17, var0, (float)var20.x + 0.5f, var20.y, (float)var20.z + 0.5f);
                            var5.wakeUpPlayer(true, false, false);
                            var17.playLivingSound();
                            var2 = true;
                            var7 = true;
                        }
                    }
                }
                ++var8;
            }
            break;
        }
    }
}

