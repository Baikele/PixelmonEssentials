package com.pixelmonessentials.common.teams;

import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;

import java.util.ArrayList;

public class Team {
    private String name;
    private ArrayList<Pokemon> members = new ArrayList<Pokemon>();

    public Team(String name){
        this.name=name;
    }

    public String getName(){
        return this.name;
    }

    public ArrayList<Pokemon> getMembers() {
        return new ArrayList<>(members);
    }

    public Pokemon getMember(int slot) {
        if(slot>members.size())
            return null;
        return getMembers().get(slot);
    }

    public void addMember(Pokemon pixelmon) {
        if(members.size()<6)
            members.add(pixelmon);
    }
}
