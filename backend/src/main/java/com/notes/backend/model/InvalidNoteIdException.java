package com.notes.backend.model;

import java.util.UUID;

public class InvalidNoteIdException extends RuntimeException {
    public InvalidNoteIdException(UUID noteId) {
        super("Note with ID: " + noteId + " does not exist");
    }
}
