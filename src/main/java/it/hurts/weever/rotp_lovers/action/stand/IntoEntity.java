package it.hurts.weever.rotp_lovers.action.stand;

import com.github.standobyte.jojo.action.ActionConditionResult;
import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityTask;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.github.standobyte.jojo.util.mod.JojoModUtil;
import it.hurts.weever.rotp_lovers.LoversAddon;
import it.hurts.weever.rotp_lovers.LoversConfig;
import it.hurts.weever.rotp_lovers.Util;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.Map;

public class IntoEntity extends StandEntityAction {
    public IntoEntity(Builder builder) {
        super(builder);
    }

    @Override
    public boolean standCanTickPerform(World world, StandEntity standEntity, IStandPower userPower, StandEntityTask task) {
        return task.getTarget().getType() == ActionTarget.TargetType.ENTITY;
    }

    @Override
    public TargetRequirement getTargetRequirement() {
        return TargetRequirement.ENTITY;
    }

    @Override
    public void standPerform(@NotNull World world, StandEntity standEntity, IStandPower userPower, StandEntityTask task) {
        if (!world.isClientSide()) {
            ActionTarget target = task.getTarget();
            PlayerEntity user = (PlayerEntity) userPower.getUser();
            switch (target.getType()) {
                case ENTITY:
                    if (target.getEntity() instanceof LivingEntity && target.getEntity().isAlive()) {
                        LivingEntity targetEntity = (LivingEntity) target.getEntity();
                        if (Util.getPlayers().containsValue(user)) {
                            Util.getPlayers().entrySet().removeIf(entry -> entry.getValue() == user);
                        } else {
                            Util.getPlayers().putIfAbsent(targetEntity, user);
                        }
                    }
                    break;
                case BLOCK:
                case EMPTY:
                default:
                    Util.getPlayers().entrySet().removeIf(entry -> entry.getKey() == user);
                    break;
            }
        }
    }

    @Mod.EventBusSubscriber(modid = LoversAddon.MOD_ID)
    public static class IntoEntityHandler {
        public static int getMultipleDamage() {
            return LoversConfig.getCommonConfigInstance(false).DefaultMultipleDamage.get();
        }

        @SubscribeEvent
        public static void livingHurtEvent(LivingHurtEvent event) {
            LivingEntity entity = event.getEntityLiving();
            if (entity == null || entity.isDeadOrDying() || !entity.isAlive()) return;
            Map<LivingEntity, PlayerEntity> players = Util.getPlayers();
            if (players.containsValue(entity)) {
                for (Map.Entry<LivingEntity, PlayerEntity> entry : players.entrySet()) {
                    if (entry.getValue() == entity) {
                        entry.getKey().hurt(DamageSource.playerAttack(entry.getValue()), event.getAmount() * getMultipleDamage());
                        event.setAmount(event.getAmount() / 2);
                        // event.setCanceled(true);
                    }
                }
            }
        }

        @SubscribeEvent
        public static void livingDeathEvent(LivingDeathEvent event) {
            LivingEntity entity = event.getEntityLiving();
            Util.getPlayers().entrySet().removeIf(entry -> entry.getKey() == entity);
            Util.getPlayers().entrySet().removeIf(entry -> entry.getValue() == entity);
        }
    }
}
