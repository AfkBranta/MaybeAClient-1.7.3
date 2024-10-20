package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.src.ChunkCoordinates;
import net.minecraft.src.ItemStack;
import net.minecraft.src.WatchableObject;

public class DataWatcher {
    private static final HashMap dataTypes = new HashMap();
    private final Map watchedObjects = new HashMap();
    private boolean objectChanged;

    static {
        dataTypes.put(Byte.class, 0);
        dataTypes.put(Short.class, 1);
        dataTypes.put(Integer.class, 2);
        dataTypes.put(Float.class, 3);
        dataTypes.put(String.class, 4);
        dataTypes.put(ItemStack.class, 5);
        dataTypes.put(ChunkCoordinates.class, 6);
    }

    public void addObject(int var1, Object var2) {
        Integer var3 = (Integer)dataTypes.get(var2.getClass());
        if (var3 == null) {
            throw new IllegalArgumentException("Unknown data type: " + var2.getClass());
        }
        if (var1 > 31) {
            throw new IllegalArgumentException("Data value id is too big with " + var1 + "! (Max is " + 31 + ")");
        }
        if (this.watchedObjects.containsKey(var1)) {
            throw new IllegalArgumentException("Duplicate id value for " + var1 + "!");
        }
        WatchableObject var4 = new WatchableObject(var3, var1, var2);
        this.watchedObjects.put(var1, var4);
    }

    public byte getWatchableObjectByte(int var1) {
        return (Byte)((WatchableObject)this.watchedObjects.get(var1)).getObject();
    }

    public int func_25115_b(int var1) {
        return (Integer)((WatchableObject)this.watchedObjects.get(var1)).getObject();
    }

    public String func_25116_c(int var1) {
        return (String)((WatchableObject)this.watchedObjects.get(var1)).getObject();
    }

    public void updateObject(int var1, Object var2) {
        WatchableObject var3 = (WatchableObject)this.watchedObjects.get(var1);
        if (!var2.equals(var3.getObject())) {
            var3.setObject(var2);
            var3.setWatching(true);
            this.objectChanged = true;
        }
    }

    public static void writeObjectsInListToStream(List var0, DataOutputStream var1) throws IOException {
        if (var0 != null) {
            for (WatchableObject var3 : var0) {
                DataWatcher.writeWatchableObject(var1, var3);
            }
        }
        var1.writeByte(127);
    }

    public void writeWatchableObjects(DataOutputStream var1) throws IOException {
        for (WatchableObject var3 : this.watchedObjects.values()) {
            DataWatcher.writeWatchableObject(var1, var3);
        }
        var1.writeByte(127);
    }

    private static void writeWatchableObject(DataOutputStream var0, WatchableObject var1) throws IOException {
        int var2 = (var1.getObjectType() << 5 | var1.getDataValueId() & 0x1F) & 0xFF;
        var0.writeByte(var2);
        switch (var1.getObjectType()) {
            case 0: {
                var0.writeByte(((Byte)var1.getObject()).byteValue());
                break;
            }
            case 1: {
                var0.writeShort(((Short)var1.getObject()).shortValue());
                break;
            }
            case 2: {
                var0.writeInt((Integer)var1.getObject());
                break;
            }
            case 3: {
                var0.writeFloat(((Float)var1.getObject()).floatValue());
                break;
            }
            case 4: {
                var0.writeUTF((String)var1.getObject());
                break;
            }
            case 5: {
                ItemStack var3 = (ItemStack)var1.getObject();
                var0.writeShort(var3.getItem().shiftedIndex);
                var0.writeByte(var3.stackSize);
                var0.writeShort(var3.getItemDamage());
            }
            case 6: {
                ChunkCoordinates var4 = (ChunkCoordinates)var1.getObject();
                var0.writeInt(var4.x);
                var0.writeInt(var4.y);
                var0.writeInt(var4.z);
            }
        }
    }

    public static List readWatchableObjects(DataInputStream var0) throws IOException {
        ArrayList<WatchableObject> var1 = null;
        byte var2 = var0.readByte();
        while (var2 != 127) {
            if (var1 == null) {
                var1 = new ArrayList<WatchableObject>();
            }
            int var3 = (var2 & 0xE0) >> 5;
            int var4 = var2 & 0x1F;
            WatchableObject var5 = null;
            switch (var3) {
                case 0: {
                    var5 = new WatchableObject(var3, var4, var0.readByte());
                    break;
                }
                case 1: {
                    var5 = new WatchableObject(var3, var4, var0.readShort());
                    break;
                }
                case 2: {
                    var5 = new WatchableObject(var3, var4, var0.readInt());
                    break;
                }
                case 3: {
                    var5 = new WatchableObject(var3, var4, Float.valueOf(var0.readFloat()));
                    break;
                }
                case 4: {
                    var5 = new WatchableObject(var3, var4, var0.readUTF());
                    break;
                }
                case 5: {
                    short var6 = var0.readShort();
                    byte var7 = var0.readByte();
                    short var8 = var0.readShort();
                    new WatchableObject(var3, var4, new ItemStack(var6, (int)var7, (int)var8));
                }
                case 6: {
                    int var9 = var0.readInt();
                    int var10 = var0.readInt();
                    int var11 = var0.readInt();
                    var5 = new WatchableObject(var3, var4, new ChunkCoordinates(var9, var10, var11));
                }
            }
            var1.add(var5);
            var2 = var0.readByte();
        }
        return var1;
    }

    public void updateWatchedObjectsFromList(List var1) {
        for (WatchableObject var3 : var1) {
            WatchableObject var4 = (WatchableObject)this.watchedObjects.get(var3.getDataValueId());
            if (var4 == null) continue;
            var4.setObject(var3.getObject());
        }
    }
}

