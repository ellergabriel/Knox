package com.example.knox.ui.placeholder;

import android.content.Context;

import com.example.knox.systemComponents.Credentials;
import com.example.knox.systemComponents.Database;
import com.example.knox.systemComponents.PasswordDAO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class PlaceholderContent {

    /**
     * An array of sample (placeholder) items.
     */
    public static final List<Credentials> ITEMS = new ArrayList<Credentials>();

    /**
     * A map of sample (placeholder) items, by ID.
     */
    public static final Map<String, PlaceholderItem> ITEM_MAP = new HashMap<String, PlaceholderItem>();

    private static final int COUNT = 25;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createPlaceholderItem(i));
        }


//        Context context;
//
//        Database db = Database.getInstance(getApplicationContext());
//
//        PasswordDAO dao = db.passDao();
//        dao.insertAll(new Credentials("eller010", "shazbot", "google.com"));
//        Credentials tester = dao.getFullCred("google.com");
//
//        List<Credentials> test = dao.getAllCreds();

//        System.out.println("test");

//        logButton.setOnClickListener(view ->{
//            if (user.getText().toString().equals("eller010") &&
//                    pass.getText().toString().equals("password")){
//                //System.exit(0);
//
//            }
//        });
    }

    private static void addItem(Credentials item) {
        ITEMS.add(item);
//        ITEM_MAP.put(item.id, item);
    }

    private static Credentials createPlaceholderItem(int position) {
        return new Credentials("Item ", "hi", "createplaceholderitem");
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

    /**
     * A placeholder item representing a piece of content.
     */
    public static class PlaceholderItem {
//        public final String id;
        public final String content;
        public final String details;

        public PlaceholderItem(String content, String details) {
//            this.id = id;
            this.content = content;
            this.details = details;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}