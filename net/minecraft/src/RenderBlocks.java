/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.src;

import net.minecraft.client.Minecraft;
import net.minecraft.src.Block;
import net.minecraft.src.BlockBed;
import net.minecraft.src.BlockDoor;
import net.minecraft.src.BlockFluids;
import net.minecraft.src.BlockRedstoneRepeater;
import net.minecraft.src.BlockRedstoneWire;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.Material;
import net.minecraft.src.MathHelper;
import net.minecraft.src.ModelBed;
import net.minecraft.src.Tessellator;
import net.minecraft.src.Vec3D;
import net.minecraft.src.World;
import net.skidcode.gh.maybeaclient.hacks.XRayHack;
import org.lwjgl.opengl.GL11;

public class RenderBlocks {
    private IBlockAccess blockAccess;
    private int overrideBlockTexture = -1;
    private boolean flipTexture = false;
    private boolean renderAllFaces = false;
    private boolean enableAO;
    private float field_22384_f;
    private float aoLightValueXNeg;
    private float aoLightValueYNeg;
    private float aoLightValueZNeg;
    private float aoLightValueXPos;
    private float aoLightValueYPos;
    private float aoLightValueZPos;
    private float field_22377_m;
    private float field_22376_n;
    private float field_22375_o;
    private float field_22374_p;
    private float field_22373_q;
    private float field_22372_r;
    private float field_22371_s;
    private float field_22370_t;
    private float field_22369_u;
    private float field_22368_v;
    private float field_22367_w;
    private float field_22366_x;
    private float field_22365_y;
    private float field_22364_z;
    private float field_22362_A;
    private float field_22360_B;
    private float field_22358_C;
    private float field_22356_D;
    private float field_22354_E;
    private float field_22353_F;
    private int field_22352_G = 1;
    private float colorRedTopLeft;
    private float colorRedBottomLeft;
    private float colorRedBottomRight;
    private float colorRedTopRight;
    private float colorGreenTopLeft;
    private float colorGreenBottomLeft;
    private float colorGreenBottomRight;
    private float colorGreenTopRight;
    private float colorBlueTopLeft;
    private float colorBlueBottomLeft;
    private float colorBlueBottomRight;
    private float colorBlueTopRight;
    private boolean field_22339_T;
    private boolean field_22338_U;
    private boolean field_22337_V;
    private boolean field_22336_W;
    private boolean field_22335_X;
    private boolean field_22334_Y;
    private boolean field_22333_Z;
    private boolean field_22363_aa;
    private boolean field_22361_ab;
    private boolean field_22359_ac;
    private boolean field_22357_ad;
    private boolean field_22355_ae;

    public RenderBlocks(IBlockAccess var1) {
        this.blockAccess = var1;
    }

    public RenderBlocks() {
    }

    public void renderBlockUsingTexture(Block var1, int var2, int var3, int var4, int var5) {
        this.overrideBlockTexture = var5;
        this.renderBlockByRenderType(var1, var2, var3, var4);
        this.overrideBlockTexture = -1;
    }

    public boolean renderBlockByRenderType(Block var1, int var2, int var3, int var4) {
        int var5 = var1.getRenderType();
        var1.setBlockBoundsBasedOnState(this.blockAccess, var2, var3, var4);
        if (XRayHack.INSTANCE.status && XRayHack.INSTANCE.mode.currentMode.equalsIgnoreCase("Opacity")) {
            this.renderAllFaces = XRayHack.INSTANCE.blockChooser.blocks[var1.blockID];
        }
        if (var5 == 0) {
            return this.renderStandardBlock(var1, var2, var3, var4);
        }
        if (var5 == 4) {
            return this.renderBlockFluids(var1, var2, var3, var4);
        }
        if (var5 == 13) {
            return this.renderBlockCactus(var1, var2, var3, var4);
        }
        if (var5 == 1) {
            return this.renderBlockReed(var1, var2, var3, var4);
        }
        if (var5 == 6) {
            return this.renderBlockCrops(var1, var2, var3, var4);
        }
        if (var5 == 2) {
            return this.renderBlockTorch(var1, var2, var3, var4);
        }
        if (var5 == 3) {
            return this.renderBlockFire(var1, var2, var3, var4);
        }
        if (var5 == 5) {
            return this.renderBlockRedstoneWire(var1, var2, var3, var4);
        }
        if (var5 == 8) {
            return this.renderBlockLadder(var1, var2, var3, var4);
        }
        if (var5 == 7) {
            return this.renderBlockDoor(var1, var2, var3, var4);
        }
        if (var5 == 9) {
            return this.renderBlockMinecartTrack(var1, var2, var3, var4);
        }
        if (var5 == 10) {
            return this.renderBlockStairs(var1, var2, var3, var4);
        }
        if (var5 == 11) {
            return this.renderBlockFence(var1, var2, var3, var4);
        }
        if (var5 == 12) {
            return this.renderBlockLever(var1, var2, var3, var4);
        }
        if (var5 == 14) {
            return this.renderBlockBed(var1, var2, var3, var4);
        }
        return var5 == 15 ? this.renderBlockRepeater(var1, var2, var3, var4) : false;
    }

    private boolean renderBlockBed(Block var1, int var2, int var3, int var4) {
        float var66;
        Tessellator var5 = Tessellator.instance;
        int var6 = this.blockAccess.getBlockMetadata(var2, var3, var4);
        int var7 = BlockBed.getDirectionFromMetadata(var6);
        boolean var8 = BlockBed.isBlockFootOfBed(var6);
        float var9 = 0.5f;
        float var10 = 1.0f;
        float var11 = 0.8f;
        float var12 = 0.6f;
        float var25 = var1.getBlockBrightness(this.blockAccess, var2, var3, var4);
        var5.setColorOpaque_F(var9 * var25, var9 * var25, var9 * var25);
        int var26 = var1.getBlockTexture(this.blockAccess, var2, var3, var4, 0);
        int var27 = (var26 & 0xF) << 4;
        int var28 = var26 & 0xF0;
        double var29 = (float)var27 / 256.0f;
        double var31 = ((double)(var27 + 16) - 0.01) / 256.0;
        double var33 = (float)var28 / 256.0f;
        double var35 = ((double)(var28 + 16) - 0.01) / 256.0;
        double var37 = (double)var2 + var1.minX;
        double var39 = (double)var2 + var1.maxX;
        double var41 = (double)var3 + var1.minY + 0.1875;
        double var43 = (double)var4 + var1.minZ;
        double var45 = (double)var4 + var1.maxZ;
        var5.addVertexWithUV(var37, var41, var45, var29, var35);
        var5.addVertexWithUV(var37, var41, var43, var29, var33);
        var5.addVertexWithUV(var39, var41, var43, var31, var33);
        var5.addVertexWithUV(var39, var41, var45, var31, var35);
        float var64 = var1.getBlockBrightness(this.blockAccess, var2, var3 + 1, var4);
        var5.setColorOpaque_F(var10 * var64, var10 * var64, var10 * var64);
        var27 = var1.getBlockTexture(this.blockAccess, var2, var3, var4, 1);
        var28 = (var27 & 0xF) << 4;
        int var67 = var27 & 0xF0;
        double var30 = (float)var28 / 256.0f;
        double var32 = ((double)(var28 + 16) - 0.01) / 256.0;
        double var34 = (float)var67 / 256.0f;
        double var36 = ((double)(var67 + 16) - 0.01) / 256.0;
        double var38 = var30;
        double var40 = var32;
        double var42 = var34;
        double var44 = var34;
        double var46 = var30;
        double var48 = var32;
        double var50 = var36;
        double var52 = var36;
        if (var7 == 0) {
            var40 = var30;
            var42 = var36;
            var46 = var32;
            var52 = var34;
        } else if (var7 == 2) {
            var38 = var32;
            var44 = var36;
            var48 = var30;
            var50 = var34;
        } else if (var7 == 3) {
            var38 = var32;
            var44 = var36;
            var48 = var30;
            var50 = var34;
            var40 = var30;
            var42 = var36;
            var46 = var32;
            var52 = var34;
        }
        double var54 = (double)var2 + var1.minX;
        double var56 = (double)var2 + var1.maxX;
        double var58 = (double)var3 + var1.maxY;
        double var60 = (double)var4 + var1.minZ;
        double var62 = (double)var4 + var1.maxZ;
        var5.addVertexWithUV(var56, var58, var62, var46, var50);
        var5.addVertexWithUV(var56, var58, var60, var38, var42);
        var5.addVertexWithUV(var54, var58, var60, var40, var44);
        var5.addVertexWithUV(var54, var58, var62, var48, var52);
        var26 = ModelBed.field_22280_a[var7];
        if (var8) {
            var26 = ModelBed.field_22280_a[ModelBed.field_22279_b[var7]];
        }
        int var65 = 4;
        switch (var7) {
            case 0: {
                var65 = 5;
                break;
            }
            case 1: {
                var65 = 3;
            }
            default: {
                break;
            }
            case 3: {
                var65 = 2;
            }
        }
        if (var26 != 2 && (this.renderAllFaces || var1.shouldSideBeRendered(this.blockAccess, var2, var3, var4 - 1, 2))) {
            var66 = var1.getBlockBrightness(this.blockAccess, var2, var3, var4 - 1);
            if (var1.minZ > 0.0) {
                var66 = var25;
            }
            var5.setColorOpaque_F(var11 * var66, var11 * var66, var11 * var66);
            this.flipTexture = var65 == 2;
            this.renderEastFace(var1, var2, var3, var4, var1.getBlockTexture(this.blockAccess, var2, var3, var4, 2));
        }
        if (var26 != 3 && (this.renderAllFaces || var1.shouldSideBeRendered(this.blockAccess, var2, var3, var4 + 1, 3))) {
            var66 = var1.getBlockBrightness(this.blockAccess, var2, var3, var4 + 1);
            if (var1.maxZ < 1.0) {
                var66 = var25;
            }
            var5.setColorOpaque_F(var11 * var66, var11 * var66, var11 * var66);
            this.flipTexture = var65 == 3;
            this.renderWestFace(var1, var2, var3, var4, var1.getBlockTexture(this.blockAccess, var2, var3, var4, 3));
        }
        if (var26 != 4 && (this.renderAllFaces || var1.shouldSideBeRendered(this.blockAccess, var2 - 1, var3, var4, 4))) {
            var66 = var1.getBlockBrightness(this.blockAccess, var2 - 1, var3, var4);
            if (var1.minX > 0.0) {
                var66 = var25;
            }
            var5.setColorOpaque_F(var12 * var66, var12 * var66, var12 * var66);
            this.flipTexture = var65 == 4;
            this.renderNorthFace(var1, var2, var3, var4, var1.getBlockTexture(this.blockAccess, var2, var3, var4, 4));
        }
        if (var26 != 5 && (this.renderAllFaces || var1.shouldSideBeRendered(this.blockAccess, var2 + 1, var3, var4, 5))) {
            var66 = var1.getBlockBrightness(this.blockAccess, var2 + 1, var3, var4);
            if (var1.maxX < 1.0) {
                var66 = var25;
            }
            var5.setColorOpaque_F(var12 * var66, var12 * var66, var12 * var66);
            this.flipTexture = var65 == 5;
            this.renderSouthFace(var1, var2, var3, var4, var1.getBlockTexture(this.blockAccess, var2, var3, var4, 5));
        }
        this.flipTexture = false;
        return true;
    }

    public boolean renderBlockTorch(Block var1, int var2, int var3, int var4) {
        int var5 = this.blockAccess.getBlockMetadata(var2, var3, var4);
        Tessellator var6 = Tessellator.instance;
        float var7 = var1.getBlockBrightness(this.blockAccess, var2, var3, var4);
        if (Block.lightValue[var1.blockID] > 0) {
            var7 = 1.0f;
        }
        var6.setColorOpaque_F(var7, var7, var7);
        double var8 = 0.4f;
        double var10 = 0.5 - var8;
        double var12 = 0.2f;
        if (var5 == 1) {
            this.renderTorchAtAngle(var1, (double)var2 - var10, (double)var3 + var12, var4, -var8, 0.0);
        } else if (var5 == 2) {
            this.renderTorchAtAngle(var1, (double)var2 + var10, (double)var3 + var12, var4, var8, 0.0);
        } else if (var5 == 3) {
            this.renderTorchAtAngle(var1, var2, (double)var3 + var12, (double)var4 - var10, 0.0, -var8);
        } else if (var5 == 4) {
            this.renderTorchAtAngle(var1, var2, (double)var3 + var12, (double)var4 + var10, 0.0, var8);
        } else {
            this.renderTorchAtAngle(var1, var2, var3, var4, 0.0, 0.0);
        }
        return true;
    }

    private boolean renderBlockRepeater(Block var1, int var2, int var3, int var4) {
        int var5 = this.blockAccess.getBlockMetadata(var2, var3, var4);
        int var6 = var5 & 3;
        int var7 = (var5 & 0xC) >> 2;
        this.renderStandardBlock(var1, var2, var3, var4);
        Tessellator var8 = Tessellator.instance;
        float var9 = var1.getBlockBrightness(this.blockAccess, var2, var3, var4);
        if (Block.lightValue[var1.blockID] > 0) {
            var9 = (var9 + 1.0f) * 0.5f;
        }
        var8.setColorOpaque_F(var9, var9, var9);
        double var10 = -0.1875;
        double var12 = 0.0;
        double var14 = 0.0;
        double var16 = 0.0;
        double var18 = 0.0;
        switch (var6) {
            case 0: {
                var18 = -0.3125;
                var14 = BlockRedstoneRepeater.field_22024_a[var7];
                break;
            }
            case 1: {
                var16 = 0.3125;
                var12 = -BlockRedstoneRepeater.field_22024_a[var7];
                break;
            }
            case 2: {
                var18 = 0.3125;
                var14 = -BlockRedstoneRepeater.field_22024_a[var7];
                break;
            }
            case 3: {
                var16 = -0.3125;
                var12 = BlockRedstoneRepeater.field_22024_a[var7];
            }
        }
        this.renderTorchAtAngle(var1, (double)var2 + var12, (double)var3 + var10, (double)var4 + var14, 0.0, 0.0);
        this.renderTorchAtAngle(var1, (double)var2 + var16, (double)var3 + var10, (double)var4 + var18, 0.0, 0.0);
        int var20 = var1.getBlockTextureFromSide(1);
        int var21 = (var20 & 0xF) << 4;
        int var22 = var20 & 0xF0;
        double var23 = (float)var21 / 256.0f;
        double var25 = ((float)var21 + 15.99f) / 256.0f;
        double var27 = (float)var22 / 256.0f;
        double var29 = ((float)var22 + 15.99f) / 256.0f;
        float var31 = 0.125f;
        float var32 = var2 + 1;
        float var33 = var2 + 1;
        float var34 = var2 + 0;
        float var35 = var2 + 0;
        float var36 = var4 + 0;
        float var37 = var4 + 1;
        float var38 = var4 + 1;
        float var39 = var4 + 0;
        float var40 = (float)var3 + var31;
        if (var6 == 2) {
            var32 = var33 = (float)(var2 + 0);
            var34 = var35 = (float)(var2 + 1);
            var36 = var39 = (float)(var4 + 1);
            var37 = var38 = (float)(var4 + 0);
        } else if (var6 == 3) {
            var32 = var35 = (float)(var2 + 0);
            var33 = var34 = (float)(var2 + 1);
            var36 = var37 = (float)(var4 + 0);
            var38 = var39 = (float)(var4 + 1);
        } else if (var6 == 1) {
            var32 = var35 = (float)(var2 + 1);
            var33 = var34 = (float)(var2 + 0);
            var36 = var37 = (float)(var4 + 1);
            var38 = var39 = (float)(var4 + 0);
        }
        var8.addVertexWithUV(var35, var40, var39, var23, var27);
        var8.addVertexWithUV(var34, var40, var38, var23, var29);
        var8.addVertexWithUV(var33, var40, var37, var25, var29);
        var8.addVertexWithUV(var32, var40, var36, var25, var27);
        return true;
    }

