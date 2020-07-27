package com.pixelmonessentials.common.api.action.types.trainerActions;

import com.pixelmonessentials.PixelmonEssentials;
import com.pixelmonessentials.common.api.action.ActionBase;
import com.pixelmonessentials.common.api.action.ActionData;
import com.pixelmonessentials.common.api.gui.EssentialsGuis;
import com.pixelmonessentials.common.guis.TrainerDataGui;
import com.pixelmonessentials.common.guis.battles.SelectTeamGui;
import com.pixelmonessentials.common.teams.TeamCategory;
import com.pixelmonessentials.common.util.EssentialsLogger;
import net.minecraft.entity.player.EntityPlayerMP;
import noppes.npcs.api.wrapper.PlayerWrapper;
import noppes.npcs.api.wrapper.gui.CustomGuiTextFieldWrapper;
import noppes.npcs.api.wrapper.gui.CustomGuiWrapper;
import noppes.npcs.controllers.CustomGuiController;

public class SaveTeamAction extends ActionBase {
    public SaveTeamAction(){
        super("SAVE_TEAM");
    }

    @Override
    public void doAction(EntityPlayerMP playerMP, ActionData data){
        EssentialsGuis gui= PixelmonEssentials.essentialsGuisHandler.getGui(playerMP);
        if(gui instanceof SelectTeamGui){
            String[] previousChoices=((SelectTeamGui) gui).getParentChoices();
            TrainerDataGui dataGui=((SelectTeamGui) gui).getParentGui();
            TeamCategory category=PixelmonEssentials.teamManager.categories.get(((SelectTeamGui) gui).getCategoryIndex());
            dataGui.saveTeam(category.getName(), category.teams.get(((SelectTeamGui) gui).getTeamIndex()).getName());
            CustomGuiWrapper guiWrapper=dataGui.getGui();
            EssentialsLogger.info(previousChoices[0]);
            ((CustomGuiTextFieldWrapper)guiWrapper.getComponent(401)).setText(previousChoices[0]);
            ((CustomGuiTextFieldWrapper)guiWrapper.getComponent(402)).setText(previousChoices[1]);
            ((CustomGuiTextFieldWrapper)guiWrapper.getComponent(403)).setText(previousChoices[2]);
            new PlayerWrapper(playerMP).showCustomGui(guiWrapper);
            PixelmonEssentials.essentialsGuisHandler.addOrReplaceGui(playerMP, dataGui);
        }
    }
}
