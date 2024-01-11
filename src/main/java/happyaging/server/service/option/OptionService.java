package happyaging.server.service.option;

import happyaging.server.domain.option.Option;
import happyaging.server.exception.AppException;
import happyaging.server.exception.errorcode.AppErrorCode;
import happyaging.server.repository.option.OptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OptionService {

    private final OptionRepository optionRepository;

    @Transactional(readOnly = true)
    public Option findOption(Long choiceId) {
        return optionRepository.findById(choiceId)
                .orElseThrow(() -> new AppException(AppErrorCode.INVALID_OPTION));
    }
}
