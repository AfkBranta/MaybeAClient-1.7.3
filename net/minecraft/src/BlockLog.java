/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.Random;
import net.minecraft.src.AchievementList;
import net.minecraft.src.Block;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Material;
import net.minecraft.src.World;

public class BlockLog
extends Block {
    protected BlockLog(int var1) {
        super(var1, Material.wood);
        this.blockIndexInTexture = 20;
    }

    @Override
    public int quantityDropped(Random var1) {
        return 1;
    }

    @Override
    public int idDropped(int var1, Random var2) {
        return Block.wood.blockID;
    }

    @Override
    public void harvestBlock(World var1, EntityPlayer var2, int var3, int var4, int var5, int var6) {
        super.harvestBlock(var1, var2, var3, var4, var5, var6);
        var2.addStat(AchievementList.field_25198_c, 1);
    }

    @Override
    public void onBlockRemoval(World var1, int var2, int var3, int var4) {
        int var5 = 4;
        int var6 = var5 + 1;
        if (var1.checkChunksExist(var2 - var6, var3 - var6, var4 - var6, var2 + var6, var3 + var6, var4 + var6)) {
            int var7 = -var5;
            while (var7 <= var5) {
                int var8 = -var5;
                while (var8 <= var5) {
                    int var9 = -var5;
                    while (var9 <= var5) {
                        int var11;
                        int var10 = var1.getBlockId(var2 + var7, var3 + var8, var4 + var9);
                        if (var10 == Block.leaves.blockID && ((var11 = var1.getBlockMetadata(var2 + var7, var3 + var8, var4 + var9)) & 4) == 0) {
                            var1.setBlockMetadata(var2 + var7, var3 + var8, var4 + var9, var11 | 4);
                        }
                        ++var9;
                    }
                    ++var8;
                }
                ++var7;
            }
        }
    }

    @Override
    public int getBlockTextureFromSideAndMetadata(int var1, int var2) {
        if (var1 == 1) {
            return 21;
        }
        if (var1 == 0) {
            return 21;
        }
        if (var2 == 1) {
            return 116;
        }
        return var2 == 2 ? 117 : 20;
    }

    @Override
    protected int damageDropped(int var1) {
        return var1;
    }
}

