package com.aditya.jaspertest.service;

import com.aditya.jaspertest.beans.NameModel;
import com.aditya.jaspertest.beans.OccupationDetails;
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
    private static String BLANK_TEMPLATE_PATH= "src/main/resources/templates/jasper/blank_template.jrxml";


    /**
     * Method to fetch reports
     * @return
     */
    public JasperPrint getTestReport() {
        //todo: use template in {@link ReportService#REPORT_PATH}
        PersonData personData = fillRandomData();
        PersonData personData1 = fillRandomData1();
        PersonData personData2 = fillRandomData2();


//        HashMap<String, Object> map = new HashMap<String, Object>();
//        map.put("firstName", personData.);
//        map.put("lastName", "Chandeliya");
//        map.put("salary", 25000.0);

        try {
            // main report : blank template
            JasperReportBuilder mainReport = DynamicReports.report().setTemplateDesign(BLANK_TEMPLATE_PATH);
            mainReport.setDataSource(new JREmptyDataSource());

            //subsection 1
            JasperReportBuilder subSection1 =  DynamicReports.report().setTemplateDesign(REPORT_PATH);
            subSection1.setDataSource(new JRBeanCollectionDataSource(Collections.singletonList(personData)));
            mainReport.addDetail(DynamicReports.cmp.horizontalList(DynamicReports.cmp.subreport(subSection1)));

            //subsection 2
            JasperReportBuilder subSection2 =  DynamicReports.report().setTemplateDesign(REPORT_PATH);
            subSection2.setDataSource(new JRBeanCollectionDataSource(Collections.singletonList(personData1)));
            mainReport.addDetail(DynamicReports.cmp.horizontalList(DynamicReports.cmp.subreport(subSection2)));


            //subsection 3
            JasperReportBuilder subSection3 =  DynamicReports.report().setTemplateDesign(REPORT_PATH);
            subSection3.setDataSource(new JRBeanCollectionDataSource(Collections.singletonList(personData2)));
            mainReport.addDetail(DynamicReports.cmp.horizontalList(DynamicReports.cmp.subreport(subSection3)));


//            mainReport.addDetail(DynamicReports.cmp.horizontalList(DynamicReports.cmp.subreport(subSection1)));


            /**
             * This wil generate pdf in your download folder ;)
             */
            JasperPrint jPrint = JasperFillManager.fillReport(mainReport.toJasperReport(),
                    mainReport.getJasperParameters(), mainReport.getDataSource());
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
        NameModel nameModel = new NameModel("Aditya", "Kumar", "Chandeliya");
        OccupationDetails occupationDetails =
                new OccupationDetails("Tata Consultancy Services",
                        "Full-Stack Dev", 100.0, "Sahyadri Park");

        PersonData pData = new PersonData(nameModel, occupationDetails, 25, "Bike Riding");
        return pData;
    }

    public PersonData fillRandomData1() {
        NameModel nameModel = new NameModel("John", "Lol", "Wick");
        OccupationDetails occupationDetails =
                new OccupationDetails("Secret Services",
                        "Agent", 1000000.0, "Unknown");

        PersonData pData = new PersonData(nameModel, occupationDetails, 50, "Kutta paalna");
        return pData;
    }

    public PersonData fillRandomData2() {
        NameModel nameModel = new NameModel("Tony", "Lol", "Stark");
        OccupationDetails occupationDetails =
                new OccupationDetails("Stark Enterprises",
                        "CEO & Owner", 1000000000.0, "Stark Tower");

        PersonData pData = new PersonData(nameModel, occupationDetails, 45, "Experiments");
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
