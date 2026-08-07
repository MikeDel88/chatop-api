package com.project.chatop.exception;

import com.project.chatop.dto.response.ErrorResponse;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ProblemDetail;

import java.util.List;

@Getter
@Setter
public class BodyProblemDetail extends ProblemDetail {

    private List<ErrorResponse> errors;

    protected BodyProblemDetail(ProblemDetail problemDetail) {
        super(problemDetail);
    }

    public static BodyProblemDetail from(ProblemDetail problemDetail) {
        return new BodyProblemDetail(problemDetail);
    }
}
