package com.wpam.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class ChildServer {

    @Id
    @GeneratedValue
    private Long id;
    private String ip;
    private String port;
    private boolean isActive;
}
