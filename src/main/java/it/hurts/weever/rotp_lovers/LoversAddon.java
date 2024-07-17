package it.hurts.weever.rotp_lovers;

import it.hurts.weever.rotp_lovers.init.InitEntities;
import it.hurts.weever.rotp_lovers.init.InitSounds;
import it.hurts.weever.rotp_lovers.init.InitStands;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(LoversAddon.MOD_ID)
public class LoversAddon {
    public static final String MOD_ID = "rotp_lovers";
    public static final Logger LOGGER = LogManager.getLogger();

    public LoversAddon() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, LoversConfig.commonSpec);
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        InitEntities.ENTITIES.register(modEventBus);
        InitSounds.SOUNDS.register(modEventBus);
        InitStands.ACTIONS.register(modEventBus);
        InitStands.STANDS.register(modEventBus);
    }

    public static Logger getLogger() {
        return LOGGER;
    }
}
