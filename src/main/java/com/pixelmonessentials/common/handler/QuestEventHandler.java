package com.pixelmonessentials.common.handler;

import com.pixelmonessentials.PixelmonEssentials;
import com.pixelmonessentials.common.api.quests.Objective;
import com.pixelmonessentials.common.api.quests.ObjectiveData;
import com.pixelmonessentials.common.api.quests.objectiveData.QuestObjectiveData;
import com.pixelmonessentials.common.api.quests.objectives.CaughtObjective;
import com.pixelmonessentials.common.api.quests.objectives.DefeatPokemonObjective;
import com.pixelmonessentials.common.api.quests.objectives.PartyObjective;
import com.pixelmonessentials.common.util.EssentialsLogger;
import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.api.events.BeatWildPixelmonEvent;
import com.pixelmonmod.pixelmon.api.events.PokedexEvent;
import com.pixelmonmod.pixelmon.api.events.storage.ChangeStorageEvent;
import com.pixelmonmod.pixelmon.battles.controller.participants.PixelmonWrapper;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.server.FMLServerHandler;
import noppes.npcs.api.event.QuestEvent;
import noppes.npcs.api.handler.data.IQuest;
import noppes.npcs.api.handler.data.IQuestObjective;
import noppes.npcs.api.wrapper.PlayerWrapper;

import java.util.ArrayList;
import java.util.List;

public class QuestEventHandler {

    @SubscribeEvent
    public void onQuestStart(QuestEvent.QuestStartEvent event){
        ArrayList<ObjectiveData> objectiveData=PixelmonEssentials.questsManager.getObjectivesFromQuest(event.quest, event.player.getMCEntity());
        if(objectiveData!=null){
            for(ObjectiveData objective:objectiveData){
                Objective objectiveType=PixelmonEssentials.questsManager.getObjectiveFromName(objective.getObjectiveType());
                ArrayList<ObjectiveData> singleObjective=new ArrayList<ObjectiveData>();
                singleObjective.add(objective);
                objectiveType.calculateProgress(singleObjective, event.player.getMCEntity());
            }
        }
    }

    @SubscribeEvent
    public void onDexRegister(PokedexEvent event){
        EssentialsLogger.info("event");
        CaughtObjective objective=(CaughtObjective) PixelmonEssentials.questsManager.getObjectiveFromName("CAUGHT");
        EntityPlayerMP playerMP=Pixelmon.storageManager.getParty(event.uuid).getPlayer();
        PlayerWrapper playerWrapper=new PlayerWrapper(playerMP);
        IQuest[] quests=playerWrapper.getActiveQuests();
        for(IQuest quest:quests){
            if(objective.getQuestFromId(quest.getId())!=null){
                objective.calculateProgress(objective.getQuestFromId(quest.getId()), playerMP);
            }
        }
    }

    @SubscribeEvent
    public void onPartyChange(ChangeStorageEvent event){
        PartyObjective objective=(PartyObjective) PixelmonEssentials.questsManager.getObjectiveFromName("PARTY");
        EntityPlayerMP playerMP=Pixelmon.storageManager.getParty(event.newStorage.uuid).getPlayer();
        if(playerMP!=null){
            PlayerWrapper playerWrapper=new PlayerWrapper(playerMP);
            IQuest[] quests=playerWrapper.getActiveQuests();
            for(IQuest quest:quests){
                if(objective.getQuestFromId(quest.getId())!=null){
                    objective.calculateProgress(objective.getQuestFromId(quest.getId()), playerMP);
                }
            }
        }
    }

    @SubscribeEvent
    public void onDefeatPokemon(BeatWildPixelmonEvent event){
        List<PixelmonWrapper> pixelmonWrappers=event.wpp.controlledPokemon;
        EntityPlayerMP playerMP=event.player;
        DefeatPokemonObjective objective=(DefeatPokemonObjective) PixelmonEssentials.questsManager.getObjectiveFromName("KO_POKEMON");
        PlayerWrapper playerWrapper=new PlayerWrapper(playerMP);
        IQuest[] quests=playerWrapper.getActiveQuests();
        for(IQuest quest:quests){
            if(objective.getQuestFromId(quest.getId())!=null){
                ArrayList<ObjectiveData> dataList=objective.getQuestFromId(quest.getId());
                if(dataList.size()>0){
                    for(ObjectiveData data:dataList){
                        IQuestObjective questObjective=quest.getObjectives(playerWrapper)[data.getObjectiveId()];
                        if (questObjective.getMaxProgress()==questObjective.getProgress()){
                            return;
                        }
                        for(PixelmonWrapper pixelmonWrapper:pixelmonWrappers){
                            if (questObjective.getMaxProgress()==questObjective.getProgress()){
                                break;
                            }
                            if(data.passesCheck(pixelmonWrapper.getSpecies())){
                                questObjective.setProgress(questObjective.getProgress()+1);
                            }
                        }
                    }
                }
            }
        }
    }
}
