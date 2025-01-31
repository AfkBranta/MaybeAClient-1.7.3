/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import net.minecraft.src.NetHandler;
import net.minecraft.src.Packet;

public class Packet52MultiBlockChange
extends Packet {
    public int xPosition;
    public int zPosition;
    public short[] coordinateArray;
    public byte[] typeArray;
    public byte[] metadataArray;
    public int size;

    public Packet52MultiBlockChange() {
        this.isChunkDataPacket = true;
    }

    @Override
    public void readPacketData(DataInputStream var1) throws IOException {
        this.xPosition = var1.readInt();
        this.zPosition = var1.readInt();
        this.size = var1.readShort() & 0xFFFF;
        this.coordinateArray = new short[this.size];
        this.typeArray = new byte[this.size];
        this.metadataArray = new byte[this.size];
        int var2 = 0;
        while (var2 < this.size) {
            this.coordinateArray[var2] = var1.readShort();
            ++var2;
        }
        var1.readFully(this.typeArray);
        var1.readFully(this.metadataArray);
    }

    @Override
    public void writePacketData(DataOutputStream var1) throws IOException {
        var1.writeInt(this.xPosition);
        var1.writeInt(this.zPosition);
        var1.writeShort((short)this.size);
        int var2 = 0;
        while (var2 < this.size) {
            var1.writeShort(this.coordinateArray[var2]);
            ++var2;
        }
        var1.write(this.typeArray);
        var1.write(this.metadataArray);
    }

    @Override
    public void processPacket(NetHandler var1) {
        var1.handleMultiBlockChange(this);
    }

    @Override
    public int getPacketSize() {
        return 10 + this.size * 4;
    }
}

