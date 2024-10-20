/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.ARBVertexBufferObject
 *  org.lwjgl.opengl.GL11
 *  org.lwjgl.opengl.GLContext
 */
package net.minecraft.src;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import net.minecraft.src.GLAllocation;
import net.skidcode.gh.maybeaclient.hacks.SchematicaHack;
import net.skidcode.gh.maybeaclient.hacks.XRayHack;
import org.lwjgl.opengl.ARBVertexBufferObject;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;

public class Tessellator {
    public static boolean convertQuadsToTriangles = true;
    private static boolean tryVBO = false;
    private ByteBuffer byteBuffer;
    private IntBuffer intBuffer;
    private FloatBuffer floatBuffer;
    private int[] rawBuffer;
    private int vertexCount = 0;
    private double textureU;
    private double textureV;
    private int color;
    private boolean hasColor = false;
    private boolean hasTexture = false;
    private boolean hasNormals = false;
    private int rawBufferIndex = 0;
    private int addedVertices = 0;
    private boolean isColorDisabled = false;
    private int drawMode;
    private double xOffset;
    private double yOffset;
    private double zOffset;
    private int normal;
    public static final Tessellator instance = new Tessellator(0x200000);
    private boolean isDrawing = false;
    private boolean useVBO = false;
    private IntBuffer vertexBuffers;
    private int vboIndex = 0;
    private int vboCount = 10;
    private int bufferSize;
    public boolean schematicaRendering = false;

    private Tessellator(int var1) {
        this.bufferSize = var1;
        this.byteBuffer = GLAllocation.createDirectByteBuffer(var1 * 4);
        this.intBuffer = this.byteBuffer.asIntBuffer();
        this.floatBuffer = this.byteBuffer.asFloatBuffer();
        this.rawBuffer = new int[var1];
        boolean bl = this.useVBO = tryVBO && GLContext.getCapabilities().GL_ARB_vertex_buffer_object;
        if (this.useVBO) {
            this.vertexBuffers = GLAllocation.createDirectIntBuffer(this.vboCount);
            ARBVertexBufferObject.glGenBuffersARB((IntBuffer)this.vertexBuffers);
        }
    }

    public void draw() {
        if (!this.isDrawing) {
            throw new IllegalStateException("Not tesselating!");
        }
        this.isDrawing = false;
        if (this.vertexCount > 0) {
            this.intBuffer.clear();
            this.intBuffer.put(this.rawBuffer, 0, this.rawBufferIndex);
            this.byteBuffer.position(0);
            this.byteBuffer.limit(this.rawBufferIndex * 4);
            if (this.useVBO) {
                this.vboIndex = (this.vboIndex + 1) % this.vboCount;
                ARBVertexBufferObject.glBindBufferARB((int)34962, (int)this.vertexBuffers.get(this.vboIndex));
                ARBVertexBufferObject.glBufferDataARB((int)34962, (ByteBuffer)this.byteBuffer, (int)35040);
            }
            if (this.hasTexture) {
                if (this.useVBO) {
                    GL11.glTexCoordPointer((int)2, (int)5126, (int)32, (long)12L);
                } else {
                    this.floatBuffer.position(3);
                    GL11.glTexCoordPointer((int)2, (int)32, (FloatBuffer)this.floatBuffer);
                }
                GL11.glEnableClientState((int)32888);
            }
            if (this.hasColor) {
                if (this.useVBO) {
                    GL11.glColorPointer((int)4, (int)5121, (int)32, (long)20L);
                } else {
                    this.byteBuffer.position(20);
                    GL11.glColorPointer((int)4, (boolean)true, (int)32, (ByteBuffer)this.byteBuffer);
                }
                GL11.glEnableClientState((int)32886);
            }
            if (this.hasNormals) {
                if (this.useVBO) {
                    GL11.glNormalPointer((int)5120, (int)32, (long)24L);
                } else {
                    this.byteBuffer.position(24);
                    GL11.glNormalPointer((int)32, (ByteBuffer)this.byteBuffer);
                }
                GL11.glEnableClientState((int)32885);
            }
            if (this.useVBO) {
                GL11.glVertexPointer((int)3, (int)5126, (int)32, (long)0L);
            } else {
                this.floatBuffer.position(0);
                GL11.glVertexPointer((int)3, (int)32, (FloatBuffer)this.floatBuffer);
            }
            GL11.glEnableClientState((int)32884);
            if (this.drawMode == 7 && convertQuadsToTriangles) {
                GL11.glDrawArrays((int)4, (int)0, (int)this.vertexCount);
            } else {
                GL11.glDrawArrays((int)this.drawMode, (int)0, (int)this.vertexCount);
            }
            GL11.glDisableClientState((int)32884);
            if (this.hasTexture) {
                GL11.glDisableClientState((int)32888);
            }
            if (this.hasColor) {
                GL11.glDisableClientState((int)32886);
            }
            if (this.hasNormals) {
                GL11.glDisableClientState((int)32885);
            }
        }
        this.reset();
    }

