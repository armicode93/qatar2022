package com.example.qatar2022.entities;




import com.sun.istack.NotNull;
import lombok.*;
import org.apache.tomcat.util.codec.binary.Base64;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name="image")
public class Image implements Serializable {

    @Id
    @GeneratedValue
    private Long id;




    private String nom;
    private String type;

     @Lob // to storage large objects into the database
    @Column(name="image", length = 1000)
    private byte[] imageByte;


   public String generateBase64Image()
    {
        return Base64.encodeBase64String(this.imageByte);
    }




}
