package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import net.minecraft.src.NetHandler;
import net.minecraft.src.Packet;

public class Packet23VehicleSpawn
extends Packet {
    public int entityId;
    public int xPosition;
    public int yPosition;
    public int zPosition;
    public int type;

    @Override
    public void readPacketData(DataInputStream var1) throws IOException {
        this.entityId = var1.readInt();
        this.type = var1.readByte();
        this.xPosition = var1.readInt();
        this.yPosition = var1.readInt();
        this.zPosition = var1.readInt();
    }

    @Override
    public void writePacketData(DataOutputStream var1) throws IOException {
        var1.writeInt(this.entityId);
        var1.writeByte(this.type);
        var1.writeInt(this.xPosition);
        var1.writeInt(this.yPosition);
        var1.writeInt(this.zPosition);
    }

    @Override
    public void processPacket(NetHandler var1) {
        var1.handleVehicleSpawn(this);
    }

    @Override
    public int getPacketSize() {
        return 17;
    }
}

