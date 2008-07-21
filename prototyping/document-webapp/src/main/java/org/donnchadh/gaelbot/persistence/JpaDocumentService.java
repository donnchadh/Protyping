package org.donnchadh.gaelbot.persistence;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.donnchadh.gaelbot.crawler.AbstractBot;
import org.donnchadh.gaelbot.domainmodel.Document;
import org.donnchadh.gaelbot.domainmodel.DocumentRepository;
import org.donnchadh.gaelbot.domainmodel.Language;
import org.donnchadh.gaelbot.domainmodel.RepositoryDocument;
import org.donnchadh.gaelbot.webapp.SearchCriteria;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * A JPA-based implementation of the Booking Service. Delegates to a JPA entity manager to issue data access calls
 * against the backing repository. The EntityManager reference is provided by the managing container (Spring)
 * automatically.
 */
@Service("bookingService")
@Repository
public class JpaDocumentService implements DocumentService {
    private static final Charset UTF_8 = Charset.forName(AbstractBot.UTF_8);

    private final DocumentRepository documentRepository;

    private EntityManager em;

    
    public JpaDocumentService(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }
    
    @PersistenceContext
    public void setEntityManager(EntityManager em) {
        this.em = em;
    }

//    @Transactional(readOnly = true)
//    @SuppressWarnings("unchecked")
//    public List<Booking> findBookings(String username) {
//	if (username != null) {
//	    return em.createQuery("select b from Booking b where b.user.username = :username order by b.checkinDate")
//		    .setParameter("username", username).getResultList();
//	} else {
//	    return null;
//	}
//    }
//
//    @Transactional(readOnly = true)
//    @SuppressWarnings("unchecked")
//    public List<Hotel> findHotels(SearchCriteria criteria) {
//	String pattern = getSearchPattern(criteria);
//	return em.createQuery(
//		"select h from Hotel h where lower(h.name) like " + pattern + " or lower(h.city) like " + pattern
//			+ " or lower(h.zip) like " + pattern + " or lower(h.address) like " + pattern).setMaxResults(
//		criteria.getPageSize()).setFirstResult(criteria.getPage() * criteria.getPageSize()).getResultList();
//    }
//
//    @Transactional(readOnly = true)
//    public Hotel findHotelById(Long id) {
//	return em.find(Hotel.class, id);
//    }
//
//    @Transactional(readOnly = true)
//    public Booking createBooking(Long hotelId, String username) {
//	Hotel hotel = em.find(Hotel.class, hotelId);
//	User user = findUser(username);
//	return new Booking(hotel, user);
//    }
//
//    @Transactional
//    public void cancelBooking(Long id) {
//	Booking booking = em.find(Booking.class, id);
//	if (booking != null) {
//	    em.remove(booking);
//	}
//    }

    // helpers

//    private User findUser(String username) {
//	return (User) em.createQuery("select u from User u where u.username = :username").setParameter("username",
//		username).getSingleResult();
//    }

    
    public Document importDocument(URL url) {
        return importDocumentFile(null, url);
    }
    
    public Document importDocumentFile(File file, URL originalUrl) {
        RepositoryDocument repositoryDocument = documentRepository.importDocument(file);
        // TODO
        String langaugeCode = "ga";
        Charset characterSet = UTF_8;
        // TODO
        URI uri = null;
        Language language = (Language)em.createQuery("select l from Language l where l.code = :languageCode").setParameter("languageCode",
              langaugeCode).getSingleResult();
        Document document = new Document(repositoryDocument, originalUrl, uri, language, characterSet);
        em.persist(document);
        return document;
    }

    @Override
    public Document findDocumentById(Long id) {
        return em.find(Document.class, id);
    }

    @Override
    public List<Document> findDocuments(String username) {
      if (username != null) {
          return em.createQuery("select b from Booking b where b.user.username = :username order by b.checkinDate")
              .setParameter("username", username).getResultList();
      } else {
          return null;
      }
    }

    @Override
    public List<Document> findDocuments(SearchCriteria criteria) {
      String pattern = getSearchPattern(criteria);
      return em.createQuery(
          "select h from Hotel h where lower(h.name) like " + pattern + " or lower(h.city) like " + pattern
              + " or lower(h.zip) like " + pattern + " or lower(h.address) like " + pattern).setMaxResults(
          criteria.getPageSize()).setFirstResult(criteria.getPage() * criteria.getPageSize()).getResultList();
    }

    private String getSearchPattern(SearchCriteria criteria) {
        if (StringUtils.hasText(criteria.getSearchString())) {
            return "'%" + criteria.getSearchString().toLowerCase().replace('*', '%') + "%'";
        } else {
            return "'%'";
        }
        }

}