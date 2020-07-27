package com.pixelmonessentials.common.api.action.types.trainerActions;

import com.pixelmonessentials.PixelmonEssentials;
import com.pixelmonessentials.common.api.action.ActionBase;
import com.pixelmonessentials.common.api.action.ActionData;
import com.pixelmonessentials.common.api.gui.EssentialsGuis;
import com.pixelmonessentials.common.guis.TrainerDataGui;
import com.pixelmonessentials.common.guis.battles.SelectTeamGui;
import net.minecraft.entity.player.EntityPlayerMP;

public class SelectTeamAction extends ActionBase {
    public SelectTeamAction(){
        super("SELECT_TEAM");
    }

    @Override
    public void doAction(EntityPlayerMP playerMP, ActionData data){
        EssentialsGuis gui= PixelmonEssentials.essentialsGuisHandler.getGui(playerMP);
        if(gui instanceof TrainerDataGui){
            SelectTeamGui selectTeamGui=new SelectTeamGui();
            selectTeamGui.setParentGui((TrainerDataGui) gui);
            selectTeamGui.init(playerMP);
        }
    }
}
