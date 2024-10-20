/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import net.minecraft.src.NetHandler;
import net.minecraft.src.Packet;

public class Packet16BlockItemSwitch
extends Packet {
    public int id;

    public Packet16BlockItemSwitch() {
    }

    public Packet16BlockItemSwitch(int var1) {
        this.id = var1;
    }

    @Override
    public void readPacketData(DataInputStream var1) throws IOException {
        this.id = var1.readShort();
    }

    @Override
    public void writePacketData(DataOutputStream var1) throws IOException {
        var1.writeShort(this.id);
    }

    @Override
    public void processPacket(NetHandler var1) {
        var1.handleBlockItemSwitch(this);
    }

    @Override
    public int getPacketSize() {
        return 2;
    }
}

