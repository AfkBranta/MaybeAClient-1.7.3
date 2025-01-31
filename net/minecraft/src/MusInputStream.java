/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import net.minecraft.src.CodecMus;

class MusInputStream
extends InputStream {
    private int hash;
    private InputStream inputStream;
    byte[] buffer;
    final CodecMus codec;

    public MusInputStream(CodecMus var1, URL var2, InputStream var3) {
        this.codec = var1;
        this.buffer = new byte[1];
        this.inputStream = var3;
        String var4 = var2.getPath();
        var4 = var4.substring(var4.lastIndexOf("/") + 1);
        this.hash = var4.hashCode();
    }

    @Override
    public int read() throws IOException {
        int var1 = this.read(this.buffer, 0, 1);
        return var1 < 0 ? var1 : this.buffer[0];
    }

    @Override
    public int read(byte[] var1, int var2, int var3) throws IOException {
        var3 = this.inputStream.read(var1, var2, var3);
        int var4 = 0;
        while (var4 < var3) {
            byte by = (byte)(var1[var2 + var4] ^ this.hash >> 8);
            var1[var2 + var4] = by;
            byte var5 = by;
            this.hash = this.hash * 498729871 + 85731 * var5;
            ++var4;
        }
        return var3;
    }
}

