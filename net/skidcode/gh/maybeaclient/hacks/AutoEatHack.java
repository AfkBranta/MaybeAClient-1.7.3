/*
 * Decompiled with CFR 0.152.
 */
package net.skidcode.gh.maybeaclient.hacks;

import net.minecraft.src.ItemFood;
import net.minecraft.src.ItemStack;
import net.skidcode.gh.maybeaclient.events.Event;
import net.skidcode.gh.maybeaclient.events.EventListener;
import net.skidcode.gh.maybeaclient.events.EventRegistry;
import net.skidcode.gh.maybeaclient.events.impl.EventPlayerUpdatePre;
import net.skidcode.gh.maybeaclient.hacks.Hack;
import net.skidcode.gh.maybeaclient.hacks.category.Category;
import net.skidcode.gh.maybeaclient.hacks.settings.SettingInteger;

public class AutoEatHack
extends Hack
implements EventListener {
    public SettingInteger maxHPOverflow = new SettingInteger(this, "MaxOverflow", 19, 0, 19);
    public SettingInteger delayTicks = new SettingInteger(this, "DelayTicks", 10, 0, 20);
    public long ticksPassed = 0L;

    public AutoEatHack() {
        super("AutoEat", "Automatically eats food", 0, Category.COMBAT);
        this.addSetting(this.maxHPOverflow);
        this.addSetting(this.delayTicks);
        EventRegistry.registerListener(EventPlayerUpdatePre.class, this);
    }

    public void handleEvent(Event e) {
        if (e instanceof EventPlayerUpdatePre) {
            ++this.ticksPassed;
            if (AutoEatHack.mc.thePlayer.health <= 19) {
                int prevItem = AutoEatHack.mc.thePlayer.inventory.currentItem;
                int i = 0;
                while (i < 9) {
                    ItemFood food;
                    int newHealth;
                    ItemStack stack = AutoEatHack.mc.thePlayer.inventory.getStackInSlot(i);
                    if (stack != null && stack.getItem() instanceof ItemFood && this.ticksPassed > (long)this.delayTicks.value && (newHealth = (food = (ItemFood)stack.getItem()).getHealAmount()) + AutoEatHack.mc.thePlayer.health - 20 <= this.maxHPOverflow.value) {
                        AutoEatHack.mc.thePlayer.inventory.currentItem = i;
                        AutoEatHack.mc.playerController.sendUseItem(AutoEatHack.mc.thePlayer, AutoEatHack.mc.theWorld, stack);
                        AutoEatHack.mc.thePlayer.inventory.currentItem = prevItem;
                        this.ticksPassed = 0L;
                        break;
                    }
                    ++i;
                }
            }
        }
    }
}

