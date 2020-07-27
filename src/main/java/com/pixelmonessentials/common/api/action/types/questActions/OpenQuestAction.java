package com.pixelmonessentials.common.api.action.types.questActions;

import com.pixelmonessentials.PixelmonEssentials;
import com.pixelmonessentials.common.api.action.ActionBase;
import com.pixelmonessentials.common.api.action.ActionData;
import com.pixelmonessentials.common.api.gui.EssentialsGuis;
import com.pixelmonessentials.common.guis.objectives.CustomObjectiveGui;
import com.pixelmonessentials.common.guis.objectives.ObjectiveSelectionGui;
import com.pixelmonessentials.common.guis.objectives.QuestSelectionGui;
import net.minecraft.entity.player.EntityPlayerMP;

public class OpenQuestAction extends ActionBase {
    public OpenQuestAction(){
        super("OPEN_QUEST");
    }

    @Override
    public void doAction(EntityPlayerMP playerMP, ActionData data){
        EssentialsGuis openGui= PixelmonEssentials.essentialsGuisHandler.getGui(playerMP);
        if(openGui instanceof QuestSelectionGui){
            ObjectiveSelectionGui gui=new ObjectiveSelectionGui(((QuestSelectionGui) openGui).getQuests(((QuestSelectionGui) openGui).getCategories().get(((QuestSelectionGui) openGui).getCategoryIndex())).get(((QuestSelectionGui) openGui).getQuestIndex()));
            gui.init(playerMP);
        }
        else if(openGui instanceof CustomObjectiveGui){
            ObjectiveSelectionGui gui=new ObjectiveSelectionGui(((CustomObjectiveGui) openGui).getQuest());
            gui.init(playerMP);
        }
    }
}
