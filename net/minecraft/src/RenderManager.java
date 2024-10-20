/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.src;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityArrow;
import net.minecraft.src.EntityBoat;
import net.minecraft.src.EntityChicken;
import net.minecraft.src.EntityCow;
import net.minecraft.src.EntityCreeper;
import net.minecraft.src.EntityEgg;
import net.minecraft.src.EntityFallingSand;
import net.minecraft.src.EntityFireball;
import net.minecraft.src.EntityFish;
import net.minecraft.src.EntityGhast;
import net.minecraft.src.EntityItem;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityMinecart;
import net.minecraft.src.EntityPainting;
import net.minecraft.src.EntityPig;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntitySheep;
import net.minecraft.src.EntitySkeleton;
import net.minecraft.src.EntitySlime;
import net.minecraft.src.EntitySnowball;
import net.minecraft.src.EntitySpider;
import net.minecraft.src.EntitySquid;
import net.minecraft.src.EntityTNTPrimed;
import net.minecraft.src.EntityWolf;
import net.minecraft.src.EntityZombie;
import net.minecraft.src.EntityZombieSimple;
import net.minecraft.src.FontRenderer;
import net.minecraft.src.GameSettings;
import net.minecraft.src.Item;
import net.minecraft.src.ItemRenderer;
import net.minecraft.src.ModelBiped;
import net.minecraft.src.ModelChicken;
import net.minecraft.src.ModelCow;
import net.minecraft.src.ModelPig;
import net.minecraft.src.ModelSheep1;
import net.minecraft.src.ModelSheep2;
import net.minecraft.src.ModelSkeleton;
import net.minecraft.src.ModelSlime;
import net.minecraft.src.ModelSquid;
import net.minecraft.src.ModelWolf;
import net.minecraft.src.ModelZombie;
import net.minecraft.src.Render;
import net.minecraft.src.RenderArrow;
import net.minecraft.src.RenderBiped;
import net.minecraft.src.RenderBoat;
import net.minecraft.src.RenderChicken;
import net.minecraft.src.RenderCow;
import net.minecraft.src.RenderCreeper;
import net.minecraft.src.RenderEngine;
import net.minecraft.src.RenderEntity;
import net.minecraft.src.RenderFallingSand;
import net.minecraft.src.RenderFireball;
import net.minecraft.src.RenderFish;
import net.minecraft.src.RenderGhast;
import net.minecraft.src.RenderItem;
import net.minecraft.src.RenderLiving;
import net.minecraft.src.RenderMinecart;
import net.minecraft.src.RenderPainting;
import net.minecraft.src.RenderPig;
import net.minecraft.src.RenderPlayer;
import net.minecraft.src.RenderSheep;
import net.minecraft.src.RenderSlime;
import net.minecraft.src.RenderSnowball;
import net.minecraft.src.RenderSpider;
import net.minecraft.src.RenderSquid;
import net.minecraft.src.RenderTNTPrimed;
import net.minecraft.src.RenderWolf;
import net.minecraft.src.RenderZombieSimple;
import net.minecraft.src.World;
import net.skidcode.gh.maybeaclient.hacks.NoRenderHack;
import org.lwjgl.opengl.GL11;

public class RenderManager {
    private Map entityRenderMap = new HashMap();
    public static RenderManager instance = new RenderManager();
    private FontRenderer fontRenderer;
    public static double renderPosX;
    public static double renderPosY;
    public static double renderPosZ;
    public RenderEngine renderEngine;
    public ItemRenderer itemRenderer;
    public World worldObj;
    public EntityLiving livingPlayer;
    public float playerViewY;
    public float playerViewX;
    public GameSettings options;
    public double renderPositionX;
    public double renderPositionY;
    public double renderPositionZ;

