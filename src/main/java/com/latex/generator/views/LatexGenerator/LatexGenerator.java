package com.latex.generator.views.LatexGenerator;

import com.latex.generator.backend.Database;
import com.mongodb.*;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.templatemodel.TemplateModel;
import com.latex.generator.MainView;
import com.latex.generator.views.LatexGenerator.LatexGenerator.FormViewModel;
import org.bson.Document;
import org.vaadin.olli.FileDownloadWrapper;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.prefs.Preferences;

import static com.mongodb.client.model.Filters.eq;

@Route(value = "Latex-Generator", layout = MainView.class)
@RouteAlias(value = "latex-generator", layout = MainView.class)
@PageTitle("Latex Generator")
@JsModule("src/views/LatexGenerator/latex-generator.js")
@Tag("latex-generator")
public class LatexGenerator extends PolymerTemplate<FormViewModel> {
    ArrayList<String> names;
    String fileName = "";

    public static interface FormViewModel extends TemplateModel {

    }

    String date = "";
    String teacherEmail = "";
    @Id
    private ComboBox<String> SchoolSubject;

    @Id
    private Button GeneratePDF;
    @Id
    private Button GenerateLATEX;
    @Id
    private TextField TestTitle;
    @Id
    private IntegerField easy;
    @Id
    private TextField Grade;
    @Id
    private TextField TestDuration;
    @Id
    private IntegerField medium;
    @Id
    private IntegerField difficult;
    @Id
    private ComboBox<String> TestType;
    ArrayList<String> exercices = new ArrayList<>();
    ArrayList<Integer> nombre = new ArrayList<>();

    public LatexGenerator() {
        if (Database.db == null) {
            Database.connect();
        }
        Preferences prefs = Preferences.userNodeForPackage(MainView.class);
        teacherEmail = prefs.get("TeacherEmail", "Undefined");
        exercices.add("TP");
        exercices.add("EXAM");
        exercices.add("TD");

        for (int i = 1; i < 11; i++) {
            nombre.add(i);
        }

        MongoCollection<Document> coll = Database.db.getCollection("Subjects");
        getAllDocuments(coll);

        SchoolSubject.setItems(names);
        TestType.setItems(exercices);
        TestType.setValue(exercices.get(0));
        SchoolSubject.setValue(names.get(0));
        GenerateLATEX.addClickListener(e -> {
            openLatexFile();
        });
        GeneratePDF.addClickListener(e -> {
            openPdfFile();
        });
    }


