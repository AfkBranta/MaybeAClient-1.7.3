/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.Block;
import net.minecraft.src.Material;
import net.minecraft.src.World;

public class BlockSponge
extends Block {
    protected BlockSponge(int var1) {
        super(var1, Material.sponge);
        this.blockIndexInTexture = 48;
    }

    @Override
    public void onBlockAdded(World var1, int var2, int var3, int var4) {
        int var5 = 2;
        int var6 = var2 - var5;
        while (var6 <= var2 + var5) {
            int var7 = var3 - var5;
            while (var7 <= var3 + var5) {
                int var8 = var4 - var5;
                while (var8 <= var4 + var5) {
                    var1.getBlockMaterial(var6, var7, var8);
                    ++var8;
                }
                ++var7;
            }
            ++var6;
        }
    }

    @Override
    public void onBlockRemoval(World var1, int var2, int var3, int var4) {
        int var5 = 2;
        int var6 = var2 - var5;
        while (var6 <= var2 + var5) {
            int var7 = var3 - var5;
            while (var7 <= var3 + var5) {
                int var8 = var4 - var5;
                while (var8 <= var4 + var5) {
                    var1.notifyBlocksOfNeighborChange(var6, var7, var8, var1.getBlockId(var6, var7, var8));
                    ++var8;
                }
                ++var7;
            }
            ++var6;
        }
    }
}

