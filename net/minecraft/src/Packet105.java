/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import net.minecraft.src.NetHandler;
import net.minecraft.src.Packet;

public class Packet105
extends Packet {
    public int windowId;
    public int progressBar;
    public int progressBarValue;

    @Override
    public void processPacket(NetHandler var1) {
        var1.func_20090_a(this);
    }

    @Override
    public void readPacketData(DataInputStream var1) throws IOException {
        this.windowId = var1.readByte();
        this.progressBar = var1.readShort();
        this.progressBarValue = var1.readShort();
    }

    @Override
    public void writePacketData(DataOutputStream var1) throws IOException {
        var1.writeByte(this.windowId);
        var1.writeShort(this.progressBar);
        var1.writeShort(this.progressBarValue);
    }

    @Override
    public int getPacketSize() {
        return 5;
    }
}

