package gateways;

import usecases.EventManager;

import java.io.*;
import java.io.Serializable;

/**
 * A Gateway class that serializes and deserializes the Event Manager class
 */
public class EventGateway implements GatewayInterface<EventManager>, Serializable {

    /**
     * Serial extension file egt_save which stores serialized and
     * deserialized data
     */
    public String fileName = "egt_save.ser";
    public String txtSchedule = "print_rs.txt";

    /**
     * Serializes an inputted Event Manager's data
     *
     * @param em UseCases.EventManager object
     */

    public void serializeData(EventManager em){
        //try catch block, enters try and catches if there is a FileNotFoundException or silently
        //catches IO exception
        try {
            //create new file
            File new_file = new File(fileName);
            //Save data to file, and convert the EventManager object em
            FileOutputStream store_file = new FileOutputStream(new_file);
            ObjectOutputStream conv_obj = new ObjectOutputStream(store_file);
            //Serialize data and store in this file
            conv_obj.writeObject(em);
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
     * Deserializes an Event Manager's data
     *
     * @return the Event Manager class
     */
    public EventManager deserializeData() {
        //Instantiate New EventManager object
        EventManager em = new EventManager();
        try {
            //create new file using String fileName
            File new_file2 = new File(fileName);
            //Save data and assign read value object to EventManager object em
            FileInputStream file2 = new FileInputStream(new_file2);
            ObjectInputStream input = new ObjectInputStream(file2);
            em = (EventManager) input.readObject();
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
            System.out.println("EventManager Class was not found");
        }
        //Return EventManager
        return em;

    }

    /**
     * This method writes a passed in EventManager's schedule into a saved text file
     * @param ret this is the String builder toString output that is to be produced in the text file
     * @catch FileNotFound exception
     * @catch IOException
     */
    public void outputToTxtFile(String ret) {
        try {
            //create new file
            File txt1 = new File(txtSchedule);
            FileOutputStream fos1 = new FileOutputStream(txt1);
            //write the passed in string ret into the txt file
            PrintWriter out = new PrintWriter(fos1);
            out.println(ret);
            //close files
            fos1.close();
            out.close();
        }
        //catch FileNotFoundException
        catch (FileNotFoundException e) {
            System.out.println("Generating new file: " + txtSchedule);
        }
        //Silently catch IO exception
        catch (IOException ignored){}
//        //Catch ClassNotFoundException
//        catch (ClassNotFoundException e) {
//            System.out.println("UserManager Class was not found");
//        }
    }
}

