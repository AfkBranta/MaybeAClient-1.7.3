/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.Random;
import net.minecraft.src.Block;
import net.minecraft.src.BlockContainer;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.Material;
import net.minecraft.src.MathHelper;
import net.minecraft.src.TileEntity;
import net.minecraft.src.TileEntityFurnace;
import net.minecraft.src.World;

public class BlockFurnace
extends BlockContainer {
    private final boolean isActive;

    protected BlockFurnace(int var1, boolean var2) {
        super(var1, Material.rock);
        this.isActive = var2;
        this.blockIndexInTexture = 45;
    }

    @Override
    public int idDropped(int var1, Random var2) {
        return Block.stoneOvenIdle.blockID;
    }

    @Override
    public void onBlockAdded(World var1, int var2, int var3, int var4) {
        super.onBlockAdded(var1, var2, var3, var4);
        this.setDefaultDirection(var1, var2, var3, var4);
    }

    private void setDefaultDirection(World var1, int var2, int var3, int var4) {
        int var5 = var1.getBlockId(var2, var3, var4 - 1);
        int var6 = var1.getBlockId(var2, var3, var4 + 1);
        int var7 = var1.getBlockId(var2 - 1, var3, var4);
        int var8 = var1.getBlockId(var2 + 1, var3, var4);
        int var9 = 3;
        if (Block.opaqueCubeLookup[var5] && !Block.opaqueCubeLookup[var6]) {
            var9 = 3;
        }
        if (Block.opaqueCubeLookup[var6] && !Block.opaqueCubeLookup[var5]) {
            var9 = 2;
        }
        if (Block.opaqueCubeLookup[var7] && !Block.opaqueCubeLookup[var8]) {
            var9 = 5;
        }
        if (Block.opaqueCubeLookup[var8] && !Block.opaqueCubeLookup[var7]) {
            var9 = 4;
        }
        var1.setBlockMetadataWithNotify(var2, var3, var4, var9);
    }

    @Override
    public int getBlockTexture(IBlockAccess var1, int var2, int var3, int var4, int var5) {
        if (var5 == 1) {
            return this.blockIndexInTexture + 17;
        }
        if (var5 == 0) {
            return this.blockIndexInTexture + 17;
        }
        int var6 = var1.getBlockMetadata(var2, var3, var4);
        if (var5 != var6) {
            return this.blockIndexInTexture;
        }
        return this.isActive ? this.blockIndexInTexture + 16 : this.blockIndexInTexture - 1;
    }

    @Override
    public void randomDisplayTick(World var1, int var2, int var3, int var4, Random var5) {
        if (this.isActive) {
            int var6 = var1.getBlockMetadata(var2, var3, var4);
            float var7 = (float)var2 + 0.5f;
            float var8 = (float)var3 + 0.0f + var5.nextFloat() * 6.0f / 16.0f;
            float var9 = (float)var4 + 0.5f;
            float var10 = 0.52f;
            float var11 = var5.nextFloat() * 0.6f - 0.3f;
            if (var6 == 4) {
                var1.spawnParticle("smoke", var7 - var10, var8, var9 + var11, 0.0, 0.0, 0.0);
                var1.spawnParticle("flame", var7 - var10, var8, var9 + var11, 0.0, 0.0, 0.0);
            } else if (var6 == 5) {
                var1.spawnParticle("smoke", var7 + var10, var8, var9 + var11, 0.0, 0.0, 0.0);
                var1.spawnParticle("flame", var7 + var10, var8, var9 + var11, 0.0, 0.0, 0.0);
            } else if (var6 == 2) {
                var1.spawnParticle("smoke", var7 + var11, var8, var9 - var10, 0.0, 0.0, 0.0);
                var1.spawnParticle("flame", var7 + var11, var8, var9 - var10, 0.0, 0.0, 0.0);
            } else if (var6 == 3) {
                var1.spawnParticle("smoke", var7 + var11, var8, var9 + var10, 0.0, 0.0, 0.0);
                var1.spawnParticle("flame", var7 + var11, var8, var9 + var10, 0.0, 0.0, 0.0);
            }
        }
    }

    @Override
    public int getBlockTextureFromSide(int var1) {
        if (var1 == 1) {
            return this.blockIndexInTexture + 17;
        }
        if (var1 == 0) {
            return this.blockIndexInTexture + 17;
        }
        return var1 == 3 ? this.blockIndexInTexture - 1 : this.blockIndexInTexture;
    }

    @Override
    public boolean blockActivated(World var1, int var2, int var3, int var4, EntityPlayer var5) {
        if (var1.multiplayerWorld) {
            return true;
        }
        TileEntityFurnace var6 = (TileEntityFurnace)var1.getBlockTileEntity(var2, var3, var4);
        var5.displayGUIFurnace(var6);
        return true;
    }

    public static void updateFurnaceBlockState(boolean var0, World var1, int var2, int var3, int var4) {
        int var5 = var1.getBlockMetadata(var2, var3, var4);
        TileEntity var6 = var1.getBlockTileEntity(var2, var3, var4);
        if (var0) {
            var1.setBlockWithNotify(var2, var3, var4, Block.stoneOvenActive.blockID);
        } else {
            var1.setBlockWithNotify(var2, var3, var4, Block.stoneOvenIdle.blockID);
        }
        var1.setBlockMetadataWithNotify(var2, var3, var4, var5);
        var1.setBlockTileEntity(var2, var3, var4, var6);
    }

    @Override
    protected TileEntity getBlockEntity() {
        return new TileEntityFurnace();
    }

    @Override
    public void onBlockPlacedBy(World var1, int var2, int var3, int var4, EntityLiving var5) {
        int var6 = MathHelper.floor_double((double)(var5.rotationYaw * 4.0f / 360.0f) + 0.5) & 3;
        if (var6 == 0) {
            var1.setBlockMetadataWithNotify(var2, var3, var4, 2);
        }
        if (var6 == 1) {
            var1.setBlockMetadataWithNotify(var2, var3, var4, 5);
        }
        if (var6 == 2) {
            var1.setBlockMetadataWithNotify(var2, var3, var4, 3);
        }
        if (var6 == 3) {
            var1.setBlockMetadataWithNotify(var2, var3, var4, 4);
        }
    }
}

