package com.pixelmonessentials.common.battles.rules;

import com.pixelmonmod.pixelmon.battles.rules.BattleRules;

public class CustomRules {
    String name;
    BattleRules rules;

    public CustomRules(){
        this.name="";
    }

    public CustomRules(String name, BattleRules rules){
        this.name=name;
        this.rules=rules;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name=name;
    }

    public BattleRules getRules(){
        return this.rules;
    }

    public void setRules(BattleRules rules){
        this.rules=rules;
    }
}
