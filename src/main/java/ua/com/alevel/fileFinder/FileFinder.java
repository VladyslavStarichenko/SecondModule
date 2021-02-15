package ua.com.alevel.fileFinder;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class FileFinder {
    public static void main(String[] args) {
        List<File> files = new ArrayList<>();
        File fileToTest ;
        String name = "";
        File currentDirectory;
        commandToChoose();
        if(commandToChoose().equals("1")){
            fileToTest = new File(getPath());

            print(searchTXTFiles(fileToTest,files));
        }else if(commandToChoose().equals("2")){

            fileToTest = new File(getPath());
            System.out.println("Enter the extension");
            name = getName();
            print(searchFilesWithPath(fileToTest,files,name));
        }
        else if(commandToChoose().equals("3")){
            fileToTest = new File(getPath());
                Long size = getSize();
                print(searchWithSize(fileToTest,size,files));
        }
        else if(commandToChoose().equals("4")){
            File fileToCheck = new File(getPath());
            print(searchDirectoryByName(fileToCheck,getName(),files));
        }
        else if(commandToChoose().equals("5")){

            fileToTest = new File(new File(".").getAbsolutePath());
                print(searchByName(fileToTest,getName(),files));
        }
        else if(commandToChoose().equals("6")){

            try {
                fileToTest = new File(new File(".").getAbsolutePath());
                print(searchByKeyInText(fileToTest,getKey(),files));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        else if(commandToChoose().equals("7")){
            System.exit(0);
        }
        else{
            System.out.println("Choose the right command");


        }


    }

    private static String getKey() {
        Scanner key = new Scanner(System.in);
        return key.nextLine();
    }

    private static Long getSize() {
        Scanner name = new Scanner(System.in);
        return name.nextLong();
    }

    private static String commandToChoose(){
        System.out.println("Hello it a file finder choose operation below to implement");
        System.out.println("Press 1 to find txt file");
        System.out.println("Press 2 to find file by its extension");
        System.out.println("Press 3 to find file by size");
        System.out.println("Press 4 to find directory");
        System.out.println("Press 5 to find file by name");
        System.out.println("Press 6 to find file by key in file");
        System.out.println("Press 7 to exit");
        Scanner scanner = new Scanner(System.in);
        String result = scanner.nextLine();
        return result;
    }
    private static String getName() {
        Scanner name = new Scanner(System.in);
        return name.nextLine();
    }

    private static String getPath() {
        System.out.println("Please enter the path");
        Scanner path = new Scanner(System.in);
        return path.nextLine();
    }

    private static void print(List<File>fileList){
        for (File file : fileList){
            System.out.println(file.getAbsolutePath());
        }
    }

    private static List<File> searchTXTFiles(File root, List<File> fileList){
        File currentDirectory = new File(new File(".").getAbsolutePath());
        if(root == null || root.toString() ==  ""){
            root = currentDirectory;
        }
        if(root.isDirectory()){
            File [] directories = root.listFiles();
            if(directories != null){
                for(File file : directories){
                    if(file.isDirectory()){
                        searchTXTFiles(file,fileList);
                    }else if(file.getName().toLowerCase().endsWith(".txt")){
                        fileList.add(file);
                    }
                }
            }
        }
        return fileList;

    }

    private  static List<File> searchByName(File currentDirectory,String name, List<File> fileList){
        if(currentDirectory.isDirectory()){
            File [] directories = currentDirectory.listFiles();
            if(directories != null){
                for(File file : directories){
                    if(file.isDirectory()){
                        searchByName(file,name,fileList);
                    }else if(file.getName().toLowerCase().equals(name.toLowerCase())){
                        fileList.add(file);
                    }
                }
            }
        }
        return fileList;
    }

    private  static List<File> searchWithSize(File currentDirectory,Long size, List<File> fileList){
        if(currentDirectory.isDirectory()){
            File [] directories = currentDirectory.listFiles();
            if(directories != null){
                for(File file : directories){
                    if(file.isDirectory()){
                        searchWithSize(file,size,fileList);
                    }else if(file.getUsableSpace() == size){
                        fileList.add(file);
                    }else {
                        throw new RuntimeException("There is no files with parameters like this");
                    }
                }
            }
        }
        return fileList;
    }



    private  static List<File> searchFilesWithPath(File root, List<File> fileList, String extension){
        if(root.isDirectory()){
            File [] directories = root.listFiles();
            if(directories != null){
                for(File file : directories){
                    if(file.isDirectory()){
                        searchTXTFiles(file,fileList);
                    }else if(file.getName().toLowerCase().endsWith(extension)){
                        fileList.add(file);
                    }
                }
            }
        }
        return  fileList;
    }

    private static  List<File> searchDirectoryByName(File root,String name, List<File> fileList){
        if(root.isDirectory()){
            File [] directories = root.listFiles();
            if(directories != null){
                for(File file : directories){
                    if(file.isDirectory() && file.getName().toLowerCase().equals(name.toLowerCase()) ){
                        fileList.add(file);
                    }else{
                        searchDirectoryByName(file,name,fileList);
                    }
                }
            }
        }
        return fileList;
    }

    private static List<File> searchByKeyInText(File root, String key, List<File> fileList) throws FileNotFoundException {
        root = new File(new File(".").getAbsolutePath());
        List<File> currentList = searchTXTFiles(root,fileList);
        List<String> resultList = new ArrayList<>();

        for(File file : currentList){
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()){
                resultList.add(scanner.nextLine());
            }
            for(String str : resultList){
                if(str.contains(key)){
                    currentList.clear();
                    currentList.add(file);
                    System.out.println(file.getAbsolutePath());
                    break;
                }
            }
            resultList.clear();
        }
        return currentList;
    }





}
