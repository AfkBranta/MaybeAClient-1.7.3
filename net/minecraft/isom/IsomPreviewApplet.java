/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.isom;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Component;
import net.minecraft.src.CanvasIsomPreview;

public class IsomPreviewApplet
extends Applet {
    private CanvasIsomPreview a = new CanvasIsomPreview();

    public IsomPreviewApplet() {
        this.setLayout(new BorderLayout());
        this.add((Component)this.a, "Center");
    }

    @Override
    public void start() {
        this.a.func_1272_b();
    }

    @Override
    public void stop() {
        this.a.exit();
    }
}

