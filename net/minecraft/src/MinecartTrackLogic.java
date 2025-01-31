package net.minecraft.src;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.src.BlockMinecartTrack;
import net.minecraft.src.ChunkPosition;
import net.minecraft.src.World;

class MinecartTrackLogic {
    private World worldObj;
    private int trackX;
    private int trackY;
    private int trackZ;
    private int trackMetadata;
    private List connectedTracks;
    final BlockMinecartTrack minecartTrack;

    public MinecartTrackLogic(BlockMinecartTrack var1, World var2, int var3, int var4, int var5) {
        this.minecartTrack = var1;
        this.connectedTracks = new ArrayList();
        this.worldObj = var2;
        this.trackX = var3;
        this.trackY = var4;
        this.trackZ = var5;
        this.trackMetadata = var2.getBlockMetadata(var3, var4, var5);
        this.calculateConnectedTracks();
    }

    private void calculateConnectedTracks() {
        this.connectedTracks.clear();
        if (this.trackMetadata == 0) {
            this.connectedTracks.add(new ChunkPosition(this.trackX, this.trackY, this.trackZ - 1));
            this.connectedTracks.add(new ChunkPosition(this.trackX, this.trackY, this.trackZ + 1));
        } else if (this.trackMetadata == 1) {
            this.connectedTracks.add(new ChunkPosition(this.trackX - 1, this.trackY, this.trackZ));
            this.connectedTracks.add(new ChunkPosition(this.trackX + 1, this.trackY, this.trackZ));
        } else if (this.trackMetadata == 2) {
            this.connectedTracks.add(new ChunkPosition(this.trackX - 1, this.trackY, this.trackZ));
            this.connectedTracks.add(new ChunkPosition(this.trackX + 1, this.trackY + 1, this.trackZ));
        } else if (this.trackMetadata == 3) {
            this.connectedTracks.add(new ChunkPosition(this.trackX - 1, this.trackY + 1, this.trackZ));
            this.connectedTracks.add(new ChunkPosition(this.trackX + 1, this.trackY, this.trackZ));
        } else if (this.trackMetadata == 4) {
            this.connectedTracks.add(new ChunkPosition(this.trackX, this.trackY + 1, this.trackZ - 1));
            this.connectedTracks.add(new ChunkPosition(this.trackX, this.trackY, this.trackZ + 1));
        } else if (this.trackMetadata == 5) {
            this.connectedTracks.add(new ChunkPosition(this.trackX, this.trackY, this.trackZ - 1));
            this.connectedTracks.add(new ChunkPosition(this.trackX, this.trackY + 1, this.trackZ + 1));
        } else if (this.trackMetadata == 6) {
            this.connectedTracks.add(new ChunkPosition(this.trackX + 1, this.trackY, this.trackZ));
            this.connectedTracks.add(new ChunkPosition(this.trackX, this.trackY, this.trackZ + 1));
        } else if (this.trackMetadata == 7) {
            this.connectedTracks.add(new ChunkPosition(this.trackX - 1, this.trackY, this.trackZ));
            this.connectedTracks.add(new ChunkPosition(this.trackX, this.trackY, this.trackZ + 1));
        } else if (this.trackMetadata == 8) {
            this.connectedTracks.add(new ChunkPosition(this.trackX - 1, this.trackY, this.trackZ));
            this.connectedTracks.add(new ChunkPosition(this.trackX, this.trackY, this.trackZ - 1));
        } else if (this.trackMetadata == 9) {
            this.connectedTracks.add(new ChunkPosition(this.trackX + 1, this.trackY, this.trackZ));
            this.connectedTracks.add(new ChunkPosition(this.trackX, this.trackY, this.trackZ - 1));
        }
    }

    private void func_785_b() {
        int var1 = 0;
        while (var1 < this.connectedTracks.size()) {
            MinecartTrackLogic var2 = this.getMinecartTrackLogic((ChunkPosition)this.connectedTracks.get(var1));
            if (var2 != null && var2.isConnectedTo(this)) {
                this.connectedTracks.set(var1, new ChunkPosition(var2.trackX, var2.trackY, var2.trackZ));
            } else {
                this.connectedTracks.remove(var1--);
            }
            ++var1;
        }
    }

