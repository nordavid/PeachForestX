package de.davideinenkel.pfshop.utility;

import org.bukkit.entity.Player;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Rewards {

    final static int rewardCooldownInH = 3;

    public static String getRewardString(Player player) {
        boolean rewardReady = getIsRewardReady(player);

        if (rewardReady) {
            return "Reward bereit zum abholen!";
        }
        else {
            long remainingSeconds = getSecondsUntilReward(player);
            long hours = remainingSeconds % 86400 / 3600;
            long minutes = remainingSeconds % 3600 / 60;

            return "Nächster Reward in " + hours + "h " + minutes + "m " + "verfügbar";
        }
    }

    public static boolean getIsRewardReady(Player player) {
        PlayerConfig.load(player);
        LocalDateTime current = LocalDateTime.now();
        String lastReward = PlayerConfig.get().getString("lastReward");

        if (lastReward == null) {
            player.sendMessage("lr null");
            return true;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime lastRewardDate = LocalDateTime.parse(lastReward, formatter);

        Long hoursDiff = ChronoUnit.HOURS.between(lastRewardDate, current);

        if (getSecondsUntilReward(player) <= 0) {
            player.sendMessage("rready " + hoursDiff.intValue());
            return true;
        }
        else {
            return false;
        }
    }

    public static Integer getHoursSinceReward(Player player) {
        PlayerConfig.load(player);
        LocalDateTime current = LocalDateTime.now();
        String lastReward = PlayerConfig.get().getString("lastReward");

        if (lastReward == null) {
            return 0;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime lastRewardDate = LocalDateTime.parse(lastReward, formatter);

        Long hoursDiff = ChronoUnit.HOURS.between(lastRewardDate, current);
        return  hoursDiff.intValue();
    }

    public static long getSecondsUntilReward(Player player) {
        PlayerConfig.load(player);
        LocalDateTime current = LocalDateTime.now();
        String lastReward = PlayerConfig.get().getString("lastReward");

        player.sendMessage(lastReward);

        if (lastReward == null) {
            return 0;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime lastRewardDate = LocalDateTime.parse(lastReward, formatter);

        LocalDateTime nextRewardDate = lastRewardDate.plusHours(rewardCooldownInH);

        Duration remaining = Duration.between(current, nextRewardDate);

        player.sendMessage("s " + remaining.getSeconds());

        return remaining.getSeconds();
    }

}
