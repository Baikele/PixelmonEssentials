package com.pixelmonessentials.common.spawners;

import com.pixelmonessentials.PixelmonEssentials;
import com.pixelmonessentials.common.util.DaytimeUtils;
import com.pixelmonmod.pixelmon.RandomHelper;

import java.util.ArrayList;
import java.util.Random;

public class SpawnData {
    private int id;
    private ArrayList<IndividualSpawnData> spawnList;

    public SpawnData(int id){
        this.id=id;
        this.spawnList=new ArrayList<IndividualSpawnData>();
    }

    public SpawnData(){
        this.id=PixelmonEssentials.spawnerDataManagement.getFirstAvailableId();
        this.spawnList=new ArrayList<IndividualSpawnData>();
    }

    public int getId(){
        return this.id;
    }

    public ArrayList<IndividualSpawnData> getSpawns(){
        return this.spawnList;
    }

    public void deleteSpecies(String species){
        for(IndividualSpawnData spawnData: this.spawnList){
            if(spawnData.getSpeciesName().equalsIgnoreCase(species)){
                this.spawnList.remove(spawnData);
                return;
            }
        }
    }

    public void addOrReplaceSpecies(IndividualSpawnData data){
        for(IndividualSpawnData spawnData:this.getSpawns()){
            if(spawnData.getSpeciesName().equals(data.getSpeciesName())){
                this.getSpawns().remove(spawnData);
                break;
            }
        }
        this.addSpecies(data);
    }

    public IndividualSpawnData getSpawnFromTime(long time){
        long hour=time%24000;
        DaytimeUtils.EnumDayPhase phase=DaytimeUtils.getPhaseFromTick(hour);
        ArrayList<IndividualSpawnData> individualSpawnDataList=new ArrayList<IndividualSpawnData>();
        ArrayList<Integer> weights=new ArrayList<Integer>();
        for(IndividualSpawnData spawnData:this.getSpawns()){
            if(spawnData.getDayPhases().size()==0||spawnData.getDayPhases().contains(phase)){
                individualSpawnDataList.add(spawnData);
                weights.add(spawnData.getRarity());
            }
        }
        try{
            return individualSpawnDataList.get(RandomHelper.getRandomIndexFromWeights(weights));
        }
        catch (NullPointerException e){
            return null;
        }
    }


    public void addSpecies(IndividualSpawnData data){
        this.spawnList.add(data);
    }
}
