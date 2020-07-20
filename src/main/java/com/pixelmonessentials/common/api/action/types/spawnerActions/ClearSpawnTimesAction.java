package com.pixelmonessentials.common.api.action.types.spawnerActions;

import com.pixelmonessentials.PixelmonEssentials;
import com.pixelmonessentials.common.api.action.Action;
import com.pixelmonessentials.common.api.action.ActionBase;
import com.pixelmonessentials.common.api.gui.EssentialsGuis;
import com.pixelmonessentials.common.guis.spawners.IndividualSpawnGui;
import net.minecraft.entity.player.EntityPlayerMP;

public class ClearSpawnTimesAction extends ActionBase {
    public ClearSpawnTimesAction(){
        super("CLEAR_TIMES");
    }

    @Override
    public void doAction(String value, EntityPlayerMP playerMP){
        EssentialsGuis gui= PixelmonEssentials.essentialsGuisHandler.getGui(playerMP);
        if(gui instanceof IndividualSpawnGui){
            ((IndividualSpawnGui) gui).getSpawnData().getDayPhases().clear();
        }
    }
}
