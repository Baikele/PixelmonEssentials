package com.pixelmonessentials.common.handler;

import com.pixelmonessentials.PixelmonEssentials;
import com.pixelmonessentials.common.spawners.IndividualSpawnData;
import com.pixelmonessentials.common.spawners.SpawnData;
import com.pixelmonessentials.common.spawners.SpawnerLocation;
import com.pixelmonmod.pixelmon.api.events.spawning.PixelmonSpawnerEvent;
import com.pixelmonmod.pixelmon.api.pokemon.PokemonSpec;
import com.pixelmonmod.pixelmon.blocks.machines.PokemonRarity;
import com.pixelmonmod.pixelmon.blocks.spawning.TileEntityPixelmonSpawner;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class PixelmonSpawnerEventHandler {
    @SubscribeEvent
    public void onSpawnerPlaced(BlockEvent.EntityPlaceEvent event){
        if(event.getPlacedBlock().getBlock().getRegistryName().toString().equals("pixelmon:pixelmon_spawner")){
            int dim=event.getBlockSnapshot().getDimId();
            BlockPos pos=event.getBlockSnapshot().getPos();
            PixelmonEssentials.spawnerDataManagement.spawnerLocations.add(new SpawnerLocation(dim, pos));
        }
    }

    @SubscribeEvent
    public void onSpawnerFire(PixelmonSpawnerEvent event){
        if(event.spec.name.equalsIgnoreCase("Arceus")){
            int id=0;
            for(int i=0;i<event.spawner.pokemonList.size();i++){
                PokemonRarity rarity=event.spawner.pokemonList.get(i);
                if(rarity.pokemon.name.equalsIgnoreCase("Arceus")){
                    id=rarity.rarity;
                    break;
                }
            }
            if(id!=0){
                SpawnData data=PixelmonEssentials.spawnerDataManagement.getSpawnsFromId(id);
                IndividualSpawnData spawnData=data.getSpawnFromTime(event.spawner.getWorld().getWorldTime());
                PokemonSpec spec=new PokemonSpec(spawnData.getSpeciesName());
                spec.level=spawnData.generateLevel();
                try {
                    Method spawnPixelmon=TileEntityPixelmonSpawner.class.getDeclaredMethod("spawnPixelmon", PokemonSpec.class);
                    spawnPixelmon.setAccessible(true);
                    spawnPixelmon.invoke(event.spawner, spec);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                event.setCanceled(true);
            }
        }
    }
}
