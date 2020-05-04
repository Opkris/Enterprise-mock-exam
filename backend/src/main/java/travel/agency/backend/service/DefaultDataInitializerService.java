package travel.agency.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.function.Supplier;

@Service
public class DefaultDataInitializerService {
    @Autowired
    private UserService userService;

    @Autowired
    private PurchaseService purchaseService;

    @Autowired
    private TripService tripService;

    @PostConstruct
    public void init() {
        //Create some users so that we can make purchases (with same username and name)
        String firstUser = "admin";
        String secondUser = "foo";
        String thirdUser = "bar";

        attempt(() -> userService.createUser(
                firstUser, firstUser, "admin-last-name", "123", "admin@email.com", "admin"));

        attempt(() -> userService.createUser(
                secondUser, secondUser, "foo-last-name", "123", "foo@email.com", "user"));

        attempt(() -> userService.createUser(
                thirdUser, thirdUser, "bar-last-name", "123", "bar@email.com", "user"));

        //Create some trips to purchase
        Long bosnianTrip = attempt(() ->
                tripService.createTrip(
                        "Trip to bosnia",
                        "This is awesome trip to small country of Bosnia",
                        3000L,
                        "Mostar",
                        LocalDate.now(),
                        LocalDate.of(2020, 6, 6))
        );
        Long norwegianTrip = attempt(() ->
                tripService.createTrip(
                        "Trip to Norway",
                        "Trip to Svalbard, one amazing place in Norway",
                        5000L,
                        "Svalbard",
                        LocalDate.now(),
                        LocalDate.of(2020, 9, 26))
        );
        Long italianTrip = attempt(() ->
                tripService.createTrip(
                        "Trip to Italy",
                        "Visit Pisa, not only famous for leaning tower",
                        3200L,
                        "Pisa",
                        LocalDate.now(),
                        LocalDate.of(2020, 9, 26))
        );
        Long secondItalianTrip = attempt(() ->
                tripService.createTrip(
                        "Trip to Italy",
                        "Visit Rome, city of AS Roma, Colosseum and many other things",
                        4000L,
                        "Rome",
                        LocalDate.of(2021, 2, 22),
                        LocalDate.of(2021, 3, 2))
        );
        Long usaTrip = attempt(() ->
                tripService.createTrip(
                        "Trip to USA",
                        "Visit New York, the big apple, city that never sleeps",
                        5000L,
                        "New York",
                        LocalDate.of(2021, 2, 22),
                        LocalDate.of(2021, 3, 2))
        );
        Long germanTrip = attempt(() ->
                tripService.createTrip(
                        "Trip to Germany",
                        "Visit Munich, city of FC Bayern",
                        4000L,
                        "Munich",
                        LocalDate.of(2021, 2, 22),
                        LocalDate.of(2021, 3, 2))
        );

        //******************************************************************'
        //******************************************************************'
        //******************************************************************'

        Long secondGermanTrip = attempt(() ->
                tripService.createTrip(
                        "Trip to Germany",
                        "Germany's capital, dates to the 13th century",
                        4000L,
                        "Berlin",
                        LocalDate.of(2021, 2, 22),
                        LocalDate.of(2021, 3, 2))
        );

        Long danishTrip = attempt(() ->
                tripService.createTrip(
                        "Trip to Denmark",
                        "Denmark's capital",
                        4000L,
                        "Munich",
                        LocalDate.of(2021, 2, 22),
                        LocalDate.of(2021, 3, 2))
        );

        Long englandTrip = attempt(() ->
                tripService.createTrip(
                        "Trip to England",
                        "the capital of England and the United Kingdom",
                        5000L,
                        "London",
                        LocalDate.of(2021, 2, 22),
                        LocalDate.of(2021, 3, 2))
        );

        Long secondEnglandTrip = attempt(() ->
                tripService.createTrip(
                        "Trip to England",
                        "Birmingham is a major city in England's west midlands region",
                        5500L,
                        "Birmingham",
                        LocalDate.of(2021, 2, 22),
                        LocalDate.of(2021, 3, 2))
        );

        Long theadEnglandTrip = attempt(() ->
                tripService.createTrip(
                        "Trip to England",
                        "Hereford is a cathedral city",
                        5500L,
                        "Hereford",
                        LocalDate.of(2022, 5, 22),
                        LocalDate.of(2022, 7, 2))
        );

        Long austriaTrip = attempt(() ->
                tripService.createTrip(
                        "Trip to Austria",
                        "Austria's capital. lies in the country east on the Danube River",
                        6000L,
                        "Vienna",
                        LocalDate.of(2021, 2, 22),
                        LocalDate.of(2021, 3, 2))
        );

        Long secondAustriaTrip = attempt(() ->
                tripService.createTrip(
                        "Trip to Austria",
                        "Graz is the Capital of the southem Austrian province of Syria",
                        6000L,
                        "Graz",
                        LocalDate.of(2021, 2, 22),
                        LocalDate.of(2021, 3, 2))
        );







        //Okay, lets start purchasing
        //Bosnian trip
        purchaseService.newPurchase(bosnianTrip,firstUser);
        purchaseService.newPurchase(bosnianTrip,thirdUser);
        //Norwegian trip
        purchaseService.newPurchase(norwegianTrip,secondUser);
        purchaseService.newPurchase(norwegianTrip,thirdUser);
        //First Italian trip
        purchaseService.newPurchase(italianTrip,firstUser);
        purchaseService.newPurchase(italianTrip,secondUser);
        purchaseService.newPurchase(italianTrip,thirdUser);
        //Second italian trip
        purchaseService.newPurchase(secondItalianTrip, secondUser);
        //German trip
        purchaseService.newPurchase(secondItalianTrip, secondUser);

    }

    private <T> T attempt(Supplier<T> lambda) {
        try {
            return lambda.get();
        } catch (Exception e) {
            return null;
        }
    }
}