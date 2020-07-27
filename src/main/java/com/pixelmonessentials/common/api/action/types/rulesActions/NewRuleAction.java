package com.pixelmonessentials.common.api.action.types.rulesActions;

import com.pixelmonessentials.PixelmonEssentials;
import com.pixelmonessentials.common.api.action.ActionBase;
import com.pixelmonessentials.common.api.action.ActionData;
import com.pixelmonessentials.common.api.gui.EssentialsGuis;
import com.pixelmonessentials.common.guis.battles.CustomRulesGui;
import net.minecraft.entity.player.EntityPlayerMP;
import noppes.npcs.api.wrapper.PlayerWrapper;
import noppes.npcs.api.wrapper.gui.CustomGuiWrapper;

public class NewRuleAction extends ActionBase {
    public NewRuleAction(){
        super("NEW_RULE");
    }

    @Override
    public void doAction(EntityPlayerMP playerMP, ActionData data){
        EssentialsGuis openGui= PixelmonEssentials.essentialsGuisHandler.getGui(playerMP);
        if(openGui instanceof CustomRulesGui){
            CustomGuiWrapper guiWrapper=((CustomRulesGui) openGui).getNewRuleGui();
            PlayerWrapper playerWrapper=new PlayerWrapper(playerMP);
            playerWrapper.showCustomGui(guiWrapper);
        }
    }
}
