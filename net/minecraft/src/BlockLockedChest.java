/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.Random;
import net.minecraft.src.Block;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.Material;
import net.minecraft.src.World;

public class BlockLockedChest
extends Block {
    protected BlockLockedChest(int var1) {
        super(var1, Material.wood);
        this.blockIndexInTexture = 26;
    }

    @Override
    public int getBlockTexture(IBlockAccess var1, int var2, int var3, int var4, int var5) {
        if (var5 == 1) {
            return this.blockIndexInTexture - 1;
        }
        if (var5 == 0) {
            return this.blockIndexInTexture - 1;
        }
        int var6 = var1.getBlockId(var2, var3, var4 - 1);
        int var7 = var1.getBlockId(var2, var3, var4 + 1);
        int var8 = var1.getBlockId(var2 - 1, var3, var4);
        int var9 = var1.getBlockId(var2 + 1, var3, var4);
        int var10 = 3;
        if (Block.opaqueCubeLookup[var6] && !Block.opaqueCubeLookup[var7]) {
            var10 = 3;
        }
        if (Block.opaqueCubeLookup[var7] && !Block.opaqueCubeLookup[var6]) {
            var10 = 2;
        }
        if (Block.opaqueCubeLookup[var8] && !Block.opaqueCubeLookup[var9]) {
            var10 = 5;
        }
        if (Block.opaqueCubeLookup[var9] && !Block.opaqueCubeLookup[var8]) {
            var10 = 4;
        }
        return var5 == var10 ? this.blockIndexInTexture + 1 : this.blockIndexInTexture;
    }

    @Override
    public int getBlockTextureFromSide(int var1) {
        if (var1 == 1) {
            return this.blockIndexInTexture - 1;
        }
        if (var1 == 0) {
            return this.blockIndexInTexture - 1;
        }
        return var1 == 3 ? this.blockIndexInTexture + 1 : this.blockIndexInTexture;
    }

    @Override
    public boolean canPlaceBlockAt(World var1, int var2, int var3, int var4) {
        return true;
    }

    @Override
    public void updateTick(World var1, int var2, int var3, int var4, Random var5) {
        var1.setBlockWithNotify(var2, var3, var4, 0);
    }
}

