/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.src;

import net.minecraft.client.Minecraft;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Block;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.FontRenderer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemArmor;
import net.minecraft.src.ItemStack;
import net.minecraft.src.MathHelper;
import net.minecraft.src.ModelBiped;
import net.minecraft.src.RenderBlocks;
import net.minecraft.src.RenderLiving;
import net.minecraft.src.Tessellator;
import net.skidcode.gh.maybeaclient.hacks.EntityESPHack;
import net.skidcode.gh.maybeaclient.hacks.NameTagsHack;
import net.skidcode.gh.maybeaclient.utils.RenderUtils;
import org.lwjgl.opengl.GL11;

public class RenderPlayer
extends RenderLiving {
    private ModelBiped modelBipedMain;
    private ModelBiped modelArmorChestplate;
    private ModelBiped modelArmor;
    private static final String[] armorFilenamePrefix = new String[]{"cloth", "chain", "iron", "diamond", "gold"};

    public RenderPlayer() {
        super(new ModelBiped(0.0f), 0.5f);
        this.modelBipedMain = (ModelBiped)this.mainModel;
        this.modelArmorChestplate = new ModelBiped(1.0f);
        this.modelArmor = new ModelBiped(0.5f);
    }

    protected boolean setArmorModel(EntityPlayer var1, int var2, float var3) {
        Item var5;
        ItemStack var4 = var1.inventory.armorItemInSlot(3 - var2);
        if (var4 != null && (var5 = var4.getItem()) instanceof ItemArmor) {
            ItemArmor var6 = (ItemArmor)var5;
            this.loadTexture("/armor/" + armorFilenamePrefix[var6.renderIndex] + "_" + (var2 == 2 ? 2 : 1) + ".png");
            ModelBiped var7 = var2 == 2 ? this.modelArmor : this.modelArmorChestplate;
            var7.bipedHead.showModel = var2 == 0;
            var7.bipedHeadwear.showModel = var2 == 0;
            var7.bipedBody.showModel = var2 == 1 || var2 == 2;
            var7.bipedRightArm.showModel = var2 == 1;
            var7.bipedLeftArm.showModel = var2 == 1;
            var7.bipedRightLeg.showModel = var2 == 2 || var2 == 3;
            var7.bipedLeftLeg.showModel = var2 == 2 || var2 == 3;
            this.setRenderPassModel(var7);
            return true;
        }
        return false;
    }

    public void func_188_a(EntityPlayer var1, double var2, double var4, double var6, float var8, float var9) {
        ItemStack var10 = var1.inventory.getCurrentItem();
        this.modelBipedMain.field_1278_i = var10 != null;
        this.modelArmor.field_1278_i = this.modelBipedMain.field_1278_i;
        this.modelArmorChestplate.field_1278_i = this.modelBipedMain.field_1278_i;
        this.modelArmor.isSneak = this.modelBipedMain.isSneak = var1.isSneaking();
        this.modelArmorChestplate.isSneak = this.modelBipedMain.isSneak;
        double var11 = var4 - (double)var1.yOffset;
        if (var1.isSneaking()) {
            var11 -= 0.125;
        }
        super.doRenderLiving(var1, var2, var11, var6, var8, var9);
        this.modelBipedMain.isSneak = false;
        this.modelArmor.isSneak = false;
        this.modelArmorChestplate.isSneak = false;
        this.modelBipedMain.field_1278_i = false;
        this.modelArmor.field_1278_i = false;
        this.modelArmorChestplate.field_1278_i = false;
    }

    protected void renderName(EntityPlayer var1, double var2, double var4, double var6) {
        if (Minecraft.isGuiEnabled() && var1 != this.renderManager.livingPlayer && this.renderManager.livingPlayer != null) {
            float var11;
            float var8 = 1.6f;
            float var9 = 0.016666668f * var8;
            float var10 = var1.getDistanceToEntity(this.renderManager.livingPlayer);
            float f = var11 = var1.isSneaking() ? 32.0f : 64.0f;
            if (var10 < var11 || NameTagsHack.instance.status) {
                String var12 = var1.username;
                if (!var1.isSneaking() || NameTagsHack.instance.status) {
                    this.renderLivingLabel(var1, var12, var2, var4, var6, 64);
                } else {
                    FontRenderer var13 = this.getFontRendererFromRenderManager();
                    GL11.glPushMatrix();
                    GL11.glTranslatef((float)((float)var2 + 0.0f), (float)((float)var4 + 2.3f), (float)((float)var6));
                    GL11.glNormal3f((float)0.0f, (float)1.0f, (float)0.0f);
                    GL11.glRotatef((float)(-this.renderManager.playerViewY), (float)0.0f, (float)1.0f, (float)0.0f);
                    GL11.glRotatef((float)this.renderManager.playerViewX, (float)1.0f, (float)0.0f, (float)0.0f);
                    GL11.glScalef((float)(-var9), (float)(-var9), (float)var9);
                    GL11.glDisable((int)2896);
                    GL11.glTranslatef((float)0.0f, (float)(0.25f / var9), (float)0.0f);
                    GL11.glDepthMask((boolean)false);
                    GL11.glEnable((int)3042);
                    GL11.glBlendFunc((int)770, (int)771);
                    Tessellator var14 = Tessellator.instance;
                    GL11.glDisable((int)3553);
                    var14.startDrawingQuads();
                    int var15 = var13.getStringWidth(var12) / 2;
                    var14.setColorRGBA_F(0.0f, 0.0f, 0.0f, 0.25f);
                    var14.addVertex(-var15 - 1, -1.0, 0.0);
                    var14.addVertex(-var15 - 1, 8.0, 0.0);
                    var14.addVertex(var15 + 1, 8.0, 0.0);
                    var14.addVertex(var15 + 1, -1.0, 0.0);
                    var14.draw();
                    GL11.glEnable((int)3553);
                    GL11.glDepthMask((boolean)true);
                    var13.drawString(var12, -var13.getStringWidth(var12) / 2, 0, 0x20FFFFFF);
                    GL11.glEnable((int)2896);
                    GL11.glDisable((int)3042);
                    GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                    GL11.glPopMatrix();
                }
            }
        }
    }

    protected void renderSpecials(EntityPlayer var1, float var2) {
        ItemStack var21;
        float var5;
        ItemStack var3 = var1.inventory.armorItemInSlot(3);
        if (var3 != null && var3.getItem().shiftedIndex < 256) {
            GL11.glPushMatrix();
            this.modelBipedMain.bipedHead.postRender(0.0625f);
            if (RenderBlocks.renderItemIn3d(Block.blocksList[var3.itemID].getRenderType())) {
                float var4 = 0.625f;
                GL11.glTranslatef((float)0.0f, (float)-0.25f, (float)0.0f);
                GL11.glRotatef((float)180.0f, (float)0.0f, (float)1.0f, (float)0.0f);
                GL11.glScalef((float)var4, (float)(-var4), (float)var4);
            }
            this.renderManager.itemRenderer.renderItem(var3);
            GL11.glPopMatrix();
        }
        if (var1.username.equals("deadmau5") && this.loadDownloadableImageTexture(var1.skinUrl, null)) {
            int var19 = 0;
            while (var19 < 2) {
                var5 = var1.prevRotationYaw + (var1.rotationYaw - var1.prevRotationYaw) * var2 - (var1.prevRenderYawOffset + (var1.renderYawOffset - var1.prevRenderYawOffset) * var2);
                float var6 = var1.prevRotationPitch + (var1.rotationPitch - var1.prevRotationPitch) * var2;
                GL11.glPushMatrix();
                GL11.glRotatef((float)var5, (float)0.0f, (float)1.0f, (float)0.0f);
                GL11.glRotatef((float)var6, (float)1.0f, (float)0.0f, (float)0.0f);
                GL11.glTranslatef((float)(0.375f * (float)(var19 * 2 - 1)), (float)0.0f, (float)0.0f);
                GL11.glTranslatef((float)0.0f, (float)-0.375f, (float)0.0f);
                GL11.glRotatef((float)(-var6), (float)1.0f, (float)0.0f, (float)0.0f);
                GL11.glRotatef((float)(-var5), (float)0.0f, (float)1.0f, (float)0.0f);
                float var7 = 1.3333334f;
                GL11.glScalef((float)var7, (float)var7, (float)var7);
                this.modelBipedMain.renderEars(0.0625f);
                GL11.glPopMatrix();
                ++var19;
            }
        }
        if (this.loadDownloadableImageTexture(var1.playerCloakUrl, null)) {
            GL11.glPushMatrix();
            GL11.glTranslatef((float)0.0f, (float)0.0f, (float)0.125f);
            double var20 = var1.field_20066_r + (var1.field_20063_u - var1.field_20066_r) * (double)var2 - (var1.prevPosX + (var1.posX - var1.prevPosX) * (double)var2);
            double var22 = var1.field_20065_s + (var1.field_20062_v - var1.field_20065_s) * (double)var2 - (var1.prevPosY + (var1.posY - var1.prevPosY) * (double)var2);
            double var8 = var1.field_20064_t + (var1.field_20061_w - var1.field_20064_t) * (double)var2 - (var1.prevPosZ + (var1.posZ - var1.prevPosZ) * (double)var2);
            float var10 = var1.prevRenderYawOffset + (var1.renderYawOffset - var1.prevRenderYawOffset) * var2;
            double var11 = MathHelper.sin(var10 * (float)Math.PI / 180.0f);
            double var13 = -MathHelper.cos(var10 * (float)Math.PI / 180.0f);
            float var15 = (float)var22 * 10.0f;
            if (var15 < -6.0f) {
                var15 = -6.0f;
            }
            if (var15 > 32.0f) {
                var15 = 32.0f;
            }
            float var16 = (float)(var20 * var11 + var8 * var13) * 100.0f;
            float var17 = (float)(var20 * var13 - var8 * var11) * 100.0f;
            if (var16 < 0.0f) {
                var16 = 0.0f;
            }
            float var18 = var1.field_775_e + (var1.field_774_f - var1.field_775_e) * var2;
            GL11.glRotatef((float)(6.0f + var16 / 2.0f + (var15 += MathHelper.sin((var1.prevDistanceWalkedModified + (var1.distanceWalkedModified - var1.prevDistanceWalkedModified) * var2) * 6.0f) * 32.0f * var18)), (float)1.0f, (float)0.0f, (float)0.0f);
            GL11.glRotatef((float)(var17 / 2.0f), (float)0.0f, (float)0.0f, (float)1.0f);
            GL11.glRotatef((float)(-var17 / 2.0f), (float)0.0f, (float)1.0f, (float)0.0f);
            GL11.glRotatef((float)180.0f, (float)0.0f, (float)1.0f, (float)0.0f);
            this.modelBipedMain.renderCloak(0.0625f);
            GL11.glPopMatrix();
        }
        if ((var21 = var1.inventory.getCurrentItem()) != null) {
            GL11.glPushMatrix();
            this.modelBipedMain.bipedRightArm.postRender(0.0625f);
            GL11.glTranslatef((float)-0.0625f, (float)0.4375f, (float)0.0625f);
            if (var1.fishEntity != null) {
                var21 = new ItemStack(Item.stick);
            }
            if (var21.itemID < 256 && RenderBlocks.renderItemIn3d(Block.blocksList[var21.itemID].getRenderType())) {
                var5 = 0.5f;
                GL11.glTranslatef((float)0.0f, (float)0.1875f, (float)-0.3125f);
                GL11.glRotatef((float)20.0f, (float)1.0f, (float)0.0f, (float)0.0f);
                GL11.glRotatef((float)45.0f, (float)0.0f, (float)1.0f, (float)0.0f);
                GL11.glScalef((float)(var5 *= 0.75f), (float)(-var5), (float)var5);
            } else if (Item.itemsList[var21.itemID].isFull3D()) {
                var5 = 0.625f;
                if (Item.itemsList[var21.itemID].shouldRotateAroundWhenRendering()) {
                    GL11.glRotatef((float)180.0f, (float)0.0f, (float)0.0f, (float)1.0f);
                    GL11.glTranslatef((float)0.0f, (float)-0.125f, (float)0.0f);
                }
                GL11.glTranslatef((float)0.0f, (float)0.1875f, (float)0.0f);
                GL11.glScalef((float)var5, (float)(-var5), (float)var5);
                GL11.glRotatef((float)-100.0f, (float)1.0f, (float)0.0f, (float)0.0f);
                GL11.glRotatef((float)45.0f, (float)0.0f, (float)1.0f, (float)0.0f);
            } else {
                var5 = 0.375f;
                GL11.glTranslatef((float)0.25f, (float)0.1875f, (float)-0.1875f);
                GL11.glScalef((float)var5, (float)var5, (float)var5);
                GL11.glRotatef((float)60.0f, (float)0.0f, (float)0.0f, (float)1.0f);
                GL11.glRotatef((float)-90.0f, (float)1.0f, (float)0.0f, (float)0.0f);
                GL11.glRotatef((float)20.0f, (float)0.0f, (float)0.0f, (float)1.0f);
            }
            this.renderManager.itemRenderer.renderItem(var21);
            GL11.glPopMatrix();
        }
    }

    protected void func_186_b(EntityPlayer var1, float var2) {
        float var3 = 0.9375f;
        GL11.glScalef((float)var3, (float)var3, (float)var3);
    }

    public void drawFirstPersonHand() {
        this.modelBipedMain.onGround = 0.0f;
        this.modelBipedMain.setRotationAngles(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0625f);
        this.modelBipedMain.bipedRightArm.render(0.0625f);
    }

    protected void func_22016_b(EntityPlayer var1, double var2, double var4, double var6) {
        if (var1.isEntityAlive() && var1.isPlayerSleeping()) {
            super.func_22012_b(var1, var2 + (double)var1.field_22063_x, var4 + (double)var1.field_22062_y, var6 + (double)var1.field_22061_z);
        } else {
            super.func_22012_b(var1, var2, var4, var6);
        }
    }

    protected void func_22017_a(EntityPlayer var1, float var2, float var3, float var4) {
        if (var1.isEntityAlive() && var1.isPlayerSleeping()) {
            GL11.glRotatef((float)var1.getBedOrientationInDegrees(), (float)0.0f, (float)1.0f, (float)0.0f);
            GL11.glRotatef((float)this.func_172_a(var1), (float)0.0f, (float)0.0f, (float)1.0f);
            GL11.glRotatef((float)270.0f, (float)0.0f, (float)1.0f, (float)0.0f);
        } else {
            super.func_21004_a(var1, var2, var3, var4);
        }
    }

    @Override
    protected void passSpecialRender(EntityLiving entity, double var2, double var4, double var6) {
        if (EntityESPHack.instance.status && EntityESPHack.instance.getRenderingMode(entity).equalsIgnoreCase("Box") && EntityESPHack.instance.shouldRender(entity)) {
            int b;
            int g;
            int r;
            if (entity instanceof EntityPlayer) {
                r = EntityESPHack.instance.playerColor.red;
                g = EntityESPHack.instance.playerColor.green;
                b = EntityESPHack.instance.playerColor.blue;
            } else {
                System.out.println("Tried rendering esp for " + entity + " that has no color (in renderplayer)!");
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
        this.renderName((EntityPlayer)entity, var2, var4, var6);
    }

    @Override
    protected void preRenderCallback(EntityLiving var1, float var2) {
        this.func_186_b((EntityPlayer)var1, var2);
    }

    @Override
    protected boolean shouldRenderPass(EntityLiving var1, int var2, float var3) {
        return this.setArmorModel((EntityPlayer)var1, var2, var3);
    }

    @Override
    protected void renderEquippedItems(EntityLiving var1, float var2) {
        this.renderSpecials((EntityPlayer)var1, var2);
    }

    @Override
    protected void func_21004_a(EntityLiving var1, float var2, float var3, float var4) {
        this.func_22017_a((EntityPlayer)var1, var2, var3, var4);
    }

    @Override
    protected void func_22012_b(EntityLiving var1, double var2, double var4, double var6) {
        this.func_22016_b((EntityPlayer)var1, var2, var4, var6);
    }

    @Override
    public void doRenderLiving(EntityLiving var1, double var2, double var4, double var6, float var8, float var9) {
        this.func_188_a((EntityPlayer)var1, var2, var4, var6, var8, var9);
    }

    @Override
    public void doRender(Entity var1, double var2, double var4, double var6, float var8, float var9) {
        this.func_188_a((EntityPlayer)var1, var2, var4, var6, var8, var9);
    }
}

