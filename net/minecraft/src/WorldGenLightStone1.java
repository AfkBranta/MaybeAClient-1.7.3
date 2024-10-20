/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.Random;
import net.minecraft.src.Block;
import net.minecraft.src.World;
import net.minecraft.src.WorldGenerator;

public class WorldGenLightStone1
extends WorldGenerator {
    @Override
    public boolean generate(World var1, Random var2, int var3, int var4, int var5) {
        if (!var1.isAirBlock(var3, var4, var5)) {
            return false;
        }
        if (var1.getBlockId(var3, var4 + 1, var5) != Block.bloodStone.blockID) {
            return false;
        }
        var1.setBlockWithNotify(var3, var4, var5, Block.lightStone.blockID);
        int var6 = 0;
        while (var6 < 1500) {
            int var9;
            int var8;
            int var7 = var3 + var2.nextInt(8) - var2.nextInt(8);
            if (var1.getBlockId(var7, var8 = var4 - var2.nextInt(12), var9 = var5 + var2.nextInt(8) - var2.nextInt(8)) == 0) {
                int var10 = 0;
                int var11 = 0;
                while (var11 < 6) {
                    int var12 = 0;
                    if (var11 == 0) {
                        var12 = var1.getBlockId(var7 - 1, var8, var9);
                    }
                    if (var11 == 1) {
                        var12 = var1.getBlockId(var7 + 1, var8, var9);
                    }
                    if (var11 == 2) {
                        var12 = var1.getBlockId(var7, var8 - 1, var9);
                    }
                    if (var11 == 3) {
                        var12 = var1.getBlockId(var7, var8 + 1, var9);
                    }
                    if (var11 == 4) {
                        var12 = var1.getBlockId(var7, var8, var9 - 1);
                    }
                    if (var11 == 5) {
                        var12 = var1.getBlockId(var7, var8, var9 + 1);
                    }
                    if (var12 == Block.lightStone.blockID) {
                        ++var10;
                    }
                    ++var11;
                }
                if (var10 == 1) {
                    var1.setBlockWithNotify(var7, var8, var9, Block.lightStone.blockID);
                }
            }
            ++var6;
        }
        return true;
    }
}

