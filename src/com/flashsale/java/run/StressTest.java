package com.flashsale.java.run;

import com.flashsale.java.business.service.CardItem;
import com.flashsale.java.utils.InputMethod;

import java.util.List;
import java.util.concurrent.ExecutorService;

import static com.flashsale.java.run.MenuPrinter.orderService;
import static java.util.concurrent.Executors.newFixedThreadPool;

public class StressTest {
    public static void handleStressTest() {
        System.out.println("\n--- BẮT ĐẦU CHẠY STRESS TEST (50 THREADS) ---");
        int productId = InputMethod.getInputInt("Nhập ID sản phẩm : ");

        ExecutorService executor = newFixedThreadPool(50);

        for (int i = 1; i <= 50; i++) {
            int threadId = i;
            executor.execute(() -> {
                int userId = (threadId % 5) + 1;

                List<CardItem> items = new java.util.ArrayList<>();
                items.add(new CardItem(productId, 1));

                try {
                    orderService.placeOrder(userId, items);
                    System.out.println("[Thread " + threadId + "] Đặt hàng THÀNH CÔNG");
                } catch (Exception e) {
                    System.out.println("[Thread " + threadId + "] THẤT BẠI: " + e.getMessage());
                }
            });
        }

        executor.shutdown();
        while (!executor.isTerminated()) {
        }

        System.out.println("\n--- STRESS TEST KẾT THÚC ---");
    }
}
