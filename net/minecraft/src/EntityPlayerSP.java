/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.client.Minecraft;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityPickupFX;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.GuiChest;
import net.minecraft.src.GuiCrafting;
import net.minecraft.src.GuiDispenser;
import net.minecraft.src.GuiEditSign;
import net.minecraft.src.GuiFurnace;
import net.minecraft.src.IInventory;
import net.minecraft.src.MouseFilter;
import net.minecraft.src.MovementInput;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.Session;
import net.minecraft.src.StatBasic;
import net.minecraft.src.TileEntityDispenser;
import net.minecraft.src.TileEntityFurnace;
import net.minecraft.src.TileEntitySign;
import net.minecraft.src.World;
import net.skidcode.gh.maybeaclient.events.EventRegistry;
import net.skidcode.gh.maybeaclient.events.impl.EventPlayerUpdatePost;
import net.skidcode.gh.maybeaclient.events.impl.EventPlayerUpdatePre;
import net.skidcode.gh.maybeaclient.hacks.AutoTunnelHack;
import net.skidcode.gh.maybeaclient.hacks.AutoWalkHack;
import net.skidcode.gh.maybeaclient.hacks.CombatLogHack;
import net.skidcode.gh.maybeaclient.hacks.StrafeHack;

public class EntityPlayerSP
extends EntityPlayer {
    public MovementInput movementInput;
    protected Minecraft mc;
    public int field_9373_b = 20;
    private boolean inPortal = false;
    public float timeInPortal;
    public float prevTimeInPortal;
    private MouseFilter field_21903_bJ = new MouseFilter();
    private MouseFilter field_21904_bK = new MouseFilter();
    private MouseFilter field_21902_bL = new MouseFilter();

    public EntityPlayerSP(Minecraft var1, World var2, Session var3, int var4) {
        super(var2);
        this.mc = var1;
        this.dimension = var4;
        if (var3 != null && var3.username != null && var3.username.length() > 0) {
            this.skinUrl = "http://s3.amazonaws.com/MinecraftSkins/" + var3.username + ".png";
        }
        this.username = var3.username;
    }

    @Override
    public void moveEntity(double var1, double var3, double var5) {
        super.moveEntity(var1, var3, var5);
    }

    @Override
    public void updatePlayerActionState() {
        super.updatePlayerActionState();
        this.moveStrafing = this.movementInput.moveStrafe;
        this.moveForward = this.movementInput.moveForward;
        this.isJumping = this.movementInput.jump;
        if (AutoTunnelHack.instance.status && AutoTunnelHack.instance.autoWalk.value) {
            this.moveForward = 1.0f;
        }
        if (AutoWalkHack.instance.status) {
            this.moveForward = 1.0f;
        }
        if (StrafeHack.instance.status) {
            this.moveStrafing = 0.0f;
            this.moveForward = 0.0f;
        }
    }

    @Override
    public void onLivingUpdate() {
        this.prevTimeInPortal = this.timeInPortal;
        EventPlayerUpdatePre e = new EventPlayerUpdatePre();
        EventRegistry.handleEvent(e);
        if (this.inPortal) {
            if (this.timeInPortal == 0.0f) {
                this.mc.sndManager.func_337_a("portal.trigger", 1.0f, this.rand.nextFloat() * 0.4f + 0.8f);
            }
            this.timeInPortal += 0.0125f;
            if (this.timeInPortal >= 1.0f) {
                this.timeInPortal = 1.0f;
                this.field_9373_b = 10;
                this.mc.sndManager.func_337_a("portal.travel", 1.0f, this.rand.nextFloat() * 0.4f + 0.8f);
                this.mc.usePortal();
            }
            this.inPortal = false;
        } else {
            if (this.timeInPortal > 0.0f) {
                this.timeInPortal -= 0.05f;
            }
            if (this.timeInPortal < 0.0f) {
                this.timeInPortal = 0.0f;
            }
        }
        if (this.field_9373_b > 0) {
            --this.field_9373_b;
        }
        this.movementInput.updatePlayerMoveState(this);
        if (this.movementInput.sneak && this.ySize < 0.2f) {
            this.ySize = 0.2f;
        }
        EventPlayerUpdatePost ee = new EventPlayerUpdatePost();
        EventRegistry.handleEvent(ee);
        super.onLivingUpdate();
    }

    public void resetPlayerKeyState() {
        this.movementInput.resetKeyState();
    }

    public void handleKeyPress(int var1, boolean var2) {
        this.movementInput.checkKeyForMovementInput(var1, var2);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound var1) {
        super.writeEntityToNBT(var1);
        var1.setInteger("Score", this.score);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound var1) {
        super.readEntityFromNBT(var1);
        this.score = var1.getInteger("Score");
    }

    @Override
    public void func_20059_m() {
        super.func_20059_m();
        this.mc.displayGuiScreen(null);
    }

    @Override
    public void displayGUIEditSign(TileEntitySign var1) {
        this.mc.displayGuiScreen(new GuiEditSign(var1));
    }

    @Override
    public void displayGUIChest(IInventory var1) {
        this.mc.displayGuiScreen(new GuiChest(this.inventory, var1));
    }

    @Override
    public void displayWorkbenchGUI(int var1, int var2, int var3) {
        this.mc.displayGuiScreen(new GuiCrafting(this.inventory, this.worldObj, var1, var2, var3));
    }

    @Override
    public void displayGUIFurnace(TileEntityFurnace var1) {
        this.mc.displayGuiScreen(new GuiFurnace(this.inventory, var1));
    }

    @Override
    public void displayGUIDispenser(TileEntityDispenser var1) {
        this.mc.displayGuiScreen(new GuiDispenser(this.inventory, var1));
    }

    @Override
    public void onItemPickup(Entity var1, int var2) {
        this.mc.effectRenderer.addEffect(new EntityPickupFX(this.mc.theWorld, var1, this, -0.5f));
    }

    public int getPlayerArmorValue() {
        return this.inventory.getTotalArmorValue();
    }

    public void sendChatMessage(String var1) {
    }

    @Override
    public boolean isSneaking() {
        return this.movementInput.sneak;
    }

    @Override
    public void setInPortal() {
        if (this.field_9373_b > 0) {
            this.field_9373_b = 10;
        } else {
            this.inPortal = true;
        }
    }

    public void setHealth(int var1) {
        int var2 = this.health - var1;
        if (var2 <= 0) {
            this.health = var1;
        } else {
            this.field_9346_af = var2;
            this.prevHealth = this.health;
            this.field_9306_bj = this.field_9366_o;
            this.damageEntity(var2);
            this.maxHurtTime = 10;
            this.hurtTime = 10;
        }
    }

    @Override
    public boolean attackEntityFrom(Entity var1, int var2) {
        boolean b = super.attackEntityFrom(var1, var2);
        if (this == this.mc.thePlayer && CombatLogHack.instance.status && CombatLogHack.instance.mode.currentMode.equalsIgnoreCase("OnHit")) {
            if (this.mc.isMultiplayerWorld()) {
                this.mc.theWorld.sendQuittingDisconnectingPacket();
            } else {
                CombatLogHack.shouldQuit = true;
            }
        }
        return b;
    }

    @Override
    public void respawnPlayer() {
        this.mc.respawn(false);
    }

    @Override
    public void func_6420_o() {
    }

    public void addChatMessageWithMoreOpacityBG(String var1) {
        this.mc.ingameGUI.addChatMessageWithMoreOpacityBG(var1);
    }

    @Override
    public void addChatMessage(String var1) {
        this.mc.ingameGUI.func_22064_c(var1);
    }

    @Override
    public void addStat(StatBasic var1, int var2) {
    }
}

