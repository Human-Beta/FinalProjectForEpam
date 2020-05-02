package ua.nure.shishov.finaltask.web.listener;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;


/**
 * Context listener.
 * 
 * @author N.Shishov
 * 
 */
public class ContextListener implements ServletContextListener {

	private static final Logger LOG = Logger.getLogger(ContextListener.class);

	public void contextDestroyed(ServletContextEvent event) {
		log("Servlet context destruction starts");
		// nothing
		log("Servlet context destruction finished");
	}

	public void contextInitialized(ServletContextEvent event) {
		log("Servlet context initialization starts");

		ServletContext servletContext = event.getServletContext();
		initLog4J(servletContext);
		initCommandContainer();
		initLocales(servletContext);

		log("Servlet context initialization finished");
	}

	/**
	 * Initializes log4j framework.
	 * 
	 * @param servletContext
	 */
	private void initLog4J(ServletContext servletContext) {
		log("Log4J initialization started");
		try {
			PropertyConfigurator.configure(servletContext.getRealPath("WEB-INF/log4j.properties"));
			LOG.debug("Log4j has been initialized");
		} catch (Exception ex) {
			log("Cannot configure Log4j");
			ex.printStackTrace();
		}
		log("Log4J initialization finished");
	}
	
	/**
	 * Initializes locales properties.
	 * 
	 * @param servletContext
	 */
	private void initLocales(ServletContext servletContext) {
		log("Locales initialization started");
		String localesFileName = servletContext.getInitParameter("locales");
		String localesFileRealPath = servletContext.getRealPath(localesFileName);
		
		Properties locales = new Properties();
		try {
			Path path = Paths.get(localesFileRealPath);
			locales.load(Files.newBufferedReader(path));
			
			locales.list(System.out);
			servletContext.setAttribute("locales", locales);
		} catch (IOException  e) {
			LOG.warn("Cannot load file --> " + localesFileRealPath, e);
			e.printStackTrace();
		}
		
		log("Locales initialization finished");
	}

	/**
	 * Initializes CommandContainer.
	 * 
	 * @param servletContext
	 */
	private void initCommandContainer() {

		// initialize commands container
		// just load class to JVM
		try {
			Class.forName("ua.nure.shishov.finaltask.web.command.CommandContainer");
		} catch (ClassNotFoundException ex) {
			throw new IllegalStateException("Cannot initialize Command Container");
		}
	}

	private void log(String msg) {
		System.out.println("[ContextListener] " + msg);
	}
}