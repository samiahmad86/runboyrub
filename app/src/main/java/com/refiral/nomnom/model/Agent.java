package com.refiral.nomnom.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by tanay on 14/8/15.
 */
public class Agent {
    @Expose
    private long id;
    @Expose
    private String password;
    @SerializedName("last_login")
    @Expose
    private String lastLogin;
    @SerializedName("created_on")
    @Expose
    private String createdOn;
    @SerializedName("updated_on")
    @Expose
    private String updatedOn;
    @Expose
    private String name;
    @Expose
    private String username;
    @SerializedName("contact_number")
    @Expose
    private String contactNumber;
    @SerializedName("permanent_address")
    @Expose
    private String permanentAddress;
    @SerializedName("cogent_agent_id")
    @Expose
    private String cogentAgentId;
    @SerializedName("current_address")
    @Expose
    private String currentAddress;
    @SerializedName("agent_type")
    @Expose
    private String agentType;
    @SerializedName("is_logged_in")
    @Expose
    private boolean isLoggedIn;
}
