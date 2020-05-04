package travel.agency.frontend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import travel.agency.backend.entity.Purchase;
import travel.agency.backend.entity.Users;
import travel.agency.backend.service.PurchaseService;
import travel.agency.backend.service.UserService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.util.List;

/**
 * This class is adaptation of:
 * https://github.com/arcuri82/testing_security_development_enterprise_systems/blob/master/intro/exercise-solutions/quiz-game/part-11/frontend/src/main/java/org/tsdes/intro/exercises/quizgame/frontend/controller/UserInfoController.java
 */
@Named
@RequestScoped
public class UserInfoController {

    @Autowired
    private PurchaseService purchaseService;

    @Autowired
    private UserService userService;

    public String getUserName() {
        return ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
    }

    public Users getUser(){
        return userService.findUserByUserName(getUserName());
    }

    public List<Purchase> listPurchases() {
        return purchaseService.filterPurchasesByUser(getUserName());
    }
}
