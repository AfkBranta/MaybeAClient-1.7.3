/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import net.minecraft.src.NBTBase;

public class NBTTagLong
extends NBTBase {
    public long longValue;

    public NBTTagLong() {
    }

    public NBTTagLong(long var1) {
        this.longValue = var1;
    }

    @Override
    void writeTagContents(DataOutput var1) throws IOException {
        var1.writeLong(this.longValue);
    }

    @Override
    void readTagContents(DataInput var1) throws IOException {
        this.longValue = var1.readLong();
    }

    @Override
    public byte getType() {
        return 4;
    }

    public String toString() {
        return "" + this.longValue;
    }
}

