/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.src;

import net.minecraft.src.Block;
import net.minecraft.src.FontRenderer;
import net.minecraft.src.SignModel;
import net.minecraft.src.TileEntity;
import net.minecraft.src.TileEntitySign;
import net.minecraft.src.TileEntitySpecialRenderer;
import net.skidcode.gh.maybeaclient.hacks.NoRenderHack;
import org.lwjgl.opengl.GL11;

public class TileEntitySignRenderer
extends TileEntitySpecialRenderer {
    private SignModel signModel = new SignModel();

    public void renderTileEntitySignAt(TileEntitySign var1, double var2, double var4, double var6, float var8) {
        float var12;
        Block var9 = var1.getBlockType();
        GL11.glPushMatrix();
        float var10 = 0.6666667f;
        if (var9 == Block.signPost) {
            GL11.glTranslatef((float)((float)var2 + 0.5f), (float)((float)var4 + 0.75f * var10), (float)((float)var6 + 0.5f));
            float var11 = (float)(var1.getBlockMetadata() * 360) / 16.0f;
            GL11.glRotatef((float)(-var11), (float)0.0f, (float)1.0f, (float)0.0f);
            this.signModel.field_1345_b.showModel = true;
        } else {
            int var16 = var1.getBlockMetadata();
            var12 = 0.0f;
            if (var16 == 2) {
                var12 = 180.0f;
            }
            if (var16 == 4) {
                var12 = 90.0f;
            }
            if (var16 == 5) {
                var12 = -90.0f;
            }
            GL11.glTranslatef((float)((float)var2 + 0.5f), (float)((float)var4 + 0.75f * var10), (float)((float)var6 + 0.5f));
            GL11.glRotatef((float)(-var12), (float)0.0f, (float)1.0f, (float)0.0f);
            GL11.glTranslatef((float)0.0f, (float)-0.3125f, (float)-0.4375f);
            this.signModel.field_1345_b.showModel = false;
        }
        this.bindTextureByName("/item/sign.png");
        GL11.glPushMatrix();
        GL11.glScalef((float)var10, (float)(-var10), (float)(-var10));
        this.signModel.func_887_a();
        GL11.glPopMatrix();
        FontRenderer var17 = this.getFontRenderer();
        var12 = 0.016666668f * var10;
        GL11.glTranslatef((float)0.0f, (float)(0.5f * var10), (float)(0.07f * var10));
        GL11.glScalef((float)var12, (float)(-var12), (float)var12);
        GL11.glNormal3f((float)0.0f, (float)0.0f, (float)(-1.0f * var12));
        GL11.glDepthMask((boolean)false);
        int var13 = 0;
        if (!NoRenderHack.instance.status || !NoRenderHack.instance.signText.value) {
            int var14 = 0;
            while (var14 < var1.signText.length) {
                String var15 = var1.signText[var14];
                if (var14 == var1.lineBeingEdited) {
                    var15 = "> " + var15 + " <";
                    var17.drawString(var15, -var17.getStringWidth(var15) / 2, var14 * 10 - var1.signText.length * 5, var13);
                } else {
                    var17.drawString(var15, -var17.getStringWidth(var15) / 2, var14 * 10 - var1.signText.length * 5, var13);
                }
                ++var14;
            }
        }
        GL11.glDepthMask((boolean)true);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glPopMatrix();
    }

    @Override
    public void renderTileEntityAt(TileEntity var1, double var2, double var4, double var6, float var8) {
        this.renderTileEntitySignAt((TileEntitySign)var1, var2, var4, var6, var8);
    }
}

