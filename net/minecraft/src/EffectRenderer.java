/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.src;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.minecraft.src.Block;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityDiggingFX;
import net.minecraft.src.EntityFX;
import net.minecraft.src.MathHelper;
import net.minecraft.src.RenderEngine;
import net.minecraft.src.Tessellator;
import net.minecraft.src.World;
import org.lwjgl.opengl.GL11;

public class EffectRenderer {
    protected World worldObj;
    private List[] fxLayers = new List[4];
    private RenderEngine renderer;
    private Random rand = new Random();

    public EffectRenderer(World var1, RenderEngine var2) {
        if (var1 != null) {
            this.worldObj = var1;
        }
        this.renderer = var2;
        int var3 = 0;
        while (var3 < 4) {
            this.fxLayers[var3] = new ArrayList();
            ++var3;
        }
    }

    public void addEffect(EntityFX var1) {
        int var2 = var1.getFXLayer();
        this.fxLayers[var2].add(var1);
    }

    public void updateEffects() {
        int var1 = 0;
        while (var1 < 4) {
            int var2 = 0;
            while (var2 < this.fxLayers[var1].size()) {
                EntityFX var3 = (EntityFX)this.fxLayers[var1].get(var2);
                var3.onUpdate();
                if (var3.isDead) {
                    this.fxLayers[var1].remove(var2--);
                }
                ++var2;
            }
            ++var1;
        }
    }

    public void renderParticles(Entity var1, float var2) {
        float var3 = MathHelper.cos(var1.rotationYaw * (float)Math.PI / 180.0f);
        float var4 = MathHelper.sin(var1.rotationYaw * (float)Math.PI / 180.0f);
        float var5 = -var4 * MathHelper.sin(var1.rotationPitch * (float)Math.PI / 180.0f);
        float var6 = var3 * MathHelper.sin(var1.rotationPitch * (float)Math.PI / 180.0f);
        float var7 = MathHelper.cos(var1.rotationPitch * (float)Math.PI / 180.0f);
        EntityFX.interpPosX = var1.lastTickPosX + (var1.posX - var1.lastTickPosX) * (double)var2;
        EntityFX.interpPosY = var1.lastTickPosY + (var1.posY - var1.lastTickPosY) * (double)var2;
        EntityFX.interpPosZ = var1.lastTickPosZ + (var1.posZ - var1.lastTickPosZ) * (double)var2;
        int var8 = 0;
        while (var8 < 3) {
            if (this.fxLayers[var8].size() != 0) {
                int var9 = 0;
                if (var8 == 0) {
                    var9 = this.renderer.getTexture("/particles.png");
                }
                if (var8 == 1) {
                    var9 = this.renderer.getTexture("/terrain.png");
                }
                if (var8 == 2) {
                    var9 = this.renderer.getTexture("/gui/items.png");
                }
                GL11.glBindTexture((int)3553, (int)var9);
                Tessellator var10 = Tessellator.instance;
                var10.startDrawingQuads();
                int var11 = 0;
                while (var11 < this.fxLayers[var8].size()) {
                    EntityFX var12 = (EntityFX)this.fxLayers[var8].get(var11);
                    var12.renderParticle(var10, var2, var3, var7, var4, var5, var6);
                    ++var11;
                }
                var10.draw();
            }
            ++var8;
        }
    }

    public void func_1187_b(Entity var1, float var2) {
        int var3 = 3;
        if (this.fxLayers[var3].size() != 0) {
            Tessellator var4 = Tessellator.instance;
            int var5 = 0;
            while (var5 < this.fxLayers[var3].size()) {
                EntityFX var6 = (EntityFX)this.fxLayers[var3].get(var5);
                var6.renderParticle(var4, var2, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f);
                ++var5;
            }
        }
    }

    public void clearEffects(World var1) {
        this.worldObj = var1;
        int var2 = 0;
        while (var2 < 4) {
            this.fxLayers[var2].clear();
            ++var2;
        }
    }

    public void addBlockDestroyEffects(int var1, int var2, int var3) {
        int var4 = this.worldObj.getBlockId(var1, var2, var3);
        if (var4 != 0) {
            Block var5 = Block.blocksList[var4];
            int var6 = 4;
            int var7 = 0;
            while (var7 < var6) {
                int var8 = 0;
                while (var8 < var6) {
                    int var9 = 0;
                    while (var9 < var6) {
                        double var10 = (double)var1 + ((double)var7 + 0.5) / (double)var6;
                        double var12 = (double)var2 + ((double)var8 + 0.5) / (double)var6;
                        double var14 = (double)var3 + ((double)var9 + 0.5) / (double)var6;
                        this.addEffect(new EntityDiggingFX(this.worldObj, var10, var12, var14, var10 - (double)var1 - 0.5, var12 - (double)var2 - 0.5, var14 - (double)var3 - 0.5, var5).func_4041_a(var1, var2, var3));
                        ++var9;
                    }
                    ++var8;
                }
                ++var7;
            }
        }
    }

    public void addBlockHitEffects(int var1, int var2, int var3, int var4) {
        int var5 = this.worldObj.getBlockId(var1, var2, var3);
        if (var5 != 0) {
            Block var6 = Block.blocksList[var5];
            float var7 = 0.1f;
            double var8 = (double)var1 + this.rand.nextDouble() * (var6.maxX - var6.minX - (double)(var7 * 2.0f)) + (double)var7 + var6.minX;
            double var10 = (double)var2 + this.rand.nextDouble() * (var6.maxY - var6.minY - (double)(var7 * 2.0f)) + (double)var7 + var6.minY;
            double var12 = (double)var3 + this.rand.nextDouble() * (var6.maxZ - var6.minZ - (double)(var7 * 2.0f)) + (double)var7 + var6.minZ;
            if (var4 == 0) {
                var10 = (double)var2 + var6.minY - (double)var7;
            }
            if (var4 == 1) {
                var10 = (double)var2 + var6.maxY + (double)var7;
            }
            if (var4 == 2) {
                var12 = (double)var3 + var6.minZ - (double)var7;
            }
            if (var4 == 3) {
                var12 = (double)var3 + var6.maxZ + (double)var7;
            }
            if (var4 == 4) {
                var8 = (double)var1 + var6.minX - (double)var7;
            }
            if (var4 == 5) {
                var8 = (double)var1 + var6.maxX + (double)var7;
            }
            this.addEffect(new EntityDiggingFX(this.worldObj, var8, var10, var12, 0.0, 0.0, 0.0, var6).func_4041_a(var1, var2, var3).func_407_b(0.2f).func_405_d(0.6f));
        }
    }

    public String getStatistics() {
        return "" + (this.fxLayers[0].size() + this.fxLayers[1].size() + this.fxLayers[2].size());
    }
}

