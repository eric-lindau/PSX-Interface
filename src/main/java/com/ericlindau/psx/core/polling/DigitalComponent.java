package com.ericlindau.psx.core.polling;

public class DigitalComponent extends GenericPollable {
  @Override
  public float getPollData() {
    try {
      return this.getComponent().getPollData();
    } catch(NullPointerException n) {
//      System.out.println("null component");
      return 0;
    }
  }
}
