package gateways;

import usecases.MessageManager;

import java.io.*;

/**
 * A Gateway class that serializes and deserializes the Message Manager class
 *
 */
public class MessageGateway implements GatewayInterface<MessageManager>, Serializable {
    /**
     * Serial extension file mgt_save which stores serialized and deserialized data
     */
    //initializing our fileName in .ser file for saving and persistence of our data.
    public String fileName = "mgt_save.ser";

    /**
     * This method serializes an inputted Message Manager's data
     *
     * @param mm MessageManager object
     */
    public void serializeData(MessageManager mm) {
        //try catch block, enters try and catches if there is a FileNotFoundException or silently
        //catches IO exception
        try {
            //create new file
            File new_file = new File(fileName);
            //store file in new variable
            //convert, save and write to the MessageManager object as a serialized object
            FileOutputStream store_file = new FileOutputStream(new_file);
            ObjectOutputStream conv_obj = new ObjectOutputStream(store_file);
            conv_obj.writeObject(mm);
            conv_obj.close();
            store_file.close();
        }
        //catch FileNotFound exception
        catch (FileNotFoundException e) {
            System.out.println("File not Found!");
        }
        //catch IO Exception silently
        catch (IOException ignored){}

    }

    /**
     * Deserializes the given serialized file, and converts it to a Message Manager object
     *
     * @return Message Manager object
     */
    public MessageManager deserializeData() {
        //Instantiate new MessageManager object
        MessageManager mm = new MessageManager();
        //
        try {
            File new_file2 = new File(fileName);
            //store and save data in new file stream
            FileInputStream file2 = new FileInputStream(new_file2);
            ObjectInputStream input = new ObjectInputStream(file2);
            //assign deserialized MessageManager object to instantiated object earlier
            mm = (MessageManager) input.readObject();
            //close files
            input.close();
            file2.close();
        }
        //catch FileNotFound exception
        catch (FileNotFoundException e) {
            System.out.println("Generating new file: " + fileName);
        }
        //silently catch IO exception
        catch (IOException ignored) {}
        //Catch ClassNotFound exception
        catch (ClassNotFoundException e) {
            System.out.println("Message Manager Class was not found!");
        }
        //Return MessageManager
        return mm;
    }
}