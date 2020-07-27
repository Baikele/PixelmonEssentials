package com.pixelmonessentials.common.api.action.types.trainerActions;

import com.pixelmonessentials.PixelmonEssentials;
import com.pixelmonessentials.common.api.action.ActionBase;
import com.pixelmonessentials.common.api.action.ActionData;
import com.pixelmonessentials.common.api.action.datatypes.ActionIdData;
import com.pixelmonessentials.common.api.gui.EssentialsGuis;
import com.pixelmonessentials.common.guis.battles.SelectTeamGui;
import net.minecraft.entity.player.EntityPlayerMP;
import noppes.npcs.api.wrapper.PlayerWrapper;
import noppes.npcs.api.wrapper.gui.CustomGuiWrapper;

public class SetShownPokemonAction extends ActionBase {
    public SetShownPokemonAction(){
        super("SHOWN_POKEMON");
    }

    @Override
    public void doAction(EntityPlayerMP playerMP, ActionData data){
        if(data instanceof ActionIdData){
            EssentialsGuis gui= PixelmonEssentials.essentialsGuisHandler.getGui(playerMP);
            if(gui instanceof SelectTeamGui){
                ((SelectTeamGui) gui).setTeamMember(((ActionIdData) data).getId()-510);
                CustomGuiWrapper guiWrapper=((SelectTeamGui) gui).getTeamViewGui();
                PlayerWrapper playerWrapper=new PlayerWrapper(playerMP);
                guiWrapper.update(playerWrapper);
            }
        }
    }
}
