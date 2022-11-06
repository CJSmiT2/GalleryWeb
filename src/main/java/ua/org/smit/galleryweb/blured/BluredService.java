package ua.org.smit.galleryweb.blured;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import ua.org.smit.common.filesystem.FolderCms;
import ua.org.smit.common.filesystem.TxtFile;
import ua.org.smit.gallery.album.Album;
import ua.org.smit.gallery.album.Quality;
import ua.org.smit.gallery.album.image.ImageFile;

public class BluredService {

    private final List<Integer> imagesAliases = new ArrayList<>();
    private final List<String> albumsAliases = new ArrayList<>();
    private final FolderCms bluredImagesFolder;

    public BluredService(TxtFile imagesFile, TxtFile albumsFile, FolderCms bluredImagesFolder) {
        this.imagesAliases.addAll(imagesFile.readIntegers());
        this.albumsAliases.addAll(albumsFile.readByLines());
        this.bluredImagesFolder = bluredImagesFolder;
    }

    public boolean needBlured(Album album, Optional<Integer> imageAlias) {
        for (String albumAlias : this.albumsAliases) {
            if (albumAlias.equalsIgnoreCase(album.getInfo().getAlias())) {
                return true;
            }
        }

        if (imageAlias.isPresent()) {
            for (Integer img : this.imagesAliases) {
                if (img == imageAlias.get()) {
                    return true;
                }
            }
        }
        return false;
    }

    public ImageFile get(Album album, Quality quality, int imageAlias) {
        String fileName;
        if (quality == quality.ORIGINAL) {
            fileName = album.getInfo().getAlias() + "_" + imageAlias + ".jpeg";
        } else {
            String type;
            if (quality == quality.THUMBNAIL) {
                type = "thumb";
            } else {
                type = "resized";
            }
            fileName = album.getInfo().getAlias() + "_" + type + "-" + imageAlias + ".jpeg";
        }

        File file = new File(this.bluredImagesFolder + File.separator + fileName);
        return new ImageFile(file, imageAlias);
    }

}
