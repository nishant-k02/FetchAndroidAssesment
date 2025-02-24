package com.example.fetchdataapp;

import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.Map;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ItemAdapter adapter;
    private List<Item> itemList = new ArrayList<>();
    private final String DATA_URL = "https://fetch-hiring.s3.amazonaws.com/hiring.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ItemAdapter(itemList);
        recyclerView.setAdapter(adapter);

        fetchData(); // Fetch and display data
    }

    private void fetchData() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(DATA_URL).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
//                Log.e("MainActivity", "Failed to fetch data: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        JSONArray jsonArray = new JSONArray(response.body().string());
                        processJsonData(jsonArray);
                    } catch (Exception e) {
                        Log.e("MainActivity", "Error parsing JSON: " + e.getMessage());
                    }
                }
            }
        });
    }

    private void processJsonData(JSONArray jsonArray) {
        try {
            TreeMap<Integer, List<Item>> groupedItems = new TreeMap<>();
            int skippedItems = 0;  // Track skipped items

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                int listId = obj.getInt("listId");
                int id = obj.getInt("id");

                // Retrieve name value
                String name = obj.optString("name", "").trim();
                Log.d("MainActivity", "Item ID: " + id + " - Name: " + name);

                // Check if name is valid (not null and not empty)
                if (!name.isEmpty() && !"null".equals(name)) {
                    Item item = new Item(id, listId, name);
                    groupedItems.computeIfAbsent(listId, k -> new ArrayList<>()).add(item);
                } else {
                    skippedItems++;
                    Log.d("MainActivity", "Skipping item ID: " + id + " due to invalid name.");
                }
            }

            // Clear existing itemList
            itemList.clear();

            // Add sorted items to itemList
            for (Map.Entry<Integer, List<Item>> entry : groupedItems.entrySet()) {
                List<Item> sortedItems = entry.getValue();
                sortedItems.sort(Comparator.comparing(Item::getName));
                itemList.addAll(sortedItems);
            }

            // Log summary
            Log.d("MainActivity", "Total valid items processed: " + itemList.size());
            Log.d("MainActivity", "Total items skipped due to null/empty names: " + skippedItems);

            // Notify adapter on the main thread to update UI
            runOnUiThread(() -> adapter.notifyDataSetChanged());
        } catch (Exception e) {
            Log.e("MainActivity", "Error parsing JSON: " + e.getMessage());
        }
    }
}