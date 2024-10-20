/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.NetworkManager;

class NetworkReaderThread
extends Thread {
    final NetworkManager netManager;

    NetworkReaderThread(NetworkManager var1, String var2) {
        super(var2);
        this.netManager = var1;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void run() {
        block27: {
            Object object;
            boolean var12;
            block26: {
                Object object2 = NetworkManager.threadSyncObject;
                synchronized (object2) {
                    ++NetworkManager.numReadThreads;
                }
                while (true) {
                    var12 = false;
                    var12 = true;
                    if (!NetworkManager.isRunning(this.netManager)) break block26;
                    if (NetworkManager.isServerTerminating(this.netManager)) break;
                    NetworkManager.readNetworkPacket(this.netManager);
                    try {
                        NetworkReaderThread.sleep(0L);
                    }
                    catch (InterruptedException interruptedException) {
                        // empty catch block
                    }
                    if (!var12) continue;
                    object = NetworkManager.threadSyncObject;
                    synchronized (object) {
                        --NetworkManager.numReadThreads;
                    }
                }
                try {
                    var12 = false;
                    if (!var12) break block27;
                    object = NetworkManager.threadSyncObject;
                }
                catch (Throwable throwable) {
                    if (var12) {
                        object = NetworkManager.threadSyncObject;
                        synchronized (object) {
                            --NetworkManager.numReadThreads;
                        }
                    }
                    throw throwable;
                }
                synchronized (object) {
                    --NetworkManager.numReadThreads;
                }
            }
            var12 = false;
            if (!var12) break block27;
            object = NetworkManager.threadSyncObject;
            synchronized (object) {
                --NetworkManager.numReadThreads;
            }
        }
        Object object = NetworkManager.threadSyncObject;
        synchronized (object) {
            --NetworkManager.numReadThreads;
        }
    }
}

