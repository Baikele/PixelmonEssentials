package com.pixelmonessentials.common.api.data;

import com.pixelmonessentials.common.util.EssentialsLogger;
import com.pixelmonessentials.common.util.NpcScriptDataManipulator;
import jdk.nashorn.api.scripting.JSObject;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import jdk.nashorn.api.scripting.ScriptUtils;
import jdk.nashorn.internal.objects.Global;
import jdk.nashorn.internal.runtime.ScriptObject;
import noppes.npcs.api.wrapper.NPCWrapper;
import noppes.npcs.entity.EntityNPCInterface;

public class TrainerNPCData {
    int initDialogId;
    int winDialogId;
    int lossDialogId;
    String categoryName;
    String teamName;
    String rules;
    String encounterTheme;
    String battleTheme;
    String victoryTheme;

    public TrainerNPCData(){
        this.initDialogId=0;
        this.winDialogId=0;
        this.lossDialogId=0;
        this.categoryName="";
        this.teamName="";
        this.rules="";
        this.encounterTheme="";
        this.battleTheme="";
        this.victoryTheme="";
    }

    public void setInitDialogId(int dialogId){
        this.initDialogId=dialogId;
    }

    public int getInitDialogId(){
        return this.initDialogId;
    }

    public void setWinDialogId(int dialogId){
        this.winDialogId=dialogId;
    }

    public int getWinDialogId(){
        return this.winDialogId;
    }

    public void setLossDialogId(int dialogId){
        this.lossDialogId=dialogId;
    }

    public int getLossDialogId(){
        return this.lossDialogId;
    }

    public void setTeam(String team){
        this.teamName=team;
    }

    public String getTeam(){
        return this.teamName;
    }

    public void setCategoryName(String category){
        this.categoryName=category;
    }

    public String getCategoryName(){
        return this.categoryName;
    }

    public void setRules(String rules){
        this.rules=rules;
    }

    public String getRules(){
        return this.rules;
    }

    public void setEncounterTheme(String theme){
        this.encounterTheme=theme;
    }

    public String getEncounterTheme(){
        return this.encounterTheme;
    }

    public void setBattleTheme(String theme){
        this.battleTheme=theme;
    }

    public String getBattleTheme(){
        return this.battleTheme;
    }

    public void setVictoryTheme(String theme){
        this.victoryTheme=theme;
    }

    public String getVictoryTheme(){
        return this.victoryTheme;
    }

    public void setNpcTrainerData(EntityNPCInterface npc){
        NPCWrapper npcWrapper= new NPCWrapper(npc);
        ScriptObjectMirror object=NpcScriptDataManipulator.getJavascriptObject(npcWrapper, "trainerData");
        TrainerNPCData data=new TrainerNPCData();
        if(object!=null){
            data.initDialogId=(int)object.get("initDialog");
            data.winDialogId=(int)object.get("winDialog");
            data.lossDialogId=(int)object.get("lossDialog");
            data.categoryName=(String) object.get("category");
            data.teamName=(String) object.get("team");
            /*data.encounterTheme=(String) object.get("encounterTheme");
            data.battleTheme=(String) object.get("battleTheme");
            data.victoryTheme=(String) object.get("victoryTheme");*/
        }/*
        if(!this.encounterTheme.equals("")){
            data.encounterTheme=this.encounterTheme;
        }
        if(!this.battleTheme.equals("")){
            data.battleTheme=this.battleTheme;
        }
        if(!this.victoryTheme.equals("")){
            data.victoryTheme=this.victoryTheme;
        }*/
        if(!this.categoryName.equals("")){
            data.categoryName=this.categoryName;
        }
        if(!this.teamName.equals("")){
            data.teamName=this.teamName;
        }
        if(!this.rules.equals("")){
            data.rules=this.rules;
        }
        if(this.initDialogId!=0){
            data.initDialogId=this.initDialogId;
        }
        if(this.winDialogId!=0){
            data.winDialogId=this.winDialogId;
        }
        if(this.lossDialogId!=0){
            data.lossDialogId=this.lossDialogId;
        }
        String convertedData=this.dataToObjectString();
        NpcScriptDataManipulator.setJavascriptObject(npcWrapper, "trainerData", convertedData);
    }

    public String dataToObjectString(){
        String data="{\n";
        if(!this.rules.equals("")){
            data+="    \"rules\":\""+this.rules+"\",\n";
        }
        data+="    \"initDialog\":"+this.initDialogId+",\n";
        data+="    \"winDialog\":"+this.winDialogId+",\n";
        data+="    \"lossDialog\":"+this.lossDialogId+",\n";
        data+="    \"category\":\""+this.categoryName+"\",\n";
        data+="    \"team\": \""+this.teamName+"\"\n";/*
        data+="\t\"encounterTheme\": \""+this.encounterTheme+"\",\n";
        data+="\t\"battleTheme\": \""+this.battleTheme+"\",\n";
        data+="\t\"victoryTheme\": \""+this.victoryTheme+"\"\n";*/
        data+="};";
        return data;
    }
}
