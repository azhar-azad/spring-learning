package com.azad.musiclibrary.models.entities;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "album")
public class AlbumEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String albumName;

    @Column(nullable = false)
    private LocalDate released;

    public AlbumEntity() {
    }

    public Long getId() {
        return id;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public LocalDate getReleased() {
        return released;
    }

    public void setReleased(LocalDate released) {
        this.released = released;
    }
}
