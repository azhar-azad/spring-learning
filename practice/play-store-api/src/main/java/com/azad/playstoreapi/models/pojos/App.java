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

    @NotNull(message = "App size must be provided in KB")
    @Min(value = 1, message = "App size has to be at least 1KB.")
    protected Double appSizeKB;

    @NotNull(message = "PG Rating must be provided")
    protected String pgRating;
    protected Integer downloadCount;
    protected Double currentRating;

    @NotNull(message = "Must provide if those app is a game")
    protected Boolean isGame;

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

    public Double getAppSizeKB() {
        return appSizeKB;
    }

    public void setAppSizeKB(Double appSizeKB) {
        this.appSizeKB = appSizeKB;
    }

    public String getPgRating() {
        return pgRating;
    }

    public void setPgRating(String pgRating) {
        this.pgRating = pgRating;
    }

    public Integer getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(Integer downloadCount) {
        this.downloadCount = downloadCount;
    }

    public Double getCurrentRating() {
        return currentRating;
    }

    public void setCurrentRating(Double currentRating) {
        this.currentRating = currentRating;
    }

    public Boolean getGame() {
        return isGame;
    }

    public void setGame(Boolean game) {
        isGame = game;
    }
}