    public boolean renderBlockLever(Block var1, int var2, int var3, int var4) {
        boolean var9;
        int var5 = this.blockAccess.getBlockMetadata(var2, var3, var4);
        int var6 = var5 & 7;
        boolean var7 = (var5 & 8) > 0;
        Tessellator var8 = Tessellator.instance;
        boolean bl = var9 = this.overrideBlockTexture >= 0;
        if (!var9) {
            this.overrideBlockTexture = Block.cobblestone.blockIndexInTexture;
        }
        float var10 = 0.25f;
        float var11 = 0.1875f;
        float var12 = 0.1875f;
        if (var6 == 5) {
            var1.setBlockBounds(0.5f - var11, 0.0f, 0.5f - var10, 0.5f + var11, var12, 0.5f + var10);
        } else if (var6 == 6) {
            var1.setBlockBounds(0.5f - var10, 0.0f, 0.5f - var11, 0.5f + var10, var12, 0.5f + var11);
        } else if (var6 == 4) {
            var1.setBlockBounds(0.5f - var11, 0.5f - var10, 1.0f - var12, 0.5f + var11, 0.5f + var10, 1.0f);
        } else if (var6 == 3) {
            var1.setBlockBounds(0.5f - var11, 0.5f - var10, 0.0f, 0.5f + var11, 0.5f + var10, var12);
        } else if (var6 == 2) {
            var1.setBlockBounds(1.0f - var12, 0.5f - var10, 0.5f - var11, 1.0f, 0.5f + var10, 0.5f + var11);
        } else if (var6 == 1) {
            var1.setBlockBounds(0.0f, 0.5f - var10, 0.5f - var11, var12, 0.5f + var10, 0.5f + var11);
        }
        this.renderStandardBlock(var1, var2, var3, var4);
        if (!var9) {
            this.overrideBlockTexture = -1;
        }
        float var13 = var1.getBlockBrightness(this.blockAccess, var2, var3, var4);
        if (Block.lightValue[var1.blockID] > 0) {
            var13 = 1.0f;
        }
        var8.setColorOpaque_F(var13, var13, var13);
        int var14 = var1.getBlockTextureFromSide(0);
        if (this.overrideBlockTexture >= 0) {
            var14 = this.overrideBlockTexture;
        }
        int var15 = (var14 & 0xF) << 4;
        int var16 = var14 & 0xF0;
        float var17 = (float)var15 / 256.0f;
        float var18 = ((float)var15 + 15.99f) / 256.0f;
        float var19 = (float)var16 / 256.0f;
        float var20 = ((float)var16 + 15.99f) / 256.0f;
        Vec3D[] var21 = new Vec3D[8];
        float var22 = 0.0625f;
        float var23 = 0.0625f;
        float var24 = 0.625f;
        var21[0] = Vec3D.createVector(-var22, 0.0, -var23);
        var21[1] = Vec3D.createVector(var22, 0.0, -var23);
        var21[2] = Vec3D.createVector(var22, 0.0, var23);
        var21[3] = Vec3D.createVector(-var22, 0.0, var23);
        var21[4] = Vec3D.createVector(-var22, var24, -var23);
        var21[5] = Vec3D.createVector(var22, var24, -var23);
        var21[6] = Vec3D.createVector(var22, var24, var23);
        var21[7] = Vec3D.createVector(-var22, var24, var23);
        int var25 = 0;
        while (var25 < 8) {
            if (var7) {
                var21[var25].zCoord -= 0.0625;
                var21[var25].rotateAroundX(0.69813174f);
            } else {
                var21[var25].zCoord += 0.0625;
                var21[var25].rotateAroundX(-0.69813174f);
            }
            if (var6 == 6) {
                var21[var25].rotateAroundY(1.5707964f);
            }
            if (var6 < 5) {
                var21[var25].yCoord -= 0.375;
                var21[var25].rotateAroundX(1.5707964f);
                if (var6 == 4) {
                    var21[var25].rotateAroundY(0.0f);
                }
                if (var6 == 3) {
                    var21[var25].rotateAroundY((float)Math.PI);
                }
                if (var6 == 2) {
                    var21[var25].rotateAroundY(1.5707964f);
                }
                if (var6 == 1) {
                    var21[var25].rotateAroundY(-1.5707964f);
                }
                var21[var25].xCoord += (double)var2 + 0.5;
                var21[var25].yCoord += (double)((float)var3 + 0.5f);
                var21[var25].zCoord += (double)var4 + 0.5;
            } else {
                var21[var25].xCoord += (double)var2 + 0.5;
                var21[var25].yCoord += (double)((float)var3 + 0.125f);
                var21[var25].zCoord += (double)var4 + 0.5;
            }
            ++var25;
        }
        Vec3D var30 = null;
        Vec3D var26 = null;
        Vec3D var27 = null;
        Vec3D var28 = null;
        int var29 = 0;
        while (var29 < 6) {
            if (var29 == 0) {
                var17 = (float)(var15 + 7) / 256.0f;
                var18 = ((float)(var15 + 9) - 0.01f) / 256.0f;
                var19 = (float)(var16 + 6) / 256.0f;
                var20 = ((float)(var16 + 8) - 0.01f) / 256.0f;
            } else if (var29 == 2) {
                var17 = (float)(var15 + 7) / 256.0f;
                var18 = ((float)(var15 + 9) - 0.01f) / 256.0f;
                var19 = (float)(var16 + 6) / 256.0f;
                var20 = ((float)(var16 + 16) - 0.01f) / 256.0f;
            }
            if (var29 == 0) {
                var30 = var21[0];
                var26 = var21[1];
                var27 = var21[2];
                var28 = var21[3];
            } else if (var29 == 1) {
                var30 = var21[7];
                var26 = var21[6];
                var27 = var21[5];
                var28 = var21[4];
            } else if (var29 == 2) {
                var30 = var21[1];
                var26 = var21[0];
                var27 = var21[4];
                var28 = var21[5];
            } else if (var29 == 3) {
                var30 = var21[2];
                var26 = var21[1];
                var27 = var21[5];
                var28 = var21[6];
            } else if (var29 == 4) {
                var30 = var21[3];
                var26 = var21[2];
                var27 = var21[6];
                var28 = var21[7];
            } else if (var29 == 5) {
                var30 = var21[0];
                var26 = var21[3];
                var27 = var21[7];
                var28 = var21[4];
            }
            var8.addVertexWithUV(var30.xCoord, var30.yCoord, var30.zCoord, var17, var20);
            var8.addVertexWithUV(var26.xCoord, var26.yCoord, var26.zCoord, var18, var20);
            var8.addVertexWithUV(var27.xCoord, var27.yCoord, var27.zCoord, var18, var19);
            var8.addVertexWithUV(var28.xCoord, var28.yCoord, var28.zCoord, var17, var19);
            ++var29;
        }
        return true;
    }

    public boolean renderBlockFire(Block var1, int var2, int var3, int var4) {
        Tessellator var5 = Tessellator.instance;
        int var6 = var1.getBlockTextureFromSide(0);
        if (this.overrideBlockTexture >= 0) {
            var6 = this.overrideBlockTexture;
        }
        float var7 = var1.getBlockBrightness(this.blockAccess, var2, var3, var4);
        var5.setColorOpaque_F(var7, var7, var7);
        int var8 = (var6 & 0xF) << 4;
        int var9 = var6 & 0xF0;
        double var10 = (float)var8 / 256.0f;
        double var12 = ((float)var8 + 15.99f) / 256.0f;
        double var14 = (float)var9 / 256.0f;
        double var16 = ((float)var9 + 15.99f) / 256.0f;
        float var18 = 1.4f;
        if (!this.blockAccess.isBlockOpaqueCube(var2, var3 - 1, var4) && !Block.fire.canBlockCatchFire(this.blockAccess, var2, var3 - 1, var4)) {
            double var21;
            float var37 = 0.2f;
            float var20 = 0.0625f;
            if ((var2 + var3 + var4 & 1) == 1) {
                var10 = (float)var8 / 256.0f;
                var12 = ((float)var8 + 15.99f) / 256.0f;
                var14 = (float)(var9 + 16) / 256.0f;
                var16 = ((float)var9 + 15.99f + 16.0f) / 256.0f;
            }
            if ((var2 / 2 + var3 / 2 + var4 / 2 & 1) == 1) {
                var21 = var12;
                var12 = var10;
                var10 = var21;
            }
            if (Block.fire.canBlockCatchFire(this.blockAccess, var2 - 1, var3, var4)) {
                var5.addVertexWithUV((float)var2 + var37, (float)var3 + var18 + var20, var4 + 1, var12, var14);
                var5.addVertexWithUV(var2 + 0, (float)(var3 + 0) + var20, var4 + 1, var12, var16);
                var5.addVertexWithUV(var2 + 0, (float)(var3 + 0) + var20, var4 + 0, var10, var16);
                var5.addVertexWithUV((float)var2 + var37, (float)var3 + var18 + var20, var4 + 0, var10, var14);
                var5.addVertexWithUV((float)var2 + var37, (float)var3 + var18 + var20, var4 + 0, var10, var14);
                var5.addVertexWithUV(var2 + 0, (float)(var3 + 0) + var20, var4 + 0, var10, var16);
                var5.addVertexWithUV(var2 + 0, (float)(var3 + 0) + var20, var4 + 1, var12, var16);
                var5.addVertexWithUV((float)var2 + var37, (float)var3 + var18 + var20, var4 + 1, var12, var14);
            }
            if (Block.fire.canBlockCatchFire(this.blockAccess, var2 + 1, var3, var4)) {
                var5.addVertexWithUV((float)(var2 + 1) - var37, (float)var3 + var18 + var20, var4 + 0, var10, var14);
                var5.addVertexWithUV(var2 + 1 - 0, (float)(var3 + 0) + var20, var4 + 0, var10, var16);
                var5.addVertexWithUV(var2 + 1 - 0, (float)(var3 + 0) + var20, var4 + 1, var12, var16);
                var5.addVertexWithUV((float)(var2 + 1) - var37, (float)var3 + var18 + var20, var4 + 1, var12, var14);
                var5.addVertexWithUV((float)(var2 + 1) - var37, (float)var3 + var18 + var20, var4 + 1, var12, var14);
                var5.addVertexWithUV(var2 + 1 - 0, (float)(var3 + 0) + var20, var4 + 1, var12, var16);
                var5.addVertexWithUV(var2 + 1 - 0, (float)(var3 + 0) + var20, var4 + 0, var10, var16);
                var5.addVertexWithUV((float)(var2 + 1) - var37, (float)var3 + var18 + var20, var4 + 0, var10, var14);
            }
            if (Block.fire.canBlockCatchFire(this.blockAccess, var2, var3, var4 - 1)) {
                var5.addVertexWithUV(var2 + 0, (float)var3 + var18 + var20, (float)var4 + var37, var12, var14);
                var5.addVertexWithUV(var2 + 0, (float)(var3 + 0) + var20, var4 + 0, var12, var16);
                var5.addVertexWithUV(var2 + 1, (float)(var3 + 0) + var20, var4 + 0, var10, var16);
                var5.addVertexWithUV(var2 + 1, (float)var3 + var18 + var20, (float)var4 + var37, var10, var14);
                var5.addVertexWithUV(var2 + 1, (float)var3 + var18 + var20, (float)var4 + var37, var10, var14);
                var5.addVertexWithUV(var2 + 1, (float)(var3 + 0) + var20, var4 + 0, var10, var16);
                var5.addVertexWithUV(var2 + 0, (float)(var3 + 0) + var20, var4 + 0, var12, var16);
                var5.addVertexWithUV(var2 + 0, (float)var3 + var18 + var20, (float)var4 + var37, var12, var14);
            }
            if (Block.fire.canBlockCatchFire(this.blockAccess, var2, var3, var4 + 1)) {
                var5.addVertexWithUV(var2 + 1, (float)var3 + var18 + var20, (float)(var4 + 1) - var37, var10, var14);
                var5.addVertexWithUV(var2 + 1, (float)(var3 + 0) + var20, var4 + 1 - 0, var10, var16);
                var5.addVertexWithUV(var2 + 0, (float)(var3 + 0) + var20, var4 + 1 - 0, var12, var16);
                var5.addVertexWithUV(var2 + 0, (float)var3 + var18 + var20, (float)(var4 + 1) - var37, var12, var14);
                var5.addVertexWithUV(var2 + 0, (float)var3 + var18 + var20, (float)(var4 + 1) - var37, var12, var14);
                var5.addVertexWithUV(var2 + 0, (float)(var3 + 0) + var20, var4 + 1 - 0, var12, var16);
                var5.addVertexWithUV(var2 + 1, (float)(var3 + 0) + var20, var4 + 1 - 0, var10, var16);
                var5.addVertexWithUV(var2 + 1, (float)var3 + var18 + var20, (float)(var4 + 1) - var37, var10, var14);
            }
            if (Block.fire.canBlockCatchFire(this.blockAccess, var2, var3 + 1, var4)) {
                var21 = (double)var2 + 0.5 + 0.5;
                double var23 = (double)var2 + 0.5 - 0.5;
                double var25 = (double)var4 + 0.5 + 0.5;
                double var27 = (double)var4 + 0.5 - 0.5;
                double var29 = (double)var2 + 0.5 - 0.5;
                double var31 = (double)var2 + 0.5 + 0.5;
                double var33 = (double)var4 + 0.5 - 0.5;
                double var35 = (double)var4 + 0.5 + 0.5;
                var10 = (float)var8 / 256.0f;
                var12 = ((float)var8 + 15.99f) / 256.0f;
                var14 = (float)var9 / 256.0f;
                var16 = ((float)var9 + 15.99f) / 256.0f;
                var18 = -0.2f;
                if ((var2 + ++var3 + var4 & 1) == 0) {
                    var5.addVertexWithUV(var29, (float)var3 + var18, var4 + 0, var12, var14);
                    var5.addVertexWithUV(var21, var3 + 0, var4 + 0, var12, var16);
                    var5.addVertexWithUV(var21, var3 + 0, var4 + 1, var10, var16);
                    var5.addVertexWithUV(var29, (float)var3 + var18, var4 + 1, var10, var14);
                    var10 = (float)var8 / 256.0f;
                    var12 = ((float)var8 + 15.99f) / 256.0f;
                    var14 = (float)(var9 + 16) / 256.0f;
                    var16 = ((float)var9 + 15.99f + 16.0f) / 256.0f;
                    var5.addVertexWithUV(var31, (float)var3 + var18, var4 + 1, var12, var14);
                    var5.addVertexWithUV(var23, var3 + 0, var4 + 1, var12, var16);
                    var5.addVertexWithUV(var23, var3 + 0, var4 + 0, var10, var16);
                    var5.addVertexWithUV(var31, (float)var3 + var18, var4 + 0, var10, var14);
                } else {
                    var5.addVertexWithUV(var2 + 0, (float)var3 + var18, var35, var12, var14);
                    var5.addVertexWithUV(var2 + 0, var3 + 0, var27, var12, var16);
                    var5.addVertexWithUV(var2 + 1, var3 + 0, var27, var10, var16);
                    var5.addVertexWithUV(var2 + 1, (float)var3 + var18, var35, var10, var14);
                    var10 = (float)var8 / 256.0f;
                    var12 = ((float)var8 + 15.99f) / 256.0f;
                    var14 = (float)(var9 + 16) / 256.0f;
                    var16 = ((float)var9 + 15.99f + 16.0f) / 256.0f;
                    var5.addVertexWithUV(var2 + 1, (float)var3 + var18, var33, var12, var14);
                    var5.addVertexWithUV(var2 + 1, var3 + 0, var25, var12, var16);
                    var5.addVertexWithUV(var2 + 0, var3 + 0, var25, var10, var16);
                    var5.addVertexWithUV(var2 + 0, (float)var3 + var18, var33, var10, var14);
                }
            }
        } else {
            double var19 = (double)var2 + 0.5 + 0.2;
            double var21 = (double)var2 + 0.5 - 0.2;
            double var23 = (double)var4 + 0.5 + 0.2;
            double var25 = (double)var4 + 0.5 - 0.2;
            double var27 = (double)var2 + 0.5 - 0.3;
            double var29 = (double)var2 + 0.5 + 0.3;
            double var31 = (double)var4 + 0.5 - 0.3;
            double var33 = (double)var4 + 0.5 + 0.3;
            var5.addVertexWithUV(var27, (float)var3 + var18, var4 + 1, var12, var14);
            var5.addVertexWithUV(var19, var3 + 0, var4 + 1, var12, var16);
            var5.addVertexWithUV(var19, var3 + 0, var4 + 0, var10, var16);
            var5.addVertexWithUV(var27, (float)var3 + var18, var4 + 0, var10, var14);
            var5.addVertexWithUV(var29, (float)var3 + var18, var4 + 0, var12, var14);
            var5.addVertexWithUV(var21, var3 + 0, var4 + 0, var12, var16);
            var5.addVertexWithUV(var21, var3 + 0, var4 + 1, var10, var16);
            var5.addVertexWithUV(var29, (float)var3 + var18, var4 + 1, var10, var14);
            var10 = (float)var8 / 256.0f;
            var12 = ((float)var8 + 15.99f) / 256.0f;
            var14 = (float)(var9 + 16) / 256.0f;
            var16 = ((float)var9 + 15.99f + 16.0f) / 256.0f;
            var5.addVertexWithUV(var2 + 1, (float)var3 + var18, var33, var12, var14);
            var5.addVertexWithUV(var2 + 1, var3 + 0, var25, var12, var16);
            var5.addVertexWithUV(var2 + 0, var3 + 0, var25, var10, var16);
            var5.addVertexWithUV(var2 + 0, (float)var3 + var18, var33, var10, var14);
            var5.addVertexWithUV(var2 + 0, (float)var3 + var18, var31, var12, var14);
            var5.addVertexWithUV(var2 + 0, var3 + 0, var23, var12, var16);
            var5.addVertexWithUV(var2 + 1, var3 + 0, var23, var10, var16);
            var5.addVertexWithUV(var2 + 1, (float)var3 + var18, var31, var10, var14);
            var19 = (double)var2 + 0.5 - 0.5;
            var21 = (double)var2 + 0.5 + 0.5;
            var23 = (double)var4 + 0.5 - 0.5;
            var25 = (double)var4 + 0.5 + 0.5;
            var27 = (double)var2 + 0.5 - 0.4;
            var29 = (double)var2 + 0.5 + 0.4;
            var31 = (double)var4 + 0.5 - 0.4;
            var33 = (double)var4 + 0.5 + 0.4;
            var5.addVertexWithUV(var27, (float)var3 + var18, var4 + 0, var10, var14);
            var5.addVertexWithUV(var19, var3 + 0, var4 + 0, var10, var16);
            var5.addVertexWithUV(var19, var3 + 0, var4 + 1, var12, var16);
            var5.addVertexWithUV(var27, (float)var3 + var18, var4 + 1, var12, var14);
            var5.addVertexWithUV(var29, (float)var3 + var18, var4 + 1, var10, var14);
            var5.addVertexWithUV(var21, var3 + 0, var4 + 1, var10, var16);
            var5.addVertexWithUV(var21, var3 + 0, var4 + 0, var12, var16);
            var5.addVertexWithUV(var29, (float)var3 + var18, var4 + 0, var12, var14);
            var10 = (float)var8 / 256.0f;
            var12 = ((float)var8 + 15.99f) / 256.0f;
            var14 = (float)var9 / 256.0f;
            var16 = ((float)var9 + 15.99f) / 256.0f;
            var5.addVertexWithUV(var2 + 0, (float)var3 + var18, var33, var10, var14);
            var5.addVertexWithUV(var2 + 0, var3 + 0, var25, var10, var16);
            var5.addVertexWithUV(var2 + 1, var3 + 0, var25, var12, var16);
            var5.addVertexWithUV(var2 + 1, (float)var3 + var18, var33, var12, var14);
            var5.addVertexWithUV(var2 + 1, (float)var3 + var18, var31, var10, var14);
            var5.addVertexWithUV(var2 + 1, var3 + 0, var23, var10, var16);
            var5.addVertexWithUV(var2 + 0, var3 + 0, var23, var12, var16);
            var5.addVertexWithUV(var2 + 0, (float)var3 + var18, var31, var12, var14);
        }
        return true;
    }

