/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.src;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;
import org.lwjgl.opengl.GL11;

public class GLAllocation {
    private static List displayLists = new ArrayList();
    private static List textureNames = new ArrayList();

    public static synchronized int generateDisplayLists(int var0) {
        int var1 = GL11.glGenLists((int)var0);
        displayLists.add(var1);
        displayLists.add(var0);
        return var1;
    }

    public static synchronized void generateTextureNames(IntBuffer var0) {
        GL11.glGenTextures((IntBuffer)var0);
        int var1 = var0.position();
        while (var1 < var0.limit()) {
            textureNames.add(var0.get(var1));
            ++var1;
        }
    }

    public static synchronized void deleteTexturesAndDisplayLists() {
        int var0 = 0;
        while (var0 < displayLists.size()) {
            GL11.glDeleteLists((int)((Integer)displayLists.get(var0)), (int)((Integer)displayLists.get(var0 + 1)));
            var0 += 2;
        }
        IntBuffer var2 = GLAllocation.createDirectIntBuffer(textureNames.size());
        var2.flip();
        GL11.glDeleteTextures((IntBuffer)var2);
        int var1 = 0;
        while (var1 < textureNames.size()) {
            var2.put((Integer)textureNames.get(var1));
            ++var1;
        }
        var2.flip();
        GL11.glDeleteTextures((IntBuffer)var2);
        displayLists.clear();
        textureNames.clear();
    }

    public static synchronized ByteBuffer createDirectByteBuffer(int var0) {
        ByteBuffer var1 = ByteBuffer.allocateDirect(var0).order(ByteOrder.nativeOrder());
        return var1;
    }

    public static IntBuffer createDirectIntBuffer(int var0) {
        return GLAllocation.createDirectByteBuffer(var0 << 2).asIntBuffer();
    }

    public static FloatBuffer createDirectFloatBuffer(int var0) {
        return GLAllocation.createDirectByteBuffer(var0 << 2).asFloatBuffer();
    }
}

