/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.Random;
import net.minecraft.src.Block;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.Material;
import net.minecraft.src.World;
import net.skidcode.gh.maybeaclient.hacks.XRayHack;

public class BlockStep
extends Block {
    public static final String[] field_22037_a = new String[]{"stone", "sand", "wood", "cobble"};
    private boolean blockType;

    public BlockStep(int var1, boolean var2) {
        super(var1, 6, Material.rock);
        this.blockType = var2;
        if (!var2) {
            this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.5f, 1.0f);
        }
        this.setLightOpacity(255);
    }

    @Override
    public int getBlockTextureFromSideAndMetadata(int var1, int var2) {
        if (var2 == 0) {
            return var1 <= 1 ? 6 : 5;
        }
        if (var2 == 1) {
            if (var1 == 0) {
                return 208;
            }
            return var1 == 1 ? 176 : 192;
        }
        if (var2 == 2) {
            return 4;
        }
        return var2 == 3 ? 16 : 6;
    }

    @Override
    public int getBlockTextureFromSide(int var1) {
        return this.getBlockTextureFromSideAndMetadata(var1, 0);
    }

    @Override
    public boolean isOpaqueCube() {
        return this.blockType;
    }

    @Override
    public void onBlockAdded(World var1, int var2, int var3, int var4) {
        int var7;
        if (this != Block.stairSingle) {
            super.onBlockAdded(var1, var2, var3, var4);
        }
        int var5 = var1.getBlockId(var2, var3 - 1, var4);
        int var6 = var1.getBlockMetadata(var2, var3, var4);
        if (var6 == (var7 = var1.getBlockMetadata(var2, var3 - 1, var4)) && var5 == BlockStep.stairSingle.blockID) {
            var1.setBlockWithNotify(var2, var3, var4, 0);
            var1.setBlockAndMetadataWithNotify(var2, var3 - 1, var4, Block.stairDouble.blockID, var6);
        }
    }

    @Override
    public int idDropped(int var1, Random var2) {
        return Block.stairSingle.blockID;
    }

    @Override
    public int quantityDropped(Random var1) {
        return this.blockType ? 2 : 1;
    }

    @Override
    protected int damageDropped(int var1) {
        return var1;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return this.blockType;
    }

    @Override
    public boolean shouldSideBeRendered(IBlockAccess var1, int var2, int var3, int var4, int var5) {
        if (XRayHack.INSTANCE.status && !XRayHack.INSTANCE.mode.currentMode.equalsIgnoreCase("Opacity")) {
            return XRayHack.INSTANCE.blockChooser.blocks[this.blockID];
        }
        if (this != Block.stairSingle) {
            super.shouldSideBeRendered(var1, var2, var3, var4, var5);
        }
        if (var5 == 1) {
            return true;
        }
        if (!super.shouldSideBeRendered(var1, var2, var3, var4, var5)) {
            return false;
        }
        if (var5 == 0) {
            return true;
        }
        return var1.getBlockId(var2, var3, var4) != this.blockID;
    }
}

