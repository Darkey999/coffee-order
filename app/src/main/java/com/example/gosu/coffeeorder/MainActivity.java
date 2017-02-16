package com.example.gosu.coffeeorder;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {
    TextView quantityView, summary;
    CheckBox checkCream, checkChocolate;
    Button orderButton;
    EditText getName;
    int quantity = 0;
    String finalText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        quantityView = (TextView) findViewById(R.id.quantityView);
        summary = (TextView) findViewById(R.id.summary);
        checkCream = (CheckBox) findViewById(R.id.checkCream);
        checkChocolate = (CheckBox) findViewById(R.id.checkChocolate);
        orderButton = (Button) findViewById(R.id.orderButton);
        getName = (EditText) findViewById(R.id.getName);
        ImageView iv = new ImageView(this);

        //listener for sending Intent (mail)
        orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent share = new Intent(Intent.ACTION_SENDTO);
                share.setData(Uri.parse("mailto:")); // only email apps should handle this
                share.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.coffe_order));
                share.putExtra(Intent.EXTRA_TEXT, finalText + "");
                if (share.resolveActivity(getPackageManager()) != null) {
                    startActivity(Intent.createChooser(share, getString(R.string.send_order))); //showing share window
                }
            }
        });

        //listener for checking EditText state
        getName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                makeOrder();
            }
        });
    }

    //handling minus button
    public void minusOne(View view) {
        if (quantity > 0) quantity--;
        displayQuantity(quantity);
        makeOrder();
    }

    //handling plus button
    public void plusOne(View view) {
        quantity++;
        displayQuantity(quantity);
        makeOrder();
    }

    //function for displaying amount of cups
    public void displayQuantity(int quantity) {
        quantityView.setText(quantity + "");
    }

    //function checking state of the order and showing summary
    public void makeOrder() {
        String whipped, chocolate;
        String price = NumberFormat.getCurrencyInstance().format(quantity * 5);
        if (checkCream.isChecked() && checkChocolate.isChecked()) {
            whipped = getString(R.string.confirm);
            chocolate = getString(R.string.confirm);
            price = NumberFormat.getCurrencyInstance().format(quantity * 9);
        } else if (checkCream.isChecked() && !checkChocolate.isChecked()) {
            whipped = getString(R.string.confirm);
            chocolate = getString(R.string.deny);
            price = NumberFormat.getCurrencyInstance().format(quantity * 7);
        } else if (!checkCream.isChecked() && checkChocolate.isChecked()) {
            whipped = getString(R.string.deny);
            chocolate = getString(R.string.confirm);
            price = NumberFormat.getCurrencyInstance().format(quantity * 7);
        } else {
            whipped = getString(R.string.deny);
            chocolate = getString(R.string.deny);
        }
        summary.setText(getString(R.string.name) + getName.getText() + "\n" + getString(R.string.quantity) + ":" + quantity + "\n" + getString(R.string.total) + price + "\n" + getString(R.string.add_cream) + whipped + "\n" + getString(R.string.add_chocolate) + chocolate + "\n" + getString(R.string.thanks));
        finalText = summary.getText().toString();
    }

    //handling cream CheckBox
    public void checkCream(View view) {
        makeOrder();
    }

    //handling chocolate CheckBox
    public void checkChocolate(View view) {
        makeOrder();
    }
}
