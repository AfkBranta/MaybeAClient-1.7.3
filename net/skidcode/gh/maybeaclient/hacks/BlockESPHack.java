/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.skidcode.gh.maybeaclient.hacks;

import java.util.ArrayList;
import java.util.HashSet;
import net.minecraft.src.RenderManager;
import net.minecraft.src.Tessellator;
import net.skidcode.gh.maybeaclient.events.Event;
import net.skidcode.gh.maybeaclient.events.EventListener;
import net.skidcode.gh.maybeaclient.events.EventRegistry;
import net.skidcode.gh.maybeaclient.events.impl.EventWorldRenderPreFog;
import net.skidcode.gh.maybeaclient.hacks.Hack;
import net.skidcode.gh.maybeaclient.hacks.category.Category;
import net.skidcode.gh.maybeaclient.hacks.settings.SettingBlockChooser;
import net.skidcode.gh.maybeaclient.hacks.settings.SettingColor;
import net.skidcode.gh.maybeaclient.hacks.settings.SettingFloat;
import net.skidcode.gh.maybeaclient.utils.BlockPos;
import org.lwjgl.opengl.GL11;

public class BlockESPHack
extends Hack
implements EventListener {
    public static HashSet<BlockPos> blocksToRender = new HashSet();
    public static ArrayList<BlockPos> removed = new ArrayList();
    public static BlockESPHack instance;
    public SettingColor color = new SettingColor(this, "Color", 255, 0, 0);
    public SettingFloat width = new SettingFloat(this, "Line Width", 1.0f, 1.0f, 5.0f, 0.1f);
    public SettingBlockChooser blocks = new SettingBlockChooser(this, "Blocks", new int[0]){

        @Override
        public void blockChanged(int id) {
            BlockESPHack.mc.entityRenderer.updateRenderer();
            BlockESPHack.mc.theWorld.markBlocksDirty((int)BlockESPHack.mc.thePlayer.posX - 256, 0, (int)BlockESPHack.mc.thePlayer.posZ - 256, (int)BlockESPHack.mc.thePlayer.posX + 256, 127, (int)BlockESPHack.mc.thePlayer.posZ + 256);
        }
    };

    public BlockESPHack() {
        super("BlockESP", "Outlines blocks", 0, Category.RENDER);
        instance = this;
        this.addSetting(this.blocks);
        this.addSetting(this.color);
        this.addSetting(this.width);
        EventRegistry.registerListener(EventWorldRenderPreFog.class, this);
    }

    public void handleEvent(Event event) {
        if (event instanceof EventWorldRenderPreFog) {
            Tessellator tess = Tessellator.instance;
            GL11.glPushMatrix();
            GL11.glBlendFunc((int)770, (int)771);
            GL11.glColor3f((float)((float)this.color.red / 255.0f), (float)((float)this.color.green / 255.0f), (float)((float)this.color.blue / 255.0f));
            GL11.glLineWidth((float)this.width.value);
            GL11.glDisable((int)3553);
            GL11.glDisable((int)2929);
            GL11.glDepthMask((boolean)false);
            for (BlockPos pos : blocksToRender) {
                int id = BlockESPHack.mc.theWorld.getBlockId(pos.x, pos.y, pos.z);
                if (this.blocks.blocks[id]) {
                    double renderX = (double)pos.x - RenderManager.renderPosX;
                    double renderY = (double)pos.y - RenderManager.renderPosY;
                    double renderZ = (double)pos.z - RenderManager.renderPosZ;
                    tess.startDrawing(3);
                    tess.addVertex(renderX, renderY, renderZ);
                    tess.addVertex(renderX + 1.0, renderY, renderZ);
                    tess.addVertex(renderX + 1.0, renderY, renderZ + 1.0);
                    tess.addVertex(renderX, renderY, renderZ + 1.0);
                    tess.addVertex(renderX, renderY, renderZ);
                    tess.draw();
                    tess.startDrawing(3);
                    tess.addVertex(renderX, renderY + 1.0, renderZ);
                    tess.addVertex(renderX + 1.0, renderY + 1.0, renderZ);
                    tess.addVertex(renderX + 1.0, renderY + 1.0, renderZ + 1.0);
                    tess.addVertex(renderX, renderY + 1.0, renderZ + 1.0);
                    tess.addVertex(renderX, renderY + 1.0, renderZ);
                    tess.draw();
                    tess.startDrawing(1);
                    tess.addVertex(renderX, renderY, renderZ);
                    tess.addVertex(renderX, renderY + 1.0, renderZ);
                    tess.addVertex(renderX + 1.0, renderY, renderZ);
                    tess.addVertex(renderX + 1.0, renderY + 1.0, renderZ);
                    tess.addVertex(renderX + 1.0, renderY, renderZ + 1.0);
                    tess.addVertex(renderX + 1.0, renderY + 1.0, renderZ + 1.0);
                    tess.addVertex(renderX, renderY, renderZ + 1.0);
                    tess.addVertex(renderX, renderY + 1.0, renderZ + 1.0);
                    tess.draw();
                    continue;
                }
                removed.add(pos);
            }
            int i = removed.size();
            while (--i >= 0) {
                BlockPos pos = removed.remove(i);
                blocksToRender.remove(pos);
            }
            GL11.glDepthMask((boolean)true);
            GL11.glEnable((int)3553);
            GL11.glEnable((int)2929);
            GL11.glPopMatrix();
        }
    }
}

