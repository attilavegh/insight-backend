package hu.vattila.insight.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class SocialUser {
    private String authToken;
    private String email;
    private String firstName;
    private String id;
    private String idToken;
    private String lastName;
    private String name;
    private String photoUrl;
    private String provider;
}
