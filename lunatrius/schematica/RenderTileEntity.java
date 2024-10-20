/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package lunatrius.schematica;

import lunatrius.schematica.SchematicWorld;
import lunatrius.schematica.Settings;
import net.minecraft.src.Block;
import net.minecraft.src.BlockChest;
import net.minecraft.src.FontRenderer;
import net.minecraft.src.RenderEngine;
import net.minecraft.src.SignModel;
import net.minecraft.src.TileEntity;
import net.minecraft.src.TileEntityRenderer;
import net.minecraft.src.TileEntitySign;
import net.skidcode.gh.maybeaclient.hacks.SchematicaHack;
import org.lwjgl.opengl.GL11;

public class RenderTileEntity {
    private final Settings settings = Settings.instance();
    private final SignModel modelSign = new SignModel();
    private final SchematicWorld world;

    public RenderTileEntity(SchematicWorld world) {
        this.world = world;
    }

    public void renderTileEntitySignAt(TileEntitySign par1TileEntitySign) {
        float var12;
        Block var9 = this.getBlockType(par1TileEntitySign);
        GL11.glPushMatrix();
        float var10 = 0.6666667f;
        if (var9 == Block.signPost) {
            GL11.glTranslatef((float)((float)par1TileEntitySign.xCoord + 0.5f), (float)((float)par1TileEntitySign.yCoord + 0.75f * var10), (float)((float)par1TileEntitySign.zCoord + 0.5f));
            float var11 = (float)(this.getBlockMetadata(par1TileEntitySign) * 360) / 16.0f;
            GL11.glRotatef((float)(-var11), (float)0.0f, (float)1.0f, (float)0.0f);
            this.modelSign.field_1345_b.showModel = true;
        } else {
            int var16 = this.getBlockMetadata(par1TileEntitySign);
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
            GL11.glTranslatef((float)((float)par1TileEntitySign.xCoord + 0.5f), (float)((float)par1TileEntitySign.yCoord + 0.75f * var10), (float)((float)par1TileEntitySign.zCoord + 0.5f));
            GL11.glRotatef((float)(-var12), (float)0.0f, (float)1.0f, (float)0.0f);
            GL11.glTranslatef((float)0.0f, (float)-0.3125f, (float)-0.4375f);
            this.modelSign.field_1345_b.showModel = false;
        }
        this.bindTextureByName("/item/sign.png");
        GL11.glPushMatrix();
        GL11.glScalef((float)var10, (float)(-var10), (float)(-var10));
        this.modelSign.func_887_a();
        GL11.glPopMatrix();
        FontRenderer var17 = this.settings.minecraft.fontRenderer;
        var12 = 0.016666668f * var10;
        GL11.glTranslatef((float)0.0f, (float)(0.5f * var10), (float)(0.07f * var10));
        GL11.glScalef((float)var12, (float)(-var12), (float)var12);
        GL11.glNormal3f((float)0.0f, (float)0.0f, (float)(-1.0f * var12));
        GL11.glDepthMask((boolean)false);
        int var13 = (int)((255.0f - (float)SchematicaHack.instance.alpha.value) * 255.0f) * 0x1000000;
        int var14 = 0;
        while (var14 < par1TileEntitySign.signText.length) {
            String var15 = par1TileEntitySign.signText[var14];
            var17.drawString(var15, -var17.getStringWidth(var15) / 2, var14 * 10 - par1TileEntitySign.signText.length * 5, var13);
            ++var14;
        }
        GL11.glDepthMask((boolean)true);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)(255.0f - (float)SchematicaHack.instance.alpha.value));
        GL11.glPopMatrix();
    }

    private Block getBlockType(TileEntity tileEntity) {
        return this.world.getBlock(tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord);
    }

    private int getBlockMetadata(TileEntity tileEntity) {
        return this.world.getBlockMetadata(tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord);
    }

    private void unifyAdjacentChests(BlockChest par1BlockChest, SchematicWorld par2World, int par3, int par4, int par5) {
        int var5 = par2World.getBlockId(par3, par4, par5 - 1);
        int var6 = par2World.getBlockId(par3, par4, par5 + 1);
        int var7 = par2World.getBlockId(par3 - 1, par4, par5);
        int var8 = par2World.getBlockId(par3 + 1, par4, par5);
        if (var5 != par1BlockChest.blockID && var6 != par1BlockChest.blockID) {
            if (var7 != par1BlockChest.blockID && var8 != par1BlockChest.blockID) {
                int metadata = 3;
                if (Block.opaqueCubeLookup[var5] && !Block.opaqueCubeLookup[var6]) {
                    metadata = 3;
                }
                if (Block.opaqueCubeLookup[var6] && !Block.opaqueCubeLookup[var5]) {
                    metadata = 2;
                }
                if (Block.opaqueCubeLookup[var7] && !Block.opaqueCubeLookup[var8]) {
                    metadata = 5;
                }
                if (Block.opaqueCubeLookup[var8] && !Block.opaqueCubeLookup[var7]) {
                    metadata = 4;
                }
            } else {
                int var10 = par2World.getBlockId(var7 == par1BlockChest.blockID ? par3 - 1 : par3 + 1, par4, par5 - 1);
                int var11 = par2World.getBlockId(var7 == par1BlockChest.blockID ? par3 - 1 : par3 + 1, par4, par5 + 1);
                int metadata = 3;
                int var14 = var7 == par1BlockChest.blockID ? par2World.getBlockMetadata(par3 - 1, par4, par5) : par2World.getBlockMetadata(par3 + 1, par4, par5);
                if (var14 == 2) {
                    metadata = 2;
                }
                if ((Block.opaqueCubeLookup[var5] || Block.opaqueCubeLookup[var10]) && !Block.opaqueCubeLookup[var6] && !Block.opaqueCubeLookup[var11]) {
                    metadata = 3;
                }
                if ((Block.opaqueCubeLookup[var6] || Block.opaqueCubeLookup[var11]) && !Block.opaqueCubeLookup[var5] && !Block.opaqueCubeLookup[var10]) {
                    metadata = 2;
                }
            }
        } else {
            int var10 = par2World.getBlockId(par3 - 1, par4, var5 == par1BlockChest.blockID ? par5 - 1 : par5 + 1);
            int var11 = par2World.getBlockId(par3 + 1, par4, var5 == par1BlockChest.blockID ? par5 - 1 : par5 + 1);
            int metadata = 5;
            int var14 = var5 == par1BlockChest.blockID ? par2World.getBlockMetadata(par3, par4, par5 - 1) : par2World.getBlockMetadata(par3, par4, par5 + 1);
            if (var14 == 4) {
                metadata = 4;
            }
            if ((Block.opaqueCubeLookup[var7] || Block.opaqueCubeLookup[var10]) && !Block.opaqueCubeLookup[var8] && !Block.opaqueCubeLookup[var11]) {
                metadata = 5;
            }
            if ((Block.opaqueCubeLookup[var8] || Block.opaqueCubeLookup[var11]) && !Block.opaqueCubeLookup[var7] && !Block.opaqueCubeLookup[var10]) {
                metadata = 4;
            }
            par2World.setBlockMetadata(par3, par4, par5, (byte)metadata);
        }
    }

    private void bindTextureByName(String string) {
        RenderEngine var2 = TileEntityRenderer.instance.renderEngine;
        if (var2 != null) {
            var2.bindTexture(var2.getTexture(string));
        }
    }
}

