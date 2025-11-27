package es.uniovi.eii.ds.macro;

import java.util.ArrayList;
import java.util.List;

import es.uniovi.eii.ds.commands.MacroCommand;

public class Macro implements MacroCommand {
    private final List<MacroCommand> instructions =
                                                  new ArrayList<MacroCommand>();
    private final String name;

    public Macro(String name) {
        this.name = name;

    }

    public String getName() {
        return this.name;
    }

    public void addInstruction(MacroCommand command) {
        instructions.add(command);
    }

    @Override
    public void execute() {

        for (MacroCommand command : instructions) {

            command.execute();

        }
    }
}
