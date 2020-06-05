package sg.edu.np.week_6_whackamole_3_0;

import androidx.appcompat.app.AppCompatActivity;

import android.accounts.Account;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class Main4Activity extends AppCompatActivity implements View.OnClickListener {

    /* Hint:
        1. This creates the Whack-A-Mole layout and starts a countdown to ready the user
        2. The game difficulty is based on the selected level
        3. The levels are with the following difficulties.
            a. Level 1 will have a new mole at each 10000ms.
                - i.e. level 1 - 10000ms
                       level 2 - 9000ms
                       level 3 - 8000ms
                       ...
                       level 10 - 1000ms
            b. Each level up will shorten the time to next mole by 100ms with level 10 as 1000 second per mole.
            c. For level 1 ~ 5, there is only 1 mole.
            d. For level 6 ~ 10, there are 2 moles.
            e. Each location of the mole is randomised.
        4. There is an option return to the login page.
     */
    private static final String FILENAME = "Main4Activity.java";
    private static final String TAG = "Whack-A-Mole3.0!";
    CountDownTimer readyTimer;
    CountDownTimer newMolePlaceTimer;
    final int[] score = {0};
    int timeInSeconds;
    TextView basicScoreTextView;
    MyDBHandler handler;
    String levelSelected;
    String username;
    UserData user;

    private void readyTimer(final int aTimeInSeconds){
        /*  HINT:
            The "Get Ready" Timer.
            Log.v(TAG, "Ready CountDown!" + millisUntilFinished/ 1000);
            Toast message -"Get Ready In X seconds"
            Log.v(TAG, "Ready CountDown Complete!");
            Toast message - "GO!"
            belongs here.
            This timer countdown from 10 seconds to 0 seconds and stops after "GO!" is shown.
         */
        readyTimer = new CountDownTimer( 10000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Log.v(TAG, FILENAME + ": Ready CountDown!" + millisUntilFinished / 1000 + " seconds");
                String toastMsg = "Get Ready In " + millisUntilFinished / 1000 + " seconds";
                makeToast(toastMsg);
            }

            @Override
            public void onFinish() {
                Log.v(TAG, FILENAME + ": Ready CountDown Complete!");
                makeToast("GO!");
                placeMoleTimer(aTimeInSeconds);
                readyTimer.cancel();
            }
        };
        readyTimer.start();
    }
    private void placeMoleTimer(final int aTimeInSecs){
        /* HINT:
           Creates new mole location each second.
           Log.v(TAG, "New Mole Location!");
           setNewMole();
           belongs here.
           This is an infinite countdown timer.
         */
        newMolePlaceTimer = new CountDownTimer(aTimeInSecs * 1000, aTimeInSecs * 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (aTimeInSecs <= 5) {
                    setTwoMoles();
                }
                else {
                    setNewMole();
                }
            }

            @Override
            public void onFinish() {
                newMolePlaceTimer.start();
            }
        };
        newMolePlaceTimer.start();
    }
    private static final int[] BUTTON_IDS = {
            /* HINT:
                Stores the 9 buttons IDs here for those who wishes to use array to create all 9 buttons.
                You may use if you wish to change or remove to suit your codes.*/
            R.id.topLeftBtn, R.id.topMidBtn, R.id.topRightBtn, R.id.midLeftBtn, R.id.midBtn, R.id.midRightBtn,
            R.id.bottomLeftBtn, R.id.bottomMidBtn, R.id.bottomRightBtn
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        /*Hint:
            This starts the countdown timers one at a time and prepares the user.
            This also prepares level difficulty.
            It also prepares the button listeners to each button.
            You may wish to use the for loop to populate all 9 buttons with listeners.
            It also prepares the back button and updates the user score to the database
            if the back button is selected.
         */
        Intent receivingEnd = getIntent();
        levelSelected = receivingEnd.getStringExtra("Level");
        username = receivingEnd.getStringExtra("Username");

        timeInSeconds = determineTime(levelSelected);
        if (timeInSeconds <= 5) {
            setTwoMoles();
        }
        else {
            setNewMole();
        }
        basicScoreTextView = findViewById(R.id.basicScoreTextView);
        basicScoreTextView.setText(score[0] + "");
        readyTimer(timeInSeconds);
        handler = new MyDBHandler(this, "WhackAMole.db", null, 1);
        user = handler.findUser(username);


        /*for(final int id : BUTTON_IDS){
            *//*  HINT:
            This creates a for loop to populate all 9 buttons with listeners.
            You may use if you wish to remove or change to suit your codes.
            *//*
        }*/
    }
    @Override
    protected void onStart(){
        super.onStart();
    }

    public int determineTime(String aLevelSelected) {
        int time;
        if (aLevelSelected.equals("1")) {
            time = 10;
        }
        else if (aLevelSelected.equals("2")) {
            time = 9;
        }
        else if (aLevelSelected.equals("3")) {
            time = 8;
        }
        else if (aLevelSelected.equals("4")) {
            time = 7;
        }
        else if (aLevelSelected.equals("5")) {
            time = 6;
        }
        else if (aLevelSelected.equals("6")) {
            time = 5;
        }
        else if (aLevelSelected.equals("7")) {
            time = 4;
        }
        else if (aLevelSelected.equals("8")) {
            time = 3;
        }
        else if (aLevelSelected.equals("9")) {
            time = 2;
        }
        else {
            time = 1;
        }

        return time;
    }

    public int determineLevel(String aLevelSelected) {
        int level;
        if (aLevelSelected.equals("1")) {
            level = 1;
        }
        else if (aLevelSelected.equals("2")) {
            level = 2;
        }
        else if (aLevelSelected.equals("3")) {
            level = 3;
        }
        else if (aLevelSelected.equals("4")) {
            level = 4;
        }
        else if (aLevelSelected.equals("5")) {
            level = 5;
        }
        else if (aLevelSelected.equals("6")) {
            level = 6;
        }
        else if (aLevelSelected.equals("7")) {
            level = 7;
        }
        else if (aLevelSelected.equals("8")) {
            level = 8;
        }
        else if (aLevelSelected.equals("9")) {
            level = 9;
        }
        else {
            level = 10;
        }
        return level;
    }

    private void doCheck(Button checkButton)
    {
        /* Hint:
            Checks for hit or miss
            Log.v(TAG, FILENAME + ": Hit, score added!");
            Log.v(TAG, FILENAME + ": Missed, point deducted!");
            belongs here.
        */
        if(checkButton.getText().equals("*")) {
            score[0]++;
            Log.v(TAG, FILENAME + ": Hit, score added!");
        }
        else {
            score[0]--;
            Log.v(TAG, FILENAME + ": Missed, point deducted!");
        }
        if (determineLevel(levelSelected) >= 6) {
            setTwoMoles();
        }
        else {
            setNewMole();
        }

        basicScoreTextView.setText(score[0] + "");
    }

    public int setNewMole()
    {
        /* Hint:
            Clears the previous mole location and gets a new random location of the next mole location.
            Sets the new location of the mole. Adds additional mole if the level difficulty is from 6 to 10.
         */
        Random ran = new Random();
        int randomLocation = ran.nextInt(9);
        for (int i : BUTTON_IDS) {
            Button btn = (Button) findViewById(i);
            btn.setText("O");
        }
        Button moleBtn = (Button) findViewById(BUTTON_IDS[randomLocation]);
        moleBtn.setText("*");
        return randomLocation;
    }

    public void setTwoMoles() {
        int firstLocation = setNewMole();
        hardSetNewMole(firstLocation);
    }

    public void hardSetNewMole(int firstLocation) {
        Random ran = new Random();
        int secondRanLocation = ran.nextInt(9);
        if (secondRanLocation == firstLocation) {
            hardSetNewMole(firstLocation);
        }
        else {
            Button secondMoleBtn = (Button) findViewById(BUTTON_IDS[secondRanLocation]);
            secondMoleBtn.setText("*");
        }
        Log.v(TAG, FILENAME + ": New Mole Location!");
    }

    private void updateUserScore(CountDownTimer newMolePlaceTimer, CountDownTimer readyTimer)
    {
        Log.v(TAG, FILENAME + ": Update User Score...");

     /* Hint:
        This updates the user score to the database if needed. Also stops the timers.
        Log.v(TAG, FILENAME + ": Update User Score...");
      */
        newMolePlaceTimer.cancel();
        readyTimer.cancel();
        int finalScore = Integer.parseInt(basicScoreTextView.getText().toString());
        if (finalScore > user.getScores().get(Integer.parseInt(levelSelected) - 1)) {
            ArrayList<Integer> scoreList = user.getScores();
            scoreList.set(Integer.parseInt(levelSelected) - 1, finalScore);
            user.setScores(scoreList);
        }
        saveScore(user);

    }

    public void makeToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        for (int i : BUTTON_IDS) {
            if (v.getId() == i) {
                doCheck((Button) findViewById(i));
            }
        }
    }

    public void onClick2(View view) {
        updateUserScore(newMolePlaceTimer, readyTimer);

        finish();
    }

    public void saveScore(UserData aUser) {
        if (aUser != null) {
            boolean result = handler.deleteAccount(aUser.getMyUserName());
        }
        handler.addUser(aUser);
    }
}
