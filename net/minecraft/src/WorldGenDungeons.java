package net.minecraft.src;

import java.util.Random;
import net.minecraft.src.Block;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.TileEntityChest;
import net.minecraft.src.TileEntityMobSpawner;
import net.minecraft.src.World;
import net.minecraft.src.WorldGenerator;

public class WorldGenDungeons
extends WorldGenerator {
    @Override
    public boolean generate(World var1, Random var2, int var3, int var4, int var5) {
        int var12;
        int var11;
        int var6 = 3;
        int var7 = var2.nextInt(2) + 2;
        int var8 = var2.nextInt(2) + 2;
        int var9 = 0;
        int var10 = var3 - var7 - 1;
        while (var10 <= var3 + var7 + 1) {
            var11 = var4 - 1;
            while (var11 <= var4 + var6 + 1) {
                var12 = var5 - var8 - 1;
                while (var12 <= var5 + var8 + 1) {
                    Material var13 = var1.getBlockMaterial(var10, var11, var12);
                    if (var11 == var4 - 1 && !var13.isSolid()) {
                        return false;
                    }
                    if (var11 == var4 + var6 + 1 && !var13.isSolid()) {
                        return false;
                    }
                    if ((var10 == var3 - var7 - 1 || var10 == var3 + var7 + 1 || var12 == var5 - var8 - 1 || var12 == var5 + var8 + 1) && var11 == var4 && var1.isAirBlock(var10, var11, var12) && var1.isAirBlock(var10, var11 + 1, var12)) {
                        ++var9;
                    }
                    ++var12;
                }
                ++var11;
            }
            ++var10;
        }
        if (var9 >= 1 && var9 <= 5) {
            var10 = var3 - var7 - 1;
            while (var10 <= var3 + var7 + 1) {
                var11 = var4 + var6;
                while (var11 >= var4 - 1) {
                    var12 = var5 - var8 - 1;
                    while (var12 <= var5 + var8 + 1) {
                        if (var10 != var3 - var7 - 1 && var11 != var4 - 1 && var12 != var5 - var8 - 1 && var10 != var3 + var7 + 1 && var11 != var4 + var6 + 1 && var12 != var5 + var8 + 1) {
                            var1.setBlockWithNotify(var10, var11, var12, 0);
                        } else if (var11 >= 0 && !var1.getBlockMaterial(var10, var11 - 1, var12).isSolid()) {
                            var1.setBlockWithNotify(var10, var11, var12, 0);
                        } else if (var1.getBlockMaterial(var10, var11, var12).isSolid()) {
                            if (var11 == var4 - 1 && var2.nextInt(4) != 0) {
                                var1.setBlockWithNotify(var10, var11, var12, Block.cobblestoneMossy.blockID);
                            } else {
                                var1.setBlockWithNotify(var10, var11, var12, Block.cobblestone.blockID);
                            }
                        }
                        ++var12;
                    }
                    --var11;
                }
                ++var10;
            }
            var10 = 0;
            while (var10 < 2) {
                var11 = 0;
                while (var11 < 3) {
                    int var14;
                    var12 = var3 + var2.nextInt(var7 * 2 + 1) - var7;
                    if (var1.isAirBlock(var12, var4, var14 = var5 + var2.nextInt(var8 * 2 + 1) - var8)) {
                        int var15 = 0;
                        if (var1.getBlockMaterial(var12 - 1, var4, var14).isSolid()) {
                            ++var15;
                        }
                        if (var1.getBlockMaterial(var12 + 1, var4, var14).isSolid()) {
                            ++var15;
                        }
                        if (var1.getBlockMaterial(var12, var4, var14 - 1).isSolid()) {
                            ++var15;
                        }
                        if (var1.getBlockMaterial(var12, var4, var14 + 1).isSolid()) {
                            ++var15;
                        }
                        if (var15 == 1) {
                            var1.setBlockWithNotify(var12, var4, var14, Block.crate.blockID);
                            TileEntityChest var16 = (TileEntityChest)var1.getBlockTileEntity(var12, var4, var14);
                            for (int var17 = 0; var17 < 8; ++var17) {
                                ItemStack var18 = this.pickCheckLootItem(var2);
                                if (var18 == null) continue;
                                var16.setInventorySlotContents(var2.nextInt(var16.getSizeInventory()), var18);
                            }
                            break;
                        }
                    }
                    ++var11;
                }
                ++var10;
            }
            var1.setBlockWithNotify(var3, var4, var5, Block.mobSpawner.blockID);
            TileEntityMobSpawner var19 = (TileEntityMobSpawner)var1.getBlockTileEntity(var3, var4, var5);
            var19.setMobID(this.pickMobSpawner(var2));
            return true;
        }
        return false;
    }

    private ItemStack pickCheckLootItem(Random var1) {
        int var2 = var1.nextInt(11);
        if (var2 == 0) {
            return new ItemStack(Item.saddle);
        }
        if (var2 == 1) {
            return new ItemStack(Item.ingotIron, var1.nextInt(4) + 1);
        }
        if (var2 == 2) {
            return new ItemStack(Item.bread);
        }
        if (var2 == 3) {
            return new ItemStack(Item.wheat, var1.nextInt(4) + 1);
        }
        if (var2 == 4) {
            return new ItemStack(Item.gunpowder, var1.nextInt(4) + 1);
        }
        if (var2 == 5) {
            return new ItemStack(Item.silk, var1.nextInt(4) + 1);
        }
        if (var2 == 6) {
            return new ItemStack(Item.bucketEmpty);
        }
        if (var2 == 7 && var1.nextInt(100) == 0) {
            return new ItemStack(Item.appleGold);
        }
        if (var2 == 8 && var1.nextInt(2) == 0) {
            return new ItemStack(Item.redstone, var1.nextInt(4) + 1);
        }
        if (var2 == 9 && var1.nextInt(10) == 0) {
            return new ItemStack(Item.itemsList[Item.record13.shiftedIndex + var1.nextInt(2)]);
        }
        return var2 == 10 ? new ItemStack(Item.dyePowder, 1, 3) : null;
    }

    private String pickMobSpawner(Random var1) {
        int var2 = var1.nextInt(4);
        if (var2 == 0) {
            return "Skeleton";
        }
        if (var2 == 1) {
            return "Zombie";
        }
        if (var2 == 2) {
            return "Zombie";
        }
        return var2 == 3 ? "Spider" : "";
    }
}

