package com.onedata.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class BookDto {


    private Long bookId;

    private String title;

    private String author;

    private String isbn;

    private LocalDate publishedDate;

    private Integer availableCopies;


}
