/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Mouse
 */
package net.minecraft.src;

import java.awt.Component;
import org.lwjgl.input.Mouse;

public class MouseHelper {
    private Component field_1117_c;
    public int deltaX;
    public int deltaY;
    private int field_1115_e = 10;

    public MouseHelper(Component var1) {
        this.field_1117_c = var1;
    }

    public void grabMouseCursor() {
        Mouse.setGrabbed((boolean)true);
        this.deltaX = 0;
        this.deltaY = 0;
    }

    public void ungrabMouseCursor() {
        Mouse.setCursorPosition((int)(this.field_1117_c.getWidth() / 2), (int)(this.field_1117_c.getHeight() / 2));
        Mouse.setGrabbed((boolean)false);
    }

    public void mouseXYChange() {
        this.deltaX = Mouse.getDX();
        this.deltaY = Mouse.getDY();
    }
}

