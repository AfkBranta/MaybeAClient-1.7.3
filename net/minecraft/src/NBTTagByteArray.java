package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import net.minecraft.src.NBTBase;

public class NBTTagByteArray
extends NBTBase {
    public byte[] byteArray;

    public NBTTagByteArray() {
    }

    public NBTTagByteArray(byte[] var1) {
        this.byteArray = var1;
    }

    @Override
    void writeTagContents(DataOutput var1) throws IOException {
        var1.writeInt(this.byteArray.length);
        var1.write(this.byteArray);
    }

    @Override
    void readTagContents(DataInput var1) throws IOException {
        int var2 = var1.readInt();
        this.byteArray = new byte[var2];
        var1.readFully(this.byteArray);
    }

    @Override
    public byte getType() {
        return 7;
    }

    public String toString() {
        return "[" + this.byteArray.length + " bytes]";
    }
}

