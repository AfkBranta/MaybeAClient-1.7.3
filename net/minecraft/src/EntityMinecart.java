/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.List;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Block;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityItem;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.MathHelper;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.NBTTagList;
import net.minecraft.src.Vec3D;
import net.minecraft.src.World;

public class EntityMinecart
extends Entity
implements IInventory {
    private ItemStack[] cargoItems = new ItemStack[36];
    public int minecartCurrentDamage = 0;
    public int minecartTimeSinceHit = 0;
    public int minecartRockDirection = 1;
    private boolean field_856_i = false;
    public int minecartType;
    public int fuel;
    public double pushX;
    public double pushZ;
    private static final int[][][] field_855_j;
    private int field_9415_k;
    private double field_9414_l;
    private double field_9413_m;
    private double field_9412_n;
    private double field_9411_o;
    private double field_9410_p;
    private double field_9409_q;
    private double field_9408_r;
    private double field_9407_s;

    static {
        int[][][] nArrayArray = new int[10][][];
        int[][] nArrayArray2 = new int[2][];
        int[] nArray = new int[3];
        nArray[2] = -1;
        nArrayArray2[0] = nArray;
        int[] nArray2 = new int[3];
        nArray2[2] = 1;
        nArrayArray2[1] = nArray2;
        nArrayArray[0] = nArrayArray2;
        int[][] nArrayArray3 = new int[2][];
        int[] nArray3 = new int[3];
        nArray3[0] = -1;
        nArrayArray3[0] = nArray3;
        int[] nArray4 = new int[3];
        nArray4[0] = 1;
        nArrayArray3[1] = nArray4;
        nArrayArray[1] = nArrayArray3;
        int[][] nArrayArray4 = new int[2][];
        int[] nArray5 = new int[3];
        nArray5[0] = -1;
        nArray5[1] = -1;
        nArrayArray4[0] = nArray5;
        int[] nArray6 = new int[3];
        nArray6[0] = 1;
        nArrayArray4[1] = nArray6;
        nArrayArray[2] = nArrayArray4;
        int[][] nArrayArray5 = new int[2][];
        int[] nArray7 = new int[3];
        nArray7[0] = -1;
        nArrayArray5[0] = nArray7;
        int[] nArray8 = new int[3];
        nArray8[0] = 1;
        nArray8[1] = -1;
        nArrayArray5[1] = nArray8;
        nArrayArray[3] = nArrayArray5;
        int[][] nArrayArray6 = new int[2][];
        int[] nArray9 = new int[3];
        nArray9[2] = -1;
        nArrayArray6[0] = nArray9;
        int[] nArray10 = new int[3];
        nArray10[1] = -1;
        nArray10[2] = 1;
        nArrayArray6[1] = nArray10;
        nArrayArray[4] = nArrayArray6;
        int[][] nArrayArray7 = new int[2][];
        int[] nArray11 = new int[3];
        nArray11[1] = -1;
        nArray11[2] = -1;
        nArrayArray7[0] = nArray11;
        int[] nArray12 = new int[3];
        nArray12[2] = 1;
        nArrayArray7[1] = nArray12;
        nArrayArray[5] = nArrayArray7;
        int[][] nArrayArray8 = new int[2][];
        int[] nArray13 = new int[3];
        nArray13[2] = 1;
        nArrayArray8[0] = nArray13;
        int[] nArray14 = new int[3];
        nArray14[0] = 1;
        nArrayArray8[1] = nArray14;
        nArrayArray[6] = nArrayArray8;
        int[][] nArrayArray9 = new int[2][];
        int[] nArray15 = new int[3];
        nArray15[2] = 1;
        nArrayArray9[0] = nArray15;
        int[] nArray16 = new int[3];
        nArray16[0] = -1;
        nArrayArray9[1] = nArray16;
        nArrayArray[7] = nArrayArray9;
        int[][] nArrayArray10 = new int[2][];
        int[] nArray17 = new int[3];
        nArray17[2] = -1;
        nArrayArray10[0] = nArray17;
        int[] nArray18 = new int[3];
        nArray18[0] = -1;
        nArrayArray10[1] = nArray18;
        nArrayArray[8] = nArrayArray10;
        int[][] nArrayArray11 = new int[2][];
        int[] nArray19 = new int[3];
        nArray19[2] = -1;
        nArrayArray11[0] = nArray19;
        int[] nArray20 = new int[3];
        nArray20[0] = 1;
        nArrayArray11[1] = nArray20;
        nArrayArray[9] = nArrayArray11;
        field_855_j = nArrayArray;
    }

    public EntityMinecart(World var1) {
        super(var1);
        this.preventEntitySpawning = true;
        this.setSize(0.98f, 0.7f);
        this.yOffset = this.height / 2.0f;
    }

    @Override
    protected boolean canTriggerWalking() {
        return false;
    }

    @Override
    protected void entityInit() {
    }

    @Override
    public AxisAlignedBB getCollisionBox(Entity var1) {
        return var1.boundingBox;
    }

    @Override
    public AxisAlignedBB getBoundingBox() {
        return null;
    }

    @Override
    public boolean canBePushed() {
        return true;
    }

    public EntityMinecart(World var1, double var2, double var4, double var6, int var8) {
        this(var1);
        this.setPosition(var2, var4 + (double)this.yOffset, var6);
        this.motionX = 0.0;
        this.motionY = 0.0;
        this.motionZ = 0.0;
        this.prevPosX = var2;
        this.prevPosY = var4;
        this.prevPosZ = var6;
        this.minecartType = var8;
    }

    @Override
    public double getMountedYOffset() {
        return (double)this.height * 0.0 - (double)0.3f;
    }

    @Override
    public boolean attackEntityFrom(Entity var1, int var2) {
        if (!this.worldObj.multiplayerWorld && !this.isDead) {
            this.minecartRockDirection = -this.minecartRockDirection;
            this.minecartTimeSinceHit = 10;
            this.setBeenAttacked();
            this.minecartCurrentDamage += var2 * 10;
            if (this.minecartCurrentDamage > 40) {
                this.dropItemWithOffset(Item.minecartEmpty.shiftedIndex, 1, 0.0f);
                if (this.minecartType == 1) {
                    this.dropItemWithOffset(Block.crate.blockID, 1, 0.0f);
                } else if (this.minecartType == 2) {
                    this.dropItemWithOffset(Block.stoneOvenIdle.blockID, 1, 0.0f);
                }
                this.setEntityDead();
            }
            return true;
        }
        return true;
    }

    @Override
    public void performHurtAnimation() {
        System.out.println("Animating hurt");
        this.minecartRockDirection = -this.minecartRockDirection;
        this.minecartTimeSinceHit = 10;
        this.minecartCurrentDamage += this.minecartCurrentDamage * 10;
    }

    @Override
    public boolean canBeCollidedWith() {
        return !this.isDead;
    }

    @Override
    public void setEntityDead() {
        int var1 = 0;
        while (var1 < this.getSizeInventory()) {
            ItemStack var2 = this.getStackInSlot(var1);
            if (var2 != null) {
                float var3 = this.rand.nextFloat() * 0.8f + 0.1f;
                float var4 = this.rand.nextFloat() * 0.8f + 0.1f;
                float var5 = this.rand.nextFloat() * 0.8f + 0.1f;
                while (var2.stackSize > 0) {
                    int var6 = this.rand.nextInt(21) + 10;
                    if (var6 > var2.stackSize) {
                        var6 = var2.stackSize;
                    }
                    var2.stackSize -= var6;
                    EntityItem var7 = new EntityItem(this.worldObj, this.posX + (double)var3, this.posY + (double)var4, this.posZ + (double)var5, new ItemStack(var2.itemID, var6, var2.getItemDamage()));
                    float var8 = 0.05f;
                    var7.motionX = (float)this.rand.nextGaussian() * var8;
                    var7.motionY = (float)this.rand.nextGaussian() * var8 + 0.2f;
                    var7.motionZ = (float)this.rand.nextGaussian() * var8;
                    this.worldObj.entityJoinedWorld(var7);
                }
            }
            ++var1;
        }
        super.setEntityDead();
    }

    @Override
    public void onUpdate() {
        if (this.minecartTimeSinceHit > 0) {
            --this.minecartTimeSinceHit;
        }
        if (this.minecartCurrentDamage > 0) {
            --this.minecartCurrentDamage;
        }
        if (this.worldObj.multiplayerWorld && this.field_9415_k > 0) {
            if (this.field_9415_k > 0) {
                double var41 = this.posX + (this.field_9414_l - this.posX) / (double)this.field_9415_k;
                double var42 = this.posY + (this.field_9413_m - this.posY) / (double)this.field_9415_k;
                double var5 = this.posZ + (this.field_9412_n - this.posZ) / (double)this.field_9415_k;
                double var7 = this.field_9411_o - (double)this.rotationYaw;
                while (var7 < -180.0) {
                    var7 += 360.0;
                }
                while (var7 >= 180.0) {
                    var7 -= 360.0;
                }
                this.rotationYaw = (float)((double)this.rotationYaw + var7 / (double)this.field_9415_k);
                this.rotationPitch = (float)((double)this.rotationPitch + (this.field_9410_p - (double)this.rotationPitch) / (double)this.field_9415_k);
                --this.field_9415_k;
                this.setPosition(var41, var42, var5);
                this.setRotation(this.rotationYaw, this.rotationPitch);
            } else {
                this.setPosition(this.posX, this.posY, this.posZ);
                this.setRotation(this.rotationYaw, this.rotationPitch);
            }
        } else {
            int var3;
            int var2;
            this.prevPosX = this.posX;
            this.prevPosY = this.posY;
            this.prevPosZ = this.posZ;
            this.motionY -= (double)0.04f;
            int var1 = MathHelper.floor_double(this.posX);
            if (this.worldObj.getBlockId(var1, (var2 = MathHelper.floor_double(this.posY)) - 1, var3 = MathHelper.floor_double(this.posZ)) == Block.minecartTrack.blockID) {
                --var2;
            }
            double var4 = 0.4;
            boolean var6 = false;
            double var7 = 0.0078125;
            if (this.worldObj.getBlockId(var1, var2, var3) == Block.minecartTrack.blockID) {
                double var39;
                double var36;
                double var34;
                double var32;
                Vec3D var9 = this.func_514_g(this.posX, this.posY, this.posZ);
                int var10 = this.worldObj.getBlockMetadata(var1, var2, var3);
                this.posY = var2;
                if (var10 >= 2 && var10 <= 5) {
                    this.posY = var2 + 1;
                }
                if (var10 == 2) {
                    this.motionX -= var7;
                }
                if (var10 == 3) {
                    this.motionX += var7;
                }
                if (var10 == 4) {
                    this.motionZ += var7;
                }
                if (var10 == 5) {
                    this.motionZ -= var7;
                }
                int[][] var11 = field_855_j[var10];
                double var12 = var11[1][0] - var11[0][0];
                double var14 = var11[1][2] - var11[0][2];
                double var16 = Math.sqrt(var12 * var12 + var14 * var14);
                double var18 = this.motionX * var12 + this.motionZ * var14;
                if (var18 < 0.0) {
                    var12 = -var12;
                    var14 = -var14;
                }
                double var20 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
                this.motionX = var20 * var12 / var16;
                this.motionZ = var20 * var14 / var16;
                double var22 = 0.0;
                double var24 = (double)var1 + 0.5 + (double)var11[0][0] * 0.5;
                double var26 = (double)var3 + 0.5 + (double)var11[0][2] * 0.5;
                double var28 = (double)var1 + 0.5 + (double)var11[1][0] * 0.5;
                double var30 = (double)var3 + 0.5 + (double)var11[1][2] * 0.5;
                var12 = var28 - var24;
                var14 = var30 - var26;
                if (var12 == 0.0) {
                    this.posX = (double)var1 + 0.5;
                    var22 = this.posZ - (double)var3;
                } else if (var14 == 0.0) {
                    this.posZ = (double)var3 + 0.5;
                    var22 = this.posX - (double)var1;
                } else {
                    var32 = this.posX - var24;
                    var34 = this.posZ - var26;
                    var22 = var36 = (var32 * var12 + var34 * var14) * 2.0;
                }
                this.posX = var24 + var12 * var22;
                this.posZ = var26 + var14 * var22;
                this.setPosition(this.posX, this.posY + (double)this.yOffset, this.posZ);
                var32 = this.motionX;
                var34 = this.motionZ;
                if (this.riddenByEntity != null) {
                    var32 *= 0.75;
                    var34 *= 0.75;
                }
                if (var32 < -var4) {
                    var32 = -var4;
                }
                if (var32 > var4) {
                    var32 = var4;
                }
                if (var34 < -var4) {
                    var34 = -var4;
                }
                if (var34 > var4) {
                    var34 = var4;
                }
                this.moveEntity(var32, 0.0, var34);
                if (var11[0][1] != 0 && MathHelper.floor_double(this.posX) - var1 == var11[0][0] && MathHelper.floor_double(this.posZ) - var3 == var11[0][2]) {
                    this.setPosition(this.posX, this.posY + (double)var11[0][1], this.posZ);
                } else if (var11[1][1] != 0 && MathHelper.floor_double(this.posX) - var1 == var11[1][0] && MathHelper.floor_double(this.posZ) - var3 == var11[1][2]) {
                    this.setPosition(this.posX, this.posY + (double)var11[1][1], this.posZ);
                }
                if (this.riddenByEntity != null) {
                    this.motionX *= (double)0.997f;
                    this.motionY *= 0.0;
                    this.motionZ *= (double)0.997f;
                } else {
                    if (this.minecartType == 2) {
                        var36 = MathHelper.sqrt_double(this.pushX * this.pushX + this.pushZ * this.pushZ);
                        if (var36 > 0.01) {
                            var6 = true;
                            this.pushX /= var36;
                            this.pushZ /= var36;
                            double var38 = 0.04;
                            this.motionX *= (double)0.8f;
                            this.motionY *= 0.0;
                            this.motionZ *= (double)0.8f;
                            this.motionX += this.pushX * var38;
                            this.motionZ += this.pushZ * var38;
                        } else {
                            this.motionX *= (double)0.9f;
                            this.motionY *= 0.0;
                            this.motionZ *= (double)0.9f;
                        }
                    }
                    this.motionX *= (double)0.96f;
                    this.motionY *= 0.0;
                    this.motionZ *= (double)0.96f;
                }
                Vec3D var46 = this.func_514_g(this.posX, this.posY, this.posZ);
                if (var46 != null && var9 != null) {
                    double var37 = (var9.yCoord - var46.yCoord) * 0.05;
                    var20 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
                    if (var20 > 0.0) {
                        this.motionX = this.motionX / var20 * (var20 + var37);
                        this.motionZ = this.motionZ / var20 * (var20 + var37);
                    }
                    this.setPosition(this.posX, var46.yCoord, this.posZ);
                }
                int var47 = MathHelper.floor_double(this.posX);
                int var48 = MathHelper.floor_double(this.posZ);
                if (var47 != var1 || var48 != var3) {
                    var20 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
                    this.motionX = var20 * (double)(var47 - var1);
                    this.motionZ = var20 * (double)(var48 - var3);
                }
                if (this.minecartType == 2 && (var39 = (double)MathHelper.sqrt_double(this.pushX * this.pushX + this.pushZ * this.pushZ)) > 0.01 && this.motionX * this.motionX + this.motionZ * this.motionZ > 0.001) {
                    this.pushX /= var39;
                    this.pushZ /= var39;
                    if (this.pushX * this.motionX + this.pushZ * this.motionZ < 0.0) {
                        this.pushX = 0.0;
                        this.pushZ = 0.0;
                    } else {
                        this.pushX = this.motionX;
                        this.pushZ = this.motionZ;
                    }
                }
            } else {
                if (this.motionX < -var4) {
                    this.motionX = -var4;
                }
                if (this.motionX > var4) {
                    this.motionX = var4;
                }
                if (this.motionZ < -var4) {
                    this.motionZ = -var4;
                }
                if (this.motionZ > var4) {
                    this.motionZ = var4;
                }
                if (this.onGround) {
                    this.motionX *= 0.5;
                    this.motionY *= 0.5;
                    this.motionZ *= 0.5;
                }
                this.moveEntity(this.motionX, this.motionY, this.motionZ);
                if (!this.onGround) {
                    this.motionX *= (double)0.95f;
                    this.motionY *= (double)0.95f;
                    this.motionZ *= (double)0.95f;
                }
            }
            this.rotationPitch = 0.0f;
            double var43 = this.prevPosX - this.posX;
            double var44 = this.prevPosZ - this.posZ;
            if (var43 * var43 + var44 * var44 > 0.001) {
                this.rotationYaw = (float)(Math.atan2(var44, var43) * 180.0 / Math.PI);
                if (this.field_856_i) {
                    this.rotationYaw += 180.0f;
                }
            }
            double var13 = this.rotationYaw - this.prevRotationYaw;
            while (var13 >= 180.0) {
                var13 -= 360.0;
            }
            while (var13 < -180.0) {
                var13 += 360.0;
            }
            if (var13 < -170.0 || var13 >= 170.0) {
                this.rotationYaw += 180.0f;
                this.field_856_i = !this.field_856_i;
            }
            this.setRotation(this.rotationYaw, this.rotationPitch);
            List var15 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(0.2f, 0.0, 0.2f));
            if (var15 != null && var15.size() > 0) {
                int var45 = 0;
                while (var45 < var15.size()) {
                    Entity var17 = (Entity)var15.get(var45);
                    if (var17 != this.riddenByEntity && var17.canBePushed() && var17 instanceof EntityMinecart) {
                        var17.applyEntityCollision(this);
                    }
                    ++var45;
                }
            }
            if (this.riddenByEntity != null && this.riddenByEntity.isDead) {
                this.riddenByEntity = null;
            }
            if (var6 && this.rand.nextInt(4) == 0) {
                --this.fuel;
                if (this.fuel < 0) {
                    this.pushZ = 0.0;
                    this.pushX = 0.0;
                }
                this.worldObj.spawnParticle("largesmoke", this.posX, this.posY + 0.8, this.posZ, 0.0, 0.0, 0.0);
            }
        }
    }

    public Vec3D func_515_a(double var1, double var3, double var5, double var7) {
        int var11;
        int var10;
        int var9 = MathHelper.floor_double(var1);
        if (this.worldObj.getBlockId(var9, (var10 = MathHelper.floor_double(var3)) - 1, var11 = MathHelper.floor_double(var5)) == Block.minecartTrack.blockID) {
            --var10;
        }
        if (this.worldObj.getBlockId(var9, var10, var11) == Block.minecartTrack.blockID) {
            int var12 = this.worldObj.getBlockMetadata(var9, var10, var11);
            var3 = var10;
            if (var12 >= 2 && var12 <= 5) {
                var3 = var10 + 1;
            }
            int[][] var13 = field_855_j[var12];
            double var14 = var13[1][0] - var13[0][0];
            double var16 = var13[1][2] - var13[0][2];
            double var18 = Math.sqrt(var14 * var14 + var16 * var16);
            if (var13[0][1] != 0 && MathHelper.floor_double(var1 += (var14 /= var18) * var7) - var9 == var13[0][0] && MathHelper.floor_double(var5 += (var16 /= var18) * var7) - var11 == var13[0][2]) {
                var3 += (double)var13[0][1];
            } else if (var13[1][1] != 0 && MathHelper.floor_double(var1) - var9 == var13[1][0] && MathHelper.floor_double(var5) - var11 == var13[1][2]) {
                var3 += (double)var13[1][1];
            }
            return this.func_514_g(var1, var3, var5);
        }
        return null;
    }

    public Vec3D func_514_g(double var1, double var3, double var5) {
        int var9;
        int var8;
        int var7 = MathHelper.floor_double(var1);
        if (this.worldObj.getBlockId(var7, (var8 = MathHelper.floor_double(var3)) - 1, var9 = MathHelper.floor_double(var5)) == Block.minecartTrack.blockID) {
            --var8;
        }
        if (this.worldObj.getBlockId(var7, var8, var9) == Block.minecartTrack.blockID) {
            int var10 = this.worldObj.getBlockMetadata(var7, var8, var9);
            var3 = var8;
            if (var10 >= 2 && var10 <= 5) {
                var3 = var8 + 1;
            }
            int[][] var11 = field_855_j[var10];
            double var12 = 0.0;
            double var14 = (double)var7 + 0.5 + (double)var11[0][0] * 0.5;
            double var16 = (double)var8 + 0.5 + (double)var11[0][1] * 0.5;
            double var18 = (double)var9 + 0.5 + (double)var11[0][2] * 0.5;
            double var20 = (double)var7 + 0.5 + (double)var11[1][0] * 0.5;
            double var22 = (double)var8 + 0.5 + (double)var11[1][1] * 0.5;
            double var24 = (double)var9 + 0.5 + (double)var11[1][2] * 0.5;
            double var26 = var20 - var14;
            double var28 = (var22 - var16) * 2.0;
            double var30 = var24 - var18;
            if (var26 == 0.0) {
                var1 = (double)var7 + 0.5;
                var12 = var5 - (double)var9;
            } else if (var30 == 0.0) {
                var5 = (double)var9 + 0.5;
                var12 = var1 - (double)var7;
            } else {
                double var36;
                double var32 = var1 - var14;
                double var34 = var5 - var18;
                var12 = var36 = (var32 * var26 + var34 * var30) * 2.0;
            }
            var1 = var14 + var26 * var12;
            var3 = var16 + var28 * var12;
            var5 = var18 + var30 * var12;
            if (var28 < 0.0) {
                var3 += 1.0;
            }
            if (var28 > 0.0) {
                var3 += 0.5;
            }
            return Vec3D.createVector(var1, var3, var5);
        }
        return null;
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound var1) {
        var1.setInteger("Type", this.minecartType);
        if (this.minecartType == 2) {
            var1.setDouble("PushX", this.pushX);
            var1.setDouble("PushZ", this.pushZ);
            var1.setShort("Fuel", (short)this.fuel);
        } else if (this.minecartType == 1) {
            NBTTagList var2 = new NBTTagList();
            int var3 = 0;
            while (var3 < this.cargoItems.length) {
                if (this.cargoItems[var3] != null) {
                    NBTTagCompound var4 = new NBTTagCompound();
                    var4.setByte("Slot", (byte)var3);
                    this.cargoItems[var3].writeToNBT(var4);
                    var2.setTag(var4);
                }
                ++var3;
            }
            var1.setTag("Items", var2);
        }
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound var1) {
        this.minecartType = var1.getInteger("Type");
        if (this.minecartType == 2) {
            this.pushX = var1.getDouble("PushX");
            this.pushZ = var1.getDouble("PushZ");
            this.fuel = var1.getShort("Fuel");
        } else if (this.minecartType == 1) {
            NBTTagList var2 = var1.getTagList("Items");
            this.cargoItems = new ItemStack[this.getSizeInventory()];
            int var3 = 0;
            while (var3 < var2.tagCount()) {
                NBTTagCompound var4 = (NBTTagCompound)var2.tagAt(var3);
                int var5 = var4.getByte("Slot") & 0xFF;
                if (var5 >= 0 && var5 < this.cargoItems.length) {
                    this.cargoItems[var5] = new ItemStack(var4);
                }
                ++var3;
            }
        }
    }

    @Override
    public float getShadowSize() {
        return 0.0f;
    }

    @Override
    public void applyEntityCollision(Entity var1) {
        if (!this.worldObj.multiplayerWorld && var1 != this.riddenByEntity) {
            double var4;
            double var2;
            double var6;
            if (var1 instanceof EntityLiving && !(var1 instanceof EntityPlayer) && this.minecartType == 0 && this.motionX * this.motionX + this.motionZ * this.motionZ > 0.01 && this.riddenByEntity == null && var1.ridingEntity == null) {
                var1.mountEntity(this);
            }
            if ((var6 = (var2 = var1.posX - this.posX) * var2 + (var4 = var1.posZ - this.posZ) * var4) >= (double)1.0E-4f) {
                var6 = MathHelper.sqrt_double(var6);
                var2 /= var6;
                var4 /= var6;
                double var8 = 1.0 / var6;
                if (var8 > 1.0) {
                    var8 = 1.0;
                }
                var2 *= var8;
                var4 *= var8;
                var2 *= (double)0.1f;
                var4 *= (double)0.1f;
                var2 *= (double)(1.0f - this.entityCollisionReduction);
                var4 *= (double)(1.0f - this.entityCollisionReduction);
                var2 *= 0.5;
                var4 *= 0.5;
                if (var1 instanceof EntityMinecart) {
                    double var10 = var1.motionX + this.motionX;
                    double var12 = var1.motionZ + this.motionZ;
                    if (((EntityMinecart)var1).minecartType == 2 && this.minecartType != 2) {
                        this.motionX *= (double)0.2f;
                        this.motionZ *= (double)0.2f;
                        this.addVelocity(var1.motionX - var2, 0.0, var1.motionZ - var4);
                        var1.motionX *= (double)0.7f;
                        var1.motionZ *= (double)0.7f;
                    } else if (((EntityMinecart)var1).minecartType != 2 && this.minecartType == 2) {
                        var1.motionX *= (double)0.2f;
                        var1.motionZ *= (double)0.2f;
                        var1.addVelocity(this.motionX + var2, 0.0, this.motionZ + var4);
                        this.motionX *= (double)0.7f;
                        this.motionZ *= (double)0.7f;
                    } else {
                        this.motionX *= (double)0.2f;
                        this.motionZ *= (double)0.2f;
                        this.addVelocity((var10 /= 2.0) - var2, 0.0, (var12 /= 2.0) - var4);
                        var1.motionX *= (double)0.2f;
                        var1.motionZ *= (double)0.2f;
                        var1.addVelocity(var10 + var2, 0.0, var12 + var4);
                    }
                } else {
                    this.addVelocity(-var2, 0.0, -var4);
                    var1.addVelocity(var2 / 4.0, 0.0, var4 / 4.0);
                }
            }
        }
    }

    @Override
    public int getSizeInventory() {
        return 27;
    }

    @Override
    public ItemStack getStackInSlot(int var1) {
        return this.cargoItems[var1];
    }

    @Override
    public ItemStack decrStackSize(int var1, int var2) {
        if (this.cargoItems[var1] != null) {
            if (this.cargoItems[var1].stackSize <= var2) {
                ItemStack var3 = this.cargoItems[var1];
                this.cargoItems[var1] = null;
                return var3;
            }
            ItemStack var3 = this.cargoItems[var1].splitStack(var2);
            if (this.cargoItems[var1].stackSize == 0) {
                this.cargoItems[var1] = null;
            }
            return var3;
        }
        return null;
    }

    @Override
    public void setInventorySlotContents(int var1, ItemStack var2) {
        this.cargoItems[var1] = var2;
        if (var2 != null && var2.stackSize > this.getInventoryStackLimit()) {
            var2.stackSize = this.getInventoryStackLimit();
        }
    }

    @Override
    public String getInvName() {
        return "Minecart";
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public void onInventoryChanged() {
    }

    @Override
    public boolean interact(EntityPlayer var1) {
        if (this.minecartType == 0) {
            if (this.riddenByEntity != null && this.riddenByEntity instanceof EntityPlayer && this.riddenByEntity != var1) {
                return true;
            }
            if (!this.worldObj.multiplayerWorld) {
                var1.mountEntity(this);
            }
        } else if (this.minecartType == 1) {
            if (!this.worldObj.multiplayerWorld) {
                var1.displayGUIChest(this);
            }
        } else if (this.minecartType == 2) {
            ItemStack var2 = var1.inventory.getCurrentItem();
            if (var2 != null && var2.itemID == Item.coal.shiftedIndex) {
                if (--var2.stackSize == 0) {
                    var1.inventory.setInventorySlotContents(var1.inventory.currentItem, null);
                }
                this.fuel += 1200;
            }
            this.pushX = this.posX - var1.posX;
            this.pushZ = this.posZ - var1.posZ;
        }
        return true;
    }

    @Override
    public void setPositionAndRotation2(double var1, double var3, double var5, float var7, float var8, int var9) {
        this.field_9414_l = var1;
        this.field_9413_m = var3;
        this.field_9412_n = var5;
        this.field_9411_o = var7;
        this.field_9410_p = var8;
        this.field_9415_k = var9 + 2;
        this.motionX = this.field_9409_q;
        this.motionY = this.field_9408_r;
        this.motionZ = this.field_9407_s;
    }

    @Override
    public void setVelocity(double var1, double var3, double var5) {
        this.field_9409_q = this.motionX = var1;
        this.field_9408_r = this.motionY = var3;
        this.field_9407_s = this.motionZ = var5;
    }

    @Override
    public boolean canInteractWith(EntityPlayer var1) {
        if (this.isDead) {
            return false;
        }
        return var1.getDistanceSqToEntity(this) <= 64.0;
    }
}

