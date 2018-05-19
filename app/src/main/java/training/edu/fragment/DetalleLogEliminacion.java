package training.edu.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import training.edu.droidbountyhunter.R;
import training.edu.interfaces.OnLogListener;

public class DetalleLogEliminacion extends Fragment implements OnLogListener {

    private boolean mIsTablet = false;
    private TextView mStatusTxt;
    private TextView mDateTxt;
    private String mStatus;
    private String mDate;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIsTablet = getActivity().getIntent().getBooleanExtra("isTablet", false);
        if(!mIsTablet) {
            mStatus = getActivity().getIntent().getStringExtra("status");
            mDate = getActivity().getIntent().getStringExtra("date");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detalle_log_de_eliminacion, container, false);

        mStatusTxt = (TextView) view.findViewById(R.id.txtEstatus);
        mDateTxt = (TextView) view.findViewById(R.id.txtFecha);
        if(!mIsTablet) {
            UpdateData();
        }

        return view;
    }

    private void UpdateData() {
        if(mStatus != null || mDate != null) {
            mStatusTxt.setText(mStatus.equals("0") ? "Fugitivo" : "Atrapado");
            mDateTxt.setText(mDate);
        }
    }

    @Override
    public void onLogItemList(String date, String status) {
        mStatus = status;
        mDate = date;
        UpdateData();
    }
}
