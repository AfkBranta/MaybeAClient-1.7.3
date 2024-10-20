/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.HashEntry;

public class MCHashTable {
    private transient HashEntry[] slots = new HashEntry[16];
    private transient int count;
    private int threshold = 12;
    private final float growFactor = 0.75f;
    private volatile transient int versionStamp;

    private static int computeHash(int var0) {
        var0 ^= var0 >>> 20 ^ var0 >>> 12;
        return var0 ^ var0 >>> 7 ^ var0 >>> 4;
    }

    private static int getSlotIndex(int var0, int var1) {
        return var0 & var1 - 1;
    }

    public Object lookup(int var1) {
        int var2 = MCHashTable.computeHash(var1);
        HashEntry var3 = this.slots[MCHashTable.getSlotIndex(var2, this.slots.length)];
        while (var3 != null) {
            if (var3.hashEntry == var1) {
                return var3.valueEntry;
            }
            var3 = var3.nextEntry;
        }
        return null;
    }

    public void addKey(int var1, Object var2) {
        int var3 = MCHashTable.computeHash(var1);
        int var4 = MCHashTable.getSlotIndex(var3, this.slots.length);
        HashEntry var5 = this.slots[var4];
        while (var5 != null) {
            if (var5.hashEntry == var1) {
                var5.valueEntry = var2;
            }
            var5 = var5.nextEntry;
        }
        ++this.versionStamp;
        this.insert(var3, var1, var2, var4);
    }

    private void grow(int var1) {
        HashEntry[] var2 = this.slots;
        int var3 = var2.length;
        if (var3 == 0x40000000) {
            this.threshold = Integer.MAX_VALUE;
        } else {
            HashEntry[] var4 = new HashEntry[var1];
            this.copyTo(var4);
            this.slots = var4;
            this.threshold = (int)((float)var1 * 0.75f);
        }
    }

    private void copyTo(HashEntry[] var1) {
        HashEntry[] var2 = this.slots;
        int var3 = var1.length;
        int var4 = 0;
        while (var4 < var2.length) {
            HashEntry var5 = var2[var4];
            if (var5 != null) {
                HashEntry var6;
                var2[var4] = null;
                do {
                    var6 = var5.nextEntry;
                    int var7 = MCHashTable.getSlotIndex(var5.slotHash, var3);
                    var5.nextEntry = var1[var7];
                    var1[var7] = var5;
                    var5 = var6;
                } while (var6 != null);
            }
            ++var4;
        }
    }

    public Object removeObject(int var1) {
        HashEntry var2 = this.removeEntry(var1);
        return var2 == null ? null : var2.valueEntry;
    }

    final HashEntry removeEntry(int var1) {
        HashEntry var4;
        int var2 = MCHashTable.computeHash(var1);
        int var3 = MCHashTable.getSlotIndex(var2, this.slots.length);
        HashEntry var5 = var4 = this.slots[var3];
        while (var5 != null) {
            HashEntry var6 = var5.nextEntry;
            if (var5.hashEntry == var1) {
                ++this.versionStamp;
                --this.count;
                if (var4 == var5) {
                    this.slots[var3] = var6;
                } else {
                    var4.nextEntry = var6;
                }
                return var5;
            }
            var4 = var5;
            var5 = var6;
        }
        return var5;
    }

    public void clearMap() {
        ++this.versionStamp;
        HashEntry[] var1 = this.slots;
        int var2 = 0;
        while (var2 < var1.length) {
            var1[var2] = null;
            ++var2;
        }
        this.count = 0;
    }

    private void insert(int var1, int var2, Object var3, int var4) {
        HashEntry var5 = this.slots[var4];
        this.slots[var4] = new HashEntry(var1, var2, var3, var5);
        if (this.count++ >= this.threshold) {
            this.grow(2 * this.slots.length);
        }
    }

    static int getHash(int var0) {
        return MCHashTable.computeHash(var0);
    }
}

