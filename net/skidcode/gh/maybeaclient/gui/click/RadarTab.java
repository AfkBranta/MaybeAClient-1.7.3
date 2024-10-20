/*
 * Decompiled with CFR 0.152.
 */
package net.skidcode.gh.maybeaclient.gui.click;

import java.util.HashMap;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntityPlayerSP;
import net.minecraft.src.NBTBase;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.ScaledResolution;
import net.skidcode.gh.maybeaclient.Client;
import net.skidcode.gh.maybeaclient.gui.click.ClickGUI;
import net.skidcode.gh.maybeaclient.gui.click.Tab;
import net.skidcode.gh.maybeaclient.hacks.RadarHack;
import net.skidcode.gh.maybeaclient.utils.ChatColor;

public class RadarTab
extends Tab {
    public static RadarTab instance;
    boolean first = true;
    boolean prevMinimized = this.minimized;

    public RadarTab() {
        super("Radar", 0, 12);
        instance = this;
    }

    @Override
    public void renderIngame() {
        if (RadarHack.instance.status) {
            super.renderIngame();
        }
    }

    public void renderName(boolean alignRight) {
        if (alignRight) {
            int xStart = this.xPos;
            int yStart = this.yPos;
            this.renderFrame(xStart, yStart, xStart + this.width, yStart + 12);
            Client.mc.fontRenderer.drawString(this.name, xStart + this.width - Client.mc.fontRenderer.getStringWidth(this.name), yStart + 2, 0xFFFFFF);
        } else {
            super.renderName();
        }
    }

    @Override
    public void renderMinimized() {
        this.height = 12;
        this.renderName(RadarHack.instance.alignment.currentMode.equalsIgnoreCase("Right"));
    }

    @Override
    public void render() {
        int savdWidth = this.width;
        this.width = Client.mc.fontRenderer.getStringWidth(this.name) + 2;
        int savdHeight = this.height;
        EntityPlayerSP local = Client.mc.thePlayer;
        HashMap<Integer, String> players = new HashMap<Integer, String>();
        int height = 14;
        int width = this.width;
        boolean showCoords = RadarHack.instance.showXYZ.value;
        for (Object o : Client.mc.theWorld.playerEntities) {
            EntityPlayer player = (EntityPlayer)o;
            if (player.entityId == local.entityId && !(Client.mc.currentScreen instanceof ClickGUI)) continue;
            String d = String.format("%.2f", player.getDistance(local.posX, local.posY, local.posZ));
            String dist = String.valueOf(player.username) + " [" + (Object)((Object)ChatColor.LIGHTCYAN) + d + (Object)((Object)ChatColor.WHITE) + "]";
            if (showCoords) {
                dist = String.valueOf(dist) + " XYZ: " + String.format("%s%.2f %.2f %.2f", new Object[]{ChatColor.LIGHTCYAN, player.posX, player.posY, player.posZ});
            }
            players.put(player.entityId, dist);
            int w = Client.mc.fontRenderer.getStringWidth(dist) + 2;
            if (w > width) {
                width = w;
            }
            height += 12;
        }
        this.height = height;
        this.width = width;
        boolean alignRight = RadarHack.instance.alignment.currentMode.equalsIgnoreCase("Right");
        boolean expandTop = RadarHack.instance.expand.currentMode.equalsIgnoreCase("Top");
        ScaledResolution scaledResolution = new ScaledResolution(Client.mc.gameSettings, Client.mc.displayWidth, Client.mc.displayHeight);
        if (RadarHack.instance.staticPositon.currentMode.equalsIgnoreCase("Bottom Right")) {
            alignRight = true;
            expandTop = true;
            this.xPos = scaledResolution.getScaledWidth() - this.width;
            this.yPos = scaledResolution.getScaledHeight() - this.height;
        } else if (RadarHack.instance.staticPositon.currentMode.equalsIgnoreCase("Bottom Left")) {
            alignRight = false;
            expandTop = true;
            this.xPos = 0;
            this.yPos = scaledResolution.getScaledHeight() - this.height;
        } else if (RadarHack.instance.staticPositon.currentMode.equalsIgnoreCase("Top Right")) {
            alignRight = true;
            expandTop = false;
            this.xPos = scaledResolution.getScaledWidth() - this.width;
            this.yPos = 0;
        } else if (RadarHack.instance.staticPositon.currentMode.equalsIgnoreCase("Top Left")) {
            alignRight = false;
            expandTop = false;
            this.xPos = 0;
            this.yPos = 0;
        }
        if (!this.minimized) {
            if (this.first) {
                this.first = false;
                if (alignRight && savdWidth != this.width) {
                    this.xPos -= this.width - savdWidth;
                    Client.saveClickGUI();
                }
            } else {
                boolean sav = false;
                if (expandTop && savdHeight != this.height) {
                    this.yPos -= this.height - savdHeight;
                    sav = true;
                }
                if (alignRight && savdWidth != this.width) {
                    this.xPos -= this.width - savdWidth;
                    sav = true;
                }
                if (sav) {
                    Client.saveClickGUI();
                }
            }
        }
        if (this.minimized) {
            this.width = Client.mc.fontRenderer.getStringWidth(this.name) + 2;
            if (alignRight && savdWidth != this.width) {
                this.xPos -= this.width - savdWidth;
            }
            this.renderMinimized();
            return;
        }
        if (players.size() > 0) {
            this.renderFrame(this.xPos, this.yPos + 15, this.xPos + this.width, this.yPos + this.height);
            int h = 15;
            for (String s : players.values()) {
                if (alignRight) {
                    Client.mc.fontRenderer.drawString(s, this.xPos + this.width - Client.mc.fontRenderer.getStringWidth(s), this.yPos + h + 2, 0xFFFFFF);
                } else {
                    Client.mc.fontRenderer.drawString(s, this.xPos + 2, this.yPos + h + 2, 0xFFFFFF);
                }
                h += 12;
            }
        }
        this.renderName(alignRight);
        this.prevMinimized = this.minimized;
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        NBTTagCompound comp = (NBTTagCompound)NBTBase.createTagOfType((byte)10);
        comp.setInteger("xPos", RadarHack.instance.alignment.currentMode.equalsIgnoreCase("Right") ? this.xPos + this.width : this.xPos);
        comp.setInteger("yPos", this.yPos);
        comp.setBoolean("Minimized", this.minimized);
        tag.setCompoundTag("Position", comp);
    }
}

