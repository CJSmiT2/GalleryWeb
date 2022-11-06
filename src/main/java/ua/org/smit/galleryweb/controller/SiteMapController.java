package ua.org.smit.galleryweb.controller;

import java.sql.Timestamp;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ua.org.smit.gallery.Gallery;
import ua.org.smit.gallery.album.AlbumInfo;
import ua.org.smit.gallery.album.image.ImageInfo;
import ua.org.smit.gallery.tag.Tag;
import ua.org.smit.gallery.user.photomodel.PhotomodelInfo;
import ua.org.smit.sitemap.ChangeFreq;
import ua.org.smit.sitemap.LastMod;
import ua.org.smit.sitemap.Loc;
import ua.org.smit.sitemap.Priority;
import ua.org.smit.sitemap.SiteMap;
import static ua.org.smit.galleryweb.mvcconfig.WebAppConfig.getPropperties;

@Controller
public class SiteMapController {

    @Autowired
    private Gallery gallery;

    final String domain = getPropperties().getProperty("domain");

    private int updatePeriod = 60 * 60 * 1000; // 1 hour
    private long lastUpdate = System.currentTimeMillis();

    private String map;

    @RequestMapping(value = {"/sitemap.xml"})
    @ResponseBody
    public String getSiteMap() {
        if (map == null
                || (lastUpdate + updatePeriod) < System.currentTimeMillis()) {
            map = createMap();
            lastUpdate = System.currentTimeMillis();
        }
        return map;
    }

    String createMap() {
        Timestamp lastUpdate = gallery.getLastAlbums(1).get(0).getInfo().getCreated();

        SiteMap siteMap = new SiteMap();

        siteMap.addUrl(new Loc(domain, ""),
                Optional.of(new LastMod(lastUpdate)),
                Optional.of(ChangeFreq.always),
                Optional.of(new Priority("1.0")));

        for (Tag tag : gallery.getTagsService().getAllTags()) {
            siteMap.addUrl(new Loc(domain, "tags/get_by_tag/" + tag.getName()),
                    Optional.of(new LastMod(lastUpdate)),
                    Optional.of(ChangeFreq.weekly),
                    Optional.of(new Priority("0.5")));
        }

        for (PhotomodelInfo photoModel : this.gallery.getAllPhotomodelsInfos()) {
            siteMap.addUrl(new Loc(domain, "model/" + photoModel.getAlias()),
                    Optional.of(new LastMod(lastUpdate)),
                    Optional.of(ChangeFreq.monthly),
                    Optional.of(new Priority("0.8")));
        }

        for (AlbumInfo album : this.gallery.getAllAlbumsInfos()) {
            siteMap.addUrl(new Loc(domain, "album/" + album.getAlias()),
                    Optional.of(new LastMod(album.getUpdated())),
                    Optional.of(ChangeFreq.monthly),
                    Optional.of(new Priority("0.7")));
        }

        for (ImageInfo imageInfo : this.gallery.getAllImagesInfos()) {
            imageInfo.bindAlbumInfo();
            if (imageInfo.getAlbumInfo().isPresent()) {
                siteMap.addUrl(new Loc(domain, "image_view/" + imageInfo.getAlbumInfo().get().getAlias() + "/" + imageInfo.getAlias()),
                        Optional.of(new LastMod(imageInfo.getCreated())),
                        Optional.of(ChangeFreq.never),
                        Optional.of(new Priority("1")));
            }
        }

        return siteMap.getResult();
    }

}
