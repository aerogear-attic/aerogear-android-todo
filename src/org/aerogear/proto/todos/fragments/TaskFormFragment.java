/*
 * JBoss, Home of Professional Open Source
 * Copyright 2012, Red Hat, Inc., and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.aerogear.proto.todos.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.actionbarsherlock.app.SherlockDialogFragment;
import org.aerogear.proto.todos.Constants;
import org.aerogear.proto.todos.R;
import org.aerogear.proto.todos.activities.MainActivity;
import org.aerogear.proto.todos.data.Task;
import org.aerogear.proto.todos.services.ToDoAPIService;

import java.util.Calendar;

public class TaskFormFragment extends Fragment {

    private final Task task;

    private int day;
    private int month;
    private int year;

    private EditText date;

    public TaskFormFragment() {
        this.task = new Task();
        splitDate();
    }

    public TaskFormFragment(Task task) {
        this.task = task;
        splitDate(task.getDate());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.form_task, null);

        TextView title = (TextView) view.findViewById(R.id.title);
        title.setText(getResources().getString(R.string.tasks));

        final EditText name = (EditText)view.findViewById(R.id.name);
        date = (EditText)view.findViewById(R.id.date);
        final EditText description = (EditText)view.findViewById(R.id.description);

        if( task.getId() != null ) {
            Button button = (Button) view.findViewById(R.id.buttonSave);
            button.setText(R.string.update);
        }

        name.setText(task.getTitle());
        date.setText(task.getDate());
        description.setText(task.getDescription());

        ImageView buttonCalendar = (ImageView) view.findViewById(R.id.buttonCalendar);
        buttonCalendar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                showTimePickerDialog(view);
            }
        });

        Button buttonSave = (Button) view.findViewById(R.id.buttonSave);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameStr = name.getText().toString();
                if (nameStr.length() < 1) {
                    name.setError("Please enter a title");
                    return;
                }

                task.setTitle(nameStr);
                task.setDate(date.getText().toString());
                task.setDescription(description.getText().toString());

                Intent intent = new Intent(getActivity(), ToDoAPIService.class);
                intent.setAction(Constants.ACTION_POST_TASK);
                intent.putExtra(Constants.EXTRA_TASK, task);
                getActivity().startService(intent);
                ((MainActivity) getActivity()).showTaskList();
            }
        });

        Button buttonCancel = (Button) view.findViewById(R.id.buttonCancel);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).showTaskList();
            }
        });

        return view;

    }

    private void showTimePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
    }

    private void setDate() {
        date.setText(year + "-" + String.format("%02d", month + 1) + "-" + String.format("%02d", day));
    }

    private void splitDate() {
        final Calendar c = Calendar.getInstance();
        day = c.get(Calendar.DAY_OF_MONTH);
        month = c.get(Calendar.MONTH);
        year = c.get(Calendar.YEAR);
    }

    private void splitDate(String date) {
        String[] data = date.split("-");
        year = Integer.valueOf(data[0]);
        month = Integer.valueOf(data[1]) - 1;
        day = Integer.valueOf(data[2]);
    }

    private class DatePickerFragment extends SherlockDialogFragment implements DatePickerDialog.OnDateSetListener {
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            TaskFormFragment.this.year = year;
            TaskFormFragment.this.month = month;
            TaskFormFragment.this.day = day;
            setDate();
        }
    }

}

