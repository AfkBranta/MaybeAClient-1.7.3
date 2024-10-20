package net.minecraft.src;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Block;
import net.minecraft.src.BlockFluids;
import net.minecraft.src.Chunk;
import net.minecraft.src.ChunkCache;
import net.minecraft.src.ChunkCoordIntPair;
import net.minecraft.src.ChunkCoordinates;
import net.minecraft.src.ChunkProviderLoadOrGenerate;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EnumSkyBlock;
import net.minecraft.src.Explosion;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.IChunkLoader;
import net.minecraft.src.IChunkProvider;
import net.minecraft.src.IProgressUpdate;
import net.minecraft.src.ISaveHandler;
import net.minecraft.src.IWorldAccess;
import net.minecraft.src.Material;
import net.minecraft.src.MathHelper;
import net.minecraft.src.MetadataChunkBlock;
import net.minecraft.src.MovingObjectPosition;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.NextTickListEntry;
import net.minecraft.src.PathEntity;
import net.minecraft.src.Pathfinder;
import net.minecraft.src.SpawnerAnimals;
import net.minecraft.src.TileEntity;
import net.minecraft.src.Vec3D;
import net.minecraft.src.WorldChunkManager;
import net.minecraft.src.WorldInfo;
import net.minecraft.src.WorldProvider;
import net.minecraft.src.WorldProviderHell;
import net.skidcode.gh.maybeaclient.Client;
import net.skidcode.gh.maybeaclient.hacks.FullBrightHack;
import net.skidcode.gh.maybeaclient.hacks.LockTimeHack;

