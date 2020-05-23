package com.example.ruralecommerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Seller extends AppCompatActivity {
    public static final String TAG = "TAG";
    EditText mGoodName, mQuantity, mPrice;
    Button mSell;

    FirebaseAuth fAuth;
    FirebaseFirestore fstore;
    String userID;

    @Override
    public void onBackPressed(){
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller);
//        mGoodName = findViewById(R.id.good);
        mQuantity = findViewById(R.id.quantity);
        mPrice = findViewById(R.id.price);
        mSell = findViewById(R.id.sell);

        fAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();

        mSell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                final String good = mGoodName.getText().toString().trim();
                final String good = "Grains";
                final String quantity = mQuantity.getText().toString();
                final String price = mPrice.getText().toString();

                if(TextUtils.isEmpty(good)){
                    mGoodName.setError("Email is required");
                    return;
                }

                if(TextUtils.isEmpty(quantity)){
                    mQuantity.setError("Quantity is required");
                    return;
                }

                if(TextUtils.isEmpty(price)){
                    mPrice.setError("Price is required");
                    return;
                }

                userID = fAuth.getCurrentUser().getUid();
                DocumentReference documentReference = fstore.collection("Cart").document(userID);
                Map<String, Object> transaction= new HashMap<>();
                transaction.put("Goodname", good);
                transaction.put("Quantity", quantity);
                transaction.put("Price", price);

                documentReference.set(transaction).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(Seller.this, "Successful Addition!", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "onSuccess: Seller is created for "+ userID);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Seller.this, "Error!", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "onFailure: "+ e.toString());
                    }
                });
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

    }
}
