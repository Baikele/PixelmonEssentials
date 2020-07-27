package com.pixelmonessentials.common.guis.objectives;

import com.pixelmonessentials.PixelmonEssentials;
import com.pixelmonessentials.common.api.action.ActionData;
import com.pixelmonessentials.common.api.gui.EssentialsButton;
import com.pixelmonessentials.common.api.gui.EssentialsFormGui;
import com.pixelmonessentials.common.api.gui.bases.EssentialsScrollGuiBase;
import com.pixelmonessentials.common.api.quests.ObjectiveData;
import net.minecraft.entity.player.EntityPlayerMP;
import noppes.npcs.api.wrapper.PlayerWrapper;
import noppes.npcs.api.wrapper.gui.CustomGuiScrollWrapper;
import noppes.npcs.api.wrapper.gui.CustomGuiWrapper;
import noppes.npcs.controllers.CustomGuiController;
import noppes.npcs.controllers.data.Quest;
import noppes.npcs.quests.QuestManual;

import java.util.ArrayList;
import java.util.HashMap;

public class CustomObjectiveGui extends EssentialsScrollGuiBase implements EssentialsFormGui {
    private Quest quest;
    private int objective;
    private int submitButton;
    private int objectiveIndex;
    private int typeIndex;
    private ObjectiveData objectiveData;
    private ArrayList<ObjectiveData> dataTypes;
    private HashMap<Integer, Integer> extraScrolls;
    private EntityPlayerMP playerMP;

    public CustomObjectiveGui(){
        super(1006);
        this.submitButton=500;
    }

    public CustomObjectiveGui(Quest quest, int objective){
        super(1006);
        this.submitButton=500;
        this.quest=quest;
        this.objective=objective;
        this.objectiveIndex=-1;
        this.typeIndex=-1;
        this.dataTypes=new ArrayList<ObjectiveData>();
        this.extraScrolls=new HashMap<Integer, Integer>();
        this.addButton(new EssentialsButton(501, new ActionData("OPEN_QUEST")));
    }

    public HashMap<Integer, Integer> getExtraScrolls(){
        return this.extraScrolls;
    }

    public void setObjective(ObjectiveData data){
        this.objectiveData=data;
    }

    public int getSubmitButton(){
        return this.submitButton;
    }

    public Quest getQuest(){
        return this.quest;
    }

    public int getObjective(){
        return this.objective;
    }

    public int getObjectiveIndex(){
        return this.objectiveIndex;
    }

    @Override
    public void init(EntityPlayerMP player){
        this.playerMP=player;
        PlayerWrapper playerWrapper=new PlayerWrapper(player);
        ArrayList<ObjectiveData> objectives=PixelmonEssentials.questsManager.getObjectivesFromRealQuest(quest);
        for(ObjectiveData data:objectives){
            if(data.getObjectiveId()==this.objective){
                this.objectiveData=data;
                break;
            }
        }
        CustomGuiWrapper gui=this.getBasicGui();
        playerWrapper.showCustomGui(gui);
        PixelmonEssentials.essentialsGuisHandler.addOrReplaceGui(player, this);
    }

    public CustomGuiWrapper getBasicGui(){
        QuestManual manual=(QuestManual) this.quest.questInterface;
        String objectiveName=manual.manuals.keySet().toArray(new String[manual.manuals.size()])[objective];
        CustomGuiWrapper gui=new CustomGuiWrapper(this.getId(), 256, 256, false);
        gui.setBackgroundTexture("customnpcs:textures/gui/bgfilled.png");
        gui.addLabel(201, objectiveName, 75, 5, 128, 20);
        if(this.objectiveIndex>-1){
            gui.addScroll(300, 22, 35, 100, 100, this.getObjectiveList()).setDefaultSelection(this.objectiveIndex);
        }
        else{
            gui.addScroll(300, 22, 35, 100, 100, this.getObjectiveList());
        }
        if(this.typeIndex>-1){
            gui.addScroll(301, 132, 35, 100, 100, this.getDataTypeNames()).setDefaultSelection(this.typeIndex);
        }
        else{
            gui.addScroll(301, 132, 35, 100, 100, this.getDataTypeNames());
        }
        gui.addButton(500, "Save", 70, 225, 48, 20);
        gui.addButton(501, "Cancel", 140, 225, 48, 20);
        return gui;
    }

    @Override
    public void setIndex(int id, String selection){
        if(id==300){
            for(int i=0;i<PixelmonEssentials.questsManager.objectives.size();i++){
                if(PixelmonEssentials.questsManager.objectives.get(i).getName().replace("_", " ").equalsIgnoreCase(selection)){
                    this.objectiveIndex=i;
                    return;
                }
            }
        }
        else if(id==301){
            for(int i=0;i<this.dataTypes.size();i++){
                if(this.dataTypes.get(i).getObjectiveType().equalsIgnoreCase(selection)){
                    this.typeIndex=i;
                    return;
                }
            }
        }
        else{
            CustomGuiScrollWrapper scroll=(CustomGuiScrollWrapper) CustomGuiController.getOpenGui(this.playerMP).getComponent(id);
            String[] options=scroll.getList();
            for(int i=0;i<options.length;i++){
                if(options[i].equals(selection)){
                    if(this.extraScrolls.containsKey(id)){
                        this.extraScrolls.replace(id, i);
                    }
                    else{
                        this.extraScrolls.put(id, i);
                    }
                    break;
                }
            }
        }
    }

    @Override
    public void updateScroll(int id, EntityPlayerMP playerMP){
        PlayerWrapper playerWrapper=new PlayerWrapper(playerMP);
        if(id==300){
            this.typeIndex=-1;
            this.dataTypes=PixelmonEssentials.questsManager.objectives.get(this.objectiveIndex).getCompatibleObjectives();
            this.getBasicGui().update(playerWrapper);
        }
        if(id==301){
            CustomGuiWrapper gui=this.getBasicGui();
            gui=this.dataTypes.get(this.typeIndex).addComponents(gui);
            gui.update(playerWrapper);
        }
    }

    @Override
    public void sendForm(EntityPlayerMP player){
        PlayerWrapper playerWrapper=new PlayerWrapper(player);
        ObjectiveData data=this.dataTypes.get(this.typeIndex);
        String error=data.checkForErrors(player);
        CustomGuiWrapper gui=CustomGuiController.getOpenGui(player);
        if(!error.equals("")){
            gui=this.dataTypes.get(this.typeIndex).addComponents(gui);
            gui.addLabel(203, error, 100, 18, 128, 20, 16711680);
            gui.update(playerWrapper);
        }
        else{
            ObjectiveSelectionGui nextGui=new ObjectiveSelectionGui(this.quest);
            nextGui.init(playerMP);
        }
    }

    public String[] getObjectiveList(){
        String[] objectives=new String[PixelmonEssentials.questsManager.objectives.size()];
        for(int i=0;i<objectives.length;i++){
            objectives[i]=fixString(PixelmonEssentials.questsManager.objectives.get(i).getName());
        }
        return objectives;
    }

    public String[] getDataTypeNames(){
        String[] types=new String[this.dataTypes.size()];
        for(int i=0;i<this.dataTypes.size();i++){
            types[i]=this.dataTypes.get(i).getObjectiveType();
        }
        return types;
    }

    public static String fixString(String objective){
        return objective.replace("_", " ");
    }
}
