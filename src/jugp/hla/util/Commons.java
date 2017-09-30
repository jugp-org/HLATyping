/**
* Common variables and functions
*/

package jugp.hla.util;

import java.net.JarURLConnection;
import java.net.URL;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Global constants/varilables/settings
 * 
 * @author Ahmed Moustafa (ahmed@users.sf.net)
 */

public abstract class Commons {
	/**
	 * Logger
	 */
	private static final Logger logger = Logger.getLogger(Commons.class
			.getName());

	/**
	 * Build timestamp attribute in the manifest in the jar
	 */
	private static final String BUILD_TIMESTAMP = "Created-At";

	/**
	 *  
	 */
	private static final String currentRelease = getManifestBuildTimestamp();

	/**
	 * Default home directory
	 */
	private static final String DEFAULT_USER_DIRECTORY = ".";

	/**
	 * Default file separator
	 */
	private static final String DEFAULT_FILE_SEPARATOR = "/";

	/**
	 * Default line separator
	 */
	private static final String DEFAULT_LINE_SEPARATOR = "\r\n";

	/**
	 * User home directory
	 */
	private static String userDirectory = DEFAULT_USER_DIRECTORY;
	static {
		try {
			userDirectory = System.getProperty("user.dir");
		} catch (Exception e) {
			logger.log(Level.WARNING, "Failed getting user current directory: "
					+ e.getMessage(), e);
		}
	}

	/**
	 * Line separator
	 */
	private static String fileSeparator = DEFAULT_FILE_SEPARATOR;
	static {
		try {
			fileSeparator = System.getProperty("file.separator");
		} catch (Exception e) {
			logger.log(Level.WARNING, "Failed getting system file separator: "
					+ e.getMessage(), e);
		}
	}

	/**
	 * Line separator
	 */
	private static String lineSeparator = DEFAULT_LINE_SEPARATOR;

	static {
		try {
			lineSeparator = System.getProperty("line.separator");
		} catch (Exception e) {
			logger.log(Level.WARNING, "Failed getting system line separator: "
					+ e.getMessage(), e);
		}
	}

	/**
	 * JNLP enabled
	 */
	private static boolean jnlp = false;
	static {
		try {
			jnlp = "true".equalsIgnoreCase(System.getProperty("jnlp.enabled"));
		} catch (Exception e) {
			logger.log(Level.WARNING, "Failed getting jnlp enabled property: "
					+ e.getMessage(), e);
		}
		setJnlp(jnlp);
	}

	/**
	 * Returns system file separator.
	 * 
	 * @return file separator
	 */
	public static String getFileSeparator() {
		return fileSeparator;
	}

	/**
	 * Returns system line separator.
	 * 
	 * @return line separator
	 */
	public static String getLineSeparator() {
		return lineSeparator;
	}

	/**
	 * Returns user's current directory.
	 * 
	 * @return user's current directory
	 */
	public static String getUserDirectory() {
		return userDirectory;
	}

	/**
	 * Sets the JNLP flag to true of false
	 * 
	 * @param jnlp
	 *            true or false
	 */
	public static void setJnlp(boolean jnlp) {
		Commons.jnlp = jnlp;
	}

	/**
	 * Returns true if jnlp is enabled
	 * 
	 * @return true if jnlp is enabled, otherwise returns false
	 */
	public static boolean isJnlp() {
		return jnlp;
	}

	/**
	 * Gets the build teimstamp from the jar manifest
	 * 
	 * @return build timestamp
	 */
	private static String getManifestBuildTimestamp() {
		JarURLConnection connection = null;
		JarFile jarFile = null;
		URL url = Commons.class.getClassLoader().getResource("jaligner");
		try {
			// Get jar connection
			connection = (JarURLConnection) url.openConnection();

			// Get the jar file
			jarFile = connection.getJarFile();

			// Get the manifest
			Manifest manifest = jarFile.getManifest();

			// Get the manifest entries
			Attributes attributes = manifest.getMainAttributes();

			Attributes.Name name = new Attributes.Name(BUILD_TIMESTAMP);
			return attributes.getValue(name);
		} catch (Exception e) {
			String message = "Failed getting the current release info: "
					+ e.getMessage();
			logger.log(Level.WARNING, message);
		}
		return null;
	}

	/**
	 * Returns the current release version of HLATyping
	 * 
	 * @return current release
	 */
	public static String getCurrentRelease() {
		return currentRelease;
	}

	/**
	 * Returns information about HLATyping
	 * 
	 * @return information about HLATyping
	 */
	public static String getInfo() {
		return "HLATyping - Build: " + getCurrentRelease()
				+ " - By: DNA-Technology (2017)";
	}
}