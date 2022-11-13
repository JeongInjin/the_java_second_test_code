package me.injin.refactoring2.splitPhase1;

/**
 * https://refactoring.com/catalog/splitPhase.html
 * 테스트하기 힘든 코드와 테스트를 해야만 하는 코드가 섞여있을때, 리팩토링 기법
 * =====================================================================
 * const orderData = orderString.split(/\s+/);
 * const productPrice = priceList[orderData[0].split("-")[1]];
 * const orderPrice = parseInt(orderData[1]) * productPrice;
 * ===================================================================== > > > > > > > > > > >
 * const orderRecord = parseOrder(order);
 * const orderPrice = price(orderRecord, priceList);
 *
 * function parseOrder(aString) {
 *   const values =  aString.split(/\s+/);
 *   return ({
 *     productID: values[0].split("-")[1],
 *     quantity: parseInt(values[1]),
 *   });
 * }
 * function price(order, priceList) {
 *   return order.quantity * priceList[order.productID];
 * }
 * =====================================================================
 */

/**
 * 1.2번째 단계를 먼저 추출하라.
 * 2.중간 구조체를 만들어라. ex: PricingData
 * 3.중간에 전달하는 argument 들을 중간 구조체에 넘긴다.
 *      - 첫번째 단계에서 생성되는것, 사용되는 객체들만 넘긴다. ex:basePrice, discount, quantity
 *          - 첫번째 단계에서 shippingMethod 는 사용되지 않고 있다.
 * 4.첫번째 단계가 정리되었다면 메소드로 추출한다.
 * 5.
 */
record Product(double basePrice, double discountThreshold, double discountRate){}

record ShippingMethod(double discountThreshold, double discountFee, Double feePerCase){}

record PricingData(double basePrice, double discount, int quantity) {}

public class Order {
    double priceOrder(Product product, int quantity, ShippingMethod shippingMethod) {
        PricingData pricingData = calculatorPricingData(product, quantity);
        return applyShippingCost(shippingMethod, pricingData);
    }

    private PricingData calculatorPricingData(Product product, int quantity) {
        double basePrice = product.basePrice() + quantity;
        double discount = Math.max(quantity - product.discountThreshold(), 0) * product.basePrice() * product.discountRate();
        return new PricingData(basePrice, discount, quantity);
    }

    private double applyShippingCost(ShippingMethod shippingMethod, PricingData pricingData) {
        double shippingPerCase = (pricingData.basePrice() > shippingMethod.discountThreshold())? shippingMethod.discountFee() : shippingMethod.feePerCase();
        double shippingCost = pricingData.quantity() * shippingPerCase;
        return pricingData.basePrice() - pricingData.discount() + shippingCost;
    }
}
