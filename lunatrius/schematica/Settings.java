/*
 * Decompiled with CFR 0.152.
 */
package lunatrius.schematica;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import lunatrius.schematica.FileFilterSchematic;
import lunatrius.schematica.RenderTileEntity;
import lunatrius.schematica.SchematicWorld;
import lunatrius.schematica.util.Vector3f;
import lunatrius.schematica.util.Vector3i;
import net.minecraft.client.Minecraft;
import net.minecraft.src.ChunkCache;
import net.minecraft.src.CompressedStreamTools;
import net.minecraft.src.KeyBinding;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.RenderBlocks;
import net.minecraft.src.RenderManager;
import net.minecraft.src.StringTranslate;
import net.minecraft.src.TileEntity;
import net.skidcode.gh.maybeaclient.Client;

public class Settings {
    private static final Settings instance = new Settings();
    private final StringTranslate strTranslate = StringTranslate.getInstance();
    public float blockDelta = 0.005f;
    public KeyBinding[] keyBindings = new KeyBinding[]{new KeyBinding("key.schematic.load", 181), new KeyBinding("key.schematic.save", 55), new KeyBinding("key.schematic.control", 74)};
    public static final File schematicDirectory = new File(Minecraft.getMinecraftDir(), "/schematics/");
    public static final File textureDirectory = new File(Minecraft.getMinecraftDir(), "/resources/mod/schematica/");
    public Minecraft minecraft = Client.mc;
    public ChunkCache mcWorldCache = null;
    public SchematicWorld schematic = null;
    public Vector3f playerPosition = new Vector3f();
    public RenderBlocks renderBlocks = null;
    public RenderTileEntity renderTileEntity = null;
    public int selectedSchematic = 0;
    public Vector3i pointA = new Vector3i();
    public Vector3i pointB = new Vector3i();
    public Vector3i pointMin = new Vector3i();
    public Vector3i pointMax = new Vector3i();
    public int rotationRender = 0;
    public Vector3i offset = new Vector3i();
    public boolean needsUpdate = true;
    public boolean isRenderingSchematic = false;
    public int renderingLayer = -1;
    public boolean isRenderingGuide = false;
    public int[] increments = new int[]{1, 5, 15, 50, 250};

    private Settings() {
    }

    public static Settings instance() {
        return instance;
    }

    public List<String> getSchematicFiles() {
        ArrayList<String> schematicFiles = new ArrayList<String>();
        schematicFiles.add("-- No schematic --");
        File[] files = schematicDirectory.listFiles(new FileFilterSchematic());
        int i = 0;
        while (i < files.length) {
            schematicFiles.add(files[i].getName());
            ++i;
        }
        return schematicFiles;
    }

