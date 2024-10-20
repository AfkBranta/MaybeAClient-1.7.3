package net.minecraft.src;

import net.minecraft.src.Block;
import net.minecraft.src.Material;

public class BlockCloth
extends Block {
    public BlockCloth() {
        super(35, 64, Material.cloth);
    }

    @Override
    public int getBlockTextureFromSideAndMetadata(int var1, int var2) {
        if (var2 == 0) {
            return this.blockIndexInTexture;
        }
        var2 = ~(var2 & 0xF);
        return 113 + ((var2 & 8) >> 3) + (var2 & 7) * 16;
    }

    @Override
    protected int damageDropped(int var1) {
        return var1;
    }

    public static int func_21034_c(int var0) {
        return ~var0 & 0xF;
    }

    public static int func_21035_d(int var0) {
        return ~var0 & 0xF;
    }
}

