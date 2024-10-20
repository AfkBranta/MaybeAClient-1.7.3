/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.Random;
import net.minecraft.src.Block;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.Item;
import net.minecraft.src.Material;
import net.minecraft.src.MathHelper;
import net.minecraft.src.World;

public class BlockRedstoneRepeater
extends Block {
    public static final double[] field_22024_a = new double[]{-0.0625, 0.0625, 0.1875, 0.3125};
    private static final int[] field_22023_b = new int[]{1, 2, 3, 4};
    private final boolean field_22025_c;

    protected BlockRedstoneRepeater(int var1, boolean var2) {
        super(var1, 102, Material.circuits);
        this.field_22025_c = var2;
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.125f, 1.0f);
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public boolean canPlaceBlockAt(World var1, int var2, int var3, int var4) {
        return !var1.isBlockOpaqueCube(var2, var3 - 1, var4) ? false : super.canPlaceBlockAt(var1, var2, var3, var4);
    }

    @Override
    public boolean canBlockStay(World var1, int var2, int var3, int var4) {
        return !var1.isBlockOpaqueCube(var2, var3 - 1, var4) ? false : super.canBlockStay(var1, var2, var3, var4);
    }

    @Override
    public void updateTick(World var1, int var2, int var3, int var4, Random var5) {
        int var6 = var1.getBlockMetadata(var2, var3, var4);
        boolean var7 = this.func_22022_g(var1, var2, var3, var4, var6);
        if (this.field_22025_c && !var7) {
            var1.setBlockAndMetadataWithNotify(var2, var3, var4, Block.redstoneRepeaterIdle.blockID, var6);
        } else if (!this.field_22025_c) {
            var1.setBlockAndMetadataWithNotify(var2, var3, var4, Block.redstoneRepeaterActive.blockID, var6);
            if (!var7) {
                int var8 = (var6 & 0xC) >> 2;
                var1.scheduleBlockUpdate(var2, var3, var4, Block.redstoneRepeaterActive.blockID, field_22023_b[var8] * 2);
            }
        }
    }

    @Override
    public int getBlockTextureFromSideAndMetadata(int var1, int var2) {
        if (var1 == 0) {
            return this.field_22025_c ? 99 : 115;
        }
        if (var1 == 1) {
            return this.field_22025_c ? 147 : 131;
        }
        return 5;
    }

    @Override
    public boolean shouldSideBeRendered(IBlockAccess var1, int var2, int var3, int var4, int var5) {
        return var5 != 0 && var5 != 1;
    }

    @Override
    public int getRenderType() {
        return 15;
    }

    @Override
    public int getBlockTextureFromSide(int var1) {
        return this.getBlockTextureFromSideAndMetadata(var1, 0);
    }

    @Override
    public boolean isIndirectlyPoweringTo(World var1, int var2, int var3, int var4, int var5) {
        return this.isPoweringTo(var1, var2, var3, var4, var5);
    }

    @Override
    public boolean isPoweringTo(IBlockAccess var1, int var2, int var3, int var4, int var5) {
        if (!this.field_22025_c) {
            return false;
        }
        int var6 = var1.getBlockMetadata(var2, var3, var4) & 3;
        if (var6 == 0 && var5 == 3) {
            return true;
        }
        if (var6 == 1 && var5 == 4) {
            return true;
        }
        if (var6 == 2 && var5 == 2) {
            return true;
        }
        return var6 == 3 && var5 == 5;
    }

    @Override
    public void onNeighborBlockChange(World var1, int var2, int var3, int var4, int var5) {
        if (!this.canBlockStay(var1, var2, var3, var4)) {
            this.dropBlockAsItem(var1, var2, var3, var4, var1.getBlockMetadata(var2, var3, var4));
            var1.setBlockWithNotify(var2, var3, var4, 0);
        } else {
            int var6 = var1.getBlockMetadata(var2, var3, var4);
            boolean var7 = this.func_22022_g(var1, var2, var3, var4, var6);
            int var8 = (var6 & 0xC) >> 2;
            if (this.field_22025_c && !var7) {
                var1.scheduleBlockUpdate(var2, var3, var4, this.blockID, field_22023_b[var8] * 2);
            } else if (!this.field_22025_c && var7) {
                var1.scheduleBlockUpdate(var2, var3, var4, this.blockID, field_22023_b[var8] * 2);
            }
        }
    }

    private boolean func_22022_g(World var1, int var2, int var3, int var4, int var5) {
        int var6 = var5 & 3;
        switch (var6) {
            case 0: {
                return var1.isBlockIndirectlyProvidingPowerTo(var2, var3, var4 + 1, 3);
            }
            case 1: {
                return var1.isBlockIndirectlyProvidingPowerTo(var2 - 1, var3, var4, 4);
            }
            case 2: {
                return var1.isBlockIndirectlyProvidingPowerTo(var2, var3, var4 - 1, 2);
            }
            case 3: {
                return var1.isBlockIndirectlyProvidingPowerTo(var2 + 1, var3, var4, 5);
            }
        }
        return false;
    }

    @Override
    public boolean blockActivated(World var1, int var2, int var3, int var4, EntityPlayer var5) {
        int var6 = var1.getBlockMetadata(var2, var3, var4);
        int var7 = (var6 & 0xC) >> 2;
        var7 = var7 + 1 << 2 & 0xC;
        var1.setBlockMetadataWithNotify(var2, var3, var4, var7 | var6 & 3);
        return true;
    }

    @Override
    public boolean canProvidePower() {
        return false;
    }

    @Override
    public void onBlockPlacedBy(World var1, int var2, int var3, int var4, EntityLiving var5) {
        int var6 = ((MathHelper.floor_double((double)(var5.rotationYaw * 4.0f / 360.0f) + 0.5) & 3) + 2) % 4;
        var1.setBlockMetadataWithNotify(var2, var3, var4, var6);
        boolean var7 = this.func_22022_g(var1, var2, var3, var4, var6);
        if (var7) {
            var1.scheduleBlockUpdate(var2, var3, var4, this.blockID, 1);
        }
    }

    @Override
    public void onBlockAdded(World var1, int var2, int var3, int var4) {
        var1.notifyBlocksOfNeighborChange(var2 + 1, var3, var4, this.blockID);
        var1.notifyBlocksOfNeighborChange(var2 - 1, var3, var4, this.blockID);
        var1.notifyBlocksOfNeighborChange(var2, var3, var4 + 1, this.blockID);
        var1.notifyBlocksOfNeighborChange(var2, var3, var4 - 1, this.blockID);
        var1.notifyBlocksOfNeighborChange(var2, var3 - 1, var4, this.blockID);
        var1.notifyBlocksOfNeighborChange(var2, var3 + 1, var4, this.blockID);
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public int idDropped(int var1, Random var2) {
        return Item.redstoneRepeater.shiftedIndex;
    }

    @Override
    public void randomDisplayTick(World var1, int var2, int var3, int var4, Random var5) {
        if (this.field_22025_c) {
            int var6 = var1.getBlockMetadata(var2, var3, var4);
            double var7 = (double)((float)var2 + 0.5f) + (double)(var5.nextFloat() - 0.5f) * 0.2;
            double var9 = (double)((float)var3 + 0.4f) + (double)(var5.nextFloat() - 0.5f) * 0.2;
            double var11 = (double)((float)var4 + 0.5f) + (double)(var5.nextFloat() - 0.5f) * 0.2;
            double var13 = 0.0;
            double var15 = 0.0;
            if (var5.nextInt(2) == 0) {
                switch (var6 & 3) {
                    case 0: {
                        var15 = -0.3125;
                        break;
                    }
                    case 1: {
                        var13 = 0.3125;
                        break;
                    }
                    case 2: {
                        var15 = 0.3125;
                        break;
                    }
                    case 3: {
                        var13 = -0.3125;
                    }
                }
            } else {
                int var17 = (var6 & 0xC) >> 2;
                switch (var6 & 3) {
                    case 0: {
                        var15 = field_22024_a[var17];
                        break;
                    }
                    case 1: {
                        var13 = -field_22024_a[var17];
                        break;
                    }
                    case 2: {
                        var15 = -field_22024_a[var17];
                        break;
                    }
                    case 3: {
                        var13 = field_22024_a[var17];
                    }
                }
            }
            var1.spawnParticle("reddust", var7 + var13, var9, var11 + var15, 0.0, 0.0, 0.0);
        }
    }
}

