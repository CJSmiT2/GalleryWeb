package ua.org.smit.galleryweb.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import ua.org.smit.gallery.album.Album;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import ua.org.smit.authorization.AuthUser;
import ua.org.smit.common.web.antibrut.AntiBrut;
import ua.org.smit.gallery.album.image.ImageInfo;
import ua.org.smit.galleryweb.utilites.DeterminateImage;
import ua.org.smit.common.web.utilites.grid.GridService;
import ua.org.smit.common.web.utilites.pagination.Pagination;
import ua.org.smit.gallery.album.AlbumInfo;
import ua.org.smit.gallery.album.Quality;
import ua.org.smit.gallery.album.image.ImageFile;
import ua.org.smit.gallery.tag.Tag;
import ua.org.smit.gallery.user.photomodel.Photomodel;
import ua.org.smit.galleryweb.blured.BluredService;
import ua.org.smit.galleryweb.exceptions.BadRequestFoundException;
import ua.org.smit.galleryweb.utilites.PreviousPage;
import ua.org.smit.authorization.Authorization;
import ua.org.smit.gallery.Gallery;
import ua.org.smit.galleryweb.mvcconfig.WebAppConfig;
import static ua.org.smit.galleryweb.mvcconfig.WebAppConfig.frontEndData;
import static ua.org.smit.galleryweb.mvcconfig.WebAppConfig.homePageMainText;
import ua.org.smit.legacy.collectorsmode.CollectorAccount;
import ua.org.smit.legacy.collectorsmode.CollectorsService;

@Controller
public class HomeController {

    static Logger log = Logger.getLogger(HomeController.class);

    @Autowired
    private Gallery gallery;

    @Autowired
    private CollectorsService talexCollectorsService;

    @Autowired
    private Authorization authorization;

    @Autowired
    private BluredService bluredService;

    @Autowired
    private PreviousPage pages;

    private final AntiBrut antibrutAlbum = new AntiBrut(0);
    private final AntiBrut antibrutImage = new AntiBrut(0);
    private final AntiBrut antibrutTimeCounter = new AntiBrut(12);

    @RequestMapping(value = "/")
    public Object home(HttpSession session) {

        int columnSize = 3;

        ModelAndView model = new ModelAndView("home");
        Optional<AuthUser> user = authorization.find(session.getId());
        model.addObject("user", user);
        model.addObject("rows", new GridService(columnSize).makeRows(gallery.getLastAlbums(6)));
        model.addObject("tags", gallery.getTagsService().getAllTags());
        model.addObject("homePageMainText", homePageMainText);
        model.addObject("frontEndData", frontEndData);
        return model;
    }

    @RequestMapping(value = {"/image_view/{albumAlias}/{imageAlias}"},
            method = {RequestMethod.GET, RequestMethod.HEAD})
    public ModelAndView image(
            @PathVariable("albumAlias") String albumAlias,
            @PathVariable("imageAlias") int imageAlias,
            HttpSession session) {
        try {
            Album album = gallery.getAlbum(albumAlias);
            ImageInfo image = album.getImages().getByAlias(imageAlias);

            ModelAndView model = new ModelAndView("image/image");
            Optional<AuthUser> user = authorization.find(session.getId());
            model.addObject("user", user);
            model.addObject("album", album);
            model.addObject("photomodels", album.getPhotomodels().getList());
            model.addObject("image", image);
            model.addObject("imageFile", gallery.getAlbum(albumAlias)
                    .getByQuality(Quality.RESIZED)
                    .getByAlias(image.getAlias()));
            model.addObject("lastAlbum", pages.getLastAlbum(session.getId()));
            
            model.addObject("frontEndData", frontEndData);
            return model;
        } catch (RuntimeException ex) {
            throw new BadRequestFoundException("Cant find by '" + albumAlias
                    + "' and '" + imageAlias + "'");
        }
    }

    @RequestMapping(value = "/albums")
    public ModelAndView albums(
            @RequestParam(value = "page") int pageRequested,
            HttpSession session) {
        int columnSize = 3;

        Pagination pagination
                = new Pagination(
                        columnSize,
                        gallery.getAllAlbumAliases(),
                        pageRequested);

        List<Album> albums = this.gallery.getAlbumsByAliases(pagination.getSelectedAliases());

        ModelAndView model = new ModelAndView("album/albums");
        Optional<AuthUser> user = authorization.find(session.getId());
        model.addObject("user", user);
        model.addObject("rows", new GridService(columnSize).makeRows(albums));
        model.addObject("pagination", pagination);
        model.addObject("frontEndData", frontEndData);
        return model;
    }

    @RequestMapping(value = "/album/{albumAlias}")
    public ModelAndView album(
            @PathVariable("albumAlias") String albumAlias,
            HttpSession session) {
        try {
            this.pages.addLastAlbum(albumAlias, session.getId());

            Album album = gallery.getAlbum(albumAlias);
            List<ImageInfo> imagesInfos = album.getImages().getAllByAlbum();

            ModelAndView model = new ModelAndView("album/album");
            Optional<AuthUser> user = authorization.find(session.getId());
            model.addObject("user", user);
            model.addObject("album", album);
            model.addObject("rows", new GridService(6).makeRows(imagesInfos));
            model.addObject("frontEndData", frontEndData);
            return model;
        } catch (RuntimeException ex) {
            throw new BadRequestFoundException("Cant find by '" + albumAlias + "'");
        }
    }

