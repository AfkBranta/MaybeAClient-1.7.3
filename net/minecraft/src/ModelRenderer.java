/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.src;

import net.minecraft.src.GLAllocation;
import net.minecraft.src.PositionTexureVertex;
import net.minecraft.src.Tessellator;
import net.minecraft.src.TexturedQuad;
import org.lwjgl.opengl.GL11;

public class ModelRenderer {
    private PositionTexureVertex[] corners;
    private TexturedQuad[] faces;
    private int textureOffsetX;
    private int textureOffsetY;
    public float offsetX;
    public float offsetY;
    public float offsetZ;
    public float rotateAngleX;
    public float rotateAngleY;
    public float rotateAngleZ;
    private boolean compiled = false;
    private int displayList = 0;
    public boolean mirror = false;
    public boolean showModel = true;
    public boolean field_1402_i = false;

    public ModelRenderer(int var1, int var2) {
        this.textureOffsetX = var1;
        this.textureOffsetY = var2;
    }

    public void addBox(float var1, float var2, float var3, int var4, int var5, int var6) {
        this.addBox(var1, var2, var3, var4, var5, var6, 0.0f);
    }

    public void addBox(float var1, float var2, float var3, int var4, int var5, int var6, float var7) {
        this.corners = new PositionTexureVertex[8];
        this.faces = new TexturedQuad[6];
        float var8 = var1 + (float)var4;
        float var9 = var2 + (float)var5;
        float var10 = var3 + (float)var6;
        var1 -= var7;
        var2 -= var7;
        var3 -= var7;
        var8 += var7;
        var9 += var7;
        var10 += var7;
        if (this.mirror) {
            float var11 = var8;
            var8 = var1;
            var1 = var11;
        }
        PositionTexureVertex var20 = new PositionTexureVertex(var1, var2, var3, 0.0f, 0.0f);
        PositionTexureVertex var12 = new PositionTexureVertex(var8, var2, var3, 0.0f, 8.0f);
        PositionTexureVertex var13 = new PositionTexureVertex(var8, var9, var3, 8.0f, 8.0f);
        PositionTexureVertex var14 = new PositionTexureVertex(var1, var9, var3, 8.0f, 0.0f);
        PositionTexureVertex var15 = new PositionTexureVertex(var1, var2, var10, 0.0f, 0.0f);
        PositionTexureVertex var16 = new PositionTexureVertex(var8, var2, var10, 0.0f, 8.0f);
        PositionTexureVertex var17 = new PositionTexureVertex(var8, var9, var10, 8.0f, 8.0f);
        PositionTexureVertex var18 = new PositionTexureVertex(var1, var9, var10, 8.0f, 0.0f);
        this.corners[0] = var20;
        this.corners[1] = var12;
        this.corners[2] = var13;
        this.corners[3] = var14;
        this.corners[4] = var15;
        this.corners[5] = var16;
        this.corners[6] = var17;
        this.corners[7] = var18;
        this.faces[0] = new TexturedQuad(new PositionTexureVertex[]{var16, var12, var13, var17}, this.textureOffsetX + var6 + var4, this.textureOffsetY + var6, this.textureOffsetX + var6 + var4 + var6, this.textureOffsetY + var6 + var5);
        this.faces[1] = new TexturedQuad(new PositionTexureVertex[]{var20, var15, var18, var14}, this.textureOffsetX + 0, this.textureOffsetY + var6, this.textureOffsetX + var6, this.textureOffsetY + var6 + var5);
        this.faces[2] = new TexturedQuad(new PositionTexureVertex[]{var16, var15, var20, var12}, this.textureOffsetX + var6, this.textureOffsetY + 0, this.textureOffsetX + var6 + var4, this.textureOffsetY + var6);
        this.faces[3] = new TexturedQuad(new PositionTexureVertex[]{var13, var14, var18, var17}, this.textureOffsetX + var6 + var4, this.textureOffsetY + 0, this.textureOffsetX + var6 + var4 + var4, this.textureOffsetY + var6);
        this.faces[4] = new TexturedQuad(new PositionTexureVertex[]{var12, var20, var14, var13}, this.textureOffsetX + var6, this.textureOffsetY + var6, this.textureOffsetX + var6 + var4, this.textureOffsetY + var6 + var5);
        this.faces[5] = new TexturedQuad(new PositionTexureVertex[]{var15, var16, var17, var18}, this.textureOffsetX + var6 + var4 + var6, this.textureOffsetY + var6, this.textureOffsetX + var6 + var4 + var6 + var4, this.textureOffsetY + var6 + var5);
        if (this.mirror) {
            int var19 = 0;
            while (var19 < this.faces.length) {
                this.faces[var19].flipFace();
                ++var19;
            }
        }
    }

