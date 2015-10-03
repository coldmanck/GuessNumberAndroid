package tw.edu.nctu.coldman519.guessnumberandroid;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
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

    private Button submitButton, clearButton, answerButton, sortButton;
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
        addListenerOnSortButton();

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

                int[] aAndB;
                final TextView resultTextView = (TextView) findViewById(R.id.result);

                // Check if input is correct or not
                boolean isInputRight = true;

                if(message.length() != digit)   // check if four digits
                    isInputRight = false;
                else{
                    for (int i = 0; i < digit; i++) {
                        int charNumber = (int) message.charAt(i);
                        if (charNumber < 48 || charNumber > 57) {   // check number 0~9
                            isInputRight = false;
                            break;
                        }
                        else {
                            for (int j = 0; j < i; j++) // ensure number not repeat
                                if (message.charAt(j) == message.charAt(i)) {
                                    isInputRight = false;
                                    break;
                                }
                        }
                    }
                }

                // If incorrect input, show error message
                if(!isInputRight){
                    new AlertDialog.Builder(tw.edu.nctu.coldman519.guessnumberandroid.MainActivity.this)
                            .setTitle("Error")
                            .setMessage("Input 4 not repeat number!")
                            .setPositiveButton(getString(R.string.okay), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // do nothing
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
                // If repeat answer then show error message
                else if(history.contains(message))
                    resultTextView.append("You have input " + message + " before!!\n");
                // Start processing
                else {
                    aAndB = guessGame.startGame(4, message);
                    history.add(message);
                    resultTextView.append("Your answer: " + message + " -> " + aAndB[0] + " A " + aAndB[1] + " B\n");

                    // if correct answer got
                    if(aAndB[0] == 4){
                        new AlertDialog.Builder(tw.edu.nctu.coldman519.guessnumberandroid.MainActivity.this)
                                .setTitle("Congratulation!")
                                .setMessage("Press to start a new game")
                                .setPositiveButton(getString(R.string.new_game), new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        guessGame.setNumber(digit);
                                        history.clear();
                                        resultTextView.setText("");
                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                    }
                }

                inputEditText.setText("");
            }
        });
    }

    // clear button
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

    // show-answer button
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

    // sort the result
    public void addListenerOnSortButton() {
        sortButton = (Button) findViewById(R.id.sort_button);
        sortButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                TextView resultTextView = (TextView) findViewById(R.id.result);
                resultTextView.setText("");

                ArrayList<Integer> intAnswer = new ArrayList<>();
                for(String i: history)
                    intAnswer.add(Integer.valueOf(i));
                intAnswer = sortAnswer(intAnswer);

                for(Integer i: intAnswer)
                    resultTextView.append(i + "\n");
            }
        });
    }

    // quick sort implementation
    public ArrayList<Integer> sortAnswer(ArrayList<Integer> intAnswer){
        if (intAnswer.size() < 2)
            return intAnswer;

        // random pivot
        //int pivot = list.get(random.nextInt(list.size() - 1));

        // middle pivot
        int pivot = intAnswer.get(intAnswer.size() / 2);
        intAnswer.remove(intAnswer.size() / 2);
        ArrayList<Integer> less = new ArrayList<Integer>();
        ArrayList<Integer> greater = new ArrayList<Integer>();
        ArrayList<Integer> result = new ArrayList<Integer>();
        for (Integer n : intAnswer)
        {
            if (n > pivot)
                greater.add(n);
            else
                less.add(n);
        }
        result.addAll(sortAnswer(less));
        result.add(pivot);
        result.addAll(sortAnswer(greater));
        return result;
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
