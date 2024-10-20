/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityItem;
import net.minecraft.src.EnumArt;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.MathHelper;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.World;

public class EntityPainting
extends Entity {
    private int field_695_c = 0;
    public int direction = 0;
    public int xPosition;
    public int yPosition;
    public int zPosition;
    public EnumArt art;

    public EntityPainting(World var1) {
        super(var1);
        this.yOffset = 0.0f;
        this.setSize(0.5f, 0.5f);
    }

    public EntityPainting(World var1, int var2, int var3, int var4, int var5) {
        this(var1);
        this.xPosition = var2;
        this.yPosition = var3;
        this.zPosition = var4;
        ArrayList<EnumArt> var6 = new ArrayList<EnumArt>();
        EnumArt[] var7 = EnumArt.values();
        int var8 = var7.length;
        int var9 = 0;
        while (var9 < var8) {
            EnumArt var10;
            this.art = var10 = var7[var9];
            this.func_412_b(var5);
            if (this.func_410_i()) {
                var6.add(var10);
            }
            ++var9;
        }
        if (var6.size() > 0) {
            this.art = (EnumArt)((Object)var6.get(this.rand.nextInt(var6.size())));
        }
        this.func_412_b(var5);
    }

    public EntityPainting(World var1, int var2, int var3, int var4, int var5, String var6) {
        this(var1);
        this.xPosition = var2;
        this.yPosition = var3;
        this.zPosition = var4;
        EnumArt[] var7 = EnumArt.values();
        int var8 = var7.length;
        int var9 = 0;
        while (var9 < var8) {
            EnumArt var10 = var7[var9];
            if (var10.title.equals(var6)) {
                this.art = var10;
                break;
            }
            ++var9;
        }
        this.func_412_b(var5);
    }

    @Override
    protected void entityInit() {
    }

    public void func_412_b(int var1) {
        this.direction = var1;
        this.prevRotationYaw = this.rotationYaw = (float)(var1 * 90);
        float var2 = this.art.sizeX;
        float var3 = this.art.sizeY;
        float var4 = this.art.sizeX;
        if (var1 != 0 && var1 != 2) {
            var2 = 0.5f;
        } else {
            var4 = 0.5f;
        }
        var2 /= 32.0f;
        var3 /= 32.0f;
        var4 /= 32.0f;
        float var5 = (float)this.xPosition + 0.5f;
        float var6 = (float)this.yPosition + 0.5f;
        float var7 = (float)this.zPosition + 0.5f;
        float var8 = 0.5625f;
        if (var1 == 0) {
            var7 -= var8;
        }
        if (var1 == 1) {
            var5 -= var8;
        }
        if (var1 == 2) {
            var7 += var8;
        }
        if (var1 == 3) {
            var5 += var8;
        }
        if (var1 == 0) {
            var5 -= this.func_411_c(this.art.sizeX);
        }
        if (var1 == 1) {
            var7 += this.func_411_c(this.art.sizeX);
        }
        if (var1 == 2) {
            var5 += this.func_411_c(this.art.sizeX);
        }
        if (var1 == 3) {
            var7 -= this.func_411_c(this.art.sizeX);
        }
        this.setPosition(var5, var6 += this.func_411_c(this.art.sizeY), var7);
        float var9 = -0.00625f;
        this.boundingBox.setBounds(var5 - var2 - var9, var6 - var3 - var9, var7 - var4 - var9, var5 + var2 + var9, var6 + var3 + var9, var7 + var4 + var9);
    }

    private float func_411_c(int var1) {
        if (var1 == 32) {
            return 0.5f;
        }
        return var1 == 64 ? 0.5f : 0.0f;
    }

    @Override
    public void onUpdate() {
        if (this.field_695_c++ == 100 && !this.worldObj.multiplayerWorld) {
            this.field_695_c = 0;
            if (!this.func_410_i()) {
                this.setEntityDead();
                this.worldObj.entityJoinedWorld(new EntityItem(this.worldObj, this.posX, this.posY, this.posZ, new ItemStack(Item.painting)));
            }
        }
    }

    public boolean func_410_i() {
        int var7;
        if (this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox).size() > 0) {
            return false;
        }
        int var1 = this.art.sizeX / 16;
        int var2 = this.art.sizeY / 16;
        int var3 = this.xPosition;
        int var4 = this.yPosition;
        int var5 = this.zPosition;
        if (this.direction == 0) {
            var3 = MathHelper.floor_double(this.posX - (double)((float)this.art.sizeX / 32.0f));
        }
        if (this.direction == 1) {
            var5 = MathHelper.floor_double(this.posZ - (double)((float)this.art.sizeX / 32.0f));
        }
        if (this.direction == 2) {
            var3 = MathHelper.floor_double(this.posX - (double)((float)this.art.sizeX / 32.0f));
        }
        if (this.direction == 3) {
            var5 = MathHelper.floor_double(this.posZ - (double)((float)this.art.sizeX / 32.0f));
        }
        var4 = MathHelper.floor_double(this.posY - (double)((float)this.art.sizeY / 32.0f));
        int var6 = 0;
        while (var6 < var1) {
            var7 = 0;
            while (var7 < var2) {
                Material var8 = this.direction != 0 && this.direction != 2 ? this.worldObj.getBlockMaterial(this.xPosition, var4 + var7, var5 + var6) : this.worldObj.getBlockMaterial(var3 + var6, var4 + var7, this.zPosition);
                if (!var8.isSolid()) {
                    return false;
                }
                ++var7;
            }
            ++var6;
        }
        List var9 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox);
        var7 = 0;
        while (var7 < var9.size()) {
            if (var9.get(var7) instanceof EntityPainting) {
                return false;
            }
            ++var7;
        }
        return true;
    }

    @Override
    public boolean canBeCollidedWith() {
        return true;
    }

    @Override
    public boolean attackEntityFrom(Entity var1, int var2) {
        if (!this.isDead && !this.worldObj.multiplayerWorld) {
            this.setEntityDead();
            this.setBeenAttacked();
            this.worldObj.entityJoinedWorld(new EntityItem(this.worldObj, this.posX, this.posY, this.posZ, new ItemStack(Item.painting)));
        }
        return true;
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound var1) {
        var1.setByte("Dir", (byte)this.direction);
        var1.setString("Motive", this.art.title);
        var1.setInteger("TileX", this.xPosition);
        var1.setInteger("TileY", this.yPosition);
        var1.setInteger("TileZ", this.zPosition);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound var1) {
        this.direction = var1.getByte("Dir");
        this.xPosition = var1.getInteger("TileX");
        this.yPosition = var1.getInteger("TileY");
        this.zPosition = var1.getInteger("TileZ");
        String var2 = var1.getString("Motive");
        EnumArt[] var3 = EnumArt.values();
        int var4 = var3.length;
        int var5 = 0;
        while (var5 < var4) {
            EnumArt var6 = var3[var5];
            if (var6.title.equals(var2)) {
                this.art = var6;
            }
            ++var5;
        }
        if (this.art == null) {
            this.art = EnumArt.Kebab;
        }
        this.func_412_b(this.direction);
    }
}

