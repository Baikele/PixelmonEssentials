package com.pixelmonessentials.common.quests;

import com.google.gson.*;
import com.google.gson.stream.JsonWriter;
import com.pixelmonessentials.PixelmonEssentials;
import com.pixelmonessentials.common.api.quests.Objective;
import com.pixelmonessentials.common.api.quests.ObjectiveData;
import com.pixelmonessentials.common.api.quests.objectiveData.QuestObjectiveData;
import com.pixelmonessentials.common.api.quests.objectiveData.QuestSpeciesObjectiveData;
import com.pixelmonessentials.common.api.quests.objectiveData.QuestTypeObjectiveData;
import com.pixelmonessentials.common.api.quests.objectives.CaughtObjective;
import com.pixelmonessentials.common.api.quests.objectives.DefeatPokemonObjective;
import com.pixelmonessentials.common.api.quests.objectives.PartyObjective;
import net.minecraft.entity.player.EntityPlayerMP;
import noppes.npcs.api.handler.data.IQuest;
import noppes.npcs.api.wrapper.PlayerWrapper;
import noppes.npcs.controllers.QuestController;
import noppes.npcs.controllers.data.Quest;
import noppes.npcs.controllers.data.QuestCategory;
import noppes.npcs.quests.QuestManual;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

public class QuestsManager {
    public ArrayList<Objective> objectives=new ArrayList<Objective>();
    public ArrayList<ObjectiveData> dataTypes=new ArrayList<ObjectiveData>();
    public File questFile;

    public QuestsManager(){
    }

    public void loadQuests(){
        this.questFile=new File(PixelmonEssentials.configFolder, "quests.json");
        try {
            if (!this.questFile.exists()) {
                this.questFile.createNewFile();
            } else {
                loadQuestData();
            }
        }catch (IOException e) {
                e.printStackTrace();
            }
    }

    public void loadQuestData() throws FileNotFoundException {
        InputStream inputStream=new FileInputStream(this.questFile);
        JsonArray array=new JsonParser().parse(new InputStreamReader(inputStream, StandardCharsets.UTF_8)).getAsJsonArray();
        for(JsonElement json:array){
            JsonObject object=json.getAsJsonObject();
            if(object.has("objective")&&object.has("quests")){
                Objective objective=this.getObjectiveFromName(object.get("objective").getAsString());
                JsonArray quests=object.getAsJsonArray("quests");
                for(JsonElement element:quests){
                    JsonObject quest=element.getAsJsonObject();
                    ObjectiveData objectiveType=this.getObjectiveDataFromName(quest.get("objectiveType").getAsString());
                    objective.addQuest(objectiveType.loadFromJson(quest));
                }
            }
        }
    }

    public void save(){
        try {
            JsonWriter writer=new JsonWriter(new FileWriter(this.questFile));
            writer.setIndent("\t");
            writer.beginArray();
            for(Objective objective:this.objectives){
                writer.beginObject();
                writer.name("objective").value(objective.getName());
                writer.name("quests");
                writer.beginArray();
                for(ObjectiveData data:objective.getQuests()){
                    JsonObject jsonObject=data.saveToJson();
                    writer.beginObject();
                    Iterator<Map.Entry<String, JsonElement>> properties=jsonObject.entrySet().iterator();
                    while(properties.hasNext()){
                        Map.Entry<String, JsonElement> elementEntry=properties.next();
                        JsonPrimitive element=elementEntry.getValue().getAsJsonPrimitive();
                        if(element.isBoolean()){
                            writer.name(elementEntry.getKey()).value(element.getAsBoolean());
                        }
                        else if(element.isNumber()){
                            writer.name(elementEntry.getKey()).value(element.getAsNumber());
                        }
                        else if(element.isString()){
                            writer.name(elementEntry.getKey()).value(element.getAsString());
                        }
                    }
                    writer.endObject();
                }
                writer.endArray();
                writer.endObject();
            }
            writer.endArray();
            writer.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public ArrayList<Objective> getObjectives(){
        return this.objectives;
    }

    public void removeObjective(int quest, int objective){
        ArrayList<ObjectiveData> objectiveData=this.getObjectivesFromRealQuest(QuestController.instance.quests.get(quest));
        for(ObjectiveData data:objectiveData){
            if(data.getObjectiveId()==objective){
                for(Objective objectiveType:this.getObjectives()){
                    if(objectiveType.getQuests().contains(data)){
                        objectiveType.removeQuest(data);
                        return;
                    }
                }
            }
        }
    }

    public void getQuestsWithDummyObjective(){
        ArrayList<Quest> questsWithDummy=new ArrayList<Quest>();
        Collection<QuestCategory> categories=QuestController.instance.categories.values();
        for(QuestCategory category:categories){
            Collection<Quest> quests=category.quests.values();
            for(Quest quest:quests){
                if(quest.questInterface instanceof QuestManual){
                    questsWithDummy.add(quest);
                }
            }
        }
    }

    public ArrayList<ObjectiveData> getObjectivesFromRealQuest(Quest quest){
        ArrayList<ObjectiveData> objectives=new ArrayList<>();
        QuestManual manual=(QuestManual) quest.questInterface;
        for(int i=0;i<manual.manuals.size();i++){
            for(Objective objective:this.objectives){
                ArrayList<ObjectiveData> data=objective.getObjectivesFromQuestId(quest.getId());
                if(data!=null){
                    for(ObjectiveData objectiveData:data){
                        objectives.add(objectiveData);
                    }
                }
            }
        }
        return objectives;
    }

    public ArrayList<ObjectiveData> getObjectivesFromQuest(IQuest quest, EntityPlayerMP playerMP){
        PlayerWrapper playerWrapper=new PlayerWrapper(playerMP);
        ArrayList<ObjectiveData> objectives=new ArrayList<>();
        for(int i=0;i<quest.getObjectives(playerWrapper).length;i++){
            for(Objective objective:this.objectives){
                ArrayList<ObjectiveData> data=objective.getObjectivesFromQuestId(quest.getId());
                if(data!=null){
                    for(ObjectiveData objectiveData:data){
                        objectives.add(objectiveData);
                    }
                }
            }
        }
        return objectives;
    }

    public ObjectiveData getObjectiveDataFromName(String name){
        for(ObjectiveData data:this.dataTypes){
            if(data.getObjectiveType().equals(name)){
                return data;
            }
        }
        return null;
    }

    public Objective getObjectiveFromName(String name){
        for(Objective objective:this.objectives){
            if(objective.getName().equalsIgnoreCase(name)){
                return objective;
            }
        }
        return null;
    }

    public void init(){
        this.objectives.add(new CaughtObjective());
        this.objectives.add(new PartyObjective());
        this.objectives.add(new DefeatPokemonObjective());

        this.dataTypes.add(new QuestObjectiveData());
        this.dataTypes.add(new QuestSpeciesObjectiveData());
        this.dataTypes.add(new QuestTypeObjectiveData());
    }
}
