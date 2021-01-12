package com.gladurbad.nebula.check.combat;

import com.gladurbad.nebula.Nebula;
import com.gladurbad.nebula.check.Check;
import com.gladurbad.nebula.data.PlayerData;
import net.minecraft.server.v1_8_R3.AxisAlignedBB;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.util.Vector;

public class Reach extends Check implements Listener {

    public Reach(final String name) {
        super(name);
    }

    @EventHandler
    public void onEntityDamageByEntity(final EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            final PlayerData playerData = Nebula.instance.getPlayerDataManager().getPlayerData((Player) event.getDamager());
            if (playerData != null) {
                final Vector attackerVector = playerData.getBukkitPlayer().getLocation().toVector().setY(0);
                final Vector victimVector = event.getEntity().getLocation().toVector().setY(0);

                final double distance = attackerVector.distance(victimVector);

                final AxisAlignedBB boundingBox = ((CraftEntity) event.getEntity()).getHandle().getBoundingBox();

                final double widthX = Math.abs(boundingBox.a - boundingBox.d) / 2;
                final double widthZ = Math.abs(boundingBox.c - boundingBox.f) / 2;
                final double girth = Math.hypot(widthX, widthZ);

                final double reach = distance - girth;

                if (reach > 3.3) {
                    if (++playerData.getDataStorage().reachVerbose > 5) {
                        flag(playerData, String.format("reach=%.4f", reach));
                    }
                } else {
                    playerData.getDataStorage().reachVerbose = Math.max(playerData.getDataStorage().reachVerbose - 1, 0);
                }
            }
        }
    }
}
