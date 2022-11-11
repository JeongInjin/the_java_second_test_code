package me.injin.legacyrefactoring;

import org.approvaltests.Approvals;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class GildedRoseTest {

    @Test
    void updateQuality() {
        GildedRose sut = new GildedRose(new Item[]{
                new Item("foo", 0, 0),
        });

        sut.updateQuality();

        assertThat(sut.items[0].toString()).isEqualTo("Item{name='foo', sellIn=-1, quality=0}");
    }
}