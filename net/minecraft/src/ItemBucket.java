/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.Block;
import net.minecraft.src.EntityCow;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EnumMovingObjectType;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.MathHelper;
import net.minecraft.src.MovingObjectPosition;
import net.minecraft.src.Vec3D;
import net.minecraft.src.World;

public class ItemBucket
extends Item {
    private int isFull;

    public ItemBucket(int var1, int var2) {
        super(var1);
        this.maxStackSize = 1;
        this.isFull = var2;
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
        MovingObjectPosition var24 = var2.rayTraceBlocks_do(var13, var23, this.isFull == 0);
        if (var24 == null) {
            return var1;
        }
        if (var24.typeOfHit == EnumMovingObjectType.TILE) {
            int var25 = var24.blockX;
            int var26 = var24.blockY;
            int var27 = var24.blockZ;
            if (!var2.func_6466_a(var3, var25, var26, var27)) {
                return var1;
            }
            if (this.isFull == 0) {
                if (var2.getBlockMaterial(var25, var26, var27) == Material.water && var2.getBlockMetadata(var25, var26, var27) == 0) {
                    var2.setBlockWithNotify(var25, var26, var27, 0);
                    return new ItemStack(Item.bucketWater);
                }
                if (var2.getBlockMaterial(var25, var26, var27) == Material.lava && var2.getBlockMetadata(var25, var26, var27) == 0) {
                    var2.setBlockWithNotify(var25, var26, var27, 0);
                    return new ItemStack(Item.bucketLava);
                }
            } else {
                if (this.isFull < 0) {
                    return new ItemStack(Item.bucketEmpty);
                }
                if (var24.sideHit == 0) {
                    --var26;
                }
                if (var24.sideHit == 1) {
                    ++var26;
                }
                if (var24.sideHit == 2) {
                    --var27;
                }
                if (var24.sideHit == 3) {
                    ++var27;
                }
                if (var24.sideHit == 4) {
                    --var25;
                }
                if (var24.sideHit == 5) {
                    ++var25;
                }
                if (var2.isAirBlock(var25, var26, var27) || !var2.getBlockMaterial(var25, var26, var27).isSolid()) {
                    if (var2.worldProvider.isHellWorld && this.isFull == Block.waterMoving.blockID) {
                        var2.playSoundEffect(var7 + 0.5, var9 + 0.5, var11 + 0.5, "random.fizz", 0.5f, 2.6f + (var2.rand.nextFloat() - var2.rand.nextFloat()) * 0.8f);
                        int var28 = 0;
                        while (var28 < 8) {
                            var2.spawnParticle("largesmoke", (double)var25 + Math.random(), (double)var26 + Math.random(), (double)var27 + Math.random(), 0.0, 0.0, 0.0);
                            ++var28;
                        }
                    } else {
                        var2.setBlockAndMetadataWithNotify(var25, var26, var27, this.isFull, 0);
                    }
                    return new ItemStack(Item.bucketEmpty);
                }
            }
        } else if (this.isFull == 0 && var24.entityHit instanceof EntityCow) {
            return new ItemStack(Item.bucketMilk);
        }
        return var1;
    }
}

