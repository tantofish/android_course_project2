package com.tantofish.androidcourseproject2.models;

/**
 * Created by yutu on 8/8/15.
 */
public class SearchFilter {
    private String imageSize;
    private String imageColor;
    private String imageType;
    private String searchSite;

    public SearchFilter() {
        imageSize  = "";
        imageColor = "";
        imageType  = "";
        searchSite = "";
    }

    public String getImageSize() {
        //do mapping
        if(imageSize == null)   return imageSize;

        if (imageSize.equals("small")) {
            return "icon";
        } else if (imageSize.equals("medium")) {
            return "medium";
        } else if (imageSize.equals("large")) {
            return "xxlarge";
        } else if (imageSize.equals("extra-large")) {
            return "huge";
        } else {
            return "";
        }
    }

    public void setImageSize(String imageSize) {
        this.imageSize = imageSize;
    }

    public String getSearchSite() {
        return searchSite;
    }

    public void setSearchSite(String searchSite) {
        this.searchSite = searchSite;
    }

    public String getImageType() {
        if(imageType.equals("default")) return "";
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public String getImageColor() {
        if(imageColor.equals("default")) return "";
        return imageColor;
    }

    public void setImageColor(String imageColor) {
        this.imageColor = imageColor;
    }
}
