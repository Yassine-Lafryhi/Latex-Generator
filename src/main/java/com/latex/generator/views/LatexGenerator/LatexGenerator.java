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
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.templatemodel.TemplateModel;
import com.latex.generator.MainView;
import com.latex.generator.views.LatexGenerator.LatexGenerator.FormViewModel;
import org.bson.Document;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.prefs.Preferences;

@Route(value = "Latex-Generator", layout = MainView.class)
@RouteAlias(value = "latex-generator", layout = MainView.class)
@PageTitle("Latex Generator")
@JsModule("src/views/LatexGenerator/latex-generator.js")
@Tag("latex-generator")
public class LatexGenerator extends PolymerTemplate<FormViewModel> {
    ArrayList<String> names;

    public static interface FormViewModel extends TemplateModel {

    }
    String teacherEmail = "";
    @Id
    private ComboBox<String> combo;

    @Id
    private Button GeneratePDF;
    @Id
    private Button GenerateLATEX;

    @Id
    private IntegerField easy;
    @Id
    private TextField niveau;
    @Id
    private TextField duree;
    @Id
    private IntegerField medium;
    @Id
    private IntegerField difficult;
    @Id
    private ComboBox<String>combobox;
    ArrayList<String> exercices=new ArrayList<>();
    ArrayList<Integer>nombre=new ArrayList<>();

    MongoDatabase db;
    public LatexGenerator() {
        Preferences prefs = Preferences.userNodeForPackage(MainView.class);
        teacherEmail = prefs.get("TeacherEmail", "Undefined");
        exercices.add("TP");
        exercices.add("EXAM");
        exercices.add("TD");

        for (int i=1;i<11;i++){
            nombre.add(i);
        }
        int port_no = 27017;
        String host_name = "67.207.85.11", db_name = "ProjectDB", db_coll_name = "Subjects";
        String client_url = "mongodb://" + host_name + ":" + port_no + "/" + db_name;
        MongoClientURI uri = new MongoClientURI(client_url);
        MongoClient mongo_client = new MongoClient(uri);
        db = mongo_client.getDatabase(db_name);
        MongoCollection<Document> coll = db.getCollection(db_coll_name);
        getAllDocuments(coll);

        combo.setItems(names);
        combobox.setItems(exercices);
        combobox.setValue(exercices.get(0));
        combo.setValue(names.get(0));
        GenerateLATEX.addClickListener(e -> {
            getExercise();
        });
        GeneratePDF.addClickListener(e -> {
            getExercise();
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
        easyExercisesNumber = Integer.parseInt(easy.getValue().toString());
        difiicultExerciseNumber = Integer.parseInt(difficult.getValue().toString());
        mediumExercisesNumber = Integer.parseInt(medium.getValue().toString());
        exercisesSum = (mediumExercisesNumber + difiicultExerciseNumber + easyExercisesNumber);
        ArrayList<String> allExercises = new ArrayList<>();

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
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter("tp1.tex"));
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
                "bash", "-c", "pdflatex -output-directory="  + " -jobname=" + combobox.getValue() + " " + "tp1.tex");
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



        File file = new File( combobox.getValue()+ ".log");
        if (file.exists()) {
            file.delete();
        }

        file = new File( combobox.getValue() + ".aux");
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


    }
    private void getAllDocuments(MongoCollection<Document> col) {
        FindIterable<Document> fi = col.find(Filters.eq("Email", teacherEmail));
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


}
