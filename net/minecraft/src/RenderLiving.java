/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.src;

import net.minecraft.client.Minecraft;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityAnimals;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityMobs;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.MathHelper;
import net.minecraft.src.ModelBase;
import net.minecraft.src.ModelBiped;
import net.minecraft.src.Render;
import net.skidcode.gh.maybeaclient.hacks.EntityESPHack;
import net.skidcode.gh.maybeaclient.utils.RenderUtils;
import org.lwjgl.opengl.GL11;

public class RenderLiving
extends Render {
    protected ModelBase mainModel;
    protected ModelBase renderPassModel;

    public RenderLiving(ModelBase var1, float var2) {
        this.mainModel = var1;
        this.shadowSize = var2;
    }

    public void setRenderPassModel(ModelBase var1) {
        this.renderPassModel = var1;
    }

    public void doRenderLiving(EntityLiving entity, double var2, double var4, double var6, float var8, float var9) {
        GL11.glPushMatrix();
        GL11.glDisable((int)2884);
        this.mainModel.onGround = this.func_167_c(entity, var9);
        this.mainModel.isRiding = entity.isRiding();
        if (this.renderPassModel != null) {
            this.renderPassModel.isRiding = this.mainModel.isRiding;
        }
        try {
            float var10 = entity.prevRenderYawOffset + (entity.renderYawOffset - entity.prevRenderYawOffset) * var9;
            float var11 = entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * var9;
            float var12 = entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * var9;
            this.func_22012_b(entity, var2, var4, var6);
            float var13 = this.func_170_d(entity, var9);
            this.func_21004_a(entity, var13, var10, var9);
            float var14 = 0.0625f;
            GL11.glEnable((int)32826);
            GL11.glScalef((float)-1.0f, (float)-1.0f, (float)1.0f);
            this.preRenderCallback(entity, var9);
            GL11.glTranslatef((float)0.0f, (float)(-24.0f * var14 - 0.0078125f), (float)0.0f);
            float var15 = entity.field_705_Q + (entity.field_704_R - entity.field_705_Q) * var9;
            float var16 = entity.field_703_S - entity.field_704_R * (1.0f - var9);
            if (var15 > 1.0f) {
                var15 = 1.0f;
            }
            this.loadDownloadableImageTexture(entity.skinUrl, entity.getEntityTexture());
            GL11.glEnable((int)3008);
            this.mainModel.func_25103_a(entity, var16, var15, var9);
            this.mainModel.render(var16, var15, var13, var11 - var10, var12, var14);
            int var17 = 0;
            while (var17 < 4) {
                if (this.shouldRenderPass(entity, var17, var9)) {
                    this.renderPassModel.render(var16, var15, var13, var11 - var10, var12, var14);
                    GL11.glDisable((int)3042);
                    GL11.glEnable((int)3008);
                }
                ++var17;
            }
            if (EntityESPHack.instance.status && EntityESPHack.instance.shouldRender(entity)) {
                int b;
                int g;
                int r;
                if (entity instanceof EntityMobs) {
                    r = EntityESPHack.instance.hostileColor.red;
                    g = EntityESPHack.instance.hostileColor.green;
                    b = EntityESPHack.instance.hostileColor.blue;
                } else if (entity instanceof EntityAnimals) {
                    r = EntityESPHack.instance.animalColor.red;
                    g = EntityESPHack.instance.animalColor.green;
                    b = EntityESPHack.instance.animalColor.blue;
                } else if (entity instanceof EntityPlayer) {
                    r = EntityESPHack.instance.playerColor.red;
                    g = EntityESPHack.instance.playerColor.green;
                    b = EntityESPHack.instance.playerColor.blue;
                } else {
                    System.out.println("Tried rendering esp for " + entity + " that has no color!");
                    r = 0;
                    g = 0;
                    b = 0;
                }
                if (EntityESPHack.instance.getRenderingMode(entity).equalsIgnoreCase("Fill")) {
                    GL11.glPushMatrix();
                    GL11.glPushAttrib((int)1048575);
                    GL11.glDisable((int)3553);
                    GL11.glDisable((int)2896);
                    GL11.glDisable((int)2929);
                    GL11.glDisable((int)3008);
                    GL11.glEnable((int)3042);
                    GL11.glDisable((int)2912);
                    GL11.glBlendFunc((int)770, (int)32772);
                    GL11.glColor3f((float)((float)r / 255.0f), (float)((float)g / 255.0f), (float)((float)b / 255.0f));
                    this.mainModel.render(var16, var15, var13, var11 - var10, var12, var14);
                    GL11.glPopAttrib();
                    GL11.glPopMatrix();
                } else if (EntityESPHack.instance.getRenderingMode(entity).equalsIgnoreCase("Outline")) {
                    if (entity instanceof EntityPlayer) {
                        ((ModelBiped)this.mainModel).bipedHeadwear.showModel = false;
                    }
                    GL11.glPushAttrib((int)1048575);
                    GL11.glDisable((int)2912);
                    GL11.glDisable((int)3008);
                    GL11.glDisable((int)3553);
                    GL11.glDisable((int)2896);
                    GL11.glEnable((int)3042);
                    GL11.glBlendFunc((int)770, (int)771);
                    GL11.glLineWidth((float)3.0f);
                    GL11.glEnable((int)2848);
                    GL11.glEnable((int)2960);
                    GL11.glClear((int)1024);
                    GL11.glClearStencil((int)15);
                    GL11.glStencilFunc((int)512, (int)1, (int)15);
                    GL11.glStencilOp((int)7681, (int)7681, (int)7681);
                    GL11.glPolygonMode((int)1032, (int)6913);
                    this.mainModel.render(var16, var15, var13, var11 - var10, var12, var14);
                    GL11.glStencilFunc((int)512, (int)0, (int)15);
                    GL11.glStencilOp((int)7681, (int)7681, (int)7681);
                    GL11.glPolygonMode((int)1032, (int)6914);
                    this.mainModel.render(var16, var15, var13, var11 - var10, var12, var14);
                    GL11.glStencilFunc((int)514, (int)1, (int)15);
                    GL11.glStencilOp((int)7680, (int)7680, (int)7680);
                    GL11.glPolygonMode((int)1032, (int)6913);
                    GL11.glColor4f((float)(r / 255), (float)(g / 255), (float)(b / 255), (float)1.0f);
                    GL11.glDepthMask((boolean)false);
                    GL11.glDisable((int)2929);
                    this.mainModel.render(var16, var15, var13, var11 - var10, var12, var14);
                    GL11.glEnable((int)2929);
                    GL11.glDepthMask((boolean)true);
                    GL11.glDisable((int)2960);
                    GL11.glDisable((int)2848);
                    GL11.glHint((int)3154, (int)4352);
                    GL11.glEnable((int)3042);
                    GL11.glEnable((int)2896);
                    GL11.glEnable((int)3553);
                    GL11.glEnable((int)3008);
                    GL11.glPopAttrib();
                    if (entity instanceof EntityPlayer) {
                        ((ModelBiped)this.mainModel).bipedHeadwear.showModel = true;
                    }
                }
            }
            this.renderEquippedItems(entity, var9);
            float var25 = entity.getEntityBrightness(var9);
            int var18 = this.getColorMultiplier(entity, var25, var9);
            if ((var18 >> 24 & 0xFF) > 0 || entity.hurtTime > 0 || entity.deathTime > 0) {
                GL11.glDisable((int)3553);
                GL11.glDisable((int)3008);
                GL11.glEnable((int)3042);
                GL11.glBlendFunc((int)770, (int)771);
                GL11.glDepthFunc((int)514);
                if (entity.hurtTime > 0 || entity.deathTime > 0) {
                    GL11.glColor4f((float)var25, (float)0.0f, (float)0.0f, (float)0.4f);
                    this.mainModel.render(var16, var15, var13, var11 - var10, var12, var14);
                    int var19 = 0;
                    while (var19 < 4) {
                        if (this.shouldRenderPass(entity, var19, var9)) {
                            GL11.glColor4f((float)var25, (float)0.0f, (float)0.0f, (float)0.4f);
                            this.renderPassModel.render(var16, var15, var13, var11 - var10, var12, var14);
                        }
                        ++var19;
                    }
                }
                if ((var18 >> 24 & 0xFF) > 0) {
                    float var26 = (float)(var18 >> 16 & 0xFF) / 255.0f;
                    float var20 = (float)(var18 >> 8 & 0xFF) / 255.0f;
                    float var21 = (float)(var18 & 0xFF) / 255.0f;
                    float var22 = (float)(var18 >> 24 & 0xFF) / 255.0f;
                    GL11.glColor4f((float)var26, (float)var20, (float)var21, (float)var22);
                    this.mainModel.render(var16, var15, var13, var11 - var10, var12, var14);
                    int var23 = 0;
                    while (var23 < 4) {
                        if (this.shouldRenderPass(entity, var23, var9)) {
                            GL11.glColor4f((float)var26, (float)var20, (float)var21, (float)var22);
                            this.renderPassModel.render(var16, var15, var13, var11 - var10, var12, var14);
                        }
                        ++var23;
                    }
                }
                GL11.glDepthFunc((int)515);
                GL11.glDisable((int)3042);
                GL11.glEnable((int)3008);
                GL11.glEnable((int)3553);
            }
            GL11.glDisable((int)32826);
        }
        catch (Exception var24) {
            var24.printStackTrace();
        }
        GL11.glEnable((int)2884);
        GL11.glPopMatrix();
        this.passSpecialRender(entity, var2, var4, var6);
    }

    protected void func_22012_b(EntityLiving var1, double var2, double var4, double var6) {
        GL11.glTranslatef((float)((float)var2), (float)((float)var4), (float)((float)var6));
    }

    protected void func_21004_a(EntityLiving var1, float var2, float var3, float var4) {
        GL11.glRotatef((float)(180.0f - var3), (float)0.0f, (float)1.0f, (float)0.0f);
        if (var1.deathTime > 0) {
            float var5 = ((float)var1.deathTime + var4 - 1.0f) / 20.0f * 1.6f;
            if ((var5 = MathHelper.sqrt_float(var5)) > 1.0f) {
                var5 = 1.0f;
            }
            GL11.glRotatef((float)(var5 * this.func_172_a(var1)), (float)0.0f, (float)0.0f, (float)1.0f);
        }
    }

    protected float func_167_c(EntityLiving var1, float var2) {
        return var1.getSwingProgress(var2);
    }

    protected float func_170_d(EntityLiving var1, float var2) {
        return (float)var1.ticksExisted + var2;
    }

    protected void renderEquippedItems(EntityLiving var1, float var2) {
    }

    protected boolean shouldRenderPass(EntityLiving var1, int var2, float var3) {
        return false;
    }

    protected float func_172_a(EntityLiving var1) {
        return 90.0f;
    }

    protected int getColorMultiplier(EntityLiving var1, float var2, float var3) {
        return 0;
    }

    protected void preRenderCallback(EntityLiving var1, float var2) {
    }

    protected void passSpecialRender(EntityLiving entity, double var2, double var4, double var6) {
        if (EntityESPHack.instance.status && EntityESPHack.instance.getRenderingMode(entity).equalsIgnoreCase("Box") && EntityESPHack.instance.shouldRender(entity)) {
            int b;
            int g;
            int r;
            if (entity instanceof EntityMobs) {
                r = EntityESPHack.instance.hostileColor.red;
                g = EntityESPHack.instance.hostileColor.green;
                b = EntityESPHack.instance.hostileColor.blue;
            } else if (entity instanceof EntityAnimals) {
                r = EntityESPHack.instance.animalColor.red;
                g = EntityESPHack.instance.animalColor.green;
                b = EntityESPHack.instance.animalColor.blue;
            } else if (entity instanceof EntityPlayer) {
                r = EntityESPHack.instance.playerColor.red;
                g = EntityESPHack.instance.playerColor.green;
                b = EntityESPHack.instance.playerColor.blue;
            } else {
                System.out.println("Tried rendering esp for " + entity + " that has no color!");
                r = 0;
                g = 0;
                b = 0;
            }
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
            GL11.glEnable((int)2929);
        }
        if (Minecraft.isDebugInfoEnabled()) {
            this.renderLivingLabel(entity, Integer.toString(entity.entityId), var2, var4, var6, 64);
        }
    }

    @Override
    public void doRender(Entity var1, double var2, double var4, double var6, float var8, float var9) {
        this.doRenderLiving((EntityLiving)var1, var2, var4, var6, var8, var9);
    }
}

