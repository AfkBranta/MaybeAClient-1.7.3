/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.NetworkManager;

class NetworkWriterThread
extends Thread {
    final NetworkManager netManager;

    NetworkWriterThread(NetworkManager var1, String var2) {
        super(var2);
        this.netManager = var1;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void run() {
        Object object = NetworkManager.threadSyncObject;
        synchronized (object) {
            ++NetworkManager.numWriteThreads;
        }
        while (true) {
            Object object2;
            boolean var11;
            block20: {
                var11 = false;
                try {
                    var11 = true;
                    if (NetworkManager.isRunning(this.netManager)) break block20;
                    var11 = false;
                    if (!var11) break;
                    object2 = NetworkManager.threadSyncObject;
                }
                catch (Throwable throwable) {
                    if (var11) {
                        object2 = NetworkManager.threadSyncObject;
                        synchronized (object2) {
                            --NetworkManager.numWriteThreads;
                        }
                    }
                    throw throwable;
                }
                synchronized (object2) {
                    --NetworkManager.numWriteThreads;
                    break;
                }
            }
            NetworkManager.sendNetworkPacket(this.netManager);
            if (!var11) continue;
            object2 = NetworkManager.threadSyncObject;
            synchronized (object2) {
                --NetworkManager.numWriteThreads;
            }
        }
        Object object3 = NetworkManager.threadSyncObject;
        synchronized (object3) {
            --NetworkManager.numWriteThreads;
        }
    }
}

