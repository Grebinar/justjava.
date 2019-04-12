/**
 * IMPORTANT: Make sure you are using the correct package name. 
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.example.android.justjava;



import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    int quantity = 0;
    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        EditText text = findViewById(R.id.customer);
        String name = text.getText().toString();

        CheckBox whippedCreamBox = findViewById(R.id.whip_cream);
        boolean hasWhippedCream = whippedCreamBox.isChecked();

        CheckBox chocolateTopping = findViewById(R.id.chocolate);
        boolean hasChocolate = chocolateTopping.isChecked();

        int price = calculatePrice(hasWhippedCream, hasChocolate);
        String priceMessage = createOrderSummary(price, hasWhippedCream, hasChocolate, name);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for " + name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }


    private String createOrderSummary(int price, boolean whippedCream, boolean chocolateTopping, String name) {
        String priceMessage = "Name: " + name;
        priceMessage += "\nwhipped cream? " + whippedCream;
        priceMessage += "\nChocolate?" + chocolateTopping;
        priceMessage += "\nQuantity:" + quantity;
        priceMessage += "\nTotal:$" + price;
        priceMessage += "\nThank you!";
        return priceMessage;
    }

    /**
     * Calculates the price of the order.
     *
     */
    private int calculatePrice(boolean addWhipCream, boolean addChocolate) {
        int basePrice = 5;
        if (addWhipCream) {
            basePrice = basePrice + 1;
        }

        if (addChocolate) {
            basePrice = basePrice + 2;
        }

        return quantity * basePrice;
    }

    public void increment(View view) {
        quantity = quantity + 1;

        if (quantity > 99) {
            quantity = 100;
            Toast.makeText(this, "You can't have more than 100 coffee", Toast.LENGTH_SHORT).show();
        }
        display(quantity);
    }

    public void decrement(View view) {
        quantity = quantity - 1;

        if (quantity <2) {
            quantity = 1;
            Toast.makeText(this, "You can't have less than 1 coffee", Toast.LENGTH_SHORT).show();
        }
        display(quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int digit) {
        TextView quantityTextView = findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + digit);
    }

}