package fk.retail.ip.core.poi;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author pragalathan.m
 */
@Slf4j
public class SpreadSheetWriter {

    private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd_HHmmss.S");

    public void populateTemplate(InputStream template, OutputStream out, List<Map<String, Object>> records) throws InvalidFormatException, IOException {
        Path tempFile = Files.createTempFile(null, format.format(new Date()));
        Files.copy(template, tempFile, StandardCopyOption.REPLACE_EXISTING);

//        try (OPCPackage pkg = OPCPackage.open(tempFile.toFile())) {
//            XSSFWorkbook wb = new XSSFWorkbook(pkg);
//            Sheet sheet = wb.getSheetAt(0);
//            //sheet.protectSheet("uneditable");
//            List<String> headers = new ArrayList<>();
//            Row headerRow = sheet.getRow(0);
//            for (int c = 0; c < headerRow.getLastCellNum(); c++) {
//                Cell cell = headerRow.getCell(c);
//                if (cell == null) {
//                    continue;
//                }
//                headers.add(cell.getStringCellValue());
//            }
//
//            // read csv and write to spreadsheet
//            for (int r = 0; r < records.size(); r++) {
//                Map<String, Object> record = records.get(r);
//                Row row = sheet.createRow(r + 1);
//                for (int c = 0; c < headers.size(); c++) {
//                    if (headers.get(c).trim().isEmpty()) {
//                        break;
//                    }
//                    Object value = record.get(headers.get(c));
//                    Cell cell = row.getCell(c, Row.CREATE_NULL_AS_BLANK);
//                    setCellValue(value, cell);
//                    applyCellStyle(wb, cell, headers.get(c));
//                }
//            }
//            wb.write(out);
//        }


        List<String> headers = new ArrayList<>();

        try (OPCPackage pkg = OPCPackage.open(tempFile.toFile())) {
            XSSFWorkbook wb = new XSSFWorkbook(pkg);
            Sheet sheet = wb.getSheetAt(0);
            //sheet.protectSheet("uneditable");

            Row headerRow = sheet.getRow(0);
            for (int c = 0; c < headerRow.getLastCellNum(); c++) {
                Cell cell = headerRow.getCell(c);
                if (cell == null) {
                    continue;
                }
                headers.add(cell.getStringCellValue());
            }
        }

        SXSSFWorkbook wb1 = new SXSSFWorkbook(100);
        Sheet sh = wb1.createSheet();

        Row headerRow = sh.createRow(0);

        log.info("Memory {}", Runtime.getRuntime().freeMemory());
        for (int c = 0; c < headers.size(); c++) {
            if (headers.get(c).trim().isEmpty()) {
                break;
            }
            Cell cell = headerRow.getCell(c, Row.CREATE_NULL_AS_BLANK);
            setCellValue(headers.get(c), cell);
            applyCellStyle(wb1, cell, headers.get(c));
            log.info("Writing to disk");
        }

        log.info("Memory {}", Runtime.getRuntime().freeMemory());


        for (int r = 0; r < records.size(); r++) {
            Map<String, Object> record = records.get(r);
            Row row = sh.createRow(r + 1);
            for (int c = 0; c < headers.size(); c++) {
                if (headers.get(c).trim().isEmpty()) {
                    break;
                }
                Object value = record.get(headers.get(c));
                Cell cell = row.getCell(c, Row.CREATE_NULL_AS_BLANK);
                setCellValue(value, cell);
                applyCellStyle(wb1, cell, headers.get(c));
            }
            log.info("Memory {}", Runtime.getRuntime().freeMemory());
        }

        wb1.write(out);
        out.close();
        wb1.dispose();
    }


    private void setCellValue(Object value, Cell cell) {
        if (value == null) {
            return;
        }
        if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
            cell.setCellValue(NumberUtils.toDouble(value.toString()));
        } else {
            cell.setCellValue(value.toString());
        }
    }

    protected void applyCellStyle(SXSSFWorkbook wb, Cell cell, String columnName) {
        CellStyle editableStyle = wb.createCellStyle();
        editableStyle.setLocked(false);
        cell.setCellStyle(editableStyle);
    }
}