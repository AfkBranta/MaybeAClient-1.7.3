/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.src;

import java.util.List;
import net.minecraft.src.GuiSlot;
import net.minecraft.src.GuiTexturePacks;
import net.minecraft.src.Tessellator;
import net.minecraft.src.TexturePackBase;
import net.skidcode.gh.maybeaclient.Client;
import org.lwjgl.opengl.GL11;

class GuiTexturePackSlot
extends GuiSlot {
    final GuiTexturePacks parentTexturePackGui;

    public GuiTexturePackSlot(GuiTexturePacks var1) {
        super(Client.mc, var1.width, var1.height, 32, var1.height - 55 + 4, 36);
        this.parentTexturePackGui = var1;
    }

    @Override
    protected int getSize() {
        List var1 = Client.mc.texturePackList.availableTexturePacks();
        return var1.size();
    }

    @Override
    protected void elementClicked(int var1, boolean var2) {
        List var3 = Client.mc.texturePackList.availableTexturePacks();
        Client.mc.texturePackList.setTexturePack((TexturePackBase)var3.get(var1));
        Client.mc.renderEngine.refreshTextures();
    }

    @Override
    protected boolean isSelected(int var1) {
        List var2 = Client.mc.texturePackList.availableTexturePacks();
        return Client.mc.texturePackList.selectedTexturePack == var2.get(var1);
    }

    @Override
    protected int getContentHeight() {
        return this.getSize() * 36;
    }

    @Override
    protected void drawBackground() {
        this.parentTexturePackGui.drawDefaultBackground();
    }

    @Override
    protected void drawSlot(int var1, int var2, int var3, int var4, Tessellator var5) {
        TexturePackBase var6 = (TexturePackBase)Client.mc.texturePackList.availableTexturePacks().get(var1);
        var6.bindThumbnailTexture(Client.mc);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        var5.startDrawingQuads();
        var5.setColorOpaque_I(0xFFFFFF);
        var5.addVertexWithUV(var2, var3 + var4, 0.0, 0.0, 1.0);
        var5.addVertexWithUV(var2 + 32, var3 + var4, 0.0, 1.0, 1.0);
        var5.addVertexWithUV(var2 + 32, var3, 0.0, 1.0, 0.0);
        var5.addVertexWithUV(var2, var3, 0.0, 0.0, 0.0);
        var5.draw();
        this.parentTexturePackGui.drawString(Client.mc.fontRenderer, var6.texturePackFileName, var2 + 32 + 2, var3 + 1, 0xFFFFFF);
        this.parentTexturePackGui.drawString(Client.mc.fontRenderer, var6.firstDescriptionLine, var2 + 32 + 2, var3 + 12, 0x808080);
        this.parentTexturePackGui.drawString(Client.mc.fontRenderer, var6.secondDescriptionLine, var2 + 32 + 2, var3 + 12 + 10, 0x808080);
    }
}

