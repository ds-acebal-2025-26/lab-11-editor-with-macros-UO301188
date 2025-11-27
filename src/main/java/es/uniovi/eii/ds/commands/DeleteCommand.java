package es.uniovi.eii.ds.commands;

import es.uniovi.eii.ds.editor.Editor;

public class DeleteCommand implements MacroCommand {
    private Editor editor;

    public DeleteCommand(Editor editor) {
        this.editor = editor;
    }

    @Override
    public void execute() {
        editor.delete();

    }

}
