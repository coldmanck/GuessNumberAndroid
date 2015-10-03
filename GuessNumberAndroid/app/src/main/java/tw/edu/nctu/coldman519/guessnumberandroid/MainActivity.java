package tw.edu.nctu.coldman519.guessnumberandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button submitButton, clearButton, answerButton;
    private GuessGame guessGame = new GuessGame();
    private ArrayList<String> history = new ArrayList<>();
    private final int digit = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        guessGame.setNumber(digit);

        addListenerOnSubmitButton();
        addListenerOnClearButton();
        addListenerOnAnswerButton();

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    public void addListenerOnSubmitButton() {
        submitButton = (Button) findViewById(R.id.submit_button);
        submitButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                EditText inputEditText = (EditText) findViewById(R.id.edit_text);
                String message = inputEditText.getText().toString();

                TextView resultTextView = (TextView) findViewById(R.id.result);

                if(history.contains(message))
                    resultTextView.append("You have input " + message + " before!!\n");
                else {
                    int[] aAndB = guessGame.startGame(4, message);
                    history.add(message);
                    resultTextView.append(aAndB[0] + " A " + aAndB[1] + " B\n");
                    if(aAndB[0] == 4)
                        
                }
            }
        });
    }

    public void addListenerOnClearButton() {
        clearButton = (Button) findViewById(R.id.clear_button);
        clearButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                TextView resultTextView = (TextView) findViewById(R.id.result);
                resultTextView.setText("");
            }
        });
    }

    public void addListenerOnAnswerButton() {
        answerButton = (Button) findViewById(R.id.answer_button);
        answerButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                TextView resultTextView = (TextView) findViewById(R.id.result);
                resultTextView.append("Answer: " + Arrays.toString(guessGame.getAnswer()) + "\n");
            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
