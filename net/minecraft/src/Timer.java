/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.skidcode.gh.maybeaclient.hacks.ClockspeedHack;

public class Timer {
    public float ticksPerSecond;
    private double lastHRTime;
    public int elapsedTicks;
    public float renderPartialTicks;
    public float timerSpeed = 1.0f;
    public float elapsedPartialTicks = 0.0f;
    private long lastSyncSysClock;
    private long lastSyncHRClock;
    private double timeSyncAdjustment = 1.0;

    public Timer(float var1) {
        this.ticksPerSecond = var1;
        this.lastSyncSysClock = System.currentTimeMillis();
        this.lastSyncHRClock = System.nanoTime() / 1000000L;
    }

    public void updateTimer() {
        double var9;
        this.timerSpeed = ClockspeedHack.instance.status ? ClockspeedHack.instance.speed.value : 1.0f;
        long var1 = System.currentTimeMillis();
        long var3 = var1 - this.lastSyncSysClock;
        long var5 = System.nanoTime() / 1000000L;
        if (var3 > 1000L) {
            long var7 = var5 - this.lastSyncHRClock;
            var9 = (double)var3 / (double)var7;
            this.timeSyncAdjustment += (var9 - this.timeSyncAdjustment) * (double)0.2f;
            this.lastSyncSysClock = var1;
            this.lastSyncHRClock = var5;
        }
        if (var3 < 0L) {
            this.lastSyncSysClock = var1;
            this.lastSyncHRClock = var5;
        }
        double var11 = (double)var5 / 1000.0;
        var9 = (var11 - this.lastHRTime) * this.timeSyncAdjustment;
        this.lastHRTime = var11;
        if (var9 < 0.0) {
            var9 = 0.0;
        }
        if (var9 > 1.0) {
            var9 = 1.0;
        }
        this.elapsedPartialTicks = (float)((double)this.elapsedPartialTicks + var9 * (double)this.timerSpeed * (double)this.ticksPerSecond);
        this.elapsedTicks = (int)this.elapsedPartialTicks;
        this.elapsedPartialTicks -= (float)this.elapsedTicks;
        if (this.elapsedTicks > 10) {
            this.elapsedTicks = 10;
        }
        this.renderPartialTicks = this.elapsedPartialTicks;
    }
}

