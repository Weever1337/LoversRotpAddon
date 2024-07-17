package it.hurts.weever.rotp_lovers;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ArmorStandEntity;
import net.minecraft.entity.player.PlayerEntity;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Util {
    private static final Map<LivingEntity, PlayerEntity> PLAYERS = new HashMap<>();
    public static Map<LivingEntity, PlayerEntity> getPlayers() {
        return PLAYERS;
    }
}
