/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import net.minecraft.src.NetHandler;
import net.minecraft.src.Packet;

public class Packet255KickDisconnect
extends Packet {
    public String reason;

    public Packet255KickDisconnect() {
    }

    public Packet255KickDisconnect(String var1) {
        this.reason = var1;
    }

    @Override
    public void readPacketData(DataInputStream var1) throws IOException {
        this.reason = var1.readUTF();
    }

    @Override
    public void writePacketData(DataOutputStream var1) throws IOException {
        var1.writeUTF(this.reason);
    }

    @Override
    public void processPacket(NetHandler var1) {
        var1.handleKickDisconnect(this);
    }

    @Override
    public int getPacketSize() {
        return this.reason.length();
    }
}