    public boolean renderBlockRedstoneWire(Block var1, int var2, int var3, int var4) {
        boolean var28;
        Tessellator var5 = Tessellator.instance;
        int var6 = this.blockAccess.getBlockMetadata(var2, var3, var4);
        int var7 = var1.getBlockTextureFromSideAndMetadata(1, var6);
        if (this.overrideBlockTexture >= 0) {
            var7 = this.overrideBlockTexture;
        }
        float var8 = var1.getBlockBrightness(this.blockAccess, var2, var3, var4);
        float var9 = (float)var6 / 15.0f;
        float var10 = var9 * 0.6f + 0.4f;
        if (var6 == 0) {
            var10 = 0.0f;
        }
        float var11 = var9 * var9 * 0.7f - 0.5f;
        float var12 = var9 * var9 * 0.6f - 0.7f;
        if (var11 < 0.0f) {
            var11 = 0.0f;
        }
        if (var12 < 0.0f) {
            var12 = 0.0f;
        }
        var5.setColorOpaque_F(var8 * var10, var8 * var11, var8 * var12);
        int var13 = (var7 & 0xF) << 4;
        int var14 = var7 & 0xF0;
        double var15 = (float)var13 / 256.0f;
        double var17 = ((float)var13 + 15.99f) / 256.0f;
        double var19 = (float)var14 / 256.0f;
        double var21 = ((float)var14 + 15.99f) / 256.0f;
        float var23 = 0.0f;
        float var24 = 0.03125f;
        boolean var25 = BlockRedstoneWire.isPowerProviderOrWire(this.blockAccess, var2 - 1, var3, var4) || !this.blockAccess.isBlockOpaqueCube(var2 - 1, var3, var4) && BlockRedstoneWire.isPowerProviderOrWire(this.blockAccess, var2 - 1, var3 - 1, var4);
        boolean var26 = BlockRedstoneWire.isPowerProviderOrWire(this.blockAccess, var2 + 1, var3, var4) || !this.blockAccess.isBlockOpaqueCube(var2 + 1, var3, var4) && BlockRedstoneWire.isPowerProviderOrWire(this.blockAccess, var2 + 1, var3 - 1, var4);
        boolean var27 = BlockRedstoneWire.isPowerProviderOrWire(this.blockAccess, var2, var3, var4 - 1) || !this.blockAccess.isBlockOpaqueCube(var2, var3, var4 - 1) && BlockRedstoneWire.isPowerProviderOrWire(this.blockAccess, var2, var3 - 1, var4 - 1);
        boolean bl = var28 = BlockRedstoneWire.isPowerProviderOrWire(this.blockAccess, var2, var3, var4 + 1) || !this.blockAccess.isBlockOpaqueCube(var2, var3, var4 + 1) && BlockRedstoneWire.isPowerProviderOrWire(this.blockAccess, var2, var3 - 1, var4 + 1);
        if (!this.blockAccess.isBlockOpaqueCube(var2, var3 + 1, var4)) {
            if (this.blockAccess.isBlockOpaqueCube(var2 - 1, var3, var4) && BlockRedstoneWire.isPowerProviderOrWire(this.blockAccess, var2 - 1, var3 + 1, var4)) {
                var25 = true;
            }
            if (this.blockAccess.isBlockOpaqueCube(var2 + 1, var3, var4) && BlockRedstoneWire.isPowerProviderOrWire(this.blockAccess, var2 + 1, var3 + 1, var4)) {
                var26 = true;
            }
            if (this.blockAccess.isBlockOpaqueCube(var2, var3, var4 - 1) && BlockRedstoneWire.isPowerProviderOrWire(this.blockAccess, var2, var3 + 1, var4 - 1)) {
                var27 = true;
            }
            if (this.blockAccess.isBlockOpaqueCube(var2, var3, var4 + 1) && BlockRedstoneWire.isPowerProviderOrWire(this.blockAccess, var2, var3 + 1, var4 + 1)) {
                var28 = true;
            }
        }
        float var29 = 0.3125f;
        float var30 = var2 + 0;
        float var31 = var2 + 1;
        float var32 = var4 + 0;
        float var33 = var4 + 1;
        int var34 = 0;
        if ((var25 || var26) && !var27 && !var28) {
            var34 = 1;
        }
        if ((var27 || var28) && !var26 && !var25) {
            var34 = 2;
        }
        if (var34 != 0) {
            var15 = (float)(var13 + 16) / 256.0f;
            var17 = ((float)(var13 + 16) + 15.99f) / 256.0f;
            var19 = (float)var14 / 256.0f;
            var21 = ((float)var14 + 15.99f) / 256.0f;
        }
        if (var34 == 0) {
            if (var26 || var27 || var28 || var25) {
                if (!var25) {
                    var30 += var29;
                }
                if (!var25) {
                    var15 += (double)(var29 / 16.0f);
                }
                if (!var26) {
                    var31 -= var29;
                }
                if (!var26) {
                    var17 -= (double)(var29 / 16.0f);
                }
                if (!var27) {
                    var32 += var29;
                }
                if (!var27) {
                    var19 += (double)(var29 / 16.0f);
                }
                if (!var28) {
                    var33 -= var29;
                }
                if (!var28) {
                    var21 -= (double)(var29 / 16.0f);
                }
            }
            var5.addVertexWithUV(var31 + var23, (float)var3 + var24, var33 + var23, var17, var21);
            var5.addVertexWithUV(var31 + var23, (float)var3 + var24, var32 - var23, var17, var19);
            var5.addVertexWithUV(var30 - var23, (float)var3 + var24, var32 - var23, var15, var19);
            var5.addVertexWithUV(var30 - var23, (float)var3 + var24, var33 + var23, var15, var21);
        }
        if (var34 == 1) {
            var5.addVertexWithUV(var31 + var23, (float)var3 + var24, var33 + var23, var17, var21);
            var5.addVertexWithUV(var31 + var23, (float)var3 + var24, var32 - var23, var17, var19);
            var5.addVertexWithUV(var30 - var23, (float)var3 + var24, var32 - var23, var15, var19);
            var5.addVertexWithUV(var30 - var23, (float)var3 + var24, var33 + var23, var15, var21);
        }
        if (var34 == 2) {
            var5.addVertexWithUV(var31 + var23, (float)var3 + var24, var33 + var23, var17, var21);
            var5.addVertexWithUV(var31 + var23, (float)var3 + var24, var32 - var23, var15, var21);
            var5.addVertexWithUV(var30 - var23, (float)var3 + var24, var32 - var23, var15, var19);
            var5.addVertexWithUV(var30 - var23, (float)var3 + var24, var33 + var23, var17, var19);
        }
        var15 = (float)(var13 + 16) / 256.0f;
        var17 = ((float)(var13 + 16) + 15.99f) / 256.0f;
        var19 = (float)var14 / 256.0f;
        var21 = ((float)var14 + 15.99f) / 256.0f;
        if (!this.blockAccess.isBlockOpaqueCube(var2, var3 + 1, var4)) {
            if (this.blockAccess.isBlockOpaqueCube(var2 - 1, var3, var4) && this.blockAccess.getBlockId(var2 - 1, var3 + 1, var4) == Block.redstoneWire.blockID) {
                var5.addVertexWithUV((float)var2 + var24, (float)(var3 + 1) + var23, (float)(var4 + 1) + var23, var17, var19);
                var5.addVertexWithUV((float)var2 + var24, (float)(var3 + 0) - var23, (float)(var4 + 1) + var23, var15, var19);
                var5.addVertexWithUV((float)var2 + var24, (float)(var3 + 0) - var23, (float)(var4 + 0) - var23, var15, var21);
                var5.addVertexWithUV((float)var2 + var24, (float)(var3 + 1) + var23, (float)(var4 + 0) - var23, var17, var21);
            }
            if (this.blockAccess.isBlockOpaqueCube(var2 + 1, var3, var4) && this.blockAccess.getBlockId(var2 + 1, var3 + 1, var4) == Block.redstoneWire.blockID) {
                var5.addVertexWithUV((float)(var2 + 1) - var24, (float)(var3 + 0) - var23, (float)(var4 + 1) + var23, var15, var21);
                var5.addVertexWithUV((float)(var2 + 1) - var24, (float)(var3 + 1) + var23, (float)(var4 + 1) + var23, var17, var21);
                var5.addVertexWithUV((float)(var2 + 1) - var24, (float)(var3 + 1) + var23, (float)(var4 + 0) - var23, var17, var19);
                var5.addVertexWithUV((float)(var2 + 1) - var24, (float)(var3 + 0) - var23, (float)(var4 + 0) - var23, var15, var19);
            }
            if (this.blockAccess.isBlockOpaqueCube(var2, var3, var4 - 1) && this.blockAccess.getBlockId(var2, var3 + 1, var4 - 1) == Block.redstoneWire.blockID) {
                var5.addVertexWithUV((float)(var2 + 1) + var23, (float)(var3 + 0) - var23, (float)var4 + var24, var15, var21);
                var5.addVertexWithUV((float)(var2 + 1) + var23, (float)(var3 + 1) + var23, (float)var4 + var24, var17, var21);
                var5.addVertexWithUV((float)(var2 + 0) - var23, (float)(var3 + 1) + var23, (float)var4 + var24, var17, var19);
                var5.addVertexWithUV((float)(var2 + 0) - var23, (float)(var3 + 0) - var23, (float)var4 + var24, var15, var19);
            }
            if (this.blockAccess.isBlockOpaqueCube(var2, var3, var4 + 1) && this.blockAccess.getBlockId(var2, var3 + 1, var4 + 1) == Block.redstoneWire.blockID) {
                var5.addVertexWithUV((float)(var2 + 1) + var23, (float)(var3 + 1) + var23, (float)(var4 + 1) - var24, var17, var19);
                var5.addVertexWithUV((float)(var2 + 1) + var23, (float)(var3 + 0) - var23, (float)(var4 + 1) - var24, var15, var19);
                var5.addVertexWithUV((float)(var2 + 0) - var23, (float)(var3 + 0) - var23, (float)(var4 + 1) - var24, var15, var21);
                var5.addVertexWithUV((float)(var2 + 0) - var23, (float)(var3 + 1) + var23, (float)(var4 + 1) - var24, var17, var21);
            }
        }
        return true;
    }

    public boolean renderBlockMinecartTrack(Block var1, int var2, int var3, int var4) {
        Tessellator var5 = Tessellator.instance;
        int var6 = this.blockAccess.getBlockMetadata(var2, var3, var4);
        int var7 = var1.getBlockTextureFromSideAndMetadata(0, var6);
        if (this.overrideBlockTexture >= 0) {
            var7 = this.overrideBlockTexture;
        }
        float var8 = var1.getBlockBrightness(this.blockAccess, var2, var3, var4);
        var5.setColorOpaque_F(var8, var8, var8);
        int var9 = (var7 & 0xF) << 4;
        int var10 = var7 & 0xF0;
        double var11 = (float)var9 / 256.0f;
        double var13 = ((float)var9 + 15.99f) / 256.0f;
        double var15 = (float)var10 / 256.0f;
        double var17 = ((float)var10 + 15.99f) / 256.0f;
        float var19 = 0.0625f;
        float var20 = var2 + 1;
        float var21 = var2 + 1;
        float var22 = var2 + 0;
        float var23 = var2 + 0;
        float var24 = var4 + 0;
        float var25 = var4 + 1;
        float var26 = var4 + 1;
        float var27 = var4 + 0;
        float var28 = (float)var3 + var19;
        float var29 = (float)var3 + var19;
        float var30 = (float)var3 + var19;
        float var31 = (float)var3 + var19;
        if (var6 != 1 && var6 != 2 && var6 != 3 && var6 != 7) {
            if (var6 == 8) {
                var20 = var21 = (float)(var2 + 0);
                var22 = var23 = (float)(var2 + 1);
                var24 = var27 = (float)(var4 + 1);
                var25 = var26 = (float)(var4 + 0);
            } else if (var6 == 9) {
                var20 = var23 = (float)(var2 + 0);
                var21 = var22 = (float)(var2 + 1);
                var24 = var25 = (float)(var4 + 0);
                var26 = var27 = (float)(var4 + 1);
            }
        } else {
            var20 = var23 = (float)(var2 + 1);
            var21 = var22 = (float)(var2 + 0);
            var24 = var25 = (float)(var4 + 1);
            var26 = var27 = (float)(var4 + 0);
        }
        if (var6 != 2 && var6 != 4) {
            if (var6 == 3 || var6 == 5) {
                var29 += 1.0f;
                var30 += 1.0f;
            }
        } else {
            var28 += 1.0f;
            var31 += 1.0f;
        }
        var5.addVertexWithUV(var20, var28, var24, var13, var15);
        var5.addVertexWithUV(var21, var29, var25, var13, var17);
        var5.addVertexWithUV(var22, var30, var26, var11, var17);
        var5.addVertexWithUV(var23, var31, var27, var11, var15);
        var5.addVertexWithUV(var23, var31, var27, var11, var15);
        var5.addVertexWithUV(var22, var30, var26, var11, var17);
        var5.addVertexWithUV(var21, var29, var25, var13, var17);
        var5.addVertexWithUV(var20, var28, var24, var13, var15);
        return true;
    }

    public boolean renderBlockLadder(Block var1, int var2, int var3, int var4) {
        Tessellator var5 = Tessellator.instance;
        int var6 = var1.getBlockTextureFromSide(0);
        if (this.overrideBlockTexture >= 0) {
            var6 = this.overrideBlockTexture;
        }
        float var7 = var1.getBlockBrightness(this.blockAccess, var2, var3, var4);
        var5.setColorOpaque_F(var7, var7, var7);
        int var8 = (var6 & 0xF) << 4;
        int var9 = var6 & 0xF0;
        double var10 = (float)var8 / 256.0f;
        double var12 = ((float)var8 + 15.99f) / 256.0f;
        double var14 = (float)var9 / 256.0f;
        double var16 = ((float)var9 + 15.99f) / 256.0f;
        int var18 = this.blockAccess.getBlockMetadata(var2, var3, var4);
        float var19 = 0.0f;
        float var20 = 0.05f;
        if (var18 == 5) {
            var5.addVertexWithUV((float)var2 + var20, (float)(var3 + 1) + var19, (float)(var4 + 1) + var19, var10, var14);
            var5.addVertexWithUV((float)var2 + var20, (float)(var3 + 0) - var19, (float)(var4 + 1) + var19, var10, var16);
            var5.addVertexWithUV((float)var2 + var20, (float)(var3 + 0) - var19, (float)(var4 + 0) - var19, var12, var16);
            var5.addVertexWithUV((float)var2 + var20, (float)(var3 + 1) + var19, (float)(var4 + 0) - var19, var12, var14);
        }
        if (var18 == 4) {
            var5.addVertexWithUV((float)(var2 + 1) - var20, (float)(var3 + 0) - var19, (float)(var4 + 1) + var19, var12, var16);
            var5.addVertexWithUV((float)(var2 + 1) - var20, (float)(var3 + 1) + var19, (float)(var4 + 1) + var19, var12, var14);
            var5.addVertexWithUV((float)(var2 + 1) - var20, (float)(var3 + 1) + var19, (float)(var4 + 0) - var19, var10, var14);
            var5.addVertexWithUV((float)(var2 + 1) - var20, (float)(var3 + 0) - var19, (float)(var4 + 0) - var19, var10, var16);
        }
        if (var18 == 3) {
            var5.addVertexWithUV((float)(var2 + 1) + var19, (float)(var3 + 0) - var19, (float)var4 + var20, var12, var16);
            var5.addVertexWithUV((float)(var2 + 1) + var19, (float)(var3 + 1) + var19, (float)var4 + var20, var12, var14);
            var5.addVertexWithUV((float)(var2 + 0) - var19, (float)(var3 + 1) + var19, (float)var4 + var20, var10, var14);
            var5.addVertexWithUV((float)(var2 + 0) - var19, (float)(var3 + 0) - var19, (float)var4 + var20, var10, var16);
        }
        if (var18 == 2) {
            var5.addVertexWithUV((float)(var2 + 1) + var19, (float)(var3 + 1) + var19, (float)(var4 + 1) - var20, var10, var14);
            var5.addVertexWithUV((float)(var2 + 1) + var19, (float)(var3 + 0) - var19, (float)(var4 + 1) - var20, var10, var16);
            var5.addVertexWithUV((float)(var2 + 0) - var19, (float)(var3 + 0) - var19, (float)(var4 + 1) - var20, var12, var16);
            var5.addVertexWithUV((float)(var2 + 0) - var19, (float)(var3 + 1) + var19, (float)(var4 + 1) - var20, var12, var14);
        }
        return true;
    }

