package me.injin.legacyrefactoring;

import org.approvaltests.Approvals;
import org.approvaltests.combinations.CombinationApprovals;
import org.junit.jupiter.api.Test;

import java.util.Arrays;


class GildedRoseTest {

    @Test
    void updateQuality() {
        String[] name = {"foo", "Aged Brie", "Backstage passes to a TAFKAL80ETC concert", "Sulfuras, Hand of Ragnaros"};
        Integer[] sellIn = {-1, 0, 11};
        Integer[] quality = {0, 1, 49, 50};

        //여러 조합으로 테스트를 해야할 시
        CombinationApprovals.verifyAllCombinations(this::doUpdateQuality, name, sellIn, quality);

    }

    private String doUpdateQuality(String name, int sellIn, int quality) {
        GildedRose sut = new GildedRose(new Item[]{new Item(name, sellIn, quality)});
        sut.updateQuality();
        return Arrays.asList(sut.items).toString();
    }
}