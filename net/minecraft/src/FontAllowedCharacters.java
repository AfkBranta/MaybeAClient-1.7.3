/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class FontAllowedCharacters {
    public static final String allowedCharacters = FontAllowedCharacters.getAllowedCharacters();
    public static final char[] field_22286_b;

    static {
        char[] cArray = new char[15];
        cArray[0] = 47;
        cArray[1] = 10;
        cArray[2] = 13;
        cArray[3] = 9;
        cArray[5] = 12;
        cArray[6] = 96;
        cArray[7] = 63;
        cArray[8] = 42;
        cArray[9] = 92;
        cArray[10] = 60;
        cArray[11] = 62;
        cArray[12] = 124;
        cArray[13] = 34;
        cArray[14] = 58;
        field_22286_b = cArray;
    }

    private static String getAllowedCharacters() {
        String var0 = "";
        try {
            BufferedReader var1 = new BufferedReader(new InputStreamReader(FontAllowedCharacters.class.getResourceAsStream("/font.txt"), "UTF-8"));
            String var2 = "";
            while ((var2 = var1.readLine()) != null) {
                if (var2.startsWith("#")) continue;
                var0 = String.valueOf(var0) + var2;
            }
            var1.close();
        }
        catch (Exception exception) {
            // empty catch block
        }
        return var0;
    }
}

