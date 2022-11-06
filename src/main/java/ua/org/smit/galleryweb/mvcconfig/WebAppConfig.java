package ua.org.smit.galleryweb.mvcconfig;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import ua.org.smit.authorization.AuthorizationImpl;
import ua.org.smit.authorization.RegistrationImpl;
import ua.org.smit.common.filesystem.FolderCms;
import ua.org.smit.common.filesystem.TxtFile;
import ua.org.smit.gallery.Gallery;
import ua.org.smit.gallery.main.GalleryService;
import ua.org.smit.galleryweb.blured.BluredService;
import ua.org.smit.galleryweb.utilites.PreviousPage;
import ua.org.smit.legacy.collectorsmode.CollectorsService;
import ua.org.smit.webcomponents.system.messages.SystemMessagesService;
import ua.org.smit.authorization.Authorization;
import ua.org.smit.authorization.Registration;

@Configuration
@EnableWebMvc
@ComponentScan("ua.org.smit.galleryweb.controller")
public class WebAppConfig implements WebMvcConfigurer {

    private static final String ENV = "LIQUID_GALLERY";

    public static final String ROOT_DIRECTORY = System.getenv(ENV);

    public static Properties properties = null;

    static SessionFactory sessionFactory = new org.hibernate.cfg.Configuration()
            .configure(new File(ROOT_DIRECTORY + "hibernate.cfg.xml")).buildSessionFactory();
    
    public static FrontEndData frontEndData = new FrontEndData(getPropperties());
    
    public static String homePageMainText = 
            new TxtFile(ROOT_DIRECTORY + File.separator + "home_page_main_text.txt")
                    .readAllTextSolid();

    public static Properties getPropperties() {
        if (properties != null) {
            return properties;
        }

        try {
            FileReader reader = null;
            properties = new Properties();
            reader = new FileReader(ROOT_DIRECTORY + File.separator + "main.properties");

            properties.load(reader);
        } catch (FileNotFoundException ex) {
            throw new RuntimeException();
        } catch (IOException ex) {
            throw new RuntimeException();
        }
        return properties;
    }

    @Bean
    public UrlBasedViewResolver setupViewResolver() {
        UrlBasedViewResolver resolver = new UrlBasedViewResolver();
        resolver.setPrefix("/WEB-INF/jsp/");
        resolver.setSuffix(".jsp");
        resolver.setViewClass(JstlView.class);

        return resolver;
    }

    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
        commonsMultipartResolver.setDefaultEncoding("utf-8");
        commonsMultipartResolver.setMaxUploadSize(300000000); // ~ 300mb
        return commonsMultipartResolver;
    }

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }

    @Bean
    public Gallery getGallery() {
        String GALLERY_PATH = ROOT_DIRECTORY + File.separator + "gallery";

        Gallery gallery = new GalleryService(new FolderCms(GALLERY_PATH), sessionFactory);
        return gallery;
    }

    @Bean
    public Authorization getAuthorization() {
        return new AuthorizationImpl(sessionFactory);
    }

    @Bean
    public CollectorsService getTalexCollectorsService() {
        FolderCms folder = new FolderCms(ROOT_DIRECTORY + File.separator + "collectors_mod");
        return new CollectorsService(folder);
    }

    @Bean
    public BluredService getBluredService() {
        FolderCms blured
                = new FolderCms(ROOT_DIRECTORY + File.separator + "blured");

        TxtFile imagesFile
                = new TxtFile(ROOT_DIRECTORY + File.separator
                        + "blured_images_service" + File.separator
                        + "images.txt");

        TxtFile albumsFile = new TxtFile(ROOT_DIRECTORY + File.separator
                + "blured_images_service" + File.separator
                + "albums.txt");
        return new BluredService(imagesFile, albumsFile, blured);
    }

    @Bean
    public PreviousPage getPreviousPage() {
        return new PreviousPage();
    }

    @Bean
    public SystemMessagesService getSystemMessagesService() {
        return new SystemMessagesService();
    }

    @Bean
    public Registration getRegistration() {
        return new RegistrationImpl();
    }
}
