/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Mouse
 *  org.lwjgl.opengl.Display
 *  org.lwjgl.opengl.GL11
 *  org.lwjgl.opengl.GLContext
 *  org.lwjgl.util.glu.GLU
 */
package net.minecraft.src;

import java.nio.FloatBuffer;
import java.util.List;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Block;
import net.minecraft.src.ChunkProviderLoadOrGenerate;
import net.minecraft.src.ClippingHelperImplementation;
import net.minecraft.src.EffectRenderer;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntityRainFX;
import net.minecraft.src.Frustrum;
import net.minecraft.src.GLAllocation;
import net.minecraft.src.IChunkProvider;
import net.minecraft.src.ItemRenderer;
import net.minecraft.src.Material;
import net.minecraft.src.MathHelper;
import net.minecraft.src.MouseFilter;
import net.minecraft.src.MovingObjectPosition;
import net.minecraft.src.PlayerControllerTest;
import net.minecraft.src.RenderGlobal;
import net.minecraft.src.RenderHelper;
import net.minecraft.src.ScaledResolution;
import net.minecraft.src.Vec3D;
import net.minecraft.src.World;
import net.skidcode.gh.maybeaclient.events.EventRegistry;
import net.skidcode.gh.maybeaclient.events.impl.EventWorldRenderPreFog;
import net.skidcode.gh.maybeaclient.hacks.EntityESPHack;
import net.skidcode.gh.maybeaclient.hacks.FOVHack;
import net.skidcode.gh.maybeaclient.hacks.LockTimeHack;
import net.skidcode.gh.maybeaclient.hacks.NoRenderHack;
import net.skidcode.gh.maybeaclient.hacks.SchematicaHack;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.util.glu.GLU;

public class EntityRenderer {
    private Minecraft mc;
    private float farPlaneDistance = 0.0f;
    public ItemRenderer itemRenderer;
    private int field_1386_j;
    private Entity field_1385_k = null;
    private MouseFilter mouseFilterXAxis = new MouseFilter();
    private MouseFilter mouseFilterYAxis = new MouseFilter();
    private MouseFilter field_22233_n = new MouseFilter();
    private MouseFilter field_22232_o = new MouseFilter();
    private MouseFilter field_22231_p = new MouseFilter();
    private MouseFilter field_22229_q = new MouseFilter();
    private float field_22228_r = 4.0f;
    private float field_22227_s = 4.0f;
    private float field_22226_t = 0.0f;
    private float field_22225_u = 0.0f;
    private float field_22224_v = 0.0f;
    private float field_22223_w = 0.0f;
    private float field_22222_x = 0.0f;
    private float field_22221_y = 0.0f;
    private float field_22220_z = 0.0f;
    private float field_22230_A = 0.0f;
    private double cameraZoom = 1.0;
    private double cameraYaw = 0.0;
    private double cameraPitch = 0.0;
    private long prevFrameTime = System.currentTimeMillis();
    private Random random = new Random();
    volatile int field_1394_b = 0;
    volatile int field_1393_c = 0;
    FloatBuffer field_1392_d = GLAllocation.createDirectFloatBuffer(16);
    float fogColorRed;
    float fogColorGreen;
    float fogColorBlue;
    private float field_1382_n;
    private float field_1381_o;
    boolean isHand = false;

    public EntityRenderer(Minecraft var1) {
        this.mc = var1;
        this.itemRenderer = new ItemRenderer(var1);
    }

    public void updateRenderer() {
        this.field_1382_n = this.field_1381_o;
        this.field_22227_s = this.field_22228_r;
        this.field_22225_u = this.field_22226_t;
        this.field_22223_w = this.field_22224_v;
        this.field_22221_y = this.field_22222_x;
        this.field_22230_A = this.field_22220_z;
        if (this.mc.renderViewEntity == null) {
            this.mc.renderViewEntity = this.mc.thePlayer;
        }
        float var1 = this.mc.theWorld.getLightBrightness(MathHelper.floor_double(this.mc.renderViewEntity.posX), MathHelper.floor_double(this.mc.renderViewEntity.posY), MathHelper.floor_double(this.mc.renderViewEntity.posZ));
        float var2 = (float)(3 - this.mc.gameSettings.renderDistance) / 3.0f;
        float var3 = var1 * (1.0f - var2) + var2;
        this.field_1381_o += (var3 - this.field_1381_o) * 0.1f;
        ++this.field_1386_j;
        this.itemRenderer.updateEquippedItem();
        if (this.mc.isRaining) {
            this.addRainParticles();
        }
    }

