package it.hurts.weever.rotp_lovers.init;

import it.hurts.weever.rotp_lovers.LoversAddon;

import net.minecraft.util.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class InitSounds {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(
            ForgeRegistries.SOUND_EVENTS, LoversAddon.MOD_ID);

//    public static final RegistryObject<SoundEvent> PJ_INJECT = SOUNDS.register("pj_inject",
//            () -> new SoundEvent(new ResourceLocation(MandomAddon.MOD_ID, "pj_inject"))
//    );
}
