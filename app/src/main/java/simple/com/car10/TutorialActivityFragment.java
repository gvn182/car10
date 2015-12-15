/*
 * Copyright 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package simple.com.car10;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TutorialActivityFragment extends Fragment {
    /**
     * The argument key for the page number this fragment represents.
     */
    public static final String ARG_PAGE = "page";

    /**
     * The fragment's page number, which is set to the argument value for {@link #ARG_PAGE}.
     */
    private int mPageNumber;

    /**
     * Factory method for this fragment class. Constructs a new fragment for the given page number.
     */
    public static TutorialActivityFragment create(int pageNumber) {
        TutorialActivityFragment fragment = new TutorialActivityFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public TutorialActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPageNumber = getArguments().getInt(ARG_PAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        ViewGroup rootView;
        // Inflate the layout containing a title and body text.
        switch (mPageNumber)
        {
            case 0:rootView = (ViewGroup) inflater
                    .inflate(R.layout.fragment_screen_slide_page, container, false);break;
            case 1: rootView = (ViewGroup) inflater
                    .inflate(R.layout.fragment_screen_slide_page2, container, false);break;
            default :rootView  = (ViewGroup) inflater
                    .inflate(R.layout.fragment_screen_slide_page3, container, false);break;


        }


        // Set the title view to show the page number.
     //   ((TextView) rootView.findViewById(android.R.id.text1)).setText(
       //         getString(R.string.title_template_step, mPageNumber + 1));

        return rootView;
    }

    /**
     * Returns the page number represented by this fragment object.
     */
    public int getPageNumber() {
        return mPageNumber;
    }
}
