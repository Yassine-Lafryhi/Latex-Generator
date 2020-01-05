package com.latex.generator.views.LatexGenerator;

import com.mongodb.*;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.templatemodel.TemplateModel;

import com.latex.generator.MainView;
import com.latex.generator.views.LatexGenerator.LatexGenerator.FormViewModel;
import org.bson.Document;

import javax.swing.*;
import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

@Route(value = "form", layout = MainView.class)
@RouteAlias(value = "latexgenerator", layout = MainView.class)
@PageTitle("Form")
@JsModule("src/views/LatexGenerator/latex-generator.js")
@Tag("latex-generator")
public class LatexGenerator extends PolymerTemplate<FormViewModel> {
    ArrayList<String> names;

    // This is the Java c
    // ompanion file of a design
    // You can find the design file in /frontend/src/views/src/views/form/form-view.js
    // The design can be easily edited by using Vaadin Designer (vaadin.com/designer)

    public static interface FormViewModel extends TemplateModel {

    }

    @Id
    private ComboBox<String> combo;

    @Id
    private Button Pdf;
    @Id
    private Button Latex;

    @Id
    private ComboBox <Integer> easy;
    @Id
    private TextField niveau;
    @Id
    private TextField duree;
    @Id
    private ComboBox <Integer> medium;
    @Id
    private ComboBox <Integer> difficult;
    static String name;
    @Id
    private ComboBox<String>combobox;
    String contenu="";
    ArrayList<String> exercices=new ArrayList<>();
    ArrayList<Integer>nombre=new ArrayList<>();

