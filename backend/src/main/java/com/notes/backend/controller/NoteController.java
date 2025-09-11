package com.notes.backend.controller;

import com.notes.backend.model.Note;
import com.notes.backend.model.NoteDTO;
import com.notes.backend.service.NoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/notes")
@CrossOrigin(origins = "*")
public class NoteController {
    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping
    public ResponseEntity<List<Note>> getAllNotes() {
        return ResponseEntity.ok(noteService.getAllNotes());
    }

    @PostMapping("/add")
    public ResponseEntity<Note> addNote(@RequestBody NoteDTO noteDTO) {
        return ResponseEntity.ok(noteService.addNote(noteDTO.getTitle(), noteDTO.getContent()));
    }

    @PutMapping("/edit/{noteId}")
    public ResponseEntity<Note> editNote(@PathVariable UUID noteId, @RequestBody NoteDTO noteDTO) {
        return ResponseEntity.ok(noteService.editNote(noteId, noteDTO.getTitle(), noteDTO.getContent()));
    }

    @DeleteMapping("/delete/{noteId}")
    public void deleteNote(@PathVariable UUID noteId) {
        noteService.deleteNote(noteId);
        ResponseEntity.noContent().build();
    }
}
