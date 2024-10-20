package net.minecraft.src;

import net.minecraft.src.Block;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.MathHelper;
import net.minecraft.src.World;

public class EntityFlying
extends EntityLiving {
    public EntityFlying(World var1) {
        super(var1);
    }

    @Override
    protected void fall(float var1) {
    }

    @Override
    public void moveEntityWithHeading(float var1, float var2) {
        if (this.handleWaterMovement()) {
            this.moveFlying(var1, var2, 0.02f);
            this.moveEntity(this.motionX, this.motionY, this.motionZ);
            this.motionX *= (double)0.8f;
            this.motionY *= (double)0.8f;
            this.motionZ *= (double)0.8f;
        } else if (this.handleLavaMovement()) {
            this.moveFlying(var1, var2, 0.02f);
            this.moveEntity(this.motionX, this.motionY, this.motionZ);
            this.motionX *= 0.5;
            this.motionY *= 0.5;
            this.motionZ *= 0.5;
        } else {
            float var3 = 0.91f;
            if (this.onGround) {
                var3 = 0.54600006f;
                int var4 = this.worldObj.getBlockId(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.boundingBox.minY) - 1, MathHelper.floor_double(this.posZ));
                if (var4 > 0) {
                    var3 = Block.blocksList[var4].slipperiness * 0.91f;
                }
            }
            float var8 = 0.16277136f / (var3 * var3 * var3);
            this.moveFlying(var1, var2, this.onGround ? 0.1f * var8 : 0.02f);
            var3 = 0.91f;
            if (this.onGround) {
                var3 = 0.54600006f;
                int var5 = this.worldObj.getBlockId(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.boundingBox.minY) - 1, MathHelper.floor_double(this.posZ));
                if (var5 > 0) {
                    var3 = Block.blocksList[var5].slipperiness * 0.91f;
                }
            }
            this.moveEntity(this.motionX, this.motionY, this.motionZ);
            this.motionX *= (double)var3;
            this.motionY *= (double)var3;
            this.motionZ *= (double)var3;
        }
        this.field_705_Q = this.field_704_R;
        double var10 = this.posX - this.prevPosX;
        double var9 = this.posZ - this.prevPosZ;
        float var7 = MathHelper.sqrt_double(var10 * var10 + var9 * var9) * 4.0f;
        if (var7 > 1.0f) {
            var7 = 1.0f;
        }
        this.field_704_R += (var7 - this.field_704_R) * 0.4f;
        this.field_703_S += this.field_704_R;
    }

    @Override
    public boolean isOnLadder() {
        return false;
    }
}

