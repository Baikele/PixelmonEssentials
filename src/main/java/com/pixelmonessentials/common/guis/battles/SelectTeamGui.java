package com.pixelmonessentials.common.guis.battles;

import com.pixelmonessentials.PixelmonEssentials;
import com.pixelmonessentials.common.api.action.ActionData;
import com.pixelmonessentials.common.api.action.datatypes.ActionIdData;
import com.pixelmonessentials.common.api.gui.EssentialsButton;
import com.pixelmonessentials.common.api.gui.EssentialsGuis;
import com.pixelmonessentials.common.api.gui.bases.EssentialsScrollGuiBase;
import com.pixelmonessentials.common.guis.TrainerDataGui;
import com.pixelmonessentials.common.teams.Team;
import com.pixelmonessentials.common.util.EssentialsLogger;
import com.pixelmonessentials.common.util.GuiUtils;
import com.pixelmonessentials.common.util.Reference;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.entities.pixelmon.stats.Moveset;
import com.pixelmonmod.pixelmon.entities.pixelmon.stats.StatsType;
import net.minecraft.entity.player.EntityPlayerMP;
import noppes.npcs.api.wrapper.PlayerWrapper;
import noppes.npcs.api.wrapper.gui.CustomGuiScrollWrapper;
import noppes.npcs.api.wrapper.gui.CustomGuiTextFieldWrapper;
import noppes.npcs.api.wrapper.gui.CustomGuiWrapper;
import noppes.npcs.controllers.CustomGuiController;

public class SelectTeamGui extends EssentialsScrollGuiBase {
    private TrainerDataGui parentGui;
    private int categoryIndex;
    private int teamIndex;
    private Team tempTeam;
    private int teamMember;
    String[] parentChoices;

    public SelectTeamGui(){
        super(1008);
        this.categoryIndex=-1;
        this.teamIndex=-1;
        //buttons 500-501
        this.addButton(new EssentialsButton(500, new ActionData("SAVE_TEAM")));
        this.addButton(new EssentialsButton(501, new ActionData("RETURN_TRAINER")));
        this.addButton(new EssentialsButton(503, new ActionData("RETURN_TEAM")));
        this.addButton(new EssentialsButton(502, new ActionIdData("SHOWN_POKEMON", 510)));
        this.addButton(new EssentialsButton(510, new ActionIdData("SHOWN_POKEMON", 510)));
        this.addButton(new EssentialsButton(511, new ActionIdData("SHOWN_POKEMON", 511)));
        this.addButton(new EssentialsButton(512, new ActionIdData("SHOWN_POKEMON", 512)));
        this.addButton(new EssentialsButton(513, new ActionIdData("SHOWN_POKEMON", 513)));
        this.addButton(new EssentialsButton(514, new ActionIdData("SHOWN_POKEMON", 514)));
        this.addButton(new EssentialsButton(515, new ActionIdData("SHOWN_POKEMON", 515)));
    }

    public int getCategoryIndex(){
        return this.categoryIndex;
    }

    public int getTeamIndex(){
        return this.teamIndex;
    }

    public String[] getParentChoices(){
        return this.parentChoices;
    }

    @Override
    public void init(EntityPlayerMP playerMP){
        CustomGuiWrapper parentGui=CustomGuiController.getOpenGui(playerMP);
        this.parentChoices=new String[3];
        parentChoices[0]=((CustomGuiTextFieldWrapper)parentGui.getComponent(401)).getText();
        parentChoices[1]=((CustomGuiTextFieldWrapper)parentGui.getComponent(402)).getText();
        parentChoices[2]=((CustomGuiTextFieldWrapper)parentGui.getComponent(403)).getText();
        CustomGuiWrapper gui=this.getSelectionGui();
        PlayerWrapper playerWrapper=new PlayerWrapper(playerMP);
        playerWrapper.showCustomGui(gui);
        PixelmonEssentials.essentialsGuisHandler.addOrReplaceGui(playerMP, this);
    }

