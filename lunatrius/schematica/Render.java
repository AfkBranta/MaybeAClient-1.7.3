/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.BufferUtils
 *  org.lwjgl.opengl.GL11
 */
package lunatrius.schematica;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.imageio.ImageIO;
import lunatrius.schematica.RenderTileEntity;
import lunatrius.schematica.SchematicWorld;
import lunatrius.schematica.Settings;
import lunatrius.schematica.util.Vector3f;
import lunatrius.schematica.util.Vector3i;
import lunatrius.schematica.util.Vector4i;
import net.minecraft.src.Block;
import net.minecraft.src.EntityPlayerSP;
import net.minecraft.src.EntityRenderer;
import net.minecraft.src.GLAllocation;
import net.minecraft.src.RenderBlocks;
import net.minecraft.src.Tessellator;
import net.minecraft.src.TexturePackBase;
import net.minecraft.src.TileEntity;
import net.minecraft.src.TileEntityRenderer;
import net.minecraft.src.TileEntitySign;
import net.minecraft.src.TileEntitySpecialRenderer;
import net.minecraft.src.World;
import net.skidcode.gh.maybeaclient.hacks.SchematicaHack;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

public class Render {
    public final Settings settings = Settings.instance();
    public final List<String> textures = new ArrayList<String>();
    public final BufferedImage missingTextureImage = new BufferedImage(64, 64, 2);
    public boolean hasBlockList = false;
    private int glBlockList;
    public int bufferSizeQuad = 0x100000;
    public FloatBuffer cBufferQuad = BufferUtils.createFloatBuffer((int)(this.bufferSizeQuad * 4));
    public FloatBuffer vBufferQuad = BufferUtils.createFloatBuffer((int)(this.bufferSizeQuad * 3));
    public int objectCountQuad = -1;
    public boolean needsExpansionQuad = false;
    public int bufferSizeLine = 0x100000;
    public FloatBuffer cBufferLine = BufferUtils.createFloatBuffer((int)(this.bufferSizeLine * 4));
    public FloatBuffer vBufferLine = BufferUtils.createFloatBuffer((int)(this.bufferSizeLine * 3));
    public int objectCountLine = -1;
    public boolean needsExpansionLine = false;
    public SchematicaHack schematica;

    public Render(SchematicaHack insatnce) {
        this.schematica = insatnce;
        this.initTexture();
        this.initReflection();
    }

