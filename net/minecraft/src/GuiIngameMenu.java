package net.minecraft.src;

import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiMainMenu;
import net.minecraft.src.GuiOptions;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.MathHelper;
import net.minecraft.src.StatList;
import net.skidcode.gh.maybeaclient.hacks.FreecamHack;

public class GuiIngameMenu
extends GuiScreen {
    private int updateCounter2 = 0;
    private int updateCounter = 0;

    @Override
    public void initGui() {
        this.updateCounter2 = 0;
        this.controlList.clear();
        this.controlList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 48, "Save and quit to title"));
        if (this.mc.isMultiplayerWorld()) {
            ((GuiButton)this.controlList.get((int)0)).displayString = "Disconnect";
        }
        this.controlList.add(new GuiButton(4, this.width / 2 - 100, this.height / 4 + 24, "Back to game"));
        this.controlList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 96, "Options..."));
    }

    @Override
    protected void actionPerformed(GuiButton var1) {
        if (var1.id == 0) {
            this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
        }
        if (var1.id == 1) {
            this.mc.field_25001_G.func_25100_a(StatList.field_25180_i, 1);
            if (this.mc.isMultiplayerWorld()) {
                this.mc.theWorld.sendQuittingDisconnectingPacket();
            }
            this.mc.changeWorld1(null);
            FreecamHack.hasXYZ = false;
            this.mc.displayGuiScreen(new GuiMainMenu());
        }
        if (var1.id == 4) {
            this.mc.displayGuiScreen(null);
            this.mc.setIngameFocus();
        }
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        ++this.updateCounter;
    }

    @Override
    public void drawScreen(int var1, int var2, float var3) {
        boolean var4;
        this.drawDefaultBackground();
        boolean bl = var4 = !this.mc.theWorld.func_650_a(this.updateCounter2++);
        if (var4 || this.updateCounter < 20) {
            float var5 = ((float)(this.updateCounter % 10) + var3) / 10.0f;
            var5 = MathHelper.sin(var5 * (float)Math.PI * 2.0f) * 0.2f + 0.8f;
            int var6 = (int)(255.0f * var5);
            this.drawString(this.fontRenderer, "Saving level..", 8, this.height - 16, var6 << 16 | var6 << 8 | var6);
        }
        this.drawCenteredString(this.fontRenderer, "Game menu", this.width / 2, 40, 0xFFFFFF);
        super.drawScreen(var1, var2, var3);
    }
}

