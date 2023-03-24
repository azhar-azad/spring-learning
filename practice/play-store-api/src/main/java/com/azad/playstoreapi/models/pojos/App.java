package com.azad.playstoreapi.models.pojos;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class App {


    @NotNull(message = "App name cannot be empty")
    @Size(min = 2, max = 30, message = "App name length has to be between 2 to 50 characters.")
    protected String appName;
    protected String appShortName;
    protected String appLogo;
    protected String appDescription;

    @NotNull(message = "App size must be provided in MB")
    protected Double appSizeMB;

    @NotNull(message = "PG Rating must be provided")
    protected String pgRating;
    protected String downloadCount;
    protected Double currentRating;

    @NotNull(message = "Must provide if this app is a game")
    protected String isGame;

    public App() {
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppShortName() {
        return appShortName;
    }

    public void setAppShortName(String appShortName) {
        this.appShortName = appShortName;
    }

    public String getAppLogo() {
        return appLogo;
    }

    public void setAppLogo(String appLogo) {
        this.appLogo = appLogo;
    }

    public String getAppDescription() {
        return appDescription;
    }

    public void setAppDescription(String appDescription) {
        this.appDescription = appDescription;
    }

    public String getPgRating() {
        return pgRating;
    }

    public void setPgRating(String pgRating) {
        this.pgRating = pgRating;
    }

    public Double getCurrentRating() {
        return currentRating;
    }

    public void setCurrentRating(Double currentRating) {
        this.currentRating = currentRating;
    }

    public Double getAppSizeMB() {
        return appSizeMB;
    }

    public void setAppSizeMB(Double appSizeMB) {
        this.appSizeMB = appSizeMB;
    }

    public String getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(String downloadCount) {
        this.downloadCount = downloadCount;
    }

    public String getIsGame() {
        return isGame;
    }

    public void setIsGame(String isGame) {
        this.isGame = isGame;
    }
}
