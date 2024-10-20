/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.Random;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Block;
import net.minecraft.src.Material;
import net.minecraft.src.MovingObjectPosition;
import net.minecraft.src.Vec3D;
import net.minecraft.src.World;

public class BlockTorch
extends Block {
    protected BlockTorch(int var1, int var2) {
        super(var1, var2, Material.circuits);
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
    public int getRenderType() {
        return 2;
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
        if (var1.isBlockOpaqueCube(var2, var3, var4 + 1)) {
            return true;
        }
        return var1.isBlockOpaqueCube(var2, var3 - 1, var4);
    }

    @Override
    public void onBlockPlaced(World var1, int var2, int var3, int var4, int var5) {
        int var6 = var1.getBlockMetadata(var2, var3, var4);
        if (var5 == 1 && var1.isBlockOpaqueCube(var2, var3 - 1, var4)) {
            var6 = 5;
        }
        if (var5 == 2 && var1.isBlockOpaqueCube(var2, var3, var4 + 1)) {
            var6 = 4;
        }
        if (var5 == 3 && var1.isBlockOpaqueCube(var2, var3, var4 - 1)) {
            var6 = 3;
        }
        if (var5 == 4 && var1.isBlockOpaqueCube(var2 + 1, var3, var4)) {
            var6 = 2;
        }
        if (var5 == 5 && var1.isBlockOpaqueCube(var2 - 1, var3, var4)) {
            var6 = 1;
        }
        var1.setBlockMetadataWithNotify(var2, var3, var4, var6);
    }

    @Override
    public void updateTick(World var1, int var2, int var3, int var4, Random var5) {
        super.updateTick(var1, var2, var3, var4, var5);
        if (var1.getBlockMetadata(var2, var3, var4) == 0) {
            this.onBlockAdded(var1, var2, var3, var4);
        }
    }

    @Override
    public void onBlockAdded(World var1, int var2, int var3, int var4) {
        if (var1.isBlockOpaqueCube(var2 - 1, var3, var4)) {
            var1.setBlockMetadataWithNotify(var2, var3, var4, 1);
        } else if (var1.isBlockOpaqueCube(var2 + 1, var3, var4)) {
            var1.setBlockMetadataWithNotify(var2, var3, var4, 2);
        } else if (var1.isBlockOpaqueCube(var2, var3, var4 - 1)) {
            var1.setBlockMetadataWithNotify(var2, var3, var4, 3);
        } else if (var1.isBlockOpaqueCube(var2, var3, var4 + 1)) {
            var1.setBlockMetadataWithNotify(var2, var3, var4, 4);
        } else if (var1.isBlockOpaqueCube(var2, var3 - 1, var4)) {
            var1.setBlockMetadataWithNotify(var2, var3, var4, 5);
        }
        this.dropTorchIfCantStay(var1, var2, var3, var4);
    }

    @Override
    public void onNeighborBlockChange(World var1, int var2, int var3, int var4, int var5) {
        if (this.dropTorchIfCantStay(var1, var2, var3, var4)) {
            int var6 = var1.getBlockMetadata(var2, var3, var4);
            boolean var7 = false;
            if (!var1.isBlockOpaqueCube(var2 - 1, var3, var4) && var6 == 1) {
                var7 = true;
            }
            if (!var1.isBlockOpaqueCube(var2 + 1, var3, var4) && var6 == 2) {
                var7 = true;
            }
            if (!var1.isBlockOpaqueCube(var2, var3, var4 - 1) && var6 == 3) {
                var7 = true;
            }
            if (!var1.isBlockOpaqueCube(var2, var3, var4 + 1) && var6 == 4) {
                var7 = true;
            }
            if (!var1.isBlockOpaqueCube(var2, var3 - 1, var4) && var6 == 5) {
                var7 = true;
            }
            if (var7) {
                this.dropBlockAsItem(var1, var2, var3, var4, var1.getBlockMetadata(var2, var3, var4));
                var1.setBlockWithNotify(var2, var3, var4, 0);
            }
        }
    }

    private boolean dropTorchIfCantStay(World var1, int var2, int var3, int var4) {
        if (!this.canPlaceBlockAt(var1, var2, var3, var4)) {
            this.dropBlockAsItem(var1, var2, var3, var4, var1.getBlockMetadata(var2, var3, var4));
            var1.setBlockWithNotify(var2, var3, var4, 0);
            return false;
        }
        return true;
    }

    @Override
    public MovingObjectPosition collisionRayTrace(World var1, int var2, int var3, int var4, Vec3D var5, Vec3D var6) {
        int var7 = var1.getBlockMetadata(var2, var3, var4) & 7;
        float var8 = 0.15f;
        if (var7 == 1) {
            this.setBlockBounds(0.0f, 0.2f, 0.5f - var8, var8 * 2.0f, 0.8f, 0.5f + var8);
        } else if (var7 == 2) {
            this.setBlockBounds(1.0f - var8 * 2.0f, 0.2f, 0.5f - var8, 1.0f, 0.8f, 0.5f + var8);
        } else if (var7 == 3) {
            this.setBlockBounds(0.5f - var8, 0.2f, 0.0f, 0.5f + var8, 0.8f, var8 * 2.0f);
        } else if (var7 == 4) {
            this.setBlockBounds(0.5f - var8, 0.2f, 1.0f - var8 * 2.0f, 0.5f + var8, 0.8f, 1.0f);
        } else {
            var8 = 0.1f;
            this.setBlockBounds(0.5f - var8, 0.0f, 0.5f - var8, 0.5f + var8, 0.6f, 0.5f + var8);
        }
        return super.collisionRayTrace(var1, var2, var3, var4, var5, var6);
    }

    @Override
    public void randomDisplayTick(World var1, int var2, int var3, int var4, Random var5) {
        int var6 = var1.getBlockMetadata(var2, var3, var4);
        double var7 = (float)var2 + 0.5f;
        double var9 = (float)var3 + 0.7f;
        double var11 = (float)var4 + 0.5f;
        double var13 = 0.22f;
        double var15 = 0.27f;
        if (var6 == 1) {
            var1.spawnParticle("smoke", var7 - var15, var9 + var13, var11, 0.0, 0.0, 0.0);
            var1.spawnParticle("flame", var7 - var15, var9 + var13, var11, 0.0, 0.0, 0.0);
        } else if (var6 == 2) {
            var1.spawnParticle("smoke", var7 + var15, var9 + var13, var11, 0.0, 0.0, 0.0);
            var1.spawnParticle("flame", var7 + var15, var9 + var13, var11, 0.0, 0.0, 0.0);
        } else if (var6 == 3) {
            var1.spawnParticle("smoke", var7, var9 + var13, var11 - var15, 0.0, 0.0, 0.0);
            var1.spawnParticle("flame", var7, var9 + var13, var11 - var15, 0.0, 0.0, 0.0);
        } else if (var6 == 4) {
            var1.spawnParticle("smoke", var7, var9 + var13, var11 + var15, 0.0, 0.0, 0.0);
            var1.spawnParticle("flame", var7, var9 + var13, var11 + var15, 0.0, 0.0, 0.0);
        } else {
            var1.spawnParticle("smoke", var7, var9, var11, 0.0, 0.0, 0.0);
            var1.spawnParticle("flame", var7, var9, var11, 0.0, 0.0, 0.0);
        }
    }
}

