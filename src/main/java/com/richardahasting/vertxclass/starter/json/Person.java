package com.richardahasting.vertxclass.starter.json;

public class Person {
  private Integer id;
  private String name;
  private Boolean lovesVertX;

  public Person()
  {}

  public Person(Integer id, String name, Boolean lovesVertX) {
    this.id = id;
    this.name = name;
    this.lovesVertX = lovesVertX;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Boolean getLovesVertX() {
    return lovesVertX;
  }

  public void setLovesVertX(Boolean lovesVertX) {
    this.lovesVertX = lovesVertX;
  }

  @Override
  public String toString()
  {
    return "Person: " + getId() + ":" + getName()+":" + getLovesVertX();
  }
}
