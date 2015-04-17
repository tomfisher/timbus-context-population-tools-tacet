package edu.teco.tacet.meta.nongen;

public enum Unit {
    MILLISECONDS(1),
    SECONDS(1000),
    MINUTES(1000 * 60),
    HOURS(1000 * 60 * 60),
    DAYS(1000 * 60 * 60 * 24);

    private int mulitplier;

    Unit(int multiplier) {
        this.mulitplier = multiplier;
    }

    public static String[] getPossibleValues() {
        String[] units = new String[Unit.values().length];
        for (int i = 0; i < units.length; i++) {
            units[i] = Unit.values()[i].getUnitNameLowerCase();
        }
        return units;
    }

    public int getMultiplier() {
        return mulitplier;
    }

    public String getUnitNameLowerCase() {
        return toString().toLowerCase();
    }
}
