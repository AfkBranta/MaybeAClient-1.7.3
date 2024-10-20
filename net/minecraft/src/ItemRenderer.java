/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.src;

import net.minecraft.client.Minecraft;
import net.minecraft.src.Block;
import net.minecraft.src.EntityPlayerSP;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.MathHelper;
import net.minecraft.src.Render;
import net.minecraft.src.RenderBlocks;
import net.minecraft.src.RenderHelper;
import net.minecraft.src.RenderManager;
import net.minecraft.src.RenderPlayer;
import net.minecraft.src.Tessellator;
import org.lwjgl.opengl.GL11;

public class ItemRenderer {
    private Minecraft mc;
    private ItemStack itemToRender = null;
    private float equippedProgress = 0.0f;
    private float prevEquippedProgress = 0.0f;
    private RenderBlocks renderBlocksInstance = new RenderBlocks();
    private int field_20099_f = -1;

    public ItemRenderer(Minecraft var1) {
        this.mc = var1;
    }

    public void renderItem(ItemStack var1) {
        GL11.glPushMatrix();
        if (var1.itemID < 256 && RenderBlocks.renderItemIn3d(Block.blocksList[var1.itemID].getRenderType())) {
            GL11.glBindTexture((int)3553, (int)this.mc.renderEngine.getTexture("/terrain.png"));
            this.renderBlocksInstance.renderBlockOnInventory(Block.blocksList[var1.itemID], var1.getItemDamage());
        } else {
            float var15;
            float var14;
            float var13;
            if (var1.itemID < 256) {
                GL11.glBindTexture((int)3553, (int)this.mc.renderEngine.getTexture("/terrain.png"));
            } else {
                GL11.glBindTexture((int)3553, (int)this.mc.renderEngine.getTexture("/gui/items.png"));
            }
            Tessellator var2 = Tessellator.instance;
            float var3 = ((float)(var1.getIconIndex() % 16 * 16) + 0.0f) / 256.0f;
            float var4 = ((float)(var1.getIconIndex() % 16 * 16) + 15.99f) / 256.0f;
            float var5 = ((float)(var1.getIconIndex() / 16 * 16) + 0.0f) / 256.0f;
            float var6 = ((float)(var1.getIconIndex() / 16 * 16) + 15.99f) / 256.0f;
            float var7 = 1.0f;
            float var8 = 0.0f;
            float var9 = 0.3f;
            GL11.glEnable((int)32826);
            GL11.glTranslatef((float)(-var8), (float)(-var9), (float)0.0f);
            float var10 = 1.5f;
            GL11.glScalef((float)var10, (float)var10, (float)var10);
            GL11.glRotatef((float)50.0f, (float)0.0f, (float)1.0f, (float)0.0f);
            GL11.glRotatef((float)335.0f, (float)0.0f, (float)0.0f, (float)1.0f);
            GL11.glTranslatef((float)-0.9375f, (float)-0.0625f, (float)0.0f);
            float var11 = 0.0625f;
            var2.startDrawingQuads();
            var2.setNormal(0.0f, 0.0f, 1.0f);
            var2.addVertexWithUV(0.0, 0.0, 0.0, var4, var6);
            var2.addVertexWithUV(var7, 0.0, 0.0, var3, var6);
            var2.addVertexWithUV(var7, 1.0, 0.0, var3, var5);
            var2.addVertexWithUV(0.0, 1.0, 0.0, var4, var5);
            var2.draw();
            var2.startDrawingQuads();
            var2.setNormal(0.0f, 0.0f, -1.0f);
            var2.addVertexWithUV(0.0, 1.0, 0.0f - var11, var4, var5);
            var2.addVertexWithUV(var7, 1.0, 0.0f - var11, var3, var5);
            var2.addVertexWithUV(var7, 0.0, 0.0f - var11, var3, var6);
            var2.addVertexWithUV(0.0, 0.0, 0.0f - var11, var4, var6);
            var2.draw();
            var2.startDrawingQuads();
            var2.setNormal(-1.0f, 0.0f, 0.0f);
            int var12 = 0;
            while (var12 < 16) {
                var13 = (float)var12 / 16.0f;
                var14 = var4 + (var3 - var4) * var13 - 0.001953125f;
                var15 = var7 * var13;
                var2.addVertexWithUV(var15, 0.0, 0.0f - var11, var14, var6);
                var2.addVertexWithUV(var15, 0.0, 0.0, var14, var6);
                var2.addVertexWithUV(var15, 1.0, 0.0, var14, var5);
                var2.addVertexWithUV(var15, 1.0, 0.0f - var11, var14, var5);
                ++var12;
            }
            var2.draw();
            var2.startDrawingQuads();
            var2.setNormal(1.0f, 0.0f, 0.0f);
            var12 = 0;
            while (var12 < 16) {
                var13 = (float)var12 / 16.0f;
                var14 = var4 + (var3 - var4) * var13 - 0.001953125f;
                var15 = var7 * var13 + 0.0625f;
                var2.addVertexWithUV(var15, 1.0, 0.0f - var11, var14, var5);
                var2.addVertexWithUV(var15, 1.0, 0.0, var14, var5);
                var2.addVertexWithUV(var15, 0.0, 0.0, var14, var6);
                var2.addVertexWithUV(var15, 0.0, 0.0f - var11, var14, var6);
                ++var12;
            }
            var2.draw();
            var2.startDrawingQuads();
            var2.setNormal(0.0f, 1.0f, 0.0f);
            var12 = 0;
            while (var12 < 16) {
                var13 = (float)var12 / 16.0f;
                var14 = var6 + (var5 - var6) * var13 - 0.001953125f;
                var15 = var7 * var13 + 0.0625f;
                var2.addVertexWithUV(0.0, var15, 0.0, var4, var14);
                var2.addVertexWithUV(var7, var15, 0.0, var3, var14);
                var2.addVertexWithUV(var7, var15, 0.0f - var11, var3, var14);
                var2.addVertexWithUV(0.0, var15, 0.0f - var11, var4, var14);
                ++var12;
            }
            var2.draw();
            var2.startDrawingQuads();
            var2.setNormal(0.0f, -1.0f, 0.0f);
            var12 = 0;
            while (var12 < 16) {
                var13 = (float)var12 / 16.0f;
                var14 = var6 + (var5 - var6) * var13 - 0.001953125f;
                var15 = var7 * var13;
                var2.addVertexWithUV(var7, var15, 0.0, var3, var14);
                var2.addVertexWithUV(0.0, var15, 0.0, var4, var14);
                var2.addVertexWithUV(0.0, var15, 0.0f - var11, var4, var14);
                var2.addVertexWithUV(var7, var15, 0.0f - var11, var3, var14);
                ++var12;
            }
            var2.draw();
            GL11.glDisable((int)32826);
        }
        GL11.glPopMatrix();
    }

