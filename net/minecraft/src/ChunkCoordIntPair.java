package net.minecraft.src;

public class ChunkCoordIntPair {
    public final int chunkXPos;
    public final int chunkZPos;

    public ChunkCoordIntPair(int var1, int var2) {
        this.chunkXPos = var1;
        this.chunkZPos = var2;
    }

    public static int func_22011_a(int var0, int var1) {
        return (var0 < 0 ? Integer.MIN_VALUE : 0) | (var0 & Short.MAX_VALUE) << 16 | (var1 < 0 ? 32768 : 0) | var1 & Short.MAX_VALUE;
    }

    public int hashCode() {
        return ChunkCoordIntPair.func_22011_a(this.chunkXPos, this.chunkZPos);
    }

    public boolean equals(Object var1) {
        ChunkCoordIntPair var2 = (ChunkCoordIntPair)var1;
        return var2.chunkXPos == this.chunkXPos && var2.chunkZPos == this.chunkZPos;
    }
}

