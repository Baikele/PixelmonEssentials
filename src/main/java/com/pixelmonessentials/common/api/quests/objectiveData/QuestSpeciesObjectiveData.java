package com.pixelmonessentials.common.api.quests.objectiveData;

import com.google.gson.JsonObject;
import com.pixelmonessentials.PixelmonEssentials;
import com.pixelmonessentials.common.api.gui.EssentialsGuis;
import com.pixelmonessentials.common.api.quests.ObjectiveData;
import com.pixelmonessentials.common.api.quests.ObjectiveDataBase;
import com.pixelmonessentials.common.guis.objectives.CustomObjectiveGui;
import com.pixelmonmod.pixelmon.enums.EnumSpecies;
import net.minecraft.entity.player.EntityPlayerMP;
import noppes.npcs.api.wrapper.gui.CustomGuiLabelWrapper;
import noppes.npcs.api.wrapper.gui.CustomGuiTextFieldWrapper;
import noppes.npcs.api.wrapper.gui.CustomGuiWrapper;
import noppes.npcs.controllers.CustomGuiController;

public class QuestSpeciesObjectiveData extends ObjectiveDataBase {
    String species;

    public QuestSpeciesObjectiveData(){
        super("Species");
        this.addObjective(PixelmonEssentials.questsManager.getObjectiveFromName("CAUGHT"));
        this.addObjective(PixelmonEssentials.questsManager.getObjectiveFromName("KO_POKEMON"));
        this.addObjective(PixelmonEssentials.questsManager.getObjectiveFromName("PARTY"));
        this.addObjective(PixelmonEssentials.questsManager.getObjectiveFromName("SEEN"));
    }

    public QuestSpeciesObjectiveData(int questId, int objectiveId, String species){
        super("Species", questId, objectiveId);
        this.species=species;
        this.addObjective(PixelmonEssentials.questsManager.getObjectiveFromName("CAUGHT"));
        this.addObjective(PixelmonEssentials.questsManager.getObjectiveFromName("KO_POKEMON"));
        this.addObjective(PixelmonEssentials.questsManager.getObjectiveFromName("PARTY"));
        this.addObjective(PixelmonEssentials.questsManager.getObjectiveFromName("SEEN"));
    }

    public String getSpecies(){
        return this.species;
    }

    @Override
    public String getCriteria(){
        return this.species;
    }

    @Override
    public boolean passesCheck(EnumSpecies species){
        return species.name.equalsIgnoreCase(this.getSpecies());
    }

    @Override
    public CustomGuiWrapper addComponents(CustomGuiWrapper gui){
        gui.addLabel(202, "Species:", 60, 170, 128, 20);
        gui.addTextField(400, 110, 170, 100, 20);
        return gui;
    }

    @Override
    public String checkForErrors(EntityPlayerMP playerMP){
        EssentialsGuis gui=PixelmonEssentials.essentialsGuisHandler.getGui(playerMP);
        if(gui instanceof CustomObjectiveGui){
            CustomGuiWrapper guiWrapper= CustomGuiController.getOpenGui(playerMP);
            CustomGuiTextFieldWrapper textfield=(CustomGuiTextFieldWrapper) guiWrapper.getComponent(400);
            if(textfield!=null){
                if(!EnumSpecies.hasPokemonAnyCase(textfield.getText())){
                    return "Not a valid species";
                }
            }
            QuestSpeciesObjectiveData data=new QuestSpeciesObjectiveData(((CustomObjectiveGui) gui).getQuest().id, ((CustomObjectiveGui) gui).getObjective(), textfield.getText().toLowerCase());
            PixelmonEssentials.questsManager.removeObjective(((CustomObjectiveGui) gui).getQuest().id, ((CustomObjectiveGui) gui).getObjective());
            PixelmonEssentials.questsManager.getObjectives().get(((CustomObjectiveGui) gui).getObjectiveIndex()).addQuest(data);
            return "";
        }
        else {
            return "Not the right GUI.";
        }
    }

    @Override
    public ObjectiveData loadFromJson(JsonObject object){
        int questId=-1;
        int objectiveId=-1;
        String species="";
        if(object.has("questId")){
            questId=object.get("questId").getAsInt();
        }
        if(object.has("objectiveId")){
            objectiveId=object.get("objectiveId").getAsInt();
        }
        if(object.has("species")){
            species=object.get("species").getAsString();
        }
        if(questId!=-1 && objectiveId !=-1 && !species.equals("")){
            return new QuestSpeciesObjectiveData(questId, objectiveId, species);
        }
        return null;
    }

    @Override
    public JsonObject saveToJson(){
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("objectiveType", this.getObjectiveType());
        jsonObject.addProperty("questId", this.questId);
        jsonObject.addProperty("objectiveId", this.objectiveId);
        jsonObject.addProperty("species", this.species);
        return jsonObject;
    }
}
