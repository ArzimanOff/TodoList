package com.example.todolist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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
        /*
        holder.noteItemView - это сам элемент заметки
         */
        Note note = notes.get(position);

        // таким образом получаем textView внутри holder.noteItemView и устанавливаем ему текст
        TextView noteItemViewText = holder.noteItemView.findViewById(R.id.tvNoteItemText);

        // таким образом получаем View внутри holder.noteItemView который отображает
        // приоритет и устанвливаем его
        View priorityView = holder.noteItemView.findViewById(R.id.priorityView);

        setNoteViewFeatures(note, noteItemViewText, priorityView);

        holder.noteItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onNoteClickListener != null){
                    onNoteClickListener.onNoteClick(note);
                }
            }
        });
    }

    /**
     * Функция для установки свойств заметки (текста, приоритета)
     * @param note объект заметки, в которую будут установлены свойства
     * @param noteItemViewText объект textView внутри note в которую надо установить текст
     * @param priorityView объект View внутри note которому нужно установить цвет фона исходя от приоритета
     */
    private void setNoteViewFeatures(Note note, TextView noteItemViewText, View priorityView) {
        noteItemViewText.setText(note.getText());
        int bgId;
        switch (note.getPriority()){
            case 0:
                bgId = R.drawable.low_priority_view_bg;
                break;
            case 1:
                bgId = R.drawable.medium_priority_view_bg;
                break;
            default:
                bgId = R.drawable.high_priority_view_bg;
        }
        priorityView.setBackgroundResource(bgId);
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    static class NotesViewHolder extends RecyclerView.ViewHolder{
        private LinearLayout noteItemView;
        public NotesViewHolder(@NonNull View itemView) {
            super(itemView);
            noteItemView = itemView.findViewById(R.id.noteItem);
        }
    }

    interface OnNoteClickListener{
        void onNoteClick(Note note);
    }
}
