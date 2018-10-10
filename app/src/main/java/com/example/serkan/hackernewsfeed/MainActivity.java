package com.example.serkan.hackernewsfeed;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayList<String> newsHeaderList = new ArrayList<>();
    private ArrayList<String> newsIdList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.listView = findViewById(R.id.newsListView);
        generateNewsHeaderList(fetchDataFromAPI("https://hacker-news.firebaseio.com/v0/topstories.json"));
        displayNewsHeaders();
        handleListViewClickListener();
    }

    public void handleListViewClickListener() {
        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, WebContentActivity.class);
                intent.putExtra("url", getURL(position));
                startActivity(intent);
            }
        });
    }

    public String getURL(int index){
        String url = "https://hacker-news.firebaseio.com/v0/item/" + this.newsIdList.get(index) + ".json";
        String data = fetchDataFromAPI(url);
        JsonObject json = new Gson().fromJson(data, JsonObject.class);
        String fetchUrl = json.get("url").toString();
        fetchUrl = fetchUrl.substring(1,fetchUrl.length());
        return fetchUrl;
    }

    public void displayNewsHeaders() {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, this.newsHeaderList);
        this.listView.setAdapter(arrayAdapter);
    }

    public void generateNewsHeaderList(String data) {
        generateNewsIdList(data);
        this.newsIdList.subList(20,this.newsIdList.size()).clear();
        for (String storyId:newsIdList){
            String url = "https://hacker-news.firebaseio.com/v0/item/" + storyId + ".json";
            this.newsHeaderList.add(getNewsHeader(fetchDataFromAPI(url)));
        }
    }

    public void generateNewsIdList(String data){
        data = data.substring(1, data.length());
        this.newsIdList = new ArrayList<>(Arrays.asList(data.split(",")));
    }

    public String getNewsHeader(String data){
        JsonObject json = new Gson().fromJson(data, JsonObject.class);
        return json.get("title").toString();
    }

    public String fetchDataFromAPI(String apiUrl) {
        FetchAPI fetchAPI = new FetchAPI();
        String res = "";
        try {
            res = fetchAPI.execute(apiUrl).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }
}
