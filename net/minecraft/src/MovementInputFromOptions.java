/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.GameSettings;
import net.minecraft.src.MovementInput;
import net.skidcode.gh.maybeaclient.Client;

public class MovementInputFromOptions
extends MovementInput {
    private boolean[] movementKeyStates = new boolean[10];
    private GameSettings gameSettings;

    public MovementInputFromOptions(GameSettings var1) {
        this.gameSettings = var1;
    }

    @Override
    public void checkKeyForMovementInput(int var1, boolean var2) {
        int var3 = -1;
        if (var1 == this.gameSettings.keyBindForward.keyCode) {
            var3 = 0;
        }
        if (var1 == this.gameSettings.keyBindBack.keyCode) {
            var3 = 1;
        }
        if (var1 == this.gameSettings.keyBindLeft.keyCode) {
            var3 = 2;
        }
        if (var1 == this.gameSettings.keyBindRight.keyCode) {
            var3 = 3;
        }
        if (var1 == this.gameSettings.keyBindJump.keyCode) {
            var3 = 4;
        }
        if (var1 == this.gameSettings.keyBindSneak.keyCode) {
            var3 = 5;
        }
        if (Client.mc.inGameHasFocus) {
            Client.onKeyPress(var1, var2);
        }
        if (var3 >= 0) {
            this.movementKeyStates[var3] = var2;
        }
    }

    @Override
    public void resetKeyState() {
        int var1 = 0;
        while (var1 < 10) {
            this.movementKeyStates[var1] = false;
            ++var1;
        }
    }

    @Override
    public void updatePlayerMoveState(EntityPlayer var1) {
        this.moveStrafe = 0.0f;
        this.moveForward = 0.0f;
        if (this.movementKeyStates[0]) {
            this.moveForward += 1.0f;
        }
        if (this.movementKeyStates[1]) {
            this.moveForward -= 1.0f;
        }
        if (this.movementKeyStates[2]) {
            this.moveStrafe += 1.0f;
        }
        if (this.movementKeyStates[3]) {
            this.moveStrafe -= 1.0f;
        }
        this.jump = this.movementKeyStates[4];
        this.sneak = this.movementKeyStates[5];
        if (this.sneak) {
            this.moveStrafe = (float)((double)this.moveStrafe * 0.3);
            this.moveForward = (float)((double)this.moveForward * 0.3);
        }
    }
}

