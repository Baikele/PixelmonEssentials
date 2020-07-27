package com.pixelmonessentials.common.guis.objectives;

import com.pixelmonessentials.PixelmonEssentials;
import com.pixelmonessentials.common.api.action.ActionData;
import com.pixelmonessentials.common.api.gui.EssentialsButton;
import com.pixelmonessentials.common.api.gui.bases.EssentialsScrollGuiBase;
import com.pixelmonessentials.common.api.quests.Objective;
import com.pixelmonessentials.common.api.quests.ObjectiveData;
import net.minecraft.entity.player.EntityPlayerMP;
import noppes.npcs.api.wrapper.PlayerWrapper;
import noppes.npcs.api.wrapper.gui.CustomGuiWrapper;
import noppes.npcs.controllers.CustomGuiController;
import noppes.npcs.controllers.data.Quest;
import noppes.npcs.quests.QuestManual;

import java.util.ArrayList;
import java.util.Set;

public class ObjectiveSelectionGui extends EssentialsScrollGuiBase {
    private Quest quest;
    private String[] objectives;
    private ArrayList<ObjectiveData> objectiveData;
    private int index;

    public ObjectiveSelectionGui(){
        super(1005);
    }

    public ObjectiveSelectionGui(Quest quest){
        super(1005);
        this.quest=quest;
        this.index=-1;
        this.addButton(new EssentialsButton(500, new ActionData("OPEN_OBJECTIVE")));
        this.addButton(new EssentialsButton(501, new ActionData("CLEAR_OBJECTIVE")));
        this.addButton(new EssentialsButton(502, new ActionData("QUEST_GUI")));
    }

    public int getIndex(){
        return this.index;
    }

    public Quest getQuest(){
        return this.quest;
    }

    @Override
    public void init(EntityPlayerMP player){
        PlayerWrapper playerWrapper=new PlayerWrapper(player);
        this.objectives=this.getObjectives(player);
        this.objectiveData= PixelmonEssentials.questsManager.getObjectivesFromRealQuest(this.quest);
        CustomGuiWrapper gui=new CustomGuiWrapper(this.getId(), 256, 256, false);
        gui.setBackgroundTexture("customnpcs:textures/gui/bgfilled.png");
        gui.addLabel(200, this.quest.title, 90, 5, 128, 20);
        gui.addScroll(300, 10, 30, 100, 50, this.objectives);
        gui.addLabel(201, "Objective:", 115, 26, 60, 20);
        gui.addLabel(202, "Criteria:", 115, 41, 60, 20);
        gui.addLabel(203, "Name:", 115, 56, 60, 20);
        gui.addButton(502, "Back", 10, 85, 100, 20);
        playerWrapper.showCustomGui(gui);
        PixelmonEssentials.essentialsGuisHandler.addOrReplaceGui(player, this);
    }

    @Override
    public void setIndex(int id, String selection){
        if(id==300){
            for(int i=0;i<this.objectives.length;i++){
                if(this.objectives[i].equals(selection)){
                    this.index=i;
                    return;
                }
            }
        }
    }

    @Override
    public void updateScroll(int id, EntityPlayerMP playerMP){
        PlayerWrapper playerWrapper=new PlayerWrapper(playerMP);
        CustomGuiWrapper gui=CustomGuiController.getOpenGui(playerMP);
        gui.removeComponent(500);
        gui.removeComponent(501);
        gui.removeComponent(204);
        gui.removeComponent(205);
        gui.removeComponent(206);
        if(id==300){
            if(this.index>=0){
                String objectiveName="None";
                Objective objective=null;
                ObjectiveData objectiveData=null;
                if(this.objectiveData.size()>0){
                    for(ObjectiveData data:this.objectiveData){
                        if(data.getObjectiveId()==this.index){
                            objectiveData=data;
                            objective=data.getParentObjective();
                            break;
                        }
                    }
                }
                if(objective!=null){
                    objectiveName=objective.getName();
                    gui.addButton(501, "Clear", 62, 110, 48, 20);
                    gui.addLabel(205, objectiveData.getObjectiveType(), 170, 41, 60, 20);
                    gui.addLabel(206, objectiveData.getCriteria(), 170, 56, 60, 20);
                }
                gui.addLabel(204, objectiveName, 170, 26, 60, 20);
                gui.addButton(500, "Edit", 10, 110, 48, 20);
                gui.addScroll(300, 10, 30, 100, 50, this.objectives).setDefaultSelection(this.index);
                gui.update(playerWrapper);
            }
        }
    }

    public String[] getObjectives(EntityPlayerMP playerMP){
        QuestManual manual=(QuestManual) this.quest.questInterface;
        Set<String> keys=manual.manuals.keySet();
        Object[] names=keys.toArray();
        String[] objectives=new String[names.length];
        for(int i=0;i<names.length;i++){
            objectives[i]=(String)names[i];
        }
        return objectives;
    }
}
