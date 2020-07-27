package com.pixelmonessentials.common.api.action.types.trainerActions;

import com.pixelmonessentials.PixelmonEssentials;
import com.pixelmonessentials.common.api.action.ActionBase;
import com.pixelmonessentials.common.api.action.ActionData;
import com.pixelmonessentials.common.api.gui.EssentialsGuis;
import com.pixelmonessentials.common.guis.battles.TrainerRulesGui;
import net.minecraft.entity.player.EntityPlayerMP;

public class CancelRulesAction extends ActionBase {
    public CancelRulesAction(){
        super("CANCEL_RULES");
    }

    @Override
    public void doAction(EntityPlayerMP playerMP, ActionData data){
        EssentialsGuis gui= PixelmonEssentials.essentialsGuisHandler.getGui(playerMP);
        if(gui instanceof TrainerRulesGui){
            EssentialsGuis parentGui=((TrainerRulesGui) gui).getParentGui();
            parentGui.init(playerMP);
        }
    }
}
