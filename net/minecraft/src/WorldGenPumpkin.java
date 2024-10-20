/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.Random;
import net.minecraft.src.Block;
import net.minecraft.src.World;
import net.minecraft.src.WorldGenerator;

public class WorldGenPumpkin
extends WorldGenerator {
    @Override
    public boolean generate(World var1, Random var2, int var3, int var4, int var5) {
        int var6 = 0;
        while (var6 < 64) {
            int var9;
            int var8;
            int var7 = var3 + var2.nextInt(8) - var2.nextInt(8);
            if (var1.isAirBlock(var7, var8 = var4 + var2.nextInt(4) - var2.nextInt(4), var9 = var5 + var2.nextInt(8) - var2.nextInt(8)) && var1.getBlockId(var7, var8 - 1, var9) == Block.grass.blockID && Block.pumpkin.canPlaceBlockAt(var1, var7, var8, var9)) {
                var1.setBlockAndMetadata(var7, var8, var9, Block.pumpkin.blockID, var2.nextInt(4));
            }
            ++var6;
        }
        return true;
    }
}

