package travel.agency.backend.service;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import travel.agency.backend.TestApplication;
import travel.agency.backend.entity.Trip;
import travel.agency.backend.entity.Users;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class TripServiceTest extends ServiceTestBase {

    @Autowired
    private TripService tripService;

    private String title = "Mordor";
    private String description = "It lay to the east of Gondor and the great river Andurin, and south of Mirkwood";
    private Long cost = 15000L;
    private String location = "Middle-earth";
    private LocalDate departure = LocalDate.now();
    private LocalDate returnDate = LocalDate.of(2042, 11, 1);


    @Test
    public void ShouldCreateTrip(){
        Long id = tripService.createTrip(
                title,
                description,
                cost,
                location,
                departure,
                returnDate
        );
        Trip trip = tripService.getTrip(id,true);
        List<Users> users = trip.getAllTravelers();
        assertNotNull(trip);
        assertNotNull(users);


    }

    @Test
    public void shouldGetAllTrips(){
        Long tripOne = tripService.createTrip(
                title,
                description,
                cost,
                location,
                departure,
                returnDate
        );

        Long tripTwo = tripService.createTrip(
                title + "_2",
                description,
                cost,
                location,
                departure,
                returnDate
        );

        Long tripTree = tripService.createTrip(
                title + "_3",
                description,
                cost,
                location,
                departure,
                returnDate
        );
        assertNotNull(tripOne);
        assertNotNull(tripTwo);
        assertNotNull(tripTree);

        List<Trip> allTrips = tripService.getAllTrips(false);
        assertEquals(3, allTrips.size());
    }

    @Test
    public void shouldDeleteTrip(){
        Long trip = tripService.createTrip(
                title,
                description,
                cost,
                location,
                departure,
                returnDate
        );
        assertNotNull(trip);

        tripService.deleteTrip(trip);

        // if the trip is deleted i should get a IllegalStateException when im tying to get the specific trip
        assertThrows(IllegalStateException.class, () -> tripService.getTrip(trip, false));

    }

    @Test
    public void shouldFilterTrip(){
        Long trip = tripService.createTrip(
                title,
                description,
                cost,
                location,
                departure,
                returnDate
        );
        assertNotNull(trip);

        Long tripTwo = tripService.createTrip(
                title,
                description,
                cost,
                location,
                departure,
                returnDate
        );
        assertNotNull(tripTwo);

        Long tripTree = tripService.createTrip(
                "Gondor",
                description,
                cost,
                "Lord Of The Rings",
                departure,
                returnDate
        );
        assertNotNull(trip);

        Long tripFour = tripService.createTrip(
                "Andurin",
                description,
                cost,
                "Lord Of The Rings",
                departure,
                returnDate
        );
        assertNotNull(tripTree);

        List<Trip> mordor = tripService.filterTripsByLocation(location);
        List<Trip> lordOfRings = tripService.filterTripsByLocation("Lord Of The Rings");

        assertEquals(2, mordor.size());
        assertEquals(2, lordOfRings.size());

    }

    @Test
    public void shouldFilterByCost(){

        Long trip = tripService.createTrip(
                title,
                description,
                cost,
                location,
                departure,
                returnDate
        );
        assertNotNull(trip);
        Long tripTwo = tripService.createTrip(
                title,
                description,
                cost,
                location,
                departure,
                returnDate
        );
        assertNotNull(tripTwo);


        // creating this class to "check" if the filter is accurate
        Long tripTree = tripService.createTrip(
                title,
                description,
                14999L,
                location,
                departure,
                returnDate
        );
        assertNotNull(tripTree);

        Long tripFour = tripService.createTrip(
                title,
                description,
                15001L,
                location,
                departure,
                returnDate
        );
        assertNotNull(tripFour);

        Long tripFive = tripService.createTrip(
                title,
                description,
                15001L,
                location,
                departure,
                returnDate
        );
        assertNotNull(tripFour);

        List<Trip> tripListOne = tripService.filterByCost(15000L);
        List<Trip> tripListTwo = tripService.filterByCost(15001L);

        assertEquals(2,tripListTwo.size());
        assertEquals(2,tripListTwo.size());
    }


}
