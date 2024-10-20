/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.Random;
import net.minecraft.src.Block;
import net.minecraft.src.BlockFlower;
import net.minecraft.src.EntityItem;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;

public class BlockCrops
extends BlockFlower {
    protected BlockCrops(int var1, int var2) {
        super(var1, var2);
        this.blockIndexInTexture = var2;
        this.setTickOnLoad(true);
        float var3 = 0.5f;
        this.setBlockBounds(0.5f - var3, 0.0f, 0.5f - var3, 0.5f + var3, 0.25f, 0.5f + var3);
    }

    @Override
    protected boolean canThisPlantGrowOnThisBlockID(int var1) {
        return var1 == Block.tilledField.blockID;
    }

    @Override
    public void updateTick(World var1, int var2, int var3, int var4, Random var5) {
        float var7;
        int var6;
        super.updateTick(var1, var2, var3, var4, var5);
        if (var1.getBlockLightValue(var2, var3 + 1, var4) >= 9 && (var6 = var1.getBlockMetadata(var2, var3, var4)) < 7 && var5.nextInt((int)(100.0f / (var7 = this.getGrowthRate(var1, var2, var3, var4)))) == 0) {
            var1.setBlockMetadataWithNotify(var2, var3, var4, ++var6);
        }
    }

    public void fertilize(World var1, int var2, int var3, int var4) {
        var1.setBlockMetadataWithNotify(var2, var3, var4, 7);
    }

    private float getGrowthRate(World var1, int var2, int var3, int var4) {
        float var5 = 1.0f;
        int var6 = var1.getBlockId(var2, var3, var4 - 1);
        int var7 = var1.getBlockId(var2, var3, var4 + 1);
        int var8 = var1.getBlockId(var2 - 1, var3, var4);
        int var9 = var1.getBlockId(var2 + 1, var3, var4);
        int var10 = var1.getBlockId(var2 - 1, var3, var4 - 1);
        int var11 = var1.getBlockId(var2 + 1, var3, var4 - 1);
        int var12 = var1.getBlockId(var2 + 1, var3, var4 + 1);
        int var13 = var1.getBlockId(var2 - 1, var3, var4 + 1);
        boolean var14 = var8 == this.blockID || var9 == this.blockID;
        boolean var15 = var6 == this.blockID || var7 == this.blockID;
        boolean var16 = var10 == this.blockID || var11 == this.blockID || var12 == this.blockID || var13 == this.blockID;
        int var17 = var2 - 1;
        while (var17 <= var2 + 1) {
            int var18 = var4 - 1;
            while (var18 <= var4 + 1) {
                int var19 = var1.getBlockId(var17, var3 - 1, var18);
                float var20 = 0.0f;
                if (var19 == Block.tilledField.blockID) {
                    var20 = 1.0f;
                    if (var1.getBlockMetadata(var17, var3 - 1, var18) > 0) {
                        var20 = 3.0f;
                    }
                }
                if (var17 != var2 || var18 != var4) {
                    var20 /= 4.0f;
                }
                var5 += var20;
                ++var18;
            }
            ++var17;
        }
        if (var16 || var14 && var15) {
            var5 /= 2.0f;
        }
        return var5;
    }

    @Override
    public int getBlockTextureFromSideAndMetadata(int var1, int var2) {
        if (var2 < 0) {
            var2 = 7;
        }
        return this.blockIndexInTexture + var2;
    }

    @Override
    public int getRenderType() {
        return 6;
    }

    @Override
    public void onBlockDestroyedByPlayer(World var1, int var2, int var3, int var4, int var5) {
        super.onBlockDestroyedByPlayer(var1, var2, var3, var4, var5);
        if (!var1.multiplayerWorld) {
            int var6 = 0;
            while (var6 < 3) {
                if (var1.rand.nextInt(15) <= var5) {
                    float var7 = 0.7f;
                    float var8 = var1.rand.nextFloat() * var7 + (1.0f - var7) * 0.5f;
                    float var9 = var1.rand.nextFloat() * var7 + (1.0f - var7) * 0.5f;
                    float var10 = var1.rand.nextFloat() * var7 + (1.0f - var7) * 0.5f;
                    EntityItem var11 = new EntityItem(var1, (float)var2 + var8, (float)var3 + var9, (float)var4 + var10, new ItemStack(Item.seeds));
                    var11.delayBeforeCanPickup = 10;
                    var1.entityJoinedWorld(var11);
                }
                ++var6;
            }
        }
    }

    @Override
    public int idDropped(int var1, Random var2) {
        return var1 == 7 ? Item.wheat.shiftedIndex : -1;
    }

    @Override
    public int quantityDropped(Random var1) {
        return 1;
    }
}

