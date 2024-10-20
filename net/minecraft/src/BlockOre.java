package net.minecraft.src;

import java.util.Random;
import net.minecraft.src.Block;
import net.minecraft.src.Item;
import net.minecraft.src.Material;

public class BlockOre
extends Block {
    public BlockOre(int var1, int var2) {
        super(var1, var2, Material.rock);
    }

    @Override
    public int idDropped(int var1, Random var2) {
        if (this.blockID == Block.oreCoal.blockID) {
            return Item.coal.shiftedIndex;
        }
        if (this.blockID == Block.oreDiamond.blockID) {
            return Item.diamond.shiftedIndex;
        }
        return this.blockID == Block.oreLapis.blockID ? Item.dyePowder.shiftedIndex : this.blockID;
    }

    @Override
    public int quantityDropped(Random var1) {
        return this.blockID == Block.oreLapis.blockID ? 4 + var1.nextInt(5) : 1;
    }

    @Override
    protected int damageDropped(int var1) {
        return this.blockID == Block.oreLapis.blockID ? 4 : 0;
    }
}

