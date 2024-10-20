package net.minecraft.src;

import net.minecraft.src.Entity;
import net.minecraft.src.EntityArrow;
import net.minecraft.src.EntityMobs;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.MathHelper;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.World;

public class EntitySkeleton
extends EntityMobs {
    private static final ItemStack defaultHeldItem = new ItemStack(Item.bow, 1);

    public EntitySkeleton(World var1) {
        super(var1);
        this.texture = "/mob/skeleton.png";
    }

    @Override
    protected String getLivingSound() {
        return "mob.skeleton";
    }

    @Override
    protected String getHurtSound() {
        return "mob.skeletonhurt";
    }

    @Override
    protected String getDeathSound() {
        return "mob.skeletonhurt";
    }

    @Override
    public void onLivingUpdate() {
        float var1;
        if (this.worldObj.isDaytime() && (var1 = this.getEntityBrightness(1.0f)) > 0.5f && this.worldObj.canBlockSeeTheSky(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)) && this.rand.nextFloat() * 30.0f < (var1 - 0.4f) * 2.0f) {
            this.fire = 300;
        }
        super.onLivingUpdate();
    }

    @Override
    protected void attackEntity(Entity var1, float var2) {
        if (var2 < 10.0f) {
            double var3 = var1.posX - this.posX;
            double var5 = var1.posZ - this.posZ;
            if (this.attackTime == 0) {
                EntityArrow var7 = new EntityArrow(this.worldObj, this);
                var7.posY += 1.0;
                double var8 = var1.posY - (double)0.2f - var7.posY;
                float var10 = MathHelper.sqrt_double(var3 * var3 + var5 * var5) * 0.2f;
                this.worldObj.playSoundAtEntity(this, "random.bow", 1.0f, 1.0f / (this.rand.nextFloat() * 0.4f + 0.8f));
                this.worldObj.entityJoinedWorld(var7);
                var7.setArrowHeading(var3, var8 + (double)var10, var5, 0.6f, 12.0f);
                this.attackTime = 30;
            }
            this.rotationYaw = (float)(Math.atan2(var5, var3) * 180.0 / 3.1415927410125732) - 90.0f;
            this.hasAttacked = true;
        }
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound var1) {
        super.writeEntityToNBT(var1);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound var1) {
        super.readEntityFromNBT(var1);
    }

    @Override
    protected int getDropItemId() {
        return Item.arrow.shiftedIndex;
    }

    @Override
    protected void dropFewItems() {
        int var1 = this.rand.nextInt(3);
        int var2 = 0;
        while (var2 < var1) {
            this.dropItem(Item.arrow.shiftedIndex, 1);
            ++var2;
        }
        var1 = this.rand.nextInt(3);
        var2 = 0;
        while (var2 < var1) {
            this.dropItem(Item.bone.shiftedIndex, 1);
            ++var2;
        }
    }

    @Override
    public ItemStack getHeldItem() {
        return defaultHeldItem;
    }
}

