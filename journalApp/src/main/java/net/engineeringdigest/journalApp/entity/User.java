package net.engineeringdigest.journalApp.entity;
/**
 * Each user will have a UserName, Password and a ist of journal entries
 */

import lombok.Data;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "users")
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
    List<JournalEntry> journalEntryList = new ArrayList<>();



}
