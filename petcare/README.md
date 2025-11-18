# PetCare Veterinary Clinic Management System

This project provides a simple console application written in Java for managing a veterinary clinic.  It demonstrates CRUD (Create, Read, Update, Delete) operations for owners (`proprietários`), veterinarians (`veterinários`), animals (`animais`) and consultations (`consultas`) using PostgreSQL and JDBC.  The application adheres to basic database normalization and uses prepared statements and `try‑with‑resources` to prevent SQL injection and ensure resources are closed properly.

## Prerequisites

* **Java 17+** – the source code targets Java 17.  Ensure that both the JDK and the PostgreSQL JDBC driver (`postgresql-<version>.jar`) are available on your classpath.
* **PostgreSQL** – install PostgreSQL and create a user with rights to create databases and tables.
* **psql** client (optional) – to run the DDL script.

## Database setup

1. Run the SQL script `clinicaveterinariadb.sql` located in the project root.  This script creates the database `clinicaveterinariadb` and all necessary tables, primary keys, foreign keys and indexes.  For example:

   ```bash
   psql -U your_user -f clinicaveterinariadb.sql
   ```

2. Update the JDBC connection information in `com/petcare/DBConnection.java` (URL, user and password) to match your PostgreSQL host, port and credentials.

## Compiling and running

1. From the project root (`petcare`), compile the source files.  Ensure that the PostgreSQL JDBC driver is on the classpath when compiling and running.  For example:

   ```bash
   # assuming postgresql driver is in libs/postgresql.jar
   javac -classpath libs/postgresql.jar -d out src/com/petcare/DBConnection.java src/com/petcare/model/*.java src/com/petcare/dao/*.java src/com/petcare/app/PetCareApp.java
   
   # run the application
   java -classpath libs/postgresql.jar:out com.petcare.app.PetCareApp
   ```

2. Follow the on‑screen menu to manage owners, veterinarians, animals and consultations.  You can create, list, update and delete entries.  The consultations menu also allows listing consultations based on an owner's CPF using a join between tables.

## Notes

* Unique constraints enforce that each owner has a unique CPF and each veterinarian has a unique CRMV.
* Foreign key constraints enforce that each animal belongs to one owner and each consultation is associated with exactly one animal and one veterinarian.  Cascade deletes are used so that removing an owner or veterinarian automatically removes associated animals or consultations.
* All SQL operations use `PreparedStatement` to avoid SQL injection and `try‑with‑resources` to close `Connection`, `Statement` and `ResultSet` objects automatically【488517508522160†L64-L94】【961424596952096†L208-L216】.
* The database schema follows the third normal form (3NF), meaning that all non‑key attributes depend on the primary key and there are no transitive dependencies【161766739894210†L135-L143】.  Each table has a surrogate primary key and uses foreign keys to reference related tables, preserving referential integrity【671618758217790†L384-L409】.
