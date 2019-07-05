package com.example.nudge.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.apptakk.http_request.HttpRequest;
import com.apptakk.http_request.HttpRequestTask;
import com.apptakk.http_request.HttpResponse;
import com.example.nudge.R;
import com.example.nudge.adapters.MarketAdapter;
import com.example.nudge.models.MarketModel;
import com.example.nudge.models.crop;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SelectedCropActity extends AppCompatActivity {

    ImageView backBtn;

    FirebaseFirestore db;
    TextView title;
    RecyclerView marketRcv;
    MarketAdapter adapter;
    List<MarketModel> markets = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_crop_actity);

//        Intent intent=getIntent();
//        crop crop=intent.getParcelableExtra("Crop Name");

//        int cropimage = crop.getCropimage();
//        String cropname = crop.getCropname();

        marketRcv = findViewById(R.id.markets_rcv);
        adapter = new MarketAdapter(markets,this);

        backBtn = findViewById(R.id.back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        title = findViewById(R.id.crop_title_id);

        title.setText("Loading ...");

        String id = getIntent().getStringExtra("ID");
        db = FirebaseFirestore.getInstance();

        marketRcv.setLayoutManager(new LinearLayoutManager(this));
        marketRcv.setAdapter(adapter);

        db.collection("crops").document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                final crop cropModel = documentSnapshot.toObject(crop.class);

                String url = "https://quiet-springs-34953.herokuapp.com/fetchdata?commodity=turmeric";

                new HttpRequestTask(
                        new HttpRequest(url, HttpRequest.POST, "{ \"some\": \"data\" }"),

                        new HttpRequest.Handler() {
                            @Override
                            public void response(HttpResponse response) {
                                if (response.code == 200) {

                                    String res = response.body;

                                    JSONObject jsonObj = null;
                                    try {
                                        jsonObj = new JSONObject(res);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                    try {
                                        String message = jsonObj.getString("message");
                                        title.setText("Top Countries to export "+cropModel.getName());

                                        JSONArray arr = jsonObj.getJSONArray("result");
                                        String countries[] = new String[arr.length()];
                                        for (int i = 0; i < arr.length(); i++) {
                                            countries[i] = arr.getString(i);
                                            Log.d("Countries are ",countries[i]);
                                            getInfo(countries[i]);
                                        }

                                    } catch (JSONException e) {
                                        Toast.makeText(SelectedCropActity.this,"No answer", Toast.LENGTH_SHORT).show();
                                        e.printStackTrace();
                                    }

                                } else {
                                    Log.e(this.getClass().toString(), "Request unsuccessful: " + response);
                                }
                            }
                        }).execute();
            }
        });
    }

    void getInfo(final String country) {

        String url = "https://quiet-springs-34953.herokuapp.com/fetchdata?country="+country;

        new HttpRequestTask(
                new HttpRequest(url, HttpRequest.POST, "{ \"some\": \"data\" }"),

                new HttpRequest.Handler() {
                    @Override
                    public void response(HttpResponse response) {
                        if (response.code == 200) {

                            String res = response.body;

                            JSONObject jsonObj = null;
                            try {
                                jsonObj = new JSONObject(res);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            try {
                                String message = jsonObj.getString("result");

                                String SP="";
                                String exportCharge = "";
                                String Profitrate = "";
                                String profitPercent = "";
                                int j=0;
                                List<String> ans = new ArrayList<>();
                                for(int k=0;k<message.length();k++) {
                                    String number= "";
                                    while(Character.isDigit(message.charAt(k)) || (k-1>0 && k+1<message.length() && Character.isDigit(message.charAt(k-1)) && Character.isDigit(message.charAt(k+1)))) {
                                        number+= message.charAt(k);
                                        k++;
                                    }
                                    if(number.length()!=0) ans.add(number);
                                }

                                SP = ans.get(0);
                                exportCharge = ans.get(1);
                                Profitrate = ans.get(2);
                                profitPercent = ans.get(3);

                                MarketModel marketModel = new MarketModel(SP,exportCharge,Profitrate,profitPercent,country,message);
                                markets.add(marketModel);
                                adapter.notifyDataSetChanged();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        } else {
                            Log.e(this.getClass().toString(), "Request unsuccessful: " + response);
                        }
                    }
                }).execute();
    }
}
