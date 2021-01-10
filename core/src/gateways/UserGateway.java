package gateways;

import usecases.UserManager;

import java.io.*;
/**
 * A Gateway class that serializes and deserializes the User Manager class
 * This class implements print schedule method that outputs desired Strings into a text file
 */
public class UserGateway implements GatewayInterface<UserManager>, Serializable {

    /**
     * Serial extension file ugt_save.ser which stores serialized and
     * deserialized data
     */

    public String fileName = "ugt_save.ser";
    public String txtSchedule = "print_schedule.txt";

    /**
     * This method serializes an inputted Entities.User Manager's data
     *
     * @param um UseCases.UserManager object
     *
     */
    public void serializeData(UserManager um) {
        //try catch block with catch IO and FileNotFound exceptions
        try {
            //create new file
            File new_file = new File(fileName);
            //save data objects in designated store file
            //Serialize object by writing UserManager um as value to variable conv_obj
            FileOutputStream store_file = new FileOutputStream(new_file);
            ObjectOutputStream conv_obj = new ObjectOutputStream(store_file);
            conv_obj.writeObject(um);
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
     * This method deserializes a User Manager's data
     *
     * @return the UserManager class
     */
    public UserManager deserializeData() {
        //Instantiate UserManager object um
        UserManager um = new UserManager();
        //try catch block ensures FileNotFound, ClassNotFound and IO Exception are caught
        try {
            //create new file
            File new_file2 = new File(fileName);
            //assign to file, save and unpack the serialized data to UserManager object
            //input variable has value file input stream assigned
            //Assign and deserialize our data to UserManager um
            FileInputStream file2 = new FileInputStream(new_file2);
            ObjectInputStream input = new ObjectInputStream(file2);
            um = (UserManager) input.readObject();
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
            System.out.println("UserManager Class was not found");
        }
        return um;
    }

//    /** ** Didn't have time to fully implement functionality
//     * This method writes a passed in UserManager's schedule into a saved text file
//     * @param ret this is the String builder toString output that is to be produced in the text file
//     * @catch FileNotFound exception
//     * @catch IOException
//     */
//    public void outputToTxtFile(String ret) {
//        try {
//            //create new file
//            File txt1 = new File(txtSchedule);
//            FileOutputStream fos1 = new FileOutputStream(txt1);
//            //write the passed in string ret into the txt file
//            PrintWriter out = new PrintWriter(fos1);
//            out.println(ret);
//            //close files
//            fos1.close();
//            out.close();
//        }
//        //catch FileNotFoundException
//        catch (FileNotFoundException e) {
//            System.out.println("Generating new file: " + txtSchedule);
//        }
//        //Silently catch IO exception
//        catch (IOException ignored){}
////        //Catch ClassNotFoundException
////        catch (ClassNotFoundException e) {
////            System.out.println("UserManager Class was not found");
////        }
//    }
}