package travel.agency.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import travel.agency.backend.entity.Trip;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class TripService {

    @Autowired
    private EntityManager em;

    public List<Trip> getAllTrips(Boolean withTravelers) {
        TypedQuery<Trip> query = em.createQuery(
                "SELECT t FROM Trip t ORDER BY t.cost ASC", Trip.class
        );
        List<Trip> allTrips = query.getResultList();

        //If we ant to load all users taking this trip we need to bypass lazy fetching by using .size on list
        //Using only getAllTravelers is not enough
        if (withTravelers) {
            allTrips.forEach(u -> u.getAllTravelers().size());
        }

        return allTrips;
    }

    public Long createTrip(
            String title,
            String description,
            Long cost,
            String locationName,
            LocalDate departureDate,
            LocalDate returnDate
    ) {
        Trip trip = new Trip();

        trip.setTitle(title);
        trip.setDescription(description);
        trip.setCost(cost);
        trip.setLocationName(locationName);
        trip.setDepartureDate(departureDate);
        trip.setReturnDate(returnDate);

        em.persist(trip);

        return trip.getId();
    }

    public Trip getTrip(Long tripID, Boolean withTravelers) {
        Trip trip = em.find(Trip.class, tripID);

        if (trip == null) {
            throw new IllegalStateException("No such trip found");
        }

        if (withTravelers) {
            trip.getAllTravelers().size();
        }

        return trip;
    }

    public void deleteTrip(Long tripID) {
        Trip tripToRemove = em.find(Trip.class, tripID);

        if (tripToRemove == null) {
            throw new IllegalStateException("Trip not found in database");
        }

        em.remove(tripToRemove);
    }

    public List<Trip> filterTripsByLocation(String locationName) {
        TypedQuery<Trip> query = em.createQuery(
                "SELECT t FROM Trip t WHERE t.locationName =?1 ORDER BY t.cost ASC", Trip.class
        );

        query.setParameter(1, locationName);

        return query.getResultList();
    }

    public List<Trip> filterByCost(Long cost) {
        TypedQuery<Trip> query = em.createQuery(
                "SELECT t FROM Trip t WHERE t.cost =?1", Trip.class
        );

        query.setParameter(1, cost);

        return query.getResultList();
    }
}
