package com.latex.generator.views.AddExercise;


import com.latex.generator.MainView;
import com.latex.generator.backend.Database;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
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
    // This is the Java companion file of a design
    // You can find the design file in /frontend/src/views/src/views/form/form-view.js
    // The design can be easily edited by using Vaadin Designer (vaadin.com/designer)

    public static interface FormViewModel extends TemplateModel {

    }

    String teacherEmail = "";
    @Id
    private ComboBox<String> combo;

    @Id
    private VerticalLayout all;

    @Id
    private Button cancel;
    @Id
    private Button add;
    @Id
    private TextArea textarea;
    @Id
    private ComboBox<String> combobox;

    public AddExercise() {
        Preferences prefs = Preferences.userNodeForPackage(MainView.class);
        teacherEmail = prefs.get("TeacherEmail", "Undefined");
        //all.setSizeFull();

        //all.setSizeFull();
        //wrapper.setAlignItems(FlexComponent.Alignment.CENTER);
        ArrayList<String> level = new ArrayList<>();
        level.add("Easy");
        level.add("Difficult");
        level.add("Medium");
        combobox.setItems(level);
        // Mongodb initialization parameters.

        String db_coll_name = "Subjects";

        Database.connect();

        // Fetching the collection from the mongodb.
        MongoCollection<Document> coll = Database.db.getCollection(db_coll_name);
        getAllDocuments(coll);
        combo.setItems(names);
        combo.setValue(names.get(0));
        // Fetching all the documents from the mongodb.


        // log.info("\n");

        // Fetching a single document from the mongodb based on a search_string.
        //getSelectiveDocument(coll);

        // Configure Form

        // Binder<Employee> binder = new Binder<>(Employee.class);

        // Bind fields. This where you'd define e.g. validation rules
        /// binder.bindInstanceFields(this);

        //  cancel.addClickListener(e -> binder.readBean(null));
        add.addClickListener(e -> {
            // getAllDocuments(coll);
            textarea.getValue();
            basicDBObject_Example(new MongoClient("67.207.85.11", 27017).getDB("ProjectDB").getCollection("Exercises"));
            //Notification.show(textarea.getValue());
            textarea.setValue("");
            Notification.show("THe exercise has been added successfully !",2000, Notification.Position.BOTTOM_CENTER);
        });
    }

    private void getAllDocuments(MongoCollection<Document> col) {
        //log.info("Fetching all documents from the collection");

        // Performing a read operation on the collection.
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


    private void basicDBObject_Example(DBCollection collection) {

        BasicDBObject document = new BasicDBObject();
        document.put("Content", textarea.getValue());
        document.put("Subject", combo.getValue());
        document.put("Difficulty", combobox.getValue());
        // Notification.show(combo.getValue()+textarea.getValue());
       /* BasicDBObject documentDetail = new BasicDBObject();
        documentDetail.put("addressLine1", "Sweet Home");
        documentDetail.put("addressLine2", "Karol Bagh");
        documentDetail.put("addressLine3", "New Delhi, India");

        document.put("address", documentDetail);*/

        collection.insert(document);
    }
}
