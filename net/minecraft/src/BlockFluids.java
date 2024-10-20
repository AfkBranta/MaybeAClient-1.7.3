/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.Random;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Block;
import net.minecraft.src.Entity;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.Material;
import net.minecraft.src.Vec3D;
import net.minecraft.src.World;
import net.skidcode.gh.maybeaclient.Client;
import net.skidcode.gh.maybeaclient.hacks.FullBrightHack;
import net.skidcode.gh.maybeaclient.hacks.JesusHack;
import net.skidcode.gh.maybeaclient.hacks.LiquidInteractHack;
import net.skidcode.gh.maybeaclient.hacks.XRayHack;

public abstract class BlockFluids
extends Block {
    public static boolean forceNullBB = false;

    protected BlockFluids(int var1, Material var2) {
        super(var1, (var2 == Material.lava ? 14 : 12) * 16 + 13, var2);
        float var3 = 0.0f;
        float var4 = 0.0f;
        this.setBlockBounds(0.0f + var4, 0.0f + var3, 0.0f + var4, 1.0f + var4, 1.0f + var3, 1.0f + var4);
        this.setTickOnLoad(true);
    }

    public static float getPercentAir(int var0) {
        if (var0 >= 8) {
            var0 = 0;
        }
        float var1 = (float)(var0 + 1) / 9.0f;
        return var1;
    }

    @Override
    public int getBlockTextureFromSide(int var1) {
        return var1 != 0 && var1 != 1 ? this.blockIndexInTexture + 1 : this.blockIndexInTexture;
    }

    protected int getFlowDecay(World var1, int var2, int var3, int var4) {
        return var1.getBlockMaterial(var2, var3, var4) != this.blockMaterial ? -1 : var1.getBlockMetadata(var2, var3, var4);
    }

    protected int getEffectiveFlowDecay(IBlockAccess var1, int var2, int var3, int var4) {
        if (var1.getBlockMaterial(var2, var3, var4) != this.blockMaterial) {
            return -1;
        }
        int var5 = var1.getBlockMetadata(var2, var3, var4);
        if (var5 >= 8) {
            var5 = 0;
        }
        return var5;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean canCollideCheck(int var1, boolean var2) {
        if (LiquidInteractHack.instance.status) {
            return true;
        }
        return var2 && var1 == 0;
    }

    @Override
    public boolean shouldSideBeRendered(IBlockAccess var1, int var2, int var3, int var4, int var5) {
        Material var6 = var1.getBlockMaterial(var2, var3, var4);
        if (XRayHack.INSTANCE.status) {
            return true;
        }
        if (var6 == this.blockMaterial) {
            return false;
        }
        if (var6 == Material.ice) {
            return false;
        }
        return var5 == 1 ? true : super.shouldSideBeRendered(var1, var2, var3, var4, var5);
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World var1, int var2, int var3, int var4) {
        if (forceNullBB) {
            return null;
        }
        if (JesusHack.INSTANCE.status) {
            if (JesusHack.INSTANCE.mode.currentMode.equalsIgnoreCase("Normal+")) {
                if (Client.mc.thePlayer.inWater && !Client.mc.thePlayer.isSneaking()) {
                    Client.mc.thePlayer.jump();
                    return null;
                }
                if (Client.mc.thePlayer.fallDistance < 2.0f && !Client.mc.thePlayer.isSneaking()) {
                    return super.getCollisionBoundingBoxFromPool(var1, var2, var3, var4);
                }
                return null;
            }
            if (JesusHack.INSTANCE.mode.currentMode.equalsIgnoreCase("Jump")) {
                if (Client.mc.thePlayer.inWater) {
                    Client.mc.thePlayer.jump();
                }
                return null;
            }
            return super.getCollisionBoundingBoxFromPool(var1, var2, var3, var4);
        }
        return null;
    }

    @Override
    public int getRenderType() {
        return 4;
    }

    @Override
    public int idDropped(int var1, Random var2) {
        return 0;
    }

    @Override
    public int quantityDropped(Random var1) {
        return 0;
    }

    private Vec3D getFlowVector(IBlockAccess var1, int var2, int var3, int var4) {
        Vec3D var5 = Vec3D.createVector(0.0, 0.0, 0.0);
        int var6 = this.getEffectiveFlowDecay(var1, var2, var3, var4);
        int var7 = 0;
        while (var7 < 4) {
            int var12;
            int var11;
            int var8 = var2;
            int var10 = var4;
            if (var7 == 0) {
                var8 = var2 - 1;
            }
            if (var7 == 1) {
                var10 = var4 - 1;
            }
            if (var7 == 2) {
                ++var8;
            }
            if (var7 == 3) {
                ++var10;
            }
            if ((var11 = this.getEffectiveFlowDecay(var1, var8, var3, var10)) < 0) {
                if (!var1.getBlockMaterial(var8, var3, var10).getIsSolid() && (var11 = this.getEffectiveFlowDecay(var1, var8, var3 - 1, var10)) >= 0) {
                    var12 = var11 - (var6 - 8);
                    var5 = var5.addVector((var8 - var2) * var12, (var3 - var3) * var12, (var10 - var4) * var12);
                }
            } else if (var11 >= 0) {
                var12 = var11 - var6;
                var5 = var5.addVector((var8 - var2) * var12, (var3 - var3) * var12, (var10 - var4) * var12);
            }
            ++var7;
        }
        if (var1.getBlockMetadata(var2, var3, var4) >= 8) {
            boolean var13 = false;
            if (var13 || this.shouldSideBeRendered(var1, var2, var3, var4 - 1, 2)) {
                var13 = true;
            }
            if (var13 || this.shouldSideBeRendered(var1, var2, var3, var4 + 1, 3)) {
                var13 = true;
            }
            if (var13 || this.shouldSideBeRendered(var1, var2 - 1, var3, var4, 4)) {
                var13 = true;
            }
            if (var13 || this.shouldSideBeRendered(var1, var2 + 1, var3, var4, 5)) {
                var13 = true;
            }
            if (var13 || this.shouldSideBeRendered(var1, var2, var3 + 1, var4 - 1, 2)) {
                var13 = true;
            }
            if (var13 || this.shouldSideBeRendered(var1, var2, var3 + 1, var4 + 1, 3)) {
                var13 = true;
            }
            if (var13 || this.shouldSideBeRendered(var1, var2 - 1, var3 + 1, var4, 4)) {
                var13 = true;
            }
            if (var13 || this.shouldSideBeRendered(var1, var2 + 1, var3 + 1, var4, 5)) {
                var13 = true;
            }
            if (var13) {
                var5 = var5.normalize().addVector(0.0, -6.0, 0.0);
            }
        }
        var5 = var5.normalize();
        return var5;
    }

    @Override
    public void velocityToAddToEntity(World var1, int var2, int var3, int var4, Entity var5, Vec3D var6) {
        Vec3D var7 = this.getFlowVector(var1, var2, var3, var4);
        var6.xCoord += var7.xCoord;
        var6.yCoord += var7.yCoord;
        var6.zCoord += var7.zCoord;
    }

    @Override
    public int tickRate() {
        if (this.blockMaterial == Material.water) {
            return 5;
        }
        return this.blockMaterial == Material.lava ? 30 : 0;
    }

    @Override
    public float getBlockBrightness(IBlockAccess var1, int var2, int var3, int var4) {
        float var6;
        if (FullBrightHack.INSTANCE.status || XRayHack.INSTANCE.status) {
            return 1.0f;
        }
        float var5 = var1.getLightBrightness(var2, var3, var4);
        return var5 > (var6 = var1.getLightBrightness(var2, var3 + 1, var4)) ? var5 : var6;
    }

    @Override
    public void updateTick(World var1, int var2, int var3, int var4, Random var5) {
        super.updateTick(var1, var2, var3, var4, var5);
    }

    @Override
    public int getRenderBlockPass() {
        return this.blockMaterial == Material.water ? 1 : 0;
    }

    @Override
    public void randomDisplayTick(World var1, int var2, int var3, int var4, Random var5) {
        int var6;
        if (this.blockMaterial == Material.water && var5.nextInt(64) == 0 && (var6 = var1.getBlockMetadata(var2, var3, var4)) > 0 && var6 < 8) {
            var1.playSoundEffect((float)var2 + 0.5f, (float)var3 + 0.5f, (float)var4 + 0.5f, "liquid.water", var5.nextFloat() * 0.25f + 0.75f, var5.nextFloat() * 1.0f + 0.5f);
        }
        if (this.blockMaterial == Material.lava && var1.getBlockMaterial(var2, var3 + 1, var4) == Material.air && !var1.isBlockOpaqueCube(var2, var3 + 1, var4) && var5.nextInt(100) == 0) {
            double var12 = (float)var2 + var5.nextFloat();
            double var8 = (double)var3 + this.maxY;
            double var10 = (float)var4 + var5.nextFloat();
            var1.spawnParticle("lava", var12, var8, var10, 0.0, 0.0, 0.0);
        }
    }

    public static double func_293_a(IBlockAccess var0, int var1, int var2, int var3, Material var4) {
        Vec3D var5 = null;
        if (var4 == Material.water) {
            var5 = ((BlockFluids)Block.waterMoving).getFlowVector(var0, var1, var2, var3);
        }
        if (var4 == Material.lava) {
            var5 = ((BlockFluids)Block.lavaMoving).getFlowVector(var0, var1, var2, var3);
        }
        return var5.xCoord == 0.0 && var5.zCoord == 0.0 ? -1000.0 : Math.atan2(var5.zCoord, var5.xCoord) - 1.5707963267948966;
    }

    @Override
    public void onBlockAdded(World var1, int var2, int var3, int var4) {
        this.checkForHarden(var1, var2, var3, var4);
    }

    @Override
    public void onNeighborBlockChange(World var1, int var2, int var3, int var4, int var5) {
        this.checkForHarden(var1, var2, var3, var4);
    }

    private void checkForHarden(World var1, int var2, int var3, int var4) {
        if (var1.getBlockId(var2, var3, var4) == this.blockID && this.blockMaterial == Material.lava) {
            boolean var5 = false;
            if (var5 || var1.getBlockMaterial(var2, var3, var4 - 1) == Material.water) {
                var5 = true;
            }
            if (var5 || var1.getBlockMaterial(var2, var3, var4 + 1) == Material.water) {
                var5 = true;
            }
            if (var5 || var1.getBlockMaterial(var2 - 1, var3, var4) == Material.water) {
                var5 = true;
            }
            if (var5 || var1.getBlockMaterial(var2 + 1, var3, var4) == Material.water) {
                var5 = true;
            }
            if (var5 || var1.getBlockMaterial(var2, var3 + 1, var4) == Material.water) {
                var5 = true;
            }
            if (var5) {
                int var6 = var1.getBlockMetadata(var2, var3, var4);
                if (var6 == 0) {
                    var1.setBlockWithNotify(var2, var3, var4, Block.obsidian.blockID);
                } else if (var6 <= 4) {
                    var1.setBlockWithNotify(var2, var3, var4, Block.cobblestone.blockID);
                }
                this.triggerLavaMixEffects(var1, var2, var3, var4);
            }
        }
    }

    protected void triggerLavaMixEffects(World var1, int var2, int var3, int var4) {
        var1.playSoundEffect((float)var2 + 0.5f, (float)var3 + 0.5f, (float)var4 + 0.5f, "random.fizz", 0.5f, 2.6f + (var1.rand.nextFloat() - var1.rand.nextFloat()) * 0.8f);
        int var5 = 0;
        while (var5 < 8) {
            var1.spawnParticle("largesmoke", (double)var2 + Math.random(), (double)var3 + 1.2, (double)var4 + Math.random(), 0.0, 0.0, 0.0);
            ++var5;
        }
    }
}

