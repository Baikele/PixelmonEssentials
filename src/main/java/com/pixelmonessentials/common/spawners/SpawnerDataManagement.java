package com.pixelmonessentials.common.spawners;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonWriter;
import com.pixelmonessentials.PixelmonEssentials;
import com.pixelmonessentials.common.util.DaytimeUtils;
import com.pixelmonessentials.common.util.Reference;
import net.minecraft.util.math.BlockPos;

import java.io.*;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Objects;

public class SpawnerDataManagement {
    public ArrayList<SpawnData> spawnData=new ArrayList<SpawnData>();
    public ArrayList<SpawnerLocation> spawnerLocations=new ArrayList<SpawnerLocation>();
    File spawnDataDir;
    File spawnerLocationDir;

    public SpawnData getSpawnsFromId(int id){
        for(SpawnData data:this.spawnData){
            if(data.getId()==id){
                return data;
            }
        }
        return null;
    }

    public void init(){
        spawnDataDir=new File(PixelmonEssentials.configFolder, "spawners/");
        spawnerLocationDir=new File(PixelmonEssentials.dataFolder, "spawners.json");
        load();
    }

    public boolean contains(int id){
        for(SpawnData data:this.spawnData){
            if(data.getId()==id){
                return true;
            }
        }
        return false;
    }

    public int getFirstAvailableId(){
        int id=1;
        while(true){
            if(!this.contains(id)){
                return id;
            }
            id++;
        }
    }

    public void load(){
        if(!spawnDataDir.exists()){
            spawnDataDir.mkdirs();
        }
        else{
            this.loadFiles();
        }
    }

    public void loadFiles(){
        this.spawnData.clear();
        try {
            for (File f : Objects.requireNonNull(spawnDataDir.listFiles())) {
                if (f.getName().endsWith(".json")) {
                    int id=Integer.parseInt(f.getName().replace(".json",""));
                    loadSpawner(id);
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (NumberFormatException e){
            e.printStackTrace();
        }
    }

    public void loadSpawner(int file) throws FileNotFoundException {
        InputStream iStream=new FileInputStream(new File(spawnDataDir, file+".json"));
        SpawnData data=new SpawnData(file);
        JsonArray array=new JsonParser().parse(new InputStreamReader(iStream, StandardCharsets.UTF_8)).getAsJsonArray();
        for(JsonElement json: array){
            JsonObject object=json.getAsJsonObject();
            if(object.has("species")){
                IndividualSpawnData spawn=new IndividualSpawnData();
                spawn.setSpeciesName(object.get("species").getAsString());
                spawn.setMinLevel(object.get("minLevel").getAsInt());
                spawn.setMaxLevel(object.get("maxLevel").getAsInt());
                spawn.setRarity(object.get("rarity").getAsInt());
                if(object.has("dayPhases")){
                    JsonArray phaseArray=object.get("dayPhases").getAsJsonArray();
                    for(JsonElement phase:phaseArray){
                        spawn.addDayPhaseOnString(phase.getAsString());
                    }
                }
                data.addSpecies(spawn);
            }
        }
        this.spawnData.add(data);
    }

    public void save(){
        saveSpawners();
        for(SpawnData data: this.spawnData){
            File file=new File(this.spawnDataDir, data.getId()+".json");
            try {
                if(!file.exists()) {
                    file.createNewFile();
                }
                JsonWriter writer=new JsonWriter(new FileWriter(file));
                writer.setIndent("\t");
                writer.beginArray();
                for(IndividualSpawnData individualSpawnData: data.getSpawns()) {
                    writer.beginObject();
                    writer.name("species").value(individualSpawnData.getSpeciesName());
                    writer.name("minLevel").value(individualSpawnData.getMinLevel());
                    writer.name("maxLevel").value(individualSpawnData.getMaxLevel());
                    writer.name("rarity").value(individualSpawnData.getRarity());
                    if(individualSpawnData.getDayPhases().size()>0) {
                        writer.name("dayPhases").beginArray();
                        for(DaytimeUtils.EnumDayPhase dayPhase: individualSpawnData.getDayPhases()){
                            writer.value(DaytimeUtils.getNameFromEnum(dayPhase));
                        }
                        writer.endArray();
                    }
                    writer.endObject();
                }
                writer.endArray();
                writer.close();
            } catch (IOException e) {
                    e.printStackTrace();
            }
        }
    }

    public void saveSpawners(){
        try {
            if(!this.spawnerLocationDir.exists()){
                if(!this.spawnerLocationDir.getParentFile().exists()){
                    this.spawnerLocationDir.getParentFile().mkdirs();
                }
                this.spawnerLocationDir.createNewFile();
            }
            else{
                loadSpawners();
            }
            JsonWriter writer=new JsonWriter(new FileWriter(this.spawnerLocationDir));
            writer.setIndent("\t");
            writer.beginArray();
            for(SpawnerLocation location:this.spawnerLocations){
                writer.beginObject();
                writer.name("dim").value(location.getDim());
                writer.name("pos").beginObject();
                writer.name("x").value(location.getPos().getX());
                writer.name("y").value(location.getPos().getY());
                writer.name("z").value(location.getPos().getZ());
                writer.endObject();
                writer.endObject();
            }
            writer.endArray();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadSpawners() throws FileNotFoundException {
        InputStream iStream=new FileInputStream(this.spawnerLocationDir);
        JsonArray array=new JsonParser().parse(new InputStreamReader(iStream, StandardCharsets.UTF_8)).getAsJsonArray();
        for(JsonElement json: array){
            JsonObject object=json.getAsJsonObject();
            int dim=object.get("dim").getAsInt();
            JsonObject blockPos=object.getAsJsonObject("pos");
            int x=blockPos.get("x").getAsInt();
            int y=blockPos.get("y").getAsInt();
            int z=blockPos.get("z").getAsInt();
            BlockPos pos=new BlockPos(x, y, z);
            SpawnerLocation location=new SpawnerLocation(dim, pos);
            if(!this.spawnerLocations.contains(location)){
                this.spawnerLocations.add(location);
            }
        }
    }
}
