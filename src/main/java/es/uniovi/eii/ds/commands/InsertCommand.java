package es.uniovi.eii.ds.commands;

import java.util.Arrays;

import es.uniovi.eii.ds.editor.Editor;

public class InsertCommand implements MacroCommand {
    private Editor editor;
    private String[] wordsToInsert;

    public InsertCommand(Editor editor, String[] wordsToInsert) {
        this.editor = editor;
        this.wordsToInsert = Arrays.copyOf(wordsToInsert, wordsToInsert.length);
    }

    @Override
    public void execute() {
        editor.insert(wordsToInsert);
    }

}