    public void getMouseOver(float var1) {
        if (this.mc.renderViewEntity != null && this.mc.theWorld != null) {
            double var2 = this.mc.playerController.getBlockReachDistance();
            this.mc.objectMouseOver = this.mc.renderViewEntity.rayTrace(var2, var1);
            double var4 = var2;
            Vec3D var6 = this.mc.renderViewEntity.getPosition(var1);
            if (this.mc.objectMouseOver != null) {
                var4 = this.mc.objectMouseOver.hitVec.distanceTo(var6);
            }
            if (this.mc.playerController instanceof PlayerControllerTest) {
                var2 = 32.0;
                var4 = 32.0;
            } else {
                if (var4 > 3.0) {
                    var4 = 3.0;
                }
                var2 = var4;
            }
            Vec3D var7 = this.mc.renderViewEntity.getLook(var1);
            Vec3D var8 = var6.addVector(var7.xCoord * var2, var7.yCoord * var2, var7.zCoord * var2);
            this.field_1385_k = null;
            float var9 = 1.0f;
            List var10 = this.mc.theWorld.getEntitiesWithinAABBExcludingEntity(this.mc.renderViewEntity, this.mc.renderViewEntity.boundingBox.addCoord(var7.xCoord * var2, var7.yCoord * var2, var7.zCoord * var2).expand(var9, var9, var9));
            double var11 = 0.0;
            int var13 = 0;
            while (var13 < var10.size()) {
                Entity var14 = (Entity)var10.get(var13);
                if (var14.canBeCollidedWith()) {
                    double var18;
                    float var15 = var14.getCollisionBorderSize();
                    AxisAlignedBB var16 = var14.boundingBox.expand(var15, var15, var15);
                    MovingObjectPosition var17 = var16.func_1169_a(var6, var8);
                    if (var16.isVecInside(var6)) {
                        if (0.0 < var11 || var11 == 0.0) {
                            this.field_1385_k = var14;
                            var11 = 0.0;
                        }
                    } else if (var17 != null && ((var18 = var6.distanceTo(var17.hitVec)) < var11 || var11 == 0.0)) {
                        this.field_1385_k = var14;
                        var11 = var18;
                    }
                }
                ++var13;
            }
            if (this.field_1385_k != null && !(this.mc.playerController instanceof PlayerControllerTest)) {
                this.mc.objectMouseOver = new MovingObjectPosition(this.field_1385_k);
            }
        }
    }

    private float func_914_d(float var1) {
        EntityLiving var2 = this.mc.renderViewEntity;
        float var3 = 70.0f;
        if (FOVHack.instance.status & !this.isHand) {
            var3 = FOVHack.instance.fov.value;
        }
        if (var2.isInsideOfMaterial(Material.water)) {
            var3 = var3 == 70.0f ? 60.0f : var3 * 60.0f / 70.0f;
        }
        if (var2.health <= 0) {
            float var4 = (float)var2.deathTime + var1;
            var3 /= (1.0f - 500.0f / (var4 + 500.0f)) * 2.0f + 1.0f;
        }
        return var3 + this.field_22221_y + (this.field_22222_x - this.field_22221_y) * var1;
    }

    private void hurtCameraEffect(float var1) {
        float var4;
        EntityLiving var2 = this.mc.renderViewEntity;
        float var3 = (float)var2.hurtTime - var1;
        if (var2.health <= 0) {
            var4 = (float)var2.deathTime + var1;
            GL11.glRotatef((float)(40.0f - 8000.0f / (var4 + 200.0f)), (float)0.0f, (float)0.0f, (float)1.0f);
        }
        if (var3 >= 0.0f) {
            var3 /= (float)var2.maxHurtTime;
            var3 = MathHelper.sin(var3 * var3 * var3 * var3 * (float)Math.PI);
            var4 = var2.attackedAtYaw;
            GL11.glRotatef((float)(-var4), (float)0.0f, (float)1.0f, (float)0.0f);
            GL11.glRotatef((float)(-var3 * 14.0f), (float)0.0f, (float)0.0f, (float)1.0f);
            GL11.glRotatef((float)var4, (float)0.0f, (float)1.0f, (float)0.0f);
        }
    }

