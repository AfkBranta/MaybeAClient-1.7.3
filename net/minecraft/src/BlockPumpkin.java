/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.Block;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.Material;
import net.minecraft.src.MathHelper;
import net.minecraft.src.World;

public class BlockPumpkin
extends Block {
    private boolean blockType;

    protected BlockPumpkin(int var1, int var2, boolean var3) {
        super(var1, Material.pumpkin);
        this.blockIndexInTexture = var2;
        this.setTickOnLoad(true);
        this.blockType = var3;
    }

    @Override
    public int getBlockTextureFromSideAndMetadata(int var1, int var2) {
        if (var1 == 1) {
            return this.blockIndexInTexture;
        }
        if (var1 == 0) {
            return this.blockIndexInTexture;
        }
        int var3 = this.blockIndexInTexture + 1 + 16;
        if (this.blockType) {
            ++var3;
        }
        if (var2 == 0 && var1 == 2) {
            return var3;
        }
        if (var2 == 1 && var1 == 5) {
            return var3;
        }
        if (var2 == 2 && var1 == 3) {
            return var3;
        }
        return var2 == 3 && var1 == 4 ? var3 : this.blockIndexInTexture + 16;
    }

    @Override
    public int getBlockTextureFromSide(int var1) {
        if (var1 == 1) {
            return this.blockIndexInTexture;
        }
        if (var1 == 0) {
            return this.blockIndexInTexture;
        }
        return var1 == 3 ? this.blockIndexInTexture + 1 + 16 : this.blockIndexInTexture + 16;
    }

    @Override
    public void onBlockAdded(World var1, int var2, int var3, int var4) {
        super.onBlockAdded(var1, var2, var3, var4);
    }

    @Override
    public boolean canPlaceBlockAt(World var1, int var2, int var3, int var4) {
        int var5 = var1.getBlockId(var2, var3, var4);
        return (var5 == 0 || Block.blocksList[var5].blockMaterial.getIsLiquid()) && var1.isBlockOpaqueCube(var2, var3 - 1, var4);
    }

    @Override
    public void onBlockPlacedBy(World var1, int var2, int var3, int var4, EntityLiving var5) {
        int var6 = MathHelper.floor_double((double)(var5.rotationYaw * 4.0f / 360.0f) + 0.5) & 3;
        var1.setBlockMetadataWithNotify(var2, var3, var4, var6);
    }
}

