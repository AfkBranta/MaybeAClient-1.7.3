/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package lunatrius.schematica;

import lunatrius.schematica.GuiSchematicLoad;
import lunatrius.schematica.Settings;
import net.minecraft.src.GuiSlot;
import net.minecraft.src.Tessellator;
import net.skidcode.gh.maybeaclient.Client;
import org.lwjgl.opengl.GL11;

public class GuiSchematicLoadSlot
extends GuiSlot {
    private final Settings settings = Settings.instance();
    final GuiSchematicLoad parentSchematicGuiChooser;

    public GuiSchematicLoadSlot(GuiSchematicLoad schematicGuiChooser) {
        super(Settings.instance().minecraft, schematicGuiChooser.width, schematicGuiChooser.height, 32, schematicGuiChooser.height - 55 + 4, 36);
        this.parentSchematicGuiChooser = schematicGuiChooser;
    }

    @Override
    protected int getSize() {
        return this.settings.getSchematicFiles().size();
    }

    @Override
    protected void elementClicked(int index, boolean par2) {
        this.settings.selectedSchematic = index;
    }

    @Override
    protected boolean isSelected(int index) {
        return index == this.settings.selectedSchematic;
    }

    @Override
    protected int getContentHeight() {
        return this.getSize() * 36;
    }

    @Override
    protected void drawBackground() {
        this.parentSchematicGuiChooser.drawDefaultBackground();
    }

    public static void bindTexture(String name) {
        int n = Client.mc.renderEngine.getTexture(name);
        GL11.glBindTexture((int)3553, (int)n);
    }

    @Override
    protected void drawSlot(int index, int x, int y, int par4, Tessellator tessellator) {
        String schematicName = this.settings.getSchematicFiles().get(index).replaceAll("(?i)\\.schematic$", "");
        GuiSchematicLoadSlot.bindTexture("/gui/unknown_pack.png");
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        tessellator.startDrawingQuads();
        tessellator.setColorOpaque_I(0xFFFFFF);
        tessellator.addVertexWithUV(x, y + par4, 0.0, 0.0, 1.0);
        tessellator.addVertexWithUV(x + 32, y + par4, 0.0, 1.0, 1.0);
        tessellator.addVertexWithUV(x + 32, y, 0.0, 1.0, 0.0);
        tessellator.addVertexWithUV(x, y, 0.0, 0.0, 0.0);
        tessellator.draw();
        this.parentSchematicGuiChooser.drawString(this.settings.minecraft.fontRenderer, schematicName, x + 32 + 2, y + 1, 0xFFFFFF);
    }
}