    private boolean isMinecartTrack(int var1, int var2, int var3) {
        if (this.worldObj.getBlockId(var1, var2, var3) == this.minecartTrack.blockID) {
            return true;
        }
        if (this.worldObj.getBlockId(var1, var2 + 1, var3) == this.minecartTrack.blockID) {
            return true;
        }
        return this.worldObj.getBlockId(var1, var2 - 1, var3) == this.minecartTrack.blockID;
    }

    private MinecartTrackLogic getMinecartTrackLogic(ChunkPosition var1) {
        if (this.worldObj.getBlockId(var1.x, var1.y, var1.z) == this.minecartTrack.blockID) {
            return new MinecartTrackLogic(this.minecartTrack, this.worldObj, var1.x, var1.y, var1.z);
        }
        if (this.worldObj.getBlockId(var1.x, var1.y + 1, var1.z) == this.minecartTrack.blockID) {
            return new MinecartTrackLogic(this.minecartTrack, this.worldObj, var1.x, var1.y + 1, var1.z);
        }
        return this.worldObj.getBlockId(var1.x, var1.y - 1, var1.z) == this.minecartTrack.blockID ? new MinecartTrackLogic(this.minecartTrack, this.worldObj, var1.x, var1.y - 1, var1.z) : null;
    }

    private boolean isConnectedTo(MinecartTrackLogic var1) {
        int var2 = 0;
        while (var2 < this.connectedTracks.size()) {
            ChunkPosition var3 = (ChunkPosition)this.connectedTracks.get(var2);
            if (var3.x == var1.trackX && var3.z == var1.trackZ) {
                return true;
            }
            ++var2;
        }
        return false;
    }

    private boolean func_794_b(int var1, int var2, int var3) {
        int var4 = 0;
        while (var4 < this.connectedTracks.size()) {
            ChunkPosition var5 = (ChunkPosition)this.connectedTracks.get(var4);
            if (var5.x == var1 && var5.z == var3) {
                return true;
            }
            ++var4;
        }
        return false;
    }

    private int getAdjacentTracks() {
        int var1 = 0;
        if (this.isMinecartTrack(this.trackX, this.trackY, this.trackZ - 1)) {
            ++var1;
        }
        if (this.isMinecartTrack(this.trackX, this.trackY, this.trackZ + 1)) {
            ++var1;
        }
        if (this.isMinecartTrack(this.trackX - 1, this.trackY, this.trackZ)) {
            ++var1;
        }
        if (this.isMinecartTrack(this.trackX + 1, this.trackY, this.trackZ)) {
            ++var1;
        }
        return var1;
    }

    private boolean handleKeyPress(MinecartTrackLogic var1) {
        if (this.isConnectedTo(var1)) {
            return true;
        }
        if (this.connectedTracks.size() == 2) {
            return false;
        }
        if (this.connectedTracks.size() == 0) {
            return true;
        }
        ChunkPosition var2 = (ChunkPosition)this.connectedTracks.get(0);
        return var1.trackY == this.trackY && var2.y == this.trackY ? true : true;
    }

    private void func_788_d(MinecartTrackLogic var1) {
        this.connectedTracks.add(new ChunkPosition(var1.trackX, var1.trackY, var1.trackZ));
        boolean var2 = this.func_794_b(this.trackX, this.trackY, this.trackZ - 1);
        boolean var3 = this.func_794_b(this.trackX, this.trackY, this.trackZ + 1);
        boolean var4 = this.func_794_b(this.trackX - 1, this.trackY, this.trackZ);
        boolean var5 = this.func_794_b(this.trackX + 1, this.trackY, this.trackZ);
        int var6 = -1;
        if (var2 || var3) {
            var6 = 0;
        }
        if (var4 || var5) {
            var6 = 1;
        }
        if (var3 && var5 && !var2 && !var4) {
            var6 = 6;
        }
        if (var3 && var4 && !var2 && !var5) {
            var6 = 7;
        }
        if (var2 && var4 && !var3 && !var5) {
            var6 = 8;
        }
        if (var2 && var5 && !var3 && !var4) {
            var6 = 9;
        }
        if (var6 == 0) {
            if (this.worldObj.getBlockId(this.trackX, this.trackY + 1, this.trackZ - 1) == this.minecartTrack.blockID) {
                var6 = 4;
            }
            if (this.worldObj.getBlockId(this.trackX, this.trackY + 1, this.trackZ + 1) == this.minecartTrack.blockID) {
                var6 = 5;
            }
        }
        if (var6 == 1) {
            if (this.worldObj.getBlockId(this.trackX + 1, this.trackY + 1, this.trackZ) == this.minecartTrack.blockID) {
                var6 = 2;
            }
            if (this.worldObj.getBlockId(this.trackX - 1, this.trackY + 1, this.trackZ) == this.minecartTrack.blockID) {
                var6 = 3;
            }
        }
        if (var6 < 0) {
            var6 = 0;
        }
        this.worldObj.setBlockMetadataWithNotify(this.trackX, this.trackY, this.trackZ, var6);
    }

