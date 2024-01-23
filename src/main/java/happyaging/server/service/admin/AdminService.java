package happyaging.server.service.admin;

import happyaging.server.domain.question.Question;
import happyaging.server.dto.admin.survey.ExcelDataDTO;
import happyaging.server.exception.AppException;
import happyaging.server.exception.errorcode.AppErrorCode;
import happyaging.server.repository.question.QuestionRepository;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final QuestionRepository questionRepository;

    public byte[] createExcelFile(List<ExcelDataDTO> data) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = createSheet(workbook);
        addDataToSheet(sheet, data);
        return convertToFileStream(workbook);
    }

    private void addDataToSheet(Sheet sheet, List<ExcelDataDTO> data) {
        List<Question> questions = questionRepository.findAll();

        int rowNumber = 2;
        for (ExcelDataDTO dto : data) {
            Row row = sheet.createRow(rowNumber++);
            int colNumber = 1;
            row.createCell(colNumber++).setCellValue(dto.getUserId());
            row.createCell(colNumber++).setCellValue(dto.getUserName());
            row.createCell(colNumber++).setCellValue(dto.getSeniorId());
            row.createCell(colNumber++).setCellValue(dto.getSeniorName());
            row.createCell(colNumber++).setCellValue(dto.getAddress());
            row.createCell(colNumber++).setCellValue(dto.getBirth().toString());
            row.createCell(colNumber++).setCellValue(dto.getSex().getDescription());
            row.createCell(colNumber++).setCellValue(dto.getSurveyAt().toString());
            row.createCell(colNumber++).setCellValue(dto.getRank());
            Map<String, String> responsesByNumber = dto.getResponsesByNumber();
            for (Question question : questions) {
                row.createCell(colNumber++).setCellValue(responsesByNumber.getOrDefault(question.getNumber(), ""));
            }
        }
    }

    private byte[] convertToFileStream(Workbook workbook) {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            workbook.write(bos);
            return bos.toByteArray();
        } catch (IOException exception) {
            throw new AppException(AppErrorCode.DISABLE_TO_CONVERT_EXCEL);
        }
    }

    private Sheet createSheet(Workbook workbook) {
        try {
            Sheet sheet = workbook.createSheet(LocalDate.now() + "_낙상예방측정결과");
            Row headerRow = sheet.createRow(1);

            headerRow.createCell(1).setCellValue("유저 ID");
            headerRow.createCell(2).setCellValue("유저 이름");
            headerRow.createCell(3).setCellValue("시니어 ID");
            headerRow.createCell(4).setCellValue("시니어 이름");
            headerRow.createCell(5).setCellValue("주소");
            headerRow.createCell(6).setCellValue("출생년도");
            headerRow.createCell(7).setCellValue("성별");
            headerRow.createCell(8).setCellValue("설문 측정일");
            headerRow.createCell(9).setCellValue("낙상 위험도");
            headerRow.createCell(10).setCellValue("Q1");
            headerRow.createCell(11).setCellValue("Q2");
            headerRow.createCell(12).setCellValue("Q3-1");
            headerRow.createCell(13).setCellValue("Q3-2");
            headerRow.createCell(14).setCellValue("Q3-3");
            headerRow.createCell(15).setCellValue("Q4-1");
            headerRow.createCell(16).setCellValue("Q4-2");
            headerRow.createCell(17).setCellValue("Q4-3");
            headerRow.createCell(18).setCellValue("Q4-4");
            headerRow.createCell(19).setCellValue("Q4-5");
            headerRow.createCell(20).setCellValue("Q4-6");
            headerRow.createCell(21).setCellValue("Q4-7");
            headerRow.createCell(22).setCellValue("Q4-8");
            headerRow.createCell(23).setCellValue("Q5-1");
            headerRow.createCell(24).setCellValue("Q5-2");
            headerRow.createCell(25).setCellValue("Q5-3");
            headerRow.createCell(26).setCellValue("Q6-1");
            headerRow.createCell(27).setCellValue("Q6-2");
            headerRow.createCell(28).setCellValue("Q6-3");
            headerRow.createCell(29).setCellValue("Q6-4");
            headerRow.createCell(30).setCellValue("Q6-5");
            headerRow.createCell(31).setCellValue("Q7-1");
            headerRow.createCell(32).setCellValue("Q7-2");
            headerRow.createCell(33).setCellValue("Q7-3");
            headerRow.createCell(34).setCellValue("Q7-4");
            headerRow.createCell(35).setCellValue("Q7-5");
            headerRow.createCell(36).setCellValue("Q8");
            headerRow.createCell(37).setCellValue("Q9-1");
            headerRow.createCell(38).setCellValue("Q9-2");
            headerRow.createCell(39).setCellValue("Q9-3");
            headerRow.createCell(40).setCellValue("Q9-4");
            headerRow.createCell(41).setCellValue("Q9-5");
            headerRow.createCell(42).setCellValue("Q9-6");
            headerRow.createCell(43).setCellValue("Q9-7");
            headerRow.createCell(44).setCellValue("Q9-8");
            headerRow.createCell(45).setCellValue("Q9-9");
            headerRow.createCell(46).setCellValue("Q9-10");
            headerRow.createCell(47).setCellValue("Q10");
            headerRow.createCell(48).setCellValue("Q11");
            headerRow.createCell(49).setCellValue("Q12-1");
            headerRow.createCell(50).setCellValue("Q12-2");
            headerRow.createCell(51).setCellValue("Q13-1");
            headerRow.createCell(52).setCellValue("Q13-2");
            headerRow.createCell(53).setCellValue("Q13-3");
            headerRow.createCell(54).setCellValue("Q13-4");
            headerRow.createCell(55).setCellValue("Q13-5");
            headerRow.createCell(56).setCellValue("Q13-6");
            headerRow.createCell(57).setCellValue("Q14-1");
            headerRow.createCell(58).setCellValue("Q14-2");
            headerRow.createCell(59).setCellValue("Q14-3");
            headerRow.createCell(60).setCellValue("Q14-4");
            headerRow.createCell(61).setCellValue("Q14-5");
            return sheet;
        } catch (Exception exception) {
            throw new AppException(AppErrorCode.DISABLE_TO_CONVERT_EXCEL);
        }
    }
}
