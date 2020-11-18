/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.FileManager;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.*;


/**
 * @author sap1
 */
public class ObjectManager {

    // read txt file, para ller el query
    public static String readFile(String path) throws FileNotFoundException, IOException {
        String everything;

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            everything = sb.toString();
        }

        return everything;
    }

    // escribe un objeto
    public static void writeObject(Object obj, String filePath) {
        try {
            //use buffering
            OutputStream file = new FileOutputStream(filePath);
            OutputStream buffer = new BufferedOutputStream(file);
            try (ObjectOutput output = new ObjectOutputStream(buffer)) {
                // escribe el objeto
                output.writeObject(obj);
            }
        } catch (IOException ex) {
            System.out.println("ERRROROROROROROROROR " + ex.getMessage());
        }
    }

    // escribe un objeto
    public static Object readObject(String filePath) {
        try {
            //use buffering
            InputStream file = new FileInputStream(filePath);
            InputStream buffer = new BufferedInputStream(file);
            try (ObjectInput input = new ObjectInputStream(buffer)) {
                //deserialize the List
                return input.readObject();
            }
        } catch (ClassNotFoundException | IOException ignored) {

        }
        return null;
    }


}// fin clase
