package me.injin.legacyrefactoring;

/**
 * 예제 코드의 문제점
 * 길다
 * 들여쓰기가 많다 (indentation based complexity)
 *      - cyclomatic complexity: 독립적인 실행 흐름의 갯수 ~= 테스트의 갯수
 *      - indentation based complexity: 가독성(인지적)
 * 중첩된 조건문이 많고
 * 테스트하기 매우 어려운 코드임
 */

/**
 * 복잡한 레거시 코드에 테스트를 추가하는 3가지 기법
 * Characterization Test
 *  - 수동으로 테스트를 추가하는 기법(from Working Effectively with Legacy Code)
 * Approval Test
 *  - Characterization Test 를 다양한 인자의 조합으로 작성을 돕는 라이브러리
 *      - 단위 테스트에서 검증(assert)은 어려울 수 있음
 *      - https://approvaltests.com/
 *      - approval test 는 결과의 스냅샷을 만들고,  변경되지 않았음을 검증하여 단위 테스트에서 검증을 단순화함
 *          - build.gradle =>     testImplementation("com.approvaltests:approvaltests:18.5.0") 추가
 * Mutation Testing
 *  - 100% 테스트 커버리지를 갖는 코드도 완전히 버그가 없는 것을 보장하지 못함.
 *  프로덕션 코드의 일부를 수정(mutate) 하여 테스트가 실패하는지 확인하여, 프로덕션 코드가 변경되었을때 테스트가 실패하도록 테스트를 추가해 나가는 기법
 *  PIT 를 이용하면 코드의 변환(mutation)을 자동으로 생성 가능
 *
 *  - 정의
 *      - 고의로 버그를 유발(프로덕션 코드를 약간 수정해서)하고 테스트가 버그를 잡아내는지(실패하는지)확인하여 테스트의 완정성을 검증하는 기법
 *          - 이렇게 프로덕션 코드를 약간 변경하는 테스트를 돌연변이 테스트(mutation test)아럭ㅎ 험
 *          - 100% 커버리지를 갖는 경우도 테스트가 실행하지 않고 성공하는 경우가 있음
 *          - 코드를 실행하는 것과 코드를 테스트하는 것은 동일한 것이 아니여서
 *       - PIT 을 이용해서 수행
 *          - PIT 은 런타임에 프로덕션 코드에 코드를 삽입하여 돌연변이 테스트를 발생시킴
 *          - 살아남은 돌연변이 테스트가 있다는 것은 100% 커버리지를 갖는 테스트가 있다고 하더라도, 결함(defect)을 완전히 방지하지 못했다는 것을 의미
 *
 *          ---
 *          => 아..pitest 가 안되네..
 */
public class GildedRose {
    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        for (int i = 0; i < items.length; i++) {
            if (!items[i].name.equals("Aged Brie")
                    && !items[i].name.equals("Backstage passes to a TAFKAL80ETC concert")) {
                if (items[i].quality > 0) {
                    if (!items[i].name.equals("Sulfuras, Hand of Ragnaros")) {
                        items[i].quality = items[i].quality - 1;
                    }
                }
            } else {
                if (items[i].quality < 50) {
                    items[i].quality = items[i].quality + 1;

                    if (items[i].name.equals("Backstage passes to a TAFKAL80ETC concert")) {
                        if (items[i].sellIn < 11) {
                            if (items[i].quality < 50) {
                                items[i].quality = items[i].quality + 1;
                            }
                        }

                        if (items[i].sellIn < 6) {
                            if (items[i].quality < 50) {
                                items[i].quality = items[i].quality + 1;
                            }
                        }
                    }
                }
            }

            if (!items[i].name.equals("Sulfuras, Hand of Ragnaros")) {
                items[i].sellIn = items[i].sellIn - 1;
            }

            if (items[i].sellIn < 0) {
                if (!items[i].name.equals("Aged Brie")) {
                    if (!items[i].name.equals("Backstage passes to a TAFKAL80ETC concert")) {
                        if (items[i].quality > 0) {
                            if (!items[i].name.equals("Sulfuras, Hand of Ragnaros")) {
                                items[i].quality = items[i].quality - 1;
                            }
                        }
                    } else {
                        items[i].quality = items[i].quality - items[i].quality;
                    }
                } else {
                    if (items[i].quality < 50) {
                        items[i].quality = items[i].quality + 1;
                    }
                }
            }
        }
    }
}
