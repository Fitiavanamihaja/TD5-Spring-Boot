package org.example.td5.TDingredient;

import java.time.Instant;

public class Main {
    public static void main(String[] args) {

        DataRetriever dataRetriever = new DataRetriever();

        Order order = new Order();
        order.setReference("CMD-001");
        order.setCreationDatetime(Instant.now());
        order.setType(OrderTypeEnum.EAT_IN);
        order.setStatus(OrderStatusEnum.CREATED);

        Order saved = dataRetriever.saveOrder(order);
        System.out.println(saved);

        saved.setStatus(OrderStatusEnum.READY);
        dataRetriever.saveOrder(saved);
        System.out.println(saved);

        saved.setStatus(OrderStatusEnum.DELIVERED);
        dataRetriever.saveOrder(saved);
        System.out.println(saved);

        saved.setType(OrderTypeEnum.TAKE_AWAY);
        dataRetriever.saveOrder(saved);
    }
}
public class Main {

    public static void main(String[] args) {

        Dish d1 = new Dish("Salade César", 8.50, DishTypeEnum.ENTREE);
        Dish d2 = new Dish("Steak Frites", 15.90, DishTypeEnum.PLAT_PRINCIPAL);
        Dish d3 = new Dish("Tiramisu", 6.50, DishTypeEnum.DESSERT);
        Dish d4 = new Dish("Coca-Cola", 3.50, DishTypeEnum.BOISSON);

        System.out.println(d1);
        System.out.println(d2);
        System.out.println(d3);
        System.out.println(d4);
    }
}