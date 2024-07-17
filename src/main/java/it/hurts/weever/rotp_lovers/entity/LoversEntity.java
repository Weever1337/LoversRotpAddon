package it.hurts.weever.rotp_lovers.entity;

import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityType;

import com.github.standobyte.jojo.init.power.stand.ModStandsInit;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import it.hurts.weever.rotp_lovers.Util;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class LoversEntity extends StandEntity {
    public LoversEntity(StandEntityType<LoversEntity> type, World world) {
        super(type, world);
    }
    @Override
    public void tick() {
        super.tick();
        IStandPower power = this.getUserPower();
        if (power == null || getUser() == null) return;
        if (this.getCurrentTaskAction() == ModStandsInit.UNSUMMON_STAND_ENTITY.get()) {
            Util.getPlayers().entrySet().removeIf(entry -> entry.getValue() == getUser());
        }
    }
}
