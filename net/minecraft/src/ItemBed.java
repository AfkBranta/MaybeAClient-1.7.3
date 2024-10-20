package net.minecraft.src;

import net.minecraft.src.Block;
import net.minecraft.src.BlockBed;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.MathHelper;
import net.minecraft.src.World;

public class ItemBed
extends Item {
    public ItemBed(int var1) {
        super(var1);
    }

    @Override
    public boolean onItemUse(ItemStack var1, EntityPlayer var2, World var3, int var4, int var5, int var6, int var7) {
        if (var7 != 1) {
            return false;
        }
        ++var5;
        BlockBed var8 = (BlockBed)Block.blockBed;
        int var9 = MathHelper.floor_double((double)(var2.rotationYaw * 4.0f / 360.0f) + 0.5) & 3;
        int var10 = 0;
        int var11 = 0;
        if (var9 == 0) {
            var11 = 1;
        }
        if (var9 == 1) {
            var10 = -1;
        }
        if (var9 == 2) {
            var11 = -1;
        }
        if (var9 == 3) {
            var10 = 1;
        }
        if (var3.isAirBlock(var4, var5, var6) && var3.isAirBlock(var4 + var10, var5, var6 + var11) && var3.isBlockOpaqueCube(var4, var5 - 1, var6) && var3.isBlockOpaqueCube(var4 + var10, var5 - 1, var6 + var11)) {
            var3.setBlockAndMetadataWithNotify(var4, var5, var6, var8.blockID, var9);
            var3.setBlockAndMetadataWithNotify(var4 + var10, var5, var6 + var11, var8.blockID, var9 + 8);
            --var1.stackSize;
            return true;
        }
        return false;
    }
}

