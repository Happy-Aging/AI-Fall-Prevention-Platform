package happyaging.server.domain.question;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.stream.Stream;

public enum QuestionType {
    SENIOR_INFO("시니어 정보"),
    HEALTH("건강 위험도"),
    ENVIRONMENT("주거환경");

    private final String type;

    QuestionType(String type) {
        this.type = type;
    }

    @JsonCreator
    public static QuestionType toQuestionType(String questionType) {
        return Stream.of(QuestionType.values())
                .filter(value -> value.type.equals(questionType))
                .findFirst()
                .orElse(null);
    }

    public String getType() {
        return type;
    }
}
