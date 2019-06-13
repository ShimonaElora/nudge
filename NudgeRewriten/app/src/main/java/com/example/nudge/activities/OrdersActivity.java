package com.example.nudge.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import com.example.nudge.R;
import com.example.nudge.adapters.OrdersAdapter;
import com.example.nudge.models.OrderModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class OrdersActivity extends AppCompatActivity {


    Button orderBtn,receivedBtn,deliveredBtn;
    ImageView backBtn;
    RecyclerView orderRcv;
    OrdersAdapter adapter;
    List<String> farmers = new ArrayList<>();
    List<String> orderTypes = new ArrayList<>();
    List<String> dates = new ArrayList<>();
    List<Integer> flag = new ArrayList<>();
    List<OrderModel> orderModelList;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        orderBtn = findViewById(R.id.order_btn);
        receivedBtn = findViewById(R.id.received_btn);
        deliveredBtn = findViewById(R.id.delivered_btn);
        backBtn = findViewById(R.id.back_btn1);
        orderRcv = findViewById(R.id.order_rcv);
        preferences = getSharedPreferences("MyPref",MODE_PRIVATE);

        farmers.add("John Doe");
        farmers.add("Pablo Escobar");
        farmers.add("Skyler Grey");
        farmers.add("Daft Punk");
        farmers.add("Pablo Escobar");

        for(int i=0;i<5;i++) {
            orderTypes.add("Ordered By:");
            dates.add("2"+i+"th June,2019");
        }

        flag.add(1);

        orderRcv.setLayoutManager(new LinearLayoutManager(this));
        orderRcv.setItemAnimator(new DefaultItemAnimator());
        adapter = new OrdersAdapter(farmers,orderTypes,dates,flag,this);
        orderRcv.setAdapter(adapter);


        orderBtn.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) v.performClick();
            }
        });

        receivedBtn.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) v.performClick();
            }
        });

        deliveredBtn.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) v.performClick();
            }
        });

        orderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderBtn.requestFocus();
                orderTypes.clear();
                for(int i=0;i<5;i++) orderTypes.add("Ordered By:");
                flag.clear();
                flag.add(1);
                adapter.notifyDataSetChanged();
                loadSharedPref();
            }
        });

        receivedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                receivedBtn.requestFocus();
                orderTypes.clear();
                for(int i=0;i<5;i++) orderTypes.add("Deliver to:");
                flag.clear();
                flag.add(2);
                adapter.notifyDataSetChanged();
            }
        });

        deliveredBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deliveredBtn.requestFocus();
                orderTypes.clear();
                for(int i=0;i<5;i++) orderTypes.add("Delivered to:");
                flag.clear();
                flag.add(3);
                adapter.notifyDataSetChanged();

            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    public  void  loadSharedPref(){
        Gson gson = new Gson();
        String json = preferences.getString("OrderPlaced",null);
        Type type = new TypeToken<ArrayList<OrderModel>>() {}.getType();
        orderModelList= gson.fromJson(json,type);
        if(orderModelList == null)
        {
            orderModelList= new ArrayList<>();
        }
        adapter.updateAdapter(orderModelList);
        adapter.notifyDataSetChanged();
    }
}
