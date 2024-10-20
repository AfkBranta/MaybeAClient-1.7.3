/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Keyboard
 */
package net.minecraft.src;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import net.minecraft.client.Minecraft;
import net.minecraft.src.EnumOptions;
import net.minecraft.src.KeyBinding;
import net.minecraft.src.StringTranslate;
import org.lwjgl.input.Keyboard;

public class GameSettings {
    private static final String[] RENDER_DISTANCES = new String[]{"options.renderDistance.far", "options.renderDistance.normal", "options.renderDistance.short", "options.renderDistance.tiny"};
    private static final String[] DIFFICULTIES = new String[]{"options.difficulty.peaceful", "options.difficulty.easy", "options.difficulty.normal", "options.difficulty.hard"};
    private static final String[] field_25147_K = new String[]{"options.guiScale.auto", "options.guiScale.small", "options.guiScale.normal", "options.guiScale.large"};
    public float musicVolume = 1.0f;
    public float soundVolume = 1.0f;
    public float mouseSensitivity = 0.5f;
    public boolean invertMouse = false;
    public int renderDistance = 0;
    public boolean viewBobbing = true;
    public boolean anaglyph = false;
    public boolean limitFramerate = false;
    public boolean fancyGraphics = true;
    public boolean ambientOcclusion = true;
    public String skin = "Default";
    public KeyBinding keyBindForward = new KeyBinding("key.forward", 17);
    public KeyBinding keyBindLeft = new KeyBinding("key.left", 30);
    public KeyBinding keyBindBack = new KeyBinding("key.back", 31);
    public KeyBinding keyBindRight = new KeyBinding("key.right", 32);
    public KeyBinding keyBindJump = new KeyBinding("key.jump", 57);
    public KeyBinding keyBindInventory = new KeyBinding("key.inventory", 18);
    public KeyBinding keyBindDrop = new KeyBinding("key.drop", 16);
    public KeyBinding keyBindChat = new KeyBinding("key.chat", 20);
    public KeyBinding keyBindToggleFog = new KeyBinding("key.fog", 33);
    public KeyBinding keyBindSneak = new KeyBinding("key.sneak", 42);
    public KeyBinding[] keyBindings = new KeyBinding[]{this.keyBindForward, this.keyBindLeft, this.keyBindBack, this.keyBindRight, this.keyBindJump, this.keyBindSneak, this.keyBindDrop, this.keyBindInventory, this.keyBindChat, this.keyBindToggleFog};
    protected Minecraft mc;
    private File optionsFile;
    public int difficulty = 2;
    public boolean hideGUI = false;
    public boolean thirdPersonView = false;
    public boolean showDebugInfo = false;
    public String lastServer = "";
    public String realLastServer = "";
    public boolean field_22275_C = false;
    public boolean smoothCamera = false;
    public boolean field_22273_E = false;
    public float field_22272_F = 1.0f;
    public float field_22271_G = 1.0f;
    public int guiScale = 0;

    public GameSettings(Minecraft var1, File var2) {
        this.mc = var1;
        this.optionsFile = new File(var2, "options.txt");
        this.loadOptions();
    }

    public GameSettings() {
    }

    public String getKeyBindingDescription(int var1) {
        StringTranslate var2 = StringTranslate.getInstance();
        return var2.translateKey(this.keyBindings[var1].keyDescription);
    }

    public String getOptionDisplayString(int var1) {
        return Keyboard.getKeyName((int)this.keyBindings[var1].keyCode);
    }

    public void setKeyBinding(int var1, int var2) {
        this.keyBindings[var1].keyCode = var2;
        this.saveOptions();
    }

    public void setOptionFloatValue(EnumOptions var1, float var2) {
        if (var1 == EnumOptions.MUSIC) {
            this.musicVolume = var2;
            this.mc.sndManager.onSoundOptionsChanged();
        }
        if (var1 == EnumOptions.SOUND) {
            this.soundVolume = var2;
            this.mc.sndManager.onSoundOptionsChanged();
        }
        if (var1 == EnumOptions.SENSITIVITY) {
            this.mouseSensitivity = var2;
        }
    }

