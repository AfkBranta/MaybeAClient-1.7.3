package net.minecraft.src;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import net.minecraft.src.ColorizerFoliage;

public class ColorizerGrass {
    private static final int[] grassBuffer = new int[65536];

    static {
        try {
            BufferedImage var0 = ImageIO.read(ColorizerFoliage.class.getResource("/misc/grasscolor.png"));
            var0.getRGB(0, 0, 256, 256, grassBuffer, 0, 256);
        }
        catch (Exception var1) {
            var1.printStackTrace();
        }
    }

    public static int getGrassColor(double var0, double var2) {
        int var4 = (int)((1.0 - var0) * 255.0);
        int var5 = (int)((1.0 - (var2 *= var0)) * 255.0);
        return grassBuffer[var5 << 8 | var4];
    }
}

