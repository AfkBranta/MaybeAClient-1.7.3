/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.Random;
import net.minecraft.src.Block;
import net.minecraft.src.BlockLeavesBase;
import net.minecraft.src.ColorizerFoliage;
import net.minecraft.src.Entity;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.Material;
import net.minecraft.src.World;

public class BlockLeaves
extends BlockLeavesBase {
    private int baseIndexInPNG;
    int[] adjacentTreeBlocks;

    protected BlockLeaves(int var1, int var2) {
        super(var1, var2, Material.leaves, false);
        this.baseIndexInPNG = var2;
        this.setTickOnLoad(true);
    }

    @Override
    public int colorMultiplier(IBlockAccess var1, int var2, int var3, int var4) {
        int var5 = var1.getBlockMetadata(var2, var3, var4);
        if ((var5 & 1) == 1) {
            return ColorizerFoliage.getFoliageColorPine();
        }
        if ((var5 & 2) == 2) {
            return ColorizerFoliage.getFoliageColorBirch();
        }
        var1.getWorldChunkManager().func_4069_a(var2, var4, 1, 1);
        double var6 = var1.getWorldChunkManager().temperature[0];
        double var8 = var1.getWorldChunkManager().humidity[0];
        return ColorizerFoliage.getFoliageColor(var6, var8);
    }

    @Override
    public void onBlockRemoval(World var1, int var2, int var3, int var4) {
        int var5 = 1;
        int var6 = var5 + 1;
        if (var1.checkChunksExist(var2 - var6, var3 - var6, var4 - var6, var2 + var6, var3 + var6, var4 + var6)) {
            int var7 = -var5;
            while (var7 <= var5) {
                int var8 = -var5;
                while (var8 <= var5) {
                    int var9 = -var5;
                    while (var9 <= var5) {
                        int var10 = var1.getBlockId(var2 + var7, var3 + var8, var4 + var9);
                        if (var10 == Block.leaves.blockID) {
                            int var11 = var1.getBlockMetadata(var2 + var7, var3 + var8, var4 + var9);
                            var1.setBlockMetadata(var2 + var7, var3 + var8, var4 + var9, var11 | 4);
                        }
                        ++var9;
                    }
                    ++var8;
                }
                ++var7;
            }
        }
    }

    @Override
    public void updateTick(World var1, int var2, int var3, int var4, Random var5) {
        int var6;
        if (!var1.multiplayerWorld && ((var6 = var1.getBlockMetadata(var2, var3, var4)) & 4) != 0) {
            int var12;
            int var7 = 4;
            int var8 = var7 + 1;
            int var9 = 32;
            int var10 = var9 * var9;
            int var11 = var9 / 2;
            if (this.adjacentTreeBlocks == null) {
                this.adjacentTreeBlocks = new int[var9 * var9 * var9];
            }
            if (var1.checkChunksExist(var2 - var8, var3 - var8, var4 - var8, var2 + var8, var3 + var8, var4 + var8)) {
                var12 = -var7;
                while (true) {
                    int var15;
                    int var14;
                    int var13;
                    if (var12 > var7) {
                        for (var12 = 1; var12 <= 4; ++var12) {
                            var13 = -var7;
                            while (var13 <= var7) {
                                var14 = -var7;
                                while (var14 <= var7) {
                                    var15 = -var7;
                                    while (var15 <= var7) {
                                        if (this.adjacentTreeBlocks[(var13 + var11) * var10 + (var14 + var11) * var9 + var15 + var11] == var12 - 1) {
                                            if (this.adjacentTreeBlocks[(var13 + var11 - 1) * var10 + (var14 + var11) * var9 + var15 + var11] == -2) {
                                                this.adjacentTreeBlocks[(var13 + var11 - 1) * var10 + (var14 + var11) * var9 + var15 + var11] = var12;
                                            }
                                            if (this.adjacentTreeBlocks[(var13 + var11 + 1) * var10 + (var14 + var11) * var9 + var15 + var11] == -2) {
                                                this.adjacentTreeBlocks[(var13 + var11 + 1) * var10 + (var14 + var11) * var9 + var15 + var11] = var12;
                                            }
                                            if (this.adjacentTreeBlocks[(var13 + var11) * var10 + (var14 + var11 - 1) * var9 + var15 + var11] == -2) {
                                                this.adjacentTreeBlocks[(var13 + var11) * var10 + (var14 + var11 - 1) * var9 + var15 + var11] = var12;
                                            }
                                            if (this.adjacentTreeBlocks[(var13 + var11) * var10 + (var14 + var11 + 1) * var9 + var15 + var11] == -2) {
                                                this.adjacentTreeBlocks[(var13 + var11) * var10 + (var14 + var11 + 1) * var9 + var15 + var11] = var12;
                                            }
                                            if (this.adjacentTreeBlocks[(var13 + var11) * var10 + (var14 + var11) * var9 + (var15 + var11 - 1)] == -2) {
                                                this.adjacentTreeBlocks[(var13 + var11) * var10 + (var14 + var11) * var9 + (var15 + var11 - 1)] = var12;
                                            }
                                            if (this.adjacentTreeBlocks[(var13 + var11) * var10 + (var14 + var11) * var9 + var15 + var11 + 1] == -2) {
                                                this.adjacentTreeBlocks[(var13 + var11) * var10 + (var14 + var11) * var9 + var15 + var11 + 1] = var12;
                                            }
                                        }
                                        ++var15;
                                    }
                                    ++var14;
                                }
                                ++var13;
                            }
                        }
                        break;
                    }
                    var13 = -var7;
                    while (var13 <= var7) {
                        var14 = -var7;
                        while (var14 <= var7) {
                            var15 = var1.getBlockId(var2 + var12, var3 + var13, var4 + var14);
                            this.adjacentTreeBlocks[(var12 + var11) * var10 + (var13 + var11) * var9 + var14 + var11] = var15 == Block.wood.blockID ? 0 : (var15 == Block.leaves.blockID ? -2 : -1);
                            ++var14;
                        }
                        ++var13;
                    }
                    ++var12;
                }
            }
            if ((var12 = this.adjacentTreeBlocks[var11 * var10 + var11 * var9 + var11]) >= 0) {
                var1.setBlockMetadataWithNotify(var2, var3, var4, var6 & 0xFFFFFFFB);
            } else {
                this.removeLeaves(var1, var2, var3, var4);
            }
        }
    }

    private void removeLeaves(World var1, int var2, int var3, int var4) {
        this.dropBlockAsItem(var1, var2, var3, var4, var1.getBlockMetadata(var2, var3, var4));
        var1.setBlockWithNotify(var2, var3, var4, 0);
    }

    @Override
    public int quantityDropped(Random var1) {
        return var1.nextInt(16) == 0 ? 1 : 0;
    }

    @Override
    public int idDropped(int var1, Random var2) {
        return Block.sapling.blockID;
    }

    @Override
    public boolean isOpaqueCube() {
        return !this.graphicsLevel;
    }

    @Override
    public int getBlockTextureFromSideAndMetadata(int var1, int var2) {
        return (var2 & 3) == 1 ? this.blockIndexInTexture + 80 : this.blockIndexInTexture;
    }

    public void setGraphicsLevel(boolean var1) {
        this.graphicsLevel = var1;
        this.blockIndexInTexture = this.baseIndexInPNG + (var1 ? 0 : 1);
    }

    @Override
    public void onEntityWalking(World var1, int var2, int var3, int var4, Entity var5) {
        super.onEntityWalking(var1, var2, var3, var4, var5);
    }
}

