package doerthous.log4j;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Configurator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;

public class T4 {
    public static void main(String[] args) throws URISyntaxException, FileNotFoundException {
        URL url =  T2.class.getClassLoader().getResource("doerthous/log4j/log4j2T4.xml");
        File log4jFile = new File(url.toURI());
        ConfigurationSource source = new ConfigurationSource(new FileInputStream(log4jFile), log4jFile);
        Configurator.initialize(null, source);

        Logger logger = LogManager.getLogger("T4"); // 对应配置文件中自定义的Logger的name
        logger.trace("trace level");
        logger.debug("debug level");
        logger.info("info level");
        logger.warn("warn level");
        logger.error("error level");
        logger.fatal("fatal level");
    }
}