    private void reset() {
        this.vertexCount = 0;
        this.byteBuffer.clear();
        this.rawBufferIndex = 0;
        this.addedVertices = 0;
    }

    public void startDrawingQuads() {
        this.startDrawing(7);
    }

    public void startDrawing(int var1) {
        if (this.isDrawing) {
            throw new IllegalStateException("Already tesselating!");
        }
        this.isDrawing = true;
        this.reset();
        this.drawMode = var1;
        this.hasNormals = false;
        this.hasColor = false;
        this.hasTexture = false;
        this.isColorDisabled = false;
    }

    public void setTextureUV(double var1, double var3) {
        this.hasTexture = true;
        this.textureU = var1;
        this.textureV = var3;
    }

    public void setColorOpaque_F(float var1, float var2, float var3) {
        this.setColorOpaque((int)(var1 * 255.0f), (int)(var2 * 255.0f), (int)(var3 * 255.0f));
    }

    public void setColorRGBA_F(float var1, float var2, float var3, float var4) {
        this.setColorRGBA((int)(var1 * 255.0f), (int)(var2 * 255.0f), (int)(var3 * 255.0f), (int)(var4 * 255.0f));
    }

    public void setColorOpaque(int var1, int var2, int var3) {
        if (this.schematicaRendering) {
            if (SchematicaHack.instance.enableAlpha.value) {
                this.setColorRGBA(var1, var2, var3, SchematicaHack.instance.alpha.value & 0xFF);
            } else {
                this.setColorRGBA(var1, var2, var3, 255);
            }
        } else if (XRayHack.INSTANCE.status && XRayHack.INSTANCE.mode.currentMode.equalsIgnoreCase("Opacity")) {
            this.setColorRGBA(var1, var2, var3, (int)(XRayHack.INSTANCE.opacity.value * 255.0f) & 0xFF);
        } else {
            this.setColorRGBA(var1, var2, var3, 255);
        }
    }

    public void setColorRGBA(int var1, int var2, int var3, int var4) {
        if (!this.isColorDisabled) {
            if (var1 > 255) {
                var1 = 255;
            }
            if (var2 > 255) {
                var2 = 255;
            }
            if (var3 > 255) {
                var3 = 255;
            }
            if (var4 > 255) {
                var4 = 255;
            }
            if (var1 < 0) {
                var1 = 0;
            }
            if (var2 < 0) {
                var2 = 0;
            }
            if (var3 < 0) {
                var3 = 0;
            }
            if (var4 < 0) {
                var4 = 0;
            }
            this.hasColor = true;
            this.color = ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN ? var4 << 24 | var3 << 16 | var2 << 8 | var1 : var1 << 24 | var2 << 16 | var3 << 8 | var4;
        }
    }

