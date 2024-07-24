package it.hurts.weever.rotp_lovers.init;

import com.github.standobyte.jojo.action.Action;
import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.entity.stand.StandEntityType;
import com.github.standobyte.jojo.init.power.stand.EntityStandRegistryObject;
import com.github.standobyte.jojo.power.impl.stand.stats.StandStats;
import com.github.standobyte.jojo.power.impl.stand.type.EntityStandType;
import com.github.standobyte.jojo.power.impl.stand.type.StandType;
import com.github.standobyte.jojo.util.mod.StoryPart;
import it.hurts.weever.rotp_lovers.LoversAddon;
import it.hurts.weever.rotp_lovers.action.stand.*;
import it.hurts.weever.rotp_lovers.entity.LoversEntity;

import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

public class InitStands {
    @SuppressWarnings("unchecked")
    public static final DeferredRegister<Action<?>> ACTIONS = DeferredRegister.create(
            (Class<Action<?>>) ((Class<?>) Action.class), LoversAddon.MOD_ID);
    @SuppressWarnings("unchecked")
    public static final DeferredRegister<StandType<?>> STANDS = DeferredRegister.create(
            (Class<StandType<?>>) ((Class<?>) StandType.class), LoversAddon.MOD_ID);
    
 // ======================================== Lovers ========================================
    public static final RegistryObject<StandEntityAction> INTO_OR_RETRACT = ACTIONS.register("into_entity",
         () -> new IntoEntity(new StandEntityAction.Builder()
                 .cooldown(6)
                 .staminaCost(100)
         ));

    public static final RegistryObject<StandEntityAction> RETRACT = ACTIONS.register("retract",
            () -> new RetractFromEntity(new StandEntityAction.Builder()
                    .cooldown(6)
            ));

    public static final EntityStandRegistryObject<EntityStandType<StandStats>, StandEntityType<LoversEntity>> STAND_LOVERS =
            new EntityStandRegistryObject<>("lovers",
                    STANDS, 
                    () -> new EntityStandType.Builder<>()
                    .color(0xC6C07B)
                    .storyPartName(StoryPart.STARDUST_CRUSADERS.getName())
                    .leftClickHotbar(
                            INTO_OR_RETRACT.get()
                    ).rightClickHotbar(
                            RETRACT.get()
                    ).defaultStats(StandStats.class, new StandStats.Builder()
                            .power(2.0)
                            .speed(6.0)
                            .range(250.0, 300.0)
                            .durability(14.0)
                            .precision(6.0)
                            .randomWeight(2.0)
                            .build("Lovers")
                    )
                    .disableManualControl().disableStandLeap()
                    .build(),
                    InitEntities.ENTITIES,
                    () -> new StandEntityType<>(LoversEntity::new, 0.65F, 1.8F)
                            .summonSound(InitSounds.VOID)
                            .unsummonSound(InitSounds.VOID))
            .withDefaultStandAttributes();
}