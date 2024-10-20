/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.Random;
import net.minecraft.src.Block;
import net.minecraft.src.BlockContainer;
import net.minecraft.src.EntityArrow;
import net.minecraft.src.EntityEgg;
import net.minecraft.src.EntityItem;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntitySnowball;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.MathHelper;
import net.minecraft.src.TileEntity;
import net.minecraft.src.TileEntityDispenser;
import net.minecraft.src.World;

public class BlockDispenser
extends BlockContainer {
    protected BlockDispenser(int var1) {
        super(var1, Material.rock);
        this.blockIndexInTexture = 45;
    }

    @Override
    public int tickRate() {
        return 4;
    }

    @Override
    public int idDropped(int var1, Random var2) {
        return Block.dispenser.blockID;
    }

    @Override
    public void onBlockAdded(World var1, int var2, int var3, int var4) {
        super.onBlockAdded(var1, var2, var3, var4);
        this.setDispenserDefaultDirection(var1, var2, var3, var4);
    }

    private void setDispenserDefaultDirection(World var1, int var2, int var3, int var4) {
        int var5 = var1.getBlockId(var2, var3, var4 - 1);
        int var6 = var1.getBlockId(var2, var3, var4 + 1);
        int var7 = var1.getBlockId(var2 - 1, var3, var4);
        int var8 = var1.getBlockId(var2 + 1, var3, var4);
        int var9 = 3;
        if (Block.opaqueCubeLookup[var5] && !Block.opaqueCubeLookup[var6]) {
            var9 = 3;
        }
        if (Block.opaqueCubeLookup[var6] && !Block.opaqueCubeLookup[var5]) {
            var9 = 2;
        }
        if (Block.opaqueCubeLookup[var7] && !Block.opaqueCubeLookup[var8]) {
            var9 = 5;
        }
        if (Block.opaqueCubeLookup[var8] && !Block.opaqueCubeLookup[var7]) {
            var9 = 4;
        }
        var1.setBlockMetadataWithNotify(var2, var3, var4, var9);
    }

    @Override
    public int getBlockTexture(IBlockAccess var1, int var2, int var3, int var4, int var5) {
        if (var5 == 1) {
            return this.blockIndexInTexture + 17;
        }
        if (var5 == 0) {
            return this.blockIndexInTexture + 17;
        }
        int var6 = var1.getBlockMetadata(var2, var3, var4);
        return var5 != var6 ? this.blockIndexInTexture : this.blockIndexInTexture + 1;
    }

    @Override
    public int getBlockTextureFromSide(int var1) {
        if (var1 == 1) {
            return this.blockIndexInTexture + 17;
        }
        if (var1 == 0) {
            return this.blockIndexInTexture + 17;
        }
        return var1 == 3 ? this.blockIndexInTexture + 1 : this.blockIndexInTexture;
    }

    @Override
    public boolean blockActivated(World var1, int var2, int var3, int var4, EntityPlayer var5) {
        if (var1.multiplayerWorld) {
            return true;
        }
        TileEntityDispenser var6 = (TileEntityDispenser)var1.getBlockTileEntity(var2, var3, var4);
        var5.displayGUIDispenser(var6);
        return true;
    }

    private void dispenseItem(World var1, int var2, int var3, int var4, Random var5) {
        int var6 = var1.getBlockMetadata(var2, var3, var4);
        float var9 = 0.0f;
        float var10 = 0.0f;
        if (var6 == 3) {
            var10 = 1.0f;
        } else if (var6 == 2) {
            var10 = -1.0f;
        } else {
            var9 = var6 == 5 ? 1.0f : -1.0f;
        }
        TileEntityDispenser var11 = (TileEntityDispenser)var1.getBlockTileEntity(var2, var3, var4);
        ItemStack var12 = var11.getRandomStackFromInventory();
        double var13 = (double)var2 + (double)var9 * 0.5 + 0.5;
        double var15 = (double)var3 + 0.5;
        double var17 = (double)var4 + (double)var10 * 0.5 + 0.5;
        if (var12 == null) {
            var1.playSoundEffect(var2, var3, var4, "random.click", 1.0f, 1.2f);
        } else {
            double var20;
            if (var12.itemID == Item.arrow.shiftedIndex) {
                EntityArrow var19 = new EntityArrow(var1, var13, var15, var17);
                var19.setArrowHeading(var9, 0.1f, var10, 1.1f, 6.0f);
                var1.entityJoinedWorld(var19);
                var1.playSoundEffect(var2, var3, var4, "random.bow", 1.0f, 1.2f);
            } else if (var12.itemID == Item.egg.shiftedIndex) {
                EntityEgg var34 = new EntityEgg(var1, var13, var15, var17);
                var34.func_20048_a(var9, 0.1f, var10, 1.1f, 6.0f);
                var1.entityJoinedWorld(var34);
                var1.playSoundEffect(var2, var3, var4, "random.bow", 1.0f, 1.2f);
            } else if (var12.itemID == Item.snowball.shiftedIndex) {
                EntitySnowball var35 = new EntitySnowball(var1, var13, var15, var17);
                var35.func_467_a(var9, 0.1f, var10, 1.1f, 6.0f);
                var1.entityJoinedWorld(var35);
                var1.playSoundEffect(var2, var3, var4, "random.bow", 1.0f, 1.2f);
            } else {
                EntityItem var36 = new EntityItem(var1, var13, var15 - 0.3, var17, var12);
                var20 = var5.nextDouble() * 0.1 + 0.2;
                var36.motionX = (double)var9 * var20;
                var36.motionY = 0.2f;
                var36.motionZ = (double)var10 * var20;
                var36.motionX += var5.nextGaussian() * (double)0.0075f * 6.0;
                var36.motionY += var5.nextGaussian() * (double)0.0075f * 6.0;
                var36.motionZ += var5.nextGaussian() * (double)0.0075f * 6.0;
                var1.entityJoinedWorld(var36);
                var1.playSoundEffect(var2, var3, var4, "random.click", 1.0f, 1.0f);
            }
            int var37 = 0;
            while (var37 < 10) {
                var20 = var5.nextDouble() * 0.2 + 0.01;
                double var22 = var13 + (double)var9 * 0.01 + (var5.nextDouble() - 0.5) * (double)var10 * 0.5;
                double var24 = var15 + (var5.nextDouble() - 0.5) * 0.5;
                double var26 = var17 + (double)var10 * 0.01 + (var5.nextDouble() - 0.5) * (double)var9 * 0.5;
                double var28 = (double)var9 * var20 + var5.nextGaussian() * 0.01;
                double var30 = -0.03 + var5.nextGaussian() * 0.01;
                double var32 = (double)var10 * var20 + var5.nextGaussian() * 0.01;
                var1.spawnParticle("smoke", var22, var24, var26, var28, var30, var32);
                ++var37;
            }
        }
    }

    @Override
    public void onNeighborBlockChange(World var1, int var2, int var3, int var4, int var5) {
        if (var5 > 0 && Block.blocksList[var5].canProvidePower()) {
            boolean var6;
            boolean bl = var6 = var1.isBlockIndirectlyGettingPowered(var2, var3, var4) || var1.isBlockIndirectlyGettingPowered(var2, var3 + 1, var4);
            if (var6) {
                var1.scheduleBlockUpdate(var2, var3, var4, this.blockID, this.tickRate());
            }
        }
    }

    @Override
    public void updateTick(World var1, int var2, int var3, int var4, Random var5) {
        if (var1.isBlockIndirectlyGettingPowered(var2, var3, var4) || var1.isBlockIndirectlyGettingPowered(var2, var3 + 1, var4)) {
            this.dispenseItem(var1, var2, var3, var4, var5);
        }
    }

    @Override
    protected TileEntity getBlockEntity() {
        return new TileEntityDispenser();
    }

    @Override
    public void onBlockPlacedBy(World var1, int var2, int var3, int var4, EntityLiving var5) {
        int var6 = MathHelper.floor_double((double)(var5.rotationYaw * 4.0f / 360.0f) + 0.5) & 3;
        if (var6 == 0) {
            var1.setBlockMetadataWithNotify(var2, var3, var4, 2);
        }
        if (var6 == 1) {
            var1.setBlockMetadataWithNotify(var2, var3, var4, 5);
        }
        if (var6 == 2) {
            var1.setBlockMetadataWithNotify(var2, var3, var4, 3);
        }
        if (var6 == 3) {
            var1.setBlockMetadataWithNotify(var2, var3, var4, 4);
        }
    }
}

