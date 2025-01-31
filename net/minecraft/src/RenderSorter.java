/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.Comparator;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.WorldRenderer;

public class RenderSorter
implements Comparator {
    private EntityLiving field_4274_a;

    public RenderSorter(EntityLiving var1) {
        this.field_4274_a = var1;
    }

    public int func_993_a(WorldRenderer var1, WorldRenderer var2) {
        double var7;
        boolean var3 = var1.isInFrustum;
        boolean var4 = var2.isInFrustum;
        if (var3 && !var4) {
            return 1;
        }
        if (var4 && !var3) {
            return -1;
        }
        double var5 = var1.distanceToEntitySquared(this.field_4274_a);
        if (var5 < (var7 = (double)var2.distanceToEntitySquared(this.field_4274_a))) {
            return 1;
        }
        if (var5 > var7) {
            return -1;
        }
        return var1.field_1735_w < var2.field_1735_w ? 1 : -1;
    }

    public int compare(Object var1, Object var2) {
        return this.func_993_a((WorldRenderer)var1, (WorldRenderer)var2);
    }
}

