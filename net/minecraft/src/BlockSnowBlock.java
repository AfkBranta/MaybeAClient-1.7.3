package net.minecraft.src;

import java.util.Random;
import net.minecraft.src.Block;
import net.minecraft.src.EnumSkyBlock;
import net.minecraft.src.Item;
import net.minecraft.src.Material;
import net.minecraft.src.World;

public class BlockSnowBlock
extends Block {
    protected BlockSnowBlock(int var1, int var2) {
        super(var1, var2, Material.builtSnow);
        this.setTickOnLoad(true);
    }

    @Override
    public int idDropped(int var1, Random var2) {
        return Item.snowball.shiftedIndex;
    }

    @Override
    public int quantityDropped(Random var1) {
        return 4;
    }

    @Override
    public void updateTick(World var1, int var2, int var3, int var4, Random var5) {
        if (var1.getSavedLightValue(EnumSkyBlock.Block, var2, var3, var4) > 11) {
            this.dropBlockAsItem(var1, var2, var3, var4, var1.getBlockMetadata(var2, var3, var4));
            var1.setBlockWithNotify(var2, var3, var4, 0);
        }
    }
}

