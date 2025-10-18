package lab6;

import java.util.*;

public class Application {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        RBtree<Integer> tree = new RBtree<>();
        System.out.println("=== ЧЕРВОНО-ЧОРНЕ ДЕРЕВО ===");
        System.out.println("1 - Випадкові числа");
        System.out.println("2 - Введення з клавіатури");
        System.out.println("3 - Впорядкований масив");
        System.out.print("Ваш вибір: ");
        int choice = sc.nextInt();

        int[] numbers;
        if (choice == 1) {
            numbers = generateRandomArray(8);
            System.out.println("Згенерований масив: " + Arrays.toString(numbers));
        } else if (choice == 2) {
            System.out.print("Введіть кількість елементів: ");
            int n = sc.nextInt();
            numbers = new int[n];
            for (int i = 0; i < n; i++) {
                System.out.print("Елемент " + (i + 1) + ": ");
                numbers[i] = sc.nextInt();
            }
        } else {
            numbers = generateRandomArray(8);
            Arrays.sort(numbers);
            System.out.println("Впорядкований масив: " + Arrays.toString(numbers));
        }
        for (int num : numbers) {
            try {
                tree.insert(num);
            } catch (IllegalArgumentException e) {
                System.out.println("Дубль пропущено: " + num);
            }
        }
        System.out.println("\n=== Структура дерева ===");
        printTree(tree);

        System.out.print("\nПошук елемента (введіть число або -1 для пропуску): ");
        int searchVal = sc.nextInt();
        if (searchVal != -1) {
            var node = tree.search(searchVal);
            System.out.println(node != null ? "Елемент знайдено" : "Елемент не знайдено");
        }
        System.out.print("\nВидалити елемент (введіть число або -1 для пропуску): ");
        int delVal = sc.nextInt();
        if (delVal != -1) {
            tree.deleteNode(delVal);
            System.out.println("Після видалення:");
            printTree(tree);
        }

        sc.close();

    }
    private static int[] generateRandomArray(int n) {
        Random rand = new Random();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = rand.nextInt(100);
        }
        return arr;
    }
    private static void printTree(RBtree<Integer> tree) {
        System.out.println("In-order (симетричний обхід):");
        inorderPrint(tree);
    }

    private static void inorderPrint(RBtree<Integer> tree) {
        inorderRecursive(tree, tree.getRoot(), 0);
    }

    private static void inorderRecursive(RBtree<Integer> tree, Object nodeObj, int depth) {
        if (nodeObj == null) return;
        try {
            var node = (Object) nodeObj.getClass().cast(nodeObj);
            var valueField = nodeObj.getClass().getDeclaredField("value");
            var leftField = nodeObj.getClass().getDeclaredField("left");
            var rightField = nodeObj.getClass().getDeclaredField("right");
            var colorField = nodeObj.getClass().getDeclaredField("color");
            valueField.setAccessible(true);
            leftField.setAccessible(true);
            rightField.setAccessible(true);
            colorField.setAccessible(true);

            Object left = leftField.get(nodeObj);
            Object right = rightField.get(nodeObj);
            Object value = valueField.get(nodeObj);
            Object color = colorField.get(nodeObj);

            if (left != null) inorderRecursive(tree, left, depth + 1);
            System.out.println("  ".repeat(depth) + value + " (" + color + ")");
            if (right != null) inorderRecursive(tree, right, depth + 1);
        } catch (Exception ignored) {}
    }
}
