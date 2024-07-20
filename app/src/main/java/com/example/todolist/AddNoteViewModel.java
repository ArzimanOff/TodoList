package com.example.todolist;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class AddNoteViewModel extends AndroidViewModel {
    private NotesDao notesDao;
    private MutableLiveData<Boolean> noteAdded = new MutableLiveData<>();
    public AddNoteViewModel(@NonNull Application application) {
        super(application);
        notesDao = NoteDatabase.getInstance(application).notesDao();
    }

    public void saveNote(Note note) {
         notesDao.add(note)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action() {
                    @Override
                    public void run() throws Throwable {
                        noteAdded.setValue(true);
                    }
                });
    }

    public NotesDao getNotesDao() {
        return notesDao;
    }

    public LiveData<Boolean> getNoteAdded() {
        return noteAdded;
    }
}
