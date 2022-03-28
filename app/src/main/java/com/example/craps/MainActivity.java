package com.example.craps;


import android.content.DialogInterface;
        import android.content.Intent;
        import java.text.NumberFormat; // currency formatting

        import android.support.v7.app.AlertDialog;
        import android.support.v7.app.AppCompatActivity; // base class for activities
        import android.os.Bundle; // saving state information
        import android.text.Editable; // for EditText event handling
        import android.text.TextWatcher; // EditText listener
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText; // for buyin amount input
        import android.widget.TextView; // displaying text


public class MainActivity extends AppCompatActivity {

    // currency formatter
    private static final NumberFormat currencyFormat =
            NumberFormat.getCurrencyInstance();

    private Button newgameButton;
    private Button rulesButton;
    private TextView buyinAmountTextView;
    private EditText buyinAmount;
    double buyinMoney = 100;
    Intent newGameIntent;



    // called when the activity is first created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // call superclass's version
        setContentView(R.layout.activity_main); // inflate the GUI
        getSupportActionBar().setDisplayShowHomeEnabled(true); // display icon on actionbar
        getSupportActionBar().setIcon(R.mipmap.ic_launcher); // icon

        newgameButton = (Button) findViewById(R.id.newgameButton);
        rulesButton = (Button) findViewById(R.id.rulesButton) ;
        buyinAmount = (EditText) findViewById(R.id.buyinAmount);




        // get reference to the TextViews
        // that the craps game interacts with programmatically
        buyinAmountTextView =
                (TextView) findViewById(R.id.amount_display_textview);

        // update GUI based on buyinAmount
        buyinAmountTextView.setText(
                currencyFormat.format(buyinMoney));

        // set buyinAmount edit text's TextWatcher
        EditText buyinAmountEditText =
                (EditText) findViewById(R.id.buyinAmount);
        buyinAmountEditText.addTextChangedListener(buyinAmountEditTextWatcher);


    } // end method onCreate


    // event-handling object that responds to buyinAmountEditText's events
    private TextWatcher buyinAmountEditTextWatcher =
            new TextWatcher() {
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count){

                    // convert buyinAmountEditText's into a double
                    try{
                        buyinMoney = Double.parseDouble(s.toString()) / 100.0;
                    } // end try

                    catch (NumberFormatException e){

                        buyinMoney = 100; // default if an exception occurs
                    } // end catch

                    // display currency formatted bill amount
                    buyinAmountTextView.setText(currencyFormat.format(buyinMoney));

                } // end method onTextChanged

                @Override
                public void afterTextChanged(Editable s){
                } // end afterTextChanged

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after){
                } // end method beforeTextChanged

            };


    public double getBuyinAmount(){
        return buyinMoney;
    }


    public void rulesClick(){
        AlertDialog.Builder rules = new AlertDialog.Builder(MainActivity.this);
        rules.setCancelable(false);
        rules.setTitle("RULES");
        rules.setMessage("Here are the rules: "
                + "\n"
                + "\n1. BuyinAmount/Bank is set to 100"
                + "\n2. You can bet up to your bank"
                + "\n3. You win/lose whatever you bet"
                + "\n4. If you get 7 or 11 on your first roll, you win."
                + "\n5. House wins if your first roll is 2, 3, or 12."
                + "\n6. If you roll 4, 5, 6, 8, 9, or 10 the number"
                + "\n    will become the players point and the same"
                + "\n    number must be rolled again to win."
                + "\n    If you roll a 7, House wins.");
        rules.setNeutralButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alert = rules.create();
        alert.show();
    }

    public void rulesButton (View view){

        rulesClick();
    }

    public void newGameButton(View v) {

         newGameIntent = new Intent(MainActivity.this,Dice.class);

         startActivity(newGameIntent);
       //  recreate();

    }

}