    private boolean func_786_c(int var1, int var2, int var3) {
        MinecartTrackLogic var4 = this.getMinecartTrackLogic(new ChunkPosition(var1, var2, var3));
        if (var4 == null) {
            return false;
        }
        var4.func_785_b();
        return var4.handleKeyPress(this);
    }

    public void func_792_a(boolean var1) {
        boolean var2 = this.func_786_c(this.trackX, this.trackY, this.trackZ - 1);
        boolean var3 = this.func_786_c(this.trackX, this.trackY, this.trackZ + 1);
        boolean var4 = this.func_786_c(this.trackX - 1, this.trackY, this.trackZ);
        boolean var5 = this.func_786_c(this.trackX + 1, this.trackY, this.trackZ);
        int var6 = -1;
        if ((var2 || var3) && !var4 && !var5) {
            var6 = 0;
        }
        if ((var4 || var5) && !var2 && !var3) {
            var6 = 1;
        }
        if (var3 && var5 && !var2 && !var4) {
            var6 = 6;
        }
        if (var3 && var4 && !var2 && !var5) {
            var6 = 7;
        }
        if (var2 && var4 && !var3 && !var5) {
            var6 = 8;
        }
        if (var2 && var5 && !var3 && !var4) {
            var6 = 9;
        }
        if (var6 == -1) {
            if (var2 || var3) {
                var6 = 0;
            }
            if (var4 || var5) {
                var6 = 1;
            }
            if (var1) {
                if (var3 && var5) {
                    var6 = 6;
                }
                if (var4 && var3) {
                    var6 = 7;
                }
                if (var5 && var2) {
                    var6 = 9;
                }
                if (var2 && var4) {
                    var6 = 8;
                }
            } else {
                if (var2 && var4) {
                    var6 = 8;
                }
                if (var5 && var2) {
                    var6 = 9;
                }
                if (var4 && var3) {
                    var6 = 7;
                }
                if (var3 && var5) {
                    var6 = 6;
                }
            }
        }
        if (var6 == 0) {
            if (this.worldObj.getBlockId(this.trackX, this.trackY + 1, this.trackZ - 1) == this.minecartTrack.blockID) {
                var6 = 4;
            }
            if (this.worldObj.getBlockId(this.trackX, this.trackY + 1, this.trackZ + 1) == this.minecartTrack.blockID) {
                var6 = 5;
            }
        }
        if (var6 == 1) {
            if (this.worldObj.getBlockId(this.trackX + 1, this.trackY + 1, this.trackZ) == this.minecartTrack.blockID) {
                var6 = 2;
            }
            if (this.worldObj.getBlockId(this.trackX - 1, this.trackY + 1, this.trackZ) == this.minecartTrack.blockID) {
                var6 = 3;
            }
        }
        if (var6 < 0) {
            var6 = 0;
        }
        this.trackMetadata = var6;
        this.calculateConnectedTracks();
        this.worldObj.setBlockMetadataWithNotify(this.trackX, this.trackY, this.trackZ, var6);
        int var7 = 0;
        while (var7 < this.connectedTracks.size()) {
            MinecartTrackLogic var8 = this.getMinecartTrackLogic((ChunkPosition)this.connectedTracks.get(var7));
            if (var8 != null) {
                var8.func_785_b();
                if (var8.handleKeyPress(this)) {
                    var8.func_788_d(this);
                }
            }
            ++var7;
        }
    }

    static int getNAdjacentTracks(MinecartTrackLogic var0) {
        return var0.getAdjacentTracks();
    }
}

