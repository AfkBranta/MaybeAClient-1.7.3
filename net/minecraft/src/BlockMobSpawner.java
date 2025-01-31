package net.minecraft.src;

import java.util.Random;
import net.minecraft.src.BlockContainer;
import net.minecraft.src.Material;
import net.minecraft.src.TileEntity;
import net.minecraft.src.TileEntityMobSpawner;

public class BlockMobSpawner
extends BlockContainer {
    protected BlockMobSpawner(int var1, int var2) {
        super(var1, var2, Material.rock);
    }

    @Override
    protected TileEntity getBlockEntity() {
        return new TileEntityMobSpawner();
    }

    @Override
    public int idDropped(int var1, Random var2) {
        return 0;
    }

    @Override
    public int quantityDropped(Random var1) {
        return 0;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }
}

