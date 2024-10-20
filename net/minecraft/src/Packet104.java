/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NetHandler;
import net.minecraft.src.Packet;

public class Packet104
extends Packet {
    public int windowId;
    public ItemStack[] itemStack;

    @Override
    public void readPacketData(DataInputStream var1) throws IOException {
        this.windowId = var1.readByte();
        int var2 = var1.readShort();
        this.itemStack = new ItemStack[var2];
        int var3 = 0;
        while (var3 < var2) {
            short var4 = var1.readShort();
            if (var4 >= 0) {
                byte var5 = var1.readByte();
                short var6 = var1.readShort();
                this.itemStack[var3] = new ItemStack(var4, (int)var5, (int)var6);
            }
            ++var3;
        }
    }

    @Override
    public void writePacketData(DataOutputStream var1) throws IOException {
        var1.writeByte(this.windowId);
        var1.writeShort(this.itemStack.length);
        int var2 = 0;
        while (var2 < this.itemStack.length) {
            if (this.itemStack[var2] == null) {
                var1.writeShort(-1);
            } else {
                var1.writeShort((short)this.itemStack[var2].itemID);
                var1.writeByte((byte)this.itemStack[var2].stackSize);
                var1.writeShort((short)this.itemStack[var2].getItemDamage());
            }
            ++var2;
        }
    }

    @Override
    public void processPacket(NetHandler var1) {
        var1.func_20094_a(this);
    }

    @Override
    public int getPacketSize() {
        return 3 + this.itemStack.length * 5;
    }
}

