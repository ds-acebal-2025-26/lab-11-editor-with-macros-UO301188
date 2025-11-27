package es.uniovi.eii.ds.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import es.uniovi.eii.ds.commands.DeleteCommand;
import es.uniovi.eii.ds.commands.InsertCommand;
import es.uniovi.eii.ds.commands.MacroCommand;
import es.uniovi.eii.ds.commands.ReplaceCommand;
import es.uniovi.eii.ds.editor.Editor;
import es.uniovi.eii.ds.macro.Macro;

public class Main {

    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

    Editor editor = new Editor();
    // Esto mejor que lo haga todo el editor
    private final Map<String, Macro> recordedMacros = new HashMap<>();
    private Macro currentRecording = null;

    public static void main(String[] args) {
        new Main().run();
    }

    // Main program loop.
    public void run() {
        editor.drawLogo();
        editor.showHelp();

        while (true) {
            UserCommand command = promptUser();
            String[] args = command.args;

            MacroCommand commandToExecute = null;
            switch (command.name) {
            case "open" -> open(args);
            case "insert" -> {
                commandToExecute = new InsertCommand(editor, args);
            }
            case "delete" -> {
                commandToExecute = new DeleteCommand(editor);
            }
            case "replace" -> {
                if (!checkArguments(args, 2, "replace <find> <replace>"))
                    break;
                commandToExecute = new ReplaceCommand(editor, args[0], args[1]);
            }
            case "help" -> editor.showHelp();
            case "record" -> {
                if (!checkArguments(args, 1, "record <macroName>"))
                    break;
                String macroName = args[0];

                currentRecording = new Macro(macroName);
            }
            case "stop" -> {
                if (currentRecording != null) {
                    recordedMacros.put(currentRecording.getName(),
                            currentRecording);
                    currentRecording = null;
                }
            }
            case "execute" -> {
                if (!checkArguments(args, 1, "execute <macroName>")) {
                    break;
                }
                String macroName = args[0];
                MacroCommand toExecute = recordedMacros.get(macroName);

                if (toExecute != null) {

                    commandToExecute = toExecute;
                } else {
                    System.out.println("Macro '" + macroName + "' not found.");
                }
            }
            default -> {
                System.out.println("Unknown command");
                continue;
            }
            }

            if (commandToExecute != null) {
                commandToExecute.execute();

                if (currentRecording != null) {
                    currentRecording.addInstruction(commandToExecute);
                } else {
                    System.out.println(editor.getText().trim());
                }
            }

        }
    }

    // $-- Some individual user commands that do a bit more work ---------------

    private void open(String[] args) {
        if (!checkArguments(args, 1, "open <file>"))
            return;
        try {
            String filename = args[0];
            editor.setText(new StringBuilder(readFile(filename)).toString());
            System.out.println(editor.getText().trim());
        } catch (Exception e) {
            System.out.println("Document could not be opened");
        }
    }

    private boolean checkArguments(String[] args, int expected, String syntax) {
        if (args.length != expected) {
            System.out.println("Invalid number of arguments => " + syntax);
            return false;
        }
        return true;
    }

    private String readFile(String filename) {
        InputStream in = getClass().getResourceAsStream("/" + filename);
        if (in == null)
            throw new IllegalArgumentException("File not found: " + filename);

        try (BufferedReader input = new BufferedReader(
                new InputStreamReader(in))) {
            StringBuilder result = new StringBuilder();
            String line;
            boolean firstLine = true;
            while (( line = input.readLine() ) != null) {
                if (!firstLine)
                    result.append(System.lineSeparator());
                result.append(line);
                firstLine = false;
            }
            return result.toString();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    // $-- Auxiliary methods ---------------------------------------------------

    // YOU DON'T NEED TO UNDERSTAND OR MODIFY THE CODE BELOW THIS LINE

    private record UserCommand(String name, String[] args) {
    }

    // Prompts the user and reads a line of input and returns it as a record
    // with
    // the command and its arguments. If EOF is reached (i.e., there are nothing
    // to
    // read), an error occurs or the user types "exit", the program exits. If
    // there
    // are no arguments, the args array is empty.
    //
    // Example:
    //
    // > insert "no quiero acordarme" --> returns UserInput("insert", ["no",
    // "quiero", "acordarme"])
    // > delete --> returns UserInput("delete", [])
    //
    private UserCommand promptUser() {
        while (true) {
            System.out.print("> ");
            try {
                String line = in.readLine();
                if (line == null)
                    System.exit(0);
                if (line.equals("exit"))
                    exit();
                if (line.isBlank())
                    continue;
                String[] parts = line.split("\\s+");
                return new UserCommand(parts[0],
                        Arrays.copyOfRange(parts, 1, parts.length));
            } catch (IOException e) {
                System.out.println("Error reading input");
                System.exit(2);
            }
        }
    }

    private void exit() {
        System.out.println("Goodbye!");
        System.exit(0);
    }
}
