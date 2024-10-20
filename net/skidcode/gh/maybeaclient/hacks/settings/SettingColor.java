/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Mouse
 *  org.lwjgl.opengl.GL11
 */
package net.skidcode.gh.maybeaclient.hacks.settings;

import java.nio.ByteBuffer;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.Tessellator;
import net.skidcode.gh.maybeaclient.Client;
import net.skidcode.gh.maybeaclient.gui.click.Tab;
import net.skidcode.gh.maybeaclient.hacks.Hack;
import net.skidcode.gh.maybeaclient.hacks.settings.Setting;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class SettingColor
extends Setting {
    public static final int sliders = 1;
    public static final int picker = 2;
    public int guiMode = 1;
    public int initialR;
    public int initialG;
    public int initialB;
    public int red;
    public int green;
    public int blue;
    public ByteBuffer colPick = ByteBuffer.allocateDirect(16);
    public boolean minimized = true;
    int pressedOn = -1;

    public SettingColor(Hack hack, String name, int initialR, int initialG, int initialB) {
        super(hack, name);
        this.setValue(initialR, initialG, initialB);
        this.initialG = initialG;
        this.initialR = initialR;
        this.initialB = initialB;
    }

    public void setValue(int r, int g, int b) {
        this.red = r;
        this.green = g;
        this.blue = b;
    }

    @Override
    public String valueToString() {
        return this.red + ";" + this.green + ";" + this.blue;
    }

    @Override
    public boolean validateValue(String value) {
        String[] splitted = value.split(";");
        if (splitted.length != 3) {
            return false;
        }
        String[] stringArray = splitted;
        int n = splitted.length;
        int n2 = 0;
        while (n2 < n) {
            String s = stringArray[n2];
            try {
                Integer.parseInt(s);
            }
            catch (NumberFormatException e) {
                return false;
            }
            ++n2;
        }
        return true;
    }

    @Override
    public void reset() {
        this.setValue(this.initialR, this.initialG, this.initialB);
    }

    @Override
    public void writeToNBT(NBTTagCompound output) {
        NBTTagCompound tg = new NBTTagCompound();
        tg.setByteArray("Color", new byte[]{(byte)(this.initialR & 0xFF), (byte)(this.initialG & 0xFF), (byte)(this.initialB & 0xFF)});
        tg.setInteger("GUIMode", this.guiMode);
        tg.setBoolean("Minimized", this.minimized);
        output.setCompoundTag(this.name, tg);
    }

    public String getModeName() {
        if (this.guiMode == 1) {
            return "Slider";
        }
        return "Picker";
    }

    @Override
    public void readFromNBT(NBTTagCompound input) {
        if (input.hasKey(this.name)) {
            if (Client.convertingVersion == 1) {
                System.out.println("SettingColor: converting.(Hack name: " + this.hack.name + ")");
                byte[] bytes = input.getByteArray(this.name);
                this.setValue(bytes[0] & 0xFF, bytes[1] & 0xFF, bytes[2] & 0xFF);
            } else {
                int guiMode;
                NBTTagCompound tg = input.getCompoundTag(this.name);
                byte[] bytes = tg.getByteArray("Color");
                this.setValue(bytes[0] & 0xFF, bytes[1] & 0xFF, bytes[2] & 0xFF);
                this.guiMode = guiMode = tg.getInteger("GUIMode");
                if (tg.hasKey("Minimized")) {
                    boolean minimized;
                    this.minimized = minimized = tg.getBoolean("Minimized");
                }
            }
        }
    }

    @Override
    public void renderText(int x, int y) {
        Client.mc.fontRenderer.drawString(this.name, x + 2, y + 2, 0xFFFFFF);
        if (this.minimized) {
            return;
        }
        Client.mc.fontRenderer.drawString("Mode - " + this.getModeName(), x + 2 + 4, y + 2 + 12, 0xFFFFFF);
        if (this.guiMode == 1) {
            Client.mc.fontRenderer.drawString("Red - " + this.red, x + 2 + 4, y + 2 + 24, 0xFFFFFF);
            Client.mc.fontRenderer.drawString("Green - " + this.green, x + 2 + 4, y + 2 + 36, 0xFFFFFF);
            Client.mc.fontRenderer.drawString("Blue - " + this.blue, x + 2 + 4, y + 2 + 48, 0xFFFFFF);
        }
    }

    @Override
    public void onDeselect(Tab tab, int xMin, int yMin, int xMax, int yMax, int mouseX, int mouseY, int mouseClick) {
        this.pressedOn = -1;
    }

    @Override
    public void onMouseMoved(int xMin, int yMin, int xMax, int yMax, int mouseX, int mouseY, int mouseClick) {
        int mouseOff = mouseX - xMin;
        int sizeX = xMax - xMin;
        float step = 255.0f / (float)sizeX;
        int val = (int)((float)Math.round((float)mouseOff * step * 100.0f) / 100.0f);
        if (val < 0) {
            val = 0;
        }
        if (val > 255) {
            val = 255;
        }
        if (this.guiMode == 2 && this.pressedOn > 1) {
            if (mouseX < xMin || mouseX > xMax) {
                return;
            }
            if (mouseY < yMin + 12 || mouseY > yMax) {
                return;
            }
            int mx = Mouse.getX();
            int my = Mouse.getY();
            GL11.glReadPixels((int)mx, (int)my, (int)1, (int)1, (int)6408, (int)5121, (ByteBuffer)this.colPick);
            this.red = this.colPick.get(0) & 0xFF;
            this.green = this.colPick.get(1) & 0xFF;
            this.blue = this.colPick.get(2) & 0xFF;
            return;
        }
        switch (this.pressedOn) {
            case 2: {
                this.red = val;
                break;
            }
            case 3: {
                this.green = val;
                break;
            }
            case 4: {
                this.blue = val;
            }
        }
    }

    @Override
    public void onPressedInside(int xMin, int yMin, int xMax, int yMax, int mouseX, int mouseY, int mouseClick) {
        block15: {
            int mos;
            block14: {
                mos = (mouseY - yMin) / 12;
                if (mos <= 0) break block14;
                int oldPressedOn = this.pressedOn;
                this.pressedOn = mos;
                int mouseOff = mouseX - xMin;
                int sizeX = xMax - xMin;
                float step = 255.0f / (float)sizeX;
                int val = (int)((float)Math.round((float)mouseOff * step * 100.0f) / 100.0f);
                if (val < 0) {
                    val = 0;
                }
                if (val > 255) {
                    val = 255;
                }
                if (this.guiMode == 2 && mos > 1) {
                    if (mouseX < xMin || mouseX > xMax) {
                        return;
                    }
                    if (mouseY < yMin + 12 || mouseY > yMax) {
                        return;
                    }
                    int mx = Mouse.getX();
                    int my = Mouse.getY();
                    GL11.glReadPixels((int)mx, (int)my, (int)1, (int)1, (int)6408, (int)5121, (ByteBuffer)this.colPick);
                    this.red = this.colPick.get(0) & 0xFF;
                    this.green = this.colPick.get(1) & 0xFF;
                    this.blue = this.colPick.get(2) & 0xFF;
                    this.colPick.clear();
                    return;
                }
                switch (mos) {
                    case 1: {
                        if (oldPressedOn == -1) {
                            ++this.guiMode;
                            if (this.guiMode > 2) {
                                this.guiMode = 1;
                                break;
                            }
                        }
                        break block15;
                    }
                    case 2: {
                        this.red = val;
                        break;
                    }
                    case 3: {
                        this.green = val;
                        break;
                    }
                    case 4: {
                        this.blue = val;
                    }
                }
                break block15;
            }
            if (mos == 0 && this.pressedOn == -1) {
                this.minimized = !this.minimized;
                this.pressedOn = 0;
            }
        }
    }

    @Override
    public void renderElement(Tab tab, int xStart, int yStart, int xEnd, int yEnd) {
        if (!this.minimized) {
            tab.renderFrameBackGround(xStart, yStart, xEnd, yStart + 10, 0.0f, 0.6666667f, 0.6666667f, 1.0f);
        }
        int xmi = xEnd - 10;
        int xma = xEnd;
        int ymi = yStart;
        int yma = yStart + 10;
        tab.renderFrameBackGround(xmi, ymi, xma, yma, (float)this.red / 255.0f, (float)this.green / 255.0f, (float)this.blue / 255.0f, 1.0f);
        if (this.minimized) {
            return;
        }
        int diff1 = xEnd - xStart;
        float step = 255.0f / (float)diff1;
        tab.renderFrameBackGround(xStart, yStart += 12, xEnd, yStart + 10, 0.0f, 0.6666667f, 0.6666667f, 1.0f);
        yStart += 12;
        if (this.guiMode == 1) {
            int value = this.red;
            if (value > 255) {
                value = 255;
            }
            if (value < 0) {
                value = 0;
            }
            int diff3 = (int)((float)value / step);
            tab.renderFrameBackGround(xStart, yStart, xStart + diff3, yStart + 10, 0.0f, 0.6666667f, 0.6666667f, 1.0f);
            yStart += 12;
            value = this.green;
            if (value > 255) {
                value = 255;
            }
            if (value < 0) {
                value = 0;
            }
            diff3 = (int)((float)value / step);
            tab.renderFrameBackGround(xStart, yStart, xStart + diff3, yStart + 10, 0.0f, 0.6666667f, 0.6666667f, 1.0f);
            yStart += 12;
            value = this.blue;
            if (value > 255) {
                value = 255;
            }
            if (value < 0) {
                value = 0;
            }
            diff3 = (int)((float)value / step);
            tab.renderFrameBackGround(xStart, yStart, xStart + diff3, yStart + 10, 0.0f, 0.6666667f, 0.6666667f, 1.0f);
        } else {
            Tessellator tes = Tessellator.instance;
            int yHalfEnd = yStart + (yEnd - yStart) / 2;
            GL11.glPushMatrix();
            GL11.glColor4f((float)0.0f, (float)0.0f, (float)0.0f, (float)1.0f);
            GL11.glBegin((int)7);
            GL11.glVertex2d((double)xStart, (double)yStart);
            GL11.glVertex2d((double)xStart, (double)yHalfEnd);
            GL11.glVertex2d((double)xEnd, (double)yHalfEnd);
            GL11.glVertex2d((double)xEnd, (double)yStart);
            GL11.glEnd();
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            GL11.glBegin((int)7);
            GL11.glVertex2d((double)xStart, (double)yHalfEnd);
            GL11.glVertex2d((double)xStart, (double)yEnd);
            GL11.glVertex2d((double)xEnd, (double)yEnd);
            GL11.glVertex2d((double)xEnd, (double)yHalfEnd);
            GL11.glEnd();
            int prv = GL11.glGetInteger((int)2900);
            GL11.glShadeModel((int)7425);
            GL11.glBlendFunc((int)770, (int)771);
            GL11.glEnable((int)3042);
            GL11.glBegin((int)7);
            int i = 0;
            while (i < 6) {
                float hue = i * 60;
                float saturation = 100.0f;
                float value = 100.0f;
                float huenxt = (i + 1) * 60;
                float factor = (float)(xEnd - xStart) / 6.0f;
                float r = 0.0f;
                float g = 0.0f;
                float b = 0.0f;
                float rn = 0.0f;
                float gn = 0.0f;
                float bn = 0.0f;
                float h = hue / 360.0f;
                float hn = huenxt / 360.0f;
                float s = saturation / 100.0f;
                float v = value / 100.0f;
                int d = (int)(h * 6.0f);
                float f = h * 6.0f - (float)d;
                float p = v * (1.0f - s);
                float q = v * (1.0f - f * s);
                float t = v * (1.0f - (1.0f - f) * s);
                switch (d % 6) {
                    case 0: {
                        r = v;
                        g = t;
                        b = p;
                        break;
                    }
                    case 1: {
                        r = q;
                        g = v;
                        b = p;
                        break;
                    }
                    case 2: {
                        r = p;
                        g = v;
                        b = t;
                        break;
                    }
                    case 3: {
                        r = p;
                        g = q;
                        b = v;
                        break;
                    }
                    case 4: {
                        r = t;
                        g = p;
                        b = v;
                        break;
                    }
                    case 5: {
                        r = v;
                        g = p;
                        b = q;
                    }
                }
                d = (int)(hn * 6.0f);
                f = h * 6.0f - (float)d;
                p = v * (1.0f - s);
                q = v * (1.0f - f * s);
                t = v * (1.0f - (1.0f - f) * s);
                switch (d % 6) {
                    case 0: {
                        rn = v;
                        gn = t;
                        bn = p;
                        break;
                    }
                    case 1: {
                        rn = q;
                        gn = v;
                        bn = p;
                        break;
                    }
                    case 2: {
                        rn = p;
                        gn = v;
                        bn = t;
                        break;
                    }
                    case 3: {
                        rn = p;
                        gn = q;
                        bn = v;
                        break;
                    }
                    case 4: {
                        rn = t;
                        gn = p;
                        bn = v;
                        break;
                    }
                    case 5: {
                        rn = v;
                        gn = p;
                        bn = q;
                    }
                }
                GL11.glColor4f((float)r, (float)g, (float)b, (float)0.0f);
                GL11.glVertex2d((double)((float)xStart + (float)i * factor), (double)yEnd);
                GL11.glColor4f((float)rn, (float)gn, (float)bn, (float)0.0f);
                GL11.glVertex2d((double)((float)xStart + (float)(i + 1) * factor), (double)yEnd);
                GL11.glColor4f((float)rn, (float)gn, (float)bn, (float)1.0f);
                GL11.glVertex2d((double)((float)xStart + (float)(i + 1) * factor), (double)yHalfEnd);
                GL11.glColor4f((float)r, (float)g, (float)b, (float)1.0f);
                GL11.glVertex2d((double)((float)xStart + (float)i * factor), (double)yHalfEnd);
                GL11.glColor4f((float)r, (float)g, (float)b, (float)1.0f);
                GL11.glVertex2d((double)((float)xStart + (float)i * factor), (double)yHalfEnd);
                GL11.glColor4f((float)rn, (float)gn, (float)bn, (float)1.0f);
                GL11.glVertex2d((double)((float)xStart + (float)(i + 1) * factor), (double)yHalfEnd);
                GL11.glColor4f((float)rn, (float)gn, (float)bn, (float)0.0f);
                GL11.glVertex2d((double)((float)xStart + (float)(i + 1) * factor), (double)yStart);
                GL11.glColor4f((float)r, (float)g, (float)b, (float)0.0f);
                GL11.glVertex2d((double)((float)xStart + (float)i * factor), (double)yStart);
                ++i;
            }
            GL11.glEnd();
            GL11.glDisable((int)3042);
            GL11.glShadeModel((int)prv);
            GL11.glPopMatrix();
        }
    }

    @Override
    public int getSettingWidth() {
        int w1 = super.getSettingWidth();
        if (w1 < 90) {
            w1 = 100;
        }
        return w1;
    }

    @Override
    public int getSettingHeight() {
        if (this.minimized) {
            return 12;
        }
        return 60;
    }
}

