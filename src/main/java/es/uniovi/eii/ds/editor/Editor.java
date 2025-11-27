package es.uniovi.eii.ds.editor;

public class Editor {
    private StringBuilder text = new StringBuilder();

    public String getText() {
        return this.text.toString();
    }

    public void setText(String newContent) {
        this.text = new StringBuilder(newContent);
    }

    public void insert(String[] words) {
        for (String word : words) {
            text.append(" ").append(word);
        }
    }

    public void delete() {
        int indexOfLastWord = text.toString().trim().lastIndexOf(" ");
        if (indexOfLastWord == -1)
            text = new StringBuilder("");
        else
            text.setLength(indexOfLastWord);
    }

    public void replace(String find, String replace) {
        this.text = new StringBuilder(
                this.text.toString().replace(find, replace));
    }

    public void drawLogo() {
        System.out.println(LOGO);
    }

    public void showHelp() {
        System.out.println(HELP);
    }

    private static final String LOGO = """

            ███╗   ███╗ █████╗  ██████╗████████╗███████╗██╗  ██╗
            ████╗ ████║██╔══██╗██╔════╝╚══██╔══╝██╔════╝╚██╗██╔╝
            ██╔████╔██║███████║██║        ██║   █████╗   ╚███╔╝
            ██║╚██╔╝██║██╔══██║██║        ██║   ██╔══╝   ██╔██╗
            ██║ ╚═╝ ██║██║  ██║╚██████╗   ██║   ███████╗██╔╝ ██╗
            ╚═╝     ╚═╝╚═╝  ╚═╝ ╚═════╝   ╚═╝   ╚══════╝╚═╝  ╚═╝
            """;

    private static final String HELP =
                                     """
                                             ┌──────────────────────┬─────────────────────────────────────────────┐
                                             │ open <file>          │                                             │
                                             │ insert <text>        │ append text to the end                      │
                                             │ delete               │ delete the last word                        │
                                             │ replace <a> <b>      │ replace <a> with <b> in the whole document  │
                                             ├──────────────────────┼─────────────────────────────────────────────┤
                                             │ record <macro>       │ start recording a macro                     │
                                             │ stop                 │ stop recording                              │
                                             │ execute <macro>      │ execute the specified macro                 │
                                             ├──────────────────────┼─────────────────────────────────────────────┤
                                             │ help                 │                                             │
                                             │ exit                 │                                             │
                                             └──────────────────────┴─────────────────────────────────────────────┘
                                             """;
}
