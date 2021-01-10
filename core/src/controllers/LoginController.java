package controllers;

import entities.User;
import gateways.LoginLogGateway;
import gateways.UserAccountGateway;
import gateways.UserGateway;
import org.json.simple.JSONObject;
import presenters.LoginPresenter;
import usecases.LoginLogManager;
import usecases.UserAccountManager;
import usecases.UserManager;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * A Controller class which deals with users logging in and creating new accounts.
 *
 * @author Haider Bokhari
 * @version 2.0
 *
 */

public class LoginController {
    private ArrayList<String[]> accounts;
    protected UserAccountManager uam;
    protected LoginLogManager llm;
    private final LoginPresenter lp;

    private final LoginLogGateway llg;
    private final UserAccountGateway uag;

    /**
     * Constructor for LoginController object.
     */
    public LoginController(){
        //The gateways that will be used to serialize data
        this.llg = new LoginLogGateway();
        this.uag = new UserAccountGateway();

        //Use gateways to initialize all use cases and managers
        this.llm = llg.deserializeData();
        this.uam = uag.deserializeData();

        //Get list of all existing accounts from the user manager
        this.accounts = uam.getAccountInfo();
        this.lp = new LoginPresenter();
    }

    /**
     * Called to create a new account for a user.
     */
    public JSONObject createAccount(JSONObject obj, boolean isMade){

        String Username = obj.get("username").toString();
        String Password = obj.get("password").toString();
        String confirmPassword = obj.get("confirmPassword").toString();
        String type = obj.get("userType").toString();
        String q1 = obj.get("securityQuestion1").toString();
        String ans1 = obj.get("securityAnswer1").toString();
        String q2 = obj.get("securityQuestion2").toString();
        String ans2 = obj.get("securityAnswer2").toString();
        boolean security = (Boolean) obj.get("setup");

        String firstName = obj.get("firstName").toString();
        String lastName = obj.get("lastName").toString();
        uam = uag.deserializeData();


        //Check for whitespace
        if (Username.contains(" "))
            return lp.noWhiteSpace();
        //Check Username Minimum Length
        if (Username.length() < 1)
            return lp.EmptyName();

        //Check minimum password length for security
        if(Password.length() < 6)
            return lp.EmptyPassword();

        if (!confirmPassword.equals(Password))
            return lp.passwordsDontMatch();

        if (q1.length() == 0 || q2.length() == 0)
            return lp.emptyQuestion();

        if (ans1.length() == 0 || ans2.length() == 0)
            return lp.emptyAnswer();

        if (firstName.length() == 0 || lastName.length() == 0)
            return lp.emptyName();

        UserGateway ug = new UserGateway();
        UserManager um = ug.deserializeData();
        //Add account to the user manager and update the Accounts Arraylist
        if (!isMade){
            //If the counter is 0, that means the username isn't taken and can be set.
            if (usernameExists(Username))
                return lp.UsernameTaken();

            uam.addAccount(Username, Password, type, security,
                    q1, q2, ans1, ans2);
            uag.serializeData(uam);
            this.accounts = uam.getAccountInfo();

            switch (type) {
                case "attendee":
                    um.createNewAttendee(Username, firstName, lastName, false);
                    ug.serializeData(um);
                    return lp.AccountMade();
                case "organizer":
                    um.createNewOrganizer(Username, firstName, lastName);
                    ug.serializeData(um);
                    return lp.AccountMade();
                case "speaker":
                    um.createNewSpeaker(Username, firstName, lastName);
                    ug.serializeData(um);
                    return lp.AccountMade();
                case "administrator":
                    um.createNewAdmin(Username, firstName, lastName);
                    ug.serializeData(um);
                    return lp.AccountMade();
                case "vip":
                    um.createNewAttendee(Username, firstName, lastName, true);
                    ug.serializeData(um);
                    return lp.AccountMade();
                default:
                    return lp.IncorrectCredentials();
            }
        }
        else{
            uam = uag.deserializeData();
            User us = um.getUser(Username);

            us.setFirstName(firstName);
            us.setLastName(lastName);

            ug.serializeData(um);

            uam.setPassword(Username, Password);
            uam.setUserType(Username, type);
            uam.setSetSecurity(Username, security);
            uam.setSecurityQuestions(Username, q1, ans1, q2, ans2);

            this.accounts = uam.getAccountInfo();
            uag.serializeData(uam);

            return lp.AccountUpdated();
        }
    }

