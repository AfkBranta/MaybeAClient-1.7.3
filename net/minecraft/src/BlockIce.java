/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.Random;
import net.minecraft.src.Block;
import net.minecraft.src.BlockBreakable;
import net.minecraft.src.EnumSkyBlock;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.Material;
import net.minecraft.src.World;

public class BlockIce
extends BlockBreakable {
    public BlockIce(int var1, int var2) {
        super(var1, var2, Material.ice, false);
        this.slipperiness = 0.98f;
        this.setTickOnLoad(true);
    }

    @Override
    public int getRenderBlockPass() {
        return 1;
    }

    @Override
    public boolean shouldSideBeRendered(IBlockAccess var1, int var2, int var3, int var4, int var5) {
        return super.shouldSideBeRendered(var1, var2, var3, var4, 1 - var5);
    }

    @Override
    public void onBlockRemoval(World var1, int var2, int var3, int var4) {
        Material var5 = var1.getBlockMaterial(var2, var3 - 1, var4);
        if (var5.getIsSolid() || var5.getIsLiquid()) {
            var1.setBlockWithNotify(var2, var3, var4, Block.waterMoving.blockID);
        }
    }

    @Override
    public int quantityDropped(Random var1) {
        return 0;
    }

    @Override
    public void updateTick(World var1, int var2, int var3, int var4, Random var5) {
        if (var1.getSavedLightValue(EnumSkyBlock.Block, var2, var3, var4) > 11 - Block.lightOpacity[this.blockID]) {
            this.dropBlockAsItem(var1, var2, var3, var4, var1.getBlockMetadata(var2, var3, var4));
            var1.setBlockWithNotify(var2, var3, var4, Block.waterStill.blockID);
        }
    }
}

