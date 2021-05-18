package com.aditya.jaspertest.service;

import org.springframework.stereotype.Service;

/**
 * @author Aditya Chandeliya
 * @since 18th May, 2021
 */
@Service
public class ReportService {

    private static String REPORT_PATH= "src/main/resources/templates/jasper/employee_report_template.jsxml";

    /**
     * Method to fetch reports
     * @return
     */
    public String getTestReport() {
        //todo: use template in {@link ReportService#REPORT_PATH}
        return "";
    }
}
