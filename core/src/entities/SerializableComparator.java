package entities;

import java.io.Serializable;
import java.util.Comparator;

/**
 * A comparator class that can be serialized by a program.
 *
 * @author Keegan McGonigal
 * @version 1.0
 *
 * @param <T> a generic type of the object you wish to compare.
 */

public interface SerializableComparator<T> extends Comparator<T>, Serializable {
}