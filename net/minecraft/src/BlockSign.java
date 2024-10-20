package net.minecraft.src;

import java.util.Random;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.BlockContainer;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.Item;
import net.minecraft.src.Material;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;

public class BlockSign
extends BlockContainer {
    private Class signEntityClass;
    private boolean isFreestanding;

    protected BlockSign(int var1, Class var2, boolean var3) {
        super(var1, Material.wood);
        this.isFreestanding = var3;
        this.blockIndexInTexture = 4;
        this.signEntityClass = var2;
        float var4 = 0.25f;
        float var5 = 1.0f;
        this.setBlockBounds(0.5f - var4, 0.0f, 0.5f - var4, 0.5f + var4, var5, 0.5f + var4);
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World var1, int var2, int var3, int var4) {
        return null;
    }

    @Override
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World var1, int var2, int var3, int var4) {
        this.setBlockBoundsBasedOnState(var1, var2, var3, var4);
        return super.getSelectedBoundingBoxFromPool(var1, var2, var3, var4);
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess var1, int var2, int var3, int var4) {
        if (!this.isFreestanding) {
            int var5 = var1.getBlockMetadata(var2, var3, var4);
            float var6 = 0.28125f;
            float var7 = 0.78125f;
            float var8 = 0.0f;
            float var9 = 1.0f;
            float var10 = 0.125f;
            this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
            if (var5 == 2) {
                this.setBlockBounds(var8, var6, 1.0f - var10, var9, var7, 1.0f);
            }
            if (var5 == 3) {
                this.setBlockBounds(var8, var6, 0.0f, var9, var7, var10);
            }
            if (var5 == 4) {
                this.setBlockBounds(1.0f - var10, var6, var8, 1.0f, var7, var9);
            }
            if (var5 == 5) {
                this.setBlockBounds(0.0f, var6, var8, var10, var7, var9);
            }
        }
    }

    @Override
    public int getRenderType() {
        return -1;
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
    protected TileEntity getBlockEntity() {
        try {
            return (TileEntity)this.signEntityClass.newInstance();
        }
        catch (Exception var2) {
            throw new RuntimeException(var2);
        }
    }

    @Override
    public int idDropped(int var1, Random var2) {
        return Item.sign.shiftedIndex;
    }

    @Override
    public void onNeighborBlockChange(World var1, int var2, int var3, int var4, int var5) {
        boolean var6 = false;
        if (this.isFreestanding) {
            if (!var1.getBlockMaterial(var2, var3 - 1, var4).isSolid()) {
                var6 = true;
            }
        } else {
            int var7 = var1.getBlockMetadata(var2, var3, var4);
            var6 = true;
            if (var7 == 2 && var1.getBlockMaterial(var2, var3, var4 + 1).isSolid()) {
                var6 = false;
            }
            if (var7 == 3 && var1.getBlockMaterial(var2, var3, var4 - 1).isSolid()) {
                var6 = false;
            }
            if (var7 == 4 && var1.getBlockMaterial(var2 + 1, var3, var4).isSolid()) {
                var6 = false;
            }
            if (var7 == 5 && var1.getBlockMaterial(var2 - 1, var3, var4).isSolid()) {
                var6 = false;
            }
        }
        if (var6) {
            this.dropBlockAsItem(var1, var2, var3, var4, var1.getBlockMetadata(var2, var3, var4));
            var1.setBlockWithNotify(var2, var3, var4, 0);
        }
        super.onNeighborBlockChange(var1, var2, var3, var4, var5);
    }
}