    public void setOptionValue(EnumOptions var1, int var2) {
        if (var1 == EnumOptions.INVERT_MOUSE) {
            boolean bl = this.invertMouse = !this.invertMouse;
        }
        if (var1 == EnumOptions.RENDER_DISTANCE) {
            this.renderDistance = this.renderDistance + var2 & 3;
        }
        if (var1 == EnumOptions.GUI_SCALE) {
            this.guiScale = this.guiScale + var2 & 3;
        }
        if (var1 == EnumOptions.VIEW_BOBBING) {
            boolean bl = this.viewBobbing = !this.viewBobbing;
        }
        if (var1 == EnumOptions.ANAGLYPH) {
            this.anaglyph = !this.anaglyph;
            this.mc.renderEngine.refreshTextures();
        }
        if (var1 == EnumOptions.LIMIT_FRAMERATE) {
            boolean bl = this.limitFramerate = !this.limitFramerate;
        }
        if (var1 == EnumOptions.DIFFICULTY) {
            this.difficulty = this.difficulty + var2 & 3;
        }
        if (var1 == EnumOptions.GRAPHICS) {
            this.fancyGraphics = !this.fancyGraphics;
            this.mc.renderGlobal.loadRenderers();
        }
        if (var1 == EnumOptions.AMBIENT_OCCLUSION) {
            this.ambientOcclusion = !this.ambientOcclusion;
            this.mc.renderGlobal.loadRenderers();
        }
        this.saveOptions();
    }

    public float getOptionFloatValue(EnumOptions var1) {
        if (var1 == EnumOptions.MUSIC) {
            return this.musicVolume;
        }
        if (var1 == EnumOptions.SOUND) {
            return this.soundVolume;
        }
        return var1 == EnumOptions.SENSITIVITY ? this.mouseSensitivity : 0.0f;
    }

    public boolean getOptionOrdinalValue(EnumOptions var1) {
        switch (var1) {
            case INVERT_MOUSE: {
                return this.invertMouse;
            }
            case VIEW_BOBBING: {
                return this.viewBobbing;
            }
            case ANAGLYPH: {
                return this.anaglyph;
            }
            case LIMIT_FRAMERATE: {
                return this.limitFramerate;
            }
            case AMBIENT_OCCLUSION: {
                return this.ambientOcclusion;
            }
        }
        return false;
    }

    public String getKeyBinding(EnumOptions var1) {
        StringTranslate var2 = StringTranslate.getInstance();
        String var3 = String.valueOf(var2.translateKey(var1.getEnumString())) + ": ";
        if (var1.getEnumFloat()) {
            float var5 = this.getOptionFloatValue(var1);
            if (var1 == EnumOptions.SENSITIVITY) {
                if (var5 == 0.0f) {
                    return String.valueOf(var3) + var2.translateKey("options.sensitivity.min");
                }
                return var5 == 1.0f ? String.valueOf(var3) + var2.translateKey("options.sensitivity.max") : String.valueOf(var3) + (int)(var5 * 200.0f) + "%";
            }
            return var5 == 0.0f ? String.valueOf(var3) + var2.translateKey("options.off") : String.valueOf(var3) + (int)(var5 * 100.0f) + "%";
        }
        if (var1.getEnumBoolean()) {
            boolean var4 = this.getOptionOrdinalValue(var1);
            return var4 ? String.valueOf(var3) + var2.translateKey("options.on") : String.valueOf(var3) + var2.translateKey("options.off");
        }
        if (var1 == EnumOptions.RENDER_DISTANCE) {
            return String.valueOf(var3) + var2.translateKey(RENDER_DISTANCES[this.renderDistance]);
        }
        if (var1 == EnumOptions.DIFFICULTY) {
            return String.valueOf(var3) + var2.translateKey(DIFFICULTIES[this.difficulty]);
        }
        if (var1 == EnumOptions.GUI_SCALE) {
            return String.valueOf(var3) + var2.translateKey(field_25147_K[this.guiScale]);
        }
        if (var1 == EnumOptions.GRAPHICS) {
            return this.fancyGraphics ? String.valueOf(var3) + var2.translateKey("options.graphics.fancy") : String.valueOf(var3) + var2.translateKey("options.graphics.fast");
        }
        return var3;
    }