    @RequestMapping(value = "/model/{alias}")
    public ModelAndView model(
            @PathVariable("alias") String alias,
            HttpSession session) {
        try {
            ModelAndView model = new ModelAndView("models/model_albums");
            Optional<AuthUser> user = authorization.find(session.getId());
            model.addObject("user", user);
            Photomodel photomodel = this.gallery.getPhotomodel(alias);
            List<AlbumInfo> albums = this.gallery.getAlbumInfoByIds(photomodel.getInfo().getAlbumsIds());
            model.addObject("photomodel", photomodel);
            model.addObject("rows", new GridService(3).makeRows(albums));
            model.addObject("frontEndData", frontEndData);
            return model;
        } catch (RuntimeException ex) {
            throw new BadRequestFoundException("Cant find model by '" + alias + "'");
        }
    }

    @RequestMapping(value = "/models")
    public ModelAndView models(HttpSession session) {
        ModelAndView model = new ModelAndView("models/models");
        Optional<AuthUser> user = authorization.find(session.getId());
        model.addObject("user", user);
        model.addObject("photomodels", gallery.getAllPhotomodelsInfos());
        model.addObject("frontEndData", frontEndData);
        return model;
    }

    @RequestMapping(value = "/collectors_mode/collectors")
    public ModelAndView collectors(HttpSession session) {
        ModelAndView model = new ModelAndView("collectors_mode/collectors");
        Optional<AuthUser> user = authorization.find(session.getId());
        model.addObject("user", user);
        model.addObject("collectors", talexCollectorsService.getCollectorsWithImages());
        model.addObject("frontEndData", frontEndData);
        return model;
    }

    @RequestMapping(value = "/collectors_mode/list")
    public ModelAndView collectorsList(
            @RequestParam(value = "nick_name") String nickName,
            HttpSession session) {
        ModelAndView model = new ModelAndView("collectors_mode/list");

        try {
            Optional<AuthUser> user = authorization.find(session.getId());
            model.addObject("user", user);
            CollectorAccount collector = talexCollectorsService.getCollector(nickName);
            List<Integer> ids = collector.getImageCollection().getImagesIds2();
            List<ImageInfo> images = this.gallery.getImages(ids);

            model.addObject("rows", new GridService(6).makeRows(images));
            model.addObject("collector_account", collector);
            model.addObject("frontEndData", frontEndData);
            return model;
        } catch (RuntimeException ex) {
            throw new BadRequestFoundException("Cant find collector by '" + nickName + "'");
        }
    }

    @RequestMapping(value = "/tags/get_by_tag/{tagName}")
    public ModelAndView imagesByTag(
            @PathVariable("tagName") String tagName,
            HttpSession session) {
        ModelAndView model = new ModelAndView("tags/album_by_tags");
        Optional<AuthUser> user = authorization.find(session.getId());
        model.addObject("user", user);
        Tag tag = gallery.getTagsService().getTagByName(tagName).get();

        model.addObject("tag", tag);
        model.addObject("rows", new GridService(6).makeRows(tag.getImagesInfos()));
        model.addObject("frontEndData", frontEndData);

        return model;
    }

    @ResponseBody
    @RequestMapping(value = {"/img/{albumAlias}/{fileName}"},
            method = {RequestMethod.GET, RequestMethod.HEAD}, produces = {"image/jpeg"})
    public Object getImageFromAlbum(
            @PathVariable("albumAlias") String albumAlias,
            @PathVariable("fileName") String fileName,
            HttpSession session) throws IOException {

        DeterminateImage determinate = new DeterminateImage(fileName);
        ImageFile image = null;

        if (authorization.isUserActive(session.getId())) {
            image = gallery.getAlbum(albumAlias)
                    .getByQuality(determinate.getImgType())
                    .getByAlias(determinate.getImageId());
        } else {
            Album album = gallery.getAlbum(albumAlias);
            int imageAlias = determinate.getImageId();
            Quality quality = determinate.getImgType();
            if (this.bluredService.needBlured(
                    album, Optional.of(imageAlias))) {
                image = bluredService.get(album, quality, imageAlias);
            } else {
                image = gallery.getAlbum(albumAlias)
                        .getByQuality(determinate.getImgType())
                        .getByAlias(determinate.getImageId());
            }
        }

        if (!image.exists()) {
            throw new BadRequestFoundException("Cant find image by fileName = '" + fileName + "'");
        }

        return image.getBytes();
    }

    @RequestMapping(value = {"/googled853d75ea4c25e4b.html"})
    @ResponseBody
    public String googleVerification() {
        return "google-site-verification: googled853d75ea4c25e4b.html";
    }

    @RequestMapping(value = "/add_seconds_view_time/{imageAlias}")
    @ResponseStatus(value = HttpStatus.OK)
    public void addSecondsViewTime(
            @PathVariable("imageAlias") int imageAlias,
            HttpSession session) {

        if (antibrutTimeCounter.isCanHit(imageAlias, session.getId())) {
            gallery.addSecondsViewTime(imageAlias);
        }
    }

    @RequestMapping(value = "/hit_image/{imageAlias}")
    @ResponseStatus(value = HttpStatus.OK)
    public void hitImage(
            @PathVariable("imageAlias") int imageAlias,
            HttpSession session) {

        if (antibrutImage.isCanHit(imageAlias, session.getId())) {
            gallery.addHitImage(imageAlias);
        }

    }

    @RequestMapping(value = "/hit_album/{albumAlias}")
    @ResponseStatus(value = HttpStatus.OK)
    public void hitAlbum(
            @PathVariable("albumAlias") String albumAlias,
            HttpSession session) {

        if (antibrutAlbum.isCanHit(albumAlias.hashCode(), session.getId())) {
            gallery.addHitAlbum(albumAlias);
        }
    }
}
