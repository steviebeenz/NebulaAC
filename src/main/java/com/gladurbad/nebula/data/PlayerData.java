package com.gladurbad.nebula.data;

import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayDeque;
import java.util.UUID;

@Getter
public class PlayerData {

    private final Player bukkitPlayer;
    private final UUID uuid;
    private final DataStorage dataStorage = new DataStorage();

    public PlayerData(final Player bukkitPlayer) {
        this.bukkitPlayer = bukkitPlayer;
        this.uuid = bukkitPlayer.getUniqueId();
        dataStorage.flyFakeGroundLastOnGroundLocation = bukkitPlayer.isOnGround() ? bukkitPlayer.getLocation() : null;
    }

    public class DataStorage {
        public int reachVerbose;
        public long lastDamageTakenTime;
        public int speedGroundTicks, speedAirTicks, speedSinceIceTicks, speedSinceUnderBlockTicks, speedSinceSlimeTicks;
        public int speedYportAirTicks, speedLastAirTicks;
        public double speedYportVerbose;
        public boolean speedYportShouldCheck, speedYportShouldCheck2;
        public double flyLastGroundY;
        public Location lastHorizontalFlyDistanceLocation;
        public int aimHeuristic1Verbose;
        public int speedVerbose;
        public long lastAttackTime;
        public Location flyFakeGroundLastOnGroundLocation;
        public ArrayDeque<Float> pitchSamples = new ArrayDeque<>();
        public double flyFakeGroundVerbose;
        public double speedLastDeltaXZ, speedLastDeltaY;
        public Location speedLastLocation;
        public long lastEventSpeedTimestamp;
        public int eventSpeedTicks;
        public long totalEventSpeedTime;
        public int eventSpeedVerbose;
    }
}
