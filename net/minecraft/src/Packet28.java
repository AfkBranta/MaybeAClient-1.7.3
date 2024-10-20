/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import net.minecraft.src.Entity;
import net.minecraft.src.NetHandler;
import net.minecraft.src.Packet;

public class Packet28
extends Packet {
    public int entityId;
    public int motionX;
    public int motionY;
    public int motionZ;

    public Packet28() {
    }

    public Packet28(Entity var1) {
        this(var1.entityId, var1.motionX, var1.motionY, var1.motionZ);
    }

    public Packet28(int var1, double var2, double var4, double var6) {
        this.entityId = var1;
        double var8 = 3.9;
        if (var2 < -var8) {
            var2 = -var8;
        }
        if (var4 < -var8) {
            var4 = -var8;
        }
        if (var6 < -var8) {
            var6 = -var8;
        }
        if (var2 > var8) {
            var2 = var8;
        }
        if (var4 > var8) {
            var4 = var8;
        }
        if (var6 > var8) {
            var6 = var8;
        }
        this.motionX = (int)(var2 * 8000.0);
        this.motionY = (int)(var4 * 8000.0);
        this.motionZ = (int)(var6 * 8000.0);
    }

    @Override
    public void readPacketData(DataInputStream var1) throws IOException {
        this.entityId = var1.readInt();
        this.motionX = var1.readShort();
        this.motionY = var1.readShort();
        this.motionZ = var1.readShort();
    }

    @Override
    public void writePacketData(DataOutputStream var1) throws IOException {
        var1.writeInt(this.entityId);
        var1.writeShort(this.motionX);
        var1.writeShort(this.motionY);
        var1.writeShort(this.motionZ);
    }

    @Override
    public void processPacket(NetHandler var1) {
        var1.func_6498_a(this);
    }

    @Override
    public int getPacketSize() {
        return 10;
    }
}

