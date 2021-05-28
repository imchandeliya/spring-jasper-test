package com.aditya.jaspertest.service;

import com.aditya.jaspertest.beans.PersonData;
import com.google.common.io.Resources;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.builder.ReportTemplateBuilder;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRSaver;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import net.sf.jasperreports.export.SimplePdfReportConfiguration;
import net.sf.jasperreports.repo.Resource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

/**
 * @author Aditya Chandeliya
 * @since 18th May, 2021
 */
@Service
public class ReportService {

    private static String REPORT_PATH= "src/main/resources/templates/jasper/employee_report_template.jrxml";

    /**
     * Method to fetch reports
     * @return
     */
    public JasperPrint getTestReport() {
        //todo: use template in {@link ReportService#REPORT_PATH}
        PersonData personData = fillRandomData();

//        JasperReportBuilder
//        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(Arrays.asList(personData));
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("firstName", "Aditya");
        map.put("lastName", "Chandeliya");
        map.put("salary", 25000.0);

        try {
            JasperReportBuilder jasperReportBuilder = DynamicReports.report().setTemplateDesign(REPORT_PATH);
            jasperReportBuilder.setDataSource(new JRBeanCollectionDataSource(Collections.singletonList(map)));

            JasperPrint jPrint = JasperFillManager.fillReport(jasperReportBuilder.toJasperReport(),
                    jasperReportBuilder.getJasperParameters(), jasperReportBuilder.getDataSource());
            return jPrint;

        } catch (JRException | DRException e) {
            e.printStackTrace();
        }

        return null;
    }

    private void printPDF(JasperPrint jasperPrint) throws JRException {
        JRPdfExporter exporter = new JRPdfExporter();

        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(
                new SimpleOutputStreamExporterOutput("employeeReport.pdf"));

        SimplePdfReportConfiguration reportConfig
                = new SimplePdfReportConfiguration();
        reportConfig.setSizePageToContent(true);
        reportConfig.setForceLineBreakPolicy(false);

        SimplePdfExporterConfiguration exportConfig
                = new SimplePdfExporterConfiguration();
        exportConfig.setMetadataAuthor("baeldung");
        exportConfig.setEncrypted(true);
        exportConfig.setAllowedPermissionsHint("PRINTING");

        exporter.setConfiguration(reportConfig);
        exporter.setConfiguration(exportConfig);

        exporter.exportReport();
    }



    public PersonData fillRandomData() {
        PersonData pData = new PersonData();
        pData.setAge(24);
        pData.setName("Aditya Chandeliya");
        pData.setOccupation("Software Developer");
        return pData;
    }


    /**
     * method to compile reports
     */
    public String compileJasperReportTemplates() {
        try {
//            InputStream employeeReportStream = new FileInputStream(new File(REPORT_PATH));
            InputStream ers = getClass().getResourceAsStream(REPORT_PATH);
            JasperReport jasperReport = JasperCompileManager.compileReport(ers);
//            JasperFillManager.fillReport
            JRSaver.saveObject(jasperReport, "src/main/resources/templates/target/employee_report.jasper");
            return "compiled";
        } catch (JRException e) {
            e.printStackTrace();
            return "Issue in compilation";
        }
    }
}
