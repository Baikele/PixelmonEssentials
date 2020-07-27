package com.pixelmonessentials.common.guis;

import com.pixelmonessentials.PixelmonEssentials;
import com.pixelmonessentials.common.api.action.ActionData;
import com.pixelmonessentials.common.api.action.datatypes.ActionNpcGuiData;
import com.pixelmonessentials.common.api.data.TrainerNPCData;
import com.pixelmonessentials.common.api.gui.EssentialsButton;
import com.pixelmonessentials.common.api.gui.bases.EssentialsFormGuiBase;
import com.pixelmonessentials.common.api.gui.bases.EssentialsGuiBase;
import com.pixelmonessentials.common.util.NpcScriptDataManipulator;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import net.minecraft.entity.player.EntityPlayerMP;
import noppes.npcs.api.wrapper.NPCWrapper;
import noppes.npcs.api.wrapper.PlayerWrapper;
import noppes.npcs.api.wrapper.gui.CustomGuiTextFieldWrapper;
import noppes.npcs.api.wrapper.gui.CustomGuiWrapper;
import noppes.npcs.controllers.CustomGuiController;
import noppes.npcs.entity.EntityNPCInterface;

public class TrainerDataGui extends EssentialsFormGuiBase {
    private String category;
    private String team;
    private String rules;
    EntityNPCInterface npc;
    private boolean losBattle;

    public TrainerDataGui(){
        super(1001, 500);
        this.addButton(new EssentialsButton(502, new ActionData("SELECT_TEAM")));
        this.addButton(new EssentialsButton(503, new ActionData("SELECT_RULES")));
        this.addButton(new EssentialsButton(504, new ActionData("CLEAR_RULES")));
        this.addButton(new EssentialsButton(505, new ActionData("FLIP_LOS")));
        this.team="";
        this.category="";
        this.rules="default";
    }

    public void flipLos(){
        this.losBattle=!losBattle;
    }

    public void setRules(String rules){
        this.rules=rules;
    }

    public void setNpc(EntityNPCInterface npc){
        this.npc=npc;
    }

    public EntityNPCInterface getNpc(){
        return this.npc;
    }

    public String getTeam(){
        return this.team;
    }

    public void saveTeam(String category, String team){
        this.category=category;
        this.team=team;
    }

    @Override
    public void init(EntityPlayerMP player){
        PlayerWrapper playerWrapper=new PlayerWrapper(player);
        CustomGuiWrapper gui=this.getGui();
        PixelmonEssentials.essentialsGuisHandler.addOrReplaceGui(player, this);
        playerWrapper.showCustomGui(gui);
    }

    public CustomGuiWrapper getGui(){
        CustomGuiWrapper gui=new CustomGuiWrapper(this.getId(), 256, 256, false);
        this.losBattle=true;
        ScriptObjectMirror object= NpcScriptDataManipulator.getJavascriptObject(new NPCWrapper(this.npc), "trainerData");
        Object dialogObject= NpcScriptDataManipulator.getJavascriptVariable(new NPCWrapper(this.npc), "losDialog");
        int dialog=0;
        if(object!=null){
            if(dialogObject!=null){
                dialog=(int)dialogObject;
            }
            else{
                this.losBattle=false;
            }
            int initDialog=(int)object.get("initDialog");
            int winDialog=(int)object.get("winDialog");
            int lossDialog=(int)object.get("lossDialog");
            String team=(String)object.get("team");
            String category=(String)object.get("category");
            String rules=(String)object.get("rules");
            if(this.losBattle){
                if(initDialog!=dialog&&dialog!=0){
                    this.losBattle=false;
                }
            }
            if(rules!=null&&this.rules.equals("default")){
                this.rules=rules;
            }
            if(team!=null&&this.team.equals("")){
                this.team=team;
            }
            if(category!=null&&this.category.equals("")){
                this.category=category;
            }
            if(initDialog!=0){
                gui.addTextField(401, 110, 40, 30, 20).setText(initDialog+"");
            }
            else{
                gui.addTextField(401, 110, 40, 30, 20);
            }

            if(winDialog!=0){
                gui.addTextField(402, 110, 70, 30, 20).setText(winDialog+"");
            }
            else{
                gui.addTextField(402, 110, 70, 30, 20);
            }

            if(lossDialog!=0){
                gui.addTextField(403, 110, 100, 30, 20).setText(lossDialog+"");
            }
            else{
                gui.addTextField(403, 110, 100, 30, 20);
            }
        }
        else{
            gui.addTextField(401, 110, 40, 30, 20);
            gui.addTextField(402, 110, 70, 30, 20);
            gui.addTextField(403, 110, 100, 30, 20);
        }
        gui.setBackgroundTexture("customnpcs:textures/gui/bgfilled.png");
        gui.addLabel(200, "Trainer Data", 90, 5, 200, 20);
        gui.addLabel(201, "Init Dialog ID", 20, 40, 200, 20);
        gui.addLabel(202, "Win Dialog ID", 20, 70, 200, 20);
        gui.addLabel(203, "Loss Dialog ID", 20, 100, 200, 20);
        gui.addLabel(204, "Team:", 150, 40, 100, 20);
        gui.addLabel(241, team, 180, 40, 100, 20);
        gui.addLabel(205, "Rules:", 150, 85, 100, 20);
        gui.addLabel(251, rules, 182, 85, 100, 20);
        gui.addButton(503, "Select", 150, 105, 40, 20);
        gui.addButton(504, "Clear", 195, 105, 34, 20);
        gui.addLabel(206, "Battle on sight:", 130, 130, 100, 20);
        if(this.losBattle){
            gui.addButton(505, "Yes", 210, 130, 30, 20);
        }
        else{
            gui.addButton(505, "No", 210, 130, 30, 20);
        }
        gui.addButton(502, "Select", 150, 60, 50, 20);
        gui.addButton(500, "Save", 50, 215, 50, 20);
        this.addButton(new EssentialsButton(500, new ActionNpcGuiData("SAVE_TRAINER", npc, 1001)));
        gui.addButton(501, "Cancel", 150, 215, 50, 20);
        this.addButton(new EssentialsButton(501, new ActionData("CLOSE_GUI")));
        return gui;
    }

