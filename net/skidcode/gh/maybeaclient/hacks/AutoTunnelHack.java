/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.skidcode.gh.maybeaclient.hacks;

import java.util.ArrayList;
import net.minecraft.src.Block;
import net.minecraft.src.MathHelper;
import net.minecraft.src.RenderManager;
import net.skidcode.gh.maybeaclient.events.Event;
import net.skidcode.gh.maybeaclient.events.EventListener;
import net.skidcode.gh.maybeaclient.events.EventRegistry;
import net.skidcode.gh.maybeaclient.events.impl.EventPlayerUpdatePost;
import net.skidcode.gh.maybeaclient.events.impl.EventWorldRenderPreFog;
import net.skidcode.gh.maybeaclient.hacks.Hack;
import net.skidcode.gh.maybeaclient.hacks.category.Category;
import net.skidcode.gh.maybeaclient.hacks.settings.SettingBoolean;
import net.skidcode.gh.maybeaclient.hacks.settings.SettingInteger;
import net.skidcode.gh.maybeaclient.hacks.settings.SettingMode;
import net.skidcode.gh.maybeaclient.utils.BlockPos;
import net.skidcode.gh.maybeaclient.utils.ChatColor;
import net.skidcode.gh.maybeaclient.utils.Direction;
import net.skidcode.gh.maybeaclient.utils.PlayerUtils;
import net.skidcode.gh.maybeaclient.utils.RenderUtils;
import org.lwjgl.opengl.GL11;

