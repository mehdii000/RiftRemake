package me.mehdidev.rift.stats;

import me.mehdidev.rift.handlers.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class RiftStats {

    private HashMap<UUID, List<DoubleStatValue>> playerUUIDStatsMap = new HashMap<>();

    public List<DoubleStatValue> getStats(User user) {
        if (playerUUIDStatsMap.containsKey(user.getUuid())) {
            return playerUUIDStatsMap.get(user.getUuid());
        }
        user.debug("No stats for this player were found???");
        return null;
    }

    public void updateStats(User user) {
        List<DoubleStatValue> tempStatsList = new ArrayList<>();

        tempStatsList.add(new DoubleStatValue(RiftStat.RIFT_TIME));
        tempStatsList.add(new DoubleStatValue(RiftStat.SPEED));
        tempStatsList.add(new DoubleStatValue(RiftStat.MANA));
        tempStatsList.add(new DoubleStatValue(RiftStat.MANA_REGEN));
        tempStatsList.add(new DoubleStatValue(RiftStat.RIFT_DAMAGE));
        tempStatsList.add(new DoubleStatValue(RiftStat.HEARTS));

        List<StatModifier> modifiers = user.getActiveStatModifiers();
        modifiers.sort((mod1, mod2) -> {
            int mod1Priority = getModificationPriority(mod1.getModificationType());
            int mod2Priority = getModificationPriority(mod2.getModificationType());
            return Integer.compare(mod1Priority, mod2Priority);
        });

        for (StatModifier modifier : modifiers) {
            switch (modifier.getModificationType()) {
                case ADD:
                    tempStatsList.get(modifier.getModifiedStat().ordinal()).addToValue(modifier.getValue());
                    break;
                case MULTIPLY:
                    tempStatsList.get(modifier.getModifiedStat().ordinal()).multiplyValueBy(modifier.getValue());
                    break;
                case OVERRIDE:
                    tempStatsList.get(modifier.getModifiedStat().ordinal()).overrideValue(modifier.getValue());
                    break;
                default:
            }
        }

        playerUUIDStatsMap.put(user.getUuid(), tempStatsList);
    }

    // Helper method to assign a priority to each modification type
    private int getModificationPriority(StatModifier.ModificationType type) {
        switch (type) {
            case ADD:
                return 1; // ADD should be first
            case MULTIPLY:
                return 2; // MULTIPLY should be second
            case OVERRIDE:
                return 3; // OVERRIDE should be last
            default:
                return Integer.MAX_VALUE; // If unknown type, place it at the end
        }
    }

    public DoubleStatValue getStat(User user, RiftStat statType) {
        switch (statType) {
            case RIFT_TIME:
                return getStats(user).get(0);
            case SPEED:
                return getStats(user).get(1);
            case MANA:
                return getStats(user).get(2);
            case MANA_REGEN:
                return getStats(user).get(3);
            case RIFT_DAMAGE:
                return getStats(user).get(4);
            case HEARTS:
                return getStats(user).get(5);
            default:
                user.debug("This stat type wasnt found. Weird?");
                return null;
        }
    }

    public void detachUserStats(User user) {
        playerUUIDStatsMap.remove(user.getUuid());
    }
}
