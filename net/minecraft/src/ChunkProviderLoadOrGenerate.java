package net.minecraft.src;

import java.io.IOException;
import net.minecraft.src.Chunk;
import net.minecraft.src.EmptyChunk;
import net.minecraft.src.IChunkLoader;
import net.minecraft.src.IChunkProvider;
import net.minecraft.src.IProgressUpdate;
import net.minecraft.src.World;

public class ChunkProviderLoadOrGenerate
implements IChunkProvider {
    private Chunk blankChunk;
    private IChunkProvider chunkProvider;
    private IChunkLoader chunkLoader;
    private Chunk[] chunks = new Chunk[1024];
    private World worldObj;
    int lastQueriedChunkXPos = -999999999;
    int lastQueriedChunkZPos = -999999999;
    private Chunk lastQueriedChunk;
    private int curChunkX;
    private int curChunkY;

    public ChunkProviderLoadOrGenerate(World var1, IChunkLoader var2, IChunkProvider var3) {
        this.blankChunk = new EmptyChunk(var1, new byte[32768], 0, 0);
        this.worldObj = var1;
        this.chunkLoader = var2;
        this.chunkProvider = var3;
    }

    public void setCurrentChunkOver(int var1, int var2) {
        this.curChunkX = var1;
        this.curChunkY = var2;
    }

    public boolean canChunkExist(int var1, int var2) {
        int var3 = 15;
        return var1 >= this.curChunkX - var3 && var2 >= this.curChunkY - var3 && var1 <= this.curChunkX + var3 && var2 <= this.curChunkY + var3;
    }

    @Override
    public boolean chunkExists(int var1, int var2) {
        if (!this.canChunkExist(var1, var2)) {
            return false;
        }
        if (var1 == this.lastQueriedChunkXPos && var2 == this.lastQueriedChunkZPos && this.lastQueriedChunk != null) {
            return true;
        }
        int var3 = var1 & 0x1F;
        int var4 = var2 & 0x1F;
        int var5 = var3 + var4 * 32;
        return this.chunks[var5] != null && (this.chunks[var5] == this.blankChunk || this.chunks[var5].isAtLocation(var1, var2));
    }

    @Override
    public Chunk func_538_d(int var1, int var2) {
        return this.provideChunk(var1, var2);
    }

    @Override
    public Chunk provideChunk(int var1, int var2) {
        if (var1 == this.lastQueriedChunkXPos && var2 == this.lastQueriedChunkZPos && this.lastQueriedChunk != null) {
            return this.lastQueriedChunk;
        }
        if (!this.worldObj.findingSpawnPoint && !this.canChunkExist(var1, var2)) {
            return this.blankChunk;
        }
        int var3 = var1 & 0x1F;
        int var4 = var2 & 0x1F;
        int var5 = var3 + var4 * 32;
        if (!this.chunkExists(var1, var2)) {
            Chunk var6;
            if (this.chunks[var5] != null) {
                this.chunks[var5].onChunkUnload();
                this.saveChunk(this.chunks[var5]);
                this.saveExtraChunkData(this.chunks[var5]);
            }
            if ((var6 = this.func_542_c(var1, var2)) == null) {
                var6 = this.chunkProvider == null ? this.blankChunk : this.chunkProvider.provideChunk(var1, var2);
            }
            this.chunks[var5] = var6;
            var6.func_4143_d();
            if (this.chunks[var5] != null) {
                this.chunks[var5].onChunkLoad();
            }
            if (!this.chunks[var5].isTerrainPopulated && this.chunkExists(var1 + 1, var2 + 1) && this.chunkExists(var1, var2 + 1) && this.chunkExists(var1 + 1, var2)) {
                this.populate(this, var1, var2);
            }
            if (this.chunkExists(var1 - 1, var2) && !this.provideChunk((int)(var1 - 1), (int)var2).isTerrainPopulated && this.chunkExists(var1 - 1, var2 + 1) && this.chunkExists(var1, var2 + 1) && this.chunkExists(var1 - 1, var2)) {
                this.populate(this, var1 - 1, var2);
            }
            if (this.chunkExists(var1, var2 - 1) && !this.provideChunk((int)var1, (int)(var2 - 1)).isTerrainPopulated && this.chunkExists(var1 + 1, var2 - 1) && this.chunkExists(var1, var2 - 1) && this.chunkExists(var1 + 1, var2)) {
                this.populate(this, var1, var2 - 1);
            }
            if (this.chunkExists(var1 - 1, var2 - 1) && !this.provideChunk((int)(var1 - 1), (int)(var2 - 1)).isTerrainPopulated && this.chunkExists(var1 - 1, var2 - 1) && this.chunkExists(var1, var2 - 1) && this.chunkExists(var1 - 1, var2)) {
                this.populate(this, var1 - 1, var2 - 1);
            }
        }
        this.lastQueriedChunkXPos = var1;
        this.lastQueriedChunkZPos = var2;
        this.lastQueriedChunk = this.chunks[var5];
        return this.chunks[var5];
    }

    private Chunk func_542_c(int var1, int var2) {
        if (this.chunkLoader == null) {
            return this.blankChunk;
        }
        try {
            Chunk var3 = this.chunkLoader.loadChunk(this.worldObj, var1, var2);
            if (var3 != null) {
                var3.lastSaveTime = this.worldObj.getWorldTime();
            }
            return var3;
        }
        catch (Exception var4) {
            var4.printStackTrace();
            return this.blankChunk;
        }
    }

    private void saveExtraChunkData(Chunk var1) {
        if (this.chunkLoader != null) {
            try {
                this.chunkLoader.saveExtraChunkData(this.worldObj, var1);
            }
            catch (Exception var3) {
                var3.printStackTrace();
            }
        }
    }

    private void saveChunk(Chunk var1) {
        if (this.chunkLoader != null) {
            try {
                var1.lastSaveTime = this.worldObj.getWorldTime();
                this.chunkLoader.saveChunk(this.worldObj, var1);
            }
            catch (IOException var3) {
                var3.printStackTrace();
            }
        }
    }

    @Override
    public void populate(IChunkProvider var1, int var2, int var3) {
        Chunk var4 = this.provideChunk(var2, var3);
        if (!var4.isTerrainPopulated) {
            var4.isTerrainPopulated = true;
            if (this.chunkProvider != null) {
                this.chunkProvider.populate(var1, var2, var3);
                var4.setChunkModified();
            }
        }
    }

    @Override
    public boolean saveChunks(boolean var1, IProgressUpdate var2) {
        int var5;
        int var3 = 0;
        int var4 = 0;
        if (var2 != null) {
            var5 = 0;
            while (var5 < this.chunks.length) {
                if (this.chunks[var5] != null && this.chunks[var5].needsSaving(var1)) {
                    ++var4;
                }
                ++var5;
            }
        }
        var5 = 0;
        int var6 = 0;
        while (var6 < this.chunks.length) {
            if (this.chunks[var6] != null) {
                if (var1 && !this.chunks[var6].neverSave) {
                    this.saveExtraChunkData(this.chunks[var6]);
                }
                if (this.chunks[var6].needsSaving(var1)) {
                    this.saveChunk(this.chunks[var6]);
                    this.chunks[var6].isModified = false;
                    if (++var3 == 2 && !var1) {
                        return false;
                    }
                    if (var2 != null && ++var5 % 10 == 0) {
                        var2.setLoadingProgress(var5 * 100 / var4);
                    }
                }
            }
            ++var6;
        }
        if (var1) {
            if (this.chunkLoader == null) {
                return true;
            }
            this.chunkLoader.saveExtraData();
        }
        return true;
    }

    @Override
    public boolean func_532_a() {
        if (this.chunkLoader != null) {
            this.chunkLoader.func_814_a();
        }
        return this.chunkProvider.func_532_a();
    }

    @Override
    public boolean func_536_b() {
        return true;
    }

    @Override
    public String toString() {
        return "ChunkCache: " + this.chunks.length;
    }
}

