/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import net.minecraft.src.NetHandler;
import net.minecraft.src.Packet;

public class Packet7
extends Packet {
    public int playerEntityId;
    public int targetEntity;
    public int isLeftClick;

    public Packet7() {
    }

    public Packet7(int var1, int var2, int var3) {
        this.playerEntityId = var1;
        this.targetEntity = var2;
        this.isLeftClick = var3;
    }

    @Override
    public void readPacketData(DataInputStream var1) throws IOException {
        this.playerEntityId = var1.readInt();
        this.targetEntity = var1.readInt();
        this.isLeftClick = var1.readByte();
    }

    @Override
    public void writePacketData(DataOutputStream var1) throws IOException {
        var1.writeInt(this.playerEntityId);
        var1.writeInt(this.targetEntity);
        var1.writeByte(this.isLeftClick);
    }

    @Override
    public void processPacket(NetHandler var1) {
        var1.func_6499_a(this);
    }

    @Override
    public int getPacketSize() {
        return 9;
    }
}

