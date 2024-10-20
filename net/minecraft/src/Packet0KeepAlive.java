package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import net.minecraft.src.NetHandler;
import net.minecraft.src.Packet;

public class Packet0KeepAlive
extends Packet {
    @Override
    public void processPacket(NetHandler var1) {
    }

    @Override
    public void readPacketData(DataInputStream var1) throws IOException {
    }

    @Override
    public void writePacketData(DataOutputStream var1) throws IOException {
    }

    @Override
    public int getPacketSize() {
        return 0;
    }
}

