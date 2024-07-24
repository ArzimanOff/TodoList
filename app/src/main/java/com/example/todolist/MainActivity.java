package com.example.todolist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;


public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerViewNotes;
    private NotesAdapter notesAdapter;
    private LinearLayout emptyNotesPlaceholder;
    private MaterialButton newNoteFromPlaceholderBtn;
    private FloatingActionButton btnNewNote;
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        notesAdapter = new NotesAdapter();
        notesAdapter.setOnNoteClickListener(new NotesAdapter.OnNoteClickListener() {
            @Override
            public void onNoteClick(Note note) {
                //TODO
            }
        });
        recyclerViewNotes.setAdapter(notesAdapter);

        viewModel.getNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                if (notes.isEmpty()) {
                    emptyNotesPlaceholder.setVisibility(View.VISIBLE);
                    btnNewNote.setVisibility(View.GONE);
                    recyclerViewNotes.setVisibility(View.GONE);
                } else {
                    emptyNotesPlaceholder.setVisibility(View.GONE);
                    recyclerViewNotes.setVisibility(View.VISIBLE);
                    btnNewNote.setVisibility(View.VISIBLE);
                    notesAdapter.setNotes(notes);
                }
            }
        });
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(
                        0,
                        ItemTouchHelper.LEFT
                ) {
                    @Override
                    public boolean onMove(
                            @NonNull RecyclerView recyclerView,
                            @NonNull RecyclerView.ViewHolder viewHolder,
                            @NonNull RecyclerView.ViewHolder target
                    ) {
                        return false;
                    }

                    @Override
                    public void onSwiped(
                            @NonNull RecyclerView.ViewHolder viewHolder,
                            int direction
                    ) {
                        int position = viewHolder.getAdapterPosition();
                        Note note = notesAdapter.getNotes().get(position);
                        viewModel.remove(note);
                        ApplicationSignals.vibrate(getBaseContext());
                    }
                });

        itemTouchHelper.attachToRecyclerView(recyclerViewNotes);


        // Устанавливаем обработчик нажатий с помощью лямбда-выражений
        View.OnClickListener openAddNoteClickListener = v -> openAddNoteActivity();

        btnNewNote.setOnClickListener(openAddNoteClickListener);
        newNoteFromPlaceholderBtn.setOnClickListener(openAddNoteClickListener);
    }

    private void openAddNoteActivity() {
        Intent intent = AddNoteActivity.newIntent(MainActivity.this);
        startActivity(intent);
    }

    private void initViews() {
        recyclerViewNotes = findViewById(R.id.recyclerViewNotes);
        btnNewNote = findViewById(R.id.btnNewNote);
        emptyNotesPlaceholder = findViewById(R.id.empty_notes_placeholder);
        newNoteFromPlaceholderBtn = findViewById(R.id.placeholder_new_note_btn);
    }
}
