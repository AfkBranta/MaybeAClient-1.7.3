/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import net.minecraft.src.NetHandler;
import net.minecraft.src.Packet;

public class Packet3Chat
extends Packet {
    public String message;

    public Packet3Chat() {
    }

    public Packet3Chat(String var1) {
        this.message = var1;
    }

    @Override
    public void readPacketData(DataInputStream var1) throws IOException {
        this.message = var1.readUTF();
    }

    @Override
    public void writePacketData(DataOutputStream var1) throws IOException {
        var1.writeUTF(this.message);
    }

    @Override
    public void processPacket(NetHandler var1) {
        var1.handleChat(this);
    }

    @Override
    public int getPacketSize() {
        return this.message.length();
    }
}

