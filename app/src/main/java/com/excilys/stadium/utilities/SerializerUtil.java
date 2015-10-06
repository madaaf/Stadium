package com.excilys.stadium.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import timber.log.Timber;

/**
 * Created by excilys on 05/06/15.
 */
public class SerializerUtil<Objet> {
    private File fichier;
    private FileInputStream fileInput;
    private FileOutputStream fileOutput;
    private ObjectInputStream objectInput;
    private ObjectOutputStream objectOutput;

    public SerializerUtil(String filepath) {
        fichier = new File(filepath);
        if(!fichier.exists() && (fichier.length()==0)){
            try {
                fichier.createNewFile();
            } catch (IOException e) {
                Timber.e(e, "Error creating the file " + filepath);
                throw new RuntimeException();
            }
        }

    }

    public void setObjet(Objet o){
        try {
            fileOutput = new FileOutputStream(fichier, false);
            objectOutput = new ObjectOutputStream(fileOutput);

            try {
                objectOutput.writeObject(o);
                objectOutput.flush();
            } finally {
                try {
                    objectOutput.close();
                } finally {
                    fileOutput.close();
                }
            }
        } catch (IOException e) {
            Timber.e(e, "Error serialzer set object " + o.toString());
            throw new RuntimeException();
        }
    }

    @SuppressWarnings("unchecked")
    public Objet getObject(){
        Objet obj = null;
        try {
            fileInput = new FileInputStream(fichier);
            objectInput = new ObjectInputStream(fileInput);

            try {
                obj = (Objet) objectInput.readObject();
            } finally {
                try {
                    objectInput.close();
                } finally {
                    fileInput.close();
                }
            }
        } catch(IOException e) {
            Timber.e(e, " IO exception, Error deserialzer getobject ");
            throw new RuntimeException();
        } catch(ClassNotFoundException e1) {
            Timber.e(e1, "ClassNotFoundException, Error deserialzer getobject ");
            throw new RuntimeException();
        }
        return obj;
    }


}
