package gateways;

import usecases.LoginLogManager;

import java.io.*;
import java.io.Serializable;

/**
 * A Gateway class that serializes and deserializes the LoginLog Manager class
 */
public class LoginLogGateway implements GatewayInterface<LoginLogManager>, Serializable {

    /**
     * Serial extension file logs_save which stores serialized and
     * deserialized data of logs
     */
    public String fileName = "logs_save.ser";

    /**
     * Serializes an inputted Login Log Manager's data
     *
     * @param llm LoginLogManager object
     */
    public void serializeData(LoginLogManager llm){
        //try catch block, enters try and catches if there is a FileNotFoundException or silently
        //catches IO exception
        try {
            //create new file
            File new_file = new File(fileName);
            //Save data to file, and convert the LoginLogManager object llm
            FileOutputStream store_file = new FileOutputStream(new_file);
            ObjectOutputStream conv_obj = new ObjectOutputStream(store_file);
            //Serialize data and store in this file
            conv_obj.writeObject(llm);
            //close files
            conv_obj.close();
            store_file.close();
        }
        //catch FileNotFound exception
        catch (FileNotFoundException e) {
            System.out.println("File not Found!");
        }
        //catch IO exception silently
        catch (IOException ignored){}
    }

    /**
     * Deserializes a Login Log Manager's data
     *
     * @return the Login Log Manager class
     */
    public LoginLogManager deserializeData() {
        //Instantiate New LoginLogManager object
        LoginLogManager llm = new LoginLogManager();
        try {
            //create new file using String fileName
            File new_file2 = new File(fileName);
            //Save data and assign read value object to LoginLogManager object llm
            FileInputStream file2 = new FileInputStream(new_file2);
            ObjectInputStream input = new ObjectInputStream(file2);
            llm = (LoginLogManager) input.readObject();
            //close files
            input.close();
            file2.close();

        }
        //catch FileNotFoundException
        catch (FileNotFoundException e) {
            System.out.println("Generating new file: " + fileName);
        }
        //silently catch IO exception
        catch (IOException ignored){}
        //catch ClassNotFoundException
        catch (ClassNotFoundException e) {
            System.out.println("LoginLogManager Class was not found");
        }
        //Return LoginLogManager
        return llm;
    }

}

