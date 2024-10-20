/*
 * Decompiled with CFR 0.152.
 */
package lunatrius.schematica;

import java.io.File;
import java.io.FileFilter;

public class FileFilterSchematic
extends javax.swing.filechooser.FileFilter
implements FileFilter {
    @Override
    public boolean accept(File file) {
        if (file.isDirectory()) {
            return false;
        }
        return file.getPath().toLowerCase().endsWith(".schematic");
    }

    @Override
    public String getDescription() {
        return "Schematic files (*.schematic)";
    }
}

