/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.Packet;
import net.minecraft.src.Packet100;
import net.minecraft.src.Packet101;
import net.minecraft.src.Packet102;
import net.minecraft.src.Packet103;
import net.minecraft.src.Packet104;
import net.minecraft.src.Packet105;
import net.minecraft.src.Packet106;
import net.minecraft.src.Packet10Flying;
import net.minecraft.src.Packet130;
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

public class NetHandler {
    public void handleMapChunk(Packet51MapChunk var1) {
    }

    public void registerPacket(Packet var1) {
    }

    public void handleErrorMessage(String var1, Object[] var2) {
    }

    public void handleKickDisconnect(Packet255KickDisconnect var1) {
        this.registerPacket(var1);
    }

    public void handleLogin(Packet1Login var1) {
        this.registerPacket(var1);
    }

    public void handleFlying(Packet10Flying var1) {
        this.registerPacket(var1);
    }

    public void handleMultiBlockChange(Packet52MultiBlockChange var1) {
        this.registerPacket(var1);
    }

    public void handleBlockDig(Packet14BlockDig var1) {
        this.registerPacket(var1);
    }

    public void handleBlockChange(Packet53BlockChange var1) {
        this.registerPacket(var1);
    }

    public void handlePreChunk(Packet50PreChunk var1) {
        this.registerPacket(var1);
    }

    public void handleNamedEntitySpawn(Packet20NamedEntitySpawn var1) {
        this.registerPacket(var1);
    }

    public void handleEntity(Packet30Entity var1) {
        this.registerPacket(var1);
    }

    public void handleEntityTeleport(Packet34EntityTeleport var1) {
        this.registerPacket(var1);
    }

    public void handlePlace(Packet15Place var1) {
        this.registerPacket(var1);
    }

    public void handleBlockItemSwitch(Packet16BlockItemSwitch var1) {
        this.registerPacket(var1);
    }

    public void handleDestroyEntity(Packet29DestroyEntity var1) {
        this.registerPacket(var1);
    }

    public void handlePickupSpawn(Packet21PickupSpawn var1) {
        this.registerPacket(var1);
    }

    public void handleCollect(Packet22Collect var1) {
        this.registerPacket(var1);
    }

    public void handleChat(Packet3Chat var1) {
        this.registerPacket(var1);
    }

    public void handleVehicleSpawn(Packet23VehicleSpawn var1) {
        this.registerPacket(var1);
    }

    public void handleArmAnimation(Packet18ArmAnimation var1) {
        this.registerPacket(var1);
    }

    public void func_21147_a(Packet19 var1) {
        this.registerPacket(var1);
    }

    public void handleHandshake(Packet2Handshake var1) {
        this.registerPacket(var1);
    }

    public void handleMobSpawn(Packet24MobSpawn var1) {
        this.registerPacket(var1);
    }

    public void handleUpdateTime(Packet4UpdateTime var1) {
        this.registerPacket(var1);
    }

    public void handleSpawnPosition(Packet6SpawnPosition var1) {
        this.registerPacket(var1);
    }

    public void func_6498_a(Packet28 var1) {
        this.registerPacket(var1);
    }

    public void func_21148_a(Packet40 var1) {
        this.registerPacket(var1);
    }

    public void func_6497_a(Packet39 var1) {
        this.registerPacket(var1);
    }

    public void func_6499_a(Packet7 var1) {
        this.registerPacket(var1);
    }

    public void func_9447_a(Packet38 var1) {
        this.registerPacket(var1);
    }

    public void handleHealth(Packet8 var1) {
        this.registerPacket(var1);
    }

    public void func_9448_a(Packet9 var1) {
        this.registerPacket(var1);
    }

    public void func_12245_a(Packet60 var1) {
        this.registerPacket(var1);
    }

    public void func_20087_a(Packet100 var1) {
        this.registerPacket(var1);
    }

    public void func_20092_a(Packet101 var1) {
        this.registerPacket(var1);
    }

    public void func_20091_a(Packet102 var1) {
        this.registerPacket(var1);
    }

    public void func_20088_a(Packet103 var1) {
        this.registerPacket(var1);
    }

    public void func_20094_a(Packet104 var1) {
        this.registerPacket(var1);
    }

    public void func_20093_a(Packet130 var1) {
        this.registerPacket(var1);
    }

    public void func_20090_a(Packet105 var1) {
        this.registerPacket(var1);
    }

    public void handlePlayerInventory(Packet5PlayerInventory var1) {
        this.registerPacket(var1);
    }

    public void func_20089_a(Packet106 var1) {
        this.registerPacket(var1);
    }

    public void func_21146_a(Packet25 var1) {
        this.registerPacket(var1);
    }

    public void func_21145_a(Packet54 var1) {
        this.registerPacket(var1);
    }

    public void func_22186_a(Packet17Sleep var1) {
    }

    public void func_22185_a(Packet27 var1) {
    }

    public void func_25118_a(Packet70 var1) {
    }
}

