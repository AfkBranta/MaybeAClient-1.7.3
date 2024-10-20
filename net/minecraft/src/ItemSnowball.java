package net.minecraft.src;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntitySnowball;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;

public class ItemSnowball
extends Item {
    public ItemSnowball(int var1) {
        super(var1);
        this.maxStackSize = 16;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack var1, World var2, EntityPlayer var3) {
        --var1.stackSize;
        var2.playSoundAtEntity(var3, "random.bow", 0.5f, 0.4f / (itemRand.nextFloat() * 0.4f + 0.8f));
        if (!var2.multiplayerWorld) {
            var2.entityJoinedWorld(new EntitySnowball(var2, var3));
        }
        return var1;
    }
}

