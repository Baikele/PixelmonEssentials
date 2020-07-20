package com.pixelmonessentials.common.api.action.types.questActions;

import com.pixelmonessentials.common.api.action.ActionBase;
import com.pixelmonessentials.common.guis.objectives.QuestSelectionGui;
import net.minecraft.entity.player.EntityPlayerMP;

public class OpenQuestGuiAction extends ActionBase {
    public OpenQuestGuiAction(){
        super("QUEST_GUI");
    }

    @Override
    public void doAction(String value, EntityPlayerMP playerMP){
        QuestSelectionGui gui=new QuestSelectionGui();
        gui.init(playerMP);
    }
}
