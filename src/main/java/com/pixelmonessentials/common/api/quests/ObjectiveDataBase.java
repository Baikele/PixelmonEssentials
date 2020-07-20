package com.pixelmonessentials.common.api.quests;

import com.google.gson.JsonObject;
import com.pixelmonessentials.PixelmonEssentials;
import com.pixelmonessentials.common.api.gui.EssentialsGuis;
import com.pixelmonessentials.common.api.quests.Objective;
import com.pixelmonessentials.common.api.quests.ObjectiveData;
import com.pixelmonessentials.common.guis.objectives.CustomObjectiveGui;
import com.pixelmonmod.pixelmon.enums.EnumSpecies;
import net.minecraft.entity.player.EntityPlayerMP;
import noppes.npcs.api.wrapper.gui.CustomGuiComponentWrapper;
import noppes.npcs.api.wrapper.gui.CustomGuiWrapper;

import java.util.ArrayList;

public class ObjectiveDataBase implements ObjectiveData {
    String name;
    public int questId;
    public int objectiveId;
    ArrayList<Objective> eligibleObjectives;

    public ObjectiveDataBase(){
        this.eligibleObjectives=new ArrayList<Objective>();
    }

    public ObjectiveDataBase(String name){
        this.name=name;
        this.eligibleObjectives=new ArrayList<Objective>();
    }

    public ObjectiveDataBase(String name, int questId, int objectiveId){
        this.name=name;
        this.questId=questId;
        this.objectiveId=objectiveId;
        this.eligibleObjectives=new ArrayList<Objective>();
    }

    public Objective getParentObjective(){
        ArrayList<Objective> objectives=PixelmonEssentials.questsManager.getObjectives();
        for(Objective objective: objectives){
            if(objective.getQuestFromId(this.questId)!=null){
                ArrayList<ObjectiveData> data=objective.getObjectivesFromQuestId(this.questId);
                for(ObjectiveData objectiveData: data){
                    if(objectiveData.getObjectiveId()==this.objectiveId){
                        return objective;
                    }
                }
            }
        }
        return null;
    }

    public ArrayList<Objective> getEligibleObjectives(){
        return this.eligibleObjectives;
    }

    public String getCriteria(){
        return "";
    }

    public String getObjectiveType(){
        return this.name;
    }

    public int getQuestId(){
        return this.questId;
    }

    public int getObjectiveId(){
        return this.objectiveId;
    }

    public CustomGuiWrapper addComponents(CustomGuiWrapper gui){
        return gui;
    }

    public void addObjective(Objective objective){
        this.eligibleObjectives.add(objective);
    }

    public boolean passesCheck(EnumSpecies species){
        return true;
    }

    public String checkForErrors(EntityPlayerMP playerMP){
        EssentialsGuis gui=PixelmonEssentials.essentialsGuisHandler.getGui(playerMP);
        if(gui instanceof CustomObjectiveGui) {
            PixelmonEssentials.questsManager.removeObjective(((CustomObjectiveGui) gui).getQuest().id, ((CustomObjectiveGui) gui).getObjective());
        }
        return "";
    }

    public ObjectiveData loadFromJson(JsonObject object){
        return null;
    }

    public JsonObject saveToJson(){
        return null;
    }
}
