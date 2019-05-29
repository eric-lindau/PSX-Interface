package com.ericlindau.psx.core.processing;

import com.ericlindau.psx.config.Configured;
import com.ericlindau.psx.core.polling.Pollable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnalogValue extends Value {

  @Configured
  private long min, max;
  @Configured
  private boolean centered, inverted;

  HashMap<Integer, String> thresholds; // TODO: Get from .toml

  public AnalogValue(List<Pollable> components) {
    super(components);
  }

  public String getPollData() { // TODO: Consider memory (GC) & initialization
    Long literal = 0L;

    for (Pollable component : components) {
      float a = component.getPollData();
      literal += Math.round((max * a) + (min * (1 - a))); // Scaling
    }

    if (literal > max) {
      literal = max;
    }
    if (literal < min) {
      literal = min;
    }

    if (centered) {
      literal /= 2; // TODO: Verify
    }

    if (inverted) {
      literal *= -1;
    }

    for (Map.Entry<Integer, String> threshold : thresholds.entrySet()) {
      if (literal < threshold.getKey()) {
        return threshold.getValue();
      }
    }

    return literal.toString();
  }

  public long getMin() {
    return this.min;
  }

  public long getMax() {
    return this.max;
  }

}