/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.src.Block;
import net.minecraft.src.Chunk;
import net.minecraft.src.ChunkCoordinates;
import net.minecraft.src.CraftingInventoryCB;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityArrow;
import net.minecraft.src.EntityBoat;
import net.minecraft.src.EntityEgg;
import net.minecraft.src.EntityFallingSand;
import net.minecraft.src.EntityFish;
import net.minecraft.src.EntityItem;
import net.minecraft.src.EntityList;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityMinecart;
import net.minecraft.src.EntityOtherPlayerMP;
import net.minecraft.src.EntityPainting;
import net.minecraft.src.EntityPickupFX;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntityPlayerSP;
import net.minecraft.src.EntitySnowball;
import net.minecraft.src.EntityTNTPrimed;
import net.minecraft.src.Explosion;
import net.minecraft.src.GuiConnectFailed;
import net.minecraft.src.GuiDownloadTerrain;
import net.minecraft.src.InventoryBasic;
import net.minecraft.src.ItemStack;
import net.minecraft.src.MathHelper;
import net.minecraft.src.NetHandler;
import net.minecraft.src.NetworkManager;
import net.minecraft.src.Packet;
import net.minecraft.src.Packet100;
import net.minecraft.src.Packet101;
import net.minecraft.src.Packet103;
import net.minecraft.src.Packet104;
import net.minecraft.src.Packet105;
import net.minecraft.src.Packet106;
import net.minecraft.src.Packet10Flying;
import net.minecraft.src.Packet130;
import net.minecraft.src.Packet17Sleep;
import net.minecraft.src.Packet18ArmAnimation;
import net.minecraft.src.Packet1Login;
import net.minecraft.src.Packet20NamedEntitySpawn;
import net.minecraft.src.Packet21PickupSpawn;
import net.minecraft.src.Packet22Collect;
import net.minecraft.src.Packet23VehicleSpawn;
import net.minecraft.src.Packet24MobSpawn;
import net.minecraft.src.Packet25;
import net.minecraft.src.Packet255KickDisconnect;
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
import net.minecraft.src.Packet70;
import net.minecraft.src.Packet8;
import net.minecraft.src.Packet9;
import net.minecraft.src.PlayerControllerMP;
import net.minecraft.src.StatList;
import net.minecraft.src.TileEntity;
import net.minecraft.src.TileEntityDispenser;
import net.minecraft.src.TileEntityFurnace;
import net.minecraft.src.TileEntitySign;
import net.minecraft.src.WorldClient;

