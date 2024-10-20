/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.src;

import java.awt.image.BufferedImage;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Block;
import net.minecraft.src.Entity;
import net.minecraft.src.FontRenderer;
import net.minecraft.src.MathHelper;
import net.minecraft.src.ModelBase;
import net.minecraft.src.ModelBiped;
import net.minecraft.src.RenderBlocks;
import net.minecraft.src.RenderEngine;
import net.minecraft.src.RenderManager;
import net.minecraft.src.Tessellator;
import net.minecraft.src.World;
import net.skidcode.gh.maybeaclient.hacks.NameTagsHack;
import org.lwjgl.opengl.GL11;

public abstract class Render {
    protected RenderManager renderManager;
    private ModelBase modelBase = new ModelBiped();
    private RenderBlocks renderBlocks = new RenderBlocks();
    protected float shadowSize = 0.0f;
    protected float field_194_c = 1.0f;

    public abstract void doRender(Entity var1, double var2, double var4, double var6, float var8, float var9);

    public void renderLivingLabel(Entity var1, String var2, double var3, double var5, double var7, int var9) {
        float var10 = var1.getDistanceToEntity(this.renderManager.livingPlayer);
        if (var10 <= (float)var9 || NameTagsHack.instance.status) {
            float scale;
            FontRenderer fontRenderer = this.getFontRendererFromRenderManager();
            float var12 = 1.6f;
            float var13 = 0.016666668f * var12;
            if (NameTagsHack.instance.status && 0.016666668f * var12 > (var13 *= (scale = NameTagsHack.instance.scale.value) * 0.1f * var10)) {
                var13 = 0.016666668f * var12;
            }
            GL11.glPushMatrix();
            if (NameTagsHack.instance.status) {
                GL11.glDisable((int)2912);
            }
            GL11.glTranslated((double)var3, (double)(var5 + 2.3), (double)var7);
            GL11.glNormal3f((float)0.0f, (float)1.0f, (float)0.0f);
            GL11.glRotatef((float)(-this.renderManager.playerViewY), (float)0.0f, (float)1.0f, (float)0.0f);
            GL11.glRotatef((float)this.renderManager.playerViewX, (float)1.0f, (float)0.0f, (float)0.0f);
            GL11.glScalef((float)(-var13), (float)(-var13), (float)var13);
            GL11.glDisable((int)2896);
            GL11.glDepthMask((boolean)false);
            GL11.glDisable((int)2929);
            GL11.glEnable((int)3042);
            GL11.glBlendFunc((int)770, (int)771);
            Tessellator var14 = Tessellator.instance;
            int var15 = 0;
            if (var2.equals("deadmau5")) {
                var15 = -10;
            }
            GL11.glDisable((int)3553);
            var14.startDrawingQuads();
            int var16 = fontRenderer.getStringWidth(var2) / 2;
            var14.setColorRGBA_F(0.0f, 0.0f, 0.0f, 0.25f);
            var14.addVertex(-var16 - 1, -1 + var15, 0.0);
            var14.addVertex(-var16 - 1, 8 + var15, 0.0);
            var14.addVertex(var16 + 1, 8 + var15, 0.0);
            var14.addVertex(var16 + 1, -1 + var15, 0.0);
            var14.draw();
            GL11.glEnable((int)3553);
            fontRenderer.drawString(var2, -fontRenderer.getStringWidth(var2) / 2, var15, 0x20FFFFFF);
            if (!NameTagsHack.instance.status) {
                GL11.glEnable((int)2929);
            }
            GL11.glDepthMask((boolean)true);
            fontRenderer.drawString(var2, -fontRenderer.getStringWidth(var2) / 2, var15, -1);
            GL11.glEnable((int)2896);
            GL11.glDisable((int)3042);
            if (NameTagsHack.instance.status) {
                GL11.glEnable((int)2912);
            }
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            if (NameTagsHack.instance.status) {
                GL11.glEnable((int)2929);
            }
            GL11.glPopMatrix();
        }
    }

