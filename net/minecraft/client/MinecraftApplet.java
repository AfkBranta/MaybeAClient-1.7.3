/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Component;
import net.minecraft.client.Minecraft;
import net.minecraft.src.CanvasMinecraftApplet;
import net.minecraft.src.MinecraftAppletImpl;
import net.minecraft.src.Session;

public class MinecraftApplet
extends Applet {
    private Canvas mcCanvas;
    private Minecraft mc;
    private Thread mcThread = null;

    @Override
    public void init() {
        this.mcCanvas = new CanvasMinecraftApplet(this);
        boolean var1 = false;
        if (this.getParameter("fullscreen") != null) {
            var1 = this.getParameter("fullscreen").equalsIgnoreCase("true");
        }
        this.mc = new MinecraftAppletImpl(this, this, this.mcCanvas, this, this.getWidth(), this.getHeight(), var1);
        this.mc.minecraftUri = this.getDocumentBase().getHost();
        if (this.getDocumentBase().getPort() > 0) {
            StringBuilder var10000 = new StringBuilder();
            Minecraft var10002 = this.mc;
            var10002.minecraftUri = var10000.append(var10002.minecraftUri).append(":").append(this.getDocumentBase().getPort()).toString();
        }
        if (this.getParameter("username") != null && this.getParameter("sessionid") != null) {
            this.mc.session = new Session(this.getParameter("username"), this.getParameter("sessionid"));
            System.out.println("Setting user: " + this.mc.session.username + ", " + this.mc.session.sessionId);
            if (this.getParameter("mppass") != null) {
                this.mc.session.mpPassParameter = this.getParameter("mppass");
            }
        } else {
            this.mc.session = new Session("Player", "");
        }
        if (this.getParameter("server") != null && this.getParameter("port") != null) {
            this.mc.setServer(this.getParameter("server"), Integer.parseInt(this.getParameter("port")));
        }
        this.mc.hideQuitButton = true;
        this.setLayout(new BorderLayout());
        this.add((Component)this.mcCanvas, "Center");
        this.mcCanvas.setFocusable(true);
        this.validate();
    }

    public void startMainThread() {
        if (this.mcThread == null) {
            this.mcThread = new Thread((Runnable)this.mc, "Minecraft main thread");
            this.mcThread.start();
        }
    }

    @Override
    public void start() {
        if (this.mc != null) {
            this.mc.isWorldLoaded = false;
        }
    }

    @Override
    public void stop() {
        if (this.mc != null) {
            this.mc.isWorldLoaded = true;
        }
    }

    @Override
    public void destroy() {
        this.shutdown();
    }

    public void shutdown() {
        if (this.mcThread != null) {
            this.mc.shutdown();
            try {
                this.mcThread.join(10000L);
            }
            catch (InterruptedException var4) {
                try {
                    this.mc.shutdownMinecraftApplet();
                }
                catch (Exception var3) {
                    var3.printStackTrace();
                }
            }
            this.mcThread = null;
        }
    }

    public void clearApplet() {
        this.mcCanvas = null;
        this.mc = null;
        this.mcThread = null;
        try {
            this.removeAll();
            this.validate();
        }
        catch (Exception exception) {
            // empty catch block
        }
    }
}

