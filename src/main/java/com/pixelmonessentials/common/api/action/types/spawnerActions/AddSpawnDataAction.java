package com.pixelmonessentials.common.api.action.types.spawnerActions;

import com.pixelmonessentials.PixelmonEssentials;
import com.pixelmonessentials.common.api.action.Action;
import com.pixelmonessentials.common.api.action.ActionBase;
import com.pixelmonessentials.common.api.gui.EssentialsGuis;
import com.pixelmonessentials.common.guis.spawners.SpawnerInfoGui;
import com.pixelmonessentials.common.spawners.SpawnData;
import net.minecraft.entity.player.EntityPlayerMP;

public class AddSpawnDataAction extends ActionBase {
    public AddSpawnDataAction(){
        super("ADD_SPAWN");
    }

    @Override
    public void doAction(String value, EntityPlayerMP playerMP){
        EssentialsGuis gui=PixelmonEssentials.essentialsGuisHandler.getGui(playerMP);
        if(gui instanceof SpawnerInfoGui){
            PixelmonEssentials.spawnerDataManagement.spawnData.add(new SpawnData());
            ((SpawnerInfoGui)gui).updateScroll(300, playerMP);
        }
    }
}
