package com.pixelmonessentials.common.api.quests.objectives;

import com.pixelmonessentials.common.api.quests.Objective;
import com.pixelmonessentials.common.api.quests.ObjectiveBase;
import com.pixelmonessentials.common.api.quests.ObjectiveData;
import com.pixelmonessentials.common.api.quests.objectiveData.QuestObjectiveData;
import com.pixelmonessentials.common.api.quests.objectiveData.QuestSpeciesObjectiveData;
import com.pixelmonessentials.common.api.quests.objectiveData.QuestTypeObjectiveData;
import com.pixelmonessentials.common.util.EssentialsLogger;
import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.enums.EnumSpecies;
import com.pixelmonmod.pixelmon.enums.EnumType;
import com.pixelmonmod.pixelmon.pokedex.EnumPokedexRegisterStatus;
import com.pixelmonmod.pixelmon.pokedex.Pokedex;
import net.minecraft.entity.player.EntityPlayerMP;
import noppes.npcs.api.handler.data.IQuestObjective;
import noppes.npcs.controllers.QuestController;
import noppes.npcs.quests.QuestManual;

import java.util.ArrayList;
import java.util.Map;

public class CaughtObjective extends ObjectiveBase {
    public CaughtObjective(){
        super("CAUGHT");
    }

    @Override
    public void calculateProgress(ArrayList<ObjectiveData> dataList, EntityPlayerMP playerMP){
        if(dataList.size()>0){
            QuestManual quest=(QuestManual)QuestController.instance.quests.get(dataList.get(0).getQuestId()).questInterface;
            for(ObjectiveData data:dataList){
                IQuestObjective objective=quest.getObjectives(playerMP)[data.getObjectiveId()];
                Pokedex pokedex=Pixelmon.storageManager.getParty(playerMP).pokedex;
                int progress=0;
                if (objective.getMaxProgress()!=objective.getProgress()){
                    int max=objective.getMaxProgress();
                    Map<Integer, EnumPokedexRegisterStatus> dex=pokedex.getSeenMap();
                    if(data instanceof QuestSpeciesObjectiveData){
                        if(dex.get(EnumSpecies.getPokedexNumber(((QuestSpeciesObjectiveData) data).getSpecies()))==EnumPokedexRegisterStatus.caught){
                            objective.setProgress(objective.getMaxProgress());
                        }
                    }
                    else{
                        Object[] dexNumbers=dex.keySet().toArray();
                        for(int i=0;i<dex.size();i++){
                            if(dex.get(dexNumbers[i])==EnumPokedexRegisterStatus.caught){
                                if(data.passesCheck(EnumSpecies.getFromDex((int)dexNumbers[i]))){
                                    EssentialsLogger.info(dexNumbers[i]);
                                    progress++;
                                }
                            }
                            if(progress>=max){
                                break;
                            }
                        }
                        objective.setProgress(progress);
                    }
                }
            }
        }
    }
}
