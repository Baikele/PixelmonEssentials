package com.pixelmonessentials.common.teams;

import java.util.ArrayList;

public class TeamCategory {
    private String name;
    public ArrayList<Team> teams;

    public TeamCategory(String name){
        this.name=name;
        this.teams=new ArrayList<Team>();
    }

    public String getName(){
        return this.name;
    }

    public void createTeam(String name)
    {
        Team team = new Team(name);
        this.teams.add(team);
    }

    public Team getTeam(String name)
    {
        for(Team team : this.teams)
        {
            if(team.getName().equalsIgnoreCase(name)){
                return team;
            }
        }
        return null;
    }

    public String[] getTeamNames(){
        String[] teams=new String[this.teams.size()];
        for(int i=0;i<teams.length;i++){
            teams[i]=this.teams.get(i).getName();
        }
        return teams;
    }
}
