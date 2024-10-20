/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.Block;
import net.minecraft.src.EnumToolMaterial;
import net.minecraft.src.ItemTool;
import net.minecraft.src.Material;

public class ItemPickaxe
extends ItemTool {
    private static Block[] blocksEffectiveAgainst = new Block[]{Block.cobblestone, Block.stairDouble, Block.stairSingle, Block.stone, Block.sandStone, Block.cobblestoneMossy, Block.oreIron, Block.blockSteel, Block.oreCoal, Block.blockGold, Block.oreGold, Block.oreDiamond, Block.blockDiamond, Block.ice, Block.bloodStone, Block.oreLapis, Block.blockLapis};

    protected ItemPickaxe(int var1, EnumToolMaterial var2) {
        super(var1, 2, var2, blocksEffectiveAgainst);
    }

    @Override
    public boolean canHarvestBlock(Block var1) {
        if (var1 == Block.obsidian) {
            return this.toolMaterial.getHarvestLevel() == 3;
        }
        if (var1 != Block.blockDiamond && var1 != Block.oreDiamond) {
            if (var1 != Block.blockGold && var1 != Block.oreGold) {
                if (var1 != Block.blockSteel && var1 != Block.oreIron) {
                    if (var1 != Block.blockLapis && var1 != Block.oreLapis) {
                        if (var1 != Block.oreRedstone && var1 != Block.oreRedstoneGlowing) {
                            if (var1.blockMaterial == Material.rock) {
                                return true;
                            }
                            return var1.blockMaterial == Material.iron;
                        }
                        return this.toolMaterial.getHarvestLevel() >= 2;
                    }
                    return this.toolMaterial.getHarvestLevel() >= 1;
                }
                return this.toolMaterial.getHarvestLevel() >= 1;
            }
            return this.toolMaterial.getHarvestLevel() >= 2;
        }
        return this.toolMaterial.getHarvestLevel() >= 2;
    }
}

