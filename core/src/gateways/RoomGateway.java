package gateways;

import usecases.RoomManager;

import java.io.*;

/**
 * A Gateway class that serializes and deserializes the Room Manager class
 */
public class RoomGateway implements GatewayInterface<RoomManager>, Serializable {
    /**
     * Serial extension file rgt_save which stores serialized and
     * deserialized data
     */
    //empty .ser file for our saving and persistence of RoomGateway data
    public String fileName = "rgt_save.ser";

    /**
     * Serializes an inputted Room Manager's list of rooms
     *
     * @param rm the Room Manager class to be serialized
     *
     */
    public void serializeData(RoomManager rm) {
        //try catch block
        try {
            //create new file with passed in variable of empty file
            File new_file = new File(fileName);
            //save file
            FileOutputStream store_file = new FileOutputStream(new_file);
            ObjectOutputStream conv_obj = new ObjectOutputStream(store_file);
            conv_obj.writeObject((RoomManager) rm);
            conv_obj.close();
            store_file.close();
        }
        //catch file not found exception
        catch (FileNotFoundException e) {
            System.out.println("File not Found!");
        }
        //catches IO exception silently
        catch (IOException ignored){}
    }

    /**
     * Deserializes the given serialized file, and converts it to a Room Manager object
     *
     * @return Room Manager object
     */
    public RoomManager deserializeData() {
        //instantiate RoomManager object
        RoomManager rm = new RoomManager();
        //try catch block which catches FileNotFoundException,IO exception and ClassNotFoundException
        try {
            //create new file
            File new_file2 = new File(fileName);
            //store and save serialized objects
            FileInputStream file2 = new FileInputStream(new_file2);
            ObjectInputStream input = new ObjectInputStream(file2);
            //deserialize object and caste as a RoomManager object
            rm = (RoomManager) input.readObject();
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
        //Class not found exception
        catch (ClassNotFoundException e) {
            System.out.println("RoomManager Class was not found!");
        }
        //return RoomManager object
        return rm;
    }
}


