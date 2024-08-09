package com.onedata.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Borrow {
    private Long id;

    @NotNull(message = "Member ID cannot be null")
    private Long memberId;

    @NotNull(message = "Book ID cannot be null")
    private Long bookId;

    @NotNull(message = "Borrow date cannot be null")
    private LocalDate borrowDate;

    @NotNull(message = "Due date cannot be null")
    private LocalDate dueDate;

}
