package com.pixelmonessentials.common.api.action.types.trainerActions;

import com.pixelmonessentials.PixelmonEssentials;
import com.pixelmonessentials.common.api.action.ActionBase;
import com.pixelmonessentials.common.api.action.ActionData;
import com.pixelmonessentials.common.api.gui.EssentialsGuis;
import com.pixelmonessentials.common.guis.TrainerDataGui;
import net.minecraft.entity.player.EntityPlayerMP;
import noppes.npcs.api.wrapper.PlayerWrapper;
import noppes.npcs.api.wrapper.gui.CustomGuiLabelWrapper;
import noppes.npcs.api.wrapper.gui.CustomGuiWrapper;
import noppes.npcs.controllers.CustomGuiController;

public class ClearRulesAction extends ActionBase {
    public ClearRulesAction(){
        super("CLEAR_RULES");
    }

    public void doAction(EntityPlayerMP playerMP, ActionData data){
        EssentialsGuis gui= PixelmonEssentials.essentialsGuisHandler.getGui(playerMP);
        if(gui instanceof TrainerDataGui){
            ((TrainerDataGui) gui).setRules("default");
            CustomGuiWrapper guiWrapper= CustomGuiController.getOpenGui(playerMP);
            ((CustomGuiLabelWrapper)guiWrapper.getComponent(251)).setText("default");
            guiWrapper.update(new PlayerWrapper(playerMP));
        }
    }
}
