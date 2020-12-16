package com.merp.chillhai;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class OnlineGameActivity extends AppCompatActivity {

    private static final String TAG = "premDebug";

    int[] boxStatusLocal = {-1, -1, -1, -1, -1, -1, -1, -1, -1};
    int gameWinner = -1;
    int chances=9;
    int thisPlayersTurn,playerId,indexOfBoxClicked;
    boolean hasAWinner = false;
    boolean playerOnesTurn = true;
    TextView statusText;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    ImageView i1;
    ImageView i2;
    ImageView i3;
    ImageView i4;
    ImageView i5;
    ImageView i6;
    ImageView i7;
    ImageView i8;
    ImageView i9;
    private Context mContext;
    private DatabaseReference boxStatusRef;
    int clickedBoxStatus;
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


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Toast.makeText(getApplicationContext(),"Back pressed ",Toast.LENGTH_LONG).show();
        startActivity(new Intent(OnlineGameActivity.this,MainMenuActivity.class));
        finish();
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.online_game_activity);
        i1 = findViewById(R.id.imageView1Online);
        i2 = findViewById(R.id.imageView2Online);
        i3 = findViewById(R.id.imageView3Online);
        i4 = findViewById(R.id.imageView4Online);
        i5 = findViewById(R.id.imageView5Online);
        i6 = findViewById(R.id.imageView6Online);
        i7 = findViewById(R.id.imageView7Online);
        i8 = findViewById(R.id.imageView8Online);
        i9 = findViewById(R.id.imageView9Online);
        mAuth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        statusText=findViewById(R.id.statusTextViewOnline);
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user=mAuth.getCurrentUser();
        if(user!=null){
            if (user.getEmail().equals("player1@test.com")) {
                playerId = 1;
                Log.i(TAG, "onStart: playerId set to 1");
            } else {
                playerId = 2;
                Log.i(TAG, "onStart: playerId set to 2");
            }
        }

        whichPlayersTurnRef = database.getReference("/whichPlayersTurn");
        whichPlayersTurnRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                thisPlayersTurn=Integer.parseInt(snapshot.getValue().toString());
                if(thisPlayersTurn==1){playerOnesTurn=true;}else{playerOnesTurn=false;} //temp edit
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        chancesLeftRef=database.getReference("/chancesLeft");
        chancesLeftRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chances=Integer.parseInt(snapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        bStatus1 = database.getReference("/boxStatus/1");
        bStatus2 = database.getReference("/boxStatus/2");
        bStatus3 = database.getReference("/boxStatus/3");
        bStatus4 = database.getReference("/boxStatus/4");
        bStatus5 = database.getReference("/boxStatus/5");
        bStatus6 = database.getReference("/boxStatus/6");
        bStatus7 = database.getReference("/boxStatus/7");
        bStatus8 = database.getReference("/boxStatus/8");
        bStatus9 = database.getReference("/boxStatus/9");

        bStatus1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                int x = Integer.parseInt(snapshot.getValue().toString());
                boxStatusLocal[0] = x;
                if (x == 1) {
                    Log.i(TAG, "onDataChange: changed box boxStatusLocal1 to 1");
                    Log.i(TAG, "onDataChange: change image to  x");
                    i1.setBackgroundResource(R.drawable.x_small);
                } else if (x == 2) {
                    Log.i(TAG, "onDataChange: changed box boxStatusLocal1 to 2");
                    Log.i(TAG, "onDataChange: change image to O");
                    i1.setBackgroundResource(R.drawable.o_small);
                } else if (x == -1) {
                    i1.setBackgroundResource(android.R.color.transparent);
                }
                if (checkWin(1, playerOnesTurn)) {
                    Log.i(TAG, "onDataChange: checkWin called with playerOnesTurn=" + playerOnesTurn + " and local box stattus is ");
                    endGame();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        bStatus2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int x = Integer.parseInt(snapshot.getValue().toString());
                boxStatusLocal[1] = x;
                if (x == 1) {
                    Log.i(TAG, "onDataChange: changed box boxStatusLocal2 to 1");
                    Log.i(TAG, "onDataChange: change image to  x");
                    i2.setBackgroundResource(R.drawable.x_small);
                } else if (x == 2) {
                    Log.i(TAG, "onDataChange: changed box boxStatusLocal2 to 2");
                    Log.i(TAG, "onDataChange: change image to O");
                    i2.setBackgroundResource(R.drawable.o_small);
                } else if (x == -1) {
                    i2.setBackgroundResource(android.R.color.transparent);
                }
                if (checkWin(2, playerOnesTurn)) {
                    endGame();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        bStatus3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int x = Integer.parseInt(snapshot.getValue().toString());
                boxStatusLocal[2] = x;
                if (x == 1) {
                    Log.i(TAG, "onDataChange: change image to  x");
                    i3.setBackgroundResource(R.drawable.x_small);
                } else if (x == 2) {
                    Log.i(TAG, "onDataChange: change image to O");
                    i3.setBackgroundResource(R.drawable.o_small);
                } else if (x == -1) {
                    i3.setBackgroundResource(android.R.color.transparent);
                }
                if (checkWin(3, playerOnesTurn)) {
                    endGame();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        bStatus4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int x = Integer.parseInt(snapshot.getValue().toString());
                boxStatusLocal[3] = x;
                if (x == 1) {
                    Log.i(TAG, "onDataChange: change image to  x");
                    i4.setBackgroundResource(R.drawable.x_small);
                } else if (x == 2) {
                    Log.i(TAG, "onDataChange: change image to O");
                    i4.setBackgroundResource(R.drawable.o_small);
                } else if (x == -1) {
                    i4.setBackgroundResource(android.R.color.transparent);
                }
                if (checkWin(4, playerOnesTurn)) {
                    endGame();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        bStatus5.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int x = Integer.parseInt(snapshot.getValue().toString());
                boxStatusLocal[4] = x;
                if (x == 1) {
                    Log.i(TAG, "onDataChange: change image to  x");
                    i5.setBackgroundResource(R.drawable.x_small);
                } else if (x == 2) {
                    Log.i(TAG, "onDataChange: change image to O");
                    i5.setBackgroundResource(R.drawable.o_small);
                } else if (x == -1) {
                    i5.setBackgroundResource(android.R.color.transparent);
                }
                if (checkWin(5, playerOnesTurn)) {
                    endGame();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        bStatus6.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int x = Integer.parseInt(snapshot.getValue().toString());
                boxStatusLocal[5] = x;
                if (x == 1) {
                    Log.i(TAG, "onDataChange: change image to  x");
                    i6.setBackgroundResource(R.drawable.x_small);
                } else if (x == 2) {
                    Log.i(TAG, "onDataChange: change image to O");
                    i6.setBackgroundResource(R.drawable.o_small);
                } else if (x == -1) {
                    i6.setBackgroundResource(android.R.color.transparent);
                }
                if (checkWin(6, playerOnesTurn)) {
                    endGame();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        bStatus7.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int x = Integer.parseInt(snapshot.getValue().toString());
                boxStatusLocal[6] = x;
                if (x == 1) {
                    Log.i(TAG, "onDataChange: change image to  x");
                    i7.setBackgroundResource(R.drawable.x_small);
                } else if (x == 2) {
                    Log.i(TAG, "onDataChange: change image to O");
                    i7.setBackgroundResource(R.drawable.o_small);
                } else if (x == -1) {
                    i7.setBackgroundResource(android.R.color.transparent);
                }
                if (checkWin(7, playerOnesTurn)) {
                    endGame();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        bStatus8.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int x = Integer.parseInt(snapshot.getValue().toString());
                boxStatusLocal[7] = x;
                if (x == 1) {
                    Log.i(TAG, "onDataChange: change image to  x");
                    i8.setBackgroundResource(R.drawable.x_small);
                } else if (x == 2) {
                    Log.i(TAG, "onDataChange: change image to O");
                    i8.setBackgroundResource(R.drawable.o_small);
                } else if (x == -1) {
                    i8.setBackgroundResource(android.R.color.transparent);
                }
                if (checkWin(8, playerOnesTurn)) {
                    endGame();
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        bStatus9.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int x = Integer.parseInt(snapshot.getValue().toString());
                boxStatusLocal[8]=x;
                if (x == 1) {
                    Log.i(TAG, "onDataChange: change image to  x");
                    i9.setBackgroundResource(R.drawable.x_small);
                } else if (x == 2) {
                    Log.i(TAG, "onDataChange: change image to O");
                    i9.setBackgroundResource(R.drawable.o_small);
                } else if (x == -1) {
                    i9.setBackgroundResource(android.R.color.transparent);
                }
                if (checkWin(9, playerOnesTurn)) {
                    endGame();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    public void clickedBoxOnline(View view) {
        final ImageView box = (ImageView) view;
        final int boxClicked = Integer.parseInt(box.getTag().toString());
        Log.i(TAG, "Clicked box "+boxClicked);

        whichPlayersTurnRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(Integer.parseInt(snapshot.getValue().toString())==playerId){
                    boxStatusRef=database.getReference("/boxStatus/"+boxClicked);
                    boxStatusRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            clickedBoxStatus=Integer.parseInt(snapshot.getValue().toString());
                            if(clickedBoxStatus==-1){
                                //The box is not filled
                                boxStatusRef.setValue(playerId);
                                if(playerId==1){
                                    whichPlayersTurnRef.setValue(2);
                                    playerOnesTurn=true;
                                    Log.i(TAG, "playerOnesTurn set to false");
                                }
                                else{
                                    whichPlayersTurnRef.setValue(1);
                                    playerOnesTurn=false;
                                    Log.i(TAG, "playerOnesTurn set to true");
                                }
                                chances--;
                                chancesLeftRef.setValue(chances);
                            }
                            else{
                                Toast.makeText(getApplicationContext(),"Already filled",Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }else{
                    Toast.makeText(getApplicationContext(),"not your turn ",Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    boolean checkWin(int boxChanged, boolean playerOnesTurn){

        Log.i(TAG, "checkWin initiated & playerOnesTurn="+playerOnesTurn);


        Log.i(TAG, "checkWin: "+boxStatusLocal[0]+' '+boxStatusLocal[1]+' '+boxStatusLocal[2]);
        Log.i(TAG, "checkWin: "+boxStatusLocal[3]+' '+boxStatusLocal[4]+' '+boxStatusLocal[5]);
        Log.i(TAG, "checkWin: "+boxStatusLocal[6]+' '+boxStatusLocal[7]+' '+boxStatusLocal[8]);

        int winningMaterial; // winning material is just the value that should be found in boxStatus i.e either x or o
        int player; // 1:player one(x)    2:player two(o)
        if (playerOnesTurn) {
            winningMaterial = 1;
            player = 1;
        } else {
            winningMaterial = 2;
            player = 2;
        }
        Log.i(TAG, "checkWin: Win material"+winningMaterial);



        //check rows
        if (boxChanged == 1 || boxChanged == 2 || boxChanged == 3) {
            //check first row
            if (boxStatusLocal[0] == winningMaterial && boxStatusLocal[1] == winningMaterial && boxStatusLocal[2] == winningMaterial) {/*player won*/
                gameWinner = player;
                hasAWinner = true;
                return true;
            }
        } else if (boxChanged == 4 || boxChanged == 5 || boxChanged == 6) {
            //check second row
            if (boxStatusLocal[3] == winningMaterial && boxStatusLocal[4] == winningMaterial && boxStatusLocal[5] == winningMaterial) {/*player won*/
                gameWinner = player;
                hasAWinner = true;
                return true;
            }
        } else if (boxChanged == 7 || boxChanged == 8 || boxChanged == 9) {
            //check third row
            if (boxStatusLocal[6] == winningMaterial && boxStatusLocal[7] == winningMaterial && boxStatusLocal[8] == winningMaterial) {/*player won*/
                gameWinner = player;
                hasAWinner = true;
                return true;
            }
        }
        //check columns
        if (boxChanged == 1 || boxChanged == 4 || boxChanged == 7) {
            //check first column
            if (boxStatusLocal[0] == winningMaterial && boxStatusLocal[3] == winningMaterial && boxStatusLocal[6] == winningMaterial) {/*player won*/
                gameWinner = player;
                hasAWinner = true;
                return true;
            }
        } else if (boxChanged == 2 || boxChanged == 5 || boxChanged == 8) {
            //check second column
            if (boxStatusLocal[1] == winningMaterial && boxStatusLocal[4] == winningMaterial && boxStatusLocal[7] == winningMaterial) {/*player won*/
                gameWinner = player;
                hasAWinner = true;
                return true;
            }
        } else if (boxChanged == 3 || boxChanged == 6 || boxChanged == 9) {
            //check third column
            if (boxStatusLocal[2] == winningMaterial && boxStatusLocal[5] == winningMaterial && boxStatusLocal[8] == winningMaterial) {/*player won*/
                gameWinner = player;
                hasAWinner = true;
                return true;
            }
        }

        //if it lies on diagonal check the diagonal/(s)

        //main diagonal
        if (boxChanged == 1 || boxChanged == 5 || boxChanged == 9) {
            if (boxStatusLocal[0] == winningMaterial && boxStatusLocal[4] == winningMaterial && boxStatusLocal[8] == winningMaterial) {/*player won*/
                gameWinner = player;
                hasAWinner = true;
                return true;
            }
        }
        //second diagonal
        if (boxChanged == 7 || boxChanged == 5 || boxChanged == 3) {
            if (boxStatusLocal[6] == winningMaterial && boxStatusLocal[4] == winningMaterial && boxStatusLocal[2] == winningMaterial) {/*player won*/
                gameWinner = player;
                hasAWinner = true;
                return true;
            }
        }
        Log.i(TAG, "checkWin: returns False "+boxChanged+" "+playerOnesTurn);
        return false;
    }
    void endGame(){
        if (hasAWinner) {
            Log.i(TAG, "endGame: Player " + gameWinner + " WINS!");
            statusText.setText("Player "+gameWinner+" wins!");
            Toast.makeText(this, "Game Over ! Player " + gameWinner + " WINS!", Toast.LENGTH_LONG).show();

        } else {
            Log.i(TAG, "endGame: its a draw");
            statusText.setText("Game Draw !");
        }
    }
}