package com.eum.freeBoard.entity;

import javax.persistence.*;

import com.eum.bbs.entity.Bbs;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("freeBoard")
@Getter @Setter
public class FreeBoard extends Bbs{

}
