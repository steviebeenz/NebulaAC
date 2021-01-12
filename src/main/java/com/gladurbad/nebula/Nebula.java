package com.gladurbad.nebula;

import com.gladurbad.nebula.check.combat.KillAura;
import com.gladurbad.nebula.check.combat.Reach;
import com.gladurbad.nebula.check.movement.Fly;
import com.gladurbad.nebula.check.movement.Speed;
import com.gladurbad.nebula.listener.JoinQuitListener;
import com.gladurbad.nebula.manager.PlayerDataManager;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class Nebula extends JavaPlugin {

    public static Nebula instance;

    private final PlayerDataManager playerDataManager = new PlayerDataManager();

    @Override
    public void onEnable() {
        instance = this;

        for (final Player player : Bukkit.getServer().getOnlinePlayers()) {
            playerDataManager.add(player);
        }

        Bukkit.getServer().getPluginManager().registerEvents(new JoinQuitListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new Reach("Reach"), this);
        Bukkit.getServer().getPluginManager().registerEvents(new Speed("Speed"), this);
        Bukkit.getServer().getPluginManager().registerEvents(new Fly("Fly"), this);
        Bukkit.getServer().getPluginManager().registerEvents(new KillAura("KillAura"), this);
    }

    @Override
    public void onDisable() {

    }
}
