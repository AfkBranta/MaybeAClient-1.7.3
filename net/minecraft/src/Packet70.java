/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import net.minecraft.src.NetHandler;
import net.minecraft.src.Packet;

public class Packet70
extends Packet {
    public static final String[] field_25020_a = new String[]{"tile.bed.notValid"};
    public int field_25019_b;

    @Override
    public void readPacketData(DataInputStream var1) throws IOException {
        this.field_25019_b = var1.readByte();
    }

    @Override
    public void writePacketData(DataOutputStream var1) throws IOException {
        var1.writeByte(this.field_25019_b);
    }

    @Override
    public void processPacket(NetHandler var1) {
        var1.func_25118_a(this);
    }

    @Override
    public int getPacketSize() {
        return 1;
    }
}

