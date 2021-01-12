package com.gladurbad.nebula.check;

import com.gladurbad.nebula.Nebula;
import com.gladurbad.nebula.data.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public abstract class Check {
    private final Map<UUID, Integer> violationsMap = new HashMap<>();

    private final String name;

    public Check(final String name) {
        this.name = name;
    }

    public void flag(final PlayerData playerData, final String information) {
        final int violations = violationsMap.getOrDefault(playerData.getUuid(), 0) + 1;
        violationsMap.put(playerData.getUuid(), violations);

        for (final Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage(
                    ChatColor.translateAlternateColorCodes('&',
                            "&5Nebula > &7" + playerData.getBukkitPlayer().getName() +
                                    " failed " + name + " [" + information + "] (x" + violations + ")"
                    )
            );
        }
    }
}
