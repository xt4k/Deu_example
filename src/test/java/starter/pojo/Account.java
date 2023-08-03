package starter.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Account {

    @JsonProperty("allow_tracking")
    public Boolean allowTracking;

    @JsonProperty("country_of_residence")
    public String countryOfResidence;

    @JsonProperty("two_factor_authentication")
    public Boolean twoFactorAuthentication;

    @JsonProperty("last_login")
    public String lastLogin;

    @JsonProperty("default_team")
    public String defaultTeam;

    @JsonProperty("verified")
    public Boolean verified;

    @JsonProperty("created_at")
    public String createdAt;

    @JsonProperty("identity_provider")
    public String identityProvider;

    @JsonProperty("federated")
    public Boolean federated;

    @JsonProperty("updated_at")
    public String updatedAt;

    @JsonProperty("name")
    public String name;

    @JsonProperty("delinquent_at")
    public String delinquentAt;

    @JsonProperty("id")
    public String id;

    @JsonProperty("default_organization")
    public String defaultOrganization;

    @JsonProperty("beta")
    public Boolean beta;

    @JsonProperty("email")
    public String email;

    @JsonProperty("sms_number")
    public String smsNumber;

    @JsonProperty("suspended_at")
    public String suspendedAt;

    public Account() {
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return
                "Response{" +
                        "allow_tracking = '" + allowTracking + '\'' +
                        ",country_of_residence = '" + countryOfResidence + '\'' +
                        ",two_factor_authentication = '" + twoFactorAuthentication + '\'' +
                        ",last_login = '" + lastLogin + '\'' +
                        ",default_team = '" + defaultTeam + '\'' +
                        ",verified = '" + verified + '\'' +
                        ",created_at = '" + createdAt + '\'' +
                        ",identity_provider = '" + identityProvider + '\'' +
                        ",federated = '" + federated + '\'' +
                        ",updated_at = '" + updatedAt + '\'' +
                        ",name = '" + name + '\'' +
                        ",delinquent_at = '" + delinquentAt + '\'' +
                        ",id = '" + id + '\'' +
                        ",default_organization = '" + defaultOrganization + '\'' +
                        ",beta = '" + beta + '\'' +
                        ",email = '" + email + '\'' +
                        ",sms_number = '" + smsNumber + '\'' +
                        ",suspended_at = '" + suspendedAt + '\'' +
                        "}";
    }
}