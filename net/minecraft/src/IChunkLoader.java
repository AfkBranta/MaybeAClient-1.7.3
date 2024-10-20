/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.io.IOException;
import net.minecraft.src.Chunk;
import net.minecraft.src.World;

public interface IChunkLoader {
    public Chunk loadChunk(World var1, int var2, int var3) throws IOException;

    public void saveChunk(World var1, Chunk var2) throws IOException;

    public void saveExtraChunkData(World var1, Chunk var2) throws IOException;

    public void func_814_a();

    public void saveExtraData();
}

