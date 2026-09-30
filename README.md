# Library Management System v3

Professional Java MVC Library Management System using Servlet, JSP, JDBC and MySQL.

## Included features
- Professional login screen with clear error messages
- Session-protected dashboard
- Dashboard statistics and quick actions
- Book management: add, delete and instant search
- Member management: add, delete and instant search
- Book issue workflow
- Book return workflow
- Automatic late fine calculation (₹5/day)
- Responsive desktop/mobile layout
- MySQL database with demo data

## Requirements
- JDK 17+
- Maven 3.9+
- MySQL 8+
- Apache Tomcat 10.1+

## Run
1. Execute `database/library.sql` in MySQL.
2. Open `src/main/java/com/library/util/DBConnection.java` and set your MySQL password.
3. Run `mvn clean package`.
4. Copy `target/library.war` into Tomcat `webapps`.
5. Start Tomcat.
6. Open `http://localhost:8080/library/`.

### Demo login
Username: `admin`
Password: `admin123`

If the login page opens but the database is unavailable, the page now shows a clear database connection message instead of appearing blank.
