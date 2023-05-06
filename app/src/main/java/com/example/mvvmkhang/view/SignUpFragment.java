package com.example.mvvmkhang.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mvvmkhang.R;
import com.example.mvvmkhang.viewmodels.AuthViewModel;
import com.google.firebase.auth.FirebaseUser;


public class SignUpFragment extends Fragment {

    private AuthViewModel viewModel;
    private NavController navController;
    private EditText editEmail,editPass;
    private TextView signIntext;
    private Button signUpbtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        editEmail = view.findViewById(R.id.signup_email);
        editPass = view.findViewById(R.id.signup_password);
        signIntext = view.findViewById(R.id.loginRedirectText);
        signUpbtn = view.findViewById(R.id.signup_button);

        signIntext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_signupFragment_to_signInFragment3);
            }
        });

        signUpbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editEmail.getText().toString();
                String pass = editPass.getText().toString();
                if (email.isEmpty()){
                    editEmail.setError("Email cannot be empty");
                }
                if (pass.isEmpty()){
                    editPass.setError("Password cannot be empty");
                } else {
                    viewModel.signUp(email,pass);
                    Toast.makeText(getContext(), "Registered Successfully", Toast.LENGTH_SHORT).show();
                    viewModel.getFirebaseUserMutableLiveData().observe((getViewLifecycleOwner()), new Observer<FirebaseUser>() {
                        @Override
                        public void onChanged(FirebaseUser firebaseUser) {
                            if (firebaseUser != null) {
                                navController.navigate(R.id.action_signupFragment_to_signInFragment3);
                            } else {
                                Toast.makeText(getContext(), "Registered Failed", Toast.LENGTH_SHORT).show();
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