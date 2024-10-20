package net.minecraft.src;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.src.CompressedStreamTools;
import net.minecraft.src.IProgressUpdate;
import net.minecraft.src.ISaveFormat;
import net.minecraft.src.ISaveHandler;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.SaveFormatComparator;
import net.minecraft.src.SaveHandler;
import net.minecraft.src.WorldInfo;

public class SaveFormatOld
implements ISaveFormat {
    protected final File field_22180_a;

    public SaveFormatOld(File var1) {
        if (!var1.exists()) {
            var1.mkdirs();
        }
        this.field_22180_a = var1;
    }

    @Override
    public String func_22178_a() {
        return "Old Format";
    }

    @Override
    public List func_22176_b() {
        ArrayList<SaveFormatComparator> var1 = new ArrayList<SaveFormatComparator>();
        int var2 = 0;
        while (var2 < 5) {
            String var3 = "World" + (var2 + 1);
            WorldInfo var4 = this.func_22173_b(var3);
            if (var4 != null) {
                var1.add(new SaveFormatComparator(var3, "", var4.getLastTimePlayed(), var4.getSizeOnDisk(), false));
            }
            ++var2;
        }
        return var1;
    }

    @Override
    public void func_22177_c() {
    }

    @Override
    public WorldInfo func_22173_b(String var1) {
        File var2 = new File(this.field_22180_a, var1);
        if (!var2.exists()) {
            return null;
        }
        File var3 = new File(var2, "level.dat");
        if (var3.exists()) {
            try {
                NBTTagCompound var4 = CompressedStreamTools.func_1138_a(new FileInputStream(var3));
                NBTTagCompound var5 = var4.getCompoundTag("Data");
                return new WorldInfo(var5);
            }
            catch (Exception var6) {
                var6.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public void func_22170_a(String var1, String var2) {
        File var4;
        File var3 = new File(this.field_22180_a, var1);
        if (var3.exists() && (var4 = new File(var3, "level.dat")).exists()) {
            try {
                NBTTagCompound var5 = CompressedStreamTools.func_1138_a(new FileInputStream(var4));
                NBTTagCompound var6 = var5.getCompoundTag("Data");
                var6.setString("LevelName", var2);
                CompressedStreamTools.writeGzippedCompoundToOutputStream(var5, new FileOutputStream(var4));
            }
            catch (Exception var7) {
                var7.printStackTrace();
            }
        }
    }

    @Override
    public void func_22172_c(String var1) {
        File var2 = new File(this.field_22180_a, var1);
        if (var2.exists()) {
            SaveFormatOld.func_22179_a(var2.listFiles());
            var2.delete();
        }
    }

    protected static void func_22179_a(File[] var0) {
        int var1 = 0;
        while (var1 < var0.length) {
            if (var0[var1].isDirectory()) {
                SaveFormatOld.func_22179_a(var0[var1].listFiles());
            }
            var0[var1].delete();
            ++var1;
        }
    }

    @Override
    public ISaveHandler getSaveLoader(String var1, boolean var2) {
        return new SaveHandler(this.field_22180_a, var1, var2);
    }

    @Override
    public boolean isOldMapFormat(String var1) {
        return false;
    }

    @Override
    public boolean convertMapFormat(String var1, IProgressUpdate var2) {
        return false;
    }
}

