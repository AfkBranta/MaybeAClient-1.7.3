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

public class Packet103
extends Packet {
    public int windowId;
    public int itemSlot;
    public ItemStack myItemStack;

    @Override
    public void processPacket(NetHandler var1) {
        var1.func_20088_a(this);
    }

    @Override
    public void readPacketData(DataInputStream var1) throws IOException {
        this.windowId = var1.readByte();
        this.itemSlot = var1.readShort();
        short var2 = var1.readShort();
        if (var2 >= 0) {
            byte var3 = var1.readByte();
            short var4 = var1.readShort();
            this.myItemStack = new ItemStack(var2, (int)var3, (int)var4);
        } else {
            this.myItemStack = null;
        }
    }

    @Override
    public void writePacketData(DataOutputStream var1) throws IOException {
        var1.writeByte(this.windowId);
        var1.writeShort(this.itemSlot);
        if (this.myItemStack == null) {
            var1.writeShort(-1);
        } else {
            var1.writeShort(this.myItemStack.itemID);
            var1.writeByte(this.myItemStack.stackSize);
            var1.writeShort(this.myItemStack.getItemDamage());
        }
    }

    @Override
    public int getPacketSize() {
        return 8;
    }
}

