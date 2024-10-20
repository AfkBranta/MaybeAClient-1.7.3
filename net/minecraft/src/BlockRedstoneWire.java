/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Block;
import net.minecraft.src.ChunkPosition;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.Item;
import net.minecraft.src.Material;
import net.minecraft.src.World;

public class BlockRedstoneWire
extends Block {
    private boolean wiresProvidePower = true;
    private Set field_21031_b = new HashSet();

    public BlockRedstoneWire(int var1, int var2) {
        super(var1, var2, Material.circuits);
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.0625f, 1.0f);
    }

    @Override
    public int getBlockTextureFromSideAndMetadata(int var1, int var2) {
        return this.blockIndexInTexture;
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
        return 5;
    }

    @Override
    public boolean canPlaceBlockAt(World var1, int var2, int var3, int var4) {
        return var1.isBlockOpaqueCube(var2, var3 - 1, var4);
    }

    private void updateAndPropagateCurrentStrength(World var1, int var2, int var3, int var4) {
        this.func_21030_a(var1, var2, var3, var4, var2, var3, var4);
        ArrayList var5 = new ArrayList(this.field_21031_b);
        this.field_21031_b.clear();
        int var6 = 0;
        while (var6 < var5.size()) {
            ChunkPosition var7 = (ChunkPosition)var5.get(var6);
            var1.notifyBlocksOfNeighborChange(var7.x, var7.y, var7.z, this.blockID);
            ++var6;
        }
    }

    private void func_21030_a(World var1, int var2, int var3, int var4, int var5, int var6, int var7) {
        int var13;
        int var12;
        int var11;
        int var8 = var1.getBlockMetadata(var2, var3, var4);
        int var9 = 0;
        this.wiresProvidePower = false;
        boolean var10 = var1.isBlockIndirectlyGettingPowered(var2, var3, var4);
        this.wiresProvidePower = true;
        if (var10) {
            var9 = 15;
        } else {
            var11 = 0;
            while (var11 < 4) {
                var12 = var2;
                var13 = var4;
                if (var11 == 0) {
                    var12 = var2 - 1;
                }
                if (var11 == 1) {
                    ++var12;
                }
                if (var11 == 2) {
                    var13 = var4 - 1;
                }
                if (var11 == 3) {
                    ++var13;
                }
                if (var12 != var5 || var3 != var6 || var13 != var7) {
                    var9 = this.getMaxCurrentStrength(var1, var12, var3, var13, var9);
                }
                if (var1.isBlockOpaqueCube(var12, var3, var13) && !var1.isBlockOpaqueCube(var2, var3 + 1, var4)) {
                    if (var12 != var5 || var3 + 1 != var6 || var13 != var7) {
                        var9 = this.getMaxCurrentStrength(var1, var12, var3 + 1, var13, var9);
                    }
                } else if (!(var1.isBlockOpaqueCube(var12, var3, var13) || var12 == var5 && var3 - 1 == var6 && var13 == var7)) {
                    var9 = this.getMaxCurrentStrength(var1, var12, var3 - 1, var13, var9);
                }
                ++var11;
            }
            var9 = var9 > 0 ? --var9 : 0;
        }
        if (var8 != var9) {
            var1.editingBlocks = true;
            var1.setBlockMetadataWithNotify(var2, var3, var4, var9);
            var1.markBlocksDirty(var2, var3, var4, var2, var3, var4);
            var1.editingBlocks = false;
            var11 = 0;
            while (var11 < 4) {
                var12 = var2;
                var13 = var4;
                int var14 = var3 - 1;
                if (var11 == 0) {
                    var12 = var2 - 1;
                }
                if (var11 == 1) {
                    ++var12;
                }
                if (var11 == 2) {
                    var13 = var4 - 1;
                }
                if (var11 == 3) {
                    ++var13;
                }
                if (var1.isBlockOpaqueCube(var12, var3, var13)) {
                    var14 += 2;
                }
                boolean var15 = false;
                int var16 = this.getMaxCurrentStrength(var1, var12, var3, var13, -1);
                var9 = var1.getBlockMetadata(var2, var3, var4);
                if (var9 > 0) {
                    --var9;
                }
                if (var16 >= 0 && var16 != var9) {
                    this.func_21030_a(var1, var12, var3, var13, var2, var3, var4);
                }
                var16 = this.getMaxCurrentStrength(var1, var12, var14, var13, -1);
                var9 = var1.getBlockMetadata(var2, var3, var4);
                if (var9 > 0) {
                    --var9;
                }
                if (var16 >= 0 && var16 != var9) {
                    this.func_21030_a(var1, var12, var14, var13, var2, var3, var4);
                }
                ++var11;
            }
            if (var8 == 0 || var9 == 0) {
                this.field_21031_b.add(new ChunkPosition(var2, var3, var4));
                this.field_21031_b.add(new ChunkPosition(var2 - 1, var3, var4));
                this.field_21031_b.add(new ChunkPosition(var2 + 1, var3, var4));
                this.field_21031_b.add(new ChunkPosition(var2, var3 - 1, var4));
                this.field_21031_b.add(new ChunkPosition(var2, var3 + 1, var4));
                this.field_21031_b.add(new ChunkPosition(var2, var3, var4 - 1));
                this.field_21031_b.add(new ChunkPosition(var2, var3, var4 + 1));
            }
        }
    }

    private void notifyWireNeighborsOfNeighborChange(World var1, int var2, int var3, int var4) {
        if (var1.getBlockId(var2, var3, var4) == this.blockID) {
            var1.notifyBlocksOfNeighborChange(var2, var3, var4, this.blockID);
            var1.notifyBlocksOfNeighborChange(var2 - 1, var3, var4, this.blockID);
            var1.notifyBlocksOfNeighborChange(var2 + 1, var3, var4, this.blockID);
            var1.notifyBlocksOfNeighborChange(var2, var3, var4 - 1, this.blockID);
            var1.notifyBlocksOfNeighborChange(var2, var3, var4 + 1, this.blockID);
            var1.notifyBlocksOfNeighborChange(var2, var3 - 1, var4, this.blockID);
            var1.notifyBlocksOfNeighborChange(var2, var3 + 1, var4, this.blockID);
        }
    }

    @Override
    public void onBlockAdded(World var1, int var2, int var3, int var4) {
        super.onBlockAdded(var1, var2, var3, var4);
        if (!var1.multiplayerWorld) {
            this.updateAndPropagateCurrentStrength(var1, var2, var3, var4);
            var1.notifyBlocksOfNeighborChange(var2, var3 + 1, var4, this.blockID);
            var1.notifyBlocksOfNeighborChange(var2, var3 - 1, var4, this.blockID);
            this.notifyWireNeighborsOfNeighborChange(var1, var2 - 1, var3, var4);
            this.notifyWireNeighborsOfNeighborChange(var1, var2 + 1, var3, var4);
            this.notifyWireNeighborsOfNeighborChange(var1, var2, var3, var4 - 1);
            this.notifyWireNeighborsOfNeighborChange(var1, var2, var3, var4 + 1);
            if (var1.isBlockOpaqueCube(var2 - 1, var3, var4)) {
                this.notifyWireNeighborsOfNeighborChange(var1, var2 - 1, var3 + 1, var4);
            } else {
                this.notifyWireNeighborsOfNeighborChange(var1, var2 - 1, var3 - 1, var4);
            }
            if (var1.isBlockOpaqueCube(var2 + 1, var3, var4)) {
                this.notifyWireNeighborsOfNeighborChange(var1, var2 + 1, var3 + 1, var4);
            } else {
                this.notifyWireNeighborsOfNeighborChange(var1, var2 + 1, var3 - 1, var4);
            }
            if (var1.isBlockOpaqueCube(var2, var3, var4 - 1)) {
                this.notifyWireNeighborsOfNeighborChange(var1, var2, var3 + 1, var4 - 1);
            } else {
                this.notifyWireNeighborsOfNeighborChange(var1, var2, var3 - 1, var4 - 1);
            }
            if (var1.isBlockOpaqueCube(var2, var3, var4 + 1)) {
                this.notifyWireNeighborsOfNeighborChange(var1, var2, var3 + 1, var4 + 1);
            } else {
                this.notifyWireNeighborsOfNeighborChange(var1, var2, var3 - 1, var4 + 1);
            }
        }
    }

    @Override
    public void onBlockRemoval(World var1, int var2, int var3, int var4) {
        super.onBlockRemoval(var1, var2, var3, var4);
        if (!var1.multiplayerWorld) {
            var1.notifyBlocksOfNeighborChange(var2, var3 + 1, var4, this.blockID);
            var1.notifyBlocksOfNeighborChange(var2, var3 - 1, var4, this.blockID);
            this.updateAndPropagateCurrentStrength(var1, var2, var3, var4);
            this.notifyWireNeighborsOfNeighborChange(var1, var2 - 1, var3, var4);
            this.notifyWireNeighborsOfNeighborChange(var1, var2 + 1, var3, var4);
            this.notifyWireNeighborsOfNeighborChange(var1, var2, var3, var4 - 1);
            this.notifyWireNeighborsOfNeighborChange(var1, var2, var3, var4 + 1);
            if (var1.isBlockOpaqueCube(var2 - 1, var3, var4)) {
                this.notifyWireNeighborsOfNeighborChange(var1, var2 - 1, var3 + 1, var4);
            } else {
                this.notifyWireNeighborsOfNeighborChange(var1, var2 - 1, var3 - 1, var4);
            }
            if (var1.isBlockOpaqueCube(var2 + 1, var3, var4)) {
                this.notifyWireNeighborsOfNeighborChange(var1, var2 + 1, var3 + 1, var4);
            } else {
                this.notifyWireNeighborsOfNeighborChange(var1, var2 + 1, var3 - 1, var4);
            }
            if (var1.isBlockOpaqueCube(var2, var3, var4 - 1)) {
                this.notifyWireNeighborsOfNeighborChange(var1, var2, var3 + 1, var4 - 1);
            } else {
                this.notifyWireNeighborsOfNeighborChange(var1, var2, var3 - 1, var4 - 1);
            }
            if (var1.isBlockOpaqueCube(var2, var3, var4 + 1)) {
                this.notifyWireNeighborsOfNeighborChange(var1, var2, var3 + 1, var4 + 1);
            } else {
                this.notifyWireNeighborsOfNeighborChange(var1, var2, var3 - 1, var4 + 1);
            }
        }
    }

    private int getMaxCurrentStrength(World var1, int var2, int var3, int var4, int var5) {
        if (var1.getBlockId(var2, var3, var4) != this.blockID) {
            return var5;
        }
        int var6 = var1.getBlockMetadata(var2, var3, var4);
        return var6 > var5 ? var6 : var5;
    }

    @Override
    public void onNeighborBlockChange(World var1, int var2, int var3, int var4, int var5) {
        if (!var1.multiplayerWorld) {
            int var6 = var1.getBlockMetadata(var2, var3, var4);
            boolean var7 = this.canPlaceBlockAt(var1, var2, var3, var4);
            if (!var7) {
                this.dropBlockAsItem(var1, var2, var3, var4, var6);
                var1.setBlockWithNotify(var2, var3, var4, 0);
            } else {
                this.updateAndPropagateCurrentStrength(var1, var2, var3, var4);
            }
            super.onNeighborBlockChange(var1, var2, var3, var4, var5);
        }
    }

    @Override
    public int idDropped(int var1, Random var2) {
        return Item.redstone.shiftedIndex;
    }

    @Override
    public boolean isIndirectlyPoweringTo(World var1, int var2, int var3, int var4, int var5) {
        return !this.wiresProvidePower ? false : this.isPoweringTo(var1, var2, var3, var4, var5);
    }

    @Override
    public boolean isPoweringTo(IBlockAccess var1, int var2, int var3, int var4, int var5) {
        boolean var9;
        if (!this.wiresProvidePower) {
            return false;
        }
        if (var1.getBlockMetadata(var2, var3, var4) == 0) {
            return false;
        }
        if (var5 == 1) {
            return true;
        }
        boolean var6 = BlockRedstoneWire.isPowerProviderOrWire(var1, var2 - 1, var3, var4) || !var1.isBlockOpaqueCube(var2 - 1, var3, var4) && BlockRedstoneWire.isPowerProviderOrWire(var1, var2 - 1, var3 - 1, var4);
        boolean var7 = BlockRedstoneWire.isPowerProviderOrWire(var1, var2 + 1, var3, var4) || !var1.isBlockOpaqueCube(var2 + 1, var3, var4) && BlockRedstoneWire.isPowerProviderOrWire(var1, var2 + 1, var3 - 1, var4);
        boolean var8 = BlockRedstoneWire.isPowerProviderOrWire(var1, var2, var3, var4 - 1) || !var1.isBlockOpaqueCube(var2, var3, var4 - 1) && BlockRedstoneWire.isPowerProviderOrWire(var1, var2, var3 - 1, var4 - 1);
        boolean bl = var9 = BlockRedstoneWire.isPowerProviderOrWire(var1, var2, var3, var4 + 1) || !var1.isBlockOpaqueCube(var2, var3, var4 + 1) && BlockRedstoneWire.isPowerProviderOrWire(var1, var2, var3 - 1, var4 + 1);
        if (!var1.isBlockOpaqueCube(var2, var3 + 1, var4)) {
            if (var1.isBlockOpaqueCube(var2 - 1, var3, var4) && BlockRedstoneWire.isPowerProviderOrWire(var1, var2 - 1, var3 + 1, var4)) {
                var6 = true;
            }
            if (var1.isBlockOpaqueCube(var2 + 1, var3, var4) && BlockRedstoneWire.isPowerProviderOrWire(var1, var2 + 1, var3 + 1, var4)) {
                var7 = true;
            }
            if (var1.isBlockOpaqueCube(var2, var3, var4 - 1) && BlockRedstoneWire.isPowerProviderOrWire(var1, var2, var3 + 1, var4 - 1)) {
                var8 = true;
            }
            if (var1.isBlockOpaqueCube(var2, var3, var4 + 1) && BlockRedstoneWire.isPowerProviderOrWire(var1, var2, var3 + 1, var4 + 1)) {
                var9 = true;
            }
        }
        if (!(var8 || var7 || var6 || var9 || var5 < 2 || var5 > 5)) {
            return true;
        }
        if (var5 == 2 && var8 && !var6 && !var7) {
            return true;
        }
        if (var5 == 3 && var9 && !var6 && !var7) {
            return true;
        }
        if (var5 == 4 && var6 && !var8 && !var9) {
            return true;
        }
        return var5 == 5 && var7 && !var8 && !var9;
    }

    @Override
    public boolean canProvidePower() {
        return this.wiresProvidePower;
    }

    @Override
    public void randomDisplayTick(World var1, int var2, int var3, int var4, Random var5) {
        int var6 = var1.getBlockMetadata(var2, var3, var4);
        if (var6 > 0) {
            double var7 = (double)var2 + 0.5 + ((double)var5.nextFloat() - 0.5) * 0.2;
            double var9 = (float)var3 + 0.0625f;
            double var11 = (double)var4 + 0.5 + ((double)var5.nextFloat() - 0.5) * 0.2;
            float var13 = (float)var6 / 15.0f;
            float var14 = var13 * 0.6f + 0.4f;
            if (var6 == 0) {
                var14 = 0.0f;
            }
            float var15 = var13 * var13 * 0.7f - 0.5f;
            float var16 = var13 * var13 * 0.6f - 0.7f;
            if (var15 < 0.0f) {
                var15 = 0.0f;
            }
            if (var16 < 0.0f) {
                var16 = 0.0f;
            }
            var1.spawnParticle("reddust", var7, var9, var11, var14, var15, var16);
        }
    }

    public static boolean isPowerProviderOrWire(IBlockAccess var0, int var1, int var2, int var3) {
        int var4 = var0.getBlockId(var1, var2, var3);
        if (var4 == Block.redstoneWire.blockID) {
            return true;
        }
        if (var4 == 0) {
            return false;
        }
        return Block.blocksList[var4].canProvidePower();
    }
}