    public boolean renderBlockReed(Block var1, int var2, int var3, int var4) {
        Tessellator var5 = Tessellator.instance;
        float var6 = var1.getBlockBrightness(this.blockAccess, var2, var3, var4);
        var5.setColorOpaque_F(var6, var6, var6);
        this.renderCrossedSquares(var1, this.blockAccess.getBlockMetadata(var2, var3, var4), var2, var3, var4);
        return true;
    }

    public boolean renderBlockCrops(Block var1, int var2, int var3, int var4) {
        Tessellator var5 = Tessellator.instance;
        float var6 = var1.getBlockBrightness(this.blockAccess, var2, var3, var4);
        var5.setColorOpaque_F(var6, var6, var6);
        this.func_1245_b(var1, this.blockAccess.getBlockMetadata(var2, var3, var4), var2, (float)var3 - 0.0625f, var4);
        return true;
    }

    public void renderTorchAtAngle(Block var1, double var2, double var4, double var6, double var8, double var10) {
        Tessellator var12 = Tessellator.instance;
        int var13 = var1.getBlockTextureFromSide(0);
        if (this.overrideBlockTexture >= 0) {
            var13 = this.overrideBlockTexture;
        }
        int var14 = (var13 & 0xF) << 4;
        int var15 = var13 & 0xF0;
        float var16 = (float)var14 / 256.0f;
        float var17 = ((float)var14 + 15.99f) / 256.0f;
        float var18 = (float)var15 / 256.0f;
        float var19 = ((float)var15 + 15.99f) / 256.0f;
        double var20 = (double)var16 + 0.02734375;
        double var22 = (double)var18 + 0.0234375;
        double var24 = (double)var16 + 0.03515625;
        double var26 = (double)var18 + 0.03125;
        double var28 = (var2 += 0.5) - 0.5;
        double var30 = var2 + 0.5;
        double var32 = (var6 += 0.5) - 0.5;
        double var34 = var6 + 0.5;
        double var36 = 0.0625;
        double var38 = 0.625;
        var12.addVertexWithUV(var2 + var8 * (1.0 - var38) - var36, var4 + var38, var6 + var10 * (1.0 - var38) - var36, var20, var22);
        var12.addVertexWithUV(var2 + var8 * (1.0 - var38) - var36, var4 + var38, var6 + var10 * (1.0 - var38) + var36, var20, var26);
        var12.addVertexWithUV(var2 + var8 * (1.0 - var38) + var36, var4 + var38, var6 + var10 * (1.0 - var38) + var36, var24, var26);
        var12.addVertexWithUV(var2 + var8 * (1.0 - var38) + var36, var4 + var38, var6 + var10 * (1.0 - var38) - var36, var24, var22);
        var12.addVertexWithUV(var2 - var36, var4 + 1.0, var32, var16, var18);
        var12.addVertexWithUV(var2 - var36 + var8, var4 + 0.0, var32 + var10, var16, var19);
        var12.addVertexWithUV(var2 - var36 + var8, var4 + 0.0, var34 + var10, var17, var19);
        var12.addVertexWithUV(var2 - var36, var4 + 1.0, var34, var17, var18);
        var12.addVertexWithUV(var2 + var36, var4 + 1.0, var34, var16, var18);
        var12.addVertexWithUV(var2 + var8 + var36, var4 + 0.0, var34 + var10, var16, var19);
        var12.addVertexWithUV(var2 + var8 + var36, var4 + 0.0, var32 + var10, var17, var19);
        var12.addVertexWithUV(var2 + var36, var4 + 1.0, var32, var17, var18);
        var12.addVertexWithUV(var28, var4 + 1.0, var6 + var36, var16, var18);
        var12.addVertexWithUV(var28 + var8, var4 + 0.0, var6 + var36 + var10, var16, var19);
        var12.addVertexWithUV(var30 + var8, var4 + 0.0, var6 + var36 + var10, var17, var19);
        var12.addVertexWithUV(var30, var4 + 1.0, var6 + var36, var17, var18);
        var12.addVertexWithUV(var30, var4 + 1.0, var6 - var36, var16, var18);
        var12.addVertexWithUV(var30 + var8, var4 + 0.0, var6 - var36 + var10, var16, var19);
        var12.addVertexWithUV(var28 + var8, var4 + 0.0, var6 - var36 + var10, var17, var19);
        var12.addVertexWithUV(var28, var4 + 1.0, var6 - var36, var17, var18);
    }

    public void renderCrossedSquares(Block var1, int var2, double var3, double var5, double var7) {
        Tessellator var9 = Tessellator.instance;
        int var10 = var1.getBlockTextureFromSideAndMetadata(0, var2);
        if (this.overrideBlockTexture >= 0) {
            var10 = this.overrideBlockTexture;
        }
        int var11 = (var10 & 0xF) << 4;
        int var12 = var10 & 0xF0;
        double var13 = (float)var11 / 256.0f;
        double var15 = ((float)var11 + 15.99f) / 256.0f;
        double var17 = (float)var12 / 256.0f;
        double var19 = ((float)var12 + 15.99f) / 256.0f;
        double var21 = var3 + 0.5 - (double)0.45f;
        double var23 = var3 + 0.5 + (double)0.45f;
        double var25 = var7 + 0.5 - (double)0.45f;
        double var27 = var7 + 0.5 + (double)0.45f;
        var9.addVertexWithUV(var21, var5 + 1.0, var25, var13, var17);
        var9.addVertexWithUV(var21, var5 + 0.0, var25, var13, var19);
        var9.addVertexWithUV(var23, var5 + 0.0, var27, var15, var19);
        var9.addVertexWithUV(var23, var5 + 1.0, var27, var15, var17);
        var9.addVertexWithUV(var23, var5 + 1.0, var27, var13, var17);
        var9.addVertexWithUV(var23, var5 + 0.0, var27, var13, var19);
        var9.addVertexWithUV(var21, var5 + 0.0, var25, var15, var19);
        var9.addVertexWithUV(var21, var5 + 1.0, var25, var15, var17);
        var9.addVertexWithUV(var21, var5 + 1.0, var27, var13, var17);
        var9.addVertexWithUV(var21, var5 + 0.0, var27, var13, var19);
        var9.addVertexWithUV(var23, var5 + 0.0, var25, var15, var19);
        var9.addVertexWithUV(var23, var5 + 1.0, var25, var15, var17);
        var9.addVertexWithUV(var23, var5 + 1.0, var25, var13, var17);
        var9.addVertexWithUV(var23, var5 + 0.0, var25, var13, var19);
        var9.addVertexWithUV(var21, var5 + 0.0, var27, var15, var19);
        var9.addVertexWithUV(var21, var5 + 1.0, var27, var15, var17);
    }

    public void func_1245_b(Block var1, int var2, double var3, double var5, double var7) {
        Tessellator var9 = Tessellator.instance;
        int var10 = var1.getBlockTextureFromSideAndMetadata(0, var2);
        if (this.overrideBlockTexture >= 0) {
            var10 = this.overrideBlockTexture;
        }
        int var11 = (var10 & 0xF) << 4;
        int var12 = var10 & 0xF0;
        double var13 = (float)var11 / 256.0f;
        double var15 = ((float)var11 + 15.99f) / 256.0f;
        double var17 = (float)var12 / 256.0f;
        double var19 = ((float)var12 + 15.99f) / 256.0f;
        double var21 = var3 + 0.5 - 0.25;
        double var23 = var3 + 0.5 + 0.25;
        double var25 = var7 + 0.5 - 0.5;
        double var27 = var7 + 0.5 + 0.5;
        var9.addVertexWithUV(var21, var5 + 1.0, var25, var13, var17);
        var9.addVertexWithUV(var21, var5 + 0.0, var25, var13, var19);
        var9.addVertexWithUV(var21, var5 + 0.0, var27, var15, var19);
        var9.addVertexWithUV(var21, var5 + 1.0, var27, var15, var17);
        var9.addVertexWithUV(var21, var5 + 1.0, var27, var13, var17);
        var9.addVertexWithUV(var21, var5 + 0.0, var27, var13, var19);
        var9.addVertexWithUV(var21, var5 + 0.0, var25, var15, var19);
        var9.addVertexWithUV(var21, var5 + 1.0, var25, var15, var17);
        var9.addVertexWithUV(var23, var5 + 1.0, var27, var13, var17);
        var9.addVertexWithUV(var23, var5 + 0.0, var27, var13, var19);
        var9.addVertexWithUV(var23, var5 + 0.0, var25, var15, var19);
        var9.addVertexWithUV(var23, var5 + 1.0, var25, var15, var17);
        var9.addVertexWithUV(var23, var5 + 1.0, var25, var13, var17);
        var9.addVertexWithUV(var23, var5 + 0.0, var25, var13, var19);
        var9.addVertexWithUV(var23, var5 + 0.0, var27, var15, var19);
        var9.addVertexWithUV(var23, var5 + 1.0, var27, var15, var17);
        var21 = var3 + 0.5 - 0.5;
        var23 = var3 + 0.5 + 0.5;
        var25 = var7 + 0.5 - 0.25;
        var27 = var7 + 0.5 + 0.25;
        var9.addVertexWithUV(var21, var5 + 1.0, var25, var13, var17);
        var9.addVertexWithUV(var21, var5 + 0.0, var25, var13, var19);
        var9.addVertexWithUV(var23, var5 + 0.0, var25, var15, var19);
        var9.addVertexWithUV(var23, var5 + 1.0, var25, var15, var17);
        var9.addVertexWithUV(var23, var5 + 1.0, var25, var13, var17);
        var9.addVertexWithUV(var23, var5 + 0.0, var25, var13, var19);
        var9.addVertexWithUV(var21, var5 + 0.0, var25, var15, var19);
        var9.addVertexWithUV(var21, var5 + 1.0, var25, var15, var17);
        var9.addVertexWithUV(var23, var5 + 1.0, var27, var13, var17);
        var9.addVertexWithUV(var23, var5 + 0.0, var27, var13, var19);
        var9.addVertexWithUV(var21, var5 + 0.0, var27, var15, var19);
        var9.addVertexWithUV(var21, var5 + 1.0, var27, var15, var17);
        var9.addVertexWithUV(var21, var5 + 1.0, var27, var13, var17);
        var9.addVertexWithUV(var21, var5 + 0.0, var27, var13, var19);
        var9.addVertexWithUV(var23, var5 + 0.0, var27, var15, var19);
        var9.addVertexWithUV(var23, var5 + 1.0, var27, var15, var17);
    }

    public boolean renderBlockFluids(Block var1, int var2, int var3, int var4) {
        double var34;
        double var33;
        double var32;
        int var27;
        int var24;
        Tessellator var5 = Tessellator.instance;
        boolean var6 = var1.shouldSideBeRendered(this.blockAccess, var2, var3 + 1, var4, 1);
        boolean var7 = var1.shouldSideBeRendered(this.blockAccess, var2, var3 - 1, var4, 0);
        boolean[] var8 = new boolean[]{var1.shouldSideBeRendered(this.blockAccess, var2, var3, var4 - 1, 2), var1.shouldSideBeRendered(this.blockAccess, var2, var3, var4 + 1, 3), var1.shouldSideBeRendered(this.blockAccess, var2 - 1, var3, var4, 4), var1.shouldSideBeRendered(this.blockAccess, var2 + 1, var3, var4, 5)};
        if (!(var6 || var7 || var8[0] || var8[1] || var8[2] || var8[3])) {
            return false;
        }
        boolean var9 = false;
        float var10 = 0.5f;
        float var11 = 1.0f;
        float var12 = 0.8f;
        float var13 = 0.6f;
        double var14 = 0.0;
        double var16 = 1.0;
        Material var18 = var1.blockMaterial;
        int var19 = this.blockAccess.getBlockMetadata(var2, var3, var4);
        float var20 = this.func_1224_a(var2, var3, var4, var18);
        float var21 = this.func_1224_a(var2, var3, var4 + 1, var18);
        float var22 = this.func_1224_a(var2 + 1, var3, var4 + 1, var18);
        float var23 = this.func_1224_a(var2 + 1, var3, var4, var18);
        if (this.renderAllFaces || var6) {
            var9 = true;
            var24 = var1.getBlockTextureFromSideAndMetadata(1, var19);
            float var25 = (float)BlockFluids.func_293_a(this.blockAccess, var2, var3, var4, var18);
            if (var25 > -999.0f) {
                var24 = var1.getBlockTextureFromSideAndMetadata(2, var19);
            }
            int var26 = (var24 & 0xF) << 4;
            var27 = var24 & 0xF0;
            double var28 = ((double)var26 + 8.0) / 256.0;
            double var30 = ((double)var27 + 8.0) / 256.0;
            if (var25 < -999.0f) {
                var25 = 0.0f;
            } else {
                var28 = (double)(var26 + 16) / 256.0;
                var30 = (double)(var27 + 16) / 256.0;
            }
            var32 = (double)MathHelper.sin(var25) * 8.0 / 256.0;
            var33 = (double)MathHelper.cos(var25) * 8.0 / 256.0;
            var34 = var1.getBlockBrightness(this.blockAccess, var2, var3, var4);
            var5.setColorOpaque_F((float)((double)var11 * var34), (float)((double)var11 * var34), (float)((double)var11 * var34));
            var5.addVertexWithUV(var2 + 0, (float)var3 + var20, var4 + 0, var28 - var33 - var32, var30 - var33 + var32);
            var5.addVertexWithUV(var2 + 0, (float)var3 + var21, var4 + 1, var28 - var33 + var32, var30 + var33 + var32);
            var5.addVertexWithUV(var2 + 1, (float)var3 + var22, var4 + 1, var28 + var33 + var32, var30 + var33 - var32);
            var5.addVertexWithUV(var2 + 1, (float)var3 + var23, var4 + 0, var28 + var33 - var32, var30 - var33 - var32);
        }
        if (this.renderAllFaces || var7) {
            float var48 = var1.getBlockBrightness(this.blockAccess, var2, var3 - 1, var4);
            var5.setColorOpaque_F(var10 * var48, var10 * var48, var10 * var48);
            this.renderBottomFace(var1, var2, var3, var4, var1.getBlockTextureFromSide(0));
            var9 = true;
        }
        var24 = 0;
        while (var24 < 4) {
            int var49 = var2;
            var27 = var4;
            if (var24 == 0) {
                var27 = var4 - 1;
            }
            if (var24 == 1) {
                ++var27;
            }
            if (var24 == 2) {
                var49 = var2 - 1;
            }
            if (var24 == 3) {
                ++var49;
            }
            int var50 = var1.getBlockTextureFromSideAndMetadata(var24 + 2, var19);
            int var29 = (var50 & 0xF) << 4;
            int var51 = var50 & 0xF0;
            if (this.renderAllFaces || var8[var24]) {
                double var36;
                double var35;
                double var31;
                if (var24 == 0) {
                    var31 = var20;
                    var32 = var23;
                    var33 = var2;
                    var35 = var2 + 1;
                    var34 = var4;
                    var36 = var4;
                } else if (var24 == 1) {
                    var31 = var22;
                    var32 = var21;
                    var33 = var2 + 1;
                    var35 = var2;
                    var34 = var4 + 1;
                    var36 = var4 + 1;
                } else if (var24 == 2) {
                    var31 = var21;
                    var32 = var20;
                    var33 = var2;
                    var35 = var2;
                    var34 = var4 + 1;
                    var36 = var4;
                } else {
                    var31 = var23;
                    var32 = var22;
                    var33 = var2 + 1;
                    var35 = var2 + 1;
                    var34 = var4;
                    var36 = var4 + 1;
                }
                var9 = true;
                double var37 = (double)(var29 + 0) / 256.0;
                double var39 = ((double)(var29 + 16) - 0.01) / 256.0;
                double var41 = ((double)var51 + (1.0 - var31) * 16.0) / 256.0;
                double var43 = ((double)var51 + (1.0 - var32) * 16.0) / 256.0;
                double var45 = ((double)(var51 + 16) - 0.01) / 256.0;
                float var47 = var1.getBlockBrightness(this.blockAccess, var49, var3, var27);
                var47 = var24 < 2 ? (var47 *= var12) : (var47 *= var13);
                var5.setColorOpaque_F(var11 * var47, var11 * var47, var11 * var47);
                var5.addVertexWithUV(var33, (double)var3 + var31, var34, var37, var41);
                var5.addVertexWithUV(var35, (double)var3 + var32, var36, var39, var43);
                var5.addVertexWithUV(var35, var3 + 0, var36, var39, var45);
                var5.addVertexWithUV(var33, var3 + 0, var34, var37, var45);
            }
            ++var24;
        }
        var1.minY = var14;
        var1.maxY = var16;
        return var9;
    }

