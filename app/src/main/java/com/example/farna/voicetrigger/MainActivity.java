package com.example.farna.voicetrigger;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;



public class MainActivity extends AppCompatActivity {
    int i;
    Button mButton;
    Button sButton;
    boolean trigger = false;

    boolean calculated = false;
    String s = "";
    String sendGPAemail, result, comments, header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        promptSpeechInput();

        sButton = (Button) findViewById(R.id.doggo);

        sButton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                      //  promptSpeechInput();
                       // sendEmail();
                        if(trigger) {
                            sendEmail();
                        }
                        else{promptSpeechInput();//System.out.println("hi nothing to send");}
                    }
                }

        );
    }


    protected void sendEmail() {
        Log.i("Send email", "");

        String[] TO = {"farabi@uci.edu"};
        // String[] CC = {"farabi@uci.edu"};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");


        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        //emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your GPA Goal");

        //Email String content
        result = "SOS";

        emailIntent.putExtra(Intent.EXTRA_TEXT, result);

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
            Log.i("Finished sending ...", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(MainActivity.this,
                    "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }

    /*****************************************************Voice Recognition*************************************************************************/


    public void promptSpeechInput(){
        Intent i = new Intent(RecognizerIntent. ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        i.putExtra(RecognizerIntent.EXTRA_PROMPT, "SAY SOMETHING");

        try{
            startActivityForResult(i, 100);
        }
        catch(ActivityNotFoundException a) {
            Toast.makeText(MainActivity.this, "Sorry!", Toast.LENGTH_LONG).show();
        }

    }

    public void onActivityResult(int request_code, int result_code, Intent i) {
        super.onActivityResult(request_code , result_code , i);
        switch (request_code) {

            case 100:
                if (result_code == RESULT_OK && i != null) {
                    ArrayList<String> result = i.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    //resultTEXT.setText(result.get(0));
                    if(result.get(0).equals("SOS")){
                        trigger = true;
                        System.out.println("hi result"+result.get(0));
                    }
                    else{
                    System.out.println("hi result in else"+result.get(0));
                     }
                }
                break;
        }
    }

}