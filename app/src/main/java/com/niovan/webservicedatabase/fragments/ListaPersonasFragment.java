package com.niovan.webservicedatabase.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.niovan.webservicedatabase.MyPersonaRecyclerViewAdapter;
import com.niovan.webservicedatabase.R;
import com.niovan.webservicedatabase.models.Persona;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class ListaPersonasFragment extends Fragment {

    // Declaracion de variables...
    RecyclerView recyclerView;
    List<Persona> listaPersonas;

    MyPersonaRecyclerViewAdapter adapterPersonas;

    // TODO: Customize parameters
    private int mColumnCount = 1;

    private OnListFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ListaPersonasFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_persona_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            //Lista de elementos (Personas)
            listaPersonas = new ArrayList<>();
            listaPersonas
                    .add(new Persona("Carlos",
                            "03/14/2018",
                            'M', 2,
                            "https://ekiy5aot90-flywheel.netdna-ssl.com/wp-content/uploads/2014/05/segue-blog-what-is-persona-development-why-is-it-important.png"));
            listaPersonas
                    .add(new Persona("Bernald",
                            "03/14/1993",
                            'M', 4,
                            "https://www.vbout.com/images/persona/buyer-persona-image1.png"));
            listaPersonas
                    .add(new Persona("Jennifer",
                            "11/18/1994",
                            'F', 5,
                            "http://ingeus.com.sg/wp-content/uploads/2017/10/buyer-persona-image4.png"));
            adapterPersonas = new MyPersonaRecyclerViewAdapter(listaPersonas, mListener);
            recyclerView.setAdapter(adapterPersonas);
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Persona item);
    }
}
