package giel.hva.nl.gamebacklog.Models;

import java.util.Date;

public class GameObject {
    private String title;
    private String platform;
    private String notes;
    private Date date;
    private GameStatus gameStatus;

    public enum GameStatus{
        WantToPlay ("Want to play"),
        Playing ("Playing"),
        Stalled ("Stalled"),
        Dropped ("Dropped");

        private String friendlyName;

        GameStatus(String friendlyName){
            this.friendlyName = friendlyName;
        }

        @Override public String toString(){
            return friendlyName;
        }
    }

    public GameObject(String title, String platform, String notes, Date date, GameStatus gameStatus) {
        this.title = title;
        this.platform = platform;
        this.notes = notes;
        this.date = date;
        this.gameStatus = gameStatus;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }
}


