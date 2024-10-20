/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.skidcode.gh.maybeaclient.hacks.settings;

import net.minecraft.src.Block;
import net.minecraft.src.GuiIngame;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;
import net.skidcode.gh.maybeaclient.Client;
import net.skidcode.gh.maybeaclient.gui.click.Tab;
import net.skidcode.gh.maybeaclient.hacks.Hack;
import net.skidcode.gh.maybeaclient.hacks.settings.Setting;
import org.lwjgl.opengl.GL11;

public class SettingBlockChooser
extends Setting {
    public boolean[] blocks = new boolean[Block.blocksList.length];
    public int[] ids;
    public static final int colsMax = 13;
    public int width;
    public int height;
    public boolean minimized = false;
    public boolean minPressd = false;
    public int xoff = -1;
    public int yoff = -1;

    public SettingBlockChooser(Hack hack, String name, int ... ids) {
        super(hack, name);
        int id;
        int[] nArray = ids;
        int n = ids.length;
        int n2 = 0;
        while (n2 < n) {
            id = nArray[n2];
            this.blocks[id] = true;
            ++n2;
        }
        this.ids = ids;
        id = 1;
        int drawn = 0;
        int hei = 0;
        int wid = 0;
        while (id < 256) {
            Block b = Block.blocksList[id];
            if (b != null) {
                wid = drawn * 18;
                ++drawn;
            }
            if (wid > this.width) {
                this.width = wid;
            }
            if (drawn >= 13) {
                drawn = 0;
                wid = 0;
                hei += 18;
            }
            ++id;
        }
        this.width += 18;
        this.height = hei + 18;
    }

    public void blockChanged(int id) {
    }

    @Override
    public String valueToString() {
        String s = "";
        int i = 0;
        while (i < this.blocks.length) {
            if (this.blocks[i]) {
                s = String.valueOf(s) + i + ", ";
            }
            ++i;
        }
        return s.substring(0, s.length() - 2);
    }

    @Override
    public void reset() {
        int i = 0;
        while (i < this.blocks.length) {
            this.blocks[i] = false;
            ++i;
        }
        int[] nArray = this.ids;
        int n = this.ids.length;
        int n2 = 0;
        while (n2 < n) {
            int id = nArray[n2];
            this.blocks[id] = true;
            ++n2;
        }
    }

    @Override
    public boolean validateValue(String value) {
        try {
            Integer.parseInt(value);
            return true;
        }
        catch (NumberFormatException numberFormatException) {
            return false;
        }
    }

    @Override
    public void renderElement(Tab tab, int xStart, int yStart, int xEnd, int yEnd) {
        if (this.minimized) {
            return;
        }
        tab.renderFrameBackGround(xStart, yStart, xEnd, yStart + 10, 0.0f, 0.6666667f, 0.6666667f, 1.0f);
        yStart += 12;
        GL11.glPushMatrix();
        int id = 1;
        int drawn = 0;
        int yOff = 0;
        while (id < 256) {
            Block b = Block.blocksList[id];
            if (b != null) {
                GL11.glDisable((int)2896);
                if (this.blocks[id]) {
                    int xb = xStart + drawn * 18;
                    int yb = yStart + yOff;
                    tab.renderFrameBackGround(xb, yb, xb + 16, yb + 16, 0.0f, 0.6666667f, 0.6666667f, 1.0f);
                }
                ++drawn;
            }
            if (drawn >= 13) {
                drawn = 0;
                yOff += 18;
            }
            ++id;
        }
        GL11.glPopMatrix();
    }

    @Override
    public void onDeselect(Tab tab, int xMin, int yMin, int xMax, int yMax, int mouseX, int mouseY, int mouseClick) {
        this.yoff = -1;
        this.xoff = -1;
        this.minPressd = false;
    }

    @Override
    public void onPressedInside(int xMin, int yMin, int xMax, int yMax, int mouseX, int mouseY, int mouseClick) {
        if (mouseY > yMin && mouseY < yMin + 12 && mouseX > xMin && mouseY < xMax) {
            if (!this.minPressd) {
                this.minimized = !this.minimized;
                this.minPressd = true;
            }
            return;
        }
        int col = (mouseX - (xMin += 2)) / 18;
        int row = (mouseY - (yMin += 14)) / 18;
        int offX = (mouseX - xMin) % 18;
        int id = 1;
        int drawn = 0;
        int yOff = 0;
        if (this.xoff == -1 && this.yoff == -1 && offX < 14) {
            while (id < 256) {
                Block b = Block.blocksList[id];
                if (b != null) {
                    if (yOff == row && col == drawn) {
                        this.blocks[id] = !this.blocks[id];
                        this.blockChanged(id);
                        this.xoff = drawn;
                        this.yoff = row;
                        break;
                    }
                    ++drawn;
                }
                if (drawn >= 13) {
                    drawn = 0;
                    ++yOff;
                }
                ++id;
            }
        }
    }

    @Override
    public void renderText(int x, int y) {
        Client.mc.fontRenderer.drawString(this.name, x + 2, y + 2, 0xFFFFFF);
        if (this.minimized) {
            return;
        }
        y += 12;
        GL11.glPushMatrix();
        int id = 1;
        int drawn = 0;
        int yOff = 0;
        while (id < 256) {
            Block b = Block.blocksList[id];
            if (b != null) {
                GL11.glDisable((int)2896);
                GL11.glDisable((int)2929);
                GuiIngame.itemRenderer.renderItemIntoGUI(Client.mc.fontRenderer, Client.mc.renderEngine, new ItemStack(b), x + drawn * 18, y + yOff);
                ++drawn;
            }
            if (drawn >= 13) {
                drawn = 0;
                yOff += 18;
            }
            ++id;
        }
        GL11.glDisable((int)2929);
        GL11.glPopMatrix();
    }

    @Override
    public void writeToNBT(NBTTagCompound output) {
        byte[] bs = new byte[this.blocks.length];
        int i = 0;
        while (i < bs.length) {
            bs[i] = (byte)(this.blocks[i] ? 1 : 0);
            ++i;
        }
        NBTTagCompound tg = new NBTTagCompound();
        tg.setByteArray("Blocks", bs);
        tg.setBoolean("Minimized", this.minimized);
        output.setCompoundTag(this.name, tg);
    }

    @Override
    public void readFromNBT(NBTTagCompound input) {
        NBTTagCompound tg = input.getCompoundTag(this.name);
        byte[] bts = tg.getByteArray("Blocks");
        int i = 0;
        while (i < bts.length) {
            this.blocks[i] = bts[i] != 0;
            ++i;
        }
        this.minimized = tg.getBoolean("Minimized");
    }

    @Override
    public int getSettingWidth() {
        return this.width;
    }

    @Override
    public int getSettingHeight() {
        if (this.minimized) {
            return 12;
        }
        return this.height + 12;
    }
}

