package it.hurts.weever.rotp_lovers.init;

import it.hurts.weever.rotp_lovers.LoversAddon;

import net.minecraft.entity.EntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class InitEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(
            ForgeRegistries.ENTITIES, LoversAddon.MOD_ID);

};
