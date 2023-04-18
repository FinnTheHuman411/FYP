package com.fypkevin03.ar_restaurantmenu;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Activity_Admin_AC_Add extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    DatabaseHelper_Account db;
    EditText e1, e2, e3, e4, e5;
    Button b1;
    TextView t1;

    String s1,s2,s3,s4, s5;
    Boolean chkusername;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_ac_add);

        // Sign Up Copy
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        db = new DatabaseHelper_Account(this);
        e1 = (EditText)findViewById(R.id.username);
        e2 = (EditText)findViewById(R.id.email);
        e3 = (EditText)findViewById(R.id.age);
        e4 = (EditText)findViewById(R.id.pass);
        e5 = (EditText)findViewById(R.id.cpass);
        b1 = (Button) findViewById(R.id.register);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                s1 = e1.getText().toString();
                s2 = e2.getText().toString();
                s3 = e3.getText().toString();
                s4 = e4.getText().toString();
                s5 = e5.getText().toString();

                chkusername = db.chkusername(s1);

                String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
                Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(s2);

                if (s1.equals("") || s2.equals("") || s3.equals("") || s4.equals("") || s5.equals("")){
                    Toast.makeText(getApplicationContext(), "Fields are empty", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(s4.equals(s5)){
                        if(chkusername == false){
                            Toast.makeText(getApplicationContext(), "User Name Already exists", Toast.LENGTH_SHORT).show();
                        }
                        else if (matcher.matches() == false){
                            Toast.makeText(getApplicationContext(), "Invalid email format", Toast.LENGTH_SHORT).show();
                        } else {
                            new Activity_Admin_AC_Add();
                        }
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Password do not match", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // Spinner
        Spinner Role_Spinner = findViewById(R.id.role_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.role, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Role_Spinner.setAdapter(adapter);
        Role_Spinner.setOnItemSelectedListener(this);
    }

    // Sign Up Copy
    class SignUpTask extends AsyncTask<String, Void, String > {
        ProgressDialog progressDialog;
        Boolean insert;

        @Override
        protected void onPreExecute(){
            progressDialog = ProgressDialog.show(Activity_Admin_AC_Add.this,
                    "Creating a new account",
                    "Loading, please wait...");
        }

        @Override
        protected String doInBackground(String... strings){
            insert = db.insert(s1, s2, s3, s4, "admin");
            try {
                Thread.sleep(2500);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s){
            progressDialog.dismiss();
            if (insert ==true){
                Toast.makeText(getApplicationContext(), "Registered Successfully", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home)
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    // Spinner
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String text = adapterView.getItemAtPosition(i).toString();
        Toast.makeText(adapterView.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

}
