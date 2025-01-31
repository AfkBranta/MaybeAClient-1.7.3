/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.Block;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EnumToolMaterial;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;

public class ItemTool
extends Item {
    private Block[] blocksEffectiveAgainst;
    private float efficiencyOnProperMaterial = 4.0f;
    private int damageVsEntity;
    protected EnumToolMaterial toolMaterial;

    protected ItemTool(int var1, int var2, EnumToolMaterial var3, Block[] var4) {
        super(var1);
        this.toolMaterial = var3;
        this.blocksEffectiveAgainst = var4;
        this.maxStackSize = 1;
        this.setMaxDamage(var3.getMaxUses());
        this.efficiencyOnProperMaterial = var3.getEfficiencyOnProperMaterial();
        this.damageVsEntity = var2 + var3.getDamageVsEntity();
    }

    @Override
    public float getStrVsBlock(ItemStack var1, Block var2) {
        int var3 = 0;
        while (var3 < this.blocksEffectiveAgainst.length) {
            if (this.blocksEffectiveAgainst[var3] == var2) {
                return this.efficiencyOnProperMaterial;
            }
            ++var3;
        }
        return 1.0f;
    }

    @Override
    public boolean hitEntity(ItemStack var1, EntityLiving var2, EntityLiving var3) {
        var1.func_25190_a(2, var3);
        return true;
    }

    @Override
    public boolean func_25008_a(ItemStack var1, int var2, int var3, int var4, int var5, EntityLiving var6) {
        var1.func_25190_a(1, var6);
        return true;
    }

    @Override
    public int getDamageVsEntity(Entity var1) {
        return this.damageVsEntity;
    }

    @Override
    public boolean isFull3D() {
        return true;
    }
}

