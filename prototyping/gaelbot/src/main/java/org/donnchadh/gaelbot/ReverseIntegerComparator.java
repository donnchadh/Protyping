/**
 * 
 */
package org.donnchadh.gaelbot;

import java.util.Comparator;

public final class ReverseIntegerComparator implements Comparator<Integer> {
    public int compare(Integer o1, Integer o2) {
        return o2.compareTo(o1);
    }
}