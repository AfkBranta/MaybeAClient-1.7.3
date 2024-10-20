/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.Material;

public class MaterialLiquid
extends Material {
    @Override
    public boolean getIsLiquid() {
        return true;
    }

    @Override
    public boolean getIsSolid() {
        return false;
    }

    @Override
    public boolean isSolid() {
        return false;
    }
}

