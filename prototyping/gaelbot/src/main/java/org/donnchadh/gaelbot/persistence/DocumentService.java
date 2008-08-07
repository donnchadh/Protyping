package org.donnchadh.gaelbot.persistence;

import java.io.File;
import java.net.URL;
import java.util.List;

import org.donnchadh.gaelbot.domainmodel.Document;
import org.donnchadh.gaelbot.webapp.SearchCriteria;

/**
 * A service interface for retrieving hotels and bookings from a backing repository. Also supports the ability to cancel
 * a booking.
 */
public interface DocumentService {

//    /**
//     * Find bookings made by the given user
//     * @param username the user's name
//     * @return their bookings
//     */
//    public List<Booking> findBookings(String username);
//
//    /**
//     * Find hotels available for booking by some criteria.
//     * @param criteria the search criteria
//     * @return a list of hotels meeting the criteria
//     */
//    public List<Hotel> findHotels(SearchCriteria criteria);
//
//    /**
//     * Find hotels by their identifier.
//     * @param id the hotel id
//     * @return the hotel
//     */
//    public Hotel findHotelById(Long id);
//
//    /**
//     * Create a new, transient hotel booking instance for the given user.
//     * @param hotelId the hotelId
//     * @param userName the user name
//     * @return the new transient booking instance
//     */
//    public Booking createBooking(Long hotelId, String userName);
//
//    /**
//     * Cancel an existing booking.
//     * @param id the booking id
//     */
//    public void cancelBooking(Long id);

    public Document importDocument(URL originalUrl);

    public Document importDocumentFile(File file, URL originalUrl);

    public List<Document> findDocuments(String name);

    public List<Document> findDocuments(SearchCriteria criteria);

    public Document findDocumentById(Long id);

}
