/*
 * Decompiled with CFR 0.152.
 */
package lunatrius.schematica;

import java.util.ArrayList;
import java.util.List;
import lunatrius.schematica.Settings;
import net.minecraft.src.Block;
import net.minecraft.src.IChunkProvider;
import net.minecraft.src.Material;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.NBTTagList;
import net.minecraft.src.TileEntity;
import net.minecraft.src.TileEntityChest;
import net.minecraft.src.World;
import net.skidcode.gh.maybeaclient.Client;

public class SchematicWorld
extends World {
    public final Settings settings = Settings.instance();
    public int[][][] blocks = null;
    public int[][][] metadata = null;
    public List<TileEntity> tileEntities = null;
    public short width = 0;
    public short length = 0;
    public short height = 0;

    public SchematicWorld() {
        super(Client.mc.theWorld, Client.mc.theWorld.worldProvider);
    }

    public SchematicWorld(int[][][] blocks, int[][][] metadata, List<TileEntity> tileEntities, short width, short height, short length) {
        this();
        this.blocks = blocks;
        this.metadata = metadata;
        this.tileEntities = tileEntities;
        this.width = width;
        this.length = length;
        this.height = height;
    }

    public void readFromNBT(NBTTagCompound tagCompound) {
        byte[] localBlocks = tagCompound.getByteArray("Blocks");
        byte[] localMetadata = tagCompound.getByteArray("Data");
        boolean extra = false;
        byte[] extraBlocks = null;
        extra = tagCompound.hasKey("Add");
        if (extra) {
            extraBlocks = tagCompound.getByteArray("Add");
        }
        this.width = tagCompound.getShort("Width");
        this.length = tagCompound.getShort("Length");
        this.height = tagCompound.getShort("Height");
        this.blocks = new int[this.width][this.height][this.length];
        this.metadata = new int[this.width][this.height][this.length];
        int x = 0;
        while (x < this.width) {
            int y = 0;
            while (y < this.height) {
                int z = 0;
                while (z < this.length) {
                    this.blocks[x][y][z] = localBlocks[x + (y * this.length + z) * this.width] & 0xFF;
                    this.metadata[x][y][z] = localMetadata[x + (y * this.length + z) * this.width] & 0xFF;
                    if (extra) {
                        int[] nArray = this.blocks[x][y];
                        int n = z;
                        nArray[n] = nArray[n] | (extraBlocks[x + (y * this.length + z) * this.width] & 0xFF) << 8;
                    }
                    ++z;
                }
                ++y;
            }
            ++x;
        }
        this.tileEntities = new ArrayList<TileEntity>();
        NBTTagList tileEntitiesList = tagCompound.getTagList("TileEntities");
        int i = 0;
        while (i < tileEntitiesList.tagCount()) {
            TileEntity tileEntity = TileEntity.createAndLoadEntity((NBTTagCompound)tileEntitiesList.tagAt(i));
            if (tileEntity != null) {
                tileEntity.worldObj = this;
                this.tileEntities.add(tileEntity);
            }
            ++i;
        }
        this.refreshChests();
    }

    public void writeToNBT(NBTTagCompound tagCompound) {
        tagCompound.setShort("Width", this.width);
        tagCompound.setShort("Length", this.length);
        tagCompound.setShort("Height", this.height);
        byte[] localBlocks = new byte[this.width * this.length * this.height];
        byte[] localMetadata = new byte[this.width * this.length * this.height];
        byte[] extraBlocks = new byte[this.width * this.length * this.height];
        boolean extra = false;
        int x = 0;
        while (x < this.width) {
            int y = 0;
            while (y < this.height) {
                int z = 0;
                while (z < this.length) {
                    localBlocks[x + (y * this.length + z) * this.width] = (byte)this.blocks[x][y][z];
                    localMetadata[x + (y * this.length + z) * this.width] = (byte)this.metadata[x][y][z];
                    extraBlocks[x + (y * this.length + z) * this.width] = (byte)(this.blocks[x][y][z] >> 8);
                    if (extraBlocks[x + (y * this.length + z) * this.width] > 0) {
                        extra = true;
                    }
                    ++z;
                }
                ++y;
            }
            ++x;
        }
        tagCompound.setString("Materials", "Classic");
        tagCompound.setByteArray("Blocks", localBlocks);
        tagCompound.setByteArray("Data", localMetadata);
        if (extra) {
            tagCompound.setByteArray("Add", extraBlocks);
        }
        tagCompound.setTag("Entities", new NBTTagList());
        NBTTagList tileEntitiesList = new NBTTagList();
        for (TileEntity tileEntity : this.tileEntities) {
            NBTTagCompound tileEntityTagCompound = new NBTTagCompound();
            tileEntity.writeToNBT(tileEntityTagCompound);
            tileEntitiesList.setTag(tileEntityTagCompound);
        }
        tagCompound.setTag("TileEntities", tileEntitiesList);
    }

    @Override
    public int getBlockId(int x, int y, int z) {
        if (x < 0 || y < 0 || z < 0 || x >= this.width || y >= this.height || z >= this.length) {
            return 0;
        }
        return this.blocks[x][y][z] & 0xFFF;
    }

    @Override
    public TileEntity getBlockTileEntity(int x, int y, int z) {
        int i = 0;
        while (i < this.tileEntities.size()) {
            if (this.tileEntities.get((int)i).xCoord == x && this.tileEntities.get((int)i).yCoord == y && this.tileEntities.get((int)i).zCoord == z) {
                return this.tileEntities.get(i);
            }
            ++i;
        }
        return null;
    }

    @Override
    public float getLightBrightness(int x, int y, int z) {
        return 1.0f;
    }

    @Override
    public int getBlockMetadata(int x, int y, int z) {
        if (x < 0 || y < 0 || z < 0 || x >= this.width || y >= this.height || z >= this.length) {
            return 0;
        }
        return this.metadata[x][y][z];
    }

    @Override
    public Material getBlockMaterial(int x, int y, int z) {
        return this.getBlock(x, y, z) != null ? this.getBlock((int)x, (int)y, (int)z).blockMaterial : Material.air;
    }

    @Override
    public boolean isBlockOpaqueCube(int x, int y, int z) {
        if (this.settings.renderingLayer != -1 && this.settings.renderingLayer != y) {
            return false;
        }
        return this.getBlock(x, y, z) != null && this.getBlock(x, y, z).isOpaqueCube();
    }

    @Override
    public boolean isAirBlock(int x, int y, int z) {
        if (x < 0 || y < 0 || z < 0 || x >= this.width || y >= this.height || z >= this.length) {
            return true;
        }
        return this.blocks[x][y][z] == 0;
    }

    @Override
    protected IChunkProvider getChunkProvider() {
        return null;
    }

    public void setBlockMetadata(int x, int y, int z, byte metadata) {
        this.metadata[x][y][z] = metadata;
    }

    public Block getBlock(int x, int y, int z) {
        return Block.blocksList[this.getBlockId(x, y, z)];
    }

    public void setTileEntities(List<TileEntity> tileEntities) {
        this.tileEntities = tileEntities;
    }

    public List<TileEntity> getTileEntities() {
        return this.tileEntities;
    }

    public void refreshChests() {
    }

    public void checkForAdjacentChests(TileEntityChest tileEntityChest) {
    }

    public void flip() {
        int x = 0;
        while (x < this.width) {
            int y = 0;
            while (y < this.height) {
                int z = 0;
                while (z < (this.length + 1) / 2) {
                    int tmp = this.blocks[x][y][z];
                    this.blocks[x][y][z] = this.blocks[x][y][this.length - 1 - z];
                    this.blocks[x][y][this.length - 1 - z] = tmp;
                    if (z == this.length - 1 - z) {
                        this.metadata[x][y][z] = this.flipMetadataZ(this.metadata[x][y][z], this.blocks[x][y][z]);
                    } else {
                        tmp = this.metadata[x][y][z];
                        this.metadata[x][y][z] = this.flipMetadataZ(this.metadata[x][y][this.length - 1 - z], this.blocks[x][y][z]);
                        this.metadata[x][y][this.length - 1 - z] = this.flipMetadataZ(tmp, this.blocks[x][y][this.length - 1 - z]);
                    }
                    ++z;
                }
                ++y;
            }
            ++x;
        }
        this.refreshChests();
    }

    public int flipMetadataZ(int blockMetadata, int blockId) {
        if (blockId == Block.torchWood.blockID || blockId == Block.torchRedstoneActive.blockID || blockId == Block.torchRedstoneIdle.blockID) {
            switch (blockMetadata) {
                case 3: {
                    return 4;
                }
                case 4: {
                    return 3;
                }
            }
        } else if (blockId == Block.minecartTrack.blockID) {
            switch (blockMetadata) {
                case 4: {
                    return 5;
                }
                case 5: {
                    return 4;
                }
                case 6: {
                    return 9;
                }
                case 7: {
                    return 8;
                }
                case 8: {
                    return 7;
                }
                case 9: {
                    return 6;
                }
            }
        } else if (blockId == Block.stairCompactCobblestone.blockID || blockId == Block.stairCompactPlanks.blockID) {
            switch (blockMetadata & 3) {
                case 2: {
                    return (byte)(3 | blockMetadata & 4);
                }
                case 3: {
                    return (byte)(2 | blockMetadata & 4);
                }
            }
        } else if (blockId == Block.lever.blockID) {
            switch (blockMetadata & 7) {
                case 3: {
                    return (byte)(4 | blockMetadata & 8);
                }
                case 4: {
                    return (byte)(3 | blockMetadata & 8);
                }
            }
        } else if (blockId == Block.doorWood.blockID || blockId == Block.doorSteel.blockID) {
            if ((blockMetadata & 8) == 8) {
                return (byte)(blockMetadata ^ 1);
            }
            switch (blockMetadata & 3) {
                case 1: {
                    return (byte)(3 | blockMetadata & 0xC);
                }
                case 3: {
                    return (byte)(1 | blockMetadata & 0xC);
                }
            }
        } else if (blockId == Block.signPost.blockID) {
            switch (blockMetadata) {
                case 0: {
                    return 8;
                }
                case 1: {
                    return 7;
                }
                case 2: {
                    return 6;
                }
                case 3: {
                    return 5;
                }
                case 4: {
                    return 4;
                }
                case 5: {
                    return 3;
                }
                case 6: {
                    return 2;
                }
                case 7: {
                    return 1;
                }
                case 8: {
                    return 0;
                }
                case 9: {
                    return 15;
                }
                case 10: {
                    return 14;
                }
                case 11: {
                    return 13;
                }
                case 12: {
                    return 12;
                }
                case 13: {
                    return 11;
                }
                case 14: {
                    return 10;
                }
                case 15: {
                    return 9;
                }
            }
        } else if (blockId == Block.ladder.blockID || blockId == Block.signWall.blockID || blockId == Block.stoneOvenActive.blockID || blockId == Block.stoneOvenIdle.blockID || blockId == Block.dispenser.blockID || blockId == Block.crate.blockID) {
            switch (blockMetadata) {
                case 2: {
                    return 3;
                }
                case 3: {
                    return 2;
                }
            }
        } else if (blockId == Block.pumpkin.blockID || blockId == Block.pumpkinLantern.blockID) {
            switch (blockMetadata) {
                case 0: {
                    return 2;
                }
                case 2: {
                    return 0;
                }
            }
        } else if (blockId == Block.blockBed.blockID) {
            switch (blockMetadata & 3) {
                case 0: {
                    return (byte)(2 | blockMetadata & 0xC);
                }
                case 2: {
                    return (byte)(blockMetadata & 0xC);
                }
            }
        } else if (blockId == Block.redstoneRepeaterActive.blockID || blockId == Block.redstoneRepeaterIdle.blockID) {
            switch (blockMetadata & 3) {
                case 0: {
                    return (byte)(2 | blockMetadata & 0xC);
                }
                case 2: {
                    return (byte)(blockMetadata & 0xC);
                }
            }
        }
        return blockMetadata;
    }

    public void rotate() {
        int[][][] localBlocks = new int[this.length][this.height][this.width];
        int[][][] localMetadata = new int[this.length][this.height][this.width];
        int x = 0;
        while (x < this.width) {
            int y = 0;
            while (y < this.height) {
                int z = 0;
                while (z < this.length) {
                    localBlocks[z][y][x] = this.blocks[this.width - 1 - x][y][z];
                    localMetadata[z][y][x] = this.rotateMetadata(this.metadata[this.width - 1 - x][y][z], this.blocks[this.width - 1 - x][y][z]);
                    ++z;
                }
                ++y;
            }
            ++x;
        }
        this.blocks = localBlocks;
        this.metadata = localMetadata;
        this.refreshChests();
        short tmp = this.width;
        this.width = this.length;
        this.length = tmp;
    }

    public int rotateMetadata(int blockMetadata, int blockId) {
        if (blockId == Block.torchWood.blockID || blockId == Block.torchRedstoneActive.blockID || blockId == Block.torchRedstoneIdle.blockID) {
            switch (blockMetadata) {
                case 1: {
                    return 4;
                }
                case 2: {
                    return 3;
                }
                case 3: {
                    return 1;
                }
                case 4: {
                    return 2;
                }
            }
        } else if (blockId == Block.minecartTrack.blockID) {
            switch (blockMetadata) {
                case 0: {
                    return 1;
                }
                case 1: {
                    return 0;
                }
                case 2: {
                    return 4;
                }
                case 3: {
                    return 5;
                }
                case 4: {
                    return 3;
                }
                case 5: {
                    return 2;
                }
                case 6: {
                    return 9;
                }
                case 7: {
                    return 6;
                }
                case 8: {
                    return 7;
                }
                case 9: {
                    return 8;
                }
            }
        } else if (blockId == Block.stairCompactCobblestone.blockID || blockId == Block.stairCompactPlanks.blockID) {
            switch (blockMetadata & 3) {
                case 0: {
                    return (byte)(3 | blockMetadata & 4);
                }
                case 1: {
                    return (byte)(2 | blockMetadata & 4);
                }
                case 2: {
                    return (byte)(blockMetadata & 4);
                }
                case 3: {
                    return (byte)(1 | blockMetadata & 4);
                }
            }
        } else if (blockId == Block.lever.blockID) {
            switch (blockMetadata & 7) {
                case 1: {
                    return (byte)(4 | blockMetadata & 8);
                }
                case 2: {
                    return (byte)(3 | blockMetadata & 8);
                }
                case 3: {
                    return (byte)(1 | blockMetadata & 8);
                }
                case 4: {
                    return (byte)(2 | blockMetadata & 8);
                }
                case 5: {
                    return (byte)(6 | blockMetadata & 8);
                }
                case 6: {
                    return (byte)(5 | blockMetadata & 8);
                }
            }
        } else if (blockId == Block.doorWood.blockID || blockId == Block.doorSteel.blockID) {
            if ((blockMetadata & 8) == 8) {
                return blockMetadata;
            }
            switch (blockMetadata & 3) {
                case 0: {
                    return (byte)(3 | blockMetadata & 0xC);
                }
                case 1: {
                    return (byte)(blockMetadata & 0xC);
                }
                case 2: {
                    return (byte)(1 | blockMetadata & 0xC);
                }
                case 3: {
                    return (byte)(2 | blockMetadata & 0xC);
                }
            }
        } else {
            if (blockId == Block.signPost.blockID) {
                return (byte)((blockMetadata + 12) % 16);
            }
            if (blockId == Block.ladder.blockID || blockId == Block.signWall.blockID || blockId == Block.stoneOvenActive.blockID || blockId == Block.stoneOvenIdle.blockID || blockId == Block.dispenser.blockID || blockId == Block.crate.blockID) {
                switch (blockMetadata) {
                    case 2: {
                        return 4;
                    }
                    case 3: {
                        return 5;
                    }
                    case 4: {
                        return 3;
                    }
                    case 5: {
                        return 2;
                    }
                }
            } else if (blockId == Block.pumpkin.blockID || blockId == Block.pumpkinLantern.blockID) {
                switch (blockMetadata) {
                    case 0: {
                        return 3;
                    }
                    case 1: {
                        return 0;
                    }
                    case 2: {
                        return 1;
                    }
                    case 3: {
                        return 2;
                    }
                }
            } else if (blockId == Block.blockBed.blockID) {
                switch (blockMetadata & 3) {
                    case 0: {
                        return (byte)(3 | blockMetadata & 0xC);
                    }
                    case 1: {
                        return (byte)(blockMetadata & 0xC);
                    }
                    case 2: {
                        return (byte)(1 | blockMetadata & 0xC);
                    }
                    case 3: {
                        return (byte)(2 | blockMetadata & 0xC);
                    }
                }
            } else if (blockId == Block.redstoneRepeaterActive.blockID || blockId == Block.redstoneRepeaterIdle.blockID) {
                switch (blockMetadata & 3) {
                    case 0: {
                        return (byte)(3 | blockMetadata & 0xC);
                    }
                    case 1: {
                        return (byte)(blockMetadata & 0xC);
                    }
                    case 2: {
                        return (byte)(1 | blockMetadata & 0xC);
                    }
                    case 3: {
                        return (byte)(2 | blockMetadata & 0xC);
                    }
                }
            }
        }
        return blockMetadata;
    }

    public int width() {
        return this.width;
    }

    public int length() {
        return this.length;
    }

    public int height() {
        return this.height;
    }
}

