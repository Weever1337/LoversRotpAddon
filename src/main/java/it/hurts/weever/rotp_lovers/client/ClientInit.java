package it.hurts.weever.rotp_lovers.client;

import com.github.standobyte.jojo.client.ui.marker.MarkerRenderer;
import it.hurts.weever.rotp_lovers.LoversAddon;
import it.hurts.weever.rotp_lovers.client.render.LoversRenderer;
import it.hurts.weever.rotp_lovers.client.ui.marker.LoversMarker;
import it.hurts.weever.rotp_lovers.init.InitStands;

import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(modid = LoversAddon.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientInit {
    @SubscribeEvent
    public static void onFMLClientSetup(FMLClientSetupEvent event) {
        Minecraft mc = event.getMinecraftSupplier().get();
        RenderingRegistry.registerEntityRenderingHandler(
                InitStands.STAND_LOVERS.getEntityType(), LoversRenderer::new
        );
        MarkerRenderer.Handler.addRenderer(new LoversMarker(mc));
    }
}