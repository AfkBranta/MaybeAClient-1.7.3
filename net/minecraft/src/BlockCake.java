/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.Random;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Block;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.Material;
import net.minecraft.src.World;

public class BlockCake
extends Block {
    protected BlockCake(int var1, int var2) {
        super(var1, var2, Material.cakeMaterial);
        this.setTickOnLoad(true);
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess var1, int var2, int var3, int var4) {
        int var5 = var1.getBlockMetadata(var2, var3, var4);
        float var6 = 0.0625f;
        float var7 = (float)(1 + var5 * 2) / 16.0f;
        float var8 = 0.5f;
        this.setBlockBounds(var7, 0.0f, var6, 1.0f - var6, var8, 1.0f - var6);
    }

    @Override
    public void setBlockBoundsForItemRender() {
        float var1 = 0.0625f;
        float var2 = 0.5f;
        this.setBlockBounds(var1, 0.0f, var1, 1.0f - var1, var2, 1.0f - var1);
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World var1, int var2, int var3, int var4) {
        int var5 = var1.getBlockMetadata(var2, var3, var4);
        float var6 = 0.0625f;
        float var7 = (float)(1 + var5 * 2) / 16.0f;
        float var8 = 0.5f;
        return AxisAlignedBB.getBoundingBoxFromPool((float)var2 + var7, var3, (float)var4 + var6, (float)(var2 + 1) - var6, (float)var3 + var8 - var6, (float)(var4 + 1) - var6);
    }

    @Override
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World var1, int var2, int var3, int var4) {
        int var5 = var1.getBlockMetadata(var2, var3, var4);
        float var6 = 0.0625f;
        float var7 = (float)(1 + var5 * 2) / 16.0f;
        float var8 = 0.5f;
        return AxisAlignedBB.getBoundingBoxFromPool((float)var2 + var7, var3, (float)var4 + var6, (float)(var2 + 1) - var6, (float)var3 + var8, (float)(var4 + 1) - var6);
    }

    @Override
    public int getBlockTextureFromSideAndMetadata(int var1, int var2) {
        if (var1 == 1) {
            return this.blockIndexInTexture;
        }
        if (var1 == 0) {
            return this.blockIndexInTexture + 3;
        }
        return var2 > 0 && var1 == 4 ? this.blockIndexInTexture + 2 : this.blockIndexInTexture + 1;
    }

    @Override
    public int getBlockTextureFromSide(int var1) {
        if (var1 == 1) {
            return this.blockIndexInTexture;
        }
        return var1 == 0 ? this.blockIndexInTexture + 3 : this.blockIndexInTexture + 1;
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
    public boolean blockActivated(World var1, int var2, int var3, int var4, EntityPlayer var5) {
        this.eatCakeSlice(var1, var2, var3, var4, var5);
        return true;
    }

    @Override
    public void onBlockClicked(World var1, int var2, int var3, int var4, EntityPlayer var5) {
        this.eatCakeSlice(var1, var2, var3, var4, var5);
    }

    private void eatCakeSlice(World var1, int var2, int var3, int var4, EntityPlayer var5) {
        if (var5.health < 20) {
            var5.heal(3);
            int var6 = var1.getBlockMetadata(var2, var3, var4) + 1;
            if (var6 >= 6) {
                var1.setBlockWithNotify(var2, var3, var4, 0);
            } else {
                var1.setBlockMetadataWithNotify(var2, var3, var4, var6);
                var1.markBlockAsNeedsUpdate(var2, var3, var4);
            }
        }
    }

    @Override
    public boolean canPlaceBlockAt(World var1, int var2, int var3, int var4) {
        return !super.canPlaceBlockAt(var1, var2, var3, var4) ? false : this.canBlockStay(var1, var2, var3, var4);
    }

    @Override
    public void onNeighborBlockChange(World var1, int var2, int var3, int var4, int var5) {
        if (!this.canBlockStay(var1, var2, var3, var4)) {
            this.dropBlockAsItem(var1, var2, var3, var4, var1.getBlockMetadata(var2, var3, var4));
            var1.setBlockWithNotify(var2, var3, var4, 0);
        }
    }

    @Override
    public boolean canBlockStay(World var1, int var2, int var3, int var4) {
        return var1.getBlockMaterial(var2, var3 - 1, var4).isSolid();
    }

    @Override
    public int quantityDropped(Random var1) {
        return 0;
    }

    @Override
    public int idDropped(int var1, Random var2) {
        return 0;
    }
}