    protected void loadTexture(String var1) {
        RenderEngine var2 = this.renderManager.renderEngine;
        var2.bindTexture(var2.getTexture(var1));
    }

    protected void loadTexture(String key, BufferedImage img) {
        RenderEngine var2 = this.renderManager.renderEngine;
        var2.bindTexture(var2.putTexture(key, img));
    }

    protected boolean loadDownloadableImageTexture(String var1, String var2) {
        RenderEngine var3 = this.renderManager.renderEngine;
        int var4 = var3.getTextureForDownloadableImage(var1, var2);
        if (var4 >= 0) {
            var3.bindTexture(var4);
            return true;
        }
        return false;
    }

    private void renderEntityOnFire(Entity var1, double var2, double var4, double var6, float var8) {
        GL11.glDisable((int)2896);
        int var9 = Block.fire.blockIndexInTexture;
        int var10 = (var9 & 0xF) << 4;
        int var11 = var9 & 0xF0;
        float var12 = (float)var10 / 256.0f;
        float var13 = ((float)var10 + 15.99f) / 256.0f;
        float var14 = (float)var11 / 256.0f;
        float var15 = ((float)var11 + 15.99f) / 256.0f;
        GL11.glPushMatrix();
        GL11.glTranslatef((float)((float)var2), (float)((float)var4), (float)((float)var6));
        float var16 = var1.width * 1.4f;
        GL11.glScalef((float)var16, (float)var16, (float)var16);
        this.loadTexture("/terrain.png");
        Tessellator var17 = Tessellator.instance;
        float var18 = 1.0f;
        float var19 = 0.5f;
        float var20 = 0.0f;
        float var21 = var1.height / var1.width;
        GL11.glRotatef((float)(-this.renderManager.playerViewY), (float)0.0f, (float)1.0f, (float)0.0f);
        GL11.glTranslatef((float)0.0f, (float)0.0f, (float)(-0.4f + (float)((int)var21) * 0.02f));
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        var17.startDrawingQuads();
        while (var21 > 0.0f) {
            var17.addVertexWithUV(var18 - var19, 0.0f - var20, 0.0, var13, var15);
            var17.addVertexWithUV(0.0f - var19, 0.0f - var20, 0.0, var12, var15);
            var17.addVertexWithUV(0.0f - var19, 1.4f - var20, 0.0, var12, var14);
            var17.addVertexWithUV(var18 - var19, 1.4f - var20, 0.0, var13, var14);
            var21 -= 1.0f;
            var20 -= 1.0f;
            var18 *= 0.9f;
            GL11.glTranslatef((float)0.0f, (float)0.0f, (float)-0.04f);
        }
        var17.draw();
        GL11.glPopMatrix();
        GL11.glEnable((int)2896);
    }

    private void renderShadow(Entity var1, double var2, double var4, double var6, float var8, float var9) {
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        RenderEngine var10 = this.renderManager.renderEngine;
        var10.bindTexture(var10.getTexture("%clamp%/misc/shadow.png"));
        World var11 = this.getWorldFromRenderManager();
        GL11.glDepthMask((boolean)false);
        float var12 = this.shadowSize;
        double var13 = var1.lastTickPosX + (var1.posX - var1.lastTickPosX) * (double)var9;
        double var15 = var1.lastTickPosY + (var1.posY - var1.lastTickPosY) * (double)var9 + (double)var1.getShadowSize();
        double var17 = var1.lastTickPosZ + (var1.posZ - var1.lastTickPosZ) * (double)var9;
        int var19 = MathHelper.floor_double(var13 - (double)var12);
        int var20 = MathHelper.floor_double(var13 + (double)var12);
        int var21 = MathHelper.floor_double(var15 - (double)var12);
        int var22 = MathHelper.floor_double(var15);
        int var23 = MathHelper.floor_double(var17 - (double)var12);
        int var24 = MathHelper.floor_double(var17 + (double)var12);
        double var25 = var2 - var13;
        double var27 = var4 - var15;
        double var29 = var6 - var17;
        Tessellator var31 = Tessellator.instance;
        var31.startDrawingQuads();
        int var32 = var19;
        while (var32 <= var20) {
            int var33 = var21;
            while (var33 <= var22) {
                int var34 = var23;
                while (var34 <= var24) {
                    int var35 = var11.getBlockId(var32, var33 - 1, var34);
                    if (var35 > 0 && var11.getBlockLightValue(var32, var33, var34) > 3) {
                        this.renderShadowOnBlock(Block.blocksList[var35], var2, var4 + (double)var1.getShadowSize(), var6, var32, var33, var34, var8, var12, var25, var27 + (double)var1.getShadowSize(), var29);
                    }
                    ++var34;
                }
                ++var33;
            }
            ++var32;
        }
        var31.draw();
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glDisable((int)3042);
        GL11.glDepthMask((boolean)true);
    }

