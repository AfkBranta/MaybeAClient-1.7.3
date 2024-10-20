/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.InflaterInputStream;
import net.minecraft.src.RegionFileChunkBuffer;

public class RegionFile {
    private static final byte[] field_22213_a = new byte[4096];
    private final File field_22212_b;
    private RandomAccessFile field_22219_c;
    private final int[] field_22218_d = new int[1024];
    private final int[] field_22217_e = new int[1024];
    private ArrayList field_22216_f;
    private int field_22215_g;
    private long field_22214_h = 0L;

    public RegionFile(File var1) {
        this.field_22212_b = var1;
        this.func_22204_b("REGION LOAD " + this.field_22212_b);
        this.field_22215_g = 0;
        try {
            int var4;
            int var2;
            if (var1.exists()) {
                this.field_22214_h = var1.lastModified();
            }
            this.field_22219_c = new RandomAccessFile(var1, "rw");
            if (this.field_22219_c.length() < 4096L) {
                var2 = 0;
                while (var2 < 1024) {
                    this.field_22219_c.writeInt(0);
                    ++var2;
                }
                var2 = 0;
                while (var2 < 1024) {
                    this.field_22219_c.writeInt(0);
                    ++var2;
                }
                this.field_22215_g += 8192;
            }
            if ((this.field_22219_c.length() & 0xFFFL) != 0L) {
                var2 = 0;
                while ((long)var2 < (this.field_22219_c.length() & 0xFFFL)) {
                    this.field_22219_c.write(0);
                    ++var2;
                }
            }
            var2 = (int)this.field_22219_c.length() / 4096;
            this.field_22216_f = new ArrayList(var2);
            int var3 = 0;
            while (var3 < var2) {
                this.field_22216_f.add(true);
                ++var3;
            }
            this.field_22216_f.set(0, false);
            this.field_22216_f.set(1, false);
            this.field_22219_c.seek(0L);
            var3 = 0;
            while (var3 < 1024) {
                this.field_22218_d[var3] = var4 = this.field_22219_c.readInt();
                if (var4 != 0 && (var4 >> 8) + (var4 & 0xFF) <= this.field_22216_f.size()) {
                    int var5 = 0;
                    while (var5 < (var4 & 0xFF)) {
                        this.field_22216_f.set((var4 >> 8) + var5, false);
                        ++var5;
                    }
                }
                ++var3;
            }
            var3 = 0;
            while (var3 < 1024) {
                this.field_22217_e[var3] = var4 = this.field_22219_c.readInt();
                ++var3;
            }
        }
        catch (IOException var6) {
            var6.printStackTrace();
        }
    }

    public synchronized int func_22209_a() {
        int var1 = this.field_22215_g;
        this.field_22215_g = 0;
        return var1;
    }

    private void func_22211_a(String var1) {
    }

    private void func_22204_b(String var1) {
        this.func_22211_a(String.valueOf(var1) + "\n");
    }

    private void func_22199_a(String var1, int var2, int var3, String var4) {
        this.func_22211_a("REGION " + var1 + " " + this.field_22212_b.getName() + "[" + var2 + "," + var3 + "] = " + var4);
    }

    private void func_22197_a(String var1, int var2, int var3, int var4, String var5) {
        this.func_22211_a("REGION " + var1 + " " + this.field_22212_b.getName() + "[" + var2 + "," + var3 + "] " + var4 + "B = " + var5);
    }

    private void func_22201_b(String var1, int var2, int var3, String var4) {
        this.func_22199_a(var1, var2, var3, String.valueOf(var4) + "\n");
    }

    public synchronized DataInputStream func_22210_a(int var1, int var2) {
        int var6;
        block10: {
            int var5;
            int var4;
            block9: {
                int var3;
                block8: {
                    if (this.func_22206_d(var1, var2)) {
                        this.func_22201_b("READ", var1, var2, "out of bounds");
                        return null;
                    }
                    try {
                        var3 = this.func_22207_e(var1, var2);
                        if (var3 != 0) break block8;
                        return null;
                    }
                    catch (IOException var10) {
                        this.func_22201_b("READ", var1, var2, "exception");
                        return null;
                    }
                }
                var4 = var3 >> 8;
                var5 = var3 & 0xFF;
                if (var4 + var5 <= this.field_22216_f.size()) break block9;
                this.func_22201_b("READ", var1, var2, "invalid sector");
                return null;
            }
            this.field_22219_c.seek(var4 * 4096);
            var6 = this.field_22219_c.readInt();
            if (var6 <= 4096 * var5) break block10;
            this.func_22201_b("READ", var1, var2, "invalid length: " + var6 + " > 4096 * " + var5);
            return null;
        }
        byte var7 = this.field_22219_c.readByte();
        if (var7 == 1) {
            byte[] var8 = new byte[var6 - 1];
            this.field_22219_c.read(var8);
            DataInputStream var9 = new DataInputStream(new GZIPInputStream(new ByteArrayInputStream(var8)));
            return var9;
        }
        if (var7 == 2) {
            byte[] var8 = new byte[var6 - 1];
            this.field_22219_c.read(var8);
            DataInputStream var9 = new DataInputStream(new InflaterInputStream(new ByteArrayInputStream(var8)));
            return var9;
        }
        this.func_22201_b("READ", var1, var2, "unknown version " + var7);
        return null;
    }

