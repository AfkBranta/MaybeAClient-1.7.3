package net.minecraft.src;

import java.util.Random;
import net.minecraft.src.Block;
import net.minecraft.src.MathHelper;
import net.minecraft.src.World;
import net.minecraft.src.WorldGenerator;

public class WorldGenMinable
extends WorldGenerator {
    private int minableBlockId;
    private int numberOfBlocks;

    public WorldGenMinable(int var1, int var2) {
        this.minableBlockId = var1;
        this.numberOfBlocks = var2;
    }

    @Override
    public boolean generate(World var1, Random var2, int var3, int var4, int var5) {
        float var6 = var2.nextFloat() * (float)Math.PI;
        double var7 = (float)(var3 + 8) + MathHelper.sin(var6) * (float)this.numberOfBlocks / 8.0f;
        double var9 = (float)(var3 + 8) - MathHelper.sin(var6) * (float)this.numberOfBlocks / 8.0f;
        double var11 = (float)(var5 + 8) + MathHelper.cos(var6) * (float)this.numberOfBlocks / 8.0f;
        double var13 = (float)(var5 + 8) - MathHelper.cos(var6) * (float)this.numberOfBlocks / 8.0f;
        double var15 = var4 + var2.nextInt(3) + 2;
        double var17 = var4 + var2.nextInt(3) + 2;
        int var19 = 0;
        while (var19 <= this.numberOfBlocks) {
            double var20 = var7 + (var9 - var7) * (double)var19 / (double)this.numberOfBlocks;
            double var22 = var15 + (var17 - var15) * (double)var19 / (double)this.numberOfBlocks;
            double var24 = var11 + (var13 - var11) * (double)var19 / (double)this.numberOfBlocks;
            double var26 = var2.nextDouble() * (double)this.numberOfBlocks / 16.0;
            double var28 = (double)(MathHelper.sin((float)var19 * (float)Math.PI / (float)this.numberOfBlocks) + 1.0f) * var26 + 1.0;
            double var30 = (double)(MathHelper.sin((float)var19 * (float)Math.PI / (float)this.numberOfBlocks) + 1.0f) * var26 + 1.0;
            int var32 = (int)(var20 - var28 / 2.0);
            int var33 = (int)(var22 - var30 / 2.0);
            int var34 = (int)(var24 - var28 / 2.0);
            int var35 = (int)(var20 + var28 / 2.0);
            int var36 = (int)(var22 + var30 / 2.0);
            int var37 = (int)(var24 + var28 / 2.0);
            int var38 = var32;
            while (var38 <= var35) {
                double var39 = ((double)var38 + 0.5 - var20) / (var28 / 2.0);
                if (var39 * var39 < 1.0) {
                    int var41 = var33;
                    while (var41 <= var36) {
                        double var42 = ((double)var41 + 0.5 - var22) / (var30 / 2.0);
                        if (var39 * var39 + var42 * var42 < 1.0) {
                            int var44 = var34;
                            while (var44 <= var37) {
                                double var45 = ((double)var44 + 0.5 - var24) / (var28 / 2.0);
                                if (var39 * var39 + var42 * var42 + var45 * var45 < 1.0 && var1.getBlockId(var38, var41, var44) == Block.stone.blockID) {
                                    var1.setBlock(var38, var41, var44, this.minableBlockId);
                                }
                                ++var44;
                            }
                        }
                        ++var41;
                    }
                }
                ++var38;
            }
            ++var19;
        }
        return true;
    }
}