    private RenderManager() {
        this.entityRenderMap.put(EntitySpider.class, new RenderSpider());
        this.entityRenderMap.put(EntityPig.class, new RenderPig(new ModelPig(), new ModelPig(0.5f), 0.7f));
        this.entityRenderMap.put(EntitySheep.class, new RenderSheep(new ModelSheep2(), new ModelSheep1(), 0.7f));
        this.entityRenderMap.put(EntityCow.class, new RenderCow(new ModelCow(), 0.7f));
        this.entityRenderMap.put(EntityWolf.class, new RenderWolf(new ModelWolf(), 0.5f));
        this.entityRenderMap.put(EntityChicken.class, new RenderChicken(new ModelChicken(), 0.3f));
        this.entityRenderMap.put(EntityCreeper.class, new RenderCreeper());
        this.entityRenderMap.put(EntitySkeleton.class, new RenderBiped(new ModelSkeleton(), 0.5f));
        this.entityRenderMap.put(EntityZombie.class, new RenderBiped(new ModelZombie(), 0.5f));
        this.entityRenderMap.put(EntitySlime.class, new RenderSlime(new ModelSlime(16), new ModelSlime(0), 0.25f));
        this.entityRenderMap.put(EntityPlayer.class, new RenderPlayer());
        this.entityRenderMap.put(EntityZombieSimple.class, new RenderZombieSimple(new ModelZombie(), 0.5f, 6.0f));
        this.entityRenderMap.put(EntityGhast.class, new RenderGhast());
        this.entityRenderMap.put(EntitySquid.class, new RenderSquid(new ModelSquid(), 0.7f));
        this.entityRenderMap.put(EntityLiving.class, new RenderLiving(new ModelBiped(), 0.5f));
        this.entityRenderMap.put(Entity.class, new RenderEntity());
        this.entityRenderMap.put(EntityPainting.class, new RenderPainting());
        this.entityRenderMap.put(EntityArrow.class, new RenderArrow());
        this.entityRenderMap.put(EntitySnowball.class, new RenderSnowball(Item.snowball.getIconIndex(null)));
        this.entityRenderMap.put(EntityEgg.class, new RenderSnowball(Item.egg.getIconIndex(null)));
        this.entityRenderMap.put(EntityFireball.class, new RenderFireball());
        this.entityRenderMap.put(EntityItem.class, new RenderItem());
        this.entityRenderMap.put(EntityTNTPrimed.class, new RenderTNTPrimed());
        this.entityRenderMap.put(EntityFallingSand.class, new RenderFallingSand());
        this.entityRenderMap.put(EntityMinecart.class, new RenderMinecart());
        this.entityRenderMap.put(EntityBoat.class, new RenderBoat());
        this.entityRenderMap.put(EntityFish.class, new RenderFish());
        for (Render var2 : this.entityRenderMap.values()) {
            var2.setRenderManager(this);
        }
    }

    public Render getEntityClassRenderObject(Class var1) {
        Render var2 = (Render)this.entityRenderMap.get(var1);
        if (var2 == null && var1 != Entity.class) {
            var2 = this.getEntityClassRenderObject(var1.getSuperclass());
            this.entityRenderMap.put(var1, var2);
        }
        return var2;
    }

    public Render getEntityRenderObject(Entity var1) {
        return this.getEntityClassRenderObject(var1.getClass());
    }

    public void cacheActiveRenderInfo(World var1, RenderEngine var2, FontRenderer var3, EntityLiving var4, GameSettings var5, float var6) {
        this.worldObj = var1;
        this.renderEngine = var2;
        this.options = var5;
        this.livingPlayer = var4;
        this.fontRenderer = var3;
        this.playerViewY = var4.prevRotationYaw + (var4.rotationYaw - var4.prevRotationYaw) * var6;
        this.playerViewX = var4.prevRotationPitch + (var4.rotationPitch - var4.prevRotationPitch) * var6;
        this.renderPositionX = var4.lastTickPosX + (var4.posX - var4.lastTickPosX) * (double)var6;
        this.renderPositionY = var4.lastTickPosY + (var4.posY - var4.lastTickPosY) * (double)var6;
        this.renderPositionZ = var4.lastTickPosZ + (var4.posZ - var4.lastTickPosZ) * (double)var6;
    }

    public void renderEntity(Entity var1, float var2) {
        double var3 = var1.lastTickPosX + (var1.posX - var1.lastTickPosX) * (double)var2;
        double var5 = var1.lastTickPosY + (var1.posY - var1.lastTickPosY) * (double)var2;
        double var7 = var1.lastTickPosZ + (var1.posZ - var1.lastTickPosZ) * (double)var2;
        float var9 = var1.prevRotationYaw + (var1.rotationYaw - var1.prevRotationYaw) * var2;
        float var10 = var1.getEntityBrightness(var2);
        GL11.glColor3f((float)var10, (float)var10, (float)var10);
        this.renderEntityWithPosYaw(var1, var3 - renderPosX, var5 - renderPosY, var7 - renderPosZ, var9, var2);
    }

    public void renderEntityWithPosYaw(Entity var1, double var2, double var4, double var6, float var8, float var9) {
        if (var1 instanceof EntityItem && NoRenderHack.instance.status && NoRenderHack.instance.itemEntities.value) {
            return;
        }
        Render var10 = this.getEntityRenderObject(var1);
        if (var10 != null) {
            var10.doRender(var1, var2, var4, var6, var8, var9);
            var10.doRenderShadowAndFire(var1, var2, var4, var6, var8, var9);
        }
    }

    public void func_852_a(World var1) {
        this.worldObj = var1;
    }

    public double func_851_a(double var1, double var3, double var5) {
        double var7 = var1 - this.renderPositionX;
        double var9 = var3 - this.renderPositionY;
        double var11 = var5 - this.renderPositionZ;
        return var7 * var7 + var9 * var9 + var11 * var11;
    }

    public FontRenderer getFontRenderer() {
        return this.fontRenderer;
    }
}

