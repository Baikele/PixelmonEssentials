package com.pixelmonessentials.common.api.action.types.spawnerActions;

import com.pixelmonessentials.PixelmonEssentials;
import com.pixelmonessentials.common.api.action.Action;
import com.pixelmonessentials.common.api.action.ActionBase;
import com.pixelmonessentials.common.api.action.ActionData;
import com.pixelmonessentials.common.api.gui.EssentialsGuis;
import com.pixelmonessentials.common.guis.spawners.SpawnerInfoGui;
import net.minecraft.entity.player.EntityPlayerMP;

public class DeleteSpeciesAction extends ActionBase {
    public DeleteSpeciesAction(){
        super("DELETE_SPECIES");
    }

    @Override
    public void doAction(EntityPlayerMP player, ActionData data){
        EssentialsGuis gui= PixelmonEssentials.essentialsGuisHandler.getGui(player);
        if(gui instanceof SpawnerInfoGui){
            PixelmonEssentials.spawnerDataManagement.spawnData.get(((SpawnerInfoGui) gui).getIdIndex()).getSpawns().remove(((SpawnerInfoGui) gui).getSpeciesIndex());
            ((SpawnerInfoGui) gui).setIndex(301, "");
            ((SpawnerInfoGui) gui).updateScroll(301, player);
        }
    }
}
