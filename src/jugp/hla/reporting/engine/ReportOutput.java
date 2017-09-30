package org.egov.infra.reporting.engine; 
 
import java.io.Serializable; 
 
import org.egov.infra.reporting.engine.ReportConstants.FileFormat; 
 
/**
 * Class to represent a generated report 
 */ 
public class ReportOutput implements Serializable { 
    private static final long serialVersionUID = -2559611205589631905L; 
    private byte[] reportOutputData; 
 private FileFormat reportFormat; 
 
 /**
  * Default constructor 
  */ 
 public ReportOutput() { 
 
 } 
 
 /**
  * Constructor 
  * @param reportOutputData The report output data as byte array 
  * @param reportInput The report input object 
  * @see ReportRequest 
  */ 
 public ReportOutput(final byte[] reportOutputData, final ReportRequest reportInput) { 
  this.reportOutputData = reportOutputData; 
  this.reportFormat = reportInput.getReportFormat(); 
 } 
 
 /**
  * @return the Report Data 
  */ 
 public byte[] getReportOutputData() { 
  return this.reportOutputData; 
 } 
 
 /**
  * @param reportOutputData the Report Output Data to set 
  */ 
 public void setReportOutputData(final byte[] reportOutputData) { 
  this.reportOutputData = reportOutputData; 
 } 
 
 /**
  * @return the Report Format 
  */ 
 public FileFormat getReportFormat() { 
  return this.reportFormat; 
 } 
 
 /**
  * @param reportFormat the Report Format to set 
  */ 
 public void setReportFormat(final FileFormat reportFormat) { 
  this.reportFormat = reportFormat; 
 } 
}