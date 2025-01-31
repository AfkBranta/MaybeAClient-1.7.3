/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.Block;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.MathHelper;
import net.minecraft.src.TileEntitySign;
import net.minecraft.src.World;

public class ItemSign
extends Item {
    public ItemSign(int var1) {
        super(var1);
        this.maxStackSize = 1;
    }

    @Override
    public boolean onItemUse(ItemStack var1, EntityPlayer var2, World var3, int var4, int var5, int var6, int var7) {
        if (var7 == 0) {
            return false;
        }
        if (!var3.getBlockMaterial(var4, var5, var6).isSolid()) {
            return false;
        }
        if (var7 == 1) {
            ++var5;
        }
        if (var7 == 2) {
            --var6;
        }
        if (var7 == 3) {
            ++var6;
        }
        if (var7 == 4) {
            --var4;
        }
        if (var7 == 5) {
            ++var4;
        }
        if (!Block.signPost.canPlaceBlockAt(var3, var4, var5, var6)) {
            return false;
        }
        if (var7 == 1) {
            var3.setBlockAndMetadataWithNotify(var4, var5, var6, Block.signPost.blockID, MathHelper.floor_double((double)((var2.rotationYaw + 180.0f) * 16.0f / 360.0f) + 0.5) & 0xF);
        } else {
            var3.setBlockAndMetadataWithNotify(var4, var5, var6, Block.signWall.blockID, var7);
        }
        --var1.stackSize;
        TileEntitySign var8 = (TileEntitySign)var3.getBlockTileEntity(var4, var5, var6);
        if (var8 != null) {
            var2.displayGUIEditSign(var8);
        }
        return true;
    }
}

