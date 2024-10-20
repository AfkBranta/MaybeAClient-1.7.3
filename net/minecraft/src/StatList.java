package net.minecraft.src;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import net.minecraft.src.Achievement;
import net.minecraft.src.AchievementList;
import net.minecraft.src.AchievementMap;
import net.minecraft.src.Block;
import net.minecraft.src.CraftingManager;
import net.minecraft.src.FurnaceRecipes;
import net.minecraft.src.IRecipe;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.StatBasic;
import net.minecraft.src.StatCollector;
import net.minecraft.src.StatCrafting;
import net.minecraft.src.StatDistance;
import net.minecraft.src.StatTime;

public class StatList {
    private static Map field_25169_C = new HashMap();
    public static List field_25188_a = new ArrayList();
    public static List field_25187_b = new ArrayList();
    public static List field_25186_c = new ArrayList();
    public static List field_25185_d = new ArrayList();
    public static StatBasic field_25184_e = new StatBasic(1000, StatCollector.func_25200_a("stat.startGame")).func_25068_c();
    public static StatBasic field_25183_f = new StatBasic(1001, StatCollector.func_25200_a("stat.createWorld")).func_25068_c();
    public static StatBasic field_25182_g = new StatBasic(1002, StatCollector.func_25200_a("stat.loadWorld")).func_25068_c();
    public static StatBasic field_25181_h = new StatBasic(1003, StatCollector.func_25200_a("stat.joinMultiplayer")).func_25068_c();
    public static StatBasic field_25180_i = new StatBasic(1004, StatCollector.func_25200_a("stat.leaveGame")).func_25068_c();
    public static StatBasic field_25179_j = new StatTime(1100, StatCollector.func_25200_a("stat.playOneMinute")).func_25068_c();
    public static StatBasic field_25178_k = new StatDistance(2000, StatCollector.func_25200_a("stat.walkOneCm")).func_25068_c();
    public static StatBasic field_25177_l = new StatDistance(2001, StatCollector.func_25200_a("stat.swimOneCm")).func_25068_c();
    public static StatBasic field_25176_m = new StatDistance(2002, StatCollector.func_25200_a("stat.fallOneCm")).func_25068_c();
    public static StatBasic field_25175_n = new StatDistance(2003, StatCollector.func_25200_a("stat.climbOneCm")).func_25068_c();
    public static StatBasic field_25174_o = new StatDistance(2004, StatCollector.func_25200_a("stat.flyOneCm")).func_25068_c();
    public static StatBasic field_25173_p = new StatDistance(2005, StatCollector.func_25200_a("stat.diveOneCm")).func_25068_c();
    public static StatBasic field_25171_q = new StatBasic(2010, StatCollector.func_25200_a("stat.jump")).func_25068_c();
    public static StatBasic field_25168_r = new StatBasic(2011, StatCollector.func_25200_a("stat.drop")).func_25068_c();
    public static StatBasic field_25167_s = new StatBasic(2020, StatCollector.func_25200_a("stat.damageDealt")).func_25068_c();
    public static StatBasic field_25165_t = new StatBasic(2021, StatCollector.func_25200_a("stat.damageTaken")).func_25068_c();
    public static StatBasic field_25163_u = new StatBasic(2022, StatCollector.func_25200_a("stat.deaths")).func_25068_c();
    public static StatBasic field_25162_v = new StatBasic(2023, StatCollector.func_25200_a("stat.mobKills")).func_25068_c();
    public static StatBasic field_25161_w = new StatBasic(2024, StatCollector.func_25200_a("stat.playerKills")).func_25068_c();
    public static StatBasic field_25160_x = new StatBasic(2025, StatCollector.func_25200_a("stat.fishCaught")).func_25068_c();
    public static StatBasic[] field_25159_y = StatList.func_25153_a("stat.mineBlock", 0x1000000);
    public static StatBasic[] field_25158_z = null;
    public static StatBasic[] field_25172_A = null;
    public static StatBasic[] field_25170_B = null;
    private static boolean field_25166_D = false;
    private static boolean field_25164_E = false;

    public StatList() {
        System.out.println("Stats: " + field_25188_a.size());
    }

    public static void func_25152_a(StatBasic var0) {
        if (field_25169_C.containsKey(var0.field_25071_d)) {
            throw new RuntimeException("Duplicate stat id: " + ((StatBasic)StatList.field_25169_C.get((Object)Integer.valueOf((int)var0.field_25071_d))).field_25070_e + " and " + var0.field_25070_e + " at id " + var0.field_25071_d);
        }
        var0.field_25069_f = AchievementMap.func_25208_a(var0.field_25071_d);
        field_25188_a.add(var0);
        if (var0.func_25067_a()) {
            AchievementList.field_25196_a.add((Achievement)var0);
        } else if (var0 instanceof StatCrafting) {
            StatCrafting var1 = (StatCrafting)var0;
            if (var1.func_25072_b() < Block.blocksList.length) {
                field_25186_c.add(var1);
            } else {
                field_25185_d.add(var1);
            }
        } else {
            field_25187_b.add(var0);
        }
    }

