/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.Iterator;
import java.util.List;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Block;
import net.minecraft.src.BlockBed;
import net.minecraft.src.ChunkCoordinates;
import net.minecraft.src.CraftingInventoryCB;
import net.minecraft.src.CraftingInventoryPlayerCB;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityArrow;
import net.minecraft.src.EntityCreeper;
import net.minecraft.src.EntityFish;
import net.minecraft.src.EntityGhast;
import net.minecraft.src.EntityItem;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityMobs;
import net.minecraft.src.EntityWolf;
import net.minecraft.src.EnumStatus;
import net.minecraft.src.IChunkProvider;
import net.minecraft.src.IInventory;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.MathHelper;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.NBTTagList;
import net.minecraft.src.StatBasic;
import net.minecraft.src.StatList;
import net.minecraft.src.TileEntityDispenser;
import net.minecraft.src.TileEntityFurnace;
import net.minecraft.src.TileEntitySign;
import net.minecraft.src.World;
import net.skidcode.gh.maybeaclient.hacks.FreecamHack;

public abstract class EntityPlayer
extends EntityLiving {
    public InventoryPlayer inventory = new InventoryPlayer(this);
    public CraftingInventoryCB inventorySlots;
    public CraftingInventoryCB craftingInventory;
    public byte field_9371_f = 0;
    public int score = 0;
    public float field_775_e;
    public float field_774_f;
    public boolean isSwinging = false;
    public int swingProgressInt = 0;
    public String username;
    public int dimension;
    public String playerCloakUrl;
    public double field_20066_r;
    public double field_20065_s;
    public double field_20064_t;
    public double field_20063_u;
    public double field_20062_v;
    public double field_20061_w;
    private boolean sleeping;
    private ChunkCoordinates bedChunkCoordinates;
    private int sleepTimer;
    public float field_22063_x;
    public float field_22062_y;
    public float field_22061_z;
    private ChunkCoordinates playerSpawnCoordinate;
    private int damageRemainder = 0;
    public EntityFish fishEntity = null;

    public EntityPlayer(World var1) {
        super(var1);
        this.craftingInventory = this.inventorySlots = new CraftingInventoryPlayerCB(this.inventory, !var1.multiplayerWorld);
        this.yOffset = 1.62f;
        ChunkCoordinates var2 = var1.getSpawnPoint();
        this.setLocationAndAngles((double)var2.x + 0.5, var2.y + 1, (double)var2.z + 0.5, 0.0f, 0.0f);
        this.health = 20;
        this.field_9351_C = "humanoid";
        this.field_9353_B = 180.0f;
        this.fireResistance = 20;
        this.texture = "/mob/char.png";
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(16, (byte)0);
    }

    @Override
    public void onUpdate() {
        if (this.isPlayerSleeping()) {
            ++this.sleepTimer;
            if (this.sleepTimer > 100) {
                this.sleepTimer = 100;
            }
            if (!this.isInBed()) {
                this.wakeUpPlayer(true, true, false);
            } else if (!this.worldObj.multiplayerWorld && this.worldObj.isDaytime()) {
                this.wakeUpPlayer(false, true, true);
            }
        } else if (this.sleepTimer > 0) {
            ++this.sleepTimer;
            if (this.sleepTimer >= 110) {
                this.sleepTimer = 0;
            }
        }
        super.onUpdate();
        if (!this.worldObj.multiplayerWorld && this.craftingInventory != null && !this.craftingInventory.isUsableByPlayer(this)) {
            this.func_20059_m();
            this.craftingInventory = this.inventorySlots;
        }
        this.field_20066_r = this.field_20063_u;
        this.field_20065_s = this.field_20062_v;
        this.field_20064_t = this.field_20061_w;
        double var1 = this.posX - this.field_20063_u;
        double var3 = this.posY - this.field_20062_v;
        double var5 = this.posZ - this.field_20061_w;
        double var7 = 10.0;
        if (var1 > var7) {
            this.field_20066_r = this.field_20063_u = this.posX;
        }
        if (var5 > var7) {
            this.field_20064_t = this.field_20061_w = this.posZ;
        }
        if (var3 > var7) {
            this.field_20065_s = this.field_20062_v = this.posY;
        }
        if (var1 < -var7) {
            this.field_20066_r = this.field_20063_u = this.posX;
        }
        if (var5 < -var7) {
            this.field_20064_t = this.field_20061_w = this.posZ;
        }
        if (var3 < -var7) {
            this.field_20065_s = this.field_20062_v = this.posY;
        }
        this.field_20063_u += var1 * 0.25;
        this.field_20061_w += var5 * 0.25;
        this.field_20062_v += var3 * 0.25;
        this.addStat(StatList.field_25179_j, 1);
    }

    @Override
    protected boolean isMovementBlocked() {
        return this.health <= 0 || this.isPlayerSleeping();
    }

    protected void func_20059_m() {
        this.craftingInventory = this.inventorySlots;
    }

    @Override
    public void updateCloak() {
        this.cloakUrl = this.playerCloakUrl = "http://s3.amazonaws.com/MinecraftCloaks/" + this.username + ".png";
    }

    @Override
    public void updateRidden() {
        super.updateRidden();
        this.field_775_e = this.field_774_f;
        this.field_774_f = 0.0f;
    }

    @Override
    public void preparePlayerToSpawn() {
        this.yOffset = 1.62f;
        this.setSize(0.6f, 1.8f);
        super.preparePlayerToSpawn();
        this.health = 20;
        this.deathTime = 0;
    }

    @Override
    protected void updatePlayerActionState() {
        if (this.isSwinging) {
            ++this.swingProgressInt;
            if (this.swingProgressInt == 8) {
                this.swingProgressInt = 0;
                this.isSwinging = false;
            }
        } else {
            this.swingProgressInt = 0;
        }
        this.swingProgress = (float)this.swingProgressInt / 8.0f;
    }

    @Override
    public void onLivingUpdate() {
        List var3;
        if (this.worldObj.difficultySetting == 0 && this.health < 20 && this.ticksExisted % 20 * 12 == 0) {
            this.heal(1);
        }
        this.inventory.decrementAnimations();
        this.field_775_e = this.field_774_f;
        super.onLivingUpdate();
        float var1 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
        float var2 = (float)Math.atan(-this.motionY * (double)0.2f) * 15.0f;
        if (var1 > 0.1f) {
            var1 = 0.1f;
        }
        if (!this.onGround || this.health <= 0) {
            var1 = 0.0f;
        }
        if (this.onGround || this.health <= 0) {
            var2 = 0.0f;
        }
        this.field_774_f += (var1 - this.field_774_f) * 0.4f;
        this.field_9328_R += (var2 - this.field_9328_R) * 0.8f;
        if (this.health > 0 && (var3 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(1.0, 0.0, 1.0))) != null) {
            int var4 = 0;
            while (var4 < var3.size()) {
                Entity var5 = (Entity)var3.get(var4);
                if (!var5.isDead) {
                    this.func_451_h(var5);
                }
                ++var4;
            }
        }
    }

    private void func_451_h(Entity var1) {
        var1.onCollideWithPlayer(this);
    }

    public int getScore() {
        return this.score;
    }

    @Override
    public void onDeath(Entity var1) {
        super.onDeath(var1);
        this.setSize(0.2f, 0.2f);
        this.setPosition(this.posX, this.posY, this.posZ);
        this.motionY = 0.1f;
        if (this.username.equals("Notch")) {
            this.dropPlayerItemWithRandomChoice(new ItemStack(Item.appleRed, 1), true);
        }
        this.inventory.dropAllItems();
        if (var1 != null) {
            this.motionX = -MathHelper.cos((this.attackedAtYaw + this.rotationYaw) * (float)Math.PI / 180.0f) * 0.1f;
            this.motionZ = -MathHelper.sin((this.attackedAtYaw + this.rotationYaw) * (float)Math.PI / 180.0f) * 0.1f;
        } else {
            this.motionZ = 0.0;
            this.motionX = 0.0;
        }
        this.yOffset = 0.1f;
        this.addStat(StatList.field_25163_u, 1);
    }

    @Override
    public void addToPlayerScore(Entity var1, int var2) {
        this.score += var2;
        if (var1 instanceof EntityPlayer) {
            this.addStat(StatList.field_25161_w, 1);
        } else {
            this.addStat(StatList.field_25162_v, 1);
        }
    }

    public void dropCurrentItem() {
        this.dropPlayerItemWithRandomChoice(this.inventory.decrStackSize(this.inventory.currentItem, 1), false);
    }

    public void dropPlayerItem(ItemStack var1) {
        this.dropPlayerItemWithRandomChoice(var1, false);
    }

    public void dropPlayerItemWithRandomChoice(ItemStack var1, boolean var2) {
        if (var1 != null) {
            EntityItem var3 = new EntityItem(this.worldObj, this.posX, this.posY - (double)0.3f + (double)this.getEyeHeight(), this.posZ, var1);
            var3.delayBeforeCanPickup = 40;
            float var4 = 0.1f;
            if (var2) {
                float var5 = this.rand.nextFloat() * 0.5f;
                float var6 = this.rand.nextFloat() * (float)Math.PI * 2.0f;
                var3.motionX = -MathHelper.sin(var6) * var5;
                var3.motionZ = MathHelper.cos(var6) * var5;
                var3.motionY = 0.2f;
            } else {
                var4 = 0.3f;
                var3.motionX = -MathHelper.sin(this.rotationYaw / 180.0f * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0f * (float)Math.PI) * var4;
                var3.motionZ = MathHelper.cos(this.rotationYaw / 180.0f * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0f * (float)Math.PI) * var4;
                var3.motionY = -MathHelper.sin(this.rotationPitch / 180.0f * (float)Math.PI) * var4 + 0.1f;
                var4 = 0.02f;
                float var5 = this.rand.nextFloat() * (float)Math.PI * 2.0f;
                var3.motionX += Math.cos(var5) * (double)(var4 *= this.rand.nextFloat());
                var3.motionY += (double)((this.rand.nextFloat() - this.rand.nextFloat()) * 0.1f);
                var3.motionZ += Math.sin(var5) * (double)var4;
            }
            this.joinEntityItemWithWorld(var3);
            this.addStat(StatList.field_25168_r, 1);
        }
    }

    protected void joinEntityItemWithWorld(EntityItem var1) {
        this.worldObj.entityJoinedWorld(var1);
    }

    public float getCurrentPlayerStrVsBlock(Block var1) {
        float var2 = this.inventory.getStrVsBlock(var1);
        if (this.isInsideOfMaterial(Material.water)) {
            var2 /= 5.0f;
        }
        if (!this.onGround) {
            var2 /= 5.0f;
        }
        return var2;
    }

    public boolean canHarvestBlock(Block var1) {
        return this.inventory.canHarvestBlock(var1);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound var1) {
        super.readEntityFromNBT(var1);
        NBTTagList var2 = var1.getTagList("Inventory");
        this.inventory.readFromNBT(var2);
        this.dimension = var1.getInteger("Dimension");
        this.sleeping = var1.getBoolean("Sleeping");
        this.sleepTimer = var1.getShort("SleepTimer");
        if (this.sleeping) {
            this.bedChunkCoordinates = new ChunkCoordinates(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ));
            this.wakeUpPlayer(true, true, false);
        }
        if (var1.hasKey("SpawnX") && var1.hasKey("SpawnY") && var1.hasKey("SpawnZ")) {
            this.playerSpawnCoordinate = new ChunkCoordinates(var1.getInteger("SpawnX"), var1.getInteger("SpawnY"), var1.getInteger("SpawnZ"));
        }
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound var1) {
        super.writeEntityToNBT(var1);
        var1.setTag("Inventory", this.inventory.writeToNBT(new NBTTagList()));
        var1.setInteger("Dimension", this.dimension);
        var1.setBoolean("Sleeping", this.sleeping);
        var1.setShort("SleepTimer", (short)this.sleepTimer);
        if (this.playerSpawnCoordinate != null) {
            var1.setInteger("SpawnX", this.playerSpawnCoordinate.x);
            var1.setInteger("SpawnY", this.playerSpawnCoordinate.y);
            var1.setInteger("SpawnZ", this.playerSpawnCoordinate.z);
        }
    }

    public void displayGUIChest(IInventory var1) {
    }

    public void displayWorkbenchGUI(int var1, int var2, int var3) {
    }

    public void onItemPickup(Entity var1, int var2) {
    }

    @Override
    public float getEyeHeight() {
        return 0.12f;
    }

    protected void resetHeight() {
        this.yOffset = 1.62f;
    }

    @Override
    public boolean attackEntityFrom(Entity var1, int var2) {
        this.field_9344_ag = 0;
        if (this.health <= 0) {
            return false;
        }
        if (this.isPlayerSleeping()) {
            this.wakeUpPlayer(true, true, false);
        }
        if (var1 instanceof EntityMobs || var1 instanceof EntityArrow) {
            if (this.worldObj.difficultySetting == 0) {
                var2 = 0;
            }
            if (this.worldObj.difficultySetting == 1) {
                var2 = var2 / 3 + 1;
            }
            if (this.worldObj.difficultySetting == 3) {
                var2 = var2 * 3 / 2;
            }
        }
        if (var2 == 0) {
            return false;
        }
        Entity var3 = var1;
        if (var1 instanceof EntityArrow && ((EntityArrow)var1).archer != null) {
            var3 = ((EntityArrow)var1).archer;
        }
        if (var3 instanceof EntityLiving) {
            this.alertWolves((EntityLiving)var3, false);
        }
        this.addStat(StatList.field_25165_t, var2);
        return super.attackEntityFrom(var1, var2);
    }

    protected void alertWolves(EntityLiving var1, boolean var2) {
        if (!(var1 instanceof EntityCreeper) && !(var1 instanceof EntityGhast)) {
            EntityWolf var3;
            if (var1 instanceof EntityWolf && (var3 = (EntityWolf)var1).isWolfTamed() && this.username.equals(var3.getWolfOwner())) {
                return;
            }
            List var7 = this.worldObj.getEntitiesWithinAABB(EntityWolf.class, AxisAlignedBB.getBoundingBoxFromPool(this.posX, this.posY, this.posZ, this.posX + 1.0, this.posY + 1.0, this.posZ + 1.0).expand(16.0, 4.0, 16.0));
            Iterator var4 = var7.iterator();
            while (true) {
                if (!var4.hasNext()) {
                    return;
                }
                Entity var5 = (Entity)var4.next();
                EntityWolf var6 = (EntityWolf)var5;
                if (!var6.isWolfTamed() || var6.getTarget() != null || !this.username.equals(var6.getWolfOwner()) || var2 && var6.isWolfSitting()) continue;
                var6.setWolfSitting(false);
                var6.setTarget(var1);
            }
        }
    }

    @Override
    protected void damageEntity(int var1) {
        int var2 = 25 - this.inventory.getTotalArmorValue();
        int var3 = var1 * var2 + this.damageRemainder;
        this.inventory.damageArmor(var1);
        var1 = var3 / 25;
        this.damageRemainder = var3 % 25;
        super.damageEntity(var1);
    }

    public void displayGUIFurnace(TileEntityFurnace var1) {
    }

    public void displayGUIDispenser(TileEntityDispenser var1) {
    }

    public void displayGUIEditSign(TileEntitySign var1) {
    }

    public void useCurrentItemOnEntity(Entity var1) {
        ItemStack var2;
        if (!var1.interact(this) && (var2 = this.getCurrentEquippedItem()) != null && var1 instanceof EntityLiving) {
            var2.useItemOnEntity((EntityLiving)var1);
            if (var2.stackSize <= 0) {
                var2.func_1097_a(this);
                this.destroyCurrentEquippedItem();
            }
        }
    }

    public ItemStack getCurrentEquippedItem() {
        return this.inventory.getCurrentItem();
    }

    public void destroyCurrentEquippedItem() {
        this.inventory.setInventorySlotContents(this.inventory.currentItem, null);
    }

    @Override
    public double getYOffset() {
        return this.yOffset - 0.5f;
    }

    public void swingItem() {
        this.swingProgressInt = -1;
        this.isSwinging = true;
    }

    public void attackTargetEntityWithCurrentItem(Entity var1) {
        int var2 = this.inventory.getDamageVsEntity(var1);
        if (var2 > 0) {
            var1.attackEntityFrom(this, var2);
            ItemStack var3 = this.getCurrentEquippedItem();
            if (var3 != null && var1 instanceof EntityLiving) {
                var3.hitEntity((EntityLiving)var1, this);
                if (var3.stackSize <= 0) {
                    var3.func_1097_a(this);
                    this.destroyCurrentEquippedItem();
                }
            }
            if (var1 instanceof EntityLiving) {
                if (var1.isEntityAlive()) {
                    this.alertWolves((EntityLiving)var1, true);
                }
                this.addStat(StatList.field_25167_s, var2);
            }
        }
    }

    public void respawnPlayer() {
    }

    public abstract void func_6420_o();

    public void onItemStackChanged(ItemStack var1) {
    }

    @Override
    public void setEntityDead() {
        super.setEntityDead();
        this.inventorySlots.onCraftGuiClosed(this);
        if (this.craftingInventory != null) {
            this.craftingInventory.onCraftGuiClosed(this);
        }
    }

    @Override
    public boolean isEntityInsideOpaqueBlock() {
        if (FreecamHack.instance.status && FreecamHack.instance.noclip.value) {
            return false;
        }
        return !this.sleeping && super.isEntityInsideOpaqueBlock();
    }

    public EnumStatus sleepInBedAt(int var1, int var2, int var3) {
        if (!this.isPlayerSleeping() && this.isEntityAlive()) {
            if (this.worldObj.worldProvider.field_4220_c) {
                return EnumStatus.NOT_POSSIBLE_HERE;
            }
            if (this.worldObj.isDaytime()) {
                return EnumStatus.NOT_POSSIBLE_NOW;
            }
            if (Math.abs(this.posX - (double)var1) <= 3.0 && Math.abs(this.posY - (double)var2) <= 2.0 && Math.abs(this.posZ - (double)var3) <= 3.0) {
                this.setSize(0.2f, 0.2f);
                this.yOffset = 0.2f;
                if (this.worldObj.blockExists(var1, var2, var3)) {
                    int var4 = this.worldObj.getBlockMetadata(var1, var2, var3);
                    int var5 = BlockBed.getDirectionFromMetadata(var4);
                    float var6 = 0.5f;
                    float var7 = 0.5f;
                    switch (var5) {
                        case 0: {
                            var7 = 0.9f;
                            break;
                        }
                        case 1: {
                            var6 = 0.1f;
                            break;
                        }
                        case 2: {
                            var7 = 0.1f;
                            break;
                        }
                        case 3: {
                            var6 = 0.9f;
                        }
                    }
                    this.func_22052_e(var5);
                    this.setPosition((float)var1 + var6, (float)var2 + 0.9375f, (float)var3 + var7);
                } else {
                    this.setPosition((float)var1 + 0.5f, (float)var2 + 0.9375f, (float)var3 + 0.5f);
                }
                this.sleeping = true;
                this.sleepTimer = 0;
                this.bedChunkCoordinates = new ChunkCoordinates(var1, var2, var3);
                this.motionY = 0.0;
                this.motionZ = 0.0;
                this.motionX = 0.0;
                if (!this.worldObj.multiplayerWorld) {
                    this.worldObj.updateAllPlayersSleepingFlag();
                }
                return EnumStatus.OK;
            }
            return EnumStatus.TOO_FAR_AWAY;
        }
        return EnumStatus.OTHER_PROBLEM;
    }

    private void func_22052_e(int var1) {
        this.field_22063_x = 0.0f;
        this.field_22061_z = 0.0f;
        switch (var1) {
            case 0: {
                this.field_22061_z = -1.8f;
                break;
            }
            case 1: {
                this.field_22063_x = 1.8f;
                break;
            }
            case 2: {
                this.field_22061_z = 1.8f;
                break;
            }
            case 3: {
                this.field_22063_x = -1.8f;
            }
        }
    }

    public void wakeUpPlayer(boolean var1, boolean var2, boolean var3) {
        this.setSize(0.6f, 1.8f);
        this.resetHeight();
        ChunkCoordinates var4 = this.bedChunkCoordinates;
        ChunkCoordinates var5 = this.bedChunkCoordinates;
        if (var4 != null && this.worldObj.getBlockId(var4.x, var4.y, var4.z) == Block.blockBed.blockID) {
            BlockBed.setBedOccupied(this.worldObj, var4.x, var4.y, var4.z, false);
            var5 = BlockBed.getNearestEmptyChunkCoordinates(this.worldObj, var4.x, var4.y, var4.z, 0);
            if (var5 == null) {
                var5 = new ChunkCoordinates(var4.x, var4.y + 1, var4.z);
            }
            this.setPosition((float)var5.x + 0.5f, (float)var5.y + this.yOffset + 0.1f, (float)var5.z + 0.5f);
        }
        this.sleeping = false;
        if (!this.worldObj.multiplayerWorld && var2) {
            this.worldObj.updateAllPlayersSleepingFlag();
        }
        this.sleepTimer = var1 ? 0 : 100;
        if (var3) {
            this.setPlayerSpawnCoordinate(this.bedChunkCoordinates);
        }
    }

    private boolean isInBed() {
        return this.worldObj.getBlockId(this.bedChunkCoordinates.x, this.bedChunkCoordinates.y, this.bedChunkCoordinates.z) == Block.blockBed.blockID;
    }

    public static ChunkCoordinates func_25060_a(World var0, ChunkCoordinates var1) {
        IChunkProvider var2 = var0.getIChunkProvider();
        var2.func_538_d(var1.x - 3 >> 4, var1.z - 3 >> 4);
        var2.func_538_d(var1.x + 3 >> 4, var1.z - 3 >> 4);
        var2.func_538_d(var1.x - 3 >> 4, var1.z + 3 >> 4);
        var2.func_538_d(var1.x + 3 >> 4, var1.z + 3 >> 4);
        if (var0.getBlockId(var1.x, var1.y, var1.z) != Block.blockBed.blockID) {
            return null;
        }
        ChunkCoordinates var3 = BlockBed.getNearestEmptyChunkCoordinates(var0, var1.x, var1.y, var1.z, 0);
        return var3;
    }

    public float getBedOrientationInDegrees() {
        if (this.bedChunkCoordinates != null) {
            int var1 = this.worldObj.getBlockMetadata(this.bedChunkCoordinates.x, this.bedChunkCoordinates.y, this.bedChunkCoordinates.z);
            int var2 = BlockBed.getDirectionFromMetadata(var1);
            switch (var2) {
                case 0: {
                    return 90.0f;
                }
                case 1: {
                    return 0.0f;
                }
                case 2: {
                    return 270.0f;
                }
                case 3: {
                    return 180.0f;
                }
            }
        }
        return 0.0f;
    }

    @Override
    public boolean isPlayerSleeping() {
        return this.sleeping;
    }

    public boolean isPlayerFullyAsleep() {
        return this.sleeping && this.sleepTimer >= 100;
    }

    public int func_22060_M() {
        return this.sleepTimer;
    }

    public void addChatMessage(String var1) {
    }

    public ChunkCoordinates getPlayerSpawnCoordinate() {
        return this.playerSpawnCoordinate;
    }

    public void setPlayerSpawnCoordinate(ChunkCoordinates var1) {
        this.playerSpawnCoordinate = var1 != null ? new ChunkCoordinates(var1) : null;
    }

    public void addStat(StatBasic var1, int var2) {
    }

    @Override
    protected void jump() {
        super.jump();
        this.addStat(StatList.field_25171_q, 1);
    }

    @Override
    public void moveEntityWithHeading(float var1, float var2) {
        double var3 = this.posX;
        double var5 = this.posY;
        double var7 = this.posZ;
        super.moveEntityWithHeading(var1, var2);
        this.addMovementStat(this.posX - var3, this.posY - var5, this.posZ - var7);
    }

    private void addMovementStat(double var1, double var3, double var5) {
        if (this.isInsideOfMaterial(Material.water)) {
            int var7 = Math.round(MathHelper.sqrt_double(var1 * var1 + var3 * var3 + var5 * var5) * 100.0f);
            if (var7 > 0) {
                this.addStat(StatList.field_25173_p, var7);
            }
        } else if (this.handleWaterMovement()) {
            int var7 = Math.round(MathHelper.sqrt_double(var1 * var1 + var5 * var5) * 100.0f);
            if (var7 > 0) {
                this.addStat(StatList.field_25177_l, var7);
            }
        } else if (this.isOnLadder()) {
            if (var3 > 0.0) {
                this.addStat(StatList.field_25175_n, (int)Math.round(var3 * 100.0));
            }
        } else if (this.onGround) {
            int var7 = Math.round(MathHelper.sqrt_double(var1 * var1 + var5 * var5) * 100.0f);
            if (var7 > 0) {
                this.addStat(StatList.field_25178_k, var7);
            }
        } else {
            int var7 = Math.round(MathHelper.sqrt_double(var1 * var1 + var5 * var5) * 100.0f);
            if (var7 > 25) {
                this.addStat(StatList.field_25174_o, var7);
            }
        }
    }

    @Override
    protected void fall(float var1) {
        if (var1 >= 2.0f) {
            this.addStat(StatList.field_25176_m, (int)Math.round((double)var1 * 100.0));
        }
        super.fall(var1);
    }
}