    public void addVertexWithUV(double var1, double var3, double var5, double var7, double var9) {
        this.setTextureUV(var7, var9);
        this.addVertex(var1, var3, var5);
    }

    public void addVertex(double var1, double var3, double var5) {
        ++this.addedVertices;
        if (this.drawMode == 7 && convertQuadsToTriangles && this.addedVertices % 4 == 0) {
            int var7 = 0;
            while (var7 < 2) {
                int var8 = 8 * (3 - var7);
                if (this.hasTexture) {
                    this.rawBuffer[this.rawBufferIndex + 3] = this.rawBuffer[this.rawBufferIndex - var8 + 3];
                    this.rawBuffer[this.rawBufferIndex + 4] = this.rawBuffer[this.rawBufferIndex - var8 + 4];
                }
                if (this.hasColor) {
                    this.rawBuffer[this.rawBufferIndex + 5] = this.rawBuffer[this.rawBufferIndex - var8 + 5];
                }
                this.rawBuffer[this.rawBufferIndex + 0] = this.rawBuffer[this.rawBufferIndex - var8 + 0];
                this.rawBuffer[this.rawBufferIndex + 1] = this.rawBuffer[this.rawBufferIndex - var8 + 1];
                this.rawBuffer[this.rawBufferIndex + 2] = this.rawBuffer[this.rawBufferIndex - var8 + 2];
                ++this.vertexCount;
                this.rawBufferIndex += 8;
                ++var7;
            }
        }
        if (this.hasTexture) {
            this.rawBuffer[this.rawBufferIndex + 3] = Float.floatToRawIntBits((float)this.textureU);
            this.rawBuffer[this.rawBufferIndex + 4] = Float.floatToRawIntBits((float)this.textureV);
        }
        if (this.hasColor) {
            this.rawBuffer[this.rawBufferIndex + 5] = this.color;
        }
        if (this.hasNormals) {
            this.rawBuffer[this.rawBufferIndex + 6] = this.normal;
        }
        this.rawBuffer[this.rawBufferIndex + 0] = Float.floatToRawIntBits((float)(var1 + this.xOffset));
        this.rawBuffer[this.rawBufferIndex + 1] = Float.floatToRawIntBits((float)(var3 + this.yOffset));
        this.rawBuffer[this.rawBufferIndex + 2] = Float.floatToRawIntBits((float)(var5 + this.zOffset));
        this.rawBufferIndex += 8;
        ++this.vertexCount;
        if (this.vertexCount % 4 == 0 && this.rawBufferIndex >= this.bufferSize - 32) {
            this.draw();
            this.isDrawing = true;
        }
    }

    public void setColorOpaque_I(int var1) {
        int var2 = var1 >> 16 & 0xFF;
        int var3 = var1 >> 8 & 0xFF;
        int var4 = var1 & 0xFF;
        this.setColorOpaque(var2, var3, var4);
    }

    public void setColorRGBA_I(int var1, int var2) {
        int var3 = var1 >> 16 & 0xFF;
        int var4 = var1 >> 8 & 0xFF;
        int var5 = var1 & 0xFF;
        this.setColorRGBA(var3, var4, var5, var2);
    }

    public void disableColor() {
        this.isColorDisabled = true;
    }

    public void setNormal(float var1, float var2, float var3) {
        if (!this.isDrawing) {
            System.out.println("But..");
        }
        this.hasNormals = true;
        byte var4 = (byte)(var1 * 128.0f);
        byte var5 = (byte)(var2 * 127.0f);
        byte var6 = (byte)(var3 * 127.0f);
        this.normal = var4 | var5 << 8 | var6 << 16;
    }

    public void setTranslationD(double var1, double var3, double var5) {
        this.xOffset = var1;
        this.yOffset = var3;
        this.zOffset = var5;
    }

    public void setTranslationF(float var1, float var2, float var3) {
        this.xOffset += (double)var1;
        this.yOffset += (double)var2;
        this.zOffset += (double)var3;
    }
}

