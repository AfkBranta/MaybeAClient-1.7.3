package net.minecraft.src;

import java.util.List;
import net.minecraft.src.IProgressUpdate;
import net.minecraft.src.ISaveHandler;
import net.minecraft.src.WorldInfo;

public interface ISaveFormat {
    public String func_22178_a();

    public ISaveHandler getSaveLoader(String var1, boolean var2);

    public List func_22176_b();

    public void func_22177_c();

    public WorldInfo func_22173_b(String var1);

    public void func_22172_c(String var1);

    public void func_22170_a(String var1, String var2);

    public boolean isOldMapFormat(String var1);

    public boolean convertMapFormat(String var1, IProgressUpdate var2);
}

