package tw.edu.nctu.coldman519.guessnumberandroid;

import android.widget.TextView;

import java.util.Random;

/**
 * Created by DueMBA on 2015/10/3.
 */
public class GuessGame {
    private int[] answer;
    private int a, b, digit;

    public void setNumber(int d){

        digit = d;
        answer = new int[digit];

        Random rand = new Random();
        for(int i = 0; i < digit; i++) {
            while(true) {
                boolean isSpecial = true;
                int temp = rand.nextInt(10);

                for (int j = 0; j < i; j++)
                    if (temp == answer[j]) {
                        isSpecial = false;
                        break;
                    }

                if(isSpecial) {
                    answer[i] = temp;
                    break;
                }
            }
        }
    }

    public int[] startGame(int d, String guess){


        int[] guessNumber = getUserInput(guess);
        int[] returnValue = new int[2];

        for(int i = 0; i < digit; i++){
            if(answer[i] == guessNumber[i])
                a++;
            else
                for(int j = 0; j < digit; j++){
                    if(answer[i] == guessNumber[j])
                        b++;
                }
        }

        returnValue[0] = a;
        returnValue[1] = b;
        a = 0;
        b = 0;
        return returnValue;

    }

    public int[] getUserInput(String guess){
        int[] guess_temp = new int[digit];
        for(int i = 0; i < digit; i++)
            guess_temp[i] = Integer.valueOf(guess.substring(i, i+1));

        return guess_temp;
    }

    public int[] getAnswer(){
        return answer;
    }
}
