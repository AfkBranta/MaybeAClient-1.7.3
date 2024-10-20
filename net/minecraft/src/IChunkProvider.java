/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.Chunk;
import net.minecraft.src.IProgressUpdate;

public interface IChunkProvider {
    public boolean chunkExists(int var1, int var2);

    public Chunk provideChunk(int var1, int var2);

    public Chunk func_538_d(int var1, int var2);

    public void populate(IChunkProvider var1, int var2, int var3);

    public boolean saveChunks(boolean var1, IProgressUpdate var2);

    public boolean func_532_a();

    public boolean func_536_b();

    public String toString();
}

