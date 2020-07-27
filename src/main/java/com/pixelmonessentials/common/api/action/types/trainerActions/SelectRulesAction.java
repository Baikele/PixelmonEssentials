package com.pixelmonessentials.common.api.action.types.trainerActions;

import com.pixelmonessentials.common.api.action.ActionBase;
import com.pixelmonessentials.common.api.action.ActionData;
import com.pixelmonessentials.common.guis.battles.TrainerRulesGui;
import net.minecraft.entity.player.EntityPlayerMP;

public class SelectRulesAction extends ActionBase {
    public SelectRulesAction(){
        super("SELECT_RULES");
    }

    @Override
    public void doAction(EntityPlayerMP playerMP, ActionData data){
        TrainerRulesGui gui=new TrainerRulesGui();
        gui.init(playerMP);
    }
}
