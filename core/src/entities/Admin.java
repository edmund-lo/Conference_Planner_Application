package entities;

import java.io.Serializable;

/**
 * An Entity class for an Admin that inherits from User.
 *
 * @author Edmund Lo
 *
 */
public class Admin extends User implements Serializable {

    /**
     * Constructor for an Attendee that inherits from User.
     *
     * @param username the username of the User
     * @param firstName the user's firstName
     * @param lastName the user's lastName
     */
    public Admin(String username, String firstName, String lastName) {
        super(username, firstName, lastName);
    }

}
