/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import net.minecraft.src.NetHandler;
import net.minecraft.src.Packet;

public class Packet14BlockDig
extends Packet {
    public int xPosition;
    public int yPosition;
    public int zPosition;
    public int face;
    public int status;

    public Packet14BlockDig() {
    }

    public Packet14BlockDig(int var1, int var2, int var3, int var4, int var5) {
        this.status = var1;
        this.xPosition = var2;
        this.yPosition = var3;
        this.zPosition = var4;
        this.face = var5;
    }

    @Override
    public void readPacketData(DataInputStream var1) throws IOException {
        this.status = var1.read();
        this.xPosition = var1.readInt();
        this.yPosition = var1.read();
        this.zPosition = var1.readInt();
        this.face = var1.read();
    }

    @Override
    public void writePacketData(DataOutputStream var1) throws IOException {
        var1.write(this.status);
        var1.writeInt(this.xPosition);
        var1.write(this.yPosition);
        var1.writeInt(this.zPosition);
        var1.write(this.face);
    }

    @Override
    public void processPacket(NetHandler var1) {
        var1.handleBlockDig(this);
    }

    @Override
    public int getPacketSize() {
        return 11;
    }
}