    private void setupViewBobbing(float var1) {
        if (this.mc.renderViewEntity instanceof EntityPlayer) {
            EntityPlayer var2 = (EntityPlayer)this.mc.renderViewEntity;
            float var3 = var2.distanceWalkedModified - var2.prevDistanceWalkedModified;
            float var4 = -(var2.distanceWalkedModified + var3 * var1);
            float var5 = var2.field_775_e + (var2.field_774_f - var2.field_775_e) * var1;
            float var6 = var2.cameraPitch + (var2.field_9328_R - var2.cameraPitch) * var1;
            GL11.glTranslatef((float)(MathHelper.sin(var4 * (float)Math.PI) * var5 * 0.5f), (float)(-Math.abs(MathHelper.cos(var4 * (float)Math.PI) * var5)), (float)0.0f);
            GL11.glRotatef((float)(MathHelper.sin(var4 * (float)Math.PI) * var5 * 3.0f), (float)0.0f, (float)0.0f, (float)1.0f);
            GL11.glRotatef((float)(Math.abs(MathHelper.cos(var4 * (float)Math.PI - 0.2f) * var5) * 5.0f), (float)1.0f, (float)0.0f, (float)0.0f);
            GL11.glRotatef((float)var6, (float)1.0f, (float)0.0f, (float)0.0f);
        }
    }

    private void orientCamera(float var1) {
        EntityLiving var2 = this.mc.renderViewEntity;
        float var3 = var2.yOffset - 1.62f;
        double var4 = var2.prevPosX + (var2.posX - var2.prevPosX) * (double)var1;
        double var6 = var2.prevPosY + (var2.posY - var2.prevPosY) * (double)var1 - (double)var3;
        double var8 = var2.prevPosZ + (var2.posZ - var2.prevPosZ) * (double)var1;
        GL11.glRotatef((float)(this.field_22230_A + (this.field_22220_z - this.field_22230_A) * var1), (float)0.0f, (float)0.0f, (float)1.0f);
        if (var2.isPlayerSleeping()) {
            var3 = (float)((double)var3 + 1.0);
            GL11.glTranslatef((float)0.0f, (float)0.3f, (float)0.0f);
            if (!this.mc.gameSettings.field_22273_E) {
                int var10 = this.mc.theWorld.getBlockId(MathHelper.floor_double(var2.posX), MathHelper.floor_double(var2.posY), MathHelper.floor_double(var2.posZ));
                if (var10 == Block.blockBed.blockID) {
                    int var11 = this.mc.theWorld.getBlockMetadata(MathHelper.floor_double(var2.posX), MathHelper.floor_double(var2.posY), MathHelper.floor_double(var2.posZ));
                    int var12 = var11 & 3;
                    GL11.glRotatef((float)(var12 * 90), (float)0.0f, (float)1.0f, (float)0.0f);
                }
                GL11.glRotatef((float)(var2.prevRotationYaw + (var2.rotationYaw - var2.prevRotationYaw) * var1 + 180.0f), (float)0.0f, (float)-1.0f, (float)0.0f);
                GL11.glRotatef((float)(var2.prevRotationPitch + (var2.rotationPitch - var2.prevRotationPitch) * var1), (float)-1.0f, (float)0.0f, (float)0.0f);
            }
        } else if (this.mc.gameSettings.thirdPersonView) {
            double var27 = this.field_22227_s + (this.field_22228_r - this.field_22227_s) * var1;
            if (this.mc.gameSettings.field_22273_E) {
                float var28 = this.field_22225_u + (this.field_22226_t - this.field_22225_u) * var1;
                float var13 = this.field_22223_w + (this.field_22224_v - this.field_22223_w) * var1;
                GL11.glTranslatef((float)0.0f, (float)0.0f, (float)((float)(-var27)));
                GL11.glRotatef((float)var13, (float)1.0f, (float)0.0f, (float)0.0f);
                GL11.glRotatef((float)var28, (float)0.0f, (float)1.0f, (float)0.0f);
            } else {
                float var28 = var2.rotationYaw;
                float var13 = var2.rotationPitch;
                double var14 = (double)(-MathHelper.sin(var28 / 180.0f * (float)Math.PI) * MathHelper.cos(var13 / 180.0f * (float)Math.PI)) * var27;
                double var16 = (double)(MathHelper.cos(var28 / 180.0f * (float)Math.PI) * MathHelper.cos(var13 / 180.0f * (float)Math.PI)) * var27;
                double var18 = (double)(-MathHelper.sin(var13 / 180.0f * (float)Math.PI)) * var27;
                int var20 = 0;
                while (var20 < 8) {
                    double var25;
                    MovingObjectPosition var24;
                    float var21 = (var20 & 1) * 2 - 1;
                    float var22 = (var20 >> 1 & 1) * 2 - 1;
                    float var23 = (var20 >> 2 & 1) * 2 - 1;
                    if ((var24 = this.mc.theWorld.rayTraceBlocks(Vec3D.createVector(var4 + (double)(var21 *= 0.1f), var6 + (double)(var22 *= 0.1f), var8 + (double)(var23 *= 0.1f)), Vec3D.createVector(var4 - var14 + (double)var21 + (double)var23, var6 - var18 + (double)var22, var8 - var16 + (double)var23))) != null && (var25 = var24.hitVec.distanceTo(Vec3D.createVector(var4, var6, var8))) < var27) {
                        var27 = var25;
                    }
                    ++var20;
                }
                GL11.glRotatef((float)(var2.rotationPitch - var13), (float)1.0f, (float)0.0f, (float)0.0f);
                GL11.glRotatef((float)(var2.rotationYaw - var28), (float)0.0f, (float)1.0f, (float)0.0f);
                GL11.glTranslatef((float)0.0f, (float)0.0f, (float)((float)(-var27)));
                GL11.glRotatef((float)(var28 - var2.rotationYaw), (float)0.0f, (float)1.0f, (float)0.0f);
                GL11.glRotatef((float)(var13 - var2.rotationPitch), (float)1.0f, (float)0.0f, (float)0.0f);
            }
        } else {
            GL11.glTranslatef((float)0.0f, (float)0.0f, (float)-0.1f);
        }
        if (!this.mc.gameSettings.field_22273_E) {
            GL11.glRotatef((float)(var2.prevRotationPitch + (var2.rotationPitch - var2.prevRotationPitch) * var1), (float)1.0f, (float)0.0f, (float)0.0f);
            GL11.glRotatef((float)(var2.prevRotationYaw + (var2.rotationYaw - var2.prevRotationYaw) * var1 + 180.0f), (float)0.0f, (float)1.0f, (float)0.0f);
        }
        GL11.glTranslatef((float)0.0f, (float)var3, (float)0.0f);
    }

