package com.pixelmonessentials.common.api.action.types.spawnerActions;

import com.pixelmonessentials.PixelmonEssentials;
import com.pixelmonessentials.common.api.action.Action;
import com.pixelmonessentials.common.api.action.ActionBase;
import com.pixelmonessentials.common.api.action.ActionData;
import com.pixelmonessentials.common.api.gui.EssentialsGuis;
import net.minecraft.entity.player.EntityPlayerMP;

public class SaveSpeciesAction extends ActionBase {
    public SaveSpeciesAction(){
        super("SAVE_SPECIES");
    }

    @Override
    public void doAction(EntityPlayerMP player, ActionData data){
        EssentialsGuis gui= PixelmonEssentials.essentialsGuisHandler.getGui(player);
    }
}
