package io.dimoffon.sn.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserFilter {

    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private boolean withInterests;

}
