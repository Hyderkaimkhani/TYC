package com.example.hyder.tuc;

/**
 * Created by Hyder on 3/1/2016.
 */

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

public class RegisterUser {

    @JsonProperty("email")
    private String email;
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("last_name")
    private String lastName;
    @JsonProperty("password")
    private String newPassword;

    /**
     *
     * @return
     * The email
     */
    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    /**
     *
     * @param email
     * The email
     */
    @JsonProperty("email")
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *
     * @return
     * The firstName
     */
    @JsonProperty("first_name")
    public String getFirstName() {
        return firstName;
    }

    /**
     *
     * @param firstName
     * The first_name
     */
    @JsonProperty("first_name")
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     *
     * @return
     * The lastName
     */
    @JsonProperty("last_name")
    public String getLastName() {
        return lastName;
    }

    /**
     *
     * @param lastName
     * The last_name
     */
    @JsonProperty("last_name")
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     *
     * @return
     * The newPassword
     */
    @JsonProperty("password")
    public String getNewPassword() {
        return newPassword;
    }

    /**
     *
     * @param newPassword
     * The new_password
     */
    @JsonProperty("password")
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }


}