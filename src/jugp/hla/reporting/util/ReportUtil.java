package org.egov.infra.reporting.util; 
 
import java.io.IOException; 
import java.io.InputStream; 
import java.math.BigDecimal; 
import java.sql.Connection; 
import java.sql.PreparedStatement; 
import java.sql.ResultSet; 
import java.sql.SQLException; 
import java.util.Date; 
import java.util.List; 
import java.util.Properties; 
 
import javax.servlet.http.HttpServletRequest; 
 
import org.apache.struts2.ServletActionContext; 
import org.egov.infra.exception.ApplicationRuntimeException; 
import org.egov.infra.reporting.engine.ReportConstants; 
import org.egov.infra.utils.EgovThreadLocals; 
import org.egov.infra.web.utils.WebUtils; 
import org.egov.infstr.utils.DateUtils; 
import org.egov.infstr.utils.HibernateUtil; 
import org.egov.infstr.utils.NumberUtil; 
import org.hibernate.HibernateException; 
import org.hibernate.Query; 
import org.slf4j.Logger; 
import org.slf4j.LoggerFactory; 
 
/**
 * Provides utility methods related to reports 
 */ 
public final class ReportUtil { 
 
 private static final Logger LOGGER = LoggerFactory.getLogger(ReportUtil.class); 
 
 /**
  * Private constructor to silence PMD warning of "all static methods" 
  */ 
 private ReportUtil() { 
 } 
 
 /**
  * Returns input stream for given file. First checks in the custom location (/custom/[filePath]/). If not found, tries the given location [filePath] 
  * @param filePath Path of file to be loaded from classpath 
  * @return Input stream for given file 
  */ 
 private static InputStream getFileAsStream(final String filePath) { 
  InputStream fileInputStream = null; 
  String errMsg = null; 
  // Try custom file first 
  fileInputStream = ReportUtil.class.getResourceAsStream(ReportConstants.CUSTOM_DIR_NAME + filePath); 
  if (fileInputStream == null) { 
   // Custom file not available. Try given path 
   fileInputStream = ReportUtil.class.getResourceAsStream(filePath); 
  } 
  if (fileInputStream == null) { 
   // Still not found. Logger error and throw exception. 
   errMsg = "File [" + filePath + "] could not be loaded from CLASSPATH!"; 
   LOGGER.error(errMsg); 
   throw new ApplicationRuntimeException(errMsg); 
  } 
  return fileInputStream; 
 } 
 
 /**
  * Returns User Signature InputStream 
  * @param user id 
  * @return user signature for the given user id 
  */ 
 /*public static InputStream getUserSignature(final Integer userId) {
  final Session session = HibernateUtil.getCurrentSession(); 
  InputStream signatureStream = null; 
  final User user = (User) session.load(User.class, userId); 
  if (user != null && user.getUserSignature() != null) { 
   final byte[] value = CryptoHelper.decrypt(user.getUserSignature().getSignature(), CryptoHelper.decrypt(user.getPassword())); 
   signatureStream = new ByteArrayInputStream(value); 
 
  } 
  return signatureStream; 
 } 
*/ 
 /**
  * Returns input stream for given image file. First checks in the custom location (/custom/reports/images/). If not found, tries the product location (/reports/images/) 
  * @param imageName Name of image to be read 
  * @return Input stream for given image file 
  */ 
 public static InputStream getImageAsStream(final String imageName) { 
  return getFileAsStream(ReportConstants.IMAGES_BASE_PATH + imageName); 
 } 
 
 /**
  * This method can be used to fetch the logo image to be printed in those reports that use SQL query (JDBC) as data source. 
  * @return The logo images for currently logged in city as an input stream 
  */ 
 //These values should be passed as report parameters to the jrxml not to fetch from the database since it is already in session 
        @Deprecated 
 public static InputStream getLogoImageAsStream(final Connection connection) { 
  try { 
   return getImageAsStream((String) fetchFromDBSql(connection, "SELECT LOGO FROM EG_CITY WHERE DOMAINURL = '" + EgovThreadLocals.getDomainName() + "'")); 
  } catch (final SQLException e) { 
   throw new ApplicationRuntimeException("Exception in getting logo image!", e); 
  } 
 } 
 
 /**
  * This method can be used to fetch the logo image to be printed in those reports that use Java Bean array/collection as data source. 
  * @return The logo images for currently logged in city as an input stream 
  */ 
        //These values should be passed as report parameters to the jrxml not to fetch from the database since it is already in session 
        @Deprecated 
 public static InputStream getLogoImageAsStream() { 
  try { 
   return getImageAsStream(fetchLogo()); 
  } catch (final HibernateException e) { 
   throw new ApplicationRuntimeException("Exception in getting logo image!", e); 
  } 
 } 
 
        //These values should be passed as report parameters to the jrxml not to fetch from the database since it is already in session 
        @Deprecated 
 public static String fetchLogo() { 
  return (String) HibernateUtil.getCurrentSession().createSQLQuery("SELECT LOGO FROM EG_CITY WHERE DOMAINURL = '" + EgovThreadLocals.getDomainName() + "'").list().get(0); 
 } 
  
  
        //Reading from session 
 public static String getCityName() { 
         return ServletActionContext.getRequest().getSession().getAttribute("cityname").toString(); 
 } 
 
 /**
  * Returns input stream for given report template. First checks in the custom location (/custom/reports/templates/). If not found, tries the product location (/reports/templates/) 
  * @param templateName Report template to be read 
  * @return Input stream for given report template 
  */ 
 public static InputStream getTemplateAsStream(final String templateName) { 
  return getFileAsStream(ReportConstants.REPORTS_BASE_PATH + ReportConstants.TEMPLATE_DIR_NAME + templateName); 
 } 
 
