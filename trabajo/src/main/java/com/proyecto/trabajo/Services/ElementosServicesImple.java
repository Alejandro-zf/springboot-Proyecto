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
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.proyecto.trabajo.Mapper.ElementosMapper;
import com.proyecto.trabajo.dto.ElementoDto;
import com.proyecto.trabajo.dto.ElementoUpdateDtos;
import com.proyecto.trabajo.dto.ElementosCreateDto;
import com.proyecto.trabajo.models.Elementos;
import com.proyecto.trabajo.models.Sub_categoria;
import com.proyecto.trabajo.models.Categoria;
import com.proyecto.trabajo.repository.ElementosRepository;
import com.proyecto.trabajo.repository.Sub_categoriaRepository;
import com.proyecto.trabajo.repository.CategoriaRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ElementosServicesImple implements ElementosServices {

    private final ElementosRepository elementosRepository;
    private final ElementosMapper elementosMapper;
    private final Sub_categoriaRepository subcategoriaRepository;
    private final CategoriaRepository categoriaRepository;

    public ElementosServicesImple(ElementosRepository elementosRepository, ElementosMapper elementosMapper,
            Sub_categoriaRepository subcategoriaRepository, CategoriaRepository categoriaRepository) {
        this.elementosRepository = elementosRepository;
        this.elementosMapper = elementosMapper;
        this.subcategoriaRepository = subcategoriaRepository;
        this.categoriaRepository = categoriaRepository;
    }

    @Override
    public byte[] generarPlantilla() {
        try (Workbook workbook = new XSSFWorkbook(); java.io.ByteArrayOutputStream bos = new java.io.ByteArrayOutputStream()) {
            CreationHelper creationHelper = workbook.getCreationHelper();
            Sheet sheet = workbook.createSheet("plantilla_elementos");
            Row titleRow = sheet.createRow(0);
            Cell titleCell = titleRow.createCell(0);
            titleCell.setCellValue("Plantilla importación - Elementos");

            org.apache.poi.ss.usermodel.CellStyle titleStyle = workbook.createCellStyle();
            org.apache.poi.ss.usermodel.Font titleFont = workbook.createFont();
            titleFont.setBold(true);
            titleFont.setFontHeightInPoints((short)14);
            titleStyle.setFont(titleFont);
            titleStyle.setAlignment(org.apache.poi.ss.usermodel.HorizontalAlignment.CENTER);
            titleStyle.setFillForegroundColor(org.apache.poi.ss.usermodel.IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
            titleStyle.setFillPattern(org.apache.poi.ss.usermodel.FillPatternType.SOLID_FOREGROUND);
            titleCell.setCellStyle(titleStyle);
            workbook.getSheet("plantilla_elementos").addMergedRegion(new org.apache.poi.ss.util.CellRangeAddress(0,0,0,6));

            Row headerRow = sheet.createRow(1);
            String[] headers = new String[] {"nombre_elemento", "observaciones", "numero_serie", "componente", "estado", "marca_elemento", "id_subcategoria"};

            org.apache.poi.ss.usermodel.CellStyle headerStyle = workbook.createCellStyle();
            org.apache.poi.ss.usermodel.Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);
            headerStyle.setFillForegroundColor(org.apache.poi.ss.usermodel.IndexedColors.GREY_25_PERCENT.getIndex());
            headerStyle.setFillPattern(org.apache.poi.ss.usermodel.FillPatternType.SOLID_FOREGROUND);
            headerStyle.setBorderBottom(org.apache.poi.ss.usermodel.BorderStyle.THIN);
            headerStyle.setBorderTop(org.apache.poi.ss.usermodel.BorderStyle.THIN);
            headerStyle.setBorderLeft(org.apache.poi.ss.usermodel.BorderStyle.THIN);
            headerStyle.setBorderRight(org.apache.poi.ss.usermodel.BorderStyle.THIN);

            for (int i = 0; i < headers.length; i++) {
                Cell c = headerRow.createCell(i);
                c.setCellValue(headers[i]);
                c.setCellStyle(headerStyle);
            }
            Sheet listas = workbook.createSheet("dicce_list");
            Row lheader = listas.createRow(0);
            lheader.createCell(0).setCellValue("Id_Subcategoria - Nombre");
            lheader.createCell(2).setCellValue("Id_Categoria - Nombre");
            Row noteRow = listas.createRow(1);
            noteRow.createCell(0).setCellValue("NOTA: Si crea una categoría o subcategoría nueva, coloque el id de la subcategoría en la columna 'id_subcategoria' para que el elemento vaya a ese apartado.");

            int rowIdx = 2;
            try {
                List<Sub_categoria> subcats = subcategoriaRepository.findAll();
                for (Sub_categoria sc : subcats) {
                    Row r = listas.getRow(rowIdx) == null ? listas.createRow(rowIdx) : listas.getRow(rowIdx);
                    String name = null;
                    try { name = sc.getNom_subcategoria(); } catch (Exception e) { name = sc.toString(); }
                    r.createCell(0).setCellValue(String.valueOf(sc.getId()) + " - " + (name != null ? name : ""));
                    rowIdx++;
                }
            } catch (Exception e) {
            }

            int catRow = 2;
            try {
                List<Categoria> cats = categoriaRepository.findAll();
                for (Categoria cObj : cats) {
                    Row r = listas.getRow(catRow) == null ? listas.createRow(catRow) : listas.getRow(catRow);
                    String cname = null;
                    try { cname = cObj.getNom_categoria(); } catch (Exception e) { cname = cObj.toString(); }
                    r.createCell(2).setCellValue(String.valueOf(cObj.getId()) + " - " + (cname != null ? cname : ""));
                    catRow++;
                }
            } catch (Exception e) {
            }

            int listasIndex = workbook.getSheetIndex(listas);
            workbook.setSheetHidden(listasIndex, true);

            // Comentarios en la hoja principal (mensajes amigables, sin referencias a endpoints)
            Drawing<?> drawing = sheet.createDrawingPatriarch();
            ClientAnchor anchorSub = creationHelper.createClientAnchor();
            anchorSub.setCol1(6);
            anchorSub.setCol2(7);
            anchorSub.setRow1(1);
            anchorSub.setRow2(4);
            Comment commentSub = drawing.createCellComment(anchorSub);
            commentSub.setString(creationHelper.createRichTextString("Para obtener el id de subcategoría: vaya al apartado 'Subcategorías' en el panel de administración; al lado de cada subcategoría aparece su id. Pegue ese id aquí."));
            sheet.getRow(1).getCell(6).setCellComment(commentSub);

            ClientAnchor anchorEst = creationHelper.createClientAnchor();
            anchorEst.setCol1(4);
            anchorEst.setCol2(5);
            anchorEst.setRow1(1);
            anchorEst.setRow2(4);
            Comment commentEst = drawing.createCellComment(anchorEst);
            commentEst.setString(creationHelper.createRichTextString("Estado: 1 = Activo, 2 = Inactivo. Use '1' para que el elemento quede activo por defecto."));
            sheet.getRow(1).getCell(4).setCellComment(commentEst);

            ClientAnchor anchorInstr = creationHelper.createClientAnchor();
            anchorInstr.setCol1(0);
            anchorInstr.setCol2(3);
            anchorInstr.setRow1(0);
            anchorInstr.setRow2(2);
            Comment commentInstr = drawing.createCellComment(anchorInstr);
            commentInstr.setString(creationHelper.createRichTextString("Esta plantilla importa elementos. Rellene cada fila con los datos solicitados. Use la hoja oculta 'dicce_list' para ver los ids y valores sugeridos. No modifique la fila de encabezado."));
            sheet.getRow(0).getCell(0).setCellComment(commentInstr);

            int[] widths = new int[] {8000,10000,7000,8000,3000,7000,5000};
            for (int i = 0; i < headers.length; i++) {
                sheet.setColumnWidth(i, widths[i]);
            }
            try {
                Row s1 = listas.getRow(catRow) == null ? listas.createRow(catRow) : listas.getRow(catRow);
                s1.createCell(4).setCellValue("1 - Activo");
                catRow++;
                Row s2 = listas.getRow(catRow) == null ? listas.createRow(catRow) : listas.getRow(catRow);
                s2.createCell(4).setCellValue("2 - Inactivo");
            } catch (Exception e) {
            }
            sheet.createFreezePane(0,2);

            workbook.write(bos);
            return bos.toByteArray();
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
            Path uploadDir = Paths.get("uploads");
            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
            }
            String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path target = uploadDir.resolve(filename);
            try (InputStream is = file.getInputStream()) {
                Files.copy(is, target, StandardCopyOption.REPLACE_EXISTING);
            }
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
                String[] parts = s.split("\\s*-\\s*");
                String lead = parts.length > 0 ? parts[0].replaceAll("[^0-9]", "") : s.replaceAll("[^0-9]", "");
                if (lead.isEmpty()) throw new NumberFormatException("Valor numérico no encontrado en: '" + s + "'");
                return Long.valueOf(lead);
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