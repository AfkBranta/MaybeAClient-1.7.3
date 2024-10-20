/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.Block;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.MathHelper;
import net.minecraft.src.World;

public class ItemDoor
extends Item {
    private Material field_321_a;

    public ItemDoor(int var1, Material var2) {
        super(var1);
        this.field_321_a = var2;
        this.maxStackSize = 1;
    }

    @Override
    public boolean onItemUse(ItemStack var1, EntityPlayer var2, World var3, int var4, int var5, int var6, int var7) {
        if (var7 != 1) {
            return false;
        }
        Block var8 = this.field_321_a == Material.wood ? Block.doorWood : Block.doorSteel;
        if (!var8.canPlaceBlockAt(var3, var4, ++var5, var6)) {
            return false;
        }
        int var9 = MathHelper.floor_double((double)((var2.rotationYaw + 180.0f) * 4.0f / 360.0f) - 0.5) & 3;
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
        int var12 = (var3.isBlockOpaqueCube(var4 - var10, var5, var6 - var11) ? 1 : 0) + (var3.isBlockOpaqueCube(var4 - var10, var5 + 1, var6 - var11) ? 1 : 0);
        int var13 = (var3.isBlockOpaqueCube(var4 + var10, var5, var6 + var11) ? 1 : 0) + (var3.isBlockOpaqueCube(var4 + var10, var5 + 1, var6 + var11) ? 1 : 0);
        boolean var14 = var3.getBlockId(var4 - var10, var5, var6 - var11) == var8.blockID || var3.getBlockId(var4 - var10, var5 + 1, var6 - var11) == var8.blockID;
        boolean var15 = var3.getBlockId(var4 + var10, var5, var6 + var11) == var8.blockID || var3.getBlockId(var4 + var10, var5 + 1, var6 + var11) == var8.blockID;
        boolean var16 = false;
        if (var14 && !var15) {
            var16 = true;
        } else if (var13 > var12) {
            var16 = true;
        }
        if (var16) {
            var9 = var9 - 1 & 3;
            var9 += 4;
        }
        var3.setBlockWithNotify(var4, var5, var6, var8.blockID);
        var3.setBlockMetadataWithNotify(var4, var5, var6, var9);
        var3.setBlockWithNotify(var4, var5 + 1, var6, var8.blockID);
        var3.setBlockMetadataWithNotify(var4, var5 + 1, var6, var9 + 8);
        --var1.stackSize;
        return true;
    }
}

