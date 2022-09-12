package com.ciandt.summit.bootcamp2022.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "Usuarios")
public class User implements Serializable {

    @Id
    @Column(name = "Id")
    @NonNull
    private String id;

    @Column(name = "Nome")
    @NonNull
    private String nome;

    @ManyToOne
    @JoinColumn(name = "PlaylistId",
            referencedColumnName = "Id")
    private Playlist playlistId;

    @ManyToOne
    @JoinColumn(name = "TipoUsuarioId",
            referencedColumnName = "Id")
    @NonNull
    private UserType userTypeId;
}