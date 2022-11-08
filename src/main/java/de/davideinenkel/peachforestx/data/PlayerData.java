package de.davideinenkel.peachforestx.data;

import de.davideinenkel.peachforestx.utility.DateAndTime;

import java.time.LocalDateTime;

public class PlayerData {
    public Integer balance;
    public Integer level;
    public Integer exp;
    public LocalDateTime lastReward;
    public LocalDateTime lastTimeOnline;

    public PlayerData(Integer balance, Integer level, Integer exp, String lastReward, String lastTimeOnline) {
        this.balance = balance;
        this.level = level;
        this.exp = exp;
        this.lastReward = DateAndTime.SringToDateTime(lastReward);
        this.lastTimeOnline = DateAndTime.SringToDateTime(lastTimeOnline);
    }
}
