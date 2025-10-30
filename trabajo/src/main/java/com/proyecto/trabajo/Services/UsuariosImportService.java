package com.proyecto.trabajo.Services;

import java.io.InputStream;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.proyecto.trabajo.dto.UsuariosCreateDto;

@Service
public class UsuariosImportService {
    private static final Logger log = LoggerFactory.getLogger(UsuariosImportService.class);

    private final UsuariosServices usuariosServices;

    public UsuariosImportService(UsuariosServices usuariosServices) {
        this.usuariosServices = usuariosServices;
    }
    public Map<String, Object> importFromExcel(InputStream is) throws Exception {
        try (Workbook workbook = WorkbookFactory.create(is)) {
            Sheet sheet = workbook.getSheetAt(0);
            int lastRow = sheet.getLastRowNum();
            java.util.List<Object> created = new java.util.ArrayList<>();
            java.util.List<Map<String, Object>> errors = new java.util.ArrayList<>();

            for (int i = 1; i <= lastRow; i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;
                try {
                    UsuariosCreateDto dto = createDtoFromRow(row);
                    if (dto == null) {
                        errors.add(Map.of("fila", i + 1, "error", "Fila vacía o inválida"));
                        continue;
                    }
                    var creado = usuariosServices.guardar(dto);
                    created.add(creado);
                } catch (Exception ex) {
                    log.debug("Error procesando fila {}: {}", i + 1, ex.getMessage());
                    errors.add(Map.of("fila", i + 1, "error", ex.getMessage()));
                }
            }

            return Map.of("creados", created.size(), "detallesCreacion", created, "errores", errors);
        }
    }

    private UsuariosCreateDto createDtoFromRow(Row row) {
        String nom = getCellString(row.getCell(0));
        String ape = getCellString(row.getCell(1));
        String correo = getCellString(row.getCell(2));
        String password = getCellString(row.getCell(3));
        Long numDoc = getCellLong(row.getCell(4));
        Long idTipoDoc = getCellLong(row.getCell(5));
        Long idRole = getCellLong(row.getCell(6));

        if (nom == null && ape == null && correo == null && password == null && numDoc == null && idTipoDoc == null && idRole == null) {
            return null;
        }

        UsuariosCreateDto dto = new UsuariosCreateDto();
        dto.setNom_su(nom);
        dto.setApe_su(ape);
        dto.setCorre(correo);
        dto.setPasword(password);
        dto.setNum_docu(numDoc);
        dto.setEstad((byte) 1);
        dto.setId_tip_docu(idTipoDoc);
        dto.setId_role(idRole);
        return dto;
    }

    private String getCellString(Cell cell) {
        if (cell == null) return null;
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim();
            case NUMERIC:
                return String.valueOf((long) cell.getNumericCellValue());
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                try { return cell.getStringCellValue(); } catch (Exception e) { return null; }
            case BLANK:
            default:
                return null;
        }
    }

    private Long getCellLong(Cell cell) {
        if (cell == null) return null;
        try {
            switch (cell.getCellType()) {
                case STRING:
                    String s = cell.getStringCellValue().trim();
                    if (s.isEmpty()) return null;
                    return Long.parseLong(s.replaceAll("\\.0$", ""));
                case NUMERIC:
                    return (long) cell.getNumericCellValue();
                case FORMULA:
                    try { return Long.parseLong(cell.getStringCellValue()); } catch (Exception e) { return null; }
                default:
                    return null;
            }
        } catch (Exception e) {
            return null;
        }
    }
}
