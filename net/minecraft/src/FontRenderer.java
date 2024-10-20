/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.src;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.IntBuffer;
import javax.imageio.ImageIO;
import net.minecraft.src.FontAllowedCharacters;
import net.minecraft.src.GLAllocation;
import net.minecraft.src.GameSettings;
import net.minecraft.src.RenderEngine;
import net.minecraft.src.Tessellator;
import org.lwjgl.opengl.GL11;

public class FontRenderer {
    private int[] charWidth = new int[256];
    public int fontTextureName = 0;
    private int fontDisplayLists;
    private IntBuffer buffer = GLAllocation.createDirectIntBuffer(1024);

    public FontRenderer(GameSettings var1, String var2, RenderEngine var3) {
        int var16;
        int var15;
        int var12;
        int var11;
        int var10;
        int var9;
        BufferedImage var4;
        try {
            var4 = ImageIO.read(RenderEngine.class.getResourceAsStream(var2));
        }
        catch (IOException var18) {
            throw new RuntimeException(var18);
        }
        int var5 = var4.getWidth();
        int var6 = var4.getHeight();
        int[] var7 = new int[var5 * var6];
        var4.getRGB(0, 0, var5, var6, var7, 0, var5);
        int var8 = 0;
        while (var8 < 256) {
            var9 = var8 % 16;
            var10 = var8 / 16;
            var11 = 7;
            while (var11 >= 0) {
                var12 = var9 * 8 + var11;
                boolean var13 = true;
                int var14 = 0;
                while (var14 < 8 && var13) {
                    var15 = (var10 * 8 + var14) * var5;
                    var16 = var7[var12 + var15] & 0xFF;
                    if (var16 > 0) {
                        var13 = false;
                    }
                    ++var14;
                }
                if (!var13) break;
                --var11;
            }
            if (var8 == 32) {
                var11 = 2;
            }
            this.charWidth[var8] = var11 + 2;
            ++var8;
        }
        this.fontTextureName = var3.allocateAndSetupTexture(var4);
        this.fontDisplayLists = GLAllocation.generateDisplayLists(288);
        Tessellator var19 = Tessellator.instance;
        var9 = 0;
        while (var9 < 256) {
            GL11.glNewList((int)(this.fontDisplayLists + var9), (int)4864);
            var19.startDrawingQuads();
            var10 = var9 % 16 * 8;
            var11 = var9 / 16 * 8;
            float var20 = 7.99f;
            float var21 = 0.0f;
            float var23 = 0.0f;
            var19.addVertexWithUV(0.0, 0.0f + var20, 0.0, (float)var10 / 128.0f + var21, ((float)var11 + var20) / 128.0f + var23);
            var19.addVertexWithUV(0.0f + var20, 0.0f + var20, 0.0, ((float)var10 + var20) / 128.0f + var21, ((float)var11 + var20) / 128.0f + var23);
            var19.addVertexWithUV(0.0f + var20, 0.0, 0.0, ((float)var10 + var20) / 128.0f + var21, (float)var11 / 128.0f + var23);
            var19.addVertexWithUV(0.0, 0.0, 0.0, (float)var10 / 128.0f + var21, (float)var11 / 128.0f + var23);
            var19.draw();
            GL11.glTranslatef((float)this.charWidth[var9], (float)0.0f, (float)0.0f);
            GL11.glEndList();
            ++var9;
        }
        var9 = 0;
        while (var9 < 32) {
            boolean var24;
            var10 = (var9 >> 3 & 1) * 85;
            var11 = (var9 >> 2 & 1) * 170 + var10;
            var12 = (var9 >> 1 & 1) * 170 + var10;
            int var22 = (var9 >> 0 & 1) * 170 + var10;
            if (var9 == 6) {
                var11 += 85;
            }
            boolean bl = var24 = var9 >= 16;
            if (var1.anaglyph) {
                var15 = (var11 * 30 + var12 * 59 + var22 * 11) / 100;
                var16 = (var11 * 30 + var12 * 70) / 100;
                int var17 = (var11 * 30 + var22 * 70) / 100;
                var11 = var15;
                var12 = var16;
                var22 = var17;
            }
            if (var24) {
                var11 /= 4;
                var12 /= 4;
                var22 /= 4;
            }
            GL11.glNewList((int)(this.fontDisplayLists + 256 + var9), (int)4864);
            GL11.glColor3f((float)((float)var11 / 255.0f), (float)((float)var12 / 255.0f), (float)((float)var22 / 255.0f));
            GL11.glEndList();
            ++var9;
        }
    }

    public void drawStringWithShadow(String var1, int var2, int var3, int var4) {
        this.renderString(var1, var2 + 1, var3 + 1, var4, true);
        this.drawString(var1, var2, var3, var4);
    }

    public void drawString(String var1, int var2, int var3, int var4) {
        this.renderString(var1, var2, var3, var4, false);
    }

    public void drawSplittedString(String s, int x, int y, int col, int splitWidth, int yAdd) {
        String toRend = "";
        String[] rend = s.split(" ");
        int i = 0;
        while (i < rend.length) {
            if (this.getStringWidth(String.valueOf(toRend) + rend[i]) >= splitWidth) {
                this.drawString(toRend, x, y, col);
                toRend = String.valueOf(rend[i]) + " ";
                y += yAdd;
            } else {
                toRend = String.valueOf(toRend) + rend[i];
                toRend = String.valueOf(toRend) + " ";
            }
            ++i;
        }
        this.drawString(toRend, x, y, col);
    }

    public int[] getSplittedStringWidthAndHeight(String s, int splitWidth, int yAdd) {
        int maxWidth = 0;
        int y = 0;
        String toRend = "";
        String[] rend = s.split(" ");
        int i = 0;
        while (i < rend.length) {
            int w = this.getStringWidth(String.valueOf(toRend) + rend[i]);
            if (w >= splitWidth) {
                toRend = String.valueOf(rend[i]) + " ";
                y += yAdd;
            } else {
                if (w > maxWidth) {
                    maxWidth = w;
                }
                toRend = String.valueOf(toRend) + rend[i];
                toRend = String.valueOf(toRend) + " ";
            }
            ++i;
        }
        return new int[]{maxWidth, y};
    }

    /*
     * Exception decompiling
     */
    public void renderString(String s, int var2, int var3, int var4, boolean var5) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: CONTINUE without a while class org.benf.cfr.reader.bytecode.analysis.parse.statement.AssignmentSimple
         *     at org.benf.cfr.reader.bytecode.analysis.parse.statement.GotoStatement.getTargetStartBlock(GotoStatement.java:102)
         *     at org.benf.cfr.reader.bytecode.analysis.parse.statement.IfStatement.getStructuredStatement(IfStatement.java:110)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.getStructuredStatementPlaceHolder(Op03SimpleStatement.java:550)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:727)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:850)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at org.benf.cfr.reader.Main.main(Main.java:54)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    public int getStringWidth(String var1) {
        if (var1 == null) {
            return 0;
        }
        int var2 = 0;
        int var3 = 0;
        while (var3 < var1.length()) {
            if (var1.charAt(var3) == '\u00a7') {
                ++var3;
            } else {
                int var4 = FontAllowedCharacters.allowedCharacters.indexOf(var1.charAt(var3));
                if (var4 >= 0) {
                    var2 += this.charWidth[var4 + 32];
                }
            }
            ++var3;
        }
        return var2;
    }
}

