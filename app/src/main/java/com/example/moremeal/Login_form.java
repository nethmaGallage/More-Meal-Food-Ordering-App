package com.example.moremeal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;



import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

public class Login_form extends AppCompatActivity  {

    EditText username;
    EditText password;
    Button register;
    Button login;


    String name = "Notification_channel";
    String Channel_ID = "ID_1";
    String description = "Sample Notification";
    EditText editTextName;



    private FirebaseAuth mAuth;
    private static final String TAG = "EmailPassword";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_form);

        mAuth = FirebaseAuth.getInstance();


        Intent register = getIntent(); // redirect after register form
        Intent intent1 = getIntent() ; // redirect after main-activity2
        Intent intent = getIntent();//redirect after main-activity3
        Intent main4tologin = getIntent();//from main-activity4

        setupUI();
        setupListeners();




        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel channel = new NotificationChannel(Channel_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);


            //Creating the LayoutInflater instance
            LayoutInflater li = getLayoutInflater();
            //Getting the View object as defined in the custom toast.xml file
            View layout = li.inflate(R.layout.customtoast, (ViewGroup)
                    findViewById(R.id.custom_toast_layout));


            //Creating the Toast object
            Toast toast = new Toast(getApplicationContext());
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
            toast.setView(layout);
            //setting the view of custom toast layout
            toast.show();

        }



    } //end of on-create





    private void setupListeners() {

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkUsername();

                // Check if user is signed in (non-null) and update UI accordingly.
                FirebaseUser currentUser = mAuth.getCurrentUser();
                if(currentUser != null){
                    reload();

                    //Intent create to redirect to Main-Activity 3
                    Intent login = new Intent(Login_form.this,Success.class);
                    startActivity(login);
                }


                Intent intent = new Intent(getApplicationContext(),Signup_form.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
                PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);
                NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), Channel_ID).setSmallIcon(R.drawable.ic_launcher_background).setContentTitle("My notification").setContentText("Hello World!").setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setContentIntent(pendingIntent).setAutoCancel(true);
                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());
                notificationManager.notify(0, builder.build());

                Context context = getApplicationContext();
                //The context to use. Usually your Application or Activity object
                CharSequence message = "You just clicked the OK button";
                //Display string  int
                int duration = Toast.LENGTH_SHORT;
                //How long the toast message will lasts
                Toast toast = Toast.makeText(context, message, duration);
                toast.show();
                toast.setGravity(Gravity.TOP|Gravity.LEFT, 0, 0);

            }
        });

        //  Intent create to redirect to Sig-nup form when click Register button
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login_form.this,Signup_form.class);
                startActivity(i);

            }
        });
    }


    // [END on_start_check_user]

    private void reload() {

    }

    private void updateUI(FirebaseUser user) {

    }

    private void checkUsername() {
        boolean isValid = true;

        if(isEmpty(username)){

            username.setError("You must enter username to login ! ");
            isValid = false;
        }
        else {

            if (!isEmail(username)) {

                username.setError("Enter Valid email ! ");
                isValid = false;
            }
        }

        if(isEmpty(password)){

            password.setError("password is incorrect");
            isValid = false;
        }

        else{

            if(password.getText().toString().length() < 4){
                password.setError("password must be at least 4 characters long ");
                isValid = false ;
            }
        }

        if(isValid){
            String emailV = username.getText().toString();
            String passwordV = password.getText().toString();

            // [START sign_in_with_email]
            mAuth.signInWithEmailAndPassword(emailV, passwordV)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                updateUI(user);
                                setupListeners();


                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                                Toast.makeText(Login_form.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                updateUI(null);
                            }
                        }
                    });
            // [END sign_in_with_email]

        }

        else{

            Toast t = Toast.makeText(this,"Wrong email or password.please try again " ,Toast.LENGTH_SHORT);
            t.show();

        }
    }



    private boolean isEmail(EditText text) {

        CharSequence email = text.getText().toString();
        return  (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    private boolean isEmpty(EditText text) {

        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }


    private void setupUI() {

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        register = findViewById(R.id.register);
        login = findViewById(R.id.login);

    }


}