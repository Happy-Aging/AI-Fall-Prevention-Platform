package happyaging.server.service;

import happyaging.server.dto.response.ResponseListDTO;
import happyaging.server.repository.ResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ResultService {
    private final ResultRepository resultRepository;

    @Transactional
    public void createResult(ResponseListDTO responseListDTO) {
        //TODO 인공지능 서버에 데이터를 보내고 값을 받아서 데이터베이스에 저장
    }
}
