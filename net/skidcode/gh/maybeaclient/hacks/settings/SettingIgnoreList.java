/*
 * Decompiled with CFR 0.152.
 */
package net.skidcode.gh.maybeaclient.hacks.settings;

import java.util.HashSet;
import net.minecraft.src.NBTBase;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.NBTTagList;
import net.minecraft.src.NBTTagString;
import net.skidcode.gh.maybeaclient.gui.click.Tab;
import net.skidcode.gh.maybeaclient.hacks.Hack;
import net.skidcode.gh.maybeaclient.hacks.settings.Setting;

public class SettingIgnoreList
extends Setting {
    public boolean enabled = true;
    public HashSet<String> names = new HashSet();

    public SettingIgnoreList(Hack hack, String name) {
        super(hack, name);
    }

    public void setValue(String name) {
        if (this.names.contains(name = name.toLowerCase())) {
            this.names.remove(name);
        } else {
            this.names.add(name);
        }
    }

    @Override
    public String valueToString() {
        String nms = String.join((CharSequence)", ", this.names);
        return nms;
    }

    @Override
    public void reset() {
        this.names.clear();
        this.enabled = true;
    }

    @Override
    public boolean validateValue(String value) {
        return true;
    }

    @Override
    public void writeToNBT(NBTTagCompound output) {
        NBTTagCompound comp = new NBTTagCompound();
        NBTTagList list = new NBTTagList();
        comp.setBoolean("Enabled", this.enabled);
        for (String s : this.names) {
            NBTTagString st = new NBTTagString();
            st.stringValue = s;
            list.setTag(st);
        }
        comp.setTag("Names", list);
        output.setTag(this.name, comp);
    }

    @Override
    public void readFromNBT(NBTTagCompound input) {
        if (input.tagMap.containsKey(this.name)) {
            NBTTagCompound tag = input.getCompoundTag(this.name);
            this.enabled = tag.getBoolean("Enabled");
            NBTTagList list = tag.getTagList("Names");
            if (list != null) {
                int i = 0;
                while (i < list.tagCount()) {
                    NBTBase nb = list.tagAt(i);
                    if (nb instanceof NBTTagString) {
                        NBTTagString str = (NBTTagString)nb;
                        this.names.add(str.stringValue);
                    } else {
                        System.out.println("[MaybeAClient] Ignore list contains invalid tag types! " + nb.getType());
                    }
                    ++i;
                }
            }
        }
    }

    @Override
    public void renderElement(Tab tab, int xStart, int yStart, int xEnd, int yEnd) {
        if (this.enabled) {
            tab.renderFrameBackGround(xStart, yStart, xEnd, yEnd, 0.0f, 0.6666667f, 0.6666667f, 1.0f);
        }
    }

    @Override
    public void onDeselect(Tab tab, int xMin, int yMin, int xMax, int yMax, int mouseX, int mouseY, int mouseClick) {
        this.enabled = !this.enabled;
    }
}

