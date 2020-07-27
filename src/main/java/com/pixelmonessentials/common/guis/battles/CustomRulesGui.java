package com.pixelmonessentials.common.guis.battles;

import com.pixelmonessentials.PixelmonEssentials;
import com.pixelmonessentials.common.api.action.ActionData;
import com.pixelmonessentials.common.api.gui.EssentialsButton;
import com.pixelmonessentials.common.api.gui.EssentialsPersistentGui;
import com.pixelmonessentials.common.api.gui.EssentialsScrollGui;
import com.pixelmonessentials.common.api.gui.bases.EssentialsFormGuiBase;
import com.pixelmonessentials.common.battles.rules.CustomRules;
import com.pixelmonmod.pixelmon.battles.rules.BattleRules;
import com.pixelmonmod.pixelmon.comm.packetHandlers.OpenScreen;
import com.pixelmonmod.pixelmon.enums.EnumGuiScreen;
import net.minecraft.entity.player.EntityPlayerMP;
import noppes.npcs.api.wrapper.PlayerWrapper;
import noppes.npcs.api.wrapper.gui.CustomGuiScrollWrapper;
import noppes.npcs.api.wrapper.gui.CustomGuiTextFieldWrapper;
import noppes.npcs.api.wrapper.gui.CustomGuiWrapper;
import noppes.npcs.controllers.CustomGuiController;

public class CustomRulesGui extends EssentialsFormGuiBase implements EssentialsScrollGui, EssentialsPersistentGui {
    private EntityPlayerMP playerMP;
    private int index=-1;
    private CustomRules tempRules;

    public CustomRulesGui(int id){
        super(id, 503);
        this.addButton(new EssentialsButton(500, new ActionData("NEW_RULE")));
        this.addButton(new EssentialsButton(501, new ActionData("EDIT_RULE")));
        this.addButton(new EssentialsButton(502, new ActionData("REMOVE_RULE")));
        this.addButton(new EssentialsButton(504, new ActionData("RELOAD_RULES")));
    }

    public CustomRulesGui(){
        super(1007, 503);
        this.addButton(new EssentialsButton(500, new ActionData("NEW_RULE")));
        this.addButton(new EssentialsButton(501, new ActionData("EDIT_RULE")));
        this.addButton(new EssentialsButton(502, new ActionData("REMOVE_RULE")));
        this.addButton(new EssentialsButton(504, new ActionData("RELOAD_RULES")));
    }

    @Override
    public void init(EntityPlayerMP playerMP){
        this.playerMP=playerMP;
        this.index=-1;
        this.addCustomRules();
        PlayerWrapper playerWrapper=new PlayerWrapper(playerMP);
        CustomGuiWrapper gui=this.getBaseGui();
        playerWrapper.showCustomGui(gui);
        PixelmonEssentials.essentialsGuisHandler.addOrReplaceGui(playerMP, this);
    }

    public void setTempRules(CustomRules rules){
        this.tempRules=rules;
    }

    public CustomRules getTempRules(){
        return this.tempRules;
    }

    public int getIndex(){
        return this.index;
    }

    public CustomGuiWrapper getBaseGui(){
        CustomGuiWrapper gui=new CustomGuiWrapper(this.getId(), 256, 256, false);
        gui.setBackgroundTexture("customnpcs:textures/gui/bgfilled.png");
        gui.addLabel(200, "Custom Rules", 100, 3, 128, 20);
        if(this.index!=-1){
            gui.addScroll(300, 10, 23, 100, 150, PixelmonEssentials.customRulesManager.getRuleNames()).setDefaultSelection(this.index);
        }
        else{
            gui.addScroll(300, 10, 23, 100, 150, PixelmonEssentials.customRulesManager.getRuleNames());
        }
        gui.addButton(500, "Add", 10, 175, 48, 20);
        return gui;
    }

