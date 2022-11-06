package ua.org.smit.galleryweb.utilites;

import ua.org.smit.gallery.album.Quality;

public class DeterminateImage {

    private Quality imgType;
    private String imageId;

    public DeterminateImage(String fileName) {
        if (fileName.contains(".jpg") || fileName.contains(".jpeg")) {
            fileName = fileName.substring(0, fileName.lastIndexOf('.'));
        }

        String imgTypeString;
        if (fileName.contains("-")) {
            String[] imageInfo = fileName.split("-");
            imgTypeString = imageInfo[0];
            imageId = imageInfo[1];
            if (imgTypeString.contains("thumb")) {
                imgType = Quality.THUMBNAIL;
            } else if (imgTypeString.contains("resized")) {
                imgType = Quality.RESIZED;
            } else if (imgTypeString.contains("upscaled_x2")) {
                imgType = Quality.UPSCALE_X2;
            } else {
                imgType = Quality.ORIGINAL;
            }
        } else {
            imgType = Quality.ORIGINAL;
            imageId = fileName;
        }
    }

    public Quality getImgType() {
        return imgType;
    }

    public int getImageId() {
        return Integer.valueOf(imageId);
    }

}