    public void func_21152_a(double var1, double var3, double var5) {
        this.cameraZoom = var1;
        this.cameraYaw = var3;
        this.cameraPitch = var5;
    }

    public void resetZoom() {
        this.cameraZoom = 1.0;
    }

    public void setupCameraTransform(float var1, int var2) {
        float var4;
        this.farPlaneDistance = 256 >> this.mc.gameSettings.renderDistance;
        GL11.glMatrixMode((int)5889);
        GL11.glLoadIdentity();
        float var3 = 0.07f;
        if (this.mc.gameSettings.anaglyph) {
            GL11.glTranslatef((float)((float)(-(var2 * 2 - 1)) * var3), (float)0.0f, (float)0.0f);
        }
        if (this.cameraZoom != 1.0) {
            GL11.glTranslatef((float)((float)this.cameraYaw), (float)((float)(-this.cameraPitch)), (float)0.0f);
            GL11.glScaled((double)this.cameraZoom, (double)this.cameraZoom, (double)1.0);
            GLU.gluPerspective((float)this.func_914_d(var1), (float)((float)this.mc.displayWidth / (float)this.mc.displayHeight), (float)0.05f, (float)this.farPlaneDistance);
        } else {
            GLU.gluPerspective((float)this.func_914_d(var1), (float)((float)this.mc.displayWidth / (float)this.mc.displayHeight), (float)0.05f, (float)this.farPlaneDistance);
        }
        GL11.glMatrixMode((int)5888);
        GL11.glLoadIdentity();
        if (this.mc.gameSettings.anaglyph) {
            GL11.glTranslatef((float)((float)(var2 * 2 - 1) * 0.1f), (float)0.0f, (float)0.0f);
        }
        this.hurtCameraEffect(var1);
        if (this.mc.gameSettings.viewBobbing) {
            this.setupViewBobbing(var1);
        }
        if ((var4 = this.mc.thePlayer.prevTimeInPortal + (this.mc.thePlayer.timeInPortal - this.mc.thePlayer.prevTimeInPortal) * var1) > 0.0f) {
            float var5 = 5.0f / (var4 * var4 + 5.0f) - var4 * 0.04f;
            var5 *= var5;
            GL11.glRotatef((float)(var4 * var4 * 1500.0f), (float)0.0f, (float)1.0f, (float)1.0f);
            GL11.glScalef((float)(1.0f / var5), (float)1.0f, (float)1.0f);
            GL11.glRotatef((float)(-var4 * var4 * 1500.0f), (float)0.0f, (float)1.0f, (float)1.0f);
        }
        this.orientCamera(var1);
    }

