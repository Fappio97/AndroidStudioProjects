package com.unito.prenotazioniandroid.data;

import android.os.AsyncTask;
import android.util.Log;

import com.unito.prenotazioniandroid.connection.DoLogin;
import com.unito.prenotazioniandroid.connection.DoLogout;
import com.unito.prenotazioniandroid.connection.response_parsers.HtmlMessageParser;
import com.unito.prenotazioniandroid.data.model.LoggedInUser;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.security.auth.login.LoginException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {
    private static final int USER_MIN_LENGTH = 4;
    private static final int PWD_MIN_LENGTH = 5;
    private static final String USER_REGEX = "[a-zA-Z]+(([a-zA-Z]|[0-9])+){" + (USER_MIN_LENGTH - 1) + ",}"; //onChange check sintax doesn't differ from Javascript, too
    private static final String PWD_REGEX = "([a-zA-Z]|[0-9]|&|!|_){" + PWD_MIN_LENGTH + ",}";

    public Result<LoggedInUser> login(String username, String password) {

        try {
            // TODO: handle loggedInUser authentication (visualizzare un messaggio di errore migliore)
            if (username.matches(USER_REGEX) && password.matches(PWD_REGEX)) {
                LoggedInUser user = new LoggedInUser(java.util.UUID.randomUUID().toString(), username);
                DoLogin loginExecutor = new DoLogin(null, username, password);
                AsyncTask<String, Void, String> execute = loginExecutor.execute();
                String s = execute.get(500, TimeUnit.MILLISECONDS);
                HtmlMessageParser htmlmp = new HtmlMessageParser(s, null);
                if (htmlmp.hasSucceded())
                    return new Result.Success<>(user);
                else
                    throw new LoginException("Wrong user/password combination");
            } else {
                throw new LoginException("Combinazione di caratteri non valida per username e/o password");
            }
        } catch (Exception e) {
            Log.d(this.getClass().getSimpleName(), e.getClass().getSimpleName() + " " + e.getMessage());
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public Result<String> logout() {//aaa
        try {
            DoLogout loginExecutor = new DoLogout(null);

            AsyncTask<String, Void, String> execute = loginExecutor.execute();
            String s = execute.get(500, TimeUnit.MILLISECONDS);
            HtmlMessageParser htmlmp = new HtmlMessageParser(s, null);
            if (htmlmp.hasSucceded())
                return new Result.Success<>("Logout effettuato");
            else
                throw new LoginException("Logout non riuscito");
        } catch (Exception e) {
            Log.d(this.getClass().getSimpleName(), e.getClass().getSimpleName() + " " + e.getMessage());
            return new Result.Error(new IOException("Error logging out", e));
        }
    }
}
