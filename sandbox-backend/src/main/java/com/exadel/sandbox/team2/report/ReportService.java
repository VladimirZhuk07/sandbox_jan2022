package com.exadel.sandbox.team2.report;

import net.sf.jasperreports.engine.JRException;

import java.io.IOException;
import java.util.List;

public interface ReportService {
    <T> String getReport(List<T> data, String jrxmlPath,String reportPath) throws JRException, IOException;
}
