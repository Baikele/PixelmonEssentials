package com.pixelmonessentials.common.guis.objectives;

import com.pixelmonessentials.PixelmonEssentials;
import com.pixelmonessentials.common.api.action.ActionData;
import com.pixelmonessentials.common.api.gui.EssentialsButton;
import com.pixelmonessentials.common.api.gui.bases.EssentialsScrollGuiBase;
import net.minecraft.entity.player.EntityPlayerMP;
import noppes.npcs.api.handler.data.IQuest;
import noppes.npcs.api.wrapper.PlayerWrapper;
import noppes.npcs.api.wrapper.gui.CustomGuiWrapper;
import noppes.npcs.controllers.CustomGuiController;
import noppes.npcs.controllers.QuestController;
import noppes.npcs.controllers.data.Quest;
import noppes.npcs.controllers.data.QuestCategory;
import noppes.npcs.quests.QuestManual;

import java.util.ArrayList;
import java.util.Collection;

public class QuestSelectionGui extends EssentialsScrollGuiBase {
    String[] categories=new String[0];
    String[] quests=new String[0];
    ArrayList<QuestCategory> categoryList;
    ArrayList<Quest> manualQuests;
    int categoryIndex=-1;
    int questIndex=-1;

    public QuestSelectionGui(){
        super(1004);
        this.categoryList=this.getCategories();
        this.manualQuests=new ArrayList<Quest>();
        this.addButton(new EssentialsButton(500, new ActionData("OPEN_QUEST")));
    }

    public int getCategoryIndex(){
        return this.categoryIndex;
    }

    public int getQuestIndex(){
        return this.questIndex;
    }

    @Override
    public void init(EntityPlayerMP player){
        PlayerWrapper playerWrapper=new PlayerWrapper(player);
        this.categories=this.getCategoryNames();
        CustomGuiWrapper gui=new CustomGuiWrapper(1004, 256, 256, false);
        gui.setBackgroundTexture("customnpcs:textures/gui/bgfilled.png");
        gui.addLabel(200, "Custom objectives", 90, 5, 128, 20);
        gui.addScroll(300, 10, 30, 100, 200, this.categories);
        gui.addScroll(301, 140, 30, 100, 150, this.quests);
        playerWrapper.showCustomGui(gui);
        PixelmonEssentials.essentialsGuisHandler.addOrReplaceGui(player, this);
    }

    @Override
    public void updateScroll(int id, EntityPlayerMP playerMP){
        PlayerWrapper playerWrapper=new PlayerWrapper(playerMP);
        CustomGuiWrapper gui= CustomGuiController.getOpenGui(playerMP);
        if(id==300){
            this.questIndex=-1;
            gui.removeComponent(500);
            this.manualQuests=this.getQuests(this.categoryList.get(this.categoryIndex));
            this.quests=this.getQuestName();
            gui.addScroll(300, 10, 30, 100, 200, this.categories).setDefaultSelection(this.categoryIndex);
            gui.addScroll(301, 140, 30, 100, 150, this.quests);
        }
        else if(id==301){
            gui.addScroll(300, 10, 30, 100, 200, this.categories).setDefaultSelection(this.categoryIndex);
            gui.addScroll(301, 140, 30, 100, 150, this.quests).setDefaultSelection(this.questIndex);
        }
        if(this.questIndex!=-1){
            gui.addButton(500, "Edit", 140, 185, 50, 20);
        }
        gui.update(playerWrapper);
    }

    @Override
    public void setIndex(int id, String selection){
        if(id==300){
            for(int i=0;i<this.categories.length;i++){
                if(this.categories[i].equalsIgnoreCase(selection)){
                    this.categoryIndex=i;
                    return;
                }
            }
        }
        else if(id==301){
            for(int i=0;i<this.quests.length;i++){
                if(this.quests[i].equalsIgnoreCase(selection)){
                    this.questIndex=i;
                    return;
                }
            }
        }
    }

    public ArrayList<QuestCategory> getCategories(){
         Collection<QuestCategory> questCategories=QuestController.instance.categories.values();
         ArrayList<QuestCategory> validCategories=new ArrayList<QuestCategory>();
         for(QuestCategory questCategory:questCategories){
             if(isValid(questCategory)){
                 validCategories.add(questCategory);
             }
         }
         return validCategories;
    }

    public String[] getCategoryNames(){
        String[] categoryName=new String[this.categoryList.size()];
        for(int i=0;i<categoryName.length;i++){
            categoryName[i]=this.categoryList.get(i).title;
        }
        return categoryName;
    }

    public ArrayList<Quest> getQuests(QuestCategory category){
        ArrayList<Quest> quests=new ArrayList<Quest>();
        for(IQuest iQuest:category.quests()){
            Quest quest=(Quest)iQuest;
            if(quest.questInterface instanceof QuestManual){
                quests.add((Quest) quest);
            }
        }
        return quests;
    }

    public String[] getQuestName(){
        String[] questNames=new String[this.manualQuests.size()];
        for(int i=0;i<this.manualQuests.size();i++){
            questNames[i]=this.manualQuests.get(i).getName();
        }
        return questNames;
    }

    public boolean isValid(QuestCategory category){
        for(IQuest quest:category.quests()){
            if(quest.getType()==5){
                return true;
            }
        }
        return false;
    }

}
