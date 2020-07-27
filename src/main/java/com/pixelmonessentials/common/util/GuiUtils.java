package com.pixelmonessentials.common.util;

import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;

public class GuiUtils {
    public static String getPokemonSprite(Pokemon pokemon){
        String sprite="pixelmon:textures/sprites/";
        if(pokemon.isShiny()){
            sprite+="shiny";
        }
        sprite+="pokemon/"+pokemon.getSpecies().getNationalPokedexNumber()+".png";
        return sprite;
    }
}
