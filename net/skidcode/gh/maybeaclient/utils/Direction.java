/*
 * Decompiled with CFR 0.152.
 */
package net.skidcode.gh.maybeaclient.utils;

public enum Direction {
    ZPOS(0, 0, 1, 2, 0.0f),
    XNEG(-1, 0, 0, 5, 90.0f),
    ZNEG(0, 0, -1, 3, 180.0f),
    XPOS(1, 0, 0, 4, 270.0f),
    NULL(0, 0, 0, 0, 0.0f);

    public int offX;
    public int offY;
    public int offZ;
    public int hitSide;
    public float yaw;

    private Direction(int x, int y, int z, int hitside, float yaw) {
        this.offX = x;
        this.offY = y;
        this.offZ = z;
        this.hitSide = hitside;
        this.yaw = yaw;
    }

    public String toString() {
        switch (this) {
            case ZPOS: {
                return "Z+";
            }
            case XNEG: {
                return "X-";
            }
            case ZNEG: {
                return "Z-";
            }
            case XPOS: {
                return "X+";
            }
        }
        return "NULL";
    }
}

