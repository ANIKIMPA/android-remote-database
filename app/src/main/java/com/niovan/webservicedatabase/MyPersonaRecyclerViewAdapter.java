package com.niovan.webservicedatabase;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.niovan.webservicedatabase.fragments.ListaPersonasFragment.OnListFragmentInteractionListener;
import com.niovan.webservicedatabase.models.Persona;
import com.squareup.picasso.Picasso;

import java.util.List;


public class MyPersonaRecyclerViewAdapter extends RecyclerView.Adapter<MyPersonaRecyclerViewAdapter.ViewHolder> {

    private final List<Persona> mListValues;
    private final OnListFragmentInteractionListener mListener;

    public MyPersonaRecyclerViewAdapter(List<Persona> items, OnListFragmentInteractionListener listener) {
        mListValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_persona_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mListValues.get(position);
        holder.textViewNombre.setText(mListValues.get(position).nombre);
        holder.textViewFechaNacimiento.setText(mListValues.get(position).fechaNacimiento);
        holder.textViewGenero.setText(Character.toString(mListValues.get(position).genero));
        holder.ratingBarValoracion.setRating(mListValues.get(position).valoracion);

        Picasso.get()
                .load(mListValues.get(position).urlPhoto)
                .into(holder.imageViewPhoto);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mListValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView textViewNombre;
        public final TextView textViewFechaNacimiento;
        public final TextView textViewGenero;
        public final ImageView imageViewPhoto;
        public final RatingBar ratingBarValoracion;
        public Persona mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            textViewNombre = view.findViewById(R.id.textViewNombre);
            textViewFechaNacimiento = view.findViewById(R.id.textViewFecha);
            textViewGenero = view.findViewById(R.id.textViewGenero);
            imageViewPhoto = view.findViewById(R.id.imageViewPhoto);
            ratingBarValoracion = view.findViewById(R.id.ratingBarValoracion);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + textViewNombre.getText() + "'";
        }
    }
}
