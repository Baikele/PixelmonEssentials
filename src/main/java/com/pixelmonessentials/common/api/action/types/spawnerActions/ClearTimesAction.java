package com.pixelmonessentials.common.api.action.types.spawnerActions;

import com.pixelmonessentials.PixelmonEssentials;
import com.pixelmonessentials.common.api.action.ActionBase;
import com.pixelmonessentials.common.api.action.ActionData;
import com.pixelmonessentials.common.api.gui.EssentialsGuis;
import com.pixelmonessentials.common.guis.spawners.IndividualSpawnGui;
import net.minecraft.entity.player.EntityPlayerMP;
import noppes.npcs.api.wrapper.PlayerWrapper;
import noppes.npcs.api.wrapper.gui.CustomGuiWrapper;

public class ClearTimesAction extends ActionBase {
    public ClearTimesAction(){
        super("CLEAR_TIMES");
    }

    @Override
    public void doAction(EntityPlayerMP playerMP, ActionData data){
        EssentialsGuis gui= PixelmonEssentials.essentialsGuisHandler.getGui(playerMP);
        if(gui instanceof IndividualSpawnGui){
            ((IndividualSpawnGui) gui).setSpawnTimes(new String[0]);
            CustomGuiWrapper guiWrapper=((IndividualSpawnGui) gui).createGui(playerMP);
            PlayerWrapper playerWrapper=new PlayerWrapper(playerMP);
            guiWrapper.update(playerWrapper);
        }
    }
}
