package net.minecraft.src;

import net.minecraft.src.Block;
import net.minecraft.src.BlockCloth;
import net.minecraft.src.BlockCrops;
import net.minecraft.src.BlockSapling;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntitySheep;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;

public class ItemDye
extends Item {
    public static final String[] dyeColors = new String[]{"black", "red", "green", "brown", "blue", "purple", "cyan", "silver", "gray", "pink", "lime", "yellow", "lightBlue", "magenta", "orange", "white"};

    public ItemDye(int var1) {
        super(var1);
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
    }

    @Override
    public int getIconIndex(ItemStack var1) {
        int var2 = var1.getItemDamage();
        return this.iconIndex + var2 % 8 * 16 + var2 / 8;
    }

    @Override
    public String getItemNameIS(ItemStack var1) {
        return String.valueOf(super.getItemName()) + "." + dyeColors[var1.getItemDamage()];
    }

    @Override
    public boolean onItemUse(ItemStack var1, EntityPlayer var2, World var3, int var4, int var5, int var6, int var7) {
        if (var1.getItemDamage() == 15) {
            int var8 = var3.getBlockId(var4, var5, var6);
            if (var8 == Block.sapling.blockID) {
                ((BlockSapling)Block.sapling).growTree(var3, var4, var5, var6, var3.rand);
                --var1.stackSize;
                return true;
            }
            if (var8 == Block.crops.blockID) {
                ((BlockCrops)Block.crops).fertilize(var3, var4, var5, var6);
                --var1.stackSize;
                return true;
            }
        }
        return false;
    }

    @Override
    public void saddleEntity(ItemStack var1, EntityLiving var2) {
        if (var2 instanceof EntitySheep) {
            EntitySheep var3 = (EntitySheep)var2;
            int var4 = BlockCloth.func_21034_c(var1.getItemDamage());
            if (!var3.getSheared() && var3.getFleeceColor() != var4) {
                var3.setFleeceColor(var4);
                --var1.stackSize;
            }
        }
    }
}

