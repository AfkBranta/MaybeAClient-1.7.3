package net.minecraft.src;

import java.util.Random;
import net.minecraft.src.Block;
import net.minecraft.src.Material;

public class BlockBookshelf
extends Block {
    public BlockBookshelf(int var1, int var2) {
        super(var1, var2, Material.wood);
    }

    @Override
    public int getBlockTextureFromSide(int var1) {
        return var1 <= 1 ? 4 : this.blockIndexInTexture;
    }

    @Override
    public int quantityDropped(Random var1) {
        return 0;
    }
}

