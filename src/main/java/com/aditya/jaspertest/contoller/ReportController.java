package com.aditya.jaspertest.contoller;

import com.aditya.jaspertest.service.ReportService;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

@RestController
@RequestMapping("/report")
public class ReportController {

    @Autowired
    ReportService reportService;

    @GetMapping("/get")
    public File getReport(HttpServletRequest request) throws FileNotFoundException, JRException {
        JasperPrint jasperPrint = reportService.getTestReport();
        File file = new File("JasperExportTest.pdf");
        OutputStream outputStream = new FileOutputStream(file);
        JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
        return file;
//        return jasperPrint;
    }

    @GetMapping("/compile")
    public String compileJasperReports(HttpServletRequest request) {
        return reportService.compileJasperReportTemplates();
    }


}
