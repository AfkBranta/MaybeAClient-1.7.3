/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Keyboard
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.src;

import net.minecraft.src.Block;
import net.minecraft.src.FontAllowedCharacters;
import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.Packet130;
import net.minecraft.src.TileEntityRenderer;
import net.minecraft.src.TileEntitySign;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class GuiEditSign
extends GuiScreen {
    protected String screenTitle = "Edit sign message:";
    private TileEntitySign entitySign;
    private int updateCounter;
    private int editLine = 0;
    private static final String allowedCharacters = FontAllowedCharacters.allowedCharacters;

    public GuiEditSign(TileEntitySign var1) {
        this.entitySign = var1;
    }

    @Override
    public void initGui() {
        this.controlList.clear();
        Keyboard.enableRepeatEvents((boolean)true);
        this.controlList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 120, "Done"));
    }

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents((boolean)false);
        if (this.mc.theWorld.multiplayerWorld) {
            this.mc.getSendQueue().addToSendQueue(new Packet130(this.entitySign.xCoord, this.entitySign.yCoord, this.entitySign.zCoord, this.entitySign.signText));
        }
    }

    @Override
    public void updateScreen() {
        ++this.updateCounter;
    }

    @Override
    protected void actionPerformed(GuiButton var1) {
        if (var1.enabled && var1.id == 0) {
            this.entitySign.x_();
            this.mc.displayGuiScreen(null);
        }
    }

    @Override
    protected void keyTyped(char var1, int var2) {
        if (var2 == 200) {
            this.editLine = this.editLine - 1 & 3;
        }
        if (var2 == 208 || var2 == 28) {
            this.editLine = this.editLine + 1 & 3;
        }
        if (var2 == 14 && this.entitySign.signText[this.editLine].length() > 0) {
            this.entitySign.signText[this.editLine] = this.entitySign.signText[this.editLine].substring(0, this.entitySign.signText[this.editLine].length() - 1);
        }
        if (allowedCharacters.indexOf(var1) >= 0 && this.entitySign.signText[this.editLine].length() < 15) {
            StringBuilder var10000 = new StringBuilder();
            String[] var10002 = this.entitySign.signText;
            int var10004 = this.editLine;
            var10002[var10004] = var10000.append(var10002[var10004]).append(var1).toString();
        }
    }

    @Override
    public void drawScreen(int var1, int var2, float var3) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRenderer, this.screenTitle, this.width / 2, 40, 0xFFFFFF);
        GL11.glPushMatrix();
        GL11.glTranslatef((float)(this.width / 2), (float)(this.height / 2), (float)50.0f);
        float var4 = 93.75f;
        GL11.glScalef((float)(-var4), (float)(-var4), (float)(-var4));
        GL11.glRotatef((float)180.0f, (float)0.0f, (float)1.0f, (float)0.0f);
        Block var5 = this.entitySign.getBlockType();
        if (var5 == Block.signPost) {
            float var6 = (float)(this.entitySign.getBlockMetadata() * 360) / 16.0f;
            GL11.glRotatef((float)var6, (float)0.0f, (float)1.0f, (float)0.0f);
            GL11.glTranslatef((float)0.0f, (float)0.3125f, (float)0.0f);
        } else {
            int var8 = this.entitySign.getBlockMetadata();
            float var7 = 0.0f;
            if (var8 == 2) {
                var7 = 180.0f;
            }
            if (var8 == 4) {
                var7 = 90.0f;
            }
            if (var8 == 5) {
                var7 = -90.0f;
            }
            GL11.glRotatef((float)var7, (float)0.0f, (float)1.0f, (float)0.0f);
            GL11.glTranslatef((float)0.0f, (float)0.3125f, (float)0.0f);
        }
        if (this.updateCounter / 6 % 2 == 0) {
            this.entitySign.lineBeingEdited = this.editLine;
        }
        TileEntityRenderer.instance.renderTileEntityAt(this.entitySign, -0.5, -0.75, -0.5, 0.0f);
        this.entitySign.lineBeingEdited = -1;
        GL11.glPopMatrix();
        super.drawScreen(var1, var2, var3);
    }
}