    public void loadOptions() {
        try {
            if (!this.optionsFile.exists()) {
                return;
            }
            BufferedReader var1 = new BufferedReader(new FileReader(this.optionsFile));
            String var2 = "";
            while ((var2 = var1.readLine()) != null) {
                String[] var3 = var2.split(":");
                if (var3[0].equals("music")) {
                    this.musicVolume = this.parseFloat(var3[1]);
                }
                if (var3[0].equals("sound")) {
                    this.soundVolume = this.parseFloat(var3[1]);
                }
                if (var3[0].equals("mouseSensitivity")) {
                    this.mouseSensitivity = this.parseFloat(var3[1]);
                }
                if (var3[0].equals("invertYMouse")) {
                    this.invertMouse = var3[1].equals("true");
                }
                if (var3[0].equals("viewDistance")) {
                    this.renderDistance = Integer.parseInt(var3[1]);
                }
                if (var3[0].equals("guiScale")) {
                    this.guiScale = Integer.parseInt(var3[1]);
                }
                if (var3[0].equals("bobView")) {
                    this.viewBobbing = var3[1].equals("true");
                }
                if (var3[0].equals("anaglyph3d")) {
                    this.anaglyph = var3[1].equals("true");
                }
                if (var3[0].equals("limitFramerate")) {
                    this.limitFramerate = var3[1].equals("true");
                }
                if (var3[0].equals("difficulty")) {
                    this.difficulty = Integer.parseInt(var3[1]);
                }
                if (var3[0].equals("fancyGraphics")) {
                    this.fancyGraphics = var3[1].equals("true");
                }
                if (var3[0].equals("ao")) {
                    this.ambientOcclusion = var3[1].equals("true");
                }
                if (var3[0].equals("skin")) {
                    this.skin = var3[1];
                }
                if (var3[0].equals("lastServer") && var3.length >= 2) {
                    this.lastServer = var3[1];
                }
                int var4 = 0;
                while (var4 < this.keyBindings.length) {
                    if (var3[0].equals("key_" + this.keyBindings[var4].keyDescription)) {
                        this.keyBindings[var4].keyCode = Integer.parseInt(var3[1]);
                    }
                    ++var4;
                }
            }
            var1.close();
        }
        catch (Exception var5) {
            System.out.println("Failed to load options");
            var5.printStackTrace();
        }
    }

    private float parseFloat(String var1) {
        if (var1.equals("true")) {
            return 1.0f;
        }
        return var1.equals("false") ? 0.0f : Float.parseFloat(var1);
    }

    public void saveOptions() {
        try {
            PrintWriter var1 = new PrintWriter(new FileWriter(this.optionsFile));
            var1.println("music:" + this.musicVolume);
            var1.println("sound:" + this.soundVolume);
            var1.println("invertYMouse:" + this.invertMouse);
            var1.println("mouseSensitivity:" + this.mouseSensitivity);
            var1.println("viewDistance:" + this.renderDistance);
            var1.println("guiScale:" + this.guiScale);
            var1.println("bobView:" + this.viewBobbing);
            var1.println("anaglyph3d:" + this.anaglyph);
            var1.println("limitFramerate:" + this.limitFramerate);
            var1.println("difficulty:" + this.difficulty);
            var1.println("fancyGraphics:" + this.fancyGraphics);
            var1.println("ao:" + this.ambientOcclusion);
            var1.println("skin:" + this.skin);
            var1.println("lastServer:" + this.lastServer);
            int var2 = 0;
            while (var2 < this.keyBindings.length) {
                var1.println("key_" + this.keyBindings[var2].keyDescription + ":" + this.keyBindings[var2].keyCode);
                ++var2;
            }
            var1.close();
        }
        catch (Exception var3) {
            System.out.println("Failed to save options");
            var3.printStackTrace();
        }
    }
}

