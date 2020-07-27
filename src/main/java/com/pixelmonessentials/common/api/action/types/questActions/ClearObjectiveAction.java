package com.pixelmonessentials.common.api.action.types.questActions;

import com.pixelmonessentials.PixelmonEssentials;
import com.pixelmonessentials.common.api.action.ActionBase;
import com.pixelmonessentials.common.api.action.ActionData;
import com.pixelmonessentials.common.api.gui.EssentialsGuis;
import com.pixelmonessentials.common.api.quests.ObjectiveData;
import com.pixelmonessentials.common.guis.objectives.ObjectiveSelectionGui;
import net.minecraft.entity.player.EntityPlayerMP;

import java.util.ArrayList;

public class ClearObjectiveAction extends ActionBase {
    public ClearObjectiveAction(){
        super("CLEAR_OBJECTIVE");
    }

    @Override
    public void doAction(EntityPlayerMP playerMP, ActionData data){
        EssentialsGuis gui= PixelmonEssentials.essentialsGuisHandler.getGui(playerMP);
        if(gui instanceof ObjectiveSelectionGui){
            ArrayList<ObjectiveData> objectiveData=PixelmonEssentials.questsManager.getObjectivesFromRealQuest(((ObjectiveSelectionGui) gui).getQuest());
            for(ObjectiveData oData: objectiveData){
                if(oData.getObjectiveId()==((ObjectiveSelectionGui) gui).getIndex()){
                    oData.getParentObjective().removeQuest(oData);
                    ((ObjectiveSelectionGui) gui).updateScroll(300, playerMP);
                    return;
                }
            }
        }
    }
}
