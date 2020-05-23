package com.example.ruralecommerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import java.util.ArrayList;
import android.content.Intent;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class Buyer extends AppCompatActivity {

    private static final String TAG = "TAG";
    private ListView listView;
    private DataAdapter mAdapter;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    ArrayList<Data> dataList = new ArrayList<>();

    @Override
    public void onBackPressed(){
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer);

        listView = (ListView) findViewById(R.id.data_list);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        final ArrayList<String> goodlist = new ArrayList<>();
        final ArrayList<String> pricelist = new ArrayList<>();
        final ArrayList<String> quantitylist = new ArrayList<>();

        fStore.collection("Cart")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String ID = document.getId();
                                String good = document.getString("Goodname");
                                String price = document.getString("Price");
                                String quantity = document.getString("Quantity");
                                goodlist.add(good);
                                pricelist.add(price);
                                quantitylist.add(quantity);
                                Log.d(TAG, ID + " => " + good + " " + price + " " + quantity + " " + goodlist.size());
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }

                        Log.d(TAG, "Entered here " + goodlist.size());
                        for(int i = 0; i < goodlist.size(); i++)
                        {
//                            dataList.add(new Data(R.drawable.ic_launcher_background, goodlist.get(i), pricelist.get(i), quantitylist.get(i)));
                            Log.d(TAG,  i + " "+ goodlist.get(i) + " " + pricelist.get(i) + " " + quantitylist.get(i) + " " + dataList.size());
                        }
                    }
                });
        dataList.add(new Data(R.drawable.ic_launcher_background, "Good 1" , "25", "25"));
        dataList.add(new Data(R.drawable.ic_launcher_background, "Good 2" , "20", "45"));
        dataList.add(new Data(R.drawable.ic_launcher_background, "Good 3" , "16", "5"));
        dataList.add(new Data(R.drawable.ic_launcher_background, "Good 4" , "24", "9"));

        mAdapter = new DataAdapter(this,dataList);
        listView.setAdapter(mAdapter);

    }
}