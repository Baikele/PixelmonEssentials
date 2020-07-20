package com.pixelmonessentials.common.api.quests;

import com.google.gson.JsonObject;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.enums.EnumSpecies;
import net.minecraft.entity.player.EntityPlayerMP;
import noppes.npcs.api.wrapper.gui.CustomGuiComponentWrapper;
import noppes.npcs.api.wrapper.gui.CustomGuiWrapper;

import java.util.ArrayList;

public interface ObjectiveData {
    String getObjectiveType();
    String getCriteria();
    Objective getParentObjective();
    int getQuestId();
    int getObjectiveId();
    ArrayList<Objective> getEligibleObjectives();
    boolean passesCheck(EnumSpecies species);
    CustomGuiWrapper addComponents(CustomGuiWrapper gui);
    String checkForErrors(EntityPlayerMP playerMP);
    ObjectiveData loadFromJson(JsonObject object);
    JsonObject saveToJson();
}
