/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.io.File;
import java.util.List;
import net.minecraft.src.IChunkLoader;
import net.minecraft.src.McRegionChunkLoader;
import net.minecraft.src.SaveHandler;
import net.minecraft.src.WorldInfo;
import net.minecraft.src.WorldProvider;
import net.minecraft.src.WorldProviderHell;

public class SaveOldDir
extends SaveHandler {
    public SaveOldDir(File var1, String var2, boolean var3) {
        super(var1, var2, var3);
    }

    @Override
    public IChunkLoader getChunkLoader(WorldProvider var1) {
        File var2 = this.getSaveDirectory();
        if (var1 instanceof WorldProviderHell) {
            File var3 = new File(var2, "DIM-1");
            var3.mkdirs();
            return new McRegionChunkLoader(var3);
        }
        return new McRegionChunkLoader(var2);
    }

    @Override
    public void saveWorldInfoAndPlayer(WorldInfo var1, List var2) {
        var1.setSaveVersion(19132);
        super.saveWorldInfoAndPlayer(var1, var2);
    }
}

