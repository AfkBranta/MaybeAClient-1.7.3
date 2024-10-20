/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.List;
import java.util.Random;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Block;
import net.minecraft.src.BlockFluids;
import net.minecraft.src.DataWatcher;
import net.minecraft.src.EntityItem;
import net.minecraft.src.EntityList;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.MathHelper;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.NBTTagDouble;
import net.minecraft.src.NBTTagFloat;
import net.minecraft.src.NBTTagList;
import net.minecraft.src.StepSound;
import net.minecraft.src.Vec3D;
import net.minecraft.src.World;
import net.skidcode.gh.maybeaclient.Client;
import net.skidcode.gh.maybeaclient.hacks.CameraLockHack;
import net.skidcode.gh.maybeaclient.hacks.FreecamHack;
import net.skidcode.gh.maybeaclient.hacks.NoClipHack;
import net.skidcode.gh.maybeaclient.hacks.NoPushHack;
import net.skidcode.gh.maybeaclient.hacks.SafeWalkHack;

public abstract class Entity {
    private static int nextEntityID = 0;
    public int entityId = nextEntityID++;
    public double renderDistanceWeight = 1.0;
    public boolean preventEntitySpawning = false;
    public Entity riddenByEntity;
    public Entity ridingEntity;
    public World worldObj;
    public double prevPosX;
    public double prevPosY;
    public double prevPosZ;
    public double posX;
    public double posY;
    public double posZ;
    public double motionX;
    public double motionY;
    public double motionZ;
    public float rotationYaw;
    public float rotationPitch;
    public float prevRotationYaw;
    public float prevRotationPitch;
    public final AxisAlignedBB boundingBox = AxisAlignedBB.getBoundingBox(0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
    public boolean onGround = false;
    public boolean isCollidedHorizontally;
    public boolean isCollidedVertically;
    public boolean isCollided = false;
    public boolean beenAttacked = false;
    public boolean field_9293_aM = true;
    public boolean isDead = false;
    public float yOffset = 0.0f;
    public float width = 0.6f;
    public float height = 1.8f;
    public float prevDistanceWalkedModified = 0.0f;
    public float distanceWalkedModified = 0.0f;
    public float fallDistance = 0.0f;
    private int nextStepDistance = 1;
    public double lastTickPosX;
    public double lastTickPosY;
    public double lastTickPosZ;
    public float ySize = 0.0f;
    public float stepHeight = 0.0f;
    public boolean noClip = false;
    public float entityCollisionReduction = 0.0f;
    public boolean field_9313_bc = false;
    protected Random rand = new Random();
    public int ticksExisted = 0;
    public int fireResistance = 1;
    public int fire = 0;
    protected int maxAir = 300;
    protected boolean inWater = false;
    public int field_9306_bj = 0;
    public int air = 300;
    private boolean isFirstUpdate = true;
    public String skinUrl;
    public String cloakUrl;
    protected boolean isImmuneToFire = false;
    protected DataWatcher dataWatcher = new DataWatcher();
    private double entityRiderPitchDelta;
    private double entityRiderYawDelta;
    public boolean addedToChunk = false;
    public int chunkCoordX;
    public int chunkCoordY;
    public int chunkCoordZ;
    public int serverPosX;
    public int serverPosY;
    public int serverPosZ;

    public Entity(World var1) {
        this.worldObj = var1;
        this.setPosition(0.0, 0.0, 0.0);
        this.dataWatcher.addObject(0, (byte)0);
        this.entityInit();
    }

    protected abstract void entityInit();

    public DataWatcher getDataWatcher() {
        return this.dataWatcher;
    }

    public boolean equals(Object var1) {
        if (var1 instanceof Entity) {
            return ((Entity)var1).entityId == this.entityId;
        }
        return false;
    }

    public int hashCode() {
        return this.entityId;
    }

    protected void preparePlayerToSpawn() {
        if (this.worldObj != null) {
            while (this.posY > 0.0) {
                this.setPosition(this.posX, this.posY, this.posZ);
                if (this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox).size() == 0) break;
                this.posY += 1.0;
            }
            this.motionZ = 0.0;
            this.motionY = 0.0;
            this.motionX = 0.0;
            this.rotationPitch = 0.0f;
        }
    }

    public void setEntityDead() {
        this.isDead = true;
    }

    protected void setSize(float var1, float var2) {
        this.width = var1;
        this.height = var2;
    }

    protected void setRotation(float var1, float var2) {
        this.rotationYaw = var1;
        this.rotationPitch = var2;
    }

