/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.BufferUtils
 *  org.lwjgl.LWJGLException
 *  org.lwjgl.input.Controllers
 *  org.lwjgl.input.Keyboard
 *  org.lwjgl.input.Mouse
 *  org.lwjgl.opengl.Display
 *  org.lwjgl.opengl.DisplayMode
 *  org.lwjgl.opengl.GL11
 *  org.lwjgl.opengl.PixelFormat
 *  org.lwjgl.util.glu.GLU
 */
package net.minecraft.client;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.io.File;
import java.nio.ByteBuffer;
import lunatrius.schematica.Settings;
import net.minecraft.client.MinecraftApplet;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Block;
import net.minecraft.src.ChunkCoordinates;
import net.minecraft.src.ChunkProviderLoadOrGenerate;
import net.minecraft.src.EffectRenderer;
import net.minecraft.src.EntityClientPlayerMP;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntityPlayerSP;
import net.minecraft.src.EntityRenderer;
import net.minecraft.src.EnumMovingObjectType;
import net.minecraft.src.EnumOS2;
import net.minecraft.src.EnumOptions;
import net.minecraft.src.FontRenderer;
import net.minecraft.src.GLAllocation;
import net.minecraft.src.GameSettings;
import net.minecraft.src.GameWindowListener;
import net.minecraft.src.GuiAchievement;
import net.minecraft.src.GuiChat;
import net.minecraft.src.GuiConflictWarning;
import net.minecraft.src.GuiConnecting;
import net.minecraft.src.GuiContainer;
import net.minecraft.src.GuiGameOver;
import net.minecraft.src.GuiIngame;
import net.minecraft.src.GuiIngameMenu;
import net.minecraft.src.GuiInventory;
import net.minecraft.src.GuiMainMenu;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.GuiSleepMP;
import net.minecraft.src.GuiUnused;
import net.minecraft.src.IChunkProvider;
import net.minecraft.src.ISaveFormat;
import net.minecraft.src.ISaveHandler;
import net.minecraft.src.ItemRenderer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.LoadingScreenRenderer;
import net.minecraft.src.MathHelper;
import net.minecraft.src.MinecraftError;
import net.minecraft.src.MinecraftException;
import net.minecraft.src.MinecraftImpl;
import net.minecraft.src.ModelBiped;
import net.minecraft.src.MouseHelper;
import net.minecraft.src.MovementInputFromOptions;
import net.minecraft.src.MovingObjectPosition;
import net.minecraft.src.NetClientHandler;
import net.minecraft.src.OpenGlCapsChecker;
import net.minecraft.src.PlayerController;
import net.minecraft.src.PlayerControllerTest;
import net.minecraft.src.RenderEngine;
import net.minecraft.src.RenderGlobal;
import net.minecraft.src.RenderManager;
import net.minecraft.src.SaveConverterMcRegion;
import net.minecraft.src.ScaledResolution;
import net.minecraft.src.ScreenShotHelper;
import net.minecraft.src.Session;
import net.minecraft.src.SoundManager;
import net.minecraft.src.StatList;
import net.minecraft.src.Teleporter;
import net.minecraft.src.Tessellator;
import net.minecraft.src.TextureCompassFX;
import net.minecraft.src.TextureFlamesFX;
import net.minecraft.src.TextureLavaFX;
import net.minecraft.src.TextureLavaFlowFX;
import net.minecraft.src.TexturePackList;
import net.minecraft.src.TexturePortalFX;
import net.minecraft.src.TextureWatchFX;
import net.minecraft.src.TextureWaterFX;
import net.minecraft.src.TexureWaterFlowFX;
import net.minecraft.src.ThreadDownloadResources;
import net.minecraft.src.ThreadSleepForever;
import net.minecraft.src.Timer;
import net.minecraft.src.UnexpectedThrowable;
import net.minecraft.src.Vec3D;
import net.minecraft.src.World;
import net.minecraft.src.WorldProvider;
import net.minecraft.src.WorldProviderHell;
import net.minecraft.src.WorldRenderer;
import net.minecraft.src.XBlah;
import net.skidcode.gh.maybeaclient.Client;
import net.skidcode.gh.maybeaclient.IngameHook;
import net.skidcode.gh.maybeaclient.hacks.AutoMouseClickHack;
import net.skidcode.gh.maybeaclient.hacks.CombatLogHack;
import net.skidcode.gh.maybeaclient.hacks.FastPlaceHack;
import net.skidcode.gh.maybeaclient.hacks.Hack;
import net.skidcode.gh.maybeaclient.hacks.InventoryWalkHack;
import net.skidcode.gh.maybeaclient.utils.PlayerUtils;
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Controllers;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;
import org.lwjgl.util.glu.GLU;

