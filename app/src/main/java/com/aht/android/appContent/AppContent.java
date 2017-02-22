package com.aht.android.appContent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing questions for user interfaces
 */
public class AppContent {

    /**
     * An array of sample items.
     */
    public static final List<Item> ITEMS = new ArrayList<>();

    /**
     * A map of sample items, by ID.
     */
    public static final Map<String, Item> ITEM_MAP = new HashMap<>();

    private static final int COUNT = 25;

    static {
        // Add some items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createDummyItem(i));
        }
    }

    private static void addItem(Item item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static Item createDummyItem(int position) {
        return new Item(String.valueOf(position), "Question " + position, makeDetails(position));
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Question: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore answer information here.");
        }
        return builder.toString();
    }

    /**
     * A item representing a piece of question.
     */
    public static class Item {
        public final String id;
        public final String question;
        public String answer;

        public Item(String id, String question, String answer) {
            this.id = id;
            this.question = question;
            this.answer = answer;
        }

        public void setAnswer(String answer) {
            this.answer = answer;
        }

        @Override
        public String toString() {
            return question;
        }
    }
}
