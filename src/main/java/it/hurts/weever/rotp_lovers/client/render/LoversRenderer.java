package it.hurts.weever.rotp_lovers.client.render;

import com.github.standobyte.jojo.client.render.entity.model.stand.StandEntityModel;
import com.github.standobyte.jojo.client.render.entity.renderer.stand.StandEntityRenderer;
import it.hurts.weever.rotp_lovers.LoversAddon;
import it.hurts.weever.rotp_lovers.entity.LoversEntity;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class LoversRenderer extends StandEntityRenderer<LoversEntity, StandEntityModel<LoversEntity>> {
    public LoversRenderer(EntityRendererManager renderManager) {
        super(renderManager, new LoversModel(), new ResourceLocation(LoversAddon.MOD_ID, "textures/entity/stand/lovers.png"), 0);
    }
}