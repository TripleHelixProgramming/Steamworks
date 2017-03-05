package org.usfirst.frc.team2363.util;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Command;

import java.util.ArrayList;
import java.util.List;

/**
 * A binary selector using 3 buttons that will return the command that is currently selected.
 * @author robotics
 */
public class AutonomousSelector extends BinaryConverter {
    
    private List<Command> commandList = new ArrayList<>();
    
    /**
     * Initialize a new autonomous selector with the provided inputs.
     * @param lsb Least significant bit
     * @param mb Middle bit
     * @param msb Most significant bit
     */
    public AutonomousSelector(DigitalInput lsb, DigitalInput mb, DigitalInput msb){
        super(lsb, mb, msb);
    }
    
    public void setCommands(List<Command> commandList){
        this.commandList = commandList;
    }

    public Command getSelectedCommand(){
        if (getSelectedNumber() >= commandList.size()) {
            return null;
        }
        return commandList.get(this.getSelectedNumber());
    }
       
}