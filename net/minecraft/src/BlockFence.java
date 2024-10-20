package net.minecraft.src;

import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Block;
import net.minecraft.src.Material;
import net.minecraft.src.World;

public class BlockFence
extends Block {
    public BlockFence(int var1, int var2) {
        super(var1, var2, Material.wood);
    }

    @Override
    public boolean canPlaceBlockAt(World var1, int var2, int var3, int var4) {
        if (var1.getBlockId(var2, var3 - 1, var4) == this.blockID) {
            return false;
        }
        return !var1.getBlockMaterial(var2, var3 - 1, var4).isSolid() ? false : super.canPlaceBlockAt(var1, var2, var3, var4);
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World var1, int var2, int var3, int var4) {
        return AxisAlignedBB.getBoundingBoxFromPool(var2, var3, var4, var2 + 1, (float)var3 + 1.5f, var4 + 1);
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
        return 11;
    }
}

