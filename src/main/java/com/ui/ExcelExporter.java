package com.ui;

import com.model.Checada;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Clase utilitaria para exportar registros de checadas a un archivo Excel (.xlsx).
 *
 * <p>
 * Utiliza Apache POI para generar un archivo con formato de tabla, incluyendo encabezados
 * en negrita, bordes de celdas y autoajuste de columnas.
 * </p>
 */
public class ExcelExporter {

    /**
     * Exporta una lista de checadas a un archivo Excel.
     *
     * <p>
     * Cada fila representa una checada y las columnas incluyen:
     * ID Registro, ID Empleado, Nombre, Apellido Paterno, Apellido Materno,
     * Ruta, Fecha y Hora.
     * </p>
     *
     * @param checadas   lista de objetos Checada a exportar
     * @param rutaArchivo ruta completa del archivo Excel de salida
     * @throws IOException si ocurre un error al crear o escribir el archivo
     */
    public static void exportarChecadas(List<Checada> checadas, String rutaArchivo) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Checadas");

        // === Estilo para encabezado ===
        CellStyle headerStyle = workbook.createCellStyle();
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 12);
        headerStyle.setFont(headerFont);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);

        // === Estilo para datos (bordes gris claro) ===
        CellStyle dataStyle = workbook.createCellStyle();
        dataStyle.setBorderBottom(BorderStyle.THIN);
        dataStyle.setBorderTop(BorderStyle.THIN);
        dataStyle.setBorderLeft(BorderStyle.THIN);
        dataStyle.setBorderRight(BorderStyle.THIN);
        short greyIndex = IndexedColors.GREY_25_PERCENT.getIndex();
        dataStyle.setBottomBorderColor(greyIndex);
        dataStyle.setTopBorderColor(greyIndex);
        dataStyle.setLeftBorderColor(greyIndex);
        dataStyle.setRightBorderColor(greyIndex);

        // === Crear encabezado ===
        Row header = sheet.createRow(0);
        String[] columnas = {"ID Registro", "ID Empleado", "Nombre", "Apellido Paterno", "Apellido Materno", "Ruta", "Fecha", "Hora"};
        for (int i = 0; i < columnas.length; i++) {
            Cell cell = header.createCell(i);
            cell.setCellValue(columnas[i]);
            cell.setCellStyle(headerStyle);
        }

        // === Llenar datos ===
        int rowNum = 1;
        for (Checada c : checadas) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(c.getIdChecada());
            row.createCell(1).setCellValue(c.getIdEmpleado());
            row.createCell(2).setCellValue(c.getNombre());
            row.createCell(3).setCellValue(c.getApellidoPaterno());
            row.createCell(4).setCellValue(c.getApellidoMaterno());
            row.createCell(5).setCellValue(c.getRuta());
            row.createCell(6).setCellValue(c.getFechaEntrada());
            row.createCell(7).setCellValue(c.getHoraEntrada());

            // Aplicar estilo de datos
            for (int i = 0; i < columnas.length; i++) {
                row.getCell(i).setCellStyle(dataStyle);
            }
        }

        // === Autoajustar columnas al contenido ===
        for (int i = 0; i < columnas.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // === Guardar archivo ===
        try (FileOutputStream fileOut = new FileOutputStream(rutaArchivo)) {
            workbook.write(fileOut);
        }
        workbook.close();
    }
}
