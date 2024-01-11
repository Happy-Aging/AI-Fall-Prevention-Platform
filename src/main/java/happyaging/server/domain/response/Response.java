package happyaging.server.domain.response;

import happyaging.server.domain.option.Option;
import happyaging.server.domain.question.Question;
import happyaging.server.domain.survey.Survey;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Getter
public class Response {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "response_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "survey_id")
    private Survey survey;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    @ManyToOne
    @JoinColumn(name = "option_id")
    private Option option;

    private String subjectiveContext;

    public static Response create(Survey survey, Question question, Option option, String subjectiveContext) {
        return Response.builder()
                .survey(survey)
                .question(question)
                .option(option)
                .subjectiveContext(subjectiveContext)
                .build();
    }
}
