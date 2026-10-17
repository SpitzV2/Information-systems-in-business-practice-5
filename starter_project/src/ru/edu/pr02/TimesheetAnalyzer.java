package ru.edu.pr02;
/**
 * Вариант 2
 * Наш интервал часов в день от 0 до 15
 */
/
public class TimesheetAnalyzer {
    public static final int EXPECTED_DAYS_COUNT = 5;
    public static final int MIN_HOURS_PER_DAY = 0;
    public static final int MAX_HOURS_PER_DAY = 15;
    public static final int DAILY_OVERTIME_THRESHOLD = 8;
    public static final int WEEKLY_STANDARD_HOURS = 40;
    public static int[] parseHours(String line) {
        if (line == null || line.trim().isEmpty()) {
            throw new IllegalArgumentException("Ошибка: входная строка пуста или null.");
        }

        String[] tokens = line.split(";", -1);
        if (tokens.length != EXPECTED_DAYS_COUNT) {
            throw new IllegalArgumentException(String.format(
                    "Ошибка структуры данных: ожидалось ровно %d значений, получено %d.",
                    EXPECTED_DAYS_COUNT, tokens.length
            ));
        }

        int[] hours = new int[EXPECTED_DAYS_COUNT];
        for (int i = 0; i < tokens.length; i++) {
            String token = tokens[i].trim();
            int position = i + 1; // Человекочитаемая позиция дня (1..5)

            if (token.isEmpty()) {
                throw new IllegalArgumentException(String.format(
                        "Ошибка на позиции %d: значение отсутствует (пустой элемент).", position
                ));
            }

            int val;
            try {
                val = Integer.parseInt(token);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException(String.format(
                        "Ошибка на позиции %d: '%s' не является целым числом.", position, token
                ));
            }

            if (val < MIN_HOURS_PER_DAY || val > MAX_HOURS_PER_DAY) {
                throw new IllegalArgumentException(String.format(
                        "Ошибка на позиции %d: значение %d выходит за допустимый диапазон [%d..%d].",
                        position, val, MIN_HOURS_PER_DAY, MAX_HOURS_PER_DAY
                ));
            }

            hours[i] = val;
        }

        return hours;
    }
    public static int total(int[] hours) {
        validateArray(hours);
        int sum = 0;
        for (int i = 0; i < hours.length; i++) {
            sum += hours[i];
        }
        return sum;
    }
    public static double average(int[] hours) {
        validateArray(hours);
        return (double) total(hours) / hours.length;
    }
    public static int overtimeDays(int[] hours) {
        validateArray(hours);
        int count = 0;
        for (int i = 0; i < hours.length; i++) {
            if (hours[i] > DAILY_OVERTIME_THRESHOLD) {
                count++;
            }
        }
        return count;
    }
    public static int weeklyOvertime(int[] hours) {
        validateArray(hours);
        int sum = total(hours);
        return Math.max(0, sum - WEEKLY_STANDARD_HOURS);
    }
    private static void validateArray(int[] hours) {
        if (hours == null || hours.length != EXPECTED_DAYS_COUNT) {
            throw new IllegalArgumentException(
                    "Массив часов должен быть не null и содержать ровно " + EXPECTED_DAYS_COUNT + " элементов."
            );
        }
    }
}
