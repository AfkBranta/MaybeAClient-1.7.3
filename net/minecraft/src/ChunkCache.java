package net.minecraft.src;

import net.minecraft.src.Block;
import net.minecraft.src.Chunk;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.Material;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraft.src.WorldChunkManager;

public class ChunkCache
implements IBlockAccess {
    private int chunkX;
    private int chunkZ;
    private Chunk[][] chunkArray;
    private World worldObj;

    public ChunkCache(World var1, int var2, int var3, int var4, int var5, int var6, int var7) {
        this.worldObj = var1;
        this.chunkX = var2 >> 4;
        this.chunkZ = var4 >> 4;
        int var8 = var5 >> 4;
        int var9 = var7 >> 4;
        this.chunkArray = new Chunk[var8 - this.chunkX + 1][var9 - this.chunkZ + 1];
        int var10 = this.chunkX;
        while (var10 <= var8) {
            int var11 = this.chunkZ;
            while (var11 <= var9) {
                this.chunkArray[var10 - this.chunkX][var11 - this.chunkZ] = var1.getChunkFromChunkCoords(var10, var11);
                ++var11;
            }
            ++var10;
        }
    }

    @Override
    public int getBlockId(int var1, int var2, int var3) {
        if (var2 < 0) {
            return 0;
        }
        if (var2 >= 128) {
            return 0;
        }
        int var4 = (var1 >> 4) - this.chunkX;
        int var5 = (var3 >> 4) - this.chunkZ;
        if (var4 >= 0 && var4 < this.chunkArray.length && var5 >= 0 && var5 < this.chunkArray[var4].length) {
            Chunk var6 = this.chunkArray[var4][var5];
            return var6 == null ? 0 : var6.getBlockID(var1 & 0xF, var2, var3 & 0xF);
        }
        return 0;
    }

    @Override
    public TileEntity getBlockTileEntity(int var1, int var2, int var3) {
        int var4 = (var1 >> 4) - this.chunkX;
        int var5 = (var3 >> 4) - this.chunkZ;
        return this.chunkArray[var4][var5].getChunkBlockTileEntity(var1 & 0xF, var2, var3 & 0xF);
    }

    @Override
    public float getLightBrightness(int var1, int var2, int var3) {
        return this.worldObj.worldProvider.lightBrightnessTable[this.getLightValue(var1, var2, var3)];
    }

    public int getLightValue(int var1, int var2, int var3) {
        return this.func_716_a(var1, var2, var3, true);
    }

    public int func_716_a(int var1, int var2, int var3, boolean var4) {
        if (var1 >= -32000000 && var3 >= -32000000 && var1 < 32000000 && var3 <= 32000000) {
            int var5;
            if (var4 && ((var5 = this.getBlockId(var1, var2, var3)) == Block.stairSingle.blockID || var5 == Block.tilledField.blockID)) {
                int var6 = this.func_716_a(var1, var2 + 1, var3, false);
                int var7 = this.func_716_a(var1 + 1, var2, var3, false);
                int var8 = this.func_716_a(var1 - 1, var2, var3, false);
                int var9 = this.func_716_a(var1, var2, var3 + 1, false);
                int var10 = this.func_716_a(var1, var2, var3 - 1, false);
                if (var7 > var6) {
                    var6 = var7;
                }
                if (var8 > var6) {
                    var6 = var8;
                }
                if (var9 > var6) {
                    var6 = var9;
                }
                if (var10 > var6) {
                    var6 = var10;
                }
                return var6;
            }
            if (var2 < 0) {
                return 0;
            }
            if (var2 >= 128) {
                var5 = 15 - this.worldObj.skylightSubtracted;
                if (var5 < 0) {
                    var5 = 0;
                }
                return var5;
            }
            var5 = (var1 >> 4) - this.chunkX;
            int var6 = (var3 >> 4) - this.chunkZ;
            return this.chunkArray[var5][var6].getBlockLightValue(var1 & 0xF, var2, var3 & 0xF, this.worldObj.skylightSubtracted);
        }
        return 15;
    }

    @Override
    public int getBlockMetadata(int var1, int var2, int var3) {
        if (var2 < 0) {
            return 0;
        }
        if (var2 >= 128) {
            return 0;
        }
        int var4 = (var1 >> 4) - this.chunkX;
        int var5 = (var3 >> 4) - this.chunkZ;
        return this.chunkArray[var4][var5].getBlockMetadata(var1 & 0xF, var2, var3 & 0xF);
    }

    @Override
    public Material getBlockMaterial(int var1, int var2, int var3) {
        int var4 = this.getBlockId(var1, var2, var3);
        return var4 == 0 ? Material.air : Block.blocksList[var4].blockMaterial;
    }

    @Override
    public boolean isBlockOpaqueCube(int var1, int var2, int var3) {
        Block var4 = Block.blocksList[this.getBlockId(var1, var2, var3)];
        return var4 == null ? false : var4.isOpaqueCube();
    }

    @Override
    public WorldChunkManager getWorldChunkManager() {
        return this.worldObj.getWorldChunkManager();
    }
}

