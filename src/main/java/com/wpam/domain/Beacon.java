package com.wpam.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class Beacon {

    @Id
    private String name;
    @ManyToOne
    private User user;
    private BeaconStatus status;
}