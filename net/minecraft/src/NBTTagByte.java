/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import net.minecraft.src.NBTBase;

public class NBTTagByte
extends NBTBase {
    public byte byteValue;

    public NBTTagByte() {
    }

    public NBTTagByte(byte var1) {
        this.byteValue = var1;
    }

    @Override
    void writeTagContents(DataOutput var1) throws IOException {
        var1.writeByte(this.byteValue);
    }

    @Override
    void readTagContents(DataInput var1) throws IOException {
        this.byteValue = var1.readByte();
    }

    @Override
    public byte getType() {
        return 1;
    }

    public String toString() {
        return "" + this.byteValue;
    }
}

