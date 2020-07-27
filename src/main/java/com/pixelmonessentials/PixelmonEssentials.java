package com.pixelmonessentials;

import com.pixelmonessentials.common.api.action.ActionHandler;
import com.pixelmonessentials.common.api.gui.EssentialsGuisHandler;
import com.pixelmonessentials.common.api.requirement.RequirementHandler;
import com.pixelmonessentials.common.battles.rules.CustomRulesManager;
import com.pixelmonessentials.common.commands.*;
import com.pixelmonessentials.common.handler.*;
import com.pixelmonessentials.common.quests.QuestsManager;
import com.pixelmonessentials.common.spawners.SpawnerDataManagement;
import com.pixelmonessentials.common.teams.TeamManager;
import com.pixelmonessentials.common.util.EssentialsLogger;
import com.pixelmonessentials.common.util.Reference;
import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.api.pokemon.PokemonSpec;
import com.pixelmonmod.pixelmon.battles.BattleRegistry;
import com.pixelmonmod.pixelmon.battles.controller.participants.PlayerParticipant;
import com.pixelmonmod.pixelmon.battles.controller.participants.WildPixelmonParticipant;
import com.pixelmonmod.pixelmon.comm.packetHandlers.battles.rules.ProposeBattleRules;
import com.pixelmonmod.pixelmon.entities.pixelmon.EntityPixelmon;
import com.pixelmonmod.pixelmon.worldGeneration.dimension.ultraspace.EnumBiome;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandDebug;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.relauncher.Side;
import noppes.npcs.CustomNpcs;
import noppes.npcs.LogWriter;
import noppes.npcs.api.event.NpcEvent;
import noppes.npcs.api.wrapper.PlayerWrapper;
import noppes.npcs.api.wrapper.WrapperNpcAPI;

import java.io.File;

@Mod(
        modid="pixelmonessentials",
        name="Pixelmon Essentials",
        dependencies="required-after:pixelmon;required-after:customnpcs;",
        version ="1.0.0",
        acceptableRemoteVersions = "*",
        acceptedMinecraftVersions="[1.12.2]"
)
public class PixelmonEssentials {
    public static final String MOD_ID="pixelmonessentials";
    public static final String MOD_NAME="Pixelmon Essentials";
    public static final String VERSION="1.0.2";
    @Mod.Instance("pixelmonessentials")
    public static PixelmonEssentials INSTANCE;

    public static RequirementHandler requirementHandler=new RequirementHandler();
    public TickHandler tickHandler=new TickHandler();
    public static TeamManager teamManager=new TeamManager();
    public static ActionHandler actionHandler=new ActionHandler();
    public static EssentialsGuisHandler essentialsGuisHandler=new EssentialsGuisHandler();
    public static SpawnerDataManagement spawnerDataManagement=new SpawnerDataManagement();
    public static QuestsManager questsManager=new QuestsManager();
    public static CustomRulesManager customRulesManager=new CustomRulesManager();
    public static File configFolder=new File(".");
    public static File dataFolder=new File(".");

    public PixelmonEssentials(){
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event){
        requirementHandler.init();
        actionHandler.init();
        essentialsGuisHandler.init();
        questsManager.init();
    }

    @Mod.EventHandler
    public void preInit(FMLInitializationEvent event){
        MinecraftForge.EVENT_BUS.register(new RightClickEventHandler());
        MinecraftForge.EVENT_BUS.register(new PixelmonSpawnerEventHandler());
        MinecraftForge.EVENT_BUS.register(TickHandler.class);
        Pixelmon.EVENT_BUS.register(new PixelmonSpawnerEventHandler());
        Pixelmon.EVENT_BUS.register(new QuestEventHandler());
        Pixelmon.EVENT_BUS.register(new TrainerEventHandler());
        WrapperNpcAPI.EVENT_BUS.register(new GuiEventHandler());
        WrapperNpcAPI.EVENT_BUS.register(new TrainerEventHandler());
        WrapperNpcAPI.EVENT_BUS.register(TickHandler.class);
    }

    @Mod.EventHandler
    public void onServerStarted(FMLServerStartedEvent event){
        getServerSaveDirectory();
        dataFolder=new File(PixelmonEssentials.configFolder, "data/");
        teamManager.init();
        spawnerDataManagement.init();
        questsManager.loadQuests();
        customRulesManager.init();
    }

    @Mod.EventHandler
    public void onServerStart(FMLServerStartingEvent event){
        event.registerServerCommand(new CommandEwand());
        event.registerServerCommand(new CommandEspawner());
        event.registerServerCommand(new CommandPEQuest());
        event.registerServerCommand(new CommandRules());
        event.registerServerCommand(new CommandTeamsReload());
    }

    @Mod.EventHandler
    public void postinit(FMLPostInitializationEvent event) {
        Pixelmon.network.registerMessage(BattleRulePacketHandler.class, ProposeBattleRules.class, 23, Side.SERVER);
    }

    @Mod.EventHandler
    public void onServerClose(FMLServerStoppingEvent event){
        spawnerDataManagement.save();
        questsManager.save();
        customRulesManager.saveAll();
    }

    public static void getServerSaveDirectory(){
        try {
            File dir = new File(".");
            if (CustomNpcs.Server != null) {
                if (!CustomNpcs.Server.isDedicatedServer()) {
                    dir = new File(Minecraft.getMinecraft().gameDir, "saves");
                }

                dir = new File(new File(dir, CustomNpcs.Server.getFolderName()), "pixelmonessentials/");
            }

            if (!dir.exists()) {
                dir.mkdirs();
            }

            configFolder=dir;
        } catch (Exception var2) {
            EssentialsLogger.info("Error getting worldsave");
        }
    }
}
