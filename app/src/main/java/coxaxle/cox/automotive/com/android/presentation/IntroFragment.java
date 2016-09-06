package coxaxle.cox.automotive.com.android.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import coxaxle.cox.automotive.com.android.R;


public class IntroFragment extends Fragment {

    private static final String BACKGROUND_COLOR = "backgroundColor";
    private static final String PAGE = "page";

    private int mBackgroundColor, mPage;

    View view;
    private TextView mFinishIntro;

    public static IntroFragment newInstance(int backgroundColor, int page) {
        IntroFragment frag = new IntroFragment();
        Bundle b = new Bundle();
        b.putInt(BACKGROUND_COLOR, backgroundColor);
        b.putInt(PAGE, page);
        frag.setArguments(b);
        return frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!getArguments().containsKey(BACKGROUND_COLOR))
            throw new RuntimeException("Fragment must contain a \"" + BACKGROUND_COLOR + "\" argument!");
        mBackgroundColor = getArguments().getInt(BACKGROUND_COLOR);

        if (!getArguments().containsKey(PAGE))
            throw new RuntimeException("Fragment must contain a \"" + PAGE + "\" argument!");
        mPage = getArguments().getInt(PAGE);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Select a layout based on the current page
        int layoutResId;
        switch (mPage) {
            case 0:
                layoutResId = R.layout.intro_fragment_layout_1;

                break;
            case 1:
                layoutResId = R.layout.intro_fragment_layout_2;
                break;
            default:
                layoutResId = R.layout.intro_fragment_layout_3;
        }

        // Inflate the layout resource file
        view = getActivity().getLayoutInflater().inflate(layoutResId, container, false);
        finishIntro(view, mPage);
        // Set the current page index as the View's tag (useful in the PageTransformer)
        view.setTag(mPage);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Set the background color of the root view to the color specified in newInstance()
        View background = view.findViewById(R.id.intro_background);
        background.setBackgroundColor(mBackgroundColor);


    }

    private void finishIntro(View v, int layoutResId) {

        switch (layoutResId) {
            case 0:
                mFinishIntro = (TextView) v.findViewById(R.id.skip_intro_action_text);

                mFinishIntro.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), LoginOptionsActivity.class);
                        startActivity(intent);

                        getActivity().finish();

                    }
                });

                break;

            case 2:
                mFinishIntro = (TextView) v.findViewById(R.id.continue_intro_action_text);

                mFinishIntro.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(getActivity(), LoginOptionsActivity.class);

                        startActivity(intent);

                        getActivity().finish();

                    }
                });

                break;
        }

    }

}