package com.pixelmonessentials.common.api.action.types.guiActions;

import com.pixelmonessentials.common.api.action.Action;
import com.pixelmonessentials.common.api.action.ActionBase;
import com.pixelmonessentials.common.api.action.ActionData;
import net.minecraft.entity.player.EntityPlayerMP;

public class CloseGuiAction extends ActionBase {
    public CloseGuiAction(){
        super("CLOSE_GUI");
    }

    @Override
    public void doAction(EntityPlayerMP playerMP, ActionData data){
        playerMP.closeScreen();
    }
}
