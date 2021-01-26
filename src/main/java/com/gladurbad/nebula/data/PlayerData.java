package com.gladurbad.nebula.data;

import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.ArrayDeque;
import java.util.UUID;

@Getter
public class PlayerData {

    private final Player bukkitPlayer;
    private final UUID uuid;
    private final DataStorage dataStorage = new DataStorage();
    private final long joinTime;

    public PlayerData(final Player bukkitPlayer) {
        this.bukkitPlayer = bukkitPlayer;
        this.joinTime = System.currentTimeMillis();
        this.uuid = bukkitPlayer.getUniqueId();
        dataStorage.flyFakeGroundLastOnGroundLocation = bukkitPlayer.isOnGround() ? bukkitPlayer.getLocation() : null;
    }

    public class DataStorage {
        public int reachVerbose;
        public int aim4verbose;
        public ArrayDeque<Float> aim1samples = new ArrayDeque<>();
        public long lastDamageTakenTime;
        public int speedGroundTicks, speedAirTicks, speedSinceIceTicks, speedSinceUnderBlockTicks, speedSinceSlimeTicks;
        public int speedYportAirTicks, speedLastAirTicks;
        public double lastModulo;
        public long baitTime;
        public int baithittimes;
        public double lastMovmeent;
        public long lastBaitHitTime;
        public Entity target;
        public Entity bait;
        public int aim6upticks, aim6downticks, lastUpticks, lastdownticks;
        public int keepsprintSlaveSales;
        public double speedYportVerbose;
        public float lastDeltaPitchidfgaskjhlfd;
        public long lastTeleport;
        public int speedNiggerTPticks;
        public float aimbot5lastniggerpitch;
        public int aim5VERBOShkdagjkahsdgfafad;
        public float aimbot5lastyaw;
        public float aimbot5yawdiffdiff;
        public float aimbot5pitchdiffdiffasjdhasdkh;
        public long aimbot5lastCUM;
        public Location speedHopDistLastGroundLoc;
        public int aim2TotalMoves;
        public int flyAccelVerbose;
        public float aim1lastpitch;
        public int aim3Verbose;
        public double flyAccelLastDeltaY;
        public int aim2Verbose;
        public long lastAim2Move;
        public int aim2PosLooks, aim2Looks, aim2Moves;
        public long lastAimCheck2RotateTime;
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
