/*
 * Decompiled with CFR 0.152.
 */
package lunatrius.schematica.util;

public class Vector4f {
    public float x;
    public float y;
    public float z;
    public float w;
    public static final Vector4f ZERO = new Vector4f();

    public Vector4f() {
        this.x = 0.0f;
        this.y = 0.0f;
        this.z = 0.0f;
        this.w = 0.0f;
    }

    public Vector4f(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public Vector4f add(Vector4f vec) {
        return this.add(vec.x, vec.y, vec.z, vec.w);
    }

    public Vector4f add(int i) {
        return this.add(i, i, i, i);
    }

    public Vector4f add(float x, float y, float z, float w) {
        this.x += x;
        this.y += y;
        this.z += z;
        this.w += w;
        return this;
    }

    public Vector4f sub(Vector4f vec) {
        return this.sub(vec.x, vec.y, vec.z, vec.w);
    }

    public Vector4f sub(int i) {
        return this.sub(i, i, i, i);
    }

    public Vector4f sub(float x, float y, float z, float w) {
        this.x -= x;
        this.y -= y;
        this.z -= z;
        this.w -= w;
        return this;
    }

    public Vector4f clone() {
        return new Vector4f(this.x, this.y, this.z, this.w);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Vector4f)) {
            return false;
        }
        Vector4f o = (Vector4f)obj;
        return this.x == o.x && this.y == o.y && this.z == o.z && this.w == o.w;
    }

    public int hashCode() {
        int hash = 7;
        hash = (int)((float)(71 * hash) + this.x);
        hash = (int)((float)(71 * hash) + this.y);
        hash = (int)((float)(71 * hash) + this.z);
        hash = (int)((float)(71 * hash) + this.w);
        return hash;
    }
}

