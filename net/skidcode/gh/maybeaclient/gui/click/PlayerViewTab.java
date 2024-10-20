/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.skidcode.gh.maybeaclient.gui.click;

import net.minecraft.src.EntityPlayerSP;
import net.minecraft.src.GuiIngame;
import net.minecraft.src.RenderHelper;
import net.minecraft.src.RenderManager;
import net.skidcode.gh.maybeaclient.Client;
import net.skidcode.gh.maybeaclient.gui.click.Tab;
import net.skidcode.gh.maybeaclient.hacks.PlayerViewHack;
import net.skidcode.gh.maybeaclient.utils.PlayerUtils;
import org.lwjgl.opengl.GL11;

public class PlayerViewTab
extends Tab {
    public static PlayerViewTab instance;

    public PlayerViewTab() {
        super("Player View");
        this.xPos = 160;
        this.xDefPos = 160;
        this.yPos = 80;
        this.yDefPos = 80;
        this.minimized = true;
        instance = this;
    }

    @Override
    public void renderIngame() {
        if (PlayerViewHack.instance.status && RenderManager.instance.livingPlayer != null) {
            super.renderIngame();
        }
    }

    @Override
    public void render() {
        if (this.minimized) {
            this.height = 12;
            this.width = Client.mc.fontRenderer.getStringWidth(this.name) + 2;
            super.render();
            return;
        }
        EntityPlayerSP player = Client.mc.thePlayer;
        this.height = 104;
        this.width = Client.mc.fontRenderer.getStringWidth(this.name) + 2 + 32;
        super.render();
        this.renderFrame(this.xPos, this.yPos + 12 + 3, this.xPos + this.width, this.yPos + this.height);
        GL11.glEnable((int)32826);
        GL11.glEnable((int)2903);
        GL11.glPushMatrix();
        int centerX = (this.width - 16) / 2;
        int centerY = this.height - 12;
        GL11.glTranslatef((float)(centerX + this.xPos), (float)(centerY + this.yPos), (float)50.0f);
        GL11.glScalef((float)-35.0f, (float)35.0f, (float)35.0f);
        GL11.glRotatef((float)180.0f, (float)0.0f, (float)0.0f, (float)1.0f);
        float lastRYaw = player.rotationYaw;
        float prevLastRYaw = player.prevRotationYaw;
        float lastBYaw = player.renderYawOffset;
        float prevLastBYaw = player.prevRenderYawOffset;
        player.rotationYaw = -PlayerUtils.wrapAngle180((lastRYaw % 360.0f + 360.0f) % 360.0f);
        player.prevRotationYaw = -PlayerUtils.wrapAngle180((prevLastRYaw % 360.0f + 360.0f) % 360.0f);
        player.renderYawOffset = -PlayerUtils.wrapAngle180((lastBYaw % 360.0f + 360.0f) % 360.0f);
        player.prevRenderYawOffset = -PlayerUtils.wrapAngle180((prevLastBYaw % 360.0f + 360.0f) % 360.0f);
        if (PlayerViewHack.instance.dontRotate.value) {
            player.prevRotationYaw = 0.0f;
            player.rotationYaw = 0.0f;
            player.prevRenderYawOffset = 0.0f;
            player.renderYawOffset = 0.0f;
        }
        GL11.glEnable((int)2929);
        float var10 = centerY + 75 - 50;
        GL11.glRotatef((float)135.0f, (float)0.0f, (float)1.0f, (float)0.0f);
        RenderHelper.enableStandardItemLighting();
        GL11.glRotatef((float)-135.0f, (float)0.0f, (float)1.0f, (float)0.0f);
        GL11.glRotatef((float)(-((float)Math.atan(var10 / 40.0f)) * 20.0f), (float)1.0f, (float)0.0f, (float)0.0f);
        GL11.glTranslatef((float)0.0f, (float)Client.mc.thePlayer.yOffset, (float)0.0f);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        RenderManager.instance.renderEntityWithPosYaw(player, 0.0, 0.0, 0.0, 0.0f, 1.0f);
        player.rotationYaw = lastRYaw;
        player.prevRotationYaw = prevLastRYaw;
        player.renderYawOffset = lastBYaw;
        player.prevRenderYawOffset = prevLastBYaw;
        GL11.glDisable((int)2929);
        GL11.glDisable((int)32826);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        int yOff = 0;
        while (yOff < 4) {
            GL11.glDisable((int)2896);
            GL11.glDisable((int)2929);
            GuiIngame.itemRenderer.renderItemIntoGUI(Client.mc.fontRenderer, Client.mc.renderEngine, player.inventory.armorInventory[3 - yOff], this.xPos + this.width - 16, this.yPos + 15 + 18 * yOff);
            GuiIngame.itemRenderer.renderItemOverlayIntoGUI(Client.mc.fontRenderer, Client.mc.renderEngine, player.inventory.armorInventory[3 - yOff], this.xPos + this.width - 16, this.yPos + 15 + 18 * yOff);
            ++yOff;
        }
        GL11.glDisable((int)2929);
        GL11.glDisable((int)2896);
        GuiIngame.itemRenderer.renderItemIntoGUI(Client.mc.fontRenderer, Client.mc.renderEngine, player.inventory.getCurrentItem(), this.xPos + this.width - 16, this.yPos + 15 + 18 * yOff);
        GuiIngame.itemRenderer.renderItemOverlayIntoGUI(Client.mc.fontRenderer, Client.mc.renderEngine, player.inventory.getCurrentItem(), this.xPos + this.width - 16, this.yPos + 15 + 18 * yOff);
        GL11.glDisable((int)2896);
        GL11.glEnable((int)3553);
        RenderHelper.disableStandardItemLighting();
        GL11.glPopMatrix();
    }
}

