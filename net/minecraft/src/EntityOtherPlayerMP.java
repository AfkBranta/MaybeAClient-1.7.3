/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.Entity;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.MathHelper;
import net.minecraft.src.World;

public class EntityOtherPlayerMP
extends EntityPlayer {
    private int field_785_bg;
    private double field_784_bh;
    private double field_783_bi;
    private double field_782_bj;
    private double field_780_bk;
    private double field_786_bl;
    float field_20924_a = 0.0f;

    public EntityOtherPlayerMP(World var1, String var2) {
        super(var1);
        this.username = var2;
        this.yOffset = 0.0f;
        this.stepHeight = 0.0f;
        if (var2 != null && var2.length() > 0) {
            this.skinUrl = "http://s3.amazonaws.com/MinecraftSkins/" + var2 + ".png";
        }
        this.noClip = true;
        this.field_22062_y = 0.25f;
        this.renderDistanceWeight = 10.0;
    }

    @Override
    protected void resetHeight() {
        this.yOffset = 0.0f;
    }

    @Override
    public boolean attackEntityFrom(Entity var1, int var2) {
        return true;
    }

    @Override
    public void setPositionAndRotation2(double var1, double var3, double var5, float var7, float var8, int var9) {
        this.field_784_bh = var1;
        this.field_783_bi = var3;
        this.field_782_bj = var5;
        this.field_780_bk = var7;
        this.field_786_bl = var8;
        this.field_785_bg = var9;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        this.field_705_Q = this.field_704_R;
        double var1 = this.posX - this.prevPosX;
        double var3 = this.posZ - this.prevPosZ;
        float var5 = MathHelper.sqrt_double(var1 * var1 + var3 * var3) * 4.0f;
        if (var5 > 1.0f) {
            var5 = 1.0f;
        }
        this.field_704_R += (var5 - this.field_704_R) * 0.4f;
        this.field_703_S += this.field_704_R;
    }

    @Override
    public float getShadowSize() {
        return 0.0f;
    }

    @Override
    public void onLivingUpdate() {
        super.updatePlayerActionState();
        if (this.field_785_bg > 0) {
            double var1 = this.posX + (this.field_784_bh - this.posX) / (double)this.field_785_bg;
            double var3 = this.posY + (this.field_783_bi - this.posY) / (double)this.field_785_bg;
            double var5 = this.posZ + (this.field_782_bj - this.posZ) / (double)this.field_785_bg;
            double var7 = this.field_780_bk - (double)this.rotationYaw;
            while (var7 < -180.0) {
                var7 += 360.0;
            }
            while (var7 >= 180.0) {
                var7 -= 360.0;
            }
            this.rotationYaw = (float)((double)this.rotationYaw + var7 / (double)this.field_785_bg);
            this.rotationPitch = (float)((double)this.rotationPitch + (this.field_786_bl - (double)this.rotationPitch) / (double)this.field_785_bg);
            --this.field_785_bg;
            this.setPosition(var1, var3, var5);
            this.setRotation(this.rotationYaw, this.rotationPitch);
        }
        this.field_775_e = this.field_774_f;
        float var9 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
        float var2 = (float)Math.atan(-this.motionY * (double)0.2f) * 15.0f;
        if (var9 > 0.1f) {
            var9 = 0.1f;
        }
        if (!this.onGround || this.health <= 0) {
            var9 = 0.0f;
        }
        if (this.onGround || this.health <= 0) {
            var2 = 0.0f;
        }
        this.field_774_f += (var9 - this.field_774_f) * 0.4f;
        this.field_9328_R += (var2 - this.field_9328_R) * 0.8f;
    }

    @Override
    public void outfitWithItem(int var1, int var2, int var3) {
        ItemStack var4 = null;
        if (var2 >= 0) {
            var4 = new ItemStack(var2, 1, var3);
        }
        if (var1 == 0) {
            this.inventory.mainInventory[this.inventory.currentItem] = var4;
        } else {
            this.inventory.armorInventory[var1 - 1] = var4;
        }
    }

    @Override
    public void func_6420_o() {
    }
}

