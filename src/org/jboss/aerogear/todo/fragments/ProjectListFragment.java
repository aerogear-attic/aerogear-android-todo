/**
 * JBoss, Home of Professional Open Source
 * Copyright Red Hat, Inc., and individual contributors
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

package org.jboss.aerogear.todo.fragments;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;
import org.jboss.aerogear.android.pipeline.LoaderPipe;
import org.jboss.aerogear.todo.R;
import org.jboss.aerogear.todo.ToDoApplication;
import org.jboss.aerogear.todo.activities.TodoActivity;
import org.jboss.aerogear.todo.callback.DeleteCallback;
import org.jboss.aerogear.todo.callback.ListFragmentCallbackHelper;
import org.jboss.aerogear.todo.callback.ReadCallback;
import org.jboss.aerogear.todo.data.Project;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ProjectListFragment extends Fragment implements ListFragmentCallbackHelper<Project> {
	
	private ArrayAdapter<Project> adapter;
	private List<Project> projects = new ArrayList<Project>();
	private LoaderPipe<Project> pipe;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.list, null);

		TextView title = (TextView) view.findViewById(R.id.title);
		title.setText(getResources().getString(R.string.projects));

		ImageView add = (ImageView) view.findViewById(R.id.add);
		add.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				((TodoActivity) getActivity()).showProjectForm();
			}
		});

		adapter = new ArrayAdapter<Project>(getActivity(),
				android.R.layout.simple_list_item_1, projects);
		ListView projectListView = (ListView) view.findViewById(R.id.list);
		projectListView.setAdapter(adapter);

		projectListView
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					public void onItemClick(AdapterView<?> adapterView,
							View view, int position, long id) {
						final Project project = projects.get(position);
						((TodoActivity) getActivity()).showProjectForm(project);
					}
				});

		projectListView
				.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
					@Override
					public boolean onItemLongClick(AdapterView<?> adapterView,
							View view, int position, long id) {
						final Project project = projects.get(position);
						new AlertDialog.Builder(getActivity())
								.setMessage(
										"Delete '" + project.getTitle() + "'?")
								.setPositiveButton("Yes",
										new DialogInterface.OnClickListener() {
											@Override
											public void onClick(
													DialogInterface dialogInterface,
													int i) {
												startDelete(project);
											}
										}).setNegativeButton("Cancel", null)
								.show();
						return true;
					}
				});

		return view;

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ToDoApplication application = ((ToDoApplication) getActivity().getApplication());
		pipe = application.getPipeline().get("projects", this, application);
	}

	@Override
	public void onStart() {
		super.onStart();
		startRefresh();
	}

	public void startRefresh() {
		pipe.reset();
		pipe.read(new ReadCallback<Project>());
	}

	private void startDelete(Project project) {
		pipe.remove(project.getId(), new DeleteCallback());
	}

	@Override
	public List<Project> getList() {
		return projects;
	}

	@Override
	public ArrayAdapter<Project> getAdapter() {
		return adapter;
	}
}
