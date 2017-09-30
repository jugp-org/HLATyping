package org.egov.infra.reporting.engine; 
 
/**
 * Reports related constants 
 */ 
public class ReportConstants { 
 
 // Enumeration of supported report formats 
 public static enum FileFormat { 
  PDF, XLS, RTF, HTM, TXT, CSV 
 }; 
 
 /**
  * The path (in CLASSPATH) where the report engine will look for images during report generation. 
  */ 
 public static final String IMAGES_BASE_PATH = "/egi/resources/global/images/"; 
 
 /**
  * Path of the directory that is checked first for any customised report templates before looking at the standard template path. i.e. System will first check in /custom/reports/templates, and then in /reports/templates 
  */ 
 public static final String CUSTOM_DIR_NAME = "/custom"; 
 
 /**
  * The based directory for report templates. As of now the only sub-directory is templates. However later it may host more sub-directories related to reports as and when required. 
  */ 
 public static final String REPORTS_BASE_PATH = "/reports/"; 
 
 /**
  * Name of the report templates directory. It is appended to the reports base path to arrive at the relative path (in CLASSPATH) where the system looks for report templates 
  */ 
 public static final String TEMPLATE_DIR_NAME = "templates/"; 
 
 /**
  * The properties file used for fetching report configuration (format) for a given report template. TODO: Support report configuration in DB 
  */ 
 public static final String REPORT_CONFIG_FILE = "/config/reports.properties"; 
 
 // UTF-8 character encoding 
 public static final String CHARACTER_ENCODING_UTF8 = "UTF-8"; 
 
 // Session attributes 
 public static final String ATTRIB_EGOV_REPORT_OUTPUT_MAP = "EGOV_REPORT_OUTPUT_MAP"; 
 
 // Request parameters 
 public static final String REQ_PARAM_REPORT_ID = "reportId"; 
 
 // Constants related to number formatting 
 public static final int AMOUNT_PRECISION_DEFAULT = 2; 
 
 // HTTP Headers 
 public static final String HTTP_HEADER_CONTENT_DISPOSITION = "Content-disposition"; 
  
 public static final String IMAGE_CONTEXT_PATH = "/egi"; 
}