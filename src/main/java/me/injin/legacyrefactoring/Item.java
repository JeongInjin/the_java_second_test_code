package me.injin.legacyrefactoring;

public class Item {
    public String name;

    public int sellIn;

    public int quality;

    protected Item(String name, int sellIn, int quality) {
        this.name = name;
        this.sellIn = sellIn;
        this.quality = quality;
    }

    public static Item of(String name, int sellIn, int quality) {
        switch (name) {
            case "Sulfuras, Hand of Ragnaros":
                return new Sulfuras(sellIn, quality);
            case "Backstage passes to a TAFKAL80ETC concert":
                return new Backstage(sellIn, quality);
            case "Aged Brie":
                return new Aged(sellIn, quality);
            default:
                return new Item(name, sellIn, quality);
        }

//        return new Item(name, sellIn, quality);
    }

//    @Override
//    public String toString() {
//        return this.name + ", " + this.sellIn + ", " + this.quality;
//    }

    void updateQuality() {
        if (quality > 0) {
            quality = quality - 1;
        }

        sellIn = sellIn - 1;

        if (sellIn < 0 && quality > 0) {
            quality = quality - 1;
        }
    }

    @Override
    public String toString() {
        return "Item{" +
                "name='" + name + '\'' +
                ", sellIn=" + sellIn +
                ", quality=" + quality +
                '}';
    }
}
