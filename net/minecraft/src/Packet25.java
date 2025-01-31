/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import net.minecraft.src.EntityPainting;
import net.minecraft.src.NetHandler;
import net.minecraft.src.Packet;

public class Packet25
extends Packet {
    public int entityId;
    public int xPosition;
    public int yPosition;
    public int zPosition;
    public int direction;
    public String title;

    public Packet25() {
    }

    public Packet25(EntityPainting var1) {
        this.entityId = var1.entityId;
        this.xPosition = var1.xPosition;
        this.yPosition = var1.yPosition;
        this.zPosition = var1.zPosition;
        this.direction = var1.direction;
        this.title = var1.art.title;
    }

    @Override
    public void readPacketData(DataInputStream var1) throws IOException {
        this.entityId = var1.readInt();
        this.title = var1.readUTF();
        this.xPosition = var1.readInt();
        this.yPosition = var1.readInt();
        this.zPosition = var1.readInt();
        this.direction = var1.readInt();
    }

    @Override
    public void writePacketData(DataOutputStream var1) throws IOException {
        var1.writeInt(this.entityId);
        var1.writeUTF(this.title);
        var1.writeInt(this.xPosition);
        var1.writeInt(this.yPosition);
        var1.writeInt(this.zPosition);
        var1.writeInt(this.direction);
    }

    @Override
    public void processPacket(NetHandler var1) {
        var1.func_21146_a(this);
    }

    @Override
    public int getPacketSize() {
        return 24;
    }
}

