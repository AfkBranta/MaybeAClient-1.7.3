/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import net.minecraft.src.Entity;
import net.minecraft.src.NetHandler;
import net.minecraft.src.Packet;

public class Packet19
extends Packet {
    public int entityId;
    public int state;

    public Packet19() {
    }

    public Packet19(Entity var1, int var2) {
        this.entityId = var1.entityId;
        this.state = var2;
    }

    @Override
    public void readPacketData(DataInputStream var1) throws IOException {
        this.entityId = var1.readInt();
        this.state = var1.readByte();
    }

    @Override
    public void writePacketData(DataOutputStream var1) throws IOException {
        var1.writeInt(this.entityId);
        var1.writeByte(this.state);
    }

    @Override
    public void processPacket(NetHandler var1) {
        var1.func_21147_a(this);
    }

    @Override
    public int getPacketSize() {
        return 5;
    }
}

