package TYC;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hv on 2/23/16.
 */
public class User {

    @JsonProperty("_id")
    private String Id;
    @JsonProperty("UserID")
    private String UserID;
    @JsonProperty("FName")
    private String FName;
    @JsonProperty("LName")
    private String LName;
    @JsonProperty("Company")
    private String Company;
    @JsonProperty("Email")
    private String Email;
    @JsonProperty("MgrID")
    private List<Object> MgrID = new ArrayList<Object>();
    @JsonProperty("Rating")
    private String Rating;
    @JsonProperty("Password")
    private String Password;
    @JsonProperty("loc")
    private Loc loc;


    /**
     * @return The Id
     */
    @JsonProperty("_id")
    public String getId() {
        return Id;
    }

    /**
     * @param Id The _id
     */
    @JsonProperty("_id")
    public void setId(String Id) {
        this.Id = Id;
    }

    /**
     * @return The UserID
     */
    @JsonProperty("UserID")
    public String getUserID() {
        return UserID;
    }

    /**
     * @param UserID The UserID
     */
    @JsonProperty("UserID")
    public void setUserID(String UserID) {
        this.UserID = UserID;
    }

    /**
     * @return The FName
     */
    @JsonProperty("FName")
    public String getFName() {
        return FName;
    }

    /**
     * @param FName The FName
     */
    @JsonProperty("FName")
    public void setFName(String FName) {
        this.FName = FName;
    }

    /**
     * @return The LName
     */
    @JsonProperty("LName")
    public String getLName() {
        return LName;
    }

    /**
     * @param LName The LName
     */
    @JsonProperty("LName")
    public void setLName(String LName) {
        this.LName = LName;
    }

    /**
     * @return The Company
     */
    @JsonProperty("Company")
    public String getCompany() {
        return Company;
    }

    /**
     * @param Company The Company
     */
    @JsonProperty("Company")
    public void setCompany(String Company) {
        this.Company = Company;
    }

    /**
     * @return The Email
     */
    @JsonProperty("Email")
    public String getEmail() {
        return Email;
    }

    /**
     * @param Email The Email
     */
    @JsonProperty("Email")
    public void setEmail(String Email) {
        this.Email = Email;
    }

    /**
     * @return The MgrID
     */
    @JsonProperty("MgrID")
    public List<Object> getMgrID() {
        return MgrID;
    }

    /**
     * @param MgrID The MgrID
     */
    @JsonProperty("MgrID")
    public void setMgrID(List<Object> MgrID) {
        this.MgrID = MgrID;
    }

    /**
     * @return The Rating
     */
    @JsonProperty("Rating")
    public String getRating() {
        return Rating;
    }

    /**
     * @param Rating The Rating
     */
    @JsonProperty("Rating")
    public void setRating(String Rating) {
        this.Rating = Rating;
    }

    /**
     * @return The Password
     */
    @JsonProperty("Password")
    public String getPassword() {
        return Password;
    }

    /**
     * @param Password The Password
     */
    @JsonProperty("Password")
    public void setPassword(String Password) {
        this.Password = Password;
    }

    /**
     * @return The loc
     */
    @JsonProperty("loc")
    public Loc getLoc() {
        return loc;
    }

    /**
     * @param loc The loc
     */
    @JsonProperty("loc")
    public void setLoc(Loc loc) {
        this.loc = loc;
    }

}