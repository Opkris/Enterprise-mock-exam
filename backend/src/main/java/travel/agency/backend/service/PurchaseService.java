package travel.agency.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import travel.agency.backend.entity.Purchase;
import travel.agency.backend.entity.Trip;
import travel.agency.backend.entity.Users;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;


@Service
@Transactional
public class PurchaseService {

    @Autowired
    private EntityManager em;

    public List<Purchase> getAllPurchases(){
        TypedQuery<Purchase> query = em.createQuery("SELECT p FROM Purchase p",Purchase.class);
        return query.getResultList();
    }

    public List<Purchase> filterPurchasesByTrip(Long tripID) {
        TypedQuery<Purchase> query = em.createQuery(
                "SELECT p FROM Purchase p WHERE p.tripInformation.id =?1", Purchase.class);
        query.setParameter(1, tripID);

        //It could be that here are no purchases but that is fine
        return query.getResultList();
    }

    public Long newPurchase(Long tripID, String userID) {
        Trip trip = em.find(Trip.class, tripID);
        Users users = em.find(Users.class, userID);

        if (trip == null) {
            throw new IllegalStateException("Trip not found");
        }
        if (users == null) {
            throw new IllegalStateException("User not found");
        }

        Purchase purchase = new Purchase();
        purchase.setBookedBy(users);
        purchase.setDateOfBooking(LocalDate.now());
        purchase.setTripInformation(trip);
        users.getBookedTrips().add(trip);
        em.persist(purchase);

        return purchase.getId();
    }

    //Could have remove purchase if I have time to implement it

    public List<Purchase> filterPurchasesByUser(String userID) {
        TypedQuery<Purchase> query = em.createQuery(
                "SELECT p FROM Purchase p WHERE p.bookedBy.userID =?1", Purchase.class
        );

        query.setParameter(1, userID);

        return query.getResultList();
    }
}