    private World getWorldFromRenderManager() {
        return this.renderManager.worldObj;
    }

    private void renderShadowOnBlock(Block var1, double var2, double var4, double var6, int var8, int var9, int var10, float var11, float var12, double var13, double var15, double var17) {
        double var20;
        Tessellator var19 = Tessellator.instance;
        if (var1.renderAsNormalBlock() && (var20 = ((double)var11 - (var4 - ((double)var9 + var15)) / 2.0) * 0.5 * (double)this.getWorldFromRenderManager().getLightBrightness(var8, var9, var10)) >= 0.0) {
            if (var20 > 1.0) {
                var20 = 1.0;
            }
            var19.setColorRGBA_F(1.0f, 1.0f, 1.0f, (float)var20);
            double var22 = (double)var8 + var1.minX + var13;
            double var24 = (double)var8 + var1.maxX + var13;
            double var26 = (double)var9 + var1.minY + var15 + 0.015625;
            double var28 = (double)var10 + var1.minZ + var17;
            double var30 = (double)var10 + var1.maxZ + var17;
            float var32 = (float)((var2 - var22) / 2.0 / (double)var12 + 0.5);
            float var33 = (float)((var2 - var24) / 2.0 / (double)var12 + 0.5);
            float var34 = (float)((var6 - var28) / 2.0 / (double)var12 + 0.5);
            float var35 = (float)((var6 - var30) / 2.0 / (double)var12 + 0.5);
            var19.addVertexWithUV(var22, var26, var28, var32, var34);
            var19.addVertexWithUV(var22, var26, var30, var32, var35);
            var19.addVertexWithUV(var24, var26, var30, var33, var35);
            var19.addVertexWithUV(var24, var26, var28, var33, var34);
        }
    }

    public static void renderOffsetAABB(AxisAlignedBB var0, double var1, double var3, double var5) {
        GL11.glDisable((int)3553);
        Tessellator var7 = Tessellator.instance;
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        var7.startDrawingQuads();
        var7.setTranslationD(var1, var3, var5);
        var7.setNormal(0.0f, 0.0f, -1.0f);
        var7.addVertex(var0.minX, var0.maxY, var0.minZ);
        var7.addVertex(var0.maxX, var0.maxY, var0.minZ);
        var7.addVertex(var0.maxX, var0.minY, var0.minZ);
        var7.addVertex(var0.minX, var0.minY, var0.minZ);
        var7.setNormal(0.0f, 0.0f, 1.0f);
        var7.addVertex(var0.minX, var0.minY, var0.maxZ);
        var7.addVertex(var0.maxX, var0.minY, var0.maxZ);
        var7.addVertex(var0.maxX, var0.maxY, var0.maxZ);
        var7.addVertex(var0.minX, var0.maxY, var0.maxZ);
        var7.setNormal(0.0f, -1.0f, 0.0f);
        var7.addVertex(var0.minX, var0.minY, var0.minZ);
        var7.addVertex(var0.maxX, var0.minY, var0.minZ);
        var7.addVertex(var0.maxX, var0.minY, var0.maxZ);
        var7.addVertex(var0.minX, var0.minY, var0.maxZ);
        var7.setNormal(0.0f, 1.0f, 0.0f);
        var7.addVertex(var0.minX, var0.maxY, var0.maxZ);
        var7.addVertex(var0.maxX, var0.maxY, var0.maxZ);
        var7.addVertex(var0.maxX, var0.maxY, var0.minZ);
        var7.addVertex(var0.minX, var0.maxY, var0.minZ);
        var7.setNormal(-1.0f, 0.0f, 0.0f);
        var7.addVertex(var0.minX, var0.minY, var0.maxZ);
        var7.addVertex(var0.minX, var0.maxY, var0.maxZ);
        var7.addVertex(var0.minX, var0.maxY, var0.minZ);
        var7.addVertex(var0.minX, var0.minY, var0.minZ);
        var7.setNormal(1.0f, 0.0f, 0.0f);
        var7.addVertex(var0.maxX, var0.minY, var0.minZ);
        var7.addVertex(var0.maxX, var0.maxY, var0.minZ);
        var7.addVertex(var0.maxX, var0.maxY, var0.maxZ);
        var7.addVertex(var0.maxX, var0.minY, var0.maxZ);
        var7.setTranslationD(0.0, 0.0, 0.0);
        var7.draw();
        GL11.glEnable((int)3553);
    }

