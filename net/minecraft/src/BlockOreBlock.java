/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.Block;
import net.minecraft.src.Material;

public class BlockOreBlock
extends Block {
    public BlockOreBlock(int var1, int var2) {
        super(var1, Material.iron);
        this.blockIndexInTexture = var2;
    }

    @Override
    public int getBlockTextureFromSide(int var1) {
        return this.blockIndexInTexture;
    }
}

