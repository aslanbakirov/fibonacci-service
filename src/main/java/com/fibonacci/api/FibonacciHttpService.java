package com.fibonacci.api;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URL;
import java.net.URLDecoder;
import java.util.concurrent.TimeUnit;

import com.fibonacci.main.Main;
import org.glassfish.grizzly.http.server.*;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by aslan on 11/06/16.
 */
public class FibonacciHttpService {

        private final HttpServer server;
        private final static String PACKAGE_NAMESPACE = "com.fibonacci.api";
        private static final Logger LOGGER = LoggerFactory.getLogger(FibonacciHttpService.class);
        private static final int SERVICE_PORT = 5555;

        public FibonacciHttpService() {
            ResourceConfig httpConf = new ResourceConfig()
                    .register(new FibonacciRestApi())
                    .packages(PACKAGE_NAMESPACE);

            URI httpUri = URI.create("http://" + Main.host() + ":" + SERVICE_PORT + "/");

            server = GrizzlyHttpServerFactory.createHttpServer(httpUri, httpConf);
            server.getServerConfiguration().addHttpHandler(
                    new StaticHttpHandler(getRoot()),
                    "/static"
            );
        }


        public static URL jarLocation() {
            return com.fibonacci.main.Main.class.getProtectionDomain().getCodeSource().getLocation();
        }

        private static String getRoot() {
            String jarPath = null;
            try {
                jarPath = URLDecoder.decode(jarLocation().getFile(), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                LOGGER.error("Could not read root directory path for jar file.", e);
                System.exit(2);
            }

            return new File(jarPath).getParentFile().getPath();
        }

        public void start() throws IOException {
            server.start();
        }

        public void stop() {
            server.shutdown(30000, TimeUnit.MILLISECONDS);
        }

    }
