/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.List;
import net.minecraft.src.IChunkLoader;
import net.minecraft.src.WorldInfo;
import net.minecraft.src.WorldProvider;

public interface ISaveHandler {
    public WorldInfo loadWorldInfo();

    public void func_22150_b();

    public IChunkLoader getChunkLoader(WorldProvider var1);

    public void saveWorldInfoAndPlayer(WorldInfo var1, List var2);

    public void saveWorldInfo(WorldInfo var1);
}

