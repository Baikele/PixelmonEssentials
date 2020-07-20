package com.pixelmonessentials.common.api.quests.objectiveData;

import com.google.gson.JsonObject;
import com.pixelmonessentials.PixelmonEssentials;
import com.pixelmonessentials.common.api.gui.EssentialsGuis;
import com.pixelmonessentials.common.api.quests.ObjectiveData;
import com.pixelmonessentials.common.api.quests.ObjectiveDataBase;
import com.pixelmonessentials.common.api.quests.objectiveData.QuestObjectiveData;
import com.pixelmonessentials.common.guis.objectives.CustomObjectiveGui;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.enums.EnumSpecies;
import com.pixelmonmod.pixelmon.enums.EnumType;
import net.minecraft.entity.player.EntityPlayerMP;
import noppes.npcs.api.wrapper.gui.CustomGuiScrollWrapper;
import noppes.npcs.api.wrapper.gui.CustomGuiTextFieldWrapper;
import noppes.npcs.api.wrapper.gui.CustomGuiWrapper;
import noppes.npcs.controllers.CustomGuiController;

import java.util.List;

public class QuestTypeObjectiveData extends ObjectiveDataBase {
    EnumType type;

    public QuestTypeObjectiveData(){
        super("Type");
        this.addObjective(PixelmonEssentials.questsManager.getObjectiveFromName("CAUGHT"));
        this.addObjective(PixelmonEssentials.questsManager.getObjectiveFromName("KO_POKEMON"));
        this.addObjective(PixelmonEssentials.questsManager.getObjectiveFromName("PARTY"));
        this.addObjective(PixelmonEssentials.questsManager.getObjectiveFromName("SEEN"));
    }

    public QuestTypeObjectiveData(int questId, int objectiveId, EnumType type){
        super("Type", questId, objectiveId);
        this.type=type;
        this.addObjective(PixelmonEssentials.questsManager.getObjectiveFromName("CAUGHT"));
        this.addObjective(PixelmonEssentials.questsManager.getObjectiveFromName("KO_POKEMON"));
        this.addObjective(PixelmonEssentials.questsManager.getObjectiveFromName("PARTY"));
        this.addObjective(PixelmonEssentials.questsManager.getObjectiveFromName("SEEN"));
    }

    public EnumType getType(){
        return this.type;
    }

    @Override
    public String getCriteria(){
        return this.type.getLocalizedName();
    }

    @Override
    public boolean passesCheck(EnumSpecies species){
        List<EnumType> types=species.getBaseStats().types;
        for(EnumType type:types){
            if(type==this.type){
                return true;
            }
        }
        return false;
    }

    @Override
    public String checkForErrors(EntityPlayerMP playerMP){
        EssentialsGuis gui=PixelmonEssentials.essentialsGuisHandler.getGui(playerMP);
        if(gui instanceof CustomObjectiveGui){
            if(((CustomObjectiveGui) gui).getExtraScrolls().containsKey(302)){
                CustomGuiScrollWrapper scroll=(CustomGuiScrollWrapper) CustomGuiController.getOpenGui(playerMP).getComponent(302);
                String type=scroll.getList()[((CustomObjectiveGui) gui).getExtraScrolls().get(302)];
                QuestTypeObjectiveData data=new QuestTypeObjectiveData(((CustomObjectiveGui) gui).getQuest().id, ((CustomObjectiveGui) gui).getObjective(), EnumType.parseType(type));
                PixelmonEssentials.questsManager.removeObjective(((CustomObjectiveGui) gui).getQuest().id, ((CustomObjectiveGui) gui).getObjective());
                PixelmonEssentials.questsManager.getObjectives().get(((CustomObjectiveGui) gui).getObjectiveIndex()).addQuest(data);
                return "";
            }
        }
        return "No type was selected.";
    }

    @Override
    public CustomGuiWrapper addComponents(CustomGuiWrapper gui){
        String[] types=new String[EnumType.getAllTypes().size()-1];
        int index=0;
        for(EnumType type:EnumType.getAllTypes()){
            if(!type.getName().equalsIgnoreCase("???")){
                types[index++]=type.getName();
            }
        }
        gui.addLabel(202, "Type:", 70, 160, 128, 20);
        gui.addScroll(302, 110, 140, 100, 80, types);
        return gui;
    }

    @Override
    public ObjectiveData loadFromJson(JsonObject object){
        int questId=-1;
        int objectiveId=-1;
        String type="";
        if(object.has("questId")){
            questId=object.get("questId").getAsInt();
        }
        if(object.has("objectiveId")){
            objectiveId=object.get("objectiveId").getAsInt();
        }
        if(object.has("type")){
            type=object.get("type").getAsString();
        }
        if(questId!=-1 && objectiveId !=-1 && !type.equals("")){
            return new QuestTypeObjectiveData(questId, objectiveId, EnumType.parseType(type));
        }
        return null;
    }

    @Override
    public JsonObject saveToJson(){
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("objectiveType", this.getObjectiveType());
        jsonObject.addProperty("questId", this.questId);
        jsonObject.addProperty("objectiveId", this.objectiveId);
        jsonObject.addProperty("type", this.type.getName());
        return jsonObject;
    }
}
