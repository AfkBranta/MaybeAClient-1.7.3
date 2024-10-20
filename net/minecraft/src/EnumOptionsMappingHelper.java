/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.EnumOptions;

class EnumOptionsMappingHelper {
    static final int[] enumOptionsMappingHelperArray = new int[EnumOptions.values().length];

    static {
        try {
            EnumOptionsMappingHelper.enumOptionsMappingHelperArray[EnumOptions.INVERT_MOUSE.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            EnumOptionsMappingHelper.enumOptionsMappingHelperArray[EnumOptions.VIEW_BOBBING.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            EnumOptionsMappingHelper.enumOptionsMappingHelperArray[EnumOptions.ANAGLYPH.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            EnumOptionsMappingHelper.enumOptionsMappingHelperArray[EnumOptions.LIMIT_FRAMERATE.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            EnumOptionsMappingHelper.enumOptionsMappingHelperArray[EnumOptions.AMBIENT_OCCLUSION.ordinal()] = 5;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
    }

    EnumOptionsMappingHelper() {
    }
}

