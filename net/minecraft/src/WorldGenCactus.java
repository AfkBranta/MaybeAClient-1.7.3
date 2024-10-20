package net.minecraft.src;

import java.util.Random;
import net.minecraft.src.Block;
import net.minecraft.src.World;
import net.minecraft.src.WorldGenerator;

public class WorldGenCactus
extends WorldGenerator {
    @Override
    public boolean generate(World var1, Random var2, int var3, int var4, int var5) {
        int var6 = 0;
        while (var6 < 10) {
            int var9;
            int var8;
            int var7 = var3 + var2.nextInt(8) - var2.nextInt(8);
            if (var1.isAirBlock(var7, var8 = var4 + var2.nextInt(4) - var2.nextInt(4), var9 = var5 + var2.nextInt(8) - var2.nextInt(8))) {
                int var10 = 1 + var2.nextInt(var2.nextInt(3) + 1);
                int var11 = 0;
                while (var11 < var10) {
                    if (Block.cactus.canBlockStay(var1, var7, var8 + var11, var9)) {
                        var1.setBlock(var7, var8 + var11, var9, Block.cactus.blockID);
                    }
                    ++var11;
                }
            }
            ++var6;
        }
        return true;
    }
}

