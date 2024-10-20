/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.src.Achievement;
import net.minecraft.src.StatCollector;

public class AchievementList {
    public static List field_25196_a = new ArrayList();
    public static Achievement field_25195_b = new Achievement(0x500000, StatCollector.func_25200_a("achievement.openInventory"), 0, 0, null);
    public static Achievement field_25198_c = new Achievement(0x500001, StatCollector.func_25200_a("achievement.mineWood"), 4, 1, field_25195_b);
    public static Achievement field_25197_d = new Achievement(0x500001, StatCollector.func_25200_a("achievement.buildWorkBench"), 8, -1, field_25198_c);
}

