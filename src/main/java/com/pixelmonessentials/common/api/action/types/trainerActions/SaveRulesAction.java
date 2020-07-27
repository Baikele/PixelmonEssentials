package com.pixelmonessentials.common.api.action.types.trainerActions;

import com.pixelmonessentials.PixelmonEssentials;
import com.pixelmonessentials.common.api.action.ActionBase;
import com.pixelmonessentials.common.api.action.ActionData;
import com.pixelmonessentials.common.api.gui.EssentialsGuis;
import com.pixelmonessentials.common.guis.TrainerDataGui;
import com.pixelmonessentials.common.guis.battles.TrainerRulesGui;
import net.minecraft.entity.player.EntityPlayerMP;
import noppes.npcs.api.wrapper.gui.CustomGuiWrapper;
import noppes.npcs.controllers.CustomGuiController;

public class SaveRulesAction extends ActionBase {
    public SaveRulesAction(){
        super("SAVE_RULES");
    }

    @Override
    public void doAction(EntityPlayerMP playerMP, ActionData data){
        EssentialsGuis gui= PixelmonEssentials.essentialsGuisHandler.getGui(playerMP);
        if(gui instanceof TrainerRulesGui){
            CustomGuiWrapper guiWrapper= CustomGuiController.getOpenGui(playerMP);
            String rules=PixelmonEssentials.customRulesManager.rulesets.get(((TrainerRulesGui) gui).getIndex()).getName();
            TrainerDataGui parentGui=((TrainerRulesGui) gui).getParentGui();
            parentGui.setRules(rules);
            parentGui.init(playerMP);
        }
    }
}
