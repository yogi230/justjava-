/**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.example.justjava;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    int numberOfCoffees=0;
    public void submitOrder(View view) {

        CheckBox whippedCreamCheckBox=(CheckBox)findViewById(R.id.whipped_cream_checkbox);
        CheckBox chocolateCheckBox=(CheckBox)findViewById(R.id.chocolate_checkbox);
        EditText nameOfUser=(EditText)findViewById(R.id.name_input);
        String userName=nameOfUser.getText().toString();
        boolean hasWhippedCream=whippedCreamCheckBox.isChecked();
        boolean hasChocolate=chocolateCheckBox.isChecked();
        display(numberOfCoffees);
//        displayPrice(numberOfCoffees*5);
        String priceMessage=createOrderSummary(hasWhippedCream,hasChocolate,userName);
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "JUST JAVA ORDER COFFEE");
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

//
//        displayMessage(priceMessage);

    }
    public void showError(View view)
    {
        Context context=getApplicationContext();
        CharSequence text="order quantity OUT OF BOUND!";
        int dur= Toast.LENGTH_SHORT;
        Toast.makeText(context,text,dur).show();
    }
    public void increment(View view) {
        if(numberOfCoffees<10) {
            numberOfCoffees++;
        }
        else
        {
            showError(view);
        }
        display(numberOfCoffees);
    }
    public void decrement(View view) {
        if(numberOfCoffees>0) {
            numberOfCoffees--;
        }
        else
        {
            showError(view);
        }
        display(numberOfCoffees);
    }
    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }
    /**
     * This method displays the given price on the screen.
     */
//    private void displayPrice(int number) {
//        TextView priceTextView = (TextView) findViewById(R.id.orderSummary_text_view);
//        priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));
//    }
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.orderSummary_text_view);
        orderSummaryTextView.setText(message);
    }
    private String createOrderSummary(boolean addWhippedCream,boolean addChocolate,String userName)
    {
        int baseprice=5;
        if(addWhippedCream)
            baseprice++;
        if(addChocolate)
            baseprice+=2;
        int price=baseprice*numberOfCoffees;
        String User_name=getString(R.string.ordername,userName);
        String message=User_name+"\nAdded Whipped Cream? "+addWhippedCream+"\nAdded Chocolate? "+addChocolate+"\nQuantity: "+numberOfCoffees+"\nTotal: $"+price+"\nThank You!!";
        return message;
    }
}