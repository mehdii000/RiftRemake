package me.mehdidev.rift.stats;

public class DoubleStatValue {

    private RiftStat type;
    private double value;

    public DoubleStatValue(RiftStat type) {
        this.type = type;
        this.value = type.getBaseValue();
    }

    public RiftStat getType() {
        return type;
    }

    public double getBaseValue() {
        return type.getBaseValue();
    }

    public void addToValue(double x) {
        setValue(getValue() + x);
    }

    public void multiplyValueBy(double x) {
        setValue(getValue() * x);
    }

    public void overrideValue(double x) {
        setValue(x);
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

}
