package com.pixelmonessentials.common.api.action.types.rulesActions;

import com.pixelmonessentials.PixelmonEssentials;
import com.pixelmonessentials.common.api.action.ActionBase;
import com.pixelmonessentials.common.api.action.ActionData;
import com.pixelmonessentials.common.api.gui.EssentialsGuis;
import com.pixelmonessentials.common.guis.battles.CustomRulesGui;
import net.minecraft.entity.player.EntityPlayerMP;

public class ReloadRulesAction extends ActionBase {
    public ReloadRulesAction(){
        super("RELOAD_RULES");
    }

    @Override
    public void doAction(EntityPlayerMP playerMP, ActionData data){
        EssentialsGuis gui= PixelmonEssentials.essentialsGuisHandler.getGui(playerMP);
        if(gui instanceof CustomRulesGui){
            gui.init(playerMP);
        }
    }
}
