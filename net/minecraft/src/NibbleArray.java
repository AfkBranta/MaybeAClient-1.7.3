/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

public class NibbleArray {
    public final byte[] data;

    public NibbleArray(int var1) {
        this.data = new byte[var1 >> 1];
    }

    public NibbleArray(byte[] var1) {
        this.data = var1;
    }

    public int getNibble(int var1, int var2, int var3) {
        int var4 = var1 << 11 | var3 << 7 | var2;
        int var5 = var4 >> 1;
        int var6 = var4 & 1;
        return var6 == 0 ? this.data[var5] & 0xF : this.data[var5] >> 4 & 0xF;
    }

    public void setNibble(int var1, int var2, int var3, int var4) {
        int var5 = var1 << 11 | var3 << 7 | var2;
        int var6 = var5 >> 1;
        int var7 = var5 & 1;
        this.data[var6] = var7 == 0 ? (byte)(this.data[var6] & 0xF0 | var4 & 0xF) : (byte)(this.data[var6] & 0xF | (var4 & 0xF) << 4);
    }

    public boolean isValid() {
        return this.data != null;
    }
}

