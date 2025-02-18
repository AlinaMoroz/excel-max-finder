package org.myproject.xlsxmax.excelmaxfinder.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

@RequiredArgsConstructor
@Service
@Slf4j
public class ExcelService {


    public int findNthMax(String filePath, int n) throws FileNotFoundException{
        log.info("Начинаем обработку файла: {}", filePath);

        File file = validateFile(filePath);
        List<Integer> allNumbers = readNumbersFromExcel(file);

        return findNthMaxFromList(allNumbers, n);
    }

    private File validateFile(String filePath) throws FileNotFoundException {
        File file = new File("/app/files/" + filePath);
        if (!file.exists()) {
            log.error("Ошибка: Файл не найден -> {}", file.getAbsolutePath());
            throw new FileNotFoundException("Файл не найден: " + filePath);
        }
        return file;
    }

    private List<Integer> readNumbersFromExcel(File file) {
        try (Workbook workbook = new XSSFWorkbook(new FileInputStream(file))) {
            Sheet sheet = workbook.getSheetAt(0);
            log.info("Читаем данные из первого листа файла: {}", file.getAbsolutePath());

            return sheetToNumbers(sheet);
        } catch (IOException e) {
            log.error("Ошибка при чтении файла {}: {}", file.getAbsolutePath(), e.getMessage());
            throw new RuntimeException("Ошибка при чтении файла: " + file.getAbsolutePath(), e);
        }
    }

    private List<Integer> sheetToNumbers(Sheet sheet) {
        List<Integer> allNumbers = new ArrayList<>();

        for (Row row : sheet) {
            Cell cell = row.getCell(0);

            if (cell != null && cell.getCellType() == CellType.NUMERIC) {
                allNumbers.add((int) cell.getNumericCellValue());
            } else {
                log.warn("Пропущена пустая или некорректная ячейка в строке {}", row.getRowNum());
            }
        }
        return allNumbers;
    }

    private int findNthMaxFromList(List<Integer> numbers, int n) {
        PriorityQueue<Integer> limitedQueue = new PriorityQueue<>(n);

        if (numbers.size() < n) {
            throw new IllegalArgumentException("Ошибка: в файле меньше " + n + " чисел.");
        }

        for (int number : numbers) {
            if (limitedQueue.size() < n) {
                limitedQueue.add(number);
            } else if (number > limitedQueue.peek()) {
                limitedQueue.poll();
                limitedQueue.add(number);
            }
        }

        log.info("Обработка завершена. N-й максимум: {}", limitedQueue.peek());
        return limitedQueue.peek();

    }
}
