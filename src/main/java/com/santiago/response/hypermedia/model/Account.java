package com.santiago.response.hypermedia.model;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Account {

    private String id;
    private String type;
    private String nickname;
}
