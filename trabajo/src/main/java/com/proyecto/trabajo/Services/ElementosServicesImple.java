package com.proyecto.trabajo.Services;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.proyecto.trabajo.Mapper.ElementosMapper;
import com.proyecto.trabajo.dto.ElementoDto;
import com.proyecto.trabajo.dto.ElementoUpdateDtos;
import com.proyecto.trabajo.dto.ElementosCreateDto;
import com.proyecto.trabajo.models.Elementos;
import com.proyecto.trabajo.repository.ElementosRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ElementosServicesImple implements ElementosServices {

    private final ElementosRepository elementosRepository;
    private final ElementosMapper elementosMapper;

    public ElementosServicesImple(ElementosRepository elementosRepository, ElementosMapper elementosMapper) {
        this.elementosRepository = elementosRepository;
        this.elementosMapper = elementosMapper;
    }

    @Override
    public byte[] generarPlantilla() {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Plantilla Elementos");
            String[] headers = new String[] {"nom_eleme", "obse", "num_seri", "componen", "est_elem", "marc", "id_subcat"};
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < headers.length; i++) {
                headerRow.createCell(i).setCellValue(headers[i]);
            }


            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            try (java.io.ByteArrayOutputStream bos = new java.io.ByteArrayOutputStream()) {
                workbook.write(bos);
                return bos.toByteArray();
            }
        } catch (Exception ex) {
            throw new RuntimeException("Error generando plantilla: " + ex.getMessage(), ex);
        }
    }

    @Override
    public ElementoDto guardar(ElementosCreateDto dto) {
        Elementos elementos = elementosMapper.toElementosFromCreateDto(dto);
        Elementos guardado = elementosRepository.save(elementos);
        return elementosMapper.toElementoDto(guardado);
    }

    @Override
    public ElementoDto buscarPorId(Long id) {
        return elementosRepository.findById(id)
                .map(elementosMapper::toElementoDto)
                .orElseThrow(() -> new EntityNotFoundException("Elemento no encontrado"));
    }

    @Override
    public List<ElementoDto> listarTodos() {
        return elementosRepository.findAll()
                .stream()
                .map(elementosMapper::toElementoDto)
                .collect(Collectors.toList());
    }

    @Override
    public void eliminar(Long id) {
        elementosRepository.deleteById(id);
    }

    @Override
    public ElementoDto actualizarElemento(Long id, ElementoUpdateDtos dto) {
        Elementos entity = elementosRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Elemento no encontrado"));
        elementosMapper.updateElementosFromUpdateDto(dto, entity);
        Elementos actualizado = elementosRepository.save(entity);
        return elementosMapper.toElementoDto(actualizado);
    }

    @Override
    public Map<String, Object> guardarMasivo(MultipartFile file) {
        Map<String, Object> resultado = new HashMap<>();
        List<String> errores = new ArrayList<>();
        int total = 0;
        int guardados = 0;

        try {
            // Guardar copia del archivo
            Path uploadDir = Paths.get("uploads");
            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
            }
            String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path target = uploadDir.resolve(filename);
            try (InputStream is = file.getInputStream()) {
                Files.copy(is, target, StandardCopyOption.REPLACE_EXISTING);
            }

            // Procesar Excel
            try (InputStream is = file.getInputStream(); Workbook workbook = WorkbookFactory.create(is)) {
                Sheet sheet = workbook.getNumberOfSheets() > 0 ? workbook.getSheetAt(0) : null;
                if (sheet == null) {
                    resultado.put("total", 0);
                    resultado.put("guardados", 0);
                    resultado.put("errores", List.of("Archivo sin hojas o vacío"));
                    return resultado;
                }

                List<Elementos> batch = new ArrayList<>();
                for (int r = sheet.getFirstRowNum(); r <= sheet.getLastRowNum(); r++) {
                    Row row = sheet.getRow(r);
                    if (row == null) continue;
                    // Suponemos que la primera fila es cabecera. Saltarla si es la primera.
                    if (r == sheet.getFirstRowNum()) continue;
                    total++;
                    try {
                        // Columnas esperadas (orden): nom_eleme, obse, num_seri, componen, est_elem, marc, id_subcat
                        String nom = getStringCell(row.getCell(0));
                        String obse = getStringCell(row.getCell(1));
                        String numSeri = getStringCell(row.getCell(2));
                        String componen = getStringCell(row.getCell(3));
                        Byte estElem = getByteCell(row.getCell(4));
                        String marc = getStringCell(row.getCell(5));
                        Long idSubcat = getLongCell(row.getCell(6));

                        ElementosCreateDto dto = new ElementosCreateDto(nom, obse, numSeri, componen, estElem, marc, null, idSubcat);
                        Elementos entidad = elementosMapper.toElementosFromCreateDto(dto);
                        batch.add(entidad);
                    } catch (Exception exRow) {
                        errores.add("Fila " + (r + 1) + ": " + exRow.getMessage());
                    }
                }

                if (!batch.isEmpty()) {
                    List<Elementos> saved = elementosRepository.saveAll(batch);
                    guardados = saved.size();
                }
            }

        } catch (Exception ex) {
            errores.add("Error al procesar archivo: " + ex.getMessage());
        }

        resultado.put("total", total);
        resultado.put("guardados", guardados);
        resultado.put("errores", errores);
        return resultado;
    }

    private static String getStringCell(Cell cell) {
        if (cell == null) return null;
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim();
            case NUMERIC:
                return String.valueOf((long) cell.getNumericCellValue());
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getStringCellValue();
            default:
                return null;
        }
    }

    private static Integer getIntegerCell(Cell cell) {
        if (cell == null) return null;
        switch (cell.getCellType()) {
            case NUMERIC:
                return (int) cell.getNumericCellValue();
            case STRING:
                String s = cell.getStringCellValue().trim();
                if (s.isEmpty()) return null;
                return Integer.valueOf(s);
            default:
                return null;
        }
    }

    private static Long getLongCell(Cell cell) {
        if (cell == null) return null;
        switch (cell.getCellType()) {
            case NUMERIC:
                return (long) cell.getNumericCellValue();
            case STRING:
                String s = cell.getStringCellValue().trim();
                if (s.isEmpty()) return null;
                return Long.valueOf(s);
            default:
                return null;
        }
    }

    private static Byte getByteCell(Cell cell) {
        if (cell == null) return null;
        switch (cell.getCellType()) {
            case NUMERIC:
                return (byte) cell.getNumericCellValue();
            case STRING:
                String s = cell.getStringCellValue().trim();
                if (s.isEmpty()) return null;
                return Byte.valueOf(s);
            default:
                return null;
        }
    }
}