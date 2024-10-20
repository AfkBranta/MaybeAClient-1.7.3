/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.src;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiOptions;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.GuiSelectWorld;
import net.minecraft.src.GuiTexturePacks;
import net.minecraft.src.MathHelper;
import net.minecraft.src.StringTranslate;
import net.minecraft.src.Tessellator;
import net.skidcode.gh.maybeaclient.gui.server.GuiServerSelector;
import net.skidcode.gh.maybeaclient.hacks.FreecamHack;
import net.skidcode.gh.maybeaclient.hacks.WorldDLHack;
import net.skidcode.gh.maybeaclient.utils.ChatColor;
import org.lwjgl.opengl.GL11;

public class GuiMainMenu
extends GuiScreen {
    private static final Random rand = new Random();
    private float updateCounter = 0.0f;
    private String splashText = "missingno";
    private GuiButton field_25096_l;

    public GuiMainMenu() {
        FreecamHack.hasXYZ = false;
        try {
            ArrayList<String> var1 = new ArrayList<String>();
            BufferedReader var2 = new BufferedReader(new InputStreamReader(GuiMainMenu.class.getResourceAsStream("/title/splashes.txt"), Charset.forName("UTF-8")));
            String var3 = "";
            while ((var3 = var2.readLine()) != null) {
                if ((var3 = var3.trim()).length() <= 0) continue;
                var1.add(var3);
            }
            this.splashText = (String)var1.get(rand.nextInt(var1.size()));
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    @Override
    public void updateScreen() {
        this.updateCounter += 1.0f;
    }

    @Override
    protected void keyTyped(char var1, int var2) {
    }

    @Override
    public void initGui() {
        Calendar var1 = Calendar.getInstance();
        var1.setTime(new Date());
        if (var1.get(2) + 1 == 11 && var1.get(5) == 9) {
            this.splashText = "Happy birthday, ez!";
        } else if (var1.get(2) + 1 == 6 && var1.get(5) == 1) {
            this.splashText = "Happy birthday, Notch!";
        } else if (var1.get(2) + 1 == 12 && var1.get(5) == 24) {
            this.splashText = "Merry X-mas!";
        } else if (var1.get(2) + 1 == 1 && var1.get(5) == 1) {
            this.splashText = "Happy new year!";
        }
        StringTranslate var2 = StringTranslate.getInstance();
        int var4 = this.height / 4 + 48;
        this.controlList.add(new GuiButton(1, this.width / 2 - 100, var4, var2.translateKey("menu.singleplayer")));
        this.field_25096_l = new GuiButton(2, this.width / 2 - 100, var4 + 24, var2.translateKey("menu.multiplayer"));
        this.controlList.add(this.field_25096_l);
        this.controlList.add(new GuiButton(3, this.width / 2 - 100, var4 + 48, var2.translateKey("menu.mods")));
        if (this.mc.hideQuitButton) {
            this.controlList.add(new GuiButton(0, this.width / 2 - 100, var4 + 72, var2.translateKey("menu.options")));
        } else {
            this.controlList.add(new GuiButton(0, this.width / 2 - 100, var4 + 72 + 12, 98, 20, var2.translateKey("menu.options")));
            this.controlList.add(new GuiButton(4, this.width / 2 + 2, var4 + 72 + 12, 98, 20, var2.translateKey("menu.quit")));
        }
        if (this.mc.session == null) {
            this.field_25096_l.enabled = false;
        }
    }

    @Override
    protected void actionPerformed(GuiButton var1) {
        if (var1.id == 0) {
            this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
        }
        if (var1.id == 1) {
            this.mc.displayGuiScreen(new GuiSelectWorld(this));
        }
        if (var1.id == 2) {
            this.mc.displayGuiScreen(new GuiServerSelector(this));
        }
        if (var1.id == 3) {
            this.mc.displayGuiScreen(new GuiTexturePacks(this));
        }
        if (var1.id == 4) {
            this.mc.shutdown();
        }
    }

    @Override
    public void drawScreen(int var1, int var2, float var3) {
        this.drawDefaultBackground();
        WorldDLHack.instance.status = false;
        Tessellator var4 = Tessellator.instance;
        int var5 = 274;
        int var6 = this.width / 2 - var5 / 2;
        int var7 = 30;
        GL11.glBindTexture((int)3553, (int)this.mc.renderEngine.getTexture("/title/mclogo.png"));
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        this.drawTexturedModalRect(var6 + 0, var7 + 0, 0, 0, 155, 44);
        this.drawTexturedModalRect(var6 + 155, var7 + 0, 0, 45, 155, 44);
        var4.setColorOpaque_I(0xFFFFFF);
        GL11.glPushMatrix();
        GL11.glTranslatef((float)(this.width / 2 + 90), (float)70.0f, (float)0.0f);
        GL11.glRotatef((float)-20.0f, (float)0.0f, (float)0.0f, (float)1.0f);
        float var8 = 1.8f - MathHelper.abs(MathHelper.sin((float)(System.currentTimeMillis() % 1000L) / 1000.0f * (float)Math.PI * 2.0f) * 0.1f);
        var8 = var8 * 100.0f / (float)(this.fontRenderer.getStringWidth(this.splashText) + 32);
        GL11.glScalef((float)var8, (float)var8, (float)var8);
        this.drawCenteredString(this.fontRenderer, this.splashText, 0, -8, 0xFFFF00);
        GL11.glPopMatrix();
        this.drawString(this.fontRenderer, "Minecraft Beta 1.4_01", 2, 2, 0x505050);
        this.drawString(this.fontRenderer, (Object)((Object)ChatColor.LIGHTCYAN) + "MaybeAClient" + " " + (Object)((Object)ChatColor.GOLD) + "1.7.3", 2, 14, 0x505050);
        String var9 = "Copyright Mojang AB. Do not distribute.";
        this.drawString(this.fontRenderer, (Object)((Object)ChatColor.DARKGRAY) + "Made by GameHerobrine.", 2, this.height - 10, 0xFFFFFF);
        this.drawString(this.fontRenderer, var9, this.width - this.fontRenderer.getStringWidth(var9) - 2, this.height - 10, 0xFFFFFF);
        super.drawScreen(var1, var2, var3);
    }
}