    private void func_4135_b(float var1, int var2) {
        this.isHand = true;
        GL11.glMatrixMode((int)5889);
        GL11.glLoadIdentity();
        GLU.gluPerspective((float)this.func_914_d(var1), (float)((float)this.mc.displayWidth / (float)this.mc.displayHeight), (float)0.05f, (float)(this.farPlaneDistance * 2.0f));
        GL11.glMatrixMode((int)5888);
        GL11.glLoadIdentity();
        if (this.mc.gameSettings.anaglyph) {
            GL11.glTranslatef((float)((float)(var2 * 2 - 1) * 0.1f), (float)0.0f, (float)0.0f);
        }
        GL11.glPushMatrix();
        this.hurtCameraEffect(var1);
        if (this.mc.gameSettings.viewBobbing) {
            this.setupViewBobbing(var1);
        }
        if (!(this.mc.gameSettings.thirdPersonView || this.mc.renderViewEntity.isPlayerSleeping() || this.mc.gameSettings.hideGUI)) {
            this.itemRenderer.renderItemInFirstPerson(var1);
        }
        GL11.glPopMatrix();
        if (!this.mc.gameSettings.thirdPersonView && !this.mc.renderViewEntity.isPlayerSleeping()) {
            this.itemRenderer.renderOverlays(var1);
            this.hurtCameraEffect(var1);
        }
        if (this.mc.gameSettings.viewBobbing) {
            this.setupViewBobbing(var1);
        }
        this.isHand = false;
    }

    public void updateCameraAndRender(float var1) {
        if (!Display.isActive()) {
            if (System.currentTimeMillis() - this.prevFrameTime > 500L) {
                this.mc.displayInGameMenu();
            }
        } else {
            this.prevFrameTime = System.currentTimeMillis();
        }
        if (this.mc.inGameHasFocus) {
            this.mc.mouseHelper.mouseXYChange();
            float var2 = this.mc.gameSettings.mouseSensitivity * 0.6f + 0.2f;
            float var3 = var2 * var2 * var2 * 8.0f;
            float var4 = (float)this.mc.mouseHelper.deltaX * var3;
            float var5 = (float)this.mc.mouseHelper.deltaY * var3;
            int var6 = 1;
            if (this.mc.gameSettings.invertMouse) {
                var6 = -1;
            }
            if (this.mc.gameSettings.smoothCamera) {
                var4 = this.mouseFilterXAxis.func_22386_a(var4, 0.05f * var3);
                var5 = this.mouseFilterYAxis.func_22386_a(var5, 0.05f * var3);
            }
            this.mc.thePlayer.func_346_d(var4, var5 * (float)var6);
        }
        if (!this.mc.field_6307_v) {
            ScaledResolution var7 = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
            int var8 = var7.getScaledWidth();
            int var9 = var7.getScaledHeight();
            int var10 = Mouse.getX() * var8 / this.mc.displayWidth;
            int var11 = var9 - Mouse.getY() * var9 / this.mc.displayHeight - 1;
            if (this.mc.theWorld != null) {
                this.renderWorld(var1);
                if (!this.mc.gameSettings.hideGUI || this.mc.currentScreen != null) {
                    this.mc.ingameGUI.renderGameOverlay(var1, this.mc.currentScreen != null, var10, var11);
                }
            } else {
                GL11.glViewport((int)0, (int)0, (int)this.mc.displayWidth, (int)this.mc.displayHeight);
                GL11.glMatrixMode((int)5889);
                GL11.glLoadIdentity();
                GL11.glMatrixMode((int)5888);
                GL11.glLoadIdentity();
                this.setupScaledResolution();
            }
            if (this.mc.currentScreen != null) {
                GL11.glClear((int)256);
                this.mc.currentScreen.drawScreen(var10, var11, var1);
                if (this.mc.currentScreen != null && this.mc.currentScreen.field_25091_h != null) {
                    this.mc.currentScreen.field_25091_h.func_25087_a(var1);
                }
            }
        }
    }

