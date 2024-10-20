/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.Block;
import net.minecraft.src.Material;

public class BlockSandStone
extends Block {
    public BlockSandStone(int var1) {
        super(var1, 192, Material.rock);
    }

    @Override
    public int getBlockTextureFromSide(int var1) {
        if (var1 == 1) {
            return this.blockIndexInTexture - 16;
        }
        return var1 == 0 ? this.blockIndexInTexture + 16 : this.blockIndexInTexture;
    }
}

