/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.src.TexturePackBase;
import net.minecraft.src.TexturePackCustom;
import net.minecraft.src.TexturePackDefault;

public class TexturePackList {
    private List availableTexturePacks = new ArrayList();
    private TexturePackBase defaultTexturePack = new TexturePackDefault();
    public TexturePackBase selectedTexturePack;
    private Map field_6538_d = new HashMap();
    private Minecraft mc;
    private File texturePackDir;
    private String currentTexturePack;

    public TexturePackList(Minecraft var1, File var2) {
        this.mc = var1;
        this.texturePackDir = new File(var2, "texturepacks");
        if (!this.texturePackDir.exists()) {
            this.texturePackDir.mkdirs();
        }
        this.currentTexturePack = var1.gameSettings.skin;
        this.updateAvaliableTexturePacks();
        this.selectedTexturePack.func_6482_a();
    }

    public boolean setTexturePack(TexturePackBase var1) {
        if (var1 == this.selectedTexturePack) {
            return false;
        }
        this.selectedTexturePack.closeTexturePackFile();
        this.currentTexturePack = var1.texturePackFileName;
        this.selectedTexturePack = var1;
        this.mc.gameSettings.skin = this.currentTexturePack;
        this.mc.gameSettings.saveOptions();
        this.selectedTexturePack.func_6482_a();
        return true;
    }

    public void updateAvaliableTexturePacks() {
        ArrayList<TexturePackBase> var1 = new ArrayList<TexturePackBase>();
        this.selectedTexturePack = null;
        var1.add(this.defaultTexturePack);
        if (this.texturePackDir.exists() && this.texturePackDir.isDirectory()) {
            File[] var2;
            File[] var3 = var2 = this.texturePackDir.listFiles();
            int var4 = var2.length;
            int var5 = 0;
            while (var5 < var4) {
                File var6 = var3[var5];
                if (var6.isFile() && var6.getName().toLowerCase().endsWith(".zip")) {
                    String var7 = String.valueOf(var6.getName()) + ":" + var6.length() + ":" + var6.lastModified();
                    try {
                        if (!this.field_6538_d.containsKey(var7)) {
                            TexturePackCustom var8 = new TexturePackCustom(var6);
                            var8.field_6488_d = var7;
                            this.field_6538_d.put(var7, var8);
                            var8.func_6485_a(this.mc);
                        }
                        TexturePackBase var12 = (TexturePackBase)this.field_6538_d.get(var7);
                        if (var12.texturePackFileName.equals(this.currentTexturePack)) {
                            this.selectedTexturePack = var12;
                        }
                        var1.add(var12);
                    }
                    catch (IOException var9) {
                        var9.printStackTrace();
                    }
                }
                ++var5;
            }
        }
        if (this.selectedTexturePack == null) {
            this.selectedTexturePack = this.defaultTexturePack;
        }
        this.availableTexturePacks.removeAll(var1);
        for (TexturePackBase var11 : this.availableTexturePacks) {
            var11.func_6484_b(this.mc);
            this.field_6538_d.remove(var11.field_6488_d);
        }
        this.availableTexturePacks = var1;
    }

    public List availableTexturePacks() {
        return new ArrayList(this.availableTexturePacks);
    }
}

