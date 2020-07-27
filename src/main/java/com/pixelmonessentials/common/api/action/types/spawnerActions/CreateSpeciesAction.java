package com.pixelmonessentials.common.api.action.types.spawnerActions;

import com.pixelmonessentials.PixelmonEssentials;
import com.pixelmonessentials.common.api.action.Action;
import com.pixelmonessentials.common.api.action.ActionBase;
import com.pixelmonessentials.common.api.action.ActionData;
import com.pixelmonessentials.common.api.gui.EssentialsGuis;
import com.pixelmonessentials.common.guis.spawners.IndividualSpawnGui;
import com.pixelmonessentials.common.guis.spawners.SpawnerInfoGui;
import com.pixelmonessentials.common.spawners.IndividualSpawnData;
import com.pixelmonessentials.common.spawners.SpawnData;
import net.minecraft.entity.player.EntityPlayerMP;

public class CreateSpeciesAction extends ActionBase {
    public CreateSpeciesAction(){
        super("CREATE_SPECIES");
    }

    @Override
    public void doAction(EntityPlayerMP player, ActionData data){
        EssentialsGuis gui=PixelmonEssentials.essentialsGuisHandler.getGui(player);
        if(gui instanceof SpawnerInfoGui){
            SpawnData spawnData=PixelmonEssentials.spawnerDataManagement.spawnData.get(((SpawnerInfoGui) gui).getIdIndex());
            IndividualSpawnGui spawnGui=new IndividualSpawnGui(new IndividualSpawnData(), spawnData);
            spawnGui.init(player);
        }
    }
}