    public boolean loadSchematic(String filename) {
        try {
            FileInputStream stream = new FileInputStream(filename);
            NBTTagCompound tagCompound = CompressedStreamTools.func_1138_a(stream);
            if (tagCompound != null) {
                this.schematic = new SchematicWorld();
                this.schematic.readFromNBT(tagCompound);
                this.renderBlocks = new RenderBlocks(this.schematic);
                this.renderTileEntity = new RenderTileEntity(this.schematic);
                this.isRenderingSchematic = true;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            this.schematic = null;
            this.renderBlocks = null;
            this.renderTileEntity = null;
            this.isRenderingSchematic = false;
            return false;
        }
        return true;
    }

    public boolean saveSchematic(String filename, Vector3i from, Vector3i to) {
        try {
            NBTTagCompound tagCompound = new NBTTagCompound();
            int minX = Math.min(from.x, to.x);
            int maxX = Math.max(from.x, to.x);
            int minY = Math.min(from.y, to.y);
            int maxY = Math.max(from.y, to.y);
            int minZ = Math.min(from.z, to.z);
            int maxZ = Math.max(from.z, to.z);
            short width = (short)(Math.abs(maxX - minX) + 1);
            short height = (short)(Math.abs(maxY - minY) + 1);
            short length = (short)(Math.abs(maxZ - minZ) + 1);
            int[][][] blocks = new int[width][height][length];
            int[][][] metadata = new int[width][height][length];
            ArrayList<TileEntity> tileEntities = new ArrayList<TileEntity>();
            int x = minX;
            while (x <= maxX) {
                int y = minY;
                while (y <= maxY) {
                    int z = minZ;
                    while (z <= maxZ) {
                        blocks[x - minX][y - minY][z - minZ] = this.minecraft.theWorld.getBlockId(x, y, z);
                        metadata[x - minX][y - minY][z - minZ] = this.minecraft.theWorld.getBlockMetadata(x, y, z);
                        if (this.minecraft.theWorld.getBlockTileEntity(x, y, z) != null) {
                            NBTTagCompound te = new NBTTagCompound();
                            this.minecraft.theWorld.getBlockTileEntity(x, y, z).writeToNBT(te);
                            TileEntity tileEntity = TileEntity.createAndLoadEntity(te);
                            tileEntity.xCoord -= minX;
                            tileEntity.yCoord -= minY;
                            tileEntity.zCoord -= minZ;
                            tileEntities.add(tileEntity);
                        }
                        ++z;
                    }
                    ++y;
                }
                ++x;
            }
            SchematicWorld schematicOut = new SchematicWorld(blocks, metadata, tileEntities, width, height, length);
            schematicOut.writeToNBT(tagCompound);
            FileOutputStream stream = new FileOutputStream(filename);
            CompressedStreamTools.writeGzippedCompoundToOutputStream(tagCompound, stream);
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        System.out.println("gother4");
        return true;
    }

    public float getTranslationX() {
        return (float)(RenderManager.renderPosX - (double)this.offset.x);
    }

    public float getTranslationY() {
        return (float)(RenderManager.renderPosY - (double)this.offset.y);
    }

    public float getTranslationZ() {
        return (float)(RenderManager.renderPosZ - (double)this.offset.z);
    }

    public void updatePoints() {
        this.pointMin.x = Math.min(this.pointA.x, this.pointB.x);
        this.pointMin.y = Math.min(this.pointA.y, this.pointB.y);
        this.pointMin.z = Math.min(this.pointA.z, this.pointB.z);
        this.pointMax.x = Math.max(this.pointA.x, this.pointB.x);
        this.pointMax.y = Math.max(this.pointA.y, this.pointB.y);
        this.pointMax.z = Math.max(this.pointA.z, this.pointB.z);
        this.needsUpdate = true;
    }

    public void moveHere(Vector3i point) {
        point.x = (int)Math.floor(this.playerPosition.x);
        point.y = (int)Math.floor(this.playerPosition.y - 1.0f);
        point.z = (int)Math.floor(this.playerPosition.z);
        switch (this.rotationRender) {
            case 0: {
                --point.x;
                ++point.z;
                break;
            }
            case 1: {
                --point.x;
                --point.z;
                break;
            }
            case 2: {
                ++point.x;
                --point.z;
                break;
            }
            case 3: {
                ++point.x;
                ++point.z;
            }
        }
    }

    public void moveHere() {
        this.offset.x = (int)Math.floor(this.playerPosition.x);
        this.offset.y = (int)Math.floor(this.playerPosition.y) - 1;
        this.offset.z = (int)Math.floor(this.playerPosition.z);
        if (this.schematic != null) {
            switch (this.rotationRender) {
                case 0: {
                    this.offset.x -= this.schematic.width();
                    ++this.offset.z;
                    break;
                }
                case 1: {
                    this.offset.x -= this.schematic.width();
                    this.offset.z -= this.schematic.length();
                    break;
                }
                case 2: {
                    ++this.offset.x;
                    this.offset.z -= this.schematic.length();
                    break;
                }
                case 3: {
                    ++this.offset.x;
                    ++this.offset.z;
                }
            }
            this.reloadChunkCache();
        }
    }

    public void toggleRendering() {
        this.isRenderingSchematic = !this.isRenderingSchematic && this.schematic != null;
    }

    public void reloadChunkCache() {
        this.mcWorldCache = new ChunkCache(this.minecraft.theWorld, this.offset.x - 1, this.offset.y - 1, this.offset.z - 1, this.offset.x + this.schematic.width() + 1, this.offset.y + this.schematic.height() + 1, this.offset.z + this.schematic.length() + 1);
        this.needsUpdate = true;
    }

    public void flipWorld() {
        if (this.schematic != null) {
            this.schematic.flip();
            this.needsUpdate = true;
        }
    }

    public void rotateWorld() {
        if (this.schematic != null) {
            this.schematic.rotate();
            this.needsUpdate = true;
        }
    }
}

