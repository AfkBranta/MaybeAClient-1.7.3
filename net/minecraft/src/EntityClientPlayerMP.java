/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.client.Minecraft;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityItem;
import net.minecraft.src.EntityPlayerSP;
import net.minecraft.src.NetClientHandler;
import net.minecraft.src.Packet101;
import net.minecraft.src.Packet10Flying;
import net.minecraft.src.Packet11PlayerPosition;
import net.minecraft.src.Packet12PlayerLook;
import net.minecraft.src.Packet13PlayerLookMove;
import net.minecraft.src.Packet14BlockDig;
import net.minecraft.src.Packet18ArmAnimation;
import net.minecraft.src.Packet19;
import net.minecraft.src.Packet3Chat;
import net.minecraft.src.Packet9;
import net.minecraft.src.Session;
import net.minecraft.src.World;
import net.skidcode.gh.maybeaclient.events.EventRegistry;
import net.skidcode.gh.maybeaclient.events.impl.EventMPMovementUpdate;

public class EntityClientPlayerMP
extends EntityPlayerSP {
    public NetClientHandler sendQueue;
    private int field_9380_bx = 0;
    private boolean field_21093_bH = false;
    private double field_9379_by;
    private double field_9378_bz;
    private double field_9377_bA;
    private double field_9376_bB;
    private float field_9385_bC;
    private float field_9384_bD;
    private boolean field_9382_bF = false;
    private boolean field_9381_bG = false;
    private int field_12242_bI = 0;

    public EntityClientPlayerMP(Minecraft var1, World var2, Session var3, NetClientHandler var4) {
        super(var1, var2, var3, 0);
        this.sendQueue = var4;
    }

    @Override
    public boolean attackEntityFrom(Entity var1, int var2) {
        return false;
    }

    @Override
    public void heal(int var1) {
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        this.func_4056_N();
    }

    public void func_4056_N() {
        boolean var15;
        boolean var1;
        EventMPMovementUpdate ev = new EventMPMovementUpdate();
        EventRegistry.handleEvent(ev);
        if (this.field_9380_bx++ == 20) {
            this.sendInventoryChanged();
            this.field_9380_bx = 0;
        }
        if ((var1 = this.isSneaking()) != this.field_9381_bG) {
            if (var1) {
                this.sendQueue.addToSendQueue(new Packet19(this, 1));
            } else {
                this.sendQueue.addToSendQueue(new Packet19(this, 2));
            }
            this.field_9381_bG = var1;
        }
        double var2 = this.posX - this.field_9379_by;
        double var4 = this.boundingBox.minY - this.field_9378_bz;
        double var6 = this.posY - this.field_9377_bA;
        double var8 = this.posZ - this.field_9376_bB;
        double var10 = this.rotationYaw - this.field_9385_bC;
        double var12 = this.rotationPitch - this.field_9384_bD;
        boolean var14 = var4 != 0.0 || var6 != 0.0 || var2 != 0.0 || var8 != 0.0;
        boolean bl = var15 = var10 != 0.0 || var12 != 0.0;
        if (this.ridingEntity != null) {
            if (var15) {
                this.sendQueue.addToSendQueue(new Packet11PlayerPosition(this.motionX, -999.0, -999.0, this.motionZ, this.onGround));
            } else {
                this.sendQueue.addToSendQueue(new Packet13PlayerLookMove(this.motionX, -999.0, -999.0, this.motionZ, this.rotationYaw, this.rotationPitch, this.onGround));
            }
            var14 = false;
        } else if (var14 && var15) {
            this.sendQueue.addToSendQueue(new Packet13PlayerLookMove(this.posX, this.boundingBox.minY, this.posY, this.posZ, this.rotationYaw, this.rotationPitch, this.onGround));
            this.field_12242_bI = 0;
        } else if (var14) {
            this.sendQueue.addToSendQueue(new Packet11PlayerPosition(this.posX, this.boundingBox.minY, this.posY, this.posZ, this.onGround));
            this.field_12242_bI = 0;
        } else if (var15) {
            this.sendQueue.addToSendQueue(new Packet12PlayerLook(this.rotationYaw, this.rotationPitch, this.onGround));
            this.field_12242_bI = 0;
        } else {
            this.sendQueue.addToSendQueue(new Packet10Flying(this.onGround));
            this.field_12242_bI = this.field_9382_bF == this.onGround && this.field_12242_bI <= 200 ? ++this.field_12242_bI : 0;
        }
        this.field_9382_bF = this.onGround;
        if (var14) {
            this.field_9379_by = this.posX;
            this.field_9378_bz = this.boundingBox.minY;
            this.field_9377_bA = this.posY;
            this.field_9376_bB = this.posZ;
        }
        if (var15) {
            this.field_9385_bC = this.rotationYaw;
            this.field_9384_bD = this.rotationPitch;
        }
    }

    @Override
    public void dropCurrentItem() {
        this.sendQueue.addToSendQueue(new Packet14BlockDig(4, 0, 0, 0, 0));
    }

    private void sendInventoryChanged() {
    }

    @Override
    protected void joinEntityItemWithWorld(EntityItem var1) {
    }

    @Override
    public void sendChatMessage(String var1) {
        this.sendQueue.addToSendQueue(new Packet3Chat(var1));
    }

    @Override
    public void swingItem() {
        super.swingItem();
        this.sendQueue.addToSendQueue(new Packet18ArmAnimation(this, 1));
    }

    @Override
    public void respawnPlayer() {
        this.sendInventoryChanged();
        this.sendQueue.addToSendQueue(new Packet9());
    }

    @Override
    protected void damageEntity(int var1) {
        this.health -= var1;
    }

    @Override
    public void func_20059_m() {
        this.sendQueue.addToSendQueue(new Packet101(this.craftingInventory.windowId));
        this.inventory.setItemStack(null);
        super.func_20059_m();
    }

    @Override
    public void setHealth(int var1) {
        if (this.field_21093_bH) {
            super.setHealth(var1);
        } else {
            this.health = var1;
            this.field_21093_bH = true;
        }
    }
}

