/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.src.NetHandler;
import net.minecraft.src.Packet0KeepAlive;
import net.minecraft.src.Packet100;
import net.minecraft.src.Packet101;
import net.minecraft.src.Packet102;
import net.minecraft.src.Packet103;
import net.minecraft.src.Packet104;
import net.minecraft.src.Packet105;
import net.minecraft.src.Packet106;
import net.minecraft.src.Packet10Flying;
import net.minecraft.src.Packet11PlayerPosition;
import net.minecraft.src.Packet12PlayerLook;
import net.minecraft.src.Packet130;
import net.minecraft.src.Packet13PlayerLookMove;
import net.minecraft.src.Packet14BlockDig;
import net.minecraft.src.Packet15Place;
import net.minecraft.src.Packet16BlockItemSwitch;
import net.minecraft.src.Packet17Sleep;
import net.minecraft.src.Packet18ArmAnimation;
import net.minecraft.src.Packet19;
import net.minecraft.src.Packet1Login;
import net.minecraft.src.Packet20NamedEntitySpawn;
import net.minecraft.src.Packet21PickupSpawn;
import net.minecraft.src.Packet22Collect;
import net.minecraft.src.Packet23VehicleSpawn;
import net.minecraft.src.Packet24MobSpawn;
import net.minecraft.src.Packet25;
import net.minecraft.src.Packet255KickDisconnect;
import net.minecraft.src.Packet27;
import net.minecraft.src.Packet28;
import net.minecraft.src.Packet29DestroyEntity;
import net.minecraft.src.Packet2Handshake;
import net.minecraft.src.Packet30Entity;
import net.minecraft.src.Packet31RelEntityMove;
import net.minecraft.src.Packet32EntityLook;
import net.minecraft.src.Packet33RelEntityMoveLook;
import net.minecraft.src.Packet34EntityTeleport;
import net.minecraft.src.Packet38;
import net.minecraft.src.Packet39;
import net.minecraft.src.Packet3Chat;
import net.minecraft.src.Packet40;
import net.minecraft.src.Packet4UpdateTime;
import net.minecraft.src.Packet50PreChunk;
import net.minecraft.src.Packet51MapChunk;
import net.minecraft.src.Packet52MultiBlockChange;
import net.minecraft.src.Packet53BlockChange;
import net.minecraft.src.Packet54;
import net.minecraft.src.Packet5PlayerInventory;
import net.minecraft.src.Packet60;
import net.minecraft.src.Packet6SpawnPosition;
import net.minecraft.src.Packet7;
import net.minecraft.src.Packet70;
import net.minecraft.src.Packet8;
import net.minecraft.src.Packet9;
import net.minecraft.src.PacketCounter;

public abstract class Packet {
    private static Map packetIdToClassMap = new HashMap();
    private static Map packetClassToIdMap = new HashMap();
    public final long creationTimeMillis = System.currentTimeMillis();
    public boolean isChunkDataPacket = false;
    private static HashMap packetStats;
    private static int totalPacketsCount;

