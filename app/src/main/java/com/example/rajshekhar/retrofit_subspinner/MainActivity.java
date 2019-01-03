package com.example.rajshekhar.retrofit_subspinner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.rajshekhar.retrofit_subspinner.apis.ApiBuilder;
import com.example.rajshekhar.retrofit_subspinner.bean.CoutryPopulation;
import com.example.rajshekhar.retrofit_subspinner.bean.PopulationBean;
import com.example.rajshekhar.retrofit_subspinner.utils.Utils;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.GsonBuilder;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.internal.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.POST;

public class MainActivity extends AppCompatActivity {
    JSONObject jsonObject;
    JSONArray jsonArray;
    CoutryPopulation coutryPopulation;
    ArrayList<String>countryList= new ArrayList<String>();
    List<CoutryPopulation>coutryPopulationList;
    private Spinner spinner;
    private TextView countryName,txtpopulation,txtrank;
    private ImageView imageView;
    private KProgressHUD hud;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initview();
        getSpinnerData();

    }

    private void initview() {
        spinner = (Spinner) findViewById(R.id.my_spinner);
        txtrank = (TextView) findViewById(R.id.rank);
        countryName = (TextView) findViewById(R.id.country);
        txtpopulation = (TextView) findViewById(R.id.population);
        imageView = (ImageView) findViewById(R.id.image);
    }
    private void getSpinnerData() {

        ApiBuilder.getInstance().getCountryDetails().enqueue(new Callback<PopulationBean>() {
            @Override
            public void onResponse(Call<PopulationBean> call, Response<PopulationBean> response) {
                if(response.isSuccessful()){
                    final PopulationBean populationBean=(PopulationBean)response.body();
                    populationBean.getWorldpopulation();
                    Utils.printLog("sucess","true"+new GsonBuilder().create().toJson(populationBean));

                    if(populationBean!=null){
                        coutryPopulationList=populationBean.getWorldpopulation();
                        for (int i=0;i<coutryPopulationList.size();i++){
                            Log.e("Country",coutryPopulationList.get(i).getCountry());
                            countryList.add(coutryPopulationList.get(i).getCountry());
                        }
                        initListeners();
                    }

                }

            }

            @Override
            public void onFailure(Call<PopulationBean> call, Throwable t) {

            }
        });



        }

    private void initListeners() {
        if(countryList.size()!=0){
            Spinner spinner =(Spinner)findViewById(R.id.my_spinner);
            spinner.setAdapter(new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_spinner_dropdown_item,countryList));
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    txtrank.setText("Rank :"+coutryPopulationList.get(position).getRank());
                    countryName.setText("Country :"+coutryPopulationList.get(position).getCountry());
                    txtpopulation.setText("Population"+coutryPopulationList.get(position).getPopulation());
                    Glide.with(MainActivity.this).load(coutryPopulationList.get(position).getFlag()).into(imageView);

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        }
    }

}
