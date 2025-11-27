package es.uniovi.eii.ds.commands;

import es.uniovi.eii.ds.editor.Editor;

public class ReplaceCommand implements MacroCommand {
    private final Editor editor;
    private final String find;
    private final String replace;

    public ReplaceCommand(Editor editor, String find, String replace) {
        this.editor = editor;
        this.find = find;
        this.replace = replace;
    }

    @Override
    public void execute() {
        editor.replace(find, replace);
    }
}