    private float func_1224_a(int var1, int var2, int var3, Material var4) {
        int var5 = 0;
        float var6 = 0.0f;
        int var7 = 0;
        while (var7 < 4) {
            int var8 = var1 - (var7 & 1);
            int var10 = var3 - (var7 >> 1 & 1);
            if (this.blockAccess.getBlockMaterial(var8, var2 + 1, var10) == var4) {
                return 1.0f;
            }
            Material var11 = this.blockAccess.getBlockMaterial(var8, var2, var10);
            if (var11 != var4) {
                if (!var11.isSolid()) {
                    var6 += 1.0f;
                    ++var5;
                }
            } else {
                int var12 = this.blockAccess.getBlockMetadata(var8, var2, var10);
                if (var12 >= 8 || var12 == 0) {
                    var6 += BlockFluids.getPercentAir(var12) * 10.0f;
                    var5 += 10;
                }
                var6 += BlockFluids.getPercentAir(var12);
                ++var5;
            }
            ++var7;
        }
        return 1.0f - var6 / (float)var5;
    }

    public void renderBlockFallingSand(Block var1, World var2, int var3, int var4, int var5) {
        float var6 = 0.5f;
        float var7 = 1.0f;
        float var8 = 0.8f;
        float var9 = 0.6f;
        Tessellator var10 = Tessellator.instance;
        var10.startDrawingQuads();
        float var11 = var1.getBlockBrightness(var2, var3, var4, var5);
        float var12 = var1.getBlockBrightness(var2, var3, var4 - 1, var5);
        if (var12 < var11) {
            var12 = var11;
        }
        var10.setColorOpaque_F(var6 * var12, var6 * var12, var6 * var12);
        this.renderBottomFace(var1, -0.5, -0.5, -0.5, var1.getBlockTextureFromSide(0));
        var12 = var1.getBlockBrightness(var2, var3, var4 + 1, var5);
        if (var12 < var11) {
            var12 = var11;
        }
        var10.setColorOpaque_F(var7 * var12, var7 * var12, var7 * var12);
        this.renderTopFace(var1, -0.5, -0.5, -0.5, var1.getBlockTextureFromSide(1));
        var12 = var1.getBlockBrightness(var2, var3, var4, var5 - 1);
        if (var12 < var11) {
            var12 = var11;
        }
        var10.setColorOpaque_F(var8 * var12, var8 * var12, var8 * var12);
        this.renderEastFace(var1, -0.5, -0.5, -0.5, var1.getBlockTextureFromSide(2));
        var12 = var1.getBlockBrightness(var2, var3, var4, var5 + 1);
        if (var12 < var11) {
            var12 = var11;
        }
        var10.setColorOpaque_F(var8 * var12, var8 * var12, var8 * var12);
        this.renderWestFace(var1, -0.5, -0.5, -0.5, var1.getBlockTextureFromSide(3));
        var12 = var1.getBlockBrightness(var2, var3 - 1, var4, var5);
        if (var12 < var11) {
            var12 = var11;
        }
        var10.setColorOpaque_F(var9 * var12, var9 * var12, var9 * var12);
        this.renderNorthFace(var1, -0.5, -0.5, -0.5, var1.getBlockTextureFromSide(4));
        var12 = var1.getBlockBrightness(var2, var3 + 1, var4, var5);
        if (var12 < var11) {
            var12 = var11;
        }
        var10.setColorOpaque_F(var9 * var12, var9 * var12, var9 * var12);
        this.renderSouthFace(var1, -0.5, -0.5, -0.5, var1.getBlockTextureFromSide(5));
        var10.draw();
    }

    public boolean renderStandardBlock(Block var1, int var2, int var3, int var4) {
        int var5 = var1.colorMultiplier(this.blockAccess, var2, var3, var4);
        float var6 = (float)(var5 >> 16 & 0xFF) / 255.0f;
        float var7 = (float)(var5 >> 8 & 0xFF) / 255.0f;
        float var8 = (float)(var5 & 0xFF) / 255.0f;
        return Minecraft.isAmbientOcclusionEnabled() ? this.renderStandardBlockWithAmbientOcclusion(var1, var2, var3, var4, var6, var7, var8) : this.renderStandardBlockWithColorMultiplier(var1, var2, var3, var4, var6, var7, var8);
    }

    public boolean renderStandardBlockWithAmbientOcclusion(Block var1, int var2, int var3, int var4, float var5, float var6, float var7) {
        this.enableAO = true;
        boolean var8 = false;
        float var9 = this.field_22384_f;
        float var10 = this.field_22384_f;
        float var11 = this.field_22384_f;
        float var12 = this.field_22384_f;
        boolean var13 = true;
        boolean var14 = true;
        boolean var15 = true;
        boolean var16 = true;
        boolean var17 = true;
        boolean var18 = true;
        this.field_22384_f = var1.getBlockBrightness(this.blockAccess, var2, var3, var4);
        this.aoLightValueXNeg = var1.getBlockBrightness(this.blockAccess, var2 - 1, var3, var4);
        this.aoLightValueYNeg = var1.getBlockBrightness(this.blockAccess, var2, var3 - 1, var4);
        this.aoLightValueZNeg = var1.getBlockBrightness(this.blockAccess, var2, var3, var4 - 1);
        this.aoLightValueXPos = var1.getBlockBrightness(this.blockAccess, var2 + 1, var3, var4);
        this.aoLightValueYPos = var1.getBlockBrightness(this.blockAccess, var2, var3 + 1, var4);
        this.aoLightValueZPos = var1.getBlockBrightness(this.blockAccess, var2, var3, var4 + 1);
        this.field_22338_U = Block.field_340_s[this.blockAccess.getBlockId(var2 + 1, var3 + 1, var4)];
        this.field_22359_ac = Block.field_340_s[this.blockAccess.getBlockId(var2 + 1, var3 - 1, var4)];
        this.field_22334_Y = Block.field_340_s[this.blockAccess.getBlockId(var2 + 1, var3, var4 + 1)];
        this.field_22363_aa = Block.field_340_s[this.blockAccess.getBlockId(var2 + 1, var3, var4 - 1)];
        this.field_22337_V = Block.field_340_s[this.blockAccess.getBlockId(var2 - 1, var3 + 1, var4)];
        this.field_22357_ad = Block.field_340_s[this.blockAccess.getBlockId(var2 - 1, var3 - 1, var4)];
        this.field_22335_X = Block.field_340_s[this.blockAccess.getBlockId(var2 - 1, var3, var4 - 1)];
        this.field_22333_Z = Block.field_340_s[this.blockAccess.getBlockId(var2 - 1, var3, var4 + 1)];
        this.field_22336_W = Block.field_340_s[this.blockAccess.getBlockId(var2, var3 + 1, var4 + 1)];
        this.field_22339_T = Block.field_340_s[this.blockAccess.getBlockId(var2, var3 + 1, var4 - 1)];
        this.field_22355_ae = Block.field_340_s[this.blockAccess.getBlockId(var2, var3 - 1, var4 + 1)];
        this.field_22361_ab = Block.field_340_s[this.blockAccess.getBlockId(var2, var3 - 1, var4 - 1)];
        if (var1.blockIndexInTexture == 3) {
            var18 = false;
            var17 = false;
            var16 = false;
            var15 = false;
            var13 = false;
        }
        if (this.renderAllFaces || var1.shouldSideBeRendered(this.blockAccess, var2, var3 - 1, var4, 0)) {
            if (this.field_22352_G <= 0) {
                var11 = var12 = this.aoLightValueYNeg;
                var10 = var12;
                var9 = var12;
            } else {
                this.field_22376_n = var1.getBlockBrightness(this.blockAccess, var2 - 1, --var3, var4);
                this.field_22374_p = var1.getBlockBrightness(this.blockAccess, var2, var3, var4 - 1);
                this.field_22373_q = var1.getBlockBrightness(this.blockAccess, var2, var3, var4 + 1);
                this.field_22371_s = var1.getBlockBrightness(this.blockAccess, var2 + 1, var3, var4);
                this.field_22377_m = !this.field_22361_ab && !this.field_22357_ad ? this.field_22376_n : var1.getBlockBrightness(this.blockAccess, var2 - 1, var3, var4 - 1);
                this.field_22375_o = !this.field_22355_ae && !this.field_22357_ad ? this.field_22376_n : var1.getBlockBrightness(this.blockAccess, var2 - 1, var3, var4 + 1);
                this.field_22372_r = !this.field_22361_ab && !this.field_22359_ac ? this.field_22371_s : var1.getBlockBrightness(this.blockAccess, var2 + 1, var3, var4 - 1);
                this.field_22370_t = !this.field_22355_ae && !this.field_22359_ac ? this.field_22371_s : var1.getBlockBrightness(this.blockAccess, var2 + 1, var3, var4 + 1);
                ++var3;
                var9 = (this.field_22375_o + this.field_22376_n + this.field_22373_q + this.aoLightValueYNeg) / 4.0f;
                var12 = (this.field_22373_q + this.aoLightValueYNeg + this.field_22370_t + this.field_22371_s) / 4.0f;
                var11 = (this.aoLightValueYNeg + this.field_22374_p + this.field_22371_s + this.field_22372_r) / 4.0f;
                var10 = (this.field_22376_n + this.field_22377_m + this.aoLightValueYNeg + this.field_22374_p) / 4.0f;
            }
            this.colorRedBottomRight = this.colorRedTopRight = (var13 ? var5 : 1.0f) * 0.5f;
            this.colorRedBottomLeft = this.colorRedTopRight;
            this.colorRedTopLeft = this.colorRedTopRight;
            this.colorGreenBottomRight = this.colorGreenTopRight = (var13 ? var6 : 1.0f) * 0.5f;
            this.colorGreenBottomLeft = this.colorGreenTopRight;
            this.colorGreenTopLeft = this.colorGreenTopRight;
            this.colorBlueBottomRight = this.colorBlueTopRight = (var13 ? var7 : 1.0f) * 0.5f;
            this.colorBlueBottomLeft = this.colorBlueTopRight;
            this.colorBlueTopLeft = this.colorBlueTopRight;
            this.colorRedTopLeft *= var9;
            this.colorGreenTopLeft *= var9;
            this.colorBlueTopLeft *= var9;
            this.colorRedBottomLeft *= var10;
            this.colorGreenBottomLeft *= var10;
            this.colorBlueBottomLeft *= var10;
            this.colorRedBottomRight *= var11;
            this.colorGreenBottomRight *= var11;
            this.colorBlueBottomRight *= var11;
            this.colorRedTopRight *= var12;
            this.colorGreenTopRight *= var12;
            this.colorBlueTopRight *= var12;
            this.renderBottomFace(var1, var2, var3, var4, var1.getBlockTexture(this.blockAccess, var2, var3, var4, 0));
            var8 = true;
        }
        if (this.renderAllFaces || var1.shouldSideBeRendered(this.blockAccess, var2, var3 + 1, var4, 1)) {
            if (this.field_22352_G <= 0) {
                var11 = var12 = this.aoLightValueYPos;
                var10 = var12;
                var9 = var12;
            } else {
                this.field_22368_v = var1.getBlockBrightness(this.blockAccess, var2 - 1, ++var3, var4);
                this.field_22364_z = var1.getBlockBrightness(this.blockAccess, var2 + 1, var3, var4);
                this.field_22366_x = var1.getBlockBrightness(this.blockAccess, var2, var3, var4 - 1);
                this.field_22362_A = var1.getBlockBrightness(this.blockAccess, var2, var3, var4 + 1);
                this.field_22369_u = !this.field_22339_T && !this.field_22337_V ? this.field_22368_v : var1.getBlockBrightness(this.blockAccess, var2 - 1, var3, var4 - 1);
                this.field_22365_y = !this.field_22339_T && !this.field_22338_U ? this.field_22364_z : var1.getBlockBrightness(this.blockAccess, var2 + 1, var3, var4 - 1);
                this.field_22367_w = !this.field_22336_W && !this.field_22337_V ? this.field_22368_v : var1.getBlockBrightness(this.blockAccess, var2 - 1, var3, var4 + 1);
                this.field_22360_B = !this.field_22336_W && !this.field_22338_U ? this.field_22364_z : var1.getBlockBrightness(this.blockAccess, var2 + 1, var3, var4 + 1);
                --var3;
                var12 = (this.field_22367_w + this.field_22368_v + this.field_22362_A + this.aoLightValueYPos) / 4.0f;
                var9 = (this.field_22362_A + this.aoLightValueYPos + this.field_22360_B + this.field_22364_z) / 4.0f;
                var10 = (this.aoLightValueYPos + this.field_22366_x + this.field_22364_z + this.field_22365_y) / 4.0f;
                var11 = (this.field_22368_v + this.field_22369_u + this.aoLightValueYPos + this.field_22366_x) / 4.0f;
            }
            this.colorRedTopRight = var14 ? var5 : 1.0f;
            this.colorRedBottomRight = this.colorRedTopRight;
            this.colorRedBottomLeft = this.colorRedTopRight;
            this.colorRedTopLeft = this.colorRedTopRight;
            this.colorGreenTopRight = var14 ? var6 : 1.0f;
            this.colorGreenBottomRight = this.colorGreenTopRight;
            this.colorGreenBottomLeft = this.colorGreenTopRight;
            this.colorGreenTopLeft = this.colorGreenTopRight;
            this.colorBlueTopRight = var14 ? var7 : 1.0f;
            this.colorBlueBottomRight = this.colorBlueTopRight;
            this.colorBlueBottomLeft = this.colorBlueTopRight;
            this.colorBlueTopLeft = this.colorBlueTopRight;
            this.colorRedTopLeft *= var9;
            this.colorGreenTopLeft *= var9;
            this.colorBlueTopLeft *= var9;
            this.colorRedBottomLeft *= var10;
            this.colorGreenBottomLeft *= var10;
            this.colorBlueBottomLeft *= var10;
            this.colorRedBottomRight *= var11;
            this.colorGreenBottomRight *= var11;
            this.colorBlueBottomRight *= var11;
            this.colorRedTopRight *= var12;
            this.colorGreenTopRight *= var12;
            this.colorBlueTopRight *= var12;
            this.renderTopFace(var1, var2, var3, var4, var1.getBlockTexture(this.blockAccess, var2, var3, var4, 1));
            var8 = true;
        }
        if (this.renderAllFaces || var1.shouldSideBeRendered(this.blockAccess, var2, var3, var4 - 1, 2)) {
            if (this.field_22352_G <= 0) {
                var11 = var12 = this.aoLightValueZNeg;
                var10 = var12;
                var9 = var12;
            } else {
                this.field_22358_C = var1.getBlockBrightness(this.blockAccess, var2 - 1, var3, --var4);
                this.field_22374_p = var1.getBlockBrightness(this.blockAccess, var2, var3 - 1, var4);
                this.field_22366_x = var1.getBlockBrightness(this.blockAccess, var2, var3 + 1, var4);
                this.field_22356_D = var1.getBlockBrightness(this.blockAccess, var2 + 1, var3, var4);
                this.field_22377_m = !this.field_22335_X && !this.field_22361_ab ? this.field_22358_C : var1.getBlockBrightness(this.blockAccess, var2 - 1, var3 - 1, var4);
                this.field_22369_u = !this.field_22335_X && !this.field_22339_T ? this.field_22358_C : var1.getBlockBrightness(this.blockAccess, var2 - 1, var3 + 1, var4);
                this.field_22372_r = !this.field_22363_aa && !this.field_22361_ab ? this.field_22356_D : var1.getBlockBrightness(this.blockAccess, var2 + 1, var3 - 1, var4);
                this.field_22365_y = !this.field_22363_aa && !this.field_22339_T ? this.field_22356_D : var1.getBlockBrightness(this.blockAccess, var2 + 1, var3 + 1, var4);
                ++var4;
                var9 = (this.field_22358_C + this.field_22369_u + this.aoLightValueZNeg + this.field_22366_x) / 4.0f;
                var10 = (this.aoLightValueZNeg + this.field_22366_x + this.field_22356_D + this.field_22365_y) / 4.0f;
                var11 = (this.field_22374_p + this.aoLightValueZNeg + this.field_22372_r + this.field_22356_D) / 4.0f;
                var12 = (this.field_22377_m + this.field_22358_C + this.field_22374_p + this.aoLightValueZNeg) / 4.0f;
            }
            this.colorRedBottomRight = this.colorRedTopRight = (var15 ? var5 : 1.0f) * 0.8f;
            this.colorRedBottomLeft = this.colorRedTopRight;
            this.colorRedTopLeft = this.colorRedTopRight;
            this.colorGreenBottomRight = this.colorGreenTopRight = (var15 ? var6 : 1.0f) * 0.8f;
            this.colorGreenBottomLeft = this.colorGreenTopRight;
            this.colorGreenTopLeft = this.colorGreenTopRight;
            this.colorBlueBottomRight = this.colorBlueTopRight = (var15 ? var7 : 1.0f) * 0.8f;
            this.colorBlueBottomLeft = this.colorBlueTopRight;
            this.colorBlueTopLeft = this.colorBlueTopRight;
            this.colorRedTopLeft *= var9;
            this.colorGreenTopLeft *= var9;
            this.colorBlueTopLeft *= var9;
            this.colorRedBottomLeft *= var10;
            this.colorGreenBottomLeft *= var10;
            this.colorBlueBottomLeft *= var10;
            this.colorRedBottomRight *= var11;
            this.colorGreenBottomRight *= var11;
            this.colorBlueBottomRight *= var11;
            this.colorRedTopRight *= var12;
            this.colorGreenTopRight *= var12;
            this.colorBlueTopRight *= var12;
            this.renderEastFace(var1, var2, var3, var4, var1.getBlockTexture(this.blockAccess, var2, var3, var4, 2));
            var8 = true;
        }
        if (this.renderAllFaces || var1.shouldSideBeRendered(this.blockAccess, var2, var3, var4 + 1, 3)) {
            if (this.field_22352_G <= 0) {
                var11 = var12 = this.aoLightValueZPos;
                var10 = var12;
                var9 = var12;
            } else {
                this.field_22354_E = var1.getBlockBrightness(this.blockAccess, var2 - 1, var3, ++var4);
                this.field_22353_F = var1.getBlockBrightness(this.blockAccess, var2 + 1, var3, var4);
                this.field_22373_q = var1.getBlockBrightness(this.blockAccess, var2, var3 - 1, var4);
                this.field_22362_A = var1.getBlockBrightness(this.blockAccess, var2, var3 + 1, var4);
                this.field_22375_o = !this.field_22333_Z && !this.field_22355_ae ? this.field_22354_E : var1.getBlockBrightness(this.blockAccess, var2 - 1, var3 - 1, var4);
                this.field_22367_w = !this.field_22333_Z && !this.field_22336_W ? this.field_22354_E : var1.getBlockBrightness(this.blockAccess, var2 - 1, var3 + 1, var4);
                this.field_22370_t = !this.field_22334_Y && !this.field_22355_ae ? this.field_22353_F : var1.getBlockBrightness(this.blockAccess, var2 + 1, var3 - 1, var4);
                this.field_22360_B = !this.field_22334_Y && !this.field_22336_W ? this.field_22353_F : var1.getBlockBrightness(this.blockAccess, var2 + 1, var3 + 1, var4);
                --var4;
                var9 = (this.field_22354_E + this.field_22367_w + this.aoLightValueZPos + this.field_22362_A) / 4.0f;
                var12 = (this.aoLightValueZPos + this.field_22362_A + this.field_22353_F + this.field_22360_B) / 4.0f;
                var11 = (this.field_22373_q + this.aoLightValueZPos + this.field_22370_t + this.field_22353_F) / 4.0f;
                var10 = (this.field_22375_o + this.field_22354_E + this.field_22373_q + this.aoLightValueZPos) / 4.0f;
            }
            this.colorRedBottomRight = this.colorRedTopRight = (var16 ? var5 : 1.0f) * 0.8f;
            this.colorRedBottomLeft = this.colorRedTopRight;
            this.colorRedTopLeft = this.colorRedTopRight;
            this.colorGreenBottomRight = this.colorGreenTopRight = (var16 ? var6 : 1.0f) * 0.8f;
            this.colorGreenBottomLeft = this.colorGreenTopRight;
            this.colorGreenTopLeft = this.colorGreenTopRight;
            this.colorBlueBottomRight = this.colorBlueTopRight = (var16 ? var7 : 1.0f) * 0.8f;
            this.colorBlueBottomLeft = this.colorBlueTopRight;
            this.colorBlueTopLeft = this.colorBlueTopRight;
            this.colorRedTopLeft *= var9;
            this.colorGreenTopLeft *= var9;
            this.colorBlueTopLeft *= var9;
            this.colorRedBottomLeft *= var10;
            this.colorGreenBottomLeft *= var10;
            this.colorBlueBottomLeft *= var10;
            this.colorRedBottomRight *= var11;
            this.colorGreenBottomRight *= var11;
            this.colorBlueBottomRight *= var11;
            this.colorRedTopRight *= var12;
            this.colorGreenTopRight *= var12;
            this.colorBlueTopRight *= var12;
            this.renderWestFace(var1, var2, var3, var4, var1.getBlockTexture(this.blockAccess, var2, var3, var4, 3));
            var8 = true;
        }
        if (this.renderAllFaces || var1.shouldSideBeRendered(this.blockAccess, var2 - 1, var3, var4, 4)) {
            if (this.field_22352_G <= 0) {
                var11 = var12 = this.aoLightValueXNeg;
                var10 = var12;
                var9 = var12;
            } else {
                this.field_22376_n = var1.getBlockBrightness(this.blockAccess, --var2, var3 - 1, var4);
                this.field_22358_C = var1.getBlockBrightness(this.blockAccess, var2, var3, var4 - 1);
                this.field_22354_E = var1.getBlockBrightness(this.blockAccess, var2, var3, var4 + 1);
                this.field_22368_v = var1.getBlockBrightness(this.blockAccess, var2, var3 + 1, var4);
                this.field_22377_m = !this.field_22335_X && !this.field_22357_ad ? this.field_22358_C : var1.getBlockBrightness(this.blockAccess, var2, var3 - 1, var4 - 1);
                this.field_22375_o = !this.field_22333_Z && !this.field_22357_ad ? this.field_22354_E : var1.getBlockBrightness(this.blockAccess, var2, var3 - 1, var4 + 1);
                this.field_22369_u = !this.field_22335_X && !this.field_22337_V ? this.field_22358_C : var1.getBlockBrightness(this.blockAccess, var2, var3 + 1, var4 - 1);
                this.field_22367_w = !this.field_22333_Z && !this.field_22337_V ? this.field_22354_E : var1.getBlockBrightness(this.blockAccess, var2, var3 + 1, var4 + 1);
                ++var2;
                var12 = (this.field_22376_n + this.field_22375_o + this.aoLightValueXNeg + this.field_22354_E) / 4.0f;
                var9 = (this.aoLightValueXNeg + this.field_22354_E + this.field_22368_v + this.field_22367_w) / 4.0f;
                var10 = (this.field_22358_C + this.aoLightValueXNeg + this.field_22369_u + this.field_22368_v) / 4.0f;
                var11 = (this.field_22377_m + this.field_22376_n + this.field_22358_C + this.aoLightValueXNeg) / 4.0f;
            }
            this.colorRedBottomRight = this.colorRedTopRight = (var17 ? var5 : 1.0f) * 0.6f;
            this.colorRedBottomLeft = this.colorRedTopRight;
            this.colorRedTopLeft = this.colorRedTopRight;
            this.colorGreenBottomRight = this.colorGreenTopRight = (var17 ? var6 : 1.0f) * 0.6f;
            this.colorGreenBottomLeft = this.colorGreenTopRight;
            this.colorGreenTopLeft = this.colorGreenTopRight;
            this.colorBlueBottomRight = this.colorBlueTopRight = (var17 ? var7 : 1.0f) * 0.6f;
            this.colorBlueBottomLeft = this.colorBlueTopRight;
            this.colorBlueTopLeft = this.colorBlueTopRight;
            this.colorRedTopLeft *= var9;
            this.colorGreenTopLeft *= var9;
            this.colorBlueTopLeft *= var9;
            this.colorRedBottomLeft *= var10;
            this.colorGreenBottomLeft *= var10;
            this.colorBlueBottomLeft *= var10;
            this.colorRedBottomRight *= var11;
            this.colorGreenBottomRight *= var11;
            this.colorBlueBottomRight *= var11;
            this.colorRedTopRight *= var12;
            this.colorGreenTopRight *= var12;
            this.colorBlueTopRight *= var12;
            this.renderNorthFace(var1, var2, var3, var4, var1.getBlockTexture(this.blockAccess, var2, var3, var4, 4));
            var8 = true;
        }
        if (this.renderAllFaces || var1.shouldSideBeRendered(this.blockAccess, var2 + 1, var3, var4, 5)) {
            if (this.field_22352_G <= 0) {
                var11 = var12 = this.aoLightValueXPos;
                var10 = var12;
                var9 = var12;
            } else {
                this.field_22371_s = var1.getBlockBrightness(this.blockAccess, ++var2, var3 - 1, var4);
                this.field_22356_D = var1.getBlockBrightness(this.blockAccess, var2, var3, var4 - 1);
                this.field_22353_F = var1.getBlockBrightness(this.blockAccess, var2, var3, var4 + 1);
                this.field_22364_z = var1.getBlockBrightness(this.blockAccess, var2, var3 + 1, var4);
                this.field_22372_r = !this.field_22359_ac && !this.field_22363_aa ? this.field_22356_D : var1.getBlockBrightness(this.blockAccess, var2, var3 - 1, var4 - 1);
                this.field_22370_t = !this.field_22359_ac && !this.field_22334_Y ? this.field_22353_F : var1.getBlockBrightness(this.blockAccess, var2, var3 - 1, var4 + 1);
                this.field_22365_y = !this.field_22338_U && !this.field_22363_aa ? this.field_22356_D : var1.getBlockBrightness(this.blockAccess, var2, var3 + 1, var4 - 1);
                this.field_22360_B = !this.field_22338_U && !this.field_22334_Y ? this.field_22353_F : var1.getBlockBrightness(this.blockAccess, var2, var3 + 1, var4 + 1);
                --var2;
                var9 = (this.field_22371_s + this.field_22370_t + this.aoLightValueXPos + this.field_22353_F) / 4.0f;
                var12 = (this.aoLightValueXPos + this.field_22353_F + this.field_22364_z + this.field_22360_B) / 4.0f;
                var11 = (this.field_22356_D + this.aoLightValueXPos + this.field_22365_y + this.field_22364_z) / 4.0f;
                var10 = (this.field_22372_r + this.field_22371_s + this.field_22356_D + this.aoLightValueXPos) / 4.0f;
            }
            this.colorRedBottomRight = this.colorRedTopRight = (var18 ? var5 : 1.0f) * 0.6f;
            this.colorRedBottomLeft = this.colorRedTopRight;
            this.colorRedTopLeft = this.colorRedTopRight;
            this.colorGreenBottomRight = this.colorGreenTopRight = (var18 ? var6 : 1.0f) * 0.6f;
            this.colorGreenBottomLeft = this.colorGreenTopRight;
            this.colorGreenTopLeft = this.colorGreenTopRight;
            this.colorBlueBottomRight = this.colorBlueTopRight = (var18 ? var7 : 1.0f) * 0.6f;
            this.colorBlueBottomLeft = this.colorBlueTopRight;
            this.colorBlueTopLeft = this.colorBlueTopRight;
            this.colorRedTopLeft *= var9;
            this.colorGreenTopLeft *= var9;
            this.colorBlueTopLeft *= var9;
            this.colorRedBottomLeft *= var10;
            this.colorGreenBottomLeft *= var10;
            this.colorBlueBottomLeft *= var10;
            this.colorRedBottomRight *= var11;
            this.colorGreenBottomRight *= var11;
            this.colorBlueBottomRight *= var11;
            this.colorRedTopRight *= var12;
            this.colorGreenTopRight *= var12;
            this.colorBlueTopRight *= var12;
            this.renderSouthFace(var1, var2, var3, var4, var1.getBlockTexture(this.blockAccess, var2, var3, var4, 5));
            var8 = true;
        }
        this.enableAO = false;
        return var8;
    }

