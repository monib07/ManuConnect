package com.example.monib.manuconnect;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private List<DataObject> results;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView.LayoutManager mLayoutManager;
    private static String LOG_TAG = "MainActivity";
    private static final String URL_Data="http://shortinfo.info/saifulData/Manuu_Script.php?id=";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        linearLayoutManager=new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        results=new ArrayList<>();
        loadData(0);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                if(linearLayoutManager.findLastCompletelyVisibleItemPosition() == results.size()-1){
                    loadData(results.get(results.size()-1).getId());
                }

            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


    }
    private void loadData(final int id)
    {
        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Load Data");
        progressDialog.show();
        StringRequest stringRequest=new StringRequest(Request.Method.GET,
                URL_Data+id,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try{
                            progressDialog.dismiss();
                            JSONObject parent =  new JSONObject(s);

                            JSONArray NewsList = parent.getJSONArray("NewsList");

                            for(int i=0;i<NewsList.length();i++)
                            {
                                JSONObject c = NewsList.getJSONObject(i);
                                int id=c.getInt("ID");
                                String title=c.getString("Title");
                                String desc=c.getString("Description");
                                String date=c.getString("Date");
                                DataObject dataObject=new DataObject(id,title,desc,date);
                                results.add(dataObject);

                            }
                            mAdapter=new MyAdapter(results,getApplicationContext());
                            mRecyclerView.setAdapter(mAdapter);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
       RequestQueue requestQueue=Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);



    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.about) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.notice_board) {
            Toast.makeText(getApplicationContext(), "Notice Board Selected", Toast.LENGTH_LONG).show();
            // Intent i = new Intent(getApplicationContext(), .class);
            //startActivity(i);
        } else if (id == R.id.contact_us) {
            startActivity(new Intent(MainActivity.this,ContactActivity.class));
        } else if (id == R.id.result) {
            Toast.makeText(getApplicationContext(), "Result Selected", Toast.LENGTH_LONG).show();
        } else if (id == R.id.interior) {
            Toast.makeText(getApplicationContext(), "Interior Selected", Toast.LENGTH_LONG).show();
        } else if (id == R.id.job) {
            Toast.makeText(getApplicationContext(), "Job Selected", Toast.LENGTH_LONG).show();
        } else if (id == R.id.home_Page) {
            Toast.makeText(getApplicationContext(), "Home Page Selected", Toast.LENGTH_LONG).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }





}


