package main;

import java.io.File;

public class NameFixer {

    public static void main(String[] args) {
        fixLargeCards();
        fixSmallCards();
    }

    private static void fixLargeCards() {
        String path = "src/main/resources/images/largecards";
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();
        for (File file : listOfFiles) {
            if (file.isFile()) {
                String name = file.getName();
                String[] part = name.split("_");
                for (int i = 1; i < part.length; i++) {
                    part[i] = part[i].substring(0, 1).toUpperCase() + part[i].substring(1);
                }
                StringBuilder newName = new StringBuilder(part[1]);
                for (int i = 2; i < part.length; i++) {
                    if (Character.isDigit(part[i].charAt(0))) {
                        newName.append(part[i]);
                    } else {
                        newName.append(" ").append(part[i]);
                    }
                }
                boolean ok = file.renameTo(new File(path + "/" + newName));
            }
        }
    }

    private static void fixSmallCards() {
        String path = "src/main/resources/images/smallcards";
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();
        for (File file : listOfFiles) {
            if (file.isFile()) {
                String name = file.getName();
                String[] part = name.split("_");
                for (int i = 1; i < part.length; i++) {
                    part[i] = part[i].substring(0, 1).toUpperCase() + part[i].substring(1);
                }
                StringBuilder newName = new StringBuilder(part[1]);
                for (int i = 2; i < part.length; i++) {
                    if (Character.isDigit(part[i].charAt(0))) {
                        newName.append(part[i]);
                    } else {
                        newName.append(" ").append(part[i]);
                    }
                }
                boolean ok = file.renameTo(new File(path + "/" + newName));
            }
        }
    }
}
