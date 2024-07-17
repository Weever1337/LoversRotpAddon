package it.hurts.weever.rotp_lovers;

import com.electronwill.nightconfig.core.CommentedConfig;
import com.electronwill.nightconfig.core.InMemoryCommentedFormat;
import com.github.standobyte.jojo.client.ClientUtil;
import it.hurts.weever.rotp_lovers.network.AddonPackets;
import it.hurts.weever.rotp_lovers.network.server.CommonConfigPacket;
import it.hurts.weever.rotp_lovers.network.server.ResetSyncedCommonConfigPacket;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.server.ServerLifecycleHooks;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nullable;

@EventBusSubscriber(modid = LoversAddon.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class LoversConfig {

    public static class Common {
        private boolean loaded = false;
        public final ForgeConfigSpec.IntValue DefaultMultipleDamage;

        private Common(ForgeConfigSpec.Builder builder) {
            this(builder, null);
        }

        private Common(ForgeConfigSpec.Builder builder, @Nullable String mainPath) {
            if (mainPath != null) {
                builder.push(mainPath);
            }

            builder.push("Lovers Settings");
            DefaultMultipleDamage = builder
                    .translation("rotp_lovers.config.multiple_damage")
                    .comment("    Multiple damage for ALL attacks to Entity if Lovers into Entity.",
                            "    Defaults to 1 (to off)."
                    )
                    .defineInRange("DefaultMultipleDamage", 1, 1, Integer.MAX_VALUE);
            builder.pop();

            if (mainPath != null) {
                builder.pop();
            }
        }

        public boolean isConfigLoaded() {
            return loaded;
        }

        private void onLoadOrReload() {
            loaded = true;
        }



        public static class SyncedValues {
            private final int DefaultMultipleDamage;
            public SyncedValues(PacketBuffer buf) {
                byte[] flags = buf.readByteArray();
                DefaultMultipleDamage = buf.readVarInt();
            }

            public void writeToBuf(PacketBuffer buf) {
                buf.writeVarInt(DefaultMultipleDamage);
            }

            private SyncedValues(Common config) {
                DefaultMultipleDamage = config.DefaultMultipleDamage.get();
            }

            public void changeConfigValues() {
                COMMON_SYNCED_TO_CLIENT.DefaultMultipleDamage.set(DefaultMultipleDamage);
            }

            public static void resetConfig() {
                COMMON_SYNCED_TO_CLIENT.DefaultMultipleDamage.clearCache();
            }

            public static void syncWithClient(ServerPlayerEntity player) {
                AddonPackets.sendToClient(new CommonConfigPacket(new SyncedValues(COMMON_FROM_FILE)), player);
            }

            public static void onPlayerLogout(ServerPlayerEntity player) {
                AddonPackets.sendToClient(new ResetSyncedCommonConfigPacket(), player);
            }
        }
    }

    private static boolean isElementNonNegativeFloat(Object num, boolean moreThanZero) {
        if (num instanceof Double) {
            Double numDouble = (Double) num;
            return (numDouble > 0 || !moreThanZero && numDouble == 0) && Float.isFinite(numDouble.floatValue());
        }
        return false;
    }

    public static class Client {
        private Client(ForgeConfigSpec.Builder builder) {
        }
    }

    static final ForgeConfigSpec commonSpec;
    private static final Common COMMON_FROM_FILE;
    private static final Common COMMON_SYNCED_TO_CLIENT;
    static {
        final Pair<Common, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Common::new);
        commonSpec = specPair.getRight();
        COMMON_FROM_FILE = specPair.getLeft();

        final Pair<Common, ForgeConfigSpec> syncedSpecPair = new ForgeConfigSpec.Builder().configure(builder -> new Common(builder, "synced"));
        CommentedConfig config = CommentedConfig.of(InMemoryCommentedFormat.defaultInstance());
        ForgeConfigSpec syncedSpec = syncedSpecPair.getRight();
        syncedSpec.correct(config);
        syncedSpec.setConfig(config);
        COMMON_SYNCED_TO_CLIENT = syncedSpecPair.getLeft();
    }

    public static Common getCommonConfigInstance(boolean isClientSide) {
        return isClientSide && !ClientUtil.isLocalServer() ? COMMON_SYNCED_TO_CLIENT : COMMON_FROM_FILE;
    }

    static final ForgeConfigSpec clientSpec;
    public static final Client CLIENT;
    static {
        final Pair<Client, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Client::new);
        clientSpec = specPair.getRight();
        CLIENT = specPair.getLeft();
    }

    @SubscribeEvent
    public static void onConfigLoad(ModConfig.ModConfigEvent event) {
        ModConfig config = event.getConfig();
        if (LoversAddon.MOD_ID.equals(config.getModId()) && config.getType() == ModConfig.Type.COMMON) {
            COMMON_FROM_FILE.onLoadOrReload();
        }
    }

    @SubscribeEvent
    public static void onConfigReload(ModConfig.Reloading event) {
        ModConfig config = event.getConfig();
        if (LoversAddon.MOD_ID.equals(config.getModId()) && config.getType() == ModConfig.Type.COMMON) {
            MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
            if (server != null) {
                server.getPlayerList().getPlayers().forEach(Common.SyncedValues::syncWithClient);
            }
        }
    }
}