    @Override
    public void sendForm(EntityPlayerMP playerMP){
        String errorMessage="";
        CustomGuiWrapper gui=CustomGuiController.getOpenGui(playerMP);
        int state=0;
        TrainerNPCData npcData=new TrainerNPCData();
        while(errorMessage.equals("")&&state<5){
            if(state==0){
                try{
                    int dialog=Integer.parseInt(((CustomGuiTextFieldWrapper)gui.getComponent(401)).getText());
                    if(dialog<=0){
                        errorMessage="Init dialog isn't a number higher than 0, and can't be a dialog!";
                    }
                    else{
                        npcData.setInitDialogId(dialog);
                        if(this.losBattle){
                            NpcScriptDataManipulator.setJavascriptVariable(new NPCWrapper(this.npc), "losDialog", npcData.getInitDialogId());
                        }
                        state++;
                    }
                }catch (NumberFormatException e){
                    errorMessage="Init dialog isn't a number!";
                }
            }
            else if(state==1){
                try{
                    int dialog=Integer.parseInt(((CustomGuiTextFieldWrapper)gui.getComponent(402)).getText());
                    if(dialog<=0){
                        errorMessage="Win dialog isn't a number higher than 0, and can't be a dialog!";
                    }
                    else{
                        npcData.setWinDialogId(dialog);
                        state++;
                    }
                }catch (NumberFormatException e){
                    errorMessage="Win dialog isn't a number!";
                }
            }
            else if(state==2){
                try{
                    int dialog=Integer.parseInt(((CustomGuiTextFieldWrapper)gui.getComponent(403)).getText());
                    if(dialog<=0){
                        errorMessage="Loss dialog isn't a number higher than 0, and can't be a dialog!";
                    }
                    else{
                        npcData.setLossDialogId(dialog);
                        state++;
                    }
                }catch (NumberFormatException e){
                    errorMessage="Loss dialog isn't a number!";
                }
            }
            else if(state==3){
                if(this.team.equals("")) {
                    errorMessage="No team was selected!";
                }
                else{
                    npcData.setCategoryName(this.category);
                    npcData.setTeam(this.team);
                    state++;
                }
            }
            else if(state==4){
                if(!this.rules.equals("default")) {
                    npcData.setRules(this.rules);
                }
                else{
                    npcData.setRules("");
                }
                state++;
            }
        }
        if(!errorMessage.equals("")){
            gui.addLabel(210, errorMessage, 5, 20, 240, 20, 16711680);
            gui.update(new PlayerWrapper(playerMP));
        }
        else{
            npcData.setNpcTrainerData(this.npc);
            playerMP.closeScreen();
        }
    }
}
