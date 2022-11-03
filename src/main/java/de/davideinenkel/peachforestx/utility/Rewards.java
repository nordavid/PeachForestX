package de.davideinenkel.peachforestx.utility;

import de.davideinenkel.peachforestx.PeachForestX;
import org.bukkit.entity.Player;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Rewards {

    //final static int rewardCooldownInH = 3;

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
        if (getSecondsUntilReward(player) <= 0) {
            return true;
        }
        else {
            return false;
        }
    }

    public static long getSecondsUntilReward(Player player) {
        PlayerConfig.load(player);
        String lastReward = PlayerConfig.get().getString("lastReward");

        if (lastReward == null) {
            return 0;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime lastRewardDate = LocalDateTime.parse(lastReward, formatter);

        LocalDateTime current = LocalDateTime.now();
        LocalDateTime nextRewardDate = lastRewardDate.plusHours(PeachForestX.getMainConfig().getInt("RewardCooldownHours"));
        Duration remaining = Duration.between(current, nextRewardDate);

        return remaining.getSeconds();
    }

    public static boolean claimReward(Player player) {
        PlayerConfig.load(player);
        PlayerConfig.get().set("balance", PlayerConfig.get().getInt("balance") + PeachForestX.getMainConfig().getInt("Reward"));
        PlayerConfig.get().set("lastReward", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
        PlayerConfig.save();
        return true;
    }

}
