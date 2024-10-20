/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.src;

import java.util.Random;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Block;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityItem;
import net.minecraft.src.FontRenderer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.MathHelper;
import net.minecraft.src.Render;
import net.minecraft.src.RenderBlocks;
import net.minecraft.src.RenderEngine;
import net.minecraft.src.Tessellator;
import net.skidcode.gh.maybeaclient.hacks.EntityESPHack;
import net.skidcode.gh.maybeaclient.hacks.ItemNameTagsHack;
import net.skidcode.gh.maybeaclient.utils.RenderUtils;
import org.lwjgl.opengl.GL11;

public class RenderItem
extends Render {
    private RenderBlocks renderBlocks = new RenderBlocks();
    private Random random = new Random();

    public RenderItem() {
        this.shadowSize = 0.15f;
        this.field_194_c = 0.75f;
    }

    public void doRenderItem(EntityItem entity, double var2, double var4, double var6, float var8, float var9) {
        this.random.setSeed(187L);
        if (ItemNameTagsHack.instance.status) {
            this.renderLivingLabel(entity, ItemNameTagsHack.getName(entity), var2, var4 - 1.3, var6, 64);
        }
        ItemStack var10 = entity.item;
        GL11.glPushMatrix();
        float var11 = MathHelper.sin(((float)entity.age + var9) / 10.0f + entity.field_804_d) * 0.1f + 0.1f;
        float var12 = (((float)entity.age + var9) / 20.0f + entity.field_804_d) * 57.295776f;
        int var13 = 1;
        if (entity.item.stackSize > 1) {
            var13 = 2;
        }
        if (entity.item.stackSize > 5) {
            var13 = 3;
        }
        if (entity.item.stackSize > 20) {
            var13 = 4;
        }
        if (EntityESPHack.instance.shouldRender(entity) && EntityESPHack.instance.getRenderingMode(entity).equalsIgnoreCase("Box")) {
            int r = EntityESPHack.instance.itemColor.red;
            int g = EntityESPHack.instance.itemColor.green;
            int b = EntityESPHack.instance.itemColor.blue;
            GL11.glEnable((int)3042);
            GL11.glBlendFunc((int)770, (int)771);
            GL11.glLineWidth((float)1.0f);
            GL11.glDisable((int)3553);
            GL11.glDepthMask((boolean)false);
            GL11.glEnable((int)2848);
            GL11.glBlendFunc((int)770, (int)771);
            GL11.glDisable((int)3553);
            GL11.glDisable((int)2929);
            GL11.glDepthMask((boolean)false);
            GL11.glEnable((int)2848);
            GL11.glDisable((int)2896);
            GL11.glColor3f((float)r, (float)g, (float)b);
            RenderUtils.drawOutlinedBB(AxisAlignedBB.getBoundingBox(var2 - (double)(entity.width / 2.0f), var4, var6 - (double)(entity.width / 2.0f), var2 + (double)(entity.width / 2.0f), var4 + (double)entity.height, var6 + (double)(entity.width / 2.0f)));
            GL11.glColor4f((float)0.0f, (float)230.0f, (float)255.0f, (float)0.3f);
            GL11.glDepthMask((boolean)true);
            GL11.glEnable((int)2896);
            GL11.glEnable((int)3553);
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            GL11.glEnable((int)2929);
        }
        GL11.glTranslatef((float)((float)var2), (float)((float)var4 + var11), (float)((float)var6));
        GL11.glEnable((int)32826);
        if (var10.itemID < 256 && RenderBlocks.renderItemIn3d(Block.blocksList[var10.itemID].getRenderType())) {
            GL11.glRotatef((float)var12, (float)0.0f, (float)1.0f, (float)0.0f);
            this.loadTexture("/terrain.png");
            float var27 = 0.25f;
            if (!Block.blocksList[var10.itemID].renderAsNormalBlock() && var10.itemID != Block.stairSingle.blockID) {
                var27 = 0.5f;
            }
            GL11.glScalef((float)var27, (float)var27, (float)var27);
            if (EntityESPHack.instance.shouldRender(entity) && EntityESPHack.instance.getRenderingMode(entity).equalsIgnoreCase("Fill")) {
                int r = EntityESPHack.instance.itemColor.red;
                int g = EntityESPHack.instance.itemColor.green;
                int b = EntityESPHack.instance.itemColor.blue;
                GL11.glEnable((int)2960);
                int var28 = 0;
                while (var28 < var13) {
                    GL11.glPushMatrix();
                    GL11.glClear((int)1024);
                    GL11.glClearStencil((int)15);
                    GL11.glStencilFunc((int)512, (int)1, (int)15);
                    GL11.glStencilOp((int)7681, (int)7681, (int)7681);
                    if (var28 > 0) {
                        float var16 = (this.random.nextFloat() * 2.0f - 1.0f) * 0.2f / var27;
                        float var17 = (this.random.nextFloat() * 2.0f - 1.0f) * 0.2f / var27;
                        float var18 = (this.random.nextFloat() * 2.0f - 1.0f) * 0.2f / var27;
                        GL11.glTranslatef((float)var16, (float)var17, (float)var18);
                    }
                    this.renderBlocks.renderBlockOnInventory(Block.blocksList[var10.itemID], var10.getItemDamage());
                    GL11.glStencilFunc((int)514, (int)1, (int)15);
                    GL11.glStencilOp((int)7681, (int)7681, (int)7680);
                    GL11.glPushMatrix();
                    GL11.glPushAttrib((int)1048575);
                    GL11.glDisable((int)3553);
                    GL11.glDisable((int)2896);
                    GL11.glDisable((int)2929);
                    GL11.glDisable((int)3008);
                    GL11.glEnable((int)3042);
                    GL11.glDisable((int)2912);
                    GL11.glBlendFunc((int)770, (int)32772);
                    GL11.glColor4f((float)((float)r / 255.0f), (float)((float)g / 255.0f), (float)((float)b / 255.0f), (float)1.0f);
                    this.renderBlocks.renderBlockOnInventory(Block.blocksList[var10.itemID], var10.getItemDamage());
                    GL11.glPopAttrib();
                    GL11.glPopMatrix();
                    GL11.glPopMatrix();
                    ++var28;
                }
                GL11.glDisable((int)2960);
            } else {
                int var28 = 0;
                while (var28 < var13) {
                    GL11.glPushMatrix();
                    if (var28 > 0) {
                        float var16 = (this.random.nextFloat() * 2.0f - 1.0f) * 0.2f / var27;
                        float var17 = (this.random.nextFloat() * 2.0f - 1.0f) * 0.2f / var27;
                        float var18 = (this.random.nextFloat() * 2.0f - 1.0f) * 0.2f / var27;
                        GL11.glTranslatef((float)var16, (float)var17, (float)var18);
                    }
                    this.renderBlocks.renderBlockOnInventory(Block.blocksList[var10.itemID], var10.getItemDamage());
                    GL11.glPopMatrix();
                    ++var28;
                }
            }
        } else {
            GL11.glScalef((float)0.5f, (float)0.5f, (float)0.5f);
            int var14 = var10.getIconIndex();
            if (var10.itemID < 256) {
                this.loadTexture("/terrain.png");
            } else {
                this.loadTexture("/gui/items.png");
            }
            Tessellator var15 = Tessellator.instance;
            float var16 = (float)(var14 % 16 * 16 + 0) / 256.0f;
            float var17 = (float)(var14 % 16 * 16 + 16) / 256.0f;
            float var18 = (float)(var14 / 16 * 16 + 0) / 256.0f;
            float var19 = (float)(var14 / 16 * 16 + 16) / 256.0f;
            float var20 = 1.0f;
            float var21 = 0.5f;
            float var22 = 0.25f;
            if (EntityESPHack.instance.shouldRender(entity) && EntityESPHack.instance.getRenderingMode(entity).equalsIgnoreCase("Fill")) {
                int r = EntityESPHack.instance.itemColor.red;
                int g = EntityESPHack.instance.itemColor.green;
                int b = EntityESPHack.instance.itemColor.blue;
                GL11.glEnable((int)2960);
                int var23 = 0;
                while (var23 < var13) {
                    GL11.glPushMatrix();
                    GL11.glClear((int)1024);
                    GL11.glClearStencil((int)15);
                    GL11.glStencilFunc((int)512, (int)1, (int)15);
                    GL11.glStencilOp((int)7681, (int)7681, (int)7681);
                    if (var23 > 0) {
                        float var24 = (this.random.nextFloat() * 2.0f - 1.0f) * 0.3f;
                        float var25 = (this.random.nextFloat() * 2.0f - 1.0f) * 0.3f;
                        float var26 = (this.random.nextFloat() * 2.0f - 1.0f) * 0.3f;
                        GL11.glTranslatef((float)var24, (float)var25, (float)var26);
                    }
                    GL11.glRotatef((float)(180.0f - this.renderManager.playerViewY), (float)0.0f, (float)1.0f, (float)0.0f);
                    var15.startDrawingQuads();
                    var15.setNormal(0.0f, 1.0f, 0.0f);
                    var15.addVertexWithUV(0.0f - var21, 0.0f - var22, 0.0, var16, var19);
                    var15.addVertexWithUV(var20 - var21, 0.0f - var22, 0.0, var17, var19);
                    var15.addVertexWithUV(var20 - var21, 1.0f - var22, 0.0, var17, var18);
                    var15.addVertexWithUV(0.0f - var21, 1.0f - var22, 0.0, var16, var18);
                    var15.draw();
                    GL11.glStencilFunc((int)514, (int)1, (int)15);
                    GL11.glStencilOp((int)7681, (int)7681, (int)7680);
                    GL11.glPushMatrix();
                    GL11.glPushAttrib((int)1048575);
                    GL11.glDisable((int)3553);
                    GL11.glDisable((int)2896);
                    GL11.glDisable((int)2929);
                    GL11.glDisable((int)3008);
                    GL11.glEnable((int)3042);
                    GL11.glDisable((int)2912);
                    GL11.glBlendFunc((int)770, (int)32772);
                    GL11.glColor4f((float)((float)r / 255.0f), (float)((float)g / 255.0f), (float)((float)b / 255.0f), (float)1.0f);
                    var15.startDrawingQuads();
                    var15.setNormal(0.0f, 1.0f, 0.0f);
                    var15.addVertexWithUV(0.0f - var21, 0.0f - var22, 0.0, var16, var19);
                    var15.addVertexWithUV(var20 - var21, 0.0f - var22, 0.0, var17, var19);
                    var15.addVertexWithUV(var20 - var21, 1.0f - var22, 0.0, var17, var18);
                    var15.addVertexWithUV(0.0f - var21, 1.0f - var22, 0.0, var16, var18);
                    var15.draw();
                    GL11.glPopAttrib();
                    GL11.glPopMatrix();
                    GL11.glPopMatrix();
                    ++var23;
                }
                GL11.glDisable((int)2960);
            } else {
                int var23 = 0;
                while (var23 < var13) {
                    GL11.glPushMatrix();
                    if (var23 > 0) {
                        float var24 = (this.random.nextFloat() * 2.0f - 1.0f) * 0.3f;
                        float var25 = (this.random.nextFloat() * 2.0f - 1.0f) * 0.3f;
                        float var26 = (this.random.nextFloat() * 2.0f - 1.0f) * 0.3f;
                        GL11.glTranslatef((float)var24, (float)var25, (float)var26);
                    }
                    GL11.glRotatef((float)(180.0f - this.renderManager.playerViewY), (float)0.0f, (float)1.0f, (float)0.0f);
                    var15.startDrawingQuads();
                    var15.setNormal(0.0f, 1.0f, 0.0f);
                    var15.addVertexWithUV(0.0f - var21, 0.0f - var22, 0.0, var16, var19);
                    var15.addVertexWithUV(var20 - var21, 0.0f - var22, 0.0, var17, var19);
                    var15.addVertexWithUV(var20 - var21, 1.0f - var22, 0.0, var17, var18);
                    var15.addVertexWithUV(0.0f - var21, 1.0f - var22, 0.0, var16, var18);
                    var15.draw();
                    GL11.glPopMatrix();
                    ++var23;
                }
            }
        }
        GL11.glDisable((int)32826);
        GL11.glPopMatrix();
    }

    public void renderItemIntoGUI(FontRenderer var1, RenderEngine var2, ItemStack var3, int var4, int var5) {
        if (var3 != null) {
            if (var3.itemID < 256 && RenderBlocks.renderItemIn3d(Block.blocksList[var3.itemID].getRenderType())) {
                int var6 = var3.itemID;
                var2.bindTexture(var2.getTexture("/terrain.png"));
                Block var7 = Block.blocksList[var6];
                GL11.glPushMatrix();
                GL11.glTranslatef((float)(var4 - 2), (float)(var5 + 3), (float)0.0f);
                GL11.glScalef((float)10.0f, (float)10.0f, (float)10.0f);
                GL11.glTranslatef((float)1.0f, (float)0.5f, (float)8.0f);
                GL11.glRotatef((float)210.0f, (float)1.0f, (float)0.0f, (float)0.0f);
                GL11.glRotatef((float)45.0f, (float)0.0f, (float)1.0f, (float)0.0f);
                GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                GL11.glScalef((float)1.0f, (float)1.0f, (float)1.0f);
                this.renderBlocks.renderBlockOnInventory(var7, var3.getItemDamage());
                GL11.glPopMatrix();
            } else if (var3.getIconIndex() >= 0) {
                GL11.glDisable((int)2896);
                if (var3.itemID < 256) {
                    var2.bindTexture(var2.getTexture("/terrain.png"));
                } else {
                    var2.bindTexture(var2.getTexture("/gui/items.png"));
                }
                this.renderTexturedQuad(var4, var5, var3.getIconIndex() % 16 * 16, var3.getIconIndex() / 16 * 16, 16, 16);
                GL11.glEnable((int)2896);
            }
            GL11.glEnable((int)2884);
        }
    }

    public void renderItemOverlayIntoGUI(FontRenderer var1, RenderEngine var2, ItemStack var3, int var4, int var5) {
        if (var3 != null) {
            if (var3.stackSize > 1 || var3.stackSize < 1) {
                String var6 = "" + var3.stackSize;
                GL11.glDisable((int)2896);
                GL11.glDisable((int)2929);
                var1.drawStringWithShadow(var6, var4 + 19 - 2 - var1.getStringWidth(var6), var5 + 6 + 3, 0xFFFFFF);
                GL11.glEnable((int)2896);
                GL11.glEnable((int)2929);
            }
            if (var3.isItemDamaged()) {
                int var11 = (int)Math.round(13.0 - (double)var3.getItemDamageForDisplay() * 13.0 / (double)var3.getMaxDamage());
                int var7 = (int)Math.round(255.0 - (double)var3.getItemDamageForDisplay() * 255.0 / (double)var3.getMaxDamage());
                GL11.glDisable((int)2896);
                GL11.glDisable((int)2929);
                GL11.glDisable((int)3553);
                Tessellator var8 = Tessellator.instance;
                int var9 = 255 - var7 << 16 | var7 << 8;
                int var10 = (255 - var7) / 4 << 16 | 0x3F00;
                this.renderQuad(var8, var4 + 2, var5 + 13, 13, 2, 0);
                this.renderQuad(var8, var4 + 2, var5 + 13, 12, 1, var10);
                this.renderQuad(var8, var4 + 2, var5 + 13, var11, 1, var9);
                GL11.glEnable((int)3553);
                GL11.glEnable((int)2896);
                GL11.glEnable((int)2929);
                GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            }
        }
    }

    private void renderQuad(Tessellator var1, int var2, int var3, int var4, int var5, int var6) {
        var1.startDrawingQuads();
        var1.setColorOpaque_I(var6);
        var1.addVertex(var2 + 0, var3 + 0, 0.0);
        var1.addVertex(var2 + 0, var3 + var5, 0.0);
        var1.addVertex(var2 + var4, var3 + var5, 0.0);
        var1.addVertex(var2 + var4, var3 + 0, 0.0);
        var1.draw();
    }

    public void renderTexturedQuad(int var1, int var2, int var3, int var4, int var5, int var6) {
        float var7 = 0.0f;
        float var8 = 0.00390625f;
        float var9 = 0.00390625f;
        Tessellator var10 = Tessellator.instance;
        var10.startDrawingQuads();
        var10.addVertexWithUV(var1 + 0, var2 + var6, var7, (float)(var3 + 0) * var8, (float)(var4 + var6) * var9);
        var10.addVertexWithUV(var1 + var5, var2 + var6, var7, (float)(var3 + var5) * var8, (float)(var4 + var6) * var9);
        var10.addVertexWithUV(var1 + var5, var2 + 0, var7, (float)(var3 + var5) * var8, (float)(var4 + 0) * var9);
        var10.addVertexWithUV(var1 + 0, var2 + 0, var7, (float)(var3 + 0) * var8, (float)(var4 + 0) * var9);
        var10.draw();
    }

    @Override
    public void doRender(Entity var1, double var2, double var4, double var6, float var8, float var9) {
        this.doRenderItem((EntityItem)var1, var2, var4, var6, var8, var9);
    }
}

