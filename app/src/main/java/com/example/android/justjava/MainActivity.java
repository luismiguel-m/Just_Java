package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

//import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        boolean hasWhippedCream = hasWhippedCream();
        boolean hasChocolate = hasChocolate();
        String name = getNameField();
        int price = calculatePrice(hasWhippedCream, hasChocolate);
        String priceMessage = createOrderSummary(name, price, hasWhippedCream, hasChocolate);
        displayMessage(priceMessage);

        sendEmail(priceMessage, name);
    }

    /**
     * Create summary of the order.
     *
     * @param name            of the consumer
     * @param addWhippedCream is whether or not the user wants whipped cream topping
     * @param addChocolate    is whether or not the user wants chocolate topping
     * @param price           of the order
     * @return text summary
     */
    private String createOrderSummary(String name, int price, boolean addWhippedCream, boolean addChocolate) {
        String priceMessage = "Name: " + name;
        priceMessage += "\nAdd whipped cream? " + addWhippedCream;
        priceMessage += "\nAdd chocolate? " + addChocolate;
        priceMessage += "\nQuantity: " + quantity;
        priceMessage += "\nTotal: $" + price;
        priceMessage += "\nThank you!";
        return priceMessage;
    }

//    /**
//     * This method displays the given price on the screen.
//     */
//    private void displayPrice(int number) {
//        TextView priceTextView = findViewById(R.id.order_summary_text_view);
//        priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));

//    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }

    /**
     * This method checks if the whipped cream was added
     */
    private boolean hasWhippedCream() {
        CheckBox whippedCreamCheckBox = findViewById(R.id.whipped_cream_checkbox);
        return whippedCreamCheckBox.isChecked();
    }

    /**
     * This method checks if the chocolate cream was added
     */
    private boolean hasChocolate() {
        CheckBox chocolateCheckBox = findViewById(R.id.chocolate_checkbox);
        return chocolateCheckBox.isChecked();
    }

    /**
     * This method gets the name field
     */
    private String getNameField() {
        EditText nameFieldEditText = findViewById(R.id.name_field);
        return nameFieldEditText.getText().toString();
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = findViewById(R.id.quantity_text_view);
        String num = Integer.toString(number);
        quantityTextView.setText(num);
    }

    /**
     * Calculates the price of the order.
     *
     * @param addWhippedCream is whether oe not the user wants whipped cream topping
     * @param addChocolate    is whether oe not the user wants chocolate topping
     * @return total price
     */
    private int calculatePrice(boolean addWhippedCream, boolean addChocolate) {
        // Price of 1 cup of coffee
        int basePrice = 5;

        // Add $1 if the user wants whipped cream
        if (addWhippedCream) {
            basePrice += 1;
        }

        // Add $2 if the user chocolate
        if (addChocolate) {
            basePrice += 2;
        }

        // Calculate the total order price by multiplying by quantity
        return basePrice * quantity;
    }

    /**
     * This method will increment the value of the quantity
     */
    public void increment(View view) {
        if (quantity == 100) {
            return;
        }
        ++quantity;
        displayQuantity(quantity);
    }

    /**
     * This method will decrement the value of the quantity
     */
    public void decrement(View view) {
        if (quantity == 1) {
            return;
        }
        --quantity;
        displayQuantity(quantity);
    }

    private void sendEmail(String priceMessage, String name) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for " + name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}
