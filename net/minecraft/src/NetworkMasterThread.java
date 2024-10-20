/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.NetworkManager;

class NetworkMasterThread
extends Thread {
    final NetworkManager netManager;

    NetworkMasterThread(NetworkManager var1) {
        this.netManager = var1;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(5000L);
            if (NetworkManager.getReadThread(this.netManager).isAlive()) {
                try {
                    NetworkManager.getReadThread(this.netManager).stop();
                }
                catch (Throwable throwable) {
                    // empty catch block
                }
            }
            if (NetworkManager.getWriteThread(this.netManager).isAlive()) {
                try {
                    NetworkManager.getWriteThread(this.netManager).stop();
                }
                catch (Throwable throwable) {}
            }
        }
        catch (InterruptedException var4) {
            var4.printStackTrace();
        }
    }
}

