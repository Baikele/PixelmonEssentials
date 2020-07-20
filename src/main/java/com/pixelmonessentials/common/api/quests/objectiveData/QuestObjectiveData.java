package com.pixelmonessentials.common.api.quests.objectiveData;

import com.google.gson.JsonObject;
import com.pixelmonessentials.PixelmonEssentials;
import com.pixelmonessentials.common.api.quests.Objective;
import com.pixelmonessentials.common.api.quests.ObjectiveData;
import com.pixelmonessentials.common.api.quests.ObjectiveDataBase;

import java.util.ArrayList;

public class QuestObjectiveData extends ObjectiveDataBase {

    public QuestObjectiveData(){
        super("Any");
        this.addObjective(PixelmonEssentials.questsManager.getObjectiveFromName("CAUGHT"));
        this.addObjective(PixelmonEssentials.questsManager.getObjectiveFromName("KO_POKEMON"));
        this.addObjective(PixelmonEssentials.questsManager.getObjectiveFromName("PARTY"));
        this.addObjective(PixelmonEssentials.questsManager.getObjectiveFromName("SEEN"));
    }

    public QuestObjectiveData(int questId, int objectiveId){
        super("any");
        this.questId=questId;
        this.objectiveId=objectiveId;
    }

    @Override
    public ObjectiveData loadFromJson(JsonObject object){
        int questId=-1;
        int objectiveId=-1;
        if(object.has("questId")){
            questId=object.get("questId").getAsInt();
        }
        if(object.has("objectiveId")){
            objectiveId=object.get("objectiveId").getAsInt();
        }
        if(questId!=-1 && objectiveId !=-1){
            return new QuestObjectiveData(questId, objectiveId);
        }
        return null;
    }

    @Override
    public JsonObject saveToJson(){
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("objectiveType", this.getObjectiveType());
        jsonObject.addProperty("questId", this.questId);
        jsonObject.addProperty("objectiveId", this.objectiveId);
        return jsonObject;
    }
}
