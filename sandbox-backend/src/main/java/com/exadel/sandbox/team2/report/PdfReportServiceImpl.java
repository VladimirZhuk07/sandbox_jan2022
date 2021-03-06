package com.exadel.sandbox.team2.report;

import lombok.SneakyThrows;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class PdfReportServiceImpl implements ReportService {
    private final String JRXML_FOLDER = "JRXML";
    private final String REPORT_FOLDER = "REPORT";

    @SneakyThrows
    @PostConstruct
    public void initialize() {
        checkOrCreateFolders(JRXML_FOLDER);
        checkOrCreateFolders(REPORT_FOLDER);
    }

    @SneakyThrows
    private void checkOrCreateFolders(String nameOfFolder) {
        if (!Files.exists(Path.of(nameOfFolder))) {
            Files.createDirectories(Path.of(nameOfFolder));
        }
    }

    @Override
    public <T> String getReport(List<T> data, String jrxmlPath, String reportPath) throws JRException, IOException {
        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(data, false);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("total", data.size());
        JasperReport compileReport = JasperCompileManager.compileReport(
                new FileInputStream(JRXML_FOLDER + "/" + jrxmlPath));
        JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, parameters, beanCollectionDataSource);
        String fileName = REPORT_FOLDER + "/" + reportPath + ".pdf";
        JasperExportManager.exportReportToPdfFile(jasperPrint, fileName);
        return fileName;
    }

    @Override
    public <T> String constructReport(List<T> data, String jrxmlPath, Map<String, Object> parameters, String nameOfReportFile) throws JRException, IOException {
        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(data, false);
        JasperReport compileReport = JasperCompileManager.compileReport(
                new FileInputStream(JRXML_FOLDER + "/" + jrxmlPath));
        JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, parameters, beanCollectionDataSource);
        String filePath = REPORT_FOLDER + "/" + nameOfReportFile + ".pdf";
        JasperExportManager.exportReportToPdfFile(jasperPrint, filePath);
        return filePath;
    }

}
