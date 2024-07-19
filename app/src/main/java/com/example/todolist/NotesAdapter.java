package com.example.todolist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {
    private List<Note> notes = new ArrayList<>();
    private OnNoteClickListener onNoteClickListener;
    public void setOnNoteClickListener(OnNoteClickListener onNoteClickListener) {
        this.onNoteClickListener = onNoteClickListener;
    }
    public List<Note> getNotes() {
        return new ArrayList<>(notes);
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.note_item,
                parent,
                false
        );
        return new NotesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
        Note note = notes.get(position);
        holder.noteItemView.setText(note.getText());

        int colorId;
        switch (note.getPriority()){
            case 0:
                colorId = R.color.low_priority_active;
                break;
            case 1:
                colorId = R.color.medium_priority_active;
                break;
            default:
                colorId = R.color.high_priority_active;
        }

        int color = ContextCompat.getColor(holder.itemView.getContext(), colorId);
        holder.noteItemView.setBackgroundColor(color);

        holder.noteItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onNoteClickListener != null){
                    onNoteClickListener.onNoteClick(note);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    static class NotesViewHolder extends RecyclerView.ViewHolder{
        private TextView noteItemView;
        public NotesViewHolder(@NonNull View itemView) {
            super(itemView);
            noteItemView = itemView.findViewById(R.id.tvNoteItem);
        }
    }

    interface OnNoteClickListener{
        void onNoteClick(Note note);
    }
}
