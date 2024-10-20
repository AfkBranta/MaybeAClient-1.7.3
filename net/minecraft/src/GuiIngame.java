/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.src;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.src.Block;
import net.minecraft.src.ChatLine;
import net.minecraft.src.FontRenderer;
import net.minecraft.src.Gui;
import net.minecraft.src.GuiChat;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.RenderHelper;
import net.minecraft.src.RenderItem;
import net.minecraft.src.ScaledResolution;
import net.minecraft.src.StringTranslate;
import net.minecraft.src.Tessellator;
import net.skidcode.gh.maybeaclient.IngameHook;
import net.skidcode.gh.maybeaclient.events.EventRegistry;
import net.skidcode.gh.maybeaclient.events.impl.EventRenderIngameNoDebug;
import org.lwjgl.opengl.GL11;

public class GuiIngame
extends Gui {
    public static RenderItem itemRenderer = new RenderItem();
    private List chatMessageList = new ArrayList();
    private List<Boolean> chatMessagesBGs = new ArrayList<Boolean>();
    private Random rand = new Random();
    private Minecraft mc;
    public String field_933_a = null;
    private int updateCounter = 0;
    private String recordPlaying = "";
    private int recordPlayingUpFor = 0;
    private boolean field_22065_l = false;
    public float field_6446_b;
    float prevVignetteBrightness = 1.0f;

    public GuiIngame(Minecraft var1) {
        this.mc = var1;
    }

    public void renderGameOverlay(float var1, boolean var2, int var3, int var4) {
        int var17;
        int var16;
        int var15;
        boolean var12;
        float var10;
        ScaledResolution var5 = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
        int var6 = var5.getScaledWidth();
        int var7 = var5.getScaledHeight();
        FontRenderer var8 = this.mc.fontRenderer;
        this.mc.entityRenderer.setupScaledResolution();
        GL11.glEnable((int)3042);
        if (Minecraft.isFancyGraphicsEnabled()) {
            this.renderVignette(this.mc.thePlayer.getEntityBrightness(var1), var6, var7);
        }
        ItemStack var9 = this.mc.thePlayer.inventory.armorItemInSlot(3);
        if (!this.mc.gameSettings.thirdPersonView && var9 != null && var9.itemID == Block.pumpkin.blockID) {
            this.renderPumpkinBlur(var6, var7);
        }
        if ((var10 = this.mc.thePlayer.prevTimeInPortal + (this.mc.thePlayer.timeInPortal - this.mc.thePlayer.prevTimeInPortal) * var1) > 0.0f) {
            this.renderPortalOverlay(var10, var6, var7);
        }
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glBindTexture((int)3553, (int)this.mc.renderEngine.getTexture("/gui/gui.png"));
        InventoryPlayer var11 = this.mc.thePlayer.inventory;
        this.zLevel = -90.0f;
        this.drawTexturedModalRect(var6 / 2 - 91, var7 - 22, 0, 0, 182, 22);
        this.drawTexturedModalRect(var6 / 2 - 91 - 1 + var11.currentItem * 20, var7 - 22 - 1, 0, 22, 24, 22);
        GL11.glBindTexture((int)3553, (int)this.mc.renderEngine.getTexture("/gui/icons.png"));
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)775, (int)769);
        this.drawTexturedModalRect(var6 / 2 - 7, var7 / 2 - 7, 0, 0, 16, 16);
        GL11.glDisable((int)3042);
        boolean bl = var12 = this.mc.thePlayer.field_9306_bj / 3 % 2 == 1;
        if (this.mc.thePlayer.field_9306_bj < 10) {
            var12 = false;
        }
        int var13 = this.mc.thePlayer.health;
        int var14 = this.mc.thePlayer.prevHealth;
        this.rand.setSeed(this.updateCounter * 312871);
        if (this.mc.playerController.shouldDrawHUD()) {
            int var18;
            var15 = this.mc.thePlayer.getPlayerArmorValue();
            var16 = 0;
            while (var16 < 10) {
                var17 = var7 - 32;
                if (var15 > 0) {
                    var18 = var6 / 2 + 91 - var16 * 8 - 9;
                    if (var16 * 2 + 1 < var15) {
                        this.drawTexturedModalRect(var18, var17, 34, 9, 9, 9);
                    }
                    if (var16 * 2 + 1 == var15) {
                        this.drawTexturedModalRect(var18, var17, 25, 9, 9, 9);
                    }
                    if (var16 * 2 + 1 > var15) {
                        this.drawTexturedModalRect(var18, var17, 16, 9, 9, 9);
                    }
                }
                int var28 = 0;
                if (var12) {
                    var28 = 1;
                }
                int var19 = var6 / 2 - 91 + var16 * 8;
                if (var13 <= 4) {
                    var17 += this.rand.nextInt(2);
                }
                this.drawTexturedModalRect(var19, var17, 16 + var28 * 9, 0, 9, 9);
                if (var12) {
                    if (var16 * 2 + 1 < var14) {
                        this.drawTexturedModalRect(var19, var17, 70, 0, 9, 9);
                    }
                    if (var16 * 2 + 1 == var14) {
                        this.drawTexturedModalRect(var19, var17, 79, 0, 9, 9);
                    }
                }
                if (var16 * 2 + 1 < var13) {
                    this.drawTexturedModalRect(var19, var17, 52, 0, 9, 9);
                }
                if (var16 * 2 + 1 == var13) {
                    this.drawTexturedModalRect(var19, var17, 61, 0, 9, 9);
                }
                ++var16;
            }
            if (this.mc.thePlayer.isInsideOfMaterial(Material.water)) {
                var16 = (int)Math.ceil((double)(this.mc.thePlayer.air - 2) * 10.0 / 300.0);
                var17 = (int)Math.ceil((double)this.mc.thePlayer.air * 10.0 / 300.0) - var16;
                var18 = 0;
                while (var18 < var16 + var17) {
                    if (var18 < var16) {
                        this.drawTexturedModalRect(var6 / 2 - 91 + var18 * 8, var7 - 32 - 9, 16, 18, 9, 9);
                    } else {
                        this.drawTexturedModalRect(var6 / 2 - 91 + var18 * 8, var7 - 32 - 9, 25, 18, 9, 9);
                    }
                    ++var18;
                }
            }
        }
        GL11.glDisable((int)3042);
        GL11.glEnable((int)32826);
        GL11.glPushMatrix();
        GL11.glRotatef((float)180.0f, (float)1.0f, (float)0.0f, (float)0.0f);
        RenderHelper.enableStandardItemLighting();
        GL11.glPopMatrix();
        var15 = 0;
        while (var15 < 9) {
            var16 = var6 / 2 - 90 + var15 * 20 + 2;
            var17 = var7 - 16 - 3;
            this.renderInventorySlot(var15, var16, var17, var1);
            ++var15;
        }
        RenderHelper.disableStandardItemLighting();
        GL11.glDisable((int)32826);
        if (this.mc.thePlayer.func_22060_M() > 0) {
            GL11.glDisable((int)2929);
            GL11.glDisable((int)3008);
            var15 = this.mc.thePlayer.func_22060_M();
            float var27 = (float)var15 / 100.0f;
            if (var27 > 1.0f) {
                var27 = 1.0f - (float)(var15 - 100) / 10.0f;
            }
            var17 = (int)(220.0f * var27) << 24 | 0x101020;
            this.drawRect(0, 0, var6, var7, var17);
            GL11.glEnable((int)3008);
            GL11.glEnable((int)2929);
        }
        if (this.mc.gameSettings.showDebugInfo) {
            var8.drawStringWithShadow("Minecraft Beta 1.4_01 (" + this.mc.debug + ")", 2, 2, 0xFFFFFF);
            var8.drawStringWithShadow(this.mc.func_6241_m(), 2, 12, 0xFFFFFF);
            var8.drawStringWithShadow(this.mc.func_6262_n(), 2, 22, 0xFFFFFF);
            var8.drawStringWithShadow(this.mc.func_6245_o(), 2, 32, 0xFFFFFF);
            var8.drawStringWithShadow(this.mc.func_21002_o(), 2, 42, 0xFFFFFF);
            long var24 = Runtime.getRuntime().maxMemory();
            long var29 = Runtime.getRuntime().totalMemory();
            long var30 = Runtime.getRuntime().freeMemory();
            long var21 = var29 - var30;
            String var23 = "Used memory: " + var21 * 100L / var24 + "% (" + var21 / 1024L / 1024L + "MB) of " + var24 / 1024L / 1024L + "MB";
            this.drawString(var8, var23, var6 - var8.getStringWidth(var23) - 2, 2, 0xE0E0E0);
            var23 = "Allocated memory: " + var29 * 100L / var24 + "% (" + var29 / 1024L / 1024L + "MB)";
            this.drawString(var8, var23, var6 - var8.getStringWidth(var23) - 2, 12, 0xE0E0E0);
            this.drawString(var8, "x: " + this.mc.thePlayer.posX, 2, 64, 0xE0E0E0);
            this.drawString(var8, "y: " + this.mc.thePlayer.posY, 2, 72, 0xE0E0E0);
            this.drawString(var8, "z: " + this.mc.thePlayer.posZ, 2, 80, 0xE0E0E0);
        } else {
            EventRegistry.handleEvent(new EventRenderIngameNoDebug(var5));
            IngameHook.handleIngame(var5);
        }
        if (this.recordPlayingUpFor > 0) {
            float var25 = (float)this.recordPlayingUpFor - var1;
            var16 = (int)(var25 * 256.0f / 20.0f);
            if (var16 > 255) {
                var16 = 255;
            }
            if (var16 > 0) {
                GL11.glPushMatrix();
                GL11.glTranslatef((float)(var6 / 2), (float)(var7 - 48), (float)0.0f);
                GL11.glEnable((int)3042);
                GL11.glBlendFunc((int)770, (int)771);
                var17 = 0xFFFFFF;
                if (this.field_22065_l) {
                    var17 = Color.HSBtoRGB(var25 / 50.0f, 0.7f, 0.6f) & 0xFFFFFF;
                }
                var8.drawString(this.recordPlaying, -var8.getStringWidth(this.recordPlaying) / 2, -4, var17 + (var16 << 24));
                GL11.glDisable((int)3042);
                GL11.glPopMatrix();
            }
        }
        int var26 = 10;
        boolean var31 = false;
        if (this.mc.currentScreen instanceof GuiChat) {
            var26 = 20;
            var31 = true;
        }
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glDisable((int)3008);
        GL11.glDisable((int)2929);
        GL11.glPushMatrix();
        GL11.glTranslatef((float)0.0f, (float)(var7 - 48), (float)0.0f);
        var17 = 0;
        while (var17 < this.chatMessageList.size() && var17 < var26) {
            if (((ChatLine)this.chatMessageList.get((int)var17)).updateCounter < 200 || var31) {
                double var32 = (double)((ChatLine)this.chatMessageList.get((int)var17)).updateCounter / 200.0;
                var32 = 1.0 - var32;
                if ((var32 *= 10.0) < 0.0) {
                    var32 = 0.0;
                }
                if (var32 > 1.0) {
                    var32 = 1.0;
                }
                var32 *= var32;
                int var20 = (int)(255.0 * var32);
                if (var31) {
                    var20 = 255;
                }
                if (var20 > 0) {
                    int var33 = 2;
                    int var22 = -var17 * 9;
                    String var23 = ((ChatLine)this.chatMessageList.get((int)var17)).message;
                    if (this.chatMessagesBGs.get(var17).booleanValue()) {
                        this.drawRect(var33, var22 - 1, var33 + 320, var22 + 8, (int)((float)var20 / 1.2f) << 24);
                    } else {
                        this.drawRect(var33, var22 - 1, var33 + 320, var22 + 8, var20 / 2 << 24);
                    }
                    GL11.glEnable((int)3042);
                    var8.drawStringWithShadow(var23, var33, var22, 0xFFFFFF + (var20 << 24));
                }
            }
            ++var17;
        }
        GL11.glPopMatrix();
        GL11.glEnable((int)3008);
        GL11.glEnable((int)2929);
        GL11.glDisable((int)3042);
    }

    private void renderPumpkinBlur(int var1, int var2) {
        GL11.glDisable((int)2929);
        GL11.glDepthMask((boolean)false);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glDisable((int)3008);
        GL11.glBindTexture((int)3553, (int)this.mc.renderEngine.getTexture("%blur%/misc/pumpkinblur.png"));
        Tessellator var3 = Tessellator.instance;
        var3.startDrawingQuads();
        var3.addVertexWithUV(0.0, var2, -90.0, 0.0, 1.0);
        var3.addVertexWithUV(var1, var2, -90.0, 1.0, 1.0);
        var3.addVertexWithUV(var1, 0.0, -90.0, 1.0, 0.0);
        var3.addVertexWithUV(0.0, 0.0, -90.0, 0.0, 0.0);
        var3.draw();
        GL11.glDepthMask((boolean)true);
        GL11.glEnable((int)2929);
        GL11.glEnable((int)3008);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
    }

    private void renderVignette(float var1, int var2, int var3) {
        if ((var1 = 1.0f - var1) < 0.0f) {
            var1 = 0.0f;
        }
        if (var1 > 1.0f) {
            var1 = 1.0f;
        }
        this.prevVignetteBrightness = (float)((double)this.prevVignetteBrightness + (double)(var1 - this.prevVignetteBrightness) * 0.01);
        GL11.glDisable((int)2929);
        GL11.glDepthMask((boolean)false);
        GL11.glBlendFunc((int)0, (int)769);
        GL11.glColor4f((float)this.prevVignetteBrightness, (float)this.prevVignetteBrightness, (float)this.prevVignetteBrightness, (float)1.0f);
        GL11.glBindTexture((int)3553, (int)this.mc.renderEngine.getTexture("%blur%/misc/vignette.png"));
        Tessellator var4 = Tessellator.instance;
        var4.startDrawingQuads();
        var4.addVertexWithUV(0.0, var3, -90.0, 0.0, 1.0);
        var4.addVertexWithUV(var2, var3, -90.0, 1.0, 1.0);
        var4.addVertexWithUV(var2, 0.0, -90.0, 1.0, 0.0);
        var4.addVertexWithUV(0.0, 0.0, -90.0, 0.0, 0.0);
        var4.draw();
        GL11.glDepthMask((boolean)true);
        GL11.glEnable((int)2929);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glBlendFunc((int)770, (int)771);
    }

    private void renderPortalOverlay(float var1, int var2, int var3) {
        var1 *= var1;
        var1 *= var1;
        var1 = var1 * 0.8f + 0.2f;
        GL11.glDisable((int)3008);
        GL11.glDisable((int)2929);
        GL11.glDepthMask((boolean)false);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)var1);
        GL11.glBindTexture((int)3553, (int)this.mc.renderEngine.getTexture("/terrain.png"));
        float var4 = (float)(Block.portal.blockIndexInTexture % 16) / 16.0f;
        float var5 = (float)(Block.portal.blockIndexInTexture / 16) / 16.0f;
        float var6 = (float)(Block.portal.blockIndexInTexture % 16 + 1) / 16.0f;
        float var7 = (float)(Block.portal.blockIndexInTexture / 16 + 1) / 16.0f;
        Tessellator var8 = Tessellator.instance;
        var8.startDrawingQuads();
        var8.addVertexWithUV(0.0, var3, -90.0, var4, var7);
        var8.addVertexWithUV(var2, var3, -90.0, var6, var7);
        var8.addVertexWithUV(var2, 0.0, -90.0, var6, var5);
        var8.addVertexWithUV(0.0, 0.0, -90.0, var4, var5);
        var8.draw();
        GL11.glDepthMask((boolean)true);
        GL11.glEnable((int)2929);
        GL11.glEnable((int)3008);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
    }

    private void renderInventorySlot(int var1, int var2, int var3, float var4) {
        ItemStack var5 = this.mc.thePlayer.inventory.mainInventory[var1];
        if (var5 != null) {
            float var6 = (float)var5.animationsToGo - var4;
            if (var6 > 0.0f) {
                GL11.glPushMatrix();
                float var7 = 1.0f + var6 / 5.0f;
                GL11.glTranslatef((float)(var2 + 8), (float)(var3 + 12), (float)0.0f);
                GL11.glScalef((float)(1.0f / var7), (float)((var7 + 1.0f) / 2.0f), (float)1.0f);
                GL11.glTranslatef((float)(-(var2 + 8)), (float)(-(var3 + 12)), (float)0.0f);
            }
            itemRenderer.renderItemIntoGUI(this.mc.fontRenderer, this.mc.renderEngine, var5, var2, var3);
            if (var6 > 0.0f) {
                GL11.glPopMatrix();
            }
            itemRenderer.renderItemOverlayIntoGUI(this.mc.fontRenderer, this.mc.renderEngine, var5, var2, var3);
        }
    }

    public void updateTick() {
        if (this.recordPlayingUpFor > 0) {
            --this.recordPlayingUpFor;
        }
        ++this.updateCounter;
        int var1 = 0;
        while (var1 < this.chatMessageList.size()) {
            ++((ChatLine)this.chatMessageList.get((int)var1)).updateCounter;
            ++var1;
        }
    }

    public void addChatMessageWithMoreOpacityBG(String var1) {
        while (this.mc.fontRenderer.getStringWidth(var1) > 320) {
            String lastFormatting = "";
            int var2 = 0;
            while (var2 < var1.length() && this.mc.fontRenderer.getStringWidth(var1.substring(0, var2 + 1)) <= 320) {
                if (var1.charAt(var2) == '\u00a7' && var2 + 1 < var1.length()) {
                    lastFormatting = "\u00a7" + var1.charAt(var2 + 1);
                }
                ++var2;
            }
            this.addChatMessageWithMoreOpacityBG(var1.substring(0, var2));
            var1 = String.valueOf(lastFormatting) + var1.substring(var2);
        }
        this.chatMessageList.add(0, new ChatLine(var1));
        this.chatMessagesBGs.add(0, true);
        while (this.chatMessageList.size() > 50) {
            this.chatMessageList.remove(this.chatMessageList.size() - 1);
            this.chatMessagesBGs.remove(this.chatMessagesBGs.size() - 1);
        }
    }

    public void addChatMessage(String var1) {
        while (this.mc.fontRenderer.getStringWidth(var1) > 320) {
            String lastFormatting = "";
            int var2 = 0;
            while (var2 < var1.length() && this.mc.fontRenderer.getStringWidth(var1.substring(0, var2 + 1)) <= 320) {
                if (var1.charAt(var2) == '\u00a7' && var2 + 1 < var1.length()) {
                    lastFormatting = "\u00a7" + var1.charAt(var2 + 1);
                }
                ++var2;
            }
            this.addChatMessage(var1.substring(0, var2));
            var1 = String.valueOf(lastFormatting) + var1.substring(var2);
        }
        this.chatMessageList.add(0, new ChatLine(var1));
        this.chatMessagesBGs.add(0, false);
        while (this.chatMessageList.size() > 50) {
            this.chatMessageList.remove(this.chatMessageList.size() - 1);
            this.chatMessagesBGs.remove(this.chatMessagesBGs.size() - 1);
        }
    }

    public void setRecordPlayingMessage(String var1) {
        this.recordPlaying = "Now playing: " + var1;
        this.recordPlayingUpFor = 60;
        this.field_22065_l = true;
    }

    public void func_22064_c(String var1) {
        StringTranslate var2 = StringTranslate.getInstance();
        String var3 = var2.translateKey(var1);
        this.addChatMessage(var3);
    }
}

