package com.pixelmonessentials.common.guis.battles;

import com.pixelmonessentials.PixelmonEssentials;
import com.pixelmonessentials.common.api.action.ActionData;
import com.pixelmonessentials.common.api.gui.EssentialsButton;
import com.pixelmonessentials.common.guis.TrainerDataGui;
import com.pixelmonmod.pixelmon.battles.rules.BattleRules;
import net.minecraft.entity.player.EntityPlayerMP;
import noppes.npcs.api.wrapper.PlayerWrapper;
import noppes.npcs.api.wrapper.gui.CustomGuiWrapper;
import noppes.npcs.controllers.CustomGuiController;

public class TrainerRulesGui extends CustomRulesGui {
    TrainerDataGui parentGui;

    public TrainerRulesGui(){
        super(1009);
        this.addButton(new EssentialsButton(505, new ActionData("SAVE_RULES")));
        this.addButton(new EssentialsButton(506, new ActionData("CANCEL_RULES")));
    }

    public TrainerDataGui getParentGui(){
        return this.parentGui;
    }

    @Override
    public void init(EntityPlayerMP playerMP){
        if(parentGui==null){
            parentGui=(TrainerDataGui) PixelmonEssentials.essentialsGuisHandler.getGui(playerMP);
        }
        super.init(playerMP);
    }

    @Override
    public CustomGuiWrapper getBaseGui(){
        CustomGuiWrapper gui=super.getBaseGui();
        if(this.getIndex()!=-1){
            gui.addButton(505, "Select", 10, 219, 48, 20);
        }
        gui.addButton(506, "Cancel", 61, 219, 48, 20);
        return gui;
    }
}
