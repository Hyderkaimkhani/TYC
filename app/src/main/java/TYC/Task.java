package TYC;

/**
 * Created by hv on 2/27/16.
 */

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)

public class Task {

    @JsonProperty("_id")
    private String Id;
    @JsonProperty("TaskID")
    private String TaskID;
    @JsonProperty("Subject")
    private String Subject;
    @JsonProperty("Summary")
    private String Summary;
    @JsonProperty("AssignedBy")
    private String AssignedBy;
    @JsonProperty("AssignedTo")
    private String AssignedTo;
    @JsonProperty("Status")
    private String Status;
    @JsonProperty("Date")
    private String Date;
    @JsonProperty("loc")
    private Loc loc;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

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
     * @return The TaskID
     */
    @JsonProperty("TaskID")
    public String getTaskID() {
        return TaskID;
    }

    /**
     * @param TaskID The TaskID
     */
    @JsonProperty("TaskID")
    public void setTaskID(String TaskID) {
        this.TaskID = TaskID;
    }

    /**
     * @return The Subject
     */
    @JsonProperty("Subject")
    public String getSubject() {
        return Subject;
    }

    /**
     * @param Subject The Subject
     */
    @JsonProperty("Subject")
    public void setSubject(String Subject) {
        this.Subject = Subject;
    }

    /**
     * @return The Summary
     */
    @JsonProperty("Summary")
    public String getSummary() {
        return Summary;
    }

    /**
     * @param Summary The Summary
     */
    @JsonProperty("Summary")
    public void setSummary(String Summary) {
        this.Summary = Summary;
    }

    /**
     * @return The AssignedBy
     */
    @JsonProperty("AssignedBy")
    public String getAssignedBy() {
        return AssignedBy;
    }

    /**
     * @param AssignedBy The AssignedBy
     */
    @JsonProperty("AssignedBy")
    public void setAssignedBy(String AssignedBy) {
        this.AssignedBy = AssignedBy;
    }

    /**
     * @return The AssignedTo
     */
    @JsonProperty("AssignedTo")
    public String getAssignedTo() {
        return AssignedTo;
    }

    /**
     * @param AssignedTo The AssignedTo
     */
    @JsonProperty("AssignedTo")
    public void setAssignedTo(String AssignedTo) {
        this.AssignedTo = AssignedTo;
    }

    /**
     * @return The Status
     */
    @JsonProperty("Status")
    public String getStatus() {
        return Status;
    }

    /**
     * @param Status The Status
     */
    @JsonProperty("Status")
    public void setStatus(String Status) {
        this.Status = Status;
    }

    /**
     * @return The Date
     */
    @JsonProperty("Date")
    public String getDate() {
        return Date;
    }

    /**
     * @param Date The Date
     */
    @JsonProperty("Date")
    public void setDate(String Date) {
        this.Date = Date;
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
