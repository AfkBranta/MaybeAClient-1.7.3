package net.minecraft.src;

import lunatrius.schematica.Settings;
import net.minecraft.client.Minecraft;
import net.minecraft.src.Block;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityClientPlayerMP;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NetClientHandler;
import net.minecraft.src.Packet102;
import net.minecraft.src.Packet14BlockDig;
import net.minecraft.src.Packet15Place;
import net.minecraft.src.Packet16BlockItemSwitch;
import net.minecraft.src.Packet7;
import net.minecraft.src.PlayerController;
import net.minecraft.src.World;
import net.skidcode.gh.maybeaclient.hacks.AutoToolHack;
import net.skidcode.gh.maybeaclient.hacks.InstantHack;
import net.skidcode.gh.maybeaclient.hacks.ReachHack;
import net.skidcode.gh.maybeaclient.hacks.SpeedMineHack;

public class PlayerControllerMP
extends PlayerController {
    public int currentBlockX = -1;
    public int currentBlockY = -1;
    public int currentBlocKZ = -1;
    public float field_9442_f = 0.0f;
    public float field_1080_g = 0.0f;
    public float field_9441_h = 0.0f;
    public int field_9440_i = 0;
    public boolean isUsing = false;
    public NetClientHandler netClientHandler;
    public int field_1075_l = 0;

    public PlayerControllerMP(Minecraft var1, NetClientHandler var2) {
        super(var1);
        this.netClientHandler = var2;
    }

    @Override
    public void flipPlayer(EntityPlayer var1) {
        var1.rotationYaw = -180.0f;
    }

    @Override
    public boolean sendBlockRemoved(int var1, int var2, int var3, int var4) {
        int var5 = this.mc.theWorld.getBlockId(var1, var2, var3);
        boolean var6 = super.sendBlockRemoved(var1, var2, var3, var4);
        ItemStack var7 = this.mc.thePlayer.getCurrentEquippedItem();
        if (var7 != null) {
            var7.func_25191_a(var5, var1, var2, var3, this.mc.thePlayer);
            if (var7.stackSize == 0) {
                var7.func_1097_a(this.mc.thePlayer);
                this.mc.thePlayer.destroyCurrentEquippedItem();
            }
        }
        return var6;
    }

    @Override
    public void clickBlock(int var1, int var2, int var3, int var4) {
        if (!this.isUsing || var1 != this.currentBlockX || var2 != this.currentBlockY || var3 != this.currentBlocKZ) {
            float progress;
            this.netClientHandler.addToSendQueue(new Packet14BlockDig(0, var1, var2, var3, var4));
            int var5 = this.mc.theWorld.getBlockId(var1, var2, var3);
            if (var5 > 0 && this.field_9442_f == 0.0f) {
                Block.blocksList[var5].onBlockClicked(this.mc.theWorld, var1, var2, var3, this.mc.thePlayer);
            }
            float f = progress = SpeedMineHack.instance.status ? SpeedMineHack.instance.sendDestroyAfter.value : 1.0f;
            if (var5 > 0 && Block.blocksList[var5].blockStrength(this.mc.thePlayer) >= progress) {
                this.sendBlockRemoved(var1, var2, var3, var4);
            } else {
                this.isUsing = true;
                this.currentBlockX = var1;
                this.currentBlockY = var2;
                this.currentBlocKZ = var3;
                this.field_9442_f = 0.0f;
                this.field_1080_g = 0.0f;
                this.field_9441_h = 0.0f;
            }
        }
    }

    @Override
    public boolean isBeingUsed() {
        return this.isUsing;
    }

    @Override
    public void func_6468_a() {
        this.field_9442_f = 0.0f;
        this.isUsing = false;
    }

    @Override
    public void sendBlockRemoving(int var1, int var2, int var3, int var4) {
        if (this.isUsing) {
            if (InstantHack.instance.status) {
                this.netClientHandler.addToSendQueue(new Packet14BlockDig(0, var1, var2, var3, var4));
                this.netClientHandler.addToSendQueue(new Packet14BlockDig(2, var1, var2, var3, var4));
                this.sendBlockRemoved(var1, var2, var3, var4);
            }
            this.func_730_e();
            if (this.field_9440_i > 0) {
                --this.field_9440_i;
            } else if (var1 == this.currentBlockX && var2 == this.currentBlockY && var3 == this.currentBlocKZ) {
                float progress;
                int var5 = this.mc.theWorld.getBlockId(var1, var2, var3);
                if (var5 == 0) {
                    this.isUsing = false;
                    return;
                }
                Block block = Block.blocksList[var5];
                if (AutoToolHack.instance.status) {
                    this.mc.thePlayer.inventory.currentItem = AutoToolHack.getBestSlot(block);
                    this.func_730_e();
                }
                this.field_9442_f += block.blockStrength(this.mc.thePlayer);
                if (this.field_9441_h % 4.0f == 0.0f && block != null) {
                    this.mc.sndManager.playSound(block.stepSound.func_1145_d(), (float)var1 + 0.5f, (float)var2 + 0.5f, (float)var3 + 0.5f, (block.stepSound.func_1147_b() + 1.0f) / 8.0f, block.stepSound.func_1144_c() * 0.5f);
                }
                this.field_9441_h += 1.0f;
                float f = progress = SpeedMineHack.instance.status ? SpeedMineHack.instance.sendDestroyAfter.value : 1.0f;
                if (this.field_9442_f >= progress) {
                    this.isUsing = false;
                    this.netClientHandler.addToSendQueue(new Packet14BlockDig(2, var1, var2, var3, var4));
                    this.sendBlockRemoved(var1, var2, var3, var4);
                    this.field_9442_f = 0.0f;
                    this.field_1080_g = 0.0f;
                    this.field_9441_h = 0.0f;
                    this.field_9440_i = 0;
                }
            } else {
                this.clickBlock(var1, var2, var3, var4);
            }
        }
    }

    @Override
    public void setPartialTime(float var1) {
        if (this.field_9442_f <= 0.0f) {
            this.mc.ingameGUI.field_6446_b = 0.0f;
            this.mc.renderGlobal.field_1450_i = 0.0f;
        } else {
            float div = SpeedMineHack.instance.status ? SpeedMineHack.instance.sendDestroyAfter.value : 1.0f;
            float var2 = this.field_1080_g + (this.field_9442_f - this.field_1080_g) * var1;
            this.mc.ingameGUI.field_6446_b = var2 / div;
            this.mc.renderGlobal.field_1450_i = var2 / div;
        }
    }

    @Override
    public float getBlockReachDistance() {
        if (ReachHack.instance.status) {
            return ReachHack.instance.radius.getValue();
        }
        return 4.0f;
    }

    @Override
    public void func_717_a(World var1) {
        super.func_717_a(var1);
    }

    @Override
    public void updateController() {
        this.func_730_e();
        this.field_1080_g = this.field_9442_f;
        this.mc.sndManager.playRandomMusicIfReady();
    }

    public void func_730_e() {
        int var1 = this.mc.thePlayer.inventory.currentItem;
        if (var1 != this.field_1075_l) {
            this.field_1075_l = var1;
            this.netClientHandler.addToSendQueue(new Packet16BlockItemSwitch(this.field_1075_l));
        }
    }

    @Override
    public boolean sendPlaceBlock(EntityPlayer var1, World var2, ItemStack var3, int var4, int var5, int var6, int var7) {
        this.func_730_e();
        this.netClientHandler.addToSendQueue(new Packet15Place(var4, var5, var6, var7, var1.inventory.getCurrentItem()));
        boolean var8 = super.sendPlaceBlock(var1, var2, var3, var4, var5, var6, var7);
        Settings.instance().needsUpdate = true;
        return var8;
    }

    @Override
    public boolean sendUseItem(EntityPlayer var1, World var2, ItemStack var3) {
        this.func_730_e();
        this.netClientHandler.addToSendQueue(new Packet15Place(-1, -1, -1, 255, var1.inventory.getCurrentItem()));
        boolean var4 = super.sendUseItem(var1, var2, var3);
        return var4;
    }

    @Override
    public EntityPlayer createPlayer(World var1) {
        return new EntityClientPlayerMP(this.mc, var1, this.mc.session, this.netClientHandler);
    }

    @Override
    public void func_6472_b(EntityPlayer var1, Entity var2) {
        this.func_730_e();
        this.netClientHandler.addToSendQueue(new Packet7(var1.entityId, var2.entityId, 1));
        var1.attackTargetEntityWithCurrentItem(var2);
    }

    @Override
    public void func_6475_a(EntityPlayer var1, Entity var2) {
        this.func_730_e();
        this.netClientHandler.addToSendQueue(new Packet7(var1.entityId, var2.entityId, 0));
        var1.useCurrentItemOnEntity(var2);
    }

    @Override
    public ItemStack func_20085_a(int var1, int var2, int var3, EntityPlayer var4) {
        short var5 = var4.craftingInventory.func_20111_a(var4.inventory);
        ItemStack var6 = super.func_20085_a(var1, var2, var3, var4);
        this.netClientHandler.addToSendQueue(new Packet102(var1, var2, var3, var6, var5));
        return var6;
    }

    @Override
    public void func_20086_a(int var1, EntityPlayer var2) {
        if (var1 != -9999) {
            // empty if block
        }
    }
}

