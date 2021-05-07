package com.example.moremeal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UpdateItemActivity extends AppCompatActivity {

    TextView textView1;
    TextView textView2;
    TextView textView3;
    TextView textView4;
    Button updateButton;
    DatabaseReference dbRef;
    String keyValue;
    String price = "";
    Integer quantity;
    String size;
    String medium_price;
    String large_price;
    String premium_price;
    Model ob;
    String pizzaName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_item);

        ob = new Model();
        //old values
        Intent intent = getIntent();
        String key = intent.getStringExtra(CartViewActivity.EXTRA_MESSAGE);
        String message1 = intent.getStringExtra(CartViewActivity.EXTRA_MESSAGE1);
        String message2 = intent.getStringExtra(CartViewActivity.EXTRA_MESSAGE2);
        String message3 = intent.getStringExtra(CartViewActivity.EXTRA_MESSAGE3);
        String message4 = intent.getStringExtra(CartViewActivity.EXTRA_MESSAGE4);
        String message5 = intent.getStringExtra(CartViewActivity.EXTRA_MESSAGE5);
        String message6 = intent.getStringExtra(CartViewActivity.EXTRA_MESSAGE6);
        String message7 = intent.getStringExtra(CartViewActivity.EXTRA_MESSAGE7);



        textView1 = (TextView)findViewById(R.id.row1col2);
        textView2 = (TextView)findViewById(R.id.row2col2);
        textView3 = (TextView)findViewById(R.id.row3col2);
        textView4 = (TextView)findViewById(R.id.row4col2);

       // updateButton = findViewById(R.id.updatecartbutton);


        //textView1.setText(message7);
        textView1.setText(message2);
        textView2.setText(message1);
        textView3.setText(message3);
        textView4.setText(message7);
       // textView4.setText("jjj");





    }


    public void updateItem(View view){

        Intent intent2 = getIntent();
        String key = intent2.getStringExtra(CartViewActivity.EXTRA_MESSAGE);
        String message1 = intent2.getStringExtra(CartViewActivity.EXTRA_MESSAGE1);
        String message2 = intent2.getStringExtra(CartViewActivity.EXTRA_MESSAGE2);
        String message3 = intent2.getStringExtra(CartViewActivity.EXTRA_MESSAGE3);
        String message4 = intent2.getStringExtra(CartViewActivity.EXTRA_MESSAGE4);
        String message5 = intent2.getStringExtra(CartViewActivity.EXTRA_MESSAGE5);
        String message6 = intent2.getStringExtra(CartViewActivity.EXTRA_MESSAGE6);
        String message7 = intent2.getStringExtra(CartViewActivity.EXTRA_MESSAGE7);

        keyValue = key;
        medium_price = message4;
        large_price = message5;
        premium_price = message6;
        pizzaName = message2;

        EditText editText = (EditText) findViewById(R.id.editTextUQuantity);
        String q    = editText.getText().toString();


        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.u_pizza_size);


        int selectedId = radioGroup.getCheckedRadioButtonId();

        RadioButton radioButton = (RadioButton) findViewById(selectedId);

        size = radioButton.getText().toString();






        if (size.equals("Medium")){


            price = medium_price;

        }
        else if(size.equals("Large")){

            price = large_price;

        }
        else{

            price = premium_price;
        }





        Integer quantity = Integer.parseInt(q);
        Double rightPrice = Double.parseDouble(price);
        Double price1 = Double.parseDouble(medium_price);
        Double price2 = Double.parseDouble(large_price);
        Double price3 = Double.parseDouble(premium_price);

        Double total = rightPrice * quantity;
        String totalPrice = ""+total;

        textView1.setText(pizzaName);
        textView2.setText(q);
        textView3.setText(size);
        textView4.setText(totalPrice);


        updateOnDb(keyValue, pizzaName, 1, quantity, rightPrice, size, price1, price2, price3);




    }




    public void updateOnDb(String uKey, String uPizzaName, Integer imgId, Integer uQuantity, Double uPrice, String uSize, Double medium, Double large, Double premium) {


        DatabaseReference updRef = FirebaseDatabase.getInstance().getReference().child("Model");

        updRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.hasChild(uKey)){

                    ob.setName(uPizzaName);
                    ob.setImage(R.drawable.pizza);
                    ob.setQuantity(uQuantity);
                    Double tot = uPrice * uQuantity;
                    ob.setNowPrice(tot);
                    ob.setNowSize(uSize);
                    ob.setMediumPrice(medium);
                    ob.setLargePrice(large);
                    ob.setPremiumPrice(premium);
                    ob.setMyd("hello");

                    dbRef = FirebaseDatabase.getInstance().getReference().child("Model").child(uKey);
                    dbRef.setValue(ob);
                    Toast.makeText(getApplicationContext(), "Modified", Toast.LENGTH_SHORT).show();



                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });














    }
}