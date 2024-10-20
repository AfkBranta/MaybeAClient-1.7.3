/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.client.Minecraft;
import net.minecraft.src.Block;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.PlayerController;
import net.minecraft.src.Session;
import net.minecraft.src.World;

public class PlayerControllerTest
extends PlayerController {
    public PlayerControllerTest(Minecraft var1) {
        super(var1);
        this.field_1064_b = true;
    }

    @Override
    public void func_6473_b(EntityPlayer var1) {
        int var2 = 0;
        while (var2 < 9) {
            if (var1.inventory.mainInventory[var2] == null) {
                this.mc.thePlayer.inventory.mainInventory[var2] = new ItemStack((Block)Session.registeredBlocksList.get(var2));
            } else {
                this.mc.thePlayer.inventory.mainInventory[var2].stackSize = 1;
            }
            ++var2;
        }
    }

    @Override
    public boolean shouldDrawHUD() {
        return false;
    }

    @Override
    public void func_717_a(World var1) {
        super.func_717_a(var1);
    }

    @Override
    public void updateController() {
    }
}

