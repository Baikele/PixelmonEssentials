package com.pixelmonessentials.common.spawners;

import com.pixelmonessentials.common.util.DaytimeUtils;

import java.util.ArrayList;

public class IndividualSpawnData {
    private String speciesName;
    private String[] specs;
    private int minLevel;
    private int maxLevel;
    private int rarity;
    private ArrayList<DaytimeUtils.EnumDayPhase> dayPhases;

    public IndividualSpawnData(){
        this.speciesName="";
        this.specs=new String[0];
        this.minLevel=1;
        this.maxLevel=1;
        this.rarity=1;
        this.dayPhases=new ArrayList<DaytimeUtils.EnumDayPhase>();
    }

    public String getSpeciesName() {
        return speciesName;
    }

    public void setSpeciesName(String speciesName) {
        this.speciesName = speciesName;
    }

    public String[] getSpecs(){
        return this.specs;
    }

    public String getSpecsString(){
        String specs="";
        for(int i=0;i<this.specs.length;i++){
            specs+=this.specs[i];
            if(i!=this.specs.length-1){
                specs+=" ";
            }
        }
        return specs;
    }

    public void setSpecs(String[] specs){
        this.specs=specs;
    }

    public void setSpecs(String specs){
        this.specs=specs.split(" ");
    }

    public int getMinLevel() {
        return minLevel;
    }

    public void setMinLevel(int minLevel) {
        this.minLevel = minLevel;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public void setMaxLevel(int maxLevel) {
        this.maxLevel = maxLevel;
    }

    public int getRarity() {
        return rarity;
    }

    public void setRarity(int rarity) {
        this.rarity = rarity;
    }

    public ArrayList<DaytimeUtils.EnumDayPhase> getDayPhases() {
        return dayPhases;
    }

    public void setDayPhases(ArrayList<DaytimeUtils.EnumDayPhase> dayPhases) {
        this.dayPhases = dayPhases;
    }

    public void addDayPhaseOnString(String dayPhase){
        this.dayPhases.add(DaytimeUtils.getEnumFromName(dayPhase));
    }

    public void clearDayPhases(){
        this.dayPhases.clear();
    }

    public int generateLevel(){
        return this.minLevel+(int)(Math.random()*(this.maxLevel-this.minLevel+1));
    }
}
