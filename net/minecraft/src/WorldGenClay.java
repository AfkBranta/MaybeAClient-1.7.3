/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.Random;
import net.minecraft.src.Block;
import net.minecraft.src.Material;
import net.minecraft.src.MathHelper;
import net.minecraft.src.World;
import net.minecraft.src.WorldGenerator;

public class WorldGenClay
extends WorldGenerator {
    private int clayBlockId;
    private int numberOfBlocks;

    public WorldGenClay(int var1) {
        this.clayBlockId = Block.blockClay.blockID;
        this.numberOfBlocks = var1;
    }

    @Override
    public boolean generate(World var1, Random var2, int var3, int var4, int var5) {
        if (var1.getBlockMaterial(var3, var4, var5) != Material.water) {
            return false;
        }
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
            while (var32 <= (int)(var20 + var28 / 2.0)) {
                int var33 = (int)(var22 - var30 / 2.0);
                while (var33 <= (int)(var22 + var30 / 2.0)) {
                    int var34 = (int)(var24 - var28 / 2.0);
                    while (var34 <= (int)(var24 + var28 / 2.0)) {
                        int var41;
                        double var35 = ((double)var32 + 0.5 - var20) / (var28 / 2.0);
                        double var37 = ((double)var33 + 0.5 - var22) / (var30 / 2.0);
                        double var39 = ((double)var34 + 0.5 - var24) / (var28 / 2.0);
                        if (var35 * var35 + var37 * var37 + var39 * var39 < 1.0 && (var41 = var1.getBlockId(var32, var33, var34)) == Block.sand.blockID) {
                            var1.setBlock(var32, var33, var34, this.clayBlockId);
                        }
                        ++var34;
                    }
                    ++var33;
                }
                ++var32;
            }
            ++var19;
        }
        return true;
    }
}

