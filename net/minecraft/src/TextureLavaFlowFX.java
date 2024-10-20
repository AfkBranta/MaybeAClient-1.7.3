package net.minecraft.src;

import net.minecraft.src.Block;
import net.minecraft.src.MathHelper;
import net.minecraft.src.TextureFX;
import net.skidcode.gh.maybeaclient.hacks.NoRenderHack;

public class TextureLavaFlowFX
extends TextureFX {
    protected float[] field_1143_g = new float[256];
    protected float[] field_1142_h = new float[256];
    protected float[] field_1141_i = new float[256];
    protected float[] field_1140_j = new float[256];
    public boolean hasTexture = false;
    int field_1139_k = 0;

    public TextureLavaFlowFX() {
        super(Block.lavaMoving.blockIndexInTexture + 1);
        this.tileSize = 2;
    }

    @Override
    public void onTick() {
        int var9;
        int var8;
        int var7;
        int var6;
        int var5;
        float var3;
        int var2;
        ++this.field_1139_k;
        if (this.hasTexture && NoRenderHack.instance.status && NoRenderHack.instance.lavaAnim.value) {
            return;
        }
        int var1 = 0;
        while (var1 < 16) {
            var2 = 0;
            while (var2 < 16) {
                var3 = 0.0f;
                int var4 = (int)(MathHelper.sin((float)var2 * (float)Math.PI * 2.0f / 16.0f) * 1.2f);
                var5 = (int)(MathHelper.sin((float)var1 * (float)Math.PI * 2.0f / 16.0f) * 1.2f);
                var6 = var1 - 1;
                while (var6 <= var1 + 1) {
                    var7 = var2 - 1;
                    while (var7 <= var2 + 1) {
                        var8 = var6 + var4 & 0xF;
                        var9 = var7 + var5 & 0xF;
                        var3 += this.field_1143_g[var8 + var9 * 16];
                        ++var7;
                    }
                    ++var6;
                }
                this.field_1142_h[var1 + var2 * 16] = var3 / 10.0f + (this.field_1141_i[(var1 + 0 & 0xF) + (var2 + 0 & 0xF) * 16] + this.field_1141_i[(var1 + 1 & 0xF) + (var2 + 0 & 0xF) * 16] + this.field_1141_i[(var1 + 1 & 0xF) + (var2 + 1 & 0xF) * 16] + this.field_1141_i[(var1 + 0 & 0xF) + (var2 + 1 & 0xF) * 16]) / 4.0f * 0.8f;
                float[] var10000 = this.field_1141_i;
                int n = var1 + var2 * 16;
                var10000[n] = var10000[n] + this.field_1140_j[var1 + var2 * 16] * 0.01f;
                if (this.field_1141_i[var1 + var2 * 16] < 0.0f) {
                    this.field_1141_i[var1 + var2 * 16] = 0.0f;
                }
                var10000 = this.field_1140_j;
                int n2 = var1 + var2 * 16;
                var10000[n2] = var10000[n2] - 0.06f;
                if (Math.random() < 0.005) {
                    this.field_1140_j[var1 + var2 * 16] = 1.5f;
                }
                ++var2;
            }
            ++var1;
        }
        float[] var11 = this.field_1142_h;
        this.field_1142_h = this.field_1143_g;
        this.field_1143_g = var11;
        var2 = 0;
        while (var2 < 256) {
            var3 = this.field_1143_g[var2 - this.field_1139_k / 3 * 16 & 0xFF] * 2.0f;
            if (var3 > 1.0f) {
                var3 = 1.0f;
            }
            if (var3 < 0.0f) {
                var3 = 0.0f;
            }
            var5 = (int)(var3 * 100.0f + 155.0f);
            var6 = (int)(var3 * var3 * 255.0f);
            var7 = (int)(var3 * var3 * var3 * var3 * 128.0f);
            if (this.anaglyphEnabled) {
                var8 = (var5 * 30 + var6 * 59 + var7 * 11) / 100;
                var9 = (var5 * 30 + var6 * 70) / 100;
                int var10 = (var5 * 30 + var7 * 70) / 100;
                var5 = var8;
                var6 = var9;
                var7 = var10;
            }
            this.imageData[var2 * 4 + 0] = (byte)var5;
            this.imageData[var2 * 4 + 1] = (byte)var6;
            this.imageData[var2 * 4 + 2] = (byte)var7;
            this.imageData[var2 * 4 + 3] = -1;
            ++var2;
        }
        this.hasTexture = true;
    }
}