    public boolean renderStandardBlockWithColorMultiplier(Block var1, int var2, int var3, int var4, float var5, float var6, float var7) {
        float var27;
        this.enableAO = false;
        Tessellator var8 = Tessellator.instance;
        boolean var9 = false;
        float var10 = 0.5f;
        float var11 = 1.0f;
        float var12 = 0.8f;
        float var13 = 0.6f;
        float var14 = var11 * var5;
        float var15 = var11 * var6;
        float var16 = var11 * var7;
        if (var1 == Block.grass) {
            var7 = 1.0f;
            var6 = 1.0f;
            var5 = 1.0f;
        }
        float var17 = var10 * var5;
        float var18 = var12 * var5;
        float var19 = var13 * var5;
        float var20 = var10 * var6;
        float var21 = var12 * var6;
        float var22 = var13 * var6;
        float var23 = var10 * var7;
        float var24 = var12 * var7;
        float var25 = var13 * var7;
        float var26 = var1.getBlockBrightness(this.blockAccess, var2, var3, var4);
        if (this.renderAllFaces || var1.shouldSideBeRendered(this.blockAccess, var2, var3 - 1, var4, 0)) {
            var27 = var1.getBlockBrightness(this.blockAccess, var2, var3 - 1, var4);
            var8.setColorOpaque_F(var17 * var27, var20 * var27, var23 * var27);
            this.renderBottomFace(var1, var2, var3, var4, var1.getBlockTexture(this.blockAccess, var2, var3, var4, 0));
            var9 = true;
        }
        if (this.renderAllFaces || var1.shouldSideBeRendered(this.blockAccess, var2, var3 + 1, var4, 1)) {
            var27 = var1.getBlockBrightness(this.blockAccess, var2, var3 + 1, var4);
            if (var1.maxY != 1.0 && !var1.blockMaterial.getIsLiquid()) {
                var27 = var26;
            }
            var8.setColorOpaque_F(var14 * var27, var15 * var27, var16 * var27);
            this.renderTopFace(var1, var2, var3, var4, var1.getBlockTexture(this.blockAccess, var2, var3, var4, 1));
            var9 = true;
        }
        if (this.renderAllFaces || var1.shouldSideBeRendered(this.blockAccess, var2, var3, var4 - 1, 2)) {
            var27 = var1.getBlockBrightness(this.blockAccess, var2, var3, var4 - 1);
            if (var1.minZ > 0.0) {
                var27 = var26;
            }
            var8.setColorOpaque_F(var18 * var27, var21 * var27, var24 * var27);
            this.renderEastFace(var1, var2, var3, var4, var1.getBlockTexture(this.blockAccess, var2, var3, var4, 2));
            var9 = true;
        }
        if (this.renderAllFaces || var1.shouldSideBeRendered(this.blockAccess, var2, var3, var4 + 1, 3)) {
            var27 = var1.getBlockBrightness(this.blockAccess, var2, var3, var4 + 1);
            if (var1.maxZ < 1.0) {
                var27 = var26;
            }
            var8.setColorOpaque_F(var18 * var27, var21 * var27, var24 * var27);
            this.renderWestFace(var1, var2, var3, var4, var1.getBlockTexture(this.blockAccess, var2, var3, var4, 3));
            var9 = true;
        }
        if (this.renderAllFaces || var1.shouldSideBeRendered(this.blockAccess, var2 - 1, var3, var4, 4)) {
            var27 = var1.getBlockBrightness(this.blockAccess, var2 - 1, var3, var4);
            if (var1.minX > 0.0) {
                var27 = var26;
            }
            var8.setColorOpaque_F(var19 * var27, var22 * var27, var25 * var27);
            this.renderNorthFace(var1, var2, var3, var4, var1.getBlockTexture(this.blockAccess, var2, var3, var4, 4));
            var9 = true;
        }
        if (this.renderAllFaces || var1.shouldSideBeRendered(this.blockAccess, var2 + 1, var3, var4, 5)) {
            var27 = var1.getBlockBrightness(this.blockAccess, var2 + 1, var3, var4);
            if (var1.maxX < 1.0) {
                var27 = var26;
            }
            var8.setColorOpaque_F(var19 * var27, var22 * var27, var25 * var27);
            this.renderSouthFace(var1, var2, var3, var4, var1.getBlockTexture(this.blockAccess, var2, var3, var4, 5));
            var9 = true;
        }
        return var9;
    }

    public boolean renderBlockCactus(Block var1, int var2, int var3, int var4) {
        int var5 = var1.colorMultiplier(this.blockAccess, var2, var3, var4);
        float var6 = (float)(var5 >> 16 & 0xFF) / 255.0f;
        float var7 = (float)(var5 >> 8 & 0xFF) / 255.0f;
        float var8 = (float)(var5 & 0xFF) / 255.0f;
        return this.func_1230_b(var1, var2, var3, var4, var6, var7, var8);
    }

