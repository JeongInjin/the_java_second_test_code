package me.injin.legacyrefactoring;

public class Aged extends Item {
    public Aged(int sellIn, int quality) {
        super("Aged Brie", sellIn, quality);
    }

    @Override
    void updateQuality() {
        if (quality < 50) {
            quality = quality + 1;
        }

        sellIn = sellIn - 1;

        if (sellIn < 0 && quality < 50) {
            quality = quality + 1;
        }

    }
}
