package com.merp.chillhai;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {
    private FirebaseDatabase database;
    private DatabaseReference bStatus1;
    private DatabaseReference bStatus2;
    private DatabaseReference bStatus3;
    private DatabaseReference bStatus4;
    private DatabaseReference bStatus5;
    private DatabaseReference bStatus6;
    private DatabaseReference bStatus7;
    private DatabaseReference bStatus8;
    private DatabaseReference bStatus9;
    private DatabaseReference chancesLeftRef;
    private DatabaseReference whichPlayersTurnRef;
    private static final String TAG="premDebug";
    private FirebaseAuth FAuth;

    EditText email,pass;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        FAuth=FirebaseAuth.getInstance();
        email=findViewById(R.id.editTextTextEmailAddressLogin);
        pass=findViewById(R.id.editTextTextPasswordLogin);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = FAuth.getCurrentUser();
        if(user!=null){
            Log.i(TAG, "No login req ,we remeber you ");
            resetDb();
            startActivity(new Intent(LoginActivity.this,OnlineGameActivity.class));
        }

    }

    public void onlineLoginButtonClicked(View view) {
        FAuth.signInWithEmailAndPassword(email.getText().toString(),pass.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(),"Login Sucess! :)",Toast.LENGTH_LONG).show();
                            Log.i(TAG, "onComplete: login sucessful");
                            resetDb();
                            startActivity(new Intent(LoginActivity.this,OnlineGameActivity.class));
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"Login Failed :(",Toast.LENGTH_LONG).show();
                            Log.i(TAG, "onComplete: login failed ",task.getException());

                        }
                    }
                });
    }
    void resetDb(){
        database=FirebaseDatabase.getInstance();
        bStatus1 = database.getReference("/boxStatus/1");
        bStatus2 = database.getReference("/boxStatus/2");
        bStatus3 = database.getReference("/boxStatus/3");
        bStatus4 = database.getReference("/boxStatus/4");
        bStatus5 = database.getReference("/boxStatus/5");
        bStatus6 = database.getReference("/boxStatus/6");
        bStatus7 = database.getReference("/boxStatus/7");
        bStatus8 = database.getReference("/boxStatus/8");
        bStatus9 = database.getReference("/boxStatus/9");
        whichPlayersTurnRef=database.getReference("/whichPlayersTurn");
        chancesLeftRef=database.getReference("/chancesLeft");
        bStatus1.setValue(-1);
        bStatus2.setValue(-1);
        bStatus3.setValue(-1);
        bStatus4.setValue(-1);
        bStatus5.setValue(-1);
        bStatus6.setValue(-1);
        bStatus7.setValue(-1);
        bStatus8.setValue(-1);
        bStatus9.setValue(-1);
        whichPlayersTurnRef.setValue(1);
        chancesLeftRef.setValue(9);
    }

}
