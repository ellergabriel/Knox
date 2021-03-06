package com.example.knox.activities;

import android.app.Dialog;
import android.content.Intent;
import androidx.biometric.BiometricPrompt;
import android.os.Bundle;
import com.example.knox.R;
import com.example.knox.databinding.ActivityMainBinding;
import com.example.knox.systemComponents.Database;
import com.example.knox.systemComponents.Requestor;
import com.example.knox.systemComponents.Validator;

import androidx.appcompat.app.AppCompatActivity;
import android.provider.Settings;
import android.view.Window;
import android.view.autofill.AutofillManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.Executor;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;
    private boolean isExit = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //check for autofill enabled on startup
        AutofillManager manager = getSystemService(AutofillManager.class);
        if (!manager.hasEnabledAutofillServices()) {
            //prompts user to enable autofill from settings
            final Dialog dialog = new Dialog(MainActivity.this);
            //We have added a title in the custom layout. So let's disable the default title.
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            //The user will be able to cancel the dialog bu clicking anywhere outside the dialog.
            dialog.setCancelable(true);
            //Mention the name of the layout of your custom dialog.
            dialog.setContentView(R.layout.settings_prompt);
            Button confirm = dialog.findViewById(R.id.settings_button);
            TextView prompt = dialog.findViewById(R.id.settings_text);
            //device does not support autofill; edge case to fill out later
            if (!manager.isAutofillSupported()) {
                prompt.setText("Autofill is not supported on this device, " +
                            "exiting...");
                System.exit(0);
            } else {
                Intent settings = new Intent(Settings.ACTION_SETTINGS);
                confirm.setOnClickListener(view -> {
                    dialog.dismiss();
                    startActivity(settings);
                    System.exit(0);
                });
                dialog.show();
            }
        } else {

            /**
             * Database needs to be instantiated on startup, otherwise null pointer is thrown
             * getInstance() instantiates both the database and passwordDAO
             */
            Database.getInstance(getApplicationContext());

            Button biometricLogin = findViewById(R.id.fp_button);

           /*
            executor = ContextCompat.getMainExecutor(this);

            biometricPrompt = new BiometricPrompt(MainActivity.this,
                              executor, new BiometricPrompt.AuthenticationCallback() {
                @Override
                public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                    super.onAuthenticationError(errorCode, errString);
                            if (("Fingerprint operation canceled by user.").equals(errString)) {
                                System.out.println("Back button tester\n");
                            } else {

                                Toast.makeText(getApplicationContext(),
                                        "Authentication error: " + errString, Toast.LENGTH_SHORT)
                                        .show();
    //                            System.exit(0);
                            }
                }

                @Override
                public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                    super.onAuthenticationSucceeded(result);
                    Toast.makeText(getApplicationContext(),
                            "Authentication succeeded, welcome back", Toast.LENGTH_SHORT).show();
                    vaultMode();
                }

                @Override
                public void onAuthenticationFailed() {
                    super.onAuthenticationFailed();
                    Toast.makeText(getApplicationContext(), "Authentication failed",
                            Toast.LENGTH_SHORT).show();
                }
            });

            promptInfo = new BiometricPrompt.PromptInfo.Builder()
                    .setTitle("Biometric login")
                    .setSubtitle("Log in with fingerprint")
                    .setNegativeButtonText("Exit Knox")
                    .build();

            biometricPrompt.authenticate(promptInfo); //prompt shows up on start up
            */
            if (!Validator.isSessionValid()) {
                Validator.getInstance().createPrompt(this, false);
            }
            //Button biometricLogin = findViewById(R.id.fp_button);
            biometricLogin.setOnClickListener(view -> {
                if (Validator.isSessionValid()) { //checks for 60 second login window
                    vaultMode();
                } else {
                    if (!Validator.isSessionValid()) {
                        Validator.getInstance().createPrompt(this, false);
                    }
                }
            });


            //create requestor object via singleton creation
            Requestor.getInstance();

            /**
             uncomment to clear DB for testing
             **/
            //Database.getInstance(getApplicationContext()).deleteAll();

            //uncomment for access to debugger

            //Button debug = findViewById(R.id.debug_button);
            //debug.setOnClickListener(view -> debugMode());

        }
    }

    public void debugMode(){
        Intent intent = new Intent(this, DebugActivity.class);
        startActivity(intent);
    }

    public void vaultMode(){
        Intent intent = new Intent(this, VaultActivity.class);
        startActivity(intent);
    }

}
