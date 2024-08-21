import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static AtomicInteger word3 = new AtomicInteger();
    public static AtomicInteger word4 = new AtomicInteger();
    public static AtomicInteger word5 = new AtomicInteger();

    public static void main(String[] args) throws InterruptedException {

        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }
        // Проверка полиндрома
        Thread thread1 = new Thread(() -> {
            for (String text : texts) {
                if (isPalindrome(text) && text.length() == 3) {
                    word3.incrementAndGet();
                }
                if (isPalindrome(text) && text.length() == 4) {
                    word4.incrementAndGet();
                }
                if (isPalindrome(text) && text.length() == 5) {
                    word5.incrementAndGet();
                }
            }
        });

        // Проверка одной и той же буквы
        Thread thread2 = new Thread(() -> {
            for (String text : texts) {
                if (isSameLetter(text) && text.length() == 3) {
                    word3.incrementAndGet();
                }
                if (isSameLetter(text) && text.length() == 4) {
                    word4.incrementAndGet();
                }
                if (isSameLetter(text) && text.length() == 5) {
                    word5.incrementAndGet();
                }
            }
        });

        // Проверка сортировки
        Thread thread3 = new Thread(() -> {
            for (String text : texts) {
                if (isSorted(text) && text.length() == 3) {
                    word3.incrementAndGet();
                }
                if (isSorted(text) && text.length() == 4) {
                    word4.incrementAndGet();
                }
                if (isSorted(text) && text.length() == 5) {
                    word5.incrementAndGet();
                }
            }
        });
        thread1.start();
        thread2.start();
        thread3.start();

        thread1.join();
        thread2.join();
        thread3.join();

        System.out.println("Красивых слов с длиной 3: " + word3.get());
        System.out.println("Красивых слов с длиной 4: " + word4.get());
        System.out.println("Красивых слов с длиной 5: " + word5.get());
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static boolean isPalindrome(String text) {
        int left = 0;
        int right = text.length() - 1;
        while (left < right) {
            if (text.charAt(left) != text.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }

    public static boolean isSameLetter(String text) {
        char firstLetter = text.charAt(0);
        for (int i = 1; i < text.length(); i++) {
            if (text.charAt(i) != firstLetter) {
                return false;
            }
        }
        return true;
    }

    public static boolean isSorted(String text) {
        for (int i = 1; i < text.length(); i++) {
            if (text.charAt(i) < text.charAt(i - 1)) {
                return false;
            }
        }
        return true;
    }
}

