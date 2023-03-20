package com.fypkevin03.ar_restaurantmenu;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Activity_Login extends AppCompatActivity {

    EditText e1,e2;
    Button b1;
    AccountDatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = new AccountDatabaseHelper(this);

        e1 = (EditText)findViewById(R.id.et_login_email);
        e2 = (EditText)findViewById(R.id.et_login_password);
        b1 = (Button) findViewById(R.id.btn_submit_login);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new LoginTask().execute();
            }
        });
    }

    class LoginTask extends AsyncTask<String, Void, String >{
        String username,password;
        Boolean Chkemailpass;
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute(){
            progressDialog = ProgressDialog.show(Activity_Login.this,
                    "Logging In",
                    "Loading, please wait...");
        }

        @Override
        protected String doInBackground(String... strings){
            username = e1.getText().toString();
            password = e2.getText().toString();
            Chkemailpass = db.emailpassword(username,password);
            if (Chkemailpass == true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s){
            progressDialog.dismiss();
            if (Chkemailpass == true) {
                Intent i = new Intent(Activity_Login.this, Activity_Homepage.class);      //if login success then jump to Main page
                i.putExtra("username",username);
                startActivity(i);
                Toast.makeText(getApplicationContext(), "Successfully Login", Toast.LENGTH_SHORT).show();
                finish();
            }
            else{
                Toast.makeText(Activity_Login.this, "Wrong email or password", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.loginmenu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if (id == R.id.about_app){
            Intent i = new Intent(Activity_Login.this, Activity_Aboutus.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void btn_sign_up(View v){
        Intent i = new Intent(this, Activity_Signup.class);
        startActivity(i);
    }

    public void btn_guest(View v){
        Intent i = new Intent(this, Activity_Homepage.class);
        //i.putExtra("username","Guest");
        startActivity(i);
    }

}