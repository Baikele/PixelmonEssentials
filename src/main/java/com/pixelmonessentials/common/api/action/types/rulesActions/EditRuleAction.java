package com.pixelmonessentials.common.api.action.types.rulesActions;

import com.pixelmonessentials.PixelmonEssentials;
import com.pixelmonessentials.common.api.action.ActionBase;
import com.pixelmonessentials.common.api.action.ActionData;
import com.pixelmonessentials.common.api.gui.EssentialsGuis;
import com.pixelmonessentials.common.guis.battles.CustomRulesGui;
import net.minecraft.entity.player.EntityPlayerMP;
import noppes.npcs.api.wrapper.PlayerWrapper;
import noppes.npcs.api.wrapper.gui.CustomGuiTextFieldWrapper;
import noppes.npcs.api.wrapper.gui.CustomGuiWrapper;

public class EditRuleAction extends ActionBase {
    public EditRuleAction(){
        super("EDIT_RULE");
    }

    @Override
    public void doAction(EntityPlayerMP playerMP, ActionData data){
        EssentialsGuis gui= PixelmonEssentials.essentialsGuisHandler.getGui(playerMP);
        if(gui instanceof CustomRulesGui){
            ((CustomRulesGui) gui).setTempRules(PixelmonEssentials.customRulesManager.rulesets.get(((CustomRulesGui) gui).getIndex()));
            PixelmonEssentials.customRulesManager.rulesets.remove(PixelmonEssentials.customRulesManager.rulesets.get(((CustomRulesGui) gui).getIndex()));
            CustomGuiWrapper guiWrapper=((CustomRulesGui) gui).getNewRuleGui();
            guiWrapper.update(new PlayerWrapper(playerMP));
        }
    }
}
