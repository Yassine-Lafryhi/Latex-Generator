package com.latex.generator.views.AddExercise;

import com.latex.generator.MainView;
import com.latex.generator.backend.Database;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.templatemodel.TemplateModel;
import org.bson.Document;

import java.util.ArrayList;
import java.util.prefs.Preferences;

@Route(value = "Add-Exercise", layout = MainView.class)
@RouteAlias(value = "Add-Exercise", layout = MainView.class)
@PageTitle("Add-Exercise")
@JsModule("src/views/AddExercise/add-exercise.js")
@Tag("Add-Exercise")
public class AddExercise extends PolymerTemplate<AddExercise.FormViewModel> {
    ArrayList<String> names = new ArrayList<>();

    public static interface FormViewModel extends TemplateModel {
    }

    String teacherEmail = "";
    @Id
    private ComboBox<String> combo;
    @Id
    private VerticalLayout all;
    @Id
    private Button Add;
    @Id
    private TextArea textarea;
    @Id
    private ComboBox<String> combobox;

    public AddExercise() {
        if (Database.db == null) {
            Database.connect();
        }
        Preferences prefs = Preferences.userNodeForPackage(MainView.class);
        teacherEmail = prefs.get("TeacherEmail", "Undefined");
        ArrayList<String> level = new ArrayList<>();
        level.add("Easy");
        level.add("Medium");
        level.add("Difficult");
        combobox.setItems(level);
        String db_coll_name = "Subjects";
        MongoCollection<Document> coll = Database.db.getCollection(db_coll_name);
        getAllDocuments(coll);
        combo.setItems(names);
        combo.setValue(names.get(0));
        Add.addClickListener(e -> {
            textarea.getValue();
            basicDBObject_Example(Database.db.getCollection("Exercises"));
            textarea.setValue("");
            Notification.show("THe exercise has been added successfully !", 2000, Notification.Position.BOTTOM_CENTER);
        });
    }

    private void getAllDocuments(MongoCollection<Document> col) {
        FindIterable<Document> fi = col.find(Filters.eq("Email", teacherEmail));
        MongoCursor<Document> cursor = fi.iterator();
        try {
            while (cursor.hasNext()) {
                names.add(cursor.next().getString("Name"));
            }
        } finally {
            cursor.close();
        }
    }

    private void basicDBObject_Example(MongoCollection<Document> collection) {
        Document document = new Document();
        document.put("Content", textarea.getValue());
        document.put("Subject", combo.getValue());
        document.put("Difficulty", combobox.getValue());
        collection.insertOne(document);
    }
}
