/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.minecraft.src.NetHandler;
import net.minecraft.src.NetworkMasterThread;
import net.minecraft.src.NetworkReaderThread;
import net.minecraft.src.NetworkWriterThread;
import net.minecraft.src.Packet;
import net.skidcode.gh.maybeaclient.events.EventRegistry;
import net.skidcode.gh.maybeaclient.events.impl.EventPacketReceive;
import net.skidcode.gh.maybeaclient.events.impl.EventPacketSend;

public class NetworkManager {
    public static final Object threadSyncObject = new Object();
    public static int numReadThreads;
    public static int numWriteThreads;
    private Object sendQueueLock = new Object();
    private Socket networkSocket;
    public final SocketAddress remoteSocketAddress;
    private DataInputStream socketInputStream;
    private DataOutputStream socketOutputStream;
    private boolean isRunning = true;
    private List readPackets = Collections.synchronizedList(new ArrayList());
    private List dataPackets = Collections.synchronizedList(new ArrayList());
    private List chunkDataPackets = Collections.synchronizedList(new ArrayList());
    private NetHandler netHandler;
    private boolean isServerTerminating = false;
    private Thread writeThread;
    private Thread readThread;
    private boolean isTerminating = false;
    private String terminationReason = "";
    private Object[] field_20101_t;
    public int timeSinceLastRead = 0;
    private int sendQueueByteLength = 0;
    public int chunkDataSendCounter = 0;
    private int field_20100_w = 50;

    public NetworkManager(Socket var1, String var2, NetHandler var3) throws IOException {
        this.networkSocket = var1;
        this.remoteSocketAddress = var1.getRemoteSocketAddress();
        this.netHandler = var3;
        try {
            var1.setTrafficClass(24);
        }
        catch (SocketException var5) {
            System.err.println(var5.getMessage());
        }
        this.socketInputStream = new DataInputStream(var1.getInputStream());
        this.socketOutputStream = new DataOutputStream(var1.getOutputStream());
        this.readThread = new NetworkReaderThread(this, String.valueOf(var2) + " read thread");
        this.writeThread = new NetworkWriterThread(this, String.valueOf(var2) + " write thread");
        this.readThread.start();
        this.writeThread.start();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void addToSendQueue(Packet var1) {
        if (!this.isServerTerminating) {
            EventPacketSend ev = new EventPacketSend(var1);
            EventRegistry.handleEvent(ev);
            if (ev.cancelled) {
                return;
            }
            Object object = this.sendQueueLock;
            synchronized (object) {
                this.sendQueueByteLength += var1.getPacketSize() + 1;
                if (var1.isChunkDataPacket) {
                    this.chunkDataPackets.add(var1);
                } else {
                    this.dataPackets.add(var1);
                }
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void sendPacket() {
        block12: {
            try {
                Packet var2;
                Object object;
                boolean var1 = true;
                if (!(this.dataPackets.isEmpty() || this.chunkDataSendCounter != 0 && System.currentTimeMillis() - ((Packet)this.dataPackets.get((int)0)).creationTimeMillis < (long)this.chunkDataSendCounter)) {
                    var1 = false;
                    object = this.sendQueueLock;
                    synchronized (object) {
                        var2 = (Packet)this.dataPackets.remove(0);
                        this.sendQueueByteLength -= var2.getPacketSize() + 1;
                    }
                    Packet.writePacket(var2, this.socketOutputStream);
                }
                if (!(!var1 && this.field_20100_w-- > 0 || this.chunkDataPackets.isEmpty() || this.chunkDataSendCounter != 0 && System.currentTimeMillis() - ((Packet)this.chunkDataPackets.get((int)0)).creationTimeMillis < (long)this.chunkDataSendCounter)) {
                    var1 = false;
                    object = this.sendQueueLock;
                    synchronized (object) {
                        var2 = (Packet)this.chunkDataPackets.remove(0);
                        this.sendQueueByteLength -= var2.getPacketSize() + 1;
                    }
                    Packet.writePacket(var2, this.socketOutputStream);
                    this.field_20100_w = 50;
                }
                if (var1) {
                    Thread.sleep(10L);
                }
            }
            catch (InterruptedException var1) {
            }
            catch (Exception var9) {
                if (this.isTerminating) break block12;
                this.onNetworkError(var9);
            }
        }
    }

    private void readPacket() {
        block4: {
            try {
                Packet var1 = Packet.readPacket(this.socketInputStream);
                if (var1 != null) {
                    this.readPackets.add(var1);
                } else {
                    this.networkShutdown("disconnect.endOfStream", new Object[0]);
                }
            }
            catch (Exception var2) {
                if (this.isTerminating) break block4;
                this.onNetworkError(var2);
            }
        }
    }

    private void onNetworkError(Exception var1) {
        var1.printStackTrace();
        this.networkShutdown("disconnect.genericReason", "Internal exception: " + var1.toString());
    }

    public void networkShutdown(String var1, Object ... var2) {
        if (this.isRunning) {
            this.isTerminating = true;
            this.terminationReason = var1;
            this.field_20101_t = var2;
            new NetworkMasterThread(this).start();
            this.isRunning = false;
            try {
                this.socketInputStream.close();
                this.socketInputStream = null;
            }
            catch (Throwable throwable) {
                // empty catch block
            }
            try {
                this.socketOutputStream.close();
                this.socketOutputStream = null;
            }
            catch (Throwable throwable) {
                // empty catch block
            }
            try {
                this.networkSocket.close();
                this.networkSocket = null;
            }
            catch (Throwable throwable) {
                // empty catch block
            }
        }
    }

    public void processReadPackets() {
        if (this.sendQueueByteLength > 0x100000) {
            this.networkShutdown("disconnect.overflow", new Object[0]);
        }
        if (this.readPackets.isEmpty()) {
            if (this.timeSinceLastRead++ == 1200) {
                this.networkShutdown("disconnect.timeout", new Object[0]);
            }
        } else {
            this.timeSinceLastRead = 0;
        }
        int var1 = 100;
        while (!this.readPackets.isEmpty() && var1-- >= 0) {
            Packet var2 = (Packet)this.readPackets.remove(0);
            EventPacketReceive ev = new EventPacketReceive(var2);
            EventRegistry.handleEvent(ev);
            if (ev.cancelled) continue;
            var2.processPacket(this.netHandler);
        }
        if (this.isTerminating && this.readPackets.isEmpty()) {
            this.netHandler.handleErrorMessage(this.terminationReason, this.field_20101_t);
        }
    }

    static boolean isRunning(NetworkManager var0) {
        return var0.isRunning;
    }

    static boolean isServerTerminating(NetworkManager var0) {
        return var0.isServerTerminating;
    }

    static void readNetworkPacket(NetworkManager var0) {
        var0.readPacket();
    }

    static void sendNetworkPacket(NetworkManager var0) {
        var0.sendPacket();
    }

    static Thread getReadThread(NetworkManager var0) {
        return var0.readThread;
    }

    static Thread getWriteThread(NetworkManager var0) {
        return var0.writeThread;
    }
}