    public DataOutputStream func_22205_b(int var1, int var2) {
        return this.func_22206_d(var1, var2) ? null : new DataOutputStream(new DeflaterOutputStream(new RegionFileChunkBuffer(this, var1, var2)));
    }

    protected synchronized void func_22203_a(int var1, int var2, byte[] var3, int var4) {
        try {
            int var5 = this.func_22207_e(var1, var2);
            int var6 = var5 >> 8;
            int var7 = var5 & 0xFF;
            int var8 = (var4 + 5) / 4096 + 1;
            if (var8 >= 256) {
                return;
            }
            if (var6 != 0 && var7 == var8) {
                this.func_22197_a("SAVE", var1, var2, var4, "rewrite");
                this.func_22200_a(var6, var3, var4);
            } else {
                int var11;
                int var9 = 0;
                while (var9 < var7) {
                    this.field_22216_f.set(var6 + var9, true);
                    ++var9;
                }
                var9 = this.field_22216_f.indexOf(true);
                int var10 = 0;
                if (var9 != -1) {
                    var11 = var9;
                    while (var11 < this.field_22216_f.size()) {
                        if (var10 != 0) {
                            var10 = ((Boolean)this.field_22216_f.get(var11)).booleanValue() ? ++var10 : 0;
                        } else if (((Boolean)this.field_22216_f.get(var11)).booleanValue()) {
                            var9 = var11;
                            var10 = 1;
                        }
                        if (var10 >= var8) break;
                        ++var11;
                    }
                }
                if (var10 >= var8) {
                    this.func_22197_a("SAVE", var1, var2, var4, "reuse");
                    var6 = var9;
                    this.func_22198_a(var1, var2, var9 << 8 | var8);
                    var11 = 0;
                    while (var11 < var8) {
                        this.field_22216_f.set(var6 + var11, false);
                        ++var11;
                    }
                    this.func_22200_a(var6, var3, var4);
                } else {
                    this.func_22197_a("SAVE", var1, var2, var4, "grow");
                    this.field_22219_c.seek(this.field_22219_c.length());
                    var6 = this.field_22216_f.size();
                    var11 = 0;
                    while (var11 < var8) {
                        this.field_22219_c.write(field_22213_a);
                        this.field_22216_f.add(false);
                        ++var11;
                    }
                    this.field_22215_g += 4096 * var8;
                    this.func_22200_a(var6, var3, var4);
                    this.func_22198_a(var1, var2, var6 << 8 | var8);
                }
            }
            this.func_22208_b(var1, var2, (int)(System.currentTimeMillis() / 1000L));
        }
        catch (IOException var12) {
            var12.printStackTrace();
        }
    }

    private void func_22200_a(int var1, byte[] var2, int var3) throws IOException {
        this.func_22204_b(" " + var1);
        this.field_22219_c.seek(var1 * 4096);
        this.field_22219_c.writeInt(var3 + 1);
        this.field_22219_c.writeByte(2);
        this.field_22219_c.write(var2, 0, var3);
    }

    private boolean func_22206_d(int var1, int var2) {
        return var1 < 0 || var1 >= 32 || var2 < 0 || var2 >= 32;
    }

    private int func_22207_e(int var1, int var2) {
        return this.field_22218_d[var1 + var2 * 32];
    }

    public boolean func_22202_c(int var1, int var2) {
        return this.func_22207_e(var1, var2) != 0;
    }

    private void func_22198_a(int var1, int var2, int var3) throws IOException {
        this.field_22218_d[var1 + var2 * 32] = var3;
        this.field_22219_c.seek((var1 + var2 * 32) * 4);
        this.field_22219_c.writeInt(var3);
    }

    private void func_22208_b(int var1, int var2, int var3) throws IOException {
        this.field_22217_e[var1 + var2 * 32] = var3;
        this.field_22219_c.seek(4096 + (var1 + var2 * 32) * 4);
        this.field_22219_c.writeInt(var3);
    }

    public void func_22196_b() throws IOException {
        this.field_22219_c.close();
    }
}

