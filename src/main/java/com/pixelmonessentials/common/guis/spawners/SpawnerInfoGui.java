package com.pixelmonessentials.common.guis.spawners;

import com.pixelmonessentials.PixelmonEssentials;
import com.pixelmonessentials.common.api.action.ActionData;
import com.pixelmonessentials.common.api.gui.EssentialsButton;
import com.pixelmonessentials.common.api.gui.EssentialsGuis;
import com.pixelmonessentials.common.api.gui.EssentialsScrollGui;
import com.pixelmonessentials.common.api.gui.bases.EssentialsScrollGuiBase;
import com.pixelmonessentials.common.spawners.SpawnData;
import com.pixelmonessentials.common.util.DaytimeUtils;
import net.minecraft.entity.player.EntityPlayerMP;
import noppes.npcs.api.wrapper.PlayerWrapper;
import noppes.npcs.api.wrapper.gui.CustomGuiWrapper;
import noppes.npcs.controllers.CustomGuiController;
import noppes.npcs.entity.EntityNPCInterface;

import java.util.ArrayList;

public class SpawnerInfoGui extends EssentialsScrollGuiBase {
    private String[] spawners;
    private String[] species;
    private int idIndex;
    private int speciesIndex;

    public SpawnerInfoGui(){
        super(1002);
        this.idIndex=-1;
        this.speciesIndex=-1;
        this.spawners=this.getSpawnIds();
        this.species=this.getSpeciesSpawns();
        this.addButton(new EssentialsButton(500, new ActionData("ADD_SPAWN", "")));
        this.addButton(new EssentialsButton(501, new ActionData("DELETE_SPAWN", "")));
        this.addButton(new EssentialsButton(502, new ActionData("CREATE_SPECIES", "")));
        this.addButton(new EssentialsButton(503, new ActionData("DELETE_SPECIES", "")));
        this.addButton(new EssentialsButton(504, new ActionData("EDIT_SPECIES", "")));
    }

    public int getIdIndex(){
        return this.idIndex;
    }
    public int getSpeciesIndex(){
        return this.speciesIndex;
    }

    @Override
    public void init(EntityPlayerMP player){
        this.spawners=this.getSpawnIds();
        this.species=this.getSpeciesSpawns();
        PlayerWrapper playerWrapper=new PlayerWrapper(player);
        CustomGuiWrapper gui=this.createGui(player);
        PixelmonEssentials.essentialsGuisHandler.addOrReplaceGui(player, this);
        playerWrapper.showCustomGui(gui);
    }

    @Override
    public void setIndex(int id, String index){
        if(id==300){
            this.updateIdScroll(index);
        }
        else if(id==301){
            this.updateSpeciesScroll(index);
        }
    }

    @Override
    public void updateScroll(int id, EntityPlayerMP playerMP){
        CustomGuiWrapper customGui=CustomGuiController.getOpenGui(playerMP);
        customGui.removeComponent(300);
        customGui.removeComponent(301);
        customGui.removeComponent(501);
        customGui.removeComponent(502);
        customGui.removeComponent(503);
        customGui.removeComponent(504);
        this.spawners=this.getSpawnIds();
        this.species=this.getSpeciesSpawns();
        if(this.idIndex>-1){
            customGui.addScroll(300, 10, 38, 108, 208, this.spawners).setDefaultSelection(this.idIndex);
            customGui.addButton(502, "+", 133, 16, 20, 20);
        }
        else {
            customGui.addScroll(300, 10, 38, 108, 208, this.spawners);
        }
        if(this.speciesIndex>-1){
            customGui.addScroll(301, 133, 38, 108, 208, this.species).setDefaultSelection(this.speciesIndex);
        }
        else {
            customGui.addScroll(301, 133, 38, 108, 208, this.species);
        }
        if(this.spawners.length>0){
            customGui.addButton(501, "Delete", 37, 16, 50, 20);
        }
        if(this.species.length>0){
            customGui.addButton(503, "Delete", 157, 16, 45, 20);
            customGui.addButton(504, "Edit", 206, 16, 35, 20);
        }
        customGui.update(new PlayerWrapper(playerMP));
    }

    public CustomGuiWrapper createGui(EntityPlayerMP playerMP){
        CustomGuiWrapper gui=new CustomGuiWrapper(this.getId(), 256, 256, false);
        gui.setBackgroundTexture("customnpcs:textures/gui/bgfilled.png");
        gui.addLabel(201, "Spawn Data", 100, 0, 128, 20);
        gui.addButton(500, "+", 10, 16, 20, 20);
        if(this.spawners.length>0){
            gui.addButton(501, "Delete", 37, 16, 50, 20);
        }
        gui.addScroll(300, 10, 38, 108, 208, this.spawners);

        if(this.idIndex>=0){
            gui.addButton(502, "+", 133, 16, 20, 20);
        }
        if(this.species.length>0){
            gui.addButton(503, "Delete", 157, 16, 45, 20);
            gui.addButton(504, "Edit", 206, 16, 35, 20);
        }
        gui.addScroll(301, 133, 38, 108, 208, this.species);
        return gui;
    }

    public void updateIdScroll(String index){
        for(int i=0;i<this.spawners.length;i++){
            if(index.equals(this.spawners[i])){
                this.idIndex=i;
                this.speciesIndex=-1;
                return;
            }
        }
        this.idIndex=-1;
        this.speciesIndex=-1;
    }

    public void updateSpeciesScroll(String index){
        for(int i=0;i<this.species.length;i++){
            if(index.equals(this.species[i])){
                this.speciesIndex=i;
                return;
            }
        }
        this.speciesIndex=-1;
    }

    public String[] getSpawnIds(){
        String[] spawns=new String[PixelmonEssentials.spawnerDataManagement.spawnData.size()];
        for(int i=0;i<spawns.length;i++){
            spawns[i]=PixelmonEssentials.spawnerDataManagement.spawnData.get(i).getId()+"";
        }
        return spawns;
    }

    public String[] getSpeciesSpawns(){
        if(this.idIndex==-1){
            return new String[0];
        }
        else {
            SpawnData data=PixelmonEssentials.spawnerDataManagement.spawnData.get(this.idIndex);
            String[] spawns=new String[data.getSpawns().size()];
            for (int i=0;i<spawns.length;i++){
                spawns[i]=data.getSpawns().get(i).getSpeciesName();
            }
            return spawns;
        }
    }
}