    public void renderWorld(float var1) {
        int var14;
        long oldTime = this.mc.theWorld.getWorldTime();
        if (LockTimeHack.INSTANCE.status) {
            this.mc.theWorld.worldInfo.setWorldTime(LockTimeHack.INSTANCE.lockedTime.value);
        }
        if (this.mc.renderViewEntity == null) {
            this.mc.renderViewEntity = this.mc.thePlayer;
        }
        this.getMouseOver(var1);
        EntityLiving var2 = this.mc.renderViewEntity;
        RenderGlobal var3 = this.mc.renderGlobal;
        EffectRenderer var4 = this.mc.effectRenderer;
        double var5 = var2.lastTickPosX + (var2.posX - var2.lastTickPosX) * (double)var1;
        double var7 = var2.lastTickPosY + (var2.posY - var2.lastTickPosY) * (double)var1;
        double var9 = var2.lastTickPosZ + (var2.posZ - var2.lastTickPosZ) * (double)var1;
        IChunkProvider var11 = this.mc.theWorld.getIChunkProvider();
        if (var11 instanceof ChunkProviderLoadOrGenerate) {
            ChunkProviderLoadOrGenerate var12 = (ChunkProviderLoadOrGenerate)var11;
            int var13 = MathHelper.floor_float((int)var5) >> 4;
            var14 = MathHelper.floor_float((int)var9) >> 4;
            var12.setCurrentChunkOver(var13, var14);
        }
        int var15 = 0;
        while (var15 < 2) {
            EntityPlayer var17;
            if (this.mc.gameSettings.anaglyph) {
                if (var15 == 0) {
                    GL11.glColorMask((boolean)false, (boolean)true, (boolean)true, (boolean)false);
                } else {
                    GL11.glColorMask((boolean)true, (boolean)false, (boolean)false, (boolean)false);
                }
            }
            GL11.glViewport((int)0, (int)0, (int)this.mc.displayWidth, (int)this.mc.displayHeight);
            this.updateFogColor(var1);
            GL11.glClear((int)16640);
            GL11.glEnable((int)2884);
            this.setupCameraTransform(var1, var15);
            ClippingHelperImplementation.getInstance();
            if (this.mc.gameSettings.renderDistance < 2) {
                this.setupFog(-1);
                var3.renderSky(var1);
            }
            GL11.glEnable((int)2912);
            this.setupFog(1);
            if (this.mc.gameSettings.ambientOcclusion) {
                GL11.glShadeModel((int)7425);
            }
            Frustrum var16 = new Frustrum();
            var16.setPosition(var5, var7, var9);
            this.mc.renderGlobal.clipRenderersByFrustrum(var16, var1);
            this.mc.renderGlobal.updateRenderers(var2, false);
            this.setupFog(0);
            GL11.glEnable((int)2912);
            GL11.glBindTexture((int)3553, (int)this.mc.renderEngine.getTexture("/terrain.png"));
            RenderHelper.disableStandardItemLighting();
            var3.sortAndRender(var2, 0, var1);
            GL11.glShadeModel((int)7424);
            if (!EntityESPHack.instance.status || EntityESPHack.instance.renderingOrder.value) {
                RenderHelper.enableStandardItemLighting();
                var3.renderEntities(var2.getPosition(var1), var16, var1);
                var4.func_1187_b(var2, var1);
                RenderHelper.disableStandardItemLighting();
            } else {
                RenderHelper.enableStandardItemLighting();
                var4.func_1187_b(var2, var1);
                RenderHelper.disableStandardItemLighting();
            }
            this.setupFog(0);
            var4.renderParticles(var2, var1);
            if (this.mc.objectMouseOver != null && var2.isInsideOfMaterial(Material.water) && var2 instanceof EntityPlayer) {
                var17 = (EntityPlayer)var2;
                GL11.glDisable((int)3008);
                var3.func_959_a(var17, this.mc.objectMouseOver, 0, var17.inventory.getCurrentItem(), var1);
                var3.drawSelectionBox(var17, this.mc.objectMouseOver, 0, var17.inventory.getCurrentItem(), var1);
                GL11.glEnable((int)3008);
            }
            GL11.glBlendFunc((int)770, (int)771);
            this.setupFog(0);
            GL11.glEnable((int)3042);
            GL11.glDisable((int)2884);
            GL11.glBindTexture((int)3553, (int)this.mc.renderEngine.getTexture("/terrain.png"));
            if (this.mc.gameSettings.fancyGraphics) {
                GL11.glColorMask((boolean)false, (boolean)false, (boolean)false, (boolean)false);
                var14 = var3.sortAndRender(var2, 1, var1);
                GL11.glColorMask((boolean)true, (boolean)true, (boolean)true, (boolean)true);
                if (this.mc.gameSettings.anaglyph) {
                    if (var15 == 0) {
                        GL11.glColorMask((boolean)false, (boolean)true, (boolean)true, (boolean)false);
                    } else {
                        GL11.glColorMask((boolean)true, (boolean)false, (boolean)false, (boolean)false);
                    }
                }
                if (var14 > 0) {
                    var3.func_944_a(1, var1);
                }
            } else {
                var3.sortAndRender(var2, 1, var1);
            }
            if (EntityESPHack.instance.status && !EntityESPHack.instance.renderingOrder.value) {
                RenderHelper.enableStandardItemLighting();
                var3.renderEntities(var2.getPosition(var1), var16, var1);
                RenderHelper.disableStandardItemLighting();
                GL11.glBindTexture((int)3553, (int)this.mc.renderEngine.getTexture("/terrain.png"));
            }
            GL11.glDepthMask((boolean)true);
            GL11.glEnable((int)2884);
            GL11.glDisable((int)3042);
            if (this.cameraZoom == 1.0 && var2 instanceof EntityPlayer && this.mc.objectMouseOver != null && !var2.isInsideOfMaterial(Material.water)) {
                var17 = (EntityPlayer)var2;
                GL11.glDisable((int)3008);
                var3.func_959_a(var17, this.mc.objectMouseOver, 0, var17.inventory.getCurrentItem(), var1);
                var3.drawSelectionBox(var17, this.mc.objectMouseOver, 0, var17.inventory.getCurrentItem(), var1);
                GL11.glEnable((int)3008);
            }
            if (SchematicaHack.instance.status) {
                SchematicaHack.instance.render.onRender(this, var1);
            }
            GL11.glDisable((int)2912);
            EventWorldRenderPreFog ev = new EventWorldRenderPreFog();
            EventRegistry.handleEvent(ev);
            this.setupFog(0);
            GL11.glEnable((int)2912);
            if (!NoRenderHack.instance.status || !NoRenderHack.instance.clouds.value) {
                var3.renderClouds(var1);
            }
            GL11.glDisable((int)2912);
            this.setupFog(1);
            if (this.cameraZoom == 1.0) {
                GL11.glClear((int)256);
                this.func_4135_b(var1, var15);
            }
            if (!this.mc.gameSettings.anaglyph) {
                if (LockTimeHack.INSTANCE.status) {
                    this.mc.theWorld.worldInfo.setWorldTime(oldTime);
                }
                return;
            }
            ++var15;
        }
        GL11.glColorMask((boolean)true, (boolean)true, (boolean)true, (boolean)false);
        if (LockTimeHack.INSTANCE.status) {
            this.mc.theWorld.worldInfo.setWorldTime(oldTime);
        }
    }