    public boolean func_1230_b(Block var1, int var2, int var3, int var4, float var5, float var6, float var7) {
        float var28;
        Tessellator var8 = Tessellator.instance;
        boolean var9 = false;
        float var10 = 0.5f;
        float var11 = 1.0f;
        float var12 = 0.8f;
        float var13 = 0.6f;
        float var14 = var10 * var5;
        float var15 = var11 * var5;
        float var16 = var12 * var5;
        float var17 = var13 * var5;
        float var18 = var10 * var6;
        float var19 = var11 * var6;
        float var20 = var12 * var6;
        float var21 = var13 * var6;
        float var22 = var10 * var7;
        float var23 = var11 * var7;
        float var24 = var12 * var7;
        float var25 = var13 * var7;
        float var26 = 0.0625f;
        float var27 = var1.getBlockBrightness(this.blockAccess, var2, var3, var4);
        if (this.renderAllFaces || var1.shouldSideBeRendered(this.blockAccess, var2, var3 - 1, var4, 0)) {
            var28 = var1.getBlockBrightness(this.blockAccess, var2, var3 - 1, var4);
            var8.setColorOpaque_F(var14 * var28, var18 * var28, var22 * var28);
            this.renderBottomFace(var1, var2, var3, var4, var1.getBlockTexture(this.blockAccess, var2, var3, var4, 0));
            var9 = true;
        }
        if (this.renderAllFaces || var1.shouldSideBeRendered(this.blockAccess, var2, var3 + 1, var4, 1)) {
            var28 = var1.getBlockBrightness(this.blockAccess, var2, var3 + 1, var4);
            if (var1.maxY != 1.0 && !var1.blockMaterial.getIsLiquid()) {
                var28 = var27;
            }
            var8.setColorOpaque_F(var15 * var28, var19 * var28, var23 * var28);
            this.renderTopFace(var1, var2, var3, var4, var1.getBlockTexture(this.blockAccess, var2, var3, var4, 1));
            var9 = true;
        }
        if (this.renderAllFaces || var1.shouldSideBeRendered(this.blockAccess, var2, var3, var4 - 1, 2)) {
            var28 = var1.getBlockBrightness(this.blockAccess, var2, var3, var4 - 1);
            if (var1.minZ > 0.0) {
                var28 = var27;
            }
            var8.setColorOpaque_F(var16 * var28, var20 * var28, var24 * var28);
            var8.setTranslationF(0.0f, 0.0f, var26);
            this.renderEastFace(var1, var2, var3, var4, var1.getBlockTexture(this.blockAccess, var2, var3, var4, 2));
            var8.setTranslationF(0.0f, 0.0f, -var26);
            var9 = true;
        }
        if (this.renderAllFaces || var1.shouldSideBeRendered(this.blockAccess, var2, var3, var4 + 1, 3)) {
            var28 = var1.getBlockBrightness(this.blockAccess, var2, var3, var4 + 1);
            if (var1.maxZ < 1.0) {
                var28 = var27;
            }
            var8.setColorOpaque_F(var16 * var28, var20 * var28, var24 * var28);
            var8.setTranslationF(0.0f, 0.0f, -var26);
            this.renderWestFace(var1, var2, var3, var4, var1.getBlockTexture(this.blockAccess, var2, var3, var4, 3));
            var8.setTranslationF(0.0f, 0.0f, var26);
            var9 = true;
        }
        if (this.renderAllFaces || var1.shouldSideBeRendered(this.blockAccess, var2 - 1, var3, var4, 4)) {
            var28 = var1.getBlockBrightness(this.blockAccess, var2 - 1, var3, var4);
            if (var1.minX > 0.0) {
                var28 = var27;
            }
            var8.setColorOpaque_F(var17 * var28, var21 * var28, var25 * var28);
            var8.setTranslationF(var26, 0.0f, 0.0f);
            this.renderNorthFace(var1, var2, var3, var4, var1.getBlockTexture(this.blockAccess, var2, var3, var4, 4));
            var8.setTranslationF(-var26, 0.0f, 0.0f);
            var9 = true;
        }
        if (this.renderAllFaces || var1.shouldSideBeRendered(this.blockAccess, var2 + 1, var3, var4, 5)) {
            var28 = var1.getBlockBrightness(this.blockAccess, var2 + 1, var3, var4);
            if (var1.maxX < 1.0) {
                var28 = var27;
            }
            var8.setColorOpaque_F(var17 * var28, var21 * var28, var25 * var28);
            var8.setTranslationF(-var26, 0.0f, 0.0f);
            this.renderSouthFace(var1, var2, var3, var4, var1.getBlockTexture(this.blockAccess, var2, var3, var4, 5));
            var8.setTranslationF(var26, 0.0f, 0.0f);
            var9 = true;
        }
        return var9;
    }

    public boolean renderBlockFence(Block var1, int var2, int var3, int var4) {
        float var19;
        boolean var13;
        boolean var5 = false;
        float var6 = 0.375f;
        float var7 = 0.625f;
        var1.setBlockBounds(var6, 0.0f, var6, var7, 1.0f, var7);
        this.renderStandardBlock(var1, var2, var3, var4);
        boolean var8 = false;
        boolean var9 = false;
        if (this.blockAccess.getBlockId(var2 - 1, var3, var4) == var1.blockID || this.blockAccess.getBlockId(var2 + 1, var3, var4) == var1.blockID) {
            var8 = true;
        }
        if (this.blockAccess.getBlockId(var2, var3, var4 - 1) == var1.blockID || this.blockAccess.getBlockId(var2, var3, var4 + 1) == var1.blockID) {
            var9 = true;
        }
        boolean var10 = this.blockAccess.getBlockId(var2 - 1, var3, var4) == var1.blockID;
        boolean var11 = this.blockAccess.getBlockId(var2 + 1, var3, var4) == var1.blockID;
        boolean var12 = this.blockAccess.getBlockId(var2, var3, var4 - 1) == var1.blockID;
        boolean bl = var13 = this.blockAccess.getBlockId(var2, var3, var4 + 1) == var1.blockID;
        if (!var8 && !var9) {
            var8 = true;
        }
        var6 = 0.4375f;
        var7 = 0.5625f;
        float var14 = 0.75f;
        float var15 = 0.9375f;
        float var16 = var10 ? 0.0f : var6;
        float var17 = var11 ? 1.0f : var7;
        float var18 = var12 ? 0.0f : var6;
        float f = var19 = var13 ? 1.0f : var7;
        if (var8) {
            var1.setBlockBounds(var16, var14, var6, var17, var15, var7);
            this.renderStandardBlock(var1, var2, var3, var4);
        }
        if (var9) {
            var1.setBlockBounds(var6, var14, var18, var7, var15, var19);
            this.renderStandardBlock(var1, var2, var3, var4);
        }
        var14 = 0.375f;
        var15 = 0.5625f;
        if (var8) {
            var1.setBlockBounds(var16, var14, var6, var17, var15, var7);
            this.renderStandardBlock(var1, var2, var3, var4);
        }
        if (var9) {
            var1.setBlockBounds(var6, var14, var18, var7, var15, var19);
            this.renderStandardBlock(var1, var2, var3, var4);
        }
        var1.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        return var5;
    }

