package com.pixelmonessentials.common.api.action.types.trainerActions;

import com.pixelmonessentials.common.api.action.ActionBase;
import com.pixelmonessentials.common.api.action.ActionData;
import com.pixelmonessentials.common.api.action.datatypes.ActionNpcGuiData;
import com.pixelmonessentials.common.guis.npc.OtherSettingsGui;
import net.minecraft.entity.player.EntityPlayerMP;
import noppes.npcs.api.wrapper.PlayerWrapper;

public class OtherSettingsAction extends ActionBase {
    public OtherSettingsAction(){
        super("OTHER_NPC");
    }

    @Override
    public void doAction(EntityPlayerMP playerMP, ActionData data){
        if(data instanceof ActionNpcGuiData){
            OtherSettingsGui gui=new OtherSettingsGui();
            gui.setNpc(((ActionNpcGuiData) data).getNpc());
            gui.init(playerMP);
        }
    }
}