    public void renderItemInFirstPerson(float var1) {
        float var2 = this.prevEquippedProgress + (this.equippedProgress - this.prevEquippedProgress) * var1;
        EntityPlayerSP var3 = this.mc.thePlayer;
        GL11.glPushMatrix();
        GL11.glRotatef((float)(var3.prevRotationPitch + (var3.rotationPitch - var3.prevRotationPitch) * var1), (float)1.0f, (float)0.0f, (float)0.0f);
        GL11.glRotatef((float)(var3.prevRotationYaw + (var3.rotationYaw - var3.prevRotationYaw) * var1), (float)0.0f, (float)1.0f, (float)0.0f);
        RenderHelper.enableStandardItemLighting();
        GL11.glPopMatrix();
        float var4 = this.mc.theWorld.getLightBrightness(MathHelper.floor_double(var3.posX), MathHelper.floor_double(var3.posY), MathHelper.floor_double(var3.posZ));
        GL11.glColor4f((float)var4, (float)var4, (float)var4, (float)1.0f);
        ItemStack var5 = this.itemToRender;
        if (var3.fishEntity != null) {
            var5 = new ItemStack(Item.stick);
        }
        if (var5 != null) {
            GL11.glPushMatrix();
            float var6 = 0.8f;
            float var7 = var3.getSwingProgress(var1);
            float var8 = MathHelper.sin(var7 * (float)Math.PI);
            float var9 = MathHelper.sin(MathHelper.sqrt_float(var7) * (float)Math.PI);
            GL11.glTranslatef((float)(-var9 * 0.4f), (float)(MathHelper.sin(MathHelper.sqrt_float(var7) * (float)Math.PI * 2.0f) * 0.2f), (float)(-var8 * 0.2f));
            GL11.glTranslatef((float)(0.7f * var6), (float)(-0.65f * var6 - (1.0f - var2) * 0.6f), (float)(-0.9f * var6));
            GL11.glRotatef((float)45.0f, (float)0.0f, (float)1.0f, (float)0.0f);
            GL11.glEnable((int)32826);
            var7 = var3.getSwingProgress(var1);
            var8 = MathHelper.sin(var7 * var7 * (float)Math.PI);
            var9 = MathHelper.sin(MathHelper.sqrt_float(var7) * (float)Math.PI);
            GL11.glRotatef((float)(-var8 * 20.0f), (float)0.0f, (float)1.0f, (float)0.0f);
            GL11.glRotatef((float)(-var9 * 20.0f), (float)0.0f, (float)0.0f, (float)1.0f);
            GL11.glRotatef((float)(-var9 * 80.0f), (float)1.0f, (float)0.0f, (float)0.0f);
            var7 = 0.4f;
            GL11.glScalef((float)var7, (float)var7, (float)var7);
            if (var5.getItem().shouldRotateAroundWhenRendering()) {
                GL11.glRotatef((float)180.0f, (float)0.0f, (float)1.0f, (float)0.0f);
            }
            this.renderItem(var5);
            GL11.glPopMatrix();
        } else {
            GL11.glPushMatrix();
            float var6 = 0.8f;
            float var7 = var3.getSwingProgress(var1);
            float var8 = MathHelper.sin(var7 * (float)Math.PI);
            float var9 = MathHelper.sin(MathHelper.sqrt_float(var7) * (float)Math.PI);
            GL11.glTranslatef((float)(-var9 * 0.3f), (float)(MathHelper.sin(MathHelper.sqrt_float(var7) * (float)Math.PI * 2.0f) * 0.4f), (float)(-var8 * 0.4f));
            GL11.glTranslatef((float)(0.8f * var6), (float)(-0.75f * var6 - (1.0f - var2) * 0.6f), (float)(-0.9f * var6));
            GL11.glRotatef((float)45.0f, (float)0.0f, (float)1.0f, (float)0.0f);
            GL11.glEnable((int)32826);
            var7 = var3.getSwingProgress(var1);
            var8 = MathHelper.sin(var7 * var7 * (float)Math.PI);
            var9 = MathHelper.sin(MathHelper.sqrt_float(var7) * (float)Math.PI);
            GL11.glRotatef((float)(var9 * 70.0f), (float)0.0f, (float)1.0f, (float)0.0f);
            GL11.glRotatef((float)(-var8 * 20.0f), (float)0.0f, (float)0.0f, (float)1.0f);
            GL11.glBindTexture((int)3553, (int)this.mc.renderEngine.getTextureForDownloadableImage(this.mc.thePlayer.skinUrl, this.mc.thePlayer.getEntityTexture()));
            GL11.glTranslatef((float)-1.0f, (float)3.6f, (float)3.5f);
            GL11.glRotatef((float)120.0f, (float)0.0f, (float)0.0f, (float)1.0f);
            GL11.glRotatef((float)200.0f, (float)1.0f, (float)0.0f, (float)0.0f);
            GL11.glRotatef((float)-135.0f, (float)0.0f, (float)1.0f, (float)0.0f);
            GL11.glScalef((float)1.0f, (float)1.0f, (float)1.0f);
            GL11.glTranslatef((float)5.6f, (float)0.0f, (float)0.0f);
            Render var10 = RenderManager.instance.getEntityRenderObject(this.mc.thePlayer);
            RenderPlayer var11 = (RenderPlayer)var10;
            var9 = 1.0f;
            GL11.glScalef((float)var9, (float)var9, (float)var9);
            var11.drawFirstPersonHand();
            GL11.glPopMatrix();
        }
        GL11.glDisable((int)32826);
        RenderHelper.disableStandardItemLighting();
    }

