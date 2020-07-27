package com.pixelmonessentials.common.api.action.types.questActions;

import com.pixelmonessentials.PixelmonEssentials;
import com.pixelmonessentials.common.api.action.ActionBase;
import com.pixelmonessentials.common.api.action.ActionData;
import com.pixelmonessentials.common.api.gui.EssentialsGuis;
import com.pixelmonessentials.common.guis.objectives.CustomObjectiveGui;
import com.pixelmonessentials.common.guis.objectives.ObjectiveSelectionGui;
import com.pixelmonessentials.common.guis.objectives.QuestSelectionGui;
import net.minecraft.entity.player.EntityPlayerMP;
import noppes.npcs.controllers.data.Quest;

public class OpenObjectiveAction extends ActionBase {
    public OpenObjectiveAction(){
        super("OPEN_OBJECTIVE");
    }

    @Override
    public void doAction(EntityPlayerMP playerMP, ActionData data){
        EssentialsGuis gui= PixelmonEssentials.essentialsGuisHandler.getGui(playerMP);
        if(gui instanceof ObjectiveSelectionGui){
            CustomObjectiveGui newGui=new CustomObjectiveGui(((ObjectiveSelectionGui) gui).getQuest(), ((ObjectiveSelectionGui) gui).getIndex());
            newGui.init(playerMP);
        }
    }
}
