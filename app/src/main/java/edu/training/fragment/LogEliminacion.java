package edu.training.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import edu.training.data.DBProvider;
import edu.training.droidbountyhunter.DetalleLogEliminacion;
import edu.training.droidbountyhunter.R;
import edu.training.interfaces.OnLogListener;

public class LogEliminacion extends Fragment {

    private ArrayAdapter<String> mAdapter;
    private ArrayList<String[]> mLogs;
    private OnLogListener mListener;
    private boolean mIsTablet = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_log_eliminacion, container, false);

        DBProvider database = new DBProvider(getActivity());
        mLogs = database.ObtenerLogsEliminacion();
        String[] logsRow = new String[mLogs.size()];
        for (int index = 0; index < mLogs.size(); index++) {
            logsRow[index] = mLogs.get(index)[0] + " --> " + mLogs.get(index)[1];
        }

        ListView lista = (ListView) view.findViewById(R.id.lista);
        mAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, logsRow);
        lista.setAdapter(mAdapter);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String date = mLogs.get(position)[1];
                String status = mLogs.get(position)[2];
                Toast.makeText(getContext(), String.valueOf(position) + " " + mAdapter.getItem(position), Toast.LENGTH_SHORT).show();
                if(mIsTablet) {
                    mListener.onLogItemList(date, status);
                } else {
                    Intent intent = new Intent(getContext(), DetalleLogEliminacion.class);
                    intent.putExtra("isTablet", mIsTablet);
                    intent.putExtra("status", status);
                    intent.putExtra("date", date);
                    startActivity(intent);
                }
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mIsTablet = getActivity().getSupportFragmentManager().findFragmentById(R.id.fragmentDetalleLogEliminacion) != null;
        if(mIsTablet) {
            mListener = (OnLogListener) getActivity().getSupportFragmentManager().findFragmentById(R.id.fragmentDetalleLogEliminacion);
        }
    }
}
