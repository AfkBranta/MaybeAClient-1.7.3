package net.minecraft.src;

import java.util.Random;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Block;
import net.minecraft.src.Material;
import net.minecraft.src.World;

public class BlockLadder
extends Block {
    protected BlockLadder(int var1, int var2) {
        super(var1, var2, Material.circuits);
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World var1, int var2, int var3, int var4) {
        int var5 = var1.getBlockMetadata(var2, var3, var4);
        float var6 = 0.125f;
        if (var5 == 2) {
            this.setBlockBounds(0.0f, 0.0f, 1.0f - var6, 1.0f, 1.0f, 1.0f);
        }
        if (var5 == 3) {
            this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, var6);
        }
        if (var5 == 4) {
            this.setBlockBounds(1.0f - var6, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        }
        if (var5 == 5) {
            this.setBlockBounds(0.0f, 0.0f, 0.0f, var6, 1.0f, 1.0f);
        }
        return super.getCollisionBoundingBoxFromPool(var1, var2, var3, var4);
    }

    @Override
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World var1, int var2, int var3, int var4) {
        int var5 = var1.getBlockMetadata(var2, var3, var4);
        float var6 = 0.125f;
        if (var5 == 2) {
            this.setBlockBounds(0.0f, 0.0f, 1.0f - var6, 1.0f, 1.0f, 1.0f);
        }
        if (var5 == 3) {
            this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, var6);
        }
        if (var5 == 4) {
            this.setBlockBounds(1.0f - var6, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        }
        if (var5 == 5) {
            this.setBlockBounds(0.0f, 0.0f, 0.0f, var6, 1.0f, 1.0f);
        }
        return super.getSelectedBoundingBoxFromPool(var1, var2, var3, var4);
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
        return 8;
    }

    @Override
    public boolean canPlaceBlockAt(World var1, int var2, int var3, int var4) {
        if (var1.isBlockOpaqueCube(var2 - 1, var3, var4)) {
            return true;
        }
        if (var1.isBlockOpaqueCube(var2 + 1, var3, var4)) {
            return true;
        }
        if (var1.isBlockOpaqueCube(var2, var3, var4 - 1)) {
            return true;
        }
        return var1.isBlockOpaqueCube(var2, var3, var4 + 1);
    }

    @Override
    public void onBlockPlaced(World var1, int var2, int var3, int var4, int var5) {
        int var6 = var1.getBlockMetadata(var2, var3, var4);
        if ((var6 == 0 || var5 == 2) && var1.isBlockOpaqueCube(var2, var3, var4 + 1)) {
            var6 = 2;
        }
        if ((var6 == 0 || var5 == 3) && var1.isBlockOpaqueCube(var2, var3, var4 - 1)) {
            var6 = 3;
        }
        if ((var6 == 0 || var5 == 4) && var1.isBlockOpaqueCube(var2 + 1, var3, var4)) {
            var6 = 4;
        }
        if ((var6 == 0 || var5 == 5) && var1.isBlockOpaqueCube(var2 - 1, var3, var4)) {
            var6 = 5;
        }
        var1.setBlockMetadataWithNotify(var2, var3, var4, var6);
    }

    @Override
    public void onNeighborBlockChange(World var1, int var2, int var3, int var4, int var5) {
        int var6 = var1.getBlockMetadata(var2, var3, var4);
        boolean var7 = false;
        if (var6 == 2 && var1.isBlockOpaqueCube(var2, var3, var4 + 1)) {
            var7 = true;
        }
        if (var6 == 3 && var1.isBlockOpaqueCube(var2, var3, var4 - 1)) {
            var7 = true;
        }
        if (var6 == 4 && var1.isBlockOpaqueCube(var2 + 1, var3, var4)) {
            var7 = true;
        }
        if (var6 == 5 && var1.isBlockOpaqueCube(var2 - 1, var3, var4)) {
            var7 = true;
        }
        if (!var7) {
            this.dropBlockAsItem(var1, var2, var3, var4, var6);
            var1.setBlockWithNotify(var2, var3, var4, 0);
        }
        super.onNeighborBlockChange(var1, var2, var3, var4, var5);
    }

    @Override
    public int quantityDropped(Random var1) {
        return 1;
    }
}

