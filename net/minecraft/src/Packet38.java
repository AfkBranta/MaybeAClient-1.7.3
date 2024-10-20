/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import net.minecraft.src.NetHandler;
import net.minecraft.src.Packet;

public class Packet38
extends Packet {
    public int entityId;
    public byte entityStatus;

    @Override
    public void readPacketData(DataInputStream var1) throws IOException {
        this.entityId = var1.readInt();
        this.entityStatus = var1.readByte();
    }

    @Override
    public void writePacketData(DataOutputStream var1) throws IOException {
        var1.writeInt(this.entityId);
        var1.writeByte(this.entityStatus);
    }

    @Override
    public void processPacket(NetHandler var1) {
        var1.func_9447_a(this);
    }

    @Override
    public int getPacketSize() {
        return 5;
    }
}

