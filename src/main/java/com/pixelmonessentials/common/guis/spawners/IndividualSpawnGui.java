package com.pixelmonessentials.common.guis.spawners;

import com.pixelmonessentials.PixelmonEssentials;
import com.pixelmonessentials.common.api.action.ActionData;
import com.pixelmonessentials.common.api.action.datatypes.ActionIdData;
import com.pixelmonessentials.common.api.gui.EssentialsButton;
import com.pixelmonessentials.common.api.gui.EssentialsFormGui;
import com.pixelmonessentials.common.api.gui.EssentialsPersistentGui;
import com.pixelmonessentials.common.api.gui.bases.EssentialsMultiselectScrollGuiBase;
import com.pixelmonessentials.common.spawners.IndividualSpawnData;
import com.pixelmonessentials.common.spawners.SpawnData;
import com.pixelmonessentials.common.util.DaytimeUtils;
import com.pixelmonmod.pixelmon.config.PixelmonConfig;
import com.pixelmonmod.pixelmon.enums.EnumSpecies;
import net.minecraft.entity.player.EntityPlayerMP;
import noppes.npcs.api.wrapper.PlayerWrapper;
import noppes.npcs.api.wrapper.gui.CustomGuiScrollWrapper;
import noppes.npcs.api.wrapper.gui.CustomGuiTextFieldWrapper;
import noppes.npcs.api.wrapper.gui.CustomGuiWrapper;
import noppes.npcs.controllers.CustomGuiController;

import java.util.ArrayList;

public class IndividualSpawnGui extends EssentialsMultiselectScrollGuiBase implements EssentialsFormGui {
    private IndividualSpawnData spawnData;
    private int submitButton;
    private SpawnData data;
    private String[] spawnTimes;

    public IndividualSpawnGui(){
        super(1003, new int[]{301});
        this.submitButton=502;
    }

    public IndividualSpawnGui(IndividualSpawnData spawnData, SpawnData data){
        super(1003, new int[]{301});
        this.spawnData=spawnData;
        this.data=data;
        this.addButton(new EssentialsButton(503, new ActionIdData("OPEN_GUI", 1002)));
        this.submitButton=502;
        this.addButton(new EssentialsButton(500, new ActionData("EDIT_TIMES")));
        this.addButton(new EssentialsButton(501, new ActionData("CLEAR_TIMES")));
        this.addButton(new EssentialsButton(504, new ActionData("RETURN_INDIVIDUAL")));
        this.addButton(new EssentialsButton(505, new ActionData("CANCEL_TIMES")));
    }

    public int getSubmitButton(){
        return this.submitButton;
    }

    public IndividualSpawnData getSpawnData(){
        return this.spawnData;
    }

    @Override
    public void init(EntityPlayerMP player){
        PlayerWrapper playerWrapper=new PlayerWrapper(player);
        this.spawnTimes=this.getSpawnTimes();
        CustomGuiWrapper gui=this.createGui(player);
        PixelmonEssentials.essentialsGuisHandler.addOrReplaceGui(player, this);
        playerWrapper.showCustomGui(gui);
    }

    public CustomGuiWrapper createSpawnTimesGui(EntityPlayerMP playerMP){
        CustomGuiWrapper gui=new CustomGuiWrapper(this.getId(), 256, 256, false);
        gui.setBackgroundTexture("customnpcs:textures/gui/bgfilled.png");
        gui.addLabel(201, "Spawn Times", 100, 5, 128, 20);
        String[] times=new String[]{"Dawn", "Morning", "Afternoon", "Dusk", "Night"};
        gui.addScroll(301, 70, 30, 120, 100, times).setMultiSelect(true);
        gui.addButton(504, "Save", 70, 135, 48, 20);
        gui.addButton(505, "Cancel", 140, 135, 48, 20);
        return gui;
    }

    public CustomGuiWrapper createGui(EntityPlayerMP playerMP){
        CustomGuiWrapper gui=new CustomGuiWrapper(this.getId(), 256, 256, false);
        gui.setBackgroundTexture("customnpcs:textures/gui/bgfilled.png");
        gui.addLabel(201, "Add Species", 100, 5, 128, 20);
        gui.addLabel(202, "Name:", 40, 50, 128, 20);
        gui.addTextField(400, 73, 50, 80, 20).setText(this.spawnData.getSpeciesName());
        gui.addLabel(208, "Specs:", 122, 50, 110, 20);
        gui.addTextField(404, 157, 50, 90, 20).setText(this.spawnData.getSpecsString());
        gui.addLabel(203, "Min Level:", 20, 80, 128, 20);
        gui.addTextField(401, 73, 80, 35, 20).setText(this.spawnData.getMinLevel()+"");
        gui.addLabel(204, "Max Level:", 18, 110, 128, 20);
        gui.addTextField(402, 73, 110, 35, 20).setText(this.spawnData.getMaxLevel()+"");
        gui.addLabel(205, "Rarity:", 18, 140, 128, 20);
        gui.addTextField(403, 73, 140, 35, 20).setText(this.spawnData.getRarity()+"");
        gui.addLabel(206, "Spawn times:", 141, 70, 128, 20);
        gui.addScroll(300, 140, 87, 100, 78, spawnTimes);
        gui.addLabel(207, "Note: Having no spawn times means they can always spawn", 20, 175, 112, 20);
        gui.addButton(500, "Edit", 140, 170, 48, 20);
        gui.addButton(501, "Clear", 192, 170, 48, 20);
        gui.addButton(502, "Save", 70, 210, 48, 20);
        gui.addButton(503, "Cancel", 140, 210, 48, 20);
        return gui;
    }

