package org.usfirst.frc.team2363.util;

import edu.wpi.first.wpilibj.DigitalInput;

/**
 * Returns the decimal version of a 3 bit binary number.
 * @author robotics
 */
public class BinaryConverter {
    private DigitalInput lsb, mb, msb;
    
    /**
    * Initialize a new selector with the provided inputs.
    * @param lsb Least significant bit.
    * @param mb Middle bit.
    * @param msb Most significant bit.
    */
    public BinaryConverter(DigitalInput lsb, DigitalInput mb, DigitalInput msb) {
        this.lsb = lsb;
        this.mb = mb;
        this.msb = msb;
        
    }
    
    /**
     * Retrieves the current decimal value based on the 3 binary bits.
     * @return The decimal value.
     */
    public int getSelectedNumber(){
        return (lsb.get() ? 1 : 0) +        
                2 * (mb.get() ? 1 : 0) +
                4 * (msb.get() ? 1 : 0);
        
    }
    
}