package com.abdulwahab.sosgame.model;

// Represents a single SOS line in cell coordinates.
public class SOSLine {
    public final int r1, c1, r2, c2;
    public SOSLine(int r1, int c1, int r2, int c2) {
        this.r1 = r1; this.c1 = c1; this.r2 = r2; this.c2 = c2;
    }
}
