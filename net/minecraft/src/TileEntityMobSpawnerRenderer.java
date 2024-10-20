/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.src;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityList;
import net.minecraft.src.RenderManager;
import net.minecraft.src.TileEntity;
import net.minecraft.src.TileEntityMobSpawner;
import net.minecraft.src.TileEntitySpecialRenderer;
import net.skidcode.gh.maybeaclient.Client;
import org.lwjgl.opengl.GL11;

public class TileEntityMobSpawnerRenderer
extends TileEntitySpecialRenderer {
    private Map entityHashMap = new HashMap();

    public void renderTileEntityMobSpawner(TileEntityMobSpawner var1, double var2, double var4, double var6, float var8) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)((float)var2 + 0.5f), (float)((float)var4), (float)((float)var6 + 0.5f));
        Entity var9 = (Entity)this.entityHashMap.get(var1.getMobID());
        if (var9 == null) {
            var9 = EntityList.createEntityInWorld(var1.getMobID(), null);
            this.entityHashMap.put(var1.getMobID(), var9);
        }
        if (var9 != null) {
            var9.setWorld(var1.worldObj);
            float var10 = 0.4375f;
            GL11.glTranslatef((float)0.0f, (float)0.4f, (float)0.0f);
            GL11.glRotatef((float)((float)(var1.yaw2 + (var1.yaw - var1.yaw2) * (double)var8) * 10.0f), (float)0.0f, (float)1.0f, (float)0.0f);
            GL11.glRotatef((float)-30.0f, (float)1.0f, (float)0.0f, (float)0.0f);
            GL11.glTranslatef((float)0.0f, (float)-0.4f, (float)0.0f);
            GL11.glScalef((float)var10, (float)var10, (float)var10);
            var9.setLocationAndAngles(var2, var4, var6, 0.0f, 0.0f);
            boolean debugEnabled = Minecraft.isDebugInfoEnabled();
            Client.mc.gameSettings.showDebugInfo = false;
            RenderManager.instance.renderEntityWithPosYaw(var9, 0.0, 0.0, 0.0, 0.0f, var8);
            Client.mc.gameSettings.showDebugInfo = debugEnabled;
        }
        GL11.glPopMatrix();
    }

    @Override
    public void renderTileEntityAt(TileEntity var1, double var2, double var4, double var6, float var8) {
        this.renderTileEntityMobSpawner((TileEntityMobSpawner)var1, var2, var4, var6, var8);
    }
}

