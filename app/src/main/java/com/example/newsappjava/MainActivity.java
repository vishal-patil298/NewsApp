package com.example.newsappjava;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.FileWriter;
import java.io.IOException;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NewsItemClicked{

    private RecyclerView recyclerView;
    private NewsListAdapter mAdapter;

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter=new NewsListAdapter(this);
        fetchData();
        recyclerView.setAdapter(mAdapter);
    }

    /**
     * Restart function to fetch the data on restart
     * @return nothing
     */
    @Override
    protected void onRestart() {
        super.onRestart();
        fetchData();
    }

    /**
     * fetchData functin create the json object to get the response and call the update news function of the NewslistAdapter class.
     * Add the JsonObjectRequest to the request queue.
     */
    private void fetchData()
    {

        String url="https://stagingams.pubmatic.com:8443/sdk/Sushant/news.json";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    /**
                     * Listener method work after receiving the jsonObject response.
                     * @param response JSONObject contains the JSONArray and JSON elements.
                     */
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray newsJsonArray=response.getJSONArray("articles");
                            ArrayList<News> newsArray=new ArrayList<News>();
                            for (int i=0;i<newsJsonArray.length();i++)
                            {
                                //fetching the jsonboject from the jsonarray
                                JSONObject newsJsonObject=newsJsonArray.getJSONObject(i);

                                //creating object of news classes by parametric constructor
                                News news=new News(newsJsonObject.getString("title"),
                                        newsJsonObject.getString("author"),
                                        newsJsonObject.getString("url"),
                                        newsJsonObject.getString("urlToImage"));

                                //adding the created object into the array of objects
                                newsArray.add(news);
                            }
                            mAdapter.updateNews(newsArray);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(error.toString(),"Failed to recevie response from provided API.");
                    }
                });
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }

    /**
     * onItemClicked is an of NewsItemClicked Interface.
     * which open links in the custom chrome tab.
     * @param item View of Recycler View holder.
     */
    @Override
    public void onItemClicked(News item) {
        if(item==null) {
            Log.d("Error","Object is null");
        }
        else
        {
            CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
            CustomTabsIntent customTabsIntent = builder.build();
            customTabsIntent.launchUrl(this, Uri.parse(item.url));
        }
    }
}