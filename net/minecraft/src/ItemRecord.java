package net.minecraft.src;

import net.minecraft.src.Block;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;

public class ItemRecord
extends Item {
    private String recordName;

    protected ItemRecord(int var1, String var2) {
        super(var1);
        this.recordName = var2;
        this.maxStackSize = 1;
    }

    @Override
    public boolean onItemUse(ItemStack var1, EntityPlayer var2, World var3, int var4, int var5, int var6, int var7) {
        if (var3.getBlockId(var4, var5, var6) == Block.jukebox.blockID && var3.getBlockMetadata(var4, var5, var6) == 0) {
            var3.setBlockMetadataWithNotify(var4, var5, var6, this.shiftedIndex - Item.record13.shiftedIndex + 1);
            var3.playRecord(this.recordName, var4, var5, var6);
            --var1.stackSize;
            return true;
        }
        return false;
    }
}

