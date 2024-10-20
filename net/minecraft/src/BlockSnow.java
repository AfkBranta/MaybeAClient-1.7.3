/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.Random;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Block;
import net.minecraft.src.EntityItem;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EnumSkyBlock;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.StatList;
import net.minecraft.src.World;
import net.skidcode.gh.maybeaclient.hacks.XRayHack;

public class BlockSnow
extends Block {
    protected BlockSnow(int var1, int var2) {
        super(var1, var2, Material.snow);
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.125f, 1.0f);
        this.setTickOnLoad(true);
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World var1, int var2, int var3, int var4) {
        return null;
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
    public boolean canPlaceBlockAt(World var1, int var2, int var3, int var4) {
        int var5 = var1.getBlockId(var2, var3 - 1, var4);
        return var5 != 0 && Block.blocksList[var5].isOpaqueCube() ? var1.getBlockMaterial(var2, var3 - 1, var4).getIsSolid() : false;
    }

    @Override
    public void onNeighborBlockChange(World var1, int var2, int var3, int var4, int var5) {
        this.func_314_h(var1, var2, var3, var4);
    }

    private boolean func_314_h(World var1, int var2, int var3, int var4) {
        if (!this.canPlaceBlockAt(var1, var2, var3, var4)) {
            this.dropBlockAsItem(var1, var2, var3, var4, var1.getBlockMetadata(var2, var3, var4));
            var1.setBlockWithNotify(var2, var3, var4, 0);
            return false;
        }
        return true;
    }

    @Override
    public void harvestBlock(World var1, EntityPlayer var2, int var3, int var4, int var5, int var6) {
        int var7 = Item.snowball.shiftedIndex;
        float var8 = 0.7f;
        double var9 = (double)(var1.rand.nextFloat() * var8) + (double)(1.0f - var8) * 0.5;
        double var11 = (double)(var1.rand.nextFloat() * var8) + (double)(1.0f - var8) * 0.5;
        double var13 = (double)(var1.rand.nextFloat() * var8) + (double)(1.0f - var8) * 0.5;
        EntityItem var15 = new EntityItem(var1, (double)var3 + var9, (double)var4 + var11, (double)var5 + var13, new ItemStack(var7, 1, 0));
        var15.delayBeforeCanPickup = 10;
        var1.entityJoinedWorld(var15);
        var1.setBlockWithNotify(var3, var4, var5, 0);
        var2.addStat(StatList.field_25159_y[this.blockID], 1);
    }

    @Override
    public int idDropped(int var1, Random var2) {
        return Item.snowball.shiftedIndex;
    }

    @Override
    public int quantityDropped(Random var1) {
        return 0;
    }

    @Override
    public void updateTick(World var1, int var2, int var3, int var4, Random var5) {
        if (var1.getSavedLightValue(EnumSkyBlock.Block, var2, var3, var4) > 11) {
            this.dropBlockAsItem(var1, var2, var3, var4, var1.getBlockMetadata(var2, var3, var4));
            var1.setBlockWithNotify(var2, var3, var4, 0);
        }
    }

    @Override
    public boolean shouldSideBeRendered(IBlockAccess var1, int var2, int var3, int var4, int var5) {
        if (XRayHack.INSTANCE.status && !XRayHack.INSTANCE.mode.currentMode.equalsIgnoreCase("Opacity")) {
            return XRayHack.INSTANCE.blockChooser.blocks[this.blockID];
        }
        Material var6 = var1.getBlockMaterial(var2, var3, var4);
        if (var5 == 1) {
            return true;
        }
        return var6 == this.blockMaterial ? false : super.shouldSideBeRendered(var1, var2, var3, var4, var5);
    }
}

