/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.Material;
import net.minecraft.src.TileEntity;
import net.minecraft.src.WorldChunkManager;

public interface IBlockAccess {
    public int getBlockId(int var1, int var2, int var3);

    public TileEntity getBlockTileEntity(int var1, int var2, int var3);

    public float getLightBrightness(int var1, int var2, int var3);

    public int getBlockMetadata(int var1, int var2, int var3);

    public Material getBlockMaterial(int var1, int var2, int var3);

    public boolean isBlockOpaqueCube(int var1, int var2, int var3);

    public WorldChunkManager getWorldChunkManager();
}