    MongoDatabase db;
    public LatexGenerator() {

        exercices.add("TP");
        exercices.add("EXAM");
        exercices.add("TD");

        for (int i=1;i<11;i++){
            nombre.add(i);
        }
        // Mongodb initialization parameters.
        int port_no = 27017;
        String host_name = "67.207.85.11", db_name = "ProjectDB", db_coll_name = "Subjects";

        // Mongodb connection string.
        String client_url = "mongodb://" + host_name + ":" + port_no + "/" + db_name;
        MongoClientURI uri = new MongoClientURI(client_url);

        // Connecting to the mongodb server using the given client uri.
        MongoClient mongo_client = new MongoClient(uri);

        // Fetching the database from the mongodb.
        db = mongo_client.getDatabase(db_name);

        // Fetching the collection from the mongodb.
        MongoCollection<Document> coll = db.getCollection(db_coll_name);
        getAllDocuments(coll);
        easy.setItems(nombre);
        difficult.setItems(nombre);
        medium.setItems(nombre);
        combo.setItems(names);
        combobox.setItems(exercices);
        combobox.setValue(exercices.get(0));
        combo.setValue(names.get(0));

        // Fetching all the documents from the mongodb.


        // log.info("\n");

        // Fetching a single document from the mongodb based on a search_string.
        getSelectiveDocument(coll);

        // Configure Form

        // Binder<Employee> binder = new Binder<>(Employee.class);

        // Bind fields. This where you'd define e.g. validation rules
        /// binder.bindInstanceFields(this);

        //  cancel.addClickListener(e -> binder.readBean(null));

        Latex.addClickListener(e -> {
            // getAllDocuments(coll);

            getExercise();

            //Notification.show(textarea.getValue());

        });
        Pdf.addClickListener(e -> {
            // getAllDocuments(coll);

            getExercise();

            //Notification.show(textarea.getValue());

        });
    }
    private void getExercise(){

        MongoClient mongoClient = new MongoClient("67.207.85.11", 27017);
        DB database = mongoClient.getDB("ProjectDB");
        DBCollection collection = database.getCollection("Exercises");

        ArrayList<String> easyExercises = new ArrayList<>();
        ArrayList<String> mediumExercises = new ArrayList<>();
        ArrayList<String> difficultExercises = new ArrayList<>();


        BasicDBObject searchQuery1 = new BasicDBObject();
        searchQuery1.put("Difficulty", "Easy");
        searchQuery1.put("Subject",combo.getValue());
        DBCursor cursor = collection.find(searchQuery1);

        while (cursor.hasNext()) {
            easyExercises.add(cursor.next().get("Content").toString());
        }

        BasicDBObject searchQuery2 = new BasicDBObject();
        searchQuery2.put("Difficulty", "Medium");
        searchQuery2.put("Subject",combo.getValue());
        DBCursor cursor2 = collection.find(searchQuery2);

        while (cursor2.hasNext())
            mediumExercises.add(cursor2.next().get("Content").toString());

        BasicDBObject searchQuery3 = new BasicDBObject();
        searchQuery3.put("Difficulty", "Difficult");
        searchQuery3.put("Subject",combo.getValue());
        DBCursor cursor3 = collection.find(searchQuery3);
        while (cursor3.hasNext()){
            difficultExercises.add(cursor3.next().get("Content").toString());

        }


        String latexFileContent = "\\documentclass{article}\n" +
                "\\renewcommand{\\baselinestretch}{1}\n" +
                "\\setlength{\\textheight}{9in}\n" +
                "\\setlength{\\textwidth}{7.2in}\n" +
                "\\setlength{\\headheight}{0in}\n" +
                "\\setlength{\\headsep}{-0.6in}\n" +
                "\\setlength{\\topmargin}{0in}\n" +
                "\\setlength{\\oddsidemargin}{-0.6in}\n" +
                "\\setlength{\\evensidemargin}{0in}\n" +
                "\\setlength{\\parindent}{0in}\n" +
                "\\begin{document}\n" +
                "{\\Large\\Huge  Module/Element :web \\hfill    \\\\\n" +
                "  Nom de l'ensiegnant :baddi\\hfill    \\\\\n" +
                "   Niveau :"+niveau.getValue()+" \\hfill    \\\\\n" +
                "   Durée:"+duree.getValue()+"\n" +
                "  \n" +
                "\\begin{center}\n" +
                "\\Huge Examen Session Normal\\\\\n" +
                "\\hrulefill\\end{center}";
        latexFileContent+="{\\Large "+combobox.getValue() +"\\ :"+combo.getValue()+"\\";
        int easyExercisesNumber, mediumExercisesNumber, difiicultExerciseNumber, exercisesSum;
        easyExercisesNumber = easy.getValue();
        difiicultExerciseNumber = difficult.getValue();
        mediumExercisesNumber = medium.getValue();
        exercisesSum = (mediumExercisesNumber + difiicultExerciseNumber + easyExercisesNumber);
        ArrayList<String> allExercises = new ArrayList<String>();

        allExercises.addAll(getExercise(difficultExercises, difiicultExerciseNumber));
        allExercises.addAll(getExercise(mediumExercises, mediumExercisesNumber));
        allExercises.addAll(getExercise(easyExercises, easyExercisesNumber));
        for (int i = 0; i < exercisesSum; i++) {
            Random random = new Random();
            int exerciseIndex = random.nextInt(exercisesSum);
            String exercise = allExercises.get(exerciseIndex);

            if(combobox.getValue().equals("Exam")){
                int note=20/exercisesSum;
                latexFileContent += ((i == 0 ? "" : "\\\\") + "{\\Large\\Huge Exercise" + (i + 1) + ": "+note+"point\\par} " + exercise + "\\\\");
            }
            else {
                latexFileContent += ((i == 0 ? "" : "\\\\") + "{\\Large\\Huge Exercise"+ (i + 1) + ": \\par} " + exercise + "\\\\");
            }
        }


        latexFileContent += "\n\\end{document}";
        //System.out.println(latexFileContent);
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter("D:\\LatexTest\\tp1.tex"));
            writer.write(latexFileContent);

        } catch (IOException e) {
        } finally {
            try {
                if (writer != null)
                    writer.close();
            } catch (IOException e) {
            }
        }
        Process p = null;

        ProcessBuilder builder = new ProcessBuilder(
                "cmd.exe", "/c", "pdflatex -output-directory=" + "D:\\LatexTest" + " -jobname=" + combobox.getValue() + " " + "D:\\LatexTest\\tp1.tex");
        builder.redirectErrorStream(true);

        try {
            p = builder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line = null;
        while (true) {
            try {
                line = r.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (line == null) {
                break;
            }
            System.out.println(line);
        }



        File file = new File("D:\\LatexTest\\" + combobox.getValue()+ ".log");
        if (file.exists()) {
            file.delete();
        }

        file = new File("D:\\LatexTest\\" + combobox.getValue() + ".aux");
        if (file.exists()) {
            file.delete();
        }
    }
    public  ArrayList<String> getExercise(ArrayList<String> list, int n) {
        ArrayList<String> list1 = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            Random random = new Random();
            list1.add(list.get(random.nextInt(list.size())));
        }
        return list1;
       /* ProcessBuilder builder = new ProcessBuilder(
                "cmd.exe", "/c", "pdflatex -output-directory=" + "D:\\LatexTest" + " -jobname=" + name + " " + "D:\\LatexTest\\tp1.tex");
        builder.redirectErrorStream(true);
        Process p = null;
        try {
            p = builder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line = null;
        while (true) {
            try {
                line = r.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (line == null) {
                break;
            }
            System.out.println(line);
        }



        File file = new File("D:\\LatexTest\\" + name + ".log");
        if (file.exists()) {
            file.delete();
        }

        file = new File("D:\\LatexTest\\" + name + ".aux");
        if (file.exists()) {
            file.delete();
        }
*/

    }
    private void getAllDocuments(MongoCollection<Document> col) {
        //log.info("Fetching all documents from the collection");

        // Performing a read operation on the collection.
        FindIterable<Document> fi = col.find(Filters.eq("Email", "baddi.y@ucd.ac.ma"));
        MongoCursor<Document> cursor = fi.iterator();
        try {
            names=new ArrayList<>();
            while(cursor.hasNext()) {
                names.add(cursor.next().getString("Name")) ;
            }
        } finally {
            cursor.close();
        }

    }

    // Fetch a selective document from the mongo collection.
    private  void getSelectiveDocument(MongoCollection<Document> col) {
        //  log.info("Fetching a particular document from the collection");

        // Performing a read operation on the collection.
        String col_name = "name", srch_string = "Charlotte Neil";
        FindIterable<Document> fi = col.find(Filters.eq(col_name, srch_string));
        MongoCursor<Document> cursor = fi.iterator();
        try {
            while(cursor.hasNext()) {
                //log.info(cursor.next().toJson());
            }
        } finally {
            cursor.close();
        }
    }
    private  void basicDBObject_Example(DBCollection collection){

        BasicDBObject document = new BasicDBObject();
        document.put("ExerciseSubject", combo.getValue());
        // Notification.show(combo.getValue()+textarea.getValue());
       /* BasicDBObject documentDetail = new BasicDBObject();
        documentDetail.put("addressLine1", "Sweet Home");
        documentDetail.put("addressLine2", "Karol Bagh");
        documentDetail.put("addressLine3", "New Delhi, India");

        document.put("address", documentDetail);*/

        // collection.insert(document);
    }
}
