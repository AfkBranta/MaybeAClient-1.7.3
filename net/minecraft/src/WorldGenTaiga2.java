package net.minecraft.src;

import java.util.Random;
import net.minecraft.src.Block;
import net.minecraft.src.World;
import net.minecraft.src.WorldGenerator;

public class WorldGenTaiga2
extends WorldGenerator {
    @Override
    public boolean generate(World var1, Random var2, int var3, int var4, int var5) {
        int var6 = var2.nextInt(4) + 6;
        int var7 = 1 + var2.nextInt(2);
        int var8 = var6 - var7;
        int var9 = 2 + var2.nextInt(2);
        boolean var10 = true;
        if (var4 >= 1 && var4 + var6 + 1 <= 128) {
            int var15;
            int var13;
            int var21;
            int var11 = var4;
            while (var11 <= var4 + 1 + var6 && var10) {
                boolean var12 = true;
                var21 = var11 - var4 < var7 ? 0 : var9;
                var13 = var3 - var21;
                while (var13 <= var3 + var21 && var10) {
                    int var14 = var5 - var21;
                    while (var14 <= var5 + var21 && var10) {
                        if (var11 >= 0 && var11 < 128) {
                            var15 = var1.getBlockId(var13, var11, var14);
                            if (var15 != 0 && var15 != Block.leaves.blockID) {
                                var10 = false;
                            }
                        } else {
                            var10 = false;
                        }
                        ++var14;
                    }
                    ++var13;
                }
                ++var11;
            }
            if (!var10) {
                return false;
            }
            var11 = var1.getBlockId(var3, var4 - 1, var5);
            if ((var11 == Block.grass.blockID || var11 == Block.dirt.blockID) && var4 < 128 - var6 - 1) {
                int var17;
                int var16;
                var1.setBlock(var3, var4 - 1, var5, Block.dirt.blockID);
                var21 = var2.nextInt(2);
                var13 = 1;
                int var22 = 0;
                var15 = 0;
                while (var15 <= var8) {
                    var16 = var4 + var6 - var15;
                    var17 = var3 - var21;
                    while (var17 <= var3 + var21) {
                        int var18 = var17 - var3;
                        int var19 = var5 - var21;
                        while (var19 <= var5 + var21) {
                            int var20 = var19 - var5;
                            if (!(Math.abs(var18) == var21 && Math.abs(var20) == var21 && var21 > 0 || Block.opaqueCubeLookup[var1.getBlockId(var17, var16, var19)])) {
                                var1.setBlockAndMetadata(var17, var16, var19, Block.leaves.blockID, 1);
                            }
                            ++var19;
                        }
                        ++var17;
                    }
                    if (var21 >= var13) {
                        var21 = var22;
                        var22 = 1;
                        if (++var13 > var9) {
                            var13 = var9;
                        }
                    } else {
                        ++var21;
                    }
                    ++var15;
                }
                var15 = var2.nextInt(3);
                var16 = 0;
                while (var16 < var6 - var15) {
                    var17 = var1.getBlockId(var3, var4 + var16, var5);
                    if (var17 == 0 || var17 == Block.leaves.blockID) {
                        var1.setBlockAndMetadata(var3, var4 + var16, var5, Block.wood.blockID, 1);
                    }
                    ++var16;
                }
                return true;
            }
            return false;
        }
        return false;
    }
}

