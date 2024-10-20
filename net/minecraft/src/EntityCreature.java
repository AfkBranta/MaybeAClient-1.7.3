package net.minecraft.src;

import net.minecraft.src.Entity;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.MathHelper;
import net.minecraft.src.PathEntity;
import net.minecraft.src.Vec3D;
import net.minecraft.src.World;

public class EntityCreature
extends EntityLiving {
    private PathEntity pathToEntity;
    protected Entity playerToAttack;
    protected boolean hasAttacked = false;

    public EntityCreature(World var1) {
        super(var1);
    }

    protected boolean func_25028_d_() {
        return false;
    }

    @Override
    protected void updatePlayerActionState() {
        this.hasAttacked = this.func_25028_d_();
        float var1 = 16.0f;
        if (this.playerToAttack == null) {
            this.playerToAttack = this.findPlayerToAttack();
            if (this.playerToAttack != null) {
                this.pathToEntity = this.worldObj.getPathToEntity(this, this.playerToAttack, var1);
            }
        } else if (!this.playerToAttack.isEntityAlive()) {
            this.playerToAttack = null;
        } else {
            float var2 = this.playerToAttack.getDistanceToEntity(this);
            if (this.canEntityBeSeen(this.playerToAttack)) {
                this.attackEntity(this.playerToAttack, var2);
            }
        }
        if (!(this.hasAttacked || this.playerToAttack == null || this.pathToEntity != null && this.rand.nextInt(20) != 0)) {
            this.pathToEntity = this.worldObj.getPathToEntity(this, this.playerToAttack, var1);
        } else if (!this.hasAttacked && (this.pathToEntity == null && this.rand.nextInt(80) == 0 || this.rand.nextInt(80) == 0)) {
            boolean var21 = false;
            int var3 = -1;
            int var4 = -1;
            int var5 = -1;
            float var6 = -99999.0f;
            int var7 = 0;
            while (var7 < 10) {
                int var10;
                int var9;
                int var8 = MathHelper.floor_double(this.posX + (double)this.rand.nextInt(13) - 6.0);
                float var11 = this.getBlockPathWeight(var8, var9 = MathHelper.floor_double(this.posY + (double)this.rand.nextInt(7) - 3.0), var10 = MathHelper.floor_double(this.posZ + (double)this.rand.nextInt(13) - 6.0));
                if (var11 > var6) {
                    var6 = var11;
                    var3 = var8;
                    var4 = var9;
                    var5 = var10;
                    var21 = true;
                }
                ++var7;
            }
            if (var21) {
                this.pathToEntity = this.worldObj.getEntityPathToXYZ(this, var3, var4, var5, 10.0f);
            }
        }
        int var22 = MathHelper.floor_double(this.boundingBox.minY);
        boolean var23 = this.handleWaterMovement();
        boolean var24 = this.handleLavaMovement();
        this.rotationPitch = 0.0f;
        if (this.pathToEntity != null && this.rand.nextInt(100) != 0) {
            Vec3D var25 = this.pathToEntity.getPosition(this);
            double var26 = this.width * 2.0f;
            while (var25 != null && var25.squareDistanceTo(this.posX, var25.yCoord, this.posZ) < var26 * var26) {
                this.pathToEntity.incrementPathIndex();
                if (this.pathToEntity.isFinished()) {
                    var25 = null;
                    this.pathToEntity = null;
                    continue;
                }
                var25 = this.pathToEntity.getPosition(this);
            }
            this.isJumping = false;
            if (var25 != null) {
                double var27 = var25.xCoord - this.posX;
                double var28 = var25.zCoord - this.posZ;
                double var12 = var25.yCoord - (double)var22;
                float var14 = (float)(Math.atan2(var28, var27) * 180.0 / 3.1415927410125732) - 90.0f;
                float var15 = var14 - this.rotationYaw;
                this.moveForward = this.moveSpeed;
                while (var15 < -180.0f) {
                    var15 += 360.0f;
                }
                while (var15 >= 180.0f) {
                    var15 -= 360.0f;
                }
                if (var15 > 30.0f) {
                    var15 = 30.0f;
                }
                if (var15 < -30.0f) {
                    var15 = -30.0f;
                }
                this.rotationYaw += var15;
                if (this.hasAttacked && this.playerToAttack != null) {
                    double var16 = this.playerToAttack.posX - this.posX;
                    double var18 = this.playerToAttack.posZ - this.posZ;
                    float var20 = this.rotationYaw;
                    this.rotationYaw = (float)(Math.atan2(var18, var16) * 180.0 / 3.1415927410125732) - 90.0f;
                    var15 = (var20 - this.rotationYaw + 90.0f) * (float)Math.PI / 180.0f;
                    this.moveStrafing = -MathHelper.sin(var15) * this.moveForward * 1.0f;
                    this.moveForward = MathHelper.cos(var15) * this.moveForward * 1.0f;
                }
                if (var12 > 0.0) {
                    this.isJumping = true;
                }
            }
            if (this.playerToAttack != null) {
                this.faceEntity(this.playerToAttack, 30.0f, 30.0f);
            }
            if (this.isCollidedHorizontally) {
                this.isJumping = true;
            }
            if (this.rand.nextFloat() < 0.8f && (var23 || var24)) {
                this.isJumping = true;
            }
        } else {
            super.updatePlayerActionState();
            this.pathToEntity = null;
        }
    }

    protected void attackEntity(Entity var1, float var2) {
    }

    protected float getBlockPathWeight(int var1, int var2, int var3) {
        return 0.0f;
    }

    protected Entity findPlayerToAttack() {
        return null;
    }

    @Override
    public boolean getCanSpawnHere() {
        int var1 = MathHelper.floor_double(this.posX);
        int var2 = MathHelper.floor_double(this.boundingBox.minY);
        int var3 = MathHelper.floor_double(this.posZ);
        return super.getCanSpawnHere() && this.getBlockPathWeight(var1, var2, var3) >= 0.0f;
    }

    public boolean hasPath() {
        return this.pathToEntity != null;
    }

    public void setPathToEntity(PathEntity var1) {
        this.pathToEntity = var1;
    }

    public Entity getTarget() {
        return this.playerToAttack;
    }

    public void setTarget(Entity var1) {
        this.playerToAttack = var1;
    }
}

