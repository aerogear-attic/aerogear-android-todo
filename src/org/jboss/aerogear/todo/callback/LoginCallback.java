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
package org.jboss.aerogear.todo.callback;

import org.jboss.aerogear.android.http.HeaderAndBody;
import org.jboss.aerogear.todo.activities.TodoActivity;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;
import org.jboss.aerogear.android.pipeline.support.AbstractFragmentActivityCallback;

public class LoginCallback extends AbstractFragmentActivityCallback<HeaderAndBody> {

	private static final long serialVersionUID = 398218128L;
	private static final String TAG = LoginCallback.class.getSimpleName();

	public LoginCallback() {
            super(serialVersionUID);
	}
	
        @Override
	public void onSuccess(HeaderAndBody data) {
		getFragmentActivity().startActivity(new Intent(getFragmentActivity(), TodoActivity.class));
	}

        @Override
	public void onFailure(Exception e) {
		Log.e(TAG, e.getMessage(), e);
		Toast.makeText(getFragmentActivity(), "Login failed", Toast.LENGTH_LONG)
				.show();
	}
}