public class NetClientHandler
extends NetHandler {
    public boolean disconnected = false;
    public NetworkManager netManager;
    public String field_1209_a;
    public Minecraft mc;
    public WorldClient worldClient;
    public boolean field_1210_g = false;
    Random rand = new Random();
    public String ip;
    public int port;

    public NetClientHandler(Minecraft var1, String var2, int var3) throws UnknownHostException, IOException {
        this.mc = var1;
        Socket var4 = new Socket(InetAddress.getByName(var2), var3);
        this.ip = var2;
        this.port = var3;
        this.netManager = new NetworkManager(var4, "Client", this);
    }

    public void processReadPackets() {
        if (!this.disconnected) {
            this.netManager.processReadPackets();
        }
    }

    @Override
    public void handleLogin(Packet1Login var1) {
        this.mc.playerController = new PlayerControllerMP(this.mc, this);
        this.mc.field_25001_G.func_25100_a(StatList.field_25181_h, 1);
        this.worldClient = new WorldClient(this, var1.mapSeed, var1.dimension);
        this.worldClient.multiplayerWorld = true;
        this.mc.changeWorld1(this.worldClient);
        this.mc.displayGuiScreen(new GuiDownloadTerrain(this));
        this.mc.thePlayer.entityId = var1.protocolVersion;
    }

    @Override
    public void handlePickupSpawn(Packet21PickupSpawn var1) {
        double var2 = (double)var1.xPosition / 32.0;
        double var4 = (double)var1.yPosition / 32.0;
        double var6 = (double)var1.zPosition / 32.0;
        EntityItem var8 = new EntityItem(this.worldClient, var2, var4, var6, new ItemStack(var1.itemID, var1.count, var1.itemDamage));
        var8.motionX = (double)var1.rotation / 128.0;
        var8.motionY = (double)var1.pitch / 128.0;
        var8.motionZ = (double)var1.roll / 128.0;
        var8.serverPosX = var1.xPosition;
        var8.serverPosY = var1.yPosition;
        var8.serverPosZ = var1.zPosition;
        this.worldClient.func_712_a(var1.entityId, var8);
    }

    @Override
    public void handleVehicleSpawn(Packet23VehicleSpawn var1) {
        double var2 = (double)var1.xPosition / 32.0;
        double var4 = (double)var1.yPosition / 32.0;
        double var6 = (double)var1.zPosition / 32.0;
        Entity var8 = null;
        if (var1.type == 10) {
            var8 = new EntityMinecart(this.worldClient, var2, var4, var6, 0);
        }
        if (var1.type == 11) {
            var8 = new EntityMinecart(this.worldClient, var2, var4, var6, 1);
        }
        if (var1.type == 12) {
            var8 = new EntityMinecart(this.worldClient, var2, var4, var6, 2);
        }
        if (var1.type == 90) {
            var8 = new EntityFish(this.worldClient, var2, var4, var6);
        }
        if (var1.type == 60) {
            var8 = new EntityArrow(this.worldClient, var2, var4, var6);
        }
        if (var1.type == 61) {
            var8 = new EntitySnowball(this.worldClient, var2, var4, var6);
        }
        if (var1.type == 62) {
            var8 = new EntityEgg(this.worldClient, var2, var4, var6);
        }
        if (var1.type == 1) {
            var8 = new EntityBoat(this.worldClient, var2, var4, var6);
        }
        if (var1.type == 50) {
            var8 = new EntityTNTPrimed(this.worldClient, var2, var4, var6);
        }
        if (var1.type == 70) {
            var8 = new EntityFallingSand(this.worldClient, var2, var4, var6, Block.sand.blockID);
        }
        if (var1.type == 71) {
            var8 = new EntityFallingSand(this.worldClient, var2, var4, var6, Block.gravel.blockID);
        }
        if (var8 != null) {
            ((Entity)var8).serverPosX = var1.xPosition;
            ((Entity)var8).serverPosY = var1.yPosition;
            ((Entity)var8).serverPosZ = var1.zPosition;
            ((Entity)var8).rotationYaw = 0.0f;
            ((Entity)var8).rotationPitch = 0.0f;
            ((Entity)var8).entityId = var1.entityId;
            this.worldClient.func_712_a(var1.entityId, var8);
        }
    }

    @Override
    public void func_21146_a(Packet25 var1) {
        EntityPainting var2 = new EntityPainting(this.worldClient, var1.xPosition, var1.yPosition, var1.zPosition, var1.direction, var1.title);
        this.worldClient.func_712_a(var1.entityId, var2);
    }

    @Override
    public void func_6498_a(Packet28 var1) {
        Entity var2 = this.getEntityByID(var1.entityId);
        if (var2 != null) {
            var2.setVelocity((double)var1.motionX / 8000.0, (double)var1.motionY / 8000.0, (double)var1.motionZ / 8000.0);
        }
    }

    @Override
    public void func_21148_a(Packet40 var1) {
        Entity var2 = this.getEntityByID(var1.entityId);
        if (var2 != null && var1.func_21047_b() != null) {
            var2.getDataWatcher().updateWatchedObjectsFromList(var1.func_21047_b());
        }
    }

    @Override
    public void handleNamedEntitySpawn(Packet20NamedEntitySpawn var1) {
        double var2 = (double)var1.xPosition / 32.0;
        double var4 = (double)var1.yPosition / 32.0;
        double var6 = (double)var1.zPosition / 32.0;
        float var8 = (float)(var1.rotation * 360) / 256.0f;
        float var9 = (float)(var1.pitch * 360) / 256.0f;
        EntityOtherPlayerMP var10 = new EntityOtherPlayerMP(this.mc.theWorld, var1.name);
        var10.serverPosX = var1.xPosition;
        var10.serverPosY = var1.yPosition;
        var10.serverPosZ = var1.zPosition;
        int var11 = var1.currentItem;
        var10.inventory.mainInventory[var10.inventory.currentItem] = var11 == 0 ? null : new ItemStack(var11, 1, 0);
        var10.setPositionAndRotation(var2, var4, var6, var8, var9);
        this.worldClient.func_712_a(var1.entityId, var10);
    }

    @Override
    public void handleEntityTeleport(Packet34EntityTeleport var1) {
        Entity var2 = this.getEntityByID(var1.entityId);
        if (var2 != null) {
            var2.serverPosX = var1.xPosition;
            var2.serverPosY = var1.yPosition;
            var2.serverPosZ = var1.zPosition;
            double var3 = (double)var2.serverPosX / 32.0;
            double var5 = (double)var2.serverPosY / 32.0 + 0.015625;
            double var7 = (double)var2.serverPosZ / 32.0;
            float var9 = (float)(var1.yaw * 360) / 256.0f;
            float var10 = (float)(var1.pitch * 360) / 256.0f;
            var2.setPositionAndRotation2(var3, var5, var7, var9, var10, 3);
        }
    }

    @Override
    public void handleEntity(Packet30Entity var1) {
        Entity var2 = this.getEntityByID(var1.entityId);
        if (var2 != null) {
            var2.serverPosX += var1.xPosition;
            var2.serverPosY += var1.yPosition;
            var2.serverPosZ += var1.zPosition;
            double var3 = (double)var2.serverPosX / 32.0;
            double var5 = (double)var2.serverPosY / 32.0 + 0.015625;
            double var7 = (double)var2.serverPosZ / 32.0;
            float var9 = var1.rotating ? (float)(var1.yaw * 360) / 256.0f : var2.rotationYaw;
            float var10 = var1.rotating ? (float)(var1.pitch * 360) / 256.0f : var2.rotationPitch;
            var2.setPositionAndRotation2(var3, var5, var7, var9, var10, 3);
        }
    }

    @Override
    public void handleDestroyEntity(Packet29DestroyEntity var1) {
        this.worldClient.removeEntityFromWorld(var1.entityId);
    }

    @Override
    public void handleFlying(Packet10Flying var1) {
        EntityPlayerSP var2 = this.mc.thePlayer;
        double var3 = var2.posX;
        double var5 = var2.posY;
        double var7 = var2.posZ;
        float var9 = var2.rotationYaw;
        float var10 = var2.rotationPitch;
        if (var1.moving) {
            var3 = var1.xPosition;
            var5 = var1.yPosition;
            var7 = var1.zPosition;
        }
        if (var1.rotating) {
            var9 = var1.yaw;
            var10 = var1.pitch;
        }
        var2.ySize = 0.0f;
        var2.motionZ = 0.0;
        var2.motionY = 0.0;
        var2.motionX = 0.0;
        var2.setPositionAndRotation(var3, var5, var7, var9, var10);
        var1.xPosition = var2.posX;
        var1.yPosition = var2.boundingBox.minY;
        var1.zPosition = var2.posZ;
        var1.stance = var2.posY;
        this.netManager.addToSendQueue(var1);
        if (!this.field_1210_g) {
            this.mc.thePlayer.prevPosX = this.mc.thePlayer.posX;
            this.mc.thePlayer.prevPosY = this.mc.thePlayer.posY;
            this.mc.thePlayer.prevPosZ = this.mc.thePlayer.posZ;
            this.field_1210_g = true;
            this.mc.displayGuiScreen(null);
        }
    }

    @Override
    public void handlePreChunk(Packet50PreChunk var1) {
        this.worldClient.func_713_a(var1.xPosition, var1.yPosition, var1.mode);
    }

    @Override
    public void handleMultiBlockChange(Packet52MultiBlockChange var1) {
        Chunk var2 = this.worldClient.getChunkFromChunkCoords(var1.xPosition, var1.zPosition);
        int var3 = var1.xPosition * 16;
        int var4 = var1.zPosition * 16;
        int var5 = 0;
        while (var5 < var1.size) {
            short var6 = var1.coordinateArray[var5];
            int var7 = var1.typeArray[var5] & 0xFF;
            byte var8 = var1.metadataArray[var5];
            int var9 = var6 >> 12 & 0xF;
            int var10 = var6 >> 8 & 0xF;
            int var11 = var6 & 0xFF;
            var2.setBlockIDWithMetadata(var9, var11, var10, var7, var8);
            this.worldClient.func_711_c(var9 + var3, var11, var10 + var4, var9 + var3, var11, var10 + var4);
            this.worldClient.markBlocksDirty(var9 + var3, var11, var10 + var4, var9 + var3, var11, var10 + var4);
            ++var5;
        }
    }

    @Override
    public void handleMapChunk(Packet51MapChunk var1) {
        this.worldClient.func_711_c(var1.xPosition, var1.yPosition, var1.zPosition, var1.xPosition + var1.xSize - 1, var1.yPosition + var1.ySize - 1, var1.zPosition + var1.zSize - 1);
        this.worldClient.setChunkData(var1.xPosition, var1.yPosition, var1.zPosition, var1.xSize, var1.ySize, var1.zSize, var1.chunk);
    }

    @Override
    public void handleBlockChange(Packet53BlockChange var1) {
        this.worldClient.func_714_c(var1.xPosition, var1.yPosition, var1.zPosition, var1.type, var1.metadata);
    }

    @Override
    public void handleKickDisconnect(Packet255KickDisconnect var1) {
        this.netManager.networkShutdown("disconnect.kicked", new Object[0]);
        this.disconnected = true;
        this.mc.changeWorld1(null);
        this.mc.displayGuiScreen(new GuiConnectFailed("disconnect.disconnected", "disconnect.genericReason", var1.reason));
    }

    @Override
    public void handleErrorMessage(String var1, Object[] var2) {
        if (!this.disconnected) {
            this.disconnected = true;
            this.mc.changeWorld1(null);
            this.mc.displayGuiScreen(new GuiConnectFailed("disconnect.lost", var1, var2));
        }
    }

    public void addToSendQueue(Packet var1) {
        if (!this.disconnected) {
            this.netManager.addToSendQueue(var1);
        }
    }

    @Override
    public void handleCollect(Packet22Collect var1) {
        Entity var2 = this.getEntityByID(var1.collectedEntityId);
        EntityLiving var3 = (EntityLiving)this.getEntityByID(var1.collectorEntityId);
        if (var3 == null) {
            var3 = this.mc.thePlayer;
        }
        if (var2 != null) {
            this.worldClient.playSoundAtEntity(var2, "random.pop", 0.2f, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7f + 1.0f) * 2.0f);
            this.mc.effectRenderer.addEffect(new EntityPickupFX(this.mc.theWorld, var2, var3, -0.5f));
            this.worldClient.removeEntityFromWorld(var1.collectedEntityId);
        }
    }

    @Override
    public void handleChat(Packet3Chat var1) {
        this.mc.ingameGUI.addChatMessage(var1.message);
    }

    @Override
    public void handleArmAnimation(Packet18ArmAnimation var1) {
        Entity var2 = this.getEntityByID(var1.entityId);
        if (var2 != null) {
            if (var1.animate == 1) {
                EntityPlayer var3 = (EntityPlayer)var2;
                var3.swingItem();
            } else if (var1.animate == 2) {
                var2.performHurtAnimation();
            } else if (var1.animate == 3) {
                EntityPlayer var3 = (EntityPlayer)var2;
                var3.wakeUpPlayer(false, false, false);
            } else if (var1.animate == 4) {
                EntityPlayer var3 = (EntityPlayer)var2;
                var3.func_6420_o();
            }
        }
    }

    @Override
    public void func_22186_a(Packet17Sleep var1) {
        Entity var2 = this.getEntityByID(var1.field_22045_a);
        if (var2 != null && var1.field_22046_e == 0) {
            EntityPlayer var3 = (EntityPlayer)var2;
            var3.sleepInBedAt(var1.field_22044_b, var1.field_22048_c, var1.field_22047_d);
        }
    }

    @Override
    public void handleHandshake(Packet2Handshake var1) {
        if (var1.username.equals("-")) {
            this.addToSendQueue(new Packet1Login(this.mc.session.username, "Password", 10));
        } else {
            try {
                URL var2 = new URL("http://www.minecraft.net/game/joinserver.jsp?user=" + this.mc.session.username + "&sessionId=" + this.mc.session.sessionId + "&serverId=" + var1.username);
                BufferedReader var3 = new BufferedReader(new InputStreamReader(var2.openStream()));
                String var4 = var3.readLine();
                var3.close();
                if (var4.equalsIgnoreCase("ok")) {
                    this.addToSendQueue(new Packet1Login(this.mc.session.username, "Password", 10));
                } else {
                    this.netManager.networkShutdown("disconnect.loginFailedInfo", var4);
                }
            }
            catch (Exception var5) {
                var5.printStackTrace();
                this.netManager.networkShutdown("disconnect.genericReason", "Internal client error: " + var5.toString());
            }
        }
    }

    public void disconnect() {
        this.disconnected = true;
        this.netManager.networkShutdown("disconnect.closed", new Object[0]);
    }

    @Override
    public void handleMobSpawn(Packet24MobSpawn var1) {
        double var2 = (double)var1.xPosition / 32.0;
        double var4 = (double)var1.yPosition / 32.0;
        double var6 = (double)var1.zPosition / 32.0;
        float var8 = (float)(var1.yaw * 360) / 256.0f;
        float var9 = (float)(var1.pitch * 360) / 256.0f;
        EntityLiving var10 = (EntityLiving)EntityList.createEntity(var1.type, this.mc.theWorld);
        var10.serverPosX = var1.xPosition;
        var10.serverPosY = var1.yPosition;
        var10.serverPosZ = var1.zPosition;
        var10.entityId = var1.entityId;
        var10.setPositionAndRotation(var2, var4, var6, var8, var9);
        var10.field_9343_G = true;
        this.worldClient.func_712_a(var1.entityId, var10);
        List var11 = var1.getMetadata();
        if (var11 != null) {
            var10.getDataWatcher().updateWatchedObjectsFromList(var11);
        }
    }

    @Override
    public void handleUpdateTime(Packet4UpdateTime var1) {
        this.mc.theWorld.setWorldTime(var1.time);
    }

    @Override
    public void handleSpawnPosition(Packet6SpawnPosition var1) {
        this.mc.thePlayer.setPlayerSpawnCoordinate(new ChunkCoordinates(var1.xPosition, var1.yPosition, var1.zPosition));
    }

    @Override
    public void func_6497_a(Packet39 var1) {
        Entity var2 = this.getEntityByID(var1.entityId);
        Entity var3 = this.getEntityByID(var1.vehicleEntityId);
        if (var1.entityId == this.mc.thePlayer.entityId) {
            var2 = this.mc.thePlayer;
        }
        if (var2 != null) {
            var2.mountEntity(var3);
        }
    }

    @Override
    public void func_9447_a(Packet38 var1) {
        Entity var2 = this.getEntityByID(var1.entityId);
        if (var2 != null) {
            var2.handleHealthUpdate(var1.entityStatus);
        }
    }

    private Entity getEntityByID(int var1) {
        return var1 == this.mc.thePlayer.entityId ? this.mc.thePlayer : this.worldClient.func_709_b(var1);
    }

    @Override
    public void handleHealth(Packet8 var1) {
        this.mc.thePlayer.setHealth(var1.healthMP);
    }

    @Override
    public void func_9448_a(Packet9 var1) {
        this.mc.respawn(true);
    }

    @Override
    public void func_12245_a(Packet60 var1) {
        Explosion var2 = new Explosion(this.mc.theWorld, null, var1.explosionX, var1.explosionY, var1.explosionZ, var1.explosionSize);
        var2.destroyedBlockPositions = var1.destroyedBlockPositions;
        var2.doExplosionB();
    }

    @Override
    public void func_20087_a(Packet100 var1) {
        if (var1.inventoryType == 0) {
            InventoryBasic var2 = new InventoryBasic(var1.windowTitle, var1.slotsCount);
            this.mc.thePlayer.displayGUIChest(var2);
            this.mc.thePlayer.craftingInventory.windowId = var1.windowId;
        } else if (var1.inventoryType == 2) {
            TileEntityFurnace var3 = new TileEntityFurnace();
            this.mc.thePlayer.displayGUIFurnace(var3);
            this.mc.thePlayer.craftingInventory.windowId = var1.windowId;
        } else if (var1.inventoryType == 3) {
            TileEntityDispenser var4 = new TileEntityDispenser();
            this.mc.thePlayer.displayGUIDispenser(var4);
            this.mc.thePlayer.craftingInventory.windowId = var1.windowId;
        } else if (var1.inventoryType == 1) {
            EntityPlayerSP var5 = this.mc.thePlayer;
            this.mc.thePlayer.displayWorkbenchGUI(MathHelper.floor_double(var5.posX), MathHelper.floor_double(var5.posY), MathHelper.floor_double(var5.posZ));
            this.mc.thePlayer.craftingInventory.windowId = var1.windowId;
        }
    }

    @Override
    public void func_20088_a(Packet103 var1) {
        if (var1.windowId == -1) {
            this.mc.thePlayer.inventory.setItemStack(var1.myItemStack);
        } else if (var1.windowId == 0) {
            this.mc.thePlayer.inventorySlots.putStackInSlot(var1.itemSlot, var1.myItemStack);
        } else if (var1.windowId == this.mc.thePlayer.craftingInventory.windowId) {
            this.mc.thePlayer.craftingInventory.putStackInSlot(var1.itemSlot, var1.myItemStack);
        }
    }

    @Override
    public void func_20089_a(Packet106 var1) {
        CraftingInventoryCB var2 = null;
        if (var1.windowId == 0) {
            var2 = this.mc.thePlayer.inventorySlots;
        } else if (var1.windowId == this.mc.thePlayer.craftingInventory.windowId) {
            var2 = this.mc.thePlayer.craftingInventory;
        }
        if (var2 != null) {
            if (var1.field_20030_c) {
                var2.func_20113_a(var1.field_20028_b);
            } else {
                var2.func_20110_b(var1.field_20028_b);
                this.addToSendQueue(new Packet106(var1.windowId, var1.field_20028_b, true));
            }
        }
    }

    @Override
    public void func_20094_a(Packet104 var1) {
        if (var1.windowId == 0) {
            this.mc.thePlayer.inventorySlots.putStacksInSlots(var1.itemStack);
        } else if (var1.windowId == this.mc.thePlayer.craftingInventory.windowId) {
            this.mc.thePlayer.craftingInventory.putStacksInSlots(var1.itemStack);
        }
    }

    @Override
    public void func_20093_a(Packet130 var1) {
        TileEntity var2;
        if (this.mc.theWorld.blockExists(var1.xPosition, var1.yPosition, var1.zPosition) && (var2 = this.mc.theWorld.getBlockTileEntity(var1.xPosition, var1.yPosition, var1.zPosition)) instanceof TileEntitySign) {
            TileEntitySign var3 = (TileEntitySign)var2;
            int var4 = 0;
            while (var4 < 4) {
                var3.signText[var4] = var1.signLines[var4];
                ++var4;
            }
            var3.x_();
        }
    }

    @Override
    public void func_20090_a(Packet105 var1) {
        this.registerPacket(var1);
        if (this.mc.thePlayer.craftingInventory != null && this.mc.thePlayer.craftingInventory.windowId == var1.windowId) {
            this.mc.thePlayer.craftingInventory.func_20112_a(var1.progressBar, var1.progressBarValue);
        }
    }

    @Override
    public void handlePlayerInventory(Packet5PlayerInventory var1) {
        Entity var2 = this.getEntityByID(var1.entityID);
        if (var2 != null) {
            var2.outfitWithItem(var1.slot, var1.itemID, var1.itemDamage);
        }
    }

    @Override
    public void func_20092_a(Packet101 var1) {
        this.mc.thePlayer.func_20059_m();
    }

    @Override
    public void func_21145_a(Packet54 var1) {
        this.mc.theWorld.playNoteAt(var1.xLocation, var1.yLocation, var1.zLocation, var1.instrumentType, var1.pitch);
    }

    @Override
    public void func_25118_a(Packet70 var1) {
        int var2 = var1.field_25019_b;
        if (var2 >= 0 && var2 < Packet70.field_25020_a.length) {
            this.mc.thePlayer.addChatMessage(Packet70.field_25020_a[var2]);
        }
    }
}

