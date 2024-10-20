/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GLContext
 */
package net.minecraft.src;

import org.lwjgl.opengl.GLContext;

public class OpenGlCapsChecker {
    private static boolean tryCheckOcclusionCapable = false;

    public boolean checkARBOcclusion() {
        return tryCheckOcclusionCapable && GLContext.getCapabilities().GL_ARB_occlusion_query;
    }
}

