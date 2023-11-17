//package happyaging.server.domain;
//
//public enum ResponseScore {
//    QUESTION_1_YES("QUESTION_1", "예", 1),
//    QUESTION_1_NO("QUESTION_1", "아니오", 2),
//    // 다른 질문과 응답 추가
//    ;
//
//    private final String questionId;
//    private final String response;
//    private final int score;
//
//    ResponseScore(String questionId, String response, int score) {
//        this.questionId = questionId;
//        this.response = response;
//        this.score = score;
//    }
//
//    public String getQuestionId() {
//        return questionId;
//    }
//
//    public String getResponse() {
//        return response;
//    }
//
//    public int getScore() {
//        return score;
//    }
//
//    public static int getScore(String questionId, String response) {
//        for (ResponseScore rs : ResponseScore.values()) {
//            if (rs.getQuestionId().equals(questionId) && rs.getResponse().equals(response)) {
//                return rs.getScore();
//            }
//        }
//        return 1; // 기본 점수
//    }
//}
//
