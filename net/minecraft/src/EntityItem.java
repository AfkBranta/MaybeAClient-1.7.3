package net.minecraft.src;

import net.minecraft.src.Block;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.MathHelper;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.World;

public class EntityItem
extends Entity {
    public ItemStack item;
    private int field_803_e;
    public int age = 0;
    public int delayBeforeCanPickup;
    private int health = 5;
    public float field_804_d = (float)(Math.random() * Math.PI * 2.0);

    public EntityItem(World var1, double var2, double var4, double var6, ItemStack var8) {
        super(var1);
        this.setSize(0.25f, 0.25f);
        this.yOffset = this.height / 2.0f;
        this.setPosition(var2, var4, var6);
        this.item = var8;
        this.rotationYaw = (float)(Math.random() * 360.0);
        this.motionX = (float)(Math.random() * (double)0.2f - (double)0.1f);
        this.motionY = 0.2f;
        this.motionZ = (float)(Math.random() * (double)0.2f - (double)0.1f);
    }

    @Override
    protected boolean canTriggerWalking() {
        return false;
    }

    public EntityItem(World var1) {
        super(var1);
        this.setSize(0.25f, 0.25f);
        this.yOffset = this.height / 2.0f;
    }

    @Override
    protected void entityInit() {
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (this.delayBeforeCanPickup > 0) {
            --this.delayBeforeCanPickup;
        }
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.motionY -= (double)0.04f;
        if (this.worldObj.getBlockMaterial(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)) == Material.lava) {
            this.motionY = 0.2f;
            this.motionX = (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f;
            this.motionZ = (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f;
            this.worldObj.playSoundAtEntity(this, "random.fizz", 0.4f, 2.0f + this.rand.nextFloat() * 0.4f);
        }
        this.func_466_g(this.posX, this.posY, this.posZ);
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        float var1 = 0.98f;
        if (this.onGround) {
            var1 = 0.58800006f;
            int var2 = this.worldObj.getBlockId(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.boundingBox.minY) - 1, MathHelper.floor_double(this.posZ));
            if (var2 > 0) {
                var1 = Block.blocksList[var2].slipperiness * 0.98f;
            }
        }
        this.motionX *= (double)var1;
        this.motionY *= (double)0.98f;
        this.motionZ *= (double)var1;
        if (this.onGround) {
            this.motionY *= -0.5;
        }
        ++this.field_803_e;
        ++this.age;
        if (this.age >= 6000) {
            this.setEntityDead();
        }
    }

    @Override
    public boolean handleWaterMovement() {
        return this.worldObj.handleMaterialAcceleration(this.boundingBox, Material.water, this);
    }

    private boolean func_466_g(double var1, double var3, double var5) {
        int var7 = MathHelper.floor_double(var1);
        int var8 = MathHelper.floor_double(var3);
        int var9 = MathHelper.floor_double(var5);
        double var10 = var1 - (double)var7;
        double var12 = var3 - (double)var8;
        double var14 = var5 - (double)var9;
        if (Block.opaqueCubeLookup[this.worldObj.getBlockId(var7, var8, var9)]) {
            boolean var16 = !Block.opaqueCubeLookup[this.worldObj.getBlockId(var7 - 1, var8, var9)];
            boolean var17 = !Block.opaqueCubeLookup[this.worldObj.getBlockId(var7 + 1, var8, var9)];
            boolean var18 = !Block.opaqueCubeLookup[this.worldObj.getBlockId(var7, var8 - 1, var9)];
            boolean var19 = !Block.opaqueCubeLookup[this.worldObj.getBlockId(var7, var8 + 1, var9)];
            boolean var20 = !Block.opaqueCubeLookup[this.worldObj.getBlockId(var7, var8, var9 - 1)];
            boolean var21 = !Block.opaqueCubeLookup[this.worldObj.getBlockId(var7, var8, var9 + 1)];
            int var22 = -1;
            double var23 = 9999.0;
            if (var16 && var10 < var23) {
                var23 = var10;
                var22 = 0;
            }
            if (var17 && 1.0 - var10 < var23) {
                var23 = 1.0 - var10;
                var22 = 1;
            }
            if (var18 && var12 < var23) {
                var23 = var12;
                var22 = 2;
            }
            if (var19 && 1.0 - var12 < var23) {
                var23 = 1.0 - var12;
                var22 = 3;
            }
            if (var20 && var14 < var23) {
                var23 = var14;
                var22 = 4;
            }
            if (var21 && 1.0 - var14 < var23) {
                var23 = 1.0 - var14;
                var22 = 5;
            }
            float var25 = this.rand.nextFloat() * 0.2f + 0.1f;
            if (var22 == 0) {
                this.motionX = -var25;
            }
            if (var22 == 1) {
                this.motionX = var25;
            }
            if (var22 == 2) {
                this.motionY = -var25;
            }
            if (var22 == 3) {
                this.motionY = var25;
            }
            if (var22 == 4) {
                this.motionZ = -var25;
            }
            if (var22 == 5) {
                this.motionZ = var25;
            }
        }
        return false;
    }

    @Override
    protected void dealFireDamage(int var1) {
        this.attackEntityFrom(null, var1);
    }

    @Override
    public boolean attackEntityFrom(Entity var1, int var2) {
        this.setBeenAttacked();
        this.health -= var2;
        if (this.health <= 0) {
            this.setEntityDead();
        }
        return false;
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound var1) {
        var1.setShort("Health", (byte)this.health);
        var1.setShort("Age", (short)this.age);
        var1.setCompoundTag("Item", this.item.writeToNBT(new NBTTagCompound()));
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound var1) {
        this.health = var1.getShort("Health") & 0xFF;
        this.age = var1.getShort("Age");
        NBTTagCompound var2 = var1.getCompoundTag("Item");
        this.item = new ItemStack(var2);
    }

    @Override
    public void onCollideWithPlayer(EntityPlayer var1) {
        if (!this.worldObj.multiplayerWorld) {
            int var2 = this.item.stackSize;
            if (this.delayBeforeCanPickup == 0 && var1.inventory.addItemStackToInventory(this.item)) {
                this.worldObj.playSoundAtEntity(this, "random.pop", 0.2f, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7f + 1.0f) * 2.0f);
                var1.onItemPickup(this, var2);
                this.setEntityDead();
            }
        }
    }
}

