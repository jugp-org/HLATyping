package jugp.hla.reports;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
 
import javax.swing.JFrame;

import jugp.hla.database.*;
import jugp.hla.model.Allele;


import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.SimpleFileResolver;
import net.sf.jasperreports.swing.JRViewer;
 
public class ReportForm extends JFrame {
 
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
 
    public void showReport() throws JRException, ClassNotFoundException, SQLException {
    	
    	DBConnection dbConnect = new DBConnection();
    	
    	//System.out.println(new File(".").getAbsolutePath());
    	//System.out.println(new File("").getAbsolutePath());
 
        String reportSrcFile = "src/jugp/hla/reports/data/Cherry_1.jrxml";
        //String reportSrcFile = "src/jugp/hla/reports/data/Blank_A4.jrxml";
        
        
        // JRDataSource dataSource
        
        File reportsDir = new File(reportSrcFile);
        if (!reportsDir.exists()) {
            try {
                throw new FileNotFoundException(String.valueOf(reportsDir));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        
        // First, compile jrxml file.
        JasperReport jasperReport = JasperCompileManager.compileReport(reportSrcFile);
        
        // Fields for report
        HashMap<String, Object> parameters = new HashMap<String, Object>();
        
        parameters.put(JRParameter.REPORT_FILE_RESOLVER, new SimpleFileResolver(reportsDir));
        
        parameters.put("company", "MAROTHIA TECHS");
        parameters.put("receipt_no", "RE101".toString());
        parameters.put("name", "Khushboo");
        parameters.put("amount", "10000");
        parameters.put("receipt_for", "EMI Payment");
        parameters.put("date", "20-12-2016");
        parameters.put("contact", "98763178".toString());
        
        
    	// Установка соединения с БД
    	if (dbConnect.isConnected == 0) {
    		dbConnect.SetConnection("localhost", "1433", "DNA_HLA", "integrated","","");
    	}
    	
        ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
        list.add(parameters);
 
        //JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(list);
        //JasperPrint print = JasperFillManager.fillReport(jasperReport, null, beanColDataSource);
        
        JasperPrint print = JasperFillManager.fillReport(jasperReport, parameters, dbConnect.con);
        JRViewer viewer = new JRViewer(print);
        viewer.setOpaque(true);
        viewer.setVisible(true);
        this.add(viewer);
        this.setSize(700, 500);
        this.setVisible(true);
        System.out.print("Done!");
        
        dbConnect.Close();
        
        
 
    }
}
 