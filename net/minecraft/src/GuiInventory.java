/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.src;

import net.minecraft.src.AchievementList;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiContainer;
import net.minecraft.src.RenderHelper;
import net.minecraft.src.RenderManager;
import org.lwjgl.opengl.GL11;

public class GuiInventory
extends GuiContainer {
    private float xSize_lo;
    private float ySize_lo;

    public GuiInventory(EntityPlayer var1) {
        super(var1.inventorySlots);
        this.field_948_f = true;
        var1.addStat(AchievementList.field_25195_b, 1);
    }

    @Override
    public void initGui() {
        this.controlList.clear();
    }

    @Override
    protected void drawGuiContainerForegroundLayer() {
        this.fontRenderer.drawString("Crafting", 86, 16, 0x404040);
    }

    @Override
    public void drawScreen(int var1, int var2, float var3) {
        super.drawScreen(var1, var2, var3);
        this.xSize_lo = var1;
        this.ySize_lo = var2;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float var1) {
        int var2 = this.mc.renderEngine.getTexture("/gui/inventory.png");
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        this.mc.renderEngine.bindTexture(var2);
        int centerX = (this.width - this.xSize) / 2;
        int centerY = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(centerX, centerY, 0, 0, this.xSize, this.ySize);
        GL11.glEnable((int)32826);
        GL11.glEnable((int)2903);
        GL11.glPushMatrix();
        GL11.glTranslatef((float)(centerX + 51), (float)(centerY + 75), (float)50.0f);
        float var5 = 30.0f;
        GL11.glScalef((float)(-var5), (float)var5, (float)var5);
        GL11.glRotatef((float)180.0f, (float)0.0f, (float)0.0f, (float)1.0f);
        float var6 = this.mc.thePlayer.renderYawOffset;
        float var7 = this.mc.thePlayer.rotationYaw;
        float var8 = this.mc.thePlayer.rotationPitch;
        float var9 = (float)(centerX + 51) - this.xSize_lo;
        float var10 = (float)(centerY + 75 - 50) - this.ySize_lo;
        GL11.glRotatef((float)135.0f, (float)0.0f, (float)1.0f, (float)0.0f);
        RenderHelper.enableStandardItemLighting();
        GL11.glRotatef((float)-135.0f, (float)0.0f, (float)1.0f, (float)0.0f);
        GL11.glRotatef((float)(-((float)Math.atan(var10 / 40.0f)) * 20.0f), (float)1.0f, (float)0.0f, (float)0.0f);
        this.mc.thePlayer.renderYawOffset = (float)Math.atan(var9 / 40.0f) * 20.0f;
        this.mc.thePlayer.rotationYaw = (float)Math.atan(var9 / 40.0f) * 40.0f;
        this.mc.thePlayer.rotationPitch = -((float)Math.atan(var10 / 40.0f)) * 20.0f;
        GL11.glTranslatef((float)0.0f, (float)this.mc.thePlayer.yOffset, (float)0.0f);
        RenderManager.instance.renderEntityWithPosYaw(this.mc.thePlayer, 0.0, 0.0, 0.0, 0.0f, 1.0f);
        this.mc.thePlayer.renderYawOffset = var6;
        this.mc.thePlayer.rotationYaw = var7;
        this.mc.thePlayer.rotationPitch = var8;
        GL11.glPopMatrix();
        RenderHelper.disableStandardItemLighting();
        GL11.glDisable((int)32826);
    }

    @Override
    protected void actionPerformed(GuiButton var1) {
    }
}