    static {
        Packet.addIdClassMapping(0, Packet0KeepAlive.class);
        Packet.addIdClassMapping(1, Packet1Login.class);
        Packet.addIdClassMapping(2, Packet2Handshake.class);
        Packet.addIdClassMapping(3, Packet3Chat.class);
        Packet.addIdClassMapping(4, Packet4UpdateTime.class);
        Packet.addIdClassMapping(5, Packet5PlayerInventory.class);
        Packet.addIdClassMapping(6, Packet6SpawnPosition.class);
        Packet.addIdClassMapping(7, Packet7.class);
        Packet.addIdClassMapping(8, Packet8.class);
        Packet.addIdClassMapping(9, Packet9.class);
        Packet.addIdClassMapping(10, Packet10Flying.class);
        Packet.addIdClassMapping(11, Packet11PlayerPosition.class);
        Packet.addIdClassMapping(12, Packet12PlayerLook.class);
        Packet.addIdClassMapping(13, Packet13PlayerLookMove.class);
        Packet.addIdClassMapping(14, Packet14BlockDig.class);
        Packet.addIdClassMapping(15, Packet15Place.class);
        Packet.addIdClassMapping(16, Packet16BlockItemSwitch.class);
        Packet.addIdClassMapping(17, Packet17Sleep.class);
        Packet.addIdClassMapping(18, Packet18ArmAnimation.class);
        Packet.addIdClassMapping(19, Packet19.class);
        Packet.addIdClassMapping(20, Packet20NamedEntitySpawn.class);
        Packet.addIdClassMapping(21, Packet21PickupSpawn.class);
        Packet.addIdClassMapping(22, Packet22Collect.class);
        Packet.addIdClassMapping(23, Packet23VehicleSpawn.class);
        Packet.addIdClassMapping(24, Packet24MobSpawn.class);
        Packet.addIdClassMapping(25, Packet25.class);
        Packet.addIdClassMapping(27, Packet27.class);
        Packet.addIdClassMapping(28, Packet28.class);
        Packet.addIdClassMapping(29, Packet29DestroyEntity.class);
        Packet.addIdClassMapping(30, Packet30Entity.class);
        Packet.addIdClassMapping(31, Packet31RelEntityMove.class);
        Packet.addIdClassMapping(32, Packet32EntityLook.class);
        Packet.addIdClassMapping(33, Packet33RelEntityMoveLook.class);
        Packet.addIdClassMapping(34, Packet34EntityTeleport.class);
        Packet.addIdClassMapping(38, Packet38.class);
        Packet.addIdClassMapping(39, Packet39.class);
        Packet.addIdClassMapping(40, Packet40.class);
        Packet.addIdClassMapping(50, Packet50PreChunk.class);
        Packet.addIdClassMapping(51, Packet51MapChunk.class);
        Packet.addIdClassMapping(52, Packet52MultiBlockChange.class);
        Packet.addIdClassMapping(53, Packet53BlockChange.class);
        Packet.addIdClassMapping(54, Packet54.class);
        Packet.addIdClassMapping(60, Packet60.class);
        Packet.addIdClassMapping(70, Packet70.class);
        Packet.addIdClassMapping(100, Packet100.class);
        Packet.addIdClassMapping(101, Packet101.class);
        Packet.addIdClassMapping(102, Packet102.class);
        Packet.addIdClassMapping(103, Packet103.class);
        Packet.addIdClassMapping(104, Packet104.class);
        Packet.addIdClassMapping(105, Packet105.class);
        Packet.addIdClassMapping(106, Packet106.class);
        Packet.addIdClassMapping(130, Packet130.class);
        Packet.addIdClassMapping(255, Packet255KickDisconnect.class);
        packetStats = new HashMap();
        totalPacketsCount = 0;
    }

    static void addIdClassMapping(int var0, Class var1) {
        if (packetIdToClassMap.containsKey(var0)) {
            throw new IllegalArgumentException("Duplicate packet id:" + var0);
        }
        if (packetClassToIdMap.containsKey(var1)) {
            throw new IllegalArgumentException("Duplicate packet class:" + var1);
        }
        packetIdToClassMap.put(var0, var1);
        packetClassToIdMap.put(var1, var0);
    }

    public static Packet getNewPacket(int var0) {
        try {
            Class var1 = (Class)packetIdToClassMap.get(var0);
            return var1 == null ? null : (Packet)var1.newInstance();
        }
        catch (Exception var2) {
            var2.printStackTrace();
            System.out.println("Skipping packet with id " + var0);
            return null;
        }
    }

    public final int getPacketId() {
        return (Integer)packetClassToIdMap.get(this.getClass());
    }

    public static Packet readPacket(DataInputStream var0) throws IOException {
        int var5;
        Packet var2;
        block5: {
            boolean var1 = false;
            var2 = null;
            try {
                var5 = var0.read();
                if (var5 != -1) break block5;
                return null;
            }
            catch (EOFException var4) {
                System.out.println("Reached end of stream");
                return null;
            }
        }
        var2 = Packet.getNewPacket(var5);
        if (var2 == null) {
            throw new IOException("Bad packet id " + var5);
        }
        var2.readPacketData(var0);
        PacketCounter var3 = (PacketCounter)packetStats.get(var5);
        if (var3 == null) {
            var3 = new PacketCounter(null);
            packetStats.put(var5, var3);
        }
        var3.addPacket(var2.getPacketSize());
        int cfr_ignored_0 = ++totalPacketsCount % 1000;
        return var2;
    }

    public static void writePacket(Packet var0, DataOutputStream var1) throws IOException {
        var1.write(var0.getPacketId());
        var0.writePacketData(var1);
    }

    public abstract void readPacketData(DataInputStream var1) throws IOException;

    public abstract void writePacketData(DataOutputStream var1) throws IOException;

    public abstract void processPacket(NetHandler var1);

    public abstract int getPacketSize();
}