    public void setTeamMember(int member){
        this.teamMember=member;
    }

    public void setParentGui(TrainerDataGui parentGui){
        this.parentGui=parentGui;
    }

    public TrainerDataGui getParentGui(){
        return this.parentGui;
    }

    public CustomGuiWrapper getSelectionGui(){
        this.teamMember=0;
        CustomGuiWrapper gui=new CustomGuiWrapper(this.getId(), 256, 256, false);
        gui.setBackgroundTexture("customnpcs:textures/gui/bgfilled.png");
        gui.addLabel(200, "Choose a team", 90, 5, 200, 20);
        gui.addScroll(300, 10, 33, 110, 180, PixelmonEssentials.teamManager.getCategoryNames());
        gui.addScroll(301, 130, 33, 110, 180, this.getTeamNames());
        if(this.teamIndex!=-1){
            gui.addButton(500, "Select", 10, 222, 60, 20);
            gui.addButton(502, "View Team", 180, 222, 60, 20);
        }
        gui.addButton(501, "Cancel", 95, 222, 60, 20);
        return gui;
    }

    public CustomGuiWrapper getTeamViewGui(){
        this.tempTeam=PixelmonEssentials.teamManager.categories.get(this.categoryIndex).teams.get(this.teamIndex);
        CustomGuiWrapper gui=new CustomGuiWrapper(this.getId(), 256, 256, false);
        gui.setBackgroundTexture("customnpcs:textures/gui/bgfilled.png");
        gui.addLabel(200, this.tempTeam.getName(), 90, 5, 200, 20);
        gui.addButton(500, "Select", 70, 215, 60, 20);
        gui.addButton(503, "Cancel", 150, 215, 60, 20);
        gui.addLabel(204, Reference.bold+"HP", 130, 30, 200, 20);
        gui.addLabel(205, Reference.bold+"Atk", 130, 43, 200, 20);
        gui.addLabel(206, Reference.bold+"Def", 130, 56, 200, 20);
        gui.addLabel(207, Reference.bold+"SpA", 130, 69, 200, 20);
        gui.addLabel(208, Reference.bold+"SpD", 130, 82, 200, 20);
        gui.addLabel(209, Reference.bold+"Spe", 130, 95, 200, 20);
        gui.addLabel(210, Reference.bold+"Stat", 152, 18, 200, 20);
        gui.addLabel(220, Reference.bold+"EVs", 180, 18, 200, 20);
        gui.addLabel(230, Reference.bold+"IVs", 206, 18, 200, 20);
        for(int i=0;i<this.tempTeam.getMembers().size();i++){
            gui.addTexturedButton(510+i, "", 5, 16+34*i, 32, 32, "pixelmon:textures/gui/battlegui1.png", 0, 160);
            gui.addTexturedRect(100+i, GuiUtils.getPokemonSprite(this.tempTeam.getMember(i)), 5, 24+34*i, 256, 256).setScale(0.125f);
        }
        gui=this.getPokemonInfo(gui);
        return gui;
    }

