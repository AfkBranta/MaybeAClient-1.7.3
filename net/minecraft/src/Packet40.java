/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;
import net.minecraft.src.DataWatcher;
import net.minecraft.src.NetHandler;
import net.minecraft.src.Packet;

public class Packet40
extends Packet {
    public int entityId;
    private List field_21048_b;

    @Override
    public void readPacketData(DataInputStream var1) throws IOException {
        this.entityId = var1.readInt();
        this.field_21048_b = DataWatcher.readWatchableObjects(var1);
    }

    @Override
    public void writePacketData(DataOutputStream var1) throws IOException {
        var1.writeInt(this.entityId);
        DataWatcher.writeObjectsInListToStream(this.field_21048_b, var1);
    }

    @Override
    public void processPacket(NetHandler var1) {
        var1.func_21148_a(this);
    }

    @Override
    public int getPacketSize() {
        return 5;
    }

    public List func_21047_b() {
        return this.field_21048_b;
    }
}

