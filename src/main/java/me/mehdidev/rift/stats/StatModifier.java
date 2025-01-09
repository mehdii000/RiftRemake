package me.mehdidev.rift.stats;

public class StatModifier {

    private RiftStat modifiedStat;
    private ModificationType modificationType;
    private double value;
    private String description;
    private ModificationReason reason;

    public StatModifier(RiftStat modifiedStat, ModificationType modificationType, double value, ModificationReason reason, String description) {
        this.modifiedStat = modifiedStat;
        this.modificationType = modificationType;
        this.value = value;
        this.description = description;
        this.reason = reason;
    }

    public StatModifier(RiftStat modifiedStat, ModificationType modificationType, double value, ModificationReason reason) {
        this(modifiedStat, modificationType, value, reason, "No explanation was given for this!");
    }
    public StatModifier(RiftStat modifiedStat, ModificationType modificationType, double value) {
        this(modifiedStat, modificationType, value, ModificationReason.DEV);
    }

    public ModificationType getModificationType() {
        return modificationType;
    }

    public RiftStat getModifiedStat() {
        return modifiedStat;
    }

    public double getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public ModificationReason getReason() {
        return reason;
    }

    public enum ModificationType {
        ADD,
        MULTIPLY,
        OVERRIDE
    }

    public enum ModificationReason {
        DEV,
        HAND_ITEM,
        HELMET,
        CHESTPLATE,
        LEGGINGS,
        BOOTS,
        NECKLACE,
        CLOAK,
        BELT,
        BRACELET,
        ACCESSORY
    }

}
