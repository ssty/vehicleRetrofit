package com.example.acer.finalmajorproject;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listViewHeroes);

        //calling the method to display the heroes
        getVehicle();
    }

    private void getVehicle() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()) //Here we are using the GsonConverterFactory to directly convert json data to object
                .build();

        Api api = retrofit.create(Api.class);

        Call<List<Vehicle>> call = api.getVehicle();

        call.enqueue(new Callback<List<Vehicle>>() {
            @Override
            public void onResponse(Call<List<Vehicle>> call, Response<List<Vehicle>> response) {
                List<Vehicle> vehicleList = response.body();

                //Creating an String array for the ListView
                String[] vehicle = new String[vehicleList.size()];

                //looping through all the heroes and inserting the names inside the string array
                for (int i = 0; i < vehicleList.size(); i++) {
                    vehicle[i] = vehicleList.get(i).getVehicleType();
                    vehicle[i]=vehicleList.get(i).getBrand();
                    vehicle[i] = vehicleList.get(i).getDescription();
                    vehicle[i]=vehicleList.get(i).getEnginePower();
                    vehicle[i] = vehicleList.get(i).getImage();
                    vehicle[i]=vehicleList.get(i).getModelNo();
                    vehicle[i] = String.valueOf(vehicleList.get(i).getPrice());
                }


                //displaying the string array into listview
                listView.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, vehicle));

            }

            @Override
            public void onFailure(Call<List<Vehicle>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
