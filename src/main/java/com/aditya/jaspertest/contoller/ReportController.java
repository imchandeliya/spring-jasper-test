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
import java.io.*;

@RestController
@RequestMapping("/report")
public class ReportController {

    @Autowired
    ReportService reportService;

    @GetMapping("/get")
    public File getReport(HttpServletRequest request) throws IOException, JRException {
        JasperPrint jasperPrint = reportService.getTestReport2();
        String home = System.getProperty("user.home");
        File file = new File(home+"/Downloads/JasperExportTest.pdf");
        OutputStream outputStream = new FileOutputStream(file);
        JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
        outputStream.close();
        return file;
//        return jasperPrint;
    }

    @GetMapping("/compile")
    public String compileJasperReports(HttpServletRequest request) {
        return reportService.compileJasperReportTemplates();
    }


}
