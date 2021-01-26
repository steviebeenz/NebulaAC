package com.gladurbad.nebula.check;

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
        if (violations < 50) {
            for (final Player player : Bukkit.getOnlinePlayers()) {
                player.sendMessage(
                        ChatColor.translateAlternateColorCodes('&',
                                "&5Neb&du&5la > &4" + playerData.getBukkitPlayer().getName() +
                                        "&7 failed &f" + name + " &b[" + information + "] &c(x&a" + violations + "&e)"
                        )
                );
            }
        }
            if (violations > 50) {
                playerData.getBukkitPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&',
                        "&c[!] You have been detected for cheating. Please turn your hacks off."
                ));
                playerData.getBukkitPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&',
                        "&c[!] You have been detected for cheating. Please turn your hacks off."
                ));
                playerData.getBukkitPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&',
                        "&c[!] You have been detected for cheating. Please turn your hacks off."
                ));
                playerData.getBukkitPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&',
                        "&c[!] You have been detected for cheating. Please turn your hacks off."
                ));
                playerData.getBukkitPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&',
                        "&c[!] You have been detected for cheating. Please turn your hacks off."
                ));
                playerData.getBukkitPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&',
                        "&c[!] You have been detected for cheating. Please turn your hacks off."
                ));
                playerData.getBukkitPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&',
                        "&c[!] You have been detected for cheating. Please turn your hacks off."
                ));
                playerData.getBukkitPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&',
                        "&c[!] You have been detected for cheating. Please turn your hacks off."
                ));
                playerData.getBukkitPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&',
                        "&c[!] You have been detected for cheating. Please turn your hacks off."
                ));
                playerData.getBukkitPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&',
                        "&c[!] You have been detected for cheating. Please turn your hacks off."
                ));
            }
            if (violations > 55) {
                violationsMap.remove(playerData.getBukkitPlayer().getUniqueId());
                playerData.getBukkitPlayer().kickPlayer("NebulaAC> You are hacking. Please turn off the hacks.");
            }
        }
}
