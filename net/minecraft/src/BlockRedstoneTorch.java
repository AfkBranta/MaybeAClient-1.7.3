package net.minecraft.src;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.minecraft.src.Block;
import net.minecraft.src.BlockTorch;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.RedstoneUpdateInfo;
import net.minecraft.src.World;

public class BlockRedstoneTorch
extends BlockTorch {
    private boolean torchActive = false;
    private static List torchUpdates = new ArrayList();

    @Override
    public int getBlockTextureFromSideAndMetadata(int var1, int var2) {
        return var1 == 1 ? Block.redstoneWire.getBlockTextureFromSideAndMetadata(var1, var2) : super.getBlockTextureFromSideAndMetadata(var1, var2);
    }

    private boolean checkForBurnout(World var1, int var2, int var3, int var4, boolean var5) {
        if (var5) {
            torchUpdates.add(new RedstoneUpdateInfo(var2, var3, var4, var1.getWorldTime()));
        }
        int var6 = 0;
        int var7 = 0;
        while (var7 < torchUpdates.size()) {
            RedstoneUpdateInfo var8 = (RedstoneUpdateInfo)torchUpdates.get(var7);
            if (var8.x == var2 && var8.y == var3 && var8.z == var4 && ++var6 >= 8) {
                return true;
            }
            ++var7;
        }
        return false;
    }

    protected BlockRedstoneTorch(int var1, int var2, boolean var3) {
        super(var1, var2);
        this.torchActive = var3;
        this.setTickOnLoad(true);
    }

    @Override
    public int tickRate() {
        return 2;
    }

    @Override
    public void onBlockAdded(World var1, int var2, int var3, int var4) {
        if (var1.getBlockMetadata(var2, var3, var4) == 0) {
            super.onBlockAdded(var1, var2, var3, var4);
        }
        if (this.torchActive) {
            var1.notifyBlocksOfNeighborChange(var2, var3 - 1, var4, this.blockID);
            var1.notifyBlocksOfNeighborChange(var2, var3 + 1, var4, this.blockID);
            var1.notifyBlocksOfNeighborChange(var2 - 1, var3, var4, this.blockID);
            var1.notifyBlocksOfNeighborChange(var2 + 1, var3, var4, this.blockID);
            var1.notifyBlocksOfNeighborChange(var2, var3, var4 - 1, this.blockID);
            var1.notifyBlocksOfNeighborChange(var2, var3, var4 + 1, this.blockID);
        }
    }

    @Override
    public void onBlockRemoval(World var1, int var2, int var3, int var4) {
        if (this.torchActive) {
            var1.notifyBlocksOfNeighborChange(var2, var3 - 1, var4, this.blockID);
            var1.notifyBlocksOfNeighborChange(var2, var3 + 1, var4, this.blockID);
            var1.notifyBlocksOfNeighborChange(var2 - 1, var3, var4, this.blockID);
            var1.notifyBlocksOfNeighborChange(var2 + 1, var3, var4, this.blockID);
            var1.notifyBlocksOfNeighborChange(var2, var3, var4 - 1, this.blockID);
            var1.notifyBlocksOfNeighborChange(var2, var3, var4 + 1, this.blockID);
        }
    }

    @Override
    public boolean isPoweringTo(IBlockAccess var1, int var2, int var3, int var4, int var5) {
        if (!this.torchActive) {
            return false;
        }
        int var6 = var1.getBlockMetadata(var2, var3, var4);
        if (var6 == 5 && var5 == 1) {
            return false;
        }
        if (var6 == 3 && var5 == 3) {
            return false;
        }
        if (var6 == 4 && var5 == 2) {
            return false;
        }
        if (var6 == 1 && var5 == 5) {
            return false;
        }
        return var6 != 2 || var5 != 4;
    }

    private boolean func_22026_h(World var1, int var2, int var3, int var4) {
        int var5 = var1.getBlockMetadata(var2, var3, var4);
        if (var5 == 5 && var1.isBlockIndirectlyProvidingPowerTo(var2, var3 - 1, var4, 0)) {
            return true;
        }
        if (var5 == 3 && var1.isBlockIndirectlyProvidingPowerTo(var2, var3, var4 - 1, 2)) {
            return true;
        }
        if (var5 == 4 && var1.isBlockIndirectlyProvidingPowerTo(var2, var3, var4 + 1, 3)) {
            return true;
        }
        if (var5 == 1 && var1.isBlockIndirectlyProvidingPowerTo(var2 - 1, var3, var4, 4)) {
            return true;
        }
        return var5 == 2 && var1.isBlockIndirectlyProvidingPowerTo(var2 + 1, var3, var4, 5);
    }

    @Override
    public void updateTick(World var1, int var2, int var3, int var4, Random var5) {
        boolean var6 = this.func_22026_h(var1, var2, var3, var4);
        while (torchUpdates.size() > 0 && var1.getWorldTime() - ((RedstoneUpdateInfo)BlockRedstoneTorch.torchUpdates.get((int)0)).updateTime > 100L) {
            torchUpdates.remove(0);
        }
        if (this.torchActive) {
            if (var6) {
                var1.setBlockAndMetadataWithNotify(var2, var3, var4, Block.torchRedstoneIdle.blockID, var1.getBlockMetadata(var2, var3, var4));
                if (this.checkForBurnout(var1, var2, var3, var4, true)) {
                    var1.playSoundEffect((float)var2 + 0.5f, (float)var3 + 0.5f, (float)var4 + 0.5f, "random.fizz", 0.5f, 2.6f + (var1.rand.nextFloat() - var1.rand.nextFloat()) * 0.8f);
                    int var7 = 0;
                    while (var7 < 5) {
                        double var8 = (double)var2 + var5.nextDouble() * 0.6 + 0.2;
                        double var10 = (double)var3 + var5.nextDouble() * 0.6 + 0.2;
                        double var12 = (double)var4 + var5.nextDouble() * 0.6 + 0.2;
                        var1.spawnParticle("smoke", var8, var10, var12, 0.0, 0.0, 0.0);
                        ++var7;
                    }
                }
            }
        } else if (!var6 && !this.checkForBurnout(var1, var2, var3, var4, false)) {
            var1.setBlockAndMetadataWithNotify(var2, var3, var4, Block.torchRedstoneActive.blockID, var1.getBlockMetadata(var2, var3, var4));
        }
    }

    @Override
    public void onNeighborBlockChange(World var1, int var2, int var3, int var4, int var5) {
        super.onNeighborBlockChange(var1, var2, var3, var4, var5);
        var1.scheduleBlockUpdate(var2, var3, var4, this.blockID, this.tickRate());
    }

    @Override
    public boolean isIndirectlyPoweringTo(World var1, int var2, int var3, int var4, int var5) {
        return var5 == 0 ? this.isPoweringTo(var1, var2, var3, var4, var5) : false;
    }

    @Override
    public int idDropped(int var1, Random var2) {
        return Block.torchRedstoneActive.blockID;
    }

    @Override
    public boolean canProvidePower() {
        return true;
    }

    @Override
    public void randomDisplayTick(World var1, int var2, int var3, int var4, Random var5) {
        if (this.torchActive) {
            int var6 = var1.getBlockMetadata(var2, var3, var4);
            double var7 = (double)((float)var2 + 0.5f) + (double)(var5.nextFloat() - 0.5f) * 0.2;
            double var9 = (double)((float)var3 + 0.7f) + (double)(var5.nextFloat() - 0.5f) * 0.2;
            double var11 = (double)((float)var4 + 0.5f) + (double)(var5.nextFloat() - 0.5f) * 0.2;
            double var13 = 0.22f;
            double var15 = 0.27f;
            if (var6 == 1) {
                var1.spawnParticle("reddust", var7 - var15, var9 + var13, var11, 0.0, 0.0, 0.0);
            } else if (var6 == 2) {
                var1.spawnParticle("reddust", var7 + var15, var9 + var13, var11, 0.0, 0.0, 0.0);
            } else if (var6 == 3) {
                var1.spawnParticle("reddust", var7, var9 + var13, var11 - var15, 0.0, 0.0, 0.0);
            } else if (var6 == 4) {
                var1.spawnParticle("reddust", var7, var9 + var13, var11 + var15, 0.0, 0.0, 0.0);
            } else {
                var1.spawnParticle("reddust", var7, var9, var11, 0.0, 0.0, 0.0);
            }
        }
    }
}

