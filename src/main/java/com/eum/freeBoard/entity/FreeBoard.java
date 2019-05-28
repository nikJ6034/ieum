package com.eum.freeBoard.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.eum.bbs.entity.Bbs;

import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("freeBoard")
@Getter @Setter
public class FreeBoard extends Bbs{

}
