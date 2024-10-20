/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.Random;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Block;
import net.minecraft.src.Entity;
import net.minecraft.src.Material;
import net.minecraft.src.World;

public class BlockSoil
extends Block {
    protected BlockSoil(int var1) {
        super(var1, Material.ground);
        this.blockIndexInTexture = 87;
        this.setTickOnLoad(true);
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.9375f, 1.0f);
        this.setLightOpacity(255);
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World var1, int var2, int var3, int var4) {
        return AxisAlignedBB.getBoundingBoxFromPool(var2 + 0, var3 + 0, var4 + 0, var2 + 1, var3 + 1, var4 + 1);
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public int getBlockTextureFromSideAndMetadata(int var1, int var2) {
        if (var1 == 1 && var2 > 0) {
            return this.blockIndexInTexture - 1;
        }
        return var1 == 1 ? this.blockIndexInTexture : 2;
    }

    @Override
    public void updateTick(World var1, int var2, int var3, int var4, Random var5) {
        if (var5.nextInt(5) == 0) {
            if (this.isWaterNearby(var1, var2, var3, var4)) {
                var1.setBlockMetadataWithNotify(var2, var3, var4, 7);
            } else {
                int var6 = var1.getBlockMetadata(var2, var3, var4);
                if (var6 > 0) {
                    var1.setBlockMetadataWithNotify(var2, var3, var4, var6 - 1);
                } else if (!this.isCropsNearby(var1, var2, var3, var4)) {
                    var1.setBlockWithNotify(var2, var3, var4, Block.dirt.blockID);
                }
            }
        }
    }

    @Override
    public void onEntityWalking(World var1, int var2, int var3, int var4, Entity var5) {
        if (var1.rand.nextInt(4) == 0) {
            var1.setBlockWithNotify(var2, var3, var4, Block.dirt.blockID);
        }
    }

    private boolean isCropsNearby(World var1, int var2, int var3, int var4) {
        int var5 = 0;
        int var6 = var2 - var5;
        while (var6 <= var2 + var5) {
            int var7 = var4 - var5;
            while (var7 <= var4 + var5) {
                if (var1.getBlockId(var6, var3 + 1, var7) == Block.crops.blockID) {
                    return true;
                }
                ++var7;
            }
            ++var6;
        }
        return false;
    }

    private boolean isWaterNearby(World var1, int var2, int var3, int var4) {
        int var5 = var2 - 4;
        while (var5 <= var2 + 4) {
            int var6 = var3;
            while (var6 <= var3 + 1) {
                int var7 = var4 - 4;
                while (var7 <= var4 + 4) {
                    if (var1.getBlockMaterial(var5, var6, var7) == Material.water) {
                        return true;
                    }
                    ++var7;
                }
                ++var6;
            }
            ++var5;
        }
        return false;
    }

    @Override
    public void onNeighborBlockChange(World var1, int var2, int var3, int var4, int var5) {
        super.onNeighborBlockChange(var1, var2, var3, var4, var5);
        Material var6 = var1.getBlockMaterial(var2, var3 + 1, var4);
        if (var6.isSolid()) {
            var1.setBlockWithNotify(var2, var3, var4, Block.dirt.blockID);
        }
    }

    @Override
    public int idDropped(int var1, Random var2) {
        return Block.dirt.idDropped(0, var2);
    }
}

