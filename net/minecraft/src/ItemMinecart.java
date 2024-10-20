package net.minecraft.src;

import net.minecraft.src.Block;
import net.minecraft.src.EntityMinecart;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;

public class ItemMinecart
extends Item {
    public int minecartType;

    public ItemMinecart(int var1, int var2) {
        super(var1);
        this.maxStackSize = 1;
        this.minecartType = var2;
    }

    @Override
    public boolean onItemUse(ItemStack var1, EntityPlayer var2, World var3, int var4, int var5, int var6, int var7) {
        int var8 = var3.getBlockId(var4, var5, var6);
        if (var8 == Block.minecartTrack.blockID) {
            if (!var3.multiplayerWorld) {
                var3.entityJoinedWorld(new EntityMinecart(var3, (float)var4 + 0.5f, (float)var5 + 0.5f, (float)var6 + 0.5f, this.minecartType));
            }
            --var1.stackSize;
            return true;
        }
        return false;
    }
}

