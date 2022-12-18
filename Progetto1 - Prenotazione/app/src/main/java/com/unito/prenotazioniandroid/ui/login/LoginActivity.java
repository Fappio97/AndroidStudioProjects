package com.unito.prenotazioniandroid.ui.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.unito.prenotazioniandroid.LoggedInActivity;
import com.unito.prenotazioniandroid.R;
import com.unito.prenotazioniandroid.repository.network.api.PrenotazioniAPIInterface;
import com.unito.prenotazioniandroid.repository.network.api.ThePrenotazioniDBAPIClient;
import com.unito.prenotazioniandroid.repository.storge.model.Prenotazione;

import java.util.ArrayList;

import retrofit2.Call;

import static android.app.SearchManager.ACTION_KEY;

public class LoginActivity extends AppCompatActivity {
    private LoginViewModel loginViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginViewModel = ViewModelProviders.of(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);

        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.login);
        final ProgressBar loadingProgressBar = findViewById(R.id.loading);

        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                loginButton.setEnabled(loginFormState.isDataValid());
                if (loginFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(loginFormState.getUsernameError()));
                }
                if (loginFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(loginFormState.getPasswordError()));
                }
            }
        });

        /*loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(@Nullable LoginResult loginResult) {
                if (loginResult == null) {
                    return;
                }
                loadingProgressBar.setVisibility(View.GONE);
                if (loginResult.getError() != null) {
                    showLoginFailed(loginResult.getError());
                }
                if (loginResult.getSuccess() != null) {
                    updateUiWithUser(loginResult.getSuccess());
                }
                setResult(Activity.RESULT_OK);

                //Complete and destroy login activity once successful
                goToLoggedInActivity();
                finish();
            }
        });*/
        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(@Nullable LoginResult loginResult) {
                if (loginResult == null) {
                    return;
                }
                loadingProgressBar.setVisibility(View.GONE);
                if (loginResult.getError() != null) {
                    showLoginFailed(loginResult.getError());
                    finish();
                } else if (loginResult.getSuccess() != null) {
                    updateUiWithUser(loginResult.getSuccess());

                    setResult(Activity.RESULT_OK);

                    //Complete and destroy login activity once successful
                    goToLoggedInActivity();
                    //new DoLogin().execute();
                    finish();
                }
            }
        });


        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    loginViewModel.login(usernameEditText.getText().toString(),
                            passwordEditText.getText().toString());
                }
                return false;
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingProgressBar.setVisibility(View.VISIBLE);
                loginViewModel.login(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        });

        //Log.d(DEBUG_TAG, isOnLine() ? "Siamo online" : "Non siamo online");
        //testnet(findViewById(R.id.login));
    }

    private void updateUiWithUser(LoggedInUserView model) {
        String welcome = getString(R.string.welcome) + model.getDisplayName();
        // TODO : initiate successful logged in experience
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }

    private static final String DEBUG_TAG = "debug";
    private static final String TAG = "debug";
    private String[] PERMISSIONS = {
            android.Manifest.permission.ACCESS_NETWORK_STATE,
            android.Manifest.permission.INTERNET
    };
    private PermissionUtility permissionUtility;
    private static boolean loginDone = false;

    public void testnet(View aView) {
        // se sono connesso ad una rete...
        if (isOnLine()) {
            /*
            // recupero l'url da cui recuperare una pagina
            EditText lur = (EditText) findViewById(R.id.lurl);
            String stringUrl = lur.getText().toString();
            // faccio partire il task asincrono
             */
            //String lurl = "http://10.0.2.2:8080/Ripetizioni/Controller?action=Booking";//"http://www.example.com";
            //new DownloadWebpageTask().execute(lurl);
            /*if(!loginDone) {
                new DoLogin().execute();
                loginDone = true;
            } else {
                test();
            }*/
            //TODO implement
        } else {
            // display error
            Log.d(DEBUG_TAG, "Il server non è online");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (permissionUtility.onRequestPermissionsResult(requestCode, permissions, grantResults)) {
            Log.d(TAG, "Permission granted 2");
        }
    }

    public boolean isOnLine() {
        permissionUtility = new PermissionUtility(this, PERMISSIONS);
        if (permissionUtility.arePermissionsEnabled()) {
            Log.d(TAG, "Permission granted 1");
        } else {
            permissionUtility.requestMultiplePermissions();
        }
        /*ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_NETWORK_STATE},
                MY_PERMISSIONS_REQUEST_ACCESS_NETWORK_STATE);
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.INTERNET},
                MY_PERMISSIONS_REQUEST_ACCESS_NETWORK_STATE);

        int access_network_state = ContextCompat.checkSelfPermission(getApplicationContext(), "ACCESS_NETWORK_STATE");
        int internet = ContextCompat.checkSelfPermission(getApplicationContext(), "INTERNET");

        Map<String, Boolean> stringBooleanMap = null;
        String[] permissionsStrings = {Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.INTERNET};
        if(access_network_state == PERMISSION_DENIED || internet == PERMISSION_DENIED) {
            RequestMultiplePermissions requester = new RequestMultiplePermissions();
            Intent permIntent = requester.createIntent(this.getApplicationContext(), permissionsStrings);
            int reqCode = 1;
            startActivityForResult(permIntent, reqCode);
            stringBooleanMap = requester.parseResult(reqCode, null); //null -> permIntent
        }

        boolean gotAllPermissions = true;
        for(String permString : permissionsStrings) {
            gotAllPermissions &= stringBooleanMap.getOrDefault(permString, false);
        }

        if(stringBooleanMap != null && gotAllPermissions) */
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        // interfaccia per SDK >= 21
        Network[] nets = connMgr.getAllNetworks();
        for (int i = 0; i < nets.length; i++) {
            NetworkInfo info = connMgr.getNetworkInfo(nets[i]);
            boolean connessa = info.isConnected();
            if (connessa) {
                String tip = "";
                if (info.getType() == connMgr.TYPE_MOBILE) {
                    tip = "Mobile";
                } else if (info.getType() == connMgr.TYPE_WIFI) {
                    tip = "WiFi";
                }
                Log.d(DEBUG_TAG, "Mobile connected: " + tip);
                return true;
            }
        }
        //} else {
        //    Log.d(DEBUG_TAG, "Some network permission denied");
        //}

        Log.d(DEBUG_TAG, "No rete! ");
        return false;
    }

    private void test() {/*
        PrenotazioniRepository instance = PrenotazioniRepository.getInstance(this.getApplicationContext());
        PagedList<Prenotazione> prenotazioni = instance.getPrenotazioni().getValue();
        Log.d("INSTANCE", new GsonBuilder().serializeNulls().create().toJson(prenotazioni));
        //new DoDebook().execute();*/

        PrenotazioniAPIInterface prenotazioniService = ThePrenotazioniDBAPIClient.getClient();
        Call<ArrayList<Prenotazione>> callBack = prenotazioniService.getPrenotazioni(ACTION_KEY, 1);
        String url = "URL is " + callBack.request().url().url().toString(); //Alfo
        Log.d("REQ_URL", url); //Alfo
    }

    public void goToLoggedInActivity() {
        Intent intent = new Intent(this, LoggedInActivity.class);
        intent.putExtra("utenteLoggato", this.loginViewModel.getLoginRepository().getUser().getDisplayName());
        startActivity(intent);
    }
}
