package com.pixelmonessentials.common.api.quests;

import com.pixelmonessentials.common.api.quests.objectiveData.QuestObjectiveData;
import net.minecraft.entity.player.EntityPlayerMP;

import java.util.ArrayList;

public interface Objective {
    String getName();
    ArrayList<ObjectiveData> getQuests();
    ArrayList<ObjectiveData> getQuestFromId(int id);
    ArrayList<ObjectiveData> getObjectivesFromQuestId(int id);
    void addQuest(ObjectiveData data);
    void removeQuest(ObjectiveData data);
    void calculateProgress(ArrayList<ObjectiveData> data, EntityPlayerMP player);
    ArrayList<ObjectiveData> getCompatibleObjectives();
}
