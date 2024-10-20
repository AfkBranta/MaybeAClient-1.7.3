/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.Date;
import net.minecraft.src.GuiSelectWorld;
import net.minecraft.src.GuiSlot;
import net.minecraft.src.MathHelper;
import net.minecraft.src.SaveFormatComparator;
import net.minecraft.src.Tessellator;

class GuiWorldSlot
extends GuiSlot {
    final GuiSelectWorld parentWorldGui;

    public GuiWorldSlot(GuiSelectWorld var1) {
        super(var1.mc, var1.width, var1.height, 32, var1.height - 64, 36);
        this.parentWorldGui = var1;
    }

    @Override
    protected int getSize() {
        return GuiSelectWorld.getSize(this.parentWorldGui).size();
    }

    @Override
    protected void elementClicked(int var1, boolean var2) {
        boolean var3;
        GuiSelectWorld.onElementSelected(this.parentWorldGui, var1);
        GuiSelectWorld.getSelectButton((GuiSelectWorld)this.parentWorldGui).enabled = var3 = GuiSelectWorld.getSelectedWorld(this.parentWorldGui) >= 0 && GuiSelectWorld.getSelectedWorld(this.parentWorldGui) < this.getSize();
        GuiSelectWorld.getRenameButton((GuiSelectWorld)this.parentWorldGui).enabled = var3;
        GuiSelectWorld.getDeleteButton((GuiSelectWorld)this.parentWorldGui).enabled = var3;
        if (var2 && var3) {
            this.parentWorldGui.selectWorld(var1);
        }
    }

    @Override
    protected boolean isSelected(int var1) {
        return var1 == GuiSelectWorld.getSelectedWorld(this.parentWorldGui);
    }

    @Override
    protected int getContentHeight() {
        return GuiSelectWorld.getSize(this.parentWorldGui).size() * 36;
    }

    @Override
    protected void drawBackground() {
        this.parentWorldGui.drawDefaultBackground();
    }

    @Override
    protected void drawSlot(int var1, int var2, int var3, int var4, Tessellator var5) {
        SaveFormatComparator var6 = (SaveFormatComparator)GuiSelectWorld.getSize(this.parentWorldGui).get(var1);
        String var7 = var6.getDisplayName();
        if (var7 == null || MathHelper.stringNullOrLengthZero(var7)) {
            var7 = String.valueOf(GuiSelectWorld.func_22087_f(this.parentWorldGui)) + " " + (var1 + 1);
        }
        String var8 = var6.getFileName();
        var8 = String.valueOf(var8) + " (" + GuiSelectWorld.getDateFormatter(this.parentWorldGui).format(new Date(var6.func_22163_e()));
        long var9 = var6.func_22159_c();
        var8 = String.valueOf(var8) + ", " + (float)(var9 / 1024L * 100L / 1024L) / 100.0f + " MB)";
        String var11 = "";
        if (var6.func_22161_d()) {
            var11 = String.valueOf(GuiSelectWorld.func_22088_h(this.parentWorldGui)) + " " + var11;
        }
        this.parentWorldGui.drawString(this.parentWorldGui.fontRenderer, var7, var2 + 2, var3 + 1, 0xFFFFFF);
        this.parentWorldGui.drawString(this.parentWorldGui.fontRenderer, var8, var2 + 2, var3 + 12, 0x808080);
        this.parentWorldGui.drawString(this.parentWorldGui.fontRenderer, var11, var2 + 2, var3 + 12 + 10, 0x808080);
    }
}

