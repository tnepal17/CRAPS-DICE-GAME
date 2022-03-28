package com.example.craps;




import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Currency;
import java.util.Random;
import android.content.Intent;


public class Dice extends AppCompatActivity {


    // create random number generator for use in method rollDice
    private Random randomNumbers = new Random();


    private TextView winslossTextView;
    private TextView rollNumberTextView;
    private TextView resultsView;
    private TextView bankAmount;
    private EditText betAmount;
    private ImageView dieOneImage;
    private ImageView dieTwoImage;
    private Button homeButton;
    private Button rollButton;
    int dice1;
    int dice2;
    int sum;
    int bet;
    int bank=100;
    private int point = 0;
    private int wins;
    private int loss;
    int over = 20;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dice);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        resultsView = (TextView) findViewById(R.id.results);
        betAmount = (EditText) findViewById(R.id.betAmount);
        dieOneImage = (ImageView) findViewById(R.id.diceOneView);
        dieTwoImage = (ImageView) findViewById(R.id.diceTwoView);
        winslossTextView = (TextView) findViewById(R.id.winsloss);
        rollNumberTextView = (TextView) findViewById(R.id.rollNumber);
        rollButton = (Button) findViewById(R.id.rollDieButton);
        bankAmount = (TextView) findViewById(R.id.bankAmount);
       // homeButton = (Button) findViewById(R.id.homeButton);

        betAmount.addTextChangedListener(betAmountEditTextWatcher);

        bankAmount.setText(100+"");

        // Capture button clicks
        /*
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // start MainActivity
                Intent homePage = new Intent(Dice.this, MainActivity.class);
                startActivity(homePage);
            }
        });*/


    }




    public void displayBank()
    {
        bankAmount.setText(bank + "");
    }

    public void craps() {

        int sumOfDice = rollDice();

        if (over == 30) {
            over = 40;
        }
        if (over == 20) {

            switch (sumOfDice) {
                case 7:
                case 11:
                    resultsView.setText("YOU WON!");
                    rollButton.setEnabled(false);
                    wins++;
                    bank+=bet;
                    displayBank();
                    displayWins();
                    break;
                case 2: // lose with 2 on first roll
                case 3: // lose with 3 on first roll
                case 12: // lose with 12 on first roll
                    resultsView.setText("Sorry, you lose!");
                    rollButton.setEnabled(false);
                    loss++;
                    bank-=bet;
                    displayBank();
                    displayWins();
                    break;
                default: // did not win or lose, so remember point
                    point = sumOfDice; // remember the point
                    over = 30;
                    resultsView.setText("You rolled " + point + ".\n" + "You must roll " + point
                            + " to win." + "\n" + "If you roll a 7 you lose." + "\n" + "Roll Again!");
                    displayWins();
                    break;

            }

        }
        if (over == 40){

            if (point == sumOfDice){
                resultsView.setText("YOU WON!");
                rollButton.setEnabled(false);
                over = 20;
                wins++;
                bank+=bet;
                displayBank();
                displayWins();
            }
            if (sumOfDice == 7){
                resultsView.setText("Sorry, you lose!");
                rollButton.setEnabled(false);
                over = 20;
                loss++;
                bank-=bet;
                displayBank();
                displayWins();
            }
            if (sumOfDice != point && sumOfDice != 7){
                //over =30;
                resultsView.setText("You rolled " + point + ".\n" + "You must roll " + point
                        + " to win." + "\n" + "If you roll a 7 you lose." + "\n" + "Roll Again!");
                displayWins();
            }
        }
    }




    public int rollDice() {

        dice1 = 1 + randomNumbers.nextInt(6);

        dice2 = 1 + randomNumbers.nextInt(6);

        int sum = dice2 + dice1;

        rollNumberTextView.setText(String.valueOf("Roll Number: " + dice1 +
                " + " + dice2 + " = " + sum));


        switch (dice1){
            case 1:
                dieOneImage.setImageResource(R.drawable.die1);
                break;
            case 2:
                dieOneImage.setImageResource(R.drawable.die2);
                break;
            case 3:
                dieOneImage.setImageResource(R.drawable.die3);
                break;
            case 4:
                dieOneImage.setImageResource(R.drawable.die4);
                break;
            case 5:
                dieOneImage.setImageResource(R.drawable.die5);
                break;
            case 6:
                dieOneImage.setImageResource(R.drawable.die6);
                break;

        }switch (dice2){
            case 1:
                dieTwoImage.setImageResource(R.drawable.die1);
                break;
            case 2:
                dieTwoImage.setImageResource(R.drawable.die2);
                break;
            case 3:
                dieTwoImage.setImageResource(R.drawable.die3);
                break;
            case 4:
                dieTwoImage.setImageResource(R.drawable.die4);
                break;
            case 5:
                dieTwoImage.setImageResource(R.drawable.die5);
                break;
            case 6:
                dieTwoImage.setImageResource(R.drawable.die6);
                break;

        }


        return dice1 + dice2;
    }


    // will start the craps game when the button is rolled
    public void rollButton(View view) {

        craps();
        checkBank();
    }

    // displays the player wins and house wins
    private void displayWins(){
        winslossTextView.setText("Player Wins: " + wins + "   House Wins: " + loss);

    }

    // newGame clears the screen of everything but the Wins/Loss score and the bankAmount
    public void newGame (View view){

        rollButton.setEnabled(true);

        rollNumberTextView.setText("Roll Number: ");
        resultsView.setText(String.valueOf("Results: "));

        dice1 = 0;

        dice2 = 0;

        sum = 0;

        point = 0;

    }


    TextWatcher betAmountEditTextWatcher =
            new TextWatcher() {
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count){

                    // convert buyinAmountEditText's into a double
                    try{
                        bet = Integer.parseInt(s.toString());
                    } // end try

                    catch (NumberFormatException e){

                        bet = 10; // default if an exception occurs
                    } // end catch



                } // end method onTextChanged

                @Override
                public void afterTextChanged(Editable s){
                } // end afterTextChanged

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after){
                } // end method beforeTextChanged

            };


    public void checkBank()
    {

        int bankX = Integer.parseInt(bankAmount.getText().toString());

        if(bankX<bet)
        {

            Toast.makeText(Dice.this, "You don't have enough Money.... play for free(Parents credit card number accepted)", Toast.LENGTH_LONG).show();
            bet=0;
            bank=0;
            displayBank();
            betAmount.setText(0+"");
        }

    }



}
