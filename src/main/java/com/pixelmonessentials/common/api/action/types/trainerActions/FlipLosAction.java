package com.pixelmonessentials.common.api.action.types.trainerActions;

import com.pixelmonessentials.PixelmonEssentials;
import com.pixelmonessentials.common.api.action.ActionBase;
import com.pixelmonessentials.common.api.action.ActionData;
import com.pixelmonessentials.common.api.gui.EssentialsGuis;
import com.pixelmonessentials.common.guis.TrainerDataGui;
import net.minecraft.entity.player.EntityPlayerMP;
import noppes.npcs.api.wrapper.PlayerWrapper;
import noppes.npcs.api.wrapper.gui.CustomGuiButtonWrapper;
import noppes.npcs.api.wrapper.gui.CustomGuiLabelWrapper;
import noppes.npcs.api.wrapper.gui.CustomGuiWrapper;
import noppes.npcs.controllers.CustomGuiController;

public class FlipLosAction extends ActionBase {
    public FlipLosAction(){
        super("FLIP_LOS");
    }

    @Override
    public void doAction(EntityPlayerMP playerMP, ActionData data){
        EssentialsGuis gui= PixelmonEssentials.essentialsGuisHandler.getGui(playerMP);
        if(gui instanceof TrainerDataGui){
            ((TrainerDataGui) gui).flipLos();
            CustomGuiWrapper guiWrapper= CustomGuiController.getOpenGui(playerMP);
            CustomGuiButtonWrapper button=((CustomGuiButtonWrapper)guiWrapper.getComponent(505));
            if(button.getLabel().equals("Yes")){
                button.setLabel("No");
            }
            else{
                button.setLabel("Yes");
            }
            guiWrapper.update(new PlayerWrapper(playerMP));
        }
    }
}