    public void setPosition(double var1, double var3, double var5) {
        this.posX = var1;
        this.posY = var3;
        this.posZ = var5;
        float var7 = this.width / 2.0f;
        float var8 = this.height;
        this.boundingBox.setBounds(var1 - (double)var7, var3 - (double)this.yOffset + (double)this.ySize, var5 - (double)var7, var1 + (double)var7, var3 - (double)this.yOffset + (double)this.ySize + (double)var8, var5 + (double)var7);
    }

    public void func_346_d(float var1, float var2) {
        if (CameraLockHack.instance.status) {
            if (CameraLockHack.instance.lockPitch.value) {
                var2 = 0.0f;
            }
            if (CameraLockHack.instance.lockYaw.value) {
                var1 = 0.0f;
            }
        }
        float var3 = this.rotationPitch;
        float var4 = this.rotationYaw;
        this.rotationYaw = (float)((double)this.rotationYaw + (double)var1 * 0.15);
        this.rotationPitch = (float)((double)this.rotationPitch - (double)var2 * 0.15);
        if (this.rotationPitch < -90.0f) {
            this.rotationPitch = -90.0f;
        }
        if (this.rotationPitch > 90.0f) {
            this.rotationPitch = 90.0f;
        }
        this.prevRotationPitch += this.rotationPitch - var3;
        this.prevRotationYaw += this.rotationYaw - var4;
    }

    public void onUpdate() {
        this.onEntityUpdate();
    }

    public void onEntityUpdate() {
        if (this.ridingEntity != null && this.ridingEntity.isDead) {
            this.ridingEntity = null;
        }
        ++this.ticksExisted;
        this.prevDistanceWalkedModified = this.distanceWalkedModified;
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.prevRotationPitch = this.rotationPitch;
        this.prevRotationYaw = this.rotationYaw;
        if (this.handleWaterMovement()) {
            if (!this.inWater && !this.isFirstUpdate) {
                float var5;
                float var4;
                float var1 = MathHelper.sqrt_double(this.motionX * this.motionX * (double)0.2f + this.motionY * this.motionY + this.motionZ * this.motionZ * (double)0.2f) * 0.2f;
                if (var1 > 1.0f) {
                    var1 = 1.0f;
                }
                this.worldObj.playSoundAtEntity(this, "random.splash", var1, 1.0f + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.4f);
                float var2 = MathHelper.floor_double(this.boundingBox.minY);
                int var3 = 0;
                while ((float)var3 < 1.0f + this.width * 20.0f) {
                    var4 = (this.rand.nextFloat() * 2.0f - 1.0f) * this.width;
                    var5 = (this.rand.nextFloat() * 2.0f - 1.0f) * this.width;
                    this.worldObj.spawnParticle("bubble", this.posX + (double)var4, var2 + 1.0f, this.posZ + (double)var5, this.motionX, this.motionY - (double)(this.rand.nextFloat() * 0.2f), this.motionZ);
                    ++var3;
                }
                var3 = 0;
                while ((float)var3 < 1.0f + this.width * 20.0f) {
                    var4 = (this.rand.nextFloat() * 2.0f - 1.0f) * this.width;
                    var5 = (this.rand.nextFloat() * 2.0f - 1.0f) * this.width;
                    this.worldObj.spawnParticle("splash", this.posX + (double)var4, var2 + 1.0f, this.posZ + (double)var5, this.motionX, this.motionY, this.motionZ);
                    ++var3;
                }
            }
            this.fallDistance = 0.0f;
            this.inWater = true;
            this.fire = 0;
        } else {
            this.inWater = false;
        }
        if (this.worldObj.multiplayerWorld) {
            this.fire = 0;
        } else if (this.fire > 0) {
            if (this.isImmuneToFire) {
                this.fire -= 4;
                if (this.fire < 0) {
                    this.fire = 0;
                }
            } else {
                if (this.fire % 20 == 0) {
                    this.attackEntityFrom(null, 1);
                }
                --this.fire;
            }
        }
        if (this.handleLavaMovement()) {
            this.setOnFireFromLava();
        }
        if (this.posY < -64.0) {
            this.kill();
        }
        if (!this.worldObj.multiplayerWorld) {
            this.setEntityFlag(0, this.fire > 0);
            this.setEntityFlag(2, this.ridingEntity != null);
        }
        this.isFirstUpdate = false;
    }

    protected void setOnFireFromLava() {
        if (!this.isImmuneToFire) {
            this.attackEntityFrom(null, 4);
            this.fire = 600;
        }
    }

    protected void kill() {
        this.setEntityDead();
    }