    private void addRainParticles() {
        if (this.mc.gameSettings.fancyGraphics) {
            EntityLiving var1 = this.mc.renderViewEntity;
            World var2 = this.mc.theWorld;
            int var3 = MathHelper.floor_double(var1.posX);
            int var4 = MathHelper.floor_double(var1.posY);
            int var5 = MathHelper.floor_double(var1.posZ);
            int var6 = 16;
            int var7 = 0;
            while (var7 < 150) {
                int var8 = var3 + this.random.nextInt(var6) - this.random.nextInt(var6);
                int var9 = var5 + this.random.nextInt(var6) - this.random.nextInt(var6);
                int var10 = var2.func_696_e(var8, var9);
                int var11 = var2.getBlockId(var8, var10 - 1, var9);
                if (var10 <= var4 + var6 && var10 >= var4 - var6) {
                    float var12 = this.random.nextFloat();
                    float var13 = this.random.nextFloat();
                    if (var11 > 0) {
                        this.mc.effectRenderer.addEffect(new EntityRainFX(var2, (float)var8 + var12, (double)((float)var10 + 0.1f) - Block.blocksList[var11].minY, (float)var9 + var13));
                    }
                }
                ++var7;
            }
        }
    }

    public void setupScaledResolution() {
        ScaledResolution var1 = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
        GL11.glClear((int)256);
        GL11.glMatrixMode((int)5889);
        GL11.glLoadIdentity();
        GL11.glOrtho((double)0.0, (double)var1.field_25121_a, (double)var1.field_25120_b, (double)0.0, (double)1000.0, (double)3000.0);
        GL11.glMatrixMode((int)5888);
        GL11.glLoadIdentity();
        GL11.glTranslatef((float)0.0f, (float)0.0f, (float)-2000.0f);
    }

