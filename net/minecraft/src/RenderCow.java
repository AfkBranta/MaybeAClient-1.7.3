package net.minecraft.src;

import net.minecraft.src.Entity;
import net.minecraft.src.EntityCow;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.ModelBase;
import net.minecraft.src.RenderLiving;

public class RenderCow
extends RenderLiving {
    public RenderCow(ModelBase var1, float var2) {
        super(var1, var2);
    }

    public void func_177_a(EntityCow var1, double var2, double var4, double var6, float var8, float var9) {
        super.doRenderLiving(var1, var2, var4, var6, var8, var9);
    }

    @Override
    public void doRenderLiving(EntityLiving var1, double var2, double var4, double var6, float var8, float var9) {
        this.func_177_a((EntityCow)var1, var2, var4, var6, var8, var9);
    }

    @Override
    public void doRender(Entity var1, double var2, double var4, double var6, float var8, float var9) {
        this.func_177_a((EntityCow)var1, var2, var4, var6, var8, var9);
    }
}