    public void initTexture() {
        Graphics graphics = this.missingTextureImage.getGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, 64, 64);
        graphics.setColor(Color.BLACK);
        graphics.drawString("missingtex", 1, 10);
        graphics.dispose();
    }

    public void initReflection() {
    }

    public void onRender(EntityRenderer event, float f) {
        EntityPlayerSP player;
        if (this.settings.minecraft != null && (player = this.settings.minecraft.thePlayer) != null) {
            this.settings.playerPosition.x = (float)(player.lastTickPosX + (player.posX - player.lastTickPosX) * (double)f);
            this.settings.playerPosition.y = (float)(player.lastTickPosY + (player.posY - player.lastTickPosY) * (double)f);
            this.settings.playerPosition.z = (float)(player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * (double)f);
            this.settings.rotationRender = (int)((player.rotationYaw / 90.0f % 4.0f + 4.0f) % 4.0f);
            this.render();
        }
    }

    public void render() {
        GL11.glPushMatrix();
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)3042);
        GL11.glEnable((int)2929);
        GL11.glDepthMask((boolean)true);
        if (this.needsExpansionQuad) {
            this.bufferSizeQuad *= 2;
            this.cBufferQuad = BufferUtils.createFloatBuffer((int)(this.bufferSizeQuad * 4));
            this.vBufferQuad = BufferUtils.createFloatBuffer((int)(this.bufferSizeQuad * 3));
            this.needsExpansionQuad = false;
        }
        if (this.needsExpansionLine) {
            this.bufferSizeLine *= 2;
            this.cBufferLine = BufferUtils.createFloatBuffer((int)(this.bufferSizeLine * 4));
            this.vBufferLine = BufferUtils.createFloatBuffer((int)(this.bufferSizeLine * 3));
            this.needsExpansionLine = false;
        }
        int minX = 0;
        int maxX = 0;
        int minY = 0;
        int maxY = 0;
        int minZ = 0;
        int maxZ = 0;
        if (this.settings.schematic != null) {
            maxX = this.settings.schematic.width();
            maxY = this.settings.schematic.height();
            maxZ = this.settings.schematic.length();
            if (this.settings.renderingLayer >= 0) {
                minY = this.settings.renderingLayer;
                maxY = this.settings.renderingLayer + 1;
            }
        }
        if (this.settings.needsUpdate) {
            this.objectCountQuad = 0;
            this.objectCountLine = 0;
            this.cBufferQuad.clear();
            this.vBufferQuad.clear();
            this.cBufferLine.clear();
            this.vBufferLine.clear();
            if (!this.hasBlockList) {
                this.hasBlockList = true;
                this.glBlockList = GL11.glGenLists((int)1);
            }
            GL11.glNewList((int)this.glBlockList, (int)4864);
            if (this.settings.isRenderingSchematic && this.settings.schematic != null) {
                this.renderBlocks(minX, minY, minZ, maxX, maxY, maxZ);
            }
            if (this.settings.isRenderingGuide) {
                this.renderGuide();
            }
            GL11.glEndList();
            this.settings.needsUpdate = false;
        }
        GL11.glTranslatef((float)(-this.settings.getTranslationX()), (float)(-this.settings.getTranslationY()), (float)(-this.settings.getTranslationZ()));
        if (!this.hasBlockList) {
            this.hasBlockList = true;
            this.glBlockList = GL11.glGenLists((int)1);
        }
        GL11.glCallList((int)this.glBlockList);
        if (this.settings.isRenderingSchematic && this.settings.schematic != null) {
            this.renderTileEntities(minX, minY, minZ, maxX, maxY, maxZ);
        }
        if (this.objectCountQuad > 0 || this.objectCountLine > 0) {
            this.cBufferQuad.flip();
            this.vBufferQuad.flip();
            this.cBufferLine.flip();
            this.vBufferLine.flip();
            GL11.glDisable((int)3553);
            GL11.glLineWidth((float)1.5f);
            GL11.glEnableClientState((int)32884);
            GL11.glEnableClientState((int)32886);
            if (this.objectCountQuad > 0) {
                GL11.glColorPointer((int)4, (int)0, (FloatBuffer)this.cBufferQuad);
                GL11.glVertexPointer((int)3, (int)0, (FloatBuffer)this.vBufferQuad);
                GL11.glDrawArrays((int)7, (int)0, (int)this.objectCountQuad);
            }
            if (this.objectCountLine > 0) {
                GL11.glColorPointer((int)4, (int)0, (FloatBuffer)this.cBufferLine);
                GL11.glVertexPointer((int)3, (int)0, (FloatBuffer)this.vBufferLine);
                GL11.glDrawArrays((int)1, (int)0, (int)this.objectCountLine);
            }
            GL11.glDisableClientState((int)32886);
            GL11.glDisableClientState((int)32884);
            GL11.glEnable((int)3553);
        }
        GL11.glTranslatef((float)this.settings.getTranslationX(), (float)this.settings.getTranslationY(), (float)this.settings.getTranslationZ());
        GL11.glDisable((int)3042);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glPopMatrix();
    }

    public void renderBlocks(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
        Vector3i tmp;
        World mcWorld = this.settings.minecraft.theWorld;
        SchematicWorld world = this.settings.schematic;
        RenderBlocks renderBlocks = this.settings.renderBlocks;
        ArrayList<Vector4i> invalidBlockId = new ArrayList<Vector4i>();
        ArrayList<Vector4i> invalidBlockMetadata = new ArrayList<Vector4i>();
        ArrayList<Vector4i> todoBlocks = new ArrayList<Vector4i>();
        int blockId = 0;
        int mcBlockId = 0;
        int sides = 0;
        Block block = null;
        String lastTexture = "";
        boolean ambientOcclusion = this.settings.minecraft.gameSettings.ambientOcclusion;
        this.settings.minecraft.gameSettings.ambientOcclusion = false;
        Tessellator.instance.schematicaRendering = true;
        Tessellator.instance.startDrawingQuads();
        int x = minX;
        while (x < maxX) {
            int y = minY;
            while (y < maxY) {
                int z = minZ;
                while (z < maxZ) {
                    try {
                        blockId = world.getBlockId(x, y, z);
                        block = Block.blocksList[blockId];
                        mcBlockId = mcWorld.getBlockId(x + this.settings.offset.x, y + this.settings.offset.y, z + this.settings.offset.z);
                        sides = 0;
                        if (block != null) {
                            if (block.shouldSideBeRendered(world, x, y - 1, z, 0)) {
                                sides |= 1;
                            }
                            if (block.shouldSideBeRendered(world, x, y + 1, z, 1)) {
                                sides |= 2;
                            }
                            if (block.shouldSideBeRendered(world, x, y, z - 1, 2)) {
                                sides |= 4;
                            }
                            if (block.shouldSideBeRendered(world, x, y, z + 1, 3)) {
                                sides |= 8;
                            }
                            if (block.shouldSideBeRendered(world, x - 1, y, z, 4)) {
                                sides |= 0x10;
                            }
                            if (block.shouldSideBeRendered(world, x + 1, y, z, 5)) {
                                sides |= 0x20;
                            }
                        }
                        if (mcBlockId != 0) {
                            if (this.schematica.highlight.value) {
                                if (blockId != mcBlockId) {
                                    invalidBlockId.add(new Vector4i(x, y, z, sides));
                                } else if (world.getBlockMetadata(x, y, z) != mcWorld.getBlockMetadata(x + this.settings.offset.x, y + this.settings.offset.y, z + this.settings.offset.z)) {
                                    invalidBlockMetadata.add(new Vector4i(x, y, z, sides));
                                }
                            }
                        } else if (mcBlockId == 0 && blockId > 0 && blockId < 4096) {
                            if (this.schematica.highlight.value) {
                                todoBlocks.add(new Vector4i(x, y, z, sides));
                            }
                            if (block != null) {
                                renderBlocks.renderBlockByRenderType(block, x, y, z);
                            }
                        }
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                    ++z;
                }
                ++y;
            }
            ++x;
        }
        Tessellator.instance.draw();
        Tessellator.instance.schematicaRendering = false;
        this.settings.minecraft.gameSettings.ambientOcclusion = ambientOcclusion;
        this.drawCuboidLine(Vector3i.ZERO, new Vector3i(world.width(), world.height(), world.length()), 63, 0.75f, 0.0f, 0.75f, 0.25f);
        for (Vector4i invalidBlock : invalidBlockId) {
            tmp = new Vector3i(invalidBlock.x, invalidBlock.y, invalidBlock.z);
            this.drawCuboidQuad(tmp, tmp.clone().add(1), invalidBlock.w, 1.0f, 0.0f, 0.0f, 0.25f);
            this.drawCuboidLine(tmp, tmp.clone().add(1), invalidBlock.w, 1.0f, 0.0f, 0.0f, 0.25f);
        }
        for (Vector4i invalidBlock : invalidBlockMetadata) {
            tmp = new Vector3i(invalidBlock.x, invalidBlock.y, invalidBlock.z);
            this.drawCuboidQuad(tmp, tmp.clone().add(1), invalidBlock.w, 0.75f, 0.35f, 0.0f, 0.45f);
            this.drawCuboidLine(tmp, tmp.clone().add(1), invalidBlock.w, 0.75f, 0.35f, 0.0f, 0.45f);
        }
        for (Vector4i todoBlock : todoBlocks) {
            tmp = new Vector3i(todoBlock.x, todoBlock.y, todoBlock.z);
            this.drawCuboidQuad(tmp, tmp.clone().add(1), todoBlock.w, 0.0f, 0.75f, 1.0f, 0.25f);
            this.drawCuboidLine(tmp, tmp.clone().add(1), todoBlock.w, 0.0f, 0.75f, 1.0f, 0.25f);
        }
    }

    public void renderTileEntities(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
        World mcWorld = this.settings.minecraft.theWorld;
        SchematicWorld world = this.settings.schematic;
        RenderTileEntity renderTileEntity = this.settings.renderTileEntity;
        int mcBlockId = 0;
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)((255.0f - (float)this.schematica.alpha.value) / 255.0f));
        try {
            for (TileEntity tileEntity : world.getTileEntities()) {
                int x = tileEntity.xCoord;
                int y = tileEntity.yCoord;
                int z = tileEntity.zCoord;
                if (x < minX || x >= maxX || z < minZ || z >= maxZ || y < minY || y >= maxY || (mcBlockId = mcWorld.getBlockId(x + this.settings.offset.x, y + this.settings.offset.y, z + this.settings.offset.z)) != 0) continue;
                if (tileEntity instanceof TileEntitySign) {
                    renderTileEntity.renderTileEntitySignAt((TileEntitySign)tileEntity);
                    continue;
                }
                TileEntitySpecialRenderer tileEntitySpecialRenderer = TileEntityRenderer.instance.getSpecialRendererForEntity(tileEntity);
                if (tileEntitySpecialRenderer == null) continue;
                tileEntitySpecialRenderer.renderTileEntityAt(tileEntity, x, y, z, 0.0f);
                GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)((255.0f - (float)this.schematica.alpha.value) / 255.0f));
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void renderGuide() {
        Vector3i start = null;
        Vector3i end = null;
        start = this.settings.pointMin.clone().sub(this.settings.offset);
        end = this.settings.pointMax.clone().sub(this.settings.offset).add(1);
        this.drawCuboidLine(start, end, 63, 0.0f, 0.75f, 0.0f, 0.25f);
        start = this.settings.pointA.clone().sub(this.settings.offset);
        end = start.clone().add(1);
        this.drawCuboidLine(start, end, 63, 0.75f, 0.0f, 0.0f, 0.25f);
        this.drawCuboidQuad(start, end, 63, 0.75f, 0.0f, 0.0f, 0.25f);
        start = this.settings.pointB.clone().sub(this.settings.offset);
        end = start.clone().add(1);
        this.drawCuboidLine(start, end, 63, 0.0f, 0.0f, 0.75f, 0.25f);
        this.drawCuboidQuad(start, end, 63, 0.0f, 0.0f, 0.75f, 0.25f);
    }

    public void drawCuboidQuad(Vector3i a, Vector3i b, int sides, float red, float green, float blue, float alpha) {
        Vector3f zero = new Vector3f(a.x, a.y, a.z).sub(this.settings.blockDelta);
        Vector3f size = new Vector3f(b.x, b.y, b.z).add(this.settings.blockDelta);
        if (this.objectCountQuad + 24 >= this.bufferSizeQuad) {
            this.needsExpansionQuad = true;
            return;
        }
        int total = 0;
        if ((sides & 0x10) != 0) {
            this.vBufferQuad.put(zero.x).put(zero.y).put(zero.z);
            this.vBufferQuad.put(zero.x).put(zero.y).put(size.z);
            this.vBufferQuad.put(zero.x).put(size.y).put(size.z);
            this.vBufferQuad.put(zero.x).put(size.y).put(zero.z);
            total += 4;
        }
        if ((sides & 0x20) != 0) {
            this.vBufferQuad.put(size.x).put(zero.y).put(size.z);
            this.vBufferQuad.put(size.x).put(zero.y).put(zero.z);
            this.vBufferQuad.put(size.x).put(size.y).put(zero.z);
            this.vBufferQuad.put(size.x).put(size.y).put(size.z);
            total += 4;
        }
        if ((sides & 4) != 0) {
            this.vBufferQuad.put(size.x).put(zero.y).put(zero.z);
            this.vBufferQuad.put(zero.x).put(zero.y).put(zero.z);
            this.vBufferQuad.put(zero.x).put(size.y).put(zero.z);
            this.vBufferQuad.put(size.x).put(size.y).put(zero.z);
            total += 4;
        }
        if ((sides & 8) != 0) {
            this.vBufferQuad.put(zero.x).put(zero.y).put(size.z);
            this.vBufferQuad.put(size.x).put(zero.y).put(size.z);
            this.vBufferQuad.put(size.x).put(size.y).put(size.z);
            this.vBufferQuad.put(zero.x).put(size.y).put(size.z);
            total += 4;
        }
        if ((sides & 1) != 0) {
            this.vBufferQuad.put(size.x).put(zero.y).put(zero.z);
            this.vBufferQuad.put(size.x).put(zero.y).put(size.z);
            this.vBufferQuad.put(zero.x).put(zero.y).put(size.z);
            this.vBufferQuad.put(zero.x).put(zero.y).put(zero.z);
            total += 4;
        }
        if ((sides & 2) != 0) {
            this.vBufferQuad.put(size.x).put(size.y).put(zero.z);
            this.vBufferQuad.put(zero.x).put(size.y).put(zero.z);
            this.vBufferQuad.put(zero.x).put(size.y).put(size.z);
            this.vBufferQuad.put(size.x).put(size.y).put(size.z);
            total += 4;
        }
        int i = 0;
        while (i < total) {
            this.cBufferQuad.put(red).put(green).put(blue).put(alpha);
            ++i;
        }
        this.objectCountQuad += total;
    }

    public void drawCuboidLine(Vector3i a, Vector3i b, int sides, float red, float green, float blue, float alpha) {
        Vector3f zero = new Vector3f(a.x, a.y, a.z).sub(this.settings.blockDelta);
        Vector3f size = new Vector3f(b.x, b.y, b.z).add(this.settings.blockDelta);
        if (this.objectCountLine + 24 >= this.bufferSizeLine) {
            this.needsExpansionLine = true;
            return;
        }
        int total = 0;
        if ((sides & 0x11) != 0) {
            this.vBufferLine.put(zero.x).put(zero.y).put(zero.z);
            this.vBufferLine.put(zero.x).put(zero.y).put(size.z);
            total += 2;
        }
        if ((sides & 0x12) != 0) {
            this.vBufferLine.put(zero.x).put(size.y).put(zero.z);
            this.vBufferLine.put(zero.x).put(size.y).put(size.z);
            total += 2;
        }
        if ((sides & 0x21) != 0) {
            this.vBufferLine.put(size.x).put(zero.y).put(zero.z);
            this.vBufferLine.put(size.x).put(zero.y).put(size.z);
            total += 2;
        }
        if ((sides & 0x22) != 0) {
            this.vBufferLine.put(size.x).put(size.y).put(zero.z);
            this.vBufferLine.put(size.x).put(size.y).put(size.z);
            total += 2;
        }
        if ((sides & 5) != 0) {
            this.vBufferLine.put(zero.x).put(zero.y).put(zero.z);
            this.vBufferLine.put(size.x).put(zero.y).put(zero.z);
            total += 2;
        }
        if ((sides & 6) != 0) {
            this.vBufferLine.put(zero.x).put(size.y).put(zero.z);
            this.vBufferLine.put(size.x).put(size.y).put(zero.z);
            total += 2;
        }
        if ((sides & 9) != 0) {
            this.vBufferLine.put(zero.x).put(zero.y).put(size.z);
            this.vBufferLine.put(size.x).put(zero.y).put(size.z);
            total += 2;
        }
        if ((sides & 0xA) != 0) {
            this.vBufferLine.put(zero.x).put(size.y).put(size.z);
            this.vBufferLine.put(size.x).put(size.y).put(size.z);
            total += 2;
        }
        if ((sides & 0x14) != 0) {
            this.vBufferLine.put(zero.x).put(zero.y).put(zero.z);
            this.vBufferLine.put(zero.x).put(size.y).put(zero.z);
            total += 2;
        }
        if ((sides & 0x24) != 0) {
            this.vBufferLine.put(size.x).put(zero.y).put(zero.z);
            this.vBufferLine.put(size.x).put(size.y).put(zero.z);
            total += 2;
        }
        if ((sides & 0x18) != 0) {
            this.vBufferLine.put(zero.x).put(zero.y).put(size.z);
            this.vBufferLine.put(zero.x).put(size.y).put(size.z);
            total += 2;
        }
        if ((sides & 0x28) != 0) {
            this.vBufferLine.put(size.x).put(zero.y).put(size.z);
            this.vBufferLine.put(size.x).put(size.y).put(size.z);
            total += 2;
        }
        int i = 0;
        while (i < total) {
            this.cBufferLine.put(red).put(green).put(blue).put(alpha);
            ++i;
        }
        this.objectCountLine += total;
    }

    public String getTextureName(String texture) {
        if (!this.schematica.enableAlpha.value) {
            return texture;
        }
        String textureName = "/" + (int)((255.0f - (float)this.schematica.alpha.value) / 255.0f * 255.0f) + texture.replace('/', '-');
        if (this.textures.contains(textureName)) {
            return textureName;
        }
        try {
            TexturePackBase texturePackBase = this.settings.minecraft.texturePackList.selectedTexturePack;
            File newTextureFile = new File(Settings.textureDirectory, String.valueOf(texturePackBase.texturePackFileName.replace(".zip", "")) + textureName);
            System.out.println(newTextureFile.getAbsolutePath());
            if (!newTextureFile.exists()) {
                BufferedImage bufferedImage = this.readTextureImage(texturePackBase.func_6481_a(texture));
                System.out.println(bufferedImage);
                if (bufferedImage == null) {
                    return texture;
                }
                int x = 0;
                while (x < bufferedImage.getWidth()) {
                    int y = 0;
                    while (y < bufferedImage.getHeight()) {
                        int color = bufferedImage.getRGB(x, y);
                        bufferedImage.setRGB(x, y, (int)((float)(color >> 24 & 0xFF) * ((255.0f - (float)this.schematica.alpha.value) / 255.0f)) << 24 | color & 0xFFFFFF);
                        ++y;
                    }
                    ++x;
                }
                newTextureFile.getParentFile().mkdirs();
                ImageIO.write((RenderedImage)bufferedImage, "png", newTextureFile);
            }
            this.loadTexture(textureName, this.readTextureImage(new BufferedInputStream(new FileInputStream(newTextureFile))));
            this.textures.add(textureName);
            return textureName;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return texture;
    }

    public int loadTexture(String texture, BufferedImage textureImage) throws IllegalArgumentException, IllegalAccessException {
        HashMap textureMap = this.settings.minecraft.renderEngine.textureMap;
        IntBuffer singleIntBuffer = this.settings.minecraft.renderEngine.singleIntBuffer;
        Integer textureId = (Integer)textureMap.get(texture);
        if (textureId != null) {
            return textureId;
        }
        try {
            singleIntBuffer.clear();
            GLAllocation.generateTextureNames(singleIntBuffer);
            int glTextureId = singleIntBuffer.get(0);
            this.settings.minecraft.renderEngine.setupTexture(textureImage, glTextureId);
            textureMap.put(texture, glTextureId);
            return glTextureId;
        }
        catch (Exception e) {
            e.printStackTrace();
            GLAllocation.generateTextureNames(singleIntBuffer);
            int glTextureId = singleIntBuffer.get(0);
            this.settings.minecraft.renderEngine.setupTexture(this.missingTextureImage, glTextureId);
            textureMap.put(texture, glTextureId);
            return glTextureId;
        }
    }

    public BufferedImage readTextureImage(InputStream inputStream) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(inputStream);
        inputStream.close();
        return bufferedImage;
    }
}