    public boolean renderBlockStairs(Block var1, int var2, int var3, int var4) {
        boolean var5 = false;
        int var6 = this.blockAccess.getBlockMetadata(var2, var3, var4);
        if (var6 == 0) {
            var1.setBlockBounds(0.0f, 0.0f, 0.0f, 0.5f, 0.5f, 1.0f);
            this.renderStandardBlock(var1, var2, var3, var4);
            var1.setBlockBounds(0.5f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
            this.renderStandardBlock(var1, var2, var3, var4);
        } else if (var6 == 1) {
            var1.setBlockBounds(0.0f, 0.0f, 0.0f, 0.5f, 1.0f, 1.0f);
            this.renderStandardBlock(var1, var2, var3, var4);
            var1.setBlockBounds(0.5f, 0.0f, 0.0f, 1.0f, 0.5f, 1.0f);
            this.renderStandardBlock(var1, var2, var3, var4);
        } else if (var6 == 2) {
            var1.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.5f, 0.5f);
            this.renderStandardBlock(var1, var2, var3, var4);
            var1.setBlockBounds(0.0f, 0.0f, 0.5f, 1.0f, 1.0f, 1.0f);
            this.renderStandardBlock(var1, var2, var3, var4);
        } else if (var6 == 3) {
            var1.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.5f);
            this.renderStandardBlock(var1, var2, var3, var4);
            var1.setBlockBounds(0.0f, 0.0f, 0.5f, 1.0f, 0.5f, 1.0f);
            this.renderStandardBlock(var1, var2, var3, var4);
        }
        var1.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        return var5;
    }

    public boolean renderBlockDoor(Block var1, int var2, int var3, int var4) {
        Tessellator var5 = Tessellator.instance;
        BlockDoor var6 = (BlockDoor)var1;
        boolean var7 = false;
        float var8 = 0.5f;
        float var9 = 1.0f;
        float var10 = 0.8f;
        float var11 = 0.6f;
        float var12 = var1.getBlockBrightness(this.blockAccess, var2, var3, var4);
        float var13 = var1.getBlockBrightness(this.blockAccess, var2, var3 - 1, var4);
        if (var6.minY > 0.0) {
            var13 = var12;
        }
        if (Block.lightValue[var1.blockID] > 0) {
            var13 = 1.0f;
        }
        var5.setColorOpaque_F(var8 * var13, var8 * var13, var8 * var13);
        this.renderBottomFace(var1, var2, var3, var4, var1.getBlockTexture(this.blockAccess, var2, var3, var4, 0));
        var7 = true;
        var13 = var1.getBlockBrightness(this.blockAccess, var2, var3 + 1, var4);
        if (var6.maxY < 1.0) {
            var13 = var12;
        }
        if (Block.lightValue[var1.blockID] > 0) {
            var13 = 1.0f;
        }
        var5.setColorOpaque_F(var9 * var13, var9 * var13, var9 * var13);
        this.renderTopFace(var1, var2, var3, var4, var1.getBlockTexture(this.blockAccess, var2, var3, var4, 1));
        var7 = true;
        var13 = var1.getBlockBrightness(this.blockAccess, var2, var3, var4 - 1);
        if (var6.minZ > 0.0) {
            var13 = var12;
        }
        if (Block.lightValue[var1.blockID] > 0) {
            var13 = 1.0f;
        }
        var5.setColorOpaque_F(var10 * var13, var10 * var13, var10 * var13);
        int var14 = var1.getBlockTexture(this.blockAccess, var2, var3, var4, 2);
        if (var14 < 0) {
            this.flipTexture = true;
            var14 = -var14;
        }
        this.renderEastFace(var1, var2, var3, var4, var14);
        var7 = true;
        this.flipTexture = false;
        var13 = var1.getBlockBrightness(this.blockAccess, var2, var3, var4 + 1);
        if (var6.maxZ < 1.0) {
            var13 = var12;
        }
        if (Block.lightValue[var1.blockID] > 0) {
            var13 = 1.0f;
        }
        var5.setColorOpaque_F(var10 * var13, var10 * var13, var10 * var13);
        var14 = var1.getBlockTexture(this.blockAccess, var2, var3, var4, 3);
        if (var14 < 0) {
            this.flipTexture = true;
            var14 = -var14;
        }
        this.renderWestFace(var1, var2, var3, var4, var14);
        var7 = true;
        this.flipTexture = false;
        var13 = var1.getBlockBrightness(this.blockAccess, var2 - 1, var3, var4);
        if (var6.minX > 0.0) {
            var13 = var12;
        }
        if (Block.lightValue[var1.blockID] > 0) {
            var13 = 1.0f;
        }
        var5.setColorOpaque_F(var11 * var13, var11 * var13, var11 * var13);
        var14 = var1.getBlockTexture(this.blockAccess, var2, var3, var4, 4);
        if (var14 < 0) {
            this.flipTexture = true;
            var14 = -var14;
        }
        this.renderNorthFace(var1, var2, var3, var4, var14);
        var7 = true;
        this.flipTexture = false;
        var13 = var1.getBlockBrightness(this.blockAccess, var2 + 1, var3, var4);
        if (var6.maxX < 1.0) {
            var13 = var12;
        }
        if (Block.lightValue[var1.blockID] > 0) {
            var13 = 1.0f;
        }
        var5.setColorOpaque_F(var11 * var13, var11 * var13, var11 * var13);
        var14 = var1.getBlockTexture(this.blockAccess, var2, var3, var4, 5);
        if (var14 < 0) {
            this.flipTexture = true;
            var14 = -var14;
        }
        this.renderSouthFace(var1, var2, var3, var4, var14);
        var7 = true;
        this.flipTexture = false;
        return var7;
    }

    public void renderBottomFace(Block var1, double var2, double var4, double var6, int var8) {
        Tessellator var9 = Tessellator.instance;
        if (this.overrideBlockTexture >= 0) {
            var8 = this.overrideBlockTexture;
        }
        int var10 = (var8 & 0xF) << 4;
        int var11 = var8 & 0xF0;
        double var12 = ((double)var10 + var1.minX * 16.0) / 256.0;
        double var14 = ((double)var10 + var1.maxX * 16.0 - 0.01) / 256.0;
        double var16 = ((double)var11 + var1.minZ * 16.0) / 256.0;
        double var18 = ((double)var11 + var1.maxZ * 16.0 - 0.01) / 256.0;
        if (var1.minX < 0.0 || var1.maxX > 1.0) {
            var12 = ((float)var10 + 0.0f) / 256.0f;
            var14 = ((float)var10 + 15.99f) / 256.0f;
        }
        if (var1.minZ < 0.0 || var1.maxZ > 1.0) {
            var16 = ((float)var11 + 0.0f) / 256.0f;
            var18 = ((float)var11 + 15.99f) / 256.0f;
        }
        double var20 = var2 + var1.minX;
        double var22 = var2 + var1.maxX;
        double var24 = var4 + var1.minY;
        double var26 = var6 + var1.minZ;
        double var28 = var6 + var1.maxZ;
        if (this.enableAO) {
            var9.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
            var9.addVertexWithUV(var20, var24, var28, var12, var18);
            var9.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft);
            var9.addVertexWithUV(var20, var24, var26, var12, var16);
            var9.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight);
            var9.addVertexWithUV(var22, var24, var26, var14, var16);
            var9.setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
            var9.addVertexWithUV(var22, var24, var28, var14, var18);
        } else {
            var9.addVertexWithUV(var20, var24, var28, var12, var18);
            var9.addVertexWithUV(var20, var24, var26, var12, var16);
            var9.addVertexWithUV(var22, var24, var26, var14, var16);
            var9.addVertexWithUV(var22, var24, var28, var14, var18);
        }
    }

    public void renderTopFace(Block var1, double var2, double var4, double var6, int var8) {
        Tessellator var9 = Tessellator.instance;
        if (this.overrideBlockTexture >= 0) {
            var8 = this.overrideBlockTexture;
        }
        int var10 = (var8 & 0xF) << 4;
        int var11 = var8 & 0xF0;
        double var12 = ((double)var10 + var1.minX * 16.0) / 256.0;
        double var14 = ((double)var10 + var1.maxX * 16.0 - 0.01) / 256.0;
        double var16 = ((double)var11 + var1.minZ * 16.0) / 256.0;
        double var18 = ((double)var11 + var1.maxZ * 16.0 - 0.01) / 256.0;
        if (var1.minX < 0.0 || var1.maxX > 1.0) {
            var12 = ((float)var10 + 0.0f) / 256.0f;
            var14 = ((float)var10 + 15.99f) / 256.0f;
        }
        if (var1.minZ < 0.0 || var1.maxZ > 1.0) {
            var16 = ((float)var11 + 0.0f) / 256.0f;
            var18 = ((float)var11 + 15.99f) / 256.0f;
        }
        double var20 = var2 + var1.minX;
        double var22 = var2 + var1.maxX;
        double var24 = var4 + var1.maxY;
        double var26 = var6 + var1.minZ;
        double var28 = var6 + var1.maxZ;
        if (this.enableAO) {
            var9.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
            var9.addVertexWithUV(var22, var24, var28, var14, var18);
            var9.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft);
            var9.addVertexWithUV(var22, var24, var26, var14, var16);
            var9.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight);
            var9.addVertexWithUV(var20, var24, var26, var12, var16);
            var9.setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
            var9.addVertexWithUV(var20, var24, var28, var12, var18);
        } else {
            var9.addVertexWithUV(var22, var24, var28, var14, var18);
            var9.addVertexWithUV(var22, var24, var26, var14, var16);
            var9.addVertexWithUV(var20, var24, var26, var12, var16);
            var9.addVertexWithUV(var20, var24, var28, var12, var18);
        }
    }

    public void renderEastFace(Block var1, double var2, double var4, double var6, int var8) {
        double var20;
        Tessellator var9 = Tessellator.instance;
        if (this.overrideBlockTexture >= 0) {
            var8 = this.overrideBlockTexture;
        }
        int var10 = (var8 & 0xF) << 4;
        int var11 = var8 & 0xF0;
        double var12 = ((double)var10 + var1.minX * 16.0) / 256.0;
        double var14 = ((double)var10 + var1.maxX * 16.0 - 0.01) / 256.0;
        double var16 = ((double)var11 + var1.minY * 16.0) / 256.0;
        double var18 = ((double)var11 + var1.maxY * 16.0 - 0.01) / 256.0;
        if (this.flipTexture) {
            var20 = var12;
            var12 = var14;
            var14 = var20;
        }
        if (var1.minX < 0.0 || var1.maxX > 1.0) {
            var12 = ((float)var10 + 0.0f) / 256.0f;
            var14 = ((float)var10 + 15.99f) / 256.0f;
        }
        if (var1.minY < 0.0 || var1.maxY > 1.0) {
            var16 = ((float)var11 + 0.0f) / 256.0f;
            var18 = ((float)var11 + 15.99f) / 256.0f;
        }
        var20 = var2 + var1.minX;
        double var22 = var2 + var1.maxX;
        double var24 = var4 + var1.minY;
        double var26 = var4 + var1.maxY;
        double var28 = var6 + var1.minZ;
        if (this.enableAO) {
            var9.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
            var9.addVertexWithUV(var20, var26, var28, var14, var16);
            var9.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft);
            var9.addVertexWithUV(var22, var26, var28, var12, var16);
            var9.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight);
            var9.addVertexWithUV(var22, var24, var28, var12, var18);
            var9.setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
            var9.addVertexWithUV(var20, var24, var28, var14, var18);
        } else {
            var9.addVertexWithUV(var20, var26, var28, var14, var16);
            var9.addVertexWithUV(var22, var26, var28, var12, var16);
            var9.addVertexWithUV(var22, var24, var28, var12, var18);
            var9.addVertexWithUV(var20, var24, var28, var14, var18);
        }
    }

    public void renderWestFace(Block var1, double var2, double var4, double var6, int var8) {
        double var20;
        Tessellator var9 = Tessellator.instance;
        if (this.overrideBlockTexture >= 0) {
            var8 = this.overrideBlockTexture;
        }
        int var10 = (var8 & 0xF) << 4;
        int var11 = var8 & 0xF0;
        double var12 = ((double)var10 + var1.minX * 16.0) / 256.0;
        double var14 = ((double)var10 + var1.maxX * 16.0 - 0.01) / 256.0;
        double var16 = ((double)var11 + var1.minY * 16.0) / 256.0;
        double var18 = ((double)var11 + var1.maxY * 16.0 - 0.01) / 256.0;
        if (this.flipTexture) {
            var20 = var12;
            var12 = var14;
            var14 = var20;
        }
        if (var1.minX < 0.0 || var1.maxX > 1.0) {
            var12 = ((float)var10 + 0.0f) / 256.0f;
            var14 = ((float)var10 + 15.99f) / 256.0f;
        }
        if (var1.minY < 0.0 || var1.maxY > 1.0) {
            var16 = ((float)var11 + 0.0f) / 256.0f;
            var18 = ((float)var11 + 15.99f) / 256.0f;
        }
        var20 = var2 + var1.minX;
        double var22 = var2 + var1.maxX;
        double var24 = var4 + var1.minY;
        double var26 = var4 + var1.maxY;
        double var28 = var6 + var1.maxZ;
        if (this.enableAO) {
            var9.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
            var9.addVertexWithUV(var20, var26, var28, var12, var16);
            var9.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft);
            var9.addVertexWithUV(var20, var24, var28, var12, var18);
            var9.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight);
            var9.addVertexWithUV(var22, var24, var28, var14, var18);
            var9.setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
            var9.addVertexWithUV(var22, var26, var28, var14, var16);
        } else {
            var9.addVertexWithUV(var20, var26, var28, var12, var16);
            var9.addVertexWithUV(var20, var24, var28, var12, var18);
            var9.addVertexWithUV(var22, var24, var28, var14, var18);
            var9.addVertexWithUV(var22, var26, var28, var14, var16);
        }
    }

    public void renderNorthFace(Block var1, double var2, double var4, double var6, int var8) {
        double var20;
        Tessellator var9 = Tessellator.instance;
        if (this.overrideBlockTexture >= 0) {
            var8 = this.overrideBlockTexture;
        }
        int var10 = (var8 & 0xF) << 4;
        int var11 = var8 & 0xF0;
        double var12 = ((double)var10 + var1.minZ * 16.0) / 256.0;
        double var14 = ((double)var10 + var1.maxZ * 16.0 - 0.01) / 256.0;
        double var16 = ((double)var11 + var1.minY * 16.0) / 256.0;
        double var18 = ((double)var11 + var1.maxY * 16.0 - 0.01) / 256.0;
        if (this.flipTexture) {
            var20 = var12;
            var12 = var14;
            var14 = var20;
        }
        if (var1.minZ < 0.0 || var1.maxZ > 1.0) {
            var12 = ((float)var10 + 0.0f) / 256.0f;
            var14 = ((float)var10 + 15.99f) / 256.0f;
        }
        if (var1.minY < 0.0 || var1.maxY > 1.0) {
            var16 = ((float)var11 + 0.0f) / 256.0f;
            var18 = ((float)var11 + 15.99f) / 256.0f;
        }
        var20 = var2 + var1.minX;
        double var22 = var4 + var1.minY;
        double var24 = var4 + var1.maxY;
        double var26 = var6 + var1.minZ;
        double var28 = var6 + var1.maxZ;
        if (this.enableAO) {
            var9.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
            var9.addVertexWithUV(var20, var24, var28, var14, var16);
            var9.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft);
            var9.addVertexWithUV(var20, var24, var26, var12, var16);
            var9.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight);
            var9.addVertexWithUV(var20, var22, var26, var12, var18);
            var9.setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
            var9.addVertexWithUV(var20, var22, var28, var14, var18);
        } else {
            var9.addVertexWithUV(var20, var24, var28, var14, var16);
            var9.addVertexWithUV(var20, var24, var26, var12, var16);
            var9.addVertexWithUV(var20, var22, var26, var12, var18);
            var9.addVertexWithUV(var20, var22, var28, var14, var18);
        }
    }

    public void renderSouthFace(Block var1, double var2, double var4, double var6, int var8) {
        double var20;
        Tessellator var9 = Tessellator.instance;
        if (this.overrideBlockTexture >= 0) {
            var8 = this.overrideBlockTexture;
        }
        int var10 = (var8 & 0xF) << 4;
        int var11 = var8 & 0xF0;
        double var12 = ((double)var10 + var1.minZ * 16.0) / 256.0;
        double var14 = ((double)var10 + var1.maxZ * 16.0 - 0.01) / 256.0;
        double var16 = ((double)var11 + var1.minY * 16.0) / 256.0;
        double var18 = ((double)var11 + var1.maxY * 16.0 - 0.01) / 256.0;
        if (this.flipTexture) {
            var20 = var12;
            var12 = var14;
            var14 = var20;
        }
        if (var1.minZ < 0.0 || var1.maxZ > 1.0) {
            var12 = ((float)var10 + 0.0f) / 256.0f;
            var14 = ((float)var10 + 15.99f) / 256.0f;
        }
        if (var1.minY < 0.0 || var1.maxY > 1.0) {
            var16 = ((float)var11 + 0.0f) / 256.0f;
            var18 = ((float)var11 + 15.99f) / 256.0f;
        }
        var20 = var2 + var1.maxX;
        double var22 = var4 + var1.minY;
        double var24 = var4 + var1.maxY;
        double var26 = var6 + var1.minZ;
        double var28 = var6 + var1.maxZ;
        if (this.enableAO) {
            var9.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
            var9.addVertexWithUV(var20, var22, var28, var12, var18);
            var9.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft);
            var9.addVertexWithUV(var20, var22, var26, var14, var18);
            var9.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight);
            var9.addVertexWithUV(var20, var24, var26, var14, var16);
            var9.setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
            var9.addVertexWithUV(var20, var24, var28, var12, var16);
        } else {
            var9.addVertexWithUV(var20, var22, var28, var12, var18);
            var9.addVertexWithUV(var20, var22, var26, var14, var18);
            var9.addVertexWithUV(var20, var24, var26, var14, var16);
            var9.addVertexWithUV(var20, var24, var28, var12, var16);
        }
    }

    public void renderBlockOnInventory(Block var1, int var2) {
        Tessellator var3 = Tessellator.instance;
        int var4 = var1.getRenderType();
        if (var4 == 0) {
            var1.setBlockBoundsForItemRender();
            GL11.glTranslatef((float)-0.5f, (float)-0.5f, (float)-0.5f);
            var3.startDrawingQuads();
            var3.setNormal(0.0f, -1.0f, 0.0f);
            this.renderBottomFace(var1, 0.0, 0.0, 0.0, var1.getBlockTextureFromSideAndMetadata(0, var2));
            var3.draw();
            var3.startDrawingQuads();
            var3.setNormal(0.0f, 1.0f, 0.0f);
            this.renderTopFace(var1, 0.0, 0.0, 0.0, var1.getBlockTextureFromSideAndMetadata(1, var2));
            var3.draw();
            var3.startDrawingQuads();
            var3.setNormal(0.0f, 0.0f, -1.0f);
            this.renderEastFace(var1, 0.0, 0.0, 0.0, var1.getBlockTextureFromSideAndMetadata(2, var2));
            var3.draw();
            var3.startDrawingQuads();
            var3.setNormal(0.0f, 0.0f, 1.0f);
            this.renderWestFace(var1, 0.0, 0.0, 0.0, var1.getBlockTextureFromSideAndMetadata(3, var2));
            var3.draw();
            var3.startDrawingQuads();
            var3.setNormal(-1.0f, 0.0f, 0.0f);
            this.renderNorthFace(var1, 0.0, 0.0, 0.0, var1.getBlockTextureFromSideAndMetadata(4, var2));
            var3.draw();
            var3.startDrawingQuads();
            var3.setNormal(1.0f, 0.0f, 0.0f);
            this.renderSouthFace(var1, 0.0, 0.0, 0.0, var1.getBlockTextureFromSideAndMetadata(5, var2));
            var3.draw();
            GL11.glTranslatef((float)0.5f, (float)0.5f, (float)0.5f);
        } else if (var4 == 1) {
            var3.startDrawingQuads();
            var3.setNormal(0.0f, -1.0f, 0.0f);
            this.renderCrossedSquares(var1, var2, -0.5, -0.5, -0.5);
            var3.draw();
        } else if (var4 == 13) {
            var1.setBlockBoundsForItemRender();
            GL11.glTranslatef((float)-0.5f, (float)-0.5f, (float)-0.5f);
            float var5 = 0.0625f;
            var3.startDrawingQuads();
            var3.setNormal(0.0f, -1.0f, 0.0f);
            this.renderBottomFace(var1, 0.0, 0.0, 0.0, var1.getBlockTextureFromSide(0));
            var3.draw();
            var3.startDrawingQuads();
            var3.setNormal(0.0f, 1.0f, 0.0f);
            this.renderTopFace(var1, 0.0, 0.0, 0.0, var1.getBlockTextureFromSide(1));
            var3.draw();
            var3.startDrawingQuads();
            var3.setNormal(0.0f, 0.0f, -1.0f);
            var3.setTranslationF(0.0f, 0.0f, var5);
            this.renderEastFace(var1, 0.0, 0.0, 0.0, var1.getBlockTextureFromSide(2));
            var3.setTranslationF(0.0f, 0.0f, -var5);
            var3.draw();
            var3.startDrawingQuads();
            var3.setNormal(0.0f, 0.0f, 1.0f);
            var3.setTranslationF(0.0f, 0.0f, -var5);
            this.renderWestFace(var1, 0.0, 0.0, 0.0, var1.getBlockTextureFromSide(3));
            var3.setTranslationF(0.0f, 0.0f, var5);
            var3.draw();
            var3.startDrawingQuads();
            var3.setNormal(-1.0f, 0.0f, 0.0f);
            var3.setTranslationF(var5, 0.0f, 0.0f);
            this.renderNorthFace(var1, 0.0, 0.0, 0.0, var1.getBlockTextureFromSide(4));
            var3.setTranslationF(-var5, 0.0f, 0.0f);
            var3.draw();
            var3.startDrawingQuads();
            var3.setNormal(1.0f, 0.0f, 0.0f);
            var3.setTranslationF(-var5, 0.0f, 0.0f);
            this.renderSouthFace(var1, 0.0, 0.0, 0.0, var1.getBlockTextureFromSide(5));
            var3.setTranslationF(var5, 0.0f, 0.0f);
            var3.draw();
            GL11.glTranslatef((float)0.5f, (float)0.5f, (float)0.5f);
        } else if (var4 == 6) {
            var3.startDrawingQuads();
            var3.setNormal(0.0f, -1.0f, 0.0f);
            this.func_1245_b(var1, var2, -0.5, -0.5, -0.5);
            var3.draw();
        } else if (var4 == 2) {
            var3.startDrawingQuads();
            var3.setNormal(0.0f, -1.0f, 0.0f);
            this.renderTorchAtAngle(var1, -0.5, -0.5, -0.5, 0.0, 0.0);
            var3.draw();
        } else if (var4 == 10) {
            int var7 = 0;
            while (var7 < 2) {
                if (var7 == 0) {
                    var1.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.5f);
                }
                if (var7 == 1) {
                    var1.setBlockBounds(0.0f, 0.0f, 0.5f, 1.0f, 0.5f, 1.0f);
                }
                GL11.glTranslatef((float)-0.5f, (float)-0.5f, (float)-0.5f);
                var3.startDrawingQuads();
                var3.setNormal(0.0f, -1.0f, 0.0f);
                this.renderBottomFace(var1, 0.0, 0.0, 0.0, var1.getBlockTextureFromSide(0));
                var3.draw();
                var3.startDrawingQuads();
                var3.setNormal(0.0f, 1.0f, 0.0f);
                this.renderTopFace(var1, 0.0, 0.0, 0.0, var1.getBlockTextureFromSide(1));
                var3.draw();
                var3.startDrawingQuads();
                var3.setNormal(0.0f, 0.0f, -1.0f);
                this.renderEastFace(var1, 0.0, 0.0, 0.0, var1.getBlockTextureFromSide(2));
                var3.draw();
                var3.startDrawingQuads();
                var3.setNormal(0.0f, 0.0f, 1.0f);
                this.renderWestFace(var1, 0.0, 0.0, 0.0, var1.getBlockTextureFromSide(3));
                var3.draw();
                var3.startDrawingQuads();
                var3.setNormal(-1.0f, 0.0f, 0.0f);
                this.renderNorthFace(var1, 0.0, 0.0, 0.0, var1.getBlockTextureFromSide(4));
                var3.draw();
                var3.startDrawingQuads();
                var3.setNormal(1.0f, 0.0f, 0.0f);
                this.renderSouthFace(var1, 0.0, 0.0, 0.0, var1.getBlockTextureFromSide(5));
                var3.draw();
                GL11.glTranslatef((float)0.5f, (float)0.5f, (float)0.5f);
                ++var7;
            }
        } else if (var4 == 11) {
            int var7 = 0;
            while (var7 < 4) {
                float var6 = 0.125f;
                if (var7 == 0) {
                    var1.setBlockBounds(0.5f - var6, 0.0f, 0.0f, 0.5f + var6, 1.0f, var6 * 2.0f);
                }
                if (var7 == 1) {
                    var1.setBlockBounds(0.5f - var6, 0.0f, 1.0f - var6 * 2.0f, 0.5f + var6, 1.0f, 1.0f);
                }
                var6 = 0.0625f;
                if (var7 == 2) {
                    var1.setBlockBounds(0.5f - var6, 1.0f - var6 * 3.0f, -var6 * 2.0f, 0.5f + var6, 1.0f - var6, 1.0f + var6 * 2.0f);
                }
                if (var7 == 3) {
                    var1.setBlockBounds(0.5f - var6, 0.5f - var6 * 3.0f, -var6 * 2.0f, 0.5f + var6, 0.5f - var6, 1.0f + var6 * 2.0f);
                }
                GL11.glTranslatef((float)-0.5f, (float)-0.5f, (float)-0.5f);
                var3.startDrawingQuads();
                var3.setNormal(0.0f, -1.0f, 0.0f);
                this.renderBottomFace(var1, 0.0, 0.0, 0.0, var1.getBlockTextureFromSide(0));
                var3.draw();
                var3.startDrawingQuads();
                var3.setNormal(0.0f, 1.0f, 0.0f);
                this.renderTopFace(var1, 0.0, 0.0, 0.0, var1.getBlockTextureFromSide(1));
                var3.draw();
                var3.startDrawingQuads();
                var3.setNormal(0.0f, 0.0f, -1.0f);
                this.renderEastFace(var1, 0.0, 0.0, 0.0, var1.getBlockTextureFromSide(2));
                var3.draw();
                var3.startDrawingQuads();
                var3.setNormal(0.0f, 0.0f, 1.0f);
                this.renderWestFace(var1, 0.0, 0.0, 0.0, var1.getBlockTextureFromSide(3));
                var3.draw();
                var3.startDrawingQuads();
                var3.setNormal(-1.0f, 0.0f, 0.0f);
                this.renderNorthFace(var1, 0.0, 0.0, 0.0, var1.getBlockTextureFromSide(4));
                var3.draw();
                var3.startDrawingQuads();
                var3.setNormal(1.0f, 0.0f, 0.0f);
                this.renderSouthFace(var1, 0.0, 0.0, 0.0, var1.getBlockTextureFromSide(5));
                var3.draw();
                GL11.glTranslatef((float)0.5f, (float)0.5f, (float)0.5f);
                ++var7;
            }
            var1.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        }
    }

    public static boolean renderItemIn3d(int var0) {
        if (var0 == 0) {
            return true;
        }
        if (var0 == 13) {
            return true;
        }
        if (var0 == 10) {
            return true;
        }
        return var0 == 11;
    }
}

