package ua.org.smit.galleryweb.controller;

import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import ua.org.smit.authorization.AuthUser;
import ua.org.smit.common.web.utilites.grid.GridService;
import ua.org.smit.gallery.Gallery;
import ua.org.smit.gallery.album.Album;
import ua.org.smit.gallery.album.Quality;
import ua.org.smit.gallery.album.image.ImageInfo;
import ua.org.smit.galleryweb.utilites.MultipartFileConverter;
import ua.org.smit.authorization.Authorization;
import static ua.org.smit.galleryweb.mvcconfig.WebAppConfig.frontEndData;

@Controller
@RequestMapping(value = "/admin_gallery")
public class AdministratorController {

    static Logger log = Logger.getLogger(AdministratorController.class);

    @Autowired
    private Authorization authorization;

    @Autowired
    private Gallery gallery;

    @RequestMapping(value = "/home")
    public Object home(HttpSession session) {

        isAdmin(session);
        ModelAndView model = new ModelAndView("administrator/home");
        model.addObject("user", authorization.findByLogin("SmiT"));

        List<Album> albums = this.gallery.getAllAlbums();
        model.addObject("albums", albums);
        model.addObject("frontEndData", frontEndData);
        return model;
    }

    @RequestMapping(value = "/manage_album_info", method = RequestMethod.GET)
    public Object manageAlbumInfo(HttpSession session) {

        isAdmin(session);
        ModelAndView model = new ModelAndView("administrator/manage_album_info");
        model.addObject("user", authorization.findByLogin("SmiT"));
        model.addObject("frontEndData", frontEndData);
        return model;
    }

    @RequestMapping(value = "/manage_album_info", method = RequestMethod.POST)
    public String manageAlbumInfoSubmit(
            HttpSession session,
            @RequestParam(value = "title") String title,
            @RequestParam(value = "alias") String alias) {

        isAdmin(session);
        gallery.createAlbum(title, alias);

        return "redirect:edit_album?alias=" + alias;
    }

    @RequestMapping(value = "/make_as_cover")
    public String makeAsCover(
            HttpSession session,
            @RequestParam(value = "albumAlias") String albumAlias,
            @RequestParam(value = "imageAlias") String imageAlias) {

        isAdmin(session);

        Album album = this.gallery.getAlbum(albumAlias);
        album.setCover(Integer.valueOf(imageAlias));

        return "redirect:/admin_gallery/edit_album?alias=" + albumAlias;
    }

    @RequestMapping(value = "/edit_album")
    public ModelAndView editAlbum(
            @RequestParam(value = "alias") String alias,
            HttpSession session) {

        isAdmin(session);

        Album album = gallery.getAlbum(alias);
        List<ImageInfo> imagesInfos = album.getImages().getAllByAlbum();

        ModelAndView model = new ModelAndView("administrator/edit_album");
        model.addObject("album", album);
        model.addObject("rows", new GridService(6).makeRows(imagesInfos));
        model.addObject("user", authorization.findByLogin("SmiT"));
        model.addObject("frontEndData", frontEndData);
        return model;
    }

    @RequestMapping(value = "/upload", method = RequestMethod.GET)
    public Object upload(@RequestParam(value = "alias") String alias,
            HttpSession session) {

        isAdmin(session);

        ModelAndView model = new ModelAndView("administrator/upload");
        model.addObject("user", authorization.findByLogin("SmiT"));

        Album album = gallery.getAlbum(alias);
        model.addObject("album", album);
        model.addObject("frontEndData", frontEndData);
        return model;
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String uploadSubmit(@RequestParam(value = "alias") String alias,
            @RequestParam(value = "quality") String qualityS,
            List<MultipartFile> files,
            HttpSession session) {

        isAdmin(session);

        Quality quality = Quality.valueOf(qualityS.toUpperCase());

        Album album = gallery.getAlbum(alias);
        album.getMerge().mergeFiles(
                MultipartFileConverter.toFiles(files),
                quality);
        return "redirect:upload?alias=" + alias;
    }

    @RequestMapping(value = "/delete_image")
    public String deleteImage(
            HttpSession session,
            @RequestParam(value = "albumAlias") String albumAlias,
            @RequestParam(value = "imageAlias") Integer imageAlias) {

        isAdmin(session);

        this.gallery.deleteImage(albumAlias, imageAlias);

        return "redirect:/admin_gallery/edit_album?alias=" + albumAlias;
    }

    @RequestMapping(value = "/delete_album")
    public String deleteAlbum(
            HttpSession session,
            @RequestParam(value = "albumAlias") String albumAlias) {

        isAdmin(session);

        this.gallery.deleteAlbum(albumAlias);

        return "redirect:/admin_gallery/home";
    }

    private void isAdmin(HttpSession session) {
        Optional<AuthUser> user = authorization.find(session.getId());
        if (!user.get().isAdmin()) {
            throw new RuntimeException();
        }
    }
}
