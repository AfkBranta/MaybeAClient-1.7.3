/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.ARBOcclusionQuery
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.src;

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Block;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityBubbleFX;
import net.minecraft.src.EntityExplodeFX;
import net.minecraft.src.EntityFlameFX;
import net.minecraft.src.EntityHeartFX;
import net.minecraft.src.EntityLavaFX;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityNoteFX;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntityPortalFX;
import net.minecraft.src.EntityReddustFX;
import net.minecraft.src.EntitySlimeFX;
import net.minecraft.src.EntitySmokeFX;
import net.minecraft.src.EntitySorter;
import net.minecraft.src.EntitySplashFX;
import net.minecraft.src.EnumMovingObjectType;
import net.minecraft.src.GLAllocation;
import net.minecraft.src.ICamera;
import net.minecraft.src.IWorldAccess;
import net.minecraft.src.ImageBufferDownload;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.MathHelper;
import net.minecraft.src.MovingObjectPosition;
import net.minecraft.src.RenderBlocks;
import net.minecraft.src.RenderEngine;
import net.minecraft.src.RenderList;
import net.minecraft.src.RenderManager;
import net.minecraft.src.RenderSorter;
import net.minecraft.src.Tessellator;
import net.minecraft.src.TileEntity;
import net.minecraft.src.TileEntityRenderer;
import net.minecraft.src.Vec3D;
import net.minecraft.src.World;
import net.minecraft.src.WorldRenderer;
import net.skidcode.gh.maybeaclient.hacks.EntityESPHack;
import net.skidcode.gh.maybeaclient.hacks.TracersHack;
import org.lwjgl.opengl.ARBOcclusionQuery;
import org.lwjgl.opengl.GL11;

