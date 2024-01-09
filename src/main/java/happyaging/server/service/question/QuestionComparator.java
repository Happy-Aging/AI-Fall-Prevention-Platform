package happyaging.server.service.question;

import happyaging.server.domain.question.Question;
import java.util.Comparator;

public class QuestionComparator implements Comparator<Question> {
    @Override
    public int compare(Question firstQuestion, Question secondQuestion) {
        String[] firstQuestionNumber = firstQuestion.getNumber().split("-");
        String[] secondQuestionNumber = secondQuestion.getNumber().split("-");

        int mainNumberDifference = Integer.parseInt(firstQuestionNumber[0]) - Integer.parseInt(secondQuestionNumber[0]);
        if (mainNumberDifference == 0) {
            return Integer.parseInt(firstQuestionNumber[1]) - Integer.parseInt(secondQuestionNumber[1]);
        }
        return mainNumberDifference;
    }
}
