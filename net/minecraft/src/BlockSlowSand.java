/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Block;
import net.minecraft.src.Entity;
import net.minecraft.src.Material;
import net.minecraft.src.World;
import net.skidcode.gh.maybeaclient.hacks.AntiSlowdownHack;

public class BlockSlowSand
extends Block {
    public BlockSlowSand(int var1, int var2) {
        super(var1, var2, Material.sand);
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World var1, int var2, int var3, int var4) {
        float var5 = 0.125f;
        return AxisAlignedBB.getBoundingBoxFromPool(var2, var3, var4, var2 + 1, (float)(var3 + 1) - var5, var4 + 1);
    }

    @Override
    public void onEntityCollidedWithBlock(World var1, int var2, int var3, int var4, Entity var5) {
        if (!AntiSlowdownHack.instance.status) {
            var5.motionX *= 0.4;
            var5.motionZ *= 0.4;
        }
    }
}

