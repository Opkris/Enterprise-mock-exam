package travel.agency.frontend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import travel.agency.backend.entity.Users;
import travel.agency.backend.service.UserService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class UpdatePasswordController {
    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private boolean changePassword;

    private String previousPassword;

    private String password;

    private String confirmPassword;

    public boolean getChangePassword() {
        return changePassword;
    }

    public void setChangePassword(boolean changePassword) {
        this.changePassword = changePassword;
    }

    public String getPreviousPassword() {
        return previousPassword;
    }

    public void setPreviousPassword(String previousPassword) {
        this.previousPassword = previousPassword;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    private UserDetails getUserDetails() {
        return (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
    }

    public String changePassword() {
        Users user = userService.findUserByUserName(getUserDetails().getUsername());

        if (!passwordEncoder.matches(previousPassword, user.getHashedPassword()) || !password.equals(confirmPassword)) {
            return "/profile.jsf?faces-redirect=true&includeViewParams=true&error=true";
        }

        userService.updatePassword(user.getUserID(), password);

        return "/profile.jsf?faces-redirect=true";
    }
}
