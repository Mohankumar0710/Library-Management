package com.onedata.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Member {

    private Long id;
    private String name;
    private String phone;
    private LocalDate registeredDate;

}
