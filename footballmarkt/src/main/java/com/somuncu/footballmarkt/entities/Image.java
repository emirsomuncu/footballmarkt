package com.somuncu.footballmarkt.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Blob;

@NoArgsConstructor
@Data
@Entity
@Table
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fileName;
    private String fileType;

    @Lob
    private Blob image;

    private String downloadUrl;

    @ManyToOne
    private Player player ;

    @ManyToOne
    private Club club;

    @ManyToOne
    private Trophy trophy ;

    @ManyToOne
    private League league;

    public Image(Long id, String fileName, String fileType, Blob image, String downloadUrl) {
        this.id = id;
        this.fileName = fileName;
        this.fileType = fileType;
        this.image = image;
        this.downloadUrl = downloadUrl;
    }

    @Override
    public String toString() {
        return "Image{" +
                "id=" + id +
                ", fileName='" + fileName + '\'' +
                ", fileType='" + fileType + '\'' +
                ", image=" + image +
                ", downloadUrl='" + downloadUrl + '\'' +
                '}';
    }
}
