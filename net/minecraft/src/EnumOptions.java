/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

public enum EnumOptions {
    MUSIC("options.music", true, false),
    SOUND("options.sound", true, false),
    INVERT_MOUSE("options.invertMouse", false, true),
    SENSITIVITY("options.sensitivity", true, false),
    RENDER_DISTANCE("options.renderDistance", false, false),
    VIEW_BOBBING("options.viewBobbing", false, true),
    ANAGLYPH("options.anaglyph", false, true),
    LIMIT_FRAMERATE("options.limitFramerate", false, true),
    DIFFICULTY("options.difficulty", false, false),
    GRAPHICS("options.graphics", false, false),
    AMBIENT_OCCLUSION("options.ao", false, true),
    GUI_SCALE("options.guiScale", false, false);

    private final boolean enumFloat;
    private final boolean enumBoolean;
    private final String enumString;

    public static EnumOptions func_20137_a(int var0) {
        EnumOptions[] var1 = EnumOptions.values();
        int var2 = var1.length;
        int var3 = 0;
        while (var3 < var2) {
            EnumOptions var4 = var1[var3];
            if (var4.returnEnumOrdinal() == var0) {
                return var4;
            }
            ++var3;
        }
        return null;
    }

    private EnumOptions(String var3, boolean var4, boolean var5) {
        this.enumString = var3;
        this.enumFloat = var4;
        this.enumBoolean = var5;
    }

    public boolean getEnumFloat() {
        return this.enumFloat;
    }

    public boolean getEnumBoolean() {
        return this.enumBoolean;
    }

    public int returnEnumOrdinal() {
        return this.ordinal();
    }

    public String getEnumString() {
        return this.enumString;
    }
}

