package com.example.mvvmkhang.view;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mvvmkhang.R;
import com.example.mvvmkhang.viewmodels.AuthViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.developer.gbuttons.GoogleSignInButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;

import com.google.android.gms.tasks.Task;


public class SignInFragment extends Fragment {

    private AuthViewModel viewModel;
    private NavController navController;
    private EditText editEmail,editPass;
    private TextView signUptext;
    private TextView diaLogtext;
    TextView forgotPass;
    private Button signInbtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_in, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        editEmail = view.findViewById(R.id.login_email);
        editPass = view.findViewById(R.id.login_password);
        signUptext = view.findViewById(R.id.signUpRedirectText);
        diaLogtext = view.findViewById(R.id.forgot_password);
        signInbtn = view.findViewById(R.id.login_button);


        signUptext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_signInFragment3_to_signupFragment);
            }
        });
        diaLogtext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               navController.navigate(R.id.action_signInFragment3_to_dialogFragment);
            }
        });

        signInbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editEmail.getText().toString();
                String pass = editPass.getText().toString();

                if (email.isEmpty()){
                    editEmail.setError("Email cannot be empty");
                }
                if (pass.isEmpty()){
                    editPass.setError("Password cannot be empty");
                }
                if (!email.isEmpty() &&!pass.isEmpty()) {
                    viewModel.signIn(email, pass);
                    viewModel.getFirebaseUserMutableLiveData().observe(getViewLifecycleOwner(), new Observer<FirebaseUser>() {
                        @Override
                        public void onChanged(FirebaseUser firebaseUser) {
                            if (firebaseUser != null) {
                                Toast.makeText(getContext(), "Login Successfully", Toast.LENGTH_SHORT).show();
                                navController.navigate(R.id.instruct1Fragment);
                            }
                            else{
                                Toast.makeText(getContext(), "Login Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this, (ViewModelProvider.Factory) ViewModelProvider.AndroidViewModelFactory
                .getInstance(getActivity().getApplication())).get(AuthViewModel.class);
    }
}