/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

public class ModelBed {
    public static final int[] field_22280_a = new int[]{3, 4, 2, 5};
    public static final int[] field_22279_b;
    public static final int[][] bedDirection;

    static {
        int[] nArray = new int[4];
        nArray[0] = 2;
        nArray[1] = 3;
        nArray[3] = 1;
        field_22279_b = nArray;
        int[][] nArrayArray = new int[4][];
        int[] nArray2 = new int[6];
        nArray2[0] = 1;
        nArray2[2] = 3;
        nArray2[3] = 2;
        nArray2[4] = 5;
        nArray2[5] = 4;
        nArrayArray[0] = nArray2;
        int[] nArray3 = new int[6];
        nArray3[0] = 1;
        nArray3[2] = 5;
        nArray3[3] = 4;
        nArray3[4] = 2;
        nArray3[5] = 3;
        nArrayArray[1] = nArray3;
        int[] nArray4 = new int[6];
        nArray4[0] = 1;
        nArray4[2] = 2;
        nArray4[3] = 3;
        nArray4[4] = 4;
        nArray4[5] = 5;
        nArrayArray[2] = nArray4;
        int[] nArray5 = new int[6];
        nArray5[0] = 1;
        nArray5[2] = 4;
        nArray5[3] = 5;
        nArray5[4] = 3;
        nArray5[5] = 2;
        nArrayArray[3] = nArray5;
        bedDirection = nArrayArray;
    }
}

