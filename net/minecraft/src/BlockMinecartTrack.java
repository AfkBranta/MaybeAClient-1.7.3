/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.Random;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Block;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.Material;
import net.minecraft.src.MinecartTrackLogic;
import net.minecraft.src.MovingObjectPosition;
import net.minecraft.src.Vec3D;
import net.minecraft.src.World;

public class BlockMinecartTrack
extends Block {
    protected BlockMinecartTrack(int var1, int var2) {
        super(var1, var2, Material.circuits);
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.125f, 1.0f);
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
    public MovingObjectPosition collisionRayTrace(World var1, int var2, int var3, int var4, Vec3D var5, Vec3D var6) {
        this.setBlockBoundsBasedOnState(var1, var2, var3, var4);
        return super.collisionRayTrace(var1, var2, var3, var4, var5, var6);
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess var1, int var2, int var3, int var4) {
        int var5 = var1.getBlockMetadata(var2, var3, var4);
        if (var5 >= 2 && var5 <= 5) {
            this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.625f, 1.0f);
        } else {
            this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.125f, 1.0f);
        }
    }

    @Override
    public int getBlockTextureFromSideAndMetadata(int var1, int var2) {
        return var2 >= 6 ? this.blockIndexInTexture - 16 : this.blockIndexInTexture;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public int getRenderType() {
        return 9;
    }

    @Override
    public int quantityDropped(Random var1) {
        return 1;
    }

    @Override
    public boolean canPlaceBlockAt(World var1, int var2, int var3, int var4) {
        return var1.isBlockOpaqueCube(var2, var3 - 1, var4);
    }

    @Override
    public void onBlockAdded(World var1, int var2, int var3, int var4) {
        if (!var1.multiplayerWorld) {
            var1.setBlockMetadataWithNotify(var2, var3, var4, 15);
            this.func_4031_h(var1, var2, var3, var4);
        }
    }

    @Override
    public void onNeighborBlockChange(World var1, int var2, int var3, int var4, int var5) {
        if (!var1.multiplayerWorld) {
            int var6 = var1.getBlockMetadata(var2, var3, var4);
            boolean var7 = false;
            if (!var1.isBlockOpaqueCube(var2, var3 - 1, var4)) {
                var7 = true;
            }
            if (var6 == 2 && !var1.isBlockOpaqueCube(var2 + 1, var3, var4)) {
                var7 = true;
            }
            if (var6 == 3 && !var1.isBlockOpaqueCube(var2 - 1, var3, var4)) {
                var7 = true;
            }
            if (var6 == 4 && !var1.isBlockOpaqueCube(var2, var3, var4 - 1)) {
                var7 = true;
            }
            if (var6 == 5 && !var1.isBlockOpaqueCube(var2, var3, var4 + 1)) {
                var7 = true;
            }
            if (var7) {
                this.dropBlockAsItem(var1, var2, var3, var4, var1.getBlockMetadata(var2, var3, var4));
                var1.setBlockWithNotify(var2, var3, var4, 0);
            } else if (var5 > 0 && Block.blocksList[var5].canProvidePower() && MinecartTrackLogic.getNAdjacentTracks(new MinecartTrackLogic(this, var1, var2, var3, var4)) == 3) {
                this.func_4031_h(var1, var2, var3, var4);
            }
        }
    }

    private void func_4031_h(World var1, int var2, int var3, int var4) {
        if (!var1.multiplayerWorld) {
            new MinecartTrackLogic(this, var1, var2, var3, var4).func_792_a(var1.isBlockIndirectlyGettingPowered(var2, var3, var4));
        }
    }
}

