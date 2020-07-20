package com.pixelmonessentials.common.api.action.types.spawnerActions;

import com.pixelmonessentials.PixelmonEssentials;
import com.pixelmonessentials.common.api.action.ActionBase;
import com.pixelmonessentials.common.api.gui.EssentialsGuis;
import com.pixelmonessentials.common.guis.spawners.IndividualSpawnGui;
import net.minecraft.entity.player.EntityPlayerMP;
import noppes.npcs.api.wrapper.PlayerWrapper;
import noppes.npcs.api.wrapper.gui.CustomGuiWrapper;

public class EditTimesAction extends ActionBase {
    public EditTimesAction(){
        super("EDIT_TIMES");
    }

    @Override
    public void doAction(String value, EntityPlayerMP playerMP){
        EssentialsGuis gui= PixelmonEssentials.essentialsGuisHandler.getGui(playerMP);
        if(gui instanceof IndividualSpawnGui){
            CustomGuiWrapper guiWrapper=((IndividualSpawnGui) gui).createSpawnTimesGui(playerMP);
            PlayerWrapper playerWrapper=new PlayerWrapper(playerMP);
            playerWrapper.showCustomGui(guiWrapper);
        }
    }
}
