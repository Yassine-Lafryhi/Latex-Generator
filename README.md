# Latex-Generator
A JAVA web application to generate EXAM/TP/TD in LATEX format
# <strong style="color: blue; opacity: 0.80">**JAVA PROJECT**</strong> :mortar_board: 
## <span style="color:green "> 1.Project Presentation</span>
* <strong style="color:dark">Our project is a LATEX GENERATOR that its specially role is generate an EXAM or TP or TD for a professor , To make his work less difficult and more effective to win time and organize work.
Also the Administrator can add the exercises in database as he likes, he just need to select the subject and the level of and the body of the exercise.
 ## <span style="color:green">2.Main Technologies Used</span>
 #### <span style="color:#0036ad"> 1.MONGODB:</span>
 
 * <strong style="color:dark">MongoDB is a cross-platform document-oriented database program. Classified as a NoSQL database program, MongoDB uses JSON-like documents with schema. MongoDB is developed by MongoDB Inc. and licensed under the Server Side Public License (SSPL).
 *see also* [MONGODB](https://mongodb.com)
#### <span style="color:#0036ad"> 2.JAVA ENTREPRISE EDITION:</span>

* <strong style="color:dark">JEE or Java Enterprise Edition is a technology developed by Sun then Oracle for the development of distributed applications in Java. JEE is based on JSE (Java Standard Edition) and is intended for web platforms.
*see also* [JEE](https://JEE.com)
#### <span style="color:#0036ad"> 3.VAADIN:</span>
* <strong style="color:dark">Vaadin is an open-source platform for web application development. The Vaadin Platform includes a set of web components, a Java web framework, and a set of tools and application starters. Its flagship product, Vaadin Platform (previously Vaadin Framework) allows the implementation of HTML5 web user interfaces using the Java Programming Language.
*see also* [VAADIN](https://vaadin.com)
#### <span style="color:#0036ad"> 4.JETTY:</span>
* <strong style="color:dark">Eclipse Jetty provides a Web server and javax.servlet container, plus support for HTTP/2, WebSocket, OSGi, JMX, JNDI, JAAS and many other integrations. These components are open source and available for commercial use and distribution.
* <strong style="color:dark">Eclipse Jetty is used in a wide variety of projects and products, both in development and production. Jetty can be easily embedded in devices, tools, frameworks, application servers, and clusters. See the Jetty Powered page for more uses of Jetty.
*see also* [JETTY](https://jetty.com)
#### <span style="color:#0036ad"> 5.MAVEN:</span>
* <strong style="color:dark">Apache Maven (commonly known as Maven) is a production management and automation tool for Java software projects in general and Java EE in particular. It is used to automate continuous integration during software development. Maven is managed by the Apache Software Foundation organization. The tool was previously a branch of the Jakarta Project organization.
* <strong style="color:dark">The objective is to produce software from its sources, optimizing the tasks carried out for this purpose and guaranteeing the correct manufacturing order.
*see also* [MAVEN]( http://maven.apache.org)
 ## <span style="color:green ">3.CONCEPTION & ANALYSES</span>
* ###### <strong style="color:orange; opacity: 0.80">UML</strong>
![](https://i.imgur.com/u06rV0F.png)
> Classe Diagramm [color=#fd837b]

![](https://i.imgur.com/IAoVeTk.png)
> Use Case Diagramm [color=#fd837b]
 ---
## <strong style="color: blue; opacity: 0.80" >HOW TO RUN IT?   </strong>
:::info
The important thing :mag: 
:::
```bash=
mvn jetty:run
```
###### <span style="color:green "> Connection between databse and java code </span>
```java=
int port_no = 27017;
        String host_name = "67.207.85.11", db_name =         "ProjectDB", db_coll_name = "Subjects";
        MongoDatabase db;
        // Mongodb connection string.
        String client_url = "mongodb://" + host_name + ":" + port_no + "/" + db_name;
        MongoClientURI uri = new MongoClientURI(client_url);

        // Connecting to the mongodb server using the given    client uri.
        MongoClient mongo_client = new MongoClient(uri);

        // Fetching the database from the mongodb.
        db = mongo_client.getDatabase(db_name);
        [01:28, 05/01/2020] Khaoula ElMajni: MongoClient mongoClient = new MongoClient("67.207.85.11", 27017);
        DB database = mongoClient.getDB("ProjectDB");
MongoCollection<Document> coll=db.getCollection(db_coll_name);
```
![](https://i.imgur.com/u0VHhPj.jpg)


>Login Form [color=#fd837b]

![](https://i.imgur.com/Sn6DxE7.jpg)
>Creating an account form [color=#fd837b]

![](https://i.imgur.com/NPMguLw.jpg)
>Adding an exercise form [color=#fd837b]

![](https://i.imgur.com/EhEKD5o.jpg)
>Latex Generating form [color=#fd837b]
![](https://i.imgur.com/5firr6m.jpg)
>Latex Generated form [color=#fd837b]
---
* <strong style="color: dark ; opacity: 0.80">Finally we want say thank you to the one and only, our professor Mr BADDI YOUSSEF *Doctor Assistant in UCD* for his supporting  and encouraging to us, also for giving us this opportunity to improve our skills and to know new technologies as those giant ones that we worked on.

*see also about* Mr [BADDI](https://ma.linkedin.com/in/youssefbaddi/fr)
</strong>
> Created by :[name=LAFRYHI YASSINE ELMAJNI KHAOULA IMANE LAHLOU]
[time=sun,2020,01,04][color=#EF0101]