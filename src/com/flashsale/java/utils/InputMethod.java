package utils;

import java.util.Scanner;

public class InputMethod {
    private static final Scanner sc = new Scanner(System.in);

    public static String getInputString(String message) {
        System.out.print(message);
        while (true) {
            String input = sc.nextLine().trim();
            if (!input.isEmpty()) return input;
            System.err.print("Không được để trống.Nhập lại: ");
        }
    }

    public static int getInputInt(String message) {
        System.out.print(message);
        while (true) {
            try {
                int n = Integer.parseInt(sc.nextLine());
                if (n >= 0) return n;
                System.err.print("Vui lòng nhập số dương.Nhập lại: ");
            } catch (NumberFormatException e) {
                System.err.print("Nhập sai định dạng.Nhập lại: ");
            }
        }
    }
}