public class AutoTunnelHack
extends Hack
implements EventListener {
    public SettingMode direction = new SettingMode(this, "Direction", "Camera", "X+", "X-", "Z+", "Z-");
    public SettingMode breakMode;
    public SettingInteger maxMultiBlocks = new SettingInteger(this, "MaxMultiBlocks", 4, 1, 20);
    public SettingBoolean swing = new SettingBoolean(this, "Swing", false);
    public SettingBoolean autoWalk = new SettingBoolean(this, "DirectionAutowalk", false);
    public static AutoTunnelHack instance;
    public int selectedBlockX = 0;
    public int selectedBlockY = 0;
    public int selectedBlockZ = 0;
    public boolean isSelected = false;
    public static ArrayList<BlockPos> currentlyDestroying;

    static {
        currentlyDestroying = new ArrayList();
    }

    public AutoTunnelHack() {
        super("AutoTunnel", "Automatically destroy blocks in front of the player", 0, Category.MISC);
        instance = this;
        this.breakMode = new SettingMode(this, "BreakMode", new String[]{"Legal", "InstantSingle", "InstantMulti"}){

            @Override
            public void setValue(String value) {
                super.setValue(value);
                AutoTunnelHack.instance.maxMultiBlocks.hidden = !value.equalsIgnoreCase("InstantMulti");
            }
        };
        this.addSetting(this.direction);
        this.addSetting(this.autoWalk);
        this.addSetting(this.breakMode);
        this.addSetting(this.maxMultiBlocks);
        this.addSetting(this.swing);
        EventRegistry.registerListener(EventPlayerUpdatePost.class, this);
        EventRegistry.registerListener(EventWorldRenderPreFog.class, this);
    }

    @Override
    public String getNameForArrayList() {
        String s = "[";
        s = String.valueOf(s) + (Object)((Object)ChatColor.LIGHTCYAN);
        s = String.valueOf(s) + this.getDirection().toString();
        s = String.valueOf(s) + (Object)((Object)ChatColor.WHITE);
        s = String.valueOf(s) + "]";
        return String.valueOf(this.name) + s;
    }

    @Override
    public void onDisable() {
        AutoTunnelHack.mc.playerController.field_1064_b = false;
        this.isSelected = false;
    }

    public boolean trySettingXYZ(int x, int y, int z) {
        int id = AutoTunnelHack.mc.theWorld.getBlockId(x, y, z);
        if (id != 0) {
            float hardness = Block.blocksList[id].blockHardness;
            if (hardness >= 0.0f) {
                if (id != Block.waterMoving.blockID && id != Block.waterStill.blockID && id != Block.lavaMoving.blockID && id != Block.lavaStill.blockID) {
                    this.isSelected = true;
                    this.selectedBlockX = x;
                    this.selectedBlockY = y;
                    this.selectedBlockZ = z;
                    if (this.breakMode.currentMode.equalsIgnoreCase("InstantMulti")) {
                        this.isSelected = false;
                        if (currentlyDestroying.size() >= this.maxMultiBlocks.value) {
                            return true;
                        }
                        currentlyDestroying.add(new BlockPos(x, y, z));
                        if (this.swing.value) {
                            AutoTunnelHack.mc.thePlayer.swingItem();
                        }
                        PlayerUtils.destroyBlockInstant(x, y, z, this.getDirection().hitSide);
                        return false;
                    }
                } else {
                    this.isSelected = false;
                }
            }
        } else {
            this.isSelected = false;
        }
        return this.isSelected;
    }

    public Direction getDirection() {
        if (this.direction.currentMode.equalsIgnoreCase("X+")) {
            return Direction.XPOS;
        }
        if (this.direction.currentMode.equalsIgnoreCase("X-")) {
            return Direction.XNEG;
        }
        if (this.direction.currentMode.equalsIgnoreCase("Z+")) {
            return Direction.ZPOS;
        }
        if (this.direction.currentMode.equalsIgnoreCase("Z-")) {
            return Direction.ZNEG;
        }
        if (this.direction.currentMode.equalsIgnoreCase("Camera")) {
            return PlayerUtils.getDirection();
        }
        return Direction.NULL;
    }

    public void handleEvent(Event event) {
        if (event instanceof EventWorldRenderPreFog) {
            double rendX = RenderManager.renderPosX;
            double rendY = RenderManager.renderPosY;
            double rendZ = RenderManager.renderPosZ;
            GL11.glColor4f((float)1.0f, (float)0.0f, (float)0.0f, (float)1.0f);
            GL11.glDisable((int)2929);
            if (this.isSelected) {
                RenderUtils.drawOutlinedBlockBB((double)this.selectedBlockX - rendX, (double)this.selectedBlockY - rendY, (double)this.selectedBlockZ - rendZ);
            }
            for (BlockPos pos : currentlyDestroying) {
                RenderUtils.drawOutlinedBlockBB((double)pos.x - rendX, (double)pos.y - rendY, (double)pos.z - rendZ);
            }
            GL11.glEnable((int)2929);
        } else if (event instanceof EventPlayerUpdatePost) {
            currentlyDestroying.clear();
            Direction dir = this.getDirection();
            int x = MathHelper.floor_double(AutoTunnelHack.mc.thePlayer.posX);
            int y = MathHelper.floor_double(AutoTunnelHack.mc.thePlayer.posY);
            int z = MathHelper.floor_double(AutoTunnelHack.mc.thePlayer.posZ);
            boolean success = false;
            int i = 0;
            while (i < 4 && !success) {
                success = this.trySettingXYZ(x += dir.offX, y += dir.offY, z += dir.offZ);
                if (!success) {
                    success = this.trySettingXYZ(x, y - 1, z);
                }
                ++i;
            }
            if (this.isSelected) {
                if (this.swing.value) {
                    AutoTunnelHack.mc.thePlayer.swingItem();
                }
                if (this.breakMode.currentMode.equalsIgnoreCase("InstantSingle")) {
                    PlayerUtils.destroyBlockInstant(this.selectedBlockX, this.selectedBlockY, this.selectedBlockZ, dir.hitSide);
                } else {
                    PlayerUtils.destroyBlock(this.selectedBlockX, this.selectedBlockY, this.selectedBlockZ, dir.hitSide);
                }
            }
        }
    }
}

