package com.pixelmonessentials.common.api.gui;

import com.pixelmonessentials.common.guis.NpcHomeGui;
import com.pixelmonessentials.common.guis.TrainerDataGui;
import com.pixelmonessentials.common.guis.objectives.CustomObjectiveGui;
import com.pixelmonessentials.common.guis.objectives.ObjectiveSelectionGui;
import com.pixelmonessentials.common.guis.objectives.QuestSelectionGui;
import com.pixelmonessentials.common.guis.spawners.IndividualSpawnGui;
import com.pixelmonessentials.common.guis.spawners.SpawnerInfoGui;
import net.minecraft.entity.player.EntityPlayerMP;

import java.util.ArrayList;
import java.util.HashMap;

public class EssentialsGuisHandler {
    HashMap<EntityPlayerMP, EssentialsGuis> essentialsGuisHandler=new HashMap<>();
    ArrayList<EssentialsGuis> basicGuis=new ArrayList<>();

    public void init(){
        this.basicGuis.add(new NpcHomeGui());
        this.basicGuis.add(new TrainerDataGui());

        this.basicGuis.add(new IndividualSpawnGui());
        this.basicGuis.add(new SpawnerInfoGui());

        this.basicGuis.add(new QuestSelectionGui());
        this.basicGuis.add(new ObjectiveSelectionGui());
        this.basicGuis.add(new CustomObjectiveGui());
    }

    public void removeGui(EntityPlayerMP player){
        if(this.essentialsGuisHandler.containsKey(player)){
            this.essentialsGuisHandler.remove(player);
        }
    }

    public void addOrReplaceGui(EntityPlayerMP player, EssentialsGuis gui){
        if(this.essentialsGuisHandler.containsKey(player)){
            this.essentialsGuisHandler.remove(player);
        }
        this.essentialsGuisHandler.put(player, gui);
    }

    public EssentialsGuis getGui(EntityPlayerMP playerMP){
        return this.essentialsGuisHandler.get(playerMP);
    }

    public EssentialsGuis getGui(int id){
        for(EssentialsGuis gui: this.basicGuis){
            if(gui.getId()==id){
                return gui;
            }
        }
        return null;
    }

    public void addGui(EssentialsGuis gui){
        this.basicGuis.add(gui);
    }
}
