package it.hurts.weever.rotp_lovers.client.ui.marker;

import com.github.standobyte.jojo.client.ui.actionshud.ActionsOverlayGui;
import com.github.standobyte.jojo.client.ui.marker.MarkerRenderer;
import com.github.standobyte.jojo.init.power.stand.ModStandsInit;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.github.standobyte.jojo.power.impl.stand.type.StandType;
import it.hurts.weever.rotp_lovers.LoversAddon;
import it.hurts.weever.rotp_lovers.Util;
import it.hurts.weever.rotp_lovers.init.InitStands;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Stream;

public class LoversMarker extends MarkerRenderer {

    public LoversMarker(Minecraft mc){
        super(new ResourceLocation(LoversAddon.MOD_ID,"textures/power/lovers.png"), mc);
    }

    @Override
    protected boolean shouldRender() {
        AtomicBoolean render = new AtomicBoolean(false);
        ActionsOverlayGui hud = ActionsOverlayGui.getInstance();
        render.set(hud.showExtraActionHud(InitStands.INTO_OR_RETRACT.get()));
        return render.get();
    }

    protected static class Marker extends MarkerRenderer.MarkerInstance {
        public Marker(Vector3d pos, boolean outlined) {
            super(pos, outlined);
        }
    }

    @Override
    protected void updatePositions(List<MarkerRenderer.MarkerInstance> list, float partialTick) {
        IStandPower.getStandPowerOptional(this.mc.player).ifPresent((stand) ->{
            Targets(this.mc.player).forEach(target -> {
                list.add(new Marker(target.getPosition(partialTick).add(0,target.getBbHeight()*1.1,0),true));
            });
        });
    }


    public static Stream<LivingEntity> Targets(LivingEntity user){
        World world = user.level;
        Stream<LivingEntity> entidades = world.getEntitiesOfClass(LivingEntity.class,user.getBoundingBox().inflate(100),
                EntityPredicates.ENTITY_STILL_ALIVE).stream().filter(entity -> Util.getPlayers().containsKey(entity) && Util.getPlayers().containsValue(user));
        return entidades;
    }
}