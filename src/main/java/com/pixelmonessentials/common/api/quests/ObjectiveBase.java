package com.pixelmonessentials.common.api.quests;

import com.pixelmonessentials.PixelmonEssentials;
import com.pixelmonessentials.common.api.quests.objectiveData.QuestObjectiveData;
import net.minecraft.entity.player.EntityPlayerMP;

import java.util.ArrayList;

public class ObjectiveBase implements Objective {
    private String name;
    private ArrayList<ObjectiveData> quests;

    public ObjectiveBase(String name){
        this.name=name;
        this.quests=new ArrayList<ObjectiveData>();
    }

    public String getName(){
        return this.name;
    }

    public ArrayList<ObjectiveData> getQuests(){
        return this.quests;
    }

    public ArrayList<ObjectiveData> getObjectivesFromQuestId(int id){
        ArrayList<ObjectiveData> objectives=new ArrayList<>();
        for(ObjectiveData data:this.quests){
            if(data.getQuestId()==id){
                objectives.add(data);
            }
        }
        return objectives;
    }

    public ArrayList<ObjectiveData> getQuestFromId(int id){
        ArrayList<ObjectiveData> data=new ArrayList<ObjectiveData>();
        for(ObjectiveData objectiveData: this.quests){
            if(objectiveData.getQuestId()==id){
                data.add(objectiveData);
            }
        }
        return data;
    }

    public void addQuest(ObjectiveData data){
        this.quests.add(data);
    }

    public void removeQuest(ObjectiveData data){
        this.quests.remove(data);
    }

    public void calculateProgress(ArrayList<ObjectiveData> data, EntityPlayerMP player){
    }

    public ArrayList<ObjectiveData> getCompatibleObjectives(){
        ArrayList<ObjectiveData> dataTypes=new ArrayList<ObjectiveData>();
        for(ObjectiveData data:PixelmonEssentials.questsManager.dataTypes){
            if(data.getEligibleObjectives().contains(this)){
                dataTypes.add(data);
            }
        }
        return dataTypes;
    }
}
