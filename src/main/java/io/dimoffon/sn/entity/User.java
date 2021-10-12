package io.dimoffon.sn.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder=true)
public class User {

    private long id;
    private String username;
    private String password;
    private boolean enabled;
    private String firstName;
    private String lastName;
    private int age;
    private String gender;
    private String city;

}