 /**
  * Loads the report configuration file from classpath (/config/reports.properties) 
  * @return the Properties object created from the configuration file 
  */ 
 public static Properties loadReportConfig() { 
  final Properties reportProps = new Properties(); 
  try { 
   reportProps.load(getFileAsStream(ReportConstants.REPORT_CONFIG_FILE)); 
   return reportProps; 
  } catch (final IOException e) { 
   LOGGER.warn("Exception while loading report configuration file [" + ReportConstants.REPORT_CONFIG_FILE + "]", e); 
   return null; 
  } catch (final ApplicationRuntimeException e) { 
   LOGGER.warn("Exception while loading report configuration file [" + ReportConstants.REPORT_CONFIG_FILE + "]", e); 
   return null; 
  } 
 } 
 
 /**
  * Executes given HQL query (which is expected to return a single object) and returns the output. Returns null if query doesn't fetch any data. 
  * @param connection Connection to be used for executing the query. Can be passed as $P{REPORT_CONNECTION} in case of a jasper report 
  * @param query Query to be executed to get the data 
  * @return Output of the query 
  */ 
 @SuppressWarnings("unchecked") 
 public static Object fetchFromDBHql(final String hqlQuery) { 
  final Query query = HibernateUtil.getCurrentSession().createQuery(hqlQuery); 
  final List<Object> result = query.list(); 
  if (result.size() == 1) { 
   return result.get(0); 
  } else { 
   final String errMsg = "Query [" + hqlQuery + "] returned multiple rows!"; 
   LOGGER.error(errMsg); 
   throw new ApplicationRuntimeException(errMsg); 
  } 
 } 
 
 /**
  * Executes given SQL query (which is expected to return a single object) and returns the output. Returns null if query doesn't fetch any data. 
  * @param connection Connection to be used for executing the query. Can be passed as $P{REPORT_CONNECTION} in case of a jasper report 
  * @param sqlQuery Query to be executed to get the data 
  * @return Output of the query 
  */ 
 public static Object fetchFromDBSql(final Connection connection, final String sqlQuery) throws SQLException { 
  PreparedStatement statement = null; 
  ResultSet resultSet = null; 
  try { 
   statement = connection.prepareStatement(sqlQuery); 
   resultSet = statement.executeQuery(); 
   if ((resultSet != null) && resultSet.next()) { 
    return resultSet.getString(1); 
   } else { 
    return null; 
   } 
  } catch (final SQLException e) { 
   final String errMsg = "Exception while executing query [" + sqlQuery + "]"; 
   LOGGER.error(errMsg, e); 
   throw new ApplicationRuntimeException(errMsg, e); 
  } finally { 
   if (statement != null) { 
    statement.close(); 
   } 
   if (resultSet != null) { 
    resultSet.close(); 
   } 
  } 
 } 
 
 /**
  * @param year 
  * @param month 
  * @param date 
  * @return date object representing given year, month and date 
  */ 
 public static Date getDate(final int year, final int month, final int date) { 
  return DateUtils.getDate(year, month, date); 
 } 
 
 /**
  * @return Date object representing today 
  */ 
 public static Date today() { 
  return DateUtils.today(); 
 } 
 
 /**
  * @return Date object representing today 
  */ 
 public static Date now() { 
  return DateUtils.now(); 
 } 
 
 /**
  * @return Date object representing tomorrow 
  */ 
 public static Date tomorrow() { 
  return DateUtils.tomorrow(); 
 } 
 
 /**
  * Adds given number of days/months/years to given date and returns the resulting date 
  * @param inputDate Input date 
  * @param addType type to be added (Calendar.DAY_OF_MONTH/Calendar.MONTH/Calendar.YEAR) 
  * @param addAmount Number of days/months/years to be added to the input date 
  * @return Date after adding given number of days/months/years to the input date 
  */ 
 public static Date add(final Date inputDate, final int addType, final int addAmount) { 
  return DateUtils.add(inputDate, addType, addAmount); 
 } 
 
 /**
  * Converts given amount to words with default decimal precision of 2. 
  * @param amount Amount to be converted to words 
  * @return The amount in words with default decimal precision of 2. 
  */ 
 public static String amountInWords(final BigDecimal amount) { 
  return NumberUtil.amountInWords(amount); 
 } 
 
 /**
  * Formats a given number with given number of fraction digits <br> 
  * e.g. formatNumber(1000, 2, false) will return 1000.00 <br> 
  * formatNumber(1000, 2, true) will return 1,000.00 <br> 
  * @param number The number to be formatted 
  * @param fractionDigits Number of fraction digits to be used for formatting 
  * @param useGrouping Flag indicating whether grouping is to be used while formatting the number 
  * @return Formatted number with given number of fraction digits 
  */ 
 public static String formatNumber(final BigDecimal number, final int fractionDigits, final boolean useGrouping) { 
  return NumberUtil.formatNumber(number, fractionDigits, useGrouping); 
 } 
  
 /**
  * Gives the absolute path of the logo image 
  *  
  * @return absolute path of the logo image 
  */ 
 public static String logoBasePath(){ 
            HttpServletRequest request = ServletActionContext.getRequest(); 
            String url= WebUtils.extractRequestDomainURL(request, false); 
            String imagePath= url.concat(ReportConstants.IMAGE_CONTEXT_PATH).concat((String) request.getSession().getAttribute("citylogo")); 
            return imagePath; 
        } 
  
}