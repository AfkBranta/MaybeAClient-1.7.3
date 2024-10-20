/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.Block;

public class ChunkBlockMap {
    private static byte[] field_26003_a = new byte[256];

    static {
        try {
            int var0 = 0;
            while (var0 < 256) {
                byte var1 = (byte)var0;
                if (var1 != 0 && Block.blocksList[var1 & 0xFF] == null) {
                    var1 = 0;
                }
                ChunkBlockMap.field_26003_a[var0] = var1;
                ++var0;
            }
        }
        catch (Exception var2) {
            var2.printStackTrace();
        }
    }

    public static void func_26002_a(byte[] var0) {
        int var1 = 0;
        while (var1 < var0.length) {
            var0[var1] = field_26003_a[var0[var1] & 0xFF];
            ++var1;
        }
    }
}

