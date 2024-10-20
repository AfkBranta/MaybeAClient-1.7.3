/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.Comparator;
import net.minecraft.src.Entity;
import net.minecraft.src.WorldRenderer;

public class EntitySorter
implements Comparator {
    private Entity entityForSorting;

    public EntitySorter(Entity var1) {
        this.entityForSorting = var1;
    }

    public int sortByDistanceToEntity(WorldRenderer var1, WorldRenderer var2) {
        float dist2;
        float dist1 = var1.distanceToEntitySquared(this.entityForSorting);
        return dist1 < (dist2 = var2.distanceToEntitySquared(this.entityForSorting)) ? -1 : (dist1 == dist2 ? 0 : 1);
    }

    public int compare(Object var1, Object var2) {
        return this.sortByDistanceToEntity((WorldRenderer)var1, (WorldRenderer)var2);
    }
}

