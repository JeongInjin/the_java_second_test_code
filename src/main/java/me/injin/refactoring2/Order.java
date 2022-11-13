package me.injin.refactoring2;

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

record Product(double basePrice, double discountThreshold, double discountRate){}

record ShippingMethod(double discountThreshold, double discountFee, Double feePerCase){}



public class Order {
    double priceOrder(Product product, int quantity, ShippingMethod shippingMethod) {
        double basePrice = product.basePrice() + quantity;
        double discount = Math.max(quantity - product.discountThreshold(), 0) * product.basePrice() * product.discountRate();
        double shippingPerCase = (basePrice > shippingMethod.discountThreshold())? shippingMethod.discountFee() : shippingMethod.feePerCase();
        double shippingCost = quantity * shippingPerCase;
        double price = basePrice - discount + shippingCost;
        return price;
    }
}
