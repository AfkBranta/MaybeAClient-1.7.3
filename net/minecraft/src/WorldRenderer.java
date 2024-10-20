/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.src;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Block;
import net.minecraft.src.Chunk;
import net.minecraft.src.ChunkCache;
import net.minecraft.src.Entity;
import net.minecraft.src.ICamera;
import net.minecraft.src.MathHelper;
import net.minecraft.src.RenderBlocks;
import net.minecraft.src.RenderItem;
import net.minecraft.src.Tessellator;
import net.minecraft.src.TileEntity;
import net.minecraft.src.TileEntityRenderer;
import net.minecraft.src.World;
import net.skidcode.gh.maybeaclient.hacks.BlockESPHack;
import net.skidcode.gh.maybeaclient.utils.BlockPos;
import org.lwjgl.opengl.GL11;

public class WorldRenderer {
    public World worldObj;
    private int glRenderList = -1;
    private static Tessellator tessellator = Tessellator.instance;
    public static int chunksUpdated = 0;
    public int posX;
    public int posY;
    public int posZ;
    public int sizeWidth;
    public int sizeHeight;
    public int sizeDepth;
    public int field_1755_i;
    public int field_1754_j;
    public int field_1753_k;
    public int field_1752_l;
    public int field_1751_m;
    public int field_1750_n;
    public boolean isInFrustum = false;
    public boolean[] skipRenderPass = new boolean[2];
    public int field_1746_q;
    public int field_1743_r;
    public int field_1741_s;
    public float field_1740_t;
    public boolean needsUpdate;
    public AxisAlignedBB field_1736_v;
    public int field_1735_w;
    public boolean isVisible = true;
    public boolean isWaitingOnOcclusionQuery;
    public int field_1732_z;
    public boolean field_1747_A;
    private boolean isInitialized = false;
    public List tileEntityRenderers = new ArrayList();
    private List tileEntities;

    public WorldRenderer(World var1, List var2, int var3, int var4, int var5, int var6, int var7) {
        this.worldObj = var1;
        this.tileEntities = var2;
        this.sizeHeight = this.sizeDepth = var6;
        this.sizeWidth = this.sizeDepth;
        this.field_1740_t = MathHelper.sqrt_float(this.sizeWidth * this.sizeWidth + this.sizeHeight * this.sizeHeight + this.sizeDepth * this.sizeDepth) / 2.0f;
        this.glRenderList = var7;
        this.posX = -999;
        this.setPosition(var3, var4, var5);
        this.needsUpdate = false;
    }

    public void setPosition(int var1, int var2, int var3) {
        if (var1 != this.posX || var2 != this.posY || var3 != this.posZ) {
            this.setDontDraw();
            this.posX = var1;
            this.posY = var2;
            this.posZ = var3;
            this.field_1746_q = var1 + this.sizeWidth / 2;
            this.field_1743_r = var2 + this.sizeHeight / 2;
            this.field_1741_s = var3 + this.sizeDepth / 2;
            this.field_1752_l = var1 & 0x3FF;
            this.field_1751_m = var2;
            this.field_1750_n = var3 & 0x3FF;
            this.field_1755_i = var1 - this.field_1752_l;
            this.field_1754_j = var2 - this.field_1751_m;
            this.field_1753_k = var3 - this.field_1750_n;
            float var4 = 6.0f;
            this.field_1736_v = AxisAlignedBB.getBoundingBox((float)var1 - var4, (float)var2 - var4, (float)var3 - var4, (float)(var1 + this.sizeWidth) + var4, (float)(var2 + this.sizeHeight) + var4, (float)(var3 + this.sizeDepth) + var4);
            GL11.glNewList((int)(this.glRenderList + 2), (int)4864);
            RenderItem.renderAABB(AxisAlignedBB.getBoundingBoxFromPool((float)this.field_1752_l - var4, (float)this.field_1751_m - var4, (float)this.field_1750_n - var4, (float)(this.field_1752_l + this.sizeWidth) + var4, (float)(this.field_1751_m + this.sizeHeight) + var4, (float)(this.field_1750_n + this.sizeDepth) + var4));
            GL11.glEndList();
            this.markDirty();
        }
    }

    private void setupGLTranslation() {
        GL11.glTranslatef((float)this.field_1752_l, (float)this.field_1751_m, (float)this.field_1750_n);
    }

