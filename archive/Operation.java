package archive;

public enum Operation {
    CREATE("Create a new archive"),
    ADD("Add new file in archive"),
    REMOVE("Remove file from archive"),
    EXTRACT("Extract archive"),
    CONTENT("Watch the content of archive"),
    EXIT("Exit");

    private String name;

    Operation(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
