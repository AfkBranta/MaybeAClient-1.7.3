/*
 * Decompiled with CFR 0.152.
 */
package net.skidcode.gh.maybeaclient.utils;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class TextureUtils {
    public static int getAlpha(BufferedImage tex, int x, int y) {
        return tex.getRGB(x, y) >> 24 & 0xFF;
    }

    public static BufferedImage generateOutlinedTexture(BufferedImage tex) throws IOException {
        BufferedImage out = new BufferedImage(tex.getWidth(), tex.getHeight(), 2);
        int tx = 0;
        while (tx < tex.getWidth()) {
            int ty = 0;
            while (ty < tex.getHeight()) {
                int txmi = tx;
                int txma = tx + 15;
                int tymi = ty;
                int tyma = ty + 15;
                int y = tymi;
                while (y < tyma) {
                    boolean hp = false;
                    int x = txmi;
                    while (x < txma) {
                        int a = TextureUtils.getAlpha(tex, x, y);
                        if (hp) {
                            if (y != tyma && TextureUtils.getAlpha(tex, x, y + 1) == 0) {
                                hp = false;
                            }
                            if (y != tymi && TextureUtils.getAlpha(tex, x, y - 1) == 0) {
                                hp = false;
                            }
                            if (x != txma && TextureUtils.getAlpha(tex, x + 1, y) == 0) {
                                hp = false;
                            }
                            if (x != txmi && TextureUtils.getAlpha(tex, x - 1, y) == 0) {
                                hp = false;
                            }
                        }
                        if (hp && x == txma) {
                            hp = false;
                        }
                        if (hp || a == 0) {
                            out.setRGB(x, y, 0);
                        } else {
                            out.setRGB(x, y, -1);
                            hp = true;
                        }
                        ++x;
                    }
                    ++y;
                }
                ty += 16;
            }
            tx += 16;
        }
        return out;
    }
}

