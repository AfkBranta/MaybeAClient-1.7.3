/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.AxisAlignedBB;

public interface ICamera {
    public boolean isBoundingBoxInFrustum(AxisAlignedBB var1);

    public void setPosition(double var1, double var3, double var5);
}

