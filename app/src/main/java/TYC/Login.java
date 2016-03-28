package TYC;

/**
 * Created by hv on 2/24/16.
 */
import com.fasterxml.jackson.annotation.JsonProperty;

public class Login {
    @JsonProperty("email")
    private String email = null;
    @JsonProperty("password")
    private String password = null;
    /* Duration of the session, Defaults to 0, which means until browser is closed. */
  /*  @JsonProperty("duration")
    private Integer duration = null;
*/
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
