package com.pixelmonessentials.common.spawners;

import net.minecraft.util.math.BlockPos;

public class SpawnerLocation {
    private int dim;
    private BlockPos pos;

    public SpawnerLocation(int dim, BlockPos pos){
        this.dim=dim;
        this.pos=pos;
    }

    public int getDim(){
        return this.dim;
    }

    public BlockPos getPos(){
        return this.pos;
    }
}
