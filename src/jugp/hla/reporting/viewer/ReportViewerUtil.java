package org.egov.infra.reporting.viewer; 
 
import java.util.HashMap; 
import java.util.Map; 
 
import javax.servlet.http.HttpSession; 
 
import org.egov.infra.reporting.engine.ReportConstants; 
import org.egov.infra.reporting.engine.ReportOutput; 
import org.egov.infra.reporting.engine.ReportConstants.FileFormat; 
import org.egov.infstr.cache.LRUCache; 
 
/**
 * Utility methods related to report viewing 
 */ 
public final class ReportViewerUtil { 
 // Content types used for rendering files of different types 
 private static final Map<FileFormat, String> contentTypes = getContentTypes(); 
 
 /**
  * @return Array of content types in appropriate order. This order is same as the order of file formats present in the FileFormat enumeration 
  */ 
 private static Map<FileFormat, String> getContentTypes() { 
  final Map<FileFormat, String> contentTypes = new HashMap<FileFormat, String>(); 
  contentTypes.put(FileFormat.PDF, "application/pdf"); 
  contentTypes.put(FileFormat.XLS, "application/vnd.ms-excel"); 
  contentTypes.put(FileFormat.RTF, "application/rtf"); 
  contentTypes.put(FileFormat.HTM, "text/html"); 
  contentTypes.put(FileFormat.TXT, "text/plain"); 
  contentTypes.put(FileFormat.CSV, "text/plain"); 
  return contentTypes; 
 } 
 
 /**
  * Private constructor to silence PMD warning of "all static methods" 
  */ 
 private ReportViewerUtil() { 
 
 } 
 
 /**
  * Adds given report output to an internal session variable and returns the key. This key needs to be passed to the report viewer servlet for displaying the report in browser. 
  * @param reportOutput The report output object to be added to session 
  * @param session The session variables map 
  * @return Key of the report output object in the session variables map 
  */ 
 @SuppressWarnings("unchecked") 
 public static Integer addReportToSession(final ReportOutput reportOutput, final Map<String, Object> session) { 
  Integer nextKey = 0; 
 
  // Synchronized to ensure that multiple reports created by the user 
  // simultaneously do not overwrite each other. 
  synchronized (session) { 
   LRUCache<Integer, ReportOutput> reportOutputCache = (LRUCache<Integer, ReportOutput>) session.get(ReportConstants.ATTRIB_EGOV_REPORT_OUTPUT_MAP); 
 
   if (reportOutputCache == null) { 
    reportOutputCache = new LRUCache<Integer, ReportOutput>(1, 10); 
    session.put(ReportConstants.ATTRIB_EGOV_REPORT_OUTPUT_MAP, reportOutputCache); 
   } 
 
   nextKey = reportOutputCache.size(); 
   reportOutputCache.put(nextKey, reportOutput); 
  } 
 
  return nextKey; 
 } 
 
 /**
  * Adds given report output to an internal session variable and returns the key. This key needs to be passed to the report viewer servlet for displaying the report in browser. 
  * @param reportOutput The report output object to be added to session 
  * @param session The HTTP session object 
  * @return Key of the report output object in the session variables map 
  */ 
 @SuppressWarnings("unchecked") 
 public static Integer addReportToSession(final ReportOutput reportOutput, final HttpSession session) { 
  Integer nextKey = 0; 
 
  // Synchronized to ensure that multiple reports created by the user 
  // simultaneously do not overwrite each other. 
  synchronized (session) { 
   LRUCache<Integer, ReportOutput> reportOutputCache = (LRUCache<Integer, ReportOutput>) session.getAttribute(ReportConstants.ATTRIB_EGOV_REPORT_OUTPUT_MAP); 
 
   if (reportOutputCache == null) { 
    reportOutputCache = new LRUCache<Integer, ReportOutput>(1, 10); 
    session.setAttribute(ReportConstants.ATTRIB_EGOV_REPORT_OUTPUT_MAP, reportOutputCache); 
   } 
 
   nextKey = reportOutputCache.size(); 
   reportOutputCache.put(nextKey, reportOutput); 
  } 
 
  return nextKey; 
 } 
 
 /**
  * @param fileFormat File format which content type is to be returned 
  * @return content type string for given file format. This can be set in the "Content-type" http header while rendering a file in browser 
  */ 
 public static String getContentType(final FileFormat fileFormat) { 
  return contentTypes.get(fileFormat); 
 } 
 
 /**
  * @param fileFormat File format for which content disposition is to be returned 
  * @return content type string for given file format. This can be set in the "Content-disposition" http header while rendering a file in browser 
  */ 
 protected static String getContentDisposition(final FileFormat fileFormat) { 
  return "inline; filename=report." + fileFormat.toString(); 
 } 
}