public class RenderGlobal
implements IWorldAccess {
    public List tileEntities = new ArrayList();
    private World worldObj;
    private RenderEngine renderEngine;
    private List worldRenderersToUpdate = new ArrayList();
    private WorldRenderer[] sortedWorldRenderers;
    private WorldRenderer[] worldRenderers;
    private int renderChunksWide;
    private int renderChunksTall;
    private int renderChunksDeep;
    private int field_1440_s;
    private Minecraft mc;
    private RenderBlocks field_1438_u;
    private IntBuffer field_1437_v;
    private boolean occlusionEnabled = false;
    private int cloudOffsetX = 0;
    private int starGLCallList;
    private int field_1433_z;
    private int field_1432_A;
    private int field_1431_B;
    private int field_1430_C;
    private int field_1429_D;
    private int field_1428_E;
    private int field_1427_F;
    private int field_1426_G;
    private int renderDistance = -1;
    private int field_1424_I = 2;
    private int field_1423_J;
    private int field_1422_K;
    private int field_1421_L;
    int[] field_1457_b = new int[50000];
    IntBuffer field_1456_c = GLAllocation.createDirectIntBuffer(64);
    private int renderersLoaded;
    private int renderersBeingClipped;
    private int renderersBeingOccluded;
    private int renderersBeingRendered;
    private int renderersSkippingRenderPass;
    private int field_21156_R;
    private List field_1415_R = new ArrayList();
    private RenderList[] field_1414_S = new RenderList[]{new RenderList(), new RenderList(), new RenderList(), new RenderList()};
    int field_1455_d = 0;
    int field_1454_e = GLAllocation.generateDisplayLists(1);
    double prevSortX = -9999.0;
    double prevSortY = -9999.0;
    double prevSortZ = -9999.0;
    public float field_1450_i;
    int frustrumCheckOffset = 0;

    public RenderGlobal(Minecraft var1, RenderEngine var2) {
        int var9;
        this.mc = var1;
        this.renderEngine = var2;
        int var3 = 64;
        this.field_1440_s = GLAllocation.generateDisplayLists(var3 * var3 * var3 * 3);
        this.occlusionEnabled = var1.func_6251_l().checkARBOcclusion();
        if (this.occlusionEnabled) {
            this.field_1456_c.clear();
            this.field_1437_v = GLAllocation.createDirectIntBuffer(var3 * var3 * var3);
            this.field_1437_v.clear();
            this.field_1437_v.position(0);
            this.field_1437_v.limit(var3 * var3 * var3);
            ARBOcclusionQuery.glGenQueriesARB((IntBuffer)this.field_1437_v);
        }
        this.starGLCallList = GLAllocation.generateDisplayLists(3);
        GL11.glPushMatrix();
        GL11.glNewList((int)this.starGLCallList, (int)4864);
        this.renderStars();
        GL11.glEndList();
        GL11.glPopMatrix();
        Tessellator var4 = Tessellator.instance;
        this.field_1433_z = this.starGLCallList + 1;
        GL11.glNewList((int)this.field_1433_z, (int)4864);
        int var6 = 64;
        int var7 = 256 / var6 + 2;
        float var5 = 16.0f;
        int var8 = -var6 * var7;
        while (var8 <= var6 * var7) {
            var9 = -var6 * var7;
            while (var9 <= var6 * var7) {
                var4.startDrawingQuads();
                var4.addVertex(var8 + 0, var5, var9 + 0);
                var4.addVertex(var8 + var6, var5, var9 + 0);
                var4.addVertex(var8 + var6, var5, var9 + var6);
                var4.addVertex(var8 + 0, var5, var9 + var6);
                var4.draw();
                var9 += var6;
            }
            var8 += var6;
        }
        GL11.glEndList();
        this.field_1432_A = this.starGLCallList + 2;
        GL11.glNewList((int)this.field_1432_A, (int)4864);
        var5 = -16.0f;
        var4.startDrawingQuads();
        var8 = -var6 * var7;
        while (var8 <= var6 * var7) {
            var9 = -var6 * var7;
            while (var9 <= var6 * var7) {
                var4.addVertex(var8 + var6, var5, var9 + 0);
                var4.addVertex(var8 + 0, var5, var9 + 0);
                var4.addVertex(var8 + 0, var5, var9 + var6);
                var4.addVertex(var8 + var6, var5, var9 + var6);
                var9 += var6;
            }
            var8 += var6;
        }
        var4.draw();
        GL11.glEndList();
    }

    private void renderStars() {
        Random var1 = new Random(10842L);
        Tessellator var2 = Tessellator.instance;
        var2.startDrawingQuads();
        int var3 = 0;
        while (var3 < 1500) {
            double var4 = var1.nextFloat() * 2.0f - 1.0f;
            double var6 = var1.nextFloat() * 2.0f - 1.0f;
            double var8 = var1.nextFloat() * 2.0f - 1.0f;
            double var10 = 0.25f + var1.nextFloat() * 0.25f;
            double var12 = var4 * var4 + var6 * var6 + var8 * var8;
            if (var12 < 1.0 && var12 > 0.01) {
                var12 = 1.0 / Math.sqrt(var12);
                double var14 = (var4 *= var12) * 100.0;
                double var16 = (var6 *= var12) * 100.0;
                double var18 = (var8 *= var12) * 100.0;
                double var20 = Math.atan2(var4, var8);
                double var22 = Math.sin(var20);
                double var24 = Math.cos(var20);
                double var26 = Math.atan2(Math.sqrt(var4 * var4 + var8 * var8), var6);
                double var28 = Math.sin(var26);
                double var30 = Math.cos(var26);
                double var32 = var1.nextDouble() * Math.PI * 2.0;
                double var34 = Math.sin(var32);
                double var36 = Math.cos(var32);
                int var38 = 0;
                while (var38 < 4) {
                    double var39 = 0.0;
                    double var41 = (double)((var38 & 2) - 1) * var10;
                    double var43 = (double)((var38 + 1 & 2) - 1) * var10;
                    double var47 = var41 * var36 - var43 * var34;
                    double var49 = var43 * var36 + var41 * var34;
                    double var53 = var47 * var28 + var39 * var30;
                    double var55 = var39 * var28 - var47 * var30;
                    double var57 = var55 * var22 - var49 * var24;
                    double var61 = var49 * var22 + var55 * var24;
                    var2.addVertex(var14 + var57, var16 + var53, var18 + var61);
                    ++var38;
                }
            }
            ++var3;
        }
        var2.draw();
    }

    public void func_946_a(World var1) {
        if (this.worldObj != null) {
            this.worldObj.removeWorldAccess(this);
        }
        this.prevSortX = -9999.0;
        this.prevSortY = -9999.0;
        this.prevSortZ = -9999.0;
        RenderManager.instance.func_852_a(var1);
        this.worldObj = var1;
        this.field_1438_u = new RenderBlocks(var1);
        if (var1 != null) {
            var1.addWorldAccess(this);
            this.loadRenderers();
        }
    }

    public void loadRenderers() {
        EntityLiving var7;
        int var1;
        Block.leaves.setGraphicsLevel(this.mc.gameSettings.fancyGraphics);
        this.renderDistance = this.mc.gameSettings.renderDistance;
        if (this.worldRenderers != null) {
            var1 = 0;
            while (var1 < this.worldRenderers.length) {
                this.worldRenderers[var1].func_1204_c();
                ++var1;
            }
        }
        if ((var1 = 64 << 3 - this.renderDistance) > 400) {
            var1 = 400;
        }
        this.renderChunksWide = var1 / 16 + 1;
        this.renderChunksTall = 8;
        this.renderChunksDeep = var1 / 16 + 1;
        this.worldRenderers = new WorldRenderer[this.renderChunksWide * this.renderChunksTall * this.renderChunksDeep];
        this.sortedWorldRenderers = new WorldRenderer[this.renderChunksWide * this.renderChunksTall * this.renderChunksDeep];
        int var2 = 0;
        int var3 = 0;
        this.field_1431_B = 0;
        this.field_1430_C = 0;
        this.field_1429_D = 0;
        this.field_1428_E = this.renderChunksWide;
        this.field_1427_F = this.renderChunksTall;
        this.field_1426_G = this.renderChunksDeep;
        int var4 = 0;
        while (var4 < this.worldRenderersToUpdate.size()) {
            ((WorldRenderer)this.worldRenderersToUpdate.get((int)var4)).needsUpdate = false;
            ++var4;
        }
        this.worldRenderersToUpdate.clear();
        this.tileEntities.clear();
        var4 = 0;
        while (var4 < this.renderChunksWide) {
            int var5 = 0;
            while (var5 < this.renderChunksTall) {
                int var6 = 0;
                while (var6 < this.renderChunksDeep) {
                    this.worldRenderers[(var6 * this.renderChunksTall + var5) * this.renderChunksWide + var4] = new WorldRenderer(this.worldObj, this.tileEntities, var4 * 16, var5 * 16, var6 * 16, 16, this.field_1440_s + var2);
                    if (this.occlusionEnabled) {
                        this.worldRenderers[(var6 * this.renderChunksTall + var5) * this.renderChunksWide + var4].field_1732_z = this.field_1437_v.get(var3);
                    }
                    this.worldRenderers[(var6 * this.renderChunksTall + var5) * this.renderChunksWide + var4].isWaitingOnOcclusionQuery = false;
                    this.worldRenderers[(var6 * this.renderChunksTall + var5) * this.renderChunksWide + var4].isVisible = true;
                    this.worldRenderers[(var6 * this.renderChunksTall + var5) * this.renderChunksWide + var4].isInFrustum = true;
                    this.worldRenderers[(var6 * this.renderChunksTall + var5) * this.renderChunksWide + var4].field_1735_w = var3++;
                    this.worldRenderers[(var6 * this.renderChunksTall + var5) * this.renderChunksWide + var4].markDirty();
                    this.sortedWorldRenderers[(var6 * this.renderChunksTall + var5) * this.renderChunksWide + var4] = this.worldRenderers[(var6 * this.renderChunksTall + var5) * this.renderChunksWide + var4];
                    this.worldRenderersToUpdate.add(this.worldRenderers[(var6 * this.renderChunksTall + var5) * this.renderChunksWide + var4]);
                    var2 += 3;
                    ++var6;
                }
                ++var5;
            }
            ++var4;
        }
        if (this.worldObj != null && (var7 = this.mc.renderViewEntity) != null) {
            this.markRenderersForNewPosition(MathHelper.floor_double(var7.posX), MathHelper.floor_double(var7.posY), MathHelper.floor_double(var7.posZ));
            Arrays.sort(this.sortedWorldRenderers, new EntitySorter(var7));
        }
        this.field_1424_I = 2;
    }

    public void renderEntities(Vec3D var1, ICamera var2, float var3) {
        if (this.field_1424_I > 0) {
            --this.field_1424_I;
        } else {
            TileEntityRenderer.instance.cacheActiveRenderInfo(this.worldObj, this.renderEngine, this.mc.fontRenderer, this.mc.renderViewEntity, var3);
            RenderManager.instance.cacheActiveRenderInfo(this.worldObj, this.renderEngine, this.mc.fontRenderer, this.mc.renderViewEntity, this.mc.gameSettings, var3);
            this.field_1423_J = 0;
            this.field_1422_K = 0;
            this.field_1421_L = 0;
            EntityLiving var4 = this.mc.renderViewEntity;
            RenderManager.renderPosX = var4.lastTickPosX + (var4.posX - var4.lastTickPosX) * (double)var3;
            RenderManager.renderPosY = var4.lastTickPosY + (var4.posY - var4.lastTickPosY) * (double)var3;
            RenderManager.renderPosZ = var4.lastTickPosZ + (var4.posZ - var4.lastTickPosZ) * (double)var3;
            TileEntityRenderer.staticPlayerX = var4.lastTickPosX + (var4.posX - var4.lastTickPosX) * (double)var3;
            TileEntityRenderer.staticPlayerY = var4.lastTickPosY + (var4.posY - var4.lastTickPosY) * (double)var3;
            TileEntityRenderer.staticPlayerZ = var4.lastTickPosZ + (var4.posZ - var4.lastTickPosZ) * (double)var3;
            List var5 = this.worldObj.getLoadedEntityList();
            this.field_1423_J = var5.size();
            Vec3D forward = null;
            if (TracersHack.instance.status) {
                forward = new Vec3D(0.0, 0.0, 1.0);
                forward.rotateAroundX((float)(-Math.toRadians(this.mc.thePlayer.rotationPitch)));
                forward.rotateAroundY((float)(-Math.toRadians(this.mc.thePlayer.rotationYaw)));
            }
            EntityESPHack.allowRendering = true;
            int var6 = 0;
            while (var6 < var5.size()) {
                int y;
                Entity var7 = (Entity)var5.get(var6);
                if (var7 instanceof EntityPlayer && TracersHack.instance.status && var7.entityId != this.mc.thePlayer.entityId) {
                    boolean bobbing = this.mc.gameSettings.viewBobbing;
                    this.mc.gameSettings.viewBobbing = false;
                    this.mc.entityRenderer.setupCameraTransform(var3, 0);
                    double d2 = var7.lastTickPosX + (var7.posX - var7.lastTickPosX) * (double)var3;
                    double d3 = var7.lastTickPosY + (var7.posY - var7.lastTickPosY) * (double)var3 + (double)(var7.height / 2.0f);
                    double d4 = var7.lastTickPosZ + (var7.posZ - var7.lastTickPosZ) * (double)var3;
                    double diffX = d2 - RenderManager.renderPosX;
                    double diffY = d3 - RenderManager.renderPosY;
                    double diffZ = d4 - RenderManager.renderPosZ;
                    GL11.glPushMatrix();
                    GL11.glDisable((int)3553);
                    GL11.glDisable((int)3008);
                    GL11.glEnable((int)3042);
                    GL11.glDisable((int)2929);
                    GL11.glBlendFunc((int)770, (int)771);
                    GL11.glColor4f((float)TracersHack.instance.color.red, (float)TracersHack.instance.color.green, (float)TracersHack.instance.color.blue, (float)1.0f);
                    GL11.glLineWidth((float)(TracersHack.instance.width.value > 0.0f ? TracersHack.instance.width.value : 0.1f));
                    Tessellator.instance.startDrawing(1);
                    Tessellator.instance.addVertex(forward.xCoord, forward.yCoord, forward.zCoord);
                    Tessellator.instance.addVertex(diffX, diffY, diffZ);
                    Tessellator.instance.draw();
                    GL11.glEnable((int)2929);
                    GL11.glEnable((int)3008);
                    GL11.glEnable((int)3553);
                    GL11.glDisable((int)3042);
                    GL11.glPopMatrix();
                    this.mc.gameSettings.viewBobbing = bobbing;
                    this.mc.entityRenderer.setupCameraTransform(var3, 0);
                }
                if ((y = MathHelper.floor_double(var7.posY)) > 127) {
                    y = 127;
                }
                if (EntityESPHack.instance.status && var7 != this.mc.renderViewEntity && var2.isBoundingBoxInFrustum(var7.boundingBox) || var7.isInRangeToRenderVec3D(var1) && var2.isBoundingBoxInFrustum(var7.boundingBox) && (var7 != this.mc.renderViewEntity || this.mc.gameSettings.thirdPersonView || this.mc.renderViewEntity.isPlayerSleeping()) && this.worldObj.blockExists(MathHelper.floor_double(var7.posX), y, MathHelper.floor_double(var7.posZ))) {
                    ++this.field_1422_K;
                    RenderManager.instance.renderEntity(var7, var3);
                }
                ++var6;
            }
            EntityESPHack.allowRendering = false;
            var6 = 0;
            while (var6 < this.tileEntities.size()) {
                TileEntityRenderer.instance.renderTileEntity((TileEntity)this.tileEntities.get(var6), var3);
                ++var6;
            }
        }
    }

    public String func_953_b() {
        return "C: " + this.renderersBeingRendered + "/" + this.renderersLoaded + ". F: " + this.renderersBeingClipped + ", O: " + this.renderersBeingOccluded + ", E: " + this.renderersSkippingRenderPass;
    }

    public String func_957_c() {
        return "E: " + this.field_1422_K + "/" + this.field_1423_J + ". B: " + this.field_1421_L + ", I: " + (this.field_1423_J - this.field_1421_L - this.field_1422_K);
    }

    private void markRenderersForNewPosition(int var1, int var2, int var3) {
        var1 -= 8;
        var2 -= 8;
        var3 -= 8;
        this.field_1431_B = Integer.MAX_VALUE;
        this.field_1430_C = Integer.MAX_VALUE;
        this.field_1429_D = Integer.MAX_VALUE;
        this.field_1428_E = Integer.MIN_VALUE;
        this.field_1427_F = Integer.MIN_VALUE;
        this.field_1426_G = Integer.MIN_VALUE;
        int var4 = this.renderChunksWide * 16;
        int var5 = var4 / 2;
        int var6 = 0;
        while (var6 < this.renderChunksWide) {
            int var7 = var6 * 16;
            int var8 = var7 + var5 - var1;
            if (var8 < 0) {
                var8 -= var4 - 1;
            }
            if ((var7 -= (var8 /= var4) * var4) < this.field_1431_B) {
                this.field_1431_B = var7;
            }
            if (var7 > this.field_1428_E) {
                this.field_1428_E = var7;
            }
            int var9 = 0;
            while (var9 < this.renderChunksDeep) {
                int var10 = var9 * 16;
                int var11 = var10 + var5 - var3;
                if (var11 < 0) {
                    var11 -= var4 - 1;
                }
                if ((var10 -= (var11 /= var4) * var4) < this.field_1429_D) {
                    this.field_1429_D = var10;
                }
                if (var10 > this.field_1426_G) {
                    this.field_1426_G = var10;
                }
                int var12 = 0;
                while (var12 < this.renderChunksTall) {
                    int var13 = var12 * 16;
                    if (var13 < this.field_1430_C) {
                        this.field_1430_C = var13;
                    }
                    if (var13 > this.field_1427_F) {
                        this.field_1427_F = var13;
                    }
                    WorldRenderer var14 = this.worldRenderers[(var9 * this.renderChunksTall + var12) * this.renderChunksWide + var6];
                    boolean var15 = var14.needsUpdate;
                    var14.setPosition(var7, var13, var10);
                    if (!var15 && var14.needsUpdate) {
                        this.worldRenderersToUpdate.add(var14);
                    }
                    ++var12;
                }
                ++var9;
            }
            ++var6;
        }
    }

    public int sortAndRender(EntityLiving var1, int var2, double var3) {
        int var34;
        int var5 = 0;
        while (var5 < 10) {
            this.field_21156_R = (this.field_21156_R + 1) % this.worldRenderers.length;
            WorldRenderer var6 = this.worldRenderers[this.field_21156_R];
            if (var6.needsUpdate && !this.worldRenderersToUpdate.contains(var6)) {
                this.worldRenderersToUpdate.add(var6);
            }
            ++var5;
        }
        if (this.mc.gameSettings.renderDistance != this.renderDistance) {
            this.loadRenderers();
        }
        if (var2 == 0) {
            this.renderersLoaded = 0;
            this.renderersBeingClipped = 0;
            this.renderersBeingOccluded = 0;
            this.renderersBeingRendered = 0;
            this.renderersSkippingRenderPass = 0;
        }
        double var33 = var1.lastTickPosX + (var1.posX - var1.lastTickPosX) * var3;
        double var7 = var1.lastTickPosY + (var1.posY - var1.lastTickPosY) * var3;
        double var9 = var1.lastTickPosZ + (var1.posZ - var1.lastTickPosZ) * var3;
        double var11 = var1.posX - this.prevSortX;
        double var13 = var1.posY - this.prevSortY;
        double var15 = var1.posZ - this.prevSortZ;
        if (var11 * var11 + var13 * var13 + var15 * var15 > 16.0) {
            this.prevSortX = var1.posX;
            this.prevSortY = var1.posY;
            this.prevSortZ = var1.posZ;
            this.markRenderersForNewPosition(MathHelper.floor_double(var1.posX), MathHelper.floor_double(var1.posY), MathHelper.floor_double(var1.posZ));
            Arrays.sort(this.sortedWorldRenderers, new EntitySorter(var1));
        }
        int var17 = 0;
        if (this.occlusionEnabled && !this.mc.gameSettings.anaglyph && var2 == 0) {
            int var18 = 0;
            int var19 = 16;
            this.func_962_a(var18, var19);
            int var20 = var18;
            while (var20 < var19) {
                this.sortedWorldRenderers[var20].isVisible = true;
                ++var20;
            }
            var34 = var17 + this.renderSortedRenderers(var18, var19, var2, var3);
            do {
                int var35 = var19;
                if ((var19 *= 2) > this.sortedWorldRenderers.length) {
                    var19 = this.sortedWorldRenderers.length;
                }
                GL11.glDisable((int)3553);
                GL11.glDisable((int)2896);
                GL11.glDisable((int)3008);
                GL11.glDisable((int)2912);
                GL11.glColorMask((boolean)false, (boolean)false, (boolean)false, (boolean)false);
                GL11.glDepthMask((boolean)false);
                this.func_962_a(var35, var19);
                GL11.glPushMatrix();
                float var36 = 0.0f;
                float var21 = 0.0f;
                float var22 = 0.0f;
                int var23 = var35;
                while (var23 < var19) {
                    if (this.sortedWorldRenderers[var23].canRender()) {
                        this.sortedWorldRenderers[var23].isInFrustum = false;
                    } else {
                        float var24;
                        int var25;
                        if (!this.sortedWorldRenderers[var23].isInFrustum) {
                            this.sortedWorldRenderers[var23].isVisible = true;
                        }
                        if (this.sortedWorldRenderers[var23].isInFrustum && !this.sortedWorldRenderers[var23].isWaitingOnOcclusionQuery && this.cloudOffsetX % (var25 = (int)(1.0f + (var24 = MathHelper.sqrt_float(this.sortedWorldRenderers[var23].distanceToEntitySquared(var1))) / 128.0f)) == var23 % var25) {
                            WorldRenderer var26 = this.sortedWorldRenderers[var23];
                            float var27 = (float)((double)var26.field_1755_i - var33);
                            float var28 = (float)((double)var26.field_1754_j - var7);
                            float var29 = (float)((double)var26.field_1753_k - var9);
                            float var30 = var27 - var36;
                            float var31 = var28 - var21;
                            float var32 = var29 - var22;
                            if (var30 != 0.0f || var31 != 0.0f || var32 != 0.0f) {
                                GL11.glTranslatef((float)var30, (float)var31, (float)var32);
                                var36 += var30;
                                var21 += var31;
                                var22 += var32;
                            }
                            ARBOcclusionQuery.glBeginQueryARB((int)35092, (int)this.sortedWorldRenderers[var23].field_1732_z);
                            this.sortedWorldRenderers[var23].callOcclusionQueryList();
                            ARBOcclusionQuery.glEndQueryARB((int)35092);
                            this.sortedWorldRenderers[var23].isWaitingOnOcclusionQuery = true;
                        }
                    }
                    ++var23;
                }
                GL11.glPopMatrix();
                GL11.glColorMask((boolean)true, (boolean)true, (boolean)true, (boolean)true);
                GL11.glDepthMask((boolean)true);
                GL11.glEnable((int)3553);
                GL11.glEnable((int)3008);
                GL11.glEnable((int)2912);
                var34 += this.renderSortedRenderers(var35, var19, var2, var3);
            } while (var19 < this.sortedWorldRenderers.length);
        } else {
            var34 = var17 + this.renderSortedRenderers(0, this.sortedWorldRenderers.length, var2, var3);
        }
        return var34;
    }

    private void func_962_a(int var1, int var2) {
        int var3 = var1;
        while (var3 < var2) {
            if (this.sortedWorldRenderers[var3].isWaitingOnOcclusionQuery) {
                this.field_1456_c.clear();
                ARBOcclusionQuery.glGetQueryObjectuARB((int)this.sortedWorldRenderers[var3].field_1732_z, (int)34919, (IntBuffer)this.field_1456_c);
                if (this.field_1456_c.get(0) != 0) {
                    this.sortedWorldRenderers[var3].isWaitingOnOcclusionQuery = false;
                    this.field_1456_c.clear();
                    ARBOcclusionQuery.glGetQueryObjectuARB((int)this.sortedWorldRenderers[var3].field_1732_z, (int)34918, (IntBuffer)this.field_1456_c);
                    this.sortedWorldRenderers[var3].isVisible = this.field_1456_c.get(0) != 0;
                }
            }
            ++var3;
        }
    }

    private int renderSortedRenderers(int var1, int var2, int var3, double var4) {
        this.field_1415_R.clear();
        int var6 = 0;
        int var7 = var1;
        while (var7 < var2) {
            int var8;
            if (var3 == 0) {
                ++this.renderersLoaded;
                if (this.sortedWorldRenderers[var7].skipRenderPass[var3]) {
                    ++this.renderersSkippingRenderPass;
                } else if (!this.sortedWorldRenderers[var7].isInFrustum) {
                    ++this.renderersBeingClipped;
                } else if (this.occlusionEnabled && !this.sortedWorldRenderers[var7].isVisible) {
                    ++this.renderersBeingOccluded;
                } else {
                    ++this.renderersBeingRendered;
                }
            }
            if (!this.sortedWorldRenderers[var7].skipRenderPass[var3] && this.sortedWorldRenderers[var7].isInFrustum && this.sortedWorldRenderers[var7].isVisible && (var8 = this.sortedWorldRenderers[var7].getGLCallListForPass(var3)) >= 0) {
                this.field_1415_R.add(this.sortedWorldRenderers[var7]);
                ++var6;
            }
            ++var7;
        }
        EntityLiving var19 = this.mc.renderViewEntity;
        double var20 = var19.lastTickPosX + (var19.posX - var19.lastTickPosX) * var4;
        double var10 = var19.lastTickPosY + (var19.posY - var19.lastTickPosY) * var4;
        double var12 = var19.lastTickPosZ + (var19.posZ - var19.lastTickPosZ) * var4;
        int var14 = 0;
        int var15 = 0;
        while (var15 < this.field_1414_S.length) {
            this.field_1414_S[var15].func_859_b();
            ++var15;
        }
        var15 = 0;
        while (var15 < this.field_1415_R.size()) {
            WorldRenderer var16 = (WorldRenderer)this.field_1415_R.get(var15);
            int var17 = -1;
            int var18 = 0;
            while (var18 < var14) {
                if (this.field_1414_S[var18].func_862_a(var16.field_1755_i, var16.field_1754_j, var16.field_1753_k)) {
                    var17 = var18;
                }
                ++var18;
            }
            if (var17 < 0) {
                var17 = var14++;
                this.field_1414_S[var17].func_861_a(var16.field_1755_i, var16.field_1754_j, var16.field_1753_k, var20, var10, var12);
            }
            this.field_1414_S[var17].func_858_a(var16.getGLCallListForPass(var3));
            ++var15;
        }
        this.func_944_a(var3, var4);
        return var6;
    }

    public void func_944_a(int var1, double var2) {
        int var4 = 0;
        while (var4 < this.field_1414_S.length) {
            this.field_1414_S[var4].func_860_a();
            ++var4;
        }
    }

    public void updateClouds() {
        ++this.cloudOffsetX;
    }

    public void renderSky(float var1) {
        if (!this.mc.theWorld.worldProvider.field_4220_c) {
            float var11;
            float var8;
            float var7;
            GL11.glDisable((int)3553);
            Vec3D var2 = this.worldObj.func_4079_a(this.mc.renderViewEntity, var1);
            float var3 = (float)var2.xCoord;
            float var4 = (float)var2.yCoord;
            float var5 = (float)var2.zCoord;
            if (this.mc.gameSettings.anaglyph) {
                float var6 = (var3 * 30.0f + var4 * 59.0f + var5 * 11.0f) / 100.0f;
                var7 = (var3 * 30.0f + var4 * 70.0f) / 100.0f;
                var8 = (var3 * 30.0f + var5 * 70.0f) / 100.0f;
                var3 = var6;
                var4 = var7;
                var5 = var8;
            }
            GL11.glColor3f((float)var3, (float)var4, (float)var5);
            Tessellator var14 = Tessellator.instance;
            GL11.glDepthMask((boolean)false);
            GL11.glEnable((int)2912);
            GL11.glColor3f((float)var3, (float)var4, (float)var5);
            GL11.glCallList((int)this.field_1433_z);
            GL11.glDisable((int)2912);
            GL11.glDisable((int)3008);
            GL11.glEnable((int)3042);
            GL11.glBlendFunc((int)770, (int)771);
            float[] var15 = this.worldObj.worldProvider.calcSunriseSunsetColors(this.worldObj.getCelestialAngle(var1), var1);
            if (var15 != null) {
                GL11.glDisable((int)3553);
                GL11.glShadeModel((int)7425);
                GL11.glPushMatrix();
                GL11.glRotatef((float)90.0f, (float)1.0f, (float)0.0f, (float)0.0f);
                var8 = this.worldObj.getCelestialAngle(var1);
                GL11.glRotatef((float)(var8 > 0.5f ? 180.0f : 0.0f), (float)0.0f, (float)0.0f, (float)1.0f);
                var14.startDrawing(6);
                var14.setColorRGBA_F(var15[0], var15[1], var15[2], var15[3]);
                var14.addVertex(0.0, 100.0, 0.0);
                int var9 = 16;
                var14.setColorRGBA_F(var15[0], var15[1], var15[2], 0.0f);
                int var10 = 0;
                while (var10 <= var9) {
                    var11 = (float)var10 * (float)Math.PI * 2.0f / (float)var9;
                    float var12 = MathHelper.sin(var11);
                    float var13 = MathHelper.cos(var11);
                    var14.addVertex(var12 * 120.0f, var13 * 120.0f, -var13 * 40.0f * var15[3]);
                    ++var10;
                }
                var14.draw();
                GL11.glPopMatrix();
                GL11.glShadeModel((int)7424);
            }
            GL11.glEnable((int)3553);
            GL11.glBlendFunc((int)1, (int)1);
            GL11.glPushMatrix();
            var7 = 0.0f;
            var8 = 0.0f;
            float var16 = 0.0f;
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            GL11.glTranslatef((float)var7, (float)var8, (float)var16);
            GL11.glRotatef((float)0.0f, (float)0.0f, (float)0.0f, (float)1.0f);
            GL11.glRotatef((float)(this.worldObj.getCelestialAngle(var1) * 360.0f), (float)1.0f, (float)0.0f, (float)0.0f);
            float var17 = 30.0f;
            GL11.glBindTexture((int)3553, (int)this.renderEngine.getTexture("/terrain/sun.png"));
            var14.startDrawingQuads();
            var14.addVertexWithUV(-var17, 100.0, -var17, 0.0, 0.0);
            var14.addVertexWithUV(var17, 100.0, -var17, 1.0, 0.0);
            var14.addVertexWithUV(var17, 100.0, var17, 1.0, 1.0);
            var14.addVertexWithUV(-var17, 100.0, var17, 0.0, 1.0);
            var14.draw();
            var17 = 20.0f;
            GL11.glBindTexture((int)3553, (int)this.renderEngine.getTexture("/terrain/moon.png"));
            var14.startDrawingQuads();
            var14.addVertexWithUV(-var17, -100.0, var17, 1.0, 1.0);
            var14.addVertexWithUV(var17, -100.0, var17, 0.0, 1.0);
            var14.addVertexWithUV(var17, -100.0, -var17, 0.0, 0.0);
            var14.addVertexWithUV(-var17, -100.0, -var17, 1.0, 0.0);
            var14.draw();
            GL11.glDisable((int)3553);
            var11 = this.worldObj.getStarBrightness(var1);
            if (var11 > 0.0f) {
                GL11.glColor4f((float)var11, (float)var11, (float)var11, (float)var11);
                GL11.glCallList((int)this.starGLCallList);
            }
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            GL11.glDisable((int)3042);
            GL11.glEnable((int)3008);
            GL11.glEnable((int)2912);
            GL11.glPopMatrix();
            GL11.glColor3f((float)(var3 * 0.2f + 0.04f), (float)(var4 * 0.2f + 0.04f), (float)(var5 * 0.6f + 0.1f));
            GL11.glDisable((int)3553);
            GL11.glCallList((int)this.field_1432_A);
            GL11.glEnable((int)3553);
            GL11.glDepthMask((boolean)true);
        }
    }

    public void renderClouds(float var1) {
        if (!this.mc.theWorld.worldProvider.field_4220_c) {
            if (this.mc.gameSettings.fancyGraphics) {
                this.renderCloudsFancy(var1);
            } else {
                float var10;
                GL11.glDisable((int)2884);
                float var2 = (float)(this.mc.renderViewEntity.lastTickPosY + (this.mc.renderViewEntity.posY - this.mc.renderViewEntity.lastTickPosY) * (double)var1);
                int var3 = 32;
                int var4 = 256 / var3;
                Tessellator var5 = Tessellator.instance;
                GL11.glBindTexture((int)3553, (int)this.renderEngine.getTexture("/environment/clouds.png"));
                GL11.glEnable((int)3042);
                GL11.glBlendFunc((int)770, (int)771);
                Vec3D var6 = this.worldObj.func_628_d(var1);
                float var7 = (float)var6.xCoord;
                float var8 = (float)var6.yCoord;
                float var9 = (float)var6.zCoord;
                if (this.mc.gameSettings.anaglyph) {
                    var10 = (var7 * 30.0f + var8 * 59.0f + var9 * 11.0f) / 100.0f;
                    float var11 = (var7 * 30.0f + var8 * 70.0f) / 100.0f;
                    float var12 = (var7 * 30.0f + var9 * 70.0f) / 100.0f;
                    var7 = var10;
                    var8 = var11;
                    var9 = var12;
                }
                var10 = 4.8828125E-4f;
                double var22 = this.mc.renderViewEntity.prevPosX + (this.mc.renderViewEntity.posX - this.mc.renderViewEntity.prevPosX) * (double)var1 + (double)(((float)this.cloudOffsetX + var1) * 0.03f);
                double var13 = this.mc.renderViewEntity.prevPosZ + (this.mc.renderViewEntity.posZ - this.mc.renderViewEntity.prevPosZ) * (double)var1;
                int var15 = MathHelper.floor_double(var22 / 2048.0);
                int var16 = MathHelper.floor_double(var13 / 2048.0);
                float var17 = 120.0f - var2 + 0.33f;
                float var18 = (float)((var22 -= (double)(var15 * 2048)) * (double)var10);
                float var19 = (float)((var13 -= (double)(var16 * 2048)) * (double)var10);
                var5.startDrawingQuads();
                var5.setColorRGBA_F(var7, var8, var9, 0.8f);
                int var20 = -var3 * var4;
                while (var20 < var3 * var4) {
                    int var21 = -var3 * var4;
                    while (var21 < var3 * var4) {
                        var5.addVertexWithUV(var20 + 0, var17, var21 + var3, (float)(var20 + 0) * var10 + var18, (float)(var21 + var3) * var10 + var19);
                        var5.addVertexWithUV(var20 + var3, var17, var21 + var3, (float)(var20 + var3) * var10 + var18, (float)(var21 + var3) * var10 + var19);
                        var5.addVertexWithUV(var20 + var3, var17, var21 + 0, (float)(var20 + var3) * var10 + var18, (float)(var21 + 0) * var10 + var19);
                        var5.addVertexWithUV(var20 + 0, var17, var21 + 0, (float)(var20 + 0) * var10 + var18, (float)(var21 + 0) * var10 + var19);
                        var21 += var3;
                    }
                    var20 += var3;
                }
                var5.draw();
                GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                GL11.glDisable((int)3042);
                GL11.glEnable((int)2884);
            }
        }
    }

    public void renderCloudsFancy(float var1) {
        float var19;
        float var18;
        float var17;
        GL11.glDisable((int)2884);
        float var2 = (float)(this.mc.renderViewEntity.lastTickPosY + (this.mc.renderViewEntity.posY - this.mc.renderViewEntity.lastTickPosY) * (double)var1);
        Tessellator var3 = Tessellator.instance;
        float var4 = 12.0f;
        float var5 = 4.0f;
        double var6 = (this.mc.renderViewEntity.prevPosX + (this.mc.renderViewEntity.posX - this.mc.renderViewEntity.prevPosX) * (double)var1 + (double)(((float)this.cloudOffsetX + var1) * 0.03f)) / (double)var4;
        double var8 = (this.mc.renderViewEntity.prevPosZ + (this.mc.renderViewEntity.posZ - this.mc.renderViewEntity.prevPosZ) * (double)var1) / (double)var4 + (double)0.33f;
        float var10 = 108.0f - var2 + 0.33f;
        int var11 = MathHelper.floor_double(var6 / 2048.0);
        int var12 = MathHelper.floor_double(var8 / 2048.0);
        var6 -= (double)(var11 * 2048);
        var8 -= (double)(var12 * 2048);
        GL11.glBindTexture((int)3553, (int)this.renderEngine.getTexture("/environment/clouds.png"));
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        Vec3D var13 = this.worldObj.func_628_d(var1);
        float var14 = (float)var13.xCoord;
        float var15 = (float)var13.yCoord;
        float var16 = (float)var13.zCoord;
        if (this.mc.gameSettings.anaglyph) {
            var17 = (var14 * 30.0f + var15 * 59.0f + var16 * 11.0f) / 100.0f;
            var18 = (var14 * 30.0f + var15 * 70.0f) / 100.0f;
            var19 = (var14 * 30.0f + var16 * 70.0f) / 100.0f;
            var14 = var17;
            var15 = var18;
            var16 = var19;
        }
        var17 = (float)(var6 * 0.0);
        var18 = (float)(var8 * 0.0);
        var19 = 0.00390625f;
        var17 = (float)MathHelper.floor_double(var6) * var19;
        var18 = (float)MathHelper.floor_double(var8) * var19;
        float var20 = (float)(var6 - (double)MathHelper.floor_double(var6));
        float var21 = (float)(var8 - (double)MathHelper.floor_double(var8));
        int var22 = 8;
        int var23 = 3;
        float var24 = 9.765625E-4f;
        GL11.glScalef((float)var4, (float)1.0f, (float)var4);
        int var25 = 0;
        while (var25 < 2) {
            if (var25 == 0) {
                GL11.glColorMask((boolean)false, (boolean)false, (boolean)false, (boolean)false);
            } else {
                GL11.glColorMask((boolean)true, (boolean)true, (boolean)true, (boolean)true);
            }
            int var26 = -var23 + 1;
            while (var26 <= var23) {
                int var27 = -var23 + 1;
                while (var27 <= var23) {
                    int var32;
                    var3.startDrawingQuads();
                    float var28 = var26 * var22;
                    float var29 = var27 * var22;
                    float var30 = var28 - var20;
                    float var31 = var29 - var21;
                    if (var10 > -var5 - 1.0f) {
                        var3.setColorRGBA_F(var14 * 0.7f, var15 * 0.7f, var16 * 0.7f, 0.8f);
                        var3.setNormal(0.0f, -1.0f, 0.0f);
                        var3.addVertexWithUV(var30 + 0.0f, var10 + 0.0f, var31 + (float)var22, (var28 + 0.0f) * var19 + var17, (var29 + (float)var22) * var19 + var18);
                        var3.addVertexWithUV(var30 + (float)var22, var10 + 0.0f, var31 + (float)var22, (var28 + (float)var22) * var19 + var17, (var29 + (float)var22) * var19 + var18);
                        var3.addVertexWithUV(var30 + (float)var22, var10 + 0.0f, var31 + 0.0f, (var28 + (float)var22) * var19 + var17, (var29 + 0.0f) * var19 + var18);
                        var3.addVertexWithUV(var30 + 0.0f, var10 + 0.0f, var31 + 0.0f, (var28 + 0.0f) * var19 + var17, (var29 + 0.0f) * var19 + var18);
                    }
                    if (var10 <= var5 + 1.0f) {
                        var3.setColorRGBA_F(var14, var15, var16, 0.8f);
                        var3.setNormal(0.0f, 1.0f, 0.0f);
                        var3.addVertexWithUV(var30 + 0.0f, var10 + var5 - var24, var31 + (float)var22, (var28 + 0.0f) * var19 + var17, (var29 + (float)var22) * var19 + var18);
                        var3.addVertexWithUV(var30 + (float)var22, var10 + var5 - var24, var31 + (float)var22, (var28 + (float)var22) * var19 + var17, (var29 + (float)var22) * var19 + var18);
                        var3.addVertexWithUV(var30 + (float)var22, var10 + var5 - var24, var31 + 0.0f, (var28 + (float)var22) * var19 + var17, (var29 + 0.0f) * var19 + var18);
                        var3.addVertexWithUV(var30 + 0.0f, var10 + var5 - var24, var31 + 0.0f, (var28 + 0.0f) * var19 + var17, (var29 + 0.0f) * var19 + var18);
                    }
                    var3.setColorRGBA_F(var14 * 0.9f, var15 * 0.9f, var16 * 0.9f, 0.8f);
                    if (var26 > -1) {
                        var3.setNormal(-1.0f, 0.0f, 0.0f);
                        var32 = 0;
                        while (var32 < var22) {
                            var3.addVertexWithUV(var30 + (float)var32 + 0.0f, var10 + 0.0f, var31 + (float)var22, (var28 + (float)var32 + 0.5f) * var19 + var17, (var29 + (float)var22) * var19 + var18);
                            var3.addVertexWithUV(var30 + (float)var32 + 0.0f, var10 + var5, var31 + (float)var22, (var28 + (float)var32 + 0.5f) * var19 + var17, (var29 + (float)var22) * var19 + var18);
                            var3.addVertexWithUV(var30 + (float)var32 + 0.0f, var10 + var5, var31 + 0.0f, (var28 + (float)var32 + 0.5f) * var19 + var17, (var29 + 0.0f) * var19 + var18);
                            var3.addVertexWithUV(var30 + (float)var32 + 0.0f, var10 + 0.0f, var31 + 0.0f, (var28 + (float)var32 + 0.5f) * var19 + var17, (var29 + 0.0f) * var19 + var18);
                            ++var32;
                        }
                    }
                    if (var26 <= 1) {
                        var3.setNormal(1.0f, 0.0f, 0.0f);
                        var32 = 0;
                        while (var32 < var22) {
                            var3.addVertexWithUV(var30 + (float)var32 + 1.0f - var24, var10 + 0.0f, var31 + (float)var22, (var28 + (float)var32 + 0.5f) * var19 + var17, (var29 + (float)var22) * var19 + var18);
                            var3.addVertexWithUV(var30 + (float)var32 + 1.0f - var24, var10 + var5, var31 + (float)var22, (var28 + (float)var32 + 0.5f) * var19 + var17, (var29 + (float)var22) * var19 + var18);
                            var3.addVertexWithUV(var30 + (float)var32 + 1.0f - var24, var10 + var5, var31 + 0.0f, (var28 + (float)var32 + 0.5f) * var19 + var17, (var29 + 0.0f) * var19 + var18);
                            var3.addVertexWithUV(var30 + (float)var32 + 1.0f - var24, var10 + 0.0f, var31 + 0.0f, (var28 + (float)var32 + 0.5f) * var19 + var17, (var29 + 0.0f) * var19 + var18);
                            ++var32;
                        }
                    }
                    var3.setColorRGBA_F(var14 * 0.8f, var15 * 0.8f, var16 * 0.8f, 0.8f);
                    if (var27 > -1) {
                        var3.setNormal(0.0f, 0.0f, -1.0f);
                        var32 = 0;
                        while (var32 < var22) {
                            var3.addVertexWithUV(var30 + 0.0f, var10 + var5, var31 + (float)var32 + 0.0f, (var28 + 0.0f) * var19 + var17, (var29 + (float)var32 + 0.5f) * var19 + var18);
                            var3.addVertexWithUV(var30 + (float)var22, var10 + var5, var31 + (float)var32 + 0.0f, (var28 + (float)var22) * var19 + var17, (var29 + (float)var32 + 0.5f) * var19 + var18);
                            var3.addVertexWithUV(var30 + (float)var22, var10 + 0.0f, var31 + (float)var32 + 0.0f, (var28 + (float)var22) * var19 + var17, (var29 + (float)var32 + 0.5f) * var19 + var18);
                            var3.addVertexWithUV(var30 + 0.0f, var10 + 0.0f, var31 + (float)var32 + 0.0f, (var28 + 0.0f) * var19 + var17, (var29 + (float)var32 + 0.5f) * var19 + var18);
                            ++var32;
                        }
                    }
                    if (var27 <= 1) {
                        var3.setNormal(0.0f, 0.0f, 1.0f);
                        var32 = 0;
                        while (var32 < var22) {
                            var3.addVertexWithUV(var30 + 0.0f, var10 + var5, var31 + (float)var32 + 1.0f - var24, (var28 + 0.0f) * var19 + var17, (var29 + (float)var32 + 0.5f) * var19 + var18);
                            var3.addVertexWithUV(var30 + (float)var22, var10 + var5, var31 + (float)var32 + 1.0f - var24, (var28 + (float)var22) * var19 + var17, (var29 + (float)var32 + 0.5f) * var19 + var18);
                            var3.addVertexWithUV(var30 + (float)var22, var10 + 0.0f, var31 + (float)var32 + 1.0f - var24, (var28 + (float)var22) * var19 + var17, (var29 + (float)var32 + 0.5f) * var19 + var18);
                            var3.addVertexWithUV(var30 + 0.0f, var10 + 0.0f, var31 + (float)var32 + 1.0f - var24, (var28 + 0.0f) * var19 + var17, (var29 + (float)var32 + 0.5f) * var19 + var18);
                            ++var32;
                        }
                    }
                    var3.draw();
                    ++var27;
                }
                ++var26;
            }
            ++var25;
        }
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glDisable((int)3042);
        GL11.glEnable((int)2884);
    }

    public boolean updateRenderers(EntityLiving var1, boolean var2) {
        int var12;
        int var11;
        WorldRenderer var10;
        block32: {
            boolean var3 = false;
            if (!var3) break block32;
            Collections.sort(this.worldRenderersToUpdate, new RenderSorter(var1));
            int var14 = this.worldRenderersToUpdate.size() - 1;
            int var15 = this.worldRenderersToUpdate.size();
            int var16 = 0;
            while (var16 < var15) {
                block35: {
                    WorldRenderer var17;
                    block34: {
                        block33: {
                            var17 = (WorldRenderer)this.worldRenderersToUpdate.get(var14 - var16);
                            if (var2) break block33;
                            if (var17.distanceToEntitySquared(var1) > 1024.0f && (var17.isInFrustum ? var16 >= 3 : var16 >= 1)) {
                                return false;
                            }
                            break block34;
                        }
                        if (!var17.isInFrustum) break block35;
                    }
                    var17.updateRenderer();
                    this.worldRenderersToUpdate.remove(var17);
                    var17.needsUpdate = false;
                }
                ++var16;
            }
            return this.worldRenderersToUpdate.size() == 0;
        }
        RenderSorter var4 = new RenderSorter(var1);
        WorldRenderer[] var5 = new WorldRenderer[3];
        ArrayList<WorldRenderer> var6 = null;
        int var7 = this.worldRenderersToUpdate.size();
        int var8 = 0;
        int var9 = 0;
        while (var9 < var7) {
            block31: {
                block37: {
                    block36: {
                        var10 = (WorldRenderer)this.worldRenderersToUpdate.get(var9);
                        if (var2) break block36;
                        if (!(var10.distanceToEntitySquared(var1) > 1024.0f)) break block37;
                        var11 = 0;
                        while (var11 < 3 && (var5[var11] == null || var4.func_993_a(var5[var11], var10) <= 0)) {
                            ++var11;
                        }
                        if (--var11 > 0) {
                            var12 = var11;
                            while (true) {
                                if (--var12 == 0) {
                                    var5[var11] = var10;
                                    break block31;
                                }
                                var5[var12 - 1] = var5[var12];
                            }
                        }
                        break block31;
                    }
                    if (!var10.isInFrustum) break block31;
                }
                if (var6 == null) {
                    var6 = new ArrayList<WorldRenderer>();
                }
                ++var8;
                var6.add(var10);
                this.worldRenderersToUpdate.set(var9, null);
            }
            ++var9;
        }
        if (var6 != null) {
            if (var6.size() > 1) {
                Collections.sort(var6, var4);
            }
            var9 = var6.size() - 1;
            while (var9 >= 0) {
                var10 = (WorldRenderer)var6.get(var9);
                var10.updateRenderer();
                var10.needsUpdate = false;
                --var9;
            }
        }
        var9 = 0;
        int var18 = 2;
        while (var18 >= 0) {
            WorldRenderer var19 = var5[var18];
            if (var19 != null) {
                if (!var19.isInFrustum && var18 != 2) {
                    var5[var18] = null;
                    var5[0] = null;
                    break;
                }
                var5[var18].updateRenderer();
                var5[var18].needsUpdate = false;
                ++var9;
            }
            --var18;
        }
        var18 = 0;
        var11 = 0;
        var12 = this.worldRenderersToUpdate.size();
        while (var18 != var12) {
            WorldRenderer var13 = (WorldRenderer)this.worldRenderersToUpdate.get(var18);
            if (var13 != null && var13 != var5[0] && var13 != var5[1] && var13 != var5[2]) {
                if (var11 != var18) {
                    this.worldRenderersToUpdate.set(var11, var13);
                }
                ++var11;
            }
            ++var18;
        }
        while (--var18 >= var11) {
            this.worldRenderersToUpdate.remove(var18);
        }
        return var7 == var8 + var9;
    }

    public void func_959_a(EntityPlayer var1, MovingObjectPosition var2, int var3, ItemStack var4, float var5) {
        Tessellator var6 = Tessellator.instance;
        GL11.glEnable((int)3042);
        GL11.glEnable((int)3008);
        GL11.glBlendFunc((int)770, (int)1);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)((MathHelper.sin((float)System.currentTimeMillis() / 100.0f) * 0.2f + 0.4f) * 0.5f));
        if (var3 == 0) {
            if (this.field_1450_i > 0.0f) {
                GL11.glBlendFunc((int)774, (int)768);
                int var7 = this.renderEngine.getTexture("/terrain.png");
                GL11.glBindTexture((int)3553, (int)var7);
                GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)0.5f);
                GL11.glPushMatrix();
                int var8 = this.worldObj.getBlockId(var2.blockX, var2.blockY, var2.blockZ);
                Block var9 = var8 > 0 ? Block.blocksList[var8] : null;
                GL11.glDisable((int)3008);
                GL11.glPolygonOffset((float)-3.0f, (float)-3.0f);
                GL11.glEnable((int)32823);
                var6.startDrawingQuads();
                double var10 = var1.lastTickPosX + (var1.posX - var1.lastTickPosX) * (double)var5;
                double var12 = var1.lastTickPosY + (var1.posY - var1.lastTickPosY) * (double)var5;
                double var14 = var1.lastTickPosZ + (var1.posZ - var1.lastTickPosZ) * (double)var5;
                var6.setTranslationD(-var10, -var12, -var14);
                var6.disableColor();
                if (var9 == null) {
                    var9 = Block.stone;
                }
                this.field_1438_u.renderBlockUsingTexture(var9, var2.blockX, var2.blockY, var2.blockZ, 240 + (int)(this.field_1450_i * 10.0f));
                var6.draw();
                var6.setTranslationD(0.0, 0.0, 0.0);
                GL11.glPolygonOffset((float)0.0f, (float)0.0f);
                GL11.glDisable((int)32823);
                GL11.glEnable((int)3008);
                GL11.glDepthMask((boolean)true);
                GL11.glPopMatrix();
            }
        } else if (var4 != null) {
            GL11.glBlendFunc((int)770, (int)771);
            float var16 = MathHelper.sin((float)System.currentTimeMillis() / 100.0f) * 0.2f + 0.8f;
            GL11.glColor4f((float)var16, (float)var16, (float)var16, (float)(MathHelper.sin((float)System.currentTimeMillis() / 200.0f) * 0.2f + 0.5f));
            int var8 = this.renderEngine.getTexture("/terrain.png");
            GL11.glBindTexture((int)3553, (int)var8);
            int var17 = var2.blockX;
            int var18 = var2.blockY;
            int var11 = var2.blockZ;
            if (var2.sideHit == 0) {
                --var18;
            }
            if (var2.sideHit == 1) {
                ++var18;
            }
            if (var2.sideHit == 2) {
                --var11;
            }
            if (var2.sideHit == 3) {
                ++var11;
            }
            if (var2.sideHit == 4) {
                --var17;
            }
            if (var2.sideHit == 5) {
                ++var17;
            }
        }
        GL11.glDisable((int)3042);
        GL11.glDisable((int)3008);
    }

    public void drawSelectionBox(EntityPlayer var1, MovingObjectPosition var2, int var3, ItemStack var4, float var5) {
        if (var3 == 0 && var2.typeOfHit == EnumMovingObjectType.TILE) {
            GL11.glEnable((int)3042);
            GL11.glBlendFunc((int)770, (int)771);
            GL11.glColor4f((float)0.0f, (float)0.0f, (float)0.0f, (float)0.4f);
            GL11.glLineWidth((float)2.0f);
            GL11.glDisable((int)3553);
            GL11.glDepthMask((boolean)false);
            float var6 = 0.002f;
            int var7 = this.worldObj.getBlockId(var2.blockX, var2.blockY, var2.blockZ);
            if (var7 > 0) {
                Block.blocksList[var7].setBlockBoundsBasedOnState(this.worldObj, var2.blockX, var2.blockY, var2.blockZ);
                double var8 = var1.lastTickPosX + (var1.posX - var1.lastTickPosX) * (double)var5;
                double var10 = var1.lastTickPosY + (var1.posY - var1.lastTickPosY) * (double)var5;
                double var12 = var1.lastTickPosZ + (var1.posZ - var1.lastTickPosZ) * (double)var5;
                this.drawOutlinedBoundingBox(Block.blocksList[var7].getSelectedBoundingBoxFromPool(this.worldObj, var2.blockX, var2.blockY, var2.blockZ).expand(var6, var6, var6).getOffsetBoundingBox(-var8, -var10, -var12));
            }
            GL11.glDepthMask((boolean)true);
            GL11.glEnable((int)3553);
            GL11.glDisable((int)3042);
        }
    }

    private void drawOutlinedBoundingBox(AxisAlignedBB var1) {
        Tessellator var2 = Tessellator.instance;
        var2.startDrawing(3);
        var2.addVertex(var1.minX, var1.minY, var1.minZ);
        var2.addVertex(var1.maxX, var1.minY, var1.minZ);
        var2.addVertex(var1.maxX, var1.minY, var1.maxZ);
        var2.addVertex(var1.minX, var1.minY, var1.maxZ);
        var2.addVertex(var1.minX, var1.minY, var1.minZ);
        var2.draw();
        var2.startDrawing(3);
        var2.addVertex(var1.minX, var1.maxY, var1.minZ);
        var2.addVertex(var1.maxX, var1.maxY, var1.minZ);
        var2.addVertex(var1.maxX, var1.maxY, var1.maxZ);
        var2.addVertex(var1.minX, var1.maxY, var1.maxZ);
        var2.addVertex(var1.minX, var1.maxY, var1.minZ);
        var2.draw();
        var2.startDrawing(1);
        var2.addVertex(var1.minX, var1.minY, var1.minZ);
        var2.addVertex(var1.minX, var1.maxY, var1.minZ);
        var2.addVertex(var1.maxX, var1.minY, var1.minZ);
        var2.addVertex(var1.maxX, var1.maxY, var1.minZ);
        var2.addVertex(var1.maxX, var1.minY, var1.maxZ);
        var2.addVertex(var1.maxX, var1.maxY, var1.maxZ);
        var2.addVertex(var1.minX, var1.minY, var1.maxZ);
        var2.addVertex(var1.minX, var1.maxY, var1.maxZ);
        var2.draw();
    }

    public void func_949_a(int var1, int var2, int var3, int var4, int var5, int var6) {
        int var7 = MathHelper.bucketInt(var1, 16);
        int var8 = MathHelper.bucketInt(var2, 16);
        int var9 = MathHelper.bucketInt(var3, 16);
        int var10 = MathHelper.bucketInt(var4, 16);
        int var11 = MathHelper.bucketInt(var5, 16);
        int var12 = MathHelper.bucketInt(var6, 16);
        int var13 = var7;
        while (var13 <= var10) {
            int var14 = var13 % this.renderChunksWide;
            if (var14 < 0) {
                var14 += this.renderChunksWide;
            }
            int var15 = var8;
            while (var15 <= var11) {
                int var16 = var15 % this.renderChunksTall;
                if (var16 < 0) {
                    var16 += this.renderChunksTall;
                }
                int var17 = var9;
                while (var17 <= var12) {
                    int var18 = var17 % this.renderChunksDeep;
                    if (var18 < 0) {
                        var18 += this.renderChunksDeep;
                    }
                    int var19 = (var18 * this.renderChunksTall + var16) * this.renderChunksWide + var14;
                    WorldRenderer var20 = this.worldRenderers[var19];
                    if (!var20.needsUpdate) {
                        this.worldRenderersToUpdate.add(var20);
                        var20.markDirty();
                    }
                    ++var17;
                }
                ++var15;
            }
            ++var13;
        }
    }

    @Override
    public void markBlockAndNeighborsNeedsUpdate(int var1, int var2, int var3) {
        this.func_949_a(var1 - 1, var2 - 1, var3 - 1, var1 + 1, var2 + 1, var3 + 1);
    }

    @Override
    public void markBlockRangeNeedsUpdate(int var1, int var2, int var3, int var4, int var5, int var6) {
        this.func_949_a(var1 - 1, var2 - 1, var3 - 1, var4 + 1, var5 + 1, var6 + 1);
    }

    public void clipRenderersByFrustrum(ICamera var1, float var2) {
        int var3 = 0;
        while (var3 < this.worldRenderers.length) {
            if (!(this.worldRenderers[var3].canRender() || this.worldRenderers[var3].isInFrustum && (var3 + this.frustrumCheckOffset & 0xF) != 0)) {
                this.worldRenderers[var3].updateInFrustrum(var1);
            }
            ++var3;
        }
        ++this.frustrumCheckOffset;
    }

    @Override
    public void playRecord(String var1, int var2, int var3, int var4) {
        if (var1 != null) {
            this.mc.ingameGUI.setRecordPlayingMessage("C418 - " + var1);
        }
        this.mc.sndManager.playStreaming(var1, var2, var3, var4, 1.0f, 1.0f);
    }

    @Override
    public void playSound(String var1, double var2, double var4, double var6, float var8, float var9) {
        float var10 = 16.0f;
        if (var8 > 1.0f) {
            var10 *= var8;
        }
        if (this.mc.renderViewEntity.getDistanceSq(var2, var4, var6) < (double)(var10 * var10)) {
            this.mc.sndManager.playSound(var1, (float)var2, (float)var4, (float)var6, var8, var9);
        }
    }

    @Override
    public void spawnParticle(String var1, double var2, double var4, double var6, double var8, double var10, double var12) {
        double var20;
        double var18;
        double var16;
        double var14;
        if (this.mc != null && this.mc.renderViewEntity != null && this.mc.effectRenderer != null && (var14 = this.mc.renderViewEntity.posX - var2) * var14 + (var16 = this.mc.renderViewEntity.posY - var4) * var16 + (var18 = this.mc.renderViewEntity.posZ - var6) * var18 <= (var20 = 16.0) * var20) {
            if (var1.equals("bubble")) {
                this.mc.effectRenderer.addEffect(new EntityBubbleFX(this.worldObj, var2, var4, var6, var8, var10, var12));
            } else if (var1.equals("smoke")) {
                this.mc.effectRenderer.addEffect(new EntitySmokeFX(this.worldObj, var2, var4, var6, var8, var10, var12));
            } else if (var1.equals("note")) {
                this.mc.effectRenderer.addEffect(new EntityNoteFX(this.worldObj, var2, var4, var6, var8, var10, var12));
            } else if (var1.equals("portal")) {
                this.mc.effectRenderer.addEffect(new EntityPortalFX(this.worldObj, var2, var4, var6, var8, var10, var12));
            } else if (var1.equals("explode")) {
                this.mc.effectRenderer.addEffect(new EntityExplodeFX(this.worldObj, var2, var4, var6, var8, var10, var12));
            } else if (var1.equals("flame")) {
                this.mc.effectRenderer.addEffect(new EntityFlameFX(this.worldObj, var2, var4, var6, var8, var10, var12));
            } else if (var1.equals("lava")) {
                this.mc.effectRenderer.addEffect(new EntityLavaFX(this.worldObj, var2, var4, var6));
            } else if (var1.equals("splash")) {
                this.mc.effectRenderer.addEffect(new EntitySplashFX(this.worldObj, var2, var4, var6, var8, var10, var12));
            } else if (var1.equals("largesmoke")) {
                this.mc.effectRenderer.addEffect(new EntitySmokeFX(this.worldObj, var2, var4, var6, var8, var10, var12, 2.5f));
            } else if (var1.equals("reddust")) {
                this.mc.effectRenderer.addEffect(new EntityReddustFX(this.worldObj, var2, var4, var6, (float)var8, (float)var10, (float)var12));
            } else if (var1.equals("snowballpoof")) {
                this.mc.effectRenderer.addEffect(new EntitySlimeFX(this.worldObj, var2, var4, var6, Item.snowball));
            } else if (var1.equals("slime")) {
                this.mc.effectRenderer.addEffect(new EntitySlimeFX(this.worldObj, var2, var4, var6, Item.slimeBall));
            } else if (var1.equals("heart")) {
                this.mc.effectRenderer.addEffect(new EntityHeartFX(this.worldObj, var2, var4, var6, var8, var10, var12));
            }
        }
    }

    @Override
    public void obtainEntitySkin(Entity var1) {
        var1.updateCloak();
        if (var1.skinUrl != null) {
            this.renderEngine.obtainImageData(var1.skinUrl, new ImageBufferDownload());
        }
        if (var1.cloakUrl != null) {
            this.renderEngine.obtainImageData(var1.cloakUrl, new ImageBufferDownload());
        }
    }

    @Override
    public void releaseEntitySkin(Entity var1) {
        if (var1.skinUrl != null) {
            this.renderEngine.releaseImageData(var1.skinUrl);
        }
        if (var1.cloakUrl != null) {
            this.renderEngine.releaseImageData(var1.cloakUrl);
        }
    }

    @Override
    public void updateAllRenderers() {
        int var1 = 0;
        while (var1 < this.worldRenderers.length) {
            if (this.worldRenderers[var1].field_1747_A && !this.worldRenderers[var1].needsUpdate) {
                this.worldRenderersToUpdate.add(this.worldRenderers[var1]);
                this.worldRenderers[var1].markDirty();
            }
            ++var1;
        }
    }

    @Override
    public void doNothingWithTileEntity(int var1, int var2, int var3, TileEntity var4) {
    }
}

