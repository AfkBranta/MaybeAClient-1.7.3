/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.MathHelper;
import net.minecraft.src.NetHandler;
import net.minecraft.src.Packet;

public class Packet20NamedEntitySpawn
extends Packet {
    public int entityId;
    public String name;
    public int xPosition;
    public int yPosition;
    public int zPosition;
    public byte rotation;
    public byte pitch;
    public int currentItem;

    public Packet20NamedEntitySpawn() {
    }

    public Packet20NamedEntitySpawn(EntityPlayer var1) {
        this.entityId = var1.entityId;
        this.name = var1.username;
        this.xPosition = MathHelper.floor_double(var1.posX * 32.0);
        this.yPosition = MathHelper.floor_double(var1.posY * 32.0);
        this.zPosition = MathHelper.floor_double(var1.posZ * 32.0);
        this.rotation = (byte)(var1.rotationYaw * 256.0f / 360.0f);
        this.pitch = (byte)(var1.rotationPitch * 256.0f / 360.0f);
        ItemStack var2 = var1.inventory.getCurrentItem();
        this.currentItem = var2 == null ? 0 : var2.itemID;
    }

    @Override
    public void readPacketData(DataInputStream var1) throws IOException {
        this.entityId = var1.readInt();
        this.name = var1.readUTF();
        this.xPosition = var1.readInt();
        this.yPosition = var1.readInt();
        this.zPosition = var1.readInt();
        this.rotation = var1.readByte();
        this.pitch = var1.readByte();
        this.currentItem = var1.readShort();
    }

    @Override
    public void writePacketData(DataOutputStream var1) throws IOException {
        var1.writeInt(this.entityId);
        var1.writeUTF(this.name);
        var1.writeInt(this.xPosition);
        var1.writeInt(this.yPosition);
        var1.writeInt(this.zPosition);
        var1.writeByte(this.rotation);
        var1.writeByte(this.pitch);
        var1.writeShort(this.currentItem);
    }

    @Override
    public void processPacket(NetHandler var1) {
        var1.handleNamedEntitySpawn(this);
    }

    @Override
    public int getPacketSize() {
        return 28;
    }
}

