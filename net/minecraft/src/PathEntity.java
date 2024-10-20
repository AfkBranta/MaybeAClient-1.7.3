/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.Entity;
import net.minecraft.src.PathPoint;
import net.minecraft.src.Vec3D;

public class PathEntity {
    private final PathPoint[] points;
    public final int pathLength;
    private int pathIndex;

    public PathEntity(PathPoint[] var1) {
        this.points = var1;
        this.pathLength = var1.length;
    }

    public void incrementPathIndex() {
        ++this.pathIndex;
    }

    public boolean isFinished() {
        return this.pathIndex >= this.points.length;
    }

    public PathPoint func_22328_c() {
        return this.pathLength > 0 ? this.points[this.pathLength - 1] : null;
    }

    public Vec3D getPosition(Entity var1) {
        double var2 = (double)this.points[this.pathIndex].xCoord + (double)((int)(var1.width + 1.0f)) * 0.5;
        double var4 = this.points[this.pathIndex].yCoord;
        double var6 = (double)this.points[this.pathIndex].zCoord + (double)((int)(var1.width + 1.0f)) * 0.5;
        return Vec3D.createVector(var2, var4, var6);
    }
}

