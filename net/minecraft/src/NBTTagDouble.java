/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import net.minecraft.src.NBTBase;

public class NBTTagDouble
extends NBTBase {
    public double doubleValue;

    public NBTTagDouble() {
    }

    public NBTTagDouble(double var1) {
        this.doubleValue = var1;
    }

    @Override
    void writeTagContents(DataOutput var1) throws IOException {
        var1.writeDouble(this.doubleValue);
    }

    @Override
    void readTagContents(DataInput var1) throws IOException {
        this.doubleValue = var1.readDouble();
    }

    @Override
    public byte getType() {
        return 6;
    }

    public String toString() {
        return "" + this.doubleValue;
    }
}

