package com.notes.backend.service;

import com.notes.backend.model.Note;

import java.util.List;
import java.util.UUID;

public interface NoteService {
    List<Note> getAllNotes();
    Note addNote(String title, String content);
    Note editNote(UUID noteId, String title, String content);
    void deleteNote(UUID noteId);
}