    public void updateRenderer() {
        if (this.needsUpdate) {
            ++chunksUpdated;
            int var1 = this.posX;
            int var2 = this.posY;
            int var3 = this.posZ;
            int var4 = this.posX + this.sizeWidth;
            int var5 = this.posY + this.sizeHeight;
            int var6 = this.posZ + this.sizeDepth;
            int var7 = 0;
            while (var7 < 2) {
                this.skipRenderPass[var7] = true;
                ++var7;
            }
            Chunk.isLit = false;
            HashSet var21 = new HashSet();
            var21.addAll(this.tileEntityRenderers);
            this.tileEntityRenderers.clear();
            int var8 = 1;
            ChunkCache var9 = new ChunkCache(this.worldObj, var1 - var8, var2 - var8, var3 - var8, var4 + var8, var5 + var8, var6 + var8);
            RenderBlocks var10 = new RenderBlocks(var9);
            int var11 = 0;
            while (var11 < 2) {
                boolean var12 = false;
                boolean var13 = false;
                boolean var14 = false;
                int y = var2;
                while (y < var5) {
                    int z = var3;
                    while (z < var6) {
                        int x = var1;
                        while (x < var4) {
                            int id = var9.getBlockId(x, y, z);
                            if (id > 0) {
                                Block var24;
                                int var20;
                                TileEntity var23;
                                BlockPos pos;
                                if (!var14) {
                                    var14 = true;
                                    GL11.glNewList((int)(this.glRenderList + var11), (int)4864);
                                    GL11.glPushMatrix();
                                    this.setupGLTranslation();
                                    float var19 = 1.000001f;
                                    GL11.glTranslatef((float)((float)(-this.sizeDepth) / 2.0f), (float)((float)(-this.sizeHeight) / 2.0f), (float)((float)(-this.sizeDepth) / 2.0f));
                                    GL11.glScalef((float)var19, (float)var19, (float)var19);
                                    GL11.glTranslatef((float)((float)this.sizeDepth / 2.0f), (float)((float)this.sizeHeight / 2.0f), (float)((float)this.sizeDepth / 2.0f));
                                    tessellator.startDrawingQuads();
                                    tessellator.setTranslationD(-this.posX, -this.posY, -this.posZ);
                                }
                                if (BlockESPHack.instance.status && BlockESPHack.instance.blocks.blocks[id] && !BlockESPHack.blocksToRender.contains(pos = new BlockPos(x, y, z))) {
                                    BlockESPHack.blocksToRender.add(pos);
                                }
                                if (var11 == 0 && Block.isBlockContainer[id] && TileEntityRenderer.instance.hasSpecialRenderer(var23 = var9.getBlockTileEntity(x, y, z))) {
                                    this.tileEntityRenderers.add(var23);
                                }
                                if ((var20 = (var24 = Block.blocksList[id]).getRenderBlockPass()) != var11) {
                                    var12 = true;
                                } else if (var20 == var11) {
                                    var13 |= var10.renderBlockByRenderType(var24, x, y, z);
                                }
                            }
                            ++x;
                        }
                        ++z;
                    }
                    ++y;
                }
                if (var14) {
                    tessellator.draw();
                    GL11.glPopMatrix();
                    GL11.glEndList();
                    tessellator.setTranslationD(0.0, 0.0, 0.0);
                } else {
                    var13 = false;
                }
                if (var13) {
                    this.skipRenderPass[var11] = false;
                }
                if (!var12) break;
                ++var11;
            }
            HashSet var22 = new HashSet();
            var22.addAll(this.tileEntityRenderers);
            var22.removeAll(var21);
            this.tileEntities.addAll(var22);
            var21.removeAll(this.tileEntityRenderers);
            this.tileEntities.removeAll(var21);
            this.field_1747_A = Chunk.isLit;
            this.isInitialized = true;
        }
    }

    public float distanceToEntitySquared(Entity var1) {
        float var2 = (float)(var1.posX - (double)this.field_1746_q);
        float var3 = (float)(var1.posY - (double)this.field_1743_r);
        float var4 = (float)(var1.posZ - (double)this.field_1741_s);
        return var2 * var2 + var3 * var3 + var4 * var4;
    }

    public void setDontDraw() {
        int var1 = 0;
        while (var1 < 2) {
            this.skipRenderPass[var1] = true;
            ++var1;
        }
        this.isInFrustum = false;
        this.isInitialized = false;
    }

    public void func_1204_c() {
        this.setDontDraw();
        this.worldObj = null;
    }

    public int getGLCallListForPass(int var1) {
        if (!this.isInFrustum) {
            return -1;
        }
        return !this.skipRenderPass[var1] ? this.glRenderList + var1 : -1;
    }

    public void updateInFrustrum(ICamera var1) {
        this.isInFrustum = var1.isBoundingBoxInFrustum(this.field_1736_v);
    }

    public void callOcclusionQueryList() {
        GL11.glCallList((int)(this.glRenderList + 2));
    }

    public boolean canRender() {
        if (!this.isInitialized) {
            return false;
        }
        return this.skipRenderPass[0] && this.skipRenderPass[1];
    }

    public void markDirty() {
        this.needsUpdate = true;
    }
}

