/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.zip.GZIPInputStream;
import net.minecraft.src.ChunkFilePattern;
import net.minecraft.src.ChunkFolderPattern;
import net.minecraft.src.FileMatcher;
import net.minecraft.src.IProgressUpdate;
import net.minecraft.src.ISaveHandler;
import net.minecraft.src.MathHelper;
import net.minecraft.src.RegionFile;
import net.minecraft.src.RegionFileCache;
import net.minecraft.src.SaveFormatComparator;
import net.minecraft.src.SaveFormatOld;
import net.minecraft.src.SaveOldDir;
import net.minecraft.src.WorldInfo;

public class SaveConverterMcRegion
extends SaveFormatOld {
    public SaveConverterMcRegion(File var1) {
        super(var1);
    }

    @Override
    public String func_22178_a() {
        return "Scaevolus' McRegion";
    }

    @Override
    public List func_22176_b() {
        File[] var2;
        ArrayList<SaveFormatComparator> var1 = new ArrayList<SaveFormatComparator>();
        File[] var3 = var2 = this.field_22180_a.listFiles();
        int var4 = var2.length;
        int var5 = 0;
        while (var5 < var4) {
            String var7;
            WorldInfo var8;
            File var6 = var3[var5];
            if (var6.isDirectory() && (var8 = this.func_22173_b(var7 = var6.getName())) != null) {
                boolean var9 = var8.getSaveVersion() != 19132;
                String var10 = var8.getWorldName();
                if (var10 == null || MathHelper.stringNullOrLengthZero(var10)) {
                    var10 = var7;
                }
                var1.add(new SaveFormatComparator(var7, var10, var8.getLastTimePlayed(), var8.getSizeOnDisk(), var9));
            }
            ++var5;
        }
        return var1;
    }

    @Override
    public void func_22177_c() {
        RegionFileCache.func_22192_a();
    }

    @Override
    public ISaveHandler getSaveLoader(String var1, boolean var2) {
        return new SaveOldDir(this.field_22180_a, var1, var2);
    }

    @Override
    public boolean isOldMapFormat(String var1) {
        WorldInfo var2 = this.func_22173_b(var1);
        return var2 != null && var2.getSaveVersion() == 0;
    }

    @Override
    public boolean convertMapFormat(String var1, IProgressUpdate var2) {
        var2.setLoadingProgress(0);
        ArrayList var3 = new ArrayList();
        ArrayList var4 = new ArrayList();
        ArrayList var5 = new ArrayList();
        ArrayList var6 = new ArrayList();
        File var7 = new File(this.field_22180_a, var1);
        File var8 = new File(var7, "DIM-1");
        System.out.println("Scanning folders...");
        this.func_22183_a(var7, var3, var4);
        if (var8.exists()) {
            this.func_22183_a(var8, var5, var6);
        }
        int var9 = var3.size() + var5.size() + var4.size() + var6.size();
        System.out.println("Total conversion count is " + var9);
        this.func_22181_a(var7, var3, 0, var9, var2);
        this.func_22181_a(var8, var5, var3.size(), var9, var2);
        WorldInfo var10 = this.func_22173_b(var1);
        var10.setSaveVersion(19132);
        ISaveHandler var11 = this.getSaveLoader(var1, false);
        var11.saveWorldInfo(var10);
        this.func_22182_a(var4, var3.size() + var5.size(), var9, var2);
        if (var8.exists()) {
            this.func_22182_a(var6, var3.size() + var5.size() + var4.size(), var9, var2);
        }
        return true;
    }

    private void func_22183_a(File var1, ArrayList var2, ArrayList var3) {
        File[] var6;
        ChunkFolderPattern var4 = new ChunkFolderPattern(null);
        ChunkFilePattern var5 = new ChunkFilePattern(null);
        File[] var7 = var6 = var1.listFiles(var4);
        int var8 = var6.length;
        int var9 = 0;
        while (var9 < var8) {
            File[] var11;
            File var10 = var7[var9];
            var3.add(var10);
            File[] var12 = var11 = var10.listFiles(var4);
            int var13 = var11.length;
            int var14 = 0;
            while (var14 < var13) {
                File[] var16;
                File var15 = var12[var14];
                File[] var17 = var16 = var15.listFiles(var5);
                int var18 = var16.length;
                int var19 = 0;
                while (var19 < var18) {
                    File var20 = var17[var19];
                    var2.add(new FileMatcher(var20));
                    ++var19;
                }
                ++var14;
            }
            ++var9;
        }
    }

    private void func_22181_a(File var1, ArrayList var2, int var3, int var4, IProgressUpdate var5) {
        Collections.sort(var2);
        byte[] var6 = new byte[4096];
        for (FileMatcher var8 : var2) {
            int var10;
            int var9 = var8.func_22323_b();
            RegionFile var11 = RegionFileCache.func_22193_a(var1, var9, var10 = var8.func_22321_c());
            if (!var11.func_22202_c(var9 & 0x1F, var10 & 0x1F)) {
                try {
                    int var17;
                    DataInputStream var12 = new DataInputStream(new GZIPInputStream(new FileInputStream(var8.func_22324_a())));
                    DataOutputStream var13 = var11.func_22205_b(var9 & 0x1F, var10 & 0x1F);
                    boolean var14 = false;
                    while ((var17 = var12.read(var6)) != -1) {
                        var13.write(var6, 0, var17);
                    }
                    var13.close();
                    var12.close();
                }
                catch (IOException var15) {
                    var15.printStackTrace();
                }
            }
            int var16 = (int)Math.round(100.0 * (double)(++var3) / (double)var4);
            var5.setLoadingProgress(var16);
        }
        RegionFileCache.func_22192_a();
    }

    private void func_22182_a(ArrayList var1, int var2, int var3, IProgressUpdate var4) {
        for (File var6 : var1) {
            File[] var7 = var6.listFiles();
            SaveConverterMcRegion.func_22179_a(var7);
            var6.delete();
            int var8 = (int)Math.round(100.0 * (double)(++var2) / (double)var3);
            var4.setLoadingProgress(var8);
        }
    }
}

