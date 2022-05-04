package carpettisaddition;

import carpet.CarpetExtension;
import carpet.CarpetServer;
import carpet.script.annotation.AnnotationParser;
import carpettisaddition.commands.lifetime.LifeTimeCommand;
import carpettisaddition.commands.lifetime.LifeTimeTracker;
import carpettisaddition.commands.manipulate.ManipulateCommand;
import carpettisaddition.commands.raid.RaidCommand;
import carpettisaddition.commands.raid.RaidTracker;
import carpettisaddition.commands.refresh.RefreshCommand;
import carpettisaddition.commands.removeentity.RemoveEntityCommand;
import carpettisaddition.logging.TISAdditionLoggerRegistry;
import carpettisaddition.logging.loggers.lightqueue.LightQueueHUDLogger;
import carpettisaddition.logging.loggers.microtiming.MicroTimingLoggerManager;
import carpettisaddition.logging.loggers.microtiming.marker.MicroTimingMarkerManager;
import carpettisaddition.logging.loggers.microtiming.utils.MicroTimingStandardCarpetLogger;
import carpettisaddition.script.Functions;
import carpettisaddition.script.MicroTimingEvent;
import carpettisaddition.translations.TISAdditionTranslations;
import carpettisaddition.utils.deobfuscator.StackTraceDeobfuscator;
import com.google.common.collect.Maps;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;


public class CarpetTISAdditionServer implements CarpetExtension
{
    public static final CarpetTISAdditionServer INSTANCE = new CarpetTISAdditionServer();
    public static final String name = CarpetTISAdditionMod.getModId();
    public static final String fancyName = "Carpet TIS Addition";
    public static final String compactName = name.replace("-","");  // carpettisaddition
    public static final Logger LOGGER = LogManager.getLogger(fancyName);
    public static MinecraftServer minecraft_server;

    @Override
    public String version()
    {
        return name;
    }

    public static void registerExtension()
    {
        CarpetServer.manageExtension(INSTANCE);
    }

    @Override
    public void onGameStarted()
    {
        CarpetServer.settingsManager.parseSettingsClass(CarpetTISAdditionSettings.class);

        StackTraceDeobfuscator.fetchMapping();
        TISAdditionTranslations.loadTranslations();

        // scarpet things
        AnnotationParser.parseFunctionClass(Functions.class);
        MicroTimingEvent.noop();  //to register event properly
    }

    @Override
    public void onServerLoaded(MinecraftServer server)
    {
        minecraft_server = server;
    }

    /**
     * Carpet has issue (bug) to call onServerLoadedWorlds in IntegratedServer, so just do it myself to make sure it works properly
     * Only in <= 1.15.x
     */
    public void onServerLoadedWorldsCTA(MinecraftServer server)
    {
        MicroTimingLoggerManager.attachServer(server);
        LifeTimeTracker.attachServer(server);
        LightQueueHUDLogger.getInstance().attachServer(server);
        MicroTimingMarkerManager.getInstance().clear();
    }

    @Override
    public void onServerClosed(MinecraftServer server)
    {
        RaidTracker.getInstance().stop();
        MicroTimingLoggerManager.detachServer();
        LifeTimeTracker.detachServer();
    }

    @Override
    public void onTick(MinecraftServer server)
    {
        LightQueueHUDLogger.getInstance().tick();
        MicroTimingMarkerManager.getInstance().tick();
    }

    public void onCarpetClientHello(ServerPlayerEntity player)
    {
        MicroTimingStandardCarpetLogger.getInstance().onCarpetClientHello(player);
    }

    @Override
    public void registerCommands(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandBuildContext)
    {
        RaidCommand.getInstance().registerCommand(dispatcher, commandBuildContext);
        LifeTimeCommand.getInstance().registerCommand(dispatcher, commandBuildContext);
        RefreshCommand.getInstance().registerCommand(dispatcher, commandBuildContext);
        RemoveEntityCommand.getInstance().registerCommand(dispatcher, commandBuildContext);
        ManipulateCommand.getInstance().registerCommand(dispatcher, commandBuildContext);
    }

    @Override
    public void registerLoggers()
    {
        TISAdditionLoggerRegistry.registerLoggers();
    }

    @Override
    public Map<String, String> canHasTranslations(String lang)
    {
        Map<String, String> trimmedTranslation = Maps.newHashMap();
        String prefix = TISAdditionTranslations.TRANSLATION_KEY_PREFIX + "carpet_extension.";
        TISAdditionTranslations.getTranslationFromResourcePath(lang).forEach((key, value) -> {
            if (key.startsWith(prefix))
            {
                trimmedTranslation.put(key.substring(prefix.length()), value);
            }
        });
        return trimmedTranslation;
    }
}
