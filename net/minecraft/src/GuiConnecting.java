package net.minecraft.src;

import net.minecraft.client.Minecraft;
import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiMainMenu;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.NetClientHandler;
import net.minecraft.src.StringTranslate;
import net.minecraft.src.ThreadConnectToServer;

public class GuiConnecting
extends GuiScreen {
    private NetClientHandler clientHandler;
    private boolean cancelled = false;

    public GuiConnecting(Minecraft var1, String var2, int var3) {
        var1.changeWorld1(null);
        this.mc.gameSettings.realLastServer = String.valueOf(var2) + ":" + var3;
        new ThreadConnectToServer(this, var1, var2, var3).start();
    }

    @Override
    public void updateScreen() {
        if (this.clientHandler != null) {
            this.clientHandler.processReadPackets();
        }
    }

    @Override
    protected void keyTyped(char var1, int var2) {
    }

    @Override
    public void initGui() {
        StringTranslate var1 = StringTranslate.getInstance();
        this.controlList.clear();
        this.controlList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 120 + 12, var1.translateKey("gui.cancel")));
    }

    @Override
    protected void actionPerformed(GuiButton var1) {
        if (var1.id == 0) {
            this.cancelled = true;
            if (this.clientHandler != null) {
                this.clientHandler.disconnect();
            }
            this.mc.displayGuiScreen(new GuiMainMenu());
        }
    }

    @Override
    public void drawScreen(int var1, int var2, float var3) {
        this.drawDefaultBackground();
        StringTranslate var4 = StringTranslate.getInstance();
        if (this.clientHandler == null) {
            this.drawCenteredString(this.fontRenderer, var4.translateKey("connect.connecting"), this.width / 2, this.height / 2 - 50, 0xFFFFFF);
            this.drawCenteredString(this.fontRenderer, "", this.width / 2, this.height / 2 - 10, 0xFFFFFF);
        } else {
            this.drawCenteredString(this.fontRenderer, var4.translateKey("connect.authorizing"), this.width / 2, this.height / 2 - 50, 0xFFFFFF);
            this.drawCenteredString(this.fontRenderer, this.clientHandler.field_1209_a, this.width / 2, this.height / 2 - 10, 0xFFFFFF);
        }
        super.drawScreen(var1, var2, var3);
    }

    static NetClientHandler setNetClientHandler(GuiConnecting var0, NetClientHandler var1) {
        var0.clientHandler = var1;
        return var0.clientHandler;
    }

    static boolean isCancelled(GuiConnecting var0) {
        return var0.cancelled;
    }

    static NetClientHandler getNetClientHandler(GuiConnecting var0) {
        return var0.clientHandler;
    }
}