    public void setPosition(float var1, float var2, float var3) {
        this.offsetX = var1;
        this.offsetY = var2;
        this.offsetZ = var3;
    }

    public void render(float var1) {
        if (!this.field_1402_i && this.showModel) {
            if (!this.compiled) {
                this.compileDisplayList(var1);
            }
            if (this.rotateAngleX == 0.0f && this.rotateAngleY == 0.0f && this.rotateAngleZ == 0.0f) {
                if (this.offsetX == 0.0f && this.offsetY == 0.0f && this.offsetZ == 0.0f) {
                    GL11.glCallList((int)this.displayList);
                } else {
                    GL11.glTranslatef((float)(this.offsetX * var1), (float)(this.offsetY * var1), (float)(this.offsetZ * var1));
                    GL11.glCallList((int)this.displayList);
                    GL11.glTranslatef((float)(-this.offsetX * var1), (float)(-this.offsetY * var1), (float)(-this.offsetZ * var1));
                }
            } else {
                GL11.glPushMatrix();
                GL11.glTranslatef((float)(this.offsetX * var1), (float)(this.offsetY * var1), (float)(this.offsetZ * var1));
                if (this.rotateAngleZ != 0.0f) {
                    GL11.glRotatef((float)(this.rotateAngleZ * 57.295776f), (float)0.0f, (float)0.0f, (float)1.0f);
                }
                if (this.rotateAngleY != 0.0f) {
                    GL11.glRotatef((float)(this.rotateAngleY * 57.295776f), (float)0.0f, (float)1.0f, (float)0.0f);
                }
                if (this.rotateAngleX != 0.0f) {
                    GL11.glRotatef((float)(this.rotateAngleX * 57.295776f), (float)1.0f, (float)0.0f, (float)0.0f);
                }
                GL11.glCallList((int)this.displayList);
                GL11.glPopMatrix();
            }
        }
    }

    public void func_25122_b(float var1) {
        if (!this.field_1402_i && this.showModel) {
            if (!this.compiled) {
                this.compileDisplayList(var1);
            }
            GL11.glPushMatrix();
            GL11.glTranslatef((float)(this.offsetX * var1), (float)(this.offsetY * var1), (float)(this.offsetZ * var1));
            if (this.rotateAngleY != 0.0f) {
                GL11.glRotatef((float)(this.rotateAngleY * 57.295776f), (float)0.0f, (float)1.0f, (float)0.0f);
            }
            if (this.rotateAngleX != 0.0f) {
                GL11.glRotatef((float)(this.rotateAngleX * 57.295776f), (float)1.0f, (float)0.0f, (float)0.0f);
            }
            if (this.rotateAngleZ != 0.0f) {
                GL11.glRotatef((float)(this.rotateAngleZ * 57.295776f), (float)0.0f, (float)0.0f, (float)1.0f);
            }
            GL11.glCallList((int)this.displayList);
            GL11.glPopMatrix();
        }
    }

    public void postRender(float var1) {
        if (!this.field_1402_i && this.showModel) {
            if (!this.compiled) {
                this.compileDisplayList(var1);
            }
            if (this.rotateAngleX == 0.0f && this.rotateAngleY == 0.0f && this.rotateAngleZ == 0.0f) {
                if (this.offsetX != 0.0f || this.offsetY != 0.0f || this.offsetZ != 0.0f) {
                    GL11.glTranslatef((float)(this.offsetX * var1), (float)(this.offsetY * var1), (float)(this.offsetZ * var1));
                }
            } else {
                GL11.glTranslatef((float)(this.offsetX * var1), (float)(this.offsetY * var1), (float)(this.offsetZ * var1));
                if (this.rotateAngleZ != 0.0f) {
                    GL11.glRotatef((float)(this.rotateAngleZ * 57.295776f), (float)0.0f, (float)0.0f, (float)1.0f);
                }
                if (this.rotateAngleY != 0.0f) {
                    GL11.glRotatef((float)(this.rotateAngleY * 57.295776f), (float)0.0f, (float)1.0f, (float)0.0f);
                }
                if (this.rotateAngleX != 0.0f) {
                    GL11.glRotatef((float)(this.rotateAngleX * 57.295776f), (float)1.0f, (float)0.0f, (float)0.0f);
                }
            }
        }
    }

    private void compileDisplayList(float var1) {
        this.displayList = GLAllocation.generateDisplayLists(1);
        GL11.glNewList((int)this.displayList, (int)4864);
        Tessellator var2 = Tessellator.instance;
        int var3 = 0;
        while (var3 < this.faces.length) {
            this.faces[var3].draw(var2, var1);
            ++var3;
        }
        GL11.glEndList();
        this.compiled = true;
    }
}

