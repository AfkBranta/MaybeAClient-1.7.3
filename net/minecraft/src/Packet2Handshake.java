/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import net.minecraft.src.NetHandler;
import net.minecraft.src.Packet;

public class Packet2Handshake
extends Packet {
    public String username;

    public Packet2Handshake() {
    }

    public Packet2Handshake(String var1) {
        this.username = var1;
    }

    @Override
    public void readPacketData(DataInputStream var1) throws IOException {
        this.username = var1.readUTF();
    }

    @Override
    public void writePacketData(DataOutputStream var1) throws IOException {
        var1.writeUTF(this.username);
    }

    @Override
    public void processPacket(NetHandler var1) {
        var1.handleHandshake(this);
    }

    @Override
    public int getPacketSize() {
        return 4 + this.username.length() + 4;
    }
}

