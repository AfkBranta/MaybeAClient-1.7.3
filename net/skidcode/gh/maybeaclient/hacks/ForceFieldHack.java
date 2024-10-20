/*
 * Decompiled with CFR 0.152.
 */
package net.skidcode.gh.maybeaclient.hacks;

import java.util.List;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityAnimals;
import net.minecraft.src.EntityMobs;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntitySquid;
import net.minecraft.src.EntityWolf;
import net.minecraft.src.Packet7;
import net.skidcode.gh.maybeaclient.events.Event;
import net.skidcode.gh.maybeaclient.events.EventListener;
import net.skidcode.gh.maybeaclient.events.EventRegistry;
import net.skidcode.gh.maybeaclient.events.impl.EventMPMovementUpdate;
import net.skidcode.gh.maybeaclient.hacks.Hack;
import net.skidcode.gh.maybeaclient.hacks.category.Category;
import net.skidcode.gh.maybeaclient.hacks.settings.SettingChooser;
import net.skidcode.gh.maybeaclient.hacks.settings.SettingDouble;
import net.skidcode.gh.maybeaclient.hacks.settings.SettingIgnoreList;
import net.skidcode.gh.maybeaclient.utils.ChatColor;

public class ForceFieldHack
extends Hack
implements EventListener {
    public SettingChooser chooser;
    public SettingIgnoreList ignoreList;
    public SettingDouble radius;

    public ForceFieldHack() {
        super("KillAura", "Damages the entities around the player", 0, Category.COMBAT);
        boolean[] blArray = new boolean[4];
        blArray[0] = true;
        blArray[2] = true;
        this.chooser = new SettingChooser(this, "Entity Chooser", new String[]{"Players", "Animals", "Hostiles", "Neutrals"}, blArray);
        this.ignoreList = new SettingIgnoreList(this, "Ignore");
        this.radius = new SettingDouble(this, "Radius", 6.0, 0.0, 10.0);
        this.addSetting(this.chooser);
        this.addSetting(this.radius);
        this.addSetting(this.ignoreList);
        EventRegistry.registerListener(EventMPMovementUpdate.class, this);
    }

    @Override
    public String getNameForArrayList() {
        String s = "[";
        s = String.valueOf(s) + (Object)((Object)ChatColor.LIGHTCYAN);
        if (this.chooser.getValue("Players")) {
            s = String.valueOf(s) + "P";
        }
        if (this.chooser.getValue("Animals")) {
            s = String.valueOf(s) + "A";
        }
        if (this.chooser.getValue("Hostiles")) {
            s = String.valueOf(s) + "H";
        }
        if (this.chooser.getValue("Neutrals")) {
            s = String.valueOf(s) + "N";
        }
        s = String.valueOf(s) + (Object)((Object)ChatColor.WHITE);
        s = String.valueOf(s) + "]";
        return String.valueOf(this.name) + s;
    }

    public boolean canBeAttacked(Entity e) {
        return this.chooser.getValue("Players") && e instanceof EntityPlayer || this.chooser.getValue("Animals") && e instanceof EntityAnimals && !(e instanceof EntitySquid) && !(e instanceof EntityWolf) || this.chooser.getValue("Hostiles") && e instanceof EntityMobs || this.chooser.getValue("Neutrals") && (e instanceof EntitySquid || e instanceof EntityWolf);
    }

    public void handleEvent(Event event) {
        if (event instanceof EventMPMovementUpdate) {
            double rad = this.radius.getValue();
            List entitiesNearby = ForceFieldHack.mc.theWorld.getEntitiesWithinAABBExcludingEntity(ForceFieldHack.mc.thePlayer, AxisAlignedBB.getBoundingBox(ForceFieldHack.mc.thePlayer.posX - rad, ForceFieldHack.mc.thePlayer.posY - rad, ForceFieldHack.mc.thePlayer.posZ - rad, ForceFieldHack.mc.thePlayer.posX + rad, ForceFieldHack.mc.thePlayer.posY + rad, ForceFieldHack.mc.thePlayer.posZ + rad));
            for (Object o : entitiesNearby) {
                Entity e = (Entity)o;
                if (!this.canBeAttacked(e) || e instanceof EntityPlayer && this.ignoreList.enabled && this.ignoreList.names.contains(((EntityPlayer)e).username.toLowerCase())) continue;
                mc.getSendQueue().addToSendQueue(new Packet7(0, e.entityId, 1));
            }
        }
    }
}