    /**
     * Called to let user login to an existing account in the database.
     */
    public JSONObject login(String Username, String Password){
        boolean UsernameExists = false;
        boolean PasswordExists = false;

        uam = uag.deserializeData();
        this.accounts = uam.getAccountInfo();

        if (Username.length() == 0)
            return lp.EmptyName();

        if (Password.length() == 0)
            return lp.EmptyPassword();

        //Go through all existing account to see if username entered exists in the database.
        for (String[] users : accounts){
            if (users[0].equals(Username)){
                UsernameExists = true;
                //If it does exist, check if the password matches.
                if (users[1].equals(Password)){
                    PasswordExists = true;
                }
            }
        }


        if (!UsernameExists)
            return lp.usernameDoesntExist();
        //If the username doesn't exist or password doesn't match, log a failed login.
        else if (!(PasswordExists)){
            updateLogs(Username, false);
            //If past 3 logs are failed logins, lock the account.
            if (suspiciousLogs(Username)){
                return lockOut(Username);

            }
            else
                return lp.IncorrectCredentials();
        }

        //If account is locked, don't let the user login.

        if(uam.isLocked(Username))
            return lp.AccountLocked();

        updateLogs(Username, true);
        return lp.SuccessfulLogin(uam.getAccountJSON(Username));
    }

    /**
     * Locks a user, which prevents them from logging in until an Admin unlocks their account.
     */
    public JSONObject lockOut(String Username){
        uam = uag.deserializeData();

        uam.lockAccount(Username);
        uag.serializeData(uam);
        return lp.AccountLocked();
    }

    /**
     * return true if username already exists.
     */
    public boolean usernameExists(String username){
        //Update accounts list
        uam = uag.deserializeData();
        this.accounts = uam.getAccountInfo();

        int UsernameCheck = 0;
        //Loops through all existing usernames.
        for (String[] users : accounts){
            if (users[0].toLowerCase().equals(username.toLowerCase())){
                //If the Username the user entered already exists then UsernameCheck counter is increased.
                UsernameCheck++;
            }
        }
        return UsernameCheck != 0;
    }

    /**
     * Returns true if past 3 logins were failed logins, false otherwise.
     */
    public boolean suspiciousLogs(String Username){
        //Check if user has logs
        llm = llg.deserializeData();
        if (!llm.checkLogExists(Username))
            return false;

        return llm.suspiciousLogs(Username);
    }

    /**
     * Verify that user can answer security questions in order to let them change their password.
     */
    public JSONObject verifySecurityAnswers(String User, String a1, String a2){
        uam = uag.deserializeData();

        String[] answers = uam.getSecurityAns(User);

        //Check if answers to security questions match.
        if (a1.equals(answers[0]) && a2.equals(answers[1]))
            return lp.correctAnswers();
        else
            return lp.incorrectAnswers();
    }

    /**
     * Validate if username is eligible to change password.
     */
    public JSONObject validateUsername (String user){
        if (!usernameExists(user))
            return lp.usernameDoesntExist();

        if (!uam.getSecurity(user))
            return lp.noSecurity();

        return accountJson(user);
    }

    /**
     * Lets user change password.
     */
    public JSONObject resetPassword(String user, String newPassword, String confirmPassword){

        if (newPassword.length() == 0)
            return lp.EmptyPassword();

        uam = uag.deserializeData();
        //Update password
        if (newPassword.equals(confirmPassword)){
            uam.setPassword(user, newPassword);
            uag.serializeData(uam);
            return lp.passwordChanged();
        } else
            return lp.passwordsDontMatch();

    }

    /**
     * Update the logs database.
     */
    public void updateLogs(String Username, boolean type){
        llm = llg.deserializeData();

        //Get current time and convert it to string/
        LocalDateTime time = LocalDateTime.now();

        //Add log.
        llm.addToLoginLogSet(type, Username, time);
        llg.serializeData(llm);
    }

    /**
     * return JSONObject of a user.
     */
    public JSONObject accountJson(String username){
        return lp.accountLogs(uam.getAccountJSON(username));
    }

}