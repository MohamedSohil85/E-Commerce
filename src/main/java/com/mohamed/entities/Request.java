package com.mohamed.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
@DiscriminatorValue(value = "Request")
public class Request extends Ad  {

}
