package com.emarbox.entity.nonstandard;

import lombok.Data;

import javax.persistence.Table;

@Data
@Table(name="nn_media")
public class NonstandardMedia {

  private Long id;
  private String mediaName;
  private String corporateName;



}
