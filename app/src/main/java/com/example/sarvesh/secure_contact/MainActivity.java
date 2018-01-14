package com.example.sarvesh.secure_contact;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
//import com.firebase.ui.database.FirebaseListAdapter;
//import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private ListView mListView;
    private DatabaseReference mRef,mUserDatabase;
    private FirebaseUser mCurrentUser;
    private TextView mName,mNumber;
    private FirebaseDatabase mDatabase;
   // private FirebaseListAdapter<ChatMassage> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();


        mName=findViewById(R.id.username);
        mNumber=findViewById(R.id.userpass);

        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        mDatabase=FirebaseDatabase.getInstance();


    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser==null)
        {
            Intent intent=new Intent(MainActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
        }
        else
        {
            displaychatmassage();

//            Query query = FirebaseDatabase.getInstance()
//                    .getReference()
//                    .child("User")
//                    .limitToLast(50);
//
//            FirebaseListOptions<ChatMassage> options = new FirebaseListOptions.Builder<ChatMassage>()
//                    .setQuery(query, ChatMassage.class)
//                    .build();
//
//            FirebaseListAdapter<ChatMassage> adapter = new FirebaseListAdapter<ChatMassage>(options) {
//                @Override
//                protected void populateView(View v, ChatMassage model, int position) {
//                    // Bind the Chat to the view
//
//                    TextView name,pass;
//                    name=v.findViewById(R.id.text1);
//                    pass=v.findViewById(R.id.text2);
//
//                    name.setText(model.getName());
//                    pass.setText(model.getPassword());
//
//                }
//            };

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.logout) {
            FirebaseAuth.getInstance().signOut();
            Toast.makeText(getApplicationContext(), "Successfully Logout", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
        if (id == R.id.addaccount) {
            Intent intent = new Intent(MainActivity.this, ContactActivity.class);
            startActivity(intent);
            finish();
        }
        return true;
    }


    void displaychatmassage() {

        String Curreent_uid = mCurrentUser.getUid();
        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("User").child(Curreent_uid);

        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

               // Toast.makeText(getApplicationContext(), "Hello sarvesh", Toast.LENGTH_LONG).show();

                String name = dataSnapshot.child("Name").getValue().toString();
                String number = dataSnapshot.child("Number").getValue().toString();

                if(name!=null&&number!=null)
                {
                    mName.setText(name);
                    mNumber.setText(number);
                }else
                {
                    Toast.makeText(getApplicationContext(), "Database is null", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Toast.makeText(getApplicationContext(), "Error to Retriewing data", Toast.LENGTH_LONG).show();

            }
        });

    }

}
