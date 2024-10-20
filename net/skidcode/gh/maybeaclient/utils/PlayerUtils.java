/*
 * Decompiled with CFR 0.152.
 */
package net.skidcode.gh.maybeaclient.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.src.Block;
import net.minecraft.src.EntityPlayerSP;
import net.minecraft.src.MathHelper;
import net.minecraft.src.Packet14BlockDig;
import net.minecraft.src.PlayerControllerMP;
import net.skidcode.gh.maybeaclient.hacks.AutoToolHack;
import net.skidcode.gh.maybeaclient.utils.Direction;

public class PlayerUtils {
    public static Minecraft mc;

    public static Direction getDirection() {
        EntityPlayerSP player = PlayerUtils.mc.thePlayer;
        int dir = MathHelper.floor_float(player.rotationYaw * 4.0f / 360.0f + 0.5f) & 3;
        return Direction.values()[dir];
    }

    public static boolean destroyBlock(int x, int y, int z, int side) {
        PlayerUtils.mc.playerController.clickBlock(x, y, z, side);
        PlayerUtils.mc.playerController.sendBlockRemoving(x, y, z, side);
        PlayerUtils.mc.playerController.field_1064_b = PlayerUtils.mc.playerController.isBeingUsed();
        return PlayerUtils.mc.playerController.isBeingUsed();
    }

    public static float wrapAngle180(float angle) {
        if ((angle %= 360.0f) >= 180.0f) {
            angle -= 360.0f;
        }
        if (angle < -180.0f) {
            angle += 360.0f;
        }
        return angle;
    }

    public static void destroyBlockInstant(int x, int y, int z, int side) {
        if (AutoToolHack.instance.status) {
            PlayerUtils.mc.thePlayer.inventory.currentItem = AutoToolHack.getBestSlot(Block.blocksList[PlayerUtils.mc.theWorld.getBlockId(x, y, z)]);
            if (mc.isMultiplayerWorld()) {
                ((PlayerControllerMP)PlayerUtils.mc.playerController).func_730_e();
            }
        }
        if (mc.isMultiplayerWorld()) {
            PlayerControllerMP mp = (PlayerControllerMP)PlayerUtils.mc.playerController;
            mc.getSendQueue().addToSendQueue(new Packet14BlockDig(0, x, y, z, side));
            mc.getSendQueue().addToSendQueue(new Packet14BlockDig(2, x, y, z, side));
        } else {
            PlayerUtils.mc.playerController.sendBlockRemoved(x, y, z, side);
        }
    }

    public static double getSpeed(boolean horizontal) {
        double diffX = (PlayerUtils.mc.thePlayer.prevPosX - PlayerUtils.mc.thePlayer.posX) * (double)PlayerUtils.mc.timer.ticksPerSecond;
        double diffZ = (PlayerUtils.mc.thePlayer.prevPosZ - PlayerUtils.mc.thePlayer.posZ) * (double)PlayerUtils.mc.timer.ticksPerSecond;
        if (horizontal) {
            double diffY = (PlayerUtils.mc.thePlayer.prevPosY - PlayerUtils.mc.thePlayer.posY) * (double)PlayerUtils.mc.timer.ticksPerSecond;
            return Math.sqrt(diffX * diffX + diffY * diffY + diffZ * diffZ);
        }
        return Math.sqrt(diffX * diffX + diffZ * diffZ);
    }

    public static boolean isInNether() {
        int z;
        int x = MathHelper.floor_double(PlayerUtils.mc.thePlayer.posX);
        return PlayerUtils.mc.theWorld.getBlockId(x, 127, z = MathHelper.floor_double(PlayerUtils.mc.thePlayer.posZ)) == Block.bedrock.blockID;
    }
}

