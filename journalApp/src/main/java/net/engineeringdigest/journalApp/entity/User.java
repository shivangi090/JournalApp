package net.engineeringdigest.journalApp.entity;
/**
 * Each user will have a UserName, Password and a ist of journal entries
 */

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "users")
@Builder  //Lombok automatically generates a build class to set each field and a build() to create object
public class User {

    @Id
    private ObjectId id;

    @Indexed(unique = true)
    @NonNull
    private String userName;

    @NonNull
    private String password;

    /*
    @DBRef annotation is used to define a reference to another document stored in a different collection —
    similar to a foreign key in relational databases.
    Instead of storing the full referenced document, MongoDB stores only a reference (usually the _id) to it.
     */
    @DBRef
    private List<JournalEntry> journalEntryList = new ArrayList<>();

    private List<String> roles;



}
