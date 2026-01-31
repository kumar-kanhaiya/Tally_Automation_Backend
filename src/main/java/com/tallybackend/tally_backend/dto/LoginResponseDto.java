package com.tallybackend.tally_backend.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LoginResponseDto {

    private String jwt ;
    private Long userId;
}
