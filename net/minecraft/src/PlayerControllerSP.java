/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import lunatrius.schematica.Settings;
import net.minecraft.client.Minecraft;
import net.minecraft.src.Block;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.PlayerController;
import net.minecraft.src.World;
import net.skidcode.gh.maybeaclient.hacks.AutoToolHack;
import net.skidcode.gh.maybeaclient.hacks.ReachHack;

public class PlayerControllerSP
extends PlayerController {
    private int currentBlockX = -1;
    private int currentBlockY = -1;
    private int currentBlockZ = -1;
    public float curBlockDamage = 0.0f;
    private float prevBlockDamage = 0.0f;
    private float field_1069_h = 0.0f;
    private int field_1068_i = 0;

    public PlayerControllerSP(Minecraft var1) {
        super(var1);
    }

    @Override
    public void flipPlayer(EntityPlayer var1) {
        var1.rotationYaw = -180.0f;
    }

    @Override
    public boolean sendBlockRemoved(int var1, int var2, int var3, int var4) {
        int var5 = this.mc.theWorld.getBlockId(var1, var2, var3);
        int var6 = this.mc.theWorld.getBlockMetadata(var1, var2, var3);
        boolean var7 = super.sendBlockRemoved(var1, var2, var3, var4);
        ItemStack var8 = this.mc.thePlayer.getCurrentEquippedItem();
        boolean var9 = this.mc.thePlayer.canHarvestBlock(Block.blocksList[var5]);
        if (var8 != null) {
            var8.func_25191_a(var5, var1, var2, var3, this.mc.thePlayer);
            if (var8.stackSize == 0) {
                var8.func_1097_a(this.mc.thePlayer);
                this.mc.thePlayer.destroyCurrentEquippedItem();
            }
        }
        if (var7 && var9) {
            Block.blocksList[var5].harvestBlock(this.mc.theWorld, this.mc.thePlayer, var1, var2, var3, var6);
        }
        Settings.instance().needsUpdate = true;
        return var7;
    }

    @Override
    public void clickBlock(int var1, int var2, int var3, int var4) {
        int var5 = this.mc.theWorld.getBlockId(var1, var2, var3);
        if (var5 > 0 && this.curBlockDamage == 0.0f) {
            Block.blocksList[var5].onBlockClicked(this.mc.theWorld, var1, var2, var3, this.mc.thePlayer);
        }
        if (var5 > 0 && Block.blocksList[var5].blockStrength(this.mc.thePlayer) >= 1.0f) {
            this.sendBlockRemoved(var1, var2, var3, var4);
        }
    }

    @Override
    public boolean isBeingUsed() {
        return this.currentBlockY != -1;
    }

    @Override
    public void func_6468_a() {
        this.curBlockDamage = 0.0f;
        this.field_1068_i = 0;
    }

    @Override
    public void sendBlockRemoving(int var1, int var2, int var3, int var4) {
        if (this.field_1068_i > 0) {
            --this.field_1068_i;
        } else if (var1 == this.currentBlockX && var2 == this.currentBlockY && var3 == this.currentBlockZ) {
            int blockID = this.mc.theWorld.getBlockId(var1, var2, var3);
            if (blockID == 0) {
                return;
            }
            Block block = Block.blocksList[blockID];
            if (AutoToolHack.instance.status) {
                this.mc.thePlayer.inventory.currentItem = AutoToolHack.getBestSlot(block);
            }
            this.curBlockDamage += block.blockStrength(this.mc.thePlayer);
            if (this.field_1069_h % 4.0f == 0.0f && block != null) {
                this.mc.sndManager.playSound(block.stepSound.func_1145_d(), (float)var1 + 0.5f, (float)var2 + 0.5f, (float)var3 + 0.5f, (block.stepSound.func_1147_b() + 1.0f) / 8.0f, block.stepSound.func_1144_c() * 0.5f);
            }
            this.field_1069_h += 1.0f;
            if (this.curBlockDamage >= 1.0f) {
                this.sendBlockRemoved(var1, var2, var3, var4);
                this.curBlockDamage = 0.0f;
                this.prevBlockDamage = 0.0f;
                this.field_1069_h = 0.0f;
                this.field_1068_i = 5;
            }
        } else {
            this.curBlockDamage = 0.0f;
            this.prevBlockDamage = 0.0f;
            this.field_1069_h = 0.0f;
            this.currentBlockX = var1;
            this.currentBlockY = var2;
            this.currentBlockZ = var3;
        }
    }

    @Override
    public void setPartialTime(float var1) {
        if (this.curBlockDamage <= 0.0f) {
            this.mc.ingameGUI.field_6446_b = 0.0f;
            this.mc.renderGlobal.field_1450_i = 0.0f;
        } else {
            float var2;
            this.mc.ingameGUI.field_6446_b = var2 = this.prevBlockDamage + (this.curBlockDamage - this.prevBlockDamage) * var1;
            this.mc.renderGlobal.field_1450_i = var2;
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
        this.prevBlockDamage = this.curBlockDamage;
        this.mc.sndManager.playRandomMusicIfReady();
    }
}

