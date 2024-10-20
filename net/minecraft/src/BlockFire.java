/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.Random;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Block;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.Material;
import net.minecraft.src.World;

public class BlockFire
extends Block {
    private int[] chanceToEncourageFire = new int[256];
    private int[] abilityToCatchFire = new int[256];

    protected BlockFire(int var1, int var2) {
        super(var1, var2, Material.fire);
        this.setBurnRate(Block.planks.blockID, 5, 20);
        this.setBurnRate(Block.wood.blockID, 5, 5);
        this.setBurnRate(Block.leaves.blockID, 30, 60);
        this.setBurnRate(Block.bookShelf.blockID, 30, 20);
        this.setBurnRate(Block.tnt.blockID, 15, 100);
        this.setBurnRate(Block.cloth.blockID, 30, 60);
        this.setTickOnLoad(true);
    }

    private void setBurnRate(int var1, int var2, int var3) {
        this.chanceToEncourageFire[var1] = var2;
        this.abilityToCatchFire[var1] = var3;
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
        return 3;
    }

    @Override
    public int quantityDropped(Random var1) {
        return 0;
    }

    @Override
    public int tickRate() {
        return 10;
    }

    @Override
    public void updateTick(World var1, int var2, int var3, int var4, Random var5) {
        boolean var6 = var1.getBlockId(var2, var3 - 1, var4) == Block.bloodStone.blockID;
        int var7 = var1.getBlockMetadata(var2, var3, var4);
        if (var7 < 15) {
            var1.setBlockMetadataWithNotify(var2, var3, var4, var7 + 1);
            var1.scheduleBlockUpdate(var2, var3, var4, this.blockID, this.tickRate());
        }
        if (!var6 && !this.func_263_h(var1, var2, var3, var4)) {
            if (!var1.isBlockOpaqueCube(var2, var3 - 1, var4) || var7 > 3) {
                var1.setBlockWithNotify(var2, var3, var4, 0);
            }
        } else if (!var6 && !this.canBlockCatchFire(var1, var2, var3 - 1, var4) && var7 == 15 && var5.nextInt(4) == 0) {
            var1.setBlockWithNotify(var2, var3, var4, 0);
        } else {
            if (var7 % 2 == 0 && var7 > 2) {
                this.tryToCatchBlockOnFire(var1, var2 + 1, var3, var4, 300, var5);
                this.tryToCatchBlockOnFire(var1, var2 - 1, var3, var4, 300, var5);
                this.tryToCatchBlockOnFire(var1, var2, var3 - 1, var4, 250, var5);
                this.tryToCatchBlockOnFire(var1, var2, var3 + 1, var4, 250, var5);
                this.tryToCatchBlockOnFire(var1, var2, var3, var4 - 1, 300, var5);
                this.tryToCatchBlockOnFire(var1, var2, var3, var4 + 1, 300, var5);
                int var8 = var2 - 1;
                while (var8 <= var2 + 1) {
                    int var9 = var4 - 1;
                    while (var9 <= var4 + 1) {
                        int var10 = var3 - 1;
                        while (var10 <= var3 + 4) {
                            if (var8 != var2 || var10 != var3 || var9 != var4) {
                                int var12;
                                int var11 = 100;
                                if (var10 > var3 + 1) {
                                    var11 += (var10 - (var3 + 1)) * 100;
                                }
                                if ((var12 = this.getChanceOfNeighborsEncouragingFire(var1, var8, var10, var9)) > 0 && var5.nextInt(var11) <= var12) {
                                    var1.setBlockWithNotify(var8, var10, var9, this.blockID);
                                }
                            }
                            ++var10;
                        }
                        ++var9;
                    }
                    ++var8;
                }
            }
            if (var7 == 15) {
                this.tryToCatchBlockOnFire(var1, var2 + 1, var3, var4, 1, var5);
                this.tryToCatchBlockOnFire(var1, var2 - 1, var3, var4, 1, var5);
                this.tryToCatchBlockOnFire(var1, var2, var3 - 1, var4, 1, var5);
                this.tryToCatchBlockOnFire(var1, var2, var3 + 1, var4, 1, var5);
                this.tryToCatchBlockOnFire(var1, var2, var3, var4 - 1, 1, var5);
                this.tryToCatchBlockOnFire(var1, var2, var3, var4 + 1, 1, var5);
            }
        }
    }

    private void tryToCatchBlockOnFire(World var1, int var2, int var3, int var4, int var5, Random var6) {
        int var7 = this.abilityToCatchFire[var1.getBlockId(var2, var3, var4)];
        if (var6.nextInt(var5) < var7) {
            boolean var8;
            boolean bl = var8 = var1.getBlockId(var2, var3, var4) == Block.tnt.blockID;
            if (var6.nextInt(2) == 0) {
                var1.setBlockWithNotify(var2, var3, var4, this.blockID);
            } else {
                var1.setBlockWithNotify(var2, var3, var4, 0);
            }
            if (var8) {
                Block.tnt.onBlockDestroyedByPlayer(var1, var2, var3, var4, 0);
            }
        }
    }

    private boolean func_263_h(World var1, int var2, int var3, int var4) {
        if (this.canBlockCatchFire(var1, var2 + 1, var3, var4)) {
            return true;
        }
        if (this.canBlockCatchFire(var1, var2 - 1, var3, var4)) {
            return true;
        }
        if (this.canBlockCatchFire(var1, var2, var3 - 1, var4)) {
            return true;
        }
        if (this.canBlockCatchFire(var1, var2, var3 + 1, var4)) {
            return true;
        }
        if (this.canBlockCatchFire(var1, var2, var3, var4 - 1)) {
            return true;
        }
        return this.canBlockCatchFire(var1, var2, var3, var4 + 1);
    }

    private int getChanceOfNeighborsEncouragingFire(World var1, int var2, int var3, int var4) {
        int var5 = 0;
        if (!var1.isAirBlock(var2, var3, var4)) {
            return 0;
        }
        int var6 = this.getChanceToEncourageFire(var1, var2 + 1, var3, var4, var5);
        var6 = this.getChanceToEncourageFire(var1, var2 - 1, var3, var4, var6);
        var6 = this.getChanceToEncourageFire(var1, var2, var3 - 1, var4, var6);
        var6 = this.getChanceToEncourageFire(var1, var2, var3 + 1, var4, var6);
        var6 = this.getChanceToEncourageFire(var1, var2, var3, var4 - 1, var6);
        var6 = this.getChanceToEncourageFire(var1, var2, var3, var4 + 1, var6);
        return var6;
    }

    @Override
    public boolean isCollidable() {
        return false;
    }

    public boolean canBlockCatchFire(IBlockAccess var1, int var2, int var3, int var4) {
        return this.chanceToEncourageFire[var1.getBlockId(var2, var3, var4)] > 0;
    }

    public int getChanceToEncourageFire(World var1, int var2, int var3, int var4, int var5) {
        int var6 = this.chanceToEncourageFire[var1.getBlockId(var2, var3, var4)];
        return var6 > var5 ? var6 : var5;
    }

    @Override
    public boolean canPlaceBlockAt(World var1, int var2, int var3, int var4) {
        return var1.isBlockOpaqueCube(var2, var3 - 1, var4) || this.func_263_h(var1, var2, var3, var4);
    }

    @Override
    public void onNeighborBlockChange(World var1, int var2, int var3, int var4, int var5) {
        if (!var1.isBlockOpaqueCube(var2, var3 - 1, var4) && !this.func_263_h(var1, var2, var3, var4)) {
            var1.setBlockWithNotify(var2, var3, var4, 0);
        }
    }

    @Override
    public void onBlockAdded(World var1, int var2, int var3, int var4) {
        if (var1.getBlockId(var2, var3 - 1, var4) != Block.obsidian.blockID || !Block.portal.tryToCreatePortal(var1, var2, var3, var4)) {
            if (!var1.isBlockOpaqueCube(var2, var3 - 1, var4) && !this.func_263_h(var1, var2, var3, var4)) {
                var1.setBlockWithNotify(var2, var3, var4, 0);
            } else {
                var1.scheduleBlockUpdate(var2, var3, var4, this.blockID, this.tickRate());
            }
        }
    }

    @Override
    public void randomDisplayTick(World var1, int var2, int var3, int var4, Random var5) {
        block12: {
            block11: {
                float var9;
                float var8;
                float var7;
                int var6;
                if (var5.nextInt(24) == 0) {
                    var1.playSoundEffect((float)var2 + 0.5f, (float)var3 + 0.5f, (float)var4 + 0.5f, "fire.fire", 1.0f + var5.nextFloat(), var5.nextFloat() * 0.7f + 0.3f);
                }
                if (var1.isBlockOpaqueCube(var2, var3 - 1, var4) || Block.fire.canBlockCatchFire(var1, var2, var3 - 1, var4)) break block11;
                if (Block.fire.canBlockCatchFire(var1, var2 - 1, var3, var4)) {
                    var6 = 0;
                    while (var6 < 2) {
                        var7 = (float)var2 + var5.nextFloat() * 0.1f;
                        var8 = (float)var3 + var5.nextFloat();
                        var9 = (float)var4 + var5.nextFloat();
                        var1.spawnParticle("largesmoke", var7, var8, var9, 0.0, 0.0, 0.0);
                        ++var6;
                    }
                }
                if (Block.fire.canBlockCatchFire(var1, var2 + 1, var3, var4)) {
                    var6 = 0;
                    while (var6 < 2) {
                        var7 = (float)(var2 + 1) - var5.nextFloat() * 0.1f;
                        var8 = (float)var3 + var5.nextFloat();
                        var9 = (float)var4 + var5.nextFloat();
                        var1.spawnParticle("largesmoke", var7, var8, var9, 0.0, 0.0, 0.0);
                        ++var6;
                    }
                }
                if (Block.fire.canBlockCatchFire(var1, var2, var3, var4 - 1)) {
                    var6 = 0;
                    while (var6 < 2) {
                        var7 = (float)var2 + var5.nextFloat();
                        var8 = (float)var3 + var5.nextFloat();
                        var9 = (float)var4 + var5.nextFloat() * 0.1f;
                        var1.spawnParticle("largesmoke", var7, var8, var9, 0.0, 0.0, 0.0);
                        ++var6;
                    }
                }
                if (Block.fire.canBlockCatchFire(var1, var2, var3, var4 + 1)) {
                    var6 = 0;
                    while (var6 < 2) {
                        var7 = (float)var2 + var5.nextFloat();
                        var8 = (float)var3 + var5.nextFloat();
                        var9 = (float)(var4 + 1) - var5.nextFloat() * 0.1f;
                        var1.spawnParticle("largesmoke", var7, var8, var9, 0.0, 0.0, 0.0);
                        ++var6;
                    }
                }
                if (!Block.fire.canBlockCatchFire(var1, var2, var3 + 1, var4)) break block12;
                var6 = 0;
                while (var6 < 2) {
                    var7 = (float)var2 + var5.nextFloat();
                    var8 = (float)(var3 + 1) - var5.nextFloat() * 0.1f;
                    var9 = (float)var4 + var5.nextFloat();
                    var1.spawnParticle("largesmoke", var7, var8, var9, 0.0, 0.0, 0.0);
                    ++var6;
                }
                break block12;
            }
            int var6 = 0;
            while (var6 < 3) {
                float var7 = (float)var2 + var5.nextFloat();
                float var8 = (float)var3 + var5.nextFloat() * 0.5f + 0.5f;
                float var9 = (float)var4 + var5.nextFloat();
                var1.spawnParticle("largesmoke", var7, var8, var9, 0.0, 0.0, 0.0);
                ++var6;
            }
        }
    }
}