public class World
implements IBlockAccess {
    public boolean scheduledUpdatesAreImmediate = false;
    private List field_1051_z = new ArrayList();
    public List loadedEntityList = new ArrayList();
    private List unloadedEntityList = new ArrayList();
    private TreeSet scheduledTickTreeSet = new TreeSet();
    private Set scheduledTickSet = new HashSet();
    public List loadedTileEntityList = new ArrayList();
    public List playerEntities = new ArrayList();
    private long field_1019_F = 0xFFFFFFL;
    public int skylightSubtracted = 0;
    protected int field_9437_g = new Random().nextInt();
    protected int field_9436_h = 1013904223;
    public boolean editingBlocks = false;
    private long lockTimestamp = System.currentTimeMillis();
    protected int autosavePeriod = 40;
    public int difficultySetting;
    public Random rand = new Random();
    public boolean isNewWorld = false;
    public final WorldProvider worldProvider;
    protected List worldAccesses = new ArrayList();
    public IChunkProvider chunkProvider;
    protected final ISaveHandler saveHandler;
    public WorldInfo worldInfo;
    public boolean findingSpawnPoint;
    private boolean allPlayersSleeping;
    private ArrayList field_9428_I = new ArrayList();
    private int field_4204_J = 0;
    private boolean spawnHostileMobs = true;
    private boolean spawnPeacefulMobs = true;
    static int field_9429_y = 0;
    private Set field_9427_K = new HashSet();
    private int field_9426_L = this.rand.nextInt(12000);
    private List field_1012_M = new ArrayList();
    public boolean multiplayerWorld = false;

    @Override
    public WorldChunkManager getWorldChunkManager() {
        return this.worldProvider.worldChunkMgr;
    }

    public World(ISaveHandler var1, String var2, WorldProvider var3, long var4) {
        this.saveHandler = var1;
        this.worldInfo = new WorldInfo(var4, var2);
        this.worldProvider = var3;
        var3.registerWorld(this);
        this.chunkProvider = this.getChunkProvider();
        this.calculateInitialSkylight();
    }

    public World(World var1, WorldProvider var2) {
        this.lockTimestamp = var1.lockTimestamp;
        this.saveHandler = var1.saveHandler;
        this.worldInfo = new WorldInfo(var1.worldInfo);
        this.worldProvider = var2;
        var2.registerWorld(this);
        this.chunkProvider = this.getChunkProvider();
        this.calculateInitialSkylight();
    }

    public World(ISaveHandler var1, String var2, long var3) {
        this(var1, var2, var3, null);
    }

    public World(ISaveHandler var1, String var2, long var3, WorldProvider var5) {
        this.saveHandler = var1;
        this.worldInfo = var1.loadWorldInfo();
        boolean bl = this.isNewWorld = this.worldInfo == null;
        this.worldProvider = var5 != null ? var5 : (this.worldInfo != null && this.worldInfo.getDimension() == -1 ? new WorldProviderHell() : new WorldProvider());
        boolean var6 = false;
        if (this.worldInfo == null) {
            this.worldInfo = new WorldInfo(var3, var2);
            var6 = true;
        } else {
            this.worldInfo.setWorldName(var2);
        }
        this.worldProvider.registerWorld(this);
        this.chunkProvider = this.getChunkProvider();
        if (var6) {
            this.func_25098_c();
        }
        this.calculateInitialSkylight();
    }

    protected IChunkProvider getChunkProvider() {
        IChunkLoader var1 = this.saveHandler.getChunkLoader(this.worldProvider);
        return new ChunkProviderLoadOrGenerate(this, var1, this.worldProvider.getChunkProvider());
    }

    protected void func_25098_c() {
        this.findingSpawnPoint = true;
        int var1 = 0;
        int var2 = 64;
        int var3 = 0;
        while (!this.worldProvider.canCoordinateBeSpawn(var1, var3)) {
            var1 += this.rand.nextInt(64) - this.rand.nextInt(64);
            var3 += this.rand.nextInt(64) - this.rand.nextInt(64);
        }
        this.worldInfo.setSpawn(var1, var2, var3);
        this.findingSpawnPoint = false;
    }

    public void setSpawnLocation() {
        if (this.worldInfo.getSpawnY() <= 0) {
            this.worldInfo.setSpawnY(64);
        }
        int var1 = this.worldInfo.getSpawnX();
        int var2 = this.worldInfo.getSpawnZ();
        while (this.getFirstUncoveredBlock(var1, var2) == 0) {
            var1 += this.rand.nextInt(8) - this.rand.nextInt(8);
            var2 += this.rand.nextInt(8) - this.rand.nextInt(8);
        }
        this.worldInfo.setSpawnX(var1);
        this.worldInfo.setSpawnZ(var2);
    }

    public int getFirstUncoveredBlock(int var1, int var2) {
        int var3 = 63;
        while (!this.isAirBlock(var1, var3 + 1, var2)) {
            ++var3;
        }
        return this.getBlockId(var1, var3, var2);
    }

    public void func_6464_c() {
    }

    public void spawnPlayerWithLoadedChunks(EntityPlayer var1) {
        try {
            NBTTagCompound var2 = this.worldInfo.getPlayerNBTTagCompound();
            if (var2 != null) {
                var1.readFromNBT(var2);
                this.worldInfo.setPlayerNBTTagCompound(null);
            }
            if (this.chunkProvider instanceof ChunkProviderLoadOrGenerate) {
                ChunkProviderLoadOrGenerate var3 = (ChunkProviderLoadOrGenerate)this.chunkProvider;
                int var4 = MathHelper.floor_float((int)var1.posX) >> 4;
                int var5 = MathHelper.floor_float((int)var1.posZ) >> 4;
                var3.setCurrentChunkOver(var4, var5);
            }
            this.entityJoinedWorld(var1);
        }
        catch (Exception var6) {
            var6.printStackTrace();
        }
    }

    public void saveWorld(boolean var1, IProgressUpdate var2) {
        if (this.chunkProvider.func_536_b()) {
            if (var2 != null) {
                var2.func_594_b("Saving level");
            }
            this.saveLevel();
            if (var2 != null) {
                var2.displayLoadingString("Saving chunks");
            }
            this.chunkProvider.saveChunks(var1, var2);
        }
    }

    private void saveLevel() {
        this.checkSessionLock();
        this.saveHandler.saveWorldInfoAndPlayer(this.worldInfo, this.playerEntities);
    }

    public boolean func_650_a(int var1) {
        if (!this.chunkProvider.func_536_b()) {
            return true;
        }
        if (var1 == 0) {
            this.saveLevel();
        }
        return this.chunkProvider.saveChunks(false, null);
    }

    @Override
    public int getBlockId(int var1, int var2, int var3) {
        if (var1 >= -32000000 && var3 >= -32000000 && var1 < 32000000 && var3 <= 32000000) {
            if (var2 < 0) {
                return 0;
            }
            return var2 >= 128 ? 0 : this.getChunkFromChunkCoords(var1 >> 4, var3 >> 4).getBlockID(var1 & 0xF, var2, var3 & 0xF);
        }
        return 0;
    }

    public boolean isAirBlock(int var1, int var2, int var3) {
        return this.getBlockId(var1, var2, var3) == 0;
    }

    public boolean blockExists(int var1, int var2, int var3) {
        return var2 >= 0 && var2 < 128 ? this.chunkExists(var1 >> 4, var3 >> 4) : false;
    }

    public boolean doChunksNearChunkExist(int var1, int var2, int var3, int var4) {
        return this.checkChunksExist(var1 - var4, var2 - var4, var3 - var4, var1 + var4, var2 + var4, var3 + var4);
    }

    public boolean checkChunksExist(int var1, int var2, int var3, int var4, int var5, int var6) {
        if (var5 >= 0 && var2 < 128) {
            var1 >>= 4;
            var2 >>= 4;
            var3 >>= 4;
            var4 >>= 4;
            var5 >>= 4;
            var6 >>= 4;
            int var7 = var1;
            while (var7 <= var4) {
                int var8 = var3;
                while (var8 <= var6) {
                    if (!this.chunkExists(var7, var8)) {
                        return false;
                    }
                    ++var8;
                }
                ++var7;
            }
            return true;
        }
        return false;
    }

    private boolean chunkExists(int var1, int var2) {
        return this.chunkProvider.chunkExists(var1, var2);
    }

    public Chunk getChunkFromBlockCoords(int var1, int var2) {
        return this.getChunkFromChunkCoords(var1 >> 4, var2 >> 4);
    }

    public Chunk getChunkFromChunkCoords(int var1, int var2) {
        return this.chunkProvider.provideChunk(var1, var2);
    }

    public boolean setBlockAndMetadata(int var1, int var2, int var3, int var4, int var5) {
        if (var1 >= -32000000 && var3 >= -32000000 && var1 < 32000000 && var3 <= 32000000) {
            if (var2 < 0) {
                return false;
            }
            if (var2 >= 128) {
                return false;
            }
            Chunk var6 = this.getChunkFromChunkCoords(var1 >> 4, var3 >> 4);
            return var6.setBlockIDWithMetadata(var1 & 0xF, var2, var3 & 0xF, var4, var5);
        }
        return false;
    }

    public boolean setBlock(int var1, int var2, int var3, int var4) {
        if (var1 >= -32000000 && var3 >= -32000000 && var1 < 32000000 && var3 <= 32000000) {
            if (var2 < 0) {
                return false;
            }
            if (var2 >= 128) {
                return false;
            }
            Chunk var5 = this.getChunkFromChunkCoords(var1 >> 4, var3 >> 4);
            return var5.setBlockID(var1 & 0xF, var2, var3 & 0xF, var4);
        }
        return false;
    }

    @Override
    public Material getBlockMaterial(int var1, int var2, int var3) {
        int var4 = this.getBlockId(var1, var2, var3);
        return var4 == 0 ? Material.air : Block.blocksList[var4].blockMaterial;
    }

    @Override
    public int getBlockMetadata(int var1, int var2, int var3) {
        if (var1 >= -32000000 && var3 >= -32000000 && var1 < 32000000 && var3 <= 32000000) {
            if (var2 < 0) {
                return 0;
            }
            if (var2 >= 128) {
                return 0;
            }
            Chunk var4 = this.getChunkFromChunkCoords(var1 >> 4, var3 >> 4);
            return var4.getBlockMetadata(var1 &= 0xF, var2, var3 &= 0xF);
        }
        return 0;
    }

    public void setBlockMetadataWithNotify(int var1, int var2, int var3, int var4) {
        if (this.setBlockMetadata(var1, var2, var3, var4)) {
            this.notifyBlockChange(var1, var2, var3, this.getBlockId(var1, var2, var3));
        }
    }

    public boolean setBlockMetadata(int var1, int var2, int var3, int var4) {
        if (var1 >= -32000000 && var3 >= -32000000 && var1 < 32000000 && var3 <= 32000000) {
            if (var2 < 0) {
                return false;
            }
            if (var2 >= 128) {
                return false;
            }
            Chunk var5 = this.getChunkFromChunkCoords(var1 >> 4, var3 >> 4);
            var5.setBlockMetadata(var1 &= 0xF, var2, var3 &= 0xF, var4);
            return true;
        }
        return false;
    }

    public boolean setBlockWithNotify(int var1, int var2, int var3, int var4) {
        if (this.setBlock(var1, var2, var3, var4)) {
            this.notifyBlockChange(var1, var2, var3, var4);
            return true;
        }
        return false;
    }

    public boolean setBlockAndMetadataWithNotify(int var1, int var2, int var3, int var4, int var5) {
        if (this.setBlockAndMetadata(var1, var2, var3, var4, var5)) {
            this.notifyBlockChange(var1, var2, var3, var4);
            return true;
        }
        return false;
    }

    public void markBlockNeedsUpdate(int var1, int var2, int var3) {
        int var4 = 0;
        while (var4 < this.worldAccesses.size()) {
            ((IWorldAccess)this.worldAccesses.get(var4)).markBlockAndNeighborsNeedsUpdate(var1, var2, var3);
            ++var4;
        }
    }

    protected void notifyBlockChange(int var1, int var2, int var3, int var4) {
        this.markBlockNeedsUpdate(var1, var2, var3);
        this.notifyBlocksOfNeighborChange(var1, var2, var3, var4);
    }

    public void markBlocksDirtyVertical(int var1, int var2, int var3, int var4) {
        if (var3 > var4) {
            int var5 = var4;
            var4 = var3;
            var3 = var5;
        }
        this.markBlocksDirty(var1, var3, var2, var1, var4, var2);
    }

    public void markBlockAsNeedsUpdate(int var1, int var2, int var3) {
        int var4 = 0;
        while (var4 < this.worldAccesses.size()) {
            ((IWorldAccess)this.worldAccesses.get(var4)).markBlockRangeNeedsUpdate(var1, var2, var3, var1, var2, var3);
            ++var4;
        }
    }

    public void markBlocksDirty(int var1, int var2, int var3, int var4, int var5, int var6) {
        int var7 = 0;
        while (var7 < this.worldAccesses.size()) {
            ((IWorldAccess)this.worldAccesses.get(var7)).markBlockRangeNeedsUpdate(var1, var2, var3, var4, var5, var6);
            ++var7;
        }
    }

    public void notifyBlocksOfNeighborChange(int var1, int var2, int var3, int var4) {
        this.notifyBlockOfNeighborChange(var1 - 1, var2, var3, var4);
        this.notifyBlockOfNeighborChange(var1 + 1, var2, var3, var4);
        this.notifyBlockOfNeighborChange(var1, var2 - 1, var3, var4);
        this.notifyBlockOfNeighborChange(var1, var2 + 1, var3, var4);
        this.notifyBlockOfNeighborChange(var1, var2, var3 - 1, var4);
        this.notifyBlockOfNeighborChange(var1, var2, var3 + 1, var4);
    }

    private void notifyBlockOfNeighborChange(int var1, int var2, int var3, int var4) {
        Block var5;
        if (!this.editingBlocks && !this.multiplayerWorld && (var5 = Block.blocksList[this.getBlockId(var1, var2, var3)]) != null) {
            var5.onNeighborBlockChange(this, var1, var2, var3, var4);
        }
    }

    public boolean canBlockSeeTheSky(int var1, int var2, int var3) {
        return this.getChunkFromChunkCoords(var1 >> 4, var3 >> 4).canBlockSeeTheSky(var1 & 0xF, var2, var3 & 0xF);
    }

    public int getBlockLightValue(int var1, int var2, int var3) {
        return this.getBlockLightValue_do(var1, var2, var3, true);
    }

    public int getBlockLightValue_do(int var1, int var2, int var3, boolean var4) {
        if (var1 >= -32000000 && var3 >= -32000000 && var1 < 32000000 && var3 <= 32000000) {
            int var5;
            if (var4 && ((var5 = this.getBlockId(var1, var2, var3)) == Block.stairSingle.blockID || var5 == Block.tilledField.blockID)) {
                int var6 = this.getBlockLightValue_do(var1, var2 + 1, var3, false);
                int var7 = this.getBlockLightValue_do(var1 + 1, var2, var3, false);
                int var8 = this.getBlockLightValue_do(var1 - 1, var2, var3, false);
                int var9 = this.getBlockLightValue_do(var1, var2, var3 + 1, false);
                int var10 = this.getBlockLightValue_do(var1, var2, var3 - 1, false);
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
                var5 = 15 - this.skylightSubtracted;
                if (var5 < 0) {
                    var5 = 0;
                }
                return var5;
            }
            Chunk var11 = this.getChunkFromChunkCoords(var1 >> 4, var3 >> 4);
            return var11.getBlockLightValue(var1 &= 0xF, var2, var3 &= 0xF, this.skylightSubtracted);
        }
        return 15;
    }

    public boolean canExistingBlockSeeTheSky(int var1, int var2, int var3) {
        if (var1 >= -32000000 && var3 >= -32000000 && var1 < 32000000 && var3 <= 32000000) {
            if (var2 < 0) {
                return false;
            }
            if (var2 >= 128) {
                return true;
            }
            if (!this.chunkExists(var1 >> 4, var3 >> 4)) {
                return false;
            }
            Chunk var4 = this.getChunkFromChunkCoords(var1 >> 4, var3 >> 4);
            return var4.canBlockSeeTheSky(var1 &= 0xF, var2, var3 &= 0xF);
        }
        return false;
    }

    public int getHeightValue(int var1, int var2) {
        if (var1 >= -32000000 && var2 >= -32000000 && var1 < 32000000 && var2 <= 32000000) {
            if (!this.chunkExists(var1 >> 4, var2 >> 4)) {
                return 0;
            }
            Chunk var3 = this.getChunkFromChunkCoords(var1 >> 4, var2 >> 4);
            return var3.getHeightValue(var1 & 0xF, var2 & 0xF);
        }
        return 0;
    }

    public void neighborLightPropagationChanged(EnumSkyBlock var1, int var2, int var3, int var4, int var5) {
        if ((!this.worldProvider.field_6478_e || var1 != EnumSkyBlock.Sky) && this.blockExists(var2, var3, var4)) {
            int var6;
            if (var1 == EnumSkyBlock.Sky) {
                if (this.canExistingBlockSeeTheSky(var2, var3, var4)) {
                    var5 = 15;
                }
            } else if (var1 == EnumSkyBlock.Block && Block.lightValue[var6 = this.getBlockId(var2, var3, var4)] > var5) {
                var5 = Block.lightValue[var6];
            }
            if (this.getSavedLightValue(var1, var2, var3, var4) != var5) {
                this.func_616_a(var1, var2, var3, var4, var2, var3, var4);
            }
        }
    }

    public int getSavedLightValue(EnumSkyBlock var1, int var2, int var3, int var4) {
        if (var3 >= 0 && var3 < 128 && var2 >= -32000000 && var4 >= -32000000 && var2 < 32000000 && var4 <= 32000000) {
            int var5 = var2 >> 4;
            int var6 = var4 >> 4;
            if (!this.chunkExists(var5, var6)) {
                return 0;
            }
            Chunk var7 = this.getChunkFromChunkCoords(var5, var6);
            return var7.getSavedLightValue(var1, var2 & 0xF, var3, var4 & 0xF);
        }
        return var1.field_1722_c;
    }

    public void setLightValue(EnumSkyBlock var1, int var2, int var3, int var4, int var5) {
        if (var2 >= -32000000 && var4 >= -32000000 && var2 < 32000000 && var4 <= 32000000 && var3 >= 0 && var3 < 128 && this.chunkExists(var2 >> 4, var4 >> 4)) {
            Chunk var6 = this.getChunkFromChunkCoords(var2 >> 4, var4 >> 4);
            var6.setLightValue(var1, var2 & 0xF, var3, var4 & 0xF, var5);
            int var7 = 0;
            while (var7 < this.worldAccesses.size()) {
                ((IWorldAccess)this.worldAccesses.get(var7)).markBlockAndNeighborsNeedsUpdate(var2, var3, var4);
                ++var7;
            }
        }
    }

    @Override
    public float getLightBrightness(int var1, int var2, int var3) {
        if (FullBrightHack.INSTANCE.status) {
            return 1.0f;
        }
        return this.worldProvider.lightBrightnessTable[this.getBlockLightValue(var1, var2, var3)];
    }

    public boolean isDaytime() {
        return this.skylightSubtracted < 4;
    }

    public MovingObjectPosition rayTraceBlocks(Vec3D var1, Vec3D var2) {
        return this.rayTraceBlocks_do(var1, var2, false);
    }

    public MovingObjectPosition rayTraceBlocks_do(Vec3D var1, Vec3D var2, boolean var3) {
        if (!(Double.isNaN(var1.xCoord) || Double.isNaN(var1.yCoord) || Double.isNaN(var1.zCoord))) {
            if (!(Double.isNaN(var2.xCoord) || Double.isNaN(var2.yCoord) || Double.isNaN(var2.zCoord))) {
                int var4 = MathHelper.floor_double(var2.xCoord);
                int var5 = MathHelper.floor_double(var2.yCoord);
                int var6 = MathHelper.floor_double(var2.zCoord);
                int var7 = MathHelper.floor_double(var1.xCoord);
                int var8 = MathHelper.floor_double(var1.yCoord);
                int var9 = MathHelper.floor_double(var1.zCoord);
                int var10 = 200;
                while (var10-- >= 0) {
                    MovingObjectPosition var34;
                    int var35;
                    if (Double.isNaN(var1.xCoord) || Double.isNaN(var1.yCoord) || Double.isNaN(var1.zCoord)) {
                        return null;
                    }
                    if (var7 == var4 && var8 == var5 && var9 == var6) {
                        return null;
                    }
                    double var11 = 999.0;
                    double var13 = 999.0;
                    double var15 = 999.0;
                    if (var4 > var7) {
                        var11 = (double)var7 + 1.0;
                    }
                    if (var4 < var7) {
                        var11 = (double)var7 + 0.0;
                    }
                    if (var5 > var8) {
                        var13 = (double)var8 + 1.0;
                    }
                    if (var5 < var8) {
                        var13 = (double)var8 + 0.0;
                    }
                    if (var6 > var9) {
                        var15 = (double)var9 + 1.0;
                    }
                    if (var6 < var9) {
                        var15 = (double)var9 + 0.0;
                    }
                    double var17 = 999.0;
                    double var19 = 999.0;
                    double var21 = 999.0;
                    double var23 = var2.xCoord - var1.xCoord;
                    double var25 = var2.yCoord - var1.yCoord;
                    double var27 = var2.zCoord - var1.zCoord;
                    if (var11 != 999.0) {
                        var17 = (var11 - var1.xCoord) / var23;
                    }
                    if (var13 != 999.0) {
                        var19 = (var13 - var1.yCoord) / var25;
                    }
                    if (var15 != 999.0) {
                        var21 = (var15 - var1.zCoord) / var27;
                    }
                    boolean var29 = false;
                    if (var17 < var19 && var17 < var21) {
                        var35 = var4 > var7 ? 4 : 5;
                        var1.xCoord = var11;
                        var1.yCoord += var25 * var17;
                        var1.zCoord += var27 * var17;
                    } else if (var19 < var21) {
                        var35 = var5 > var8 ? 0 : 1;
                        var1.xCoord += var23 * var19;
                        var1.yCoord = var13;
                        var1.zCoord += var27 * var19;
                    } else {
                        var35 = var6 > var9 ? 2 : 3;
                        var1.xCoord += var23 * var21;
                        var1.yCoord += var25 * var21;
                        var1.zCoord = var15;
                    }
                    Vec3D var30 = Vec3D.createVector(var1.xCoord, var1.yCoord, var1.zCoord);
                    var30.xCoord = MathHelper.floor_double(var1.xCoord);
                    var7 = (int)var30.xCoord;
                    if (var35 == 5) {
                        --var7;
                        var30.xCoord += 1.0;
                    }
                    var30.yCoord = MathHelper.floor_double(var1.yCoord);
                    var8 = (int)var30.yCoord;
                    if (var35 == 1) {
                        --var8;
                        var30.yCoord += 1.0;
                    }
                    var30.zCoord = MathHelper.floor_double(var1.zCoord);
                    var9 = (int)var30.zCoord;
                    if (var35 == 3) {
                        --var9;
                        var30.zCoord += 1.0;
                    }
                    int var31 = this.getBlockId(var7, var8, var9);
                    int var32 = this.getBlockMetadata(var7, var8, var9);
                    Block var33 = Block.blocksList[var31];
                    if (var31 <= 0 || !var33.canCollideCheck(var32, var3) || (var34 = var33.collisionRayTrace(this, var7, var8, var9, var1, var2)) == null) continue;
                    return var34;
                }
                return null;
            }
            return null;
        }
        return null;
    }

    public void playSoundAtEntity(Entity var1, String var2, float var3, float var4) {
        int var5 = 0;
        while (var5 < this.worldAccesses.size()) {
            ((IWorldAccess)this.worldAccesses.get(var5)).playSound(var2, var1.posX, var1.posY - (double)var1.yOffset, var1.posZ, var3, var4);
            ++var5;
        }
    }

    public void playSoundEffect(double var1, double var3, double var5, String var7, float var8, float var9) {
        int var10 = 0;
        while (var10 < this.worldAccesses.size()) {
            ((IWorldAccess)this.worldAccesses.get(var10)).playSound(var7, var1, var3, var5, var8, var9);
            ++var10;
        }
    }

    public void playRecord(String var1, int var2, int var3, int var4) {
        int var5 = 0;
        while (var5 < this.worldAccesses.size()) {
            ((IWorldAccess)this.worldAccesses.get(var5)).playRecord(var1, var2, var3, var4);
            ++var5;
        }
    }

    public void spawnParticle(String var1, double var2, double var4, double var6, double var8, double var10, double var12) {
        int var14 = 0;
        while (var14 < this.worldAccesses.size()) {
            ((IWorldAccess)this.worldAccesses.get(var14)).spawnParticle(var1, var2, var4, var6, var8, var10, var12);
            ++var14;
        }
    }

    public boolean entityJoinedWorld(Entity var1) {
        int var2 = MathHelper.floor_double(var1.posX / 16.0);
        int var3 = MathHelper.floor_double(var1.posZ / 16.0);
        boolean var4 = false;
        if (var1 instanceof EntityPlayer) {
            var4 = true;
        }
        if (!var4 && !this.chunkExists(var2, var3)) {
            return false;
        }
        if (var1 instanceof EntityPlayer) {
            EntityPlayer var5 = (EntityPlayer)var1;
            this.playerEntities.add(var5);
            this.updateAllPlayersSleepingFlag();
        }
        this.getChunkFromChunkCoords(var2, var3).addEntity(var1);
        this.loadedEntityList.add(var1);
        this.obtainEntitySkin(var1);
        return true;
    }

    protected void obtainEntitySkin(Entity var1) {
        int var2 = 0;
        while (var2 < this.worldAccesses.size()) {
            ((IWorldAccess)this.worldAccesses.get(var2)).obtainEntitySkin(var1);
            ++var2;
        }
    }

    protected void releaseEntitySkin(Entity var1) {
        int var2 = 0;
        while (var2 < this.worldAccesses.size()) {
            ((IWorldAccess)this.worldAccesses.get(var2)).releaseEntitySkin(var1);
            ++var2;
        }
    }

    public void setEntityDead(Entity var1) {
        if (var1.riddenByEntity != null) {
            var1.riddenByEntity.mountEntity(null);
        }
        if (var1.ridingEntity != null) {
            var1.mountEntity(null);
        }
        var1.setEntityDead();
        if (var1 instanceof EntityPlayer) {
            this.playerEntities.remove((EntityPlayer)var1);
            this.updateAllPlayersSleepingFlag();
        }
    }

    public void addWorldAccess(IWorldAccess var1) {
        this.worldAccesses.add(var1);
    }

    public void removeWorldAccess(IWorldAccess var1) {
        this.worldAccesses.remove(var1);
    }

    public List getCollidingBoundingBoxes(Entity var1, AxisAlignedBB var2) {
        this.field_9428_I.clear();
        int var3 = MathHelper.floor_double(var2.minX);
        int var4 = MathHelper.floor_double(var2.maxX + 1.0);
        int var5 = MathHelper.floor_double(var2.minY);
        int var6 = MathHelper.floor_double(var2.maxY + 1.0);
        int var7 = MathHelper.floor_double(var2.minZ);
        int var8 = MathHelper.floor_double(var2.maxZ + 1.0);
        int var9 = var3;
        while (var9 < var4) {
            int var10 = var7;
            while (var10 < var8) {
                if (this.blockExists(var9, 64, var10)) {
                    int var11 = var5 - 1;
                    while (var11 < var6) {
                        Block var12 = Block.blocksList[this.getBlockId(var9, var11, var10)];
                        if (var12 != null) {
                            var12.getCollidingBoundingBoxes(this, var9, var11, var10, var2, this.field_9428_I);
                        }
                        ++var11;
                    }
                }
                ++var10;
            }
            ++var9;
        }
        double var14 = 0.25;
        List var15 = this.getEntitiesWithinAABBExcludingEntity(var1, var2.expand(var14, var14, var14));
        int var16 = 0;
        while (var16 < var15.size()) {
            AxisAlignedBB var13 = ((Entity)var15.get(var16)).getBoundingBox();
            if (var13 != null && var13.intersectsWith(var2)) {
                this.field_9428_I.add(var13);
            }
            if ((var13 = var1.getCollisionBox((Entity)var15.get(var16))) != null && var13.intersectsWith(var2)) {
                this.field_9428_I.add(var13);
            }
            ++var16;
        }
        return this.field_9428_I;
    }

    public int calculateSkylightSubtracted(float var1) {
        float var2 = this.getCelestialAngle(var1);
        float var3 = 1.0f - (MathHelper.cos(var2 * (float)Math.PI * 2.0f) * 2.0f + 0.5f);
        if (var3 < 0.0f) {
            var3 = 0.0f;
        }
        if (var3 > 1.0f) {
            var3 = 1.0f;
        }
        return (int)(var3 * 11.0f);
    }

    public Vec3D func_4079_a(Entity var1, float var2) {
        float var3 = this.getCelestialAngle(var2);
        float var4 = MathHelper.cos(var3 * (float)Math.PI * 2.0f) * 2.0f + 0.5f;
        if (var4 < 0.0f) {
            var4 = 0.0f;
        }
        if (var4 > 1.0f) {
            var4 = 1.0f;
        }
        int var5 = MathHelper.floor_double(var1.posX);
        int var6 = MathHelper.floor_double(var1.posZ);
        float var7 = (float)this.getWorldChunkManager().func_4072_b(var5, var6);
        int var8 = this.getWorldChunkManager().func_4073_a(var5, var6).getSkyColorByTemp(var7);
        float var9 = (float)(var8 >> 16 & 0xFF) / 255.0f;
        float var10 = (float)(var8 >> 8 & 0xFF) / 255.0f;
        float var11 = (float)(var8 & 0xFF) / 255.0f;
        return Vec3D.createVector(var9 *= var4, var10 *= var4, var11 *= var4);
    }

    public float getCelestialAngle(float var1) {
        return this.worldProvider.calculateCelestialAngle(this.worldInfo.getWorldTime(), var1);
    }

    public Vec3D func_628_d(float var1) {
        float var2 = this.getCelestialAngle(var1);
        float var3 = MathHelper.cos(var2 * (float)Math.PI * 2.0f) * 2.0f + 0.5f;
        if (var3 < 0.0f) {
            var3 = 0.0f;
        }
        if (var3 > 1.0f) {
            var3 = 1.0f;
        }
        float var4 = (float)(this.field_1019_F >> 16 & 0xFFL) / 255.0f;
        float var5 = (float)(this.field_1019_F >> 8 & 0xFFL) / 255.0f;
        float var6 = (float)(this.field_1019_F & 0xFFL) / 255.0f;
        return Vec3D.createVector(var4 *= var3 * 0.9f + 0.1f, var5 *= var3 * 0.9f + 0.1f, var6 *= var3 * 0.85f + 0.15f);
    }

    public Vec3D getFogColor(float var1) {
        float var2 = this.getCelestialAngle(var1);
        return this.worldProvider.func_4096_a(var2, var1);
    }

    public int findTopSolidBlock(int var1, int var2) {
        Chunk var3 = this.getChunkFromBlockCoords(var1, var2);
        int var4 = 127;
        while (this.getBlockMaterial(var1, var4, var2).getIsSolid() && var4 > 0) {
            --var4;
        }
        var1 &= 0xF;
        var2 &= 0xF;
        while (var4 > 0) {
            int var5 = var3.getBlockID(var1, var4, var2);
            if (var5 != 0 && (Block.blocksList[var5].blockMaterial.getIsSolid() || Block.blocksList[var5].blockMaterial.getIsLiquid())) {
                return var4 + 1;
            }
            --var4;
        }
        return -1;
    }

    public int func_696_e(int var1, int var2) {
        return this.getChunkFromBlockCoords(var1, var2).getHeightValue(var1 & 0xF, var2 & 0xF);
    }

    public float getStarBrightness(float var1) {
        float var2 = this.getCelestialAngle(var1);
        float var3 = 1.0f - (MathHelper.cos(var2 * (float)Math.PI * 2.0f) * 2.0f + 0.75f);
        if (var3 < 0.0f) {
            var3 = 0.0f;
        }
        if (var3 > 1.0f) {
            var3 = 1.0f;
        }
        return var3 * var3 * 0.5f;
    }

    public void scheduleBlockUpdate(int var1, int var2, int var3, int var4, int var5) {
        NextTickListEntry var6 = new NextTickListEntry(var1, var2, var3, var4);
        int var7 = 8;
        if (this.scheduledUpdatesAreImmediate) {
            int var8;
            if (this.checkChunksExist(var6.xCoord - var7, var6.yCoord - var7, var6.zCoord - var7, var6.xCoord + var7, var6.yCoord + var7, var6.zCoord + var7) && (var8 = this.getBlockId(var6.xCoord, var6.yCoord, var6.zCoord)) == var6.blockID && var8 > 0) {
                Block.blocksList[var8].updateTick(this, var6.xCoord, var6.yCoord, var6.zCoord, this.rand);
            }
        } else if (this.checkChunksExist(var1 - var7, var2 - var7, var3 - var7, var1 + var7, var2 + var7, var3 + var7)) {
            if (var4 > 0) {
                var6.setScheduledTime((long)var5 + this.worldInfo.getWorldTime());
            }
            if (!this.scheduledTickSet.contains(var6)) {
                this.scheduledTickSet.add(var6);
                this.scheduledTickTreeSet.add(var6);
            }
        }
    }

    public void func_633_c() {
        int var4;
        int var3;
        Entity var2;
        this.loadedEntityList.removeAll(this.unloadedEntityList);
        int var1 = 0;
        while (var1 < this.unloadedEntityList.size()) {
            var2 = (Entity)this.unloadedEntityList.get(var1);
            var3 = var2.chunkCoordX;
            var4 = var2.chunkCoordZ;
            if (var2.addedToChunk && this.chunkExists(var3, var4)) {
                this.getChunkFromChunkCoords(var3, var4).func_1015_b(var2);
            }
            ++var1;
        }
        var1 = 0;
        while (var1 < this.unloadedEntityList.size()) {
            this.releaseEntitySkin((Entity)this.unloadedEntityList.get(var1));
            ++var1;
        }
        this.unloadedEntityList.clear();
        var1 = 0;
        while (var1 < this.loadedEntityList.size()) {
            block12: {
                block11: {
                    var2 = (Entity)this.loadedEntityList.get(var1);
                    if (var2.ridingEntity == null) break block11;
                    if (!var2.ridingEntity.isDead && var2.ridingEntity.riddenByEntity == var2) break block12;
                    var2.ridingEntity.riddenByEntity = null;
                    var2.ridingEntity = null;
                }
                if (!var2.isDead) {
                    this.updateEntity(var2);
                }
                if (var2.isDead) {
                    var3 = var2.chunkCoordX;
                    var4 = var2.chunkCoordZ;
                    if (var2.addedToChunk && this.chunkExists(var3, var4)) {
                        this.getChunkFromChunkCoords(var3, var4).func_1015_b(var2);
                    }
                    this.loadedEntityList.remove(var1--);
                    this.releaseEntitySkin(var2);
                }
            }
            ++var1;
        }
        var1 = 0;
        while (var1 < this.loadedTileEntityList.size()) {
            TileEntity var5 = (TileEntity)this.loadedTileEntityList.get(var1);
            var5.updateEntity();
            ++var1;
        }
    }

    public void updateEntity(Entity var1) {
        this.updateEntityWithOptionalForce(var1, true);
    }

    public void updateEntityWithOptionalForce(Entity var1, boolean var2) {
        int var3 = MathHelper.floor_double(var1.posX);
        int var4 = MathHelper.floor_double(var1.posZ);
        int var5 = 32;
        if (!var2 || this.checkChunksExist(var3 - var5, 0, var4 - var5, var3 + var5, 128, var4 + var5) || var1 == Client.mc.thePlayer) {
            var1.lastTickPosX = var1.posX;
            var1.lastTickPosY = var1.posY;
            var1.lastTickPosZ = var1.posZ;
            var1.prevRotationYaw = var1.rotationYaw;
            var1.prevRotationPitch = var1.rotationPitch;
            if (var2 && var1.addedToChunk || var1 == Client.mc.thePlayer) {
                if (var1.ridingEntity != null) {
                    var1.updateRidden();
                } else {
                    var1.onUpdate();
                }
            }
            if (Double.isNaN(var1.posX) || Double.isInfinite(var1.posX)) {
                var1.posX = var1.lastTickPosX;
            }
            if (Double.isNaN(var1.posY) || Double.isInfinite(var1.posY)) {
                var1.posY = var1.lastTickPosY;
            }
            if (Double.isNaN(var1.posZ) || Double.isInfinite(var1.posZ)) {
                var1.posZ = var1.lastTickPosZ;
            }
            if (Double.isNaN(var1.rotationPitch) || Double.isInfinite(var1.rotationPitch)) {
                var1.rotationPitch = var1.prevRotationPitch;
            }
            if (Double.isNaN(var1.rotationYaw) || Double.isInfinite(var1.rotationYaw)) {
                var1.rotationYaw = var1.prevRotationYaw;
            }
            int var6 = MathHelper.floor_double(var1.posX / 16.0);
            int var7 = MathHelper.floor_double(var1.posY / 16.0);
            int var8 = MathHelper.floor_double(var1.posZ / 16.0);
            if (!var1.addedToChunk || var1.chunkCoordX != var6 || var1.chunkCoordY != var7 || var1.chunkCoordZ != var8) {
                if (var1.addedToChunk && this.chunkExists(var1.chunkCoordX, var1.chunkCoordZ)) {
                    this.getChunkFromChunkCoords(var1.chunkCoordX, var1.chunkCoordZ).func_1016_a(var1, var1.chunkCoordY);
                }
                if (this.chunkExists(var6, var8)) {
                    var1.addedToChunk = true;
                    this.getChunkFromChunkCoords(var6, var8).addEntity(var1);
                } else {
                    var1.addedToChunk = false;
                }
            }
            if (var2 && var1.addedToChunk && var1.riddenByEntity != null) {
                if (!var1.riddenByEntity.isDead && var1.riddenByEntity.ridingEntity == var1) {
                    this.updateEntity(var1.riddenByEntity);
                } else {
                    var1.riddenByEntity.ridingEntity = null;
                    var1.riddenByEntity = null;
                }
            }
        }
    }

    public boolean checkIfAABBIsClear(AxisAlignedBB var1) {
        List var2 = this.getEntitiesWithinAABBExcludingEntity(null, var1);
        int var3 = 0;
        while (var3 < var2.size()) {
            Entity var4 = (Entity)var2.get(var3);
            if (!var4.isDead && var4.preventEntitySpawning) {
                return false;
            }
            ++var3;
        }
        return true;
    }

    public boolean getIsAnyLiquid(AxisAlignedBB var1) {
        int var2 = MathHelper.floor_double(var1.minX);
        int var3 = MathHelper.floor_double(var1.maxX + 1.0);
        int var4 = MathHelper.floor_double(var1.minY);
        int var5 = MathHelper.floor_double(var1.maxY + 1.0);
        int var6 = MathHelper.floor_double(var1.minZ);
        int var7 = MathHelper.floor_double(var1.maxZ + 1.0);
        if (var1.minX < 0.0) {
            --var2;
        }
        if (var1.minY < 0.0) {
            --var4;
        }
        if (var1.minZ < 0.0) {
            --var6;
        }
        int var8 = var2;
        while (var8 < var3) {
            int var9 = var4;
            while (var9 < var5) {
                int var10 = var6;
                while (var10 < var7) {
                    Block var11 = Block.blocksList[this.getBlockId(var8, var9, var10)];
                    if (var11 != null && var11.blockMaterial.getIsLiquid()) {
                        return true;
                    }
                    ++var10;
                }
                ++var9;
            }
            ++var8;
        }
        return false;
    }

    public boolean isBoundingBoxBurning(AxisAlignedBB var1) {
        int var7;
        int var2 = MathHelper.floor_double(var1.minX);
        int var3 = MathHelper.floor_double(var1.maxX + 1.0);
        int var4 = MathHelper.floor_double(var1.minY);
        int var5 = MathHelper.floor_double(var1.maxY + 1.0);
        int var6 = MathHelper.floor_double(var1.minZ);
        if (this.checkChunksExist(var2, var4, var6, var3, var5, var7 = MathHelper.floor_double(var1.maxZ + 1.0))) {
            int var8 = var2;
            while (var8 < var3) {
                int var9 = var4;
                while (var9 < var5) {
                    int var10 = var6;
                    while (var10 < var7) {
                        int var11 = this.getBlockId(var8, var9, var10);
                        if (var11 == Block.fire.blockID || var11 == Block.lavaMoving.blockID || var11 == Block.lavaStill.blockID) {
                            return true;
                        }
                        ++var10;
                    }
                    ++var9;
                }
                ++var8;
            }
        }
        return false;
    }

    public boolean handleMaterialAcceleration(AxisAlignedBB var1, Material var2, Entity var3) {
        int var9;
        int var4 = MathHelper.floor_double(var1.minX);
        int var5 = MathHelper.floor_double(var1.maxX + 1.0);
        int var6 = MathHelper.floor_double(var1.minY);
        int var7 = MathHelper.floor_double(var1.maxY + 1.0);
        int var8 = MathHelper.floor_double(var1.minZ);
        if (!this.checkChunksExist(var4, var6, var8, var5, var7, var9 = MathHelper.floor_double(var1.maxZ + 1.0))) {
            return false;
        }
        boolean var10 = false;
        Vec3D var11 = Vec3D.createVector(0.0, 0.0, 0.0);
        int var12 = var4;
        while (var12 < var5) {
            int var13 = var6;
            while (var13 < var7) {
                int var14 = var8;
                while (var14 < var9) {
                    double var16;
                    Block var15 = Block.blocksList[this.getBlockId(var12, var13, var14)];
                    if (var15 != null && var15.blockMaterial == var2 && (double)var7 >= (var16 = (double)((float)(var13 + 1) - BlockFluids.getPercentAir(this.getBlockMetadata(var12, var13, var14))))) {
                        var10 = true;
                        var15.velocityToAddToEntity(this, var12, var13, var14, var3, var11);
                    }
                    ++var14;
                }
                ++var13;
            }
            ++var12;
        }
        if (var11.lengthVector() > 0.0) {
            var11 = var11.normalize();
            double var18 = 0.004;
            var3.motionX += var11.xCoord * var18;
            var3.motionY += var11.yCoord * var18;
            var3.motionZ += var11.zCoord * var18;
        }
        return var10;
    }

    public boolean isMaterialInBB(AxisAlignedBB var1, Material var2) {
        int var3 = MathHelper.floor_double(var1.minX);
        int var4 = MathHelper.floor_double(var1.maxX + 1.0);
        int var5 = MathHelper.floor_double(var1.minY);
        int var6 = MathHelper.floor_double(var1.maxY + 1.0);
        int var7 = MathHelper.floor_double(var1.minZ);
        int var8 = MathHelper.floor_double(var1.maxZ + 1.0);
        int var9 = var3;
        while (var9 < var4) {
            int var10 = var5;
            while (var10 < var6) {
                int var11 = var7;
                while (var11 < var8) {
                    Block var12 = Block.blocksList[this.getBlockId(var9, var10, var11)];
                    if (var12 != null && var12.blockMaterial == var2) {
                        return true;
                    }
                    ++var11;
                }
                ++var10;
            }
            ++var9;
        }
        return false;
    }

    public boolean isAABBInMaterial(AxisAlignedBB var1, Material var2) {
        int var3 = MathHelper.floor_double(var1.minX);
        int var4 = MathHelper.floor_double(var1.maxX + 1.0);
        int var5 = MathHelper.floor_double(var1.minY);
        int var6 = MathHelper.floor_double(var1.maxY + 1.0);
        int var7 = MathHelper.floor_double(var1.minZ);
        int var8 = MathHelper.floor_double(var1.maxZ + 1.0);
        int var9 = var3;
        while (var9 < var4) {
            int var10 = var5;
            while (var10 < var6) {
                int var11 = var7;
                while (var11 < var8) {
                    Block var12 = Block.blocksList[this.getBlockId(var9, var10, var11)];
                    if (var12 != null && var12.blockMaterial == var2) {
                        int var13 = this.getBlockMetadata(var9, var10, var11);
                        double var14 = var10 + 1;
                        if (var13 < 8) {
                            var14 = (double)(var10 + 1) - (double)var13 / 8.0;
                        }
                        if (var14 >= var1.minY) {
                            return true;
                        }
                    }
                    ++var11;
                }
                ++var10;
            }
            ++var9;
        }
        return false;
    }

    public Explosion createExplosion(Entity var1, double var2, double var4, double var6, float var8) {
        return this.newExplosion(var1, var2, var4, var6, var8, false);
    }

    public Explosion newExplosion(Entity var1, double var2, double var4, double var6, float var8, boolean var9) {
        Explosion var10 = new Explosion(this, var1, var2, var4, var6, var8);
        var10.field_12257_a = var9;
        var10.doExplosionA();
        var10.doExplosionB();
        return var10;
    }

    public float func_675_a(Vec3D var1, AxisAlignedBB var2) {
        double var3 = 1.0 / ((var2.maxX - var2.minX) * 2.0 + 1.0);
        double var5 = 1.0 / ((var2.maxY - var2.minY) * 2.0 + 1.0);
        double var7 = 1.0 / ((var2.maxZ - var2.minZ) * 2.0 + 1.0);
        int var9 = 0;
        int var10 = 0;
        float var11 = 0.0f;
        while (var11 <= 1.0f) {
            float var12 = 0.0f;
            while (var12 <= 1.0f) {
                float var13 = 0.0f;
                while (var13 <= 1.0f) {
                    double var14 = var2.minX + (var2.maxX - var2.minX) * (double)var11;
                    double var16 = var2.minY + (var2.maxY - var2.minY) * (double)var12;
                    double var18 = var2.minZ + (var2.maxZ - var2.minZ) * (double)var13;
                    if (this.rayTraceBlocks(Vec3D.createVector(var14, var16, var18), var1) == null) {
                        ++var9;
                    }
                    ++var10;
                    var13 = (float)((double)var13 + var7);
                }
                var12 = (float)((double)var12 + var5);
            }
            var11 = (float)((double)var11 + var3);
        }
        return (float)var9 / (float)var10;
    }

    public void onBlockHit(int var1, int var2, int var3, int var4) {
        if (var4 == 0) {
            --var2;
        }
        if (var4 == 1) {
            ++var2;
        }
        if (var4 == 2) {
            --var3;
        }
        if (var4 == 3) {
            ++var3;
        }
        if (var4 == 4) {
            --var1;
        }
        if (var4 == 5) {
            ++var1;
        }
        if (this.getBlockId(var1, var2, var3) == Block.fire.blockID) {
            this.playSoundEffect((float)var1 + 0.5f, (float)var2 + 0.5f, (float)var3 + 0.5f, "random.fizz", 0.5f, 2.6f + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.8f);
            this.setBlockWithNotify(var1, var2, var3, 0);
        }
    }

    public Entity func_4085_a(Class var1) {
        return null;
    }

    public String func_687_d() {
        return "All: " + this.loadedEntityList.size();
    }

    public String func_21119_g() {
        return this.chunkProvider.toString();
    }

    @Override
    public TileEntity getBlockTileEntity(int var1, int var2, int var3) {
        Chunk var4 = this.getChunkFromChunkCoords(var1 >> 4, var3 >> 4);
        return var4 != null ? var4.getChunkBlockTileEntity(var1 & 0xF, var2, var3 & 0xF) : null;
    }

    public void setBlockTileEntity(int var1, int var2, int var3, TileEntity var4) {
        Chunk var5 = this.getChunkFromChunkCoords(var1 >> 4, var3 >> 4);
        if (var5 != null) {
            var5.setChunkBlockTileEntity(var1 & 0xF, var2, var3 & 0xF, var4);
        }
    }

    public void removeBlockTileEntity(int var1, int var2, int var3) {
        Chunk var4 = this.getChunkFromChunkCoords(var1 >> 4, var3 >> 4);
        if (var4 != null) {
            var4.removeChunkBlockTileEntity(var1 & 0xF, var2, var3 & 0xF);
        }
    }

    @Override
    public boolean isBlockOpaqueCube(int var1, int var2, int var3) {
        Block var4 = Block.blocksList[this.getBlockId(var1, var2, var3)];
        return var4 == null ? false : var4.isOpaqueCube();
    }

    public void func_651_a(IProgressUpdate var1) {
        this.saveWorld(true, var1);
    }

    public boolean func_6465_g() {
        boolean var2;
        if (this.field_4204_J >= 50) {
            return false;
        }
        ++this.field_4204_J;
        try {
            int var1 = 500;
            while (this.field_1051_z.size() > 0) {
                if (--var1 <= 0) {
                    boolean var22;
                    boolean bl = var22 = true;
                    return bl;
                }
                ((MetadataChunkBlock)this.field_1051_z.remove(this.field_1051_z.size() - 1)).func_4127_a(this);
            }
            var2 = false;
        }
        finally {
            --this.field_4204_J;
        }
        return var2;
    }

    public void func_616_a(EnumSkyBlock var1, int var2, int var3, int var4, int var5, int var6, int var7) {
        this.func_627_a(var1, var2, var3, var4, var5, var6, var7, true);
    }

    public void func_627_a(EnumSkyBlock var1, int var2, int var3, int var4, int var5, int var6, int var7, boolean var8) {
        if (!this.worldProvider.field_6478_e || var1 != EnumSkyBlock.Sky) {
            if (++field_9429_y == 50) {
                --field_9429_y;
            } else {
                int var9 = (var5 + var2) / 2;
                int var10 = (var7 + var4) / 2;
                if (!this.blockExists(var9, 64, var10)) {
                    --field_9429_y;
                } else if (!this.getChunkFromBlockCoords(var9, var10).func_21167_h()) {
                    int var12;
                    int var11 = this.field_1051_z.size();
                    if (var8) {
                        var12 = 5;
                        if (var12 > var11) {
                            var12 = var11;
                        }
                        int var13 = 0;
                        while (var13 < var12) {
                            MetadataChunkBlock var14 = (MetadataChunkBlock)this.field_1051_z.get(this.field_1051_z.size() - var13 - 1);
                            if (var14.field_1299_a == var1 && var14.func_866_a(var2, var3, var4, var5, var6, var7)) {
                                --field_9429_y;
                                return;
                            }
                            ++var13;
                        }
                    }
                    this.field_1051_z.add(new MetadataChunkBlock(var1, var2, var3, var4, var5, var6, var7));
                    var12 = 1000000;
                    if (this.field_1051_z.size() > 1000000) {
                        System.out.println("More than " + var12 + " updates, aborting lighting updates");
                        this.field_1051_z.clear();
                    }
                    --field_9429_y;
                }
            }
        }
    }

    public void calculateInitialSkylight() {
        int var1 = this.calculateSkylightSubtracted(1.0f);
        if (var1 != this.skylightSubtracted) {
            this.skylightSubtracted = var1;
        }
    }

    public void setAllowedMobSpawns(boolean var1, boolean var2) {
        this.spawnHostileMobs = var1;
        this.spawnPeacefulMobs = var2;
    }

    public void tick() {
        int var4;
        long var2;
        if (this.isAllPlayersFullyAsleep()) {
            boolean var1 = false;
            if (this.spawnHostileMobs && this.difficultySetting >= 1) {
                var1 = SpawnerAnimals.performSleepSpawning(this, this.playerEntities);
            }
            if (!var1) {
                var2 = this.worldInfo.getWorldTime() + 24000L;
                this.worldInfo.setWorldTime(var2 - var2 % 24000L);
                this.wakeUpAllPlayers();
            }
        }
        SpawnerAnimals.performSpawning(this, this.spawnHostileMobs, this.spawnPeacefulMobs);
        this.chunkProvider.func_532_a();
        long oldTime = this.getWorldTime();
        if (LockTimeHack.INSTANCE.status) {
            this.worldInfo.setWorldTime(LockTimeHack.INSTANCE.lockedTime.value);
        }
        if ((var4 = this.calculateSkylightSubtracted(1.0f)) != this.skylightSubtracted) {
            this.skylightSubtracted = var4;
            int var5 = 0;
            while (var5 < this.worldAccesses.size()) {
                ((IWorldAccess)this.worldAccesses.get(var5)).updateAllRenderers();
                ++var5;
            }
        }
        if (LockTimeHack.INSTANCE.status) {
            this.worldInfo.setWorldTime(oldTime);
        }
        if ((var2 = this.worldInfo.getWorldTime() + 1L) % (long)this.autosavePeriod == 0L) {
            this.saveWorld(false, null);
        }
        this.worldInfo.setWorldTime(var2);
        this.TickUpdates(false);
        this.updateBlocksAndPlayCaveSounds();
    }

    protected void updateBlocksAndPlayCaveSounds() {
        int var7;
        int var6;
        int var4;
        int var3;
        this.field_9427_K.clear();
        int var1 = 0;
        while (var1 < this.playerEntities.size()) {
            EntityPlayer var2 = (EntityPlayer)this.playerEntities.get(var1);
            var3 = MathHelper.floor_double(var2.posX / 16.0);
            var4 = MathHelper.floor_double(var2.posZ / 16.0);
            int var5 = 9;
            var6 = -var5;
            while (var6 <= var5) {
                var7 = -var5;
                while (var7 <= var5) {
                    this.field_9427_K.add(new ChunkCoordIntPair(var6 + var3, var7 + var4));
                    ++var7;
                }
                ++var6;
            }
            ++var1;
        }
        if (this.field_9426_L > 0) {
            --this.field_9426_L;
        }
        for (ChunkCoordIntPair var13 : this.field_9427_K) {
            int var10;
            int var9;
            int var8;
            var3 = var13.chunkXPos * 16;
            var4 = var13.chunkZPos * 16;
            Chunk var14 = this.getChunkFromChunkCoords(var13.chunkXPos, var13.chunkZPos);
            if (this.field_9426_L == 0) {
                EntityPlayer var11;
                this.field_9437_g = this.field_9437_g * 3 + this.field_9436_h;
                var6 = this.field_9437_g >> 2;
                var7 = var6 & 0xF;
                var8 = var6 >> 8 & 0xF;
                var9 = var6 >> 16 & 0x7F;
                var10 = var14.getBlockID(var7, var9, var8);
                if (var10 == 0 && this.getBlockLightValue(var7 += var3, var9, var8 += var4) <= this.rand.nextInt(8) && this.getSavedLightValue(EnumSkyBlock.Sky, var7, var9, var8) <= 0 && (var11 = this.getClosestPlayer((double)var7 + 0.5, (double)var9 + 0.5, (double)var8 + 0.5, 8.0)) != null && var11.getDistanceSq((double)var7 + 0.5, (double)var9 + 0.5, (double)var8 + 0.5) > 4.0) {
                    this.playSoundEffect((double)var7 + 0.5, (double)var9 + 0.5, (double)var8 + 0.5, "ambient.cave.cave", 0.7f, 0.8f + this.rand.nextFloat() * 0.2f);
                    this.field_9426_L = this.rand.nextInt(12000) + 6000;
                }
            }
            var6 = 0;
            while (var6 < 80) {
                this.field_9437_g = this.field_9437_g * 3 + this.field_9436_h;
                var7 = this.field_9437_g >> 2;
                var8 = var7 & 0xF;
                var9 = var7 >> 8 & 0xF;
                var10 = var7 >> 16 & 0x7F;
                int var15 = var14.blocks[var8 << 11 | var9 << 7 | var10] & 0xFF;
                if (Block.tickOnLoad[var15]) {
                    Block.blocksList[var15].updateTick(this, var8 + var3, var10, var9 + var4, this.rand);
                }
                ++var6;
            }
        }
    }

    public boolean TickUpdates(boolean var1) {
        int var2 = this.scheduledTickTreeSet.size();
        if (var2 != this.scheduledTickSet.size()) {
            throw new IllegalStateException("TickNextTick list out of synch");
        }
        if (var2 > 1000) {
            var2 = 1000;
        }
        int var3 = 0;
        while (var3 < var2) {
            int var6;
            NextTickListEntry var4 = (NextTickListEntry)this.scheduledTickTreeSet.first();
            if (!var1 && var4.scheduledTime > this.worldInfo.getWorldTime()) break;
            this.scheduledTickTreeSet.remove(var4);
            this.scheduledTickSet.remove(var4);
            int var5 = 8;
            if (this.checkChunksExist(var4.xCoord - var5, var4.yCoord - var5, var4.zCoord - var5, var4.xCoord + var5, var4.yCoord + var5, var4.zCoord + var5) && (var6 = this.getBlockId(var4.xCoord, var4.yCoord, var4.zCoord)) == var4.blockID && var6 > 0) {
                Block.blocksList[var6].updateTick(this, var4.xCoord, var4.yCoord, var4.zCoord, this.rand);
            }
            ++var3;
        }
        return this.scheduledTickTreeSet.size() != 0;
    }

    public void randomDisplayUpdates(int var1, int var2, int var3) {
        int var4 = 16;
        Random var5 = new Random();
        int var6 = 0;
        while (var6 < 1000) {
            int var9;
            int var8;
            int var7 = var1 + this.rand.nextInt(var4) - this.rand.nextInt(var4);
            int var10 = this.getBlockId(var7, var8 = var2 + this.rand.nextInt(var4) - this.rand.nextInt(var4), var9 = var3 + this.rand.nextInt(var4) - this.rand.nextInt(var4));
            if (var10 > 0) {
                Block.blocksList[var10].randomDisplayTick(this, var7, var8, var9, var5);
            }
            ++var6;
        }
    }

    public List getEntitiesWithinAABBExcludingEntity(Entity var1, AxisAlignedBB var2) {
        this.field_1012_M.clear();
        int var3 = MathHelper.floor_double((var2.minX - 2.0) / 16.0);
        int var4 = MathHelper.floor_double((var2.maxX + 2.0) / 16.0);
        int var5 = MathHelper.floor_double((var2.minZ - 2.0) / 16.0);
        int var6 = MathHelper.floor_double((var2.maxZ + 2.0) / 16.0);
        int var7 = var3;
        while (var7 <= var4) {
            int var8 = var5;
            while (var8 <= var6) {
                if (this.chunkExists(var7, var8)) {
                    this.getChunkFromChunkCoords(var7, var8).getEntitiesWithinAABBForEntity(var1, var2, this.field_1012_M);
                }
                ++var8;
            }
            ++var7;
        }
        return this.field_1012_M;
    }

    public List getEntitiesWithinAABB(Class var1, AxisAlignedBB var2) {
        int var3 = MathHelper.floor_double((var2.minX - 2.0) / 16.0);
        int var4 = MathHelper.floor_double((var2.maxX + 2.0) / 16.0);
        int var5 = MathHelper.floor_double((var2.minZ - 2.0) / 16.0);
        int var6 = MathHelper.floor_double((var2.maxZ + 2.0) / 16.0);
        ArrayList var7 = new ArrayList();
        int var8 = var3;
        while (var8 <= var4) {
            int var9 = var5;
            while (var9 <= var6) {
                if (this.chunkExists(var8, var9)) {
                    this.getChunkFromChunkCoords(var8, var9).getEntitiesOfTypeWithinAAAB(var1, var2, var7);
                }
                ++var9;
            }
            ++var8;
        }
        return var7;
    }

    public List getLoadedEntityList() {
        return this.loadedEntityList;
    }

    public void func_698_b(int var1, int var2, int var3, TileEntity var4) {
        if (this.blockExists(var1, var2, var3)) {
            this.getChunkFromBlockCoords(var1, var3).setChunkModified();
        }
        int var5 = 0;
        while (var5 < this.worldAccesses.size()) {
            ((IWorldAccess)this.worldAccesses.get(var5)).doNothingWithTileEntity(var1, var2, var3, var4);
            ++var5;
        }
    }

    public int countEntities(Class var1) {
        int var2 = 0;
        int var3 = 0;
        while (var3 < this.loadedEntityList.size()) {
            Entity var4 = (Entity)this.loadedEntityList.get(var3);
            if (var1.isAssignableFrom(var4.getClass())) {
                ++var2;
            }
            ++var3;
        }
        return var2;
    }

    public void func_636_a(List var1) {
        this.loadedEntityList.addAll(var1);
        int var2 = 0;
        while (var2 < var1.size()) {
            this.obtainEntitySkin((Entity)var1.get(var2));
            ++var2;
        }
    }

    public void func_632_b(List var1) {
        this.unloadedEntityList.addAll(var1);
    }

    public void func_656_j() {
        while (this.chunkProvider.func_532_a()) {
        }
    }

    public boolean canBlockBePlacedAt(int var1, int var2, int var3, int var4, boolean var5) {
        int var6 = this.getBlockId(var2, var3, var4);
        Block var7 = Block.blocksList[var6];
        Block var8 = Block.blocksList[var1];
        AxisAlignedBB var9 = var8.getCollisionBoundingBoxFromPool(this, var2, var3, var4);
        if (var5) {
            var9 = null;
        }
        if (var9 != null && !this.checkIfAABBIsClear(var9)) {
            return false;
        }
        if (var7 != Block.waterMoving && var7 != Block.waterStill && var7 != Block.lavaMoving && var7 != Block.lavaStill && var7 != Block.fire && var7 != Block.snow) {
            return var1 > 0 && var7 == null && var8.canPlaceBlockAt(this, var2, var3, var4);
        }
        return true;
    }

    public PathEntity getPathToEntity(Entity var1, Entity var2, float var3) {
        int var4 = MathHelper.floor_double(var1.posX);
        int var5 = MathHelper.floor_double(var1.posY);
        int var6 = MathHelper.floor_double(var1.posZ);
        int var7 = (int)(var3 + 16.0f);
        int var8 = var4 - var7;
        int var9 = var5 - var7;
        int var10 = var6 - var7;
        int var11 = var4 + var7;
        int var12 = var5 + var7;
        int var13 = var6 + var7;
        ChunkCache var14 = new ChunkCache(this, var8, var9, var10, var11, var12, var13);
        return new Pathfinder(var14).createEntityPathTo(var1, var2, var3);
    }

    public PathEntity getEntityPathToXYZ(Entity var1, int var2, int var3, int var4, float var5) {
        int var6 = MathHelper.floor_double(var1.posX);
        int var7 = MathHelper.floor_double(var1.posY);
        int var8 = MathHelper.floor_double(var1.posZ);
        int var9 = (int)(var5 + 8.0f);
        int var10 = var6 - var9;
        int var11 = var7 - var9;
        int var12 = var8 - var9;
        int var13 = var6 + var9;
        int var14 = var7 + var9;
        int var15 = var8 + var9;
        ChunkCache var16 = new ChunkCache(this, var10, var11, var12, var13, var14, var15);
        return new Pathfinder(var16).createEntityPathTo(var1, var2, var3, var4, var5);
    }

    public boolean isBlockProvidingPowerTo(int var1, int var2, int var3, int var4) {
        int var5 = this.getBlockId(var1, var2, var3);
        return var5 == 0 ? false : Block.blocksList[var5].isIndirectlyPoweringTo(this, var1, var2, var3, var4);
    }

    public boolean isBlockGettingPowered(int var1, int var2, int var3) {
        if (this.isBlockProvidingPowerTo(var1, var2 - 1, var3, 0)) {
            return true;
        }
        if (this.isBlockProvidingPowerTo(var1, var2 + 1, var3, 1)) {
            return true;
        }
        if (this.isBlockProvidingPowerTo(var1, var2, var3 - 1, 2)) {
            return true;
        }
        if (this.isBlockProvidingPowerTo(var1, var2, var3 + 1, 3)) {
            return true;
        }
        if (this.isBlockProvidingPowerTo(var1 - 1, var2, var3, 4)) {
            return true;
        }
        return this.isBlockProvidingPowerTo(var1 + 1, var2, var3, 5);
    }

    public boolean isBlockIndirectlyProvidingPowerTo(int var1, int var2, int var3, int var4) {
        if (this.isBlockOpaqueCube(var1, var2, var3)) {
            return this.isBlockGettingPowered(var1, var2, var3);
        }
        int var5 = this.getBlockId(var1, var2, var3);
        return var5 == 0 ? false : Block.blocksList[var5].isPoweringTo(this, var1, var2, var3, var4);
    }

    public boolean isBlockIndirectlyGettingPowered(int var1, int var2, int var3) {
        if (this.isBlockIndirectlyProvidingPowerTo(var1, var2 - 1, var3, 0)) {
            return true;
        }
        if (this.isBlockIndirectlyProvidingPowerTo(var1, var2 + 1, var3, 1)) {
            return true;
        }
        if (this.isBlockIndirectlyProvidingPowerTo(var1, var2, var3 - 1, 2)) {
            return true;
        }
        if (this.isBlockIndirectlyProvidingPowerTo(var1, var2, var3 + 1, 3)) {
            return true;
        }
        if (this.isBlockIndirectlyProvidingPowerTo(var1 - 1, var2, var3, 4)) {
            return true;
        }
        return this.isBlockIndirectlyProvidingPowerTo(var1 + 1, var2, var3, 5);
    }

    public EntityPlayer getClosestPlayerToEntity(Entity var1, double var2) {
        return this.getClosestPlayer(var1.posX, var1.posY, var1.posZ, var2);
    }

    public EntityPlayer getClosestPlayer(double var1, double var3, double var5, double var7) {
        double var9 = -1.0;
        EntityPlayer var11 = null;
        int var12 = 0;
        while (var12 < this.playerEntities.size()) {
            EntityPlayer var13 = (EntityPlayer)this.playerEntities.get(var12);
            double var14 = var13.getDistanceSq(var1, var3, var5);
            if ((var7 < 0.0 || var14 < var7 * var7) && (var9 == -1.0 || var14 < var9)) {
                var9 = var14;
                var11 = var13;
            }
            ++var12;
        }
        return var11;
    }

    public EntityPlayer getPlayerEntityByName(String var1) {
        int var2 = 0;
        while (var2 < this.playerEntities.size()) {
            if (var1.equals(((EntityPlayer)this.playerEntities.get((int)var2)).username)) {
                return (EntityPlayer)this.playerEntities.get(var2);
            }
            ++var2;
        }
        return null;
    }

    public void setChunkData(int var1, int var2, int var3, int var4, int var5, int var6, byte[] var7) {
        int var8 = var1 >> 4;
        int var9 = var3 >> 4;
        int var10 = var1 + var4 - 1 >> 4;
        int var11 = var3 + var6 - 1 >> 4;
        int var12 = 0;
        int var13 = var2;
        int var14 = var2 + var5;
        if (var2 < 0) {
            var13 = 0;
        }
        if (var14 > 128) {
            var14 = 128;
        }
        int var15 = var8;
        while (var15 <= var10) {
            int var16 = var1 - var15 * 16;
            int var17 = var1 + var4 - var15 * 16;
            if (var16 < 0) {
                var16 = 0;
            }
            if (var17 > 16) {
                var17 = 16;
            }
            int var18 = var9;
            while (var18 <= var11) {
                int var19 = var3 - var18 * 16;
                int var20 = var3 + var6 - var18 * 16;
                if (var19 < 0) {
                    var19 = 0;
                }
                if (var20 > 16) {
                    var20 = 16;
                }
                var12 = this.getChunkFromChunkCoords(var15, var18).setChunkData(var7, var16, var13, var19, var17, var14, var20, var12);
                this.markBlocksDirty(var15 * 16 + var16, var13, var18 * 16 + var19, var15 * 16 + var17, var14, var18 * 16 + var20);
                ++var18;
            }
            ++var15;
        }
    }

    public void sendQuittingDisconnectingPacket() {
    }

    public void checkSessionLock() {
        this.saveHandler.func_22150_b();
    }

    public void setWorldTime(long var1) {
        this.worldInfo.setWorldTime(var1);
    }

    public long getRandomSeed() {
        return this.worldInfo.getRandomSeed();
    }

    public long getWorldTime() {
        return this.worldInfo.getWorldTime();
    }

    public ChunkCoordinates getSpawnPoint() {
        return new ChunkCoordinates(this.worldInfo.getSpawnX(), this.worldInfo.getSpawnY(), this.worldInfo.getSpawnZ());
    }

    public void setSpawnPoint(ChunkCoordinates var1) {
        this.worldInfo.setSpawn(var1.x, var1.y, var1.z);
    }

    public void joinEntityInSurroundings(Entity var1) {
        int var2 = MathHelper.floor_double(var1.posX / 16.0);
        int var3 = MathHelper.floor_double(var1.posZ / 16.0);
        int var4 = 2;
        int var5 = var2 - var4;
        while (var5 <= var2 + var4) {
            int var6 = var3 - var4;
            while (var6 <= var3 + var4) {
                this.getChunkFromChunkCoords(var5, var6);
                ++var6;
            }
            ++var5;
        }
        if (!this.loadedEntityList.contains(var1)) {
            this.loadedEntityList.add(var1);
        }
    }

    public boolean func_6466_a(EntityPlayer var1, int var2, int var3, int var4) {
        return true;
    }

    public void func_9425_a(Entity var1, byte var2) {
    }

    public void updateEntityList() {
        int var4;
        int var3;
        Entity var2;
        this.loadedEntityList.removeAll(this.unloadedEntityList);
        int var1 = 0;
        while (var1 < this.unloadedEntityList.size()) {
            var2 = (Entity)this.unloadedEntityList.get(var1);
            var3 = var2.chunkCoordX;
            var4 = var2.chunkCoordZ;
            if (var2.addedToChunk && this.chunkExists(var3, var4)) {
                this.getChunkFromChunkCoords(var3, var4).func_1015_b(var2);
            }
            ++var1;
        }
        var1 = 0;
        while (var1 < this.unloadedEntityList.size()) {
            this.releaseEntitySkin((Entity)this.unloadedEntityList.get(var1));
            ++var1;
        }
        this.unloadedEntityList.clear();
        var1 = 0;
        while (var1 < this.loadedEntityList.size()) {
            block10: {
                block9: {
                    var2 = (Entity)this.loadedEntityList.get(var1);
                    if (var2.ridingEntity == null) break block9;
                    if (!var2.ridingEntity.isDead && var2.ridingEntity.riddenByEntity == var2) break block10;
                    var2.ridingEntity.riddenByEntity = null;
                    var2.ridingEntity = null;
                }
                if (var2.isDead) {
                    var3 = var2.chunkCoordX;
                    var4 = var2.chunkCoordZ;
                    if (var2.addedToChunk && this.chunkExists(var3, var4)) {
                        this.getChunkFromChunkCoords(var3, var4).func_1015_b(var2);
                    }
                    this.loadedEntityList.remove(var1--);
                    this.releaseEntitySkin(var2);
                }
            }
            ++var1;
        }
    }

    public IChunkProvider getIChunkProvider() {
        return this.chunkProvider;
    }

    public void playNoteAt(int var1, int var2, int var3, int var4, int var5) {
        int var6 = this.getBlockId(var1, var2, var3);
        if (var6 > 0) {
            Block.blocksList[var6].playBlock(this, var1, var2, var3, var4, var5);
        }
    }

    public WorldInfo func_22144_v() {
        return this.worldInfo;
    }

    public void updateAllPlayersSleepingFlag() {
        this.allPlayersSleeping = !this.playerEntities.isEmpty();
        for (EntityPlayer var2 : this.playerEntities) {
            if (var2.isPlayerSleeping()) continue;
            this.allPlayersSleeping = false;
            break;
        }
    }

    protected void wakeUpAllPlayers() {
        this.allPlayersSleeping = false;
        for (EntityPlayer var2 : this.playerEntities) {
            if (!var2.isPlayerSleeping()) continue;
            var2.wakeUpPlayer(false, false, true);
        }
    }

    public boolean isAllPlayersFullyAsleep() {
        if (this.allPlayersSleeping && !this.multiplayerWorld) {
            EntityPlayer var2;
            Iterator var1 = this.playerEntities.iterator();
            do {
                if (var1.hasNext()) continue;
                return true;
            } while ((var2 = (EntityPlayer)var1.next()).isPlayerFullyAsleep());
            return false;
        }
        return false;
    }
}