    public void setSpawnTimes(String[] times){
        this.spawnTimes=times;
    }

    public String[] getSpawnTimes(){
        String[] spawnTimes=new String[this.spawnData.getDayPhases().size()];
        for(int i=0;i<spawnTimes.length;i++){
            spawnTimes[i]=DaytimeUtils.getNameFromEnum(this.spawnData.getDayPhases().get(i));
        }
        return spawnTimes;
    }

    @Override
    public void updateFromMultiSelect(String[] selection){
        this.spawnTimes=selection;
    }

    @Override
    public void sendForm(EntityPlayerMP player){
        CustomGuiWrapper gui=CustomGuiController.getOpenGui(player);
        String errorString="";
        String speciesName="";
        int minLevel=0;
        int maxLevel=0;
        int rarity=0;
        int state=0;
        ArrayList<DaytimeUtils.EnumDayPhase> dayPhases=new ArrayList<DaytimeUtils.EnumDayPhase>();
        while(errorString.equals("")&&state<9){
            if(state==0){
                if(!EnumSpecies.contains(((CustomGuiTextFieldWrapper)gui.getComponent(400)).getText().toLowerCase()).isPresent()){
                    errorString="ERROR: Not a valid species name";
                }
                else {
                    speciesName=((CustomGuiTextFieldWrapper)gui.getComponent(400)).getText().toLowerCase();
                }
            }
            else if(state==1){
                try{
                    minLevel=Integer.parseInt(((CustomGuiTextFieldWrapper)gui.getComponent(401)).getText());
                }catch (NumberFormatException e){
                    errorString="ERROR: Minimum level is not a valid number";
                }
            }
            else if(state==2&&minLevel<1){
                errorString="ERROR: Minimum level is lower than 1.";
            }
            else if(state==3){
                try{
                    maxLevel=Integer.parseInt(((CustomGuiTextFieldWrapper)gui.getComponent(402)).getText());
                }catch (NumberFormatException e){
                    errorString="ERROR: Maximum level is not a valid number";
                }
            }
            else if(state==4&&(maxLevel<minLevel)){
                errorString="ERROR: Maximum level is lower than minimum level.";
            }
            else if(state==5&&(maxLevel>PixelmonConfig.maxLevel)){
                errorString="ERROR: Maximum level is higher than the one in the pixelmon config file.";
            }
            else if(state==6){
                try{
                    rarity=Integer.parseInt(((CustomGuiTextFieldWrapper)gui.getComponent(403)).getText());
                }catch (NumberFormatException e){
                    errorString="ERROR: Rarity is not a valid number";
                }
            }
            else if(state==7&&rarity<=0){
                errorString="ERROR: Rarity has to be higher than 0";
            }
            else if(state==8){
                String[] times=((CustomGuiScrollWrapper)gui.getComponent(300)).getList();
                for(String time:times){
                    if(!time.equals("")){
                        dayPhases.add(DaytimeUtils.getEnumFromName(time));
                    }
                }
            }
            state++;
        }
        if(!errorString.equals("")){
            gui.addLabel(208, errorString, 10, 25, 236, 20, 16711680);
            String[] spawnTimes=this.getSpawnTimes();
            gui.addScroll(300, 140, 87, 100, 78, spawnTimes);
            gui.update(new PlayerWrapper(player));
        }
        else{
            this.spawnData.setSpeciesName(speciesName);
            String specs=((CustomGuiTextFieldWrapper)gui.getComponent(404)).getText();
            if(specs!=null){
                this.spawnData.setSpecs(specs);
            }
            this.spawnData.setMinLevel(minLevel);
            this.spawnData.setMaxLevel(maxLevel);
            this.spawnData.setRarity(rarity);
            this.spawnData.setDayPhases(dayPhases);
            this.data.addOrReplaceSpecies(this.spawnData);
            SpawnerInfoGui spawnerInfoGui=new SpawnerInfoGui();
            spawnerInfoGui.init(player);
        }
    }
}
