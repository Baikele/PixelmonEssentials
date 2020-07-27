package com.pixelmonessentials.common.api.action.types.spawnerActions;

import com.pixelmonessentials.PixelmonEssentials;
import com.pixelmonessentials.common.api.action.Action;
import com.pixelmonessentials.common.api.action.ActionBase;
import com.pixelmonessentials.common.api.action.ActionData;
import com.pixelmonessentials.common.api.gui.EssentialsGuis;
import com.pixelmonessentials.common.guis.spawners.SpawnerInfoGui;
import net.minecraft.entity.player.EntityPlayerMP;

public class DeleteSpawnDataAction extends ActionBase {

    public DeleteSpawnDataAction(){
        super("DELETE_SPAWN");
    }

    @Override
    public void doAction(EntityPlayerMP player, ActionData data){
        EssentialsGuis gui= PixelmonEssentials.essentialsGuisHandler.getGui(player);
        if(gui instanceof SpawnerInfoGui){
            int index=((SpawnerInfoGui) gui).getIdIndex();
            PixelmonEssentials.spawnerDataManagement.spawnData.remove(index);
            ((SpawnerInfoGui) gui).updateScroll(300, player);
        }
    }
}
