package com.nadimmahmud.futuregrid.Fragments;

import android.os.Bundle;

import androidx.core.app.ShareCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.nadimmahmud.futuregrid.R;

public class HomeFragment extends Fragment {
    ImageView ClickMenu,ClickClose;
    DrawerLayout drawerLayout;
    View view;
    String value;
    public HomeFragment(String value) {
        this.value = value;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);

        initialise(view);
        /*Toast.makeText(getActivity(), value, Toast.LENGTH_SHORT).show();*/
        ClickMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDrawer(drawerLayout);

            }
        });
        ClickClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeDrawer(drawerLayout);
            }
        });

        return view;
    }
    private void initialise(View view) {

        ClickMenu = view.findViewById(R.id.ClickMenu);
        ClickClose = view.findViewById(R.id.ClickClose);
        drawerLayout = view.findViewById(R.id.drawer_layout);

    }
    private void openDrawer(DrawerLayout drawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START);
        view.setClickable(false);
    }

    private void closeDrawer(DrawerLayout drawerLayout) {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
            view.setClickable(true);
        }
    }
}