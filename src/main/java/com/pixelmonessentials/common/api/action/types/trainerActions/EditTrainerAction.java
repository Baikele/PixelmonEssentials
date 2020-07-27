package com.pixelmonessentials.common.api.action.types.trainerActions;

import com.pixelmonessentials.common.api.action.ActionBase;
import com.pixelmonessentials.common.api.action.ActionData;
import com.pixelmonessentials.common.api.action.datatypes.ActionNpcGuiData;
import com.pixelmonessentials.common.guis.TrainerDataGui;
import net.minecraft.entity.player.EntityPlayerMP;

public class EditTrainerAction extends ActionBase {
    public EditTrainerAction(){
        super("EDIT_TRAINER");
    }

    @Override
    public void doAction(EntityPlayerMP player, ActionData data){
        if(data instanceof ActionNpcGuiData){
            TrainerDataGui gui=new TrainerDataGui();
            gui.setNpc(((ActionNpcGuiData) data).getNpc());
            gui.init(player);
        }
    }
}
