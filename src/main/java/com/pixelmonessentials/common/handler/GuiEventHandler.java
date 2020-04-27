package com.pixelmonessentials.common.handler;

import com.pixelmonessentials.PixelmonEssentials;
import com.pixelmonessentials.common.api.action.Action;
import com.pixelmonessentials.common.api.data.TrainerNPCData;
import com.pixelmonessentials.common.api.gui.EssentialsButton;
import com.pixelmonessentials.common.api.gui.EssentialsGuis;
import com.pixelmonessentials.common.guis.TrainerDataGui;
import com.pixelmonessentials.common.util.EssentialsLogger;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import noppes.npcs.api.INbt;
import noppes.npcs.api.event.CustomGuiEvent;
import noppes.npcs.entity.EntityNPCInterface;

import java.util.UUID;

public class GuiEventHandler {
    @SubscribeEvent
    public void onGuiButton(CustomGuiEvent.ButtonEvent event){
        EssentialsGuis gui= PixelmonEssentials.essentialsGuisHandler.getGui(event.player.getMCEntity());
        if(gui!=null){
            for(EssentialsButton button: gui.getButtons()){
                if(button.getId()==event.buttonId){
                    if(PixelmonEssentials.actionHandler.getType(button.getAction()).getName().equals("SAVE_TRAINER")){
                        TrainerNPCData trainerData=new TrainerNPCData();
                        for(int i=0;i<event.data.length;i++){
                            INbt nbt=event.data[i];
                            switch (nbt.getInteger("id")){
                                case 1:
                                    trainerData.setInitDialogId(Integer.parseInt(nbt.getString("text")));
                                    break;
                                case 2:
                                    trainerData.setWinDialogId(Integer.parseInt(nbt.getString("text")));
                                    break;
                                case 3:
                                    trainerData.setLossDialogId(Integer.parseInt(nbt.getString("text")));
                                    break;
                                case 5:
                                    trainerData.setEncounterTheme(nbt.getString("text"));
                                    break;
                                case 6:
                                    trainerData.setBattleTheme(nbt.getString("text"));
                                    break;
                                case 7:
                                    trainerData.setVictoryTheme(nbt.getString("text"));
                                    break;
                                default:
                                    break;
                            }
                        }
                        trainerData.setNpcTrainerData((EntityNPCInterface) event.player.getMCEntity().getServerWorld().getEntityFromUuid(UUID.fromString(button.getAction().value)));
                    }
                    else{
                        PixelmonEssentials.actionHandler.doActions(button.getActions(), event.player.getMCEntity());
                    }
                }
            }
        }
        else{
            EssentialsLogger.info("???");
        }
    }
}