    public boolean isOffsetPositionInLiquid(double var1, double var3, double var5) {
        AxisAlignedBB var7 = this.boundingBox.getOffsetBoundingBox(var1, var3, var5);
        List var8 = this.worldObj.getCollidingBoundingBoxes(this, var7);
        if (var8.size() > 0) {
            return false;
        }
        return !this.worldObj.getIsAnyLiquid(var7);
    }

    public void moveEntity(double var1, double var3, double var5) {
        if (this.noClip || FreecamHack.instance.status && FreecamHack.instance.noclip.value && this == Client.mc.thePlayer || NoClipHack.instance.status && this == Client.mc.thePlayer) {
            this.boundingBox.offset(var1, var3, var5);
            this.posX = (this.boundingBox.minX + this.boundingBox.maxX) / 2.0;
            this.posY = this.boundingBox.minY + (double)this.yOffset - (double)this.ySize;
            this.posZ = (this.boundingBox.minZ + this.boundingBox.maxZ) / 2.0;
        } else {
            int var30;
            int var41;
            int var40;
            int var26;
            int var38;
            int var28;
            double var23;
            double var37;
            boolean var18;
            double var7 = this.posX;
            double var9 = this.posZ;
            double var11 = var1;
            double var13 = var3;
            double var15 = var5;
            AxisAlignedBB var17 = this.boundingBox.copy();
            boolean bl = var18 = this.onGround && (this.isSneaking() || SafeWalkHack.instance.status);
            if (var18) {
                BlockFluids.forceNullBB = true;
                double var19 = 0.05;
                while (var1 != 0.0 && this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox.getOffsetBoundingBox(var1, -1.0, 0.0)).size() == 0) {
                    var1 = var1 < var19 && var1 >= -var19 ? 0.0 : (var1 > 0.0 ? (var1 -= var19) : (var1 += var19));
                    var11 = var1;
                }
                while (var5 != 0.0 && this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox.getOffsetBoundingBox(0.0, -1.0, var5)).size() == 0) {
                    var5 = var5 < var19 && var5 >= -var19 ? 0.0 : (var5 > 0.0 ? (var5 -= var19) : (var5 += var19));
                    var15 = var5;
                }
                BlockFluids.forceNullBB = false;
            }
            List var35 = this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox.addCoord(var1, var3, var5));
            int var20 = 0;
            while (var20 < var35.size()) {
                var3 = ((AxisAlignedBB)var35.get(var20)).calculateYOffset(this.boundingBox, var3);
                ++var20;
            }
            this.boundingBox.offset(0.0, var3, 0.0);
            if (!this.field_9293_aM && var13 != var3) {
                var5 = 0.0;
                var3 = 0.0;
                var1 = 0.0;
            }
            boolean var36 = this.onGround || var13 != var3 && var13 < 0.0;
            int var21 = 0;
            while (var21 < var35.size()) {
                var1 = ((AxisAlignedBB)var35.get(var21)).calculateXOffset(this.boundingBox, var1);
                ++var21;
            }
            this.boundingBox.offset(var1, 0.0, 0.0);
            if (!this.field_9293_aM && var11 != var1) {
                var5 = 0.0;
                var3 = 0.0;
                var1 = 0.0;
            }
            var21 = 0;
            while (var21 < var35.size()) {
                var5 = ((AxisAlignedBB)var35.get(var21)).calculateZOffset(this.boundingBox, var5);
                ++var21;
            }
            this.boundingBox.offset(0.0, 0.0, var5);
            if (!this.field_9293_aM && var15 != var5) {
                var5 = 0.0;
                var3 = 0.0;
                var1 = 0.0;
            }
            if (this.stepHeight > 0.0f && var36 && this.ySize < 0.05f && (var11 != var1 || var15 != var5)) {
                var37 = var1;
                var23 = var3;
                double var25 = var5;
                var1 = var11;
                var3 = this.stepHeight;
                var5 = var15;
                AxisAlignedBB var27 = this.boundingBox.copy();
                this.boundingBox.setBB(var17);
                var35 = this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox.addCoord(var11, var3, var15));
                var28 = 0;
                while (var28 < var35.size()) {
                    var3 = ((AxisAlignedBB)var35.get(var28)).calculateYOffset(this.boundingBox, var3);
                    ++var28;
                }
                this.boundingBox.offset(0.0, var3, 0.0);
                if (!this.field_9293_aM && var13 != var3) {
                    var5 = 0.0;
                    var3 = 0.0;
                    var1 = 0.0;
                }
                var28 = 0;
                while (var28 < var35.size()) {
                    var1 = ((AxisAlignedBB)var35.get(var28)).calculateXOffset(this.boundingBox, var1);
                    ++var28;
                }
                this.boundingBox.offset(var1, 0.0, 0.0);
                if (!this.field_9293_aM && var11 != var1) {
                    var5 = 0.0;
                    var3 = 0.0;
                    var1 = 0.0;
                }
                var28 = 0;
                while (var28 < var35.size()) {
                    var5 = ((AxisAlignedBB)var35.get(var28)).calculateZOffset(this.boundingBox, var5);
                    ++var28;
                }
                this.boundingBox.offset(0.0, 0.0, var5);
                if (!this.field_9293_aM && var15 != var5) {
                    var5 = 0.0;
                    var3 = 0.0;
                    var1 = 0.0;
                }
                var3 = -this.stepHeight;
                int k3 = 0;
                while (k3 < var35.size()) {
                    var3 = ((AxisAlignedBB)var35.get(k3)).calculateYOffset(this.boundingBox, var3);
                    ++k3;
                }
                this.boundingBox.offset(0.0, var3, 0.0);
                if (var37 * var37 + var25 * var25 >= var1 * var1 + var5 * var5) {
                    var1 = var37;
                    var3 = var23;
                    var5 = var25;
                    this.boundingBox.setBB(var27);
                } else {
                    this.ySize = (float)((double)this.ySize + 0.5);
                }
            }
            this.posX = (this.boundingBox.minX + this.boundingBox.maxX) / 2.0;
            this.posY = this.boundingBox.minY + (double)this.yOffset - (double)this.ySize;
            this.posZ = (this.boundingBox.minZ + this.boundingBox.maxZ) / 2.0;
            this.isCollidedHorizontally = var11 != var1 || var15 != var5;
            this.isCollidedVertically = var13 != var3;
            this.onGround = var13 != var3 && var13 < 0.0;
            this.isCollided = this.isCollidedHorizontally || this.isCollidedVertically;
            this.updateFallState(var3, this.onGround);
            if (var11 != var1) {
                this.motionX = 0.0;
            }
            if (var13 != var3) {
                this.motionY = 0.0;
            }
            if (var15 != var5) {
                this.motionZ = 0.0;
            }
            var37 = this.posX - var7;
            var23 = this.posZ - var9;
            if (this.canTriggerWalking() && !var18) {
                this.distanceWalkedModified = (float)((double)this.distanceWalkedModified + (double)MathHelper.sqrt_double(var37 * var37 + var23 * var23) * 0.6);
                var38 = MathHelper.floor_double(this.posX);
                int var262 = MathHelper.floor_double(this.posY - (double)0.2f - (double)this.yOffset);
                int var402 = MathHelper.floor_double(this.posZ);
                var28 = this.worldObj.getBlockId(var38, var262, var402);
                if (this.distanceWalkedModified > (float)this.nextStepDistance && var28 > 0) {
                    ++this.nextStepDistance;
                    StepSound var29 = Block.blocksList[var28].stepSound;
                    if (this.worldObj.getBlockId(var38, var262 + 1, var402) == Block.snow.blockID) {
                        var29 = Block.snow.stepSound;
                        this.worldObj.playSoundAtEntity(this, var29.func_1145_d(), var29.func_1147_b() * 0.15f, var29.func_1144_c());
                    } else if (!Block.blocksList[var28].blockMaterial.getIsLiquid()) {
                        this.worldObj.playSoundAtEntity(this, var29.func_1145_d(), var29.func_1147_b() * 0.15f, var29.func_1144_c());
                    }
                    Block.blocksList[var28].onEntityWalking(this.worldObj, var38, var262, var402, this);
                }
            }
            if (this.worldObj.checkChunksExist(var38 = MathHelper.floor_double(this.boundingBox.minX), var26 = MathHelper.floor_double(this.boundingBox.minY), var40 = MathHelper.floor_double(this.boundingBox.minZ), var28 = MathHelper.floor_double(this.boundingBox.maxX), var41 = MathHelper.floor_double(this.boundingBox.maxY), var30 = MathHelper.floor_double(this.boundingBox.maxZ))) {
                int var31 = var38;
                while (var31 <= var28) {
                    int var32 = var26;
                    while (var32 <= var41) {
                        int var33 = var40;
                        while (var33 <= var30) {
                            int var34 = this.worldObj.getBlockId(var31, var32, var33);
                            if (var34 > 0) {
                                Block.blocksList[var34].onEntityCollidedWithBlock(this.worldObj, var31, var32, var33, this);
                            }
                            ++var33;
                        }
                        ++var32;
                    }
                    ++var31;
                }
            }
            this.ySize *= 0.4f;
            boolean var39 = this.handleWaterMovement();
            if (this.worldObj.isBoundingBoxBurning(this.boundingBox)) {
                this.dealFireDamage(1);
                if (!var39) {
                    ++this.fire;
                    if (this.fire == 0) {
                        this.fire = 300;
                    }
                }
            } else if (this.fire <= 0) {
                this.fire = -this.fireResistance;
            }
            if (var39 && this.fire > 0) {
                this.worldObj.playSoundAtEntity(this, "random.fizz", 0.7f, 1.6f + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.4f);
                this.fire = -this.fireResistance;
            }
        }
    }

    protected boolean canTriggerWalking() {
        return true;
    }

    protected void updateFallState(double var1, boolean var3) {
        if (var3) {
            if (this.fallDistance > 0.0f) {
                this.fall(this.fallDistance);
                this.fallDistance = 0.0f;
            }
        } else if (var1 < 0.0) {
            this.fallDistance = (float)((double)this.fallDistance - var1);
        }
    }

    public AxisAlignedBB getBoundingBox() {
        return null;
    }

    protected void dealFireDamage(int var1) {
        if (!this.isImmuneToFire) {
            this.attackEntityFrom(null, var1);
        }
    }

    protected void fall(float var1) {
    }

    public boolean handleWaterMovement() {
        return this.worldObj.handleMaterialAcceleration(this.boundingBox.expand(0.0, -0.4f, 0.0), Material.water, this);
    }

    public boolean isInsideOfMaterial(Material var1) {
        int var6;
        int var5;
        double var2 = this.posY + (double)this.getEyeHeight();
        int var4 = MathHelper.floor_double(this.posX);
        int var7 = this.worldObj.getBlockId(var4, var5 = MathHelper.floor_float(MathHelper.floor_double(var2)), var6 = MathHelper.floor_double(this.posZ));
        if (var7 != 0 && Block.blocksList[var7].blockMaterial == var1) {
            float var8 = BlockFluids.getPercentAir(this.worldObj.getBlockMetadata(var4, var5, var6)) - 0.11111111f;
            float var9 = (float)(var5 + 1) - var8;
            return var2 < (double)var9;
        }
        return false;
    }

    public float getEyeHeight() {
        return 0.0f;
    }

    public boolean handleLavaMovement() {
        return this.worldObj.isMaterialInBB(this.boundingBox.expand(-0.1f, -0.4f, -0.1f), Material.lava);
    }

    public void moveFlying(float strafe, float forward, float var3) {
        float var4 = MathHelper.sqrt_float(strafe * strafe + forward * forward);
        if (var4 >= 0.01f) {
            if (var4 < 1.0f) {
                var4 = 1.0f;
            }
            var4 = var3 / var4;
            float var5 = MathHelper.sin(this.rotationYaw * (float)Math.PI / 180.0f);
            float var6 = MathHelper.cos(this.rotationYaw * (float)Math.PI / 180.0f);
            this.motionX += (double)((strafe *= var4) * var6 - (forward *= var4) * var5);
            this.motionZ += (double)(forward * var6 + strafe * var5);
        }
    }

    public float getEntityBrightness(float var1) {
        int var2 = MathHelper.floor_double(this.posX);
        double var3 = (this.boundingBox.maxY - this.boundingBox.minY) * 0.66;
        int var5 = MathHelper.floor_double(this.posY - (double)this.yOffset + var3);
        int var6 = MathHelper.floor_double(this.posZ);
        return this.worldObj.checkChunksExist(MathHelper.floor_double(this.boundingBox.minX), MathHelper.floor_double(this.boundingBox.minY), MathHelper.floor_double(this.boundingBox.minZ), MathHelper.floor_double(this.boundingBox.maxX), MathHelper.floor_double(this.boundingBox.maxY), MathHelper.floor_double(this.boundingBox.maxZ)) ? this.worldObj.getLightBrightness(var2, var5, var6) : 0.0f;
    }

    public void setWorld(World var1) {
        this.worldObj = var1;
    }

    public void setPositionAndRotation(double var1, double var3, double var5, float var7, float var8) {
        this.prevPosX = this.posX = var1;
        this.prevPosY = this.posY = var3;
        this.prevPosZ = this.posZ = var5;
        this.lastTickPosX = this.posX;
        this.lastTickPosY = this.posY;
        this.lastTickPosZ = this.posZ;
        this.prevRotationYaw = this.rotationYaw = var7;
        this.prevRotationPitch = this.rotationPitch = var8;
        this.ySize = 0.0f;
        double var9 = this.prevRotationYaw - var7;
        if (var9 < -180.0) {
            this.prevRotationYaw += 360.0f;
        }
        if (var9 >= 180.0) {
            this.prevRotationYaw -= 360.0f;
        }
        this.setPosition(this.posX, this.posY, this.posZ);
        this.setRotation(var7, var8);
    }

    public void setLocationAndAngles(double var1, double var3, double var5, float var7, float var8) {
        this.prevPosX = this.posX = var1;
        this.lastTickPosX = this.posX;
        this.prevPosY = this.posY = var3 + (double)this.yOffset;
        this.lastTickPosY = this.posY;
        this.prevPosZ = this.posZ = var5;
        this.lastTickPosZ = this.posZ;
        this.rotationYaw = var7;
        this.rotationPitch = var8;
        this.setPosition(this.posX, this.posY, this.posZ);
    }

    public float getDistanceToEntity(Entity var1) {
        float var2 = (float)(this.posX - var1.posX);
        float var3 = (float)(this.posY - var1.posY);
        float var4 = (float)(this.posZ - var1.posZ);
        return MathHelper.sqrt_float(var2 * var2 + var3 * var3 + var4 * var4);
    }

    public double getDistanceSq(double var1, double var3, double var5) {
        double var7 = this.posX - var1;
        double var9 = this.posY - var3;
        double var11 = this.posZ - var5;
        return var7 * var7 + var9 * var9 + var11 * var11;
    }

    public double getDistance(double var1, double var3, double var5) {
        double var7 = this.posX - var1;
        double var9 = this.posY - var3;
        double var11 = this.posZ - var5;
        return MathHelper.sqrt_double(var7 * var7 + var9 * var9 + var11 * var11);
    }

    public double getDistanceSqToEntity(Entity var1) {
        double var2 = this.posX - var1.posX;
        double var4 = this.posY - var1.posY;
        double var6 = this.posZ - var1.posZ;
        return var2 * var2 + var4 * var4 + var6 * var6;
    }

    public void onCollideWithPlayer(EntityPlayer var1) {
    }

    public void applyEntityCollision(Entity var1) {
        double var4;
        double var2;
        double var6;
        if (NoPushHack.instance.status) {
            return;
        }
        if (var1.riddenByEntity != this && var1.ridingEntity != this && (var6 = MathHelper.abs_max(var2 = var1.posX - this.posX, var4 = var1.posZ - this.posZ)) >= (double)0.01f) {
            var6 = MathHelper.sqrt_double(var6);
            var2 /= var6;
            var4 /= var6;
            double var8 = 1.0 / var6;
            if (var8 > 1.0) {
                var8 = 1.0;
            }
            var2 *= var8;
            var4 *= var8;
            var2 *= (double)0.05f;
            var4 *= (double)0.05f;
            this.addVelocity(-(var2 *= (double)(1.0f - this.entityCollisionReduction)), 0.0, -(var4 *= (double)(1.0f - this.entityCollisionReduction)));
            var1.addVelocity(var2, 0.0, var4);
        }
    }

    public void addVelocity(double var1, double var3, double var5) {
        this.motionX += var1;
        this.motionY += var3;
        this.motionZ += var5;
    }

    protected void setBeenAttacked() {
        this.beenAttacked = true;
    }

    public boolean attackEntityFrom(Entity var1, int var2) {
        this.setBeenAttacked();
        return false;
    }

    public boolean canBeCollidedWith() {
        return false;
    }

    public boolean canBePushed() {
        return false;
    }

    public void addToPlayerScore(Entity var1, int var2) {
    }

    public boolean isInRangeToRenderVec3D(Vec3D var1) {
        double var2 = this.posX - var1.xCoord;
        double var4 = this.posY - var1.yCoord;
        double var6 = this.posZ - var1.zCoord;
        double var8 = var2 * var2 + var4 * var4 + var6 * var6;
        return this.isInRangeToRenderDist(var8);
    }

    public boolean isInRangeToRenderDist(double var1) {
        double var3 = this.boundingBox.getAverageEdgeLength();
        return var1 < (var3 *= 64.0 * this.renderDistanceWeight) * var3;
    }

    public String getEntityTexture() {
        return null;
    }

    public boolean addEntityID(NBTTagCompound var1) {
        String var2 = this.getEntityString();
        if (!this.isDead && var2 != null) {
            var1.setString("id", var2);
            this.writeToNBT(var1);
            return true;
        }
        return false;
    }

    public void writeToNBT(NBTTagCompound var1) {
        var1.setTag("Pos", this.newDoubleNBTList(this.posX, this.posY, this.posZ));
        var1.setTag("Motion", this.newDoubleNBTList(this.motionX, this.motionY, this.motionZ));
        var1.setTag("Rotation", this.func_377_a(this.rotationYaw, this.rotationPitch));
        var1.setFloat("FallDistance", this.fallDistance);
        var1.setShort("Fire", (short)this.fire);
        var1.setShort("Air", (short)this.air);
        var1.setBoolean("OnGround", this.onGround);
        this.writeEntityToNBT(var1);
    }

    public void readFromNBT(NBTTagCompound var1) {
        NBTTagList var2 = var1.getTagList("Pos");
        NBTTagList var3 = var1.getTagList("Motion");
        NBTTagList var4 = var1.getTagList("Rotation");
        this.setPosition(0.0, 0.0, 0.0);
        this.motionX = ((NBTTagDouble)var3.tagAt((int)0)).doubleValue;
        this.motionY = ((NBTTagDouble)var3.tagAt((int)1)).doubleValue;
        this.motionZ = ((NBTTagDouble)var3.tagAt((int)2)).doubleValue;
        if (Math.abs(this.motionX) > 10.0) {
            this.motionX = 0.0;
        }
        if (Math.abs(this.motionY) > 10.0) {
            this.motionY = 0.0;
        }
        if (Math.abs(this.motionZ) > 10.0) {
            this.motionZ = 0.0;
        }
        this.lastTickPosX = this.posX = ((NBTTagDouble)var2.tagAt((int)0)).doubleValue;
        this.prevPosX = this.posX;
        this.lastTickPosY = this.posY = ((NBTTagDouble)var2.tagAt((int)1)).doubleValue;
        this.prevPosY = this.posY;
        this.lastTickPosZ = this.posZ = ((NBTTagDouble)var2.tagAt((int)2)).doubleValue;
        this.prevPosZ = this.posZ;
        this.prevRotationYaw = this.rotationYaw = ((NBTTagFloat)var4.tagAt((int)0)).floatValue % ((float)Math.PI * 2);
        this.prevRotationPitch = this.rotationPitch = ((NBTTagFloat)var4.tagAt((int)1)).floatValue % ((float)Math.PI * 2);
        this.fallDistance = var1.getFloat("FallDistance");
        this.fire = var1.getShort("Fire");
        this.air = var1.getShort("Air");
        this.onGround = var1.getBoolean("OnGround");
        this.setPosition(this.posX, this.posY, this.posZ);
        this.readEntityFromNBT(var1);
    }

    protected final String getEntityString() {
        return EntityList.getEntityString(this);
    }

    protected abstract void readEntityFromNBT(NBTTagCompound var1);

    protected abstract void writeEntityToNBT(NBTTagCompound var1);

    protected NBTTagList newDoubleNBTList(double ... var1) {
        NBTTagList var2 = new NBTTagList();
        double[] var3 = var1;
        int var4 = var1.length;
        int var5 = 0;
        while (var5 < var4) {
            double var6 = var3[var5];
            var2.setTag(new NBTTagDouble(var6));
            ++var5;
        }
        return var2;
    }

    protected NBTTagList func_377_a(float ... var1) {
        NBTTagList var2 = new NBTTagList();
        float[] var3 = var1;
        int var4 = var1.length;
        int var5 = 0;
        while (var5 < var4) {
            float var6 = var3[var5];
            var2.setTag(new NBTTagFloat(var6));
            ++var5;
        }
        return var2;
    }

    public float getShadowSize() {
        return this.height / 2.0f;
    }

    public EntityItem dropItem(int var1, int var2) {
        return this.dropItemWithOffset(var1, var2, 0.0f);
    }

    public EntityItem dropItemWithOffset(int var1, int var2, float var3) {
        return this.entityDropItem(new ItemStack(var1, var2, 0), var3);
    }

    public EntityItem entityDropItem(ItemStack var1, float var2) {
        EntityItem var3 = new EntityItem(this.worldObj, this.posX, this.posY + (double)var2, this.posZ, var1);
        var3.delayBeforeCanPickup = 10;
        this.worldObj.entityJoinedWorld(var3);
        return var3;
    }

    public boolean isEntityAlive() {
        return !this.isDead;
    }

    public boolean isEntityInsideOpaqueBlock() {
        int var1 = MathHelper.floor_double(this.posX);
        int var2 = MathHelper.floor_double(this.posY + (double)this.getEyeHeight());
        int var3 = MathHelper.floor_double(this.posZ);
        return this.worldObj.isBlockOpaqueCube(var1, var2, var3);
    }

    public boolean interact(EntityPlayer var1) {
        return false;
    }

    public AxisAlignedBB getCollisionBox(Entity var1) {
        return null;
    }

    public void updateRidden() {
        if (this.ridingEntity.isDead) {
            this.ridingEntity = null;
        } else {
            this.motionX = 0.0;
            this.motionY = 0.0;
            this.motionZ = 0.0;
            this.onUpdate();
            this.ridingEntity.updateRiderPosition();
            this.entityRiderYawDelta += (double)(this.ridingEntity.rotationYaw - this.ridingEntity.prevRotationYaw);
            this.entityRiderPitchDelta += (double)(this.ridingEntity.rotationPitch - this.ridingEntity.prevRotationPitch);
            while (this.entityRiderYawDelta >= 180.0) {
                this.entityRiderYawDelta -= 360.0;
            }
            while (this.entityRiderYawDelta < -180.0) {
                this.entityRiderYawDelta += 360.0;
            }
            while (this.entityRiderPitchDelta >= 180.0) {
                this.entityRiderPitchDelta -= 360.0;
            }
            while (this.entityRiderPitchDelta < -180.0) {
                this.entityRiderPitchDelta += 360.0;
            }
            double var1 = this.entityRiderYawDelta * 0.5;
            double var3 = this.entityRiderPitchDelta * 0.5;
            float var5 = 10.0f;
            if (var1 > (double)var5) {
                var1 = var5;
            }
            if (var1 < (double)(-var5)) {
                var1 = -var5;
            }
            if (var3 > (double)var5) {
                var3 = var5;
            }
            if (var3 < (double)(-var5)) {
                var3 = -var5;
            }
            this.entityRiderYawDelta -= var1;
            this.entityRiderPitchDelta -= var3;
            this.rotationYaw = (float)((double)this.rotationYaw + var1);
            this.rotationPitch = (float)((double)this.rotationPitch + var3);
        }
    }

    public void updateRiderPosition() {
        this.riddenByEntity.setPosition(this.posX, this.posY + this.getMountedYOffset() + this.riddenByEntity.getYOffset(), this.posZ);
    }

    public double getYOffset() {
        return this.yOffset;
    }

    public double getMountedYOffset() {
        return (double)this.height * 0.75;
    }

    public void mountEntity(Entity var1) {
        this.entityRiderPitchDelta = 0.0;
        this.entityRiderYawDelta = 0.0;
        if (var1 == null) {
            if (this.ridingEntity != null) {
                this.setLocationAndAngles(this.ridingEntity.posX, this.ridingEntity.boundingBox.minY + (double)this.ridingEntity.height, this.ridingEntity.posZ, this.rotationYaw, this.rotationPitch);
                this.ridingEntity.riddenByEntity = null;
            }
            this.ridingEntity = null;
        } else if (this.ridingEntity == var1) {
            this.ridingEntity.riddenByEntity = null;
            this.ridingEntity = null;
            this.setLocationAndAngles(var1.posX, var1.boundingBox.minY + (double)var1.height, var1.posZ, this.rotationYaw, this.rotationPitch);
        } else {
            if (this.ridingEntity != null) {
                this.ridingEntity.riddenByEntity = null;
            }
            if (var1.riddenByEntity != null) {
                var1.riddenByEntity.ridingEntity = null;
            }
            this.ridingEntity = var1;
            var1.riddenByEntity = this;
        }
    }

    public void setPositionAndRotation2(double var1, double var3, double var5, float var7, float var8, int var9) {
        this.setPosition(var1, var3, var5);
        this.setRotation(var7, var8);
    }

    public float getCollisionBorderSize() {
        return 0.1f;
    }

    public Vec3D getLookVec() {
        return null;
    }

    public void setInPortal() {
    }

    public void setVelocity(double var1, double var3, double var5) {
        this.motionX = var1;
        this.motionY = var3;
        this.motionZ = var5;
    }

    public void handleHealthUpdate(byte var1) {
    }

    public void performHurtAnimation() {
    }

    public void updateCloak() {
    }

    public void outfitWithItem(int var1, int var2, int var3) {
    }

    public boolean isBurning() {
        return this.fire > 0 || this.getEntityFlag(0);
    }

    public boolean isRiding() {
        return this.ridingEntity != null || this.getEntityFlag(2);
    }

    public boolean isSneaking() {
        return this.getEntityFlag(1);
    }

    protected boolean getEntityFlag(int var1) {
        return (this.dataWatcher.getWatchableObjectByte(0) & 1 << var1) != 0;
    }

    protected void setEntityFlag(int var1, boolean var2) {
        byte var3 = this.dataWatcher.getWatchableObjectByte(0);
        if (var2) {
            this.dataWatcher.updateObject(0, (byte)(var3 | 1 << var1));
        } else {
            this.dataWatcher.updateObject(0, (byte)(var3 & ~(1 << var1)));
        }
    }
}

