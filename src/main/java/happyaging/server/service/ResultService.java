package happyaging.server.service;

import happyaging.server.domain.Response;
import happyaging.server.repository.ResultRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ResultService {
    private final ResultRepository resultRepository;

    @Transactional
    public void createResult(List<Response> responses) {
        //TODO 응답 결과와 응답 질문을 하나로 합치는 기능
        //TODO 합친것에 번호를 매기는 기능
        //TODO total score를 계산하는 기능
        //TODO rank를 계산하는 기능
        //TODO 인공지능 서버에 데이터를 보내고 파일 저장 위치와 summary를 받는 기능
    }
}
