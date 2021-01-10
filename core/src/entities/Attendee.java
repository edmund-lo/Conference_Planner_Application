package entities;

import java.io.Serializable;

/**
 * An Entity class for an Attendee that inherits from User.
 *
 * @author Edmund Lo
 *
 */
public class Attendee extends User implements Serializable {

    /**
     * Constructor for an Attendee that inherits from User.
     *
     * @param username the username of the User
     * @param firstName the user's firstName
     * @param lastName the user's lastName
     */
    public Attendee(String username, String firstName, String lastName, boolean vip) {
        super(username, firstName, lastName);
        setVipStatus(vip);
    }
}
