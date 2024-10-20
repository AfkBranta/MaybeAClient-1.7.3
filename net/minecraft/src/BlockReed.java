/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.Random;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Block;
import net.minecraft.src.Item;
import net.minecraft.src.Material;
import net.minecraft.src.World;

public class BlockReed
extends Block {
    protected BlockReed(int var1, int var2) {
        super(var1, Material.plants);
        this.blockIndexInTexture = var2;
        float var3 = 0.375f;
        this.setBlockBounds(0.5f - var3, 0.0f, 0.5f - var3, 0.5f + var3, 1.0f, 0.5f + var3);
        this.setTickOnLoad(true);
    }

    @Override
    public void updateTick(World var1, int var2, int var3, int var4, Random var5) {
        if (var1.isAirBlock(var2, var3 + 1, var4)) {
            int var6 = 1;
            while (var1.getBlockId(var2, var3 - var6, var4) == this.blockID) {
                ++var6;
            }
            if (var6 < 3) {
                int var7 = var1.getBlockMetadata(var2, var3, var4);
                if (var7 == 15) {
                    var1.setBlockWithNotify(var2, var3 + 1, var4, this.blockID);
                    var1.setBlockMetadataWithNotify(var2, var3, var4, 0);
                } else {
                    var1.setBlockMetadataWithNotify(var2, var3, var4, var7 + 1);
                }
            }
        }
    }

    @Override
    public boolean canPlaceBlockAt(World var1, int var2, int var3, int var4) {
        int var5 = var1.getBlockId(var2, var3 - 1, var4);
        if (var5 == this.blockID) {
            return true;
        }
        if (var5 != Block.grass.blockID && var5 != Block.dirt.blockID) {
            return false;
        }
        if (var1.getBlockMaterial(var2 - 1, var3 - 1, var4) == Material.water) {
            return true;
        }
        if (var1.getBlockMaterial(var2 + 1, var3 - 1, var4) == Material.water) {
            return true;
        }
        if (var1.getBlockMaterial(var2, var3 - 1, var4 - 1) == Material.water) {
            return true;
        }
        return var1.getBlockMaterial(var2, var3 - 1, var4 + 1) == Material.water;
    }

    @Override
    public void onNeighborBlockChange(World var1, int var2, int var3, int var4, int var5) {
        this.checkBlockCoordValid(var1, var2, var3, var4);
    }

    protected final void checkBlockCoordValid(World var1, int var2, int var3, int var4) {
        if (!this.canBlockStay(var1, var2, var3, var4)) {
            this.dropBlockAsItem(var1, var2, var3, var4, var1.getBlockMetadata(var2, var3, var4));
            var1.setBlockWithNotify(var2, var3, var4, 0);
        }
    }

    @Override
    public boolean canBlockStay(World var1, int var2, int var3, int var4) {
        return this.canPlaceBlockAt(var1, var2, var3, var4);
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World var1, int var2, int var3, int var4) {
        return null;
    }

    @Override
    public int idDropped(int var1, Random var2) {
        return Item.reed.shiftedIndex;
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
    public int getRenderType() {
        return 1;
    }
}

