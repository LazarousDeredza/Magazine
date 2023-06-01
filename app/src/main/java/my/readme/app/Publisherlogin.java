package my.readme.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.namespace.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Publisherlogin extends AppCompatActivity {

    TextInputLayout email, pass;
    Button Signin, Signinphone;
    TextView Forgotpassword, signup;
    FirebaseAuth Fauth;
    String emailid, pwd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publisherlogin);


        try{

            email = (TextInputLayout)findViewById(R.id.Lemail);
            pass = (TextInputLayout)findViewById(R.id.Lpassword);
            Signin = (Button)findViewById(R.id.button4);
            signup = (TextView) findViewById(R.id.textView3);
            Forgotpassword = (TextView)findViewById(R.id.forgotpass);
            Signinphone = (Button)findViewById(R.id.btnphone);

            Fauth = FirebaseAuth.getInstance();

            Signin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    emailid = email.getEditText().getText().toString().trim();
                    pwd = pass.getEditText().getText().toString().trim();

                    if(isValid()){

                        final ProgressDialog mDialog = new ProgressDialog(Publisherlogin.this);
                        mDialog.setCanceledOnTouchOutside(false);
                        mDialog.setCancelable(false);
                        mDialog.setMessage("Sign In Please Wait.......");
                        mDialog.show();

                        Fauth.signInWithEmailAndPassword(emailid,pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if(task.isSuccessful()){
                                    mDialog.dismiss();

                                    if(Fauth.getCurrentUser().isEmailVerified()){
                                        mDialog.dismiss();
                                        Toast.makeText(Publisherlogin.this, "Congratulation! You Have Successfully Login", Toast.LENGTH_SHORT).show();
                                        Intent Z = new Intent(Publisherlogin.this,PublisherMagPanel_BottomNavigation.class);
                                        startActivity(Z);
                                        finish();

                                    }else{
                                        ReusableCodeForAll.ShowAlert(Publisherlogin.this,"Verification Failed","You Have Not Verified Your Email");

                                    }
                                }else{
                                    mDialog.dismiss();
                                    ReusableCodeForAll.ShowAlert(Publisherlogin.this,"Error",task.getException().getMessage());
                                }
                            }
                        });
                    }
                }
            });
            signup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Publisherlogin.this,PublisherRegistration.class));
                    finish();
                }
            });
            Forgotpassword.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Publisherlogin.this,PublisherForgotPassword.class));
                    finish();
                }
            });
            Signinphone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Publisherlogin.this,Publisherloginphone.class));
                    finish();
                }
            });
        }catch (Exception e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }

    }

    String emailpattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    public boolean isValid() {

        email.setErrorEnabled(false);
        email.setError("");
        pass.setErrorEnabled(false);
        pass.setError("");

        boolean isvalid = false, isvalidemail = false, isvalidpassword=false;
        if(TextUtils.isEmpty(emailid)){
            email.setErrorEnabled(true);
            email.setError("Email is Required");
        }
        else{
            if(emailid.matches(emailpattern)){
                isvalidemail=true;
            }
            else{
                email.setErrorEnabled(true);
                email.setError("lnvalid Email Adress");
            }
        }
    if(TextUtils.isEmpty(pwd))

    {

        pass.setErrorEnabled(true);
        pass.setError("password is Required");
    }
    else {
      isvalidpassword = true;
    }
    isvalid=(isvalidemail && isvalidpassword)?true:false;
        return isvalid;
    }

}