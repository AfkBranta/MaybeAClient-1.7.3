package net.minecraft.src;

import java.util.Random;
import net.minecraft.src.Block;
import net.minecraft.src.EntityTNTPrimed;
import net.minecraft.src.Material;
import net.minecraft.src.World;

public class BlockTNT
extends Block {
    public BlockTNT(int var1, int var2) {
        super(var1, var2, Material.tnt);
    }

    @Override
    public int getBlockTextureFromSide(int var1) {
        if (var1 == 0) {
            return this.blockIndexInTexture + 2;
        }
        return var1 == 1 ? this.blockIndexInTexture + 1 : this.blockIndexInTexture;
    }

    @Override
    public void onNeighborBlockChange(World var1, int var2, int var3, int var4, int var5) {
        if (var5 > 0 && Block.blocksList[var5].canProvidePower() && var1.isBlockIndirectlyGettingPowered(var2, var3, var4)) {
            this.onBlockDestroyedByPlayer(var1, var2, var3, var4, 0);
            var1.setBlockWithNotify(var2, var3, var4, 0);
        }
    }

    @Override
    public int quantityDropped(Random var1) {
        return 0;
    }

    @Override
    public void onBlockDestroyedByExplosion(World var1, int var2, int var3, int var4) {
        EntityTNTPrimed var5 = new EntityTNTPrimed(var1, (float)var2 + 0.5f, (float)var3 + 0.5f, (float)var4 + 0.5f);
        var5.fuse = var1.rand.nextInt(var5.fuse / 4) + var5.fuse / 8;
        var1.entityJoinedWorld(var5);
    }

    @Override
    public void onBlockDestroyedByPlayer(World var1, int var2, int var3, int var4, int var5) {
        if (!var1.multiplayerWorld) {
            EntityTNTPrimed var6 = new EntityTNTPrimed(var1, (float)var2 + 0.5f, (float)var3 + 0.5f, (float)var4 + 0.5f);
            var1.entityJoinedWorld(var6);
            var1.playSoundAtEntity(var6, "random.fuse", 1.0f, 1.0f);
        }
    }
}

