package org.myproject.xlsxmax.excelmaxfinder.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.web.server.ResponseStatusException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.myproject.xlsxmax.excelmaxfinder.service.ExcelService;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.*;


import java.io.FileNotFoundException;

import java.util.NoSuchElementException;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class ExcelController {

    private final ExcelService excelService;


    @Operation(summary = "Найти N-й максимум в XLSX-файле")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешный запрос, возвращает N-й максимум."),
            @ApiResponse(responseCode = "400", description = "Некорректные параметры запроса."),
            @ApiResponse(responseCode = "404", description = "Файл не найден."),
            @ApiResponse(responseCode = "422", description = "Недостаточно данных для вычисления N-го максимума."),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера.")
    })
    @GetMapping("/find-max-n")
    public int findNthMax(@RequestParam String fileName, @RequestParam  int n) {
        try {
            return excelService.findNthMax(fileName, n);
        } catch (FileNotFoundException e) {
            log.error("Файл не найден: {}", fileName, e);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (IllegalArgumentException e) {
            log.warn("Некорректный запрос: {}", e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (NoSuchElementException e) {
            log.error("Недостаточно данных: {}", e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Ошибка: " + e.getMessage());
        } catch (Exception e) {
            log.error("Ошибка сервера: {}", e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Ошибка сервера: " + e.getMessage());
        }
    }
}