    public CustomGuiWrapper getPokemonInfo(CustomGuiWrapper gui){
        Pokemon pokemon=this.tempTeam.getMember(this.teamMember);
        gui.addTexturedRect(106, GuiUtils.getPokemonSprite(pokemon), 60, 20, 256, 256).setScale(0.25f);
        gui.addLabel(201, pokemon.getSpecies().name, 60, 85, 200, 20);
        gui.addLabel(202, "Level "+pokemon.getLevel(), 60, 97, 200, 20);
        gui.addLabel(203, pokemon.getNature().getLocalizedName(), 60, 109, 200, 20);
        if(pokemon.getHeldItem()!=null){
            if(pokemon.getHeldItem().getDisplayName().equals("Air")){
                gui.addLabel(244, "None", 60, 121, 200, 20);
            }
            else {
                gui.addLabel(244, pokemon.getHeldItem().getDisplayName(), 60, 121, 200, 20);
            }
        }
        else{
            gui.addLabel(244, "None", 60, 121, 200, 20);
        }
        gui.addLabel(214, pokemon.getStats().hp+"", 155, 30, 200, 20);
        gui.addLabel(215, pokemon.getStats().attack+"", 155, 43, 200, 20);
        gui.addLabel(216, pokemon.getStats().defence+"", 155, 56, 200, 20);
        gui.addLabel(217, pokemon.getStats().specialAttack+"", 155, 69, 200, 20);
        gui.addLabel(218, pokemon.getStats().specialDefence+"", 155, 82, 200, 20);
        gui.addLabel(219, pokemon.getStats().speed+"", 155, 95, 200, 20);
        gui.addLabel(224, pokemon.getStats().evs.hp+"", 181, 30, 200, 20);
        gui.addLabel(225, pokemon.getStats().evs.attack+"", 181, 43, 200, 20);
        gui.addLabel(226, pokemon.getStats().evs.defence+"", 181, 56, 200, 20);
        gui.addLabel(227, pokemon.getStats().evs.specialAttack+"", 181, 69, 200, 20);
        gui.addLabel(228, pokemon.getStats().evs.specialDefence+"", 181, 82, 200, 20);
        gui.addLabel(229, pokemon.getStats().evs.speed+"", 181, 95, 200, 20);
        gui.addLabel(234, pokemon.getStats().ivs.hp+"", 209, 30, 200, 20);
        gui.addLabel(235, pokemon.getStats().ivs.attack+"", 209, 43, 200, 20);
        gui.addLabel(236, pokemon.getStats().ivs.defence+"", 209, 56, 200, 20);
        gui.addLabel(237, pokemon.getStats().ivs.specialAttack+"", 209, 69, 200, 20);
        gui.addLabel(238, pokemon.getStats().ivs.specialDefence+"", 209, 82, 200, 20);
        gui.addLabel(239, pokemon.getStats().ivs.speed+"", 209, 95, 200, 20);
        Moveset moves=pokemon.getMoveset();
        for(int i=0;i<moves.size();i++){
            gui.addLabel(240+i, moves.get(i).getActualMove().getAttackName(), 50+100*(i%2), 150+30*(i/2), 200, 20);
        }
        return gui;
    }

    @Override
    public void setIndex(int id, String selection){
        if(id==300){
            String[] names=PixelmonEssentials.teamManager.getCategoryNames();
            for(int i=0;i<names.length;i++){
                if(names[i].equals(selection)){
                    this.categoryIndex=i;
                    return;
                }
            }
        }
        else if(id==301){
            String[] names=this.getTeamNames();
            for(int i=0;i<names.length;i++){
                if(names[i].equals(selection)){
                    this.teamIndex=i;
                    return;
                }
            }
        }
    }

    @Override
    public void updateScroll(int id, EntityPlayerMP playerMP){
        CustomGuiWrapper gui=CustomGuiController.getOpenGui(playerMP);
        gui.removeComponent(300);
        gui.removeComponent(301);
        gui.removeComponent(502);
        gui.removeComponent(500);
        gui.addScroll(300, 10, 33, 110, 180, PixelmonEssentials.teamManager.getCategoryNames()).setDefaultSelection(this.categoryIndex);
        if(id==300){
            this.teamIndex=-1;
            gui.addScroll(301, 130, 33, 110, 180, this.getTeamNames());
        }
        else if(id==301){
            gui.addScroll(301, 130, 33, 110, 180, this.getTeamNames()).setDefaultSelection(this.teamIndex);
            gui.addButton(500, "Select", 10, 222, 60, 20);
            gui.addButton(502, "View Team", 180, 222, 60, 20);
        }
        PlayerWrapper playerWrapper=new PlayerWrapper(playerMP);
        gui.update(playerWrapper);
    }

    public String[] getTeamNames(){
        if(this.categoryIndex==-1){
            return new String[0];
        }
        return PixelmonEssentials.teamManager.categories.get(this.categoryIndex).getTeamNames();
    }
}
