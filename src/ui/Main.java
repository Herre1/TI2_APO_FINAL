package ui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;

import com.google.gson.Gson;

import exceptions.NonExistingCity;
import exceptions.DuplicateId;
import exceptions.NonExistentCommand;
import exceptions.NonExistingCountry;
import model.*;

/*
 * COMMAND ESTRUCTURE
 * INSERT COUNTRIES : INSERT INTO countries(id, name, population, countryCode) VALUES ('6ec3e8ec-3dd0-11ed-b878-0242ac120002', 'Colombia', 50.2, '+57') 
 * INSERT CITIES :  INSERT INTO cities(id,name,countryID,population) VALUES ('e4aa04f6-3dd0-11ed-b878-0242ac120002','Cali','6ec3e8ec-3dd0-11ed-b878-0242ac120002',2.2)
 * SELECT FROM : 
 * DELETE FROM :
 */
public class Main {

    static Scanner lector = new Scanner(System.in);

    public static int menu() {
        System.out.println("Welcome to the geographic information System : ");
        System.out
                .println("Type an Option for the Menu : \n1. Insert Command \n2. Import data from .SQL file \n3. Exit");
        int option;
        option = Integer.parseInt(lector.nextLine());
        return option;

    }

    public static void main(String[] args) throws NonExistentCommand, DuplicateId {

        // IMPORT CONTROLLER METODS
        GeograpicController controller = new GeograpicController();

        // LOAD JSON DATA
        ArrayList<Country> temp = new ArrayList<>();
        loadDataBaseFromJson(temp);
        if (!temp.isEmpty()) {
            controller.addToProgram(temp);
        }

        // BOOLEAN FOR A LOOP AND OPTION FOR THE SWITCH
        boolean control = true;
        String ans = "";
        while (control) {
            int option = menu();

            switch (option) {
                case 1:

                    System.out.println("Insert the command:");
                    String command = lector.nextLine();
                    String[] auxSplit = command.split(" ");
                    if (auxSplit[0].equals("INSERT") && auxSplit[1].equals("INTO")) {
                        try {
                            ans = controller.insert(command);
                            if (!ans.equals("")) {
                                System.out.println(ans);
                            }
                        } catch (NonExistentCommand a) {
                            a.printStackTrace();
                        } catch (NonExistingCountry b) {
                            b.printStackTrace();
                        } catch (NonExistingCity c) {
                            c.printStackTrace();
                        }
                        break;
                    } else if (auxSplit[0].equals("SELECT") && auxSplit[1].equals("*")
                            && auxSplit[2].equals("FROM")) {
                        boolean booleanContinue = false;
                        for (int i = 0; i < auxSplit.length - 1; i++) {
                            if (auxSplit[i].equals("ORDER") && auxSplit[i + 1].equals("BY")) {
                                booleanContinue = true;
                                break;
                            }
                        }
                        if (booleanContinue) {
                            try {
                                ans = controller.orderBy(command);
                                if (!ans.equals("")) {
                                    System.out.println(ans);
                                }
                            } catch (NonExistentCommand a) {
                                a.printStackTrace();
                            } catch (NonExistingCity b) {
                                b.printStackTrace();
                            }
                        } else {
                            try {
                                ans = controller.searchUndFilter(command);
                            } catch (NonExistentCommand a) {
                                a.printStackTrace();
                            } catch (NonExistingCity b) {
                                b.printStackTrace();
                            }
                        }
                        break;
                    } else if (auxSplit[0].equals("DELETE") && auxSplit[1].equals("FROM")) {
                        try {
                            ans = controller.delete(command);
                            if (!ans.equals("")) {
                                System.out.println(ans);
                            }
                        } catch (NonExistentCommand a) {
                            a.printStackTrace();
                        } catch (NonExistingCity b) {
                            b.printStackTrace();
                        }
                        break;
                    }
                    break;
                case 2:
                    ArrayList<String> SQLCommand = new ArrayList<>();
                    System.out.println("Write the name of the SQL file with .txt :");
                    String dir = lector.nextLine();
                    loadCommandsFromSql(SQLCommand, dir);
                    if (!SQLCommand.get(0).equals("")) {
                        for (int i = 0; i < SQLCommand.size(); i++) {
                            String command2 = SQLCommand.get(i);
                            String[] comSplit2 = command2.split(" ");
                            if (comSplit2[0].equals("INSERT") && comSplit2[1].equals("INTO")) {
                                try {
                                    controller.insert(command2);
                                } catch (NonExistingCountry a) {
                                    a.printStackTrace();
                                } catch (NonExistentCommand b) {
                                    b.printStackTrace();
                                } catch (NonExistingCity c) {
                                    c.printStackTrace();
                                }
                            } else if (comSplit2[0].equals("SELECT") && comSplit2[1].equals("*")
                                    && comSplit2[2].equals("FROM")) {
                                boolean booleanContinue = false;
                                for (int j = 0; j < comSplit2.length - 1; j++) {
                                    if (comSplit2[i].equals("ORDER") && comSplit2[i + 1].equals("BY")) {
                                        booleanContinue = true;
                                        break;
                                    }
                                }
                                if (booleanContinue) {
                                    try {
                                        controller.orderBy(command2);
                                    } catch (NonExistentCommand a) {
                                        a.printStackTrace();
                                    } catch (NonExistingCity b) {
                                        b.printStackTrace();
                                    }
                                } else {
                                    try {
                                        controller.searchUndFilter(command2);
                                    } catch (NonExistentCommand a) {
                                        a.printStackTrace();
                                    } catch (NonExistingCity b) {
                                        b.printStackTrace();
                                    }
                                }
                            } else if (comSplit2[0].equals("DELETE") && comSplit2[1].equals("FROM")) {
                                try {
                                    controller.delete(command2);
                                } catch (NonExistentCommand a) {
                                    a.printStackTrace();
                                } catch (NonExistingCity b) {
                                    b.printStackTrace();
                                }
                            }
                        }
                    }
                    break;
                case 3:
                    // METOD FOR EXITING AND SAVING AL THE DATA IN THE JSON FILE
                    saveDataBaseInJson(controller.getDataBaseAsArrayList());
                    control = false;
                    break;
                default:
                    System.out.println("ERROR THATS NOT AN OPTION !!!");
                    break;
            }
        }
        
    }

    // METODS FOR JSON WORKING
    public static void saveDataBaseInJson(ArrayList<Country> arr) {
        try {
            File file = new File("dataBase/Data.json");
            FileOutputStream fos = new FileOutputStream(file);
            Gson gson = new Gson();
            String json = gson.toJson(arr);
            fos.write(json.getBytes(StandardCharsets.UTF_8));
            fos.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadDataBaseFromJson(ArrayList<Country> arr) {
        try {
            File file = new File("dataBase/Data.json");
            FileInputStream fis = new FileInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));

            String json = "";
            String line = "";
            while ((line = reader.readLine()) != null) {
                json += line;
            }
            Gson gson = new Gson();
            Country[] data = gson.fromJson(json, Country[].class);
            if (data != null) {
                for (Country b : data) {
                    arr.add(b);
                }
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadCommandsFromSql(ArrayList<String> arr, String dir) {
        try {
            File file = new File(dir);
            FileInputStream fis = new FileInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));

            String line = "";
            while ((line = reader.readLine()) != null) {
                arr.add(line);
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
