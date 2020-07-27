package com.pixelmonessentials.common.battles;

import com.pixelmonessentials.PixelmonEssentials;
import com.pixelmonessentials.common.api.data.TrainerNPCData;
import com.pixelmonessentials.common.battles.rules.CustomRules;
import com.pixelmonessentials.common.handler.TeamPreviewTimerTask;
import com.pixelmonessentials.common.teams.Team;
import com.pixelmonessentials.common.teams.TeamCategory;
import com.pixelmonessentials.common.util.EssentialsLogger;
import com.pixelmonessentials.common.util.NpcScriptDataManipulator;
import com.pixelmonessentials.common.util.Reference;
import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.storage.PartyStorage;
import com.pixelmonmod.pixelmon.battles.BattleRegistry;
import com.pixelmonmod.pixelmon.battles.controller.participants.BattleParticipant;
import com.pixelmonmod.pixelmon.battles.controller.participants.PlayerParticipant;
import com.pixelmonmod.pixelmon.battles.controller.participants.TrainerParticipant;
import com.pixelmonmod.pixelmon.battles.controller.participants.WildPixelmonParticipant;
import com.pixelmonmod.pixelmon.battles.rules.BattleRules;
import com.pixelmonmod.pixelmon.battles.rules.teamselection.TeamSelectPokemon;
import com.pixelmonmod.pixelmon.battles.rules.teamselection.TeamSelectionList;
import com.pixelmonmod.pixelmon.comm.SetTrainerData;
import com.pixelmonmod.pixelmon.comm.packetHandlers.battles.rules.ShowTeamSelect;
import com.pixelmonmod.pixelmon.config.PixelmonEntityList;
import com.pixelmonmod.pixelmon.entities.npcs.NPCTrainer;
import com.pixelmonmod.pixelmon.entities.pixelmon.EntityPixelmon;
import com.pixelmonmod.pixelmon.storage.TrainerPartyStorage;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fml.server.FMLServerHandler;
import noppes.npcs.api.wrapper.NPCWrapper;
import noppes.npcs.controllers.DialogController;
import noppes.npcs.controllers.data.Dialog;
import noppes.npcs.entity.EntityNPCInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

public class CustomBattleHandler {
    static PixelmonEssentials mod;
    public static CustomBattleHandler instance;

    public static List<CustomNPCBattle> battles = new ArrayList<CustomNPCBattle>();

    public CustomBattleHandler()
    {
        this.mod = PixelmonEssentials.INSTANCE;
        this.instance = this;
    }

    public static void createCustomBattle(EntityPlayerMP player, EntityNPCInterface npc) {
        if (BattleRegistry.getBattle(player) != null) {
            player.sendMessage(new TextComponentString(Reference.red + "Cannot Battle!"));
            return;
        }
        EntityPixelmon pixelmon= Pixelmon.storageManager.getParty(player).getAndSendOutFirstAblePokemon(player);
        if(pixelmon==null){
            player.sendMessage(new TextComponentString(Reference.red + "You have no pokemon that are able to battle!"));
            return;
        }
        ScriptObjectMirror object= NpcScriptDataManipulator.getJavascriptObject(new NPCWrapper(npc), "trainerData");
        Team npcTeam=null;
        String category=(String) object.get("category");
        String teamName=(String) object.get("team");
        if(category!=null){
            TeamCategory teamCategory=PixelmonEssentials.teamManager.getCategory(category);
            if(teamCategory!=null&&teamName!=null){
                npcTeam=teamCategory.getTeam(teamName);
            }
        }
        if(npcTeam==null){
            player.sendMessage(new TextComponentString(Reference.red + "NPC doesn't have a team"));
            return;
        }
        String rules=(String) object.get("rules");
        BattleRules battleRules=null;
        CustomNPCBattle battle;
        if(rules!=null){
            CustomRules customRules=PixelmonEssentials.customRulesManager.getRules(rules);
            if(customRules!=null){
                battleRules=customRules.getRules();
            }
        }
        if(battleRules!=null){
            battle=new CustomNPCBattle(npc, battleRules);
        }
        else{
            battle=new CustomNPCBattle(npc);
        }
        int[] levels=new int[6];
        for(int i=0;i<npcTeam.getMembers().size();i++){
            levels[i]=npcTeam.getMember(i).getLevel();
        }
        NPCTrainer trainer = (NPCTrainer) PixelmonEntityList.createEntityByName(npc.display.getName(), player.getEntityWorld());
        SetTrainerData data=new SetTrainerData(battle.getNpc().display.getName(), " ", " ", " ", 0, null);
        trainer.update(data);
        trainer.loadPokemon(npcTeam.getMembers());
        trainer.setPosition(npc.posX,npc.posY,npc.posZ);
        PlayerParticipant playerParticipant = new PlayerParticipant(player, Pixelmon.storageManager.getParty(player).getTeam(), battle.battleType.numPokemon);
        TrainerParticipant trainerParticipant = new TrainerParticipant(trainer, player, battle.battleType.numPokemon, trainer.getPokemonStorage().getTeam());
        battle.setRemainingNPCPokemon(trainerParticipant.countAblePokemon());
        for(int i=0;i<npcTeam.getMembers().size();i++){
            trainer.getPokemonStorage().get(i).setLevel(levels[i]);
        }
        if(battle.teamPreview){
            Timer timer=new Timer();
            timer.schedule(new TeamPreviewTimerTask(battle, new PartyStorage[]{trainerParticipant.getStorage(), playerParticipant.getStorage()}), 100);
        }
        else {
            BattleRegistry.startBattle(new BattleParticipant[]{playerParticipant}, new BattleParticipant[] {trainerParticipant}, battle);
        }
    }
}