    public void renderOverlays(float var1) {
        int var2;
        GL11.glDisable((int)3008);
        if (this.mc.thePlayer.isBurning()) {
            var2 = this.mc.renderEngine.getTexture("/terrain.png");
            GL11.glBindTexture((int)3553, (int)var2);
            this.renderFireInFirstPerson(var1);
        }
        if (this.mc.thePlayer.isEntityInsideOpaqueBlock()) {
            var2 = MathHelper.floor_double(this.mc.thePlayer.posX);
            int var3 = MathHelper.floor_double(this.mc.thePlayer.posY);
            int var4 = MathHelper.floor_double(this.mc.thePlayer.posZ);
            int var5 = this.mc.renderEngine.getTexture("/terrain.png");
            GL11.glBindTexture((int)3553, (int)var5);
            int var6 = this.mc.theWorld.getBlockId(var2, var3, var4);
            if (Block.blocksList[var6] != null) {
                this.renderInsideOfBlock(var1, Block.blocksList[var6].getBlockTextureFromSide(2));
            }
        }
        if (this.mc.thePlayer.isInsideOfMaterial(Material.water)) {
            var2 = this.mc.renderEngine.getTexture("/misc/water.png");
            GL11.glBindTexture((int)3553, (int)var2);
            this.renderWarpedTextureOverlay(var1);
        }
        GL11.glEnable((int)3008);
    }

