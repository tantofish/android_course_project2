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
    }


    public String getImageSize() {
        return imageSize;
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

        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public String getImageColor() {

        return imageColor;
    }

    public void setImageColor(String imageColor) {
        this.imageColor = imageColor;
    }
}
