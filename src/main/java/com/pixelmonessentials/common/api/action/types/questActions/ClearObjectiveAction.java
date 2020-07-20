package com.pixelmonessentials.common.api.action.types.questActions;

import com.pixelmonessentials.PixelmonEssentials;
import com.pixelmonessentials.common.api.action.ActionBase;
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
    public void doAction(String value, EntityPlayerMP playerMP){
        EssentialsGuis gui= PixelmonEssentials.essentialsGuisHandler.getGui(playerMP);
        if(gui instanceof ObjectiveSelectionGui){
            ArrayList<ObjectiveData> objectiveData=PixelmonEssentials.questsManager.getObjectivesFromRealQuest(((ObjectiveSelectionGui) gui).getQuest());
            for(ObjectiveData data: objectiveData){
                if(data.getObjectiveId()==((ObjectiveSelectionGui) gui).getIndex()){
                    data.getParentObjective().removeQuest(data);
                    ((ObjectiveSelectionGui) gui).updateScroll(300, playerMP);
                    return;
                }
            }
        }
    }
}
