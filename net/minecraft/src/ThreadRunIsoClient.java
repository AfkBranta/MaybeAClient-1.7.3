package net.minecraft.src;

import net.minecraft.src.CanvasIsomPreview;

class ThreadRunIsoClient
extends Thread {
    final CanvasIsomPreview isoCanvas;

    ThreadRunIsoClient(CanvasIsomPreview var1) {
        this.isoCanvas = var1;
    }

    @Override
    public void run() {
        while (CanvasIsomPreview.isRunning(this.isoCanvas)) {
            this.isoCanvas.showNextBuffer();
            try {
                Thread.sleep(1L);
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
    }
}

