/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.List;
import net.minecraft.src.Block;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.MathHelper;
import net.minecraft.src.MovingObjectPosition;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.StepSound;
import net.minecraft.src.Vec3D;
import net.minecraft.src.World;
import net.skidcode.gh.maybeaclient.Client;
import net.skidcode.gh.maybeaclient.hacks.AntiSlowdownHack;
import net.skidcode.gh.maybeaclient.hacks.AutoTunnelHack;
import net.skidcode.gh.maybeaclient.hacks.FastLadderHack;
import net.skidcode.gh.maybeaclient.hacks.StrafeHack;

public abstract class EntityLiving
extends Entity {
    public int field_9366_o = 20;
    public float field_9365_p;
    public float field_9363_r;
    public float renderYawOffset = 0.0f;
    public float prevRenderYawOffset = 0.0f;
    protected float field_9362_u;
    protected float field_9361_v;
    protected float field_9360_w;
    protected float field_9359_x;
    protected boolean field_9358_y = true;
    protected String texture = "/mob/char.png";
    protected boolean field_9355_A = true;
    protected float field_9353_B = 0.0f;
    protected String field_9351_C = null;
    protected float field_9349_D = 1.0f;
    protected int scoreValue = 0;
    protected float field_9345_F = 0.0f;
    public boolean field_9343_G = false;
    public float prevSwingProgress;
    public float swingProgress;
    public int health = 10;
    public int prevHealth;
    private int livingSoundTime;
    public int hurtTime;
    public int maxHurtTime;
    public float attackedAtYaw = 0.0f;
    public int deathTime = 0;
    public int attackTime = 0;
    public float cameraPitch;
    public float field_9328_R;
    protected boolean unused_flag = false;
    public int field_9326_T = -1;
    public float field_9325_U = (float)(Math.random() * (double)0.9f + (double)0.1f);
    public float field_705_Q;
    public float field_704_R;
    public float field_703_S;
    protected int newPosRotationIncrements;
    protected double newPosX;
    protected double newPosY;
    protected double newPosZ;
    protected double newRotationYaw;
    protected double newRotationPitch;
    float field_9348_ae = 0.0f;
    protected int field_9346_af = 0;
    protected int field_9344_ag = 0;
    public float moveStrafing;
    public float moveForward;
    protected float randomYawVelocity;
    protected boolean isJumping = false;
    protected float defaultPitch = 0.0f;
    protected float moveSpeed = 0.7f;
    private Entity currentTarget;
    protected int numTicksToChaseTarget = 0;

    public EntityLiving(World var1) {
        super(var1);
        this.preventEntitySpawning = true;
        this.field_9363_r = (float)(Math.random() + 1.0) * 0.01f;
        this.setPosition(this.posX, this.posY, this.posZ);
        this.field_9365_p = (float)Math.random() * 12398.0f;
        this.rotationYaw = (float)(Math.random() * 3.1415927410125732 * 2.0);
        this.stepHeight = 0.5f;
    }

    @Override
    protected void entityInit() {
    }

    public boolean canEntityBeSeen(Entity var1) {
        return this.worldObj.rayTraceBlocks(Vec3D.createVector(this.posX, this.posY + (double)this.getEyeHeight(), this.posZ), Vec3D.createVector(var1.posX, var1.posY + (double)var1.getEyeHeight(), var1.posZ)) == null;
    }

    @Override
    public String getEntityTexture() {
        return this.texture;
    }

    @Override
    public boolean canBeCollidedWith() {
        return !this.isDead;
    }

    @Override
    public boolean canBePushed() {
        return !this.isDead;
    }

    @Override
    public float getEyeHeight() {
        return this.height * 0.85f;
    }

    public int getTalkInterval() {
        return 80;
    }

    public void playLivingSound() {
        String var1 = this.getLivingSound();
        if (var1 != null) {
            this.worldObj.playSoundAtEntity(this, var1, this.getSoundVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.0f);
        }
    }

    @Override
    public void onEntityUpdate() {
        int var1;
        this.prevSwingProgress = this.swingProgress;
        super.onEntityUpdate();
        if (this.rand.nextInt(1000) < this.livingSoundTime++) {
            this.livingSoundTime = -this.getTalkInterval();
            this.playLivingSound();
        }
        if (this.isEntityAlive() && this.isEntityInsideOpaqueBlock()) {
            this.attackEntityFrom(null, 1);
        }
        if (this.isImmuneToFire || this.worldObj.multiplayerWorld) {
            this.fire = 0;
        }
        if (this.isEntityAlive() && this.isInsideOfMaterial(Material.water) && !this.canBreatheUnderwater()) {
            --this.air;
            if (this.air == -20) {
                this.air = 0;
                var1 = 0;
                while (var1 < 8) {
                    float var2 = this.rand.nextFloat() - this.rand.nextFloat();
                    float var3 = this.rand.nextFloat() - this.rand.nextFloat();
                    float var4 = this.rand.nextFloat() - this.rand.nextFloat();
                    this.worldObj.spawnParticle("bubble", this.posX + (double)var2, this.posY + (double)var3, this.posZ + (double)var4, this.motionX, this.motionY, this.motionZ);
                    ++var1;
                }
                this.attackEntityFrom(null, 2);
            }
            this.fire = 0;
        } else {
            this.air = this.maxAir;
        }
        this.cameraPitch = this.field_9328_R;
        if (this.attackTime > 0) {
            --this.attackTime;
        }
        if (this.hurtTime > 0) {
            --this.hurtTime;
        }
        if (this.field_9306_bj > 0) {
            --this.field_9306_bj;
        }
        if (this.health <= 0) {
            ++this.deathTime;
            if (this.deathTime > 20) {
                this.unusedEntityMethod();
                this.setEntityDead();
                var1 = 0;
                while (var1 < 20) {
                    double var8 = this.rand.nextGaussian() * 0.02;
                    double var9 = this.rand.nextGaussian() * 0.02;
                    double var6 = this.rand.nextGaussian() * 0.02;
                    this.worldObj.spawnParticle("explode", this.posX + (double)(this.rand.nextFloat() * this.width * 2.0f) - (double)this.width, this.posY + (double)(this.rand.nextFloat() * this.height), this.posZ + (double)(this.rand.nextFloat() * this.width * 2.0f) - (double)this.width, var8, var9, var6);
                    ++var1;
                }
            }
        }
        this.field_9359_x = this.field_9360_w;
        this.prevRenderYawOffset = this.renderYawOffset;
        this.prevRotationYaw = this.rotationYaw;
        this.prevRotationPitch = this.rotationPitch;
    }

    public void spawnExplosionParticle() {
        int var1 = 0;
        while (var1 < 20) {
            double var2 = this.rand.nextGaussian() * 0.02;
            double var4 = this.rand.nextGaussian() * 0.02;
            double var6 = this.rand.nextGaussian() * 0.02;
            double var8 = 10.0;
            this.worldObj.spawnParticle("explode", this.posX + (double)(this.rand.nextFloat() * this.width * 2.0f) - (double)this.width - var2 * var8, this.posY + (double)(this.rand.nextFloat() * this.height) - var4 * var8, this.posZ + (double)(this.rand.nextFloat() * this.width * 2.0f) - (double)this.width - var6 * var8, var2, var4, var6);
            ++var1;
        }
    }

    @Override
    public void updateRidden() {
        super.updateRidden();
        this.field_9362_u = this.field_9361_v;
        this.field_9361_v = 0.0f;
    }

    @Override
    public void setPositionAndRotation2(double var1, double var3, double var5, float var7, float var8, int var9) {
        this.yOffset = 0.0f;
        this.newPosX = var1;
        this.newPosY = var3;
        this.newPosZ = var5;
        this.newRotationYaw = var7;
        this.newRotationPitch = var8;
        this.newPosRotationIncrements = var9;
    }

    @Override
    public void onUpdate() {
        boolean var11;
        super.onUpdate();
        this.onLivingUpdate();
        double var1 = this.posX - this.prevPosX;
        double var3 = this.posZ - this.prevPosZ;
        float var5 = MathHelper.sqrt_double(var1 * var1 + var3 * var3);
        float var6 = this.renderYawOffset;
        float var7 = 0.0f;
        this.field_9362_u = this.field_9361_v;
        float var8 = 0.0f;
        if (var5 > 0.05f) {
            var8 = 1.0f;
            var7 = var5 * 3.0f;
            var6 = (float)Math.atan2(var3, var1) * 180.0f / (float)Math.PI - 90.0f;
        }
        if (this.swingProgress > 0.0f) {
            var6 = this.rotationYaw;
        }
        if (!this.onGround) {
            var8 = 0.0f;
        }
        this.field_9361_v += (var8 - this.field_9361_v) * 0.3f;
        float var9 = var6 - this.renderYawOffset;
        while (var9 < -180.0f) {
            var9 += 360.0f;
        }
        while (var9 >= 180.0f) {
            var9 -= 360.0f;
        }
        this.renderYawOffset += var9 * 0.3f;
        float var10 = this.rotationYaw - this.renderYawOffset;
        while (var10 < -180.0f) {
            var10 += 360.0f;
        }
        while (var10 >= 180.0f) {
            var10 -= 360.0f;
        }
        boolean bl = var11 = var10 < -90.0f || var10 >= 90.0f;
        if (var10 < -75.0f) {
            var10 = -75.0f;
        }
        if (var10 >= 75.0f) {
            var10 = 75.0f;
        }
        this.renderYawOffset = this.rotationYaw - var10;
        if (var10 * var10 > 2500.0f) {
            this.renderYawOffset += var10 * 0.2f;
        }
        if (var11) {
            var7 *= -1.0f;
        }
        while (this.rotationYaw - this.prevRotationYaw < -180.0f) {
            this.prevRotationYaw -= 360.0f;
        }
        while (this.rotationYaw - this.prevRotationYaw >= 180.0f) {
            this.prevRotationYaw += 360.0f;
        }
        while (this.renderYawOffset - this.prevRenderYawOffset < -180.0f) {
            this.prevRenderYawOffset -= 360.0f;
        }
        while (this.renderYawOffset - this.prevRenderYawOffset >= 180.0f) {
            this.prevRenderYawOffset += 360.0f;
        }
        while (this.rotationPitch - this.prevRotationPitch < -180.0f) {
            this.prevRotationPitch -= 360.0f;
        }
        while (this.rotationPitch - this.prevRotationPitch >= 180.0f) {
            this.prevRotationPitch += 360.0f;
        }
        this.field_9360_w += var7;
    }

    @Override
    protected void setSize(float var1, float var2) {
        super.setSize(var1, var2);
    }

    public void heal(int var1) {
        if (this.health > 0) {
            this.health += var1;
            if (this.health > 20) {
                this.health = 20;
            }
            this.field_9306_bj = this.field_9366_o / 2;
        }
    }

    @Override
    public boolean attackEntityFrom(Entity var1, int var2) {
        if (this.worldObj.multiplayerWorld) {
            return false;
        }
        this.field_9344_ag = 0;
        if (this.health <= 0) {
            return false;
        }
        this.field_704_R = 1.5f;
        boolean var3 = true;
        if ((float)this.field_9306_bj > (float)this.field_9366_o / 2.0f) {
            if (var2 <= this.field_9346_af) {
                return false;
            }
            this.damageEntity(var2 - this.field_9346_af);
            this.field_9346_af = var2;
            var3 = false;
        } else {
            this.field_9346_af = var2;
            this.prevHealth = this.health;
            this.field_9306_bj = this.field_9366_o;
            this.damageEntity(var2);
            this.maxHurtTime = 10;
            this.hurtTime = 10;
        }
        this.attackedAtYaw = 0.0f;
        if (var3) {
            this.worldObj.func_9425_a(this, (byte)2);
            this.setBeenAttacked();
            if (var1 != null) {
                double var4 = var1.posX - this.posX;
                double var6 = var1.posZ - this.posZ;
                while (var4 * var4 + var6 * var6 < 1.0E-4) {
                    var4 = (Math.random() - Math.random()) * 0.01;
                    var6 = (Math.random() - Math.random()) * 0.01;
                }
                this.attackedAtYaw = (float)(Math.atan2(var6, var4) * 180.0 / 3.1415927410125732) - this.rotationYaw;
                this.knockBack(var1, var2, var4, var6);
            } else {
                this.attackedAtYaw = (int)(Math.random() * 2.0) * 180;
            }
        }
        if (this.health <= 0) {
            if (var3) {
                this.worldObj.playSoundAtEntity(this, this.getDeathSound(), this.getSoundVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.0f);
            }
            this.onDeath(var1);
        } else if (var3) {
            this.worldObj.playSoundAtEntity(this, this.getHurtSound(), this.getSoundVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.0f);
        }
        return true;
    }

    @Override
    public void performHurtAnimation() {
        this.maxHurtTime = 10;
        this.hurtTime = 10;
        this.attackedAtYaw = 0.0f;
    }

    protected void damageEntity(int var1) {
        this.health -= var1;
    }

    protected float getSoundVolume() {
        return 1.0f;
    }

    protected String getLivingSound() {
        return null;
    }

    protected String getHurtSound() {
        return "random.hurt";
    }

    protected String getDeathSound() {
        return "random.hurt";
    }

    public void knockBack(Entity var1, int var2, double var3, double var5) {
        float var7 = MathHelper.sqrt_double(var3 * var3 + var5 * var5);
        float var8 = 0.4f;
        this.motionX /= 2.0;
        this.motionY /= 2.0;
        this.motionZ /= 2.0;
        this.motionX -= var3 / (double)var7 * (double)var8;
        this.motionY += (double)0.4f;
        this.motionZ -= var5 / (double)var7 * (double)var8;
        if (this.motionY > (double)0.4f) {
            this.motionY = 0.4f;
        }
    }

    public void onDeath(Entity var1) {
        if (this.scoreValue >= 0 && var1 != null) {
            var1.addToPlayerScore(this, this.scoreValue);
        }
        this.unused_flag = true;
        if (!this.worldObj.multiplayerWorld) {
            this.dropFewItems();
        }
        this.worldObj.func_9425_a(this, (byte)3);
    }

    protected void dropFewItems() {
        int var1 = this.getDropItemId();
        if (var1 > 0) {
            int var2 = this.rand.nextInt(3);
            int var3 = 0;
            while (var3 < var2) {
                this.dropItem(var1, 1);
                ++var3;
            }
        }
    }

    protected int getDropItemId() {
        return 0;
    }

    @Override
    protected void fall(float var1) {
        int var2 = (int)Math.ceil(var1 - 3.0f);
        if (var2 > 0) {
            this.attackEntityFrom(null, var2);
            int var3 = this.worldObj.getBlockId(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY - (double)0.2f - (double)this.yOffset), MathHelper.floor_double(this.posZ));
            if (var3 > 0) {
                StepSound var4 = Block.blocksList[var3].stepSound;
                this.worldObj.playSoundAtEntity(this, var4.func_1145_d(), var4.func_1147_b() * 0.5f, var4.func_1144_c() * 0.75f);
            }
        }
    }

    public void moveEntityWithHeading(float strafe, float fwd) {
        double var3;
        float prevYaw = this.rotationYaw;
        if (AutoTunnelHack.instance.status && AutoTunnelHack.instance.autoWalk.value) {
            this.rotationYaw = AutoTunnelHack.instance.getDirection().yaw;
        }
        if (this.handleWaterMovement()) {
            var3 = this.posY;
            this.moveFlying(strafe, fwd, 0.02f);
            this.moveEntity(this.motionX, this.motionY, this.motionZ);
            this.motionX *= (double)0.8f;
            this.motionY *= (double)0.8f;
            this.motionZ *= (double)0.8f;
            this.motionY -= 0.02;
            if (this.isCollidedHorizontally && this.isOffsetPositionInLiquid(this.motionX, this.motionY + (double)0.6f - this.posY + var3, this.motionZ)) {
                this.motionY = 0.3f;
            }
        } else if (this.handleLavaMovement()) {
            var3 = this.posY;
            this.moveFlying(strafe, fwd, 0.02f);
            this.moveEntity(this.motionX, this.motionY, this.motionZ);
            this.motionX *= 0.5;
            this.motionY *= 0.5;
            this.motionZ *= 0.5;
            this.motionY -= 0.02;
            if (this.isCollidedHorizontally && this.isOffsetPositionInLiquid(this.motionX, this.motionY + (double)0.6f - this.posY + var3, this.motionZ)) {
                this.motionY = 0.3f;
            }
        } else {
            float var8 = 0.91f;
            if (this.onGround) {
                var8 = 0.54600006f;
                int var4 = this.worldObj.getBlockId(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.boundingBox.minY) - 1, MathHelper.floor_double(this.posZ));
                if (var4 > 0) {
                    var8 = AntiSlowdownHack.instance.status ? 0.54600006f : Block.blocksList[var4].slipperiness * 0.91f;
                }
            }
            float var9 = 0.16277136f / (var8 * var8 * var8);
            this.moveFlying(strafe, fwd, this.onGround ? 0.1f * var9 : 0.02f);
            var8 = 0.91f;
            if (this.onGround) {
                var8 = 0.54600006f;
                int var5 = this.worldObj.getBlockId(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.boundingBox.minY) - 1, MathHelper.floor_double(this.posZ));
                if (var5 > 0) {
                    var8 = AntiSlowdownHack.instance.status ? 0.54600006f : Block.blocksList[var5].slipperiness * 0.91f;
                }
            }
            if (this.isOnLadder()) {
                this.fallDistance = 0.0f;
                if (FastLadderHack.instance.status) {
                    if (this.motionY < -0.15) {
                        this.motionY = -FastLadderHack.instance.downwardSpeed.value;
                    }
                } else if (this.motionY < -0.15) {
                    this.motionY = -0.15;
                }
                if (this.isSneaking() && this.motionY < 0.0) {
                    this.motionY = 0.0;
                }
            }
            this.moveEntity(this.motionX, this.motionY, this.motionZ);
            if (this.isCollidedHorizontally && this.isOnLadder()) {
                this.motionY = 0.2;
                if (FastLadderHack.instance.status) {
                    this.motionY = FastLadderHack.instance.upwardSpeed.value;
                }
            }
            this.motionY -= 0.08;
            this.motionY *= (double)0.98f;
            if (!StrafeHack.instance.status || this.entityId != Client.mc.thePlayer.entityId) {
                this.motionX *= (double)var8;
                this.motionZ *= (double)var8;
            }
        }
        this.field_705_Q = this.field_704_R;
        var3 = this.posX - this.prevPosX;
        double var10 = this.posZ - this.prevPosZ;
        float var7 = MathHelper.sqrt_double(var3 * var3 + var10 * var10) * 4.0f;
        if (var7 > 1.0f) {
            var7 = 1.0f;
        }
        this.field_704_R += (var7 - this.field_704_R) * 0.4f;
        this.field_703_S += this.field_704_R;
        this.rotationYaw = prevYaw;
    }

    public boolean isOnLadder() {
        int var3;
        int var2;
        int var1 = MathHelper.floor_double(this.posX);
        return this.worldObj.getBlockId(var1, var2 = MathHelper.floor_double(this.boundingBox.minY), var3 = MathHelper.floor_double(this.posZ)) == Block.ladder.blockID || this.worldObj.getBlockId(var1, var2 + 1, var3) == Block.ladder.blockID;
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound var1) {
        var1.setShort("Health", (short)this.health);
        var1.setShort("HurtTime", (short)this.hurtTime);
        var1.setShort("DeathTime", (short)this.deathTime);
        var1.setShort("AttackTime", (short)this.attackTime);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound var1) {
        this.health = var1.getShort("Health");
        if (!var1.hasKey("Health")) {
            this.health = 10;
        }
        this.hurtTime = var1.getShort("HurtTime");
        this.deathTime = var1.getShort("DeathTime");
        this.attackTime = var1.getShort("AttackTime");
    }

    @Override
    public boolean isEntityAlive() {
        return !this.isDead && this.health > 0;
    }

    public boolean canBreatheUnderwater() {
        return false;
    }

    public void onLivingUpdate() {
        if (this.newPosRotationIncrements > 0) {
            double var1 = this.posX + (this.newPosX - this.posX) / (double)this.newPosRotationIncrements;
            double var3 = this.posY + (this.newPosY - this.posY) / (double)this.newPosRotationIncrements;
            double var5 = this.posZ + (this.newPosZ - this.posZ) / (double)this.newPosRotationIncrements;
            double var7 = this.newRotationYaw - (double)this.rotationYaw;
            while (var7 < -180.0) {
                var7 += 360.0;
            }
            while (var7 >= 180.0) {
                var7 -= 360.0;
            }
            this.rotationYaw = (float)((double)this.rotationYaw + var7 / (double)this.newPosRotationIncrements);
            this.rotationPitch = (float)((double)this.rotationPitch + (this.newRotationPitch - (double)this.rotationPitch) / (double)this.newPosRotationIncrements);
            --this.newPosRotationIncrements;
            this.setPosition(var1, var3, var5);
            this.setRotation(this.rotationYaw, this.rotationPitch);
        }
        if (this.isMovementBlocked()) {
            this.isJumping = false;
            this.moveStrafing = 0.0f;
            this.moveForward = 0.0f;
            this.randomYawVelocity = 0.0f;
        } else if (!this.field_9343_G) {
            this.updatePlayerActionState();
        }
        boolean var9 = this.handleWaterMovement();
        boolean var2 = this.handleLavaMovement();
        if (this.isJumping) {
            if (var9) {
                this.motionY += (double)0.04f;
            } else if (var2) {
                this.motionY += (double)0.04f;
            } else if (this.onGround) {
                this.jump();
            }
        }
        this.moveStrafing *= 0.98f;
        this.moveForward *= 0.98f;
        this.randomYawVelocity *= 0.9f;
        this.moveEntityWithHeading(this.moveStrafing, this.moveForward);
        List var10 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(0.2f, 0.0, 0.2f));
        if (var10 != null && var10.size() > 0) {
            int var4 = 0;
            while (var4 < var10.size()) {
                Entity var11 = (Entity)var10.get(var4);
                if (var11.canBePushed()) {
                    var11.applyEntityCollision(this);
                }
                ++var4;
            }
        }
    }

    protected boolean isMovementBlocked() {
        return this.health <= 0;
    }

    protected void jump() {
        this.motionY = 0.42f;
    }

    protected boolean canDespawn() {
        return true;
    }

    protected void updatePlayerActionState() {
        ++this.field_9344_ag;
        EntityPlayer var1 = this.worldObj.getClosestPlayerToEntity(this, -1.0);
        if (this.canDespawn() && var1 != null) {
            double var2 = var1.posX - this.posX;
            double var4 = var1.posY - this.posY;
            double var6 = var1.posZ - this.posZ;
            double var8 = var2 * var2 + var4 * var4 + var6 * var6;
            if (var8 > 16384.0) {
                this.setEntityDead();
            }
            if (this.field_9344_ag > 600 && this.rand.nextInt(800) == 0) {
                if (var8 < 1024.0) {
                    this.field_9344_ag = 0;
                } else {
                    this.setEntityDead();
                }
            }
        }
        this.moveStrafing = 0.0f;
        this.moveForward = 0.0f;
        float var10 = 8.0f;
        if (this.rand.nextFloat() < 0.02f) {
            var1 = this.worldObj.getClosestPlayerToEntity(this, var10);
            if (var1 != null) {
                this.currentTarget = var1;
                this.numTicksToChaseTarget = 10 + this.rand.nextInt(20);
            } else {
                this.randomYawVelocity = (this.rand.nextFloat() - 0.5f) * 20.0f;
            }
        }
        if (this.currentTarget != null) {
            this.faceEntity(this.currentTarget, 10.0f, this.func_25026_x());
            if (this.numTicksToChaseTarget-- <= 0 || this.currentTarget.isDead || this.currentTarget.getDistanceSqToEntity(this) > (double)(var10 * var10)) {
                this.currentTarget = null;
            }
        } else {
            if (this.rand.nextFloat() < 0.05f) {
                this.randomYawVelocity = (this.rand.nextFloat() - 0.5f) * 20.0f;
            }
            this.rotationYaw += this.randomYawVelocity;
            this.rotationPitch = this.defaultPitch;
        }
        boolean var3 = this.handleWaterMovement();
        boolean var11 = this.handleLavaMovement();
        if (var3 || var11) {
            this.isJumping = this.rand.nextFloat() < 0.8f;
        }
    }

    protected int func_25026_x() {
        return 10;
    }

    public void faceEntity(Entity var1, float var2, float var3) {
        double var6;
        double var4 = var1.posX - this.posX;
        double var8 = var1.posZ - this.posZ;
        if (var1 instanceof EntityLiving) {
            EntityLiving var10 = (EntityLiving)var1;
            var6 = this.posY + (double)this.getEyeHeight() - (var10.posY + (double)var10.getEyeHeight());
        } else {
            var6 = (var1.boundingBox.minY + var1.boundingBox.maxY) / 2.0 - (this.posY + (double)this.getEyeHeight());
        }
        double var14 = MathHelper.sqrt_double(var4 * var4 + var8 * var8);
        float var12 = (float)(Math.atan2(var8, var4) * 180.0 / 3.1415927410125732) - 90.0f;
        float var13 = (float)(Math.atan2(var6, var14) * 180.0 / 3.1415927410125732);
        this.rotationPitch = -this.updateRotation(this.rotationPitch, var13, var3);
        this.rotationYaw = this.updateRotation(this.rotationYaw, var12, var2);
    }

    public boolean func_25025_V() {
        return this.currentTarget != null;
    }

    public Entity getCurrentTarget() {
        return this.currentTarget;
    }

    private float updateRotation(float var1, float var2, float var3) {
        float var4 = var2 - var1;
        while (var4 < -180.0f) {
            var4 += 360.0f;
        }
        while (var4 >= 180.0f) {
            var4 -= 360.0f;
        }
        if (var4 > var3) {
            var4 = var3;
        }
        if (var4 < -var3) {
            var4 = -var3;
        }
        return var1 + var4;
    }

    public void unusedEntityMethod() {
    }

    public boolean getCanSpawnHere() {
        return this.worldObj.checkIfAABBIsClear(this.boundingBox) && this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox).size() == 0 && !this.worldObj.getIsAnyLiquid(this.boundingBox);
    }

    @Override
    protected void kill() {
        this.attackEntityFrom(null, 4);
    }

    public float getSwingProgress(float var1) {
        float var2 = this.swingProgress - this.prevSwingProgress;
        if (var2 < 0.0f) {
            var2 += 1.0f;
        }
        return this.prevSwingProgress + var2 * var1;
    }

    public Vec3D getPosition(float var1) {
        if (var1 == 1.0f) {
            return Vec3D.createVector(this.posX, this.posY, this.posZ);
        }
        double var2 = this.prevPosX + (this.posX - this.prevPosX) * (double)var1;
        double var4 = this.prevPosY + (this.posY - this.prevPosY) * (double)var1;
        double var6 = this.prevPosZ + (this.posZ - this.prevPosZ) * (double)var1;
        return Vec3D.createVector(var2, var4, var6);
    }

    @Override
    public Vec3D getLookVec() {
        return this.getLook(1.0f);
    }

    public Vec3D getLook(float var1) {
        if (var1 == 1.0f) {
            float var2 = MathHelper.cos(-this.rotationYaw * ((float)Math.PI / 180) - (float)Math.PI);
            float var3 = MathHelper.sin(-this.rotationYaw * ((float)Math.PI / 180) - (float)Math.PI);
            float var4 = -MathHelper.cos(-this.rotationPitch * ((float)Math.PI / 180));
            float var5 = MathHelper.sin(-this.rotationPitch * ((float)Math.PI / 180));
            return Vec3D.createVector(var3 * var4, var5, var2 * var4);
        }
        float var2 = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * var1;
        float var3 = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * var1;
        float var4 = MathHelper.cos(-var3 * ((float)Math.PI / 180) - (float)Math.PI);
        float var5 = MathHelper.sin(-var3 * ((float)Math.PI / 180) - (float)Math.PI);
        float var6 = -MathHelper.cos(-var2 * ((float)Math.PI / 180));
        float var7 = MathHelper.sin(-var2 * ((float)Math.PI / 180));
        return Vec3D.createVector(var5 * var6, var7, var4 * var6);
    }

    public MovingObjectPosition rayTrace(double var1, float var3) {
        Vec3D var4 = this.getPosition(var3);
        Vec3D var5 = this.getLook(var3);
        Vec3D var6 = var4.addVector(var5.xCoord * var1, var5.yCoord * var1, var5.zCoord * var1);
        return this.worldObj.rayTraceBlocks(var4, var6);
    }

    public int getMaxSpawnedInChunk() {
        return 4;
    }

    public ItemStack getHeldItem() {
        return null;
    }

    @Override
    public void handleHealthUpdate(byte var1) {
        if (var1 == 2) {
            this.field_704_R = 1.5f;
            this.field_9306_bj = this.field_9366_o;
            this.maxHurtTime = 10;
            this.hurtTime = 10;
            this.attackedAtYaw = 0.0f;
            this.worldObj.playSoundAtEntity(this, this.getHurtSound(), this.getSoundVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.0f);
            this.attackEntityFrom(null, 0);
        } else if (var1 == 3) {
            this.worldObj.playSoundAtEntity(this, this.getDeathSound(), this.getSoundVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.0f);
            this.health = 0;
            this.onDeath(null);
        } else {
            super.handleHealthUpdate(var1);
        }
    }

    public boolean isPlayerSleeping() {
        return false;
    }
}