    private void updateFogColor(float var1) {
        World var2 = this.mc.theWorld;
        EntityLiving var3 = this.mc.renderViewEntity;
        float var4 = 1.0f / (float)(4 - this.mc.gameSettings.renderDistance);
        var4 = 1.0f - (float)Math.pow(var4, 0.25);
        Vec3D var5 = var2.func_4079_a(this.mc.renderViewEntity, var1);
        float var6 = (float)var5.xCoord;
        float var7 = (float)var5.yCoord;
        float var8 = (float)var5.zCoord;
        Vec3D var9 = var2.getFogColor(var1);
        this.fogColorRed = (float)var9.xCoord;
        this.fogColorGreen = (float)var9.yCoord;
        this.fogColorBlue = (float)var9.zCoord;
        this.fogColorRed += (var6 - this.fogColorRed) * var4;
        this.fogColorGreen += (var7 - this.fogColorGreen) * var4;
        this.fogColorBlue += (var8 - this.fogColorBlue) * var4;
        if (var3.isInsideOfMaterial(Material.water)) {
            this.fogColorRed = 0.02f;
            this.fogColorGreen = 0.02f;
            this.fogColorBlue = 0.2f;
        } else if (var3.isInsideOfMaterial(Material.lava)) {
            this.fogColorRed = 0.6f;
            this.fogColorGreen = 0.1f;
            this.fogColorBlue = 0.0f;
        }
        float var10 = this.field_1382_n + (this.field_1381_o - this.field_1382_n) * var1;
        this.fogColorRed *= var10;
        this.fogColorGreen *= var10;
        this.fogColorBlue *= var10;
        if (this.mc.gameSettings.anaglyph) {
            float var11 = (this.fogColorRed * 30.0f + this.fogColorGreen * 59.0f + this.fogColorBlue * 11.0f) / 100.0f;
            float var12 = (this.fogColorRed * 30.0f + this.fogColorGreen * 70.0f) / 100.0f;
            float var13 = (this.fogColorRed * 30.0f + this.fogColorBlue * 70.0f) / 100.0f;
            this.fogColorRed = var11;
            this.fogColorGreen = var12;
            this.fogColorBlue = var13;
        }
        GL11.glClearColor((float)this.fogColorRed, (float)this.fogColorGreen, (float)this.fogColorBlue, (float)0.0f);
    }

    private void setupFog(int var1) {
        if (NoRenderHack.instance.status && NoRenderHack.instance.fog.value) {
            GL11.glFogf((int)2915, (float)-1.0f);
            GL11.glFogf((int)2916, (float)-1.0f);
            GL11.glFogf((int)2914, (float)0.0f);
            GL11.glFogi((int)2917, (int)2048);
            return;
        }
        EntityLiving var2 = this.mc.renderViewEntity;
        GL11.glFog((int)2918, (FloatBuffer)this.func_908_a(this.fogColorRed, this.fogColorGreen, this.fogColorBlue, 1.0f));
        GL11.glNormal3f((float)0.0f, (float)-1.0f, (float)0.0f);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        if (var2.isInsideOfMaterial(Material.water)) {
            GL11.glFogi((int)2917, (int)2048);
            GL11.glFogf((int)2914, (float)0.1f);
            float var3 = 0.4f;
            float var4 = 0.4f;
            float var5 = 0.9f;
            if (this.mc.gameSettings.anaglyph) {
                float var6 = (var3 * 30.0f + var4 * 59.0f + var5 * 11.0f) / 100.0f;
                float var7 = (var3 * 30.0f + var4 * 70.0f) / 100.0f;
                float f = (var3 * 30.0f + var5 * 70.0f) / 100.0f;
            }
        } else if (var2.isInsideOfMaterial(Material.lava)) {
            GL11.glFogi((int)2917, (int)2048);
            GL11.glFogf((int)2914, (float)2.0f);
            float var3 = 0.4f;
            float var4 = 0.3f;
            float var5 = 0.3f;
            if (this.mc.gameSettings.anaglyph) {
                float var6 = (var3 * 30.0f + var4 * 59.0f + var5 * 11.0f) / 100.0f;
                float var7 = (var3 * 30.0f + var4 * 70.0f) / 100.0f;
                float f = (var3 * 30.0f + var5 * 70.0f) / 100.0f;
            }
        } else {
            GL11.glFogi((int)2917, (int)9729);
            GL11.glFogf((int)2915, (float)(this.farPlaneDistance * 0.25f));
            GL11.glFogf((int)2916, (float)this.farPlaneDistance);
            if (var1 < 0) {
                GL11.glFogf((int)2915, (float)0.0f);
                GL11.glFogf((int)2916, (float)(this.farPlaneDistance * 0.8f));
            }
            if (GLContext.getCapabilities().GL_NV_fog_distance) {
                GL11.glFogi((int)34138, (int)34139);
            }
            if (this.mc.theWorld.worldProvider.field_4220_c) {
                GL11.glFogf((int)2915, (float)0.0f);
            }
        }
        GL11.glEnable((int)2903);
        GL11.glColorMaterial((int)1028, (int)4608);
    }

    private FloatBuffer func_908_a(float var1, float var2, float var3, float var4) {
        this.field_1392_d.clear();
        this.field_1392_d.put(var1).put(var2).put(var3).put(var4);
        this.field_1392_d.flip();
        return this.field_1392_d;
    }
}