public abstract class Minecraft
implements Runnable {
    public static Minecraft theMinecraft;
    public PlayerController playerController;
    private boolean fullscreen = false;
    public int displayWidth;
    public int prevWidth;
    public int displayHeight;
    public int prevHeight;
    private OpenGlCapsChecker glCapabilities;
    public Timer timer = new Timer(20.0f);
    public World theWorld;
    public RenderGlobal renderGlobal;
    public EntityPlayerSP thePlayer;
    public EntityLiving renderViewEntity;
    public EffectRenderer effectRenderer;
    public Session session = null;
    public String minecraftUri;
    public Canvas mcCanvas;
    public boolean hideQuitButton = true;
    public volatile boolean isWorldLoaded = false;
    public RenderEngine renderEngine;
    public FontRenderer fontRenderer;
    public GuiScreen currentScreen = null;
    public LoadingScreenRenderer loadingScreen = new LoadingScreenRenderer(this);
    public EntityRenderer entityRenderer = new EntityRenderer(this);
    private ThreadDownloadResources downloadResourcesThread;
    private int ticksRan = 0;
    private int field_6282_S = 0;
    private int field_9236_T;
    private int field_9235_U;
    public GuiAchievement field_25002_t = new GuiAchievement(this);
    public GuiIngame ingameGUI;
    public boolean field_6307_v = false;
    public ModelBiped field_9242_w = new ModelBiped(0.0f);
    public MovingObjectPosition objectMouseOver = null;
    public GameSettings gameSettings;
    protected MinecraftApplet mcApplet;
    public SoundManager sndManager = new SoundManager();
    public MouseHelper mouseHelper;
    public TexturePackList texturePackList;
    private File mcDataDir;
    private ISaveFormat saveLoader;
    public static long[] frameTimes;
    public static long[] tickTimes;
    public static int numRecordedFrameTimes;
    public XBlah field_25001_G = new XBlah();
    private String serverName;
    private int serverPort;
    private TextureWaterFX textureWaterFX = new TextureWaterFX();
    private TextureLavaFX textureLavaFX = new TextureLavaFX();
    private static File minecraftDir;
    public volatile boolean running = true;
    public String debug = "";
    boolean isTakingScreenshot = false;
    long prevFrameTime = -1L;
    public boolean inGameHasFocus = false;
    private int field_6302_aa = 0;
    public boolean isRaining = false;
    long systemTime = System.currentTimeMillis();
    private int field_6300_ab = 0;
    public int fps = 0;

    static {
        frameTimes = new long[512];
        tickTimes = new long[512];
        numRecordedFrameTimes = 0;
        minecraftDir = null;
    }

    public Minecraft(Component var1, Canvas var2, MinecraftApplet var3, int var4, int var5, boolean var6) {
        this.field_9235_U = var5;
        this.fullscreen = var6;
        this.mcApplet = var3;
        new ThreadSleepForever(this, "Timer hack thread");
        this.mcCanvas = var2;
        this.displayWidth = var4;
        this.displayHeight = var5;
        this.fullscreen = var6;
        if (var3 == null || "true".equals(var3.getParameter("stand-alone"))) {
            this.hideQuitButton = false;
        }
        theMinecraft = this;
        Hack.mc = this;
        Client.mc = this;
        Settings.instance().minecraft = this;
        PlayerUtils.mc = this;
        IngameHook.mc = this;
    }

    public abstract void displayUnexpectedThrowable(UnexpectedThrowable var1);

    public void setServer(String var1, int var2) {
        this.serverName = var1;
        this.serverPort = var2;
    }

    public void startGame() throws LWJGLException {
        if (this.mcCanvas != null) {
            Graphics var1 = this.mcCanvas.getGraphics();
            if (var1 != null) {
                var1.setColor(Color.BLACK);
                var1.fillRect(0, 0, this.displayWidth, this.displayHeight);
                var1.dispose();
            }
            Display.setParent((Canvas)this.mcCanvas);
        } else if (this.fullscreen) {
            Display.setFullscreen((boolean)true);
            this.displayWidth = Display.getDisplayMode().getWidth();
            this.displayHeight = Display.getDisplayMode().getHeight();
            if (this.displayWidth <= 0) {
                this.displayWidth = 1;
            }
            if (this.displayHeight <= 0) {
                this.displayHeight = 1;
            }
        } else {
            Display.setDisplayMode((DisplayMode)new DisplayMode(this.displayWidth, this.displayHeight));
        }
        Display.setTitle((String)"Minecraft Minecraft Beta 1.4_01");
        PixelFormat pf = new PixelFormat().withStencilBits(8);
        try {
            Display.create((PixelFormat)pf);
        }
        catch (LWJGLException var6) {
            var6.printStackTrace();
            try {
                Thread.sleep(1000L);
            }
            catch (InterruptedException interruptedException) {
                // empty catch block
            }
            Display.create((PixelFormat)pf);
        }
        RenderManager.instance.itemRenderer = new ItemRenderer(this);
        this.mcDataDir = Minecraft.getMinecraftDir();
        this.saveLoader = new SaveConverterMcRegion(new File(this.mcDataDir, "saves"));
        this.gameSettings = new GameSettings(this, this.mcDataDir);
        this.texturePackList = new TexturePackList(this, this.mcDataDir);
        this.renderEngine = new RenderEngine(this.texturePackList, this.gameSettings);
        this.fontRenderer = new FontRenderer(this.gameSettings, "/font/default.png", this.renderEngine);
        this.loadScreen();
        Keyboard.create();
        Mouse.create();
        this.mouseHelper = new MouseHelper(this.mcCanvas);
        try {
            Controllers.create();
        }
        catch (Exception var4) {
            var4.printStackTrace();
        }
        this.checkGLError("Pre startup");
        GL11.glEnable((int)3553);
        GL11.glShadeModel((int)7425);
        GL11.glClearDepth((double)1.0);
        GL11.glEnable((int)2929);
        GL11.glDepthFunc((int)515);
        GL11.glEnable((int)3008);
        GL11.glAlphaFunc((int)516, (float)0.1f);
        GL11.glCullFace((int)1029);
        GL11.glMatrixMode((int)5889);
        GL11.glLoadIdentity();
        GL11.glMatrixMode((int)5888);
        this.checkGLError("Startup");
        this.glCapabilities = new OpenGlCapsChecker();
        this.sndManager.loadSoundSettings(this.gameSettings);
        this.renderEngine.registerTextureFX(this.textureLavaFX);
        this.renderEngine.registerTextureFX(this.textureWaterFX);
        this.renderEngine.registerTextureFX(new TexturePortalFX());
        this.renderEngine.registerTextureFX(new TextureCompassFX(this));
        this.renderEngine.registerTextureFX(new TextureWatchFX(this));
        this.renderEngine.registerTextureFX(new TexureWaterFlowFX());
        this.renderEngine.registerTextureFX(new TextureLavaFlowFX());
        this.renderEngine.registerTextureFX(new TextureFlamesFX(0));
        this.renderEngine.registerTextureFX(new TextureFlamesFX(1));
        this.renderGlobal = new RenderGlobal(this, this.renderEngine);
        GL11.glViewport((int)0, (int)0, (int)this.displayWidth, (int)this.displayHeight);
        this.effectRenderer = new EffectRenderer(this.theWorld, this.renderEngine);
        try {
            this.downloadResourcesThread = new ThreadDownloadResources(this.mcDataDir, this);
            this.downloadResourcesThread.start();
        }
        catch (Exception exception) {
            // empty catch block
        }
        this.checkGLError("Post startup");
        this.ingameGUI = new GuiIngame(this);
        if (this.serverName != null) {
            this.displayGuiScreen(new GuiConnecting(this, this.serverName, this.serverPort));
        } else {
            this.displayGuiScreen(new GuiMainMenu());
        }
    }

    private void loadScreen() throws LWJGLException {
        ScaledResolution var1 = new ScaledResolution(this.gameSettings, this.displayWidth, this.displayHeight);
        GL11.glClear((int)16640);
        GL11.glMatrixMode((int)5889);
        GL11.glLoadIdentity();
        GL11.glOrtho((double)0.0, (double)var1.field_25121_a, (double)var1.field_25120_b, (double)0.0, (double)1000.0, (double)3000.0);
        GL11.glMatrixMode((int)5888);
        GL11.glLoadIdentity();
        GL11.glTranslatef((float)0.0f, (float)0.0f, (float)-2000.0f);
        GL11.glViewport((int)0, (int)0, (int)this.displayWidth, (int)this.displayHeight);
        GL11.glClearColor((float)0.0f, (float)0.0f, (float)0.0f, (float)0.0f);
        Tessellator var2 = Tessellator.instance;
        GL11.glDisable((int)2896);
        GL11.glEnable((int)3553);
        GL11.glDisable((int)2912);
        GL11.glBindTexture((int)3553, (int)this.renderEngine.getTexture("/title/mojang.png"));
        var2.startDrawingQuads();
        var2.setColorOpaque_I(0xFFFFFF);
        var2.addVertexWithUV(0.0, this.displayHeight, 0.0, 0.0, 0.0);
        var2.addVertexWithUV(this.displayWidth, this.displayHeight, 0.0, 0.0, 0.0);
        var2.addVertexWithUV(this.displayWidth, 0.0, 0.0, 0.0, 0.0);
        var2.addVertexWithUV(0.0, 0.0, 0.0, 0.0, 0.0);
        var2.draw();
        int var3 = 256;
        int var4 = 256;
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        var2.setColorOpaque_I(0xFFFFFF);
        this.func_6274_a((this.displayWidth / 2 - var3) / 2, (this.displayHeight / 2 - var4) / 2, 0, 0, var3, var4);
        GL11.glDisable((int)2896);
        GL11.glDisable((int)2912);
        GL11.glEnable((int)3008);
        GL11.glAlphaFunc((int)516, (float)0.1f);
        Display.swapBuffers();
    }

    public void func_6274_a(int var1, int var2, int var3, int var4, int var5, int var6) {
        float var7 = 0.00390625f;
        float var8 = 0.00390625f;
        Tessellator var9 = Tessellator.instance;
        var9.startDrawingQuads();
        var9.addVertexWithUV(var1 + 0, var2 + var6, 0.0, (float)(var3 + 0) * var7, (float)(var4 + var6) * var8);
        var9.addVertexWithUV(var1 + var5, var2 + var6, 0.0, (float)(var3 + var5) * var7, (float)(var4 + var6) * var8);
        var9.addVertexWithUV(var1 + var5, var2 + 0, 0.0, (float)(var3 + var5) * var7, (float)(var4 + 0) * var8);
        var9.addVertexWithUV(var1 + 0, var2 + 0, 0.0, (float)(var3 + 0) * var7, (float)(var4 + 0) * var8);
        var9.draw();
    }

    public static File getMinecraftDir() {
        if (minecraftDir == null) {
            minecraftDir = Minecraft.getAppDir("minecraft");
        }
        return minecraftDir;
    }

    public static File getAppDir(String var0) {
        File var2;
        String var1 = System.getProperty("user.home", ".");
        switch (Minecraft.getOs()) {
            case linux: 
            case solaris: {
                var2 = new File(var1, String.valueOf('.') + var0 + '/');
                break;
            }
            case windows: {
                String var3 = System.getenv("APPDATA");
                if (var3 != null) {
                    var2 = new File(var3, "." + var0 + '/');
                    break;
                }
                var2 = new File(var1, String.valueOf('.') + var0 + '/');
                break;
            }
            case macos: {
                var2 = new File(var1, "Library/Application Support/" + var0);
                break;
            }
            default: {
                var2 = new File(var1, String.valueOf(var0) + '/');
            }
        }
        if (!var2.exists() && !var2.mkdirs()) {
            throw new RuntimeException("The working directory could not be created: " + var2);
        }
        return var2;
    }

    private static EnumOS2 getOs() {
        String var0 = System.getProperty("os.name").toLowerCase();
        if (var0.contains("win")) {
            return EnumOS2.windows;
        }
        if (var0.contains("mac")) {
            return EnumOS2.macos;
        }
        if (var0.contains("solaris")) {
            return EnumOS2.solaris;
        }
        if (var0.contains("sunos")) {
            return EnumOS2.solaris;
        }
        if (var0.contains("linux")) {
            return EnumOS2.linux;
        }
        return var0.contains("unix") ? EnumOS2.linux : EnumOS2.unknown;
    }

    public ISaveFormat getSaveLoader() {
        return this.saveLoader;
    }

    public void displayGuiScreen(GuiScreen var1) {
        if (!(this.currentScreen instanceof GuiUnused)) {
            if (this.currentScreen != null) {
                this.currentScreen.onGuiClosed();
            }
            if (var1 == null && this.theWorld == null) {
                var1 = new GuiMainMenu();
            } else if (var1 == null && this.thePlayer.health <= 0) {
                var1 = new GuiGameOver();
            }
            this.currentScreen = var1;
            if (var1 != null) {
                this.setIngameNotInFocus();
                ScaledResolution var2 = new ScaledResolution(this.gameSettings, this.displayWidth, this.displayHeight);
                int var3 = var2.getScaledWidth();
                int var4 = var2.getScaledHeight();
                var1.setWorldAndResolution(this, var3, var4);
                this.field_6307_v = false;
            } else {
                this.setIngameFocus();
            }
        }
    }

    private void checkGLError(String var1) {
        int var2 = GL11.glGetError();
        if (var2 != 0) {
            String var3 = GLU.gluErrorString((int)var2);
            System.out.println("########## GL ERROR ##########");
            System.out.println("@ " + var1);
            System.out.println(String.valueOf(var2) + ": " + var3);
            System.exit(0);
        }
    }

    public void shutdownMinecraftApplet() {
        if (this.mcApplet != null) {
            this.mcApplet.clearApplet();
        }
        try {
            if (this.downloadResourcesThread != null) {
                this.downloadResourcesThread.closeMinecraft();
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        try {
            System.out.println("Stopping!");
            try {
                this.changeWorld1(null);
            }
            catch (Throwable throwable) {
                // empty catch block
            }
            try {
                GLAllocation.deleteTexturesAndDisplayLists();
            }
            catch (Throwable throwable) {
                // empty catch block
            }
            this.sndManager.closeMinecraft();
            Mouse.destroy();
            Keyboard.destroy();
        }
        finally {
            Display.destroy();
            System.exit(0);
        }
        System.gc();
    }

    /*
     * Unable to fully structure code
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public void run() {
        this.running = true;
        try {
            this.startGame();
        }
        catch (Exception var15) {
            var15.printStackTrace();
            this.displayUnexpectedThrowable(new UnexpectedThrowable("Failed to start game", var15));
            return;
        }
        try {
            try {
                var1 = System.currentTimeMillis();
                var3 = 0;
                if (true) ** GOTO lbl89
                do {
                    AxisAlignedBB.clearBoundingBoxPool();
                    Vec3D.initialize();
                    if (this.mcCanvas == null && Display.isCloseRequested()) {
                        this.shutdown();
                    }
                    if (this.isWorldLoaded && this.theWorld != null) {
                        var4 = this.timer.renderPartialTicks;
                        this.timer.updateTimer();
                        this.timer.renderPartialTicks = var4;
                    } else {
                        this.timer.updateTimer();
                    }
                    var19 = System.nanoTime();
                    var6 = 0;
                    while (var6 < this.timer.elapsedTicks) {
                        ++this.ticksRan;
                        try {
                            this.runTick();
                        }
                        catch (MinecraftException var14) {
                            this.theWorld = null;
                            this.changeWorld1(null);
                            this.displayGuiScreen(new GuiConflictWarning());
                        }
                        ++var6;
                    }
                    var20 = System.nanoTime() - var19;
                    this.checkGLError("Pre render");
                    this.sndManager.func_338_a(this.thePlayer, this.timer.renderPartialTicks);
                    GL11.glEnable((int)3553);
                    if (this.theWorld != null && !this.theWorld.multiplayerWorld) {
                        this.theWorld.func_6465_g();
                    }
                    if (this.theWorld != null && this.theWorld.multiplayerWorld) {
                        this.theWorld.func_6465_g();
                    }
                    if (this.gameSettings.limitFramerate) {
                        Thread.sleep(5L);
                    }
                    if (!Keyboard.isKeyDown((int)65)) {
                        Display.update();
                    }
                    if (!this.field_6307_v) {
                        if (this.playerController != null) {
                            this.playerController.setPartialTime(this.timer.renderPartialTicks);
                        }
                        this.entityRenderer.updateCameraAndRender(this.timer.renderPartialTicks);
                    }
                    if (!Display.isActive()) {
                        if (this.fullscreen) {
                            this.toggleFullscreen();
                        }
                        Thread.sleep(10L);
                    }
                    if (this.gameSettings.showDebugInfo) {
                        this.displayDebugInfo(var20);
                    } else {
                        this.prevFrameTime = System.nanoTime();
                    }
                    this.field_25002_t.func_25080_a();
                    Thread.yield();
                    if (Keyboard.isKeyDown((int)65)) {
                        Display.update();
                    }
                    this.screenshotListener();
                    if (!(this.mcCanvas == null || this.fullscreen || this.mcCanvas.getWidth() == this.displayWidth && this.mcCanvas.getHeight() == this.displayHeight)) {
                        this.prevWidth = this.displayWidth;
                        this.prevHeight = this.displayHeight;
                        this.displayWidth = this.mcCanvas.getWidth();
                        this.displayHeight = this.mcCanvas.getHeight();
                        if (this.displayWidth <= 0) {
                            this.displayWidth = 1;
                        }
                        if (this.displayHeight <= 0) {
                            this.displayHeight = 1;
                        }
                        this.resize(this.displayWidth, this.displayHeight);
                    }
                    this.checkGLError("Post render");
                    ++var3;
                    this.isWorldLoaded = this.isMultiplayerWorld() == false && this.currentScreen != null && this.currentScreen.doesGuiPauseGame() != false;
                    while (System.currentTimeMillis() >= var1 + 1000L) {
                        this.debug = String.valueOf(var3) + " fps, " + WorldRenderer.chunksUpdated + " chunk updates";
                        this.fps = var3;
                        WorldRenderer.chunksUpdated = 0;
                        var1 += 1000L;
                        var3 = 0;
                    }
lbl89:
                    // 2 sources

                    if (!this.running) return;
                } while (this.mcApplet == null || this.mcApplet.isActive());
                return;
            }
            catch (MinecraftError var1) {
                this.shutdownMinecraftApplet();
                return;
            }
            catch (Throwable var17) {
                this.theWorld = null;
                var17.printStackTrace();
                this.displayUnexpectedThrowable(new UnexpectedThrowable("Unexpected error", var17));
                this.shutdownMinecraftApplet();
                return;
            }
        }
        finally {
            this.shutdownMinecraftApplet();
        }
    }

    private void screenshotListener() {
        if (Keyboard.isKeyDown((int)60)) {
            if (!this.isTakingScreenshot) {
                this.isTakingScreenshot = true;
                this.ingameGUI.addChatMessage(ScreenShotHelper.saveScreenshot(minecraftDir, this.displayWidth, this.displayHeight));
            }
        } else {
            this.isTakingScreenshot = false;
        }
    }

    private String func_21001_a(File var1, int var2, int var3, int var4, int var5) {
        try {
            ByteBuffer var6 = BufferUtils.createByteBuffer((int)(var2 * var3 * 3));
            ScreenShotHelper var7 = new ScreenShotHelper(var1, var4, var5, var3);
            double var8 = (double)var4 / (double)var2;
            double var10 = (double)var5 / (double)var3;
            double var12 = var8 > var10 ? var8 : var10;
            int var14 = (var5 - 1) / var3 * var3;
            while (var14 >= 0) {
                int var15 = 0;
                while (var15 < var4) {
                    GL11.glBindTexture((int)3553, (int)this.renderEngine.getTexture("/terrain.png"));
                    double var18 = (double)(var4 - var2) / 2.0 * 2.0 - (double)(var15 * 2);
                    double var20 = (double)(var5 - var3) / 2.0 * 2.0 - (double)(var14 * 2);
                    this.entityRenderer.func_21152_a(var12, var18 /= (double)var2, var20 /= (double)var3);
                    this.entityRenderer.renderWorld(1.0f);
                    this.entityRenderer.resetZoom();
                    Display.update();
                    try {
                        Thread.sleep(10L);
                    }
                    catch (InterruptedException var23) {
                        var23.printStackTrace();
                    }
                    Display.update();
                    var6.clear();
                    GL11.glPixelStorei((int)3333, (int)1);
                    GL11.glPixelStorei((int)3317, (int)1);
                    GL11.glReadPixels((int)0, (int)0, (int)var2, (int)var3, (int)32992, (int)5121, (ByteBuffer)var6);
                    var7.func_21189_a(var6, var15, var14, var2, var3);
                    var15 += var2;
                }
                var7.func_21191_a();
                var14 -= var3;
            }
            return var7.func_21190_b();
        }
        catch (Exception var24) {
            var24.printStackTrace();
            return "Failed to save image: " + var24;
        }
    }

    private void displayDebugInfo(long var1) {
        long var3 = 16666666L;
        if (this.prevFrameTime == -1L) {
            this.prevFrameTime = System.nanoTime();
        }
        long var5 = System.nanoTime();
        Minecraft.tickTimes[Minecraft.numRecordedFrameTimes & Minecraft.frameTimes.length - 1] = var1;
        Minecraft.frameTimes[Minecraft.numRecordedFrameTimes++ & Minecraft.frameTimes.length - 1] = var5 - this.prevFrameTime;
        this.prevFrameTime = var5;
        GL11.glClear((int)256);
        GL11.glMatrixMode((int)5889);
        GL11.glLoadIdentity();
        GL11.glOrtho((double)0.0, (double)this.displayWidth, (double)this.displayHeight, (double)0.0, (double)1000.0, (double)3000.0);
        GL11.glMatrixMode((int)5888);
        GL11.glLoadIdentity();
        GL11.glTranslatef((float)0.0f, (float)0.0f, (float)-2000.0f);
        GL11.glLineWidth((float)1.0f);
        GL11.glDisable((int)3553);
        Tessellator var7 = Tessellator.instance;
        var7.startDrawing(7);
        int var8 = (int)(var3 / 200000L);
        var7.setColorOpaque_I(0x20000000);
        var7.addVertex(0.0, this.displayHeight - var8, 0.0);
        var7.addVertex(0.0, this.displayHeight, 0.0);
        var7.addVertex(frameTimes.length, this.displayHeight, 0.0);
        var7.addVertex(frameTimes.length, this.displayHeight - var8, 0.0);
        var7.setColorOpaque_I(0x20200000);
        var7.addVertex(0.0, this.displayHeight - var8 * 2, 0.0);
        var7.addVertex(0.0, this.displayHeight - var8, 0.0);
        var7.addVertex(frameTimes.length, this.displayHeight - var8, 0.0);
        var7.addVertex(frameTimes.length, this.displayHeight - var8 * 2, 0.0);
        var7.draw();
        long var9 = 0L;
        int var11 = 0;
        while (var11 < frameTimes.length) {
            var9 += frameTimes[var11];
            ++var11;
        }
        var11 = (int)(var9 / 200000L / (long)frameTimes.length);
        var7.startDrawing(7);
        var7.setColorOpaque_I(0x20400000);
        var7.addVertex(0.0, this.displayHeight - var11, 0.0);
        var7.addVertex(0.0, this.displayHeight, 0.0);
        var7.addVertex(frameTimes.length, this.displayHeight, 0.0);
        var7.addVertex(frameTimes.length, this.displayHeight - var11, 0.0);
        var7.draw();
        var7.startDrawing(1);
        int var12 = 0;
        while (var12 < frameTimes.length) {
            int var13 = (var12 - numRecordedFrameTimes & frameTimes.length - 1) * 255 / frameTimes.length;
            int var14 = var13 * var13 / 255;
            var14 = var14 * var14 / 255;
            int var15 = var14 * var14 / 255;
            var15 = var15 * var15 / 255;
            if (frameTimes[var12] > var3) {
                var7.setColorOpaque_I(-16777216 + var14 * 65536);
            } else {
                var7.setColorOpaque_I(-16777216 + var14 * 256);
            }
            long var16 = frameTimes[var12] / 200000L;
            long var18 = tickTimes[var12] / 200000L;
            var7.addVertex((float)var12 + 0.5f, (float)((long)this.displayHeight - var16) + 0.5f, 0.0);
            var7.addVertex((float)var12 + 0.5f, (float)this.displayHeight + 0.5f, 0.0);
            var7.setColorOpaque_I(-16777216 + var14 * 65536 + var14 * 256 + var14 * 1);
            var7.addVertex((float)var12 + 0.5f, (float)((long)this.displayHeight - var16) + 0.5f, 0.0);
            var7.addVertex((float)var12 + 0.5f, (float)((long)this.displayHeight - (var16 - var18)) + 0.5f, 0.0);
            ++var12;
        }
        var7.draw();
        GL11.glEnable((int)3553);
    }

    public void shutdown() {
        this.running = false;
    }

    public void setIngameFocus() {
        if (Display.isActive() && !this.inGameHasFocus) {
            this.inGameHasFocus = true;
            this.mouseHelper.grabMouseCursor();
            this.displayGuiScreen(null);
            this.field_6302_aa = this.ticksRan + 10000;
        }
    }

    public void setIngameNotInFocus() {
        if (this.inGameHasFocus) {
            if (!(this.thePlayer == null || InventoryWalkHack.instance.status && this.currentScreen instanceof GuiContainer)) {
                this.thePlayer.resetPlayerKeyState();
            }
            this.inGameHasFocus = false;
            this.mouseHelper.ungrabMouseCursor();
        }
    }

    public void displayInGameMenu() {
        if (this.currentScreen == null) {
            this.displayGuiScreen(new GuiIngameMenu());
        }
    }

    private void func_6254_a(int var1, boolean var2) {
        if (!(this.playerController.field_1064_b || var1 == 0 && this.field_6282_S > 0)) {
            if (var2 && this.objectMouseOver != null && this.objectMouseOver.typeOfHit == EnumMovingObjectType.TILE && var1 == 0) {
                int var3 = this.objectMouseOver.blockX;
                int var4 = this.objectMouseOver.blockY;
                int var5 = this.objectMouseOver.blockZ;
                this.playerController.sendBlockRemoving(var3, var4, var5, this.objectMouseOver.sideHit);
                this.effectRenderer.addBlockHitEffects(var3, var4, var5, this.objectMouseOver.sideHit);
            } else {
                this.playerController.func_6468_a();
            }
        }
    }

    private void clickMouse(int var1) {
        if (var1 != 0 || this.field_6282_S <= 0) {
            ItemStack var10;
            if (var1 == 0) {
                this.thePlayer.swingItem();
            }
            boolean var2 = true;
            if (this.objectMouseOver == null) {
                if (var1 == 0 && !(this.playerController instanceof PlayerControllerTest)) {
                    this.field_6282_S = 10;
                }
            } else if (this.objectMouseOver.typeOfHit == EnumMovingObjectType.ENTITY) {
                if (var1 == 0) {
                    this.playerController.func_6472_b(this.thePlayer, this.objectMouseOver.entityHit);
                }
                if (var1 == 1) {
                    this.playerController.func_6475_a(this.thePlayer, this.objectMouseOver.entityHit);
                }
            } else if (this.objectMouseOver.typeOfHit == EnumMovingObjectType.TILE) {
                int var3 = this.objectMouseOver.blockX;
                int var4 = this.objectMouseOver.blockY;
                int var5 = this.objectMouseOver.blockZ;
                int var6 = this.objectMouseOver.sideHit;
                Block var7 = Block.blocksList[this.theWorld.getBlockId(var3, var4, var5)];
                if (var1 == 0) {
                    this.theWorld.onBlockHit(var3, var4, var5, this.objectMouseOver.sideHit);
                    if (var7 != Block.bedrock || this.thePlayer.field_9371_f >= 100) {
                        this.playerController.clickBlock(var3, var4, var5, this.objectMouseOver.sideHit);
                    }
                } else {
                    int var9;
                    ItemStack var8 = this.thePlayer.inventory.getCurrentItem();
                    int n = var9 = var8 != null ? var8.stackSize : 0;
                    if (this.playerController.sendPlaceBlock(this.thePlayer, this.theWorld, var8, var3, var4, var5, var6)) {
                        var2 = false;
                        this.thePlayer.swingItem();
                    }
                    if (var8 == null) {
                        return;
                    }
                    if (var8.stackSize == 0) {
                        this.thePlayer.inventory.mainInventory[this.thePlayer.inventory.currentItem] = null;
                    } else if (var8.stackSize != var9) {
                        this.entityRenderer.itemRenderer.func_9449_b();
                    }
                }
            }
            if (var2 && var1 == 1 && (var10 = this.thePlayer.inventory.getCurrentItem()) != null && this.playerController.sendUseItem(this.thePlayer, this.theWorld, var10)) {
                this.entityRenderer.itemRenderer.func_9450_c();
            }
        }
    }

    public void toggleFullscreen() {
        try {
            this.fullscreen = !this.fullscreen;
            System.out.println("Toggle fullscreen!");
            if (this.fullscreen) {
                Display.setDisplayMode((DisplayMode)Display.getDesktopDisplayMode());
                this.displayWidth = Display.getDisplayMode().getWidth();
                this.displayHeight = Display.getDisplayMode().getHeight();
                if (this.displayWidth <= 0) {
                    this.displayWidth = 1;
                }
                if (this.displayHeight <= 0) {
                    this.displayHeight = 1;
                }
            } else {
                if (this.mcCanvas != null) {
                    this.displayWidth = this.mcCanvas.getWidth();
                    this.displayHeight = this.mcCanvas.getHeight();
                } else {
                    this.displayWidth = this.field_9236_T;
                    this.displayHeight = this.field_9235_U;
                }
                if (this.displayWidth <= 0) {
                    this.displayWidth = 1;
                }
                if (this.displayHeight <= 0) {
                    this.displayHeight = 1;
                }
                Display.setDisplayMode((DisplayMode)new DisplayMode(this.field_9236_T, this.field_9235_U));
            }
            this.setIngameNotInFocus();
            Display.setFullscreen((boolean)this.fullscreen);
            Display.update();
            Thread.sleep(1000L);
            if (this.fullscreen) {
                this.setIngameFocus();
            }
            if (this.currentScreen != null) {
                this.setIngameNotInFocus();
                this.resize(this.displayWidth, this.displayHeight);
            }
            System.out.println("Size: " + this.displayWidth + ", " + this.displayHeight);
        }
        catch (Exception var2) {
            var2.printStackTrace();
        }
    }

    private void resize(int var1, int var2) {
        if (var1 <= 0) {
            var1 = 1;
        }
        if (var2 <= 0) {
            var2 = 1;
        }
        this.displayWidth = var1;
        this.displayHeight = var2;
        if (this.currentScreen != null) {
            ScaledResolution var3 = new ScaledResolution(this.gameSettings, var1, var2);
            int var4 = var3.getScaledWidth();
            int var5 = var3.getScaledHeight();
            this.currentScreen.setWorldAndResolution(this, var4, var5);
        }
    }

    private void clickMiddleMouseButton() {
        if (this.objectMouseOver != null) {
            int var1 = this.theWorld.getBlockId(this.objectMouseOver.blockX, this.objectMouseOver.blockY, this.objectMouseOver.blockZ);
            if (var1 == Block.grass.blockID) {
                var1 = Block.dirt.blockID;
            }
            if (var1 == Block.stairDouble.blockID) {
                var1 = Block.stairSingle.blockID;
            }
            if (var1 == Block.bedrock.blockID) {
                var1 = Block.stone.blockID;
            }
            this.thePlayer.inventory.setCurrentItem(var1, this.playerController instanceof PlayerControllerTest);
        }
    }

    public void runTick() {
        int var3;
        IChunkProvider var1;
        this.ingameGUI.updateTick();
        this.entityRenderer.getMouseOver(1.0f);
        if (this.thePlayer != null && (var1 = this.theWorld.getIChunkProvider()) instanceof ChunkProviderLoadOrGenerate) {
            ChunkProviderLoadOrGenerate var2 = (ChunkProviderLoadOrGenerate)var1;
            var3 = MathHelper.floor_float((int)this.thePlayer.posX) >> 4;
            int var4 = MathHelper.floor_float((int)this.thePlayer.posZ) >> 4;
            var2.setCurrentChunkOver(var3, var4);
        }
        if (!this.isWorldLoaded && this.theWorld != null) {
            this.playerController.updateController();
        }
        GL11.glBindTexture((int)3553, (int)this.renderEngine.getTexture("/terrain.png"));
        if (!this.isWorldLoaded) {
            this.renderEngine.func_1067_a();
        }
        if (this.currentScreen == null && this.thePlayer != null) {
            if (this.thePlayer.health <= 0) {
                this.displayGuiScreen(null);
            } else if (this.thePlayer.isPlayerSleeping() && this.theWorld != null && this.theWorld.multiplayerWorld) {
                this.displayGuiScreen(new GuiSleepMP());
            }
        } else if (this.currentScreen != null && this.currentScreen instanceof GuiSleepMP && !this.thePlayer.isPlayerSleeping()) {
            this.displayGuiScreen(null);
        }
        if (this.currentScreen != null) {
            this.field_6302_aa = this.ticksRan + 10000;
        }
        if (this.currentScreen != null) {
            this.currentScreen.handleInput();
            if (this.currentScreen != null) {
                this.currentScreen.field_25091_h.func_25088_a();
                this.currentScreen.updateScreen();
            }
        }
        if (this.currentScreen == null || this.currentScreen.field_948_f) {
            block0: while (true) {
                if (!Mouse.next()) {
                    if (this.field_6282_S > 0) {
                        --this.field_6282_S;
                    }
                    while (true) {
                        if (!Keyboard.next()) {
                            if (this.currentScreen == null) {
                                if (Mouse.isButtonDown((int)0) && (float)(this.ticksRan - this.field_6302_aa) >= this.timer.ticksPerSecond / 4.0f && this.inGameHasFocus) {
                                    this.clickMouse(0);
                                    this.field_6302_aa = this.ticksRan;
                                }
                                if (Mouse.isButtonDown((int)1) && (FastPlaceHack.instance.status || (float)(this.ticksRan - this.field_6302_aa) >= this.timer.ticksPerSecond / 4.0f) && this.inGameHasFocus) {
                                    this.clickMouse(1);
                                    this.field_6302_aa = this.ticksRan;
                                }
                            }
                            break block0;
                        }
                        this.thePlayer.handleKeyPress(Keyboard.getEventKey(), Keyboard.getEventKeyState());
                        if (!Keyboard.getEventKeyState()) continue;
                        if (Keyboard.getEventKey() == 87) {
                            this.toggleFullscreen();
                            continue;
                        }
                        if (this.currentScreen != null) {
                            this.currentScreen.handleKeyboardInput();
                        } else {
                            if (Keyboard.getEventKey() == 1) {
                                this.displayInGameMenu();
                            }
                            if (Keyboard.getEventKey() == 31 && Keyboard.isKeyDown((int)61)) {
                                this.forceReload();
                            }
                            if (Keyboard.getEventKey() == 59) {
                                boolean bl = this.gameSettings.hideGUI = !this.gameSettings.hideGUI;
                            }
                            if (Keyboard.getEventKey() == 61) {
                                boolean bl = this.gameSettings.showDebugInfo = !this.gameSettings.showDebugInfo;
                            }
                            if (Keyboard.getEventKey() == 63) {
                                boolean bl = this.gameSettings.thirdPersonView = !this.gameSettings.thirdPersonView;
                            }
                            if (Keyboard.getEventKey() == 66) {
                                boolean bl = this.gameSettings.smoothCamera = !this.gameSettings.smoothCamera;
                            }
                            if (Keyboard.getEventKey() == this.gameSettings.keyBindInventory.keyCode) {
                                this.displayGuiScreen(new GuiInventory(this.thePlayer));
                            }
                            if (Keyboard.getEventKey() == this.gameSettings.keyBindDrop.keyCode) {
                                this.thePlayer.dropCurrentItem();
                            }
                            if (Keyboard.getEventKey() == this.gameSettings.keyBindChat.keyCode) {
                                this.displayGuiScreen(new GuiChat());
                            }
                        }
                        int var6 = 0;
                        while (var6 < 9) {
                            if (Keyboard.getEventKey() == 2 + var6) {
                                this.thePlayer.inventory.currentItem = var6;
                            }
                            ++var6;
                        }
                        if (Keyboard.getEventKey() != this.gameSettings.keyBindToggleFog.keyCode) continue;
                        this.gameSettings.setOptionValue(EnumOptions.RENDER_DISTANCE, !Keyboard.isKeyDown((int)42) && !Keyboard.isKeyDown((int)54) ? 1 : -1);
                    }
                }
                long var5 = System.currentTimeMillis() - this.systemTime;
                if (var5 > 200L) continue;
                var3 = Mouse.getEventDWheel();
                if (var3 != 0) {
                    this.thePlayer.inventory.changeCurrentItem(var3);
                    if (this.gameSettings.field_22275_C) {
                        if (var3 > 0) {
                            var3 = 1;
                        }
                        if (var3 < 0) {
                            var3 = -1;
                        }
                        GameSettings var10000 = this.gameSettings;
                        var10000.field_22272_F += (float)var3 * 0.25f;
                    }
                }
                if (this.currentScreen == null) {
                    if (!this.inGameHasFocus && Mouse.getEventButtonState()) {
                        this.setIngameFocus();
                        continue;
                    }
                    if (Mouse.getEventButton() == 0 && Mouse.getEventButtonState()) {
                        this.clickMouse(0);
                        this.field_6302_aa = this.ticksRan;
                    }
                    if (Mouse.getEventButton() == 1 && Mouse.getEventButtonState()) {
                        this.clickMouse(1);
                        this.field_6302_aa = this.ticksRan;
                    }
                    if (Mouse.getEventButton() != 2 || !Mouse.getEventButtonState()) continue;
                    this.clickMiddleMouseButton();
                    continue;
                }
                if (this.currentScreen == null) continue;
                this.currentScreen.handleMouseInput();
            }
            this.func_6254_a(0, this.currentScreen == null && (Mouse.isButtonDown((int)0) || AutoMouseClickHack.instance.status && AutoMouseClickHack.instance.left.currentMode.equalsIgnoreCase("Hold")) && this.inGameHasFocus);
            if (AutoMouseClickHack.instance.status) {
                if (!AutoMouseClickHack.instance.left.currentMode.equalsIgnoreCase("Disabled")) {
                    if (AutoMouseClickHack.instance.ldelay <= 0) {
                        if (AutoMouseClickHack.instance.left.currentMode.equalsIgnoreCase("Hold")) {
                            AutoMouseClickHack.instance.ldelay = 0;
                        } else if (AutoMouseClickHack.instance.left.currentMode.equalsIgnoreCase("Click")) {
                            AutoMouseClickHack.instance.ldelay = AutoMouseClickHack.instance.lDelayTicks.value;
                        }
                        this.clickMouse(0);
                        this.field_6302_aa = this.ticksRan;
                        this.func_6254_a(0, this.currentScreen == null && this.inGameHasFocus);
                    } else {
                        --AutoMouseClickHack.instance.ldelay;
                    }
                } else {
                    AutoMouseClickHack.instance.ldelay = 0;
                }
                if (!AutoMouseClickHack.instance.right.currentMode.equalsIgnoreCase("Disabled")) {
                    if (AutoMouseClickHack.instance.rdelay <= 0) {
                        if (AutoMouseClickHack.instance.right.currentMode.equalsIgnoreCase("Hold")) {
                            AutoMouseClickHack.instance.rdelay = 0;
                        } else if (AutoMouseClickHack.instance.right.currentMode.equalsIgnoreCase("Click")) {
                            AutoMouseClickHack.instance.rdelay = AutoMouseClickHack.instance.rDelayTicks.value;
                        }
                        this.clickMouse(1);
                        this.field_6302_aa = this.ticksRan;
                    } else {
                        --AutoMouseClickHack.instance.rdelay;
                    }
                } else {
                    AutoMouseClickHack.instance.rdelay = 0;
                }
            }
        }
        if (this.theWorld != null) {
            if (this.thePlayer != null) {
                ++this.field_6300_ab;
                if (this.field_6300_ab == 30) {
                    this.field_6300_ab = 0;
                    this.theWorld.joinEntityInSurroundings(this.thePlayer);
                }
            }
            this.theWorld.difficultySetting = this.gameSettings.difficulty;
            if (this.theWorld.multiplayerWorld) {
                this.theWorld.difficultySetting = 3;
            }
            if (!this.isWorldLoaded) {
                this.entityRenderer.updateRenderer();
            }
            if (!this.isWorldLoaded) {
                this.renderGlobal.updateClouds();
            }
            if (!this.isWorldLoaded) {
                this.theWorld.func_633_c();
            }
            if (!this.isWorldLoaded || this.isMultiplayerWorld()) {
                this.theWorld.setAllowedMobSpawns(this.gameSettings.difficulty > 0, true);
                this.theWorld.tick();
            }
            if (!this.isWorldLoaded && this.theWorld != null) {
                this.theWorld.randomDisplayUpdates(MathHelper.floor_double(this.thePlayer.posX), MathHelper.floor_double(this.thePlayer.posY), MathHelper.floor_double(this.thePlayer.posZ));
            }
            if (!this.isWorldLoaded) {
                this.effectRenderer.updateEffects();
            }
        }
        if (CombatLogHack.shouldQuit) {
            CombatLogHack.shouldQuit = false;
            this.changeWorld1(null);
            this.displayGuiScreen(new GuiMainMenu());
            if (CombatLogHack.instance.mode.currentMode.equalsIgnoreCase("Health")) {
                CombatLogHack.instance.status = false;
            }
        }
        this.systemTime = System.currentTimeMillis();
    }

    private void forceReload() {
        System.out.println("FORCING RELOAD!");
        this.sndManager = new SoundManager();
        this.sndManager.loadSoundSettings(this.gameSettings);
        this.downloadResourcesThread.reloadResources();
    }

    public boolean isMultiplayerWorld() {
        return this.theWorld != null && this.theWorld.multiplayerWorld;
    }

    public void startWorld(String var1, String var2, long var3) {
        this.changeWorld1(null);
        System.gc();
        if (this.saveLoader.isOldMapFormat(var1)) {
            this.convertMapFormat(var1, var2);
        } else {
            ISaveHandler var5 = this.saveLoader.getSaveLoader(var1, false);
            World var6 = null;
            var6 = new World(var5, var2, var3);
            if (var6.isNewWorld) {
                this.field_25001_G.func_25100_a(StatList.field_25183_f, 1);
                this.field_25001_G.func_25100_a(StatList.field_25184_e, 1);
                this.changeWorld2(var6, "Generating level");
            } else {
                this.field_25001_G.func_25100_a(StatList.field_25182_g, 1);
                this.field_25001_G.func_25100_a(StatList.field_25184_e, 1);
                this.changeWorld2(var6, "Loading level");
            }
        }
    }

    public void usePortal() {
        this.thePlayer.dimension = this.thePlayer.dimension == -1 ? 0 : -1;
        this.theWorld.setEntityDead(this.thePlayer);
        this.thePlayer.isDead = false;
        double var1 = this.thePlayer.posX;
        double var3 = this.thePlayer.posZ;
        double var5 = 8.0;
        if (this.thePlayer.dimension == -1) {
            this.thePlayer.setLocationAndAngles(var1 /= var5, this.thePlayer.posY, var3 /= var5, this.thePlayer.rotationYaw, this.thePlayer.rotationPitch);
            this.theWorld.updateEntityWithOptionalForce(this.thePlayer, false);
            World var7 = null;
            var7 = new World(this.theWorld, new WorldProviderHell());
            this.changeWorld(var7, "Entering the Nether", this.thePlayer);
        } else {
            this.thePlayer.setLocationAndAngles(var1 *= var5, this.thePlayer.posY, var3 *= var5, this.thePlayer.rotationYaw, this.thePlayer.rotationPitch);
            this.theWorld.updateEntityWithOptionalForce(this.thePlayer, false);
            World var7 = null;
            var7 = new World(this.theWorld, new WorldProvider());
            this.changeWorld(var7, "Leaving the Nether", this.thePlayer);
        }
        this.thePlayer.worldObj = this.theWorld;
        this.thePlayer.setLocationAndAngles(var1, this.thePlayer.posY, var3, this.thePlayer.rotationYaw, this.thePlayer.rotationPitch);
        this.theWorld.updateEntityWithOptionalForce(this.thePlayer, false);
        new Teleporter().func_4107_a(this.theWorld, this.thePlayer);
    }

    public void changeWorld1(World var1) {
        this.changeWorld2(var1, "");
    }

    public void changeWorld2(World var1, String var2) {
        this.changeWorld(var1, var2, null);
    }

    public void changeWorld(World var1, String var2, EntityPlayer var3) {
        this.renderViewEntity = null;
        this.loadingScreen.printText(var2);
        this.loadingScreen.displayLoadingString("");
        this.sndManager.playStreaming(null, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f);
        if (this.theWorld != null) {
            this.theWorld.func_651_a(this.loadingScreen);
        }
        this.theWorld = var1;
        if (var1 != null) {
            IChunkProvider var4;
            this.playerController.func_717_a(var1);
            if (!this.isMultiplayerWorld()) {
                if (var3 == null) {
                    this.thePlayer = (EntityPlayerSP)var1.func_4085_a(EntityPlayerSP.class);
                }
            } else if (this.thePlayer != null) {
                this.thePlayer.preparePlayerToSpawn();
                if (var1 != null) {
                    var1.entityJoinedWorld(this.thePlayer);
                }
            }
            if (!var1.multiplayerWorld) {
                this.func_6255_d(var2);
            }
            if (this.thePlayer == null) {
                this.thePlayer = (EntityPlayerSP)this.playerController.createPlayer(var1);
                this.thePlayer.preparePlayerToSpawn();
                this.playerController.flipPlayer(this.thePlayer);
            }
            this.thePlayer.movementInput = new MovementInputFromOptions(this.gameSettings);
            if (this.renderGlobal != null) {
                this.renderGlobal.func_946_a(var1);
            }
            if (this.effectRenderer != null) {
                this.effectRenderer.clearEffects(var1);
            }
            this.playerController.func_6473_b(this.thePlayer);
            if (var3 != null) {
                var1.func_6464_c();
            }
            if ((var4 = var1.getIChunkProvider()) instanceof ChunkProviderLoadOrGenerate) {
                ChunkProviderLoadOrGenerate var5 = (ChunkProviderLoadOrGenerate)var4;
                int var6 = MathHelper.floor_float((int)this.thePlayer.posX) >> 4;
                int var7 = MathHelper.floor_float((int)this.thePlayer.posZ) >> 4;
                var5.setCurrentChunkOver(var6, var7);
            }
            var1.spawnPlayerWithLoadedChunks(this.thePlayer);
            if (var1.isNewWorld) {
                var1.func_651_a(this.loadingScreen);
            }
            this.renderViewEntity = this.thePlayer;
        } else {
            this.thePlayer = null;
        }
        System.gc();
        this.systemTime = 0L;
    }

    private void convertMapFormat(String var1, String var2) {
        this.loadingScreen.printText("Converting World to " + this.saveLoader.func_22178_a());
        this.loadingScreen.displayLoadingString("This may take a while :)");
        this.saveLoader.convertMapFormat(var1, this.loadingScreen);
        this.startWorld(var1, var2, 0L);
    }

    private void func_6255_d(String var1) {
        this.loadingScreen.printText(var1);
        this.loadingScreen.displayLoadingString("Building terrain");
        int var2 = 128;
        int var3 = 0;
        int var4 = var2 * 2 / 16 + 1;
        var4 *= var4;
        IChunkProvider var5 = this.theWorld.getIChunkProvider();
        ChunkCoordinates var6 = this.theWorld.getSpawnPoint();
        if (this.thePlayer != null) {
            var6.x = (int)this.thePlayer.posX;
            var6.z = (int)this.thePlayer.posZ;
        }
        if (var5 instanceof ChunkProviderLoadOrGenerate) {
            ChunkProviderLoadOrGenerate var7 = (ChunkProviderLoadOrGenerate)var5;
            var7.setCurrentChunkOver(var6.x >> 4, var6.z >> 4);
        }
        int var10 = -var2;
        while (var10 <= var2) {
            int var8 = -var2;
            while (var8 <= var2) {
                this.loadingScreen.setLoadingProgress(var3++ * 100 / var4);
                this.theWorld.getBlockId(var6.x + var10, 64, var6.z + var8);
                while (this.theWorld.func_6465_g()) {
                }
                var8 += 16;
            }
            var10 += 16;
        }
        this.loadingScreen.displayLoadingString("Simulating world for a bit");
        boolean var9 = true;
        this.theWorld.func_656_j();
    }

    public void installResource(String var1, File var2) {
        int var3 = var1.indexOf("/");
        String var4 = var1.substring(0, var3);
        var1 = var1.substring(var3 + 1);
        if (var4.equalsIgnoreCase("sound")) {
            this.sndManager.addSound(var1, var2);
        } else if (var4.equalsIgnoreCase("newsound")) {
            this.sndManager.addSound(var1, var2);
        } else if (var4.equalsIgnoreCase("streaming")) {
            this.sndManager.addStreaming(var1, var2);
        } else if (var4.equalsIgnoreCase("music")) {
            this.sndManager.addMusic(var1, var2);
        } else if (var4.equalsIgnoreCase("newmusic")) {
            this.sndManager.addMusic(var1, var2);
        }
    }

    public OpenGlCapsChecker func_6251_l() {
        return this.glCapabilities;
    }

    public String func_6241_m() {
        return this.renderGlobal.func_953_b();
    }

    public String func_6262_n() {
        return this.renderGlobal.func_957_c();
    }

    public String func_21002_o() {
        return this.theWorld.func_21119_g();
    }

    public String func_6245_o() {
        return "P: " + this.effectRenderer.getStatistics() + ". T: " + this.theWorld.func_687_d();
    }

    public void respawn(boolean var1) {
        IChunkProvider var5;
        if (!this.theWorld.worldProvider.canRespawnHere()) {
            this.usePortal();
        }
        ChunkCoordinates var2 = null;
        ChunkCoordinates var3 = null;
        boolean var4 = true;
        if (this.thePlayer != null && !var1 && (var2 = this.thePlayer.getPlayerSpawnCoordinate()) != null && (var3 = EntityPlayer.func_25060_a(this.theWorld, var2)) == null) {
            this.thePlayer.addChatMessage("tile.bed.notValid");
        }
        if (var3 == null) {
            var3 = this.theWorld.getSpawnPoint();
            var4 = false;
        }
        if ((var5 = this.theWorld.getIChunkProvider()) instanceof ChunkProviderLoadOrGenerate) {
            ChunkProviderLoadOrGenerate var6 = (ChunkProviderLoadOrGenerate)var5;
            var6.setCurrentChunkOver(var3.x >> 4, var3.z >> 4);
        }
        this.theWorld.setSpawnLocation();
        this.theWorld.updateEntityList();
        int var7 = 0;
        if (this.thePlayer != null) {
            var7 = this.thePlayer.entityId;
            this.theWorld.setEntityDead(this.thePlayer);
        }
        this.renderViewEntity = null;
        this.thePlayer = (EntityPlayerSP)this.playerController.createPlayer(this.theWorld);
        this.renderViewEntity = this.thePlayer;
        this.thePlayer.preparePlayerToSpawn();
        if (var4) {
            this.thePlayer.setPlayerSpawnCoordinate(var2);
            this.thePlayer.setLocationAndAngles((float)var3.x + 0.5f, (float)var3.y + 0.1f, (float)var3.z + 0.5f, 0.0f, 0.0f);
        }
        this.playerController.flipPlayer(this.thePlayer);
        this.theWorld.spawnPlayerWithLoadedChunks(this.thePlayer);
        this.thePlayer.movementInput = new MovementInputFromOptions(this.gameSettings);
        this.thePlayer.entityId = var7;
        this.thePlayer.func_6420_o();
        this.playerController.func_6473_b(this.thePlayer);
        this.func_6255_d("Respawning");
        if (this.currentScreen instanceof GuiGameOver) {
            this.displayGuiScreen(null);
        }
    }

    public static void func_6269_a(String var0, String var1) {
        Minecraft.startMainThread(var0, var1, null);
    }

    public static void startMainThread(String var0, String var1, String var2) {
        boolean var3 = false;
        Frame var5 = new Frame("Minecraft");
        Canvas var6 = new Canvas();
        var5.setLayout(new BorderLayout());
        var5.add((Component)var6, "Center");
        var6.setPreferredSize(new Dimension(854, 480));
        var5.pack();
        var5.setLocationRelativeTo(null);
        MinecraftImpl var7 = new MinecraftImpl(var5, var6, null, 854, 480, var3, var5);
        Thread var8 = new Thread((Runnable)var7, "Minecraft main thread");
        var8.setPriority(10);
        var7.minecraftUri = "www.minecraft.net";
        var7.session = var0 != null && var1 != null ? new Session(var0, var1) : new Session("Player" + System.currentTimeMillis() % 1000L, "");
        if (var2 != null) {
            String[] var9 = var2.split(":");
            var7.setServer(var9[0], Integer.parseInt(var9[1]));
        }
        var5.setVisible(true);
        var5.addWindowListener(new GameWindowListener(var7, var8));
        var8.start();
    }

    public NetClientHandler getSendQueue() {
        return this.thePlayer instanceof EntityClientPlayerMP ? ((EntityClientPlayerMP)this.thePlayer).sendQueue : null;
    }

    public static void main(String[] var0) {
        String var1 = null;
        String var2 = null;
        var1 = "Player" + System.currentTimeMillis() % 1000L;
        if (var0.length > 0) {
            var1 = var0[0];
        }
        var2 = "-";
        if (var0.length > 1) {
            var2 = var0[1];
        }
        Minecraft.func_6269_a(var1, var2);
    }

    public static boolean isGuiEnabled() {
        return theMinecraft == null || !Minecraft.theMinecraft.gameSettings.hideGUI;
    }

    public static boolean isFancyGraphicsEnabled() {
        return theMinecraft != null && Minecraft.theMinecraft.gameSettings.fancyGraphics;
    }

    public static boolean isAmbientOcclusionEnabled() {
        return theMinecraft != null && Minecraft.theMinecraft.gameSettings.ambientOcclusion;
    }

    public static boolean isDebugInfoEnabled() {
        return theMinecraft != null && Minecraft.theMinecraft.gameSettings.showDebugInfo;
    }

    public boolean lineIsCommand(String var1) {
        var1.startsWith("/");
        if (var1.startsWith(Client.cmdPrefix)) {
            Client.handleCommand(var1.substring(Client.cmdPrefix.length()));
            return true;
        }
        return false;
    }
}

