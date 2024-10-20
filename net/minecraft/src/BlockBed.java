/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.Random;
import net.minecraft.src.Block;
import net.minecraft.src.ChunkCoordinates;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EnumStatus;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.Item;
import net.minecraft.src.Material;
import net.minecraft.src.ModelBed;
import net.minecraft.src.World;

public class BlockBed
extends Block {
    public static final int[][] headBlockToFootBlockMap;

    static {
        int[][] nArrayArray = new int[4][];
        int[] nArray = new int[2];
        nArray[1] = 1;
        nArrayArray[0] = nArray;
        int[] nArray2 = new int[2];
        nArray2[0] = -1;
        nArrayArray[1] = nArray2;
        int[] nArray3 = new int[2];
        nArray3[1] = -1;
        nArrayArray[2] = nArray3;
        int[] nArray4 = new int[2];
        nArray4[0] = 1;
        nArrayArray[3] = nArray4;
        headBlockToFootBlockMap = nArrayArray;
    }

    public BlockBed(int var1) {
        super(var1, 134, Material.cloth);
        this.setBounds();
    }

    @Override
    public boolean blockActivated(World var1, int var2, int var3, int var4, EntityPlayer var5) {
        int var6 = var1.getBlockMetadata(var2, var3, var4);
        if (!BlockBed.isBlockFootOfBed(var6)) {
            int var7 = BlockBed.getDirectionFromMetadata(var6);
            if (var1.getBlockId(var2 += headBlockToFootBlockMap[var7][0], var3, var4 += headBlockToFootBlockMap[var7][1]) != this.blockID) {
                return true;
            }
            var6 = var1.getBlockMetadata(var2, var3, var4);
        }
        if (BlockBed.isBedOccupied(var6)) {
            var5.addChatMessage("tile.bed.occupied");
            return true;
        }
        EnumStatus var8 = var5.sleepInBedAt(var2, var3, var4);
        if (var8 == EnumStatus.OK) {
            BlockBed.setBedOccupied(var1, var2, var3, var4, true);
            return true;
        }
        if (var8 == EnumStatus.NOT_POSSIBLE_NOW) {
            var5.addChatMessage("tile.bed.noSleep");
        }
        return true;
    }

    @Override
    public int getBlockTextureFromSideAndMetadata(int var1, int var2) {
        if (var1 == 0) {
            return Block.planks.blockIndexInTexture;
        }
        int var3 = BlockBed.getDirectionFromMetadata(var2);
        int var4 = ModelBed.bedDirection[var3][var1];
        if (BlockBed.isBlockFootOfBed(var2)) {
            if (var4 == 2) {
                return this.blockIndexInTexture + 2 + 16;
            }
            return var4 != 5 && var4 != 4 ? this.blockIndexInTexture + 1 : this.blockIndexInTexture + 1 + 16;
        }
        if (var4 == 3) {
            return this.blockIndexInTexture - 1 + 16;
        }
        return var4 != 5 && var4 != 4 ? this.blockIndexInTexture : this.blockIndexInTexture + 16;
    }

    @Override
    public int getRenderType() {
        return 14;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess var1, int var2, int var3, int var4) {
        this.setBounds();
    }

    @Override
    public void onNeighborBlockChange(World var1, int var2, int var3, int var4, int var5) {
        int var6 = var1.getBlockMetadata(var2, var3, var4);
        int var7 = BlockBed.getDirectionFromMetadata(var6);
        if (BlockBed.isBlockFootOfBed(var6)) {
            if (var1.getBlockId(var2 - headBlockToFootBlockMap[var7][0], var3, var4 - headBlockToFootBlockMap[var7][1]) != this.blockID) {
                var1.setBlockWithNotify(var2, var3, var4, 0);
            }
        } else if (var1.getBlockId(var2 + headBlockToFootBlockMap[var7][0], var3, var4 + headBlockToFootBlockMap[var7][1]) != this.blockID) {
            var1.setBlockWithNotify(var2, var3, var4, 0);
            if (!var1.multiplayerWorld) {
                this.dropBlockAsItem(var1, var2, var3, var4, var6);
            }
        }
    }

    @Override
    public int idDropped(int var1, Random var2) {
        return BlockBed.isBlockFootOfBed(var1) ? 0 : Item.bed.shiftedIndex;
    }

    private void setBounds() {
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.5625f, 1.0f);
    }

    public static int getDirectionFromMetadata(int var0) {
        return var0 & 3;
    }

    public static boolean isBlockFootOfBed(int var0) {
        return (var0 & 8) != 0;
    }

    public static boolean isBedOccupied(int var0) {
        return (var0 & 4) != 0;
    }

    public static void setBedOccupied(World var0, int var1, int var2, int var3, boolean var4) {
        int var5 = var0.getBlockMetadata(var1, var2, var3);
        var5 = var4 ? (var5 |= 4) : (var5 &= 0xFFFFFFFB);
        var0.setBlockMetadataWithNotify(var1, var2, var3, var5);
    }

    public static ChunkCoordinates getNearestEmptyChunkCoordinates(World var0, int var1, int var2, int var3, int var4) {
        int var5 = var0.getBlockMetadata(var1, var2, var3);
        int var6 = BlockBed.getDirectionFromMetadata(var5);
        int var7 = 0;
        while (var7 <= 1) {
            int var8 = var1 - headBlockToFootBlockMap[var6][0] * var7 - 1;
            int var9 = var3 - headBlockToFootBlockMap[var6][1] * var7 - 1;
            int var10 = var8 + 2;
            int var11 = var9 + 2;
            int var12 = var8;
            while (var12 <= var10) {
                int var13 = var9;
                while (var13 <= var11) {
                    if (var0.isBlockOpaqueCube(var12, var2 - 1, var13) && var0.isAirBlock(var12, var2, var13) && var0.isAirBlock(var12, var2 + 1, var13)) {
                        if (var4 <= 0) {
                            return new ChunkCoordinates(var12, var2, var13);
                        }
                        --var4;
                    }
                    ++var13;
                }
                ++var12;
            }
            ++var7;
        }
        return null;
    }
}

