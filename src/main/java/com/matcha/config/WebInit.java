package com.matcha.config;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import javax.servlet.*;
import java.io.File;

public class  WebInit extends AbstractAnnotationConfigDispatcherServletInitializer
    {

        private final String FILE_TMP_FOLDER = "java.io.tmpdir";
        private final Integer MAX_UPLOAD_SIZE = 5 * 1024 * 1024;

        @Override
        protected Class<?>[] getRootConfigClasses() {
            return null;
        }

        @Override
        protected WebApplicationContext createRootApplicationContext() {
            return super.createRootApplicationContext();
        }

        @Override
        protected Class<?>[] getServletConfigClasses() {
            return new Class[]{WebConfig.class};
        }

        @Override
        protected String[] getServletMappings() {
            return new String[]{"/"};
        }

        @Override
        protected void customizeRegistration(ServletRegistration.Dynamic registration) {

            File uploadDirectory = new File(System.getProperty(FILE_TMP_FOLDER));
            MultipartConfigElement multipartConfigElement = new MultipartConfigElement(uploadDirectory.getAbsolutePath(),
                            MAX_UPLOAD_SIZE, MAX_UPLOAD_SIZE * 5, MAX_UPLOAD_SIZE / 2);
            registration.setMultipartConfig(multipartConfigElement);
            registration.setInitParameter("throwExceptionIfNoHandlerFound", "true");
        }

        @Override
        public void onStartup(ServletContext servletContext) throws ServletException {
            super.onStartup(servletContext);
            FilterRegistration.Dynamic encoding = servletContext.addFilter("encoding", new CharacterEncodingFilter("UTF-8", true));encoding.addMappingForUrlPatterns(null, false, "/*");
        }

        @Override
        protected void registerDispatcherServlet(ServletContext servletContext) {
            super.registerDispatcherServlet(servletContext);
        }
    }
