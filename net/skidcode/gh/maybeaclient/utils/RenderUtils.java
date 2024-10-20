/*
 * Decompiled with CFR 0.152.
 */
package net.skidcode.gh.maybeaclient.utils;

import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Tessellator;

public class RenderUtils {
    public static void drawOutlinedBlockBB(double x, double y, double z) {
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawing(3);
        tessellator.addVertex(x, y, z);
        tessellator.addVertex(x + 1.0, y, z);
        tessellator.addVertex(x + 1.0, y, z + 1.0);
        tessellator.addVertex(x, y, z + 1.0);
        tessellator.addVertex(x, y, z);
        tessellator.draw();
        tessellator.startDrawing(3);
        tessellator.addVertex(x, y + 1.0, z);
        tessellator.addVertex(x + 1.0, y + 1.0, z);
        tessellator.addVertex(x + 1.0, y + 1.0, z + 1.0);
        tessellator.addVertex(x, y + 1.0, z + 1.0);
        tessellator.addVertex(x, y + 1.0, z);
        tessellator.draw();
        tessellator.startDrawing(1);
        tessellator.addVertex(x, y, z);
        tessellator.addVertex(x, y + 1.0, z);
        tessellator.addVertex(x + 1.0, y, z);
        tessellator.addVertex(x + 1.0, y + 1.0, z);
        tessellator.addVertex(x + 1.0, y, z + 1.0);
        tessellator.addVertex(x + 1.0, y + 1.0, z + 1.0);
        tessellator.addVertex(x, y, z + 1.0);
        tessellator.addVertex(x, y + 1.0, z + 1.0);
        tessellator.draw();
    }

    public static void drawOutlinedBB(AxisAlignedBB bb) {
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawing(3);
        tessellator.addVertex(bb.minX, bb.minY, bb.minZ);
        tessellator.addVertex(bb.maxX, bb.minY, bb.minZ);
        tessellator.addVertex(bb.maxX, bb.minY, bb.maxZ);
        tessellator.addVertex(bb.minX, bb.minY, bb.maxZ);
        tessellator.addVertex(bb.minX, bb.minY, bb.minZ);
        tessellator.draw();
        tessellator.startDrawing(3);
        tessellator.addVertex(bb.minX, bb.maxY, bb.minZ);
        tessellator.addVertex(bb.maxX, bb.maxY, bb.minZ);
        tessellator.addVertex(bb.maxX, bb.maxY, bb.maxZ);
        tessellator.addVertex(bb.minX, bb.maxY, bb.maxZ);
        tessellator.addVertex(bb.minX, bb.maxY, bb.minZ);
        tessellator.draw();
        tessellator.startDrawing(1);
        tessellator.addVertex(bb.minX, bb.minY, bb.minZ);
        tessellator.addVertex(bb.minX, bb.maxY, bb.minZ);
        tessellator.addVertex(bb.maxX, bb.minY, bb.minZ);
        tessellator.addVertex(bb.maxX, bb.maxY, bb.minZ);
        tessellator.addVertex(bb.maxX, bb.minY, bb.maxZ);
        tessellator.addVertex(bb.maxX, bb.maxY, bb.maxZ);
        tessellator.addVertex(bb.minX, bb.minY, bb.maxZ);
        tessellator.addVertex(bb.minX, bb.maxY, bb.maxZ);
        tessellator.draw();
    }
}

