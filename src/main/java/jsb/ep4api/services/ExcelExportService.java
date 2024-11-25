package jsb.ep4api.services;

import jakarta.servlet.http.HttpServletResponse;
import jsb.ep4api.payloads.responses.TransactionResponse;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Service
public class ExcelExportService {

    public void exportTransactions(
            List<TransactionResponse> transactions,
            OutputStream outputStream
    ) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Transactions Export File");

        Row headerRow = sheet.createRow(0);
        String headers[] = {
                "Code",
                "User ID",
                "Amount",
                "Content",
                "Gateway",
                "Status",
                "Created At",
        };

        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            CellStyle headerStyle = workbook.createCellStyle();
            Font font = workbook.createFont();
            font.setBold(true);
            headerStyle.setFont(font);
            cell.setCellStyle(headerStyle);
        }

        int rowNum = 1;
        for (TransactionResponse transaction : transactions) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(transaction.getCode());
            row.createCell(1).setCellValue(transaction.getUserId());
            row.createCell(2).setCellValue(transaction.getAmount());
            row.createCell(3).setCellValue(transaction.getContent());
            row.createCell(4).setCellValue(transaction.getGateway());
            row.createCell(5).setCellValue(transaction.getStatus());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDate = transaction.getCreatedAt().format(formatter);
            row.createCell(6).setCellValue(formattedDate);

        }

        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }

        workbook.write(outputStream);
        workbook.close();
    }
}
