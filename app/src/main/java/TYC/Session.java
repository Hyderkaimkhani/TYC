package TYC;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ALL")
public class Session {
    /* Identifier for the current user. */
    @JsonProperty("id")
    private String id = null;
    /* Email address of the current user. */
    @JsonProperty("email")
    private String email = null;
    /* First name of the current user. */
    @JsonProperty("first_name")
    private String first_name = null;
    /* Last name of the current user. */
    @JsonProperty("last_name")
    private String last_name = null;
    /* Full display name of the current user. */
    @JsonProperty("name")
    private String name = null;
    /* Is the current user a system administrator. */
    @JsonProperty("is_sys_admin")
    private final Boolean is_sys_admin = null;
    /* Name of the role to which the current user is assigned. */
    @JsonProperty("role")
    private final String role = null;
    /* Date timestamp of the last login for the current user. */
    @JsonProperty("last_login_date")
    private final String last_login_date = null;
    /* App groups and the containing apps. */
    @JsonProperty("app_groups")
    private final List<SessionApp> app_groups = new ArrayList<>();
    /* Apps that are not in any app groups. */
    @JsonProperty("no_group_apps")
    private final List<SessionApp> no_group_apps = new ArrayList<>();
    /* Id for the current session, used in X-DreamFactory-Session-Token header for API requests. */
    @JsonProperty("session_id")
    private String session_id = null;
    /* Timed ticket that can be used to start a separate session. */
    @JsonProperty("ticket")
    private String ticket = null;
    /* Expiration time for the given ticket. */
    @JsonProperty("ticket_expiry")
    private String ticket_expiry = null;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirst_name() {
        return first_name;
    }
    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }
    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getName() {
        return name;
    }
    public void setName(String display_name) {
        this.name = display_name;
    }

    public String getSession_id() {
        return session_id;
    }
    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    public String getTicket() {
        return ticket;
    }
    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public String getTicket_expiry() {
        return ticket_expiry;
    }
    public void setTicket_expiry(String ticket_expiry) {
        this.ticket_expiry = ticket_expiry;
    }

    @Override
    public String toString()  {
        return "class Session {\n" + "  id: " + id + "\n" + "  email: " + email + "\n" + "  first_name: " + first_name + "\n" + "  last_name: " + last_name + "\n" + "  name: " + name + "\n" + "  is_sys_admin: " + is_sys_admin + "\n" + "  role: " + role + "\n" + "  last_login_date: " + last_login_date + "\n" + "  app_groups: " + app_groups + "\n" + "  no_group_apps: " + no_group_apps + "\n" + "  session_id: " + session_id + "\n" + "  ticket: " + ticket + "\n" + "  ticket_expiry: " + ticket_expiry + "\n" + "}\n";
       }
}