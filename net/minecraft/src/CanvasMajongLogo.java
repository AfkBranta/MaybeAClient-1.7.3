/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import net.minecraft.src.PanelCrashReport;

class CanvasMajongLogo
extends Canvas {
    private BufferedImage logo;

    public CanvasMajongLogo() {
        try {
            this.logo = ImageIO.read(PanelCrashReport.class.getResource("/gui/logo.png"));
        }
        catch (IOException iOException) {
            // empty catch block
        }
        int var1 = 100;
        this.setPreferredSize(new Dimension(var1, var1));
        this.setMinimumSize(new Dimension(var1, var1));
    }

    @Override
    public void paint(Graphics var1) {
        super.paint(var1);
        var1.drawImage(this.logo, this.getWidth() / 2 - this.logo.getWidth() / 2, 32, null);
    }
}

