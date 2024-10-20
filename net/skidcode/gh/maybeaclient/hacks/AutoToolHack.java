/*
 * Decompiled with CFR 0.152.
 */
package net.skidcode.gh.maybeaclient.hacks;

import net.minecraft.src.Block;
import net.minecraft.src.ItemStack;
import net.skidcode.gh.maybeaclient.hacks.Hack;
import net.skidcode.gh.maybeaclient.hacks.category.Category;

public class AutoToolHack
extends Hack {
    public static AutoToolHack instance;

    public AutoToolHack() {
        super("AutoTool", "Automatically selects tools from hotbar", 0, Category.MISC);
        instance = this;
    }

    public static int getBestSlot(Block block) {
        int bestSlot = AutoToolHack.mc.thePlayer.inventory.currentItem;
        ItemStack stack = AutoToolHack.mc.thePlayer.inventory.mainInventory[bestSlot];
        float bestStrength = 0.1f;
        if (stack != null) {
            bestStrength = stack.getStrVsBlock(block);
        }
        int i = 0;
        while (i < 9) {
            float strength;
            stack = AutoToolHack.mc.thePlayer.inventory.mainInventory[i];
            if (stack != null && (strength = stack.getStrVsBlock(block)) > bestStrength) {
                bestStrength = strength;
                bestSlot = i;
            }
            ++i;
        }
        return bestSlot;
    }
}

