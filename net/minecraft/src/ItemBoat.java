/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.EntityBoat;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EnumMovingObjectType;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.MathHelper;
import net.minecraft.src.MovingObjectPosition;
import net.minecraft.src.Vec3D;
import net.minecraft.src.World;

public class ItemBoat
extends Item {
    public ItemBoat(int var1) {
        super(var1);
        this.maxStackSize = 1;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack var1, World var2, EntityPlayer var3) {
        float var20;
        float var17;
        double var21;
        float var16;
        float var4 = 1.0f;
        float var5 = var3.prevRotationPitch + (var3.rotationPitch - var3.prevRotationPitch) * var4;
        float var6 = var3.prevRotationYaw + (var3.rotationYaw - var3.prevRotationYaw) * var4;
        double var7 = var3.prevPosX + (var3.posX - var3.prevPosX) * (double)var4;
        double var9 = var3.prevPosY + (var3.posY - var3.prevPosY) * (double)var4 + 1.62 - (double)var3.yOffset;
        double var11 = var3.prevPosZ + (var3.posZ - var3.prevPosZ) * (double)var4;
        Vec3D var13 = Vec3D.createVector(var7, var9, var11);
        float var14 = MathHelper.cos(-var6 * ((float)Math.PI / 180) - (float)Math.PI);
        float var15 = MathHelper.sin(-var6 * ((float)Math.PI / 180) - (float)Math.PI);
        float var18 = var15 * (var16 = -MathHelper.cos(-var5 * ((float)Math.PI / 180)));
        Vec3D var23 = var13.addVector((double)var18 * (var21 = 5.0), (double)(var17 = MathHelper.sin(-var5 * ((float)Math.PI / 180))) * var21, (double)(var20 = var14 * var16) * var21);
        MovingObjectPosition var24 = var2.rayTraceBlocks_do(var13, var23, true);
        if (var24 == null) {
            return var1;
        }
        if (var24.typeOfHit == EnumMovingObjectType.TILE) {
            int var25 = var24.blockX;
            int var26 = var24.blockY;
            int var27 = var24.blockZ;
            if (!var2.multiplayerWorld) {
                var2.entityJoinedWorld(new EntityBoat(var2, (float)var25 + 0.5f, (float)var26 + 1.5f, (float)var27 + 0.5f));
            }
            --var1.stackSize;
        }
        return var1;
    }
}

