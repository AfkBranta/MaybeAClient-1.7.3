/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.src.NBTBase;

public class NBTTagList
extends NBTBase {
    private List tagList = new ArrayList();
    private byte tagType;

    @Override
    void writeTagContents(DataOutput var1) throws IOException {
        this.tagType = this.tagList.size() > 0 ? ((NBTBase)this.tagList.get(0)).getType() : (byte)1;
        var1.writeByte(this.tagType);
        var1.writeInt(this.tagList.size());
        int var2 = 0;
        while (var2 < this.tagList.size()) {
            ((NBTBase)this.tagList.get(var2)).writeTagContents(var1);
            ++var2;
        }
    }

    @Override
    void readTagContents(DataInput var1) throws IOException {
        this.tagType = var1.readByte();
        int var2 = var1.readInt();
        this.tagList = new ArrayList();
        int var3 = 0;
        while (var3 < var2) {
            NBTBase var4 = NBTBase.createTagOfType(this.tagType);
            var4.readTagContents(var1);
            this.tagList.add(var4);
            ++var3;
        }
    }

    @Override
    public byte getType() {
        return 9;
    }

    public String toString() {
        return this.tagList.size() + " entries of type " + NBTBase.getTagName(this.tagType);
    }

    public void setTag(NBTBase var1) {
        this.tagType = var1.getType();
        this.tagList.add(var1);
    }

    public NBTBase tagAt(int var1) {
        return (NBTBase)this.tagList.get(var1);
    }

    public int tagCount() {
        return this.tagList.size();
    }
}