    public static void renderAABB(AxisAlignedBB var0) {
        Tessellator var1 = Tessellator.instance;
        var1.startDrawingQuads();
        var1.addVertex(var0.minX, var0.maxY, var0.minZ);
        var1.addVertex(var0.maxX, var0.maxY, var0.minZ);
        var1.addVertex(var0.maxX, var0.minY, var0.minZ);
        var1.addVertex(var0.minX, var0.minY, var0.minZ);
        var1.addVertex(var0.minX, var0.minY, var0.maxZ);
        var1.addVertex(var0.maxX, var0.minY, var0.maxZ);
        var1.addVertex(var0.maxX, var0.maxY, var0.maxZ);
        var1.addVertex(var0.minX, var0.maxY, var0.maxZ);
        var1.addVertex(var0.minX, var0.minY, var0.minZ);
        var1.addVertex(var0.maxX, var0.minY, var0.minZ);
        var1.addVertex(var0.maxX, var0.minY, var0.maxZ);
        var1.addVertex(var0.minX, var0.minY, var0.maxZ);
        var1.addVertex(var0.minX, var0.maxY, var0.maxZ);
        var1.addVertex(var0.maxX, var0.maxY, var0.maxZ);
        var1.addVertex(var0.maxX, var0.maxY, var0.minZ);
        var1.addVertex(var0.minX, var0.maxY, var0.minZ);
        var1.addVertex(var0.minX, var0.minY, var0.maxZ);
        var1.addVertex(var0.minX, var0.maxY, var0.maxZ);
        var1.addVertex(var0.minX, var0.maxY, var0.minZ);
        var1.addVertex(var0.minX, var0.minY, var0.minZ);
        var1.addVertex(var0.maxX, var0.minY, var0.minZ);
        var1.addVertex(var0.maxX, var0.maxY, var0.minZ);
        var1.addVertex(var0.maxX, var0.maxY, var0.maxZ);
        var1.addVertex(var0.maxX, var0.minY, var0.maxZ);
        var1.draw();
    }

    public void setRenderManager(RenderManager var1) {
        this.renderManager = var1;
    }

    public void doRenderShadowAndFire(Entity var1, double var2, double var4, double var6, float var8, float var9) {
        double var10;
        float var12;
        if (this.renderManager == null || this.renderManager.options == null) {
            return;
        }
        if (this.renderManager.options.fancyGraphics && this.shadowSize > 0.0f && (var12 = (float)((1.0 - (var10 = this.renderManager.func_851_a(var1.posX, var1.posY, var1.posZ)) / 256.0) * (double)this.field_194_c)) > 0.0f) {
            this.renderShadow(var1, var2, var4, var6, var12, var9);
        }
        if (var1.isBurning()) {
            this.renderEntityOnFire(var1, var2, var4, var6, var9);
        }
    }

    public FontRenderer getFontRendererFromRenderManager() {
        return this.renderManager.getFontRenderer();
    }
}

