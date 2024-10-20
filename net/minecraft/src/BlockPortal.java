/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.Random;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Block;
import net.minecraft.src.BlockBreakable;
import net.minecraft.src.Entity;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.Material;
import net.minecraft.src.World;

public class BlockPortal
extends BlockBreakable {
    public BlockPortal(int var1, int var2) {
        super(var1, var2, Material.portal, false);
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World var1, int var2, int var3, int var4) {
        return null;
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess var1, int var2, int var3, int var4) {
        if (var1.getBlockId(var2 - 1, var3, var4) != this.blockID && var1.getBlockId(var2 + 1, var3, var4) != this.blockID) {
            float var5 = 0.125f;
            float var6 = 0.5f;
            this.setBlockBounds(0.5f - var5, 0.0f, 0.5f - var6, 0.5f + var5, 1.0f, 0.5f + var6);
        } else {
            float var5 = 0.5f;
            float var6 = 0.125f;
            this.setBlockBounds(0.5f - var5, 0.0f, 0.5f - var6, 0.5f + var5, 1.0f, 0.5f + var6);
        }
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    public boolean tryToCreatePortal(World var1, int var2, int var3, int var4) {
        int var8;
        int var5 = 0;
        int var6 = 0;
        if (var1.getBlockId(var2 - 1, var3, var4) == Block.obsidian.blockID || var1.getBlockId(var2 + 1, var3, var4) == Block.obsidian.blockID) {
            var5 = 1;
        }
        if (var1.getBlockId(var2, var3, var4 - 1) == Block.obsidian.blockID || var1.getBlockId(var2, var3, var4 + 1) == Block.obsidian.blockID) {
            var6 = 1;
        }
        System.out.println(String.valueOf(var5) + ", " + var6);
        if (var5 == var6) {
            return false;
        }
        if (var1.getBlockId(var2 - var5, var3, var4 - var6) == 0) {
            var2 -= var5;
            var4 -= var6;
        }
        int var7 = -1;
        while (var7 <= 2) {
            var8 = -1;
            while (var8 <= 3) {
                boolean var9;
                boolean bl = var9 = var7 == -1 || var7 == 2 || var8 == -1 || var8 == 3;
                if (var7 != -1 && var7 != 2 || var8 != -1 && var8 != 3) {
                    int var10 = var1.getBlockId(var2 + var5 * var7, var3 + var8, var4 + var6 * var7);
                    if (var9 ? var10 != Block.obsidian.blockID : var10 != 0 && var10 != Block.fire.blockID) {
                        return false;
                    }
                }
                ++var8;
            }
            ++var7;
        }
        var1.editingBlocks = true;
        var7 = 0;
        while (var7 < 2) {
            var8 = 0;
            while (var8 < 3) {
                var1.setBlockWithNotify(var2 + var5 * var7, var3 + var8, var4 + var6 * var7, Block.portal.blockID);
                ++var8;
            }
            ++var7;
        }
        var1.editingBlocks = false;
        return true;
    }

    @Override
    public void onNeighborBlockChange(World var1, int var2, int var3, int var4, int var5) {
        int var6 = 0;
        int var7 = 1;
        if (var1.getBlockId(var2 - 1, var3, var4) == this.blockID || var1.getBlockId(var2 + 1, var3, var4) == this.blockID) {
            var6 = 1;
            var7 = 0;
        }
        int var8 = var3;
        while (var1.getBlockId(var2, var8 - 1, var4) == this.blockID) {
            --var8;
        }
        if (var1.getBlockId(var2, var8 - 1, var4) != Block.obsidian.blockID) {
            var1.setBlockWithNotify(var2, var3, var4, 0);
        } else {
            int var9 = 1;
            while (var9 < 4 && var1.getBlockId(var2, var8 + var9, var4) == this.blockID) {
                ++var9;
            }
            if (var9 == 3 && var1.getBlockId(var2, var8 + var9, var4) == Block.obsidian.blockID) {
                boolean var11;
                boolean var10 = var1.getBlockId(var2 - 1, var3, var4) == this.blockID || var1.getBlockId(var2 + 1, var3, var4) == this.blockID;
                boolean bl = var11 = var1.getBlockId(var2, var3, var4 - 1) == this.blockID || var1.getBlockId(var2, var3, var4 + 1) == this.blockID;
                if (var10 && var11) {
                    var1.setBlockWithNotify(var2, var3, var4, 0);
                } else if (!(var1.getBlockId(var2 + var6, var3, var4 + var7) == Block.obsidian.blockID && var1.getBlockId(var2 - var6, var3, var4 - var7) == this.blockID || var1.getBlockId(var2 - var6, var3, var4 - var7) == Block.obsidian.blockID && var1.getBlockId(var2 + var6, var3, var4 + var7) == this.blockID)) {
                    var1.setBlockWithNotify(var2, var3, var4, 0);
                }
            } else {
                var1.setBlockWithNotify(var2, var3, var4, 0);
            }
        }
    }

    @Override
    public boolean shouldSideBeRendered(IBlockAccess var1, int var2, int var3, int var4, int var5) {
        return true;
    }

    @Override
    public int quantityDropped(Random var1) {
        return 0;
    }

    @Override
    public int getRenderBlockPass() {
        return 1;
    }

    @Override
    public void onEntityCollidedWithBlock(World var1, int var2, int var3, int var4, Entity var5) {
        if (!var1.multiplayerWorld) {
            var5.setInPortal();
        }
    }

    @Override
    public void randomDisplayTick(World var1, int var2, int var3, int var4, Random var5) {
        if (var5.nextInt(100) == 0) {
            var1.playSoundEffect((double)var2 + 0.5, (double)var3 + 0.5, (double)var4 + 0.5, "portal.portal", 1.0f, var5.nextFloat() * 0.4f + 0.8f);
        }
        int var6 = 0;
        while (var6 < 4) {
            double var7 = (float)var2 + var5.nextFloat();
            double var9 = (float)var3 + var5.nextFloat();
            double var11 = (float)var4 + var5.nextFloat();
            double var13 = 0.0;
            double var15 = 0.0;
            double var17 = 0.0;
            int var19 = var5.nextInt(2) * 2 - 1;
            var13 = ((double)var5.nextFloat() - 0.5) * 0.5;
            var15 = ((double)var5.nextFloat() - 0.5) * 0.5;
            var17 = ((double)var5.nextFloat() - 0.5) * 0.5;
            if (var1.getBlockId(var2 - 1, var3, var4) != this.blockID && var1.getBlockId(var2 + 1, var3, var4) != this.blockID) {
                var7 = (double)var2 + 0.5 + 0.25 * (double)var19;
                var13 = var5.nextFloat() * 2.0f * (float)var19;
            } else {
                var11 = (double)var4 + 0.5 + 0.25 * (double)var19;
                var17 = var5.nextFloat() * 2.0f * (float)var19;
            }
            var1.spawnParticle("portal", var7, var9, var11, var13, var15, var17);
            ++var6;
        }
    }
}

