package com.example.monib.manuconnect;

import android.*;
import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final String URL_Data = "http://manuucoe.in/ums/index.php/api/GetNews?Start=0&Limit=10&Cat=Result";
    private static String LOG_TAG = "MainActivity";
    private List<DataObject> results;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView.LayoutManager mLayoutManager;
    private static final int Req_Perms=123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseMessaging.getInstance().subscribeToTopic("test");
        FirebaseInstanceId.getInstance().getToken();


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        results = new ArrayList<>();
        loadData();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


    }

    private void loadData() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Load Data");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                URL_Data ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try {
                            progressDialog.dismiss();
                            JSONObject parent = new JSONObject(s);
                            JSONArray NewsList = parent.getJSONArray("News");
                            for (int i = 0; i < NewsList.length(); i++) {
                                JSONObject object=NewsList.getJSONObject(i);
                                DataObject dataObject=new DataObject(object.getInt("Id"),object.getString("Title"),object.getString("RedirectUrl"),object.getString("AddedOn"));
                                results.add(dataObject);
                            }
                            mAdapter = new MyAdapter(results, getApplicationContext());
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
        RequestQueue requestQueue = Volley.newRequestQueue(this);
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
            startActivity(new Intent(MainActivity.this, AboutUs.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.home)
        {
            Toast.makeText(getApplicationContext(), "Notice Board Selected", Toast.LENGTH_LONG).show();
        }
        else if (id == R.id.admission) {
            Toast.makeText(getApplicationContext(), "Result Selected", Toast.LENGTH_LONG).show();
        }
        else if (id == R.id.employment) {
            Toast.makeText(getApplicationContext(), "Job Selected", Toast.LENGTH_LONG).show();
        }
        else if (id == R.id.result) {

            Intent i = new Intent(getApplicationContext(), PostDetails.class);
            i.putExtra("Link","http://manuucoe.in/ums/resultsheet");
            startActivity(i);
        }
        else if (id == R.id.courseStructure) {
            Intent i = new Intent(getApplicationContext(), PostDetails.class);
            i.putExtra("Link","http://manuucoe.in/ums/CourseCurriculum");
            startActivity(i);
        }
        else if (id == R.id.ExamResult) {
            Intent i = new Intent(getApplicationContext(), PostDetails.class);
            i.putExtra("Link"," http://manuucoe.in/ums/ResultMarkSheets");
            startActivity(i);
        }
        else if (id == R.id.studentLogin) {
            Intent i = new Intent(getApplicationContext(), PostDetails.class);
            i.putExtra("Link","http://manuucoe.in/ums/student/login");
            startActivity(i);

        }
        else if (id == R.id.emplogin) {
            Intent i = new Intent(getApplicationContext(), PostDetails.class);
            i.putExtra("Link","http://manuucoe.in/ums/teacher/login");
            startActivity(i);
        }

        else if (id == R.id.contact) {
            if(hasPermission())
            {
                Intent i = new Intent(getApplicationContext(), ContactActivity.class);
                startActivity(i);
            }
            else
            {
                RequestPermission();
            }
        }
        else if (id == R.id.web) {
            //Toast.makeText(getApplicationContext(), "Home Page Selected", Toast.LENGTH_LONG).show();
            Intent i = new Intent(android.content.Intent.ACTION_VIEW,
                    Uri.parse("http://www.manuu.ac.in"));
            startActivity(i);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public boolean hasPermission()
    {
        int res=0;
        String[] permission=new String[]{Manifest.permission.ACCESS_FINE_LOCATION};
        for(String params:permission)

        {
            res=checkCallingOrSelfPermission(params);
            if(!(res== PackageManager.PERMISSION_GRANTED))
            {
                return false;
            }
        }
        return true;
    }

    private void RequestPermission() {
        String[] permission=new String[]{Manifest.permission.ACCESS_FINE_LOCATION};
        if(Build.VERSION.SDK_INT>=23)
        {
            requestPermissions(permission,Req_Perms);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        boolean allowed=true;
        switch (requestCode)
        {
            case Req_Perms:
                for (int res:grantResults)
                {
                    allowed=allowed && (res==PackageManager.PERMISSION_GRANTED);
                }
                break;
            default:
                allowed=false;
                break;
        }
        if(allowed)
        {
            Intent i = new Intent(getApplicationContext(), ContactActivity.class);
            startActivity(i);
        }
        else
        {
            if(Build.VERSION.SDK_INT>=23)
            {

                if(shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION));
                Toast.makeText(this,"Access Location Denied",Toast.LENGTH_SHORT).show();
            }

        }



    }


    // This fires when a notification is opened by tapping on it or one is received while the app is running.

}

