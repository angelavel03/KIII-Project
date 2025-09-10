package com.notes.backend.service.impl;

import com.notes.backend.model.InvalidNoteIdException;
import com.notes.backend.model.Note;
import com.notes.backend.repository.NoteRepository;
import com.notes.backend.service.NoteService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;

    public NoteServiceImpl(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    @Override
    public List<Note> getAllNotes() {
        return noteRepository.findAll();
    }

    @Override
    public Note addNote(String title, String content) {
        return noteRepository.save(new Note(title, content));
    }

    @Override
    public Note editNote(UUID noteId, String title, String content) {
        Note note = noteRepository.findById(noteId).orElseThrow(() -> new InvalidNoteIdException(noteId));
        note.setTitle(title);
        note.setContent(content);
        return noteRepository.save(note);
    }

    @Override
    public void deleteNote(UUID noteId) {
        Note note = noteRepository.findById(noteId).orElseThrow(() -> new InvalidNoteIdException(noteId));
        noteRepository.delete(note);
    }
}
