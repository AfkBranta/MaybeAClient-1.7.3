/*
 * Decompiled with CFR 0.152.
 */
package net.skidcode.gh.maybeaclient.gui.server;

import java.util.ArrayList;
import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiConnecting;
import net.minecraft.src.GuiMultiplayer;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.GuiSmallButton;
import net.minecraft.src.ImageBufferDownload;
import net.minecraft.src.Tessellator;
import net.skidcode.gh.maybeaclient.Client;
import net.skidcode.gh.maybeaclient.gui.altman.GuiAccManager;
import net.skidcode.gh.maybeaclient.gui.server.GuiEditServer;
import net.skidcode.gh.maybeaclient.gui.server.GuiServerSlot;
import net.skidcode.gh.maybeaclient.gui.server.ServerInfo;
import net.skidcode.gh.maybeaclient.utils.ChatColor;

public class GuiServerSelector
extends GuiScreen {
    public GuiScreen prevMenu;
    public GuiServerSlot slot;
    public GuiButton joinButton;
    public GuiButton editButton;
    public GuiButton deleteButton;
    public GuiButton directConnect;
    public GuiButton addButton;
    public GuiButton accountManager;
    public GuiButton cancelButton;
    public int selectedServer = -1;
    public static ArrayList<ServerInfo> servers = new ArrayList();

    public GuiServerSelector(GuiScreen menu) {
        this.prevMenu = menu;
    }

    @Override
    public void initGui() {
        super.initGui();
        this.controlList.clear();
        this.joinButton = new GuiSmallButton(1, this.width / 2 - 50 - 100 - 5, this.height - 47, 100, 20, "Join");
        this.directConnect = new GuiSmallButton(2, this.width / 2 - 50, this.height - 47, 100, 20, "Direct Connect");
        this.addButton = new GuiSmallButton(3, this.width / 2 - 50 + 100 + 5, this.height - 47, 100, 20, "Add");
        this.editButton = new GuiSmallButton(4, this.width / 2 - 50 - 100 - 5, this.height - 47 + 20 + 2, 100, 20, "Edit");
        this.deleteButton = new GuiSmallButton(5, this.width / 2 - 50, this.height - 47 + 20 + 2, 100, 20, "Delete");
        this.cancelButton = new GuiSmallButton(7, this.width / 2 - 50 + 100 + 5, this.height - 47 + 20 + 2, 100, 20, "Cancel");
        this.accountManager = new GuiSmallButton(6, this.width - 75, 6, 75, 20, "Accounts");
        if (this.selectedServer == -1) {
            this.selectServer(-1);
        }
        this.controlList.add(this.joinButton);
        this.controlList.add(this.directConnect);
        this.controlList.add(this.addButton);
        this.controlList.add(this.editButton);
        this.controlList.add(this.deleteButton);
        this.controlList.add(this.accountManager);
        this.controlList.add(this.cancelButton);
        this.slot = new GuiServerSlot(this);
        this.slot.registerScrollButtons(this.controlList, 8, 9);
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if (button.enabled) {
            switch (button.id) {
                case 1: {
                    ServerInfo selected = servers.get(this.selectedServer);
                    this.mc.displayGuiScreen(new GuiConnecting(this.mc, selected.ip, selected.port));
                    break;
                }
                case 2: {
                    this.mc.displayGuiScreen(new GuiMultiplayer(this));
                    break;
                }
                case 3: {
                    this.mc.displayGuiScreen(new GuiEditServer(this));
                    break;
                }
                case 4: {
                    ServerInfo selected = servers.get(this.selectedServer);
                    this.mc.displayGuiScreen(new GuiEditServer(this, selected));
                    break;
                }
                case 5: {
                    servers.remove(this.selectedServer);
                    this.selectServer(-1);
                    Client.writeCurrentServers();
                    break;
                }
                case 6: {
                    this.mc.displayGuiScreen(new GuiAccManager(this, this.selectedServer));
                    break;
                }
                case 7: {
                    this.mc.displayGuiScreen(this.prevMenu);
                }
            }
        }
    }

    public void selectServer(int i) {
        if (i == -1) {
            this.joinButton.enabled = false;
            this.editButton.enabled = false;
            this.deleteButton.enabled = false;
        } else {
            this.joinButton.enabled = true;
            this.editButton.enabled = true;
            this.deleteButton.enabled = true;
        }
        this.selectedServer = i;
    }

    public void loadSkin(String username) {
        String url = "http://s3.amazonaws.com/MinecraftSkins/" + username + ".png";
        this.mc.renderEngine.obtainImageData(url, new ImageBufferDownload());
        int i = this.mc.renderEngine.getTextureForDownloadableImage(url, "/mob/char.png");
        if (i >= 0) {
            this.mc.renderEngine.bindTexture(i);
        }
    }

    @Override
    public void drawScreen(int mX, int mY, float rendTicks) {
        this.slot.drawScreen(mX, mY, rendTicks);
        this.drawCenteredString(this.fontRenderer, "Servers", this.width / 2, 12, 0xFFFFFF);
        this.drawString(this.fontRenderer, "Username: " + (Object)((Object)ChatColor.LIGHTGREEN) + this.mc.session.username, 26, 12, 0xFFFFFF);
        this.loadSkin(this.mc.session.username);
        Tessellator tes = Tessellator.instance;
        tes.startDrawingQuads();
        tes.setColorOpaque_I(0xFFFFFF);
        tes.addVertexWithUV(2.0, 23.0, 0.0, 0.125, 0.5);
        tes.addVertexWithUV(18.0, 23.0, 0.0, 0.25, 0.5);
        tes.addVertexWithUV(18.0, 6.0, 0.0, 0.25, 0.25);
        tes.addVertexWithUV(2.0, 6.0, 0.0, 0.125, 0.25);
        tes.draw();
        super.drawScreen(mX, mY, rendTicks);
    }
}

