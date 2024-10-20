/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.MCHashTable;

class HashEntry {
    final int hashEntry;
    Object valueEntry;
    HashEntry nextEntry;
    final int slotHash;

    HashEntry(int var1, int var2, Object var3, HashEntry var4) {
        this.valueEntry = var3;
        this.nextEntry = var4;
        this.hashEntry = var2;
        this.slotHash = var1;
    }

    public final int getHash() {
        return this.hashEntry;
    }

    public final Object getValue() {
        return this.valueEntry;
    }

    public final boolean equals(Object var1) {
        Object var6;
        Object var5;
        Integer var4;
        if (!(var1 instanceof HashEntry)) {
            return false;
        }
        HashEntry var2 = (HashEntry)var1;
        Integer var3 = this.getHash();
        return (var3 == (var4 = Integer.valueOf(var2.getHash())) || var3 != null && var3.equals(var4)) && ((var5 = this.getValue()) == (var6 = var2.getValue()) || var5 != null && var5.equals(var6));
    }

    public final int hashCode() {
        return MCHashTable.getHash(this.hashEntry);
    }

    public final String toString() {
        return String.valueOf(this.getHash()) + "=" + this.getValue();
    }
}

