package com.pixelmonessentials.common.api.quests.objectives;

import com.pixelmonessentials.common.api.quests.ObjectiveBase;
import com.pixelmonessentials.common.api.quests.ObjectiveData;
import com.pixelmonessentials.common.api.quests.objectiveData.QuestObjectiveData;
import com.pixelmonessentials.common.api.quests.objectiveData.QuestSpeciesObjectiveData;
import com.pixelmonessentials.common.api.quests.objectiveData.QuestTypeObjectiveData;
import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.enums.EnumType;
import net.minecraft.entity.player.EntityPlayerMP;
import noppes.npcs.api.handler.data.IQuestObjective;
import noppes.npcs.controllers.QuestController;
import noppes.npcs.quests.QuestManual;

import java.util.ArrayList;
import java.util.List;

public class PartyObjective extends ObjectiveBase {
    public PartyObjective(){
        super("PARTY");
    }

    @Override
    public void calculateProgress(ArrayList<ObjectiveData> dataList, EntityPlayerMP playerMP){
        if(dataList.size()>0){
            for(ObjectiveData data:dataList){
                QuestManual quest=(QuestManual) QuestController.instance.quests.get(data.getQuestId()).questInterface;
                IQuestObjective objective=quest.getObjectives(playerMP)[data.getObjectiveId()];
                List<Pokemon> party=Pixelmon.storageManager.getParty(playerMP).getTeam();
                int progress=0;
                if (objective.getMaxProgress()==objective.getProgress()){
                    return;
                }
                if(data instanceof QuestSpeciesObjectiveData){
                    for(Pokemon pokemon: party){
                        if(pokemon.getSpecies().name.equalsIgnoreCase(((QuestSpeciesObjectiveData) data).getSpecies())){
                            progress++;
                        }
                        if(progress>=objective.getMaxProgress()){
                            break;
                        }
                    }
                }
                else if(data instanceof QuestTypeObjectiveData){
                    for(Pokemon pokemon: party){
                        ArrayList<EnumType> types=pokemon.getBaseStats().types;
                        for(EnumType type: types){
                            if(type==((QuestTypeObjectiveData) data).getType()){
                                progress++;
                                break;
                            }
                            if(progress>=objective.getMaxProgress()){
                                break;
                            }
                        }
                    }
                }
                objective.setProgress(progress);
            }
        }
    }
}
