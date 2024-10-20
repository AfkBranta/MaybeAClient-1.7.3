/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.Block;
import net.minecraft.src.EntityItem;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EnumToolMaterial;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.World;

public class ItemHoe
extends Item {
    public ItemHoe(int var1, EnumToolMaterial var2) {
        super(var1);
        this.maxStackSize = 1;
        this.setMaxDamage(var2.getMaxUses());
    }

    @Override
    public boolean onItemUse(ItemStack var1, EntityPlayer var2, World var3, int var4, int var5, int var6, int var7) {
        int var8 = var3.getBlockId(var4, var5, var6);
        Material var9 = var3.getBlockMaterial(var4, var5 + 1, var6);
        if ((var9.isSolid() || var8 != Block.grass.blockID) && var8 != Block.dirt.blockID) {
            return false;
        }
        Block var10 = Block.tilledField;
        var3.playSoundEffect((float)var4 + 0.5f, (float)var5 + 0.5f, (float)var6 + 0.5f, var10.stepSound.func_1145_d(), (var10.stepSound.func_1147_b() + 1.0f) / 2.0f, var10.stepSound.func_1144_c() * 0.8f);
        if (var3.multiplayerWorld) {
            return true;
        }
        var3.setBlockWithNotify(var4, var5, var6, var10.blockID);
        var1.func_25190_a(1, var2);
        if (var3.rand.nextInt(8) == 0 && var8 == Block.grass.blockID) {
            int var11 = 1;
            int var12 = 0;
            while (var12 < var11) {
                float var13 = 0.7f;
                float var14 = var3.rand.nextFloat() * var13 + (1.0f - var13) * 0.5f;
                float var15 = 1.2f;
                float var16 = var3.rand.nextFloat() * var13 + (1.0f - var13) * 0.5f;
                EntityItem var17 = new EntityItem(var3, (float)var4 + var14, (float)var5 + var15, (float)var6 + var16, new ItemStack(Item.seeds));
                var17.delayBeforeCanPickup = 10;
                var3.entityJoinedWorld(var17);
                ++var12;
            }
        }
        return true;
    }

    @Override
    public boolean isFull3D() {
        return true;
    }
}

