/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.Block;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Material;
import net.minecraft.src.World;

public class BlockWorkbench
extends Block {
    protected BlockWorkbench(int var1) {
        super(var1, Material.wood);
        this.blockIndexInTexture = 59;
    }

    @Override
    public int getBlockTextureFromSide(int var1) {
        if (var1 == 1) {
            return this.blockIndexInTexture - 16;
        }
        if (var1 == 0) {
            return Block.planks.getBlockTextureFromSide(0);
        }
        return var1 != 2 && var1 != 4 ? this.blockIndexInTexture : this.blockIndexInTexture + 1;
    }

    @Override
    public boolean blockActivated(World var1, int var2, int var3, int var4, EntityPlayer var5) {
        if (var1.multiplayerWorld) {
            return true;
        }
        var5.displayWorkbenchGUI(var2, var3, var4);
        return true;
    }
}

