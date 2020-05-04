package travel.agency.selenium.po;

import org.openqa.selenium.WebDriver;
import travel.agency.selenium.PageObject;

public class UserPO extends LayoutPO {

    public UserPO(WebDriver driver, String host, int port) {
        super(driver, host, port);
    }

    public UserPO(PageObject other) {
        super(other);
    }

    @Override
    public boolean isOnPage() {
        return getDriver().getTitle().contains("User page");
    }

    public String getUserName(){
        return getText("userNameID");
    }
}
