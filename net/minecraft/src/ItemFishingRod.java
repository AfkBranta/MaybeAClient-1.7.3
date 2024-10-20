package net.minecraft.src;

import net.minecraft.src.EntityFish;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;

public class ItemFishingRod
extends Item {
    public ItemFishingRod(int var1) {
        super(var1);
        this.setMaxDamage(64);
    }

    @Override
    public boolean isFull3D() {
        return true;
    }

    @Override
    public boolean shouldRotateAroundWhenRendering() {
        return true;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack var1, World var2, EntityPlayer var3) {
        if (var3.fishEntity != null) {
            int var4 = var3.fishEntity.catchFish();
            var1.func_25190_a(var4, var3);
            var3.swingItem();
        } else {
            var2.playSoundAtEntity(var3, "random.bow", 0.5f, 0.4f / (itemRand.nextFloat() * 0.4f + 0.8f));
            if (!var2.multiplayerWorld) {
                var2.entityJoinedWorld(new EntityFish(var2, var3));
            }
            var3.swingItem();
        }
        return var1;
    }
}