    public static void func_25154_a() {
        field_25172_A = StatList.func_25155_a(field_25172_A, "stat.useItem", 0x1020000, 0, Block.blocksList.length);
        field_25170_B = StatList.func_25149_b(field_25170_B, "stat.breakItem", 0x1030000, 0, Block.blocksList.length);
        field_25166_D = true;
        StatList.func_25157_c();
    }

    public static void func_25151_b() {
        field_25172_A = StatList.func_25155_a(field_25172_A, "stat.useItem", 0x1020000, Block.blocksList.length, 32000);
        field_25170_B = StatList.func_25149_b(field_25170_B, "stat.breakItem", 0x1030000, Block.blocksList.length, 32000);
        field_25164_E = true;
        StatList.func_25157_c();
    }

    public static void func_25157_c() {
        if (field_25166_D && field_25164_E) {
            HashSet<Integer> var0 = new HashSet<Integer>();
            for (IRecipe var2 : CraftingManager.getInstance().func_25193_b()) {
                var0.add(var2.func_25117_b().itemID);
            }
            for (ItemStack var4 : FurnaceRecipes.smelting().func_25194_b().values()) {
                var0.add(var4.itemID);
            }
            field_25158_z = new StatBasic[32000];
            for (Integer var5 : var0) {
                if (Item.itemsList[var5] == null) continue;
                String var3 = StatCollector.func_25199_a("stat.craftItem", Item.itemsList[var5].func_25009_k());
                StatList.field_25158_z[var5.intValue()] = new StatCrafting(0x1010000 + var5, var3, var5).func_25068_c();
            }
            StatList.func_25150_a(field_25158_z);
        }
    }

    private static StatBasic[] func_25153_a(String var0, int var1) {
        StatBasic[] var2 = new StatBasic[256];
        int var3 = 0;
        while (var3 < 256) {
            if (Block.blocksList[var3] != null) {
                String var4 = StatCollector.func_25199_a(var0, Block.blocksList[var3].func_25016_i());
                var2[var3] = new StatCrafting(var1 + var3, var4, var3).func_25068_c();
            }
            ++var3;
        }
        StatList.func_25150_a(var2);
        return var2;
    }

    private static StatBasic[] func_25155_a(StatBasic[] var0, String var1, int var2, int var3, int var4) {
        if (var0 == null) {
            var0 = new StatBasic[32000];
        }
        int var5 = var3;
        while (var5 < var4) {
            if (Item.itemsList[var5] != null) {
                String var6 = StatCollector.func_25199_a(var1, Item.itemsList[var5].func_25009_k());
                var0[var5] = new StatCrafting(var2 + var5, var6, var5).func_25068_c();
            }
            ++var5;
        }
        StatList.func_25150_a(var0);
        return var0;
    }

    private static StatBasic[] func_25149_b(StatBasic[] var0, String var1, int var2, int var3, int var4) {
        if (var0 == null) {
            var0 = new StatBasic[32000];
        }
        int var5 = var3;
        while (var5 < var4) {
            if (Item.itemsList[var5] != null && Item.itemsList[var5].func_25007_g()) {
                String var6 = StatCollector.func_25199_a(var1, Item.itemsList[var5].func_25009_k());
                var0[var5] = new StatCrafting(var2 + var5, var6, var5).func_25068_c();
            }
            ++var5;
        }
        StatList.func_25150_a(var0);
        return var0;
    }

    private static void func_25150_a(StatBasic[] var0) {
        StatList.func_25156_a(var0, Block.waterStill.blockID, Block.waterMoving.blockID);
        StatList.func_25156_a(var0, Block.lavaStill.blockID, Block.lavaStill.blockID);
        StatList.func_25156_a(var0, Block.pumpkinLantern.blockID, Block.pumpkin.blockID);
        StatList.func_25156_a(var0, Block.stoneOvenActive.blockID, Block.stoneOvenIdle.blockID);
        StatList.func_25156_a(var0, Block.oreRedstoneGlowing.blockID, Block.oreRedstone.blockID);
        StatList.func_25156_a(var0, Block.redstoneRepeaterActive.blockID, Block.redstoneRepeaterIdle.blockID);
        StatList.func_25156_a(var0, Block.torchRedstoneActive.blockID, Block.torchRedstoneIdle.blockID);
        StatList.func_25156_a(var0, Block.mushroomRed.blockID, Block.mushroomBrown.blockID);
        StatList.func_25156_a(var0, Block.stairSingle.blockID, Block.stairDouble.blockID);
    }

    private static void func_25156_a(StatBasic[] var0, int var1, int var2) {
        var0[var1] = var0[var2];
        field_25188_a.remove(var0[var1]);
        field_25187_b.remove(var0[var1]);
    }
}

