package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;

    @Transactional
    public void saveEntry(JournalEntry journalEntry, String username){
        try {
            User user = userService.findByUserName(username);
            journalEntry.setDate(LocalDateTime.now());
            JournalEntry saved = journalEntryRepository.save(journalEntry);
            user.getJournalEntryList().add(saved);
            userService.saveEntry(user);
        } catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException("An exception has occured while saving te entry", e);
        }

    }
    public void saveEntry(JournalEntry journalEntry){
        journalEntryRepository.save(journalEntry);
    }
    public List<JournalEntry> getAll(){
        return journalEntryRepository.findAll();
    }

    public void deleteAll() {
        journalEntryRepository.deleteAll();
    }

    public Optional<JournalEntry> findById(ObjectId myId) {
        return journalEntryRepository.findById(myId);
    }

    @Transactional
    public boolean deleteById(ObjectId myId, String username) {
        boolean journalRemovedFromUser =false;
        try {
            User user = userService.findByUserName(username);
            journalRemovedFromUser = user.getJournalEntryList().removeIf(x -> x.getId().equals(myId));
            if(journalRemovedFromUser) {
                userService.saveEntry(user);
                journalEntryRepository.deleteById(myId);
            }
        } catch (Exception e) {
            throw new RuntimeException("An error occured while deleting the journal entry", e);
        }
        return journalRemovedFromUser;
    }

//    public List<JournalEntry> findByUserName(String username){
//
//    }
}
