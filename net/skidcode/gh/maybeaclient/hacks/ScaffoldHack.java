/*
 * Decompiled with CFR 0.152.
 */
package net.skidcode.gh.maybeaclient.hacks;

import net.minecraft.src.Block;
import net.minecraft.src.ItemBlock;
import net.minecraft.src.ItemStack;
import net.minecraft.src.MathHelper;
import net.minecraft.src.PlayerControllerMP;
import net.skidcode.gh.maybeaclient.events.Event;
import net.skidcode.gh.maybeaclient.events.EventListener;
import net.skidcode.gh.maybeaclient.events.EventRegistry;
import net.skidcode.gh.maybeaclient.events.impl.EventPlayerUpdatePre;
import net.skidcode.gh.maybeaclient.hacks.AutoTunnelHack;
import net.skidcode.gh.maybeaclient.hacks.Hack;
import net.skidcode.gh.maybeaclient.hacks.category.Category;
import net.skidcode.gh.maybeaclient.hacks.settings.SettingBlockChooser;
import net.skidcode.gh.maybeaclient.hacks.settings.SettingBoolean;

public class ScaffoldHack
extends Hack
implements EventListener {
    public SettingBoolean enableBlockFilter;
    public SettingBlockChooser filter = new SettingBlockChooser(this, "Filter", new int[0]);

    public ScaffoldHack() {
        super("Scaffold", "Auto places blocks below the player", 0, Category.MOVEMENT);
        this.enableBlockFilter = new SettingBoolean(this, "Allow using only specific blocks", false){

            @Override
            public void setValue(boolean d) {
                super.setValue(d);
                ((ScaffoldHack)this.hack).filter.hidden = !this.value;
            }
        };
        this.addSetting(this.enableBlockFilter);
        this.addSetting(this.filter);
        EventRegistry.registerListener(EventPlayerUpdatePre.class, this);
    }

    public int findSlotInHotbar() {
        int i = 0;
        while (i < 9) {
            ItemStack stack = ScaffoldHack.mc.thePlayer.inventory.mainInventory[i];
            if (stack != null && stack.getItem() instanceof ItemBlock) {
                if (stack.stackSize == 0) {
                    ScaffoldHack.mc.thePlayer.inventory.mainInventory[i] = null;
                } else {
                    ItemBlock bl = (ItemBlock)stack.getItem();
                    if (!this.enableBlockFilter.value ? Block.blocksList[bl.blockID].blockMaterial.getIsSolid() : this.filter.blocks[bl.blockID]) {
                        return i;
                    }
                }
            }
            ++i;
        }
        return -1;
    }

    public boolean placeBlock(int x, int y, int z) {
        int slot;
        int px = MathHelper.floor_double(ScaffoldHack.mc.thePlayer.posX);
        int pz = MathHelper.floor_double(ScaffoldHack.mc.thePlayer.posZ);
        int py = MathHelper.floor_double(ScaffoldHack.mc.thePlayer.posY - 2.0);
        if ((px != x || pz != z || py != y) && (slot = this.findSlotInHotbar()) != -1) {
            int saved = ScaffoldHack.mc.thePlayer.inventory.currentItem;
            ItemStack item = ScaffoldHack.mc.thePlayer.inventory.mainInventory[slot];
            int side = 0;
            boolean ret = false;
            while (side <= 6) {
                int placeon;
                if (side == 6) break;
                int xp = x;
                int yp = py;
                int zp = z;
                if (side == 0) {
                    ++yp;
                }
                if (side == 1) {
                    --yp;
                }
                if (side == 2) {
                    ++zp;
                }
                if (side == 3) {
                    --zp;
                }
                if (side == 4) {
                    ++xp;
                }
                if (side == 5) {
                    --xp;
                }
                if ((placeon = ScaffoldHack.mc.theWorld.getBlockId(xp, yp, zp)) != 0) {
                    Block b = Block.blocksList[placeon];
                    if (b.blockMaterial.getIsSolid()) {
                        ScaffoldHack.mc.thePlayer.inventory.currentItem = slot;
                        if (mc.isMultiplayerWorld()) {
                            ((PlayerControllerMP)ScaffoldHack.mc.playerController).func_730_e();
                        }
                        ScaffoldHack.mc.playerController.sendPlaceBlock(ScaffoldHack.mc.thePlayer, ScaffoldHack.mc.theWorld, item, xp, yp, zp, side);
                        ScaffoldHack.mc.thePlayer.inventory.currentItem = saved;
                        if (mc.isMultiplayerWorld()) {
                            ((PlayerControllerMP)ScaffoldHack.mc.playerController).func_730_e();
                        }
                        ret = true;
                        break;
                    }
                }
                ++side;
            }
            return ret;
        }
        return false;
    }

    public boolean placeBlock(int x, int y, int z, double offX, double offZ) {
        int px = MathHelper.floor_double(ScaffoldHack.mc.thePlayer.posX);
        int py = MathHelper.floor_double(ScaffoldHack.mc.thePlayer.posY - 2.0);
        int pz = MathHelper.floor_double(ScaffoldHack.mc.thePlayer.posZ);
        if (px != x && pz != z) {
            if (this.placeBlock(x, y, pz)) {
                return this.placeBlock(x, y, z);
            }
            if (this.placeBlock(px, y, z)) {
                return this.placeBlock(x, y, z);
            }
        } else {
            if (px != x || pz != z || py != y) {
                return this.placeBlock(x, y, z);
            }
            int id = ScaffoldHack.mc.theWorld.getBlockId(x, y, z);
            if (id == 0 || !Block.blocksList[id].blockMaterial.getIsSolid()) {
                this.placeBlock(x, y, z);
            }
        }
        return false;
    }

    public void handleEvent(Event event) {
        if (event instanceof EventPlayerUpdatePre) {
            double strafe = ScaffoldHack.mc.thePlayer.moveStrafing;
            double forward = ScaffoldHack.mc.thePlayer.moveForward;
            if (forward == 0.0 && strafe == 0.0) {
                forward = 1.0;
            }
            float rotYaw = ScaffoldHack.mc.thePlayer.rotationYaw;
            if (AutoTunnelHack.instance.status && AutoTunnelHack.instance.autoWalk.value) {
                rotYaw = AutoTunnelHack.instance.getDirection().yaw;
            }
            double var5 = MathHelper.sin(rotYaw * (float)Math.PI / 180.0f);
            double var6 = MathHelper.cos(rotYaw * (float)Math.PI / 180.0f);
            if (Math.abs(var5) < 0.01) {
                var5 = 0.0;
            }
            if (Math.abs(var6) < 0.01) {
                var6 = 0.0;
            }
            double xo = strafe * var6 - forward * var5;
            double zo = forward * var6 + strafe * var5;
            double yo = 0.0;
            if (!ScaffoldHack.mc.thePlayer.onGround) {
                double mx = ScaffoldHack.mc.thePlayer.motionX;
                double mz = ScaffoldHack.mc.thePlayer.motionZ;
                if (Math.abs(mx) < 0.1) {
                    mx = 0.0;
                }
                if (Math.abs(mz) < 0.1) {
                    mz = 0.0;
                }
                if (Math.signum(xo) != Math.signum(mx)) {
                    xo = 0.0;
                }
                if (Math.signum(zo) != Math.signum(mz)) {
                    zo = 0.0;
                }
                yo = 1.0;
            }
            if (xo > 1.0) {
                xo = 1.0;
            }
            if (xo < -1.0) {
                xo = -1.0;
            }
            if (zo > 1.0) {
                zo = 1.0;
            }
            if (zo < -1.0) {
                zo = -1.0;
            }
            int placeX = MathHelper.floor_double(xo + ScaffoldHack.mc.thePlayer.posX);
            int placeY = MathHelper.floor_double(ScaffoldHack.mc.thePlayer.posY - 2.0 + yo);
            int placeZ = MathHelper.floor_double(zo + ScaffoldHack.mc.thePlayer.posZ);
            this.placeBlock(placeX, placeY, placeZ, xo, zo);
        }
    }
}

