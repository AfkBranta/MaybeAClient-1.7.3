/*
 * Decompiled with CFR 0.152.
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
import net.minecraft.src.EntityFallingSand;
import net.minecraft.src.EntityGhast;
import net.minecraft.src.EntityItem;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityMinecart;
import net.minecraft.src.EntityMobs;
import net.minecraft.src.EntityPainting;
import net.minecraft.src.EntityPig;
import net.minecraft.src.EntityPigZombie;
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
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.World;

public class EntityList {
    private static Map stringToClassMapping = new HashMap();
    private static Map classToStringMapping = new HashMap();
    private static Map IDtoClassMapping = new HashMap();
    private static Map classToIDMapping = new HashMap();

    static {
        EntityList.addMapping(EntityArrow.class, "Arrow", 10);
        EntityList.addMapping(EntitySnowball.class, "Snowball", 11);
        EntityList.addMapping(EntityItem.class, "Item", 1);
        EntityList.addMapping(EntityPainting.class, "Painting", 9);
        EntityList.addMapping(EntityLiving.class, "Mob", 48);
        EntityList.addMapping(EntityMobs.class, "Monster", 49);
        EntityList.addMapping(EntityCreeper.class, "Creeper", 50);
        EntityList.addMapping(EntitySkeleton.class, "Skeleton", 51);
        EntityList.addMapping(EntitySpider.class, "Spider", 52);
        EntityList.addMapping(EntityZombieSimple.class, "Giant", 53);
        EntityList.addMapping(EntityZombie.class, "Zombie", 54);
        EntityList.addMapping(EntitySlime.class, "Slime", 55);
        EntityList.addMapping(EntityGhast.class, "Ghast", 56);
        EntityList.addMapping(EntityPigZombie.class, "PigZombie", 57);
        EntityList.addMapping(EntityPig.class, "Pig", 90);
        EntityList.addMapping(EntitySheep.class, "Sheep", 91);
        EntityList.addMapping(EntityCow.class, "Cow", 92);
        EntityList.addMapping(EntityChicken.class, "Chicken", 93);
        EntityList.addMapping(EntitySquid.class, "Squid", 94);
        EntityList.addMapping(EntityWolf.class, "Wolf", 95);
        EntityList.addMapping(EntityTNTPrimed.class, "PrimedTnt", 20);
        EntityList.addMapping(EntityFallingSand.class, "FallingSand", 21);
        EntityList.addMapping(EntityMinecart.class, "Minecart", 40);
        EntityList.addMapping(EntityBoat.class, "Boat", 41);
    }

    private static void addMapping(Class var0, String var1, int var2) {
        stringToClassMapping.put(var1, var0);
        classToStringMapping.put(var0, var1);
        IDtoClassMapping.put(var2, var0);
        classToIDMapping.put(var0, var2);
    }

    public static Entity createEntityInWorld(String var0, World var1) {
        Entity var2 = null;
        try {
            Class var3 = (Class)stringToClassMapping.get(var0);
            if (var3 != null) {
                var2 = (Entity)var3.getConstructor(World.class).newInstance(var1);
            }
        }
        catch (Exception var4) {
            var4.printStackTrace();
        }
        return var2;
    }

    public static Entity createEntityFromNBT(NBTTagCompound var0, World var1) {
        Entity var2 = null;
        try {
            Class var3 = (Class)stringToClassMapping.get(var0.getString("id"));
            if (var3 != null) {
                var2 = (Entity)var3.getConstructor(World.class).newInstance(var1);
            }
        }
        catch (Exception var4) {
            var4.printStackTrace();
        }
        if (var2 != null) {
            var2.readFromNBT(var0);
        } else {
            System.out.println("Skipping Entity with id " + var0.getString("id"));
        }
        return var2;
    }

    public static Entity createEntity(int var0, World var1) {
        Entity var2 = null;
        try {
            Class var3 = (Class)IDtoClassMapping.get(var0);
            if (var3 != null) {
                var2 = (Entity)var3.getConstructor(World.class).newInstance(var1);
            }
        }
        catch (Exception var4) {
            var4.printStackTrace();
        }
        if (var2 == null) {
            System.out.println("Skipping Entity with id " + var0);
        }
        return var2;
    }

    public static int getEntityID(Entity var0) {
        return (Integer)classToIDMapping.get(var0.getClass());
    }

    public static String getEntityString(Entity var0) {
        return (String)classToStringMapping.get(var0.getClass());
    }
}

