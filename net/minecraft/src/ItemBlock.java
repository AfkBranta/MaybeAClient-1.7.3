/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.Block;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;

public class ItemBlock
extends Item {
    public int blockID;

    public ItemBlock(int var1) {
        super(var1);
        this.blockID = var1 + 256;
        this.setIconIndex(Block.blocksList[var1 + 256].getBlockTextureFromSide(2));
    }

    @Override
    public boolean onItemUse(ItemStack var1, EntityPlayer var2, World var3, int var4, int var5, int var6, int var7) {
        if (var3.getBlockId(var4, var5, var6) == Block.snow.blockID) {
            var7 = 0;
        } else {
            if (var7 == 0) {
                --var5;
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
        }
        if (var1.stackSize == 0) {
            return false;
        }
        if (var3.canBlockBePlacedAt(this.blockID, var4, var5, var6, false)) {
            Block var8 = Block.blocksList[this.blockID];
            if (var3.setBlockAndMetadataWithNotify(var4, var5, var6, this.blockID, this.func_21012_a(var1.getItemDamage()))) {
                Block.blocksList[this.blockID].onBlockPlaced(var3, var4, var5, var6, var7);
                Block.blocksList[this.blockID].onBlockPlacedBy(var3, var4, var5, var6, var2);
                var3.playSoundEffect((float)var4 + 0.5f, (float)var5 + 0.5f, (float)var6 + 0.5f, var8.stepSound.func_1145_d(), (var8.stepSound.func_1147_b() + 1.0f) / 2.0f, var8.stepSound.func_1144_c() * 0.8f);
                --var1.stackSize;
            }
            return true;
        }
        return false;
    }

    @Override
    public String getItemNameIS(ItemStack var1) {
        return Block.blocksList[this.blockID].getBlockName();
    }

    @Override
    public String getItemName() {
        return Block.blocksList[this.blockID].getBlockName();
    }
}

