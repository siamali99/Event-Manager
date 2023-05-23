package com.example.eventmanager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class signuplogin extends AppCompatActivity {

    private LinearLayout name_row,email_row,phone_row,remember_pass_row;
    private TextView already,title;
    private EditText uname,uemail,upass,upass2,uphone,user_id;
    private CheckBox remUserid,remLogin;
    private Button uGo,uExit,uToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sp = getSharedPreferences("useraccount",MODE_PRIVATE);
        SharedPreferences.Editor speditor = sp.edit();
        String userexist = sp.getString("userid","").toString();
        boolean isrememberedLogin = sp.getBoolean("isremLogin",false);
        boolean isrememberedId = sp.getBoolean("isremUserid",false);

        if(!userexist.isEmpty() && isrememberedLogin ){
            Intent I = new Intent(this,MainActivity2.class);
            String userid = userexist;
            String email = sp.getString("email","").toString();
            Bundle bundle = new Bundle();
            bundle.putString( "email",email);
            bundle.putString( "userid",userid);

            I.putExtras(bundle);
            startActivity(I);
            finish();
        }
        setContentView(R.layout.activity_signuplogin);
        name_row = findViewById(R.id.name_row);
        email_row = findViewById(R.id.email_row);
        phone_row = findViewById(R.id.phone_row);
        remember_pass_row = findViewById(R.id.remember_pass_row);
        already = findViewById(R.id.already);
        title = findViewById(R.id.title);
        uname = findViewById(R.id.name);
        uemail = findViewById(R.id.email);
        upass = findViewById(R.id.password);
        upass2 = findViewById(R.id.repassword);
        uphone = findViewById(R.id.phone);
        user_id = findViewById(R.id.user_id);
        remUserid = findViewById(R.id.remember_user_id);
        remLogin = findViewById(R.id.remember_pass);
        uToggle = findViewById(R.id.login_txt);
        uExit = findViewById(R.id.exit_btn);
        uGo = findViewById(R.id.go_btn);

        if(!userexist.isEmpty()){
            name_row.setVisibility(View.GONE);
            email_row.setVisibility(View.GONE);
            phone_row.setVisibility(View.GONE);
            remember_pass_row.setVisibility(View.GONE);
            uToggle.setText("Signup");
            already.setText("Don't have an account?");
            title.setText("Login");
            if(isrememberedId){
                user_id.setText(userexist);
                remUserid.setChecked(true);
            }
        }

        uToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String btntxt = uToggle.getText().toString();
                System.out.println(btntxt);

                if(btntxt.equalsIgnoreCase("Login")){
                    name_row.setVisibility(v.GONE);
                    email_row.setVisibility(v.GONE);
                    phone_row.setVisibility(v.GONE);
                    remember_pass_row.setVisibility(v.GONE);
                    uToggle.setText("Signup");
                    already.setText("Don't have an account?");
                    title.setText("Login");
                    if(isrememberedId){
                        user_id.setText(userexist);
                        remUserid.setChecked(true);
                    }
                }
                else{
                    name_row.setVisibility(v.VISIBLE);
                    email_row.setVisibility(v.VISIBLE);
                    phone_row.setVisibility(v.VISIBLE);
                    remember_pass_row.setVisibility(v.VISIBLE);
                    uToggle.setText("Login");
                    already.setText("Already have an account?");
                    title.setText("Signup");
                }
            }
        });

        uExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        uGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String txt = uToggle.getText().toString();
                if(txt.equalsIgnoreCase("Login")){

                    String userid = user_id.getText().toString();
                    String name = uname.getText().toString();
                    String pass = upass.getText().toString();
                    String pass2 = upass2.getText().toString();
                    String phone = uphone.getText().toString();
                    String  email= uemail.getText().toString();
                    boolean isremUserid = remUserid.isChecked();
                    boolean isremLogin = remLogin.isChecked();
                    if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || userid.isEmpty() || pass.isEmpty() || pass2.isEmpty()) {
                        Toast.makeText(signuplogin.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    }
                    else if (!pass.equals(pass2)) {
                        Toast.makeText(signuplogin.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        speditor.putString("userid",userid);
                        speditor.putString("name",name);
                        speditor.putString("pass",pass);
                        speditor.putString("phone",phone);
                        speditor.putString("email",email);
                        speditor.putBoolean("isremUserid", isremUserid);
                        speditor.putBoolean("isremLogin", isremLogin);
                        speditor.apply();
                        goToNewEventActivity();

                    }
//
                }
                else{

                    String userid = user_id.getText().toString().trim();
                    String pass = upass.getText().toString().trim();
                    boolean isremUserid = remUserid.isChecked();
                    boolean isremLogin = remLogin.isChecked();
                    String saveduserId = sp.getString("userid","");
                    String savedPass = sp.getString("pass","");
                    speditor.putBoolean("isremUserid", isremUserid);
                    speditor.putBoolean("isremLogin", isremLogin);
                    //complete the full..
                    speditor.apply();

                    if (!userid.equals(saveduserId) || !pass.equals(savedPass)) {
                        Toast.makeText(signuplogin.this, "Invalid user ID or password", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        // Go to new event activity
                        goToNewEventActivity();
                        System.out.println("Login done");
                        //finish(); // close this activity
                    }


                }
                System.out.println("ok");
            }
        });
    }

    private void goToNewEventActivity() {
        Intent I = new Intent(this,MainActivity2.class);
        Bundle bundle = new Bundle();
        String  email= uemail.getText().toString();
        String userid = user_id.getText().toString();
        bundle.putString( "email",email);
        bundle.putString( "userid",userid);
        I.putExtras(bundle);
        startActivity(I);
        finish();

    }
}