package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import net.minecraft.src.EntityItem;
import net.minecraft.src.MathHelper;
import net.minecraft.src.NetHandler;
import net.minecraft.src.Packet;

public class Packet21PickupSpawn
extends Packet {
    public int entityId;
    public int xPosition;
    public int yPosition;
    public int zPosition;
    public byte rotation;
    public byte pitch;
    public byte roll;
    public int itemID;
    public int count;
    public int itemDamage;

    public Packet21PickupSpawn() {
    }

    public Packet21PickupSpawn(EntityItem var1) {
        this.entityId = var1.entityId;
        this.itemID = var1.item.itemID;
        this.count = var1.item.stackSize;
        this.itemDamage = var1.item.getItemDamage();
        this.xPosition = MathHelper.floor_double(var1.posX * 32.0);
        this.yPosition = MathHelper.floor_double(var1.posY * 32.0);
        this.zPosition = MathHelper.floor_double(var1.posZ * 32.0);
        this.rotation = (byte)(var1.motionX * 128.0);
        this.pitch = (byte)(var1.motionY * 128.0);
        this.roll = (byte)(var1.motionZ * 128.0);
    }

    @Override
    public void readPacketData(DataInputStream var1) throws IOException {
        this.entityId = var1.readInt();
        this.itemID = var1.readShort();
        this.count = var1.readByte();
        this.itemDamage = var1.readShort();
        this.xPosition = var1.readInt();
        this.yPosition = var1.readInt();
        this.zPosition = var1.readInt();
        this.rotation = var1.readByte();
        this.pitch = var1.readByte();
        this.roll = var1.readByte();
    }

    @Override
    public void writePacketData(DataOutputStream var1) throws IOException {
        var1.writeInt(this.entityId);
        var1.writeShort(this.itemID);
        var1.writeByte(this.count);
        var1.writeShort(this.itemDamage);
        var1.writeInt(this.xPosition);
        var1.writeInt(this.yPosition);
        var1.writeInt(this.zPosition);
        var1.writeByte(this.rotation);
        var1.writeByte(this.pitch);
        var1.writeByte(this.roll);
    }

    @Override
    public void processPacket(NetHandler var1) {
        var1.handlePickupSpawn(this);
    }

    @Override
    public int getPacketSize() {
        return 24;
    }
}

