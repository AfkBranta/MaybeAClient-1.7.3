/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import net.minecraft.src.NBTBase;
import net.minecraft.src.NBTTagCompound;

public class CompressedStreamTools {
    public static NBTTagCompound func_1138_a(InputStream var0) throws IOException {
        NBTTagCompound var2;
        try (DataInputStream var1 = new DataInputStream(new GZIPInputStream(var0));){
            var2 = CompressedStreamTools.func_1141_a(var1);
        }
        return var2;
    }

    public static void writeGzippedCompoundToOutputStream(NBTTagCompound var0, OutputStream var1) throws IOException {
        try (DataOutputStream var2 = new DataOutputStream(new GZIPOutputStream(var1));){
            CompressedStreamTools.func_1139_a(var0, var2);
        }
    }

    public static NBTTagCompound func_1141_a(DataInput var0) throws IOException {
        NBTBase var1 = NBTBase.readTag(var0);
        if (var1 instanceof NBTTagCompound) {
            return (NBTTagCompound)var1;
        }
        throw new IOException("Root tag must be a named compound tag");
    }

    public static void func_1139_a(NBTTagCompound var0, DataOutput var1) throws IOException {
        NBTBase.writeTag(var0, var1);
    }
}

