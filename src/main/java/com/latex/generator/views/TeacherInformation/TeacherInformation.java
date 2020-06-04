package com.latex.generator.views.TeacherInformation;

import com.latex.generator.backend.Database;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.templatemodel.TemplateModel;

import com.latex.generator.MainView;
import org.bson.Document;

import java.util.ArrayList;
import java.util.prefs.Preferences;

import static com.mongodb.client.model.Filters.eq;

@Route(value = "Teacher-Information", layout = MainView.class)
@RouteAlias(value = "", layout = MainView.class)
@PageTitle("Teacher-Information")
@JsModule("src/views/TeacherInformation/teacher-information.js")
@Tag("Teacher-Information")
public class TeacherInformation extends PolymerTemplate<TeacherInformation.FormViewModel> {
    ArrayList<String> names = new ArrayList<>();
    String teacherEmail = "";

    public static interface FormViewModel extends TemplateModel {

    }

    @Id
    private TextField FirstName;

    @Id
    private TextField LastName;

    @Id
    private TextField UniversityName;

    @Id
    private TextField EstablishmentName;

    @Id
    private Button Save;


    @Id
    private EmailField Email;

    @Id
    private PasswordField NewPassword;

    @Id
    private PasswordField RepeatPassword;

    public TeacherInformation() {
        if (Database.db == null) {
            Database.connect();
        }
        Preferences prefs = Preferences.userNodeForPackage(MainView.class);
        teacherEmail = prefs.get("TeacherEmail", "Undefined");

        String db_coll_name = "Users";

        MongoCollection<Document> col = Database.db.getCollection(db_coll_name);
        FindIterable<Document> fi = col.find(eq("Email", teacherEmail));
        MongoCursor<Document> cursor = fi.iterator();

        if (cursor.hasNext()) {
            Document dc = cursor.next();
            FirstName.setValue(dc.getString("FirstName"));
            LastName.setValue(dc.getString("LastName"));
            Email.setValue(dc.getString("Email"));
            UniversityName.setValue(dc.getString("UniversityName"));
            EstablishmentName.setValue(dc.getString("EstablishmentName"));
        }


        Save.addClickListener(e -> {

            updateTeacherInformation(Database.db.getCollection("Users"));


        });


    }

    private void updateTeacherInformation(MongoCollection<Document> collection) {

        collection.updateOne(eq("Email", teacherEmail), new Document("$set", new Document("FirstName", FirstName.getValue())));
        collection.updateOne(eq("Email", teacherEmail), new Document("LastName", LastName.getValue()));
        collection.updateOne(eq("Email", teacherEmail), new Document("$set",  new Document("Email",  Email.getValue())));
        collection.updateOne(eq("Email", teacherEmail), new Document("$set", new Document("UniversityName", UniversityName.getValue())));
        collection.updateOne(eq("Email", teacherEmail), new Document("$set",   new Document("EstablishmentName",EstablishmentName.getValue())));
        Notification.show("The teacher information has been updated successfully !", 2000, Notification.Position.BOTTOM_CENTER);

    }


    public String MD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }
}
