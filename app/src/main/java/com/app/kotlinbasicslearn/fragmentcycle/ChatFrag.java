package com.app.kotlinbasicslearn.fragmentcycle;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.kotlinbasicslearn.R;
import com.app.kotlinbasicslearn.databinding.FragmentChatBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChatFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChatFrag extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private FragmentChatBinding binding;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ChatFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChatFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static ChatFrag newInstance(String param1, String param2) {
        ChatFrag fragment = new ChatFrag();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private String TAG = "ChatFrag";

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        Log.d(TAG, "onAttach: CHAT FRAG");

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate: CHAT FRAG");

        // Setup ViewModel, arguments, or analytics event

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d(TAG, "onCreateView: CHAT FRAG");

        binding = FragmentChatBinding.inflate(inflater, container, false);


//        return inflater.inflate(R.layout.fragment_chat, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "onViewCreated: CHAT FRAG");
        // Setup RecyclerView, LiveData observers, etc.
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: CHAT FRAG");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: CHAT FRAG");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: CHAT FRAG");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: CHAT FRAG");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        Clear view bindings to avoid memory leaks.
        binding = null;
        Log.d(TAG, "onDestroyView: CHAT FRAG");
    }

    @Override
    public void onDestroy() {
        //Cleanup any retained objects, cancel jobs.
        super.onDestroy();
//        Final cleanup of fragment before being destroyed.
        Log.d(TAG, "onDestroy: CHAT FRAG");
    }

    @Override
    public void onDetach() {
        //Nullify activity or listener references.
        super.onDetach();
        Log.d(TAG, "onDetach: CHAT FRAG");
    }

}