    public CustomGuiWrapper addRulesInfo(CustomGuiWrapper gui){
        if(this.index!=-1){
            CustomRules ruleset=PixelmonEssentials.customRulesManager.rulesets.get(this.index);
            BattleRules rules=ruleset.getRules();
            gui.addLabel(201, ruleset.getName(), 140, 25, 128, 20);
            gui.addLabel(202, rules.battleType.getLocalizedName(), 115, 40, 128, 20);
            gui.addLabel(203, "("+rules.numPokemon+" Pokemon)", 185, 40, 128, 20, 50176);
            gui.addLabel(204, "Level cap:", 115, 52, 128, 20);
            gui.addLabel(205, rules.levelCap+"", 170, 52, 128, 20);
            if(rules.raiseToCap){
                gui.addLabel(206, "(Raised)", 192, 52, 128, 20, 50176);
            }
            if(rules.fullHeal){
                gui.addLabel(207, "Team healed", 115, 64, 128, 20);
            }
            if(rules.teamPreview){
                gui.addLabel(208, "Team Preview on", 115, 76, 128, 20);
                gui.addLabel(209, "("+rules.teamSelectTime+"s)", 200, 76, 128, 20, 50176);
            }
            gui.addLabel(210, "Tier:", 115, 88, 128, 20);
            gui.addLabel(212, rules.tier.getLocalizedName(), 140, 88, 128, 20);
            gui.addLabel(213, "Turn Timer:", 115, 100, 128, 20);
            gui.addLabel(214, rules.turnTime+"", 175, 100, 128, 20);
            gui.addLabel(215, "Clauses:", 160, 115, 128, 20);
            gui.addScroll(301, 125, 130, 120, 115, this.getClauseNames(rules));
            gui.addButton(501, "Edit", 62, 175, 48, 20);
            gui.addButton(502, "Remove", 10, 197, 100, 20);
        }
        return gui;
    }

    public CustomGuiWrapper getNewRuleGui(){
        CustomGuiWrapper gui = new CustomGuiWrapper(this.getId(), 150, 85, false);
        gui.setBackgroundTexture("customnpcs:textures/gui/bgfilled.png");
        gui.addLabel(200, "New Rule", 60, 3, 128, 20);
        gui.addLabel(201, "Name:", 7, 25, 128, 20);
        if(this.tempRules!=null){
            gui.addTextField(400, 40, 25, 100, 20).setText(this.tempRules.getName());
        }
        else {
            gui.addTextField(400, 40, 25, 100, 20);
        }
        gui.addButton(503, "Submit", 28, 53, 48, 20);
        gui.addButton(504, "Cancel", 85, 53, 48, 20);
        return gui;
    }

    @Override
    public void setIndex(int id, String selection){
        if(id==300){
            CustomGuiScrollWrapper scroll=(CustomGuiScrollWrapper) CustomGuiController.getOpenGui(playerMP).getComponent(300);
            for(int i=0;i<scroll.getList().length;i++){
                if(scroll.getList()[i].equals(selection)){
                    this.index=i;
                    return;
                }
            }
        }
    }

    @Override
    public void updateScroll(int id, EntityPlayerMP playerMP){
        if(id==300&&this.index!=-1){
            CustomGuiWrapper gui=this.getBaseGui();
            gui=this.addRulesInfo(gui);
            gui.update(new PlayerWrapper(playerMP));
        }
    }

    public String[] getClauseNames(BattleRules rules){
        String[] clauses=new String[rules.getClauseList().size()];
        for(int i=0;i<clauses.length;i++){
            clauses[i]=rules.getClauseList().get(i).getLocalizedName();
        }
        return clauses;
    }

    public void addCustomRules(){
        if(this.tempRules==null){
            return;
        }
        if(!this.tempRules.getName().equals("")&&this.tempRules.getRules()!=null){
            PixelmonEssentials.customRulesManager.rulesets.add(this.tempRules);
        }
        this.tempRules=null;
    }

    public void addCustomRules(BattleRules rules){
        this.tempRules.setRules(rules);
        PixelmonEssentials.customRulesManager.rulesets.add(this.tempRules);
        this.tempRules=null;
        this.init(this.playerMP);
    }

    @Override
    public void sendForm(EntityPlayerMP playerMP){
        CustomGuiWrapper gui=CustomGuiController.getOpenGui(playerMP);
        String name=((CustomGuiTextFieldWrapper) gui.getComponent(400)).getText();
        if(name==null){
            gui.addLabel(202, "No name was entered!", 22, 71, 128, 20, 16711680);
            gui.update(new PlayerWrapper(playerMP));
            return;
        }
        String[] rules=PixelmonEssentials.customRulesManager.getRuleNames();
        for(int i=0;i<rules.length;i++){
            if(rules[i].equals(name)){
                gui.addLabel(202, "Name is already taken!", 22, 71, 128, 20, 16711680);
                gui.update(new PlayerWrapper(playerMP));
                return;
            }
        }
        if(this.tempRules==null){
            this.tempRules=new CustomRules();
        }
        this.tempRules.setName(name);
        OpenScreen.open(playerMP, EnumGuiScreen.BattleRulesPlayer, 999, 1);
    }
}
