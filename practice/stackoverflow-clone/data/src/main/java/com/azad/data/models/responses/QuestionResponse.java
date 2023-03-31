package com.azad.data.models.responses;

import com.azad.data.models.pojos.AppUser;
import com.azad.data.models.pojos.Question;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter @Setter
public class QuestionResponse extends Question {

    private Long id;
    private AppUser user;
}
