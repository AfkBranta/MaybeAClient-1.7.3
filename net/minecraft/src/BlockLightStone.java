/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.Random;
import net.minecraft.src.Block;
import net.minecraft.src.Item;
import net.minecraft.src.Material;

public class BlockLightStone
extends Block {
    public BlockLightStone(int var1, int var2, Material var3) {
        super(var1, var2, var3);
    }

    @Override
    public int idDropped(int var1, Random var2) {
        return Item.lightStoneDust.shiftedIndex;
    }
}

