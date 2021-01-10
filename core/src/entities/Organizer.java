package entities;

import java.io.Serializable;


/**
 * An Entity class for an Organizer that inherits from User.
 *
 * @author Edmund Lo
 *
 */
public class Organizer extends User implements Serializable {

    /**
     * Constructor for an Organizer that inherits from User.
     *
     * @param username the username of the User
     * @param firstName the user's firstName
     * @param lastName the user's lastName
     */
    public Organizer(String username, String firstName, String lastName) {
        super(username, firstName, lastName);
    }

}