    private void renderInsideOfBlock(float var1, int var2) {
        Tessellator var3 = Tessellator.instance;
        this.mc.thePlayer.getEntityBrightness(var1);
        float var4 = 0.1f;
        GL11.glColor4f((float)var4, (float)var4, (float)var4, (float)0.5f);
        GL11.glPushMatrix();
        float var5 = -1.0f;
        float var6 = 1.0f;
        float var7 = -1.0f;
        float var8 = 1.0f;
        float var9 = -0.5f;
        float var10 = 0.0078125f;
        float var11 = (float)(var2 % 16) / 256.0f - var10;
        float var12 = ((float)(var2 % 16) + 15.99f) / 256.0f + var10;
        float var13 = (float)(var2 / 16) / 256.0f - var10;
        float var14 = ((float)(var2 / 16) + 15.99f) / 256.0f + var10;
        var3.startDrawingQuads();
        var3.addVertexWithUV(var5, var7, var9, var12, var14);
        var3.addVertexWithUV(var6, var7, var9, var11, var14);
        var3.addVertexWithUV(var6, var8, var9, var11, var13);
        var3.addVertexWithUV(var5, var8, var9, var12, var13);
        var3.draw();
        GL11.glPopMatrix();
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
    }

    private void renderWarpedTextureOverlay(float var1) {
        Tessellator var2 = Tessellator.instance;
        float var3 = this.mc.thePlayer.getEntityBrightness(var1);
        GL11.glColor4f((float)var3, (float)var3, (float)var3, (float)0.5f);
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glPushMatrix();
        float var4 = 4.0f;
        float var5 = -1.0f;
        float var6 = 1.0f;
        float var7 = -1.0f;
        float var8 = 1.0f;
        float var9 = -0.5f;
        float var10 = -this.mc.thePlayer.rotationYaw / 64.0f;
        float var11 = this.mc.thePlayer.rotationPitch / 64.0f;
        var2.startDrawingQuads();
        var2.addVertexWithUV(var5, var7, var9, var4 + var10, var4 + var11);
        var2.addVertexWithUV(var6, var7, var9, 0.0f + var10, var4 + var11);
        var2.addVertexWithUV(var6, var8, var9, 0.0f + var10, 0.0f + var11);
        var2.addVertexWithUV(var5, var8, var9, var4 + var10, 0.0f + var11);
        var2.draw();
        GL11.glPopMatrix();
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glDisable((int)3042);
    }

    private void renderFireInFirstPerson(float var1) {
        Tessellator var2 = Tessellator.instance;
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)0.9f);
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        float var3 = 1.0f;
        int var4 = 0;
        while (var4 < 2) {
            GL11.glPushMatrix();
            int var5 = Block.fire.blockIndexInTexture + var4 * 16;
            int var6 = (var5 & 0xF) << 4;
            int var7 = var5 & 0xF0;
            float var8 = (float)var6 / 256.0f;
            float var9 = ((float)var6 + 15.99f) / 256.0f;
            float var10 = (float)var7 / 256.0f;
            float var11 = ((float)var7 + 15.99f) / 256.0f;
            float var12 = (0.0f - var3) / 2.0f;
            float var13 = var12 + var3;
            float var14 = 0.0f - var3 / 2.0f;
            float var15 = var14 + var3;
            float var16 = -0.5f;
            GL11.glTranslatef((float)((float)(-(var4 * 2 - 1)) * 0.24f), (float)-0.3f, (float)0.0f);
            GL11.glRotatef((float)((float)(var4 * 2 - 1) * 10.0f), (float)0.0f, (float)1.0f, (float)0.0f);
            var2.startDrawingQuads();
            var2.addVertexWithUV(var12, var14, var16, var9, var11);
            var2.addVertexWithUV(var13, var14, var16, var8, var11);
            var2.addVertexWithUV(var13, var15, var16, var8, var10);
            var2.addVertexWithUV(var12, var15, var16, var9, var10);
            var2.draw();
            GL11.glPopMatrix();
            ++var4;
        }
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glDisable((int)3042);
    }

    public void updateEquippedItem() {
        float var5;
        float var6;
        float var7;
        boolean var4;
        this.prevEquippedProgress = this.equippedProgress;
        EntityPlayerSP var1 = this.mc.thePlayer;
        ItemStack var2 = var1.inventory.getCurrentItem();
        boolean bl = var4 = this.field_20099_f == var1.inventory.currentItem && var2 == this.itemToRender;
        if (this.itemToRender == null && var2 == null) {
            var4 = true;
        }
        if (var2 != null && this.itemToRender != null && var2 != this.itemToRender && var2.itemID == this.itemToRender.itemID) {
            this.itemToRender = var2;
            var4 = true;
        }
        if ((var7 = (var6 = var4 ? 1.0f : 0.0f) - this.equippedProgress) < -(var5 = 0.4f)) {
            var7 = -var5;
        }
        if (var7 > var5) {
            var7 = var5;
        }
        this.equippedProgress += var7;
        if (this.equippedProgress < 0.1f) {
            this.itemToRender = var2;
            this.field_20099_f = var1.inventory.currentItem;
        }
    }

    public void func_9449_b() {
        this.equippedProgress = 0.0f;
    }

    public void func_9450_c() {
        this.equippedProgress = 0.0f;
    }
}

