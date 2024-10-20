/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.Random;
import net.minecraft.src.Block;
import net.minecraft.src.EntityFallingSand;
import net.minecraft.src.Material;
import net.minecraft.src.World;

public class BlockSand
extends Block {
    public static boolean fallInstantly = false;

    public BlockSand(int var1, int var2) {
        super(var1, var2, Material.sand);
    }

    @Override
    public void onBlockAdded(World var1, int var2, int var3, int var4) {
        var1.scheduleBlockUpdate(var2, var3, var4, this.blockID, this.tickRate());
    }

    @Override
    public void onNeighborBlockChange(World var1, int var2, int var3, int var4, int var5) {
        var1.scheduleBlockUpdate(var2, var3, var4, this.blockID, this.tickRate());
    }

    @Override
    public void updateTick(World var1, int var2, int var3, int var4, Random var5) {
        this.tryToFall(var1, var2, var3, var4);
    }

    private void tryToFall(World var1, int var2, int var3, int var4) {
        if (BlockSand.canFallBelow(var1, var2, var3 - 1, var4) && var3 >= 0) {
            int var8 = 32;
            if (!fallInstantly && var1.checkChunksExist(var2 - var8, var3 - var8, var4 - var8, var2 + var8, var3 + var8, var4 + var8)) {
                EntityFallingSand var9 = new EntityFallingSand(var1, (float)var2 + 0.5f, (float)var3 + 0.5f, (float)var4 + 0.5f, this.blockID);
                var1.entityJoinedWorld(var9);
            } else {
                var1.setBlockWithNotify(var2, var3, var4, 0);
                while (BlockSand.canFallBelow(var1, var2, var3 - 1, var4) && var3 > 0) {
                    --var3;
                }
                if (var3 > 0) {
                    var1.setBlockWithNotify(var2, var3, var4, this.blockID);
                }
            }
        }
    }

    @Override
    public int tickRate() {
        return 3;
    }

    public static boolean canFallBelow(World var0, int var1, int var2, int var3) {
        int var4 = var0.getBlockId(var1, var2, var3);
        if (var4 == 0) {
            return true;
        }
        if (var4 == Block.fire.blockID) {
            return true;
        }
        Material var5 = Block.blocksList[var4].blockMaterial;
        if (var5 == Material.water) {
            return true;
        }
        return var5 == Material.lava;
    }
}