    public void generate() {

        MongoCollection<Document> collection = Database.db.getCollection("Exercises");

        ArrayList<String> easyExercises = new ArrayList<>();
        ArrayList<String> mediumExercises = new ArrayList<>();
        ArrayList<String> difficultExercises = new ArrayList<>();


        FindIterable<Document> theEasy = collection.find(Filters.and(Filters.eq("Difficulty", "Easy"), Filters.eq("Subject", SchoolSubject.getValue())));
        MongoCursor<Document> cursor = theEasy.iterator();
        while (cursor.hasNext()) easyExercises.add(cursor.next().get("Content").toString());

        FindIterable<Document> theMedium = collection.find(Filters.and(Filters.eq("Difficulty", "Medium"), Filters.eq("Subject", SchoolSubject.getValue())));
        MongoCursor<Document> cursor2 = theMedium.iterator();
        while (cursor2.hasNext()) mediumExercises.add(cursor2.next().get("Content").toString());

        FindIterable<Document> theDifficult = collection.find(Filters.and(Filters.eq("Difficulty", "Difficult"), Filters.eq("Subject", SchoolSubject.getValue())));
        MongoCursor<Document> cursor3 = theDifficult.iterator();
        while (cursor3.hasNext()) difficultExercises.add(cursor3.next().get("Content").toString());


        String latexFileContent = "\\documentclass{article}\n" +
                "\\setlength{\\textheight}{10.5in}\n" +
                "\\setlength{\\textwidth}{7.2in}\n" +
                "\\setlength{\\headheight}{0in}\n" +
                "\\setlength{\\headsep}{-0.6in}\n" +
                "\\setlength{\\topmargin}{0in}\n" +
                "\\setlength{\\oddsidemargin}{-0.6in}\n" +
                "\\setlength{\\evensidemargin}{0in}\n" +
                "\\setlength{\\parindent}{0in}\n" +
                "\\begin{document}\n" +
                "\n" +
                "{\\Large  Module/Element : " + SchoolSubject.getValue() + " \\hfill    \\\\\n" +
                "\n" +
                "Nom de l'enseignant : PR. " + getTeacherFullName().toUpperCase() + "\\hfill    \\\\\n" +
                "\n" +
                "Niveau : " + Grade.getValue() + " \\hfill    \\\\\n" +
                "\n" +
                "Durée : " + TestDuration.getValue() + "\n" +
                "  \n" +
                "\\begin{center}\n" +
                "\\Large " + TestTitle.getValue() + "\\\\\n" +
                "\n" +
                "\\hrulefill\\end{center}\n" +
                "}";

        int easyExercisesNumber, mediumExercisesNumber, difficultExercisesNumber, exercisesSum;


        easyExercisesNumber = Integer.parseInt(easy.getValue().toString());
        difficultExercisesNumber = Integer.parseInt(difficult.getValue().toString());
        mediumExercisesNumber = Integer.parseInt(medium.getValue().toString());


        exercisesSum = (mediumExercisesNumber + difficultExercisesNumber + easyExercisesNumber);
        ArrayList<String> allExercises = new ArrayList<>();

        allExercises.addAll(easyExercises.subList(0, easyExercisesNumber));
        allExercises.addAll(mediumExercises.subList(0, mediumExercisesNumber));
        allExercises.addAll(difficultExercises.subList(0, difficultExercisesNumber));


        for (int i = 0; i < exercisesSum; i++) {
            Random random = new Random();
            int exerciseIndex = random.nextInt(exercisesSum);
            String exercise = allExercises.get(exerciseIndex);

            latexFileContent += "{\\Large \\Large Exercise " + (i + 1) + ": (" + ((float) 20 / exercisesSum) + " pts) \\par}\n" +
                    "\n" + exercise +
                    "\\\\\\\\";


        }


        latexFileContent += "\n\\end{document}";
        BufferedWriter writer = null;
        try {

            String format = "yyyy/MM/dd";
            DateFormat dateFormatter = new SimpleDateFormat(format);
            date = dateFormatter.format(new Date());
            new File("GeneratedFiles/" + date).mkdirs();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HHmmss");
            LocalDateTime now = LocalDateTime.now();
            fileName = SchoolSubject.getValue().replace(" ", "-") + "-" + TestType.getValue() + "-" + dtf.format(now);
            writer = new BufferedWriter(new FileWriter("GeneratedFiles/" + date + "/" + fileName + ".tex"));
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
        String command = "pdflatex -output-directory=" + new File("GeneratedFiles/" + date).getPath() + " -jobname=" + fileName + " " + new File("GeneratedFiles/" + date + "/" + fileName + ".tex").getPath();
        System.out.println(command);
        ProcessBuilder builder = new ProcessBuilder(
                "bash", "-c", command);
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


        File file = new File("GeneratedFiles/" + date + "/" + fileName + ".log");
        if (file.exists()) {
            file.delete();
        }

        file = new File("GeneratedFiles/" + date + "/" + fileName + ".aux");
        if (file.exists()) {
            file.delete();
        }
    }


    private void openLatexFile() {
        generate();
       new FileDownloadWrapper(fileName + ".tex", new File("GeneratedFiles/" + date + "/" + fileName + ".tex"));

    }


    private void openPdfFile() {
      generate();
        new FileDownloadWrapper(fileName + ".tex", new File("GeneratedFiles/" + date + "/" + fileName + ".pdf"));

    }

    private void getAllDocuments(MongoCollection<Document> col) {
        FindIterable<Document> fi = col.find(Filters.eq("Email", teacherEmail));
        MongoCursor<Document> cursor = fi.iterator();
        try {
            names = new ArrayList<>();
            while (cursor.hasNext()) {
                names.add(cursor.next().getString("Name"));
            }
        } finally {
            cursor.close();
        }

    }


    public String getTeacherFullName() {
        String teacherFullName = "";
        MongoCollection<Document> col = Database.db.getCollection("Users");
        FindIterable<Document> fi = col.find(eq("Email", teacherEmail));
        MongoCursor<Document> cursor = fi.iterator();

        if (cursor.hasNext()) {
            Document dc = cursor.next();
            teacherFullName += dc.getString("FirstName");
            teacherFullName += " ";
            teacherFullName += dc.getString("LastName");
        }
        return teacherFullName;
    }


}
