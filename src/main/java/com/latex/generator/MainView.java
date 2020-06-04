package com.latex.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.prefs.Preferences;

import com.latex.generator.backend.Database;
import com.latex.generator.views.AddExercise.AddExercise;
import com.latex.generator.views.LatexGenerator.LatexGenerator;
import com.latex.generator.views.TeacherInformation.TeacherInformation;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.TabVariant;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.RouteConfiguration;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import org.bson.Document;

@JsModule("./styles/shared-styles.js")
@PWA(name = "Latex-Generator", shortName = "Latex-Generator")
@Theme(value = Lumo.class, variant = Lumo.LIGHT)
public class MainView extends AppLayout {
    private Tabs menu;

    public MainView() {
        Preferences prefs = Preferences.userNodeForPackage(MainView.class);
        Database.connect();
        menu = createMenuTabs();
        addToNavbar(menu);
        LoginI18n i18n = LoginI18n.createDefault();
        LoginOverlay component = new LoginOverlay();
        LoginI18n.Form frm = new LoginI18n.Form();
        frm.setTitle("Log In");
        frm.setSubmit("Validate");
        frm.setPassword("Password :");
        frm.setUsername("Email :");
        frm.setForgotPassword("Forget Password ?");
        LoginI18n.Header hdr = new LoginI18n.Header();
        hdr.setTitle("Latex Generator");
        hdr.setDescription("TP/TD/EXAM Generator In Latex Format");
        i18n.setForm(frm);
        i18n.setHeader(hdr);
        i18n.setAdditionalInformation("Please use the following information to log in: Email: baddi.y@ucd.ac.ma, Password: Admin@&2020");
        i18n.getErrorMessage().setTitle("Error while logging !");
        i18n.getErrorMessage().setMessage("The credentials are incorrect, please try again !");
        component.setI18n(i18n);
        component.addLoginListener(e -> {
            Notification.show(i18n.getForm().getPassword() + i18n.getForm().getUsername());
            FindIterable<Document> fi = Database.db.getCollection("Users").
                    find(Filters.eq("Email", e.getUsername()));
            MongoCursor<Document> cursor = fi.iterator();
            try {
                if (cursor.hasNext()) {
                    String password = cursor.next().getString("Password");
                    if (password.equals(MD5(e.getPassword()))) {
                        component.setOpened(false);
                    } else {
                        component.setError(true);
                        component.setEnabled(true);
                    }
                } else {
                    component.setError(true);
                    component.setEnabled(true);
                }
            } finally {
                cursor.close();
            }
        });
        String TeacherEmail = prefs.get("TeacherEmail", "Undefined");
        if (TeacherEmail.equals("Undefined")) {
            component.setOpened(true);
        }
    }

    private static Tabs createMenuTabs() {
        final Tabs tabs = new Tabs();
        tabs.setOrientation(Tabs.Orientation.HORIZONTAL);
        tabs.add(getAvailableTabs());
        return tabs;
    }

    private static Tab[] getAvailableTabs() {
        final List<Tab> tabs = new ArrayList<>();
        tabs.add(createTab("1- Teacher Information", TeacherInformation.class));
        tabs.add(createTab("2- Add Exercise", AddExercise.class));
        tabs.add(createTab("3- Latex Generator", LatexGenerator.class));
        Database.connect();
        return tabs.toArray(new Tab[tabs.size()]);
    }

    private static Tab createTab(String title,
                                 Class<? extends Component> viewClass) {
        return createTab(populateLink(new RouterLink(null, viewClass), title));
    }

    private static Tab createTab(Component content) {
        final Tab tab = new Tab();
        tab.addThemeVariants(TabVariant.LUMO_ICON_ON_TOP);
        tab.add(content);
        return tab;
    }

    private static <T extends HasComponents> T populateLink(T a, String title) {
        a.add(title);
        return a;
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        selectTab();
    }

    private void selectTab() {
        String target = RouteConfiguration.forSessionScope()
                .getUrl(getContent().getClass());
        Optional<Component> tabToSelect = menu.getChildren().filter(tab -> {
            Component child = tab.getChildren().findFirst().get();
            return child instanceof RouterLink
                    && ((RouterLink) child).getHref().equals(target);
        }).findFirst();
        tabToSelect.ifPresent(tab -> menu.setSelectedTab((Tab) tab));
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
