package gateways;

import usecases.UserAccountManager;
//import usecases.UserManager;

import java.io.*;
/**
 * A Gateway class that serializes and deserializes the User Account Manager class
 */
public class UserAccountGateway implements GatewayInterface<UserAccountManager>, Serializable {
    /**
     * Serial extension file uam_gt_save.ser which stores serialized and
     * deserialized data
     */
    public String fileName = "uam_gt_save.ser";
    /**
     * This method serializes an inputted User Account Manager's data
     *
     * @param uam UserAccountManager object
     *
     */
    public void serializeData(UserAccountManager uam) {
        //try catch block with catch IO and FileNotFound exceptions
        try {
            //create new file
            File new_file = new File(fileName);
            //save data objects in designated store file
            //Serialize object by writing UserAccountManager um as value to variable conv_obj
            FileOutputStream store_file = new FileOutputStream(new_file);
            ObjectOutputStream conv_obj = new ObjectOutputStream(store_file);
            conv_obj.writeObject(uam);
            //close files
            conv_obj.close();
            store_file.close();
        }
        //catch FileNotFoundException
        catch (FileNotFoundException e) {
            System.out.println("File not Found!");
        }
        //Silently catch IO exception
        catch (IOException ignored){}
    }
    /**
     * This method deserializes a User Account Manager's data
     *
     * @return the UserAccountManager class
     */
    public UserAccountManager deserializeData() {
        //Instantiate UserManager object uam
        UserAccountManager uam = new UserAccountManager();
        //try catch block ensures FileNotFound, ClassNotFound and IO Exception are caught
        try {
            //create new file
            File new_file2 = new File(fileName);
            //assign to file, save and unpack the serialized data to UserAccountManager object
            //input variable has value file input stream assigned
            //Assign and deserialize our data to UserAccountManager uam
            FileInputStream file2 = new FileInputStream(new_file2);
            ObjectInputStream input = new ObjectInputStream(file2);
            uam = (UserAccountManager) input.readObject();
            //close files
            input.close();
            file2.close();
        }
        //catch FileNotFoundException
        catch (FileNotFoundException e) {
            System.out.println("Generating new file: " + fileName);
        }
        //Silently catch IO exception
        catch (IOException ignored){}
        //Catch ClassNotFoundException
        catch (ClassNotFoundException e) {
            System.out.println("UserAccountManager Class was not found");
        }
        return uam;
    }
}
