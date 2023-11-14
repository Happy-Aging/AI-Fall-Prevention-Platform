package happyaging.server.domain;

public enum Questions {
    QUESTION_1("1", "응답자 성별", ""),
    QUESTION_2("2", "어르신은 몇년생인가요?", ""),
    QUESTION_3("3", "현재 누구와 함께 사시나요?", ""),
    QUESTION_4("4", "살고 계신 집의 형태는 어떤 것인가요?", ""),
    QUESTION_6("5", "현재 살고 계신 집이나 주변 지역에 계단이나 비탈길이 많나요?", ""),
    QUESTION_9("6", "현재 노인장기요양등급 대상자이신가요?", ""),
    QUESTION_10_1("7-1", "가끔 보행 시 몸 움직임이 둔해지는 것을 느낀적이 있다.", ""),
    QUESTION_10_2("7-2", "집 안에서 걸어다닐때, 가구를 잡아야 한다든지 몸을 기대어 의지해야 지탱할 수 있다.", ""),
    QUESTION_10_3("7-3", "앉았다 일어설 때 의자나 방바닥을 짚고 손에 힘을 주어야만 한다", ""),
    QUESTION_10_4("7-4", "모퉁이를 돌거나 걷는 방향을 바꾸려고 할때의 걸음걸이가 쉽지않다.", ""),
    QUESTION_10_5("7-5", "지팡이나 실버카 등 보행기를 사용해야만 이동할 수 있다.", ""),
    QUESTION_11_1("8-1", "백내장이 있나요?", ""),
    QUESTION_11_2("8-2", "녹내장이 있나요?", ""),
    QUESTION_11_3("8-3", "황반변성이 있나요?", ""),
    QUESTION_12_1("9-1", "고혈압이 있나요?", ""),
    QUESTION_12_2("9-2", "당뇨가 있나요?", ""),
    QUESTION_12_3("9-3", "관절염이 있나요?", ""),
    QUESTION_12_4("9-4", "심장질환(협심증, 동맥질환)이 있나요?", ""),
    QUESTION_12_5("9-5", "뇌질환(중풍, 뇌졸중)이 있나요?", ""),
    QUESTION_12_7("9-6", "골다공증이 있나요?", ""),
    QUESTION_12_8("9-8", "기립성 저혈압이 있나요?", ""),
    QUESTION_12_11("9-11", "파킨슨병이 있나요?", ""),
    QUESTION_13_1("10-1", "빈뇨(자주 소변이 마려움)증상이 있나요?", ""),
    QUESTION_13_2("10-2", "긴박뇨가 있나요?", ""),
    QUESTION_13_3("10-3", "자다가 일어나서 화장실에 자주 가시나요?", ""),
    QUESTION_13_4("10-4", "설사나 변비가 있으신가요?", ""),
    QUESTION_13_5("10-5", "잔변이 있어서 불편하신가요?", ""),
    QUESTION_16("11", "최근 1년 이내에 낙상(미끄러지거나 걸려 넘어지신)경험이 있으신가요?", ""),
    QUESTION_27("12", "평소 30분 이상 운동을 얼마나 자주 하시나요?", ""),
    QUESTION_32_1("13-1", "문턱이나 단차가 있어서 화장실 출입이 어려운가요?", ""),
    QUESTION_32_2("13-2", "욕조나 샤워부스에 드나들기 어려운가요?", ""),
    QUESTION_32_3("13-3", "변기에 앉고 설 때 지지대가 필요한가요?", ""),
    QUESTION_32_4("13-4", "욕조/욕실/샤워부스 바닥 재질이 미끄러운가요?", ""),
    QUESTION_32_5("14-1", "침대에서 일어나기가 어려운가요?", ""),
    QUESTION_32_6("14-2", "침실 바닥이 미끄러운가요?", ""),
    QUESTION_32_9("15-1", "거실 바닥 재질이 미끄러운가요?", ""),
    QUESTION_32_10("15-2", "주위 정리정돈이 잘 안되어있나요?", ""),
    QUESTION_32_11("15-3", "문턱이나 단차가 있어서 걸려 넘어지기 쉬운가요?", ""),
    QUESTION_32_12("15-4", "현관사용이 불편한가요", "");

    private final String number;
    private final String question;
    private final String type;

    Questions(String number, String question, String type) {
        this.number = number;
        this.question = question;
        this.type = type;
    }

    public String getNumber() {
        return number;
    }

    public String getQuestion() {
        return question;
    }

    public String getType() {
        return type;
    }
}
